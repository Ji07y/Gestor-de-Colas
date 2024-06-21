# Gestor de Colas con RabbitMQ en Java

Este proyecto muestra un ejemplo básico de cómo utilizar RabbitMQ para la gestión de colas en una aplicación Java. Utiliza dos clases principales: `ProductorDeMensaje` y `ConsumidorDeMensajes`, que ilustran cómo enviar y recibir mensajes desde una cola de RabbitMQ, así como enviar notificaciones por correo electrónico al recibir un mensaje.

## Requisitos

- Java Development Kit (JDK) 8 o superior
- RabbitMQ instalado y ejecutándose localmente (o acceso a un servidor RabbitMQ remoto)
- Credenciales de correo electrónico para enviar notificaciones (variable de entorno `EMAIL_PASSWORD` configurada)

## Configuración

### RabbitMQ

Asegúrate de tener RabbitMQ instalado y configurado. Puedes descargarlo desde [rabbitmq.com](https://www.rabbitmq.com/download.html) y seguir las instrucciones de instalación para tu sistema operativo.

### Variables de Entorno

Configura la variable de entorno `EMAIL_PASSWORD` con la contraseña del correo electrónico desde el cual deseas enviar las notificaciones.

## Ejecución

### ProductorDeMensaje

La clase `ProductorDeMensaje` se encarga de enviar un mensaje a una cola específica en RabbitMQ.

1. Abre el proyecto en tu entorno de desarrollo.
2. Asegúrate de que RabbitMQ esté en funcionamiento.
3. Ejecuta la clase `ProductorDeMensaje`. Esto enviará un mensaje de prueba a la cola especificada.

### ConsumidorDeMensajes

La clase `ConsumidorDeMensajes` consume mensajes de la misma cola de RabbitMQ y envía una notificación por correo electrónico.

1. Asegúrate de que RabbitMQ siga en funcionamiento.
2. Ejecuta la clase `ConsumidorDeMensajes`. Esta clase esperará mensajes en la cola y los imprimirá por consola.
3. Cada vez que se recibe un mensaje, se enviará una notificación por correo electrónico al destinatario especificado.

## Detalles Técnicos

- **ConnectionFactory**: Establece la conexión a RabbitMQ utilizando `localhost` como host.
- **queueDeclare**: Define la cola `colaPrueba` en RabbitMQ.
- **basicPublish**: Envía un mensaje a la cola `colaPrueba`.
- **basicConsume**: Consuma mensajes de la cola `colaPrueba` y llama al método `enviarCorreo` para enviar una notificación por correo electrónico.

## Bibliotecas Utilizadas

- `com.rabbitmq.client`: Cliente RabbitMQ para Java.
- `javax.mail`: API JavaMail para el envío de correos electrónicos.


