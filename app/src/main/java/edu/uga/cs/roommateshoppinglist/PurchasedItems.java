package edu.uga.cs.roommateshoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PurchasedItems extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference referenceItems = database.getReference("PurchasedItems");
    private List<Item> items = new ArrayList<Item>();

    DatabaseReference db;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;
    Button settleCostButton;

    public static final String DEBUG_TAG = "PurchasedItems";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased_items);

        //create recycler
        layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.roommateRecyclerView);
        recyclerView.setLayoutManager(layoutManager);

        //add buttons
        settleCostButton = findViewById(R.id.settleCostButton);
        settleCostButton.setOnClickListener(new SettleCostButtonListener());
    }

    private class SettleCostButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PurchasedItems.this, SettleCost.class);
            PurchasedItems.this.startActivity(intent);
        }
    }

    public void onStart() {
        super.onStart();

        referenceItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot key : snapshot.getChildren()){
                    keys.add(key.getKey());
                    Item item = key.getValue(Item.class);
                    items.add(item);
                    recyclerAdapter = new PurchaseListRecyclerAdapter( items );
                    recyclerView.setAdapter( recyclerAdapter );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
