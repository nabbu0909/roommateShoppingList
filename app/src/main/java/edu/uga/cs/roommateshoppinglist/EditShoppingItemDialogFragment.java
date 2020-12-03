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

public class EditShoppingItemDialogFragment extends DialogFragment {
    DatabaseReference databaseShoppingList = FirebaseDatabase.getInstance().getReference();
    String name;
    EditText nameText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View v = inflater.inflate(R.layout.add_shopping_item_dialog, null);
        nameText = (EditText) v.findViewById(R.id.addShoppingItem);

        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Add Item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        name = nameText.getText().toString();
                        ShoppingItem item = new ShoppingItem(name);
                        Query editQuery = databaseShoppingList.child("ShoppingList").orderByChild("name").equalTo(name);

                        editQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot delSnapshot: dataSnapshot.getChildren()) {
                                    delSnapshot.getRef().child("name").setValue(name);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                        databaseShoppingList.child("ShoppingList").setValue(item);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditShoppingItemDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
