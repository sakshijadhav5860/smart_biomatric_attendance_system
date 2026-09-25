package com.example.wifiattendancesystemteacherapp.AddAttendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.wifiattendancesystemteacherapp.Comman.Urls;
import com.example.wifiattendancesystemteacherapp.HomeActivity;
import com.example.wifiattendancesystemteacherapp.R;
import com.example.wifiattendancesystemteacherapp.RegistrationActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class AddAttendanceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner_select_branch,spinner_select_sem,spinner_select_subject;
    AppCompatButton btn_add_attendance;
    TextView tv_date,tv_time_from,tv_time_to;
    ProgressDialog progressDialog;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ArrayAdapter<CharSequence> adapter, adapter1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendance);
        preferences = PreferenceManager.getDefaultSharedPreferences(AddAttendanceActivity.this);
        editor = preferences.edit();

        setTitle("Add Current Attendance");

        spinner_select_branch = findViewById(R.id.spinner_branch);
        spinner_select_sem = findViewById(R.id.spinner_teacher_semester);
        spinner_select_subject = findViewById(R.id.spinner_teacher_subject);
        tv_date = findViewById(R.id.tv_add_presenty_date);
        tv_time_from = findViewById(R.id.tv_add_presenty_time_from);
        tv_time_to = findViewById(R.id.tv_add_presenty_time_to);

        adapter = adapter.createFromResource(this,R.array.semester,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_select_sem.setAdapter(adapter);

        spinner_select_sem.setOnItemSelectedListener(this);

        btn_add_attendance = findViewById(R.id.btn_add_presenty_add_presenty);


        // on below line we are adding click listener for our pick date button
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        AddAttendanceActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                tv_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        // on below line we are adding click
        // listener for our pick date button
        tv_time_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the
                // instance of our calendar.
                // Getting the current current time
                Date date = new Date();
                // set format in 12 hours
                SimpleDateFormat formatTime = new SimpleDateFormat("hh.mm aa");
                // hh = hours in 12hr format
                // mm = minutes
                // aa = am/pm
                // display time as per format
                String time = formatTime.format(
                        date); // changing the format of 'date'

                // display time as per format
                System.out.println(
                        "Current Time in AM/PM Format is : " + time);
                Toast.makeText(AddAttendanceActivity.this, ""+time, Toast.LENGTH_SHORT).show();
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hour, minute.
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddAttendanceActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // on below line we are setting selected time
                                // in our text view.
                                tv_time_from.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, true);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });

        // on below line we are adding click
        // listener for our pick date button
        tv_time_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the
                // instance of our calendar.
                // Getting the current current time
                Date date = new Date();
                // set format in 12 hours
                SimpleDateFormat formatTime = new SimpleDateFormat("hh.mm aa");
                // hh = hours in 12hr format
                // mm = minutes
                // aa = am/pm
                // display time as per format
                String time = formatTime.format(
                        date); // changing the format of 'date'

                // display time as per format
                System.out.println(
                        "Current Time in AM/PM Format is : " + time);
                Toast.makeText(AddAttendanceActivity.this, ""+time, Toast.LENGTH_SHORT).show();
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hour, minute.
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddAttendanceActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // on below line we are setting selected time
                                // in our text view.
                                tv_time_to.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, true);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });


        btn_add_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_date.getText().toString().isEmpty()) {
                    tv_date.setError("Please Enter Date");
                } else if (tv_time_from.getText().toString().isEmpty()) {
                    tv_time_from.setError("Please Enter Start Time");
                } else if (TextUtils.isEmpty(tv_time_to.getText().toString())) {
                    tv_time_to.setError("Please Enter End Time");
                } else if (spinner_select_branch.getSelectedItem().toString().equals("Select Your Branch")) {
                    ((TextView) spinner_select_branch.getSelectedView()).setError("Please Select Your Branch");
                } else if (spinner_select_sem.getSelectedItem().toString().equals("Select Your Sem")) {
                    ((TextView) spinner_select_sem.getSelectedView()).setError("Please Select Your Sem");
                } else if (spinner_select_subject.getSelectedItem().toString().equals("Select Your Sub")) {
                    ((TextView) spinner_select_subject.getSelectedView()).setError("Please Select Your Subject");
                } else {
                    progressDialog = new ProgressDialog(AddAttendanceActivity.this);
                    progressDialog.setTitle("Registering User");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();
                    addAttendance();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String branch = spinner_select_branch.getSelectedItem().toString();

        ArrayAdapter<CharSequence> adapter = null;

        if (branch.equals("Computer Engineering")) {

            if (position == 1) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.computer_first_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 2) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.computer_second_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 3) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.computer_third_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 4) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.computer_fourth_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 5) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.computer_fifth_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 6) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.computer_sixth_sem,
                        android.R.layout.simple_spinner_dropdown_item);
            }

        } else if (branch.equals("Information Technology")) {

            if (position == 1) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.it_first_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 2) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.it_second_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 3) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.it_third_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 4) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.it_fourth_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 5) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.it_fifth_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 6) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.it_sixth_sem,
                        android.R.layout.simple_spinner_dropdown_item);
            }

        } else if (branch.equals("Electronics and Telecommunication")) {

            if (position == 1) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.etc_first_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 2) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.etc_second_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 3) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.etc_third_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 4) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.etc_fourth_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 5) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.etc_fifth_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 6) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.etc_sixth_sem,
                        android.R.layout.simple_spinner_dropdown_item);
            }

        } else if (branch.equals("Civil Engineering")) {

            if (position == 1) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.civil_first_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 2) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.civil_second_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 3) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.civil_third_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 4) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.civil_fourth_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 5) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.civil_fifth_sem,
                        android.R.layout.simple_spinner_dropdown_item);

            } else if (position == 6) {
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.civil_sixth_sem,
                        android.R.layout.simple_spinner_dropdown_item);
            }
        }

        if (adapter != null) {
            spinner_select_subject.setAdapter(adapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    private void addAttendance() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("branch",spinner_select_branch.getSelectedItem().toString());
        params.put("sem",spinner_select_sem.getSelectedItem().toString());
        params.put("subject",spinner_select_subject.getSelectedItem().toString());
        params.put("date",tv_date.getText().toString());
        params.put("time_from",tv_time_from.getText().toString());
        params.put("time_to",tv_time_to.getText().toString());

        client.post(Urls.urlAddAttendance, params, new JsonHttpResponseHandler(){

            public void onStart()
            {
                progressDialog.show();
                super.onStart();
            }

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String aa = response.getString("success");

                    if (aa.equals("1")) {
                        Intent intent = new Intent(AddAttendanceActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(AddAttendanceActivity.this, "Unable to Add Attendance", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, String res, Throwable t)
            {
                progressDialog.dismiss();
                Toast.makeText(AddAttendanceActivity.this, "could not connect", Toast.LENGTH_LONG).show();

            }

        });
    }
}