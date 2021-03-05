package ch.zli.angehrns_drawing_tablet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    private TextView editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }

        editText = findViewById(R.id.TextView);
        ImageView micButton = findViewById(R.id.micButton);
        ImageView saveButton = findViewById(R.id.save);
        ImageView twitterButton = findViewById(R.id.twitter);



        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onBeginningOfSpeech() {
                editText.setText("Listening...");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                editText.setText(data.get(0));
                changeColor(data.get(0));
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        micButton.setOnClickListener(v -> speechRecognizer.startListening(speechRecognizerIntent));

        saveButton.setOnClickListener(v -> saveImage(findViewById(R.id.simpleDrawingView1)));

        twitterButton.setOnClickListener(v -> tweet(getApplicationContext().getFilesDir() + "/image.png"));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }

    private void checkPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }

    public void changeColor(String voiceInput) {
        voiceInput = voiceInput.toLowerCase();
        int color = SimpleDrawingView.drawPaint.getColor();

        SimpleDrawingView view = findViewById(R.id.simpleDrawingView1);
        boolean hasColor = false;

        //common false inputs will be validated to make user experience friendlier
        if (voiceInput.contains("red") || voiceInput.contains("rhett") || voiceInput.contains("rent") || voiceInput.contains("right") || voiceInput.contains("wet")) {
            color = Color.RED;
            hasColor = true;
        } else if (voiceInput.contains("blue") || voiceInput.contains("peru")) {
            color = Color.BLUE;
            hasColor = true;
        } else if (voiceInput.contains("green") || voiceInput.contains("kareem")) {
            color = Color.GREEN;
            hasColor = true;
        } else if (voiceInput.contains("yellow")) {
            color = Color.YELLOW;
            hasColor = true;
        } else if (voiceInput.contains("gray")) {
            color = Color.GRAY;
            hasColor = true;
        } else if (voiceInput.contains("cyan")) {
            color = Color.CYAN;
            hasColor = true;
        } else if (voiceInput.contains("magenta") || voiceInput.contains("purple")) {
            color = Color.MAGENTA;
            hasColor = true;
        } else if (voiceInput.contains("black")) {
            color = Color.BLACK;
            hasColor = true;
        } else if (voiceInput.contains("dark gray")) {
            color = Color.DKGRAY;
            hasColor = true;
        } else if (voiceInput.contains("light gray")) {
            color = Color.LTGRAY;
            hasColor = true;
        } else if (voiceInput.contains("save") || voiceInput.contains("safe") || voiceInput.contains("spades") || voiceInput.contains("fake") || voiceInput.contains("saint")) {
            saveImage(view);
        }
        if (hasColor && voiceInput.contains("background") || voiceInput.contains("back from") || voiceInput.contains("spectrum") || voiceInput.contains("back rub") || voiceInput.contains("next round") || voiceInput.contains("text round")) {
            view.getRootView().setBackgroundColor(color);
        } else {
            SimpleDrawingView.drawPaint.setColor(color);
        }
        if (voiceInput.contains("clear")) {
            view.clear();
        }
    }

    public void saveImage(SimpleDrawingView view){

        Bitmap bitmap = view.viewToBitmap(view);

        OutputStream os;
        try {
            File file = new File(getApplicationContext().getFilesDir(), "/image.png");
            os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            bitmap.recycle();
        } catch(IOException e) {
            bitmap.recycle();
            Log.e("combineImages", "problem combining images", e);
        }
    }

    public void tweet(String filepath){

        new Thread(() -> TwitterPost.tweet(filepath)).start();
    }

}
