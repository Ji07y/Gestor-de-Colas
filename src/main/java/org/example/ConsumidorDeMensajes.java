package org.example;
import com.rabbitmq.client.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class ConsumidorDeMensajes {

    private final static String NOMBRE_COLA = "colaPrueba";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(NOMBRE_COLA, false, false, false, null);
            System.out.println(" [*] Esperando mensajes. Para salir, presione CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String mensaje = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Recibido '" + mensaje + "'");
                enviarCorreo(mensaje);
            };
            channel.basicConsume(NOMBRE_COLA, true, deliverCallback, consumerTag -> {});
        }
    }

    // Método para enviar un correo electrónico con el mensaje recibido
    private static void enviarCorreo(String mensajeTexto) {
        String para = "jonathanrojaldo@gmail.com";
        String de = "jonathan.rojaldo@hotmail.com";
        String host = "smtp.office365.com";
        int puerto = 587;

        // Configuración de las propiedades del servidor de correo
        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.host", host);
        propiedades.put("mail.smtp.port", puerto);

        // Obtener la contraseña del entorno
        String contrasena = System.getenv("EMAIL_PASSWORD");

        // Verificar si la contraseña fue obtenida correctamente
        if (contrasena == null) {
            System.err.println("Error: La contraseña no se especificó. Por favor, configure la variable de entorno EMAIL_PASSWORD.");
            return;
        }

        // Crear una sesión con autenticación
        Session sesion = Session.getInstance(propiedades, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(de, contrasena);
            }
        });

        try {
            // Crear un mensaje MIME
            MimeMessage mensaje = new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress(de));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
            mensaje.setSubject("Mensaje de RabbitMQ");
            mensaje.setText(mensajeTexto);

            // Enviar mensaje
            Transport.send(mensaje);
            System.out.println("Mensaje enviado exitosamente...");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
