package com.carlmeyer.questgeneratordemo.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.data.Data;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Enemy;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Item;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Location;
import com.carlmeyer.questgeneratordemo.questgenerator.models.NPC;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_generator, R.id.nav_locations, R.id.nav_items, R.id.nav_enemies, R.id.nav_npcs)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Initialize Realm DB
        InitializeDB(this);
        // Get the default instance of the realm DB. (We don't wont to create a new instance each time)
        realm = Realm.getDefaultInstance();

        // if the DB is empty then load the default set of data
        if (realm.isEmpty()) {
            loadData();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Initializes Realm DB with default configurations
     *
     * @param context - application context
     */
    private void InitializeDB(Context context) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder().name("questgenerator.realm").build();
        Realm.setDefaultConfiguration(config);
        // Clear the Realm if the example was previously run.
        if (Realm.getDefaultConfiguration() != null) {
            Realm.deleteRealm(Realm.getDefaultConfiguration());
        }
    }

    /**
     * Load the initial set of Data for pixel dungeon
     * This will contain the default set of enemies, npcs, locations and items that is present
     * within pixel dungeon
     */
    private void loadData() {
        /* I have already set up all the default enemies, npcs, item and locations in the Data Class
           so I am going to use that class to add them to the realm db. I could have added the
           data to the db using JSON and GSON but since I already had the Data class that I made
           I decided to save some time and use that instead
         */
        Data data = new Data();
        // Start a transaction to add all the information to the realm db.
        // TODO: make async so that we do not cause the UI thread to wait for this transaction to finish
        //  note if we do above we need to deactivate generate button until the data has been added to the DB
        realm.executeTransaction(r -> {

            // TODO: Maybe thing about error handling. Maybe add try and catch when adding each data
            //  item so that the whole transaction doesn't fail and exit if by mistake there are
            //  conflicting primary keys. (e.g. if adding "rat" and add another "rat" and exception
            //  will be thrown since 2 primary keys with the same name may not exist.

            // Add all the Locations from Data to the DB
            int idCounter = 1;
            for (Location l : data.getLocations()) {
                Location location = r.createObject(Location.class, idCounter);
                location.setName(l.getName());
                idCounter++;
                Log.d("LoadData", "Added Location to DB: " + l.getName());
            }

            // Add all the Items from Data to the DB
            idCounter = 1;
            for (Item i : data.getItems()) {
                Item item = r.createObject(Item.class, idCounter);
                item.setName(i.getName());
                idCounter++;
                Log.d("LoadData", "Added Item to DB: " + i.getName());
            }
            // Add all the Enemies from Data to the DB
            idCounter = 1;
            for (Enemy e : data.getEnemies()) {
                Log.d("LoadData", "Added Enemy to DB: " + e.getName());
                Enemy enemy = r.createObject(Enemy.class, idCounter);
                enemy.setName(e.getName());
                Location location = realm.where(Location.class).equalTo("name", e.getLocation().getName()).findFirst();
                enemy.setLocation(location);
                if (location != null) {
                    location.getEnemies().add(enemy);
                }
                idCounter++;

            }
            // Add all the NPCs from Data to the DB
            idCounter = 1;
            for (NPC n : data.getNpcs()) {
                NPC npc = r.createObject(NPC.class, idCounter);
                npc.setName(n.getName());
                Location location = realm.where(Location.class).equalTo("name", n.getLocation().getName()).findFirst();
                npc.setLocation(location);
                if (location != null) {
                    location.getNpcs().add(npc);
                }
                idCounter++;
                Log.d("LoadData", "Added NPC to DB: " + n.getName());
            }

            /*
            I also want to add the initial set of Story Fragments to the DB so that we can later
            add the functionality to add and edit Story Fragments in the App.
             */

            // Get a reference to the Default Story Fragments that I have set up for the Quest Generator
            // TODO: subaction of type string[] not allowed by Realm. Need to change it
//            StoryFragments storyFragments = new StoryFragments();
//            for (StoryFragment sf : storyFragments.getAllStoryFragments()){
//                StoryFragment storyFragment = r.createObject(StoryFragment.class);
//                storyFragment.setMotive(sf.getMotive());
//                storyFragment.setDescription(sf.getDescription());
//                storyFragment.setActions(sf.getActions());
//                Log.d("LoadData", "Added Story Fragment to DB: " + sf.getMotive() + " : " + sf.getDescription());
//            }
        });
    }

    /**
     * When the activity is closed, this function will be called as per Activity Lifecycle
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Make sure we close our Realm instance so we don't have memory leaks
    }
}
