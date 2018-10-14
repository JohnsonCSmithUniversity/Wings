package com.wings.wings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.wings.wings.WeatherAPI.Weather;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Context mContext = MainActivity.this;
    private TextView mArrivalTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DataManagerInstance instance = DataManagerInstance.getInstance();
        Flight flight = instance.getReservation().getFlights().get(0);
        final Airport airport = instance.getAirport();

        TextView departureLocationView = findViewById(R.id.departure_location_textView);
        TextView arrivalLocationView = findViewById(R.id.arrival_location_textView);
        TextView departureTime = findViewById(R.id.departure_time_textView);
        TextView arrivalLocation = findViewById(R.id.arrival_time_textView);
        mArrivalTemperature = findViewById(R.id.arrival_temp_textView);

        Button direcetionButton = findViewById(R.id.departure_direction_button);

        departureLocationView.setText(flight.getOrigin());
        arrivalLocationView.setText(flight.getDestination());
        departureTime.setText(flight.getDepartureTime());
        arrivalLocation.setText(flight.getArrivalTime());

        getWeather("" + airport.getLatitude(), "" + airport.getLongitude());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ChatUI.class));
            }
        });
        direcetionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDirectionsInGoogleMaps("" + airport.getLatitude(), "" + airport.getLongitude());
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            startActivity(new Intent(MainActivity.this, BarcodeActivity.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getWaze() {
        //startActivity(new Intent(MainActivity.this, )); https://waze.com/ul
    }

    private void getWeather(String latitude, String longitude) {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=2988f111837e4a4eb7bcab831dc628e1";
        final Gson gson = new Gson();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Weather weather = gson.fromJson(response.toString(), Weather.class);
                        int temp = (int) getFahrenheit(weather.getMain().getTemp());
                        mArrivalTemperature.setText(temp + " F");
                        //Toast.makeText(mContext, "" + temp+ "F", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void openDirectionsInGoogleMaps(String latitude, String longitude) {
        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    public double getFahrenheit(double degreesKelvin) {
        double f = (((degreesKelvin - 273) * 9d / 5) + 32);
        return f;
    }


}
