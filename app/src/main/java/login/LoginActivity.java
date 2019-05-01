package login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.three_amigos.inventorymanager.R;
import globals.HelperFunctions;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //GLOBAL NECESSARY ELEMENTS
    Context context;
    HelperFunctions helper;

    // VIEW ELEMENTS
    EditText usernameEt;
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
        context=LoginActivity.this;
        helper=new HelperFunctions(context);

        //VIEWS
        usernameEt = findViewById(R.id.username_login);
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
                break;
            case R.id.signup_login:
                startRegisterActivity();
                break;
        }
    }


    public void startRegisterActivity(){
        context.startActivity(new Intent(context,RegisterActivity.class));
    }
}
