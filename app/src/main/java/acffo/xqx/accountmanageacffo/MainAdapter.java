package acffo.xqx.accountmanageacffo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import acffo.xqx.accountmanageacffo.base.database.Account;
import acffo.xqx.accountmanageacffo.base.utils.PinyinUtils;

/**
* @author xqx
* @email djlxqx@163.com
* blog:http://www.cnblogs.com/xqxacm/
* createAt 2017/11/29
* description: 主界面 账号列表适配器
*/

public class MainAdapter extends BaseQuickAdapter<Account, BaseViewHolder> {


    public MainAdapter(ArrayList<Account> datas) {
        super(R.layout.item_main_list, datas);
    }

    @Override
    protected void convert(BaseViewHolder helper, final Account item) {
        helper.setText(R.id.name ,item.getTitle());

        Bitmap bitmap=BitmapFactory.decodeFile(item.getImgUrl());
        if (bitmap==null){
            helper.setVisible(R.id.img , false);
            helper.setVisible(R.id.noimg , true);
            helper.setText(R.id.noimg , PinyinUtils.getFirstSpell(item.getTitle()).substring(0,1).toUpperCase());
        }else {
            helper.setVisible(R.id.img , true);
            helper.setVisible(R.id.noimg , false);
            helper.setImageBitmap(R.id.img, bitmap);
        }

        // 子控件点击事件
        helper.addOnClickListener(R.id.btnDelete);

    }


}
