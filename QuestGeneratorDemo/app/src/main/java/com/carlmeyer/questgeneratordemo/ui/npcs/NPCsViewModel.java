package com.carlmeyer.questgeneratordemo.ui.npcs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NPCsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NPCsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Non Player Characters");
    }

    public LiveData<String> getText() {
        return mText;
    }
}