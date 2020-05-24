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
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

public class ItemsFragment extends Fragment {

    private Realm realm;
    private RecyclerView rvItems;
    private Button btnAddItem;
    private OrderedRealmCollection<Item> items;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_items, container, false);
        rvItems = root.findViewById(R.id.rvItems);

        realm = Realm.getDefaultInstance();
        btnAddItem = root.findViewById(R.id.btnAddItem);
        rvItems = root.findViewById(R.id.rvItems);

        getItems();

        setUpUI();

        setUpRecyclerView();
        return root;
    }

    /**
     * Set up RecyclerView
     */
    private void setUpRecyclerView() {
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

        dialog.configureView(v -> {
            EditText txtDialogItem = v.findViewById(R.id.txtAddItem);
            Button btnDialogAddItem = v.findViewById(R.id.btnDialogAddItem);

            btnDialogAddItem.setOnClickListener(v1 -> {
                if(txtDialogItem.getText().toString().isEmpty()){
                    // Show error dialog
                    new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                            .setTopColorRes(R.color.colorPrimary)
                            .setButtonsColorRes(R.color.colorAccent)
                            .setIcon(R.drawable.alert_box_light)
                            .setTitle(R.string.error)
                            .setMessage(R.string.item_may_not_be_empty)
                            .setPositiveButton(android.R.string.ok,v2 -> {})
                            .show();
                }else{
                    addItem(txtDialogItem.getText().toString());
                    dialog.dismiss();
                }

            });

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

        // get the position of the item that has been inserted alphabetically into the list
        int position = 0;
        for (Item item : items){
            if(item.getName().equals(itemName)){
                break;
            }
            position++;
        }
        // scroll to that position
        rvItems.smoothScrollToPosition(position);
    }

    /**
     * Get the items from the DB and sort them alphabetically
     */
    private void getItems(){
        items = realm.where(Item.class).findAll();
        items = items.sort("name");
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
