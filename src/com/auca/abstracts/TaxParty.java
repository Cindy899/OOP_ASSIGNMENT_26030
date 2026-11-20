package com.auca.abstracts;

import java.time.LocalDate;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Validatable;
import com.auca.exceptions.ValidationException;

public abstract class TaxParty
        implements Identifiable, Validatable {

    private String partyId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;

    public TaxParty(String partyId, String firstName,
                    String lastName, String email,
                    String phoneNumber, LocalDate dateOfBirth) {

        this.partyId = partyId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public String getPartyId() {
        return partyId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public String getId() {
        return partyId;
    }

    @Override
    public String getDisplayName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void validate() throws ValidationException {

        if (partyId == null || partyId.trim().isEmpty()) {
            throw new ValidationException("Party ID is required.");
        }

        if (firstName == null || firstName.trim().length() < 2) {
            throw new ValidationException("Invalid first name.");
        }

        if (lastName == null || lastName.trim().length() < 2) {
            throw new ValidationException("Invalid last name.");
        }

        if (email == null || !email.contains("@")) {
            throw new ValidationException("Invalid email.");
        }

        if (phoneNumber == null ||
                !phoneNumber.matches("\\+?[0-9]{10,15}")) {
            throw new ValidationException("Invalid phone number.");
        }

        if (dateOfBirth == null ||
                !dateOfBirth.isBefore(LocalDate.now())) {
            throw new ValidationException("Invalid date of birth.");
        }
    }

    
    public void printDetails() {
        System.out.println("Party ID: " + partyId);
        System.out.println("Name: " + getDisplayName());
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Date of Birth: " + dateOfBirth);
    }

    @Override
    public String getSummary() {
        return getDisplayName() + " - " + partyId;
    }
}