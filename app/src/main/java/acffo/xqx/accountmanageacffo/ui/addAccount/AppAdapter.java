package acffo.xqx.accountmanageacffo.ui.addAccount;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import acffo.xqx.accountmanageacffo.R;
import acffo.xqx.accountmanageacffo.base.entity.AppEntity;

/**
 * Created by 徐启鑫 on 2017/11/23.
 */

public class AppAdapter extends BaseQuickAdapter<AppEntity, BaseViewHolder> {


    public AppAdapter(ArrayList<AppEntity> datas) {
        super(R.layout.item_app, datas);
    }

    @Override
    protected void convert(BaseViewHolder helper, final AppEntity item) {
        helper.setText(R.id.name ,item.getName());
        helper.setImageDrawable(R.id.img,item.getImg());
    }

}
