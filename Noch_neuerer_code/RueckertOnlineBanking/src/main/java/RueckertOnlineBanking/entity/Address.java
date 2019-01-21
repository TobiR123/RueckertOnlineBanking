package RueckertOnlineBanking.entity;

import RueckertOnlineBanking.entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Address extends GeneratedIdEntity {

    private String street;
    private String houseNumber;
    private int postcode;
    private String place;

    public Address() {
        super.id = getId();
    }

    public Address(String street, String houseNumber, int postcode, String place) {
        super.id = getId();
        this.street = street;
        this.houseNumber = houseNumber;
        this.postcode = postcode;
        this.place = place;
    }

    @Override
    public String toString() {
        return "street: " + this.street +
                " house number: " + this.houseNumber +
                " postal code: " + this.postcode +
                " place " + this.place;
    }

    ///// GETTER AND SETTER /////
    public Long getId() {
        return super.getId();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
