package com.jingdong.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * 时间:2017/12/3 21:19
 * 作者:韩帅帅
 * 详情:
 */

public class MyExpanableListView extends ExpandableListView {
    public MyExpanableListView(Context context) {
        super(context);
    }

    public MyExpanableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyExpanableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //重新设置高度
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
