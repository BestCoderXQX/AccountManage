package acffo.xqx.accountmanageacffo.base.eventbus;

import java.util.ArrayList;

import acffo.xqx.accountmanageacffo.base.database.Account;

/**
* @author xqx
* @email djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/11/29
* description: 从已有的账号数据中添加数据到深层加密的集合
*/

public class AddFromAccountEventbus {
    private ArrayList<Account> lists ;

    public AddFromAccountEventbus(ArrayList<Account> lists) {
        this.lists = lists;
    }

    public ArrayList<Account> getLists() {
        return lists;
    }

    public void setLists(ArrayList<Account> lists) {
        this.lists = lists;
    }
}
