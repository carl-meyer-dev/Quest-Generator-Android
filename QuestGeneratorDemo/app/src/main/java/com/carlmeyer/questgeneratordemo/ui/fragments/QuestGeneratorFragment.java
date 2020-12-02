package com.carlmeyer.questgeneratordemo.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carlmeyer.questgeneratordemo.R;
import com.carlmeyer.questgeneratordemo.questgenerator.data.Motives;
import com.carlmeyer.questgeneratordemo.questgenerator.generator.QuestGenerator;
import com.carlmeyer.questgeneratordemo.questgenerator.generator.QuestReader;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Action;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Motivation;
import com.carlmeyer.questgeneratordemo.questgenerator.models.Quest;
import com.carlmeyer.questgeneratordemo.questgenerator.models.StoryFragment;
import com.carlmeyer.questgeneratordemo.ui.adapters.ActionsAdapter;
import com.carlmeyer.questgeneratordemo.ui.viewholders.ActionViewHolder;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

import io.realm.Realm;
import io.realm.RealmResults;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class QuestGeneratorFragment extends Fragment implements ActionViewHolder.OnActionListener {

    Realm realm;

    private String[] motivations = new String[]{Motives.KNOWLEDGE, Motives.COMFORT, Motives.JUSTICE};
    private List<Motivation> motives;

    private TextView tvQuestText;
    private EditText txtChosenStoryFragment;

    private RecyclerView rvActions;
    private ActionsAdapter actionsAdapter;
    private List<Action> questSteps;
    private Quest quest;
    private KonfettiView konfettiView;

    private List<String> storyFragments = new ArrayList<>();

    private Stack<List<Action>> questStack = new Stack<>();

    private ProgressBar pbLevel;
    private TextView tvLevel;
    private TextView tvExperience;
    private TextView tvGold;

    private int playerLevel;
    private int playerExperience;
    private int playerGold;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        realm = Realm.getDefaultInstance();

        View root = inflater.inflate(R.layout.fragment_quest_generator, container, false);

        tvQuestText = root.findViewById(R.id.tvQuestText);
        txtChosenStoryFragment = root.findViewById(R.id.txtSelectStoryFragment);

        Button btnGenerateQuest = root.findViewById(R.id.btnQuestGenerator);

        rvActions = root.findViewById(R.id.rvActions);

        konfettiView = root.findViewById(R.id.viewKonfetti);

        pbLevel = root.findViewById(R.id.pbLevel);
        tvLevel = root.findViewById(R.id.tvLevel);
        tvExperience = root.findViewById(R.id.tvExperience);
        tvGold = root.findViewById(R.id.tvGold);

        getLootLevelAndExperience();

        motives = realm.where(Motivation.class).findAll();

        setStoryFragments();


        btnGenerateQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQuest();
            }
        });

        txtChosenStoryFragment.setOnFocusChangeListener((v1, hasFocus) -> {
            // when location edit text is clicked and gains focus display a choice dialog of locations
            if (hasFocus) {
                new LovelyChoiceDialog(getContext())
                        .setTopColorRes(R.color.colorPrimary)
                        .setTitle(R.string.story_fragment)
                        .setIcon(R.drawable.puzzle_light)
                        .setMessage(R.string.choose_a_storyfragment)
                        .setItems(storyFragments, (position, motivation) -> {
                            // when a location is selected, set the location txt of the enemy and dismiss
                            txtChosenStoryFragment.setText(motivation);
                            // clear focus so that you can click on it again once dialog closes
                            txtChosenStoryFragment.clearFocus();
                        })
                        .show();
            }

        });

        return root;
    }

    private void getLootLevelAndExperience(){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
         playerLevel = sharedPref.getInt(getString(R.string.player_level), 1);
         playerExperience = sharedPref.getInt(getString(R.string.player_experience), 0);
         playerGold = sharedPref.getInt(getString(R.string.player_gold), 0);

        pbLevel.setMax(playerLevel*10);
        pbLevel.setProgress(playerExperience, true);
        tvLevel.setText("Level "+ playerLevel);
        tvExperience.setText(playerExperience + " /" + 10*playerLevel + " XP");
        tvGold.setText(playerGold + " gold");
    }

    private void setStoryFragments() {

        storyFragments.clear();
        storyFragments.add("random");
        RealmResults<StoryFragment> storyFragmentsFromDB = realm.where(StoryFragment.class).findAll();

        for (StoryFragment sf : storyFragmentsFromDB) {
            Log.d("^^", sf.getDescription());
            storyFragments.add(sf.getDescription());
        }
    }

    @SuppressLint("SetTextI18n")
    private void generateQuest() {
        QuestGenerator questGenerator = QuestGenerator.getInstance();
        if (txtChosenStoryFragment.getText().toString().equals("random")) {
            Random random = new Random();


            quest = null;

            // get a random quest motivation

        /*
        We need to filter out any motivations that do not have any story fragments.
        This might happen when the user has just created a new motivation there are no
        story fragments for that motivation yet.
        */
            List<Motivation> motivesWithStoryFragments = motives
                    .stream()
                    .filter(motivation -> motivation.getStoryFragments().size() > 0)
                    .collect(Collectors.toList());

            Motivation questMotivation;

            if (motivesWithStoryFragments.size() == 1) {
                questMotivation = motivesWithStoryFragments.get(0);
            } else {
                questMotivation = motivesWithStoryFragments.get(random.nextInt(motives.size() - 1));
            }

            Log.d("Quest Motivation", "The generated quest motivation is " + questMotivation);

            int minimumComplexity = 1;

            quest = questGenerator.getQuest(questMotivation, minimumComplexity);
        } else {

            realm.executeTransaction(r -> {

                StoryFragment storyFragment = r.where(StoryFragment.class)
                        .equalTo("description", txtChosenStoryFragment.getText().toString())
                        .findFirst();

                if (storyFragment != null) {
                    quest = questGenerator.getQuestFromStoryFragment(storyFragment);
                } else {
                    new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                            .setTopColorRes(R.color.colorPrimary)
                            .setButtonsColorRes(R.color.colorAccent)
                            .setIcon(R.drawable.alert_box_light)
                            .setTitle(R.string.could_not_find_story_fragment_title)
                            .setMessage(R.string.could_not_find_storyfragment)
                            .setPositiveButton(R.string.ok, v2 -> {
                            })
                            .show();
                }
            });

        }


        QuestReader questReader = new QuestReader();
        // Read the quest using the Quest Reader
        if (quest != null) {
            questReader.readQuest(quest);
            String questDescriptionText = questReader.getQuestDescriptionText();
            String questMotivationText = questReader.getQuestMotivationText();
            String questStepsText = questReader.getQuestStepsToString();
            questSteps = quest.root.subActions;

            setUpRecyclerView();

            showQuestDialog(quest);

        }
    }

    /**
     * Set up RecyclerView
     */
    private void setUpRecyclerView() {
        // Create and set layoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvActions.setLayoutManager(layoutManager);
        // Initialize and set locationsAdapter with list of locations
        actionsAdapter = new ActionsAdapter(questSteps, this);
        rvActions.setAdapter(actionsAdapter);
    }

    private void showQuestDialog(Quest quest) {

        if (quest.storyFragment.dialogKeys.size() > 0) {
            mapQuestDialog(quest);
        }

        new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.colorAccent)
                .setIcon(R.drawable.script_text_light)
                .setTitle(R.string.quest)
                .setMessage(quest.dialog)
                .setPositiveButton(R.string.accept, v2 -> {
                })
                .show();
    }

    private void mapQuestDialog(Quest quest) {
        int index = 0;

        Log.d("&&", "Quest Dialog");
        Log.d("&&", quest.dialog);
        Log.d("&&", "===========================");
        Log.d("&&", "Dialog Keys");

        for (String dialogKey : quest.storyFragment.dialogKeys) {
            Log.d("&&", dialogKey);
        }
        Log.d("&&", "===========================");
        Log.d("&&", "Action Subjects");
        for (Action action : questSteps) {
            if (quest.storyFragment.dialogKeys.get(index) != null) {
                Log.d("&&", "Dialog Key: " + quest.storyFragment.dialogKeys.get(index) + " | Action Subject: " + action.actionSubject);
                quest.dialog = quest.dialog.replace(quest.storyFragment.dialogKeys.get(index), action.actionSubject);
                quest.completedDialog = quest.completedDialog.replace(quest.storyFragment.dialogKeys.get(index), action.actionSubject);

            }
            index++;
        }

        tvQuestText.setText(quest.dialog);

    }

    @Override
    public void onActionClick(int position) {

        Action selectedAction = actionsAdapter.getItem(position);
        Log.d("POSITION", position + "");

        new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.colorAccent)
                .setIcon(R.drawable.human_greeting_light)
                .setTitle(selectedAction.actionDescription + "?")
                .setMessage(selectedAction.actionDialog)
                .setPositiveButton(R.string.yes, v2 -> {
                    if (position == 0) {
                        // can perform action
                        performAction(selectedAction);
                    } else {
                        // needs to perform previous action
                        performPreviousActionFirst(position - 1, selectedAction);
                    }
                })
                .setNegativeButton(R.string.no, v2 -> {
                })
                .show();
    }

    /**
     * Perform the action that was selected.
     * If no subactions simple complete the action and remove it from the list
     * else if it has sub actions start subQuest
     *
     * @param action - selected action to perform
     */
    private void performAction(Action action) {
        completeAction(action);
//        if (action.subActions.isEmpty()) {
//            completeAction(action);
//        } else {
//            startSubQuest(action);
//        }
    }

    private void completeAction(Action action) {
        new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.colorAccent)
                .setIcon(R.drawable.check_bold)
                .setMessage("You " + action.actionDescription)
                .setPositiveButton(R.string.ok, v2 -> {
                    questSteps.remove(0);
                    actionsAdapter.notifyItemRemoved(0);
                    if (questSteps.size() == 0) {
                        completeQuest();
                    }
                })
                .show();
    }


    /**
     * Start the subquest that needs to be completed to perform root action
     *
     * @param root
     */
    private void startSubQuest(Action root) {
        StringBuilder subQuestText = new StringBuilder();
        subQuestText
                .append("Before you can ")
                .append(root.actionDescription)
                .append(" you need to do the following: ").append("\n").append("\n");

        int step = 1;
        for (Action action : root.subActions) {
            subQuestText
                    .append(step).append(". ")
                    .append(action.actionDescription)
                    .append("\n")
                    .append("\n");
            step++;
        }

        new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.colorAccent)
                .setIcon(R.drawable.alert_box_light)
                .setMessage(subQuestText.toString())
                .setPositiveButton(R.string.ok, v2 -> {
                    addSubquest(root);
                })
                .show();
    }

    private void addSubquest(Action subQuest) {

        Collections.reverse(subQuest.subActions);

        for (Action action : subQuest.subActions) {
            questSteps.add(0, action);
        }

        subQuest.subActions.clear();


        actionsAdapter.notifyDataSetChanged();
    }

    private void performPreviousActionFirst(int parentPosition, Action action) {
        Action parentAction = actionsAdapter.getItem(parentPosition);
        Log.d("POSITION", parentPosition + "");

        StringBuilder performPreviousText = new StringBuilder();

        performPreviousText
                .append("Before you can ")
                .append(action.actionDescription)
                .append(" you first need to ")
                .append(parentAction.actionDescription);

        new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.colorAccent)
                .setIcon(R.drawable.alert_box_light)
                .setMessage(performPreviousText.toString())
                .setPositiveButton(R.string.ok, v2 -> {

                })
                .show();
    }

    private void completeQuest() {


        new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.colorAccent)
                .setIcon(R.drawable.check_bold)
                .setMessage(quest.completedDialog)
                .setPositiveButton(R.string.ok, v2 -> {
                    tvQuestText.setText("");
                    throwConfetti();
                    generateLoot();
                })
                .show();
    }

    private void generateLoot(){

        boolean levelUp = false;

        Random random = new Random();
        int randomXP = random.nextInt(10*playerLevel);
        int randomGold = random.nextInt(10*playerLevel);

        playerExperience += randomXP;
        playerGold += randomGold;

        if(playerExperience >= 10*playerLevel){
            levelUp = true;
            playerExperience = playerExperience - (10*playerLevel);
            playerLevel++;
        }


        StringBuilder rewardText = new StringBuilder();

        if(levelUp){
            rewardText.append("Level Up!").append("\n").append("\n");
        }
        rewardText.append("You receive the following:").append("\n").append("\n");
        rewardText.append("Experience: ").append(randomXP).append(" XP").append("\n").append("\n");
        rewardText.append("Gold: ").append(randomGold).append("\n").append("\n");

        new LovelyStandardDialog(getContext(), LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.colorAccent)
                .setTitle("Reward")
                .setIcon(R.drawable.treasure_chest_light)
                .setMessage(rewardText)
                .setPositiveButton(R.string.ok, v2 -> {
                    updateLootLevelExperience();
                })
                .show();


    }

    private void updateLootLevelExperience(){
        pbLevel.setProgress(playerExperience, true);
        tvLevel.setText("Level " + playerLevel);
        tvExperience.setText(playerExperience + " /" + (10*playerLevel) +" XP");
        tvGold.setText(playerGold + " gold");

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.player_level), playerLevel);
        editor.putInt(getString(R.string.player_experience), playerExperience);
        editor.putInt(getString(R.string.player_gold), playerGold);
        editor.apply();
    }

    private void throwConfetti() {
        konfettiView.build()
                .addColors(
                        getContext().getColor(R.color.colorPrimary),
                        getContext().getColor(R.color.colorPrimaryDark),
                        getContext().getColor(R.color.colorAccent)
                )
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 25f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                .addSizes(new Size(10, 1f))
                .setPosition(
                        konfettiView.getWidth() - konfettiView.getWidth() / 2f,
                        konfettiView.getHeight() - konfettiView.getHeight() / 1.5f
                )
                .burst(1000);
    }

    @Override
    public void onResume() {
        txtChosenStoryFragment.setText("random");
        super.onResume();
    }
}
