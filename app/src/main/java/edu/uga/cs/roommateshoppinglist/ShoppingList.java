package edu.uga.cs.roommateshoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShoppingList extends AppCompatActivity {

    private RecyclerView recyclerView;
    Button addShoppingListItem, viewPurchasedItems;

    public static final String DEBUG_TAG = "ShoppingList";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        recyclerView = findViewById(R.id.shoppingListRecyclerView);
        addShoppingListItem = findViewById(R.id.addNewItemButton);
        addShoppingListItem.setOnClickListener(new AddShoppingListItemButtonClickListener());
        viewPurchasedItems = findViewById(R.id.viewPurchasedItemsButton);
        viewPurchasedItems.setOnClickListener(new PurchasedItemsButtonClickListener());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
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

}
