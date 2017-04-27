package net.tableschedule.jsf.bean;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by aleksandrprendota on 26.04.17.
 */
public class MQListener {

    public static volatile boolean UPDATE_FLAG = false;
    private Channel channel;
    private final static String QUEUE_NAME = "mylittlequeue";

    public void startListener() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                if (message.contains("update")){
                    UPDATE_FLAG = true;
                }
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

    public void close() throws IOException, TimeoutException {
        channel.close();
    }
}
