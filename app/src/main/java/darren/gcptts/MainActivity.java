package darren.gcptts;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Locale;

import darren.gcptts.tts.TextToSpeechManger;
import darren.gcptts.tts.android.AndroidTTSAdapter;
import darren.gcptts.tts.android.AndroidVoice;
import darren.gcptts.tts.gcp.AudioConfig;
import darren.gcptts.tts.gcp.EAudioEncoding;
import darren.gcptts.tts.gcp.GCPTTSAdapter;
import darren.gcptts.tts.gcp.ESSMLlVoiceGender;
import darren.gcptts.tts.gcp.GCPVoice;
import darren.gcptts.tts.gcp.VoiceCollection;
import darren.gcptts.tts.gcp.VoiceList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getName();

    private TextToSpeechManger mTextToSpeechManger;

    private EditText mEditText;
    private Spinner mSpinnerLanguage;
    private Spinner mSpinnerStyle;
    private TextView mTextViewGender;
    private TextView mTextViewSampleRate;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeakRate;
    private Button mButtonSpeak;
    private Button mButtonSpeakPause;

    private int mPitch;
    private int mSpeakRate;
    private String mPause;
    private String mResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildViews();
        loading();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mTextToSpeechManger != null) {
            if (mButtonSpeakPause.getText().toString().compareTo(mPause) == 0) {
                mTextToSpeechManger.resume();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mTextToSpeechManger != null) {
            mTextToSpeechManger.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mTextToSpeechManger != null) {
            mTextToSpeechManger.exit();
            mTextToSpeechManger = null;
        }
    }

    private void buildViews() {
        mEditText = findViewById(R.id.ettIdText);
        mSpinnerLanguage = findViewById(R.id.snrIdLanguage);
        mSpinnerStyle = findViewById(R.id.snrIdStyle);
        mTextViewGender = findViewById(R.id.tvwIdGender);
        mTextViewSampleRate = findViewById(R.id.tvwIdSampleRate);
        mSeekBarPitch = findViewById(R.id.sbrIdPitch);
        mSeekBarSpeakRate = findViewById(R.id.sbrIdSpeakRate);
        mButtonSpeak = findViewById(R.id.btnIdSpeak);
        mButtonSpeakPause = findViewById(R.id.btnIdPauseAndResume);
        mPitch = 2000;
        mSpeakRate = 75;
        mPause = getResources().getString(R.string.btnPtSpeechPause);
        mResume = getResources().getString(R.string.btnPtSpeechResume);

        mSeekBarPitch.setMax(4000);
        mSeekBarPitch.setProgress(mPitch);
        mSeekBarPitch.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mPitch = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mSeekBarSpeakRate.setMax(375);
        mSeekBarSpeakRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSpeakRate = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mButtonSpeak.setOnClickListener(this);
        mButtonSpeakPause.setOnClickListener(this);
    }

    private void loading() {
        final Context selfContext = this;
        VoiceList voiceList = new VoiceList();
        voiceList.addVoiceListener(new VoiceList.IVoiceListener() {
            @Override
            public void onResponse(String text) {
                JsonElement jsonElement = new JsonParser().parse(text);
                if (jsonElement == null || jsonElement.getAsJsonObject() == null ||
                        jsonElement.getAsJsonObject().get("voices").getAsJsonArray() == null) {
                    Log.e(TAG, "get error json");
                    return;
                }

                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonArray jsonArray = jsonObject.get("voices").getAsJsonArray();
                final VoiceCollection voiceCollection = new VoiceCollection();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonArray jsonArrayLanguage = jsonArray.get(i)
                            .getAsJsonObject().get("languageCodes")
                            .getAsJsonArray();

                    if (jsonArrayLanguage.get(0) != null) {
                        String language = jsonArrayLanguage.get(0).toString().replace("\"", "");
                        String name = jsonArray.get(i).getAsJsonObject().get("name").toString().replace("\"", "");
                        String ssmlGender = jsonArray.get(i).getAsJsonObject().get("ssmlGender").toString().replace("\"", "");
                        ESSMLlVoiceGender essmLlVoiceGender = ESSMLlVoiceGender.convert(ssmlGender);
                        int naturalSampleRateHertz = jsonArray.get(i).getAsJsonObject().get("naturalSampleRateHertz").getAsInt();

                        GCPVoice gcpVoice = new GCPVoice(language, name, essmLlVoiceGender, naturalSampleRateHertz);
                        voiceCollection.add(language, gcpVoice);
                    }
                }

                final ArrayAdapter<String> adapterLanguage;
                adapterLanguage = new ArrayAdapter<String>(selfContext,
                        android.R.layout.simple_spinner_item,
                        voiceCollection.getLanguage());
                adapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerLanguage.post(new Runnable() {
                    @Override
                    public void run() {
                        mSpinnerLanguage.setAdapter(adapterLanguage);
                    }
                });
                mSpinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        final ArrayAdapter<String> adapterStyle;
                        adapterStyle = new ArrayAdapter<String>(selfContext,
                                android.R.layout.simple_spinner_item,
                                voiceCollection.getNames(mSpinnerLanguage.getSelectedItem().toString()));
                        adapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSpinnerStyle.post(new Runnable() {
                            @Override
                            public void run() {
                                mSpinnerStyle.setAdapter(adapterStyle);
                            }
                        });
                        mSpinnerStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                GCPVoice gcpVoice = voiceCollection.getGCPVoice(mSpinnerLanguage.getSelectedItem().toString(),
                                        mSpinnerStyle.getSelectedItem().toString());
                                if (gcpVoice != null) {
                                    mTextViewGender.setText(gcpVoice.getESSMLlGender().toString());
                                    mTextViewSampleRate.setText(String.valueOf(gcpVoice.getNaturalSampleRateHertz()));
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                Log.e(TAG, "Loading Voice List Error, error code : " + error);
            }
        });
        voiceList.start();
    }

    private void androidTTS() {
        AndroidVoice androidVoice = new AndroidVoice.Builder()
                .addLanguage(Locale.ENGLISH)
                .addPitch(1.5f)
                .addSpeakingRate(1.0f)
                .build();

        AndroidTTSAdapter androidTTSAdapter = new AndroidTTSAdapter(this, androidVoice);
        mTextToSpeechManger = new TextToSpeechManger(androidTTSAdapter);
        mTextToSpeechManger.speak(mEditText.getText().toString());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnIdSpeak: {
                if (mTextToSpeechManger != null) {
                    mTextToSpeechManger.stop();
                }

                if (mSpinnerLanguage.getSelectedItem() == null ||
                        mSpinnerStyle.getSelectedItem() == null) {
                    Toast.makeText(this,
                            "Loading Voice Error, please check network or API_KEY.",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                String languageCode = mSpinnerLanguage.getSelectedItem().toString();
                String name = mSpinnerStyle.getSelectedItem().toString();
                float pitch = ((float) (mPitch - 2000) / 100);
                float speakRate = ((float) (mSpeakRate + 25) / 100);

                GCPVoice GCPVoice = new GCPVoice(languageCode, name);
                AudioConfig audioConfig = new AudioConfig.Builder()
                        .addAudioEncoding(EAudioEncoding.MP3)
                        .addSpeakingRate(speakRate)
                        .addPitch(pitch)
                        .build();
                mTextToSpeechManger = new TextToSpeechManger(new GCPTTSAdapter(GCPVoice, audioConfig));
                mTextToSpeechManger.speak(mEditText.getText().toString());
                mButtonSpeakPause.setText(mPause);
                break;
            }

            case R.id.btnIdPauseAndResume: {
                if (mTextToSpeechManger != null) {
                    Button button = findViewById(R.id.btnIdPauseAndResume);
                    if (button.getText().toString().compareTo(mPause) == 0) {
                        mTextToSpeechManger.pause();
                        button.setText(mResume);
                    } else {
                        mTextToSpeechManger.resume();
                        button.setText(mPause);
                    }
                }
                break;
            }
        }
    }

}
