package acffo.xqx.accountmanageacffo.base.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;


import acffo.xqx.accountmanageacffo.R;

import static android.view.ViewGroup.LayoutParams;
import static android.view.ViewGroup.OnClickListener;
import static android.view.ViewGroup.OnTouchListener;

/**
* @author xqx
* @email djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/11/28
* description: 排序 条件 popupwindow
*/

public class SortPopupWindow extends PopupWindow{
    private View mMenuView; //popupwindow布局生成的View
    private TextView btnTimeAscend; //按时间升序
    private TextView btnTimeDescend; //按时间降序
    private TextView btnNameAscend; //按名字升序
    private TextView btnNameDescend; //按名字降序

    public SortPopupWindow(final Context context , OnClickListener itemsOnClick) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popup_sort, null);

        btnTimeAscend = (TextView) mMenuView.findViewById(R.id.btnTimeAscend);
        btnTimeDescend = (TextView) mMenuView.findViewById(R.id.btnTimeDescend);
        btnNameAscend = (TextView) mMenuView.findViewById(R.id.btnNameAscend);
        btnNameDescend = (TextView) mMenuView.findViewById(R.id.btnNameDescend);

        btnTimeAscend.setOnClickListener(itemsOnClick);
        btnTimeDescend.setOnClickListener(itemsOnClick);
        btnNameAscend.setOnClickListener(itemsOnClick);
        btnNameDescend.setOnClickListener(itemsOnClick);

        this.setContentView(mMenuView);
        this.setWidth(LayoutParams.WRAP_CONTENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(0x000000);
        this.setBackgroundDrawable(dw);
        this.setFocusable(true);
        //点击popupWindow之外的部分  关闭popupWindow
        mMenuView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.ly_popup).getBottom();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if(y>height){
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
}
