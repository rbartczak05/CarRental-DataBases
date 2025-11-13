package pl.lodz.p.carrental.mongoDB.model.vehicle;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "_clazz", value = "Car")
public class Car extends MotorVehicle {

    @BsonProperty("segement")
    private String segement;

    public Car(String plateNumber, double pricePerDay, double engineDisplacement, String segement) {
        super(plateNumber, pricePerDay, engineDisplacement);
        this.segement = segement;
    }

    public Car() {

    }

    public String getSegement() {
        return segement;
    }

    public void setSegement(String segement) {
        this.segement = segement;
    }

    @Override
    public String toString() {
        return "Car{" + super.toString() +
                ", segement=" + getSegement() +
                "}";
    }
}
