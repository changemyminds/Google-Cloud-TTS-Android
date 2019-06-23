package darren.gcptts.model.android;

import java.util.Locale;

/**
 * Author: Changemyminds.
 * Date: 2018/6/24.
 * Description:
 * Reference:
 */
public class AndroidVoice {
    private Locale mLocale;
    private float mSpeakingRate;            // range: 0.0 ~ 2.0
    private float mPitch;                   // range: 1.0

    private AndroidVoice() {
        mLocale = Locale.ENGLISH;
        mSpeakingRate = 1.0f;
        mPitch = 1.0f;
    }

    private Locale translate(String language) {
        language = language.toLowerCase();
        Locale locale = Locale.ENGLISH;
        switch (language) {
            case "eng":
                locale = Locale.ENGLISH;
                break;
            case "cht":
                locale = Locale.TAIWAN;
                break;
            case "chi":
                locale = Locale.CHINA;
                break;
        }

        return locale;
    }

    public static class Builder {
        private AndroidVoice mAndroidVoice;

        public Builder() {
            mAndroidVoice = new AndroidVoice();
        }

        public Builder addPitch(float pitch) {
            mAndroidVoice.mPitch = pitch;
            return this;
        }

        public Builder addSpeakingRate(float speakingRate) {
            mAndroidVoice.mSpeakingRate = speakingRate;
            return this;
        }

        public Builder addLanguage(Locale locale) {
            mAndroidVoice.mLocale = locale;
            return this;
        }

        public Builder addLanguage(String language) {
            mAndroidVoice.mLocale = mAndroidVoice.translate(language);
            return this;
        }

        public AndroidVoice build() {
            return mAndroidVoice;
        }
    }


    public float getSpeakingRate() {
        return mSpeakingRate;
    }

    public float getPitch() {
        return mPitch;
    }

    public Locale getLocale() {
        return mLocale;
    }
}
