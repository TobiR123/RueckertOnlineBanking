package RueckertOnlineBanking.entity;

import RueckertOnlineBanking.entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

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
    public String toString(){
        return "street: " + this.street +
                " house number: " + this.houseNumber +
                " postal code: " + this.postcode +
                " place " + this.place;
    }

    @Override
    public boolean equals(Object o){
        if( o == null) {
            return false;
        }
        else if(getClass() != o.getClass()) {
            return false;
        }
        final Address other = (Address) o;
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
