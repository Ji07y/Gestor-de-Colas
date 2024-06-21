package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProductorDeMensaje {
    // Nombre de la cola de RabbitMQ
    private final static String NOMBRE_COLA = "colaPrueba";

    public static void main(String[] argv) throws Exception {
        // Crear una fábrica de conexiones y establecer el host de RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        // Establecer una nueva conexión y un nuevo canal
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Declarar una cola en RabbitMQ
            channel.queueDeclare(NOMBRE_COLA, false, false, false, null);
            String mensaje = "¡Hola, RabbitMQ!";

            // Enviar el mensaje a la cola
            channel.basicPublish("", NOMBRE_COLA, null, mensaje.getBytes());
            System.out.println(" [x] Enviado '" + mensaje + "'");
        }
    }

}
