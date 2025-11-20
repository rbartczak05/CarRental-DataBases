package pl.lodz.p.carrental.postgresql.model.client;

public enum ClientType {
    STANDARD(1, 0.0),
    BRONZE(3, 0.05),
    SILVER(5, 0.1),
    GOLD(7, 0.15),
    PLATINUM(10, 0.25);

    private final int maxVehicles;
    private final double discount;

    ClientType(int maxVehicles, double discount) {
        this.maxVehicles = maxVehicles;
        this.discount = discount;
    }

    public int getMaxVehicles() {
        return maxVehicles;
    }

    public double getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return "ClientType{" +
                "maxVehicles=" + maxVehicles +
                ", discount=" + discount +
                '}';
    }
}
