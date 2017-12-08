package acffo.xqx.accountmanage.base.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
* @author xqx
* @email djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/11/30
* description: 账号信息数据库
*/


@Database(name = AccountDB.Name , version = AccountDB.VERSION)
public class AccountDB {

    static final String Name = "SchoolDB"; //数据库名
    static final int VERSION = 1;          //数据库版本号

}
