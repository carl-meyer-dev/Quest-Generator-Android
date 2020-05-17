package com.carlmeyer.questgeneratordemo.ui.npcs;

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

public class NPCsFragment extends Fragment {

    private NPCsViewModel NPCsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NPCsViewModel =
                ViewModelProviders.of(this).get(NPCsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_npcs, container, false);
        final TextView textView = root.findViewById(R.id.text_npcs);
        NPCsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
