package pl.lodz.p.carrental.mongoDB.model.vehicle;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@BsonDiscriminator(key = "_clazz")
public abstract class Vehicle {

    @BsonProperty("_id")
    private UUID id;

    @BsonProperty("pricePerDay")
    private double pricePerDay;

    @BsonProperty("rented")
    private boolean rented;

    public Vehicle(double pricePerDay) {
        this.id = UUID.randomUUID();
        this.pricePerDay = pricePerDay;
        this.rented = false;
    }

    public Vehicle() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
