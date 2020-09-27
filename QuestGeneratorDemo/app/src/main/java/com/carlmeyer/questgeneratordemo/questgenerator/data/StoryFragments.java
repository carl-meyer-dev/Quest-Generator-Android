package com.carlmeyer.questgeneratordemo.questgenerator.data;


import com.carlmeyer.questgeneratordemo.questgenerator.models.DBAction;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Motivation;
import com.carlmeyer.questgeneratordemo.questgenerator.models.StoryFragment;

import java.util.ArrayList;
import java.util.List;

public class StoryFragments {

    List<StoryFragment> storyFragments = new ArrayList<>();

    List<DBAction> actions = new ArrayList<>();

    // Knowledge Story Fragments
    StoryFragment deliverItem = new StoryFragment(
            1,
            Motives.KNOWLEDGE,
            "deliver item for study",

            new String[]{
                    Actions.GET_ITEM,
                    Actions.GOTO_LOC,
                    Actions.REPORT_NPC
            },
            "Dear Adventurer, I need someone to deliver an item of great importance that needs to be studied. " +
                    "Could you please deliver the $item1 to the $npc1 in $location1?. I will reward you once the item has been delivered.",
            new String[]{
                    "$item1",
                    "$location1",
                    "$npc1"
            }
    );

    StoryFragment interviewNPC = new StoryFragment(
            2,
            Motives.KNOWLEDGE,
            "interview an NPC",
            new String[]{
                    Actions.GOTO_LOC,
                    Actions.LISTEN_NPC,
                    Actions.GOTO_LOC,
                    Actions.REPORT_NPC
            },
            "Dear Adventurer, " +
                    "I need you to help me interview someone in the $location1. " +
                    "Go to the $location1 and talk to the $npc1. Afterwards go to the $location2 and tell the $npc2 all that you have learned. " +
                    "You will be rewarded for your efforts.",
            new String[]{
                    "$location1",
                    "$npc1",
                    "$location2",
                    "$npc2"
            }
    );

    public List<StoryFragment> getKnowledgeStoryFragments() {
        List<StoryFragment> knowledgeStoryFragments = new ArrayList<>();
        knowledgeStoryFragments.add(deliverItem);
        knowledgeStoryFragments.add(interviewNPC);
        return knowledgeStoryFragments;
    }

    StoryFragment killPests = new StoryFragment(
            6,
            Motives.COMFORT,
            "kill pests",
            new String[]{
                    Actions.GOTO_LOC,
                    Actions.KILL_ENEMY,
                    Actions.REPORT_NPC,
            },
            "Dear Adventurer,  " +
                    "we have had troubles with a monster roaming at a nearby location. " +
                    "Please go to the $location1 and kill the $enemy1 in that area. " +
                    "Afterwards you can speak with the $npc1 to claim your reward. ",
            new String[]{
                    "$location1",
                    "$enemy1",
                    "$npc1"
            }

    );

    public List<StoryFragment> getComfortFragments() {
        List<StoryFragment> comfortStoryFragments = new ArrayList<>();
        comfortStoryFragments.add(killPests);
        return comfortStoryFragments;
    }

    // Justice Story Fragments
    StoryFragment getRevenge = new StoryFragment(
            7,
            Motives.JUSTICE,
            "get revenge",
            new String[]{
                    Actions.GOTO_LOC,
                    Actions.KILL_ENEMY,
                    Actions.REPORT_NPC,
            },
            "I am looking for a mercenary that can help me with revenge. " +
                    "My father was killed by the $enemy1. " +
                    "The $enemy1 has last been spotted at the $location1. " +
                    "Go to the $location1 and kill the $enemy and I shall reward you handsomely. " +
                    "You can collect your rewards from the $npc1 when you have completed my task.",
            new String[]{
                    "$location1",
                    "$enemy1",
                    "$npc1"
            }
    );


    public List<StoryFragment> getJusticeStoryFragments() {
        List<StoryFragment> justiceStoryFragments = new ArrayList<>();
        justiceStoryFragments.add(getRevenge);
        return justiceStoryFragments;
    }

    public List<StoryFragment> getAllStoryFragments() {
        List<StoryFragment> storyFragments = new ArrayList<>();
        storyFragments.add(deliverItem);
        storyFragments.add(interviewNPC);
        storyFragments.add(killPests);
        storyFragments.add(getRevenge);
        return storyFragments;
    }

    public StoryFragment getStoryFragmentById(int id) {
        for (StoryFragment storyFragment : getAllStoryFragments()) {
            if (storyFragment.getId() == id) {
                return storyFragment;
            }
        }
        // no story fragment with such ID found
        return null;
    }

}
