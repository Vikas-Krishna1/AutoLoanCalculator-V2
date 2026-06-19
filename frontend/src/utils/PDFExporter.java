package utils;

import models.LoanApplication;
import java.util.List;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;

import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PDFExporter
{
    public static void exportApplication(
            LoanApplication application,
            String fileName)
    {
        try
        {
            Document document =
                    new Document();

            PdfWriter.getInstance(
                    document,
                    new FileOutputStream(fileName));

            document.open();

            Font titleFont =
                    new Font(
                            Font.HELVETICA,
                            18,
                            Font.BOLD);

            Font sectionFont =
                    new Font(
                            Font.HELVETICA,
                            14,
                            Font.BOLD);

            Paragraph title =
                    new Paragraph(
                            "AUTO LOAN APPLICATION REPORT",
                            titleFont);

            title.setAlignment(
                    Element.ALIGN_CENTER);

            document.add(title);

            document.add(new Paragraph(" "));

            document.add(
                    new Paragraph(
                            "Generated: "
                                    + LocalDateTime.now().format(
                                    DateTimeFormatter.ofPattern(
                                            "MM/dd/yyyy HH:mm:ss"))));

            document.add(new Paragraph(" "));

            // =====================================
            // SUMMARY
            // =====================================

            PdfPTable summaryTable =
                    new PdfPTable(2);

            summaryTable.setWidthPercentage(100);

            summaryTable.addCell(
                    headerCell("Application ID"));

            summaryTable.addCell(
                    valueCell(
                            String.valueOf(
                                    application.getApplicationId())));

            summaryTable.addCell(
                    headerCell("Status"));

            summaryTable.addCell(
                    valueCell(
                            application.getStatus()));

            document.add(summaryTable);

            document.add(new Paragraph(" "));

            // =====================================
            // APPLICANT INFORMATION
            // =====================================

            document.add(
                    new Paragraph(
                            "Applicant Information",
                            sectionFont));

            PdfPTable applicantTable =
                    new PdfPTable(2);

            applicantTable.setWidthPercentage(100);

            applicantTable.addCell(
                    headerCell("Name"));

            applicantTable.addCell(
                    valueCell(
                            application
                                    .getApplicant()
                                    .getFullName()));

            applicantTable.addCell(
                    headerCell("Email"));

            applicantTable.addCell(
                    valueCell(
                            application
                                    .getApplicant()
                                    .getEmail()));

            applicantTable.addCell(
                    headerCell("Phone"));

            applicantTable.addCell(
                    valueCell(
                            application
                                    .getApplicant()
                                    .getPhone()));

            applicantTable.addCell(
                    headerCell("Address"));

            applicantTable.addCell(
                    valueCell(
                            application
                                    .getApplicant()
                                    .getAddress()));

            document.add(applicantTable);

            document.add(new Paragraph(" "));

            // =====================================
            // VEHICLE INFORMATION
            // =====================================

            document.add(
                    new Paragraph(
                            "Vehicle Information",
                            sectionFont));

            PdfPTable vehicleTable =
                    new PdfPTable(2);

            vehicleTable.setWidthPercentage(100);

            vehicleTable.addCell(
                    headerCell("Year"));

            vehicleTable.addCell(
                    valueCell(
                            String.valueOf(
                                    application
                                            .getVehicle()
                                            .getYear())));

            vehicleTable.addCell(
                    headerCell("Make"));

            vehicleTable.addCell(
                    valueCell(
                            application
                                    .getVehicle()
                                    .getMake()));

            vehicleTable.addCell(
                    headerCell("Model"));

            vehicleTable.addCell(
                    valueCell(
                            application
                                    .getVehicle()
                                    .getModel()));

            document.add(vehicleTable);

            document.add(new Paragraph(" "));

            // =====================================
            // LOAN INFORMATION
            // =====================================

            document.add(
                    new Paragraph(
                            "Loan Information",
                            sectionFont));

            PdfPTable loanTable =
                    new PdfPTable(2);

            loanTable.setWidthPercentage(100);

            double financedAmount =
                    application.getLoan().getAutoPrice()
                            - application.getLoan().getDownPayment()
                            - application.getLoan().getCashIncentive();

            loanTable.addCell(
                    headerCell("Auto Price"));

            loanTable.addCell(
                    valueCell(
                            formatMoney(
                                    application.getLoan().getAutoPrice())));

            loanTable.addCell(
                    headerCell("Down Payment"));

            loanTable.addCell(
                    valueCell(
                            formatMoney(
                                    application.getLoan().getDownPayment())));

            loanTable.addCell(
                    headerCell("Amount Financed"));

            loanTable.addCell(
                    valueCell(
                            formatMoney(
                                    financedAmount)));

            loanTable.addCell(
                    headerCell("Interest Rate"));

            loanTable.addCell(
                    valueCell(
                            application
                                    .getLoan()
                                    .getInterestRate()
                                    + "%"));

            loanTable.addCell(
                    headerCell("Loan Term"));

            loanTable.addCell(
                    valueCell(
                            application
                                    .getLoan()
                                    .getLoanTerm()
                                    + " Months"));

            loanTable.addCell(
                    headerCell("Sales Tax"));

            loanTable.addCell(
                    valueCell(
                            application
                                    .getLoan()
                                    .getSalesTax()
                                    + "%"));

            loanTable.addCell(
                    headerCell("Fees"));

            loanTable.addCell(
                    valueCell(
                            formatMoney(
                                    application.getLoan().getFees())));

            loanTable.addCell(
                    headerCell("Cash Incentive"));

            loanTable.addCell(
                    valueCell(
                            formatMoney(
                                    application.getLoan().getCashIncentive())));

            document.add(loanTable);

            document.add(new Paragraph(" "));

            // =====================================
            // OFFICER REVIEW
            // =====================================

            document.add(
                    new Paragraph(
                            "Officer Review",
                            sectionFont));

            PdfPTable reviewTable =
                    new PdfPTable(2);

            reviewTable.setWidthPercentage(100);

            reviewTable.addCell(
                    headerCell("Status"));

            reviewTable.addCell(
                    valueCell(
                            application.getStatus()));

            reviewTable.addCell(
                    headerCell("Officer Notes"));

            reviewTable.addCell(
                    valueCell(
                            application.getReviewNotes() == null
                                    || application.getReviewNotes().isBlank()
                                    ? "No notes available"
                                    : application.getReviewNotes()));

            document.add(reviewTable);

            document.close();

            JOptionPane.showMessageDialog(
                    null,
                    "PDF exported successfully.");
        }
        catch(Exception e)
        {
            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Failed to export PDF:\n"
                            + e.getMessage());
        }
    }

    private static String formatMoney(
            double amount)
    {
        return String.format(
                "$%,.2f",
                amount);
    }

    private static PdfPCell headerCell(
            String text)
    {
        Font bold =
                new Font(
                        Font.HELVETICA,
                        11,
                        Font.BOLD);

        return new PdfPCell(
                new Phrase(
                        text,
                        bold));
    }

    private static PdfPCell valueCell(
            String text)
    {
        return new PdfPCell(
                new Phrase(text));
    }
