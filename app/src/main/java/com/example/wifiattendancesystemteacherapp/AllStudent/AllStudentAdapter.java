package com.example.wifiattendancesystemteacherapp.AllStudent;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.wifiattendancesystemteacherapp.R;

import java.util.List;

public class AllStudentAdapter extends BaseAdapter {

    List<PojoClassGetAllStudent> list;
    AppCompatActivity activity;
    TextView tv_no_records;
    SharedPreferences preferences;

    public AllStudentAdapter(List<PojoClassGetAllStudent> list, AppCompatActivity activity, TextView tv_no_records) {
        this.list = list;
        this.activity = activity;
        this.tv_no_records = tv_no_records;

        // ✅ FIX: prevent null crash
        if (activity != null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (v == null) {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.lv_all_student, parent, false);

            holder.name = v.findViewById(R.id.tv_student_name);
            holder.enrollment_no = v.findViewById(R.id.tv_student_enrollment_no);
            holder.mobile_no = v.findViewById(R.id.tv_student_mobile_no);
            holder.branch = v.findViewById(R.id.tv_student_branch);
            holder.sem = v.findViewById(R.id.tv_student_sem);
            holder.cv_single_student = v.findViewById(R.id.cv_single_student);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        PojoClassGetAllStudent obj = list.get(position);

        holder.name.setText(obj.getName());
        holder.enrollment_no.setText(obj.getEnrollment_no());
        holder.mobile_no.setText(obj.getMobile_no());
        holder.branch.setText(obj.getBranch());
        holder.sem.setText(obj.getSem());

        holder.cv_single_student.setOnClickListener(view -> {
            Intent intent = new Intent(activity, ViewStudentPresentyActivity.class);
            intent.putExtra("enrollment_no", obj.getEnrollment_no());
            activity.startActivity(intent);
        });

        return v;
    }

    static class ViewHolder {
        TextView name, mobile_no, branch, sem, enrollment_no;
        CardView cv_single_student;
    }
}