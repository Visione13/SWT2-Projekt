package swt2.frododatacruncher.Mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import swt2.frododatacruncher.Businesslogik.Dispatcher;

public abstract class Receiver {
    protected Dispatcher dispatcher;
    protected String broker = "SERVERADRESSE";
    protected MqttClient client;
    protected MqttConnectOptions connOpts;

    public Receiver(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public abstract void receive();
    protected  abstract void convertDTO(String dto);
}