package darren.gcptts.tts.gcp;

import darren.gcptts.tts.ISpeech;

/**
 * Created by USER on 2018/6/25.
 */

public class GCPTTSAdapter implements ISpeech {
    private GCPTTS mGCPTTS;

    public GCPTTSAdapter(GCPTTS gcptts) {
        mGCPTTS = gcptts;
    }

    @Override
    public void start(String text) {
        mGCPTTS.start(text);
    }

    @Override
    public void resume() {
        mGCPTTS.resumeAudio();
    }

    @Override
    public void pause() {
        mGCPTTS.pauseAudio();
    }

    @Override
    public void stop() {
        mGCPTTS.stopAudio();
    }

    @Override
    public void exit() {
        mGCPTTS.exit();
    }
}
