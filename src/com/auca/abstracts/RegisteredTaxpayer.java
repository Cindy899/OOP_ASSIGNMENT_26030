package com.auca.abstracts;

import java.time.LocalDate;

import com.auca.exceptions.ValidationException;

public abstract class RegisteredTaxpayer extends TaxParty {

    private String tin;
    private LocalDate registrationDate;
    private String taxpayerStatus;
    private String district;
    private String sector;
    private String taxOffice;

    public RegisteredTaxpayer(
            String partyId,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            LocalDate dateOfBirth,
            String tin,
            LocalDate registrationDate,
            String taxpayerStatus,
            String district,
            String sector,
            String taxOffice) {

        super(partyId, firstName, lastName, email,
        phoneNumber, dateOfBirth);

        this.tin = tin;
        this.registrationDate = registrationDate;
        this.taxpayerStatus = taxpayerStatus;
        this.district = district;
        this.sector = sector;
        this.taxOffice = taxOffice;
    }

    public String getTin() {
        return tin;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public String getTaxpayerStatus() {
        return taxpayerStatus;
    }

    public String getDistrict() {
        return district;
    }

    public String getSector() {
        return sector;
    }

    public String getTaxOffice() {
        return taxOffice;
    }

    @Override
    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(taxpayerStatus);
    }

    @Override
    public void validate() throws ValidationException {

        super.validate();

        if (tin == null || !tin.matches("[0-9]{9}")) {
            throw new ValidationException(
                    "TIN must contain exactly 9 digits.");
        }

        if (registrationDate == null ||
                registrationDate.isAfter(LocalDate.now())) {
            throw new ValidationException(
                    "Invalid registration date.");
        }

        if (getDateOfBirth() == null ||
                !getDateOfBirth().isBefore(registrationDate)) {
            throw new ValidationException(
                    "Date of birth must precede registration.");
        }

        if (taxpayerStatus == null ||
                !(taxpayerStatus.equals("ACTIVE") ||
                taxpayerStatus.equals("INACTIVE") ||
                taxpayerStatus.equals("SUSPENDED"))) {
            throw new ValidationException(
                    "Invalid taxpayer status.");
        }
    }

    @Override
    public void printDetails() {
        super.printDetails();

        System.out.println("TIN: " + tin);
        System.out.println("Registration Date: " + registrationDate);
        System.out.println("Status: " + taxpayerStatus);
        System.out.println("District: " + district);
        System.out.println("Sector: " + sector);
        System.out.println("Tax Office: " + taxOffice);
    }

    @Override
    public String getSummary() {
        return super.getSummary() + " | TIN: " + tin;
    }
}