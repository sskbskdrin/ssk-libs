package cn.sskbskdrin.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Created by sskbskdrin on 2021/1/19.
 *
 * @author sskbskdrin
 */
public final class HTTP {

    static {
        try {
            Class.forName("cn.sskbskdrin.http.okhttp.OkHttpRealRequest");
        } catch (ClassNotFoundException ignored) {
        }
    }

    public static Config getConfig() {
        return Config.INSTANCE;
    }

    public static <V> IRequest<V> url(String url, Class<V> tClass, String contentType) {
        return new HttpRequest<>(url, tClass, contentType);
    }

    public static <V> IRequest<V> url(String url, TypeToken<V> token, String contentType) {
        return new HttpRequest<>(url, token.getType(), contentType);
    }

    public static <V> IRequest<V> url(String url, IParseResponse<V> iParseResponse, String contentType) {
        return new HttpRequest<>(url, iParseResponse, contentType);
    }

    public static <V> IRequest<V> get(String url, TypeToken<V> token) {
        return url(url, token, IRequest.CONTENT_TYPE_GET);
    }

    public static <V> IRequest<V> get(String url, IParseResponse<V> iParseResponse) {
        return url(url, iParseResponse, IRequest.CONTENT_TYPE_GET);
    }

    public static <V> IRequest<V> post(String url, Class<V> tClass) {
        return url(url, tClass, IRequest.CONTENT_TYPE_FORM);
    }

    public static <V> IRequest<V> post(String url, TypeToken<V> token) {
        return url(url, token, IRequest.CONTENT_TYPE_FORM);
    }

    public static <V> IRequest<V> post(String url, IParseResponse<V> iParseResponse) {
        return url(url, iParseResponse, IRequest.CONTENT_TYPE_FORM);
    }

    public static <V> IRequest<V> postJson(String url, Class<V> tClass) {
        return url(url, tClass, IRequest.CONTENT_TYPE_JSON);
    }

    public static <V> IRequest<V> postJson(String url, TypeToken<V> token) {
        return url(url, token, IRequest.CONTENT_TYPE_JSON);
    }

    public static <V> IRequest<V> postJson(String url, IParseResponse<V> iParseResponse) {
        return url(url, iParseResponse, IRequest.CONTENT_TYPE_JSON);
    }

    public static <V> IRequest<V> postFile(String url, Class<V> tClass) {
        return url(url, tClass, IRequest.CONTENT_TYPE_MULTIPART);
    }

    public static <V> IRequest<V> postFile(String url, TypeToken<V> token) {
        return url(url, token, IRequest.CONTENT_TYPE_MULTIPART);
    }

    public static <V> IRequest<V> postFile(String url, IParseResponse<V> iParseResponse) {
        return url(url, iParseResponse, IRequest.CONTENT_TYPE_MULTIPART);
    }

    public static IRequest<File> download(String url, String filePath) {
        return new HttpRequest<>(url, new FileParse(filePath));
    }

    public static IParseResponse<String> STRING_PARSE_RESPONSE = new IParseResponse<String>() {
        @Override
        public IParseResult<String> parse(String tag, IResponse response, Type type, IRequestBody request) throws Throwable {
            return new Result<>(true, response.string());
        }
    };

    public static class FileParse implements IParseResponse<File> {

        private String filePath;

        private FileParse(String path) {
            filePath = path;
        }

        @Override
        public IParseResult<File> parse(String tag, IResponse response, Type type, IRequestBody request) throws Throwable {
            File file = new File(filePath);
            try {
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();

                FileOutputStream os = new FileOutputStream(file);
                long start = System.currentTimeMillis();

                long totalLen = response.getContentLength();
                InputStream is = response.byteStream();
                byte[] buf = new byte[1024 * 10];
                int ret, len = 0;
                if (request != null) {
                    request.publishProgress(0);
                }
                while ((ret = is.read(buf)) >= 0) {
                    os.write(buf, 0, ret);
                    len += ret;
                    if (System.currentTimeMillis() - start > 500) {
                        start = System.currentTimeMillis();
                        if (request != null) {
                            if (totalLen == 0) {
                                totalLen = -1;
                            }
                            request.publishProgress(len * 1f / totalLen);
                        }
                    }
                }
                if (request != null) {
                    request.publishProgress(1);
                }
            } catch (Throwable throwable) {
                if (file.exists()) {
                    file.delete();
                }
                throw throwable;
            } finally {
                response.close();
            }
            return new Result<>(true, file);
        }
    }
}
