package pl.lodz.p.carrental.redis.model.vehicle;

import pl.lodz.p.carrental.redis.model.vehicle.MotorVehicle;

public class Moped extends MotorVehicle {

    public Moped(String plateNumber, double pricePerDay, double engineDisplacement) {
        super(plateNumber, pricePerDay, engineDisplacement);
    }

    @Override
    public String toString() {
        return "Moped{" + super.toString() + '}';
    }
}
