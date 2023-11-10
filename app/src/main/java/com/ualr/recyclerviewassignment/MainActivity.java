package com.ualr.recyclerviewassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.Utils.InboxAdapter;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

// TODO 05. Create a new Adapter class and the corresponding ViewHolder class in a different file. The adapter will be used to populate
//  the recyclerView and manage the interaction with the items in the list
// TODO 06. Detect click events on the list items. Implement a new method to toggle items' selection in response to click events
// TODO 07. Detect click events on the thumbnail located on the left of every list row when the corresponding item is selected.
//  Implement a new method to delete the corresponding item in the list
// TODO 08. Create a new method to add a new item on the top of the list. Use the DataGenerator class to create the new item to be added.

public class MainActivity extends AppCompatActivity implements InboxAdapter.OnItemClickListener{

    private FloatingActionButton mFAB;
    private InboxAdapter adapter;
    private List<Inbox> inboxItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_multi_selection);
        initComponent();
    }

    private void initComponent() {
        // TODO 01. Generate the item list to be displayed using the DataGenerator class
        inboxItems = DataGenerator.getInboxData(this);

        // TODO 03. Do the setup of a new RecyclerView instance to display the item list properly
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO 04. Define the layout of each item in the list
        // TODO 09. Create a new instance of the created Adapter class and bind it to the RecyclerView instance created in step 03
        adapter = new InboxAdapter(inboxItems, this);
        recyclerView.setAdapter(adapter);

        mFAB = findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 10. Invoke the method created to a new item to the top of the list so it's
                //  triggered when the user taps the Floating Action Button
                Inbox newInboxItem = DataGenerator.getRandomInboxItem(MainActivity.this);
                inboxItems.add(0, newInboxItem); // Add new item to the top of the list
                adapter.notifyDataSetChanged(); // Notify the adapter of the data change
            }
        });
    }
    @Override
    public void onItemClick(Inbox item) {
        int previousSelectedPosition = -1;

        // Find the previously selected item position and update its state
        for (int i = 0; i < inboxItems.size(); i++) {
            Inbox inboxItem = inboxItems.get(i);
            if (inboxItem.isSelected()) {
                inboxItem.setSelected(false);
                previousSelectedPosition = i;
                break;
            }
        }

        // Find the clicked item position and update its state
        int clickedPosition = inboxItems.indexOf(item);
        item.setSelected(true);
        // Notify the adapter of the changes in both previously selected and clicked items
        if (previousSelectedPosition != -1) {
            adapter.notifyItemChanged(previousSelectedPosition);
        }
        adapter.notifyItemChanged(clickedPosition);
    }
    @Override
    public void onDeleteClick(Inbox item) {
        inboxItems.remove(item);
        adapter.notifyDataSetChanged(); // Notify adapter of the data change
    }

}