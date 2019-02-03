package com.benoi.marathiforbeginners.marathiforbegginers;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhrasesFragment extends Fragment {


    private MediaPlayer mMediaPlayer;


    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {

                mMediaPlayer.start();
            }else if(focusChange == AudioManager.AUDIOFOCUS_LOSS) {

                releaseMediaPlayer();
            }
        }
    };

    public PhrasesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);


        mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Where are you going?", "tu koethes jaat aahes?", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "Tuzha naav kai aahe?", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "Mazha naav....", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "Tulaka sevatath aahe?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.", "Mala Changle vatath aahe", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "tu yendh aahes ka?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.", "ho, emiyet aahe.", R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming.", "miyet aahe", R.raw.phrase_im_coming));
        words.add(new Word("Let's GO", "chal jauwya", R.raw.phrase_lets_go));
        words.add(new Word("Come Here", "ikdeye", R.raw.phrase_come_here));


        WordAdapter itemsAdapter= new WordAdapter(getActivity(), words,  R.color.category_phrases);


        ListView listView = (ListView)rootView.findViewById(R.id.word_list);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mMediaPlayer = MediaPlayer.create(getActivity(), words.get(position).getAudioResourceId());
                    mMediaPlayer.start();


                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {

                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });

        return rootView;
    }



    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }


    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {

            mMediaPlayer.release();


            mMediaPlayer = null;


            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}