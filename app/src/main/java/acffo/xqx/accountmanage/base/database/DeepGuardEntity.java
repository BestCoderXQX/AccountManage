package acffo.xqx.accountmanage.base.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
* @author xqx
* @email djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/11/30
* description:深层保护密码
*/

@Table(database = DeepGuardDB.class)
public class DeepGuardEntity extends BaseModel{
    @PrimaryKey(autoincrement = true)
    long id ;

    @Column
    String password ; // 密码

    @Column
    String quesetion;  //问题

    @Column
    String result;  //答案


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
