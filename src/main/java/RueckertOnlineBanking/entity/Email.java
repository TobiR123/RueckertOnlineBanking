package RueckertOnlineBanking.entity;

import RueckertOnlineBanking.entity.util.GeneratedIdEntity;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;


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

    public Email(EMailAddress from, EMailAddress to, String topic, String message){
        super.id = getId();
        this.from = from;
        this.to = to;
        this.topic = topic;
        this.message = message;
    }

    @Override
    public String toString(){
        return "from: " + this.from +
                " to: " + this.to +
                " topic: " + this.topic +
                " message: " + this.message;
    }

    @Override
    public boolean equals(Object o){
        if( o == null) {
            return false;
        }
        else if(getClass() != o.getClass()) {
            return false;
        }
        final Email other = (Email) o;
        if(!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if(this.id == null) {
            return 0;
        } else {
            return this.id.hashCode();
        }
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
