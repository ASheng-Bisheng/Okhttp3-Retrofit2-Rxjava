package cn.whzwl.xbs.network.utils;

/**
 * Created by Administrator on 2018/4/9.
 */

public class APIEncryptionUtils {


    /**
     * @param str 多参数
     * @return  Token
     */
    public static String TokenEncryption(String... str) {

        StringBuffer sb = new StringBuffer();
        sb.append("wlcf_");
        for (String value : str) {
            sb.append(MD5.MD51(value));
        }
        sb.append("_wlcf");
        return MD5.MD51(sb.toString());
    }

}
