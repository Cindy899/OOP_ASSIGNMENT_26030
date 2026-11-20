
package com.auca.penalty;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Validatable;
import com.auca.taxobligation.TaxObligation;

public class Penalty implements Identifiable, Validatable {

    // Six required private fields
    private String penaltyId;
    private TaxObligation obligation;
    private String penaltyType;
    private BigDecimal baseAmount;
    private BigDecimal penaltyRate;
    private BigDecimal penaltyAmount;

    // Constructor
    public Penalty(String penaltyId,
                   TaxObligation obligation,
                   String penaltyType,
                   BigDecimal baseAmount,
                   BigDecimal penaltyRate,
                   BigDecimal penaltyAmount) {

        this.penaltyId = penaltyId;
        this.obligation = obligation;
        this.penaltyType = penaltyType;
        this.baseAmount = baseAmount;
        this.penaltyRate = penaltyRate;
        this.penaltyAmount = penaltyAmount;
    }

    // Getters
    public String getPenaltyId() {
        return penaltyId;
    }

    public TaxObligation getObligation() {
        return obligation;
    }

    public String getPenaltyType() {
        return penaltyType;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public BigDecimal getPenaltyRate() {
        return penaltyRate;
    }

    public BigDecimal getPenaltyAmount() {
        return penaltyAmount;
    }

    // Calculate penalty amount
    public BigDecimal calculatePenalty() {

        return baseAmount
                .multiply(penaltyRate)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    // Identifiable methods
    @Override
    public String getId() {
        return penaltyId;
    }

    @Override
    public String getDisplayName() {
        return "Penalty " + penaltyId;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    // Validation
    @Override
    public void validate() throws ValidationException {

        if (penaltyId == null || penaltyId.trim().isEmpty()) {
            throw new ValidationException(
                "Penalty ID is required."
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

        if (penaltyType == null || penaltyType.trim().isEmpty()) {
            throw new ValidationException(
                "Penalty type is required."
            );
        }

        if (baseAmount == null
                || baseAmount.compareTo(BigDecimal.ZERO) < 0
                || baseAmount.scale() > 2
                || baseAmount.compareTo(
                    new BigDecimal("999999999999.99")) > 0) {

            throw new ValidationException(
                "Base amount must be non-negative and have at most "
                + "two decimal places."
            );
        }

        if (penaltyRate == null
                || penaltyRate.compareTo(BigDecimal.ZERO) < 0
                || penaltyRate.compareTo(new BigDecimal("100")) > 0
                || penaltyRate.scale() > 2) {

            throw new ValidationException(
                "Penalty rate must be between 0 and 100."
            );
        }

        if (penaltyAmount == null
                || penaltyAmount.compareTo(BigDecimal.ZERO) < 0
                || penaltyAmount.scale() > 2
                || penaltyAmount.compareTo(
                    new BigDecimal("999999999999.99")) > 0) {

            throw new ValidationException(
                "Penalty amount must be non-negative and have at most "
                + "two decimal places."
            );
        }

        // Check whether the supplied penalty amount is correct
        BigDecimal expectedPenalty = calculatePenalty();

        if (penaltyAmount.compareTo(expectedPenalty) != 0) {
            throw new ValidationException(
                "Incorrect penalty amount. Expected: " + expectedPenalty
            );
        }
    }

    // Validatable methods
    @Override
    public void printDetails() {
        System.out.println("----- PENALTY DETAILS -----");
        System.out.println("Penalty ID: " + penaltyId);
        System.out.println("Obligation ID: " + obligation.getId());
        System.out.println("Penalty Type: " + penaltyType);
        System.out.println("Base Amount: " + baseAmount);
        System.out.println("Penalty Rate: " + penaltyRate + "%");
        System.out.println("Penalty Amount: " + penaltyAmount);
    }

    @Override
    public String getSummary() {
        return penaltyId + " - " + penaltyType
                + " - Penalty: " + penaltyAmount;
    }
}
