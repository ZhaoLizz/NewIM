package cn.bmob.imdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviFragment;

/**
 * Created by Rui on 2018/2/24.
 */

public class ListFragment extends ParentWithNaviFragment {
    @Bind(R.id.rc_view)
    RecyclerView rc_view;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    protected String title() {
        return "列表";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= (View) inflater.inflate(R.layout.fragment_list, container, false);
        initView();
        return rootView;
    }

    private void initView() {

    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
