package pl.lodz.p.carrental.mongoDB.model.vehicle;


import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator(key = "_clazz", value = "Moped")
public class Moped extends MotorVehicle {

    public Moped(String plateNumber, double pricePerDay, double engineDisplacement) {
        super(plateNumber, pricePerDay, engineDisplacement);
    }

    public Moped() {

    }

    @Override
    public String toString() {
        return "Moped{" + super.toString() + '}';
    }
}
