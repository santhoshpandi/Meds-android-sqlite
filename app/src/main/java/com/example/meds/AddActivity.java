package com.example.meds;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    private EditText etName, etQuantity, etPrice;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etName = findViewById(R.id.et_name);
        etQuantity = findViewById(R.id.et_quantity);
        etPrice = findViewById(R.id.et_price);
        btnAdd = findViewById(R.id.btn_Add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String quantity = etQuantity.getText().toString();
                String price = etPrice.getText().toString();


                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                long status = myDB.addMedicine(name, quantity, price);

                if(status == -1){
                    Toast.makeText(AddActivity.this, "Insertion failed", Toast.LENGTH_SHORT).show();
                    etName.requestFocus();
                }
                else{
                    Toast.makeText(AddActivity.this, "Medicine Inserted", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }
}