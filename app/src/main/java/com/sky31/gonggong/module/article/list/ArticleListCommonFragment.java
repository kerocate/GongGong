package com.sky31.gonggong.module.article.list;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sky31.gonggong.R;
import com.sky31.gonggong.config.CommonFunction;
import com.sky31.gonggong.model.ArticleListModel;
import com.sky31.gonggong.module.article.ArticlePresenter;
import com.sky31.gonggong.module.article.detail.ArticleDetailActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArticleListCommonFragment extends Fragment implements ArticleListView{

    @Bind(R.id.article_listview)
    ListView articleListview;


    private ArticleListModel model;
    private static final String ARG_STR = "title";
    private String initTitle;
    private  ArticleListQuery query;

    public ArticleListCommonFragment() {



    }


    public static ArticleListCommonFragment newInstance(String title){

        ArticleListCommonFragment fragment = new ArticleListCommonFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_STR,title);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            initTitle = getArguments().getString(ARG_STR);
        }


        //initData();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //int w = 'x'-'y';
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        ButterKnife.bind(this, view);

        query = new ArticleListQuery();
        initData();

        //listAdapter = new ArticleActivityListAdapter(getActivity(),model);



        return view;
    }

    private void initData() {

        query.setCatname(initTitle);
        query.setLimit(50);
        query.setCheckID(0);
        query.setOrder("id DESC");
        ArticlePresenter presenter = new ArticlePresenter(this);
        presenter.initReqService(query.getHashMap());
        presenter.getDatas();

    }

    @Override
    public void getAriticleList(int code ,ArticleListModel model) {

        switch (code){
            case 0:
                this.model = model;
                setData();
                break;
            default:
                CommonFunction.errorToast(getActivity().getApplicationContext(), code);
        }

    }

    //加载 ListView
    public void setData(){


        if (initTitle.equals("活动日历")){
            ArticleActivityListAdapter datapter = new ArticleActivityListAdapter(getActivity(),model);
            articleListview.setDividerHeight(0);
            articleListview.setAdapter(datapter);
            datapter.notifyDataSetChanged();
        }
        else {
            model.getMsg();
            Log.d("list_fragment",model.getMsg());
            Log.d("list_fragment",model.getData().size()+"");

            articleListview.setHeaderDividersEnabled(false);

            articleListview.setDividerHeight(10);
            ArticleCommonListAdapter adapter = new ArticleCommonListAdapter(getActivity(),model.getData());
            articleListview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        articleListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String url = model.getData().get(position).getUrl();

                Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("title","文章详情");
                startActivity(intent);

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
