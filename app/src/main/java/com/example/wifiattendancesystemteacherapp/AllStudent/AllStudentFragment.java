package com.example.wifiattendancesystemteacherapp.AllStudent;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.wifiattendancesystemteacherapp.Comman.Urls;
import com.example.wifiattendancesystemteacherapp.R;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class AllStudentFragment extends Fragment {

    List<PojoClassGetAllStudent> list;
    ListView lv_all_student;
    TextView tv_no_records;
    ProgressBar pBar;
    AllStudentAdapter adapter;
    SharedPreferences preferences;

    SearchView searchView_student;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_student, container, false);

        // ✅ FIX: safe context
        preferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());

        list = new ArrayList<>();
        lv_all_student = view.findViewById(R.id.lv_presenty);
        tv_no_records = view.findViewById(R.id.tv_no_records);
        pBar = view.findViewById(R.id.progress);
        searchView_student = view.findViewById(R.id.searchview_by_name_or_enrollment_no);

        searchView_student.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchStudent(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchStudent(newText);
                return false;
            }
        });

        getAllStudent();

        return view;
    }

    private void searchStudent(String query) {

        List<PojoClassGetAllStudent> tempList = new ArrayList<>();

        for (PojoClassGetAllStudent d : list) {
            if (d.getName().toUpperCase().contains(query.toUpperCase()) ||
                    d.getEnrollment_no().toUpperCase().contains(query.toUpperCase()) ||
                    d.getMobile_no().toUpperCase().contains(query.toUpperCase())) {

                tempList.add(d);
            }
        }

        adapter = new AllStudentAdapter(tempList, (AppCompatActivity) requireActivity(), tv_no_records);
        lv_all_student.setAdapter(adapter);
    }

    public void getAllStudent() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("branch", preferences.getString("branch", ""));
        params.put("sem", preferences.getString("sem", ""));

        client.post(Urls.urlGetAllStudent, params, new JsonHttpResponseHandler() {

            public void onStart() {
                pBar.setVisibility(View.VISIBLE);
            }

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                pBar.setVisibility(View.GONE);

                try {
                    JSONArray jarry = response.getJSONArray("getAllStudent");

                    if (jarry.length() == 0) {
                        tv_no_records.setVisibility(View.VISIBLE);
                        tv_no_records.setText("No Student Found");
                        return;
                    }

                    list.clear();

                    for (int i = 0; i < jarry.length(); i++) {
                        JSONObject obj = jarry.getJSONObject(i);

                        list.add(new PojoClassGetAllStudent(
                                obj.getString("id"),
                                obj.getString("name"),
                                obj.getString("mobile_no"),
                                obj.getString("email_id"),
                                obj.getString("address"),
                                obj.getString("branch"),
                                obj.getString("sem"),
                                obj.getString("subject"),
                                obj.getString("enrollment_no")
                        ));
                    }

                    adapter = new AllStudentAdapter(list, (AppCompatActivity) requireActivity(), tv_no_records);
                    lv_all_student.setAdapter(adapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                pBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Could not connect", Toast.LENGTH_LONG).show();
            }
        });
    }
}