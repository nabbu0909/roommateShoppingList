package edu.uga.cs.roommateshoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

public class PurchasedItems extends AppCompatActivity {

    DatabaseReference db;
    RecyclerView recyclerView;
    Button settleCostButton;

    public static final String DEBUG_TAG = "PurchasedItems";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased_items);

        recyclerView = findViewById(R.id.recyclerView);
        settleCostButton = findViewById(R.id.settleCost);
        settleCostButton.setOnClickListener(new SettleCostButtonListener());
    }

    private class SettleCostButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PurchasedItems.this, SettleCost.class);
            PurchasedItems.this.startActivity(intent);
        }
    }

}
