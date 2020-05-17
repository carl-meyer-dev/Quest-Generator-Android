package com.carlmeyer.questgeneratordemo.ui.questgenerator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QuestGeneratorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public QuestGeneratorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Quest Generator");
    }

    public LiveData<String> getText() {
        return mText;
    }
}