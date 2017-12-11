package acffo.xqx.accountmanageacffo.base.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;


/**
* @author xqx
* @email djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/11/9
* description: 父Activity 进行 Activity管理
*/

public class BaseActivity extends Activity{
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
