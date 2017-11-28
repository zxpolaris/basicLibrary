package com.icow.basiclibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.icow.basiclibrary.R;
import com.icow.basiclibrary.utils.CommUtil;
import com.icow.basiclibrary.utils.LoggerUtil;

public class SideBar extends View {
    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    private String[] a;
    // 26个字母
     public static String[]c={"B","C","E","G","H","N","Q","S"};
    public static String[] b = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private int choose = -1;// 选中
    private Paint paint = new Paint();

    private TextView tvCenterAlpha; // 选中时页面中间回显的字母
    public void GetA(String[] aa){
        if(!CommUtil.isEmpty(a)){
            this.a=aa;
        }else {
            this.a=c;
        }

        LoggerUtil.v("ll="+aa);
    }
    public void setCenterAlphaTextView(TextView mTextDialog) {
        this.tvCenterAlpha = mTextDialog;
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

      public SideBar(Context context) {
        super(context);
    }

    private float startHeight;
    private float endHeight;
    private float letterHeight;
    /**
     * 重写这个方法
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取焦点改变背景颜色.
        letterHeight =  getHeight() / 27;// 获取对应高度
        int width = getWidth(); // 获取对应宽度
        float textSize = letterHeight * 5 / 6;

        if(!CommUtil.isEmpty(a)){

            startHeight = ((float) getHeight()) / 2 - (letterHeight * a.length / 2);
//            int singleHeight = letterHeight / a.length;// 获取每一个字母的高度
            for (int i = 0; i < a.length; i++) {
                paint.setColor(Color.parseColor("#333333"));
                paint.setTextSize(textSize);
                // 选中的状态
                if (i == choose) {
                    paint.setColor(Color.parseColor("#FFA531"));
                    paint.setFakeBoldText(true);
                }
                // x坐标等于中间-字符串宽度的一半.
//                float xPos = width / 2 - paint.measureText(a[i]) / 2;
//                float yPos = letterHeight * i + letterHeight;
                float xPos = width / 2;;
                float yPos = startHeight + letterHeight * i + textSize;
                if (i == a.length - 1) {
                    endHeight = yPos;
                }
                canvas.drawText(a[i], xPos, yPos, paint);
                paint.reset();// 重置画笔
            }
        }



    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        if(!CommUtil.isEmpty(a)) {
//            final int c = (int) (y / getHeight() * a.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数
            int c;
            if (y < startHeight + letterHeight) {
                c = 0;
            } else if (y > endHeight - letterHeight) {
                c = a.length - 1;
            } else {
                c = (int)((y - startHeight)/letterHeight);
            }
            switch (action) {
                case MotionEvent.ACTION_UP:
                    setBackgroundDrawable(new ColorDrawable(0x00000000));
                    choose = -1;//
                    invalidate();
                    if (tvCenterAlpha != null) {
                        tvCenterAlpha.setVisibility(View.INVISIBLE);
                    }
                    break;

                default:
                    setBackgroundResource(R.drawable.sidebar_background);
                    if (oldChoose != c) {
                            if (c >= 0 && c < a.length) {
                                if (listener != null) {
                                    listener.onTouchingLetterChanged(a[c]);
                                }
                                if (tvCenterAlpha != null) {
                                    tvCenterAlpha.setText(a[c]);
                                    tvCenterAlpha.setVisibility(View.VISIBLE);
                                }
                                choose = c;
                                invalidate();
                            }
                        }

                    break;
            }
        }
        return true;

    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String s);
    }

}