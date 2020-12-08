package edu.uga.cs.roommateshoppinglist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class SettleCost extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference referenceItems = database.getReference("PurchaseList");
    private ArrayList<Roommates> roommatesArrayList = new ArrayList<>();
    private ArrayList<Item> PurchasedItems = new ArrayList<>();
    private TextView finalCost;
    private Button confirmButton;

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

        finalCost = (TextView) findViewById(R.id.textView4);
        confirmButton = (Button) findViewById(R.id.button4);
        final Context mContext = this;
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePurchaseItems();
                Intent intent = new Intent( mContext, ShoppingList.class );
                startActivity( intent );
            }
        });
    }
    public void onStart() {
        super.onStart();

        referenceItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Double totalCost = 0.0;
                roommatesArrayList.clear();
                PurchasedItems.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot key : snapshot.getChildren()){
                    keys.add(key.getKey());
                    Item item = key.getValue(Item.class);
                    PurchasedItems.add(item);
                }

                //@calvin do stuff that turns the list of purchased items into roommates and whatever calculations here
                for (Item item: PurchasedItems) {
                    if (roommatesArrayList.size() == 0) {
                        Roommates newR = new Roommates(item.getRoommateName(), item.getItemPrice());
                        roommatesArrayList.add(newR);
                    } else {
                        boolean roommateFound = false;
                        for (Roommates r : roommatesArrayList) {
                            if (item.getRoommateName().equals(r.getName())) {
                                Double previousAmount = Double.parseDouble(r.getAmountPaid());
                                Double toAdd = Double.parseDouble(item.getItemPrice());
                                Double total = round(previousAmount+toAdd);
                                r.setAmountPaid(total.toString());
                                roommateFound = true;
                            }
                        }
                        if (!roommateFound) {
                            Roommates newR = new Roommates(item.getRoommateName(), item.getItemPrice());
                            roommatesArrayList.add(newR);
                        }
                    }
                    totalCost += Double.parseDouble(item.getItemPrice());
                }
                if (PurchasedItems.size() == 0){
                    finalCost.setText("No items were purchased");
                }
                else {
                    totalCost /= roommatesArrayList.size();
                    totalCost = round(totalCost);
                    finalCost.setText("Each person owes: $" + totalCost);
                }

                recyclerAdapter = new SettleCostRecyclerAdapter(roommatesArrayList);
                recyclerView.setAdapter(recyclerAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private static double round(Double d){
        BigDecimal bd = BigDecimal.valueOf(d);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void deletePurchaseItems(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query delQuery = ref.child("PurchaseList").orderByChild("name");

        delQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot delSnapshot: dataSnapshot.getChildren()) {
                    delSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
