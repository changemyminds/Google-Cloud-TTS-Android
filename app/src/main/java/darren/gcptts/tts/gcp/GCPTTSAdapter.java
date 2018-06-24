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

import darren.gcptts.tts.ISpeech;

/**
 * Created by USER on 2018/6/24.
 */

public class GCPTTSAdapter implements ISpeech {
    private static final String TAG = GCPTTSAdapter.class.getName();

    private GCPVoice mGCPVoice;
    private AudioConfig mAudioConfig;
    private VoiceMessage mVoiceMessage;
    private MediaPlayer mMediaPlayer;

    private int mVoiceLength = -1;

    public GCPTTSAdapter(GCPVoice GCPVoice, AudioConfig audioConfig) {
        mGCPVoice = GCPVoice;
        mAudioConfig = audioConfig;
        mMediaPlayer = new MediaPlayer();
    }

    @Override
    public void start(String text) {
        mVoiceMessage = new VoiceMessage.Builder()
                .add(new Input(text))
                .add(mGCPVoice)
                .add(mAudioConfig)
                .build();
        new Thread(runnableSend).start();
    }

    @Override
    public void resume() {
        resumeAudio();
    }

    @Override
    public void pause() {
        pauseAudio();
    }

    @Override
    public void stop() {
        stopAudio();
    }

    @Override
    public void exit() {
        stopAudio();
        mMediaPlayer = null;
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
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException IoEx) {
            System.out.print(IoEx.getMessage());
        }
    }

    private void stopAudio() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mVoiceLength = -1;
        }
    }

    private void resumeAudio() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying() && mVoiceLength != -1) {
            mMediaPlayer.seekTo(mVoiceLength);
            mMediaPlayer.start();
        }
    }

    private void pauseAudio() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mVoiceLength = mMediaPlayer.getCurrentPosition();
        }
    }
}
