package com.example.ham.mkbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class login_Register extends AppCompatActivity {
    LinearLayout Register_just,login_and_Register,login_just;
    EditText name,email,password,password_agian,name_login,password_login;
    RequestQueue requestQueue;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__register);
        Register_just=(LinearLayout)findViewById(R.id.Register_just);
        login_just=(LinearLayout)findViewById(R.id.login_just);
        login_and_Register=(LinearLayout)findViewById(R.id.login_and_Register);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        password_agian=(EditText)findViewById(R.id.password_again);
        name_login=(EditText)findViewById(R.id.name_login);
        password_login=(EditText)findViewById(R.id.password_login);
        progressBar=(ProgressBar) findViewById(R.id.Progress);
        progressBar.setRotation(1.5f);



    }

    public void Register(View view) {
        login_and_Register.setVisibility(view.GONE);
        Register_just.setVisibility(view.VISIBLE);
    }

    public void login(View view) {

        login_and_Register.setVisibility(view.GONE);
        login_just.setVisibility(view.VISIBLE);
    }

    public void Register_2(View view) {

        sharedPreferences=this.getSharedPreferences("MK",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        final String pname=name.getText().toString().trim();
        String pemail=email.getText().toString().trim();
        String ppassword=password.getText().toString().trim();
        String password_ag=password_agian.getText().toString().trim();

        if (ppassword.equals(password_ag)){
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success==true) {

                        } else {
                            Toast.makeText(login_Register.this, "فشل في الارسال  كرر  العمليه مره اخرى", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            Register_send_file register_send_file = new Register_send_file(pname, pemail, ppassword, listener);
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(register_send_file);
        }
        else {
            Toast.makeText(login_Register.this,"تأكد من كلمة السر",Toast.LENGTH_LONG).show();
        }

        editor.putString("mk",pname);
        editor.apply();
        startActivity(new Intent(login_Register.this,MainActivity.class));
        finish();

    }

    public void login_2(View view) {
        sharedPreferences=this.getSharedPreferences("MK",MODE_PRIVATE);
        editor=sharedPreferences.edit();


        final String pname=name_login.getText().toString().trim();
        String ppassword=password_login.getText().toString().trim();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success==true) {
                        startActivity(new Intent(login_Register.this,MainActivity.class));
                        editor.putString("mk",pname);
                        editor.apply();
                        finish();
                    } else {
                        Toast.makeText(login_Register.this, "فشل في الارسال  كرر  العمليه مره اخرى", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        login_send_file login_send_file = new login_send_file(pname, ppassword, listener);
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(login_send_file);

    }


}
