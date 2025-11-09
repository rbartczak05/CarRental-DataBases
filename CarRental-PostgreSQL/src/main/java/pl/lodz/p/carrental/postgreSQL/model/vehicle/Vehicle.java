package pl.lodz.p.carrental.postgreSQL.model.vehicle;

import jakarta.persistence.*;
import pl.lodz.p.carrental.postgreSQL.model.AbstractEntity;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "vehicle_type")
@Table(name = "vehicle")
@Access(AccessType.FIELD)
public abstract class Vehicle extends AbstractEntity {

    @Column(name = "price_per_day")
    private double pricePerDay;
    @Column(name = "rented")
    private boolean rented;

    public Vehicle(double pricePerDay) {
        this.pricePerDay = pricePerDay;
        this.rented = false;
    }

    public Vehicle() {

    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    @Override
    public String toString() {
        return "id=" + getId() +
                ", rented=" + isRented() +
                ", pricePerDay=" + getPricePerDay();
    }
}
