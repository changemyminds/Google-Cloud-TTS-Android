package darren.googlecloudtts;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Changemyminds.
 * Date: 2018/6/22.
 * Description:
 * Reference:
 */
public class AudioConfig implements VoiceParameter {
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
    public String getJSONHeader() {
        return "audioConfig";
    }

     @Override
    public JSONObject toJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("audioEncoding", mEAudioEncoding.toString());
            jsonObject.put("speakingRate", String.valueOf(mSpeakingRate));
            jsonObject.put("pitch", getPitch());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Deprecated
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

    private String getPitch(){
        List<String> pitchList = new ArrayList<>();
        pitchList.add(String.valueOf(mPitch));
        if ((mVolumeGainDb != 0)) {
            pitchList.add(String.valueOf(mVolumeGainDb));
        }
        if (mSampleRateHertz != 0) {
            pitchList.add(String.valueOf(mSampleRateHertz));
        }
        return pitchList.toString().replace("[", "").replace("]", "");
    }
}
