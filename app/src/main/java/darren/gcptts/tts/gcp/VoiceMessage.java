package darren.gcptts.tts.gcp;

/**
 * Created by USER on 2018/6/24.
 */

public class VoiceMessage {
    private Input mInput;
    private GCPVoice mGCPVoice;
    private AudioConfig mAudioConfig;

    private VoiceMessage() {
    }

    public static class Builder {
        private VoiceMessage mVoiceMessage;

        public Builder() {
            mVoiceMessage = new VoiceMessage();
        }

        public Builder add(Input input) {
            mVoiceMessage.mInput = input;
            return this;
        }

        public Builder add(GCPVoice GCPVoice) {
            mVoiceMessage.mGCPVoice = GCPVoice;
            return this;
        }

        public Builder add(AudioConfig audioConfig) {
            mVoiceMessage.mAudioConfig = audioConfig;
            return this;
        }

        public VoiceMessage build() {
            return mVoiceMessage;
        }
    }

    @Override
    public String toString() {
        return "{" + mInput.toString() + "," +
                mGCPVoice.toString() + "," +
                mAudioConfig.toString() +
                "}";
    }
}
