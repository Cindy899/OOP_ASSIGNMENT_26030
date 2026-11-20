
package com.auca.main;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.auca.abstracts.RegisteredTaxpayer;

import com.auca.exceptions.ValidationException;
import com.auca.exceptions.DuplicateRecordException;

import com.auca.individualtaxpayer.IndividualTaxpayer;
import com.auca.corporatetaxpayer.CorporateTaxpayer;
import com.auca.revenueofficer.RevenueOfficer;
import com.auca.taxtype.TaxType;
import com.auca.taxobligation.TaxObligation;
import com.auca.taxdeclaration.TaxDeclaration;
import com.auca.taxassessment.TaxAssessment;
import com.auca.taxpayment.TaxPayment;
import com.auca.penalty.Penalty;
import com.auca.taxrefund.TaxRefund;
import com.auca.taxclearance.TaxClearance;

public class Main {

    // ONE SCANNER FOR THE ENTIRE APPLICATION
    private static final Scanner scanner = new Scanner(System.in);

    // MAXIMUM NUMBER OF RECORDS
    private static final int MAX_RECORDS = 100;

    // ARRAYS
    private static IndividualTaxpayer[] individuals =
            new IndividualTaxpayer[MAX_RECORDS];

    private static CorporateTaxpayer[] companies =
            new CorporateTaxpayer[MAX_RECORDS];

    private static RevenueOfficer[] officers =
            new RevenueOfficer[MAX_RECORDS];

    private static TaxType[] taxTypes =
            new TaxType[MAX_RECORDS];

    private static TaxObligation[] obligations =
            new TaxObligation[MAX_RECORDS];

    private static TaxDeclaration[] declarations =
            new TaxDeclaration[MAX_RECORDS];

    private static TaxAssessment[] assessments =
            new TaxAssessment[MAX_RECORDS];

    private static TaxPayment[] payments =
            new TaxPayment[MAX_RECORDS];

    private static Penalty[] penalties =
            new Penalty[MAX_RECORDS];

    private static TaxRefund[] refunds =
            new TaxRefund[MAX_RECORDS];

    private static TaxClearance[] clearances =
            new TaxClearance[MAX_RECORDS];

    // RECORD COUNTERS
    private static int individualCount = 0;
    private static int companyCount = 0;
    private static int officerCount = 0;
    private static int taxTypeCount = 0;
    private static int obligationCount = 0;
    private static int declarationCount = 0;
    private static int assessmentCount = 0;
    private static int paymentCount = 0;
    private static int penaltyCount = 0;
    private static int refundCount = 0;
    private static int clearanceCount = 0;

