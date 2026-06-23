package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Type;
import models.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoanApplicationApi {
    private static final String BASE_URL =
            "http://127.0.0.1:8000";

    private static final HttpClient client =
            HttpClient.newHttpClient();

    private static final ObjectMapper mapper =
            new ObjectMapper();
public static List<LoanApplication> getApplicationsByOfficer(
        int officerId)
{
    try
    {
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL +
                                        "/officer/" +
                                        officerId +
                                        "/applications"))
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 200)
        {
            return mapper.readValue(
        response.body(),
        mapper.getTypeFactory()
                .constructCollectionType(
                        List.class,
                        LoanApplication.class));
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }

    return new ArrayList<>();
}
public static List<LoanApplication> getUnderReviewApplications()
{
    try
    {
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL +
                                        "/loan/review"))
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 200)
        {
            return mapper.readValue(
        response.body(),
        mapper.getTypeFactory()
                .constructCollectionType(
                        List.class,
                        LoanApplication.class));
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }

    return new ArrayList<>();
}
public static List<LoanApplication> getDeniedApplications()
{
    try
    {
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL +
                                        "/loan/denied"))
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 200)
        {
           return mapper.readValue(
        response.body(),
        mapper.getTypeFactory()
                .constructCollectionType(
                        List.class,
                        LoanApplication.class));
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }

    return new ArrayList<>();
}
public static List<LoanApplication> getApprovedApplications()
{
    try
    {
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL +
                                        "/loan/approved"))
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 200)
        {
            return mapper.readValue(
        response.body(),
        mapper.getTypeFactory()
                .constructCollectionType(
                        List.class,
                        LoanApplication.class));
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }

    return new ArrayList<>();
}
public static List<LoanApplication> getPendingApplications()
{
    try
    {
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL +
                                        "/loan/pending"))
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 200)
        {
           return mapper.readValue(
        response.body(),
        mapper.getTypeFactory()
                .constructCollectionType(
                        List.class,
                        LoanApplication.class));
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }

    return new ArrayList<>();
}
public static LoanApplication assignOfficer(
        int applicationId,
        int officerId)
{
    try
    {
        AssignOfficerRequest requestBody =
                new AssignOfficerRequest(
                        officerId);

        String json =
        mapper.writeValueAsString(requestBody);

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL +
                                        "/loan/" +
                                        applicationId +
                                        "/assign"))
                        .PUT(
                                HttpRequest.BodyPublishers
                                        .ofString(json))
                        .header(
                                "Content-Type",
                                "application/json")
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if(response.statusCode()==200)
        {
            return mapper.readValue(
        response.body(),
        LoanApplication.class);
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }

    return null;
}
public static LoanApplication approveLoan(
        int applicationId,
        int officerId,
        String notes)
{
    try
    {
        ReviewRequest requestBody =
                new ReviewRequest(
                        officerId,
                        notes);

       String json =
        mapper.writeValueAsString(requestBody);

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                            URI.create(
                                    BASE_URL +
                                    "/loan/" +
                                    applicationId +
                                    "/approve"))
                        .PUT(
                                HttpRequest.BodyPublishers
                                        .ofString(json))
                        .header(
                                "Content-Type",
                                "application/json")
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if(response.statusCode()==200)
        {
            return mapper.readValue(
        response.body(),
        LoanApplication.class
            );
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }

    return null;
}
public static LoanApplication denyLoan(
        int applicationId,
        int officerId,
        String notes)
{
    try
    {
        ReviewRequest requestBody =
                new ReviewRequest(
                        officerId,
                        notes);

        String json =
                mapper.writeValueAsString(requestBody);

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL +
                                        "/loan/" +
                                        applicationId +
                                        "/deny"))
                        .PUT(
                                HttpRequest.BodyPublishers
                                        .ofString(json))
                        .header(
                                "Content-Type",
                                "application/json")
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if(response.statusCode()==200)
        {
            return mapper.readValue(
                    response.body(),
                    LoanApplication.class
            );
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }

    return null;
}
public static LoanApplication getApplicationById(int applicationId)
{
    try
    {
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                            URI.create(
                                BASE_URL +
                                "/loan/" +
                                applicationId))
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 200)
        {
            return mapper.readValue(
                    response.body(),
                    LoanApplication.class
            );
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }

    return null;
}

public static List<LoanApplication> getApplicationsByApplicant(int applicantId)
{
    try
    {
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                            URI.create(
                                BASE_URL +
                                "/loan/applicant/" +
                                applicantId))
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() == 200)
        {
            List<LoanApplication> applications =
        mapper.readValue(
                response.body(),
                mapper.getTypeFactory()
                      .constructCollectionType(
                              List.class,
                              LoanApplication.class));
            System.out.println("Applications = " + applications);

            return applications;
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }

    return new ArrayList<>();
}
    public static LoanApplication createLoan(
            LoanApplication loan) {

        try {

            String json =
                    mapper.writeValueAsString(loan);

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(
                                    URI.create(
                                            BASE_URL + "/loan"))
                            .header(
                                    "Content-Type",
                                    "application/json")
                            .POST(
                                    HttpRequest.BodyPublishers
                                            .ofString(json))
                            .build();
                            System.out.println(json);

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers
                                    .ofString());

            return mapper.readValue(
                    response.body(),
                    LoanApplication.class);

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    public static List<LoanApplication> getAlLoanApplications() {

          try
    {
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL +
                                        "/loan"))
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 200)
        {
            return mapper.readValue(
        response.body(),
        mapper.getTypeFactory()
                .constructCollectionType(
                        List.class,
                        LoanApplication.class));
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }

    return new ArrayList<>();
    }
    
}