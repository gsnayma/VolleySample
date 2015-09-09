package com.above.volleysample.volleysample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailsOfRestaurantActivity extends AppCompatActivity {

    private TextView textViewDetailsName;
    private TextView textViewDetailsLocation, address_tv, phone_tv, website_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_restaurant);
        textViewDetailsName = (TextView) findViewById(R.id.textViewName);
        textViewDetailsLocation = (TextView) findViewById(R.id.textViewLocation);
        address_tv=(TextView) findViewById(R.id.textViewAddress);
        phone_tv= (TextView) findViewById(R.id.textViewPhone);
        website_tv=(TextView) findViewById(R.id.textViewWebsite);


        Intent intent= getIntent();
        Details d = (Details) intent.getSerializableExtra("details");
        textViewDetailsLocation.setText(d.getLocation());
        textViewDetailsName.setText(d.getName());
        address_tv.setText(d.getAddress());
        if(d.getPhone_number().length()==0)
            phone_tv.setText("Phone no is not available");
        else
        phone_tv.setText(d.getPhone_number());
        if(d.getWebsite().length()==0)
            website_tv.setText("Website doesn't exists");
        else
            website_tv.setText(d.getWebsite());



    }



}
