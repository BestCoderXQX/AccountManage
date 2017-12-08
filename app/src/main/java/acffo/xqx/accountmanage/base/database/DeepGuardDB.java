package acffo.xqx.accountmanage.base.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
* @author xqx
* @email djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/11/30
* description: 深层保护密码数据库
*/

@Database(name = DeepGuardDB.Name , version = DeepGuardDB.VERSION)
public class DeepGuardDB {
    static final String Name = "DeepGuard"; //数据库名
    static final int VERSION = 1;          //数据库版本号
}
