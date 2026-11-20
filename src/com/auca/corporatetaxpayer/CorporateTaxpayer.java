
package com.auca.corporatetaxpayer;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.auca.abstracts.RegisteredTaxpayer;
import com.auca.exceptions.ValidationException;

public class CorporateTaxpayer extends RegisteredTaxpayer {

    // Six attributes belonging to CorporateTaxpayer
    private String companyRegistrationNo;
    private String legalName;
    private LocalDate incorporationDate;
    private String businessSector;
    private String authorizedRepresentative;
    private BigDecimal annualTurnover;

    // Constructor
    public CorporateTaxpayer(
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
            String companyRegistrationNo,
            String legalName,
            LocalDate incorporationDate,
            String businessSector,
            String authorizedRepresentative,
            BigDecimal annualTurnover) {

        super(partyId, firstName, lastName, email,
                phoneNumber, dateOfBirth, tin,
                registrationDate, taxpayerStatus,
                district, sector, taxOffice);

        this.companyRegistrationNo = companyRegistrationNo;
        this.legalName = legalName;
        this.incorporationDate = incorporationDate;
        this.businessSector = businessSector;
        this.authorizedRepresentative = authorizedRepresentative;
        this.annualTurnover = annualTurnover;
    }

    // Getters
    public String getCompanyRegistrationNo() {
        return companyRegistrationNo;
    }

    public String getLegalName() {
        return legalName;
    }

    public LocalDate getIncorporationDate() {
        return incorporationDate;
    }

    public String getBusinessSector() {
        return businessSector;
    }

    public String getAuthorizedRepresentative() {
        return authorizedRepresentative;
    }

    public BigDecimal getAnnualTurnover() {
        return annualTurnover;
    }

    // Validation
    @Override
    public void validate() throws ValidationException {

        super.validate();

        if (companyRegistrationNo == null ||
                !companyRegistrationNo.matches(
                    "[A-Z0-9-]{5,30}")) {

            throw new ValidationException(
                    "Company registration number must contain "
                    + "5-30 uppercase letters, digits or hyphens.");
        }

        if (legalName == null ||
                legalName.trim().length() < 3 ||
                legalName.trim().length() > 120) {

            throw new ValidationException(
                    "Legal name must contain 3-120 characters.");
        }

        if (incorporationDate == null ||
                !incorporationDate.isBefore(getRegistrationDate())) {

            throw new ValidationException(
                    "Incorporation date must precede registration.");
        }

        if (businessSector == null ||
                businessSector.trim().length() < 2) {

            throw new ValidationException(
                    "Business sector is required.");
        }

        if (authorizedRepresentative == null ||
                authorizedRepresentative.trim().length() < 2) {

            throw new ValidationException(
                    "Authorized representative is required.");
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

    // Display name
    @Override
    public String getDisplayName() {
        return legalName + " (Corporate)";
    }

    // Print all details
    @Override
    public void printDetails() {

        super.printDetails();

        System.out.println("Company Registration Number: "
                + companyRegistrationNo);

        System.out.println("Legal Name: " + legalName);

        System.out.println("Incorporation Date: "
                + incorporationDate);

        System.out.println("Business Sector: " + businessSector);

        System.out.println("Authorized Representative: "
                + authorizedRepresentative);

        System.out.println("Annual Turnover: " + annualTurnover);
    }

    @Override
    public String getSummary() {
        return super.getSummary()
                + " | Corporate Taxpayer"
                + " | Company: " + legalName;
    }

    // Corporate-specific method
    public void displayCorporateType() {
        System.out.println("This is a corporate taxpayer.");
    }
}
