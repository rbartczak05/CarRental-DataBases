package pl.lodz.p.carrental.postgreSQL.model.vehicle;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("bicycle")
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
