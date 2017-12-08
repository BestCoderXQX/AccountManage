package acffo.xqx.accountmanage;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by 徐启鑫 on 2017/11/23.
 */

public class XApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        // 数据库的初始化
        FlowManager.init(this);
        
    }
}
