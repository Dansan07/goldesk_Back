package com.torneo.goldesk.Service;

import com.torneo.goldesk.dto.actores.organizador.OrganizadorResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MessageService {


    private final JavaMailSender mailSender;

    public MessageService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarCredencialesOrg(String destinatario, String nombre, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destinatario);
        message.setSubject("Bienvenido a Goldesk - Tus Credenciales");
        message.setText("Hola " + nombre + ",\n\n" +
                "Tu cuenta ha sido creada exitosamente.\n" +
                "Usuario: " + destinatario + "\n" +
                "Contraseña temporal: " + password + "\n\n" +
                "Por seguridad, te recomendamos cambiar tu contraseña al ingresar.");
        mailSender.send(message);
    }

    public void actualizarPasswordOrg(OrganizadorResponseDTO dto, String password){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getEmail());
        message.setSubject("Goldesk - Recuperación de Contraseña");
        message.setText("Hola " + dto.getNombre() + ",\n\n" +
                "Hemos recibido una solicitud para recuperar el acceso a tu cuenta.\n\n" +
                "Usuario: " + dto.getEmail() + "\n" +
                "Contraseña temporal: " + password + "\n\n" +
                "Por seguridad, te recomendamos cambiar esta contraseña inmediatamente después de iniciar sesión.\n" +
                "Si no realizaste esta solicitud, por favor ignora este mensaje o contacta con soporte.");
        mailSender.send(message);
    }

    public void enviarSmsCredenciales(String telefono, String password) {
        // Aquí conectarás con Twilio o el proveedor que elijas
        // Por ahora, lo dejamos como un log para pruebas
        System.out.println("Enviando SMS al número: " + telefono + " con la clave: " + password);

        // Ejemplo Twilio:
        // Message.creator(new PhoneNumber(telefono), new PhoneNumber(from), "Tu clave: " + password).create();
    }
}
