package com.duydoan.autosendsms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.icu.util.EthiopicCalendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText etCode, etNumber, etContent;
    private Button btAdd;
    private RecyclerView recyclerView;
    private ContentDataViewModel contentDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();

        ContentAdapter contentAdapter = new ContentAdapter();

        recyclerView.setAdapter(contentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contentDataViewModel = ViewModelProviders.of(this).get(ContentDataViewModel.class);
        contentDataViewModel.getAllData().observe(this, new Observer<List<ContentData>>() {
            @Override
            public void onChanged(List<ContentData> contentData) {
                contentAdapter.setData(contentData);
            }
        });

        List<ContentData> contentData1 = Database.getInstance(this).contentDataDao().findItem("123");
        for (ContentData i: contentData1 ){
            Log.i("aab", "onCreate: "+i.toString());
        }

        btAdd.setOnClickListener(v -> {

            if (!etCode.getText().toString().isEmpty()){
                if (!etNumber.getText().toString().isEmpty()){
                    if (!etContent.getText().toString().isEmpty()){
                        ContentData contentData = new ContentData(etCode.getText().toString(),
                                etNumber.getText().toString(),etContent.getText().toString());
                        contentDataViewModel.insert(contentData);
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

}
