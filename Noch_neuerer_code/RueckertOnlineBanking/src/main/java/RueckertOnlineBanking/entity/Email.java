package RueckertOnlineBanking.entity;

import RueckertOnlineBanking.entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
public class Email extends GeneratedIdEntity {

    @ManyToOne
    private EMailAddress from;
    @ManyToOne
    private EMailAddress to;
    private String topic;
    private String message;

    public Email() {
        super.id = getId();
    }

    public Email(EMailAddress from, EMailAddress to, String topic, String message) {
        super.id = getId();
        this.from = from;
        this.to = to;
        this.topic = topic;
        this.message = message;
    }

    @Override
    public String toString() {
        return "from: " + this.from +
                " to: " + this.to +
                " topic: " + this.topic +
                " message: " + this.message;
    }

    //// GETTER AND SETTER /////
    public Long getId() {
        return super.getId();
    }

    public EMailAddress getFrom() {
        return from;
    }

    public void setFrom(EMailAddress from) {
        this.from = from;
    }

    public EMailAddress getTo() {
        return to;
    }

    public void setTo(EMailAddress to) {
        this.to = to;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
