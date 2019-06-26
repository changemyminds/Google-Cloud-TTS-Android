package darren.gcptts.presenter;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Locale;
import darren.gcptts.model.AndroidTTSAdapter;
import darren.gcptts.model.GCPTTSAdapter;
import darren.gcptts.model.SpeechManager;
import darren.gcptts.model.android.AndroidVoice;
import darren.gcptts.model.gcp.AudioConfig;
import darren.gcptts.model.gcp.EAudioEncoding;
import darren.gcptts.model.gcp.ESSMLlVoiceGender;
import darren.gcptts.model.gcp.GCPVoice;
import darren.gcptts.model.gcp.VoiceCollection;
import darren.gcptts.model.gcp.VoiceList;
import darren.gcptts.view.MainActivityView;

/**
 * Author: Changemyminds.
 * Date: 2019/6/23.
 * Description:
 * Reference:
 */
public class MainActivityPresenter implements VoiceList.IVoiceListener {
    private static final String TAG = "MainActivityPresenter";
    private static final int TEXT_TO_SPEECH_CODE = 0x100;

    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    private MainActivityView mView;

    private VoiceList mVoiceList;
    private VoiceCollection mVoiceCollection;

    private SpeechManager mSpeechManager;
    private GCPTTSAdapter mGCPTTSAdapter;

    public MainActivityPresenter(MainActivityView view) {
        this.mView = view;
        mVoiceList = new VoiceList();
        mVoiceList.addVoiceListener(this);

        mSpeechManager = new SpeechManager();

        // init GCPTTSAdapter and set default
        mGCPTTSAdapter = new GCPTTSAdapter();
        mSpeechManager.setSpeech(mGCPTTSAdapter);
    }

    public void exitApp() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            // exit app
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            mView.makeToast("Press back again to Exit", false);
        }
    }

    public void onTextToSpeechResult(Context context, int requestCode, int resultCode) {
        if (requestCode == TEXT_TO_SPEECH_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                AndroidTTSAdapter androidTTSAdapter = new AndroidTTSAdapter(context);
                AndroidVoice androidVoice = new AndroidVoice.Builder()
                        .addLanguage(Locale.ENGLISH)
                        .addPitch(1.0f)
                        .addSpeakingRate(1.0f)
                        .build();
                androidTTSAdapter.setAndroidVoice(androidVoice);

                // set the next handler
                SpeechManager androidTTSManager = new SpeechManager();
                androidTTSManager.setSpeech(androidTTSAdapter);
                mSpeechManager.setSupervisor(androidTTSManager);
            } else {
                mView.makeToast("You do not have the text to speech file you have to install", true);
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                mView.startActivity(installIntent);
            }
        }
    }

    public void initGCPTTSSettings() {
        mVoiceList.start();
    }

    public void initAndroidTTSSetting() {
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        mView.startActivityForResult(checkIntent, TEXT_TO_SPEECH_CODE);
    }

    private void initGCPTTSVoice() {
        if (mGCPTTSAdapter == null) return;

        String languageCode = mView.getSelectedLanguageText();
        String name = mView.getSelectedStyleText();
        float pitch = ((float) (mView.getProgressPitch() - 2000) / 100);
        float speakRate = ((float) (mView.getProgressSpeakRate() + 25) / 100);

        GCPVoice gcpVoice = new GCPVoice(languageCode, name);
        AudioConfig audioConfig = new AudioConfig.Builder()
                .addAudioEncoding(EAudioEncoding.MP3)
                .addSpeakingRate(speakRate)
                .addPitch(pitch)
                .build();

        mGCPTTSAdapter.setGCPVoice(gcpVoice);
        mGCPTTSAdapter.setAudioConfig(audioConfig);
    }

    public void selectLanguage(String language) {
        mView.setAdapterStyle(mVoiceCollection.getNames(language));
        mView.setSpinnerStyle(mVoiceCollection.getNames(language));
    }

    public void selectStyle(String style) {
        GCPVoice gcpVoice = mVoiceCollection.getGCPVoice(mView.getSelectedLanguageText(), style);
        if (gcpVoice != null) {
            mView.setTextViewGender(gcpVoice.getESSMLlGender().toString());
            mView.setTextViewSampleRate(String.valueOf(gcpVoice.getNaturalSampleRateHertz()));
        }
    }

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

        mVoiceCollection = new VoiceCollection();
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
                mVoiceCollection.add(language, gcpVoice);
            }
        }

        mView.setAdapterLanguage(mVoiceCollection.getLanguage());
        mView.setSpinnerLanguage(mVoiceCollection.getLanguage());
    }

    @Override
    public void onFailure(String error) {
        mView.clearUI();
        mView.makeToast("Loading Voice List Error, error code : " + error, false);
        Log.e(TAG, "Loading Voice List Error, error code : " + error);
    }

    public void startSpeak(String text) {
        mSpeechManager.stopSpeak();
        if (mVoiceCollection == null || mVoiceCollection.size() == 0) {
            mView.makeToast("Loading Voice Error, please check network or API_KEY.", true);
        } else {
            initGCPTTSVoice();
        }

        mSpeechManager.startSpeak(text);
    }

    public void stopSpeak() {
        mSpeechManager.stopSpeak();
    }

    public void resumeSpeak() {
        mSpeechManager.resume();
    }

    public void pauseSpeak() {
        mSpeechManager.pause();
    }

    public void disposeSpeak() {
        mSpeechManager.dispose();
        mSpeechManager = null;
    }
}



