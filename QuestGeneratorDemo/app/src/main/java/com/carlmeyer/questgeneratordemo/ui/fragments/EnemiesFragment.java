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
import com.carlmeyer.questgeneratordemo.questgenerator.models.Enemy;
import com.carlmeyer.questgeneratordemo.ui.adapters.EnemiesAdapter;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

public class EnemiesFragment extends Fragment {

    private Realm realm;
    private RecyclerView rvEnemies;
    private Button btnAddEnemy;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_enemies, container, false);
        rvEnemies = root.findViewById(R.id.rvEnemies);

        realm = Realm.getDefaultInstance();
        btnAddEnemy = root.findViewById(R.id.btnAddEnemy);
        rvEnemies = root.findViewById(R.id.rvEnemies);
        setUpRecyclerView();
        return root;
    }

    /**
     * Set up RecyclerView
     */
    private void setUpRecyclerView() {
        // get a list of all the locations in the DB
        OrderedRealmCollection<Enemy> enemies = realm.where(Enemy.class).findAll();
        // Create and set layoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvEnemies.setLayoutManager(layoutManager);
        // Initialize and set locationsAdapter with list of locations
        EnemiesAdapter enemiesAdapter = new EnemiesAdapter(enemies);
        rvEnemies.setAdapter(enemiesAdapter);
    }

    /*
     * It is good practice to null the reference from the view to the adapter when it is no longer needed.
     * Because the <code>RealmRecyclerViewAdapter</code> registers itself as a <code>RealmResult.ChangeListener</code>
     * the view may still be reachable if anybody is still holding a reference to the <code>RealmResult>.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        rvEnemies.setAdapter(null);
        realm.close();
    }
}
