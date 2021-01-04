package darren.googlecloudtts.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

/**
 * Author: Changemyminds.
 * Date: 2020/12/17.
 * Description:
 * Reference:
 */
public class GsonUtil {
    public static <T> T toObject(String json, Class<T> tClass) {
        return getGson().fromJson(json, tClass);
    }

    public static String toJson(Object object) {
        return getGson().toJson(object);
    }

    public static <T> List<T> toList(String json, Class<T> tClass) {
        return getGson().fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }

    public static <T> Map<String, T> toMap(String json) {
        return getGson().fromJson(json, new TypeToken<Map<String, T>>() {
        }.getType());
    }

    private static Gson getGson() {
        return new Gson();
    }
}
