package com.above.volleysample.volleysample;

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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private Spinner spinner;
    private String[] listOfLocations = {"Amsterdam","Barcelona","Berlin","Dubay","London","Paris","Rome","Tuscany"};
    String location="";
    ArrayList<Restaurant> listRestaurants= new ArrayList<>();
   // String jsonData;
    String details;


    RequestQueue requestQueue;

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
                    location = spinner.getSelectedItem()+"";
                    Log.e("Location selected--> ",location);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void searchRestaurants(View v)
    {
        String url="http://tour-pedia.org/api/getPlaces?category=restaurant";

        DownloadTask task= new DownloadTask();
        task.execute(url);

        Intent intent = new Intent(MainActivity.this,SearchedRestaurantListActivity.class);
       // intent.putParcelableArrayListExtra("rest_list", (ArrayList<? extends Parcelable>) listRestaurants);
        intent.putExtra("details",details);
        startActivity(intent);

    }

    class DownloadTask extends AsyncTask<String, Void, String>
    {




        @Override
        protected String doInBackground(String... params) {

            String url=params[0];

            Log.e("Location---->","Inside doInbackground   URL --> "+ url);
            requestQueue= Volley.newRequestQueue(MainActivity.this);

            JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url,

                    new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                    try {

                        JSONArray jsonArray= jsonObject.getJSONArray("restaurants");
                        Log.e("JSON ARRAY----> ",jsonArray+"");
                       // jsonData= jsonArray+"";

                        for (int i=0 ; i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String name=jsonObject1.getString("name");
                            String location1=jsonObject1.getString("location");
                            if(location1==location) {
                                Restaurant r = new Restaurant(name, location);
                                listRestaurants.add(r);

                                Log.e("Restaurant Object----> ", r.toString());
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyError.printStackTrace();
                }
            });


            int socketTimeout = 70000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);
            requestQueue.add(request);

            return "done";
        }

       /* @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            for (int i=0; i<listRestaurants.size();i++)
            {
                 listRestaurants.get(i);
            }
        }*/
    }


}
