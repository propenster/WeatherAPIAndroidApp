package com.github.propenster.weatherapiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Declare App widgets
    Button btn_GetCityID, btn_GetCityByID, btn_GetCityByName;
    EditText edt_locationSearchInput;
    ListView lv_weatherForecastReport;
    WeatherDataService weatherDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiate App Widgets
        btn_GetCityID = findViewById(R.id.btn_GetCityID);
        btn_GetCityByID = findViewById(R.id.btn_GetCityByID);
        btn_GetCityByName = findViewById(R.id.btn_GetCityByName);
        edt_locationSearchInput = findViewById(R.id.edt_locationSearchInput);
        lv_weatherForecastReport = findViewById(R.id.lv_weatherForecastReport);
        weatherDataService = new WeatherDataService(MainActivity.this);



        //Set ActionListeners on each of the widgets
        btn_GetCityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataService.getCityID(edt_locationSearchInput.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something went wrong in main", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String cityID) {
                        Toast.makeText(MainActivity.this, "Returned an ID of " + cityID, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_GetCityByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "You clicked the GetCityByID Button", Toast.LENGTH_SHORT).show();
                weatherDataService.getCityForecastByID(edt_locationSearchInput.getText().toString(), new WeatherDataService.ForecastByIDResponseListener() {
                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        //Toast.makeText(MainActivity.this, weatherReportModels.toString(), Toast.LENGTH_LONG).show();
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReportModels);
                        lv_weatherForecastReport.setAdapter(arrayAdapter);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_GetCityByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataService.getCityForecastByName(edt_locationSearchInput.getText().toString(), new WeatherDataService.GetCityForecastByNameCallback() {
                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        //Toast.makeText(MainActivity.this, weatherReportModels.toString(), Toast.LENGTH_SHORT).show();
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReportModels);
                        lv_weatherForecastReport.setAdapter(arrayAdapter);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}