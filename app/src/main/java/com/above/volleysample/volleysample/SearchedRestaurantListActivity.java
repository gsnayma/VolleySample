package com.above.volleysample.volleysample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchedRestaurantListActivity extends AppCompatActivity {
    NewsAdapter newAdapter;
    private ArrayList<News> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_restaurant_list);


            list.add(new News("First Title", "First Description"));
            list.add(new News("Second Title", "Second Description"));
            list.add(new News("Third Title","Third Description"));
            list.add(new News("Fourth Title","Fourth Description"));
            list.add(new News("Fifth Title","Fifth Description"));

            newAdapter= new NewsAdapter(SearchedRestaurantListActivity.this,R.layout.item_list,list);
            ListView listView = (ListView) findViewById(R.id.custemListView);
            listView.setAdapter(newAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    News n = list.get(position);
                    // Log.e("Value of n : ",n.toString());
                    Intent intent = new Intent(ListActivity.this,DetailsActivity.class);

                    intent.putExtra("NEWS",n);
                    startActivity(intent);
                }
            });
        }
    }

    class NewsAdapter extends BaseAdapter
    {

        Context context;
        int itemId;
        ArrayList<News> list1;

        NewsAdapter(Context context,int itemId,ArrayList<News> list1)
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

            ImageView imageView;
            TextView textViewHeading;
            TextView textViewDescription;

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(itemId, null);

            textViewHeading = (TextView) view.findViewById(R.id.textViewItemHeading);
            // textViewDescription = (TextView) view.findViewById(R.id.textViewItemDescription);
            textViewHeading.setText(list1.get(position).toString());
            // textViewDescription.setText(list1.get(position).toString());

            return  view;
        }
    }


}
