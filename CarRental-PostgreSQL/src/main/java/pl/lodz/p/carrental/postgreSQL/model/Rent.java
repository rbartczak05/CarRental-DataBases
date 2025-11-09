package pl.lodz.p.carrental.postgreSQL.model;

import jakarta.persistence.*;
import pl.lodz.p.carrental.postgreSQL.model.client.Client;
import pl.lodz.p.carrental.postgreSQL.model.vehicle.Vehicle;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "rent")
@Access(AccessType.FIELD)
public class Rent {

    @Id
    @Column(name = "rentId")
    private UUID rentId;
    @ManyToOne(fetch = FetchType.EAGER ,cascade = CascadeType.MERGE)
    @JoinColumn(name = "clientId", foreignKey = @ForeignKey(name = "fk_rent_client_id"))
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "vehicleId", foreignKey = @ForeignKey(name = "fk_rent_vehicle_id"))
    private Vehicle vehicle;
    @Column(name = "days")
    private int days;
    @Column(name = "startDate")
    private LocalDate startDate;
    @Column(name = "endDate")
    private LocalDate endDate;
    @Column(name = "price")
    private double price;
    @Column(name = "active")
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

    public Rent() {

    }

    public UUID getRentId() {
        return rentId;
    }

    public void setRentId(UUID id) {
        this.rentId = id;
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
