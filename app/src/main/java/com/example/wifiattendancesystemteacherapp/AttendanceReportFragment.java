package com.example.wifiattendancesystemteacherapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class AttendanceReportFragment extends Fragment {

    Spinner spinnerSubject, spinnerDate;
    Button btnReport;
    TextView txtPresent, txtAbsent,txtTotalStudents;

    ArrayList<String> subjectList = new ArrayList<>();
    ArrayList<String> dateList = new ArrayList<>();
    String subject;
    String date;

    String baseUrl = "http://10.56.207.223:80/WIFIAttedanceSystem/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attendance_report, container, false);

        spinnerSubject = view.findViewById(R.id.spinner_subject);
        spinnerDate = view.findViewById(R.id.spinner_date);
        btnReport = view.findViewById(R.id.btn_report);
        txtPresent = view.findViewById(R.id.txt_present);
        txtAbsent = view.findViewById(R.id.txt_absent);
        txtTotalStudents = view.findViewById(R.id.txtTotalStudents);





        getTotalStudents(subject, date);
        loadSubjects();
        loadDates();

        btnReport.setOnClickListener(v -> {

            String subject = spinnerSubject.getSelectedItem().toString();
            String date = spinnerDate.getSelectedItem().toString();

            getReport();
            getTotalStudents(subject, date);
        });

        return view;
    }

    private void loadSubjects() {

        StringRequest request = new StringRequest(
                Request.Method.GET,
                baseUrl + "get_subjectsteacher.php",
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        subjectList.clear();

                        for (int i = 0; i < array.length(); i++) {
                            subjectList.add(array.getString(i));
                        }

                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<>(
                                        requireContext(),
                                        android.R.layout.simple_spinner_item,
                                        subjectList
                                );

                        adapter.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item
                        );

                        spinnerSubject.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        Toast.makeText(getContext(),
                                e.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getContext(),
                        error.toString(),
                        Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(requireContext()).add(request);


    }

    private void loadDates() {

        StringRequest request = new StringRequest(
                Request.Method.GET,
                baseUrl + "get_dates.php",
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        dateList.clear();

                        for (int i = 0; i < array.length(); i++) {
                            dateList.add(array.getString(i));
                        }

                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<>(
                                        requireContext(),
                                        android.R.layout.simple_spinner_item,
                                        dateList
                                );

                        adapter.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item
                        );

                        spinnerDate.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        Toast.makeText(getContext(),
                                e.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getContext(),
                        error.toString(),
                        Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(requireContext()).add(request);

    }
    private void getTotalStudents(String subject, String date) {

        StringRequest request = new StringRequest(
                Request.Method.POST,
                baseUrl + "get_total_students.php",

                response -> {
                    try {
                        JSONObject object = new JSONObject(response);

                        String total = object.getString("total");

                        txtTotalStudents.setText("Total Students: " + total);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(),
                                e.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                },

                error -> Toast.makeText(getContext(),
                        error.toString(),
                        Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("subject_name", subject);
                map.put("date", date);
                return map;
            }
        };

        Volley.newRequestQueue(getContext()).add(request);
    }

    private void getReport() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                baseUrl + "get_attendance_report.php",
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);

                        txtPresent.setText("Present: " + obj.getString("present"));
                        txtAbsent.setText("Absent: " + obj.getString("absent"));

                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("subject_name",
                        spinnerSubject.getSelectedItem().toString());

                params.put("date",
                        spinnerDate.getSelectedItem().toString());

                return params;
            }
        };

        Volley.newRequestQueue(requireContext()).add(request);
    }
}