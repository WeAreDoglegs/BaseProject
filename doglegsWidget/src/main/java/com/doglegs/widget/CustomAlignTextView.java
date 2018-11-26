package com.doglegs.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义换行对齐textview
 */
public class CustomAlignTextView extends AppCompatTextView {
    private String text;
    int maxLines;
    private float textSize;
    private float paddingLeft;
    private float paddingRight;
    private float paddingTop;
    private float paddingBottom;
    private float marginLeft;
    private float marginTop;
    private float marginRight;
    private float marginBottom;
    private int textColor;
    private JSONArray colorIndex;
    private Paint paint1 = new Paint();
    private Paint paintColor = new Paint();
    private float textShowWidth;
    private float Spacing = 0;
    private float LineSpacing = 1.3f;

    //行与行的间距
    public CustomAlignTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomAlignTextView);
        text = array.getString(R.styleable.CustomAlignTextView_text);
        textColor = array.getColor(R.styleable.CustomAlignTextView_textColor, Color.BLACK);
        maxLines = array.getInt(R.styleable.CustomAlignTextView_maxLines, 0);
        textSize = array.getDimension(R.styleable.CustomAlignTextView_textSize, dip2px(14));
        paddingLeft = array.getDimension(R.styleable.CustomAlignTextView_paddingLeft, 0);
        paddingRight = array.getDimension(R.styleable.CustomAlignTextView_paddingRight, 0);
        paddingTop = array.getDimension(R.styleable.CustomAlignTextView_paddingTop, 0);
        paddingBottom = array.getDimension(R.styleable.CustomAlignTextView_paddingBottom, 0);
        marginLeft = array.getDimension(R.styleable.CustomAlignTextView_marginLeft, 0);
        marginRight = array.getDimension(R.styleable.CustomAlignTextView_marginRight, 0);
        marginTop = array.getDimension(R.styleable.CustomAlignTextView_marginTop, 0);
        marginBottom = array.getDimension(R.styleable.CustomAlignTextView_marginBottom, 0);

        paint1.setTextSize(textSize);
        paint1.setColor(textColor);
        paint1.setAntiAlias(true);
        paintColor.setAntiAlias(true);
        paintColor.setTextSize(textSize);
        paintColor.setColor(Color.BLACK);
    }

    public CustomAlignTextView(Context context, float textSize, int textColor, int paddingLeft, int paddingRight, float marginLeft, float marginRight) {
        super(context);
        this.textSize = textSize;
        this.textColor = textColor;
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
        paint1.setTextSize(textSize);
        paint1.setColor(textColor);
        paint1.setAntiAlias(true);
        paint1.setTextAlign(Paint.Align.CENTER);
        paintColor.setAntiAlias(true);
        paintColor.setTextSize(textSize);
        paintColor.setColor(Color.BLACK);
        paintColor.setTextAlign(Paint.Align.CENTER);
    }

    public JSONArray getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(JSONArray colorIndex) {
        this.colorIndex = colorIndex;
    }

    /**
     * 传入一个索引，判断当前字是否被高亮 * * @param index * @return * @throws JSONException
     */
    public boolean isColor(int index) throws JSONException {
        if (colorIndex == null) {
            return false;
        }
        for (int i = 0; i < colorIndex.length(); i++) {
            JSONArray array = colorIndex.getJSONArray(i);
            int start = array.getInt(0);
            int end = array.getInt(1) - 1;
            if (index >= start && index <= end) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        View view = (View) this.getParent();
        textShowWidth = view.getMeasuredWidth() - paddingLeft - paddingRight - marginLeft - marginRight;
        int lineCount = 0;
        if ("".equals(this.getText().toString())) {
            text = this.getHint().toString();
            paint1.setColor(this.getHintTextColors().getDefaultColor());
        } else {
            text = this.getText().toString();
            paint1.setColor(textColor);
        }
//        text = this.getText().toString();//.replaceAll("\n", "\r\n");
        char[] textCharArray = text.toCharArray();
        // 已绘的宽度
        float drawedWidth = 0;
        float charWidth;
        float ellipsisWidth = paint1.measureText("...", 0, 3);


        for (int i = 0; i < textCharArray.length; i++) {
            charWidth = paint1.measureText(textCharArray, i, 1);
            if (textCharArray[i] == '\n') {
                lineCount++;
                drawedWidth = 0;
                continue;
            }
            //有指定最大行数 并且 当前是最大行数时
            if (maxLines > 0 && maxLines == lineCount + 1) {
                //当剩下的位置放不下最后一个字符加省略号 并且当前不是最后一个字符
                if (textShowWidth - drawedWidth < charWidth + ellipsisWidth && i != textCharArray.length - 1) {
                    //画省略号
                    canvas.drawText("...", 0, 3, paddingLeft + drawedWidth, (lineCount + 1) * textSize * LineSpacing, paint1);
                    break;
                }
            }
            if (textShowWidth - drawedWidth < charWidth) {
                lineCount++;
                drawedWidth = 0;
            }
            boolean color = false;
            try {
                color = isColor(i);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

            if (color) {
                canvas.drawText(textCharArray, i, 1, paddingLeft + drawedWidth, (lineCount + 1) * textSize * LineSpacing, paintColor);
            } else {
                canvas.drawText(textCharArray, i, 1, paddingLeft + drawedWidth, (lineCount + 1) * textSize * LineSpacing, paint1);
            }
            if (textCharArray[i] > 127 && textCharArray[i] != '、' && textCharArray[i] != '，' && textCharArray[i] != '。' && textCharArray[i] != '：' && textCharArray[i] != '！') {
                drawedWidth += charWidth + Spacing;
            } else {
                drawedWidth += charWidth;
            }
        }

        setHeight((int) ((lineCount + 1) * (int) textSize * LineSpacing + 10));
    }

    public void setAlignTextColor(int colorVal) {
        textColor = colorVal;
        paint1.setColor(textColor);
        paintColor.setColor(textColor);
        super.setTextColor(textColor);
    }

    public void setAlignPadding(int left, int right, int top, int bottom) {
        paddingLeft = left;
        paddingRight = right;
        paddingTop = top;
        paddingBottom = bottom;
        super.setPadding((int) paddingLeft, (int) paddingTop, (int) paddingRight, (int) paddingBottom);
    }

    public void setAlignMargin(float left, float right, float top, float bottom) {
        marginLeft = left;
        marginRight = right;
        marginTop = top;
        marginBottom = bottom;
    }

    public void setAlignSpacing(float spacing) {
        Spacing = spacing;
    }

    public void setAlignLineSpacing(float lineSpacing) {
        LineSpacing = lineSpacing;
    }

    public void setAlignTextSize(float textSize) {
        this.textSize = textSize;
        paint1.setTextSize(textSize);
        paintColor.setTextSize(textSize);
        super.setTextSize(textSize);
    }

    public void setText(String text) {
        float textSize = this.textSize;
        this.text = stringFilter(ToDBC(text));
        super.setText(this.text);
    }

    /**
     * 半角转换为全角 * * @param input * @return
     */
    public String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {// 全角空格为12288，半角空格为32
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)// 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 去除特殊字符或将所有中文标号替换为英文标号 * * @param str * @return
     */
    public String stringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    private int dip2px(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    private int sp2px(int spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getResources().getDisplayMetrics());
    }
}
