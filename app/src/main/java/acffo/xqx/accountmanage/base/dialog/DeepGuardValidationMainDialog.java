package acffo.xqx.accountmanage.base.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import acffo.xqx.accountmanage.MainActivity;
import acffo.xqx.accountmanage.R;
import acffo.xqx.accountmanage.base.database.Account;
import acffo.xqx.accountmanage.base.database.DeepGuardEntity;
import acffo.xqx.accountmanage.ui.main.DeepGuardActivity;

/**
* @author xqx
* @email djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/11/30
* description: 二级验证/深层保护密码验证 对话框
*/

public class DeepGuardValidationMainDialog extends Dialog {
    public DeepGuardValidationMainDialog(Context context) {
        super(context);
    }

    public DeepGuardValidationMainDialog(Context context, int themeResId) {
        super(context, themeResId);
    }



    public static class Builder {
        EditText editPassword;            // 密码框
        List<DeepGuardEntity> deepGuardPs ;

        public void create(final Context context , final Account account) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ResetPsDialog dialog = new ResetPsDialog(context, R.style.PopularDialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉title
            View layout = inflater.inflate(R.layout.dialog_deepguardvalidation, null);
            editPassword = (EditText) layout.findViewById(R.id.editPassword);

            deepGuardPs = SQLite.select().from(DeepGuardEntity.class).queryList();

            //关闭按钮
            layout.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            //关闭按钮
            layout.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            ((TextView) layout.findViewById(R.id.btnConfirm)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (editPassword.getText().toString().equals("")) {
                        TastyToast.makeText(context, "请填写深层保护密码!", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                        return;
                    }

                    if (deepGuardPs.get(0).getPassword().equals(editPassword.getText().toString())){
                        // 成功 , 进入
                        // 该账号信息不再深层保护列表里 ，直接打开
                        final AccountInfoDialog.Builder builder = new AccountInfoDialog.Builder();
                        builder.create(context , account );
                        /**
                         * 编辑
                         */
                        builder.setEdit(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface d, int which) {
                                builder.setEnableEdit();
                            }
                        });
                        dialog.dismiss();

                    }else {
                        TastyToast.makeText(context, "进入失败,密码错误!", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    }

                }
            });

            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
            dialog.show();
        }

    }
}
