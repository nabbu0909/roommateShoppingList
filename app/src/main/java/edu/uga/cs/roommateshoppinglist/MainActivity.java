package edu.uga.cs.roommateshoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button registerButton = findViewById( R.id.registerButton);
        Button signInButton = findViewById(R.id.signInButton);

        signInButton.setOnClickListener( new SignInButtonClickListener() );
        registerButton.setOnClickListener( new RegisterButtonClickListener() );

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if( currentUser != null )
            mAuth.signOut();
    }

    private class RegisterButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // start the user registration activity
            Intent intent = new Intent(view.getContext(), RegisterActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    private class SignInButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // start the user registration activity
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build()
            );

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setIsSmartLockEnabled(false)
                            .build(),
                    RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check if it is a sign in activity result
        if( requestCode == RC_SIGN_IN ) {
            IdpResponse response = IdpResponse.fromResultIntent( data );

            if( resultCode == RESULT_OK ) {

                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                Log.i( "FireBase Test", "Signed in as: " + user.getEmail() );

                // after a successful sign in, start the job leads management activity
                Intent intent = new Intent( this, MenuActivity.class );
                startActivity( intent );

            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in, e.g., by using the back button
                Toast.makeText( getApplicationContext(),
                        "Sign in failed",
                        Toast.LENGTH_SHORT).show();            }
        }
    }
}