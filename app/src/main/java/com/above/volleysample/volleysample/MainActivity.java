package com.above.volleysample.volleysample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;





import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private Spinner spinner;
    private String[] listOfLocations = {"Amsterdam","Barcelona","Berlin","Dubay","London","Paris","Rome","Tuscany"};
    String location="";
    ArrayList<Parcelable> listRestaurants= new ArrayList<>();
    StringBuilder sb;
    String details;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinner = (Spinner) findViewById(R.id.spinner);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listOfLocations);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location = spinner.getSelectedItem() + "";
                Log.e("Location selected--> ", location);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void searchRestaurants(View v)
    {
        String url="http://tour-pedia.org/api/getPlaces?category=restaurant&location="+location+"&name=La+Dolce+Vita";

        DownloadTask task= new DownloadTask();
        task.execute(url);

/*
        Intent intent = new Intent(MainActivity.this,SearchedRestaurantListActivity.class);
        intent.putParcelableArrayListExtra("rest_list", listRestaurants);
        // intent.putExtra("details",details);
        startActivity(intent);
        */
    }

    class DownloadTask extends AsyncTask<String, Void, String>
    {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("Loading data");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            //String url=params[0];

            URL url = null;


            try {

                url = new URL(params[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader isw = new InputStreamReader(in);

                int data = isw.read();
                sb = new StringBuilder();
                while (data != -1) {
                    char current = (char) data;
                    data = isw.read();
                    sb.append(current);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return sb.toString() ;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.cancel();

            String details;
            Log.e("JSON Data------> ", s);
            try {
                JSONArray jsonArray= new JSONArray(s);
                for (int i=0; i< jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name= jsonObject.getString("name");
                    details= jsonObject.getString("details");
                    listRestaurants.add(new Restaurant(name,location,details));
                    Log.e("Name and Location: ", name+"      "+location+"    "+details);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            Intent intent = new Intent(MainActivity.this,SearchedRestaurantListActivity.class);
            intent.putParcelableArrayListExtra("rest_list", listRestaurants);
            startActivity(intent);

        }



    }

}
