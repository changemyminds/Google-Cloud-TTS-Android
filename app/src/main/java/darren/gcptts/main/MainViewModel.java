package darren.gcptts.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.function.Supplier;

import darren.googlecloudtts.GoogleCloudTTS;
import darren.googlecloudtts.VoicesList;
import darren.googlecloudtts.parameter.AudioConfig;
import darren.googlecloudtts.parameter.AudioEncoding;
import darren.googlecloudtts.parameter.VoiceSelectionParams;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleOnSubscribe;

/**
 * Author: Changemyminds.
 * Date: 2020/12/17.
 * Description:
 * Reference:
 */
public class MainViewModel extends AndroidViewModel {
    private GoogleCloudTTS mGoogleCloudTTS;
    private VoicesList mVoicesList;

    public MainViewModel(@NonNull Application application, GoogleCloudTTS googleCloudTTS) {
        super(application);
        mGoogleCloudTTS = googleCloudTTS;
    }

    public Single<VoicesList> loading() {
        return Single.fromCallable(() -> mGoogleCloudTTS.load())
                .map(v -> mVoicesList = v);
    }

    public Completable speak(String text) {
        return fromCallable(() -> mGoogleCloudTTS.start(text));
    }

    public void pause() {
        mGoogleCloudTTS.pause();
    }

    public void resume() {
        mGoogleCloudTTS.resume();
    }

    public void dispose() {
        mGoogleCloudTTS.close();
    }

    public void initTTSVoice(String languageCode, String name, float pitch, float speakRate) {
        mGoogleCloudTTS.setGoogleCloudVoice(new VoiceSelectionParams(languageCode, name))
                .setAudioConfig(new AudioConfig(
                        AudioEncoding.MP3,
                        speakRate,
                        pitch
                ));
    }

    public String[] getNames(String language) {
        if (mVoicesList == null) return null;
        return mVoicesList.getNames(language);
    }

    public VoiceSelectionParams getVoiceSelectionParams(String language, String style) {
        if (mVoicesList == null) return null;

        return mVoicesList.getGCPVoice(language, style);
    }

    private Completable fromCallable(Runnable runnable) {
        return Completable.fromCallable(() -> {
            runnable.run();
            return Void.TYPE;
        });
    }


}
