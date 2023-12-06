package com.example.signbridge;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        mic.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                isButtonHeld = true;
                handler.postDelayed(startListeningRunnable, 500);
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                isButtonHeld = false;
                handler.removeCallbacks(startListeningRunnable);
                handleVoiceListening(false);
            }
            return true;
        });
    }

    private final Runnable startListeningRunnable = new Runnable() {
        @Override
        public void run() {
            handleVoiceListening(true);
        }
    };

    private void handleVoiceListening(boolean startListening) {
        if (startListening) {
            listeningText.setVisibility(View.VISIBLE);
            translate_text.setVisibility(View.INVISIBLE);
            cameraImg.setVisibility(View.INVISIBLE);
            startListening();
        } else {
            stopListening();
            listeningText.setVisibility(View.INVISIBLE);
            translate_text.setVisibility(View.VISIBLE);
            cameraImg.setVisibility(View.VISIBLE);
        }
    }

    // Initialize SpeechRecognizer only once
    private void initSpeechRecognizer() {
        if (speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext());
            speechRecognizer.setRecognitionListener(new RecognitionListener() {
                // Override methods for RecognitionListener
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
                        String recognizedText = matches.get(0);

                        // Set the recognized speech to your TextView or handle it as needed
                        result_text.setText(recognizedText);
                    }
                }

                @Override
                public void onPartialResults(Bundle partialResults) {}

                @Override
                public void onEvent(int eventType, Bundle params) {}
            });
        }
    }

    private void startListening() {
        initSpeechRecognizer();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Set the language to Urdu
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ur-RU"); // ur-RU is the locale for Roman Urdu

        speechRecognizer.startListening(intent);
    }
    


    // Method to stop listening
    private void stopListening() {
        if (speechRecognizer != null) {
            speechRecognizer.stopListening();
            speechRecognizer.cancel();
            // Do not destroy the SpeechRecognizer instance here if it's intended to be reused.
        }
    }



}