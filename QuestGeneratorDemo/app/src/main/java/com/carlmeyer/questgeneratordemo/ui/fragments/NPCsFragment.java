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
import com.carlmeyer.questgeneratordemo.questgenerator.models.NPC;
import com.carlmeyer.questgeneratordemo.ui.adapters.NPCsAdapter;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

public class NPCsFragment extends Fragment {

    private Realm realm;
    private RecyclerView rvNPCS;
    private Button btnAddNPC;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_npcs, container, false);
        rvNPCS = root.findViewById(R.id.rvNPCS);

        realm = Realm.getDefaultInstance();
        btnAddNPC = root.findViewById(R.id.btnAddNPC);
        rvNPCS = root.findViewById(R.id.rvNPCS);
        setUpRecyclerView();
        return root;
    }

    /**
     * Set up RecyclerView
     */
    private void setUpRecyclerView() {
        // get a list of all the locations in the DB
        OrderedRealmCollection<NPC> npcs = realm.where(NPC.class).findAll();
        // Create and set layoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvNPCS.setLayoutManager(layoutManager);
        // Initialize and set locationsAdapter with list of locations
        NPCsAdapter npcsAdapter = new NPCsAdapter(npcs);
        rvNPCS.setAdapter(npcsAdapter);
    }

    /*
     * It is good practice to null the reference from the view to the adapter when it is no longer needed.
     * Because the <code>RealmRecyclerViewAdapter</code> registers itself as a <code>RealmResult.ChangeListener</code>
     * the view may still be reachable if anybody is still holding a reference to the <code>RealmResult>.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        rvNPCS.setAdapter(null);
        realm.close();
    }
}
