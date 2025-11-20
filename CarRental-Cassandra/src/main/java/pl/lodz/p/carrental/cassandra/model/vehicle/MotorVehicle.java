package pl.lodz.p.carrental.cassandra.model.vehicle;

public abstract class MotorVehicle extends Vehicle {

    private double engineDisplacement;

    public MotorVehicle(String plateNumber, double pricePerDay, double engineDisplacement) {
        super(plateNumber, pricePerDay);
        this.engineDisplacement = engineDisplacement;
    }

    public double getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(double engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    @Override
    public String toString() {
        return "MotorVehicle{" +
                "engineDisplacement=" + engineDisplacement +
                '}';
    }
}
