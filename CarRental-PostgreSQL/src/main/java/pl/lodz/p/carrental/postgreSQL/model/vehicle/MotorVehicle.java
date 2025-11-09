package pl.lodz.p.carrental.postgreSQL.model.vehicle;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class MotorVehicle extends Vehicle {

    @Column(name = "engineDisplacement")
    private double engineDisplacement;

    public MotorVehicle(String plateNumber, double pricePerDay, double engineDisplacement) {
        super(plateNumber, pricePerDay);
        this.engineDisplacement = engineDisplacement;
    }

    public MotorVehicle() {

    }

    public double getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(double engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    @Override
    public String toString() {
        return "MotorVehicle{" +
                "engineDisplacement=" + engineDisplacement +
                '}';
    }
}
