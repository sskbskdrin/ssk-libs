package cn.sskbskdrin.http;

/**
 * Created by keayuan on 2020/4/7.
 *
 * @author keayuan
 */
public interface IResponse<T> {
    /**
     * 返回byte数据
     *
     * @return 数据数组
     */
    byte[] bytes();

    /**
     * 返回数据转字符串
     *
     * @return 返回的字符串
     */
    String string();
}
