package com.example.wifiattendancesystemteacherapp.TimeTable;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.MenuItem;
import android.widget.*;

import com.example.wifiattendancesystemteacherapp.Comman.Urls;
import com.example.wifiattendancesystemteacherapp.R;
import com.loopj.android.http.*;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONObject;

import java.io.*;
import java.util.UUID;

import cz.msebera.android.httpclient.Header;

public class AddTimeTableActivity extends AppCompatActivity {

    Spinner spinner_branch, spinner_sem;
    Button btn_attach_time_table, btn_add_time_table;
    ProgressBar progress;

    private static final int PICK_FILE_REQUEST = 1;

    private Uri filePath = null;
    int insertedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_table);

        spinner_branch = findViewById(R.id.spinner_branch);
        spinner_sem = findViewById(R.id.spinner_teacher_semester);
        btn_attach_time_table = findViewById(R.id.btn_attach_time_table);
        btn_add_time_table = findViewById(R.id.btn_add_time_table);
        progress = findViewById(R.id.progress);

        // ✅ NO PERMISSION REQUIRED
        btn_attach_time_table.setOnClickListener(v -> showFileChooser());

        btn_add_time_table.setOnClickListener(v -> {
            if (spinner_branch.getSelectedItem().toString().equals("Select Your Branch")) {
                ((TextView) spinner_branch.getSelectedView()).setError("Select Branch");
            } else if (spinner_sem.getSelectedItem().toString().equals("Select Your Sem")) {
                ((TextView) spinner_sem.getSelectedView()).setError("Select Sem");
            } else {
                addTimeTable();
            }
        });
    }

    // ================= INSERT DATA =================
    public void addTimeTable() {

        if (filePath == null) {
            Toast.makeText(this, "Please select PDF first", Toast.LENGTH_SHORT).show();
            return;
        }

        progress.setVisibility(ProgressBar.VISIBLE);

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("branch", spinner_branch.getSelectedItem().toString());
        params.put("sem", spinner_sem.getSelectedItem().toString());
        params.put("subject", "TimeTable");

        client.post(Urls.urlTimeTable, params, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progress.setVisibility(ProgressBar.GONE);

                try {
                    if (response.getString("success").equals("1")) {
                        insertedId = response.getInt("lastinsertedid");
                        uploadMultipart();
                    } else {
                        Toast.makeText(AddTimeTableActivity.this, "Insert Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progress.setVisibility(ProgressBar.GONE);
                Toast.makeText(AddTimeTableActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ================= FILE PICKER =================
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT); // ✅ IMPORTANT
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null) {
            filePath = data.getData();
            btn_attach_time_table.setText("PDF Selected");
        }
    }

    // ================= FILE UPLOAD =================
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void uploadMultipart() {

        try {
            if (filePath == null || insertedId == -1) {
                Toast.makeText(this, "File or ID missing", Toast.LENGTH_SHORT).show();
                return;
            }

            File file = new File(FileUtil.from(this, filePath));

            if (!file.exists()) {
                Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
                return;
            }

            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();

            params.put("id", insertedId);
            params.put("pdf", file); // 🔥 IMPORTANT

            client.post(Urls.urlAddTimeTablePDF, params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Toast.makeText(AddTimeTableActivity.this, "PDF Uploaded Successfully", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Toast.makeText(AddTimeTableActivity.this, "Upload Failed", Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // ================= FILE UTIL =================
    public static class FileUtil {

        @SuppressLint("Range")
        public static String from(Context context, Uri uri) throws IOException {

            InputStream inputStream = context.getContentResolver().openInputStream(uri);

            String fileName = "temp.pdf";

            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                cursor.close();
            }

            File file = new File(context.getCacheDir(), fileName);

            OutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int len;

            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }

            outputStream.close();
            inputStream.close();

            return file.getAbsolutePath();
        }
    }

    // ================= BACK BUTTON =================
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}