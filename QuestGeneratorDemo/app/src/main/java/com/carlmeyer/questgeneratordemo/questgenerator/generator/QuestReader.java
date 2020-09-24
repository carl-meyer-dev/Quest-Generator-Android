package com.carlmeyer.questgeneratordemo.questgenerator.generator;


import android.util.Log;

import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.DBAction;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Quest;
import com.carlmeyer.questgeneratordemo.questgenerator.models.StoryFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestReader {

    private String questMotivationText;
    private String questDescriptionText;
    private List<Action> questSteps = new ArrayList<>();
    private StringBuilder questStepsText = new StringBuilder();
    private int stepCount = 0;
    private String indent = "   ";

    /**
     * Traverse the quest tree and create an ordered list of actions that need to be completed
     * The tree is traversed in pre-order traversal in order to reach all the leaf nodes that need to
     * be read in order to get the actions in the correct order.
     *
     * @param quest - the generated quest that needs to be read
     */
    public void readQuest(Quest quest) {
        questMotivationText = quest.motivation;
        questDescriptionText = quest.description;

        /*
        The quest that is given is a tree and the root node represents the main quest that needs to
        be completed. The root action has child nodes which represent the sub-actions that need to be
        completed in order to complete the quest. A sub-action may also be a subquest having its own
        set of sub-actions that need to be completed in order to complete the subquest.
         */

        //read the sub-actions of the quest
        readSubActions(quest.root, 0);

        String questDialog;
        if (quest.storyFragment.questDialog != null) {
            questDialog = quest.storyFragment.questDialog;
            questDialogMapper(quest, questSteps);
        } else {
            questDialog = generateDialog(quest, questSteps);
        }

        quest.dialog = questDialog;

    }

    /**
     * Read and traverse any sub-actions or sub-quests of an action and append each action to a list
     * of quest steps that need to completed in order to complete the quest.
     * <p>
     * note we are also building a string of quest steps while reading the quests in case we
     * need to output a string list of quest steps. This is related to the questStepsText variable.
     * <p>
     * The ordered list of actions that need to be completed will be stored in the questSteps array
     * as we traverse and read the leaf nodes in pre-order traversal in the quest tree.
     * <p>
     * for a visual representation you can look at this picture:
     * https://drive.google.com/file/d/1w0dPhB4T-9wWuYfmYFIxu36OJUeaFFsP/view?usp=sharing
     *
     * @param action - action to complete
     * @param depth  - depth of the tree
     */
    private void readSubActions(Action action, int depth) {

        questStepsText.append("\n");

        for (int i = 0; i <= depth; i++) {
            questStepsText.append(indent);
        }

        if (action.subActions.size() == 0) {
            questSteps.add(action);
            questStepsText.append(action.actionDescription);
            stepCount++;
        } else {
            questSteps.add(action);
            questStepsText.append(action.actionDescription);
            for (Action a : action.subActions) {
                readSubActions(a, depth + 1);
            }
            stepCount++;
        }
    }

    public String getQuestDescriptionText() {
        return questDescriptionText;
    }

    public String getQuestMotivationText() {
        return questMotivationText;
    }

    public List<Action> getQuestSteps() {
        return questSteps;
    }

    /**
     * Convert the quest actions into a list of steps (string)
     *
     * @return - a string containing all the actions and indented per sub action
     */
    public String getQuestStepsToString() {
        StringBuilder steps = new StringBuilder();
        int count = 0;
        for (Action action : questSteps) {
            if (count == 0) {
                steps.append("Main Quest: ").append(action.actionDescription).append("\n").append("\n");
            } else {
                steps.append(count).append(".   ").append(action.actionDescription).append("\n");
            }
            count++;
        }
        return steps.toString();
    }

    public String getQuestStepsText() {
        return questStepsText.toString();
    }

    private String generateDialog(Quest quest, List<Action> questSteps) {
        StringBuilder questDialog = new StringBuilder();
        StoryFragment storyFragment = quest.storyFragment;
        // Explain the Quest Motivation and what the quest is about.
        questDialog.append("Dear Adventurer, I am on a quest for ").append(storyFragment.getMotivation()).append(".").append("\n").append("\n");
        questDialog.append("I need you to help me ").append(storyFragment.getDescription()).append(".").append("\n").append("\n");
        // Explain What actions need to be done for this specific storyfragment.
        // Note to self: look at the first depth of the actions tree, those actions should relate to the action in the story fragment. Actions in other depths are subquests.
        questDialog.append("In order to do this you will have to do the following: ");
        quest.questText = questDialog.toString();
        questDialog.append("\n").append("\n");
        int step = 1;
        for (Action action : quest.root.subActions) {
            questDialog.append(step).append(". ").append(action.actionDescription).append("\n").append("\n");
            step++;
        }
        questDialog.append("Complete these actions and I shall reward you greatly!");
        return questDialog.toString();
    }

    private void questDialogMapper(Quest quest, List<Action> questSteps) {
        // TODO: get all the npcs, items, locations and enemies from the quest steps & Map them to the quest dialog
        // Use some form of String formatting function to replace $npc, $location, $enemy, $item
        Log.d("$$", "Testing Dialog Mapper");
        Log.d("$$", "=============================");
       for (String action : quest.storyFragment.getActions()){
           Log.d("$$", "Action: " + action);
       }
        Log.d("$$", "Testing Quest.root.subActions");
        Log.d("$$", "=============================");
        for (Action action : quest.root.subActions) {
            Log.d("$$", "Action: " + action.actionName);
        }
    }
}
