package acffo.xqx.accountmanage.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import acffo.xqx.accountmanage.R;
import acffo.xqx.accountmanage.base.activity.BaseActivity;
import acffo.xqx.accountmanage.base.database.DeepGuardEntity;
import acffo.xqx.accountmanage.base.dialog.DeepGuardValidationSettingDialog;
import acffo.xqx.accountmanage.base.dialog.ResetPsDialog;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author xqx
 * @email djlxqx@163.com
 * blog:http://www.cnblogs.com/xqxacm/
 * createAt 2017/11/29
 * description: 设置界面
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.btnBack)
    ImageView btnBack;            // 后退
    @Bind(R.id.btnSetAdminPs)
    RelativeLayout btnSetAdminPs;      // 修改登录密码
    @Bind(R.id.btnAbout)
    RelativeLayout btnAbout;      // 关于作者
    @Bind(R.id.btnArk)
    RelativeLayout btnArk;
    @Bind(R.id.btnSetDeepGuardPs)
    RelativeLayout btnSetDeepGuardPs;   // 修改深层保护密码
    List<DeepGuardEntity> deepGuardPs ;// 深层保护数据库中密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        deepGuardPs = SQLite.select().from(DeepGuardEntity.class).queryList();
        initEvent();
    }

    private void initEvent() {
        btnBack.setOnClickListener(this);
        btnSetAdminPs.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnArk.setOnClickListener(this);
        btnSetDeepGuardPs.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        deepGuardPs.clear();
        deepGuardPs = SQLite.select().from(DeepGuardEntity.class).queryList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnSetAdminPs:
                // 修改密码
                ResetPsDialog.Builder builder = new ResetPsDialog.Builder();
                builder.create(this);
                break;
            case R.id.btnArk:
                // 深层保护
                if (deepGuardPs.size() == 0) {
                    // 如果没有设置
                    TastyToast.makeText(this, "未设置深层保护密码，请先设置！", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    startActivity(new Intent(this, SetDeepGuardPSActivity.class));
                    return;
                } else if (deepGuardPs.size() > 1) {
                    for (int i = 1; i < deepGuardPs.size(); i++) {
                        deepGuardPs.get(i).delete();
                    }
                }
                DeepGuardValidationSettingDialog.Builder deepGuardbuilder = new DeepGuardValidationSettingDialog.Builder();
                deepGuardbuilder.create(this ,1 );  // 1、代表进入深层保护列表 2、代表修改深层保护密码
                break;
            case R.id.btnAbout:
                // 关于作者
                startActivity(new Intent(this,AboutActivity.class));
                break;
            case R.id.btnSetDeepGuardPs:
                // 修改深层保护密码
                if (deepGuardPs.size() == 0) {
                    // 如果没有设置
                    TastyToast.makeText(this, "未设置深层保护密码，请先设置！", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    startActivity(new Intent(this, SetDeepGuardPSActivity.class));
                    return;
                } else if (deepGuardPs.size() > 1) {
                    for (int i = 1; i < deepGuardPs.size(); i++) {
                        deepGuardPs.get(i).delete();
                    }
                }
                DeepGuardValidationSettingDialog.Builder deepGuardbuilderReset = new DeepGuardValidationSettingDialog.Builder();
                deepGuardbuilderReset.create(this ,2 );// 1、代表进入深层保护列表 2、代表修改深层保护密码
                break;
        }
    }
}
