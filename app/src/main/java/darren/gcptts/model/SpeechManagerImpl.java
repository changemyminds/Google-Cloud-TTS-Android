package darren.gcptts.model;

/**
 * Author: Changemyminds.
 * Date: 2019/4/14.
 * Description:
 * Reference:
 */
public class SpeechManagerImpl extends SpeechManager {
    private boolean mSpeakSuccess;
    private Speech mSpeech;

    public void setSpeech(Speech speech) {
        mSpeakSuccess = false;
        mSpeech = speech;
    }

    @Override
    public void startSpeak(String text) {
        if (mSpeech != null) {
            mSpeech.start(text);
            return;
        }

        if (mSpeechManger != null) {
            mSpeechManger.startSpeak(text);
        }
    }

    @Override
    public void stopSpeak() {
        if (mSpeech != null && mSpeakSuccess) {
            mSpeech.stop();
            return;
        }

        if (mSpeechManger != null) {
            mSpeechManger.stopSpeak();
        }
    }

    @Override
    public void pause() {
        if (mSpeech != null && mSpeakSuccess) {
            mSpeech.pause();
            return;
        }

        if (mSpeechManger != null) {
            mSpeechManger.pause();
        }
    }

    @Override
    public void resume() {
        if (mSpeech != null && mSpeakSuccess) {
            mSpeech.resume();
        }

        if (mSpeechManger != null) {
            mSpeechManger.resume();
        }
    }

    @Override
    public void dispose() {
        if (mSpeech != null) {
            mSpeech.exit();
        }

        if (mSpeechManger != null) {
            mSpeechManger.dispose();
        }
    }
}
