package com.above.volleysample.volleysample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SearchedRestaurantListActivity extends AppCompatActivity {
    RestaurentAdapter restaurentAdapter;
    private ArrayList<Parcelable> list = new ArrayList<>();
    String detailsUrl;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_searched_restaurant_list);

        // get data from intent
        Intent intent = getIntent();
        list =  intent.getParcelableArrayListExtra("rest_list");
        if (list.size()==0)
        {
            new AlertDialog.Builder(SearchedRestaurantListActivity.this) .setTitle("Search Result") .setMessage("Restaurants not found") .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
            { public void onClick(DialogInterface dialog, int which)
                {
                    finish();
                 } }) .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                  { public void onClick(DialogInterface dialog, int which)
                 {
                     finish();
                 }
                  }) .setIcon(android.R.drawable.ic_dialog_alert) .show();



        }
        Log.e("String JSON----> ",list+"");



        restaurentAdapter = new RestaurentAdapter(SearchedRestaurantListActivity.this, R.layout.item_list, list);

        listView = (ListView) findViewById(R.id.custemListView);
        listView.setAdapter(restaurentAdapter);
       // restaurentAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant rest = (Restaurant) list.get(position);
                // Log.e("NAME OF RESTAURANT : ", rest.toString());
                detailsUrl = rest.getDetailsUrl();

                GetDetailsTask task = new GetDetailsTask();
                task.execute(detailsUrl);


            }
        });
        //list.clear();
        restaurentAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
    restaurentAdapter.notifyDataSetChanged();

    }



    class GetDetailsTask extends AsyncTask<String,Void,String>
    {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(SearchedRestaurantListActivity.this);
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("Loading data");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            StringBuilder sb = new StringBuilder();

            try {
                URL url= new URL(params[0]);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                InputStream in= connection.getInputStream();
                InputStreamReader isr=new InputStreamReader(in);
                int data = isr.read();

                while (data!=-1)
                {
                    char c= (char)data;
                    sb.append(c);
                    data=isr.read();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.e("Details json---> ", sb.toString());
            return sb+"";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.cancel();
            // parse JSON
            try {
                JSONObject jsonObject= new JSONObject(s);
                String name = jsonObject.getString("name") ;
                String location= jsonObject.getString("location") ;
                String address= jsonObject.getString("address") ;
                String phone_number= jsonObject.getString("phone_number") ;
                String website= jsonObject.getString("website") ;


                Intent intent = new Intent(SearchedRestaurantListActivity.this,DetailsOfRestaurantActivity.class);

                intent.putExtra("details",new Details(name,address,location,phone_number,website));
                startActivity(intent);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}

    class RestaurentAdapter extends BaseAdapter
    {

        Context context;
        int itemId;
        ArrayList<Parcelable> list1;


        RestaurentAdapter(Context context,int itemId,ArrayList<Parcelable> list1)
        {
            this.context = context;
            this.itemId = itemId;
            this.list1 = list1;
        }

        @Override
        public int getCount() {
            return list1.size();
        }

        @Override
        public Object getItem(int position) {
            return list1.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            TextView textName,textLocation;

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(itemId, null);

            textName = (TextView) view.findViewById(R.id.textViewItemName);
            textLocation=(TextView)view.findViewById(R.id.textViewItemLocation);
            Restaurant r= (Restaurant) list1.get(position);

            textName.setText(r.getNameOfRestaurant());
            textLocation.setText(r.getLocationOfRestaurant());

            return  view;
        }

    }

