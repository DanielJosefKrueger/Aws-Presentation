package de.darktech.visual;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMessage;
import de.darktech.connecting.ConnectionClient;
import de.darktech.messages.Subscription;
import de.darktech.messages.SubscriptionCallback;
import org.apache.logging.log4j.core.util.datetime.FastDateFormat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class Gui extends JFrame {

    private final static FastDateFormat formater = FastDateFormat.getInstance("dd.MM.yyyy HH:mm:ss z", Locale.GERMANY);
    private static final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 20);



    private final ConnectionClient connectionClient;
    JTextPane subscriptionPane;
    JScrollPane scrollPaneSubscription;

    JTextField topicInputField;
    JTextArea payloadInputField;
    JButton sendButton;

    public Gui(){
        this.connectionClient = ConnectionClient.getInstance();

        setContentPane(new BackGroundPane("background.jpg"));

        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        this.setLayout(null);
        this.setSize(dim);
       // this.getContentPane().setBackground(Color.white);
        final JPanel body = new JPanel();
        body.setBounds(20, 20, (int) dim.getWidth() - 40, (int) dim.getHeight() - 40);
        body.setLayout(null);
        body.setBackground(Color.white);
        body.setOpaque(false);
        this.add(body);

        final LayoutCalculator calculator = new LayoutCalculator(body.getWidth(), body.getHeight(), 2, 60);


        subscriptionPane = new JTextPane();
        subscriptionPane.setOpaque(false);
        subscriptionPane.setFont(FONT);
        //subscriptionPane.setContentType("text/html");
        //subscriptionPane.setBounds(calculator.getRectangleForPosition(0, 0, 1, 60));
        //body.add(subscriptionPane);

        scrollPaneSubscription = new JScrollPane(subscriptionPane);
        scrollPaneSubscription.createHorizontalScrollBar();
        scrollPaneSubscription.setBounds(calculator.getRectangleForPosition(0, 0, 1, 60));
        scrollPaneSubscription.setOpaque(false);
        scrollPaneSubscription.getViewport().setOpaque(false);
        body.add(scrollPaneSubscription);
        Subscription subscription = new Subscription("test/test1");
        try {
            connectionClient.subscribe(subscription);
        } catch (AWSIotException e) {
            e.printStackTrace();
        }
        subscription.addCallback(new SubscriptionCallback() {
            @Override
            public void onSubscribe(AWSIotMessage message) {
                addSubscriptionText(message);
            }
        });




        payloadInputField = new JTextArea("Insert Payload");
        payloadInputField.setBounds(calculator.getRectangleForPosition(1, 5, 1, 40));
        payloadInputField.setOpaque(false);
        payloadInputField.setFont(FONT);
        body.add(payloadInputField);


        topicInputField = new JTextField("Insert topic");
        topicInputField.setBounds(calculator.getRectangleForPosition(1, 0, 1, 5));
        topicInputField.setOpaque(false);
        topicInputField.setFont(FONT);

        body.add(topicInputField);

        sendButton = new JButton("Send Publish");
        sendButton.setBounds(calculator.getRectangleForPosition(1, 45, 1, 5));
        sendButton.setFont(FONT);
        sendButton.setOpaque(false);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    connectionClient.push(topicInputField.getText(), payloadInputField.getText());
                } catch (AWSIotException e1) {
                    e1.printStackTrace();
                }
            }
        });
        body.add(sendButton);
        this.setUndecorated(true);

        this.setAlwaysOnTop(false);
        this.setVisible(true);
    }

    private void addSubscriptionText(AWSIotMessage message) {
        System.out.println("Message arrived");

        String paneText = subscriptionPane.getText();
        String msgText = formater.format(System.currentTimeMillis()) + "\n" + "Topic: "+ message.getTopic() +"\nPayload: " + message.getStringPayload()+ "\n--------------------";
        String newText = msgText + "\n" + paneText;
        subscriptionPane.setText(newText);
        subscriptionPane.repaint();
        refresh();
    }

    private void refresh() {
        repaint();
    }



    class BackGroundPane extends JPanel {
        Image img = null;

        BackGroundPane(String imagefile) {
            if (imagefile != null) {
                MediaTracker mt = new MediaTracker(this);
                img = Toolkit.getDefaultToolkit().getImage(imagefile);
                mt.addImage(img, 0);
                try {
                    mt.waitForAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
        }
    }


    public static void main(String[] args) {
        new Gui();
    }
}
