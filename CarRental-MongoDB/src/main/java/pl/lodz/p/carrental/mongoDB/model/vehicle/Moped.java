package pl.lodz.p.carrental.mongoDB.model.vehicle;

public class Moped extends MotorVehicle {

    public Moped(String plateNumber, double pricePerDay, double engineDisplacement) {
        super(plateNumber, pricePerDay, engineDisplacement);
    }

    @Override
    public String toString() {
        return "Moped{" + super.toString() + '}';
    }
}
