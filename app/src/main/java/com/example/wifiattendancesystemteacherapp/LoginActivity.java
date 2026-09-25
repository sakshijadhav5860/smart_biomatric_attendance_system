package com.example.wifiattendancesystemteacherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wifiattendancesystemteacherapp.Comman.Urls;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 0000;
    TextInputEditText tie_login_username,tie_login_password;
    ImageView img_login_logo;;
    Button btn_login_login,btn_login_register;
    ActivityOptions options;
    ProgressDialog progressDialog;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login Page");
        getSupportActionBar().hide();

        preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        editor = preferences.edit();

        if (preferences.getBoolean("isLogin",false))
        {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        img_login_logo = findViewById(R.id.img_login_logo);
        tie_login_username = findViewById(R.id.tie_login_username);
        tie_login_password = findViewById(R.id.tie_login_password);
        btn_login_login = findViewById(R.id.btn_login_login);
        btn_login_register = findViewById(R.id.btn_login_register);

        btn_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setTitle("Login User");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();
                loginTeacher();
            }
        });

        btn_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);

                        Pair[] pairs = new Pair[2];
                        pairs[0] = new Pair<View,String>(img_login_logo,"splash_logo");
//                    pairs[0] = new Pair<View,String>(splash_app_title,"splash_title");


                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs[0]);
                        }
                        startActivity(intent,options.toBundle());

                    }
                },SPLASH_SCREEN);
            }
        });
    }

    private void loginTeacher() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.urlLoginTeacher,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            String success = obj.getString("success");
                            String id = obj.getString("id");
                            String name = obj.getString("name");
                            String mobile_no = obj.getString("mobile_no");
                            String teacher_id = obj.getString("teacher_id");
                            String email_id = obj.getString("email_id");
                            String branch = obj.getString("branch");
                            String sem = obj.getString("sem");
                            String subject = obj.getString("subject");
                            String username = obj.getString("username");
                            String password = obj.getString("password");

                            if (success.equals("1"))
                            {
                                Toast.makeText(LoginActivity.this, response,Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                editor.putBoolean("isLogin",true).commit();
                                editor.putString("id",id).commit();
                                editor.putString("name",name).commit();
                                editor.putString("mobile_no",mobile_no).commit();
                                editor.putString("email_id",email_id).commit();
                                editor.putString("branch",branch).commit();
                                editor.putString("sem",sem).commit();
                                editor.putString("subject",subject).commit();
                                editor.putString("teacher_id",teacher_id).commit();
                                editor.putString("username",username).commit();
                                editor.putString("password",password).commit();
                                Toast.makeText(LoginActivity.this, ""+id.toString(), Toast.LENGTH_SHORT).show();
                                editor.putString("username",tie_login_username.getText().toString()).commit();
                                Toast.makeText(LoginActivity.this,"Login Successfully Done",Toast.LENGTH_SHORT).show();
                                startActivity(i);
                                finish();
                                progressDialog.dismiss();
                            }else {
                                String message = obj.getString("message");
                                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();
                                Toast.makeText(LoginActivity.this, response,Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username", tie_login_username.getText().toString());
                params.put("password", tie_login_password.getText().toString());
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);

    }
}