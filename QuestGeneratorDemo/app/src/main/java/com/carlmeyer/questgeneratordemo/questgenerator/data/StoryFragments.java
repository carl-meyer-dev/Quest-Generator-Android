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
                    Actions.GET,
                    Actions.GOTO,
                    Actions.REPORT
            }
    );

    StoryFragment interviewNPC = new StoryFragment(
            2,
            Motives.KNOWLEDGE,
            "interview an NPC",
            new String[]{
                    Actions.GOTO,
                    Actions.LISTEN,
                    Actions.GOTO,
                    Actions.REPORT
            }
    );

    StoryFragment useItem = new StoryFragment(
            3,
            Motives.KNOWLEDGE,
            "use an item in the field",
            new String[]{
                    Actions.GET,
                    Actions.GOTO,
                    Actions.USE,
                    Actions.GOTO,
                    Actions.REPORT,
            }
    );

    public List<StoryFragment> getKnowledgeStoryFragments() {
        List<StoryFragment> knowledgeStoryFragments = new ArrayList<>();
        knowledgeStoryFragments.add(deliverItem);
        knowledgeStoryFragments.add(interviewNPC);
        knowledgeStoryFragments.add(useItem);
        return knowledgeStoryFragments;
    }

    // Comfort Story Fragments
    StoryFragment gatherItems = new StoryFragment(
            4,
            Motives.COMFORT,
            "gather items",
            new String[]{
                    Actions.GOTO,
                    Actions.GET,
                    Actions.GOTO,
                    Actions.REPORT,
            }
    );

    StoryFragment getLuxuries = new StoryFragment(
            5,
            Motives.COMFORT,
            "get luxuries",
            new String[]{
                    Actions.GET,
                    Actions.GOTO,
                    Actions.REPORT,
            }
    );

    StoryFragment killPests = new StoryFragment(
            6,
            Motives.COMFORT,
            "kill pests",
            new String[]{
                    Actions.GOTO,
                    Actions.KILL,
                    Actions.GOTO,
                    Actions.REPORT,
            }
    );

    public List<StoryFragment> getComfortFragments() {
        List<StoryFragment> comfortStoryFragments = new ArrayList<>();
        comfortStoryFragments.add(gatherItems);
        comfortStoryFragments.add(getLuxuries);
        comfortStoryFragments.add(killPests);
        return comfortStoryFragments;
    }

    // Justice Story Fragments
    StoryFragment getRevenge = new StoryFragment(
            7,
            Motives.JUSTICE,
            "get revenge",
            new String[]{
                    Actions.GOTO,
                    Actions.KILL,
                    Actions.REPORT,
            }
    );

    StoryFragment recoverStolenItems = new StoryFragment(
            8,
            Motives.JUSTICE,
            "recover stolen items",
            new String[]{
                    Actions.GET,
                    Actions.GOTO,
                    Actions.REPORT,
            }
    );

    StoryFragment stealBackStolenItems = new StoryFragment(
            9,
            Motives.JUSTICE,
            "steal back stolen items",
            new String[]{
                    Actions.GOTO,
                    Actions.GET,
                    Actions.GOTO,
                    Actions.REPORT,
            }
    );

    public List<StoryFragment> getJusticeStoryFragments() {
        List<StoryFragment> justiceStoryFragments = new ArrayList<>();
        justiceStoryFragments.add(getRevenge);
        justiceStoryFragments.add(recoverStolenItems);
        justiceStoryFragments.add(stealBackStolenItems);
        return justiceStoryFragments;
    }

    public List<StoryFragment> getAllStoryFragments() {
        List<StoryFragment> storyFragments = new ArrayList<>();
//        storyFragments.add(deliverItem);
//        storyFragments.add(interviewNPC);
//        storyFragments.add(useItem);
//        storyFragments.add(gatherItems);
//        storyFragments.add(getLuxuries);
//        storyFragments.add(killPests);
//        storyFragments.add(getRevenge);
//        storyFragments.add(recoverStolenItems);
//        storyFragments.add(stealBackStolenItems);
        return storyFragments;
    }

    public StoryFragment getStoryFragmentById(int id){
        for (StoryFragment storyFragment: getAllStoryFragments()) {
            if (storyFragment.getId() == id){
                return storyFragment;
            }
        }
        // no story fragment with such ID found
        return null;
    }

}
