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
    DatabaseReference databaseShoppingList = FirebaseDatabase.getInstance().getReference("ShoppingList");
    String name;
    EditText nameText;

    public static EditShoppingItemDialogFragment newInstance(String oldName){
        EditShoppingItemDialogFragment fragment = new EditShoppingItemDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString("oldName", oldName);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View v = inflater.inflate(R.layout.add_shopping_item_dialog, null);
        nameText = (EditText) v.findViewById(R.id.addShoppingItem);

        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Edit Name", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String oldName = getArguments().getString("oldName");
                        name = nameText.getText().toString();
                        ShoppingItem item = new ShoppingItem(name);
                        Query editQuery = databaseShoppingList.orderByChild("name").equalTo(oldName);

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
