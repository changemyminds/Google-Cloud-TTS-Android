package darren.googlecloudtts.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import darren.googlecloudtts.parameter.VoiceSelectionParams;

/**
 * Author: Changemyminds.
 * Date: 2018/6/24.
 * Description:
 * Reference:
 */
public class VoicesList {
    private HashMap<String, List<VoiceSelectionParams>> hashMap = new HashMap<>();

    public void add(String languageCode, VoiceSelectionParams params) {
        List<VoiceSelectionParams> googleCloudVoices = hashMap.get(languageCode);
        if (googleCloudVoices == null) {
            googleCloudVoices = new ArrayList<>();
            hashMap.put(languageCode, googleCloudVoices);
        }

        googleCloudVoices.add(params);
    }

    public String[] getLanguageCodes() {
        if (hashMap.size() == 0) {
            throw new NullPointerException("LanguageCodes size is zero!!");
        }

        List<String> languages = new ArrayList<>(hashMap.keySet());
        return languages.stream()
                .sorted(String::compareTo)
                .toArray(String[]::new);
    }

    public String[] getVoiceNames(String languageCode) {
        if (isEmptyOrNull(languageCode)) {
            throw new NullPointerException("languageCode is null");
        }

        return getGCPVoices(languageCode).stream()
                .map(VoiceSelectionParams::getName)
                .sorted(String::compareTo)
                .toArray(String[]::new);
    }

    public VoiceSelectionParams getGCPVoice(String languageCode, String voiceName) {
        if (isEmptyOrNull(languageCode)) {
            throw new NullPointerException("LanguageCode is null or empty");
        }

        if (isEmptyOrNull(voiceName)) {
            throw new NullPointerException("VoiceName is null or empty");
        }

        Optional<VoiceSelectionParams> googleCloudVoice = getGCPVoices(languageCode).stream()
                .filter(m -> m.getName().equals(voiceName))
                .findFirst();

        if (!googleCloudVoice.isPresent()) {
            throw new NullPointerException("Can't find the VoiceName " + voiceName);
        }

        return googleCloudVoice.get();
    }

    public void update(VoicesList voicesList) {
        hashMap = new HashMap<>(voicesList.hashMap);
    }

    public void clear() {
        for (HashMap.Entry<String, List<VoiceSelectionParams>> entry : hashMap.entrySet()) {
            List<VoiceSelectionParams> gcpVoices = entry.getValue();
            gcpVoices.clear();
        }

        hashMap.clear();
    }

    public int size() {
        return hashMap.size();
    }

    private List<VoiceSelectionParams> getGCPVoices(String languageCode) {
        List<VoiceSelectionParams> googleCloudVoices = hashMap.get(languageCode);
        if (googleCloudVoices == null) {
            throw new NullPointerException("Can't find the languageCode " + languageCode);
        }

        return googleCloudVoices;
    }

    private boolean isEmptyOrNull(String text) {
        return text == null || text.length() == 0;
    }
}
