package api;

import models.Vehicle;
import okhttp3.*;
import com.google.gson.Gson;

import java.io.IOException;


public class VehicleApiClient
{
        
    private static final String BASE_URL =
            "http://localhost:8000";

    private static final Gson gson =
            new Gson();

    private static final OkHttpClient client =
            new OkHttpClient();

    public static Vehicle createVehicle(
            Vehicle vehicle)
            throws IOException
    {
        String json =
                gson.toJson(vehicle);

        RequestBody body =
                RequestBody.create(
                        json,
                        MediaType.parse(
                                "application/json"));

        Request request =
                new Request.Builder()
                        .url(BASE_URL + "/vehicle")
                        .post(body)
                        .build();

        Response response =
                client.newCall(request).execute();

        if(response.isSuccessful())
        {
            String responseBody =
                    response.body().string();

            return gson.fromJson(
                    responseBody,
                    Vehicle.class);
        }

        throw new IOException(
                "Vehicle creation failed.");
    }

    public static Vehicle getVehicleById(
            int vehicleId)
            throws IOException
    {
        Request request =
                new Request.Builder()
                        .url(BASE_URL +
                                "/vehicle/" +
                                vehicleId)
                        .get()
                        .build();

        Response response =
                client.newCall(request).execute();

        if(response.isSuccessful())
        {
            return gson.fromJson(
                    response.body().string(),
                    Vehicle.class);
        }

        throw new IOException(
                "Vehicle not found.");
    }
}