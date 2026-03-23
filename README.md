# 🚗 Parking Lot System – README

## 📌 Overview

This project is a **Parking Lot Management System** implemented in Java using core Object-Oriented Design principles and design patterns.

It simulates:

* Vehicle creation
* Parking spot allocation
* Fee calculation
* Payment processing

The system is designed to be **extensible, modular, and maintainable**.

---

## 🏗️ Design Patterns Used

### 1. **Factory Pattern**

* `VehicleFactory` creates different types of vehicles (`Car`, `Bike`, `Other`)
* Encapsulates object creation logic

### 2. **Strategy Pattern**

Used in two places:

#### a. Parking Fee Strategy

* `ParkingFeeStrategy` interface
* Implementations:

  * `BasicHourlyRateStrategy`
  * `PremiumRateStrategy`

#### b. Payment Strategy

* `PaymentStrategy` interface
* Implementations:

  * `CashPayment`
  * `CreditCardPayment`

---

## 🚘 Core Components

### 1. Vehicle Hierarchy

Abstract class:

```
Vehicle
```

Subclasses:

* `CarVehicle`
* `BikeVehicle`
* `OtherVehicle`

Each vehicle:

* Has a license plate
* Has a type
* Uses a fee strategy

---

### 2. Parking Spots

Abstract class:

```
ParkingSpot
```

Subclasses:

* `CarParkingSpot`
* `BikeParkingSpot`

Features:

* Tracks occupancy
* Validates compatible vehicle type
* Supports park and vacate operations

---

### 3. Parking Lot

Class:

```
ParkingLot
```

Responsibilities:

* Maintain list of parking spots
* Find available spots
* Park vehicles
* Vacate spots

---

### 4. Fee Calculation

Handled using:

```
ParkingFeeStrategy
```

Supports:

* Hourly pricing
* Daily pricing

Example:

* Car: ₹10/hour (Basic)
* Bike: ₹5/hour (Basic)

---

### 5. Payment System

Classes:

* `Payment`
* `PaymentStrategy`

Supports:

* Cash payments
* Credit card payments

---

## ⚙️ How It Works

### Step-by-step Flow:

1. Create parking spots
2. Initialize parking lot
3. Choose fee strategy
4. Create vehicle using factory
5. Park vehicle
6. Calculate fee
7. Process payment
8. Vacate parking spot

---

## ▶️ Example Usage

```java
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
```

---

## 🧩 Extensibility

You can easily extend the system:

### Add new vehicle type

* Create new subclass of `Vehicle`
* Update `VehicleFactory`

### Add new pricing strategy

* Implement `ParkingFeeStrategy`

### Add new payment method

* Implement `PaymentStrategy`

### Add new parking spot type

* Extend `ParkingSpot`

---

## 🚀 Future Improvements

* Add ticket generation system
* Introduce entry/exit timestamps
* Add multi-level parking support
* Implement reservation system
* Add real-time availability dashboard
* Persist data using database

---

## 🧪 Error Handling

* Prevents parking in occupied spots
* Prevents invalid vehicle-spot assignment
* Uses exceptions:

  * `IllegalStateException`
  * `IllegalArgumentException`

---

## 📁 Project Structure

```
Vehicle
VehicleFactory
ParkingFeeStrategy
PaymentStrategy
ParkingSpot
ParkingLot
Main
```

---

## 📄 License

This project is for educational purposes and can be freely used or modified.

---

## 🙌 Summary

This project demonstrates:

* Clean OOP design
* Use of design patterns
* Separation of concerns
* Scalability and flexibility

Perfect for learning **Low-Level Design (LLD)** concepts and interview preparation.
