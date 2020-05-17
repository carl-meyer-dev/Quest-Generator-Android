package com.carlmeyer.questgeneratordemo.ui.enemies;

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

public class EnemiesFragment extends Fragment {

    private EnemiesViewModel enemiesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        enemiesViewModel =
                ViewModelProviders.of(this).get(EnemiesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_enemies, container, false);
        final TextView textView = root.findViewById(R.id.text_enemies);
        enemiesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
