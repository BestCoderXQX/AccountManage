package acffo.xqx.accountmanage;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * @author xqx
 * @email djlxqx@163.com
 * blog:http://www.cnblogs.com/xqxacm/
 * createAt 2017/11/29
 * description: acffo 个人APP 开源 版权所有，仅供学习！
 */

public class XApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        // 数据库的初始化
        FlowManager.init(this);
        
    }
}
