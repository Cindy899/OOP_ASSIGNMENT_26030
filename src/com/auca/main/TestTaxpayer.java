package com.auca.main;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.auca.individualtaxpayer.IndividualTaxpayer;
import com.auca.corporatetaxpayer.CorporateTaxpayer;
import com.auca.abstracts.TaxParty;
import com.auca.exceptions.ValidationException;
import com.auca.revenueofficer.RevenueOfficer;

public class TestTaxpayer {

    public static void main(String[] args) {

        try {

            // Create an individual taxpayer
            IndividualTaxpayer person =
                new IndividualTaxpayer(
                    "P001",
                    "Cindy",
                    "Akesse",
                    "cindy@gmail.com",
                    "+250788123456",
                    LocalDate.of(1999, 9, 8),
                    "123456789",
                    LocalDate.of(2025, 1, 10),
                    "ACTIVE",
                    "Gasabo",
                    "Kimironko",
                    "Kigali Tax Office",
                    "1234567890123456",
                    "Software Student",
                    "Cindy Business",
                    LocalDate.of(2024, 1, 1),
                    new BigDecimal("2500000.00"),
                    true
                );

            // Validate individual
            person.validate();

            System.out.println("INDIVIDUAL TAXPAYER");
            person.printDetails();

            System.out.println("\n--------------------");

            // Create a corporate taxpayer
            CorporateTaxpayer company =
                new CorporateTaxpayer(
                    "P002",
                    "John",
                    "Smith",
                    "john@company.com",
                    "+250788123457",
                    LocalDate.of(1985, 5, 10),
                    "987654321",
                    LocalDate.of(2025, 2, 10),
                    "ACTIVE",
                    "Gasabo",
                    "Remera",
                    "Kigali Tax Office",
                    "COMP-12345",
                    "ABC Technologies Ltd",
                    LocalDate.of(2020, 1, 1),
                    "Information Technology",
                    "John Smith",
                    new BigDecimal("50000000.00")
                );

            company.validate();

            System.out.println("CORPORATE TAXPAYER");
            company.printDetails();

            // Demonstrate inheritance and polymorphism
            TaxParty[] taxpayers = {person, company};

            System.out.println("\nPOLYMORPHISM");

            for (TaxParty taxpayer : taxpayers) {
                System.out.println(taxpayer.getDisplayName());
                System.out.println(taxpayer.getSummary());
            }

            // Create a revenue officer
            RevenueOfficer officer = new RevenueOfficer(
                "P003",
                "Michael",
                "Brown",
                "michael@gmail.com",
                "+250788123458",
                LocalDate.of(1985, 5, 10),
                "OFF001",
                "Tax Administration",
                "Senior Tax Officer",
                LocalDate.of(2020, 1, 15),
                new BigDecimal("500000.00"),
                true
            );

            // Validate officer
            officer.validate();

            System.out.println("\nREVENUE OFFICER");
            officer.printDetails();

        } catch (ValidationException e) {

            System.out.println("Validation Error: " + e.getMessage());
        }
    }
}