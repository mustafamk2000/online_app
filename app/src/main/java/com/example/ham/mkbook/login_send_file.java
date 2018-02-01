package com.example.ham.mkbook;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by H.A.M on 1/31/2018.
 */


public class login_send_file extends StringRequest {
    private  static  final  String SEND_DATA = "https://mustafa2000676.000webhostapp.com/mk_project.php";
    private java.util.Map<String,String> map;

    public login_send_file(String name, String password, Response.Listener<String> stringListener){
        super(Request.Method.POST,SEND_DATA,stringListener,null);//داله POST لارسال البيانات عكس دالهGET
        //وايضا لارسال بيانات بكميه كبيره
        map=new HashMap<>();
        map.put("Login_name",name);
        map.put("Login_pass",password);}

    @Override
    public Map<String, String> getParams(){//هنا يتم ارسال بيانات map
        return map;
    }
}
