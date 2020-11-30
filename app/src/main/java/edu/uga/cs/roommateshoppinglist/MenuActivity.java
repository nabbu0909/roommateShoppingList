package edu.uga.cs.roommateshoppinglist;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MenuActivity extends AppCompatActivity {

    TextView welcome;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        welcome = (TextView) findViewById( R.id.textView2 );

        welcome.setText("Welcome, " + user.getEmail());
    }

}
