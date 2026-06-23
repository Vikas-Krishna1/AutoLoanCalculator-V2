package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserApiClient
{
    private static final String BASE_URL =
            "http://localhost:8000";

    private static final HttpClient client =
            HttpClient.newHttpClient();

    private static final ObjectMapper mapper =
            new ObjectMapper();

    public static User login(
            String username,
            String password
    ) throws Exception
    {
        LoginRequest requestBody =
                new LoginRequest(
                        username,
                        password
                );

        String json =
                mapper.writeValueAsString(
                        requestBody
                );

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL + "/login"
                                )
                        )
                        .header(
                                "Content-Type",
                                "application/json"
                        )
                        .POST(
                                HttpRequest.BodyPublishers
                                        .ofString(json)
                        )
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        if(response.statusCode() != 200)
        {
            return null;
        }

        return mapper.readValue(
                response.body(),
                User.class
        );
    }
    public static User register(
        String username,
        String password,
        String role
) throws Exception
{
    UserCreate requestBody =
            new UserCreate(
                    username,
                    password,
                    role
            );

    String json =
            mapper.writeValueAsString(
                    requestBody
            );

    HttpRequest request =
            HttpRequest.newBuilder()
                    .uri(
                            URI.create(
                                    BASE_URL +
                                    "/register"
                            )
                    )
                    .header(
                            "Content-Type",
                            "application/json"
                    )
                    .POST(
                            HttpRequest.BodyPublishers
                                    .ofString(json)
                    )
                    .build();

    HttpResponse<String> response =
        client.send(
                request,
                HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200)
        {
                return null;
        }

        return mapper.readValue(
        response.body(),
        User.class);
}
//User registers APplicnat
public static boolean registerApplicant(
        String username,
        String password,
        String fullName,
        String email,
        String phone,
        String address,
        String dateOfBirth,
        String ssn,
        String employerName
) throws Exception
{
    RegisterApplicantRequest requestBody =
            new RegisterApplicantRequest(
                    username,
                    password,
                    fullName,
                    email,
                    phone,
                    address,
                    dateOfBirth,
                    ssn,
                    employerName
            );

    String json =
            mapper.writeValueAsString(
                    requestBody);
        System.out.println(json);
        System.out.println(
    mapper.writerWithDefaultPrettyPrinter()
          .writeValueAsString(requestBody)
);

    HttpRequest request =
            HttpRequest.newBuilder()
                    .uri(
                            URI.create(
                                    BASE_URL +
                                    "/register/applicant"))
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

    return response.statusCode() == 200;
}public static boolean registerOfficer(
        String username,
        String password,
        String fullName,
        String email,
        String phone,
        String employeeNumber
) throws Exception
{
    RegisterOfficerRequest requestBody =
            new RegisterOfficerRequest(
                    username,
                    password,
                    fullName,
                    email,
                    phone,
                    employeeNumber
            );

    String json =
            mapper.writeValueAsString(
                    requestBody);
System.out.println(json);
System.out.println(
    mapper.writerWithDefaultPrettyPrinter()
          .writeValueAsString(requestBody)
);

    HttpRequest request =
            HttpRequest.newBuilder()
                    .uri(
                            URI.create(
                                    BASE_URL +
                                    "/register/loan-officer"))
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
                    System.out.println("Status: " + response.statusCode());
System.out.println("Body: " + response.body());

    return response.statusCode() == 200;
}
}