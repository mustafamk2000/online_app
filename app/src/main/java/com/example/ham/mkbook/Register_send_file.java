package com.example.ham.mkbook;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Register_send_file extends StringRequest {

    private  static  final  String  SEND_DATA_URL="https://mustafa2000676.000webhostapp.com/Registration.php";
    private java.util.Map<String,String> map;

    public Register_send_file(String name,String email,String password ,Response.Listener<String> listener) {
        super(Method.POST, SEND_DATA_URL,listener, null);
        map=new HashMap<>();
        map.put("name",name);
        map.put("email",email);
        map.put("password",password);

    }

    @Override
    protected Map<String, String> getParams(){
        return  map;

    }
}
