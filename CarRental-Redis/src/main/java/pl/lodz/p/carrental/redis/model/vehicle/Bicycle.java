package pl.lodz.p.carrental.redis.model.vehicle;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator(key = "_clazz", value = "Bicycle")
public class Bicycle extends Vehicle {

    public Bicycle(double pricePerDay) {
        super(pricePerDay);
    }

    public Bicycle() {

    }

    @Override
    public String toString() {
        return "Bicycle{" + super.toString() + '}';
    }
}
