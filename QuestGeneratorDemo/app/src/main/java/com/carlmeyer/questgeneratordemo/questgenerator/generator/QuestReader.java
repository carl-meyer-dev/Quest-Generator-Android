package com.carlmeyer.questgeneratordemo.questgenerator.generator;


import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Quest;

import java.util.ArrayList;
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

    }

    /**
     * Read and traverse any sub-actions or sub-quests of an action and append each action to a list
     * of quest steps that need to completed in order to complete the quest.
     *
     * note we are also building a string of quest steps while reading the quests in case we
     * need to output a string list of quest steps. This is related to the questStepsText variable.
     *
     * The ordered list of actions that need to be completed will be stored in the questSteps array
     * as we traverse and read the leaf nodes in pre-order traversal in the quest tree.
     *
     * for a visual representation you can look at this picture:
     * https://drive.google.com/file/d/1w0dPhB4T-9wWuYfmYFIxu36OJUeaFFsP/view?usp=sharing
     *
     * @param action - action to complete
     * @param depth  - depth of the tree
     */
    private void readSubActions(Action action, int depth) {

        /*

         */


        questStepsText.append("\n");

        for (int i = 0; i < depth; i++) {
            questStepsText.append(indent);
        }

        if (action.subActions.size() == 0) {
            questSteps.add(action);
            questStepsText.append(action.actionText);
            stepCount++;
        } else {
            questSteps.add(action);
            questStepsText.append(action.actionText);
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
     * @return
     */
    public String getQuestStepsToString() {
        StringBuilder steps = new StringBuilder();
        int count = 0;
        for (Action action : questSteps) {
            if (count == 0) {
                steps.append("Main Quest: ").append(action.actionText).append("\n").append("\n");
            } else {
                steps.append(count).append(".   ").append(action.actionText).append("\n");
            }
            count++;
        }
        return steps.toString();
    }

    public String getQuestStepsText() {
        return questStepsText.toString();
    }
}
