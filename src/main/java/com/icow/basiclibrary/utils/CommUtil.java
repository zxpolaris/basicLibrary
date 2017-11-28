package com.icow.basiclibrary.utils;

import android.app.Service;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 *
 * @author Xun.Zhang
 * @version 1.0.0
 */
public class CommUtil {

    public static <T> List<T> arrayToList(T[] t) {
        List<T> list = Arrays.asList(t);
        return list;
    }

    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str) || TextUtils.isEmpty(str.trim());
    }

    public static boolean isEmpty(byte[] bytes) {
        return null == bytes || bytes.length <= 0;
    }

    public static <T> boolean isEmpty(T[] t) {
        return t == null || t.length <= 0;
    }

    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.size() <= 0 ? true : false;
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.size() <= 0 ? true : false;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNumber(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][34578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
    }

    public static String getCombinedDeviceID(Context context) {
        try {
            TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            return TelephonyMgr.getDeviceId();
        } catch (SecurityException e) {
            return "";
        }
    }

    /**
     * 验证密码格式（6-16位数字+字母）
     */
    public static boolean isPwdValidate(String pwd) {
        /*
        分开来注释一下：
        ^ 匹配一行的开头位置
        (?![0-9]+$) 预测该位置后面不全是数字
        (?![a-zA-Z]+$) 预测该位置后面不全是字母
        [0-9A-Za-z] {6,10} 由6-10位数字或这字母组成
        $ 匹配行结尾位置
        */
        String telRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        if (TextUtils.isEmpty(pwd)) {
            return false;
        } else {
            return pwd.matches(telRegex);
        }
    }

    /**
     * 部分隐藏
     */
    public static String hidePartString(String mobile) {
        StringBuilder sb = new StringBuilder();
        if (!CommUtil.isEmpty(mobile)) {
            for (int i = 0; i < mobile.length(); i++) {
                char c = mobile.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }


    private static final String HTTP_PREFIX = "http://";

    public static String getStandardUrl(String content) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!CommUtil.isEmpty(content) && !content.startsWith(HTTP_PREFIX)) {
            stringBuilder.append(HTTP_PREFIX);
        }
        stringBuilder.append(content);
        return stringBuilder.toString();
    }

    public static String toStringBySeparator(List<String> list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    /**
     * 提取小括号中的内容
     *
     * @param content
     * @return
     */
    public static String getParenthesesContent(String content) {
        Pattern pattern = Pattern.compile("(?<=\\()(.+?)(?=\\))");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "";
        }
    }

    public static <T> T[] listToArrays(List<T> list) {
        return (T[]) list.toArray(new String[list.size()]);
    }

    public static String replaceTransferChar(String originalText) {
        if (!isEmpty(originalText)) {
            return originalText.replaceAll("\\\\", "");
        } else {
            return "";
        }
    }

    /**
     * 判断是否为最新版本方法 将版本号根据.切分为int数组 比较
     *
     * @param localVersion  本地版本号
     * @param onlineVersion 线上版本号
     * @return
     */
    public static boolean isAppNewVersion(String localVersion, String onlineVersion) {
        if (localVersion.equals(onlineVersion)) {
            return false;
        }
        if (isEmpty(localVersion)) {
            return false;
        }
        if (isEmpty(onlineVersion)) {
            return false;
        }
        String[] localArray = localVersion.split("\\.");
        String[] onlineArray = onlineVersion.split("\\.");
        int length = localArray.length < onlineArray.length ? localArray.length : onlineArray.length;
        try {
            for (int i = 0; i < length; i++) {
                if (Integer.parseInt(onlineArray[i]) > Integer.parseInt(localArray[i])) {
                    return true;
                } else if (Integer.parseInt(onlineArray[i]) < Integer.parseInt(localArray[i])) {
                    return false;
                }
                // 相等 比较下一组值
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static String toDoubleString(double price) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(price);
    }

    public static boolean hasSpecialChar(String character) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(character);
        return m.find();
    }

    /**
     * 验证搜索格式（汉字，字母，数字）
     */
    public static boolean isSearchValidate(String mEdit) {
        String telRegex = "[\\u4e00-\\u9fa5_a-zA-Z0-9_]{1,100}";
        if (TextUtils.isEmpty(mEdit)) {
            return false;
        } else {
            return mEdit.matches(telRegex);
        }
    }

    /**
     * 得到字符串距离
     *
     * @param distance 米
     * @return
     */
    public static final String getDisplayDistance(int distance) {
        if (distance < 1000) {
            return distance + "m";
        } else {
            String distanceKm = new BigDecimal(distance).divide(new BigDecimal(1000), 1, BigDecimal.ROUND_HALF_UP).toString();
            return distanceKm + "km";
        }
    }

    public static final String getDisplayDistanceInt(int distance) {
        if (distance < 1000) {
            return distance + "m";
        } else {
            int distanceKm = distance/1000;
            return distanceKm + "km";
        }
    }

    public static String getScan(int scanCnt) {
        if(scanCnt < 1000){
            return scanCnt + "次";
        }else if(scanCnt < 10000){
            String scanCntStr = new BigDecimal(scanCnt).divide(new BigDecimal(1000), 1, BigDecimal.ROUND_HALF_UP).toString();
            return scanCntStr + "千";
        }else{
            String scanCntStr = new BigDecimal(scanCnt).divide(new BigDecimal(1000), 1, BigDecimal.ROUND_HALF_UP).toString();
            return scanCntStr + "万";
        }
    }

    public static Matcher getBizcategoryMatcher(CharSequence matchStr) {
        return BIZCATEGORY_PATTERN.matcher(matchStr);
    }

    public static final Pattern BIZCATEGORY_PATTERN = Pattern.compile("#[\\u4e00-\\u9fa5]+#");

    /**
     * unicode编码，判断是否为汉字
     */
    public static String regEx = "[\\u4e00-\\u9fa5]"; //]

    public static boolean matchChinese(String word) {
        return word.matches(regEx);
    }

    /**
     * 截取字符长度，区分中英文,中文为2个，英文为1个
     *
     * @param source 字符串内容
     * @param len    截取长度
     * @return
     */
    public static String subStringWithCAndE(String source, int len, String ellip) {
        int max = len * 2;
        boolean isAndEllip = false;
        if (TextUtils.isEmpty(source) || len <= 0)
            return "";
        StringBuffer sb = new StringBuffer();
        int sum = 0;
        char[] chars = source.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (sum >= max) {
                isAndEllip = true;
                break;
            }
            char bt = chars[i];
            String s = String.valueOf(bt);
            boolean isChinese = matchChinese(s);
            if (isChinese) {
                sum += 2;
            } else {
                sum += 1;
            }
            sb.append(s);
        }
        if (sum == max + 1) {//限制多一个表示最后一个为汉字，需要去掉
            sb.delete(sb.length() - 1, sb.length());
        }
        if (isAndEllip && !TextUtils.isEmpty(ellip)) {
            sb.append(ellip);
        }
        return sb.toString();
    }

    public static boolean checkStringLengthWithCAndE(String source, int len){
        int max = len * 2;
        int sum = 0;
        boolean isAndEllip = false;
        char[] chars = source.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (sum >= max) {
                isAndEllip = true;
                return true;
            }
            char bt = chars[i];
            String s = String.valueOf(bt);
            boolean isChinese = matchChinese(s);
            if (isChinese) {
                sum += 2;
            } else {
                sum += 1;
            }
        }
        return isAndEllip;
    }

    public static String listToSpliteString(List<String> stringList, String spliteSymbol) {
        StringBuilder sb = new StringBuilder();
        if (!isEmpty(stringList)) {
            for (int i = 0; i < stringList.size(); i++) {
                sb.append(stringList.get(i));
                if (i < stringList.size() - 1) {
                    sb.append(spliteSymbol);
                }
            }
        }
        return sb.toString();
    }

    private static final String PATTERN = "^((?=.*?\\d)(?=.*?[A-Za-z])"
            + "|(?=.*?\\d)(?=.*?[_])"
            + "|(?=.*?[A-Za-z])(?=.*?[_])"
            + "|(?=.*?[A-Z])(?=.*?[a-z]))"
            + "[\\dA-Za-z_]{6,16}$";

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String toStringFromHalfString(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}
