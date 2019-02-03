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
public class FamilyFragment extends Fragment {


    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {

                releaseMediaPlayer();
            }
        }
    };

    public FamilyFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("father","vadeel", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("mother","aii", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("brother","bhau", R.drawable.family_older_brother, R.raw.family_brother));
        words.add(new Word("sister","baheen", R.drawable.family_older_sister, R.raw.family_sister));
        words.add(new Word("granmother","aaji", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("grandfather","aajoba", R.drawable.family_grandfather, R.raw.family_grandfather));
        words.add(new Word("grandson","naatu", R.drawable.family_younger_brother, R.raw.family_grandson));
        words.add(new Word("granddaughter","naat", R.drawable.family_younger_sister, R.raw.family_granddaughter));


        WordAdapter itemsAdapter= new WordAdapter(getActivity(), words, R.color.category_family);

        ListView listView = (ListView)rootView.findViewById(R.id.word_list);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                releaseMediaPlayer();


                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

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

    // release audio resource when activity is stopped

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {

        if (mMediaPlayer != null) {

            mMediaPlayer.release();


            mMediaPlayer = null;


            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}