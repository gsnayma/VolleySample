package com.above.volleysample.volleysample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailsOfRestaurantActivity extends AppCompatActivity {

    private TextView textViewDetailsName;
    private TextView textViewDetailsLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_restaurant);
        textViewDetailsName = (TextView) findViewById(R.id.textViewName);
        textViewDetailsLocation = (TextView) findViewById(R.id.textViewLocation);


        Intent intent= getIntent();
        Details d = (Details) intent.getSerializableExtra("details");
        textViewDetailsLocation.setText(d.getLocation());
        textViewDetailsName.setText(d.getName());


    }

}
