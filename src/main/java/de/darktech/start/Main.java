package de.darktech.start;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.sun.org.apache.xpath.internal.SourceTree;
import de.darktech.connecting.ConnectionClient;
import de.darktech.messages.Subscription;
import de.darktech.messages.SubscriptionCallback;
import de.darktech.visual.Gui;

import java.util.Scanner;

public class Main  {


    public static void main(String[] args) throws Exception {
        ConnectionClient connectionClient = ConnectionClient.getInstance();
        Gui gui = new Gui();

        /*Subscription subscription = new Subscription("test/test1");
        connectionClient.subscribe(subscription);
        subscription.addCallback(new SubscriptionCallback() {
            @Override
            public void onSubscribe(AWSIotMessage message) {
                System.out.println("Message: "+ message.getStringPayload() + "Topic:" + message.getTopic());
            }
        });

        boolean run = true;
        Scanner scanner = new Scanner(System.in);
        while(run){
            System.out.println("Publish eingeben: Erste Zeile Topic, zweite Zeile: message");
            String topic = scanner.next();
            String payload = scanner.next();
            System.out.println("Sending message: " + payload + " on topic " + topic);
            connectionClient.push(topic, payload);
        }
        scanner.close();*/

    }

}
