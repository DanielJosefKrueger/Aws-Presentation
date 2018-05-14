package de.darktech.configuration;

import org.aeonbits.owner.Config;


@Config.HotReload
@Config.Sources("file:${config}")
public interface Configuration extends Config {


    @DefaultValue("")
    @Key("region")
    String awsRegion();


    @DefaultValue("")
    @Key("cert")
    String certificate();


    @DefaultValue("presenter123")
    @Key("clientid")
    String clientId();


    @DefaultValue("1awdc1234")
    @Key("praefix")
    String praefix();


    @DefaultValue("1awdc1234")
    @Key("aws_keyid")
    String awsKeyId();


    @DefaultValue("1awdc1234")
    @Key("aws_secretkey")
    String awsSecretKey();



    @DefaultValue("")
    @Key("private_key_file")
    String privateKey();


    @DefaultValue("1")
    @Key("qos")
    int qos();


    @DefaultValue("true")
    @Key("usecert")
    boolean useCertificate();


    @DefaultValue("myProject/pushdefault")
    @Key("push_topic")
    String pushTopic();

    @DefaultValue("myProject/initdefault")
    @Key("init_topic")
    String initTopic();

    @DefaultValue("myProject/backdefault")
    @Key("backchannel_topic")
    String backTopic();

}
