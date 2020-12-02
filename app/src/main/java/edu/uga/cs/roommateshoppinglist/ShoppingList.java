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

public class ShoppingList extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference referenceItems = database.getReference("ShoppingList");
    private List<ShoppingItem> items = new ArrayList<ShoppingItem>();

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;
    Button addShoppingListItem, viewPurchasedItems;

    public static final String DEBUG_TAG = "ShoppingList";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        items.add(new ShoppingItem("Eggs"));
        items.add(new ShoppingItem("Bacon"));

        //create recycler
        layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.shoppingListRecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new ShoppingListRecyclerAdapter( items );
        recyclerView.setAdapter( recyclerAdapter );

        //add buttons
        addShoppingListItem = findViewById(R.id.addNewItemButton);
        addShoppingListItem.setOnClickListener(new AddShoppingListItemButtonClickListener());
        viewPurchasedItems = findViewById(R.id.viewPurchasedItemsButton);
        viewPurchasedItems.setOnClickListener(new PurchasedItemsButtonClickListener());

    }

    private class PurchasedItemsButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ShoppingList.this, PurchasedItems.class);
            ShoppingList.this.startActivity(intent);
        }
    }

    private class AddShoppingListItemButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //Intent intent = new Intent(ShoppingList.this, TBD.class);
            //ShoppingList.this.startActivity(intent);
        }
    }

    public void readItems() {
        referenceItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot key : snapshot.getChildren()){
                    keys.add(key.getKey());
                    ShoppingItem item = key.getValue(ShoppingItem.class);
                    items.add(item);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
