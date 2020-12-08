package edu.uga.cs.roommateshoppinglist;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PurchaseListRecyclerAdapter extends RecyclerView.Adapter<PurchaseListRecyclerAdapter.PurchaseListHolder> {

    public static final String DEBUG_TAG = "PurchaseListRecyclerAdapter";

    private List<Item> items;

    public PurchaseListRecyclerAdapter(List<Item> items ) {
        this.items = items;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class PurchaseListHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemPrice, roommateName, date;
        Button changePrice, deleteItem;

        public PurchaseListHolder(View itemView ) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.itemName);
            itemPrice = (TextView) itemView.findViewById(R.id.itemPrice);
            roommateName = (TextView) itemView.findViewById(R.id.roommateName);
            date = (TextView) itemView.findViewById(R.id.date);
            changePrice = (Button) itemView.findViewById(R.id.changePrice);
            deleteItem = (Button) itemView.findViewById(R.id.button5);
        }
    }

    @Override
    public PurchaseListHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        // We need to make sure that all CardViews have the same, full width, allowed by the parent view.
        // This is a bit tricky, and we must provide the parent reference (the second param of inflate)
        // and false as the third parameter (don't attach to root).
        // Consequently, the parent view's (the RecyclerView) width will be used (match_parent).
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.purchased_item_view, parent, false );
        return new PurchaseListHolder( view );
    }

    @Override
    public void onBindViewHolder(PurchaseListHolder holder, int position ) {
        final Item item = items.get( position );
        holder.itemName.setText("Item: " + item.getItemName());
        holder.itemPrice.setText("Item Price: $" + item.getItemPrice());
        holder.roommateName.setText("Roommate: " + item.getRoommateName());
        holder.date.setText("Date Purchased: " + item.getDate());
        final DialogFragment editPurchaseDialog = new EditPurchaseItemPriceDialogFragment().newInstance(item.getItemID());

        holder.changePrice.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                editPurchaseDialog.show(fragmentManager, "EditPurchaseItemPriceDialogFragment");
            }
        });
        holder.deleteItem.setOnClickListener(new View.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                DatabaseReference databaseShoppingList = FirebaseDatabase.getInstance().getReference("ShoppingList");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String currentUser = user.getDisplayName();

                ShoppingItem newItem = new ShoppingItem(item.getItemName());
                String dbID = databaseShoppingList.push().getKey();
                databaseShoppingList.child(dbID).setValue(newItem);
                deletePurchaseListItem(item.getItemID());
            }
        });
    }

    public void deletePurchaseListItem(String name){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query delQuery = ref.child("PurchaseList").orderByChild("itemID").equalTo(name);

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

    @Override
    public int getItemCount() {
        return items.size();
    }

}
