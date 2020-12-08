package edu.uga.cs.roommateshoppinglist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * This dialog fragment prompts the user to confirm purchase of the item, moving it from
 * the shopping list to the purchased list after confirmation.
 */
public class PurchaseItemDialogFragment extends DialogFragment {
    DatabaseReference databaseShoppingList = FirebaseDatabase.getInstance().getReference("PurchaseList");
    DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("users");
    String name;
    EditText cost;
    String currentUser;

    public static PurchaseItemDialogFragment newInstance(String Name){
        PurchaseItemDialogFragment fragment = new PurchaseItemDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString("Name", Name);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View v = inflater.inflate(R.layout.purchase_item_dialog, null);
        cost = (EditText) v.findViewById(R.id.purchaseShoppingItem);

        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Confirm Purchase", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String name = getArguments().getString("Name");
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        currentUser = user.getDisplayName();

                        Item item = new Item(name, cost.getText().toString(), currentUser);

                        String dbId = databaseShoppingList.push().getKey();
                        item.setItemID(dbId);
                        databaseShoppingList.child(dbId).setValue(item);
                        deleteShoppingListItem(name);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PurchaseItemDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
    //remove the shopping item from the shopping list since its now in the purchase list
    public void deleteShoppingListItem(String name){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query delQuery = ref.child("ShoppingList").orderByChild("name").equalTo(name);

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

    private String getUserName(){
        DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String userEmail = user.getEmail();

        Query nameQuery = databaseUsers.orderByChild("email").equalTo(userEmail);

        nameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot delSnapshot: dataSnapshot.getChildren()) {
                    User userDB = delSnapshot.getValue(User.class);
                    if(userDB.getName() != null) {
                        currentUser = userDB.getName();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return currentUser;
    }
}
