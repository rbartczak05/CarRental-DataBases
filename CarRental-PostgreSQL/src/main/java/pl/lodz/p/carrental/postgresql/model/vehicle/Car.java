package pl.lodz.p.carrental.postgresql.model.vehicle;

import jakarta.persistence.*;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("car")
public class Car extends MotorVehicle {

    @Column(name = "segement")
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
