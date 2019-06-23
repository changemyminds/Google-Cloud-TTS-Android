package darren.gcptts.model.gcp;

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

/**
 * Author: Changemyminds.
 * Date: 2018/6/24.
 * Description:
 * Reference:
 */
public class GCPTTS {
    private static final String TAG = GCPTTS.class.getName();

    private List<ISpeakListener> mSpeakListeners = new ArrayList<>();

    private GCPVoice mGCPVoice;
    private AudioConfig mAudioConfig;
    private String mMessage;
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
            mMessage = text;
            mVoiceMessage = new VoiceMessage.Builder()
                    .addParameter(new Input(text))
                    .addParameter(mGCPVoice)
                    .addParameter(mAudioConfig)
                    .build();
            new Thread(runnableSend).start();
        } else {
            speakFail(text, new NullPointerException("GcpVoice or AudioConfig does not setting"));
        }
    }

    private Runnable runnableSend = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "Message: " + mVoiceMessage.toString());

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
                    speakFail(mMessage, e);
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
                                return;
                            }
                        }
                    }

                    speakFail(mMessage, new NullPointerException("get response fail"));
                }
            });
        }
    };

    private void playAudio(String base64EncodedString) {
        try {
            stopAudio();

            String url = "data:audio/mp3;base64," + base64EncodedString;
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            speakSuccess(mMessage);
        } catch (IOException IoEx) {
            speakFail(mMessage, IoEx);
        }
    }

    public void stopAudio() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mVoiceLength = -1;
        }
    }

    public void resumeAudio() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying() && mVoiceLength != -1) {
            mMediaPlayer.seekTo(mVoiceLength);
            mMediaPlayer.start();
        }
    }

    public void pauseAudio() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mVoiceLength = mMediaPlayer.getCurrentPosition();
        }
    }

    public void exit() {
        stopAudio();
        mMediaPlayer = null;
    }

    private void speakSuccess(String speakMessage) {
        for (ISpeakListener speakListener : mSpeakListeners) {
            speakListener.onSuccess(speakMessage);
        }
    }

    private void speakFail(String speakMessage, Exception e) {
        for (ISpeakListener speakListener : mSpeakListeners) {
            speakListener.onFailure(speakMessage, e);
        }
    }

    public void addSpeakListener(ISpeakListener iSpeakListener) {
        mSpeakListeners.add(iSpeakListener);
    }

    public void removeSpeakListener(ISpeakListener iSpeakListener) {
        mSpeakListeners.remove(iSpeakListener);
    }

    public void removeSpeakListener() {
        mSpeakListeners.clear();
    }

    public interface ISpeakListener {
        void onSuccess(String message);

        void onFailure(String message, Exception e);
    }
}
