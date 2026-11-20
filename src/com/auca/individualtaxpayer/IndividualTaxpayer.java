
package com.auca.individualtaxpayer;
import com.auca.exceptions.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.auca.abstracts.RegisteredTaxpayer;

public class IndividualTaxpayer extends RegisteredTaxpayer {

    // Six attributes belonging to IndividualTaxpayer
    private String nationalId;
    private String occupation;
    private String businessName;
    private LocalDate businessStartDate;
    private BigDecimal annualTurnover;
    private boolean resident;

    // Constructor
    public IndividualTaxpayer(
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
            String taxOffice,
            String nationalId,
            String occupation,
            String businessName,
            LocalDate businessStartDate,
            BigDecimal annualTurnover,
            boolean resident) {

        super(partyId, firstName, lastName, email,
                phoneNumber, dateOfBirth, tin,
                registrationDate, taxpayerStatus,
                district, sector, taxOffice);

        this.nationalId = nationalId;
        this.occupation = occupation;
        this.businessName = businessName;
        this.businessStartDate = businessStartDate;
        this.annualTurnover = annualTurnover;
        this.resident = resident;
    }

    // Getters
    public String getNationalId() {
        return nationalId;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getBusinessName() {
        return businessName;
    }

    public LocalDate getBusinessStartDate() {
        return businessStartDate;
    }

    public BigDecimal getAnnualTurnover() {
        return annualTurnover;
    }

    public boolean isResident() {
        return resident;
    }

    // Validation
    @Override
    public void validate() throws ValidationException {

        super.validate();

        if (nationalId == null ||
                !nationalId.matches("[0-9]{16}")) {
            throw new ValidationException(
                    "National ID must contain exactly 16 digits.");
        }

        if (occupation == null || occupation.trim().length() < 2) {
            throw new ValidationException("Invalid occupation.");
        }

        if (businessName == null || businessName.trim().isEmpty()) {
            throw new ValidationException("Business name is required.");
        }

        if (businessStartDate == null ||
                !businessStartDate.isBefore(getRegistrationDate())) {
            throw new ValidationException(
                    "Business start date must precede registration.");
        }

        if (annualTurnover == null ||
                annualTurnover.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(
                    "Annual turnover cannot be negative.");
        }

        if (annualTurnover.scale() > 2 ||
                annualTurnover.compareTo(
                    new BigDecimal("999999999999.99")) > 0) {
            throw new ValidationException(
                    "Annual turnover must have at most 2 decimal places "
                    + "and cannot exceed 999999999999.99.");
        }
    }

    // Display details
    @Override
    public String getDisplayName() {
        return getFirstName() + " " + getLastName()
                + " (Individual)";
    }

    @Override
    public void printDetails() {
        super.printDetails();

        System.out.println("National ID: " + nationalId);
        System.out.println("Occupation: " + occupation);
        System.out.println("Business Name: " + businessName);
        System.out.println("Business Start Date: " + businessStartDate);
        System.out.println("Annual Turnover: " + annualTurnover);
        System.out.println("Resident: " + resident);
    }

    @Override
    public String getSummary() {
        return super.getSummary()
                + " | Individual Taxpayer"
                + " | Occupation: " + occupation;
    }

    // Individual-specific method
    public void displayIndividualType() {
        System.out.println("This is an individual taxpayer.");
    }
}
