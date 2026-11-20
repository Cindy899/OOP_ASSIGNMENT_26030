
package com.auca.taxpayment;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Validatable;
import com.auca.taxassessment.TaxAssessment;

public class TaxPayment implements Identifiable, Validatable {

    // Six required private fields
    private String paymentId;
    private TaxAssessment assessment;
    private String paymentReference;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentMethod;

    // Constructor
    public TaxPayment(String paymentId,
                      TaxAssessment assessment,
                      String paymentReference,
                      BigDecimal amount,
                      LocalDate paymentDate,
                      String paymentMethod) {

        this.paymentId = paymentId;
        this.assessment = assessment;
        this.paymentReference = paymentReference;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }

    // Getters
    public String getPaymentId() {
        return paymentId;
    }

    public TaxAssessment getAssessment() {
        return assessment;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    // Identifiable methods
    @Override
    public String getId() {
        return paymentId;
    }

    @Override
    public String getDisplayName() {
        return "Tax Payment " + paymentId;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    // Validation
    @Override
    public void validate() throws ValidationException {

        if (paymentId == null || paymentId.trim().isEmpty()) {
            throw new ValidationException(
                "Payment ID is required."
            );
        }

        if (assessment == null) {
            throw new ValidationException(
                "An assessment must be selected."
            );
        }

        if (!"CONFIRMED".equals(assessment.getStatus())) {
            throw new ValidationException(
                "Payment is only allowed against a CONFIRMED assessment."
            );
        }

        if (paymentReference == null
                || paymentReference.trim().isEmpty()) {
            throw new ValidationException(
                "Payment reference is required."
            );
        }

        if (amount == null
                || amount.compareTo(BigDecimal.ZERO) <= 0
                || amount.scale() > 2
                || amount.compareTo(
                    new BigDecimal("999999999999.99")) > 0) {

            throw new ValidationException(
                "Payment amount must be greater than zero, "
                + "have at most two decimal places, and not exceed "
                + "999999999999.99."
            );
        }

        if (paymentDate == null) {
            throw new ValidationException(
                "Payment date is required."
            );
        }

        if (paymentDate.isAfter(LocalDate.now())) {
            throw new ValidationException(
                "Payment date cannot be in the future."
            );
        }

        if (paymentMethod == null
                || paymentMethod.trim().isEmpty()) {
            throw new ValidationException(
                "Payment method is required."
            );
        }
    }

    // Validatable methods
    @Override
    public void printDetails() {
        System.out.println("----- TAX PAYMENT DETAILS -----");
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Assessment ID: " + assessment.getId());
        System.out.println("Payment Reference: " + paymentReference);
        System.out.println("Amount: " + amount);
        System.out.println("Payment Date: " + paymentDate);
        System.out.println("Payment Method: " + paymentMethod);
    }

    @Override
    public String getSummary() {
        return paymentId + " - Reference: "
                + paymentReference + " - Amount: " + amount;
    }
}
