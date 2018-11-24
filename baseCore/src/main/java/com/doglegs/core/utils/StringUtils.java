package com.doglegs.core.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    // 字母Z使用了两个标签，这里有２７个值
    // i, u, v都不做声母, 跟随前面的字母
    private final static char[] CHAR_TABLE = {'啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈', '击', '喀', '垃', '妈', '拿',
            '哦', '啪', '期', '然', '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', '座'};

    public final static char[] ALPHA_TABLE = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    // 字母对应的区位码值
    public final static int CHAR_CODE[] = {1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594,
            2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858,
            4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590};

    // 存放所有国标二级汉字读音,用于次常用汉字
    public final static String SECONDARY_TABLE = "CJWGNSPGCGNE[Y[BTYYZDXYKYGT[JNNJQMBSGZSCYJSYY"
            +
            "[PGKBZGY[YWJKGKLJYWKPJQHY[W[DZLSGMRYPYWWCCKZNKYYGTTNJJNYKKZYTCJNMCYLQLYPYQFQRPZSLWBTGKJFYXJWZLTBNCXJJJJTXDTTSQZYCDXXHGCK"
            +
            "[PHFFSS[YBGXLPPBYLL[HLXS[ZM[JHSOJNGHDZQYKLGJHSGQZHXQGKEZZWYSCSCJXYEYXADZPMDSSMZJZQJYZC[J"
            +
            "[WQJBYZPXGZNZCPWHKXHQKMWFBPBYDTJZZKQHYLYGXFPTYJYYZPSZLFCHMQSHGMXXSXJ["
            +
            "[DCSBBQBEFSJYHXWGZKPYLQBGLDLCCTNMAYDDKSSNGYCSGXLYZAYBNPTSDKDYLHGYMYLCXPY"
            +
            "[JNDQJWXQXFYYFJLEJPZRXCCQWQQSBNKYMGPLBMJRQCFLNYMYQMSQYRBCJTHZTQFRXQHXMJJCJLXQGJMSHZKBSWYEMYLTXFSYDSWLYCJQXSJNQBSCTYHBFTDCYZDJWY"
            +
            "GHQFRXWCKQKXEBPTLPXJZSRMEBWHJLBJSLYYSMDXLCLQKXLHXJRZJMFQHXHWYWSBHTRXXGLHQHFNM[YKLDYXZPYLGG[MTCFPAJJZYLJTYANJGBJPLQGDZYQY"
            +
            "AXBKYSECJSZNSLYZHSXLZCGHPXZHZNYTDSBCJKDLZAYFMYDLEBBGQYZKXGLDNDNYSKJSHDLYXBCGHXYPKDJMMZNGMMCLGWZSZXZJFZNMLZZTHCSYDBDLLSCDD"
            +
            "NLKJYKJSYCJLKWHQASDKNHCSGANHDAASHTCPLCPQYBSDMPJLPZJOQLCDHJJYSPRCHN[NNLHLYYQYHWZPTCZGWWMZFFJQQQQYXACLBHKDJXDGMMYDJXZLLSYGX"
            +
            "GKJRYWZWYCLZMSSJZLDBYD[FCXYHLXCHYZJQ[[QAGMNYXPFRKSSBJLYXYSYGLNSCMHZWWMNZJJLXXHCHSY[[TTXRYCYXBYHCSMXJSZNPWGPXXTAYBGAJCXLY"
            +
            "[DCCWZOCWKCCSBNHCPDYZNFCYYTYCKXKYBSQKKYTQQXFCWCHCYKELZQBSQYJQCCLMTHSYWHMKTLKJLYCXWHEQQHTQH[PQ"
            +
            "[QSCFYMNDMGBWHWLGSLLYSDLMLXPTHMJHWLJZYHZJXHTXJLHXRSWLWZJCBXMHZQXSDZPMGFCSGLSXYMJSHXPJXWMYQKSMYPLRTHBXFTPMHYXLCHLHLZY"
            +
            "LXGSSSSTCLSLDCLRPBHZHXYYFHB[GDMYCNQQWLQHJJ[YWJZYEJJDHPBLQXTQKWHLCHQXAGTLXLJXMSL[HTZKZJECXJCJNMFBY[SFYWYBJZGNYSDZSQYRSLJ"
            +
            "PCLPWXSDWEJBJCBCNAYTWGMPAPCLYQPCLZXSBNMSGGFNZJJBZSFZYNDXHPLQKZCZWALSBCCJX[YZGWKYPSGXFZFCDKHJGXDLQFSGDSLQWZKXTMHSBGZMJZRGLYJ"
            +
            "BPMLMSXLZJQQHZYJCZYDJWBMYKLDDPMJEGXYHYLXHLQYQHKYCWCJMYYXNATJHYCCXZPCQLBZWWYTWBQCMLPMYRJCCCXFPZNZZLJPLXXYZTZLGDLDCKLYRZZGQTG"
            +
            "JHHGJLJAXFGFJZSLCFDQZLCLGJDJCSNZLLJPJQDCCLCJXMYZFTSXGCGSBRZXJQQCTZHGYQTJQQLZXJYLYLBCYAMCSTYLPDJBYREGKLZYZHLYSZQLZNWCZCLLWJQ"
            +
            "JJJKDGJZOLBBZPPGLGHTGZXYGHZMYCNQSYCYHBHGXKAMTXYXNBSKYZZGJZLQJDFCJXDYGJQJJPMGWGJJJPKQSBGBMMCJSSCLPQPDXCDYYKY[CJDDYYGYWRHJRTGZ"
            +
            "NYQLDKLJSZZGZQZJGDYKSHPZMTLCPWNJAFYZDJCNMWESCYGLBTZCGMSSLLYXQSXSBSJSBBSGGHFJLYPMZJNLYYWDQSHZXTYYWHMZYHYWDBXBTLMSYYYFSXJC[DXX"
            +
            "LHJHF[SXZQHFZMZCZTQCXZXRTTDJHNNYZQQMNQDMMG[YDXMJGDHCDYZBFFALLZTDLTFXMXQZDNGWQDBDCZJDXBZGSQQDDJCMBKZFFXMKDMDSYYSZCMLJDSYNSBRS"
            +
            "KMKMPCKLGDBQTFZSWTFGGLYPLLJZHGJ[GYPZLTCSMCNBTJBQFKTHBYZGKPBBYMTDSSXTBNPDKLEYCJNYDDYKZDDHQHSDZSCTARLLTKZLGECLLKJLQJAQNBDKKGHP"
            +
            "JTZQKSECSHALQFMMGJNLYJBBTMLYZXDCJPLDLPCQDHZYCBZSCZBZMSLJFLKRZJSNFRGJHXPDHYJYBZGDLQCSEZGXLBLGYXTWMABCHECMWYJYZLLJJYHLG[DJLSLY"
            +
            "GKDZPZXJYYZLWCXSZFGWYYDLYHCLJSCMBJHBLYZLYCBLYDPDQYSXQZBYTDKYXJY[CNRJMPDJGKLCLJBCTBJDDBBLBLCZQRPPXJCJLZCSHLTOLJNMDDDLNGKAQHQH"
            +
            "JGYKHEZNMSHRP[QQJCHGMFPRXHJGDYCHGHLYRZQLCYQJNZSQTKQJYMSZSWLCFQQQXYFGGYPTQWLMCRNFKKFSYYLQBMQAMMMYXCTPSHCPTXXZZSMPHPSHMCLMLDQF"
            +
            "YQXSZYYDYJZZHQPDSZGLSTJBCKBXYQZJSGPSXQZQZRQTBDKYXZKHHGFLBCSMDLDGDZDBLZYYCXNNCSYBZBFGLZZXSWMSCCMQNJQSBDQSJTXXMBLTXZCLZSHZCXRQ"
            +
            "JGJYLXZFJPHYMZQQYDFQJJLZZNZJCDGZYGCTXMZYSCTLKPHTXHTLBJXJLXSCDQXCBBTJFQZFSLTJBTKQBXXJJLJCHCZDBZJDCZJDCPRNPQCJPFCZLCLZXZDMXMPH"
            +
            "JSGZGSZZQLYLWTJPFSYASMCJBTZKYCWMYTCSJJLJCQLWZMALBXYFBPNLSFHTGJWEJJXXGLLJSTGSHJQLZFKCGNNNSZFDEQFHBSAQTGYLBXMMYGSZLDYDQMJJRGBJ"
            +
            "TKGDHGKBLQKBDMBYLXWCXYTTYBKMRTJZXQJBHLMHMJJZMQASLDCYXYQDLQCAFYWYXQHZ";

    /**
     * 初始化 拼音声母 对照表
     */
    private static int[] table = new int[27];

    static {
        for (int i = 0; i < 27; ++i) {
            table[i] = gbValue(CHAR_TABLE[i]);
        }
    }

    // 主函数,输入字符,得到他的声母,
    // 英文字母返回对应的大写字母
    // 其他非简体汉字返回 '0'

    public static char char2Alpha(char ch) {
        if (ch >= 'a' && ch <= 'z')
            return (char) (ch - 'a' + 'A');
        if (ch >= 'A' && ch <= 'Z')
            return ch;
        int gb = gbValue(ch);
        if (gb < table[0]) {
            return '0';
        }
        // 超出常用汉字范围，归为次常用汉字（即二级汉字）
        if (gb > table[table.length - 1]) {
            return charSecondaryAlpha(ch);
        }
        int i;
        for (i = 0; i < 26; ++i) {
            if (match(i, gb))
                break;
        }
        if (i >= 26)
            return '0';
        else
            return ALPHA_TABLE[i];
    }

    // 根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串
    public static String string2Alpha(String SourceStr) {

        String Result = "";
        int StrLength = SourceStr.length();
        int i;
        try {
            for (i = 0; i < StrLength; i++) {
                Result += char2Alpha(SourceStr.charAt(i));
            }
        } catch (Exception e) {
            Result = "";
        }
        return Result;
    }

    private static boolean match(int i, int gb) {

        if (gb < table[i])
            return false;
        int j = i + 1;

        // 字母Z使用了两个标签
        while (j < 26 && (table[j] == table[i]))
            ++j;
        if (j == 26)
            return gb <= table[j];
        else
            return gb < table[j];
    }

    // 取出汉字的编码
    private static int gbValue(char ch) {

        String str = new String();
        str += ch;
        try {
            byte[] bytes = str.getBytes("GBK");
            if (bytes.length < 2)
                return 0;
            return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 取出汉字的编码,此方法提示次常用汉字的编码获取
     *
     * @param ch
     * @return
     */
    private static char charSecondaryAlpha(char ch) {
        // 二级字库偏移量
        int ioffset = 0;
        String str = new String();
        str += ch;
        try {
            byte[] bytes = str.getBytes("GBK");
            if (bytes.length < 2) {
                return '0';

            } else {
                int areaCode = 96 + bytes[0]; // 区码
                int posCode = 96 + bytes[1]; // 位码
                int charCode = areaCode * 100 + posCode;
                if (charCode > (CHAR_CODE[0] - 1) && (charCode < CHAR_CODE[CHAR_CODE.length - 1])) {
                    for (int index = 0; index < CHAR_CODE.length; index++) {
                        if (charCode < CHAR_CODE[index]) {
                            return ALPHA_TABLE[index - 1];

                        }
                    }

                } else {
                    ioffset = (areaCode - 56) * 94 + posCode - 1;
                    if (ioffset >= 0 && ioffset <= 3007) {
                        return SECONDARY_TABLE.substring(ioffset, ioffset + 1).charAt(0);

                    }
                }
            }

            return '0';

        } catch (Exception e) {
            return '0';
        }
    }

    /**
     * 算出这个字符的BGCode值
     *
     * @param c
     * @return int
     */
    private static int getGBCode(char c) {
        byte[] bytes = null;
        int a = 0, b = 0;
        try {
            bytes = String.valueOf(c).getBytes("gbk");
            if (bytes.length == 1) {
                return bytes[0];
            }
            a = bytes[0] - 0xA0 + 256;
            b = bytes[1] - 0xA0 + 256;
            return a * 100 + b;
        } catch (UnsupportedEncodingException e) {
        }

        return a * 100 + b;
    }

    /**
     * 截短字符串
     *
     * @param str
     * @param length
     * @return
     */
    public static String subString(String str, int length) {
        if (str != null && str.length() > length) {
            str = str.substring(0, length);
        }
        return str;
    }

    /**
     * 连接字符串
     *
     * @param charSequences
     * @return
     */
    public static final String combineText(CharSequence... charSequences) {
        StringBuffer sb = new StringBuffer();
        for (CharSequence text : charSequences) {
            if (text == null) {
                text = "";
            }
            sb.append(text);
        }

        return sb.toString();
    }
//
//    /**
//     * 设置关键字的颜色,加粗
//     *
//     * @param text
//     * @param key
//     * @return
//     */
//    public static SpannableString renderSpecifyString(String text, String key, int color) {
//        SpannableString sp = null;
//        if (text == null) {
//            sp = new SpannableString("");
//            return sp;
//        }
//
//        sp = new SpannableString(text);
//        if (key == null) {
//            return sp;
//        }
//
//        int start = text.indexOf(key);
//        if (start < 0) {
//            return sp;
//        }
//
//        int end = start + key.length();
//        sp.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        sp.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        return sp;
//    }

    /**
     * 设置关键字的颜色,加粗
     *
     * @param text
     * @param key
     * @return
     */
    public static SpannableString colorSpecifyString(String text, String key, int color) {
        SpannableString sp = null;
        if (text == null) {
            sp = new SpannableString("");
            return sp;
        }

        sp = new SpannableString(text);
        if (key == null) {
            return sp;
        }

        int start = text.indexOf(key);
        if (start < 0) {
            return sp;
        }

        int end = start + key.length();
        sp.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sp;
    }

    /**
     * 是否是空字符串
     *
     * @param str
     * @return
     */
    public static boolean isEmptyString(String str) {
        return (str == null || str.trim().length() == 0);
    }

    //如果是字符串是null返回空字符串
    public static String returnEmptyString(String str) {
        return isEmptyString(str) ? "" : str;
    }

    /**
     * 判断字符串内容是否为数字
     *
     * @param content
     * @return
     */
    public static boolean isDigital(String content) {
        for (int i = 0; i < content.length(); i++) {
            if (!Character.isDigit(content.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否是有效输入（限制为汉字、英文、数字）
     *
     * @return
     */
    public static boolean isTextValid(String inputString) {
        // 只能输入汉字或者英文、数字
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";//正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(inputString);
        return m.replaceAll("").trim().equals(inputString);
    }

    /**
     * 手机号码是否有效
     *
     * @param mobile
     * @return
     */
    public static boolean isMobileValid(String mobile) {

        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (mobile.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(mobile);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    /**
     * 是否是有效的邮箱格式
     *
     * @param strEmail
     * @return
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 编码为了url
     */
    public static String encodeContentForUrl(String content) {

        try {
            return (content == null ? "" : URLEncoder.encode(content, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 编码url
     */
    public static String decodeContentForUrl(String content) {
        try {
            return (content == null ? "" : URLDecoder.decode(content, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 文本复制
     *
     * @param content
     */

    public static void copyContentText(Context imContext, String content) {
        ClipData clipData = ClipData.newPlainText(null, content);
        ClipboardManager clipboardManager = (ClipboardManager) imContext.getSystemService(Context
                .CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(clipData);
    }

    /**
     * 是否仅包含字母和汉字
     *
     * @param text
     * @return
     */
    public static boolean isLetterOrChinese(String text) {
        String regex = "^[a-zA-Z\u4e00-\u9fa5]+$";
        return text.matches(regex);
    }

    /**
     * 小写转大写
     *
     * @param str
     * @return
     */
    public static String exChangetoUp(String str) {
        if (!isEmptyString(str)) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (Character.isLowerCase(c)) {
                    sb.append(Character.toUpperCase(c));

                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }

        return str;
    }

    /**
     * byte 转16进制
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
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

    /**
     * 单个byte转换String
     *
     * @param src
     * @return
     */
    public static String byteToHexString(byte src) {
        StringBuilder stringBuilder = new StringBuilder("");
        int v = src & 0xFF;
        String hv = Integer.toHexString(v);
        if (hv.length() < 2) {
            stringBuilder.append(0);
        }
        stringBuilder.append(hv);
        return stringBuilder.toString();
    }

    public static String intToHexString(int[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i];
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * byte 转16进制
     *
     * @param src
     * @return
     */
    public static String bytesListToHexString(List<byte[]> src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.size() <= 0) {
            return null;
        }

        for (int i = 0; i < src.size(); i++) {
            byte[] bytesrc = src.get(i);
            for (int j = 0; j < bytesrc.length; j++) {
                int v = bytesrc[j] & 0xFF;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(hv);
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 将16进制 转换成10进制
     *
     * @param str
     * @return
     */
    public static String print10(String str) {

        StringBuffer buff = new StringBuffer();
        String array[] = str.split(" ");
        for (int i = 0; i < array.length; i++) {
            int num = Integer.parseInt(array[i], 16);
            buff.append(String.valueOf((char) num));
        }
        return buff.toString();
    }

    /**
     * 是否合法mac地址
     *
     * @param mac
     * @return
     */
    public static final boolean isMacValid(String mac) {
        if (isEmptyString(mac)) {
            return false;
        }
        String patternMac = "^[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}$";
        Pattern pa = Pattern.compile(patternMac);

        return pa.matcher(mac).find();
    }

    /**
     * 获取文件名称
     * @param filePath
     * @return
     */
    public static final String getFileName(String filePath) {
        if (isEmptyString(filePath)) {
            return null;
        }

        int index = filePath.lastIndexOf("/");
        if (index > 0) {
            return filePath.substring(index + 1, filePath.length());
        }
        return filePath;
    }

    /***
     * 是否包含指定字符串,不区分大小写
     *
     * @param input : 原字符串
     * @param regex
     * @return
     */
    public static boolean contain(String input, String regex) {
        if (isEmptyString(input)) {
            return false;
        }
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(input);
        boolean result = m.find();
        return result;
    }

    /**
     * 对比不区分大小写
     *
     * @param input
     * @param regex
     * @return
     */
    public static boolean equalsIgnoreCase(String input, String regex) {
        return input.equalsIgnoreCase(regex);
    }

    public static String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    public static List<String> comlpile(String str) {
        Pattern pattern = Pattern.compile(",");
        String[] strs = pattern.split(str);
        List<String> datas = new ArrayList<>();
        for (String string : strs) {
            datas.add(string);
        }
        return datas;
    }

    public static String createRandom(boolean numberFlag, int length) {
        String retStr;
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }

    /**
     * 关键字染色
     *
     * @param text
     * @param key
     * @param color
     * @return
     */
    public static SpannableString renderSpecifyString(String text, String key, int color) {
        SpannableString sp = null;
        if (text == null) {
            sp = new SpannableString("");
            return sp;
        }

        sp = new SpannableString(text);
        if (key == null) {
            return sp;
        }

        int start = text.indexOf(key);
        if (start < 0) {
            return sp;
        }
        int end = start + key.length();
        sp.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sp;
    }

    /**
     * 关键字染色(两个关键词都存在才生效)
     *
     * @param text
     * @param key1
     * @param key2
     * @param color
     * @return
     */
    public static SpannableString renderSpecifyString(String text, String key1, String key2, int color) {
        SpannableString sp = null;
        if (text == null) {
            sp = new SpannableString("");
            return sp;
        }

        sp = new SpannableString(text);
        if (key1 == null) {
            return sp;
        }

        int start = text.indexOf(key1);
        if (start < 0) {
            return sp;
        }
        int end = start + key1.length();
        sp.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        int start2 = text.indexOf(key2);
        if (start2 < 0) {
            return sp;
        }
        int end2 = start2 + key2.length();
        sp.setSpan(new ForegroundColorSpan(color), start2, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sp;
    }

    /**
     * 设置关键字颜色
     *
     * @param text  文字
     * @param key   关键字
     * @param color 颜色
     * @return
     */
    public static SpannableString renderSpecifyColorAndSize(String text, String key, int color, int textSize) {
        SpannableString sp = null;
        if (text == null) {
            sp = new SpannableString("");
            return sp;
        }

        sp = new SpannableString(text);
        if (key == null) {
            return sp;
        }

        int start = text.indexOf(key);
        if (start < 0) {
            return sp;
        }
        int end = start + key.length();
        sp.setSpan(new AbsoluteSizeSpan(textSize, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sp;
    }

    /**
     * 设置关键字颜色
     *
     * @param text 文字
     * @param key  关键字
     * @return
     */
    public static SpannableString renderSpecifySize(String text, String key, int textSize) {
        SpannableString sp = null;
        if (text == null) {
            sp = new SpannableString("");
            return sp;
        }

        sp = new SpannableString(text);
        if (key == null) {
            return sp;
        }

        int start = text.indexOf(key);
        if (start < 0) {
            return sp;
        }
        int end = start + key.length();
        sp.setSpan(new AbsoluteSizeSpan(textSize, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sp;
    }
}
