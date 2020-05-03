package com.example.questgenerator.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.questgenerator.R;
import com.example.questgenerator.models.Enemy;
import com.example.questgenerator.models.Item;
import com.example.questgenerator.models.Location;
import com.example.questgenerator.models.NPC;
import com.example.questgenerator.models.StoryFragment;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public List<Location> locations;
    public List<NPC> npcs;
    public List<Enemy> enemies;
    public List<Item> items;

    public List<StoryFragment> knowledgeStoryFragments;
    public List<StoryFragment> comfortStoryFragments;
    public List<StoryFragment> justiceStoryFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
