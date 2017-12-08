package acffo.xqx.accountmanage.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import acffo.xqx.accountmanage.R;
import acffo.xqx.accountmanage.base.activity.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.btnBack)
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        initEvent();
    }

    private void initEvent() {
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                finish();
                break;
        }
    }
}
