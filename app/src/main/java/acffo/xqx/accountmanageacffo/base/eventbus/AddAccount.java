package acffo.xqx.accountmanageacffo.base.eventbus;

import acffo.xqx.accountmanageacffo.base.database.Account;

/**
 * Created by 徐启鑫 on 2017/11/24.
 */

public class AddAccount {
    private Account account;

    public AddAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
