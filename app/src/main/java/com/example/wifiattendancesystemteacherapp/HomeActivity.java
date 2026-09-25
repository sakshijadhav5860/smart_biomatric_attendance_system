package com.example.wifiattendancesystemteacherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.wifiattendancesystemteacherapp.AddAttendance.ViewCurrentAttendanceFragment;
import com.example.wifiattendancesystemteacherapp.AllStudent.AllStudentFragment;
import com.example.wifiattendancesystemteacherapp.AllStudent.ViewStudentPresentyActivity;
import com.example.wifiattendancesystemteacherapp.MyProfile.MyProfileFragment;
import com.example.wifiattendancesystemteacherapp.TimeTable.AddTimeTableActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        preferences = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        editor = preferences.edit();

        setTitle("WIFI Attendance Teacher App");

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firsttime = prefs.getBoolean("firsttime", true);

        if (firsttime) {
            welcome();
        }
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_home_all_student);
    }

    private void welcome() {
        AlertDialog.Builder ad = new AlertDialog.Builder(HomeActivity.this);
        ad.setTitle("WIFI Attendance Teacher App");
        ad.setMessage("Welcome to WIFI Attendance Teacher App");
        ad.setPositiveButton("Thank you", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();

        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firsttime", false);
        editor.apply();

    }

    AllStudentFragment allStudentFragment = new AllStudentFragment();
    ViewCurrentAttendanceFragment viewCurrentAttendanceFragment = new ViewCurrentAttendanceFragment();
    MyProfileFragment myProfileFragment = new MyProfileFragment();

    AttendanceReportFragment attendanceReportFragment = new AttendanceReportFragment();


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_home_all_student){
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, allStudentFragment).commit();
            return true;
        }

        else if (id == R.id.menu_home_add_attendance){
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, viewCurrentAttendanceFragment).commit();
            return true;
        }
        else if (id == R.id.menu_home_my_profile){
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, myProfileFragment).commit();
            return true;
        }else if(id == R.id.menu_home_date_wise_attendance) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, attendanceReportFragment).commit();
            return true;
        }
//        switch (item.getItemId()) {
//            case R.id.menu_home_home:
//                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
//                return true;
//
//            case R.id.menu_home_citywise_saluna:
//                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, citywiseFragment).commit();
//                return true;
//
//            case R.id.menu_home_my_booking:
//                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, myBookingFragment).commit();
//                return true;
//
//            case R.id.menu_home_my_profile:
//                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, myProfileFragment).commit();
//                return true;
//        }
        return false;
    }

    @Override
    public void onBackPressed() {
        logout();
    }

    private void logout() {
        AlertDialog.Builder ad = new AlertDialog.Builder(HomeActivity.this);
        ad.setTitle("WIFI Attendance Teacher App ");
        ad.setMessage("Are you sure you want to logout");
        ad.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        ad.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                editor.putBoolean("isLogin", false).commit();
                finish();

            }
        }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home_menu_add_time_table) {
            Intent intent = new Intent(HomeActivity.this, AddTimeTableActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.home_menu_logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }
}