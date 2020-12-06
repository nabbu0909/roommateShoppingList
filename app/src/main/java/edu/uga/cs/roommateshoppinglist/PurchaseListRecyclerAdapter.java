package edu.uga.cs.roommateshoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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

        public PurchaseListHolder(View itemView ) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.itemName);
            itemPrice = (TextView) itemView.findViewById(R.id.itemPrice);
            roommateName = (TextView) itemView.findViewById(R.id.roommateName);
            date = (TextView) itemView.findViewById(R.id.date);
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
        Item item = items.get( position );
        holder.itemName.setText("Item: " + item.getItemName());
        holder.itemPrice.setText("Item Price: $" + item.getItemPrice());
        holder.roommateName.setText("Roommate: " + item.getRoommateName());
        holder.date.setText("Date Purchased: " + item.getDate());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
