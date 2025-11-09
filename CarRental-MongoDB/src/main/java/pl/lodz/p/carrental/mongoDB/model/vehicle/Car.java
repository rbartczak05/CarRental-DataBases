package pl.lodz.p.carrental.mongoDB.model.vehicle;

public class Car extends MotorVehicle {

    private String segement;

    public Car(String plateNumber, double pricePerDay, double engineDisplacement, String segement) {
        super(plateNumber, pricePerDay, engineDisplacement);
        this.segement = segement;
    }

    public String getSegement() {
        return segement;
    }

    public void setSegement(String segement) {
        this.segement = segement;
    }

    @Override
    public String toString() {
        return "Car{" +
                "segement='" + segement + '\'' +
                '}';
    }
}
