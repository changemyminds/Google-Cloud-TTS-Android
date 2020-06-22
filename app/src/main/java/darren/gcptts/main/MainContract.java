package darren.gcptts.main;

import android.content.Context;
/**
 * Author: Changemyminds.
 * Date: 2020/6/21.
 * Description:
 * Reference:
 */
public interface MainContract {
    interface IView {
        void setLanguages(String[] languages);

        String getSelectedLanguageText();

        void setStyles(String[] styles);

        void clearUI();

        String getSelectedStyleText();

        void setTextViewGender(String gender);

        void setTextViewSampleRate(String sampleRate);

        int getProgressPitch();

        int getProgressSpeakRate();

        void makeToast(String text, boolean longShow);

        // update Ui
        void invoke(Runnable runnable);

        // Property Inject
        void setPresenter(IPresenter presenter);

        Context getContext();
    }

    interface IPresenter {
        void onCreate();

        void onDestroy();

        void selectLanguage(String language);

        void selectStyle(String style);

        void startSpeak(String text);

        void stopSpeak();

        void pauseSpeak();

        void resumeSpeak();

        void disposeSpeak();

        void initAndroidTTS();

        void loadGoogleCloudTTS();
    }
}
