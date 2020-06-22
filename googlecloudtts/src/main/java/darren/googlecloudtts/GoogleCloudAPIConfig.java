package darren.googlecloudtts;

/**
 * Author: Changemyminds.
 * Date: 2020/6/21.
 * Description:
 * Reference:
 */
public class GoogleCloudAPIConfig {
    private String mApiKey;
    private String mApiKeyHeader;
    private String mSynthesizeEndpoint;
    private String mVoicesEndpoint;

    public GoogleCloudAPIConfig() {
        mApiKeyHeader = "X-Goog-Api-Key";
        mSynthesizeEndpoint = "https://texttospeech.googleapis.com/v1beta1/text:synthesize";
        mVoicesEndpoint = "https://texttospeech.googleapis.com/v1beta1/voices";
    }

    public GoogleCloudAPIConfig(String apiKey) {
        this();
        mApiKey = apiKey;
    }

    public void setApiKey(String apiKey) {
        mApiKey = apiKey;
    }

    public void setApiKeyHeader(String apiKeyHeader) {
        mApiKeyHeader = apiKeyHeader;
    }

    public void setSynthesizeEndpoint(String synthesizeEndpoint) {
        mSynthesizeEndpoint = synthesizeEndpoint;
    }

    public void setVoicesEndpoint(String voicesEndpoint) {
        mVoicesEndpoint = voicesEndpoint;
    }

    public String getApiKey() {
        return mApiKey;
    }

    public String getApiKeyHeader() {
        return mApiKeyHeader;
    }

    public String getSynthesizeEndpoint() {
        return mSynthesizeEndpoint;
    }

    public String getVoicesEndpoint() {
        return mVoicesEndpoint;
    }
}
