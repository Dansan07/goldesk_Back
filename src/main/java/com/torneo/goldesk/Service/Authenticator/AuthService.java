package com.torneo.goldesk.Service.Authenticator;

import com.torneo.goldesk.Entity.AccesosExternos;
import com.torneo.goldesk.Entity.Equipo;
import com.torneo.goldesk.Entity.Organizador;
import com.torneo.goldesk.Repository.AccesosExternosRepository;
import com.torneo.goldesk.Repository.EquipoRepository;
import com.torneo.goldesk.Repository.OrganizadorRepository;
import com.torneo.goldesk.Repository.RolRepository;
import com.torneo.goldesk.Service.MessageService;
import com.torneo.goldesk.Service.OrganizadorService;
import com.torneo.goldesk.dto.login.LoginRequestDTO;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final AccesosExternosRepository accesosExternosRepository;
    private final JwtService jwtService;
    private final EquipoRepository equipoRepository;
    private final OrganizadorRepository organizadorRepository;
    private final OrganizadorService organizadorService;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;
    private final MessageService messageService;

    public AuthService(AccesosExternosRepository accesosExternosRepository, JwtService jwtService, EquipoRepository equipoRepository, OrganizadorRepository organizadorRepository, OrganizadorService organizadorService, PasswordEncoder passwordEncoder, RolRepository rolRepository, MessageService messageService) {
        this.accesosExternosRepository = accesosExternosRepository;
        this.jwtService = jwtService;
        this.equipoRepository = equipoRepository;
        this.organizadorRepository = organizadorRepository;
        this.organizadorService = organizadorService;
        this.passwordEncoder = passwordEncoder;
        this.rolRepository = rolRepository;
        this.messageService = messageService;
    }

    public Map<String, Object> loginPorCodigo(String codigo) {
        // 1. Intentar buscar si ya existe en accesos_externos
        Optional<AccesosExternos> accesoExistente = accesosExternosRepository.findByCodigoAccesoAndActivoTrue(codigo);

        if (accesoExistente.isPresent()) {
            return generarRespuestaLogin(accesoExistente.get());
        }

        // 2. Si NO existe en accesos, buscar si el código pertenece a un EQUIPO
        Optional<Equipo> equipo = equipoRepository.findByCodigoEquipo(codigo);
        if (equipo.isPresent()) {
            AccesosExternos nuevoAcceso = crearNuevoAcceso(codigo, equipo.get(), null, "DELEGADO");
            return generarRespuestaLogin(nuevoAcceso);
        }

        // 3. Si NO es equipo, buscar si es un código de INVITADO de un ORGANIZADOR
        Optional<Organizador> org = organizadorRepository.findByCodigoInvitado(codigo);
        if (org.isPresent()) {
            AccesosExternos nuevoAcceso = crearNuevoAcceso(codigo, null, org.get(), "INVITADO");
            return generarRespuestaLogin(nuevoAcceso);
        }

        throw new RuntimeException("El código no existe en ninguna base de datos.");
    }
    private Map<String, Object> generarRespuestaLogin(AccesosExternos acceso) {
        // 1. Determinar el ID de referencia (ID del equipo o Cédula del organizador)
        String codigo;
        String nombreMostrar;

        if (acceso.getEquipo() != null) {
            codigo = String.valueOf(acceso.getCodigoAcceso());
            nombreMostrar = acceso.getEquipo().getNombreEquipo();
        } else {
            codigo = acceso.getOrganizador().getCedulaOrg();
            nombreMostrar = "Invitado de " + acceso.getOrganizador().getNombreOrg();
        }

        // 2. Generar el Token usando tu JwtService
        // Pasamos la referencia y el nombre del Rol (DELEGADO o INVITADO)
        String token = jwtService.generarToken(codigo, acceso.getRol().getTipoRol());

        // 3. Crear el mapa de respuesta que recibirá Android
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("token", token);
        respuesta.put("rol", acceso.getRol().getTipoRol());
        respuesta.put("nombre", nombreMostrar);
        respuesta.put("codigo", codigo);

        return respuesta;
    }

    private AccesosExternos crearNuevoAcceso(String codigo, Equipo equipo, Organizador org, String nombreRol) {
        AccesosExternos nuevo = new AccesosExternos();
        nuevo.setCodigoAcceso(codigo);
        nuevo.setEquipo(equipo);
        nuevo.setOrganizador(org);
        nuevo.setRol(rolRepository.findByTipoRol(nombreRol)
                .orElseThrow(()-> new RuntimeException("Error: Rol " + nombreRol + " no encontrado"))); // Buscas el rol por nombre
        nuevo.setActivo(true);
        return accesosExternosRepository.save(nuevo);
    }

    //Valida si el inicio de sesión del organizador
    public Map<String, Object> loginOrganizador(LoginRequestDTO loginDTO) {
        // 1. Buscamos al organizador por email (o cédula)
        Organizador org = organizadorRepository.findByEmailOrg(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

        // 2. Validamos la contraseña (aquí comparamos el hash)
        // !org.getPassHashOrg().equals(logintDTO.getPassword())
        if (!passwordEncoder.matches(loginDTO.getPassword(), org.getPassHashOrg())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        // 3. Validamos que esté activo
        if (!org.getActivo()) {
            throw new RuntimeException("El usuario se encuentra inhabilitado");
        }

        //4. generar token
        String token = jwtService.generarToken(org.getCedulaOrg(),org.getRol().getTipoRol());

        // 5. Retornar tanto el perfil como el token
        return Map.of(
                "token", token,
                "perfil", organizadorService.convertirADTO(org)
        );
    }

    @Transactional
    public void recuperarPasswordOrg(String email) {
        Organizador organizador= organizadorRepository.findByEmailOrg(email)
                .orElseThrow(()->new RuntimeException("El correo electrónico no existe"));

        //genera, encripta y guarda passwordTemporal
        String passwordTemporal=organizadorService
                .generarCodigoInvitado(7).toUpperCase();

        String passwordHacheada = passwordEncoder.encode(passwordTemporal);
        organizador.setPassHashOrg(passwordHacheada);
        organizadorRepository.save(organizador);

        messageService.recuperarPasswordOrg(
                organizadorService.convertirADTO(organizador),
                passwordTemporal);
    }
}
