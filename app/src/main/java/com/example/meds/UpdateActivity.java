package com.example.meds;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    private EditText etName, etQuantity, etPrice;
    private Button btnUpdate;
    private String id, name, quantity, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etName = findViewById(R.id.et_name);
        etQuantity = findViewById(R.id.et_quantity);
        etPrice = findViewById(R.id.et_price);
        btnUpdate = findViewById(R.id.btn_update);

        Intent intent = getIntent();
        id = intent.getStringExtra("vID");
        name = intent.getStringExtra("vName");
        quantity = intent.getStringExtra("vQuantity");
        price = intent.getStringExtra("vPrice");

        etName.setText(name);
        etQuantity.setText(quantity);
        etPrice.setText(price);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = etName.getText().toString();
                String newQuantity = etQuantity.getText().toString();
                String newPrice = etPrice.getText().toString();


                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                long status = myDB.updateMedicine(id, newName, newQuantity, newPrice);

                if(status == -1){
                    Toast.makeText(UpdateActivity.this, "Updation Failed !", Toast.LENGTH_SHORT).show();
                    etName.requestFocus();
                }
                else{
                    Toast.makeText(UpdateActivity.this, "Medicine Updated !", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }
}