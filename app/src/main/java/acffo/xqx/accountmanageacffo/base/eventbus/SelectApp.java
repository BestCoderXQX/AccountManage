package acffo.xqx.accountmanageacffo.base.eventbus;

import acffo.xqx.accountmanageacffo.base.entity.AppEntity;

/**
* @author xqx
* @email djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/11/24
* description: 选中一个app item之后 传递app数据
*/

public class SelectApp {
    private AppEntity appEntity;

    public SelectApp(AppEntity appEntity) {
        this.appEntity = appEntity;
    }

    public AppEntity getAppEntity() {
        return appEntity;
    }
}
