package cn.bmob.imdemo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.BaseFragment;
import cn.bmob.imdemo.bean.PhotoData;
import cn.bmob.imdemo.ui.SearchResultListActivity;
import cn.bmob.imdemo.util.TimeUtil;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by a6100890 on 2018/3/3.
 */

public class SearchLifeFragment extends BaseFragment {
    private Calendar mCalendar = Calendar.getInstance();
    public static final String EXTRA_LIST = "extra_list";

    @Bind(R.id.search_item_name)
    EditText mSearchItemName;
    @Bind(R.id.search_item_time)
    TextView mSearchItemTime;
    @Bind(R.id.search_item_location)
    EditText mSearchItemLocation;
    @Bind(R.id.search_item_btn)
    Button mSearchItemBtn;

    public static SearchLifeFragment newInstance() {
        return new SearchLifeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_life, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.search_item_time)
    public void onTimeClicked() {
        TimeUtil.showDialogPick(getContext(), mCalendar, mSearchItemTime);
    }

    @OnClick(R.id.search_item_btn)
    public void onSearchClicked() {
        String name = mSearchItemName.getText().toString();
        String location = mSearchItemLocation.getText().toString();
        Date time = mCalendar.getTime();

        queryItem(name, TimeUtil.dateToString(time, TimeUtil.FORMAT_DATE_TIME));

    }

    private PhotoData queryItem(String name, String time) {
        BmobQuery<PhotoData> query = new BmobQuery<>();
        query.addWhereEqualTo("itemName", name);
        query.findObjects(new FindListener<PhotoData>() {
            @Override
            public void done(List<PhotoData> list, BmobException e) {
                if (e == null) {
                    Logger.d(Arrays.toString(list.toArray()) + "\n" + list.size());
                    if (list.size() > 1) {
                        Intent intent = new Intent(getContext(), SearchResultListActivity.class);
                        intent.putExtra(EXTRA_LIST, (Serializable)list);
                        startActivity(intent);
                    }
                } else {
                    Logger.e(e.getMessage());
                }
            }
        });
        return null;
    }


}
