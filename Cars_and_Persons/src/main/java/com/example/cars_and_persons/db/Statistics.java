package com.example.cars_and_persons.db;

public class Statistics {
    private Long personCount;
    private Long carCount;
    private Long uniqueVendorCount;

    public Statistics() {
    }

    public Long getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Long personCount) {
        this.personCount = personCount;
    }

    public Long getCarCount() {
        return carCount;
    }

    public void setCarCount(Long carCount) {
        this.carCount = carCount;
    }

    public Long getUniqueVendorCount() {
        return uniqueVendorCount;
    }

    public void setUniqueVendorCount(Long uniqueVendorCount) {
        this.uniqueVendorCount = uniqueVendorCount;
    }
}
