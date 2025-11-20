package pl.lodz.p.carrental.mongodb.model;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.LocalDate;
import java.util.UUID;


public class Rent {

    @BsonProperty("_id")
    private UUID id;

    @BsonProperty("clientId")
    private UUID clientId;

    @BsonProperty("vehicleId")
    private UUID vehicleId;

    @BsonProperty("days")
    private int days;

    @BsonProperty("startDate")
    private LocalDate startDate;

    @BsonProperty("returnDate")
    private LocalDate returnDate;

    @BsonProperty("endDate")
    private LocalDate endDate;

    @BsonProperty("price")
    private double price;

    @BsonProperty("active")
    private boolean active;

    public Rent(UUID clientId, UUID vehicleId, int days) {
        this.id = UUID.randomUUID();
        this.clientId = clientId;
        this.vehicleId = vehicleId;
        this.days = days;
        this.startDate = LocalDate.now();
        this.returnDate = null;
        this.endDate = LocalDate.now().plusDays(days);
        this.price = 0.00;
        this.active = true;
    }

    public Rent() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(UUID vehicleId) {
        this.vehicleId = vehicleId;
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

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
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
        return "Rent{" + getId() +
                ", clientId=" + clientId +
                ", vehicleId=" + vehicleId +
                ", days=" + days +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", active=" + active +
                '}';
    }
}