    // ==========================================
    // MAIN METHOD
    // ==========================================

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" TAX ADMINISTRATION MANAGEMENT SYSTEM");
        System.out.println("======================================");

        runMenu();

        scanner.close();
    }

    // ==========================================
    // MENU LOOP
    // ==========================================

    private static void runMenu() {

        int choice = -1;

        while (choice != 0) {

            displayMenu();

            System.out.print("Enter your choice: ");

            try {

                choice = Integer.parseInt(scanner.nextLine().trim());

                handleChoice(choice);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input. Please enter a number."
                );
            }

            System.out.println();
        }
    }

    // ==========================================
    // DISPLAY MENU
    // ==========================================

    private static void displayMenu() {

        System.out.println("========== MAIN MENU ==========");

        System.out.println("1. Register Individual Taxpayer");
        System.out.println("2. Register Corporate Taxpayer");
        System.out.println("3. Register Revenue Officer");
        System.out.println("4. Register Tax Type");
        System.out.println("5. Create Tax Obligation");
        System.out.println("6. File Tax Declaration");
        System.out.println("7. Assess Declaration");
        System.out.println("8. Confirm Assessment");
        System.out.println("9. View All Records");
        System.out.println("10. View Declaration History");
        System.out.println("11. View Assessment History");
        System.out.println("12. Record Tax Payment");
        System.out.println("13. Calculate / Record Penalty");
        System.out.println("14. Request / Approve Refund");
        System.out.println("15. Request / Issue Tax Clearance");
        System.out.println("16. Search Record by ID / TIN");
        System.out.println("17. Update Eligible Record");
        System.out.println("18. View Taxpayer Statement");
        System.out.println("19. View Outstanding Balances");
        System.out.println("20. View Tax Reports");
        System.out.println("21. Demonstrate Polymorphism");

        System.out.println("0. Exit");

        System.out.println("===============================");
    }

    // ==========================================
    // HANDLE MENU CHOICE
    // ==========================================

    private static void handleChoice(int choice) {

        switch (choice) {

            case 1:
                registerIndividualTaxpayer();
                break;

            case 2:
                registerCorporateTaxpayer();
                break;

            case 3:
                registerRevenueOfficer();
                break;

            case 4:
                registerTaxType();
                break;

            case 5:
                createTaxObligation();
                break;

            case 6:
                System.out.println("File Tax Declaration");
                break;

            case 7:
                System.out.println("Assess Declaration");
                break;

            case 8:
                System.out.println("Confirm Assessment");
                break;

            case 9:
                System.out.println("View All Records");
                break;

            case 10:
                System.out.println("View Declaration History");
                break;

            case 11:
                System.out.println("View Assessment History");
                break;

            case 12:
                System.out.println("Record Tax Payment");
                break;

            case 13:
                System.out.println("Calculate / Record Penalty");
                break;

            case 14:
                System.out.println("Request / Approve Refund");
                break;

            case 15:
                System.out.println("Request / Issue Tax Clearance");
                break;

            case 16:
                System.out.println("Search Record by ID / TIN");
                break;

            case 17:
                System.out.println("Update Eligible Record");
                break;

            case 18:
                System.out.println("View Taxpayer Statement");
                break;

            case 19:
                System.out.println("View Outstanding Balances");
                break;

            case 20:
                System.out.println("View Tax Reports");
                break;

            case 21:
                System.out.println("Demonstrate Polymorphism");
                break;

            case 0:
                System.out.println(
                        "Thank you for using the Tax Administration System."
                );
                break;

            default:
                System.out.println(
                        "Invalid choice. Please select an option from 0 to 21."
                );
        }
    }

    // ==========================================
    // REGISTER INDIVIDUAL TAXPAYER
    // ==========================================

    private static void registerIndividualTaxpayer() {

        System.out.println("\n===== REGISTER INDIVIDUAL TAXPAYER =====");

        try {

            System.out.print("Enter Party ID: ");
            String partyId = scanner.nextLine().trim();

            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine().trim();

            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine().trim();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter Phone Number: ");
            String phoneNumber = scanner.nextLine().trim();

            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
            LocalDate dateOfBirth =
                    LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Enter TIN (9 digits): ");
            String tin = scanner.nextLine().trim();

            System.out.print("Enter Registration Date (YYYY-MM-DD): ");
            LocalDate registrationDate =
                    LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Enter Status (ACTIVE/INACTIVE/SUSPENDED): ");
            String taxpayerStatus =
                    scanner.nextLine().trim().toUpperCase();

            System.out.print("Enter District: ");
            String district = scanner.nextLine().trim();

            System.out.print("Enter Sector: ");
            String sector = scanner.nextLine().trim();

            System.out.print("Enter Tax Office: ");
            String taxOffice = scanner.nextLine().trim();

            System.out.print("Enter National ID (16 digits): ");
            String nationalId = scanner.nextLine().trim();

            System.out.print("Enter Occupation: ");
            String occupation = scanner.nextLine().trim();

            System.out.print("Enter Business Name: ");
            String businessName = scanner.nextLine().trim();

            System.out.print("Enter Business Start Date (YYYY-MM-DD): ");
            LocalDate businessStartDate =
                    LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Enter Annual Turnover: ");
            BigDecimal annualTurnover =
                    new BigDecimal(scanner.nextLine().trim());

            System.out.print("Is the taxpayer a resident? (yes/no): ");
            String residentInput =
                    scanner.nextLine().trim().toLowerCase();

            if (!residentInput.equals("yes")
                    && !residentInput.equals("no")) {

                throw new ValidationException(
                        "Please enter yes or no for residency."
                );
            }

            boolean resident = residentInput.equals("yes");

            if (individualCount >= MAX_RECORDS) {

                throw new ValidationException(
                        "Individual taxpayer storage is full."
                );
            }

            for (int i = 0; i < individualCount; i++) {

                if (individuals[i].getTin().equals(tin)) {

                    throw new DuplicateRecordException(
                            "This TIN already belongs to an individual taxpayer."
                    );
                }
            }

            for (int i = 0; i < companyCount; i++) {

                if (companies[i].getTin().equals(tin)) {

                    throw new DuplicateRecordException(
                            "This TIN already belongs to a corporate taxpayer."
                    );
                }
            }

            for (int i = 0; i < individualCount; i++) {

                if (individuals[i].getNationalId().equals(nationalId)) {

                    throw new DuplicateRecordException(
                            "This National ID is already registered."
                    );
                }
            }

            IndividualTaxpayer taxpayer = new IndividualTaxpayer(
                    partyId,
                    firstName,
                    lastName,
                    email,
                    phoneNumber,
                    dateOfBirth,
                    tin,
                    registrationDate,
                    taxpayerStatus,
                    district,
                    sector,
                    taxOffice,
                    nationalId,
                    occupation,
                    businessName,
                    businessStartDate,
                    annualTurnover,
                    resident
            );

            taxpayer.validate();

            individuals[individualCount] = taxpayer;
            individualCount++;

            System.out.println("\nTaxpayer registered successfully!");

            taxpayer.printDetails();

        } catch (DateTimeParseException e) {

            System.out.println("Error: Invalid date. Use YYYY-MM-DD.");

        } catch (NumberFormatException e) {

            System.out.println(
                    "Error: Please enter a valid number for annual turnover."
            );

        } catch (DuplicateRecordException e) {

            System.out.println("Duplicate Error: " + e.getMessage());

        } catch (ValidationException e) {

            System.out.println("Validation Error: " + e.getMessage());
        }
    }

    // ==========================================
    // REGISTER CORPORATE TAXPAYER
    // ==========================================

    private static void registerCorporateTaxpayer() {

        System.out.println("\n===== REGISTER CORPORATE TAXPAYER =====");

        try {

            System.out.print("Enter Party ID: ");
            String partyId = scanner.nextLine().trim();

            System.out.print("Enter First Name of Representative: ");
            String firstName = scanner.nextLine().trim();

            System.out.print("Enter Last Name of Representative: ");
            String lastName = scanner.nextLine().trim();

            System.out.print("Enter Representative Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter Representative Phone Number: ");
            String phoneNumber = scanner.nextLine().trim();

            System.out.print("Enter Representative Date of Birth (YYYY-MM-DD): ");
            LocalDate dateOfBirth =
                    LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Enter Company TIN (9 digits): ");
            String tin = scanner.nextLine().trim();

            System.out.print("Enter Registration Date (YYYY-MM-DD): ");
            LocalDate registrationDate =
                    LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Enter Status (ACTIVE/INACTIVE/SUSPENDED): ");
            String taxpayerStatus =
                    scanner.nextLine().trim().toUpperCase();

            System.out.print("Enter District: ");
            String district = scanner.nextLine().trim();

            System.out.print("Enter Sector: ");
            String sector = scanner.nextLine().trim();

            System.out.print("Enter Tax Office: ");
            String taxOffice = scanner.nextLine().trim();

            System.out.print("Enter Company Registration Number: ");
            String companyRegistrationNo =
                    scanner.nextLine().trim();

            System.out.print("Enter Legal Company Name: ");
            String legalName = scanner.nextLine().trim();

            System.out.print("Enter Incorporation Date (YYYY-MM-DD): ");
            LocalDate incorporationDate =
                    LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Enter Business Sector: ");
            String businessSector = scanner.nextLine().trim();

            System.out.print("Enter Authorized Representative: ");
            String authorizedRepresentative =
                    scanner.nextLine().trim();

            System.out.print("Enter Annual Turnover: ");
            BigDecimal annualTurnover =
                    new BigDecimal(scanner.nextLine().trim());

            if (companyCount >= MAX_RECORDS) {

                throw new ValidationException(
                        "Corporate taxpayer storage is full."
                );
            }

            for (int i = 0; i < individualCount; i++) {

                if (individuals[i].getTin().equals(tin)) {

                    throw new DuplicateRecordException(
                            "This TIN already belongs to an individual taxpayer."
                    );
                }
            }

            for (int i = 0; i < companyCount; i++) {

                if (companies[i].getTin().equals(tin)) {

                    throw new DuplicateRecordException(
                            "This TIN is already registered."
                    );
                }

                if (companies[i].getCompanyRegistrationNo()
                        .equals(companyRegistrationNo)) {

                    throw new DuplicateRecordException(
                            "This company registration number already exists."
                    );
                }
            }

            CorporateTaxpayer company = new CorporateTaxpayer(
                    partyId,
                    firstName,
                    lastName,
                    email,
                    phoneNumber,
                    dateOfBirth,
                    tin,
                    registrationDate,
                    taxpayerStatus,
                    district,
                    sector,
                    taxOffice,
                    companyRegistrationNo,
                    legalName,
                    incorporationDate,
                    businessSector,
                    authorizedRepresentative,
                    annualTurnover
            );

            company.validate();

            companies[companyCount] = company;
            companyCount++;

            System.out.println(
                    "\nCorporate taxpayer registered successfully!"
            );

            company.printDetails();

        } catch (DateTimeParseException e) {

            System.out.println("Error: Invalid date. Use YYYY-MM-DD.");

        } catch (NumberFormatException e) {

            System.out.println(
                    "Error: Enter a valid number for annual turnover."
            );

        } catch (DuplicateRecordException e) {

            System.out.println("Duplicate Error: " + e.getMessage());

        } catch (ValidationException e) {

            System.out.println("Validation Error: " + e.getMessage());
        }
    }

    // ==========================================
    // REGISTER REVENUE OFFICER
    // ==========================================

    private static void registerRevenueOfficer() {

        System.out.println("\n===== REGISTER REVENUE OFFICER =====");

        try {

            if (officerCount >= MAX_RECORDS) {

                throw new ValidationException(
                        "Officer storage is full."
                );
            }

            System.out.print("Enter Party ID: ");
            String partyId = scanner.nextLine().trim();

            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine().trim();

            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine().trim();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter Phone Number: ");
            String phoneNumber = scanner.nextLine().trim();

            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
            LocalDate dateOfBirth =
                    LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Enter Staff Number: ");
            String staffNumber = scanner.nextLine().trim();

            for (int i = 0; i < officerCount; i++) {

                if (officers[i].getStaffNumber()
                        .equalsIgnoreCase(staffNumber)) {

                    throw new DuplicateRecordException(
                            "This staff number is already registered."
                    );
                }
            }

            System.out.print("Enter Department: ");
            String department = scanner.nextLine().trim();

            System.out.print("Enter Role: ");
            String role = scanner.nextLine().trim();

            System.out.print("Enter Hire Date (YYYY-MM-DD): ");
            LocalDate hireDate =
                    LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Enter Approval Limit: ");
            BigDecimal approvalLimit =
                    new BigDecimal(scanner.nextLine().trim());

            System.out.print("Is the officer active? (YES/NO): ");
            String activeInput =
                    scanner.nextLine().trim();

            boolean active;

            if (activeInput.equalsIgnoreCase("YES")) {

                active = true;

            } else if (activeInput.equalsIgnoreCase("NO")) {

                active = false;

            } else {

                throw new ValidationException(
                        "Please enter YES or NO."
                );
            }

            RevenueOfficer officer = new RevenueOfficer(
                    partyId,
                    firstName,
                    lastName,
                    email,
                    phoneNumber,
                    dateOfBirth,
                    staffNumber,
                    department,
                    role,
                    hireDate,
                    approvalLimit,
                    active
            );

            officer.validate();

            officers[officerCount] = officer;
            officerCount++;

            System.out.println(
                    "\nRevenue officer registered successfully!"
            );

            officer.printDetails();

        } catch (DateTimeParseException e) {

            System.out.println(
                    "Error: Invalid date. Use YYYY-MM-DD."
            );

        } catch (NumberFormatException e) {

            System.out.println(
                    "Error: Enter a valid numeric approval limit."
            );

        } catch (DuplicateRecordException e) {

            System.out.println(
                    "Duplicate Error: " + e.getMessage()
            );

        } catch (ValidationException e) {

            System.out.println(
                    "Validation Error: " + e.getMessage()
            );
        }
    }

    // ==========================================
    // REGISTER TAX TYPE
    // ==========================================

    private static void registerTaxType() {

        System.out.println("\n===== REGISTER TAX TYPE =====");

        try {

            if (taxTypeCount >= MAX_RECORDS) {

                throw new ValidationException(
                        "Tax Type storage is full."
                );
            }

            System.out.print("Enter Tax Type ID: ");
            String taxTypeId = scanner.nextLine().trim();

            System.out.print("Enter Tax Code (e.g. VAT): ");
            String taxCode = scanner.nextLine().trim();

            System.out.print("Enter Tax Name: ");
            String taxName = scanner.nextLine().trim();

            System.out.print("Enter Tax Rate (0-100): ");
            BigDecimal rate =
                    new BigDecimal(scanner.nextLine().trim());

            System.out.println("\nFiling Frequency Options:");
            System.out.println("1. MONTHLY");
            System.out.println("2. QUARTERLY");
            System.out.println("3. ANNUAL");

            System.out.print("Enter Filing Frequency: ");
            String filingFrequency =
                    scanner.nextLine().trim().toUpperCase();

            System.out.print("Is this Tax Type active? (YES/NO): ");
            String activeInput =
                    scanner.nextLine().trim().toUpperCase();

            boolean active;

            if (activeInput.equals("YES")) {

                active = true;

            } else if (activeInput.equals("NO")) {

                active = false;

            } else {

                throw new ValidationException(
                        "Please enter YES or NO."
                );
            }

            for (int i = 0; i < taxTypeCount; i++) {

                if (taxTypes[i].getTaxTypeId()
                        .equalsIgnoreCase(taxTypeId)) {

                    throw new DuplicateRecordException(
                            "This Tax Type ID already exists."
                    );
                }

                if (taxTypes[i].getTaxCode()
                        .equalsIgnoreCase(taxCode)) {

                    throw new DuplicateRecordException(
                            "This Tax Code already exists."
                    );
                }
            }

            TaxType taxType = new TaxType(
                    taxTypeId,
                    taxCode,
                    taxName,
                    rate,
                    filingFrequency,
                    active
            );

            taxType.validate();

            taxTypes[taxTypeCount] = taxType;
            taxTypeCount++;

            System.out.println(
                    "\nTax Type registered successfully!"
            );

            taxType.printDetails();

        } catch (NumberFormatException e) {

            System.out.println(
                    "Error: Please enter a valid number for the tax rate."
            );

        } catch (DuplicateRecordException e) {

            System.out.println(
                    "Duplicate Error: " + e.getMessage()
            );

        } catch (ValidationException e) {

            System.out.println(
                    "Validation Error: " + e.getMessage()
            );
        }
    }

    // ==========================================
    // CREATE TAX OBLIGATION
    // ==========================================

    private static void createTaxObligation() {

        System.out.println("\n===== CREATE TAX OBLIGATION =====");

        try {

            // STEP 1: CHECK STORAGE CAPACITY

            if (obligationCount >= MAX_RECORDS) {

                throw new ValidationException(
                        "Tax Obligation storage is full."
                );
            }

            // STEP 2: ENTER OBLIGATION ID

            System.out.print("Enter Obligation ID: ");
            String obligationId = scanner.nextLine().trim();

            // STEP 3: CHECK DUPLICATE OBLIGATION ID

            for (int i = 0; i < obligationCount; i++) {

                if (obligations[i].getObligationId()
                        .equalsIgnoreCase(obligationId)) {

                    throw new DuplicateRecordException(
                            "This Obligation ID already exists."
                    );
                }
            }

            // STEP 4: DISPLAY AVAILABLE TAXPAYERS

            System.out.println("\nAvailable Taxpayers:");

            for (int i = 0; i < individualCount; i++) {

                System.out.println(
                        "Individual TIN: "
                        + individuals[i].getTin()
                        + " | "
                        + individuals[i].getDisplayName()
                );
            }

            for (int i = 0; i < companyCount; i++) {

                System.out.println(
                        "Corporate TIN: "
                        + companies[i].getTin()
                        + " | "
                        + companies[i].getDisplayName()
                );
            }

            // STEP 5: SELECT TAXPAYER

            System.out.print("\nEnter Taxpayer TIN: ");
            String tin = scanner.nextLine().trim();

            RegisteredTaxpayer selectedTaxpayer = null;

            for (int i = 0; i < individualCount; i++) {

                if (individuals[i].getTin().equals(tin)) {

                    selectedTaxpayer = individuals[i];
                    break;
                }
            }

            if (selectedTaxpayer == null) {

                for (int i = 0; i < companyCount; i++) {

                    if (companies[i].getTin().equals(tin)) {

                        selectedTaxpayer = companies[i];
                        break;
                    }
                }
            }

            if (selectedTaxpayer == null) {

                throw new ValidationException(
                        "No taxpayer found with this TIN."
                );
            }

            // STEP 6: DISPLAY AVAILABLE TAX TYPES

            System.out.println("\nAvailable Tax Types:");

            for (int i = 0; i < taxTypeCount; i++) {

                System.out.println(
                        "Tax Type ID: "
                        + taxTypes[i].getTaxTypeId()
                        + " | Name: "
                        + taxTypes[i].getTaxName()
                        + " | Rate: "
                        + taxTypes[i].getRate()
                        + "%"
                );
            }

            // STEP 7: SELECT TAX TYPE

            System.out.print("\nEnter Tax Type ID: ");
            String taxTypeId = scanner.nextLine().trim();

            TaxType selectedTaxType = null;

            for (int i = 0; i < taxTypeCount; i++) {

                if (taxTypes[i].getTaxTypeId()
                        .equalsIgnoreCase(taxTypeId)) {

                    selectedTaxType = taxTypes[i];
                    break;
                }
            }

            if (selectedTaxType == null) {

                throw new ValidationException(
                        "No Tax Type found with this ID."
                );
            }

            // STEP 8: ENTER OBLIGATION DATES

            System.out.print("Enter Period Start Date (YYYY-MM-DD): ");
            LocalDate periodStart =
                    LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Enter Period End Date (YYYY-MM-DD): ");
            LocalDate periodEnd =
                    LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Enter Due Date (YYYY-MM-DD): ");
            LocalDate dueDate =
                    LocalDate.parse(scanner.nextLine().trim());

            // STEP 9: CREATE TAX OBLIGATION OBJECT

            TaxObligation obligation = new TaxObligation(
                    obligationId,
                    selectedTaxpayer,
                    selectedTaxType,
                    periodStart,
                    periodEnd,
                    dueDate
            );

            // STEP 10: VALIDATE THE OBLIGATION

            obligation.validate();

            // STEP 11: SAVE THE OBLIGATION

            obligations[obligationCount] = obligation;
            obligationCount++;

            // STEP 12: DISPLAY SUCCESS MESSAGE

            System.out.println(
                    "\nTax Obligation created successfully!"
            );

            obligation.printDetails();

        } catch (DateTimeParseException e) {

            System.out.println(
                    "Error: Invalid date. Use YYYY-MM-DD."
            );

        } catch (DuplicateRecordException e) {

            System.out.println(
                    "Duplicate Error: " + e.getMessage()
            );

        } catch (ValidationException e) {

            System.out.println(
                    "Validation Error: " + e.getMessage()
            );
        }
    }

}
