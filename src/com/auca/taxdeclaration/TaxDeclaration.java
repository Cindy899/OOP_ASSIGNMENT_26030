
package com.auca.taxdeclaration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Validatable;
import com.auca.taxobligation.TaxObligation;

public class TaxDeclaration implements Identifiable, Validatable {

    // Six required private fields
    private String declarationId;
    private TaxObligation obligation;
    private LocalDate filingDate;
    private BigDecimal taxableAmount;
    private BigDecimal declaredTax;
    private String status;

    // Constructor
    public TaxDeclaration(String declarationId,
                          TaxObligation obligation,
                          LocalDate filingDate,
                          BigDecimal taxableAmount,
                          BigDecimal declaredTax,
                          String status) {

        this.declarationId = declarationId;
        this.obligation = obligation;
        this.filingDate = filingDate;
        this.taxableAmount = taxableAmount;
        this.declaredTax = declaredTax;
        this.status = status;
    }

    // Getters
    public String getDeclarationId() {
        return declarationId;
    }

    public TaxObligation getObligation() {
        return obligation;
    }

    public LocalDate getFilingDate() {
        return filingDate;
    }

    public BigDecimal getTaxableAmount() {
        return taxableAmount;
    }

    public BigDecimal getDeclaredTax() {
        return declaredTax;
    }

    public String getStatus() {
        return status;
    }

    // Setter for status
    public void setStatus(String status) {
        this.status = status;
    }

    // Calculate expected tax using the rate in TaxType
    public BigDecimal calculateExpectedTax() {

        BigDecimal rate = obligation.getTaxType().getRate();

        return taxableAmount
                .multiply(rate)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    // Identifiable methods
    @Override
    public String getId() {
        return declarationId;
    }

    @Override
    public String getDisplayName() {
        return "Tax Declaration " + declarationId;
    }

    @Override
    public boolean isActive() {
        return status != null
                && !status.equals("REJECTED")
                && !status.equals("CANCELLED");
    }

    // Validation
    @Override
    public void validate() throws ValidationException {

        if (declarationId == null || declarationId.trim().isEmpty()) {
            throw new ValidationException(
                "Declaration ID is required."
            );
        }

        if (obligation == null) {
            throw new ValidationException(
                "A tax obligation must be selected."
            );
        }

        if (!obligation.isActive()) {
            throw new ValidationException(
                "The selected tax obligation is not active."
            );
        }

        if (filingDate == null) {
            throw new ValidationException(
                "Filing date is required."
            );
        }

        if (filingDate.isAfter(LocalDate.now())) {
            throw new ValidationException(
                "Filing date cannot be in the future."
            );
        }

        if (taxableAmount == null
                || taxableAmount.compareTo(BigDecimal.ZERO) < 0
                || taxableAmount.scale() > 2
                || taxableAmount.compareTo(
                    new BigDecimal("999999999999.99")) > 0) {

            throw new ValidationException(
                "Taxable amount must be between 0 and 999999999999.99, "
                + "with at most two decimal places."
            );
        }

        if (declaredTax == null
                || declaredTax.compareTo(BigDecimal.ZERO) < 0
                || declaredTax.scale() > 2
                || declaredTax.compareTo(
                    new BigDecimal("999999999999.99")) > 0) {

            throw new ValidationException(
                "Declared tax must be between 0 and 999999999999.99, "
                + "with at most two decimal places."
            );
        }

        if (status == null ||
                !(status.equals("SUBMITTED")
                || status.equals("ACCEPTED")
                || status.equals("REJECTED"))) {

            throw new ValidationException(
                "Status must be SUBMITTED, ACCEPTED or REJECTED."
            );
        }

        // Check declared tax against expected tax
        BigDecimal expectedTax = calculateExpectedTax();

        BigDecimal difference = declaredTax.subtract(expectedTax).abs();

        if (difference.compareTo(new BigDecimal("0.01")) > 0) {
            throw new ValidationException(
                "Declared tax does not match the expected tax. "
                + "Expected: " + expectedTax
            );
        }
    }

    // Validatable methods
    @Override
    public void printDetails() {
        System.out.println("----- TAX DECLARATION DETAILS -----");
        System.out.println("Declaration ID: " + declarationId);
        System.out.println("Obligation ID: " + obligation.getId());
        System.out.println("Filing Date: " + filingDate);
        System.out.println("Taxable Amount: " + taxableAmount);
        System.out.println("Declared Tax: " + declaredTax);
        System.out.println("Expected Tax: " + calculateExpectedTax());
        System.out.println("Status: " + status);
    }

    @Override
    public String getSummary() {
        return declarationId + " - Taxable Amount: "
                + taxableAmount + " - Status: " + status;
    }
}
