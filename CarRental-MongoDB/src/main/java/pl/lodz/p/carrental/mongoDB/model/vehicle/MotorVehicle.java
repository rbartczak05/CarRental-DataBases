package pl.lodz.p.carrental.mongoDB.model.vehicle;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "_clazz")
public abstract class MotorVehicle extends Vehicle {

    @BsonProperty("engineDisplacement")
    private double engineDisplacement;

    @BsonProperty("plateNumber")
    private String plateNumber;

    public MotorVehicle(String plateNumber, double pricePerDay, double engineDisplacement) {
        super(pricePerDay);
        this.plateNumber = plateNumber;
        this.engineDisplacement = engineDisplacement;
    }

    public MotorVehicle() {

    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public double getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(double engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", engineDisplacement=" + getEngineDisplacement() +
                ", plateNumber=" + getPlateNumber();
    }
}
