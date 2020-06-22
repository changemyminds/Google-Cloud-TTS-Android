package darren.gcptts.main;

import android.util.Log;

import java.util.Locale;

import darren.gcptts.BuildConfig;
import darren.gcptts.model.AndroidTTSAdapter;
import darren.gcptts.model.GoogleCloudTTSAdapter;
import darren.gcptts.model.SpeechManager;
import darren.gcptts.model.android.AndroidVoice;
import darren.googlecloudtts.AudioConfig;
import darren.googlecloudtts.EAudioEncoding;
import darren.googlecloudtts.GoogleCloudVoice;
import darren.googlecloudtts.VoiceCollection;
import darren.googlecloudtts.VoiceList;
import darren.googlecloudtts.GoogleCloudAPIConfig;

/**
 * Author: Changemyminds.
 * Date: 2019/6/23.
 * Description:
 * Reference:
 */
public class MainPresenter implements MainContract.IPresenter, VoiceList.IVoiceListener {
    private static final String TAG = "MainPresenter";

    private MainContract.IView mView;

    private GoogleCloudAPIConfig mApiConfig = new GoogleCloudAPIConfig(BuildConfig.API_KEY);

    private VoiceCollection mVoiceCollection;

    private SpeechManager mSpeechManager;
    private GoogleCloudTTSAdapter mGoogleCloudTTSAdapter;

    public MainPresenter(MainContract.IView view) {
        // ioc
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void onCreate() {
        // SpeechManager decorator
        mSpeechManager = new SpeechManager();

        // init GoogleCloudTTSAdapter and set default
        mGoogleCloudTTSAdapter = new GoogleCloudTTSAdapter(mApiConfig);
        mGoogleCloudTTSAdapter.addVoiceListener(this);
        loadGoogleCloudTTS();

        // set ConcreteDecorator
        mSpeechManager.setSpeech(mGoogleCloudTTSAdapter);
    }

    @Override
    public void onDestroy() {
        disposeSpeak();
    }

    private void initGoogleCloudTTSVoice() {
        String languageCode = mView.getSelectedLanguageText();
        String name = mView.getSelectedStyleText();
        float pitch = ((float) (mView.getProgressPitch() - 2000) / 100);
        float speakRate = ((float) (mView.getProgressSpeakRate() + 25) / 100);

        mGoogleCloudTTSAdapter.setGoogleCloudVoice(new GoogleCloudVoice(languageCode, name))
                .setAudioConfig(new AudioConfig.Builder()
                        .addAudioEncoding(EAudioEncoding.MP3)
                        .addSpeakingRate(speakRate)
                        .addPitch(pitch)
                        .build()
                );
    }

    public void selectLanguage(String language) {
        if (mVoiceCollection == null) return;

        mView.setStyles(mVoiceCollection.getNames(language));
    }

    public void selectStyle(String style) {
        if (mVoiceCollection == null) return;

        GoogleCloudVoice gcpVoice = mVoiceCollection.getGCPVoice(mView.getSelectedLanguageText(), style);
        if (gcpVoice == null) return;

        mView.setTextViewGender(gcpVoice.getESSMLlGender().toString());
        mView.setTextViewSampleRate(String.valueOf(gcpVoice.getNaturalSampleRateHertz()));
    }

    @Override
    public void onResponse(String jsonText) {
        Log.d(TAG, "Load voice json string " + jsonText);
    }

    @Override
    public void onReceive(VoiceCollection collection) {
        mVoiceCollection = collection;
        mView.invoke(() ->
        {
            mView.setLanguages(mVoiceCollection.getLanguage());
            mView.makeToast("Google Cloud voice update.", false);
        });
    }

    @Override
    public void onFailure(String error) {
        mVoiceCollection = null;

        mView.invoke(() -> {
            mView.clearUI();
            mView.makeToast("Loading Voice List Error, error code : " + error, true);
        });

        Log.e(TAG, "Loading Voice List Error, error code : " + error);
    }

    public void startSpeak(String text) {
        mSpeechManager.stopSpeak();
        if (mVoiceCollection == null || mVoiceCollection.size() == 0) {
            mView.makeToast("Loading Voice Error, please check network or API_KEY.", true);
            return;
        }

        initGoogleCloudTTSVoice();
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

    @Override
    public void loadGoogleCloudTTS() {
        mView.invoke(() -> mView.makeToast("Loading google cloud voice list please wait.", true));
        mGoogleCloudTTSAdapter.loadVoiceList();
    }

    @Override
    public void initAndroidTTS() {
        AndroidTTSAdapter androidTTSAdapter = new AndroidTTSAdapter(mView.getContext());
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
    }
}



