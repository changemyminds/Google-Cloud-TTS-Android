package darren.googlecloudtts;

import org.junit.Assert;
import org.junit.Test;

import darren.googlecloudtts.model.VoicesList;
import darren.googlecloudtts.parameter.VoiceSelectionParams;

/**
 * Author: Changemyminds.
 * Date: 2021/2/20.
 * Description: Test the github issue.
 * https://github.com/changemyminds/Google-Cloud-TTS-Android/issues
 * Reference:
 */
public class IssueTests {
    // issue#5 fr-FR-Wavenet-E
    @Test
    public void testNotFoundVoiceList() {
        GoogleCloudTTS cloudTTS = GoogleCloudTTSFactory.create(TestAPIConfig.CONFIG);
        VoicesList voicesList = cloudTTS.load();
        VoiceSelectionParams params = voicesList.getGCPVoice("fr-FR", "fr-FR-Wavenet-E");
        Assert.assertNotNull(params);
    }
}
