package com.torneo.goldesk.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private JavaMailSender mailSender;

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

    public void enviarSmsCredenciales(String telefono, String password) {
        // Aquí conectarás con Twilio o el proveedor que elijas
        // Por ahora, lo dejamos como un log para pruebas
        System.out.println("Enviando SMS al número: " + telefono + " con la clave: " + password);

        // Ejemplo Twilio:
        // Message.creator(new PhoneNumber(telefono), new PhoneNumber(from), "Tu clave: " + password).create();
    }
}
