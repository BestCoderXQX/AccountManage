package acffo.xqx.accountmanageacffo.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import acffo.xqx.accountmanageacffo.R;


/**
* @author xqx
* @emil djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/6/27
* description:  确定取消对话框
*/

public class IsNoDialog extends Dialog {
    public IsNoDialog(Context context) {
        super(context);
    }

    public IsNoDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private static OnClickListener btnConfirmListener;
    private static OnClickListener btnCancelListener;

    public static class Builder {

        public IsNoDialog.Builder setConfirmButton(OnClickListener listener) {
            btnConfirmListener = listener;
            return this;
        }

        public IsNoDialog.Builder setCancelButton(OnClickListener listener) {
            btnCancelListener = listener;
            return this;
        }

        public void create(Context context, String content,String txtNo ,String txtYes) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final AccountInfoDialog dialog = new AccountInfoDialog(context, R.style.PopularDialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉title
            View layout = inflater.inflate(R.layout.dialog_is_no, null);
            ((TextView)layout.findViewById(R.id.btnCancel)).setText(txtNo);
            ((TextView)layout.findViewById(R.id.btnConfirm)).setText(txtYes);
            ((TextView) layout.findViewById(R.id.txtContent)).setText(content);

            ((TextView)layout.findViewById(R.id.btnCancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnCancelListener.onClick(dialog,
                            DialogInterface.BUTTON_POSITIVE);
                }
            });
            ((TextView)layout.findViewById(R.id.btnConfirm)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnConfirmListener.onClick(dialog,
                            DialogInterface.BUTTON_NEGATIVE);
                }
            });


            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
            dialog.show();
        }
    }
}