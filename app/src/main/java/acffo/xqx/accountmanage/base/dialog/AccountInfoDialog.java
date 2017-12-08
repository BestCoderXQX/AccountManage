package acffo.xqx.accountmanage.base.dialog;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
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

import com.sdsmdg.tastytoast.TastyToast;

import acffo.xqx.accountmanage.R;
import acffo.xqx.accountmanage.base.database.Account;
import acffo.xqx.accountmanage.base.utils.CryptUtil;
import acffo.xqx.accountmanage.base.utils.PinyinUtils;


/**
* @author xqx
* @emil djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/6/27
* description:  文本提示对话框
*/


public class AccountInfoDialog extends Dialog {
    public AccountInfoDialog(Context context) {
        super(context);
    }

    public AccountInfoDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private static OnClickListener btnEditListener;


    public static class Builder {
        EditText editAccount ;             // 账号框
        EditText editPassword;            // 密码框
        EditText editExplain;              // 备注框
        TextView txtName;                    // 标题
        ImageView img;                      // 有图片显示图片
        TextView noimg;                    // 没图片显示标题的第一个字符的大写字母
        LinearLayout lyButtonEdit;
        RelativeLayout lyEdit;
        TextView btnEdit;     // 编辑按钮
        TextView btnCopy;     // copy密码按钮
        Account account;
        public Builder setEdit(OnClickListener listener) {
            btnEditListener = listener;
            return this;
        }


        public void cancelEdit(){
            editAccount.setText(account.getName());
            editExplain.setText(account.getExplain());
            txtName.setText(account.getTitle());
            lyButtonEdit.setVisibility(View.GONE);
            lyEdit.setVisibility(View.VISIBLE);
            editAccount.setEnabled(false);
            editExplain.setEnabled(false);
            editPassword.setEnabled(false);
            editAccount.setFocusable(false);

            // 加密解密用的都是字节数组
            byte[] contentBytes = editPassword.getText().toString().getBytes();
            //加密
            byte[] bytes = CryptUtil.desEncrypt(contentBytes, CryptUtil.getPasswordByte());
            // 加密后的数据
            String encodeString = Base64.encodeToString(bytes, Base64.NO_WRAP);
            account.setPassword(encodeString);
        }

        public void setEnableEdit(){

            editAccount.setEnabled(true);
            editExplain.setEnabled(true);
            editPassword.setEnabled(true);
            editAccount.setFocusable(true);
            editAccount.setFocusableInTouchMode(true);
            editAccount.requestFocus();
            editAccount.setSelection(editAccount.getText().toString().length());
            lyButtonEdit.setVisibility(View.VISIBLE);
            lyEdit.setVisibility(View.GONE);
        }

        public void create(final Context context , final Account account) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final AccountInfoDialog dialog = new AccountInfoDialog(context, R.style.PopularDialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉title
            View layout = inflater.inflate(R.layout.dialog_message, null);
            this.account = account ;
            editAccount = (EditText) layout.findViewById(R.id.editAccount);
            editPassword = (EditText) layout.findViewById(R.id.editPassword);
            editExplain = (EditText) layout.findViewById(R.id.editExplain);
            txtName = (TextView) layout.findViewById(R.id.txtName);
            img = (ImageView) layout.findViewById(R.id.img);
            noimg = (TextView) layout.findViewById(R.id.noimg);
            btnEdit = (TextView) layout.findViewById(R.id.btnEdit);
            btnCopy = (TextView) layout.findViewById(R.id.btnCopy);
            lyButtonEdit = (LinearLayout) layout.findViewById(R.id.lyButtonEdit);
            lyEdit = (RelativeLayout) layout.findViewById(R.id.lyEdit);
            editAccount.setText(account.getName());
            editExplain.setText(account.getExplain());
            txtName.setText(account.getTitle());
            // 解密后的密码

            byte[] base64Decodedbytes = Base64.decode(account.getPassword().getBytes(), Base64.NO_WRAP);
            byte[] byteDecode = CryptUtil.desDecrypt(base64Decodedbytes, CryptUtil.getPasswordByte());
            String decodeString = new String(byteDecode);
            editPassword.setText(decodeString);

            Bitmap bitmap= BitmapFactory.decodeFile(account.getImgUrl());
            if (bitmap==null){
                noimg.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);
                noimg.setText(PinyinUtils.getFirstSpell(account.getTitle()).substring(0,1).toUpperCase());
            }else {
                noimg.setVisibility(View.GONE);
                img.setVisibility(View.VISIBLE);
                img.setImageBitmap(bitmap);
            }

            // 不可点击
            editAccount.setEnabled(false);
            editPassword.setEnabled(false);
            editExplain.setEnabled(false);

            //关闭按钮
            layout.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            //复制按钮
            btnCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(editPassword.getText().toString());
                    TastyToast.makeText(context, "复制成功!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                }
            });

            //取消按钮
            layout.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    cancelEdit();
                }
            });

            ((TextView) layout.findViewById(R.id.btnEdit)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnEditListener.onClick(dialog,
                            DialogInterface.BUTTON_POSITIVE);
                }
            });

            ((TextView) layout.findViewById(R.id.btnConfirm)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editAccount.getText().toString().equals("")) {
                        TastyToast.makeText(context, "请填写账号!", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                        return;
                    }
                    if (editPassword.getText().toString().equals("")) {
                        TastyToast.makeText(context, "请填写密码!", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                        return;
                    }
                    // 保存
                    account.setName(editAccount.getText().toString());
                    account.setExplain(editExplain.getText().toString());


                    // 加密解密用的都是字节数组
                    byte[] contentBytes = editPassword.getText().toString().getBytes();
                    //加密
                    byte[] bytes = CryptUtil.desEncrypt(contentBytes, CryptUtil.getPasswordByte());
                    // 加密后的数据
                    String encodeString = Base64.encodeToString(bytes, Base64.NO_WRAP);

                    account.setPassword(encodeString);

                    boolean save = account.save();
                    if (save) {
                        TastyToast.makeText(context, "保存成功!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                        dialog.dismiss();
                    }else{
                        TastyToast.makeText(context, "保存失败!", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    }
                }
            });

            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
            dialog.show();
        }

    }
}
