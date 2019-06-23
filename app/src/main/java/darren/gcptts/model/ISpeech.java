package darren.gcptts.model;

/**
 * Author: Changemyminds.
 * Date: 2018/6/24.
 * Description:
 * Reference:
 */
public interface ISpeech {
    void start(String text);

    void resume();

    void pause();

    void stop();

    void exit();

    void addSpeechListener(ISpeechListener speechListener);

    void removeSpeechListener(ISpeechListener speechListener);

    interface ISpeechListener {
        void onSuccess(String message);
        void onFailure(String message, Exception e);
    }
}
