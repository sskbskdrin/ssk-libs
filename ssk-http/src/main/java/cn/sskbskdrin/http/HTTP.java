package cn.sskbskdrin.http;

/**
 * Created by keayuan on 2019-11-29.
 *
 * @author keayuan
 */
public final class HTTP {
    private HTTP() {}

    public static Config globalConfig() {
        return Config.INSTANCE;
    }

    public static IRequest<String> url(String url) {
        return new HttpRequest<>(url, String.class);
    }

    public static <V> IRequest<V> url(String url, Class<V> tClass) {
        return new HttpRequest<>(url, tClass);
    }

    public static <V, T> IRequest<V> url(String url, TypeToken<T> token) {
        return new HttpRequest<>(url, token.getType());
    }

    public static <V> IRequest<V> url(String url, IParseResponse<V> iParseResponse) {
        return new HttpRequest<>(url, iParseResponse);
    }
}
