package acffo.xqx.accountmanageacffo.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import acffo.xqx.accountmanageacffo.R;
import acffo.xqx.accountmanageacffo.base.activity.BaseActivity;
import acffo.xqx.accountmanageacffo.base.database.Account;
import acffo.xqx.accountmanageacffo.base.database.Account_Table;
import acffo.xqx.accountmanageacffo.base.dialog.IsNoDialog;
import acffo.xqx.accountmanageacffo.base.dialog.MessageDialogSingle;
import acffo.xqx.accountmanageacffo.base.eventbus.AddFromAccountEventbus;
import acffo.xqx.accountmanageacffo.MainAdapter;
import acffo.xqx.accountmanageacffo.base.eventbus.UpdataMainListEventbus;
import acffo.xqx.accountmanageacffo.ui.addFromAccount.AddFromAccountActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * @author xqx
 * @email djlxqx@163.com
 * blog:http://www.cnblogs.com/xqxacm/
 * createAt 2017/11/29
 * description: 深层保护 列表  在这里的账号被查看的时候 会要求输入2次密码才可查看
 */

public class DeepGuardActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.btnBack)
    ImageView btnBack;
    @Bind(R.id.btnExplain)
    ImageView btnExplain;
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.btnAdd)
    TextView btnAdd;
    private MainAdapter adapter;
    private ArrayList<Account> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_guard);
        ButterKnife.bind(this);

        initVariables();
        initView();
        initEvent();
        initDatas();
        EventBus.getDefault().register(this);
    }

    private void initVariables() {
        datas = new ArrayList<>();
    }

    private void initView() {
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter(datas);

        //设置item点击事件  返回选中的应用的名字和图标
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter a, View view, final int position) {

            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter a, View view, final int position) {
                if (view.getId() == R.id.btnDelete){
                    IsNoDialog.Builder builder = new IsNoDialog.Builder();
                    builder.create(DeepGuardActivity.this, "是否移除，将不再受深层保护!", "否", "是");
                    builder.setConfirmButton(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 从深层加密中 去掉
                            Account account = adapter.getData().get(position);
                            account.setEncrypt(false);
                            boolean save = account.save();
                            if (save){
                                adapter.remove(position);
                                EventBus.getDefault().post(new UpdataMainListEventbus()); // 更新主界面列表，修改了数据库 需重新从数据库获取最新的记录
                            }else{
                                TastyToast.makeText(getApplicationContext(), "移除失败!", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                            }
                            dialog.dismiss();
                        }
                    });
                    builder.setCancelButton(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                }
            }
        });
        recycler.setAdapter(adapter);
    }


    private void initDatas() {
        datas.clear();
        List<Account> accountList = SQLite.select().from(Account.class).where(Account_Table.isEncrypt.is(true)).queryList();
        adapter.addData(accountList);
        adapter.notifyDataSetChanged();
    }

    private void initEvent() {
        btnBack.setOnClickListener(this);
        btnExplain.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnExplain:
                MessageDialogSingle.Builder builder = new MessageDialogSingle.Builder();
                builder.create(this, "查看该列表中的账号信息需二次验证");
                break;
            case R.id.btnAdd:
                // 添加账号信息到
                startActivity(new Intent(this, AddFromAccountActivity.class));
                break;
        }
    }
    public void onEventMainThread(AddFromAccountEventbus event) {
        ArrayList<Account> lists = event.getLists();
        if (lists.size()>0){
            // 有数据， 提示添加成功
            TastyToast.makeText(this, "添加成功", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
            EventBus.getDefault().post(new UpdataMainListEventbus()); // 更新主界面列表，修改了数据库 需重新从数据库获取最新的记录
        }
        for (int i = 0; i < lists.size(); i++) {
            Account account = lists.get(i);
            account.setEncrypt(true);
            boolean save = account.save();
            if (save){
                adapter.addData(account);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
