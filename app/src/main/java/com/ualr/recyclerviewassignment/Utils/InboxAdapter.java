package com.ualr.recyclerviewassignment.Utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ualr.recyclerviewassignment.R;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {


    private List<Inbox> inboxItems;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Inbox item);
        void onDeleteClick(Inbox item);
    }

    public InboxAdapter(List<Inbox> inboxItems, OnItemClickListener listener) {
        this.inboxItems = inboxItems;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Inbox item = inboxItems.get(position);
        holder.bind(item, listener);

        // Set other data to the ViewHolder views
        holder.textName.setText(item.getFrom());
        holder.textEmail.setText(item.getEmail());
        holder.textMessage.setText(item.getMessage());
        holder.textDate.setText(item.getDate());

        // Set click listeners for item views
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(item);
                }
            }
        });

        // Set click listener for the gray circle
        holder.textCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDeleteClick(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return inboxItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textCircle, textName, textEmail, textMessage, textDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textEmail = itemView.findViewById(R.id.textEmail);
            textMessage = itemView.findViewById(R.id.textMessage);
            textDate = itemView.findViewById(R.id.textDate);
            textCircle = itemView.findViewById(R.id.textCircle);

            textCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Inbox item = inboxItems.get(getBindingAdapterPosition());
                    if (listener != null && item.isSelected()) {
                        listener.onDeleteClick(item);
                    }
                }
            });
        }

        public void bind(final Inbox item, final OnItemClickListener listener) {
            textName.setText(item.getFrom());
            textEmail.setText(item.getEmail());
            textMessage.setText(item.getMessage());
            textDate.setText(item.getDate());
            textCircle.setText(String.valueOf(item.getFrom().charAt(0)));

            // Change the background color and textCircle based on the selection state
            if (item.isSelected()) {
                // Change the background color to grey
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.grey_40));

                // Change the text inside the gray circle to "X"
                textCircle.setText("X");
            } else {
                // Change the background color back to the default color
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.cardview_light_background));
            }
            // Handle item click events
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
