package com.example.ham.mkbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;

    ListView listView;
    ArrayList<rows_item> arrayList=new ArrayList<rows_item>();
    String url ="https://mustafa2000676.000webhostapp.com/show_data.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.list_view);



        requestQueue = Volley.newRequestQueue(this);

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,  //كود طلب البيانات من الرابط url
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("mk");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject respons = jsonArray.getJSONObject(i);//JSONObject =>{data is here}
                                String name = respons.getString("name");
                                String img = respons.getString("image");
                                String text = respons.getString("text");
                                arrayList.add(new rows_item(name, text, img));
                                all_items();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("VOLLEY", "ERROR");
            }
        }
        );
        requestQueue.add(jsonObjectRequest);

    }


    class  Ad  extends BaseAdapter {
        ArrayList<rows_item> Alist = new ArrayList<>();

        public Ad(ArrayList<rows_item> Alist) {
            this.Alist = Alist;
        }


        @Override
        public int getCount() {
            return Alist.size();
        }

        @Override
        public Object getItem(int i) {
            return Alist.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View v = layoutInflater.inflate(R.layout.rows, null);
            TextView name = (TextView) v.findViewById(R.id.name_of_user);
            TextView text = (TextView) v.findViewById(R.id.text_of_post);
            ImageView img = (ImageView) v.findViewById(R.id.image_view);

            name.setText(Alist.get(i).name);
            text.setText(Alist.get(i).text);
            Picasso.with(MainActivity.this).load("https://mustafa2000676.000webhostapp.com/image/"+ Alist.get(i).image).into(img);
            return v;
        }

    }
    public  void  all_items(){
        Ad ad = new Ad(arrayList);
        listView.setAdapter(ad);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);//لجلب القائمه
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();//لجلب id لكل ايقونه

        if (id == R.id.plus) {

            startActivity(new Intent(MainActivity.this,send_posts.class));
            finish();

        }

        return super.onOptionsItemSelected(item);
    }



}
