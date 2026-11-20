
package com.auca.revenueofficer;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.auca.abstracts.TaxParty;
import com.auca.exceptions.ValidationException;

public class RevenueOfficer extends TaxParty {

    // Six attributes belonging to RevenueOfficer
    private String staffNumber;
    private String department;
    private String role;
    private LocalDate hireDate;
    private BigDecimal approvalLimit;
    private boolean active;

    // Constructor
    public RevenueOfficer(
            String partyId,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            LocalDate dateOfBirth,
            String staffNumber,
            String department,
            String role,
            LocalDate hireDate,
            BigDecimal approvalLimit,
            boolean active) {

        super(
            partyId,
            firstName,
            lastName,
            email,
            phoneNumber,
            dateOfBirth
        );

        this.staffNumber = staffNumber;
        this.department = department;
        this.role = role;
        this.hireDate = hireDate;
        this.approvalLimit = approvalLimit;
        this.active = active;
    }

    // Getters
    public String getStaffNumber() {
        return staffNumber;
    }

    public String getDepartment() {
        return department;
    }

    public String getRole() {
        return role;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public BigDecimal getApprovalLimit() {
        return approvalLimit;
    }

    public boolean getActive() {
        return active;
    }

    // Setter
    public void setActive(boolean active) {
        this.active = active;
    }

    // Implement isActive()
    @Override
    public boolean isActive() {
        return active;
    }

    // Validation
    @Override
    public void validate() throws ValidationException {

        super.validate();

        if (staffNumber == null ||
                staffNumber.trim().isEmpty()) {

            throw new ValidationException(
                    "Staff number is required.");
        }

        if (department == null ||
                department.trim().length() < 2) {

            throw new ValidationException(
                    "Invalid department.");
        }

        if (role == null ||
                role.trim().length() < 2) {

            throw new ValidationException(
                    "Invalid officer role.");
        }

        if (hireDate == null ||
                hireDate.isAfter(LocalDate.now())) {

            throw new ValidationException(
                    "Invalid hire date.");
        }

        if (approvalLimit == null ||
                approvalLimit.compareTo(BigDecimal.ZERO) < 0) {

            throw new ValidationException(
                    "Approval limit cannot be negative.");
        }

        if (approvalLimit.scale() > 2 ||
                approvalLimit.compareTo(
                    new BigDecimal("999999999999.99")) > 0) {

            throw new ValidationException(
                    "Approval limit must have at most 2 decimal "
                    + "places and cannot exceed 999999999999.99.");
        }
    }

    // Display officer name
    @Override
    public String getDisplayName() {
        return getFirstName() + " " + getLastName()
                + " (Revenue Officer)";
    }

    // Print officer details
    @Override
    public void printDetails() {

        super.printDetails();

        System.out.println("Staff Number: " + staffNumber);
        System.out.println("Department: " + department);
        System.out.println("Role: " + role);
        System.out.println("Hire Date: " + hireDate);
        System.out.println("Approval Limit: " + approvalLimit);
        System.out.println("Active: " + active);
    }

    // Summary
    @Override
    public String getSummary() {

        return super.getSummary()
                + " | Revenue Officer"
                + " | Department: " + department
                + " | Role: " + role;
    }

    // Officer-specific method
    public void displayOfficerRole() {

        System.out.println(
                "Officer " + getDisplayName()
                + " works in " + department
                + " as " + role
        );
    }
}
