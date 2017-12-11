package acffo.xqx.accountmanageacffo.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import acffo.xqx.accountmanageacffo.R;


/**
* @author xqx
* @email djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/10/19
* description: 纯文本消息对话框 ,单行
*/

public class MessageDialogSingle extends Dialog {
    public MessageDialogSingle(Context context) {
        super(context);
    }

    public MessageDialogSingle(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {


        public void create(Context context, String content) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final MessageDialogSingle dialog = new MessageDialogSingle(context, R.style.PopularDialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉title
            View layout = inflater.inflate(R.layout.dialog_message_singleline, null);

            ((TextView) layout.findViewById(R.id.txtContent)).setText(content);
            //取消按钮
            layout.findViewById(R.id.btnComfirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
            dialog.show();
        }


    }
}
