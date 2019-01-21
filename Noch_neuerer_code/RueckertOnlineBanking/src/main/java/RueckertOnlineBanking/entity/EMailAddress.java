package RueckertOnlineBanking.entity;

import RueckertOnlineBanking.entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class EMailAddress extends GeneratedIdEntity {

    private String mailAddress;

    public EMailAddress() {
        this.id = getId();
    }

    public EMailAddress(String mailAddress) {
        super.id = getId();
        this.mailAddress = mailAddress;
    }

    @Override
    public String toString() {
        return "mailAddress: " +
                this.mailAddress;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public Long getId() {
        return super.getId();
    }

}
