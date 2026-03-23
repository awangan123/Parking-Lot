// ======================= VEHICLE =======================
public abstract class Vehicle {
    protected String licensePlate;
    protected String vehicleType;
    protected ParkingFeeStrategy feeStrategy;

    public Vehicle(String licensePlate, String vehicleType, ParkingFeeStrategy feeStrategy) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.feeStrategy = feeStrategy;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public double calculateFee(int duration, DurationType durationType) {
        return feeStrategy.calculateFee(vehicleType, duration, durationType);
    }
}

// ======================= VEHICLE TYPES =======================
class CarVehicle extends Vehicle {
    public CarVehicle(String licensePlate, String vehicleType, ParkingFeeStrategy feeStrategy) {
        super(licensePlate, vehicleType, feeStrategy);
    }
}

class BikeVehicle extends Vehicle {
    public BikeVehicle(String licensePlate, String vehicleType, ParkingFeeStrategy feeStrategy) {
        super(licensePlate, vehicleType, feeStrategy);
    }
}

class OtherVehicle extends Vehicle {
    public OtherVehicle(String licensePlate, String vehicleType, ParkingFeeStrategy feeStrategy) {
        super(licensePlate, vehicleType, feeStrategy);
    }
}

// ======================= VEHICLE FACTORY =======================
class VehicleFactory {
    public static Vehicle createVehicle(String vehicleType, String licensePlate, ParkingFeeStrategy feeStrategy) {
        if (vehicleType.equalsIgnoreCase("Car")) {
            return new CarVehicle(licensePlate, vehicleType, feeStrategy);
        } else if (vehicleType.equalsIgnoreCase("Bike")) {
            return new BikeVehicle(licensePlate, vehicleType, feeStrategy);
        }
        return new OtherVehicle(licensePlate, vehicleType, feeStrategy);
    }
}

// ======================= FEE STRATEGY =======================
interface ParkingFeeStrategy {
    double calculateFee(String vehicleType, int duration, DurationType durationType);
}

enum DurationType {
    HOURS,
    DAYS
}

class BasicHourlyRateStrategy implements ParkingFeeStrategy {
    public double calculateFee(String vehicleType, int duration, DurationType durationType) {
        switch (vehicleType.toLowerCase()) {
            case "car":
                return durationType == DurationType.HOURS ? duration * 10 : duration * 10 * 24;
            case "bike":
                return durationType == DurationType.HOURS ? duration * 5 : duration * 5 * 24;
            default:
                return durationType == DurationType.HOURS ? duration * 15 : duration * 15 * 24;
        }
    }
}

class PremiumRateStrategy implements ParkingFeeStrategy {
    public double calculateFee(String vehicleType, int duration, DurationType durationType) {
        switch (vehicleType.toLowerCase()) {
            case "car":
                return durationType == DurationType.HOURS ? duration * 15 : duration * 15 * 24;
            case "bike":
                return durationType == DurationType.HOURS ? duration * 8 : duration * 8 * 24;
            default:
                return durationType == DurationType.HOURS ? duration * 20 : duration * 20 * 24;
        }
    }
}

// ======================= PAYMENT STRATEGY =======================
interface PaymentStrategy {
    void processPayment(double amount);
}

class CashPayment implements PaymentStrategy {
    public void processPayment(double amount) {
        System.out.println("Processing cash payment: $" + amount);
    }
}

class CreditCardPayment implements PaymentStrategy {
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment: $" + amount);
    }
}

class Payment {
    private double amount;
    private PaymentStrategy strategy;

    public Payment(double amount, PaymentStrategy strategy) {
        this.amount = amount;
        this.strategy = strategy;
    }

    public void process() {
        strategy.processPayment(amount);
    }
}

// ======================= PARKING SPOT =======================
abstract class ParkingSpot {
    private int spotNumber;
    private boolean isOccupied;
    private Vehicle vehicle;
    private String spotType;

    public ParkingSpot(int spotNumber, String spotType) {
        this.spotNumber = spotNumber;
        this.spotType = spotType;
        this.isOccupied = false;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public abstract boolean canParkVehicle(Vehicle vehicle);

    public void parkVehicle(Vehicle vehicle) {
        if (isOccupied) throw new IllegalStateException("Occupied");
        if (!canParkVehicle(vehicle)) throw new IllegalArgumentException("Invalid vehicle type");
        this.vehicle = vehicle;
        this.isOccupied = true;
    }

    public void vacate() {
        this.vehicle = null;
        this.isOccupied = false;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getSpotType() {
        return spotType;
    }
}

class CarParkingSpot extends ParkingSpot {
    public CarParkingSpot(int spotNumber) {
        super(spotNumber, "Car");
    }

    public boolean canParkVehicle(Vehicle vehicle) {
        return vehicle.getVehicleType().equalsIgnoreCase("Car");
    }
}

class BikeParkingSpot extends ParkingSpot {
    public BikeParkingSpot(int spotNumber) {
        super(spotNumber, "Bike");
    }

    public boolean canParkVehicle(Vehicle vehicle) {
        return vehicle.getVehicleType().equalsIgnoreCase("Bike");
    }
}

// ======================= PARKING LOT =======================
import java.util.*;

class ParkingLot {
    private List<ParkingSpot> spots;

    public ParkingLot(List<ParkingSpot> spots) {
        this.spots = spots;
    }

    public ParkingSpot findAvailableSpot(String type) {
        for (ParkingSpot s : spots) {
            if (!s.isOccupied() && s.getSpotType().equalsIgnoreCase(type)) {
                return s;
            }
        }
        return null;
    }

    public ParkingSpot parkVehicle(Vehicle v) {
        ParkingSpot spot = findAvailableSpot(v.getVehicleType());
        if (spot != null) {
            spot.parkVehicle(v);
            return spot;
        }
        return null;
    }

    public void vacateSpot(ParkingSpot spot) {
        if (spot != null) spot.vacate();
    }
}

// ======================= MAIN =======================
public class Main {
    public static void main(String[] args) {
        List<ParkingSpot> spots = new ArrayList<>();
        spots.add(new CarParkingSpot(1));
        spots.add(new BikeParkingSpot(2));

        ParkingLot lot = new ParkingLot(spots);

        ParkingFeeStrategy basic = new BasicHourlyRateStrategy();

        Vehicle car = VehicleFactory.createVehicle("Car", "KL01AB1234", basic);

        ParkingSpot spot = lot.parkVehicle(car);

        double fee = car.calculateFee(2, DurationType.HOURS);

        PaymentStrategy payment = new CreditCardPayment();
        Payment p = new Payment(fee, payment);
        p.process();

        lot.vacateSpot(spot);
    }
}
