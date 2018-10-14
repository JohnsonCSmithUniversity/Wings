package com.wings.wings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A screen that will allow tha user to find their flight reservation
 */
public class LoginActivity extends AppCompatActivity {


    private AutoCompleteTextView mEmailView;
    private EditText mRecordLocatorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mRecordLocatorView = (EditText) findViewById(R.id.record_locator_editText);


        Button findReservationButton = (Button) findViewById(R.id.find_flight_button);
        findReservationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptGetReservation();
            }
        });

    }

    private void attemptGetReservation() {
        // Reset errors.
        mEmailView.setError(null);
        mRecordLocatorView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String recordLocator = mRecordLocatorView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid recordLocator, if the user entered one.
        if (!TextUtils.isEmpty(recordLocator) && !isPasswordValid(recordLocator)) {
            mRecordLocatorView.setError(getString(R.string.error_invalid_password));
            focusView = mRecordLocatorView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            makeRequestForUser(email);
            makeRequestForReservation(recordLocator);
            //startActivity(new Intent(LoginActivity.this, MainActivity.class));

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void makeRequestForUser(String email) {
        //split email
        String[] parts = email.split("@");
        String emailName = parts[0];
        String emailAddress = parts[1];

        //
        String url = "https://wings-jcsu.herokuapp.com/user?email=" + emailName + "%40" + emailAddress;
        final Gson gson = new Gson();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        User user = gson.fromJson(response.toString(), User.class);
                        DataManagerInstance instance = DataManagerInstance.getInstance();
                        instance.setUser(user);
                        Toast.makeText(LoginActivity.this, instance.getUser().getFirstName(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(LoginActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void makeRequestForReservation(String recordLocator) {
        String url = "https://wings-jcsu.herokuapp.com/reservation?recordLocator=" + recordLocator.trim();
        final Gson gson = new Gson();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Reservation reservation = gson.fromJson(response.toString(), Reservation.class);
                        //reservations.add(reservation);
                        DataManagerInstance instance = DataManagerInstance.getInstance();
                        instance.setReservation(reservation);
                        makeRequestForAirportInfo(reservation.getFlights().get(0).getDestination());
                        Toast.makeText(LoginActivity.this, instance.getReservation().getId(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Error app side ", Toast.LENGTH_SHORT).show();

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void makeRequestForFlight(String flightNumber, String date) {
        String url = "https://wings-jcsu.herokuapp.com/flight?flightNumber=" + flightNumber + "&date=" + date;
        final Gson gson = new Gson();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Flight flight = gson.fromJson(response.toString(), Flight.class);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(LoginActivity.this, "Error app side ", Toast.LENGTH_SHORT).show();

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void makeRequestForAirportInfo(String airportCode) {
        String url = "https://wings-jcsu.herokuapp.com/airports?code="+airportCode;
        final Gson gson = new Gson();

        final JsonArrayRequest array = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        final GsonBuilder gsonBuilder = new GsonBuilder();
                        final Gson gson = gsonBuilder.create();

                        Airport[] testCase = gson.fromJson(response.toString(), Airport[].class);
                        DataManagerInstance instance = DataManagerInstance.getInstance();
                        instance.setAirport(testCase[0]);
                        Toast.makeText(LoginActivity.this, "" + instance.getAirport().getLatitude(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Error " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(array);
    }

}

