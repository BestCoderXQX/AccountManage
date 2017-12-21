package acffo.xqx.accountmanageacffo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import acffo.xqx.accountmanageacffo.base.activity.BaseActivity;
import acffo.xqx.accountmanageacffo.base.database.Account;
import acffo.xqx.accountmanageacffo.base.database.DeepGuardEntity;
import acffo.xqx.accountmanageacffo.base.dialog.AccountInfoDialog;
import acffo.xqx.accountmanageacffo.base.dialog.DeepGuardValidationMainDialog;
import acffo.xqx.accountmanageacffo.base.dialog.IsNoDialog;
import acffo.xqx.accountmanageacffo.base.eventbus.AddAccount;
import acffo.xqx.accountmanageacffo.base.eventbus.UpdataMainListEventbus;
import acffo.xqx.accountmanageacffo.base.popupwindow.SortPopupWindow;
import acffo.xqx.accountmanageacffo.base.utils.PinyinUtils;
import acffo.xqx.accountmanageacffo.ui.addAccount.AddAccountActivity;
import acffo.xqx.accountmanageacffo.ui.main.SetDeepGuardPSActivity;
import acffo.xqx.accountmanageacffo.ui.main.SettingActivity;
import acffo.xqx.accountmanageacffo.ui.search.SearchActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * @author xqx
 * @email djlxqx@163.com
 * blog:http://www.cnblogs.com/xqxacm/
 * createAt 2017/11/23
 * description: 账号管家
 */


public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.btnAdd)
    ImageView btnAdd;
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.btnSelect)
    ImageView btnSelect;
    @Bind(R.id.btnSetting)
    ImageView btnMenu;
    @Bind(R.id.btnSearch)
    RelativeLayout btnSearch;
    ;

    private ArrayList<Account> datas;
    private MainAdapter adapter;

    private SortPopupWindow popup; // 列表排序条件popupwindow

    private int type = 0;

    /**
     * 上一次点击 back 键的时间
     * 用于双击退出的判断
     */
    private static long lastBackTime = 0;

    /**
     * 当双击 back 键在此间隔内是直接触发 onBackPressed
     */
    private final int BACK_INTERVAL = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initVariables();
        initView();
        initEvent();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initVariables() {
        datas = new ArrayList<>();
    }

    private void initData() {
        datas.clear();
        List<Account> accountList = SQLite.select().from(Account.class).queryList();
        adapter.addData(accountList);
        adapter.notifyDataSetChanged();
        showSearchLy();
    }



    private void initEvent() {
        btnAdd.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
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
                        TastyToast.makeText(MainActivity.this, "未设置深层保护密码，请先设置！", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                        startActivity(new Intent(MainActivity.this, SetDeepGuardPSActivity.class));
                        return;
                    }
                    DeepGuardValidationMainDialog.Builder deepGuardBuilder = new DeepGuardValidationMainDialog.Builder();
                    deepGuardBuilder.create(MainActivity.this, adapter.getData().get(position));
                } else {
                    // 该账号信息不再深层保护列表里 ，直接打开
                    final AccountInfoDialog.Builder builder = new AccountInfoDialog.Builder();
                    builder.create(MainActivity.this, adapter.getItem(position));
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
                    builder.create(MainActivity.this, "是否删除", "否", "是");
                    builder.setConfirmButton(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean delete = adapter.getData().get(position).delete();
                            if (delete) {
                                // 删除成功
                                adapter.remove(position);
                                TastyToast.makeText(getApplicationContext(), "删除成功!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                                showSearchLy();
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
        switch (v.getId()) {
            case R.id.btnAdd:
                // 添加
                startActivity(new Intent(this, AddAccountActivity.class));
                break;
            case R.id.btnSelect:
                // 排列条件
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                popup = new SortPopupWindow(this, ZxOnclickListener);  //创建自定义的popupwindow对象
                // 设置popupWindow显示的位置
                popup.showAtLocation(v, Gravity.NO_GRAVITY, location[0] + v.getWidth() - 10, location[1] + v.getHeight() + 10);
                // 当popupWindow 出现的时候 屏幕的透明度  ，设为0.5 即半透明 灰色效果
                backgroundAlpha(0.5f);
                // 设置popupWindow取消的点击事件，即popupWindow消失后，屏幕的透明度，全透明，就回复原状态
                popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1f);
                    }
                });
                break;
            case R.id.btnSetting:
                // 打开设置界面
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.btnSearch:
                // 搜索
                startActivity(new Intent(this , SearchActivity.class));
                break;
        }
    }


    private View.OnClickListener ZxOnclickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnTimeAscend:
                    // 按时间升序
                    initData();
                    break;
                case R.id.btnTimeDescend:
                    // 按时间降序
                    type = 3;
                    Collections.reverse(adapter.getData());
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.btnNameAscend:
                    // 按标题名字升序
                    type = 1;
                    sortByName(type);
                    break;
                case R.id.btnNameDescend:
                    // 按标题名字降序
                    type = 2;
                    sortByName(type);
                    break;
            }
            popup.dismiss();
        }
    };

    /**
     * 根据名字拼音排序
     *
     * @param type 1：升序 2：降序
     */
    private void sortByName(final int type) {
        Collections.sort(adapter.getData(), new Comparator<Account>() {
            @Override
            public int compare(Account o1, Account o2) {
                int i = 0;
                if (type == 1) {
                    i = PinyinUtils.getFirstSpell(o1.getTitle()).toUpperCase().compareToIgnoreCase(PinyinUtils.getFirstSpell(o2.getTitle()).toUpperCase());
                } else if (type == 2) {
                    i = PinyinUtils.getFirstSpell(o2.getTitle()).toUpperCase().compareToIgnoreCase(PinyinUtils.getFirstSpell(o1.getTitle()).toUpperCase());
                }
                return i;
            }
        });
        adapter.notifyDataSetChanged();
    }

    /**
     * 添加新的账号信息  收到的消息 为Account对象
     *
     * @param event
     */
    public void onEventMainThread(AddAccount event) {
        adapter.addData(event.getAccount());
        showSearchLy();
    }


    /**
     * 修改了深层保护列表，则重新冲数据库中获取到最新的。
     *
     * @param event
     */
    public void onEventMainThread(UpdataMainListEventbus event) {
        initData();        // 从数据库中获取最新的数据
        sortByName(type); // 按照之前的排序方式进行排序
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        this.getWindow().setAttributes(lp);
    }

    /**
     * 重置系统后退按钮的功能
     */
    @Override
    public void onBackPressed() {

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBackTime < BACK_INTERVAL) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "再按一次返回键回到桌面", Toast.LENGTH_SHORT).show();
        }
        lastBackTime = currentTime;
    }

    /**
     * 根据item数量来决定是否显示搜索框，当数量多于25条的时候显示
     */
    public void showSearchLy(){
        if (adapter.getData().size()>25){
            btnSearch.setVisibility(View.VISIBLE);
        }else{
            btnSearch.setVisibility(View.GONE);
        }
    }
}
