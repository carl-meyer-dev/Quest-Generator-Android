package com.example.questgenerator.generator;

import com.example.questgenerator.models.Action;
import com.example.questgenerator.models.Quest;

import java.util.List;

public class QuestReader {

    private String questMotivationText;
    private String questDescriptionText;
    private List<Action> questSteps;
    private String questStepsText;
    private int stepCount = 0;
    private String indent = "   ";

    public void ReadQuest(Quest quest){
        questMotivationText = "Motivation: " + quest.motivation;
        questDescriptionText = "Description: " + quest.description;

        ReadSubActions(quest.root, 0);

    }

    private void ReadSubActions(Action action, int depth) {

        questStepsText = "\n";

        for (int i = 0; i < depth; i++) {
            questStepsText += indent;
        }

        if(action.subActions.size() == 0){
            questSteps.add(action);
            questStepsText += action.actionText;
            stepCount++;
        }else{
            questSteps.add(action);
            questStepsText += action.actionText;
            for (Action a : action.subActions){
                ReadSubActions(a, depth + 1);
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

    public String getQuestStepsText() {
        return questStepsText;
    }
}
