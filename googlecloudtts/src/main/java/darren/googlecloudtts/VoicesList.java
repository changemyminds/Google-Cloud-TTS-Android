package darren.googlecloudtts;

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

    public void add(String language, VoiceSelectionParams gcpVoice) {
        List<VoiceSelectionParams> googleCloudVoices = hashMap.get(language);

        if (googleCloudVoices == null) {
            googleCloudVoices = new ArrayList<>();
            hashMap.put(language, googleCloudVoices);
            return;
        }

        googleCloudVoices.add(gcpVoice);
    }

    public String[] getLanguages() {
        if (hashMap.size() == 0) {
            throw new NullPointerException("Languages size is zero!!");
        }

        List<String> languages = new ArrayList<>(hashMap.keySet());
        return languages.toArray(new String[0]);
    }

    public String[] getNames(String language) {
        if (isEmptyOrNull(language)) {
            throw new NullPointerException("language is null");
        }

        return getGCPVoices(language).stream()
                .map(VoiceSelectionParams::getName)
                .toArray(String[]::new);
    }

    public VoiceSelectionParams getGCPVoice(String language, String name) {
        if (isEmptyOrNull(language)) {
            throw new NullPointerException("language is null or empty");
        }

        if (isEmptyOrNull(name)) {
            throw new NullPointerException("name is null or empty");
        }

        Optional<VoiceSelectionParams> googleCloudVoice = getGCPVoices(language).stream()
                .filter(m -> m.getName().equals(name))
                .findFirst();

        if (!googleCloudVoice.isPresent()) {
            throw new NullPointerException("can't find the name " + name);
        }

        return googleCloudVoice.get();
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

    private List<VoiceSelectionParams> getGCPVoices(String language) {
        List<VoiceSelectionParams> googleCloudVoices = hashMap.get(language);
        if (googleCloudVoices == null) {
            throw new NullPointerException("Can't find the language " + language);
        }

        return googleCloudVoices;
    }

    private boolean isEmptyOrNull(String text) {
        return text == null || text.length() == 0;
    }


}
