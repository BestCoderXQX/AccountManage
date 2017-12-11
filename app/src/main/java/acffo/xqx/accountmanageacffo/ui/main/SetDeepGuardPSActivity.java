package acffo.xqx.accountmanageacffo.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import acffo.xqx.accountmanageacffo.R;
import acffo.xqx.accountmanageacffo.base.activity.BaseActivity;
import acffo.xqx.accountmanageacffo.base.database.DeepGuardEntity;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SetDeepGuardPSActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.editPS)
    EditText editPS;
    @Bind(R.id.editPsConfirm)
    EditText editPsConfirm;
    @Bind(R.id.btnConfirm)
    TextView btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_deep_guard_ps);
        ButterKnife.bind(this);

        // 进到这个界面 说明当前没有设置 深层保护密码 即 DeepGuardDB数据库中没有数据
        // 不过以防万一 ，这里还是要清空一下数据库中的所有记录
        List<DeepGuardEntity> deepGuardPs = SQLite.select().from(DeepGuardEntity.class).queryList();
        for (int i = 0; i < deepGuardPs.size(); i++) {
            deepGuardPs.get(i).delete();
        }
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
                DeepGuardEntity deepGuardEntity = new DeepGuardEntity();
                deepGuardEntity.setPassword(editPsConfirm.getText().toString());
                boolean save = deepGuardEntity.save();
                if (save){
                    TastyToast.makeText(getApplicationContext(), "设置成功，请牢记密码!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    finish();
                }else{
                    TastyToast.makeText(getApplicationContext(), "设置失败!", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                }
                break;
        }
    }
}
