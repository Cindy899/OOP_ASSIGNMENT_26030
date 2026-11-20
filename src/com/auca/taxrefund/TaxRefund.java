
package com.auca.taxrefund;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.auca.abstracts.RegisteredTaxpayer;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Validatable;
import com.auca.revenueofficer.RevenueOfficer;
import com.auca.taxassessment.TaxAssessment;

public class TaxRefund implements Identifiable, Validatable {

    // Six required private fields
    private String refundId;
    private RegisteredTaxpayer taxpayer;
    private TaxAssessment assessment;
    private BigDecimal requestedAmount;
    private LocalDate requestDate;
    private String status;

    // Constructor
    public TaxRefund(String refundId,
                     RegisteredTaxpayer taxpayer,
                     TaxAssessment assessment,
                     BigDecimal requestedAmount,
                     LocalDate requestDate,
                     String status) {

        this.refundId = refundId;
        this.taxpayer = taxpayer;
        this.assessment = assessment;
        this.requestedAmount = requestedAmount;
        this.requestDate = requestDate;
        this.status = status;
    }

    // Getters
    public String getRefundId() {
        return refundId;
    }

    public RegisteredTaxpayer getTaxpayer() {
        return taxpayer;
    }

    public TaxAssessment getAssessment() {
        return assessment;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public String getStatus() {
        return status;
    }

    // Setter for status
    public void setStatus(String status) {
        this.status = status;
    }

    // Approve a refund
    public void approve(RevenueOfficer officer,
                        BigDecimal verifiedOverpayment)
            throws ValidationException {

        if (officer == null || !officer.isActive()) {
            throw new ValidationException(
                "Only an active revenue officer can approve a refund."
            );
        }

        if (verifiedOverpayment == null
                || verifiedOverpayment.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException(
                "A verified overpayment is required."
            );
        }

        if (requestedAmount.compareTo(verifiedOverpayment) > 0) {
            throw new ValidationException(
                "Refund amount cannot exceed the verified overpayment."
            );
        }

        if (requestedAmount.compareTo(officer.getApprovalLimit()) > 0) {
            throw new ValidationException(
                "Officer approval limit is too low for this refund."
            );
        }

        if (!"PENDING".equals(status)) {
            throw new ValidationException(
                "Only a pending refund can be approved."
            );
        }

        status = "APPROVED";
    }

    // Identifiable methods
    @Override
    public String getId() {
        return refundId;
    }

    @Override
    public String getDisplayName() {
        return "Tax Refund " + refundId;
    }

    @Override
    public boolean isActive() {
        return "PENDING".equals(status);
    }

    // Validation
    @Override
    public void validate() throws ValidationException {

        if (refundId == null || refundId.trim().isEmpty()) {
            throw new ValidationException(
                "Refund ID is required."
            );
        }

        if (taxpayer == null || !taxpayer.isActive()) {
            throw new ValidationException(
                "An active taxpayer must be selected."
            );
        }

        if (assessment == null) {
            throw new ValidationException(
                "An assessment must be selected."
            );
        }

        if (assessment.getDeclaration() == null
                || assessment.getDeclaration().getObligation() == null
                || assessment.getDeclaration().getObligation()
                    .getTaxpayer() != taxpayer) {

            throw new ValidationException(
                "The assessment must belong to the selected taxpayer."
            );
        }

        if (requestedAmount == null
                || requestedAmount.compareTo(BigDecimal.ZERO) <= 0
                || requestedAmount.scale() > 2
                || requestedAmount.compareTo(
                    new BigDecimal("999999999999.99")) > 0) {

            throw new ValidationException(
                "Refund amount must be positive and have at most "
                + "two decimal places."
            );
        }

        if (requestDate == null) {
            throw new ValidationException(
                "Refund request date is required."
            );
        }

        if (requestDate.isAfter(LocalDate.now())) {
            throw new ValidationException(
                "Refund request date cannot be in the future."
            );
        }

        if (status == null ||
                !(status.equals("PENDING")
                || status.equals("APPROVED")
                || status.equals("REJECTED"))) {

            throw new ValidationException(
                "Refund status must be PENDING, APPROVED or REJECTED."
            );
        }
    }

    // Validatable methods
    @Override
    public void printDetails() {
        System.out.println("----- TAX REFUND DETAILS -----");
        System.out.println("Refund ID: " + refundId);
        System.out.println("Taxpayer: " + taxpayer.getDisplayName());
        System.out.println("Assessment ID: " + assessment.getId());
        System.out.println("Requested Amount: " + requestedAmount);
        System.out.println("Request Date: " + requestDate);
        System.out.println("Status: " + status);
    }

    @Override
    public String getSummary() {
        return refundId + " - Amount: "
                + requestedAmount + " - Status: " + status;
    }
}
