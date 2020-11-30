package edu.uga.cs.roommateshoppinglist;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MenuActivity extends AppCompatActivity {

    TextView welcome;
    String userName;
    DatabaseReference databaseUsers;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        welcome = (TextView) findViewById( R.id.textView2 );
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        databaseUsers.orderByChild("email").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
             User userDB = snapshot.getValue(User.class);
             if (userDB.getEmail().equals(user.getEmail())){
                 userName = userDB.getName();
                 welcome.setText("Welcome, " + userName);
             }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        welcome.setText("Welcome, " + userName);
    }

}
