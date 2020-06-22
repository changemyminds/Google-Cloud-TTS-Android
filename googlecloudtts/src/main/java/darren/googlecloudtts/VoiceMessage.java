package darren.googlecloudtts;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Changemyminds.
 * Date: 2018/6/24.
 * Description:
 * Reference:
 */
public class VoiceMessage {
    private Input mInput;
    private GoogleCloudVoice mGCPVoice;
    private AudioConfig mAudioConfig;

    private List<VoiceParameter> mVoiceParameters;

    private VoiceMessage() {
        mVoiceParameters = new ArrayList<>();
    }

    public static class Builder {
        private VoiceMessage mVoiceMessage;

        public Builder() {
            mVoiceMessage = new VoiceMessage();
        }

        public Builder addParameter(VoiceParameter voiceParameter){
            mVoiceMessage.mVoiceParameters.add(voiceParameter);
            return this;
        }

        @Deprecated
        public Builder add(Input input) {
            mVoiceMessage.mVoiceParameters.add(input);
            return this;
        }

        @Deprecated
        public Builder add(GoogleCloudVoice GCPVoice) {
            mVoiceMessage.mVoiceParameters.add(GCPVoice);
            return this;
        }

        @Deprecated
        public Builder add(AudioConfig audioConfig) {
            mVoiceMessage.mVoiceParameters.add(audioConfig);
            return this;
        }

        public VoiceMessage build() {
            return mVoiceMessage;
        }
    }

    @Override
    public String toString() {
        if (mVoiceParameters.size() != 0) {
            JSONObject jsonObject = new JSONObject();
            try {
                for (VoiceParameter v : mVoiceParameters) {
                    jsonObject.put(v.getJSONHeader(), v.toJSONObject());
                }
                return jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return "";
    }
}
