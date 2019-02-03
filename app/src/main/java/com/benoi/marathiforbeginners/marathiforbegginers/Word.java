package com.benoi.marathiforbeginners.marathiforbegginers;

import android.media.Image;

public class Word {


    private String mDefaultTranslation;


    private String mMarathiTranslation;


    private int mImageResourceId = NO_IMAGE_PROVIDED;


    private static final int NO_IMAGE_PROVIDED = -1;


    private int mAudioResourceId;


    public Word(String defaultTranslation, String marathiTranslation,int audioResourceId){
        mDefaultTranslation = defaultTranslation;
        mMarathiTranslation = marathiTranslation;
        mAudioResourceId = audioResourceId;

    }


    public Word(String defaultTranslation, String marathiTranslation,int imageResourceId, int audioResourceId){
        mDefaultTranslation = defaultTranslation;
        mMarathiTranslation = marathiTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }


    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }


    public String getMarathiTranslation() {
        return mMarathiTranslation;
    }


    public int getImageResourceId() {
        return mImageResourceId;
    }


    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }


    public int getAudioResourceId() {
        return mAudioResourceId;
    }
}
