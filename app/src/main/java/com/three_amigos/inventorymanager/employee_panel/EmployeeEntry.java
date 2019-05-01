package com.three_amigos.inventorymanager.employee_panel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.three_amigos.inventorymanager.R;

public class EmployeeEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_entry);
        setTitle("Employee Panel");
    }
}
