package com.sky31.gonggong.module.swzl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sky31.gonggong.R;
import com.sky31.gonggong.base.BaseActivity;
import com.sky31.gonggong.config.CommonFunction;
import com.sky31.gonggong.model.LostAndFoundModel;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SwzlDetailActivity extends BaseActivity {

    @Bind(R.id.swzl_publish_toolbar)
    Toolbar swzlPublishToolbar;
    @Bind(R.id.swzl_detail_title)
    TextView swzlDetailTitle;
    @Bind(R.id.swzl_detail_announcer)
    TextView swzlDetailAnnouncer;
    @Bind(R.id.swzl_detail_mobile)
    TextView swzlDetailMobile;
    @Bind(R.id.swzl_detail_publish_time)
    TextView swzlDetailPublishTime;
    @Bind(R.id.swzl_detail_location)
    TextView swzlDetailLocation;
    @Bind(R.id.swzl_detail_bank_card)
    TextView swzlDetailBankCard;
    @Bind(R.id.swzl_detail_description)
    TextView swzlDetailDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swzl_detail);
        ButterKnife.bind(this);


        initToolbar();
        initView();

    }

    private void initView() {
        Intent intent = getIntent();
        LostAndFoundModel model = (LostAndFoundModel) intent.getSerializableExtra("model");
        swzlDetailAnnouncer.append(model.getAnnouncer());
        swzlDetailPublishTime.append(CommonFunction.ms2Timestamp(model.getCreated_at()));
        swzlDetailMobile.append(model.getMobile());
        swzlDetailLocation.append(model.getLocation());
        swzlDetailDescription.append(model.getDescription());
        swzlDetailTitle.setText(model.getThings());
        //如果丢失物品为校园卡，那么设置校园卡账号那栏可见。

        Log.w("bank_card",model.getThings_type());
        if (model.getThings_type().equals("1")){

            swzlDetailBankCard.setVisibility(View.VISIBLE);
            swzlDetailBankCard.append(model.getBank_card());
        }
        else {
            swzlDetailBankCard.setVisibility(View.GONE);
        }
    }


    private void initToolbar() {

        swzlPublishToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
