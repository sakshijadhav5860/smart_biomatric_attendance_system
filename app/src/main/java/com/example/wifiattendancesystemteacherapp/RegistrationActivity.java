package com.example.wifiattendancesystemteacherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextInputEditText tie_name, tie_mobile_no, tie_email_id, tie_teacher_id, tie_username, tie_password;
    ArrayAdapter<CharSequence> adapter, adapter1;
    Spinner spinner_branch, spinner_sem, spinner_sub;
    Button btn_register;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();

        tie_name = findViewById(R.id.tie_register_name);
        tie_mobile_no = findViewById(R.id.tie_register_mobile_no);
        tie_email_id = findViewById(R.id.tie_register_email_id);
        tie_teacher_id = findViewById(R.id.tie_register_teacher_id);
        tie_username = findViewById(R.id.tie_register_username);
        tie_password = findViewById(R.id.tie_register_password);
        btn_register = findViewById(R.id.btn_register_register);
        progressDialog = new ProgressDialog(this);

        spinner_branch = (Spinner) findViewById(R.id.spinner_branch);
        spinner_sem = (Spinner) findViewById(R.id.spinner_teacher_semester);
        spinner_sub = (Spinner) findViewById(R.id.spinner_teacher_subject);

        adapter = adapter.createFromResource(this,R.array.semester,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sem.setAdapter(adapter);

        spinner_sem.setOnItemSelectedListener(this);
        spinner_branch.setOnItemSelectedListener(this);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tie_name.getText().toString())) {
                    tie_name.setError("Please Enter Your Name");
                } else if (TextUtils.isEmpty(tie_mobile_no.getText().toString())) {
                    tie_mobile_no.setError("Please Enter Your Mobile Number");
                } else if (tie_mobile_no.getText().toString().length() != 10) {
                    tie_mobile_no.setError("Enter 10 Digit Mobile Number");
                } else if (TextUtils.isEmpty(tie_email_id.getText().toString())) {
                    tie_email_id.setError("Please Enter Your Email Id");
                } else if (!tie_email_id.getText().toString().contains(".com") || !tie_email_id.getText().toString().contains("@")) {
                    tie_email_id.setError("Please Enter Valid Email Id");
                } else if (spinner_branch.getSelectedItem().toString().equals("Select Your Branch")) {
                    ((TextView) spinner_branch.getSelectedView()).setError("Please Select Your Branch");
                } else if (spinner_sem.getSelectedItem().toString().equals("Select Your Sem")) {
                    ((TextView) spinner_sem.getSelectedView()).setError("Please Select Your Sem");
                } else if (spinner_sub.getSelectedItem().toString().equals("Select Your Sub")) {
                    ((TextView) spinner_sub.getSelectedView()).setError("Please Select Your Subject");
                } else if (TextUtils.isEmpty(tie_teacher_id.getText().toString())) {
                    tie_teacher_id.setError("Please Enter Your Teacher ID");
                } else if (TextUtils.isEmpty(tie_username.getText().toString())) {
                    tie_username.setError("Please Enter Your Username");
                } else if (TextUtils.isEmpty(tie_password.getText().toString())) {
                    tie_password.setError("Please Enter Your Passsword");
                } else {
                    progressDialog = new ProgressDialog(RegistrationActivity.this);
                    progressDialog.setTitle("Registering User");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();
                    registerTeacher();
                }
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateSubjects();
    }

    private void updateSubjects() {

        String branch = spinner_branch.getSelectedItem().toString();
        int semPosition = spinner_sem.getSelectedItemPosition();

        int subjectArray = R.array.select_your_subject;

        // ================= COMPUTER =================
        if (branch.equals("Computer Engineering")) {

            if (semPosition == 1)
                subjectArray = R.array.computer_first_sem;
            else if (semPosition == 2)
                subjectArray = R.array.computer_second_sem;
            else if (semPosition == 3)
                subjectArray = R.array.computer_third_sem;
            else if (semPosition == 4)
                subjectArray = R.array.computer_fourth_sem;
            else if (semPosition == 5)
                subjectArray = R.array.computer_fifth_sem;
            else if (semPosition == 6)
                subjectArray = R.array.computer_sixth_sem;
        }

        // ================= IT =================
        else if (branch.equals("Information Technology")) {

            if (semPosition == 1)
                subjectArray = R.array.it_first_sem;
            else if (semPosition == 2)
                subjectArray = R.array.it_second_sem;
            else if (semPosition == 3)
                subjectArray = R.array.it_third_sem;
            else if (semPosition == 4)
                subjectArray = R.array.it_fourth_sem;
            else if (semPosition == 5)
                subjectArray = R.array.it_fifth_sem;
            else if (semPosition == 6)
                subjectArray = R.array.it_sixth_sem;
        }

        // ================= E&TC =================
        else if (branch.equals("Electronics and Telecommunication")) {

            if (semPosition == 1)
                subjectArray = R.array.etc_first_sem;
            else if (semPosition == 2)
                subjectArray = R.array.etc_second_sem;
            else if (semPosition == 3)
                subjectArray = R.array.etc_third_sem;
            else if (semPosition == 4)
                subjectArray = R.array.etc_fourth_sem;
            else if (semPosition == 5)
                subjectArray = R.array.etc_fifth_sem;
            else if (semPosition == 6)
                subjectArray = R.array.etc_sixth_sem;
        }

        // ================= CIVIL =================
        else if (branch.equals("Civil Engineering")) {

            if (semPosition == 1)
                subjectArray = R.array.civil_first_sem;
            else if (semPosition == 2)
                subjectArray = R.array.civil_second_sem;
            else if (semPosition == 3)
                subjectArray = R.array.civil_third_sem;
            else if (semPosition == 4)
                subjectArray = R.array.civil_fourth_sem;
            else if (semPosition == 5)
                subjectArray = R.array.civil_fifth_sem;
            else if (semPosition == 6)
                subjectArray = R.array.civil_sixth_sem;
        }

        // ================= MECHANICAL =================
        else if (branch.equals("Mechanical Engineering")) {

            if (semPosition == 1)
                subjectArray = R.array.mechanical_first_sem;
            else if (semPosition == 2)
                subjectArray = R.array.mechanical_second_sem;
            else if (semPosition == 3)
                subjectArray = R.array.mechanical_third_sem;
            else if (semPosition == 4)
                subjectArray = R.array.mechanical_fourth_sem;
            else if (semPosition == 5)
                subjectArray = R.array.mechanical_fifth_sem;
            else if (semPosition == 6)
                subjectArray = R.array.mechanical_sixth_sem;
        }

        // ================= ELECTRICAL =================
        else if (branch.equals("Electrical Engineering")) {

            if (semPosition == 1)
                subjectArray = R.array.electrical_first_sem;
            else if (semPosition == 2)
                subjectArray = R.array.electrical_second_sem;
            else if (semPosition == 3)
                subjectArray = R.array.electrical_third_sem;
            else if (semPosition == 4)
                subjectArray = R.array.electrical_fourth_sem;
            else if (semPosition == 5)
                subjectArray = R.array.electrical_fifth_sem;
            else if (semPosition == 6)
                subjectArray = R.array.electrical_sixth_sem;
        }

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this,
                        subjectArray,
                        android.R.layout.simple_spinner_dropdown_item
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinner_sub.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void registerTeacher() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.urlRegisterTeacher,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            String success = obj.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                                Toast.makeText(RegistrationActivity.this, "Registration Successfully Done", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else {
                                String message = obj.getString("message");
                                Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_SHORT).show();
                                Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(RegistrationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", tie_name.getText().toString());
                params.put("mobile_no", tie_mobile_no.getText().toString());
                params.put("email_id", tie_email_id.getText().toString());
                params.put("teacher_id", tie_teacher_id.getText().toString());
                params.put("branch", spinner_branch.getSelectedItem().toString());
                params.put("sem", spinner_sem.getSelectedItem().toString());
                params.put("subject", spinner_sub.getSelectedItem().toString());
                params.put("username", tie_username.getText().toString());
                params.put("password", tie_password.getText().toString());
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        RequestQueue requestQueue = Volley.newRequestQueue(RegistrationActivity.this);
        requestQueue.add(stringRequest);
    }
}