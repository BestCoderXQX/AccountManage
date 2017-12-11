package acffo.xqx.accountmanageacffo.base.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
* @author xqx
* @email djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/11/30
* description: 账号信息
*/

@Table(database = AccountDB.class)
public class Account extends BaseModel{
    //主键 一个表必须有至少一个主键
    // (autoincrement = true) 表示该字段是自增的，可以不设置 ，默认false
    @PrimaryKey(autoincrement = true)
    long id ;

    // 标题
    @Column
    String title;

    // 名字
    @Column
    String name;

    // 密码
    @Column
    String password;

    // 备注
    @Column
    String explain ;

    // 图标 文件路径
    @Column
    String imgUrl;

    // 是否深层加密
    @Column
    boolean isEncrypt;

    public Account() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isEncrypt() {
        return isEncrypt;
    }

    public void setEncrypt(boolean encrypt) {
        isEncrypt = encrypt;
    }
}
