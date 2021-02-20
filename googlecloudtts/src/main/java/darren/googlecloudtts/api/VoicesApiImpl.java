package darren.googlecloudtts.api;

import android.util.Log;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import darren.googlecloudtts.GoogleCloudAPIConfig;
import darren.googlecloudtts.exception.ApiException;
import darren.googlecloudtts.exception.ApiResponseFailException;
import darren.googlecloudtts.response.VoicesResponse;
import darren.googlecloudtts.util.GsonUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Changemyminds.
 * Date: 2020/12/17.
 * Description:
 * Reference:
 */
public class VoicesApiImpl implements VoicesApi {
    private GoogleCloudAPIConfig mApiConfig;

    public VoicesApiImpl(GoogleCloudAPIConfig apiConfig) {
        mApiConfig = apiConfig;
    }

    @Override
    public VoicesResponse get() {
        try {
            Response response = makeRequest(mApiConfig);
            String bodyJson = Objects.requireNonNull(response.body()).string();

            if (response.code() != 200) {
                throw new ApiResponseFailException(bodyJson);
            }

            return GsonUtil.toObject(bodyJson, VoicesResponse.class);

        } catch (Exception e) {
            throw new ApiException(e);
        }
    }

    private Response makeRequest(GoogleCloudAPIConfig apiConfig) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiConfig.getVoicesEndpoint())
                .addHeader(apiConfig.getApiKeyHeader(), apiConfig.getApiKey())
                .build();

        return okHttpClient.newCall(request).execute();
    }
}
