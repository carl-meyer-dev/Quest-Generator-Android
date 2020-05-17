package com.carlmeyer.questgeneratordemo.questgenerator.models;

public class Quest {

    public String name;
    public String description;
    public String motivation;

    public Action root;

    public Quest(Action root){
        this.root = root;

        this.name = "Cool Quest";
        this.description = "All quests have the same description";
    }

    public int getDepth(){
        int depth = 0;
        depth = chilDepth(this.root, depth);
        return depth;
    }

    public int chilDepth(Action root, int depth){
        depth++;
        for (Action child : root.subActions) {
            int subDepth = chilDepth(child, depth);
            if(subDepth > depth){
                depth = subDepth;
            }
        }
        return depth;
    }

}
