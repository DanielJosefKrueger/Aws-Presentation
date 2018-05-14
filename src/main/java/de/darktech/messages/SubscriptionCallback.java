package de.darktech.messages;

import com.amazonaws.services.iot.client.AWSIotMessage;

public interface SubscriptionCallback {
    void onSubscribe(AWSIotMessage message);
}
