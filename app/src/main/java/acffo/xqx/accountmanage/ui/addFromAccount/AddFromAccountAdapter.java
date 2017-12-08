package acffo.xqx.accountmanage.ui.addFromAccount;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import acffo.xqx.accountmanage.R;
import acffo.xqx.accountmanage.base.database.Account;
import acffo.xqx.accountmanage.base.entity.AppEntity;
import acffo.xqx.accountmanage.base.utils.PinyinUtils;

/**
 * Created by 徐启鑫 on 2017/11/29.
 */

public class AddFromAccountAdapter extends BaseQuickAdapter<Account, BaseViewHolder> {

    private ArrayList<Account> selectedAccounts;

    public AddFromAccountAdapter(ArrayList<Account> datas) {
        super(R.layout.item_from_account, datas);
        selectedAccounts = new ArrayList<>();
    }

    public void setSelectedAccounts(ArrayList<Account> selectedAccounts) {
        this.selectedAccounts = selectedAccounts;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Account item) {
        helper.setText(R.id.name ,item.getTitle());
        Bitmap bitmap= BitmapFactory.decodeFile(item.getImgUrl());
        if (bitmap==null){
            helper.setVisible(R.id.img , false);
            helper.setVisible(R.id.noimg , true);
            helper.setText(R.id.noimg , PinyinUtils.getFirstSpell(item.getTitle()).substring(0,1).toUpperCase());
        }else {
            helper.setVisible(R.id.img , true);
            helper.setVisible(R.id.noimg , false);
            helper.setImageBitmap(R.id.img, bitmap);
        }

        if (selectedAccounts.contains(item)){
            helper.setImageResource(R.id.btnState , R.mipmap.icon_selected);
        }else{
            helper.setImageResource(R.id.btnState , R.mipmap.icon_unselected);
        }

    }

}
