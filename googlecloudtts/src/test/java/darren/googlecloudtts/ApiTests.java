package darren.googlecloudtts;

import org.junit.Assert;
import org.junit.Test;

import darren.googlecloudtts.api.SynthesizeApi;
import darren.googlecloudtts.api.SynthesizeApiImpl;
import darren.googlecloudtts.api.VoicesApi;
import darren.googlecloudtts.api.VoicesApiImpl;
import darren.googlecloudtts.parameter.AudioConfig;
import darren.googlecloudtts.parameter.AudioEncoding;
import darren.googlecloudtts.parameter.VoiceSelectionParams;
import darren.googlecloudtts.parameter.SynthesisInput;
import darren.googlecloudtts.request.SynthesizeRequest;
import darren.googlecloudtts.response.SynthesizeResponse;
import darren.googlecloudtts.response.VoicesResponse;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ApiTests {
    @Test
    public void testVoicesApi() {
        VoicesApi voicesApi = new VoicesApiImpl(TestAPIConfig.CONFIG);
        VoicesResponse response = voicesApi.get();
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getVoices());
    }

    @Test
    public void testSynthesizeApi() {
        SynthesizeRequest request = new SynthesizeRequest(
                new SynthesisInput("Hello"),
                new VoiceSelectionParams("en-GB", "en-GB-Wavenet-A"),
                new AudioConfig(AudioEncoding.MP3, 0.35f , 10f)
        );

        SynthesizeApi synthesizeApi = new SynthesizeApiImpl(TestAPIConfig.CONFIG);
        SynthesizeResponse response = synthesizeApi.get(request);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getAudioContent());
    }
}