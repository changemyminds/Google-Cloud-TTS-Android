package darren.gcptts.view;

import android.content.Intent;

/**
 * Author: Changemyminds.
 * Date: 2019/6/23.
 * Description:
 * Reference:
 */
public interface MainActivityView {
    void setAdapterLanguage(String[] languages);

    void setSpinnerLanguage(String[] languages);

    String getSelectedLanguageText();

    void setAdapterStyle(String[] styles);

    void setSpinnerStyle(String[] styles);

    void clearUI();

    String getSelectedStyleText();

    void setTextViewGender(String gender);

    void setTextViewSampleRate(String sampleRate);

    int getProgressPitch();

    int getProgressSpeakRate();

    void makeToast(String text, boolean longShow);

    void startActivityForResult(Intent intent, int requestCode);

    void startActivity(Intent intent);
}
