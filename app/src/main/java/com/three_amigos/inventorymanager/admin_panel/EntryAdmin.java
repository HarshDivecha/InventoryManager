package com.three_amigos.inventorymanager.admin_panel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.three_amigos.inventorymanager.R;

public class EntryAdmin extends AppCompatActivity implements View.OnClickListener {

    Button addnewinvadmin;
    Button modifyinvadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_admin);
        setTitle("Admin Panel");

        addnewinvadmin = findViewById(R.id.addNew_inv_admin);
        modifyinvadmin = findViewById(R.id.modify_inv_admin);

        addnewinvadmin.setOnClickListener(this);
        modifyinvadmin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addNew_inv_admin:
                this.startActivity(new Intent(this,AddnewInvAdmin.class));
                break;
            case R.id.modify_inv_admin:
                break;
        }
    }
}
