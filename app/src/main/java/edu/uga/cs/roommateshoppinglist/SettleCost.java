package edu.uga.cs.roommateshoppinglist;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SettleCost extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference referenceItems = database.getReference("PurchasedItems");
    private ArrayList<Roommates> roommatesArrayList = new ArrayList<Roommates>();

    DatabaseReference db;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle_cost);

        //create recycler
        layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.roommateRecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new SettleCostRecyclerAdapter(this, roommatesArrayList);
        recyclerView.setAdapter(recyclerAdapter);

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String string) {
                String roommateName = dataSnapshot.child("roommateName").getValue().toString();
                String priceString = dataSnapshot.child("itemPrice").getValue().toString();
                //if the roommate has not already added the item, then add it for them
                if (roommatesArrayList == null) {
                    Roommates newRoommate = new Roommates(roommateName, priceString);
                    roommatesArrayList.add(newRoommate);
                } else { //check for duplicate items
                    
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

    }

}
