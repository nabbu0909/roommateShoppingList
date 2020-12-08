package edu.uga.cs.roommateshoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * This class allows a preexisting user to sign in to the app using their email
 * and password from firebase.
 */
public class SignInActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signInButton;

    private static final String DEBUG_TAG = "SignInActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = (EditText) findViewById( R.id.editText1 );
        passwordEditText = (EditText) findViewById( R.id.editText2 );

        signInButton = (Button) findViewById( R.id.button3 );
        signInButton.setOnClickListener( new SignInButtonClickListener() );
    }

    private class SignInButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final String email = emailEditText.getText().toString();
            final String password = passwordEditText.getText().toString();

            final FirebaseAuth mAuth = FirebaseAuth.getInstance();

            //This is how we can allow a user to sign in using a existing email/password combination.
            //Note, if the users email and/or password is wrong, we should inform them and clear the
            //current fields. After a successfull login, it should launch to the apps main function.

            //TODO sign in with pre-existing account here

        }
    }

}
