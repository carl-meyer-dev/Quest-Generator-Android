package com.carlmeyer.questgeneratordemo.ui.enemies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EnemiesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EnemiesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Enemies");
    }

    public LiveData<String> getText() {
        return mText;
    }
}