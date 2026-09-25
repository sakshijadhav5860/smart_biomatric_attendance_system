package com.example.wifiattendancesystemteacherapp.AllStudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wifiattendancesystemteacherapp.Comman.Urls;
import com.example.wifiattendancesystemteacherapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ViewStudentPresentyActivity extends AppCompatActivity {

    List<PojoClassViewStudentPresenty> list;
    ListView lv_view_attendence;
    TextView tv_no_records,tv_present_in_count,tv_present_ratio;
    ProgressBar pBar;
    ViewStudentPresentyAdapter adapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int enrollment_no_count;
    String enrollment_no;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_presenty);

        preferences= PreferenceManager.getDefaultSharedPreferences(ViewStudentPresentyActivity.this);
        editor=preferences.edit();

        Intent intent = getIntent();
        enrollment_no = intent.getStringExtra("enrollment_no");
        Toast.makeText(this, ""+enrollment_no, Toast.LENGTH_SHORT).show();

        list = new ArrayList<PojoClassViewStudentPresenty>();
        lv_view_attendence = (ListView) findViewById(R.id.lv_view_presenty);
        tv_no_records = (TextView) findViewById(R.id.tv_no_records);
        pBar = (ProgressBar) findViewById(R.id.pBar);
        tv_no_records = (TextView) findViewById(R.id.tv_no_records);
        tv_present_in_count = (TextView) findViewById(R.id.tv_present_in_count);
        tv_present_ratio = (TextView) findViewById(R.id.tv_present_ration);

        getmypresenty(enrollment_no);
    }

    public void getmypresenty(String enrollment_no)
    {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("enrollment_no",enrollment_no);

        client.post(Urls.urlGetStudentAttendance, params, new JsonHttpResponseHandler(){

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    pBar.setVisibility(View.GONE);

                    JSONArray jarry = response.getJSONArray("getStudentAttendance");
                    for (int i = 0 ; i < jarry.length();i++)
                    {
                        JSONObject jsonObject = jarry.getJSONObject(i);
                        String date = jsonObject.getString("date");
                        String subject_name = jsonObject.getString("subject_name");
                        String presenty = jsonObject.getString("presenty");

                        Toast.makeText(ViewStudentPresentyActivity.this, ""+date, Toast.LENGTH_SHORT).show();

                        list.add(new PojoClassViewStudentPresenty(date,subject_name,presenty));
                    }

                    adapter = new ViewStudentPresentyAdapter(list, (AppCompatActivity) ViewStudentPresentyActivity.this,tv_no_records);
                    lv_view_attendence.setAdapter(adapter);

                    getAllPresentyCount();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, String res, Throwable t)
            {
                Toast.makeText(ViewStudentPresentyActivity.this, "could not connect", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void getAllPresentyCount() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("enrollment_no",enrollment_no);

        client.post(Urls.urlGetAllPresentyCount, params, new JsonHttpResponseHandler(){

            public void onStart()
            {
                pBar.setVisibility(View.VISIBLE);
                super.onStart();
            }

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    pBar.setVisibility(View.GONE);

                    JSONArray jarry = response.getJSONArray("getAllPresentyCount");
                    for (int i = 0 ; i < jarry.length();i++)
                    {
                        JSONObject jsonObject = jarry.getJSONObject(i);
                        enrollment_no_count = jsonObject.getInt("enrollment_no_count");
                        tv_present_in_count.setText("Total Present In "+enrollment_no_count+" Lectures");
                    }
                    float presenty_count = Float.parseFloat(String.valueOf(enrollment_no_count));
                    String final_ration = ((presenty_count / 30) * 100) + "";
                    tv_present_ratio.setText("Total Ratio is "+final_ration);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, String res, Throwable t)
            {
                Toast.makeText(ViewStudentPresentyActivity.this, "could not connect", Toast.LENGTH_LONG).show();

            }

        });

    }

}