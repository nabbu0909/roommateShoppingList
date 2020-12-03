package edu.uga.cs.roommateshoppinglist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddShoppingItemDialogFragment extends DialogFragment {

    DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("ShoppingList");
    String name;
    EditText nameText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View v = inflater.inflate(R.layout.add_shopping_item_dialog, null);

        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Add Item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        nameText = (EditText) v.findViewById(R.id.addShoppingItem);
                        name = nameText.getText().toString();
                        String dbId = databaseUsers.push().getKey();
                        ShoppingItem item = new ShoppingItem(name);
                        databaseUsers.child(dbId).setValue(item);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddShoppingItemDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
