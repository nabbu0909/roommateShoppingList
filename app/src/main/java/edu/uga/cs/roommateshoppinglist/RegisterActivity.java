package edu.uga.cs.roommateshoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This Activity handles the registration of a new user, prompting a valid email and then
 * double password confirmation before making that new account in firebase.
 */
public class RegisterActivity extends AppCompatActivity {


    private Button registerButton;
    private EditText emailEditText, passwordEditText, confirmPassword, nameEditText;
    private static final int RC_SIGN_IN = 123;

    DatabaseReference databaseUsers;

    private static final String DEBUG_TAG = "RegisterActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        emailEditText = (EditText) findViewById( R.id.editText1 );
        passwordEditText = (EditText) findViewById( R.id.editText2 );
        nameEditText = (EditText) findViewById( R.id.editText3 );
        confirmPassword = (EditText) findViewById(R.id.editText);

        registerButton = (Button) findViewById( R.id.button3 );
        registerButton.setOnClickListener( new RegisterButtonClickListener() );
    }

    private class RegisterButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final String email = emailEditText.getText().toString();
            final String password = passwordEditText.getText().toString();
            final String name = nameEditText.getText().toString();

            final FirebaseAuth mAuth = FirebaseAuth.getInstance();
            if(!(passwordEditText.getText().toString().equalsIgnoreCase(confirmPassword.getText().toString()))){
                Toast.makeText(RegisterActivity.this, "Passwords must be the same", Toast.LENGTH_LONG).show();
            } else if(!TextUtils.isEmpty(name)) {
                // This is how we can create a new user using an email/password combination.
                // Note that we also add an onComplete listener, which will be invoked once
                // a new user has been created by Firebase.  This is how we will know the
                // new user creation succeeded or failed.
                // If a new user has been created, Firebase already signs in the new user;
                // no separate sign in is needed.
                mAuth.createUserWithEmailAndPassword(email, password)
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

                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name)
                                            .build();

                                    user.updateProfile(profileUpdates);

                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(DEBUG_TAG, "createUserWithEmail: failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Registration failed: Must provide a proper Email Address",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(RegisterActivity.this, "Registration failed: Name cannot be empty",
                        Toast.LENGTH_LONG).show();
            }
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
