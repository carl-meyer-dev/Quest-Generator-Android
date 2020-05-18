package com.carlmeyer.questgeneratordemo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Item;
import com.carlmeyer.questgeneratordemo.ui.adapters.ItemsAdapter;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

public class ItemsFragment extends Fragment {

    private Realm realm;
    private RecyclerView rvItems;
    private Button btnAddItem;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_items, container, false);
        rvItems = root.findViewById(R.id.rvItems);

        realm = Realm.getDefaultInstance();
        btnAddItem = root.findViewById(R.id.btnAddItem);
        rvItems = root.findViewById(R.id.rvItems);
        setUpRecyclerView();
        return root;
    }

    /**
     * Set up RecyclerView
     */
    private void setUpRecyclerView() {
        // get a list of all the items in the DB
        OrderedRealmCollection<Item> items = realm.where(Item.class).findAll();
        // Create and set layoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvItems.setLayoutManager(layoutManager);
        // Initialize and set itemsAdapter with list of Items
        ItemsAdapter itemsAdapter = new ItemsAdapter(items);
        rvItems.setAdapter(itemsAdapter);
    }

    /*
     * It is good practice to null the reference from the view to the adapter when it is no longer needed.
     * Because the <code>RealmRecyclerViewAdapter</code> registers itself as a <code>RealmResult.ChangeListener</code>
     * the view may still be reachable if anybody is still holding a reference to the <code>RealmResult>.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        rvItems.setAdapter(null);
        realm.close();
    }
}
