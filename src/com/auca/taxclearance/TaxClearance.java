
package com.auca.taxclearance;

import java.time.LocalDate;

import com.auca.abstracts.RegisteredTaxpayer;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Validatable;
import com.auca.revenueofficer.RevenueOfficer;

public class TaxClearance implements Identifiable, Validatable {

    // Six required private fields
    private String clearanceId;
    private RegisteredTaxpayer taxpayer;
    private LocalDate requestDate;
    private LocalDate expiryDate;
    private String status;
    private RevenueOfficer approvedBy;

    // Constructor
    public TaxClearance(String clearanceId,
                        RegisteredTaxpayer taxpayer,
                        LocalDate requestDate,
                        LocalDate expiryDate,
                        String status,
                        RevenueOfficer approvedBy) {

        this.clearanceId = clearanceId;
        this.taxpayer = taxpayer;
        this.requestDate = requestDate;
        this.expiryDate = expiryDate;
        this.status = status;
        this.approvedBy = approvedBy;
    }

    // Getters
    public String getClearanceId() {
        return clearanceId;
    }

    public RegisteredTaxpayer getTaxpayer() {
        return taxpayer;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public RevenueOfficer getApprovedBy() {
        return approvedBy;
    }

    // Approve clearance
    public void approve(RevenueOfficer officer)
            throws ValidationException {

        if (!"PENDING".equals(status)) {
            throw new ValidationException(
                "Only a pending clearance can be approved."
            );
        }

        if (officer == null || !officer.isActive()) {
            throw new ValidationException(
                "An active revenue officer is required."
            );
        }

        if (taxpayer == null || !taxpayer.isActive()) {
            throw new ValidationException(
                "The taxpayer must be active."
            );
        }

        if (expiryDate == null || requestDate == null
                || !expiryDate.isAfter(requestDate)) {
            throw new ValidationException(
                "Expiry date must be after request date."
            );
        }

        approvedBy = officer;
        status = "APPROVED";
    }

    // Identifiable methods
    @Override
    public String getId() {
        return clearanceId;
    }

    @Override
    public String getDisplayName() {
        return "Tax Clearance " + clearanceId;
    }

    @Override
    public boolean isActive() {
        return "APPROVED".equals(status)
                && expiryDate != null
                && !expiryDate.isBefore(LocalDate.now());
    }

    // Validation
    @Override
    public void validate() throws ValidationException {

        if (clearanceId == null || clearanceId.trim().isEmpty()) {
            throw new ValidationException(
                "Clearance ID is required."
            );
        }

        if (taxpayer == null || !taxpayer.isActive()) {
            throw new ValidationException(
                "An active taxpayer must be selected."
            );
        }

        if (requestDate == null || expiryDate == null) {
            throw new ValidationException(
                "Request date and expiry date are required."
            );
        }

        if (requestDate.isAfter(LocalDate.now())) {
            throw new ValidationException(
                "Request date cannot be in the future."
            );
        }

        if (!expiryDate.isAfter(requestDate)) {
            throw new ValidationException(
                "Expiry date must be after request date."
            );
        }

        if (status == null ||
                !(status.equals("PENDING")
                || status.equals("APPROVED")
                || status.equals("REJECTED"))) {

            throw new ValidationException(
                "Status must be PENDING, APPROVED or REJECTED."
            );
        }

        if ("APPROVED".equals(status)) {

            if (approvedBy == null || !approvedBy.isActive()) {
                throw new ValidationException(
                    "An approved clearance must have an active officer."
                );
            }
        }
    }

    // Validatable methods
    @Override
    public void printDetails() {
        System.out.println("----- TAX CLEARANCE DETAILS -----");
        System.out.println("Clearance ID: " + clearanceId);
        System.out.println("Taxpayer: " + taxpayer.getDisplayName());
        System.out.println("Request Date: " + requestDate);
        System.out.println("Expiry Date: " + expiryDate);
        System.out.println("Status: " + status);

        if (approvedBy != null) {
            System.out.println(
                "Approved By: " + approvedBy.getDisplayName()
            );
        } else {
            System.out.println("Approved By: Not yet approved");
        }
    }

    @Override
    public String getSummary() {
        return clearanceId + " - "
                + taxpayer.getDisplayName()
                + " - Status: " + status;
    }
}
