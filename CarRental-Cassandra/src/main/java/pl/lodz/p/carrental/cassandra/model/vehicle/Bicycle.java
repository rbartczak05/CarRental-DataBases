package pl.lodz.p.carrental.cassandra.model.vehicle;

public class Bicycle extends Vehicle {

    public Bicycle(String plateNumber, double pricePerDay) {
        super(plateNumber, pricePerDay);
    }

    @Override
    public String toString() {
        return "Bicycle{" + super.toString() + '}';
    }
}
