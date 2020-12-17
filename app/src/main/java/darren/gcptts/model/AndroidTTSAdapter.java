package darren.gcptts.model;

import android.content.Context;
import darren.gcptts.model.androidtts.AndroidTTS;

/**
 * Author: Changemyminds.
 * Date: 2018/6/24.
 * Description:
 * Reference:
 */
public class AndroidTTSAdapter extends AndroidTTS implements Speech {
    public AndroidTTSAdapter(Context context) {
        super(context);
    }

    @Override
    public void start(String text) {
        speak(text);
    }

    @Override
    public void resume() {
        // not implements
    }

    @Override
    public void pause() {
        // not implements
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void exit() {
        // release resources
        super.exit();
    }
}
