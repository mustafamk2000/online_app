package com.example.ham.mkbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class send_posts extends AppCompatActivity {

    ImageView imageView;
    EditText text;
    String encoding;
    RequestQueue requestQueue;
    Button Done_two;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_posts);
        imageView=(ImageView)findViewById(R.id.image_view_Enter);
        text=(EditText) findViewById(R.id.enter_text);
        Done_two=(Button)findViewById(R.id.Done_two);
    }
    public void choose_image(View view) {

        Intent intent  = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra("crop","true");
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",2500);
        intent.putExtra("outputY",2500);
        intent.putExtra("return-data",true);
        startActivityForResult(intent,100);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && resultCode==RESULT_OK){
            imageView.setImageBitmap((Bitmap)data.getExtras().get("data"));
            imageView.setVisibility(v.VISIBLE);
        }

    }

    public void Done(View view) {
        SharedPreferences sharedPreferences=this.getSharedPreferences("MK",MODE_PRIVATE);
        String name =sharedPreferences.getString("mk","");

        //===================================================
        //هذه  لتحويل الصوره الى String
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();//يجلب الصوره من mageview
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,byteArrayOutputStream);
        //compress  نعمل ضغط للصوره
        //   جوده الصوره يفضل ان يكون 70 ما يزيد عن 80 لكي الرفع الى السيرفر يكون سريع

        encoding= Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);

        //===================================================

        String ptext = text.getText().toString().trim();

        Response.Listener<String> stringListener  = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success){
                        Toast.makeText(getApplicationContext(),"تم النشر ",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"حدث خطأ",Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        send_post send = new send_post(ptext,encoding,name,stringListener);
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(send);
        startActivity(new Intent(getApplicationContext(),splash_screen.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity( new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}
