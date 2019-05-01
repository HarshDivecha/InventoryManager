package login;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.three_amigos.inventorymanager.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import globals.HelperFunctions;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    HelperFunctions helper;

    EditText username;
    EditText password;
    EditText confirmPassword;
    EditText email;
    EditText phone;
    Button register;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("Register");
        context = RegisterActivity.this;
        helper = new HelperFunctions(context);

        username = findViewById(R.id.username_register_et);
        password = findViewById(R.id.password_register_et);
        confirmPassword = findViewById(R.id.confirmPassword_register_et);
        email = findViewById(R.id.email_register_et);
        phone = findViewById(R.id.phone_register_et);
        register = findViewById(R.id.register_register_btn);
        progressBar = findViewById(R.id.progressBar_register);
        progressBar.setVisibility(progressBar.GONE);

        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_register_btn:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        runFeildAuthentication();
    }

    public void runFeildAuthentication(){
        String errorString = "Cannot be Empty !!!";
        if(username.getText().toString().trim().equals("")){        //Username !empty
            username.setError(errorString);
            return;
        }else if (email.getText().toString().trim().equals("")){   //E-mail !empty
            email.setError(errorString);
            return;
        }else if (phone.getText().toString().trim().equals("")){   // Phone !empty
            phone.setError(errorString);
            return;
        }else if(!validatePasswords()){                            // validate passowrd fields
            return;
        }
        else if(!validateEmail()){                            // validate passowrd fields
            return;
        }
        else if(!validatePhone()){                            // validate passowrd fields
            return;
        }

    }


    public Boolean validateEmail(){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(!(email.getText().toString().trim().matches(emailPattern))){
            email.setError("Not a valid E-mail");
            return false;
        }
        return true;
    }

    public Boolean validatePhone(){
        String phonePattern = "^[+]?[0-9]{10,13}$";
        if(!phone.getText().toString().trim().matches(phonePattern)){
            phone.setError("Not a valid Phone number");
            return false;
        }
        return true;
    }

    public Boolean validatePasswords(){
        String pass = password.getText().toString().trim();
        String confirmPass = confirmPassword.getText().toString().trim();
        String alphaNumericError = " Password should contain: \n" +
                "1) Numbers.\n" +
                "2) Alphabets.\n" +
                "3) 6 or more characters.\n" +
                "4) No symbols.\n";

        //check if password empty
        if(pass.equals("")){
            password.setError("Cannot be Empty !!!\"");
            return false;
        }
        //check if confirm password empty
        else if(confirmPass.equals("")){
            confirmPassword.setError("Cannot be Empty !!!");
            return false;
        }
        //check if password alphanumeric and no symbol
        else if (pass.matches(".*[A-Za-z].*") && // atleast 1 alphabaet
                pass.matches(".*[0-9].*") && // atleast 1 number
                pass.matches("[A-Za-z0-9]*")&& // only alphaNumeric
                pass.length()>5){
        }else {
            password.setText("");
            confirmPassword.setText("");
            helper.makeDialog(context,alphaNumericError,"Error");
            return false;
        }
        // //check if password and confirm pass match
        if(!pass.equals(confirmPass)){
            password.setText("");
            confirmPassword.setText("");
            password.setError("Error : Password Mismatch! ");
            return false;
        }
        return true;
    }


    // Heavy Industry Constraints
    // alphanumeric+symbols+>4;
    public Boolean isValidPassword2(){
        String pass = password.getText().toString();
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(pass);

        return matcher.matches();
    }


}
