package acffo.xqx.accountmanageacffo.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;


/**
 * Created by xqx on 2017/6/12.
 */
public class BaseFragmentActivity extends FragmentActivity {
    /**
     * activity堆栈管理
     */
    protected AppManager appManager = AppManager.getAppManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加activity 到栈
        appManager.addActivity(this);

    }

    protected void onPause() {
        super.onPause();

    }

    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 从栈中移除activity
        appManager.finishActivity(this);
    }
}
