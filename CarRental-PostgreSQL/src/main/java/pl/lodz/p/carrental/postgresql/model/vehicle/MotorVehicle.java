package pl.lodz.p.carrental.postgresql.model.vehicle;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class MotorVehicle extends Vehicle {

    @Column(name = "engineDisplacement")
    private double engineDisplacement;
    @Column(name = "plateNumber")
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
