package darren.googlecloudtts.parameter;

import darren.googlecloudtts.exception.OutOfScopeException;

/**
 * Author: Changemyminds.
 * Date: 2018/6/22.
 * Description:
 * Reference:
 */
public class AudioConfig {
    private AudioEncoding audioEncoding;
    private float speakingRate;            // range: 0.25 ~ 4.00
    private float pitch;                   // range: -20.00 ~ 20.00
    private int volumeGainDb;
    private int sampleRateHertz;
    private String[] effectsProfileId;

    public AudioConfig(AudioEncoding audioEncoding, float speakingRate, float pitch) {
        setAudioEncoding(audioEncoding);
        setSpeakingRate(speakingRate);
        setPitch(pitch);
    }

    public AudioConfig() {
        audioEncoding = AudioEncoding.LINEAR16;
        speakingRate = 1.0f;
        pitch = 0.0f;
        volumeGainDb = 0;
        sampleRateHertz = 0;
    }

    public AudioEncoding getAudioEncoding() {
        return audioEncoding;
    }

    public float getSpeakingRate() {
        return speakingRate;
    }

    public float getPitch() {
        return pitch;
    }

    public int getVolumeGainDb() {
        return volumeGainDb;
    }

    public int getSampleRateHertz() {
        return sampleRateHertz;
    }

    public String[] getEffectsProfileId() {
        return effectsProfileId;
    }

    public void setAudioEncoding(AudioEncoding audioEncoding) {
        this.audioEncoding = audioEncoding;
    }

    public void setSpeakingRate(float speakingRate) {
        if (speakingRate < 0.25 || speakingRate > 4.0) {
            throw new OutOfScopeException("The SpeakingRate range is 0.25 ~ 4.0, your speakingRate is " + speakingRate);
        }

        this.speakingRate = speakingRate;
    }

    public void setPitch(float pitch) {
        if (speakingRate <= -20 || speakingRate >= 20) {
            throw new OutOfScopeException("The pitch range is -20 ~ 20, your pitch is " + pitch);
        }
        this.pitch = pitch;
    }

    public void setVolumeGainDb(int volumeGainDb) {
        this.volumeGainDb = volumeGainDb;
    }

    public void setSampleRateHertz(int sampleRateHertz) {
        this.sampleRateHertz = sampleRateHertz;
    }

    public void setEffectsProfileId(String[] effectsProfileId) {
        this.effectsProfileId = effectsProfileId;
    }
}
