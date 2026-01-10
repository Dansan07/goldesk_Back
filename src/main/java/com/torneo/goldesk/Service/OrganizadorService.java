package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.Organizador;
import com.torneo.goldesk.Entity.Rol;
import com.torneo.goldesk.Repository.OrganizadorRepository;
import com.torneo.goldesk.Repository.RolRepository;
import com.torneo.goldesk.Service.Authenticator.JwtService;
import com.torneo.goldesk.dto.login.LoginRequestDTO;
import com.torneo.goldesk.dto.actores.organizador.OrganizadorCreateDTO;
import com.torneo.goldesk.dto.actores.organizador.OrganizadorResponseDTO;
import com.torneo.goldesk.dto.actores.organizador.OrganizadorUpdateDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizadorService {

    private final OrganizadorRepository organizadorRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageService messageService;
    private final JwtService jwtService;

    public OrganizadorService(OrganizadorRepository organizadorRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder, MessageService messageService, JwtService jwtService) {
        this.organizadorRepository = organizadorRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageService = messageService;
        this.jwtService = jwtService;
    }

    public void activarOrganizador(String cedula) {
        Organizador organizador = organizadorRepository.findById(cedula)
                .orElseThrow(()-> new RuntimeException("Organizador no encontrado"));

        organizador.setActivo(true);

        organizadorRepository.save(organizador);
    }

    public void inactivarOrganizador(String cedula) {
        Organizador organizador = organizadorRepository.findById(cedula)
                .orElseThrow(()-> new RuntimeException("Organizador no encontrado"));

        organizador.setActivo(false);

        organizadorRepository.save(organizador);
    }

    public void actualizarOrganizador(OrganizadorUpdateDTO dto) {
        Organizador organizador = organizadorRepository.findById(dto.getCedula())
                .orElseThrow(()-> new RuntimeException("Organizador no encontrado"));

        organizador.setTelefonoOrg(dto.getTelefono());
        organizador.setNombreOrg(dto.getNombre());
        organizador.setApellidoOrg(dto.getApellidos());
        organizador.setEmailOrg(dto.getEmail());
        organizador.setCodigoInvitado(dto.getCodigoInvitado());
        organizador.setPassHashOrg(dto.getPassHashOrg());
        organizador.setActivo(dto.getActivo());

        organizadorRepository.save(organizador);
    }

    public OrganizadorResponseDTO crearOrganizador(OrganizadorCreateDTO dto) {

        if (organizadorRepository.existsById(dto.getCedula())) {
            throw new RuntimeException("Ya existe un organizador con esta cédula.");
        }
        Rol rol= rolRepository.findByIdRol(dto.getIdRol())
                .orElseThrow(()-> new RuntimeException("Rol no existe"));

        Organizador organizador = new Organizador();

        organizador.setCedulaOrg(dto.getCedula());
        organizador.setNombreOrg(dto.getNombre());
        organizador.setApellidoOrg(dto.getApellidos());
        organizador.setTelefonoOrg(dto.getTelefono());
        organizador.setEmailOrg(dto.getEmail());
        organizador.setRol(rol);
        organizador.setActivo(true);

        //generar codigo invitado
        String codInvitado;
        do {
            codInvitado = generarCodigoInvitado(7);
        } while (organizadorRepository.existsByCodigoInvitado(codInvitado));
        organizador.setCodigoInvitado(codInvitado);

        // ENCRIPTAR antes de guardar
        String passwordTemporal = generarCodigoInvitado(8);
        String passwordHacheada = passwordEncoder.encode(passwordTemporal);
        organizador.setPassHashOrg(passwordHacheada);

        Organizador organizadorguardado =organizadorRepository.save(organizador);

        // Enviar el correo electrónico con las credenciales
        try {
            messageService.enviarCredencialesOrg(
                    organizadorguardado.getEmailOrg(),
                    organizadorguardado.getNombreOrg(),
                    passwordTemporal // Enviamos la clave SIN encriptar
            );
        } catch (Exception e) {
            // Loguear el error pero permitir que el proceso continúe
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
        return convertirADTO(organizadorguardado);
    }

    public List<OrganizadorResponseDTO> obtenerTodos() {
        return organizadorRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public List<OrganizadorResponseDTO> obtenerActivos() {
        return organizadorRepository.findByActivoTrue()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public List<OrganizadorResponseDTO> obtenerInactivos() {
        return organizadorRepository.findByActivoFalse()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }
    private String generarCodigoInvitado(int longitud) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        java.security.SecureRandom random = new java.security.SecureRandom();
        StringBuilder sb = new StringBuilder(longitud);

        for (int i = 0; i < longitud; i++) {
            int index = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(index));
        }
        return sb.toString();
    }
    public OrganizadorResponseDTO convertirADTO(Organizador org) {
        return new OrganizadorResponseDTO(
                org.getCedulaOrg(),
                org.getTelefonoOrg(),
                org.getNombreOrg(),
                org.getApellidoOrg(),
                org.getEmailOrg(),
                org.getCodigoInvitado(),
                org.getRol().getTipoRol(),
                org.getActivo()
        );
    }
}
