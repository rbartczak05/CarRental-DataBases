package pl.lodz.p.carrental.postgresql.model;

import jakarta.persistence.*;
import pl.lodz.p.carrental.postgresql.model.client.Client;
import pl.lodz.p.carrental.postgresql.model.vehicle.Vehicle;

import java.time.LocalDate;

@Entity
@Table(name = "rent")
@Access(AccessType.FIELD)
public class Rent extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "fk_rent_client_id"))
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "vehicle_id", foreignKey = @ForeignKey(name = "fk_rent_vehicle_id"))
    private Vehicle vehicle;
    @Column(name = "days")
    private int days;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "return_date")
    private LocalDate returnDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "price")
    private double price;
    @Column(name = "active")
    private boolean active;

    public Rent(Client client, Vehicle vehicle, int days) {
        this.client = client;
        this.vehicle = vehicle;
        this.days = days;
        this.startDate = LocalDate.now();
        this.returnDate = null;
        this.endDate = LocalDate.now().plusDays(days);
        this.price = (vehicle.getPricePerDay() * days) * (1.00 - client.getClientType().getDiscount());
        this.active = true;
    }

    public Rent() {

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
