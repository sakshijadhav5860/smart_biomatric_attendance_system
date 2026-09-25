package com.example.wifiattendancesystemteacherapp.AllStudent;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wifiattendancesystemteacherapp.R;

import java.util.List;

public class ViewStudentPresentyAdapter extends BaseAdapter {

    List<PojoClassViewStudentPresenty> list;
    AppCompatActivity activity;
    TextView tv_no_records;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public ViewStudentPresentyAdapter(List<PojoClassViewStudentPresenty> list, AppCompatActivity activity, TextView tv_no_records) {
        this.list = list;
        this.activity = activity;
        this.tv_no_records = tv_no_records;

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
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

        final ViewStudentPresentyAdapter.ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (v == null)
        {
            holder = new ViewStudentPresentyAdapter.ViewHolder();
            v = inflater.inflate(R.layout.list_my_attendance, null);

            holder.presenty_date = (TextView) v.findViewById(R.id.txt_child_view_attendence_date);
            holder.presenty_subject_name= (TextView) v.findViewById(R.id.txt_child_view_attendence_subject);
            holder.presenty_status = (TextView)v.findViewById(R.id.txt_child_view_attendence_presenty);

            v.setTag(holder);
        }
        else
        {
            holder = (ViewStudentPresentyAdapter.ViewHolder) v.getTag();
        }

        final PojoClassViewStudentPresenty obj = list.get(position);
        holder.presenty_date.setText(obj.getDate());
        holder.presenty_subject_name.setText(obj.getSubject_name());
        holder.presenty_status.setText(obj.getPresenty());

        return v;
    }

    class ViewHolder
    {
        TextView presenty_date,presenty_subject_name,presenty_status;
    }
}
