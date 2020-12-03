package edu.uga.cs.roommateshoppinglist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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

        public ShoppingListHolder(View itemView ) {
            super(itemView);

            itemName = (TextView) itemView.findViewById( R.id.itemName );
        }
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
