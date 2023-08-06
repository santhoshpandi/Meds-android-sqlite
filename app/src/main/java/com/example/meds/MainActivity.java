package com.example.meds;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton btnAdd;
    private RecyclerView viewMedicine;
    private MyDatabaseHelper myDB;
    private AdapterMedicine adapterMedicine;
    private ArrayList<String> arrID, arrName, arrQuantity, arrPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new MyDatabaseHelper(MainActivity.this);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showData();
    }

    private void SQLiteToArrayList(){
        Cursor cursor = myDB.displayMedicine();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "Kindly Add Medicine", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()){
                arrID.add(cursor.getString(0));
                arrName.add(cursor.getString(1));
                arrQuantity.add(cursor.getString(2));
                arrPrice.add(cursor.getString(3));
            }
        }
    }

    private void showData(){
        arrID = new ArrayList<>();
        arrName = new ArrayList<>();
        arrQuantity = new ArrayList<>();
        arrPrice = new ArrayList<>();

        SQLiteToArrayList();

        viewMedicine = findViewById(R.id.viewMed);
        adapterMedicine = new AdapterMedicine(MainActivity.this, arrID, arrName, arrQuantity, arrPrice);
        viewMedicine.setAdapter(adapterMedicine);
        viewMedicine.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }
}