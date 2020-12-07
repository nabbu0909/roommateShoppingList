package edu.uga.cs.roommateshoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SettleCostRecyclerAdapter extends RecyclerView.Adapter<SettleCostRecyclerAdapter.RoommateHolder> {

    private ArrayList<Roommates> roommatesArrayList;
    private Context context;

    //this class gets allows context to be set and the list of roommates (from firebase) to be populated
    public SettleCostRecyclerAdapter(Context context, ArrayList<Roommates> roommatesArrayList){
        this.context = context;
        this.roommatesArrayList = roommatesArrayList;
    }

    class RoommateHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView name, amountPaid;

        public RoommateHolder(View itemView ) {
            super(itemView);
            name = (TextView) itemView.findViewById( R.id.name );
            amountPaid = (TextView) itemView.findViewById( R.id.amountPaid );
            cardView = (CardView) itemView.findViewById(R.id.card_view_roomates);

        }
    }

    @Override
    public RoommateHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        // We need to make sure that all CardViews have the same, full width, allowed by the parent view.
        // This is a bit tricky, and we must provide the parent reference (the second param of inflate)
        // and false as the third parameter (don't attach to root).
        // Consequently, the parent view's (the RecyclerView) width will be used (match_parent).
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.roommate_card_view, parent, false );
        return new RoommateHolder( view );
    }

    @Override
    public void onBindViewHolder(final RoommateHolder holder, int position){
        Roommates roommate = roommatesArrayList.get(position);
        holder.name.setText(roommate.getName());
        holder.amountPaid.setText("Amount Paid: $" + roommate.getAmountPaid());

        holder.cardView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() { return roommatesArrayList.size(); }
}
