package darren.gcptts.main;

import android.util.Log;

import java.util.Locale;

import darren.gcptts.BuildConfig;
import darren.gcptts.model.AndroidTTSAdapter;
import darren.gcptts.model.GoogleCloudTTSAdapter;
import darren.gcptts.model.SpeechManagerImpl;
import darren.gcptts.model.androidtts.AndroidVoice;
import darren.googlecloudtts.parameter.AudioConfig;
import darren.googlecloudtts.parameter.AudioEncoding;
import darren.googlecloudtts.parameter.VoiceSelectionParams;
import darren.googlecloudtts.VoicesList;
import darren.googlecloudtts.GoogleCloudAPIConfig;

/**
 * Author: Changemyminds.
 * Date: 2019/6/23.
 * Description:
 * Reference:
 */
public class MainPresenter {
//    private static final String TAG = "MainPresenter";
//
//    private MainContract.IView mView;
//
//    private GoogleCloudAPIConfig mApiConfig = new GoogleCloudAPIConfig(BuildConfig.API_KEY);
//
//    private VoicesList mVoicesCollection;
//
//    private SpeechManagerImpl mSpeechManager;
//    private GoogleCloudTTSAdapter mGoogleCloudTTSAdapter;
//
//    public MainPresenter(MainContract.IView view) {
//        // ioc
//        mView = view;
//        mView.setPresenter(this);
//    }
//
//    @Override
//    public void onCreate() {
//        // SpeechManagerImpl decorator
//        mSpeechManager = new SpeechManagerImpl();
//
//        // init GoogleCloudTTSAdapter and set default
//        mGoogleCloudTTSAdapter = new GoogleCloudTTSAdapter(mApiConfig);
//        mGoogleCloudTTSAdapter.addVoiceListener(this);
//        loadGoogleCloudTTS();
//
//        // set ConcreteDecorator
//        mSpeechManager.setSpeech(mGoogleCloudTTSAdapter);
//    }
//
//    @Override
//    public void onDestroy() {
//
//        disposeSpeak();
//    }
//
//    private void initGoogleCloudTTSVoice() {
//        String languageCode = mView.getSelectedLanguageText();
//        String name = mView.getSelectedStyleText();
//        float pitch = ((float) (mView.getProgressPitch() - 2000) / 100);
//        float speakRate = ((float) (mView.getProgressSpeakRate() + 25) / 100);
//
//        mGoogleCloudTTSAdapter.setGoogleCloudVoice(new VoiceSelectionParams(languageCode, name))
//                .setAudioConfig(new AudioConfig.Builder()
//                        .addAudioEncoding(AudioEncoding.MP3)
//                        .addSpeakingRate(speakRate)
//                        .addPitch(pitch)
//                        .build()
//                );
//    }
//
//    public void selectLanguage(String language) {
//        if (mVoicesCollection == null) return;
//
//        mView.setStyles(mVoicesCollection.getNames(language));
//    }
//
//    public void selectStyle(String style) {
//        if (mVoicesCollection == null) return;
//
//        VoiceSelectionParams gcpVoice = mVoicesCollection.getGCPVoice(mView.getSelectedLanguageText(), style);
//        if (gcpVoice == null) return;
//
//        mView.setTextViewGender(gcpVoice.getSsmlGender().toString());
//        mView.setTextViewSampleRate(String.valueOf(gcpVoice.getNaturalSampleRateHertz()));
//    }
//
//    @Override
//    public void onResponse(String jsonText) {
//        Log.d(TAG, "Load voice json string " + jsonText);
//    }
//
//    @Override
//    public void onReceive(VoicesList collection) {
//        mVoicesCollection = collection;
//        mView.invoke(() ->
//        {
//            mView.setLanguages(mVoicesCollection.getLanguages());
//            mView.makeToast("Google Cloud voice update.", false);
//        });
//    }
//
//    @Override
//    public void onFailure(String error) {
//        mVoicesCollection = null;
//
//        mView.invoke(() -> {
//            mView.clearUI();
//            mView.makeToast("Loading Voice List Error, error code : " + error, true);
//        });
//
//        Log.e(TAG, "Loading Voice List Error, error code : " + error);
//    }
//
//    public void startSpeak(String text) {
//        mSpeechManager.stopSpeak();
//        if (mVoicesCollection == null || mVoicesCollection.size() == 0) {
//            mView.makeToast("Loading Voice Error, please check network or API_KEY.", true);
//            return;
//        }
//
//        initGoogleCloudTTSVoice();
//        mSpeechManager.startSpeak(text);
//    }
//
//    public void stopSpeak() {
//        mSpeechManager.stopSpeak();
//    }
//
//    public void resumeSpeak() {
//        mSpeechManager.resume();
//    }
//
//    public void pauseSpeak() {
//        mSpeechManager.pause();
//    }
//
//    public void disposeSpeak() {
//        mSpeechManager.dispose();
//        mSpeechManager = null;
//    }
//
//    @Override
//    public void loadGoogleCloudTTS() {
//        mView.invoke(() -> mView.makeToast("Loading google cloud voice list please wait.", true));
//        mGoogleCloudTTSAdapter.loadVoiceList();
//    }
//
//    @Override
//    public void initAndroidTTS() {
//        AndroidTTSAdapter androidTTSAdapter = new AndroidTTSAdapter(mView.getContext());
//        AndroidVoice androidVoice = new AndroidVoice.Builder()
//                .addLanguage(Locale.ENGLISH)
//                .addPitch(1.0f)
//                .addSpeakingRate(1.0f)
//                .build();
//        androidTTSAdapter.setAndroidVoice(androidVoice);
//
//        // set the next handler
//        SpeechManagerImpl androidTTSManager = new SpeechManagerImpl();
//        androidTTSManager.setSpeech(androidTTSAdapter);
//        mSpeechManager.setSupervisor(androidTTSManager);
//    }
}



