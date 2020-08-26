package com.carlmeyer.questgeneratordemo.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.data.Actions;
import com.carlmeyer.questgeneratordemo.questgenerator.data.Data;
import com.carlmeyer.questgeneratordemo.questgenerator.data.Motives;
import com.carlmeyer.questgeneratordemo.questgenerator.data.StoryFragments;
import com.carlmeyer.questgeneratordemo.questgenerator.models.DBAction;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Motivation;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Enemy;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Item;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Location;
import com.carlmeyer.questgeneratordemo.questgenerator.models.NPC;
import com.carlmeyer.questgeneratordemo.questgenerator.models.StoryFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

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
                item.setCategory(i.getCategory());
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
            // Add a list of motivations to the DB so we can easily access and retrieve them when adding or editing new story fragments
            Motives motives = new Motives();
            idCounter = 1;
            for (String m : motives.getMotives()) {
                Motivation motivation = r.createObject(Motivation.class, idCounter);
                motivation.setMotivation(m);
                Log.d("LoadData", "Added Motivation to DB: " + m);
                idCounter++;
            }

            // Get a reference to the Default Story Fragments that I have set up for the Quest Generator
            StoryFragments storyFragments = new StoryFragments();
            idCounter = 1;
            for (StoryFragment sf : storyFragments.getAllStoryFragments()) {
                StoryFragment storyFragment = r.createObject(StoryFragment.class, idCounter);
                storyFragment.setDescription(sf.getDescription());
                storyFragment.setActions(sf.getActions());
                Motivation motivation = r.where(Motivation.class).equalTo("motivation",sf.getMotivation()).findFirst();
                storyFragment.setMotivation(sf.getMotivation());
                // TODO: Figure out why it is only adding the last story fragment to the motivation
                if(motivation != null){
                    motivation.addStoryFragment(sf);
                }
                idCounter++;
                Log.d("LoadData", "Added Story Fragment to DB: " + sf.getMotivation() + " : " + sf.getDescription());
            }





            // Add a list of actions to the DB so we can easily access and retrieve them when adding or editing new story fragments
            Actions actions = new Actions();
            for (DBAction a : actions.getDBActions()) {
                DBAction dbaction = r.createObject(DBAction.class, a.getId());
                dbaction.setAction(a.getAction());
                dbaction.setConfigs(a.getConfigs());
                Log.d("LoadData", "Added Action to DB: " + a.getAction());
            }




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
