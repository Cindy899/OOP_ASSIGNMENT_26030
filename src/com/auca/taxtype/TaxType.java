
package com.auca.taxtype;

import java.math.BigDecimal;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Validatable;

public class TaxType implements Identifiable, Validatable {

    // Six required private fields
    private String taxTypeId;
    private String taxCode;
    private String taxName;
    private BigDecimal rate;
    private String filingFrequency;
    private boolean active;

    // Constructor
    public TaxType(String taxTypeId, String taxCode, String taxName,
                   BigDecimal rate, String filingFrequency, boolean active) {

        this.taxTypeId = taxTypeId;
        this.taxCode = taxCode;
        this.taxName = taxName;
        this.rate = rate;
        this.filingFrequency = filingFrequency;
        this.active = active;
    }

    // Getters
    public String getTaxTypeId() {
        return taxTypeId;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public String getTaxName() {
        return taxName;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public String getFilingFrequency() {
        return filingFrequency;
    }

    // Setter
    public void setActive(boolean active) {
        this.active = active;
    }

    // Identifiable methods
    @Override
    public String getId() {
        return taxTypeId;
    }

    @Override
    public String getDisplayName() {
        return taxName;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    // Validation
    @Override
    public void validate() throws ValidationException {

        if (taxTypeId == null || taxTypeId.trim().isEmpty()) {
            throw new ValidationException("Tax Type ID is required.");
        }

        if (taxCode == null || !taxCode.matches("[A-Z0-9-]{2,20}")) {
            throw new ValidationException(
                "Tax code must contain 2-20 uppercase letters, numbers or hyphens."
            );
        }

        if (taxName == null || taxName.trim().length() < 2
                || taxName.trim().length() > 60) {
            throw new ValidationException(
                "Tax name must contain between 2 and 60 characters."
            );
        }

        if (rate == null || rate.compareTo(BigDecimal.ZERO) < 0
                || rate.compareTo(new BigDecimal("100")) > 0) {
            throw new ValidationException(
                "Tax rate must be between 0 and 100."
            );
        }

        if (filingFrequency == null ||
                !(filingFrequency.equals("MONTHLY")
                || filingFrequency.equals("QUARTERLY")
                || filingFrequency.equals("ANNUAL"))) {

            throw new ValidationException(
                "Filing frequency must be MONTHLY, QUARTERLY or ANNUAL."
            );
        }
    }

    // Validatable methods
    @Override
    public void printDetails() {
        System.out.println("----- TAX TYPE DETAILS -----");
        System.out.println("Tax Type ID: " + taxTypeId);
        System.out.println("Tax Code: " + taxCode);
        System.out.println("Tax Name: " + taxName);
        System.out.println("Rate: " + rate + "%");
        System.out.println("Filing Frequency: " + filingFrequency);
        System.out.println("Active: " + active);
    }

    @Override
    public String getSummary() {
        return taxName + " (" + taxCode + ") - " + rate + "%";
    }
}
