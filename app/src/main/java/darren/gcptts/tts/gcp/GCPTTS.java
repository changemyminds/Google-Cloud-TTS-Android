package darren.gcptts.tts.gcp;

import android.media.MediaPlayer;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import darren.gcptts.tts.ISpeech;

/**
 * Created by USER on 2018/6/24.
 */

public class GCPTTS {
    private static final String TAG = GCPTTS.class.getName();

    private List<ISpeakListener> mSpeakListeners = new ArrayList<>();

    private GCPVoice mGCPVoice;
    private AudioConfig mAudioConfig;
    private VoiceMessage mVoiceMessage;
    private MediaPlayer mMediaPlayer;

    private int mVoiceLength = -1;

    public GCPTTS() {
    }

    public GCPTTS(GCPVoice gcpVoice, AudioConfig audioConfig) {
        mGCPVoice = gcpVoice;
        mAudioConfig = audioConfig;
    }

    public void setGCPVoice(GCPVoice gcpVoice) {
        mGCPVoice = gcpVoice;
    }

    public void setAudioConfig(AudioConfig audioConfig) {
        mAudioConfig = audioConfig;
    }

    public void start(String text) {
        if (mGCPVoice != null && mAudioConfig != null) {
            mVoiceMessage = new VoiceMessage.Builder()
                    .add(new Input(text))
                    .add(mGCPVoice)
                    .add(mAudioConfig)
                    .build();
            new Thread(runnableSend).start();
        }
    }

    private Runnable runnableSend = new Runnable() {
        @Override
        public void run() {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    mVoiceMessage.toString());
            Request request = new Request.Builder()
                    .url(Config.SYNTHESIZE_ENDPOINT)
                    .addHeader(Config.API_KEY_HEADER, Config.API_KEY)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .post(body)
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Log.e(TAG, "onFailure error : " + e.getMessage());
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (response != null) {
                        Log.i(TAG, "onResponse code = " + response.code());
                        if (response.code() == 200) {
                            String text = response.body().string();
                            JsonElement jsonElement = new JsonParser().parse(text);
                            JsonObject jsonObject = jsonElement.getAsJsonObject();

                            if (jsonObject != null) {
                                String json = jsonObject.get("audioContent").toString();
                                json = json.replace("\"", "");
                                playAudio(json);
                            }
                        }
                    }
                }
            });
        }
    };

    private void playAudio(String base64EncodedString) {
        try {
            stopAudio();

            String url = "data:audio/mp3;base64," + base64EncodedString;
            if (mMediaPlayer == null) {
                mMediaPlayer = new MediaPlayer();
            }

            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException IoEx) {
            System.out.print(IoEx.getMessage());
        }
    }

    void stopAudio() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mVoiceLength = -1;
        }
    }

    void resumeAudio() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying() && mVoiceLength != -1) {
            mMediaPlayer.seekTo(mVoiceLength);
            mMediaPlayer.start();
        }
    }

    void pauseAudio() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mVoiceLength = mMediaPlayer.getCurrentPosition();
        }
    }

    void exit() {
        stopAudio();
        mMediaPlayer = null;
    }

    private void speakSuccess() {
        for (ISpeakListener speakListener : mSpeakListeners) {
            speakListener.onSuccess();
        }
    }

    private void speakFail(String message) {
        for (ISpeakListener speakListener : mSpeakListeners) {
            speakListener.onFailure(message);
        }
    }

    public void addSpeakListener(ISpeakListener iSpeakListener) {
        mSpeakListeners.add(iSpeakListener);
    }

    public void removeSpeakListener(ISpeakListener iSpeakListener) {
        mSpeakListeners.remove(iSpeakListener);
    }

    public interface ISpeakListener {
        void onSuccess();
        void onFailure(String message);
    }
}
