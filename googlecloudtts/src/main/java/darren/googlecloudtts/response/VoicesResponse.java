package darren.googlecloudtts.response;

import java.io.Serializable;
import java.util.List;

import darren.googlecloudtts.parameter.SsmlVoiceGender;

/**
 * Author: Changemyminds.
 * Date: 2020/12/17.
 * Description:
 * Reference:
 */
public class VoicesResponse implements Serializable {
    private List<Voices> voices;

    public List<Voices> getVoices() {
        return voices;
    }

    public void setVoices(List<Voices> voices) {
        this.voices = voices;
    }

    public static class Voices {
        private List<String> languageCodes;
        private String name;
        private SsmlVoiceGender ssmlGender;
        private Integer naturalSampleRateHertz;

        public List<String> getLanguageCodes() {
            return languageCodes;
        }

        public String getName() {
            return name;
        }

        public SsmlVoiceGender getSsmlGender() {
            return ssmlGender;
        }

        public Integer getNaturalSampleRateHertz() {
            return naturalSampleRateHertz;
        }

        public void setLanguageCodes(List<String> languageCodes) {
            this.languageCodes = languageCodes;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSsmlGender(SsmlVoiceGender ssmlGender) {
            this.ssmlGender = ssmlGender;
        }

        public void setNaturalSampleRateHertz(Integer naturalSampleRateHertz) {
            this.naturalSampleRateHertz = naturalSampleRateHertz;
        }
    }
}
