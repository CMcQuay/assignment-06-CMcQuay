package com.ualr.recyclerviewassignment.Utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ualr.recyclerviewassignment.R;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {

    private List<Inbox> inboxItems;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Inbox item);
        void onDeleteClick(int position);
    }

    public InboxAdapter(List<Inbox> inboxItems) {
        this.inboxItems = inboxItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Inbox item = inboxItems.get(position);
        holder.bind(item, listener);
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
        }

        public void bind(final Inbox item, final OnItemClickListener listener) {
            textName.setText(item.getFrom());
            textEmail.setText(item.getEmail());
            textMessage.setText(item.getMessage());
            textDate.setText(item.getDate());
            textCircle.setText(String.valueOf(item.getFrom().charAt(0)));

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
