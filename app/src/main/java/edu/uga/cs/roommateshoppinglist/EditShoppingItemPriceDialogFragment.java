package edu.uga.cs.roommateshoppinglist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditShoppingItemPriceDialogFragment extends DialogFragment {
    DatabaseReference databaseShoppingList = FirebaseDatabase.getInstance().getReference("PurchaseList");
    String price;
    EditText priceText;

    public static EditShoppingItemDialogFragment newInstance(String oldPrice){
        EditShoppingItemDialogFragment fragment = new EditShoppingItemDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString("oldPrice", oldPrice);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View v = inflater.inflate(R.layout.purchase_item_dialog, null);
        priceText = (EditText) v.findViewById(R.id.purchaseShoppingItem);

        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Edit Price", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String oldPrice = getArguments().getString("oldPrice");
                        price = priceText.getText().toString();
                        ShoppingItem item = new ShoppingItem(price);
                        Query editQuery = databaseShoppingList.orderByChild("itemPrice").equalTo(oldPrice);

                        editQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot delSnapshot: dataSnapshot.getChildren()) {
                                    delSnapshot.getRef().child("itemPrice").setValue(price);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditShoppingItemPriceDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
