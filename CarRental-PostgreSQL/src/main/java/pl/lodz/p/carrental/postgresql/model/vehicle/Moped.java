package pl.lodz.p.carrental.postgresql.model.vehicle;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("moped")
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
