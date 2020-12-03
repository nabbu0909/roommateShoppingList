package edu.uga.cs.roommateshoppinglist;

import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.List;

/**
 * This is an aadapter class for the RecyclerView to show all previous quizzes.
 */
public class ShoppingListRecyclerAdapter extends RecyclerView.Adapter<ShoppingListRecyclerAdapter.ShoppingListHolder>{

    public static final String DEBUG_TAG = "StateCapitalsRecyclerAdapter";

    private List<ShoppingItem> items;

    public ShoppingListRecyclerAdapter(List<ShoppingItem> items ) {
        this.items = items;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class ShoppingListHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        ImageButton editDropDown;

        public ShoppingListHolder(View itemView ) {
            super(itemView);

            itemName = (TextView) itemView.findViewById( R.id.itemName );
            editDropDown = (ImageButton) itemView.findViewById(R.id.imageButton);
            editDropDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(editDropDown.getContext(), editDropDown);
                    popup.getMenuInflater().inflate(R.menu.shopping_list_dropdown, popup.getMenu());
                    popup.show();

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Toast.makeText(editDropDown.getContext(),"You Clicked : " + item.getTitle() + " on " + itemName.getText(), Toast.LENGTH_SHORT).show();
                            switch (item.getItemId()){
                                case R.id.deleteShoppingItem:
                                    deleteShoppingListItem((String) itemName.getText());
                            }
                            return true;
                        }
                    });

                    popup.show();
                }

            });

        }
    }

    public void deleteShoppingListItem(String name){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("ShoppingList").orderByChild("name").equalTo(name);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
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
    public ShoppingListHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        // We need to make sure that all CardViews have the same, full width, allowed by the parent view.
        // This is a bit tricky, and we must provide the parent reference (the second param of inflate)
        // and false as the third parameter (don't attach to root).
        // Consequently, the parent view's (the RecyclerView) width will be used (match_parent).
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.shopping_item, parent, false );
        return new ShoppingListHolder( view );
    }

    @Override
    public void onBindViewHolder( ShoppingListHolder holder, int position ) {
        ShoppingItem item = items.get( position );
        holder.itemName.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
