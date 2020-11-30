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

import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText email, password, confirmPassword;
    private Button register;
    private static final int RC_SIGN_IN = 123;

    private static final String DEBUG_TAG = "RegisterActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById( R.id.editText1 );
        password = (EditText) findViewById( R.id.editText2 );
        confirmPassword = (EditText) findViewById(R.id.editText);

        register = (Button) findViewById( R.id.button3 );
        register.setOnClickListener( new RegisterButtonClickListener() );
    }

    private class RegisterButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            final FirebaseAuth mAuth = FirebaseAuth.getInstance();

            if(!(password.getText().toString().equalsIgnoreCase(password.getText().toString()))){
                Toast.makeText(RegisterActivity.this, "Passwords must be the same", Toast.LENGTH_LONG).show();
            } else {

                // This is how we can create a new user using an email/password combination.
                // Note that we also add an onComplete listener, which will be invoked once
                // a new user has been created by Firebase.  This is how we will know the
                // new user creation succeeded or failed.
                // If a new user has been created, Firebase already signs in the new user;
                // no separate sign in is needed.
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(getApplicationContext(),
                                            "Registered user: " + email,
                                            Toast.LENGTH_SHORT).show();

                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(DEBUG_TAG, "createUserWithEmail: success");

                                    FirebaseUser user = mAuth.getCurrentUser();

                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(DEBUG_TAG, "createUserWithEmail: failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Registration failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } //else
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //for now when a user registers, it takes you back to the main page where you can then sign in w/ new account
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Unable to sign in", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
