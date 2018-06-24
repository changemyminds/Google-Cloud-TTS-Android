package darren.gcptts.tts.gcp;

/**
 * Created by USER on 2018/6/22.
 */


public class AudioConfig {
    private EAudioEncoding mEAudioEncoding;
    private float mSpeakingRate;            // range: 0.25 ~ 4.00
    private float mPitch;                   // range: -20.00 ~ 20.00
    private int mVolumeGainDb;
    private int mSampleRateHertz;

    private AudioConfig() {
        mEAudioEncoding = EAudioEncoding.LINEAR16;
        mSpeakingRate = 1.0f;
        mPitch = 0.0f;
        mVolumeGainDb = 0;
        mSampleRateHertz = 0;
    }

    public static class Builder {
        private AudioConfig mAudioConfig;

        public Builder() {
            mAudioConfig = new AudioConfig();
        }

        public Builder addAudioEncoding(EAudioEncoding EAudioEncoding) {
            mAudioConfig.mEAudioEncoding = EAudioEncoding;
            return this;
        }

        public Builder addSpeakingRate(float speakingRate) {
            mAudioConfig.mSpeakingRate = speakingRate;
            return this;
        }

        public Builder addPitch(float pitch) {
            mAudioConfig.mPitch = pitch;
            return this;
        }

        public Builder addVolumeGainDb(int volumeGainDb) {
            mAudioConfig.mVolumeGainDb = volumeGainDb;
            return this;
        }

        public Builder addSampleRateHertz(int sampleRateHertz) {
            mAudioConfig.mSampleRateHertz = sampleRateHertz;
            return this;
        }

        public AudioConfig build() {
            return mAudioConfig;
        }
    }

    @Override
    public String toString() {
        String text = "'audioConfig':{";
        text += "'audioEncoding':'" + mEAudioEncoding.toString() + "',";
        text += "'speakingRate':'" + String.valueOf(mSpeakingRate) + "',";
        text += "'pitch':'" + String.valueOf(mPitch) + "'";
        text += (mVolumeGainDb == 0) ? "" : ",'" + String.valueOf(mVolumeGainDb) + "'";
        text += (mSampleRateHertz == 0) ? "" : ",'" + String.valueOf(mSampleRateHertz) + "'";
        text += "}";
        return text;
    }
}
