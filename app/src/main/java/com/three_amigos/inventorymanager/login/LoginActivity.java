package com.three_amigos.inventorymanager.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.three_amigos.inventorymanager.R;
import com.three_amigos.inventorymanager.globals.HelperFunctions;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //GLOBAL NECESSARY ELEMENTS
    Context context;
    HelperFunctions helper;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    // VIEW ELEMENTS
    EditText emailEt;
    EditText passwordEt;
    Button loginBtn;
    TextView registerTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle(" 3A Inventory Manager");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //GLOBALS
        context=com.three_amigos.inventorymanager.login.LoginActivity.this;
        helper=new HelperFunctions(context);
        mAuth = FirebaseAuth.getInstance();

        //VIEWS
        emailEt = findViewById(R.id.email_login);
        passwordEt = findViewById(R.id.password_login);
        loginBtn = findViewById(R.id.login_login);
        registerTv = findViewById(R.id.signup_login);

        loginBtn.setOnClickListener(this);
        registerTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_login:
                login();
                break;
            case R.id.signup_login:
                startRegisterActivity();
                break;
        }
    }

    public void login(){
        String emailStr = emailEt.getText().toString().trim();
        String passStr = passwordEt.getText().toString().trim();
        final String fireUser="";

        mAuth.signInWithEmailAndPassword(emailStr,passStr).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("Success", "createUserWithEmail:success");


                    firebaseUser = mAuth.getCurrentUser();
                    decideActivity(firebaseUser);

                } else {
                    Log.w("Error", "createUserWithEmail:failure", task.getException());
                    helper.makeDialog(context,task.getException().getMessage(),"Error");
                }
            }
        });

    }


    public void decideActivity(FirebaseUser firebaseUser){
        String userEmail = "";
        if(firebaseUser.getEmail()!=null){
            userEmail= firebaseUser.getEmail().toString();
        }

        if(!userEmail.equals("")){
            if(userEmail.equals("admin@root.com")){
                context.startActivity(new Intent(context,com.three_amigos.inventorymanager.admin_panel.EntryAdmin.class));
            }
            else{
                context.startActivity(new Intent(context,com.three_amigos.inventorymanager.employee_panel.EmployeeEntry.class));
            }
        }
    }

    //************   Activities   ************
    public void startRegisterActivity(){
        context.startActivity(new Intent(context,RegisterActivity.class));
    }

}
