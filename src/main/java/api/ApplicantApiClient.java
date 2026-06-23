package api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.*;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.util.List;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;



public class ApplicantApiClient
{
        private static final ObjectMapper mapper =
        new ObjectMapper();

static {
    mapper.setPropertyNamingStrategy(
            PropertyNamingStrategies.SNAKE_CASE);
}
    private static final String BASE_URL =
            "http://127.0.0.1:8000";


    // =====================================
    // CREATE APPLICANT
    // POST /applicant
    // =====================================

    public static Applicant createApplicant(Applicant applicant)
            throws Exception
    {
        URL url =
                new URL(BASE_URL + "/applicant");

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty(
                "Content-Type",
                "application/json");

        connection.setDoOutput(true);

        String json =
                mapper.writeValueAsString(applicant);


        try(OutputStream os =
                    connection.getOutputStream())
        {
            os.write(json.getBytes());
        }

        int status = connection.getResponseCode();

InputStream stream;

if (status >= 400)
{
    stream = connection.getErrorStream();

    String error =
            new String(stream.readAllBytes());


    throw new RuntimeException(error);
}

stream = connection.getInputStream();

return mapper.readValue(
        stream,
        Applicant.class);
    }

    // =====================================
    // GET ALL APPLICANTS
    // GET /applicant
    // =====================================

    public static List<Applicant> getAllApplicants()
            throws Exception
    {
        URL url =
                new URL(BASE_URL + "/applicant");

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        return mapper.readValue(
                connection.getInputStream(),
                new TypeReference<List<Applicant>>() {});
    }

    // =====================================
    // GET APPLICANT BY ID
    // GET /applicant/{applicant_id}
    // =====================================

    public static Applicant getApplicantById(int applicantId)
            throws Exception
    {
        URL url =
                new URL(
                        BASE_URL +
                        "/applicant/" +
                        applicantId);

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        return mapper.readValue(
                connection.getInputStream(),
                Applicant.class);
    }

    // =====================================
    // GET APPLICANT BY USER ID
    // GET /applicant/user/{user_id}
    // =====================================

    public static Applicant getApplicantByUserId(int userId)
            throws Exception
    {
        URL url =
                new URL(
                        BASE_URL +
                        "/applicant/user/" +
                        userId);

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        return mapper.readValue(
                connection.getInputStream(),
                Applicant.class);
    }

    // =====================================
    // GET APPLICATIONS BY APPLICANT
    // GET /applicant/{applicant_id}/applications
    // =====================================

    public static List<LoanApplication> getApplicationsByApplicant(
            int applicantId)
            throws Exception
    {
        URL url =
                new URL(
                        BASE_URL +
                        "/applicant/" +
                        applicantId +
                        "/applications");

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        return mapper.readValue(
                connection.getInputStream(),
                new TypeReference<List<LoanApplication>>() {});
    }
}