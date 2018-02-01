package com.example.ham.mkbook;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class send_post extends StringRequest {
    private  static  final  String SEND_DATA = "https://mustafa2000676.000webhostapp.com/math.php";
    private java.util.Map<String,String> map;

    public send_post(String text, String image,String name, Response.Listener<String> stringListener){
        super(Request.Method.POST,SEND_DATA,stringListener,null);//داله POST لارسال البيانات عكس دالهGET
        //وايضا لارسال بيانات بكميه كبيره
        map=new HashMap<>();
        map.put("text",text);
        map.put("image",image);
        map.put("name",name);
    }

    @Override
    public Map<String, String> getParams(){//هنا يتم ارسال بيانات map
        return map;
    }
}

