package com.duydoan.autosendsms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.util.EthiopicCalendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText etCode, etNumber, etContent;
    private Button btAdd;
    private RecyclerView recyclerView;
    private ContentDataViewModel contentDataViewModel;

    private final int PERMISSION_ALL = 1;
    private final String[] PERMISSIONS = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
        }
        anhXa();

        ContentAdapter contentAdapter = new ContentAdapter(new ArrayList<ContentData>());

        recyclerView.setAdapter(contentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contentDataViewModel = ViewModelProviders.of(this).get(ContentDataViewModel.class);
        contentDataViewModel.getAllData().observe(this, new Observer<List<ContentData>>() {
            @Override
            public void onChanged(List<ContentData> contentData) {
                contentAdapter.setData(contentData);
            }
        });

        btAdd.setOnClickListener(v -> {

            if (!etCode.getText().toString().isEmpty()){
                if (!etNumber.getText().toString().isEmpty()){
                    if (!etContent.getText().toString().isEmpty()){
                        ContentData contentData = new ContentData(etCode.getText().toString(),
                                etNumber.getText().toString(),etContent.getText().toString());
                        contentDataViewModel.insert(contentData);
                        etCode.setText("");
                        etNumber.setText("");
                        etContent.setText("");
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(etContent.getWindowToken(), 0);
                    }else {
                        Snackbar.make(v,R.string.etContentEmpty,Snackbar.LENGTH_SHORT).show();
                    }
                }else {
                    Snackbar.make(v,R.string.etNumberEmpty,Snackbar.LENGTH_SHORT).show();
                }
            }else {
                Snackbar.make(v,R.string.etCodeEmpty,Snackbar.LENGTH_SHORT).show();
            }
        });

    }
    private void anhXa(){
        etCode = (EditText) findViewById(R.id.etCode);
        etNumber = (EditText) findViewById(R.id.etNumber);
        etContent = (EditText) findViewById(R.id.etContent);

        btAdd =(Button) findViewById(R.id.btAdd);

        recyclerView = (RecyclerView) findViewById(R.id.rvList);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    //Kiểm tra xem có chấp nhận quyền không
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0){
                    for (int i: grantResults){
                        if(i == PackageManager.PERMISSION_DENIED){
                            Toast.makeText(this,"Ứng dụng sẽ không họat động nếu bạn" +
                                    " không cấp quyền",Toast.LENGTH_SHORT);
                            break;
                        }
                    }
                } else {
                    Toast.makeText(this,"Ứng dụng sẽ không họat động nếu bạn" +
                            " không cấp quyền",Toast.LENGTH_SHORT);
                }

            }
        }
    }

}
