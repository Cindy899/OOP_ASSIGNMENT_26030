
package com.auca.taxobligation;

import java.time.LocalDate;

import com.auca.abstracts.RegisteredTaxpayer;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Validatable;
import com.auca.taxtype.TaxType;

public class TaxObligation implements Identifiable, Validatable {

    // Six required private fields
    private String obligationId;
    private RegisteredTaxpayer taxpayer;
    private TaxType taxType;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private LocalDate dueDate;

    // Constructor
    public TaxObligation(String obligationId,
                         RegisteredTaxpayer taxpayer,
                         TaxType taxType,
                         LocalDate periodStart,
                         LocalDate periodEnd,
                         LocalDate dueDate) {

        this.obligationId = obligationId;
        this.taxpayer = taxpayer;
        this.taxType = taxType;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.dueDate = dueDate;
    }

    // Getters
    public String getObligationId() {
        return obligationId;
    }

    public RegisteredTaxpayer getTaxpayer() {
        return taxpayer;
    }

    public TaxType getTaxType() {
        return taxType;
    }

    public LocalDate getPeriodStart() {
        return periodStart;
    }

    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    // Identifiable methods
    @Override
    public String getId() {
        return obligationId;
    }

    @Override
    public String getDisplayName() {
        return "Tax Obligation " + obligationId;
    }

    @Override
    public boolean isActive() {
        return taxpayer != null && taxpayer.isActive()
                && taxType != null && taxType.isActive();
    }

    // Validation
    @Override
    public void validate() throws ValidationException {

        if (obligationId == null || obligationId.trim().isEmpty()) {
            throw new ValidationException(
                "Obligation ID is required."
            );
        }

        if (taxpayer == null) {
            throw new ValidationException(
                "A registered taxpayer must be selected."
            );
        }

        if (!taxpayer.isActive()) {
            throw new ValidationException(
                "The selected taxpayer is not active."
            );
        }

        if (taxType == null) {
            throw new ValidationException(
                "A tax type must be selected."
            );
        }

        if (!taxType.isActive()) {
            throw new ValidationException(
                "The selected tax type is not active."
            );
        }

        if (periodStart == null || periodEnd == null || dueDate == null) {
            throw new ValidationException(
                "All obligation dates are required."
            );
        }

        if (periodStart.isAfter(periodEnd)) {
            throw new ValidationException(
                "Period start cannot be after period end."
            );
        }

        if (dueDate.isBefore(periodEnd)) {
            throw new ValidationException(
                "Due date cannot be before the period end."
            );
        }
    }

    // Validatable methods
    @Override
    public void printDetails() {
        System.out.println("----- TAX OBLIGATION DETAILS -----");
        System.out.println("Obligation ID: " + obligationId);
        System.out.println("Taxpayer: " + taxpayer.getDisplayName());
        System.out.println("Tax Type: " + taxType.getTaxName());
        System.out.println("Period Start: " + periodStart);
        System.out.println("Period End: " + periodEnd);
        System.out.println("Due Date: " + dueDate);
    }

    @Override
    public String getSummary() {
        return obligationId + " - "
                + taxpayer.getDisplayName() + " - "
                + taxType.getTaxName();
    }
}