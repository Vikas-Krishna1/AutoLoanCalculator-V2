package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.LoanApplication;
import models.LoanOfficer;
import models.OfficerStatistics;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class LoanOfficerApiClient {

    private static final String BASE_URL =
            "https://autoloancalculator-v2.onrender.com";

    private static final HttpClient client =
            HttpClient.newHttpClient();

    private static final Gson gson =
            new Gson();

    /**
     * Get officer by loan_officer_id
     * GET /officer/{loan_officer_id}
     */
    public static LoanOfficer getLoanOfficerById(int officerId)
            throws Exception {

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL
                                                + "/officer/"
                                                + officerId))
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return gson.fromJson(
                    response.body(),
                    LoanOfficer.class);
        }

        throw new Exception(
                "Failed to retrieve officer. HTTP Error Code: "
                        + response.statusCode());
    }

    /**
     * Get officer by user_id
     * GET /officer/user/{user_id}
     */
    public static LoanOfficer getLoanOfficerByUserId(int userId)
            throws Exception {

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL
                                                + "/officer/user/"
                                                + userId))
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return gson.fromJson(
                    response.body(),
                    LoanOfficer.class);
        }

        throw new Exception(
                "Failed to retrieve officer. HTTP Error Code: "
                        + response.statusCode());
    }

    /**
     * Get all officers
     * GET /officer
     */
    public static List<LoanOfficer> getAllOfficers()
            throws Exception {

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL + "/officer"))
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {

            Type listType =
                    new TypeToken<List<LoanOfficer>>() {
                    }.getType();

            return gson.fromJson(
                    response.body(),
                    listType);
        }

        return new ArrayList<>();
    }

    /**
     * Get all applications assigned to an officer
     * GET /officer/{loan_officer_id}/applications
     */
    public static List<LoanApplication> getApplicationsByOfficer(
            int officerId)
            throws Exception {

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL
                                                + "/officer/"
                                                + officerId
                                                + "/applications"))
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {

            Type listType =
                    new TypeToken<List<LoanApplication>>() {
                    }.getType();

            return gson.fromJson(
                    response.body(),
                    listType);
        }

        return new ArrayList<>();
    }

    /**
     * Get statistics for an officer
     * GET /officer/{loan_officer_id}/statistics
     */
    public static OfficerStatistics getOfficerStatistics(
            int officerId)
            throws Exception {

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL
                                                + "/officer/"
                                                + officerId
                                                + "/statistics"))
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {

            return gson.fromJson(
                    response.body(),
                    OfficerStatistics.class);
        }

        throw new Exception(
                "Failed to retrieve statistics. HTTP Error Code: "
                        + response.statusCode());
    }
    public static LoanOfficer createOfficer(
        LoanOfficer officer)
        throws Exception
{
    ObjectMapper mapper =
            new ObjectMapper();

    String json =
            mapper.writeValueAsString(officer);

    HttpRequest request =
            HttpRequest.newBuilder()
                    .uri(
                            URI.create(
                                    BASE_URL +
                                    "/officer"))
                    .header(
                            "Content-Type",
                            "application/json")
                    .POST(
                            HttpRequest.BodyPublishers
                                    .ofString(json))
                    .build();

    HttpResponse<String> response =
            client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());

    if(response.statusCode() == 200)
    {
        return mapper.readValue(
                response.body(),
                LoanOfficer.class);
    }

    throw new RuntimeException(
            "Failed to create officer");
}
}