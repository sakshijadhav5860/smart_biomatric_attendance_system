package com.example.wifiattendancesystemteacherapp.MyProfile;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wifiattendancesystemteacherapp.R;

public class MyProfileFragment extends Fragment {

    TextView tv_name, tv_mobile_no, tv_email_id,tv_branch,tv_sem,tv_subject,tv_teacher_id, tv_username;
    ProgressBar progress;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String id, name, mobile_no, email_id, branch,sem,subject,teacher_id,username;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_profile, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();

        tv_name = view.findViewById(R.id.tv_my_profile_name);
        tv_mobile_no = view.findViewById(R.id.tv_my_profile_mobile);
        tv_email_id = view.findViewById(R.id.tv_my_profile_email_id);
        tv_branch = view.findViewById(R.id.tv_my_profile_branch);
        tv_sem = view.findViewById(R.id.tv_my_profile_sem);
        tv_subject = view.findViewById(R.id.tv_my_profile_subject);
        tv_teacher_id = view.findViewById(R.id.tv_my_profile_teacher_id);
        tv_username = view.findViewById(R.id.tv_my_profile_username);
        progress = view.findViewById(R.id.progress);

        id = preferences.getString("id", "");
        name = preferences.getString("name", "");
        mobile_no = preferences.getString("mobile_no", "");
        email_id = preferences.getString("email_id", "");
        branch = preferences.getString("branch", "");
        sem = preferences.getString("sem", "");
        subject = preferences.getString("subject", "");
        teacher_id = preferences.getString("teacher_id", "");
        username = preferences.getString("username", "");

        Toast.makeText(getActivity(), ""+subject, Toast.LENGTH_SHORT).show();

//        Picasso.with(getActivity()).load(Urls.OnlineImageAddress + "" + hostel_image).placeholder(R.drawable.profileimage)
//                .error(R.drawable.image_not_load).into(img_hostel_profilel);

        tv_name.setText(name);
        tv_mobile_no.setText(mobile_no);
        tv_email_id.setText(email_id);
        tv_branch.setText(branch);
        tv_sem.setText(sem);
        tv_subject.setText(subject);
        tv_teacher_id.setText(teacher_id);
        tv_username.setText(username);


        return view;
    }
}