//Export All Loan Applications
public static void exportAllApplications(
        List<LoanApplication> applications,
        String fileName)
{
    try
    {
        Document document =
                new Document();

        PdfWriter.getInstance(
                document,
                new FileOutputStream(fileName));

        document.open();

        Font titleFont =
                new Font(
                        Font.HELVETICA,
                        18,
                        Font.BOLD);

        Font sectionFont =
                new Font(
                        Font.HELVETICA,
                        14,
                        Font.BOLD);

        Paragraph title =
                new Paragraph(
                        "ALL LOAN APPLICATIONS REPORT",
                        titleFont);

        title.setAlignment(
                Element.ALIGN_CENTER);

        document.add(title);
        document.add(new Paragraph(" "));

        for(LoanApplication application : applications)
        {
            document.add(
                    new Paragraph(
                            "APPLICATION #" +
                            application.getApplicationId(),
                            sectionFont));

            document.add(new Paragraph(" "));

            // =====================
            // APPLICATION DETAILS
            // =====================

            PdfPTable applicationTable =
                    new PdfPTable(2);

            applicationTable.setWidthPercentage(100);

            applicationTable.addCell(
                    headerCell("Application ID"));

            applicationTable.addCell(
                    valueCell(
                            String.valueOf(
                                    application.getApplicationId())));

            applicationTable.addCell(
                    headerCell("Application Date"));

            applicationTable.addCell(
                    valueCell(
                            String.valueOf(
                                    application.getApplicationDate())));

            applicationTable.addCell(
                    headerCell("Status"));

            applicationTable.addCell(
                    valueCell(
                            application.getStatus()));

            applicationTable.addCell(
                    headerCell("Reviewed By"));

            applicationTable.addCell(
                    valueCell(
                            String.valueOf(
                                    application.getReviewedBy())));

            applicationTable.addCell(
                    headerCell("Review Notes"));

            applicationTable.addCell(
                    valueCell(
                            application.getReviewNotes() == null
                                    ? "No Notes"
                                    : application.getReviewNotes()));

            document.add(applicationTable);

            document.add(new Paragraph(" "));

            // =====================
            // APPLICANT
            // =====================

            document.add(
                    new Paragraph(
                            "Applicant Information",
                            sectionFont));

            PdfPTable applicantTable =
                    new PdfPTable(2);

            applicantTable.setWidthPercentage(100);

            applicantTable.addCell(headerCell("User ID"));
            applicantTable.addCell(
                    valueCell(
                            String.valueOf(
                                    application.getApplicant().getUserId())));

            applicantTable.addCell(headerCell("Applicant ID"));
            applicantTable.addCell(
                    valueCell(
                            String.valueOf(
                                    application.getApplicant().getApplicantId())));

            applicantTable.addCell(headerCell("Full Name"));
            applicantTable.addCell(
                    valueCell(
                            application.getApplicant().getFullName()));

            applicantTable.addCell(headerCell("Email"));
            applicantTable.addCell(
                    valueCell(
                            application.getApplicant().getEmail()));

            applicantTable.addCell(headerCell("Phone"));
            applicantTable.addCell(
                    valueCell(
                            application.getApplicant().getPhone()));

            applicantTable.addCell(headerCell("Address"));
            applicantTable.addCell(
                    valueCell(
                            application.getApplicant().getAddress()));

            applicantTable.addCell(headerCell("Date of Birth"));
            applicantTable.addCell(
                    valueCell(
                            application.getApplicant().getDateOfBirth()));

            applicantTable.addCell(headerCell("SSN"));
            applicantTable.addCell(
                    valueCell(
                            application.getApplicant().getSSN()));

            applicantTable.addCell(headerCell("Employer"));
            applicantTable.addCell(
                    valueCell(
                            application.getApplicant().getEmployerName()));

            document.add(applicantTable);

            document.add(new Paragraph(" "));

            // =====================
            // VEHICLE
            // =====================

            document.add(
                    new Paragraph(
                            "Vehicle Information",
                            sectionFont));

            PdfPTable vehicleTable =
                    new PdfPTable(2);

            vehicleTable.setWidthPercentage(100);

            vehicleTable.addCell(
                    headerCell("Application ID"));

            vehicleTable.addCell(
                    valueCell(
                            String.valueOf(
                                    application.getVehicle()
                                            .getApplicationId())));

            vehicleTable.addCell(
                    headerCell("Year"));

            vehicleTable.addCell(
                    valueCell(
                            String.valueOf(
                                    application.getVehicle()
                                            .getYear())));

            vehicleTable.addCell(
                    headerCell("Make"));

            vehicleTable.addCell(
                    valueCell(
                            application.getVehicle()
                                    .getMake()));

            vehicleTable.addCell(
                    headerCell("Model"));

            vehicleTable.addCell(
                    valueCell(
                            application.getVehicle()
                                    .getModel()));

            document.add(vehicleTable);

            document.add(new Paragraph(" "));

            // =====================
            // LOAN
            // =====================

            document.add(
                    new Paragraph(
                            "Loan Information",
                            sectionFont));

            PdfPTable loanTable =
                    new PdfPTable(2);

            loanTable.setWidthPercentage(100);

            loanTable.addCell(
                    headerCell("Auto Price"));

            loanTable.addCell(
                    valueCell(
                            String.format(
                                    "$%,.2f",
                                    application.getLoan()
                                            .getAutoPrice())));

            loanTable.addCell(
                    headerCell("Down Payment"));

            loanTable.addCell(
                    valueCell(
                            String.format(
                                    "$%,.2f",
                                    application.getLoan()
                                            .getDownPayment())));

            loanTable.addCell(
                    headerCell("Interest Rate"));

            loanTable.addCell(
                    valueCell(
                            application.getLoan()
                                    .getInterestRate()
                                    + "%"));

            loanTable.addCell(
                    headerCell("Loan Term"));

            loanTable.addCell(
                    valueCell(
                            application.getLoan()
                                    .getLoanTerm()
                                    + " Months"));

            loanTable.addCell(
                    headerCell("Sales Tax"));

            loanTable.addCell(
                    valueCell(
                            application.getLoan()
                                    .getSalesTax()
                                    + "%"));

            loanTable.addCell(
                    headerCell("Fees"));

            loanTable.addCell(
                    valueCell(
                            String.format(
                                    "$%,.2f",
                                    application.getLoan()
                                            .getFees())));

            loanTable.addCell(
                    headerCell("Cash Incentive"));

            loanTable.addCell(
                    valueCell(
                            String.format(
                                    "$%,.2f",
                                    application.getLoan()
                                            .getCashIncentive())));

            document.add(loanTable);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
        }

        document.close();

        JOptionPane.showMessageDialog(
                null,
                "All applications exported successfully.");
    }
    catch(Exception e)
    {
        e.printStackTrace();

        JOptionPane.showMessageDialog(
                null,
                "Failed to export applications:\n"
                        + e.getMessage());
    }
}
}