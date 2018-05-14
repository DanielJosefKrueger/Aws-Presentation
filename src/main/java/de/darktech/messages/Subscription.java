package de.darktech.messages;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;

import java.util.ArrayList;
import java.util.List;

public class Subscription extends AWSIotTopic {

    List<SubscriptionCallback> callbackList = new ArrayList<>();

    public Subscription(String topic) {
        super(topic);
    }

    public Subscription(String topic, AWSIotQos qos) {
        super(topic, qos);
    }


    @Override
    public void onMessage(AWSIotMessage message) {
        for(SubscriptionCallback callback: callbackList){
            callback.onSubscribe(message);
        }
    }

    public void addCallback(SubscriptionCallback callback){
        callbackList.add(callback);
    }
}
