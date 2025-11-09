package pl.lodz.p.carrental.redis.model.vehicle;

import pl.lodz.p.carrental.redis.model.vehicle.Vehicle;

public class Bicycle extends Vehicle {

    public Bicycle(String plateNumber, double pricePerDay) {
        super(plateNumber, pricePerDay);
    }

    @Override
    public String toString() {
        return "Bicycle{" + super.toString() + '}';
    }
}
