package com.example.packtalk.activities.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.packtalk.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Login extends AppCompatActivity implements View.OnClickListener{

    //UI components
    private EditText editTextEmailLogin, editTextPasswordLogin;
    private Button buttonLogin;
    private TextView signUpTextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initializing UI components
        editTextEmailLogin = findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin = findViewById(R.id.editTextPasswordLogin);

        //on pressing the Enter key or done on the keyboard the user will be logged in
        editTextPasswordLogin.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(buttonLogin);
                }
                return false;
            }
        });
        buttonLogin = findViewById(R.id.buttonLogin);
        signUpTextButton = findViewById(R.id.signUpTextButton);

        //setting On Click Listener on Login Button and TextView
        buttonLogin.setOnClickListener(this);
        signUpTextButton.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null){
            //a token session is generated when a user is logged into parse
            //ParseUser.getCurrentUser().logOut();
            transitionToHome();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.buttonLogin:


                if (editTextEmailLogin.getText().toString().equals("") || editTextPasswordLogin.getText().toString().equals("")){
                    FancyToast.makeText(Login.this,
                            "Email | Password is Required",
                            Toast.LENGTH_SHORT, FancyToast.WARNING,
                            true).show();
                }
                else {
                    //showing progress dialog to the user while signing in
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Logging In");
                    progressDialog.show();

                    //Logging In the user using callback
                    ParseUser.logInInBackground(editTextEmailLogin.getText().toString(),
                            editTextPasswordLogin.getText().toString(),
                            new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null && e == null) {
                                        FancyToast.makeText(Login.this,
                                                user.getUsername() + " is Logged in",
                                                Toast.LENGTH_SHORT, FancyToast.SUCCESS,
                                                true).show();
                                        //after login successful go to home
                                        transitionToHome();
                                    } else {
                                        FancyToast.makeText(Login.this,
                                                "An error occurred!" + "\n" + e.getMessage(),
                                                Toast.LENGTH_LONG, FancyToast.ERROR,
                                                true).show();
                                    }
                                    progressDialog.dismiss();
                                }
                            });
                }
                break;

            case R.id.signUpTextButton:

                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
                finish();

                break;
        }
    }
    public void rootLayoutTapped(View view){

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    private void transitionToHome(){
        Intent intent = new Intent(Login.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
