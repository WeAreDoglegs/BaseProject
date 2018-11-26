package com.doglegs.base.uitls;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.doglegs.core.constants.Config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    public final static Integer DRAWABLE_LEFT = 0;
    public final static Integer DRAWABLE_TOP = 1;
    public final static Integer DRAWABLE_RIGHT = 2;
    public final static Integer DRAWABLE_BOTTOM = 3;


    /**
     * 获取拍照图片路径
     *
     * @return
     */
    public static String getTakePhotoPath() {
        return Config.DIR.IMG_DIR + "yryc-store-image-" + DateUtils.format(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss") + ".jpg";
    }

    /**
     * 获取压缩图片文件名
     *
     * @return
     */
    public static String getImgCompressName() {
        return "yryc-store-" + UUID.randomUUID() + "-" + DateUtils.format(System.currentTimeMillis(), "yyyy-MM-dd-hh:mm:ss") + ".jpg";
    }

    public static void setTextDrawable(Context context, int id, TextView textView, Integer position) {
        Drawable drawable = context.getResources().getDrawable(id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        if (position == DRAWABLE_LEFT) {
            textView.setCompoundDrawables(drawable, null, null, null);
        } else if (position == DRAWABLE_RIGHT) {
            textView.setCompoundDrawables(null, null, drawable, null);
        } else if (position == DRAWABLE_TOP) {
            textView.setCompoundDrawables(null, drawable, null, null);
        } else if (position == DRAWABLE_BOTTOM) {
            textView.setCompoundDrawables(null, null, null, drawable);
        }
    }

    /**
     * 判断手机号码是否是联通号
     *
     * @return
     */
    public static boolean isCMCCMobile(String mobile) {
        String regex = "^((13[0,1,2])|(14[5,6])|(15[5,6])|(16[6,7])|(17[5,6])|(18[5,6]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /***
     * 方法一对集合进行深拷贝 注意需要对泛型类进行序列化(实现Serializable)
     *
     * @param srcList
     * @param <T>
     * @return
     */
    public static <T> List<T> depCopy(List<T> srcList) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(srcList);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream inStream = new ObjectInputStream(byteIn);
            List<T> destList = (List<T>) inStream.readObject();
            return destList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 核验码是否有效
     * (总位数为12位，核验码前两位为01)
     *
     * @param verificationCode
     * @return
     */
    public static boolean isVerificationCodeValid(String verificationCode) {
        String regex = "^01[0-9]{10}";
        if (verificationCode.length() != 12) {
            return false;

        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(verificationCode);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

}
