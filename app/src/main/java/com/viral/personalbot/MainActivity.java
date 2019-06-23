package com.viral.personalbot;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.security.Policy;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends Activity {

    TextToSpeech t;

    private TextView txtSpeechInput;
    private ImageView btnSpeak;

    private Policy.Parameters params;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status!=TextToSpeech.ERROR)
                {
                    t.setSpeechRate(-2);
                    t.setLanguage(Locale.ENGLISH);
                }
            }
        });

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageView) findViewById(R.id.btnSpeak);
        // hide the action bar
       // getActionBar().hide();
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }
    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    //ask how are you?
                    if (result.get(0).equals("hello") || result.get(0).equals("hi"))
                    {
                        txtSpeechInput.setText("hi .how are you ?");
                        t.speak("hi how are you?",TextToSpeech.QUEUE_FLUSH,null);
                    }

                    //open browser
                    if(result.get(0).indexOf("open")!= -1)
                    {
                        if (result.get(0).indexOf("browser")!= -1)
                        {
                            t.speak("opening browser",TextToSpeech.QUEUE_FLUSH,null);
                            Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"));
                            startActivity(i);
                        }
                    }

                    //time
                    if(result.get(0).indexOf("what")!= -1)
                    {
                        if (result.get(0).indexOf("time")!= -1)
                        {
                            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-mm-dd 'at' HH:mmSS z");
                            Date date=new Date(System.currentTimeMillis());

                            t.speak("Time is"+formatter.format(date),TextToSpeech.QUEUE_FLUSH,null);
                            txtSpeechInput.setText("Time is"+formatter.format(date));

                        }
                    }
                    //how are you => i am fine
                    if(result.get(0).indexOf("how are ")!= -1)
                    {
                        if (result.get(0).indexOf("you")!= -1)
                        {
                            t.speak("I am fine.Thanks",TextToSpeech.QUEUE_FLUSH,null);
                            txtSpeechInput.setText("I am fine.Thanks");
                        }
                    }
                    //who are you , what is your name => i am viral
                    if((result.get(0).indexOf("who are")!= -1) || result.get(0).indexOf("what is your ")!= -1)
                    {
                        if ((result.get(0).indexOf("you")!= -1) || result.get(0).indexOf("name")!= -1)
                        {
                            t.speak("I am VIRAL",TextToSpeech.QUEUE_FLUSH,null);
                            txtSpeechInput.setText("I am VIRAL.");
                        }
                    }
                    //Thanks => Your welcome
                    if((result.get(0).indexOf("thanks")!= -1) || (result.get(0).indexOf("thank you")!=-1))
                    {
                        t.speak("Your welcome.",TextToSpeech.QUEUE_FLUSH,null);
                        txtSpeechInput.setText("Your welcome");
                    }

                    //Good morning  => gm
                    if(result.get(0).indexOf("good morning")!= -1)
                    {
                        t.speak("Good morning.Have a great day.",TextToSpeech.QUEUE_FLUSH,null);
                        txtSpeechInput.setText("Good morning.Have a great day.");
                    }

                    //Good night  => good night
                    if(result.get(0).indexOf("good night")!= -1)
                    {
                        t.speak("Good night.sweet dreams bye",TextToSpeech.QUEUE_FLUSH,null);
                        txtSpeechInput.setText("Good night.sweet dreams bye");
                    }

                    //what are you doing?
                    if(result.get(0).indexOf("what are you")!= -1)
                    {
                        if (result.get(0).indexOf("doing")!= -1)
                        {
                            t.speak("i am learning how to add more features.",TextToSpeech.QUEUE_FLUSH,null);
                            txtSpeechInput.setText("i am learning how to add more features.");
                        }
                    }

                    //open => youtube
                    if(result.get(0).indexOf("open")!= -1) {

                        if(result.get(0).indexOf("YouTube")!= -1) {
                            t.speak("opening youtube",TextToSpeech.QUEUE_FLUSH,null);
                            String url = "https://www.youtube.com";
                            Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(i);

                        }
                    }

                    //open => whatsapp
                    if(result.get(0).indexOf("open")!= -1) {

                        if(result.get(0).indexOf("WhatsApp")!= -1) {
                            t.speak("opening whatsapp",TextToSpeech.QUEUE_FLUSH,null);
                            String url = "https://api.whatsapp.com/send?phone="+"+917485994073";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    }


                    //Play => Music
                    if(result.get(0).indexOf("play")!= -1) {

                        if(result.get(0).indexOf("music")!= -1) {

                            Context ctx=this; // or you can replace **'this'** with your **ActivityName.this**
                            try {
                                Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.google.android.music");
                                ctx.startActivity(i);
                            } catch (Exception e) {
                                Toast.makeText(ctx, "Error : "+e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }


                    //Turn On => GPS
                    if(result.get(0).indexOf("turn")!= -1) {

                        if(result.get(0).indexOf("GPS")!= -1) {

                            Context ctx=this;
                            try {
                                Intent intent=new Intent("android.location.GPS_ENABLED_CHANGE");
                                intent.putExtra("enabled", true);
                                sendBroadcast(intent);
                            } catch (Exception e) {
                                Toast.makeText(ctx, "Error : "+e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }
                break;
            }
        }
    }
}

/*
1.hi .how are you ?

2.good morning

3.good night

4.thanks

5.time

6.who are you

7.open browser

8.open whatsapp

9.play music


 */