package com.carlmeyer.questgeneratordemo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Item;
import com.carlmeyer.questgeneratordemo.ui.adapters.ItemsAdapter;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

public class ItemsFragment extends Fragment {

    private Realm realm;
    private RecyclerView rvItems;
    private Button btnAddItem;
    OrderedRealmCollection<Item> items;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_items, container, false);
        rvItems = root.findViewById(R.id.rvItems);

        realm = Realm.getDefaultInstance();
        btnAddItem = root.findViewById(R.id.btnAddItem);
        rvItems = root.findViewById(R.id.rvItems);

        setUpUI();

        setUpRecyclerView();
        return root;
    }

    /**
     * Set up RecyclerView
     */
    private void setUpRecyclerView() {
        // get a list of all the items in the DB
        items = realm.where(Item.class).findAll();
        // Create and set layoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvItems.setLayoutManager(layoutManager);
        // Initialize and set itemsAdapter with list of Items
        ItemsAdapter itemsAdapter = new ItemsAdapter(items);
        rvItems.setAdapter(itemsAdapter);
    }

    /**
     * Set up UI onclick listeners etc
     */
    private void setUpUI(){
        btnAddItem.setOnClickListener(v -> {
            showAddItemDialog();
        });
    }

    /**
     * Setup, Configure and Show the add item dialog
     */
    private void showAddItemDialog(){
        // set up the dialog
        LovelyCustomDialog dialog =  new LovelyCustomDialog(getContext())
                .setView(R.layout.dialog_add_item)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle(R.string.add_item)
                .setIcon(R.drawable.sword_cross_light);
        // add listeners
        dialog.setListener(R.id.btnDialogAddItem, btn -> {
            // get reference to the parent layout so we can find the EditText
            ConstraintLayout root = (ConstraintLayout) btn.getParent();
            EditText txtDialogItem = root.findViewById(R.id.txtAddItem);
            // add Item to database
            addItem(txtDialogItem.getText().toString());
            dialog.dismiss();
            rvItems.smoothScrollToPosition(items.size() - 1);
        });
        // show the dialog
        dialog.show();
    }

    /**
     * Add a new item to the database and update the list of Items
     * @param itemName - the new item's name
     */
    private void addItem(String itemName){

        // first add the Item to the database
        realm.executeTransaction(r -> {
            Item Item = r.createObject(Item.class, items.size() + 1);
            Item.setName(itemName);
        });

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
