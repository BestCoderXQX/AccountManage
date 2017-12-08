package acffo.xqx.accountmanage.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.sdsmdg.tastytoast.TastyToast;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

import acffo.xqx.accountmanage.MainActivity;
import acffo.xqx.accountmanage.R;
import acffo.xqx.accountmanage.base.Constant;
import acffo.xqx.accountmanage.base.activity.AppManager;
import acffo.xqx.accountmanage.base.activity.BaseActivity;
import acffo.xqx.accountmanage.base.database.AdminEntity;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author xqx
 * @email djlxqx@163.com
 * blog:http://www.cnblogs.com/xqxacm/
 * createAt 2017/11/28
 * description: 输入密码 ， 进入应用
 */

public class EnterMainActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.editPs)
    EditText editPs;
    @Bind(R.id.btnConfirm)
    TextView btnConfirm;
    List<AdminEntity> adminEntities ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_main_actiivty);
        ButterKnife.bind(this);
        initVariables();
        initEvent();
        initPermission();
    }

    private void initVariables() {
        // 从数据库中获取到登录密码的记录
        adminEntities = SQLite.select().from(AdminEntity.class).queryList();
        if (adminEntities.size()==0){
            // 如果没有设置
            startActivity(new Intent(this,SetAdminPsActivity.class));
            AppManager.getAppManager().finishActivity();
        }else if (adminEntities.size()>1){
            // 如果有多个记录 则只留下一个 ，其他的都删掉。当然多个记录的情况一般不存在
            for (int i = 1; i < adminEntities.size(); i++) {
                adminEntities.get(i).delete();
            }
        }
    }

    private void initEvent() {
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnConfirm:
                // 完成
                if (adminEntities.size()!=0) {
                    if (adminEntities.get(0).getPassword().equals(editPs.getText().toString())) {
                        // 成功 , 进入
                        startActivity(new Intent(this, MainActivity.class));
                        AppManager.getAppManager().finishActivity();
                    } else {
                        TastyToast.makeText(getApplicationContext(), "进入失败,密码错误!", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    }
                }else{
                    startActivity(new Intent(this, SetAdminPsActivity.class));
                    AppManager.getAppManager().finishActivity();
                }
                break;
        }
    }

    private void initPermission() {
        AndPermission.with(this)
                .requestCode(100)
                .permission( Permission.STORAGE)
                .callback(listener)
                .start();

    }
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if(requestCode == 200) {
                // TODO ...
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if(requestCode == 200) {
                // TODO ...
                TastyToast.makeText(getApplicationContext(), "拒绝权限可能导致软件使用异常", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
            }
        }
    };
}

