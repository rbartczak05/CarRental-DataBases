package pl.lodz.p.carrental.postgreSQL.model.vehicle;

import jakarta.persistence.*;
import pl.lodz.p.carrental.postgreSQL.model.AbstractEntity;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Table(name = "vehicle")
@Access(AccessType.FIELD)
public abstract class Vehicle extends AbstractEntity {

    @Id
    @Column(name = "vehicleId")
    private UUID vehicleId;
    @Column(name = "plateNumber")
    private String plateNumber;
    @Column(name = "pricePerDay")
    private double pricePerDay;
    @Column(name = "rented")
    private boolean rented;

    public Vehicle(String plateNumber, double pricePerDay) {
        this.vehicleId = UUID.randomUUID();
        this.plateNumber = plateNumber;
        this.pricePerDay = pricePerDay;
        this.rented = false;
    }

    public Vehicle() {

    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(UUID vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
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
        return "Vehicle{" +
                "vehicleId=" + vehicleId +
                ", plateNumber='" + plateNumber + '\'' +
                ", pricePerDay=" + pricePerDay +
                ", rented=" + rented +
                '}';
    }
}
