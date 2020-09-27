package com.carlmeyer.questgeneratordemo.questgenerator.data;

import com.carlmeyer.questgeneratordemo.questgenerator.models.Motivation;

import java.util.ArrayList;
import java.util.List;

public class Motives {
    // Motivations

    public static final String KNOWLEDGE = "knowledge";
    public static final String COMFORT = "comfort";
    public static final String JUSTICE = "justice";

    public List<String> getMotives() {
        List<String> motives = new ArrayList<>();

        motives.add(KNOWLEDGE);
        motives.add(COMFORT);
        motives.add(JUSTICE);

        return motives;
    }


}
