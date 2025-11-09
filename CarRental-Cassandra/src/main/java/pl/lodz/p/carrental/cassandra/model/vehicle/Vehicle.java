package pl.lodz.p.carrental.cassandra.model.vehicle;

import java.util.UUID;

public abstract class Vehicle {

    private final UUID vehicleId;
    private String plateNumber;
    private double pricePerDay;
    private boolean rented;

    public Vehicle(String plateNumber, double pricePerDay) {
        this.vehicleId = UUID.randomUUID();
        this.plateNumber = plateNumber;
        this.pricePerDay = pricePerDay;
        this.rented = false;
    }

    public UUID getVehicleId() {
        return vehicleId;
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
