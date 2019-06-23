package darren.gcptts.model.gcp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Changemyminds.
 * Date: 2018/6/24.
 * Description:
 * Reference:
 */
public class VoiceCollection {
    HashMap<String, List<GCPVoice>> hashMap;

    public VoiceCollection() {
        hashMap = new HashMap<>();
    }

    public void add(String language, GCPVoice gcpVoice) {
        if (hashMap.get(language) == null) {
            List<GCPVoice> list = new ArrayList<>();
            list.add(gcpVoice);
            hashMap.put(language, list);
            return;
        }

        List<GCPVoice> list = hashMap.get(language);
        list.add(gcpVoice);
    }

    public String[] getLanguage() {
        if (hashMap.size() == 0) {
            return null;
        }

        List<String> languages = new ArrayList<>();
        languages.addAll(hashMap.keySet());
        return languages.toArray(new String[languages.size()]);
    }

    public String[] getNames(String language) {
        if (language == null || language.length() == 0 || hashMap.get(language) == null) {
            return null;
        }

        List<String> names = new ArrayList<>();
        List<GCPVoice> gcpVoices = hashMap.get(language);
        for (GCPVoice gcpVoice : gcpVoices) {
            names.add(gcpVoice.getName());
        }

        return names.toArray(new String[names.size()]);
    }

    public List<GCPVoice> getGCPVoices(String language) {
        if (hashMap.get(language) == null) {
            return null;
        }

        return hashMap.get(language);
    }

    public GCPVoice getGCPVoice(String language, String name) {
        if (language == null || language.length() == 0 ||
                name == null || name.length() == 0 || hashMap.get(language) == null) {
            return null;
        }

        List<GCPVoice> gcpVoices = hashMap.get(language);
        for (GCPVoice gcpVoice : gcpVoices) {
            if (gcpVoice.getName().compareTo(name) == 0) {
                return gcpVoice;
            }
        }

        return null;
    }

    public void clear() {
        for (HashMap.Entry<String, List<GCPVoice>> entry : hashMap.entrySet()) {
            List<GCPVoice> gcpVoices = entry.getValue();
            gcpVoices.clear();
        }

        hashMap.clear();
    }

    public int size(){
        return hashMap.size();
    }


}
