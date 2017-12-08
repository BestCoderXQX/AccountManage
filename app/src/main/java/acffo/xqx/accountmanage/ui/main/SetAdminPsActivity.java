package acffo.xqx.accountmanage.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import acffo.xqx.accountmanage.R;
import acffo.xqx.accountmanage.base.activity.BaseActivity;
import acffo.xqx.accountmanage.base.database.AdminEntity;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author xqx
 * @email djlxqx@163.com
 * blog:http://www.cnblogs.com/xqxacm/
 * createAt 2017/11/28
 * description: 设置进入app密码
 */

public class SetAdminPsActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.editPS)
    EditText editPS;
    @Bind(R.id.editPsConfirm)
    EditText editPsConfirm;
    @Bind(R.id.btnConfirm)
    TextView btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_admin_ps);
        ButterKnife.bind(this);

        initEvent();

    }

    private void initEvent() {
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnConfirm:
                // 完成
                if (editPS.getText().toString().equals("")){
                    TastyToast.makeText(getApplicationContext(), "请填写密码", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return ;
                }
                if (editPS.getText().toString().length()<8){
                    TastyToast.makeText(getApplicationContext(), "请输入至少8位密码", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return ;
                }
                if (editPsConfirm.getText().toString().equals("")){
                    TastyToast.makeText(getApplicationContext(), "请填写确认密码", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return;
                }
                if (!editPS.getText().toString().equals(editPsConfirm.getText().toString())){
                    // 两个密码不一致
                    TastyToast.makeText(getApplicationContext(), "密码不一致!", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return;
                }
                AdminEntity adminEntity = new AdminEntity();
                adminEntity.setPassword(editPsConfirm.getText().toString());
                boolean save = adminEntity.save();
                if (save){
                    startActivity(new Intent(this,EnterMainActivity.class));
                    finish();
                }else{
                    TastyToast.makeText(getApplicationContext(), "设置失败!", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                }
                break;
        }
    }
}
