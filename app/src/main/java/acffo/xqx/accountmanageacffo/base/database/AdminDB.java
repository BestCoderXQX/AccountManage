package acffo.xqx.accountmanageacffo.base.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
* @author xqx
* @email djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/11/28
* description: 登录应用密码数据库
*/


@Database(name = AdminDB.Name , version = AdminDB.VERSION)
public class AdminDB {
    static final String Name = "ADMIN"; //数据库名
    static final int VERSION = 1;          //数据库版本号
}
