package utils;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JOptionPane;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import api.ApplicantApiClient;
import api.VehicleApiClient;
import models.*;

public class PDFExporter
{
    public static void exportApplication(
        LoanApplication application,
        String fileName)
{
        Applicant applicant = null;
        Vehicle vehicle = null;
    try
    {
         applicant =
                ApplicantApiClient.getApplicantById(
                        application.getApplicant_id());

         vehicle =
                VehicleApiClient.getVehicleById(
                        application.getVehicle_id());

        

        if(applicant == null || vehicle == null)
        {
            JOptionPane.showMessageDialog(
                    null,
                    "Applicant or vehicle not found.");
            return;
        }

        Document document = new Document();

        

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
                                    application.getApplication_id())));

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
                            applicant.getFullName()));
                                    

            applicantTable.addCell(
                    headerCell("Email"));

            applicantTable.addCell(
                    valueCell(
                            applicant.getEmail()));

            applicantTable.addCell(
                    headerCell("Phone"));

            applicantTable.addCell(
                    valueCell(
                            applicant.getPhone()));

            applicantTable.addCell(
                    headerCell("Address"));

            applicantTable.addCell(
                    valueCell(
                            applicant.getAddress()));

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
                                    vehicle.getYear())));

            vehicleTable.addCell(
                    headerCell("Make"));

            vehicleTable.addCell(
                    valueCell(
                            vehicle.getMake()));

            vehicleTable.addCell(
                    headerCell("Model"));

            vehicleTable.addCell(
                    valueCell(
                            vehicle.getModel()));

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
                    application.getMonthly_payment() * application.getLoan_term();

            loanTable.addCell(
                    headerCell("Auto Price"));

            loanTable.addCell(
                    valueCell(
                            formatMoney(
                                    application.getAuto_price())));

            loanTable.addCell(
                    headerCell("Down Payment"));

            loanTable.addCell(
                    valueCell(
                            formatMoney(
                                    application.getDown_payment())));

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
                            application.getInterest_rate()
                                    + "%"));

            loanTable.addCell(
                    headerCell("Loan Term"));

            loanTable.addCell(
                    valueCell(
                            application
                                    .getLoan_term()
                                    + " Months"));

            loanTable.addCell(
                    headerCell("Sales Tax"));

            loanTable.addCell(
                    valueCell(
                            application
                                    .getSales_tax()
                                    + "%"));

            loanTable.addCell(
                    headerCell("Fees"));

            loanTable.addCell(
                    valueCell(
                            formatMoney(
                                    application.getFees())));

            loanTable.addCell(
                    headerCell("Cash Incentive"));

            loanTable.addCell(
                    valueCell(
                            formatMoney(
                                    application.getCash_incentive())));

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
                            application.getReview_notes() == null
                                    || application.getReview_notes().isBlank()
                                    ? "No notes available"
                                    : application.getReview_notes()));

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
        Document document = new Document();

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

        title.setAlignment(Element.ALIGN_CENTER);

        document.add(title);
        document.add(new Paragraph(" "));

        for (LoanApplication application : applications)
        {
            Applicant applicant =
                    ApplicantApiClient.getApplicantById(
                            application.getApplicant_id());

            Vehicle vehicle =
                    VehicleApiClient.getVehicleById(
                            application.getVehicle_id());

            document.add(
                    new Paragraph(
                            "APPLICATION #"
                                    + application.getApplication_id(),
                            sectionFont));

            document.add(new Paragraph(" "));

            // =====================================
            // APPLICATION DETAILS
            // =====================================

            PdfPTable applicationTable =
                    new PdfPTable(2);

            applicationTable.setWidthPercentage(100);

            applicationTable.addCell(
                    headerCell("Application ID"));

            applicationTable.addCell(
                    valueCell(
                            String.valueOf(
                                    application.getApplication_id())));

            applicationTable.addCell(
                    headerCell("Application Date"));

            applicationTable.addCell(
                    valueCell(
                            String.valueOf(
                                    application.getApplication_date())));

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
                                    application.getReviewed_by())));

            applicationTable.addCell(
                    headerCell("Review Notes"));

            applicationTable.addCell(
                    valueCell(
                            application.getReview_notes() == null
                                    ? "No Notes"
                                    : application.getReview_notes()));

            document.add(applicationTable);

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

            applicantTable.addCell(headerCell("User ID"));
            applicantTable.addCell(
                    valueCell(
                            String.valueOf(
                                    applicant.getUserId())));

            applicantTable.addCell(headerCell("Applicant ID"));
            applicantTable.addCell(
                    valueCell(
                            String.valueOf(
                                    applicant.getApplicantId())));

            applicantTable.addCell(headerCell("Full Name"));
            applicantTable.addCell(
                    valueCell(
                            applicant.getFullName()));

            applicantTable.addCell(headerCell("Email"));
            applicantTable.addCell(
                    valueCell(
                            applicant.getEmail()));

            applicantTable.addCell(headerCell("Phone"));
            applicantTable.addCell(
                    valueCell(
                            applicant.getPhone()));

            applicantTable.addCell(headerCell("Address"));
            applicantTable.addCell(
                    valueCell(
                            applicant.getAddress()));

            applicantTable.addCell(headerCell("Date of Birth"));
            applicantTable.addCell(
                    valueCell(
                            applicant.getDateOfBirth()));

            applicantTable.addCell(headerCell("SSN"));
            applicantTable.addCell(
                    valueCell(
                            applicant.getSSN()));

            applicantTable.addCell(headerCell("Employer"));
            applicantTable.addCell(
                    valueCell(
                            applicant.getEmployerName()));

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
                    headerCell("Vehicle ID"));

            vehicleTable.addCell(
                    valueCell(
                            String.valueOf(
                                    vehicle.getVehicle_id())));

            vehicleTable.addCell(
                    headerCell("Year"));

            vehicleTable.addCell(
                    valueCell(
                            String.valueOf(
                                    vehicle.getYear())));

            vehicleTable.addCell(
                    headerCell("Make"));

            vehicleTable.addCell(
                    valueCell(
                            vehicle.getMake()));

            vehicleTable.addCell(
                    headerCell("Model"));

            vehicleTable.addCell(
                    valueCell(
                            vehicle.getModel()));

            vehicleTable.addCell(
                    headerCell("VIN"));

            vehicleTable.addCell(
                    valueCell(
                            vehicle.getVin()));

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
                    application.getMonthly_payment()
                            * application.getLoan_term();

            loanTable.addCell(
                    headerCell("Auto Price"));

            loanTable.addCell(
                    valueCell(
                            formatMoney(
                                    application.getAuto_price())));

            loanTable.addCell(
                    headerCell("Down Payment"));

            loanTable.addCell(
                    valueCell(
                            formatMoney(
                                    application.getDown_payment())));

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
                            application.getInterest_rate()
                                    + "%"));

            loanTable.addCell(
                    headerCell("Loan Term"));

            loanTable.addCell(
                    valueCell(
                            application.getLoan_term()
                                    + " Months"));

            loanTable.addCell(
                    headerCell("Sales Tax"));

            loanTable.addCell(
                    valueCell(
                            application.getSales_tax()
                                    + "%"));

            loanTable.addCell(
                    headerCell("Fees"));

            loanTable.addCell(
                    valueCell(
                            formatMoney(
                                    application.getFees())));

            loanTable.addCell(
                    headerCell("Cash Incentive"));

            loanTable.addCell(
                    valueCell(
                            formatMoney(
                                    application.getCash_incentive())));

            document.add(loanTable);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
        }

        document.close();

        JOptionPane.showMessageDialog(
                null,
                "All applications exported successfully.");
    }
    catch (Exception e)
    {
        e.printStackTrace();

        JOptionPane.showMessageDialog(
                null,
                "Failed to export applications:\n"
                        + e.getMessage());
    }
}
}