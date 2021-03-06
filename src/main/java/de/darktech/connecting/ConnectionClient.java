package de.darktech.connecting;

import com.amazonaws.services.iot.client.*;
import com.amazonaws.services.iot.client.sample.sampleUtil.SampleUtil;
import de.darktech.configuration.Configuration;
import de.darktech.configuration.ConfigurationLoader;


public class ConnectionClient {


    private static ConnectionClient instance;
    private final Configuration configuration;
    private final AWSIotMqttClient client;

    private ConnectionClient(){
        configuration = new ConfigurationLoader().loadConfig();

        if(configuration.useCertificate()){
            String clientEndpoint = configuration.praefix() + ".iot." + configuration.awsRegion() + ".amazonaws.com";       // replace <prefix> and <region> with your own
            String clientId = configuration.clientId();                           // replace with your own client ID. Use unique client IDs for concurrent connections.
            String certificateFile = configuration.certificate();
            String privateKeyFile = configuration.privateKey();
            SampleUtil.KeyStorePasswordPair pair = SampleUtil.getKeyStorePasswordPair(certificateFile, privateKeyFile);
            client = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);
        }else{
            String clientEndpoint = configuration.praefix() + ".iot." + configuration.awsRegion() + ".amazonaws.com";       // replace <prefix> and <region> with your own
            String clientId = configuration.clientId();                           // replace with your own client ID. Use unique client IDs for concurrent connections.
            // String certificateFile = "<certificate file>";                       // X.509 based certificate file
            // String privateKeyFile = "<private key file>";                        // PKCS#1 or PKCS#8 PEM encoded private key file
            String awsKeyId = configuration.awsKeyId();
            String awsSecretkey = configuration.awsSecretKey();
            client = new AWSIotMqttClient(clientEndpoint, clientId, awsKeyId, awsSecretkey);
        }

    }

    public static ConnectionClient getInstance(){
        if(instance==null){
            instance = new ConnectionClient();
        }
        return instance;
    }

    public void push(String topic, String message) throws AWSIotException {

        if(client.getConnectionStatus()== AWSIotConnectionStatus.DISCONNECTED){
            client.connect();
        }
        client.publish(new AWSIotMessage(topic, AWSIotQos.valueOf(configuration.qos()), message));
    }


    public void subscribe(AWSIotTopic awsIotTopic) throws AWSIotException {
        if(client.getConnectionStatus()== AWSIotConnectionStatus.DISCONNECTED){
            client.connect();
        }
        client.subscribe(awsIotTopic);
    }



}
