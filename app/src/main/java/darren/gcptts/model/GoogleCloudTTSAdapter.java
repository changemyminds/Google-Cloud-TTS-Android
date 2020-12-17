package darren.gcptts.model;

import darren.googlecloudtts.GoogleCloudTTS;

/**
 * Author: Changemyminds.
 * Date: 2018/6/25.
 * Description:
 * Reference:
 */
public class GoogleCloudTTSAdapter implements Speech {
    private GoogleCloudTTS mGoogleCloudTTS;

    public GoogleCloudTTSAdapter(GoogleCloudTTS googleCloudTTS) {
        mGoogleCloudTTS = googleCloudTTS;
    }

    @Override
    public void start(String text) {
        mGoogleCloudTTS.start(text);
    }

    @Override
    public void resume() {
        mGoogleCloudTTS.resume();
    }

    @Override
    public void pause() {
        mGoogleCloudTTS.pause();
    }

    @Override
    public void stop() {
        mGoogleCloudTTS.stop();
    }

    @Override
    public void exit() {
        mGoogleCloudTTS.close();
    }
}
