import javax.inject.Inject;



/*
package RueckertOnlineBanking.service;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.jms.*;
@RequestScoped
public class MoneyTransportService {
    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = "java:/jms/queue/RMT_ROB_TransportStatusQueue")
    private Queue queue;
    public void sendNewMessage(String s) {
        try (JMSContext context = connectionFactory.createContext() ) {
            JMSProducer producer = context.createProducer();
            ObjectMessage objectMessage = context.createObjectMessage();
            objectMessage.setObject(s);
            context.createProducer().send(queue, objectMessage);
        } catch (JMSException e) {
            System.out.println("ERROR while sending to queue!");

        }
    }
}
*/