package com.above.volleysample.volleysample;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class SearchedRestaurantListActivity extends AppCompatActivity {
    RestaurentAdapter restaurentAdapter;
    private ArrayList<Restaurant> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_restaurant_list);
            list.add(new Restaurant("Park Inn", "Bangalore"));
            list.add(new Restaurant("Sayaji", "Indore"));

        restaurentAdapter= new RestaurentAdapter(SearchedRestaurantListActivity.this,R.layout.item_list,list);
            ListView listView = (ListView) findViewById(R.id.custemListView);
            listView.setAdapter(restaurentAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Restaurant rest = list.get(position);
                    Intent intent = new Intent(SearchedRestaurantListActivity.this,DetailsOfRestaurantActivity.class);

                    intent.putExtra("RESTAURANT",rest);
                    startActivity(intent);
                }
            });
        }
    }

    class RestaurentAdapter extends BaseAdapter
    {

        Context context;
        int itemId;
        ArrayList<Restaurant> list1;


        RestaurentAdapter(Context context,int itemId,ArrayList<Restaurant> list1)
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

            TextView textName;

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(itemId, null);

            textName = (TextView) view.findViewById(R.id.textViewItemName);
            textName.setText(list1.get(position).toString());

            return  view;
        }
    }

