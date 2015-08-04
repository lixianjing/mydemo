package com.xian.myapp.utils;

import android.location.Location;
import android.text.TextUtils;
import com.xian.myapp.MyApplication;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huangshan1
 */
public class StringUtil {

    /**
     * Native to ascii string. It's same as execut native2ascii.exe.
     *
     * @param str native string
     * @return ascii string
     */
    public static String native2Ascii(String str) {
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            sb.append(char2Ascii(chars[i]));
        }
        return sb.toString();
    }

    /**
     * Native character to ascii string.
     *
     * @param c native character
     * @return ascii string
     */
    private static String char2Ascii(char c) {
        if (c > 255) {
            StringBuilder sb = new StringBuilder();
            sb.append("\\u");
            int code = (c >> 8);
            String tmp = Integer.toHexString(code);
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
            code = (c & 0xFF);
            tmp = Integer.toHexString(code);
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
            return sb.toString();
        } else {
            return Character.toString(c);
        }
    }

    /**
     * Ascii to native string. It's same as execut native2ascii.exe -reverse.
     *
     * @param str ascii string
     * @return native string
     */
    public static String ascii2Native(String str) {
        StringBuilder sb = new StringBuilder();
        int begin = 0;
        int index = str.indexOf("\\u");
        while (index != -1) {
            sb.append(str.substring(begin, index));
            sb.append(ascii2Char(str.substring(index, index + 6)));
            begin = index + 6;
            index = str.indexOf("\\u", begin);
        }
        sb.append(str.substring(begin));
        return sb.toString();
    }

    /**
     * Ascii to native character.
     *
     * @param str ascii string
     * @return native character
     */
    private static char ascii2Char(String str) {
        if (str.length() != 6) {
            throw new IllegalArgumentException("Ascii string of a native character must be 6 character.");
        }
        if (!"\\u".equals(str.substring(0, 2))) {
            throw new IllegalArgumentException("Ascii string of a native character must start with \"\\u\".");
        }
        String tmp = str.substring(2, 4);
        int code = Integer.parseInt(tmp, 16) << 8;
        tmp = str.substring(4, 6);
        code += Integer.parseInt(tmp, 16);
        return (char) code;
    }

    public static String getImageUrlByArg(String imageUrl, int width, int height, boolean cut) {
        if (imageUrl == null || imageUrl.length() == 0) {
            return "";
        }

        String newImageUrl = "";
        Pattern pattern = Pattern.compile("_\\d*-\\d*.*_");
        Matcher matcher = pattern.matcher(imageUrl);
        if (matcher.find()) {
            String arg = "_" + width + "-" + height + (cut ? "c_" : "_");
            newImageUrl = matcher.replaceFirst(arg);
        }
        return newImageUrl;
    }

    public static String getImageUrlByArgAndQuality(String imageUrl, int width, int height, boolean cut, int quality) {
        if (imageUrl == null || imageUrl.length() == 0) {
            return "";
        }

        String newImageUrl = "";
        Pattern pattern = Pattern.compile("_\\d*-\\d*.*_\\d*-");
        Matcher matcher = pattern.matcher(imageUrl);
        if (matcher.find()) {
            String arg = "_" + width + "-" + height + (cut ? "c_" : "_") + quality + "-";
            newImageUrl = matcher.replaceFirst(arg);
        }
        return newImageUrl;
    }

    /**
     * 获得新的图片URL
     */
    public static String getNewImageUrl(String imageUrl, int width, int height) {
        if (imageUrl == null || imageUrl.length() == 0) {
            return "";
        }

        String newImageUrl = "";
        Pattern pattern = Pattern.compile("_\\d*-\\d*.*_");
        Matcher matcher = pattern.matcher(imageUrl);
        if (matcher.find()) {
            String get = matcher.group();
            get = get.replaceFirst("(?<=_)([0-9]*)", "" + width).replaceFirst("(?<=-)([0-9]*)", "" + height);
            newImageUrl = matcher.replaceFirst(get);
        } else {
            int index = imageUrl.lastIndexOf('.');
            if (index > 0) {
                String postfix = imageUrl.substring(index);
                String str = imageUrl.substring(0, index);
                newImageUrl = str + "_" + width + "-" + height + "_8-15" + postfix;
            } else {
                newImageUrl = "";
            }
        }
        return newImageUrl;
    }

    /**
     * 转换 url, 返回宽为 120 像素的缩略图的 url
     */
    public static String toThumbUrl(String imageUrl) {
        final String url = imageUrl;
        try {
            if (!TextUtils.isEmpty(imageUrl)) {
                String[] datas = url.split("_");
                if (datas != null && datas.length == 3) {
                    String numberFormat = datas[1];
                    if (!TextUtils.isEmpty(numberFormat)) {
                        String[] numbers = numberFormat.split("-");
                        if (numbers != null && numbers.length == 2) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(datas[0]);
                            sb.append("_");
                            sb.append("120" + "-" + numbers[1]);
                            sb.append("_");
                            sb.append(datas[2]);
                            return sb.toString();
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return url;
    }

    public static String toSizeImgUrl(String imageUrl, int width, int height) {
        String returnUrl = imageUrl;
        if (!TextUtils.isEmpty(imageUrl)) {
            int idx = imageUrl.indexOf("_");
            if (idx != -1) {
                String[] datas = imageUrl.split("_");
                if (datas != null && datas.length > 0) {
                    String numberFormat = datas[1];
                    if (!TextUtils.isEmpty(numberFormat)) {
                        String[] numbers = numberFormat.split("-");
                        if (numbers != null && numbers.length == 2) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(datas[0]);
                            sb.append("_");
                            sb.append(width + "-" + height);
                            sb.append("_");

                            if (NetworkUtil.isWIFIAvailable(MyApplication.getContext())) {
                                String pathSux = datas[2];
                                String[] suxs = pathSux.split("-");
                                sb.append(6);
                                sb.append("-");
                                sb.append(suxs[1]);
                            } else {
                                String pathSux = datas[2];
                                String[] suxs = pathSux.split("-");
                                sb.append(5);
                                sb.append("-");
                                sb.append(suxs[1]);
                            }

                            return sb.toString();
                        }
                    }
                }
            }
        }
        return returnUrl;
    }

    /**
     * 根据两个地方的经度和纬度算出之间的距离
     */
    public static String getDistanceLatlng(double myLatitude, double myLongitude, double latitude, double longitude) {
        String result = null;
        float[] results = new float[1];
        Location.distanceBetween(myLatitude, myLongitude, latitude, longitude, results);
        int distance = (int) results[0];
        if (distance > 100) {
            double d = ((double) distance) / 1000;
            String pre = new java.text.DecimalFormat("#.#").format(d);
            result = "距离" + pre + "公里"; // km
        } else {
            result = "本地居民";
        }
        return result;
    }

    public static String MD5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(data.getBytes());
            return bytesToHexString(bytes);
        } catch (NoSuchAlgorithmException e) {
        }
        return data;
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static int parseInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static long parseLong(String str, long defaultValue) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将小数点转化为百分比
     */
    public static String dotToPercent(String value) {
        String percent = "";
        float fValue;
        try {
            fValue = Float.parseFloat(value);
            percent = fValue * 100 + "%";
        } catch (Exception e) {
            percent = "0%";
        }
        return percent;
    }

    public static String urlEncode(String input) {
        try {
            return URLEncoder.encode(input, "UTF-8");
        } catch (Exception e) {
        }
        return null;
    }
}
