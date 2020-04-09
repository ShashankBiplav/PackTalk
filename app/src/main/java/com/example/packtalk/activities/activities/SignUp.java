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
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    //UI components
    private EditText editTextName, editTextEmail, editTextPassword;
    private Button buttonSignUp;
    private TextView logInTextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize UI components
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        //on pressing the Enter key or done on the keyboard the user will be signed up
        editTextPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(buttonSignUp);
                }
                return false;
            }
        });

        buttonSignUp = findViewById(R.id.buttonSignUp);
        logInTextButton = findViewById(R.id.logInTextButton);

        //Setting On Click Listener on both button and Text View
        buttonSignUp.setOnClickListener(this);
        logInTextButton.setOnClickListener(this);

        if(ParseUser.getCurrentUser() != null) {
            //token session is generated when a user is logged into parse
            //ParseUser.getCurrentUser().logOut();
            transitionToHome();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.buttonSignUp:

                //if any of the fields are empty then don't sign up and show a toast message
                if (editTextEmail.getText().toString().equals("") ||
                        editTextName.getText().toString().equals("") ||
                        editTextPassword.getText().toString().equals("")){

                    FancyToast.makeText(SignUp.this,
                            "Email | Username | Password is Required",
                            Toast.LENGTH_SHORT, FancyToast.WARNING,
                            true).show();
                }
                else {
                    final ParseUser appUser = new ParseUser();
                    appUser.setUsername(editTextName.getText().toString());
                    appUser.setEmail(editTextEmail.getText().toString());
                    appUser.setPassword(editTextPassword.getText().toString());

                    //showing progress dialog to the user while signing in
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing Up " + editTextName.getText().toString());
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUp.this,
                                        appUser.getUsername() + " is Signed Up",
                                        Toast.LENGTH_SHORT, FancyToast.SUCCESS,
                                        true).show();
                                //if sign up is successful then go to home
                                transitionToHome();
                            } else {
                                FancyToast.makeText(SignUp.this,
                                        "An error occurred!" + "\n" + e.getMessage(),
                                        Toast.LENGTH_LONG, FancyToast.ERROR,
                                        true).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                break;

            case  R.id.logInTextButton:

                //switching to login activity
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();

                break;

        }

    }

    public void rootLayoutIsTapped(View view){

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
private void transitionToHome(){
    Intent intent = new Intent(SignUp.this, HomeActivity.class);
    startActivity(intent);
    finish();
}
}
