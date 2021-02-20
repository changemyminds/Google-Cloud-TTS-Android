package darren.googlecloudtts.api;

import org.json.JSONObject;
import java.io.IOException;
import java.util.Objects;

import darren.googlecloudtts.GoogleCloudAPIConfig;
import darren.googlecloudtts.exception.ApiException;
import darren.googlecloudtts.exception.ApiResponseFailException;
import darren.googlecloudtts.request.SynthesizeRequest;
import darren.googlecloudtts.response.SynthesizeResponse;
import darren.googlecloudtts.util.GsonUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author: Changemyminds.
 * Date: 2020/12/17.
 * Description:
 * Reference:
 */
public class SynthesizeApiImpl implements SynthesizeApi {
    private static final String CONTENT_TYPE = "application/json; charset=utf-8";

    private GoogleCloudAPIConfig mApiConfig;

    public SynthesizeApiImpl(GoogleCloudAPIConfig apiConfig) {
        mApiConfig = apiConfig;
    }

    @Override
    public SynthesizeResponse get(SynthesizeRequest request) {
        try {
            Response response = makeRequest(request, mApiConfig);
            String bodyJson = Objects.requireNonNull(response.body()).string();

            if (response.code() != 200) {
                throw new ApiResponseFailException(bodyJson);
            }

            return GsonUtil.toObject(bodyJson, SynthesizeResponse.class);
        } catch (Exception e) {
            throw new ApiException(e);
        }
    }

    private Response makeRequest(SynthesizeRequest requestBody, GoogleCloudAPIConfig apiConfig) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse(CONTENT_TYPE),
                GsonUtil.toJson(requestBody));
        Request request = new Request.Builder()
                .url(apiConfig.getSynthesizeEndpoint())
                .addHeader(apiConfig.getApiKeyHeader(), apiConfig.getApiKey())
                .addHeader("Content-Type", CONTENT_TYPE)
                .post(body)
                .build();

        return okHttpClient.newCall(request).execute();
    }
}
