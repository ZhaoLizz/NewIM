package cn.bmob.imdemo.ui.fragment;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.adapter.ListAdapter;
import cn.bmob.imdemo.adapter.ListDropMenuKindAdapter;
import cn.bmob.imdemo.adapter.ListDropMenuSortAdapter;
import cn.bmob.imdemo.base.ParentWithNaviFragment;
import cn.bmob.imdemo.bean.Things;
import cn.bmob.imdemo.ui.dropmenu.HorizontalDropDownMenu;

/**
 * Created by Rui on 2018/2/24.
 */

public class ListFragment extends ParentWithNaviFragment {
//    @Bind(R.id.rc_view)
//    RecyclerView rc_view;

    private Things testThing;
    private List<Things> mThingsList = new ArrayList<Things>();

    @Bind(R.id.list_fragment_horizontaldropdownmenu)
    HorizontalDropDownMenu mHorizontalDropDownMenu;

    private ListDropMenuKindAdapter listDropMenuKindAdapter;
    private ListDropMenuSortAdapter listDropMenuSortAdapter;
    private RecyclerView mContextView;
    private String mHeaders[] = {"种类选择", "排序选择"};
    private String mKinds[] = {"电子设备", "床上用品", "书类"};
    private String mSorts[] = {"按时间", "按地点", "按种类"};
    private List<RecyclerView> mPopipViews = new ArrayList<>();


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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (View) inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, rootView);

        testThing = new Things("无线鼠标", "电子设备", "2018.2.25",
                "中北厕所坑内", BitmapFactory.decodeResource(container.getResources(), R.drawable.test));
        initView();
        initNaviView();
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
//        rc_view.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rc_view.setNestedScrollingEnabled(false);


        RecyclerView mKindsView = new RecyclerView(getContext());
        listDropMenuKindAdapter = new ListDropMenuKindAdapter(Arrays.asList(mKinds), getContext());
        mKindsView.setAdapter(listDropMenuKindAdapter);
        mKindsView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView mSortView = new RecyclerView(getContext());
        listDropMenuSortAdapter = new ListDropMenuSortAdapter(Arrays.asList(mSorts), getContext());
        mSortView.setAdapter(listDropMenuSortAdapter);
        mSortView.setLayoutManager(new LinearLayoutManager(getContext()));

        mPopipViews.add(mKindsView);
        mPopipViews.add(mSortView);

        mContextView = new RecyclerView(getContext());
        mContextView.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mContextView.setLayoutManager(new LinearLayoutManager(getContext()));
        mContextView.setNestedScrollingEnabled(false);
        mContextView.setBackground(getResources().getDrawable(R.drawable.test));
        for (int i = 0; i < 20; i++) {
            mThingsList.add(testThing);
        }

        ListAdapter adapter = new ListAdapter(mThingsList, getContext());
        mContextView.setAdapter(adapter);
        mHorizontalDropDownMenu.setDropDownMenu(Arrays.asList(mHeaders), mPopipViews, mContextView
                , getResources().getDrawable(R.drawable.test, getActivity().getTheme()));


    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
