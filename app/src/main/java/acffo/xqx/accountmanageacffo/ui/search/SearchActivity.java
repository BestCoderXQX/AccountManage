package acffo.xqx.accountmanageacffo.ui.search;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import acffo.xqx.accountmanageacffo.MainActivity;
import acffo.xqx.accountmanageacffo.MainAdapter;
import acffo.xqx.accountmanageacffo.R;
import acffo.xqx.accountmanageacffo.base.activity.BaseActivity;
import acffo.xqx.accountmanageacffo.base.database.Account;
import acffo.xqx.accountmanageacffo.base.database.Account_Table;
import acffo.xqx.accountmanageacffo.base.database.DeepGuardEntity;
import acffo.xqx.accountmanageacffo.base.dialog.AccountInfoDialog;
import acffo.xqx.accountmanageacffo.base.dialog.DeepGuardValidationMainDialog;
import acffo.xqx.accountmanageacffo.base.dialog.IsNoDialog;
import acffo.xqx.accountmanageacffo.ui.main.SetDeepGuardPSActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author xqx
 * @email djlxqx@163.com
 * blog:http://www.cnblogs.com/xqxacm/
 * createAt 2017/12/21
 * description: 查询功能
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.editSearch)
    EditText editSearch;
    @Bind(R.id.btnSearch)
    ImageView btnSearch;
    @Bind(R.id.recycler)
    RecyclerView recycler;

    private ArrayList<Account> datas;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initVariables();
        initView();
        initEvent();
    }

    private void initEvent() {
        btnSearch.setOnClickListener(this);
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

                if (adapter.getData().get(position).isEncrypt()) {
                    // 该账号信息在深层保护列表里  需要 验证深层保护的密码
                    // 判断是否设置了,没有则先设置

                    List<DeepGuardEntity> deepGuardPs = SQLite.select().from(DeepGuardEntity.class).queryList();
                    if (deepGuardPs.size() == 0) {
                        TastyToast.makeText(SearchActivity.this, "未设置深层保护密码，请先设置！", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                        startActivity(new Intent(SearchActivity.this, SetDeepGuardPSActivity.class));
                        return;
                    }
                    DeepGuardValidationMainDialog.Builder deepGuardBuilder = new DeepGuardValidationMainDialog.Builder();
                    deepGuardBuilder.create(SearchActivity.this, adapter.getData().get(position));
                } else {
                    // 该账号信息不再深层保护列表里 ，直接打开
                    final AccountInfoDialog.Builder builder = new AccountInfoDialog.Builder();
                    builder.create(SearchActivity.this, adapter.getItem(position));
                    /**
                     * 编辑
                     */
                    builder.setEdit(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int which) {
                            builder.setEnableEdit();
                        }
                    });
                }
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter a, View view, final int position) {
                if (view.getId() == R.id.btnDelete) {
                    //删除按钮
                    IsNoDialog.Builder builder = new IsNoDialog.Builder();
                    builder.create(SearchActivity.this, "是否删除", "否", "是");
                    builder.setConfirmButton(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean delete = adapter.getData().get(position).delete();
                            if (delete) {
                                // 删除成功
                                adapter.remove(position);
                                TastyToast.makeText(getApplicationContext(), "删除成功!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                            } else {
                                // 删除失败
                                TastyToast.makeText(getApplicationContext(), "删除失败!", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSearch:
                if (editSearch.getText().toString().equals("")){
                    TastyToast.makeText(getApplicationContext(), "请输入查询内容!", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return;
                }
                searchDatas(editSearch.getText().toString());
                break;
        }
    }

    /**
     * 根据内容查询数据
     * @param content
     */
    private void searchDatas(String content) {
        datas.clear();
        List<Account> accountList = SQLite.select().from(Account.class).where(Account_Table.title.like(content)).queryList();
        adapter.addData(accountList);
        adapter.notifyDataSetChanged();
        if (adapter.getData().size()==0){
            TastyToast.makeText(getApplicationContext(), "无符合条件的结果!", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
        }
    }
}
