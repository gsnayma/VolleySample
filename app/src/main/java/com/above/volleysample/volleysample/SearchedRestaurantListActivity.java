package com.above.volleysample.volleysample;

import android.content.Context;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_restaurant_list);


          // list.add(new Restaurant("Park Inn", "Bangalore"));
          //  list.add(new Restaurant("Sayaji", "Indore"));

        list.clear();


        // get data from intent
        Intent intent = getIntent();
        list =  intent.getParcelableArrayListExtra("rest_list");
        Log.e("String JSON----> ",list+"");



        restaurentAdapter = new RestaurentAdapter(SearchedRestaurantListActivity.this, R.layout.item_list, list);

        ListView listView = (ListView) findViewById(R.id.custemListView);
        listView.setAdapter(restaurentAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Restaurant rest = (Restaurant) list.get(position);
                    // Log.e("NAME OF RESTAURANT : ", rest.toString());
                    detailsUrl=rest.getDetailsUrl();

                    GetDetailsTask task=new GetDetailsTask();
                    task.execute(detailsUrl);


                }
            });
        }

    @Override
    protected void onRestart() {
        super.onRestart();
        list.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        list.clear();
    }

    class GetDetailsTask extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... params) {

            StringBuffer sb = null;

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

            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // parse JSON

            String name = "";
            String location="";



            Intent intent = new Intent(SearchedRestaurantListActivity.this,DetailsOfRestaurantActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("location",location);
            startActivity(intent);
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

