
package com.auca.taxassessment;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Validatable;
import com.auca.taxdeclaration.TaxDeclaration;

public class TaxAssessment implements Identifiable, Validatable {

    // Six required private fields
    private String assessmentId;
    private TaxDeclaration declaration;
    private BigDecimal assessedTax;
    private BigDecimal adjustmentAmount;
    private LocalDate assessmentDate;
    private String status;

    // Constructor
    public TaxAssessment(String assessmentId,
                         TaxDeclaration declaration,
                         BigDecimal assessedTax,
                         BigDecimal adjustmentAmount,
                         LocalDate assessmentDate,
                         String status) {

        this.assessmentId = assessmentId;
        this.declaration = declaration;
        this.assessedTax = assessedTax;
        this.adjustmentAmount = adjustmentAmount;
        this.assessmentDate = assessmentDate;
        this.status = status;
    }

    // Getters
    public String getAssessmentId() {
        return assessmentId;
    }

    public TaxDeclaration getDeclaration() {
        return declaration;
    }

    public BigDecimal getAssessedTax() {
        return assessedTax;
    }

    public BigDecimal getAdjustmentAmount() {
        return adjustmentAmount;
    }

    public LocalDate getAssessmentDate() {
        return assessmentDate;
    }

    public String getStatus() {
        return status;
    }

    // Calculate assessment total
    public BigDecimal calculateTotal() {
        return assessedTax.add(adjustmentAmount);
    }

    // Setter for status
    public void setStatus(String status) {
        this.status = status;
    }

    // Identifiable methods
    @Override
    public String getId() {
        return assessmentId;
    }

    @Override
    public String getDisplayName() {
        return "Tax Assessment " + assessmentId;
    }

    @Override
    public boolean isActive() {
        return status != null
                && !status.equals("CANCELLED");
    }

    // Validation
    @Override
    public void validate() throws ValidationException {

        if (assessmentId == null || assessmentId.trim().isEmpty()) {
            throw new ValidationException(
                "Assessment ID is required."
            );
        }

        if (declaration == null) {
            throw new ValidationException(
                "A tax declaration must be selected."
            );
        }

        if (declaration.getStatus().equals("REJECTED")) {
            throw new ValidationException(
                "A rejected declaration cannot be assessed."
            );
        }

        if (declaration.getStatus().equals("CANCELLED")) {
            throw new ValidationException(
                "A cancelled declaration cannot be assessed."
            );
        }

        if (assessedTax == null
                || assessedTax.compareTo(BigDecimal.ZERO) < 0
                || assessedTax.scale() > 2
                || assessedTax.compareTo(
                    new BigDecimal("999999999999.99")) > 0) {

            throw new ValidationException(
                "Assessed tax must be a valid non-negative amount "
                + "with at most two decimal places."
            );
        }

        if (adjustmentAmount == null
                || adjustmentAmount.scale() > 2
                || adjustmentAmount.abs().compareTo(
                    new BigDecimal("999999999999.99")) > 0) {

            throw new ValidationException(
                "Adjustment amount must be valid and have at most "
                + "two decimal places."
            );
        }

        if (calculateTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(
                "Assessment total cannot be negative."
            );
        }

        if (assessmentDate == null) {
            throw new ValidationException(
                "Assessment date is required."
            );
        }

        if (assessmentDate.isAfter(LocalDate.now())) {
            throw new ValidationException(
                "Assessment date cannot be in the future."
            );
        }

        if (status == null ||
                !(status.equals("DRAFT")
                || status.equals("CONFIRMED")
                || status.equals("CANCELLED"))) {

            throw new ValidationException(
                "Status must be DRAFT, CONFIRMED or CANCELLED."
            );
        }
    }

    // Validatable methods
    @Override
    public void printDetails() {
        System.out.println("----- TAX ASSESSMENT DETAILS -----");
        System.out.println("Assessment ID: " + assessmentId);
        System.out.println("Declaration ID: " + declaration.getId());
        System.out.println("Assessed Tax: " + assessedTax);
        System.out.println("Adjustment Amount: " + adjustmentAmount);
        System.out.println("Assessment Total: " + calculateTotal());
        System.out.println("Assessment Date: " + assessmentDate);
        System.out.println("Status: " + status);
    }

    @Override
    public String getSummary() {
        return assessmentId + " - Total: "
                + calculateTotal() + " - Status: " + status;
    }
}