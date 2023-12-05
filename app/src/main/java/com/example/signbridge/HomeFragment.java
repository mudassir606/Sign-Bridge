package com.example.signbridge;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;


public class HomeFragment extends Fragment {

    ImageView mic;
    EditText translate_text;
    TextView listeningText,result_text;
    ImageView cameraImg;

     SpeechRecognizer speechRecognizer;
     Handler handler = new Handler();
     boolean isButtonHeld = false;

    public HomeFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mic = rootView.findViewById(R.id.mic_img);
        translate_text = rootView.findViewById(R.id.edit_translate_id);
        cameraImg = rootView.findViewById(R.id.imageView);
        listeningText = rootView.findViewById(R.id.listening_id);
        result_text = rootView.findViewById(R.id.text_value_id);


        performMicOperation();



        return rootView;
    }


    @SuppressLint("ClickableViewAccessibility")
    private void performMicOperation() {
        mic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Button is pressed
                    isButtonHeld = true;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isButtonHeld) {
                                // Perform actions when button is held
                                handleVoiceListening(true);
                            }
                        }
                    }, 500); // Change the delay according to your preference
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    // Button is released or touch is canceled
                    isButtonHeld = false;
                    handler.removeCallbacksAndMessages(null); // Remove any pending callbacks
                    handleVoiceListening(false);
                }
                return true;
            }
        });
    }

    private void handleVoiceListening(boolean startListening) {
        if (startListening) {
            listeningText.setVisibility(View.VISIBLE);
            translate_text.setVisibility(View.INVISIBLE);
            cameraImg.setVisibility(View.INVISIBLE);
            startListening();
        } else {
            if (speechRecognizer != null) {
                speechRecognizer.stopListening();
            }
        }
    }

    // Method to start listening for speech input
    private void startListening() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext());
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {}

            @Override
            public void onBeginningOfSpeech() {}

            @Override
            public void onRmsChanged(float rmsdB) {}

            @Override
            public void onBufferReceived(byte[] buffer) {}

            @Override
            public void onEndOfSpeech() {}

            @Override
            public void onError(int error) {}

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && matches.size() > 0) {
                    // Set the recognized speech to your TextView
                    String spokenText = matches.get(0);
                    result_text.setText(spokenText);
                }
                stopListening();
            }

            @Override
            public void onPartialResults(Bundle partialResults) {}

            @Override
            public void onEvent(int eventType, Bundle params) {}
        });

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizer.startListening(intent);
    }

    // Method to stop listening
    private void stopListening() {
        if (speechRecognizer != null) {
            speechRecognizer.stopListening();
            speechRecognizer.destroy();
        }
    }




}