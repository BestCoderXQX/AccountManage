package acffo.xqx.accountmanage.ui.addFromAccount;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import acffo.xqx.accountmanage.R;
import acffo.xqx.accountmanage.base.activity.BaseActivity;
import acffo.xqx.accountmanage.base.database.Account;
import acffo.xqx.accountmanage.base.database.Account_Table;
import acffo.xqx.accountmanage.base.eventbus.AddFromAccountEventbus;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
/**
* @author xqx
* @email djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/11/29
* description: 从已有的账号信息列表中选择放到深层保护里   当打开深层保护的账号信息的时候需要输入2次密码
*/

public class AddFromAccountActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.btnBack)
    ImageView btnBack;
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.btnSave)
    TextView btnSave;
    private AddFromAccountAdapter adapter;
    private ArrayList<Account> datas;

    private ArrayList<Account> selectedDatas ;    // 选中的账号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_from_account);
        ButterKnife.bind(this);

        initVariables();
        initView();
        initEvent();
        initDatas();

    }

    private void initVariables() {
        datas = new ArrayList<>();
        selectedDatas = new ArrayList<>();
    }

    private void initDatas() {
        datas.clear();
        List<Account> accountList = SQLite.select().from(Account.class).where(Account_Table.isEncrypt.is(false)).queryList();
        adapter.addData(accountList);
        adapter.notifyDataSetChanged();
    }

    private void initView() {

        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddFromAccountAdapter(datas);

        //设置item点击事件  选中
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter a, View view, final int position) {
                if (selectedDatas.contains(adapter.getData().get(position))){
                    selectedDatas.remove(adapter.getData().get(position));
                }else{
                    selectedDatas.add(adapter.getData().get(position));
                }
                adapter.setSelectedAccounts(selectedDatas);
                adapter.notifyItemChanged(position);
            }
        });

        recycler.setAdapter(adapter);
    }

    private void initEvent() {
        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnSave:
                // 保存
                EventBus.getDefault().post(new AddFromAccountEventbus(selectedDatas));
                finish();
                break;
        }
    }
}
