package darren.googlecloudtts;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Author: Changemyminds.
 * Date: 2018/6/22.
 * Description:
 * Reference:
 */
public class VoiceList {
    private static final String TAG = VoiceList.class.getName();

    private List<IVoiceListener> mVoiceListeners = new ArrayList<>();
    private GoogleCloudAPIConfig mApiConfig;

    public VoiceList(GoogleCloudAPIConfig apiConfig) {
        mApiConfig = apiConfig;
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(mApiConfig.getVoicesEndpoint())
                        .addHeader(mApiConfig.getApiKeyHeader(), mApiConfig.getApiKey())
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        onSuccess(response.body().string());
                    } else {
                        throw new IOException(String.valueOf(response.code()));
                    }
                } catch (IOException IoEx) {
                    IoEx.printStackTrace();
                    onError(IoEx.getMessage());
                }
            }
        }).start();
    }

    public void addVoiceListener(IVoiceListener voiceListener) {
        mVoiceListeners.add(voiceListener);
    }

    public void removeVoiceListener(IVoiceListener voiceListener) {
        mVoiceListeners.remove(voiceListener);
    }

    private void onSuccess(String text) {
        for (IVoiceListener voiceListener : mVoiceListeners) {
            voiceListener.onResponse(text);

            try {
                voiceListener.onReceive(getVoiceCollection(text));
            } catch (Exception e) {
                onError(e.getMessage());
            }
        }
    }

    private void onError(String error) {
        for (IVoiceListener voiceListener : mVoiceListeners) {
            voiceListener.onFailure(error);
        }
    }

    private VoiceCollection getVoiceCollection(String text) {
        JsonElement jsonElement = new JsonParser().parse(text);
        if (jsonElement == null || jsonElement.getAsJsonObject() == null ||
                jsonElement.getAsJsonObject().get("voices").getAsJsonArray() == null) {
            Log.e(TAG, "get error json");
            throw new NullPointerException("get error json");
        }

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("voices").getAsJsonArray();

        VoiceCollection collection = new VoiceCollection();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonArray jsonArrayLanguage = jsonArray.get(i)
                    .getAsJsonObject().get("languageCodes")
                    .getAsJsonArray();

            if (jsonArrayLanguage.get(0) != null) {
                String language = jsonArrayLanguage.get(0).toString().replace("\"", "");
                String name = jsonArray.get(i).getAsJsonObject().get("name").toString().replace("\"", "");
                String ssmlGender = jsonArray.get(i).getAsJsonObject().get("ssmlGender").toString().replace("\"", "");
                ESSMLlVoiceGender essmLlVoiceGender = ESSMLlVoiceGender.convert(ssmlGender);
                int naturalSampleRateHertz = jsonArray.get(i).getAsJsonObject().get("naturalSampleRateHertz").getAsInt();

                GoogleCloudVoice gcpVoice = new GoogleCloudVoice(language, name, essmLlVoiceGender, naturalSampleRateHertz);
                collection.add(language, gcpVoice);
            }
        }

        return collection;
    }

    public interface IVoiceListener {
        void onResponse(String jsonText);

        void onReceive(final VoiceCollection collection);

        void onFailure(String error);
    }
}
