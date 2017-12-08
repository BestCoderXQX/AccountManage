package acffo.xqx.accountmanage.ui.addAccount;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import acffo.xqx.accountmanage.R;
import acffo.xqx.accountmanage.base.activity.BaseActivity;
import acffo.xqx.accountmanage.base.entity.AppEntity;
import acffo.xqx.accountmanage.base.eventbus.SelectApp;
import acffo.xqx.accountmanage.base.utils.ApkTool;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * @author xqx
 * @email djlxqx@163.com
 * blog:http://www.cnblogs.com/xqxacm/
 * createAt 2017/11/23
 * description: 选择已有的应用名作为标题
 */

public class SelectAppActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.btnBack)
    ImageView btnBack;
    @Bind(R.id.recycler)
    RecyclerView recycler;

    private ArrayList<AppEntity> datas;
    private AppAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_app);
        ButterKnife.bind(this);

        initVariables();
        initView();
        initEvent();
        initData();
    }

    private void initData() {
        datas = ApkTool.scanLocalInstallAppList(this.getPackageManager());
        adapter.addData(datas);
        adapter.notifyDataSetChanged();
    }

    private void initVariables() {
        datas = new ArrayList<>();

    }

    private void initEvent() {
        btnBack.setOnClickListener(this);
    }

    private void initView() {
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AppAdapter(datas);

        //设置item点击事件  返回选中的应用的名字和图标
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter a, View view, int position) {
                EventBus.getDefault().post(new SelectApp(adapter.getData().get(position)));
                finish();
            }
        });
        recycler.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                finish();
                break;
        }
    }
}
