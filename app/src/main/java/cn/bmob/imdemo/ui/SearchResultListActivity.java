package cn.bmob.imdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.adapter.ListLeftPhotoAdapter;
import cn.bmob.imdemo.base.BaseActivity;
import cn.bmob.imdemo.bean.PhotoData;
import cn.bmob.imdemo.ui.fragment.SearchLifeFragment;

/**
 * 只用于显示搜到多个生活用品的情况
 */
public class SearchResultListActivity extends BaseActivity {
    @Bind(R.id.search_result_list_rv)
    RecyclerView mSearchResultListRv;
    ListLeftPhotoAdapter photoAdapter;
    List<PhotoData> mPhotoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        mPhotoList = (List<PhotoData>) getIntent().getSerializableExtra(SearchLifeFragment.EXTRA_LIST);

        if (mPhotoList != null) {
            photoAdapter = new ListLeftPhotoAdapter(mPhotoList, this);
            mSearchResultListRv.setLayoutManager(new LinearLayoutManager(this));
            mSearchResultListRv.setAdapter(photoAdapter);
        } else {
            Logger.e("list is null!");
        }
    }

    @Override
    protected void initView() {
        super.initView();

    }
}
