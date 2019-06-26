package darren.gcptts.model;

/**
 * Author: Changemyminds.
 * Date: 2019/4/14.
 * Description:
 * Reference:
 */
public class SpeechManager extends AbstractSpeechManager implements ISpeech.ISpeechListener {
    private boolean mSpeakSuccess;
    private ISpeech mSpeech;

    public void setSpeech(ISpeech speech) {
        mSpeakSuccess = false;
        mSpeech = speech;

        if (mSpeech != null) {
            mSpeech.addSpeechListener(this);
        }
    }

    @Override
    public void startSpeak(String text) {
        if (mSpeech != null) {
            mSpeech.start(text);
        } else {
            if (mSpeechManger != null) {
                mSpeechManger.startSpeak(text);
            }
        }
    }

    @Override
    public void stopSpeak() {
        if (mSpeech != null && mSpeakSuccess) {
            mSpeech.stop();
        } else {
            if (mSpeechManger != null) {
                mSpeechManger.stopSpeak();
            }
        }
    }

    @Override
    public void pause() {
        if (mSpeech != null && mSpeakSuccess) {
            mSpeech.pause();
        } else {
            if (mSpeechManger != null) {
                mSpeechManger.pause();
            }
        }
    }

    @Override
    public void resume() {
        if (mSpeech != null && mSpeakSuccess) {
            mSpeech.resume();
        } else {
            if (mSpeechManger != null) {
                mSpeechManger.resume();
            }
        }
    }

    @Override
    public void onSuccess(String message) {
        mSpeakSuccess = true;
    }

    @Override
    public void onFailure(String message, Exception e) {
        mSpeakSuccess = false;
        if (super.mSpeechManger != null) {
            mSpeechManger.startSpeak(message);
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
