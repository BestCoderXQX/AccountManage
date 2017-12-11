package acffo.xqx.accountmanageacffo.ui.addAccount;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import acffo.xqx.accountmanageacffo.R;
import acffo.xqx.accountmanageacffo.base.activity.BaseActivity;
import acffo.xqx.accountmanageacffo.base.database.Account;
import acffo.xqx.accountmanageacffo.base.eventbus.AddAccount;
import acffo.xqx.accountmanageacffo.base.eventbus.SelectApp;
import acffo.xqx.accountmanageacffo.base.utils.CryptUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
/**
* @author xqx
* @email djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/11/29
* description: 添加账号信息
*/

public class AddAccountActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.btnBack)
    ImageView btnBack;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.editTitle)
    EditText editTitle;
    @Bind(R.id.btnApp)
    TextView btnApp;
    @Bind(R.id.editAccount)
    EditText editAccount;
    @Bind(R.id.editPassword)
    EditText editPassword;
    @Bind(R.id.editExplain)
    EditText editExplain;
    @Bind(R.id.btnSave)
    TextView btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        ButterKnife.bind(this);

        initVariables();
        initView();
        initEvent();

        EventBus.getDefault().register(this);

    }

    private void initVariables() {

    }

    private void initView() {

    }

    private void initEvent() {
        btnBack.setOnClickListener(this);
        btnApp.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnApp:
                // 选择已有的应用名称作为标题
                startActivity(new Intent(this, SelectAppActivity.class));
                break;
            case R.id.btnSave:
                if (editTitle.getText().toString().equals("")) {
                    TastyToast.makeText(getApplicationContext(), "请填写标题!", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return;
                }
                if (editAccount.getText().toString().equals("")) {
                    TastyToast.makeText(getApplicationContext(), "请填写账号!", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return;
                }
                if (editPassword.getText().toString().equals("")) {
                    TastyToast.makeText(getApplicationContext(), "请填写密码!", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return;
                }
                // 保存
                Account account = new Account();
                account.setName(editAccount.getText().toString());

                // 加密解密用的都是字节数组
                byte[] contentBytes = editPassword.getText().toString().getBytes();
                //加密
                byte[] bytes = CryptUtil.desEncrypt(contentBytes, CryptUtil.getPasswordByte());
                // 加密后的数据
                String encodeString = Base64.encodeToString(bytes, Base64.NO_WRAP);

                account.setPassword(encodeString);
                account.setExplain(editExplain.getText().toString());
                account.setTitle(editTitle.getText().toString());
                account.setEncrypt(false);  // 默认无深层加密

                String fileName = Environment.getExternalStorageDirectory().getPath() + "/AcountManager/"+editTitle.getText().toString()+System.currentTimeMillis()+".png";
                File file = new File(fileName);
                if (img.getDrawable()!=null) {
                    // 有图片
                    boolean b = saveBitmap(file);
                    account.setImgUrl(file.getPath());
                }else{
                    account.setImgUrl("");
                }
                boolean save = account.save();
                if (save) {
                    TastyToast.makeText(getApplicationContext(), "保存成功!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    EventBus.getDefault().post(new AddAccount(account));
                }else{
                    TastyToast.makeText(getApplicationContext(), "保存失败!", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                }
                finish();

                break;
        }
    }

    /**
     * 图片存文件
     */
    public boolean saveBitmap(File f) {
        if (!f.exists()) {
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            try {
                f.createNewFile();
                FileOutputStream out = new FileOutputStream(f);
                ((BitmapDrawable)img.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 选择应用之后 返回 应用名和应用图标
     * @param event
     */
    public void onEventMainThread(SelectApp event) {
        img.setImageDrawable(event.getAppEntity().getImg());
        editTitle.setText(event.getAppEntity().getName());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
