package RueckertOnlineBanking.entity;

import RueckertOnlineBanking.entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class EMailAddress extends GeneratedIdEntity {

    private String mailAddress;

    public EMailAddress(){
        this.id = getId();
    }

    public EMailAddress(String mailAddress){
        super.id = getId();
        this.mailAddress = mailAddress;
    }

    @Override
    public boolean equals(Object o){
        if( o == null) {
            return false;
        }
        else if(getClass() != o.getClass()) {
            return false;
        }
        final EMailAddress other = (EMailAddress) o;
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
