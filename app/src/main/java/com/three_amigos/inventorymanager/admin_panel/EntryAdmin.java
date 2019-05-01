package com.three_amigos.inventorymanager.admin_panel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.three_amigos.inventorymanager.R;

public class EntryAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_admin);
        setTitle("Admin Panel");
    }
}
