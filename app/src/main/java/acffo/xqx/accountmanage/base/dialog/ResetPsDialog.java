package acffo.xqx.accountmanage.base.dialog;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import acffo.xqx.accountmanage.MainActivity;
import acffo.xqx.accountmanage.R;
import acffo.xqx.accountmanage.base.activity.AppManager;
import acffo.xqx.accountmanage.base.database.Account;
import acffo.xqx.accountmanage.base.database.AdminEntity;
import acffo.xqx.accountmanage.base.utils.CryptUtil;
import acffo.xqx.accountmanage.base.utils.PinyinUtils;
import acffo.xqx.accountmanage.ui.main.SetAdminPsActivity;


/**
* @author xqx
* @emil djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/6/27
* description:  输入原密码对话框
*/


public class ResetPsDialog extends Dialog {
    public ResetPsDialog(Context context) {
        super(context);
    }

    public ResetPsDialog(Context context, int themeResId) {
        super(context, themeResId);
    }



    public static class Builder {
        EditText editPassword;            // 密码框

        public void create(final Context context ) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ResetPsDialog dialog = new ResetPsDialog(context, R.style.PopularDialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉title
            View layout = inflater.inflate(R.layout.dialog_resetps, null);
            editPassword = (EditText) layout.findViewById(R.id.editPassword);


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
                        TastyToast.makeText(context, "请填写登录密码!", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                        return;
                    }
                    List<AdminEntity> adminEntities = SQLite.select().from(AdminEntity.class).queryList();
                    if (adminEntities.size()==0){
                        // 如果没有设置
                        TastyToast.makeText(context, "未设置登录密码，请先设置！", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                        AppManager.getAppManager().finishAllActivity();
                        context.startActivity(new Intent(context,SetAdminPsActivity.class));
                        dialog.dismiss();
                    }else if (adminEntities.size()>1){
                        for (int i = 1; i < adminEntities.size(); i++) {
                            adminEntities.get(i).delete();
                        }
                    }
                    if (adminEntities.get(0).getPassword().equals(editPassword.getText().toString())){
                        // 成功 , 进入
                        adminEntities.get(0).delete();
                        AppManager.getAppManager().finishAllActivity();
                        TastyToast.makeText(context, "验证成功，请设置登录密码", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                        context.startActivity(new Intent(context,SetAdminPsActivity.class));
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
