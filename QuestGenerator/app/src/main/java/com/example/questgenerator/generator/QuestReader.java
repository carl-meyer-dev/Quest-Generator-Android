package com.example.questgenerator.generator;

import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Quest;

import java.util.ArrayList;
import java.util.List;

public class QuestReader {

    private String questMotivationText;
    private String questDescriptionText;
    private List<Action> questSteps = new ArrayList<>();
    private StringBuilder questStepsText = new StringBuilder();
    private int stepCount = 0;
    private String indent = "   ";

    public void readQuest(Quest quest){
        questMotivationText = quest.motivation;
        questDescriptionText = quest.description;

        readSubActions(quest.root, 0);

    }

    private void readSubActions(Action action, int depth) {

        questStepsText.append("\n");

        for (int i = 0; i < depth; i++) {
            questStepsText.append(indent);
        }

        if(action.subActions.size() == 0){
            questSteps.add(action);
            questStepsText.append(action.actionText);
            stepCount++;
        }else{
            questSteps.add(action);
            questStepsText.append(action.actionText);
            for (Action a : action.subActions){
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

    public String getQuestStepsToString(){
        StringBuilder steps = new StringBuilder();
        int count = 0;
        for (Action action : questSteps){
            if(count == 0){
                steps.append("Main Quest: ").append(action.actionText).append("\n").append("\n");
            }else{
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
