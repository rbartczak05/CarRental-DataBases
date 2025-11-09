package pl.lodz.p.carrental.cassandra.model;

import pl.lodz.p.carrental.cassandra.model.client.Client;
import pl.lodz.p.carrental.cassandra.model.vehicle.Vehicle;

import java.time.LocalDate;
import java.util.UUID;

public class Rent {

    private final UUID rentId;
    private Client client;
    private Vehicle vehicle;
    private int days;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private boolean active;

    public Rent(Client client, Vehicle vehicle, int days) {
        this.rentId = UUID.randomUUID();
        this.client = client;
        this.vehicle = vehicle;
        this.days = days;
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now().plusDays(days);
        this.price = (vehicle.getPricePerDay() * days) * (1.00 - client.getClientType().getDiscount());
        this.active = true;
    }

    public UUID getRentId() {
        return rentId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "rentId=" + rentId +
                ", client=" + client +
                ", vehicle=" + vehicle +
                ", days=" + days +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", active=" + active +
                '}';
    }
}
