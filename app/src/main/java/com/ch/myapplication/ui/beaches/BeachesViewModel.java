package com.ch.myapplication.ui.beaches;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BeachesViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public BeachesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is beaches fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}