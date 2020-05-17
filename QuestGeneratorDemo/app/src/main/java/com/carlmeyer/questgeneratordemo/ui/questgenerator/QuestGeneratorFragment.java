package com.carlmeyer.questgeneratordemo.ui.questgenerator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.carlmeyer.questgeneratordemo.R;

public class QuestGeneratorFragment extends Fragment {

    private QuestGeneratorViewModel questGeneratorViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        questGeneratorViewModel =
                ViewModelProviders.of(this).get(QuestGeneratorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_quest_generator, container, false);
        final TextView textView = root.findViewById(R.id.text_quest_generator);
        questGeneratorViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
