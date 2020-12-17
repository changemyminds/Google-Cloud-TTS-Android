package darren.googlecloudtts;

import darren.googlecloudtts.api.SynthesizeApi;
import darren.googlecloudtts.api.SynthesizeApiImpl;
import darren.googlecloudtts.api.VoicesApi;
import darren.googlecloudtts.api.VoicesApiImpl;

/**
 * Author: Changemyminds.
 * Date: 2020/12/17.
 * Description:
 * Reference:
 */
public class GoogleCloudTTSFactory {

    public static GoogleCloudTTS create(String apiKey) {
        GoogleCloudAPIConfig config = new GoogleCloudAPIConfig(apiKey);
        return create(config);
    }

    public static GoogleCloudTTS create(GoogleCloudAPIConfig config) {
        SynthesizeApi synthesizeApi = new SynthesizeApiImpl(config);
        VoicesApi voicesApi = new VoicesApiImpl(config);
        return new GoogleCloudTTS(synthesizeApi, voicesApi);
    }
}
