package cn.bmob.imdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.BaseFragment;
import cn.bmob.imdemo.bean.IdCardData;
import cn.bmob.imdemo.util.TimeUtil;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by a6100890 on 2018/3/4.
 */

public class SearchIdCardFragment extends BaseFragment {
    @Bind(R.id.search_idcard_name)
    EditText mSearchIdcardName;
    @Bind(R.id.search_idcard_number)
    EditText mSearchIdcardNumber;
    @Bind(R.id.search_idcard_time)
    TextView mSearchIdcardTime;
    @Bind(R.id.search_idcard_location)
    EditText mSearchIdcardLocation;
    @Bind(R.id.search_idcard_search)
    Button mSearchIdcardSearch;


    public static SearchIdCardFragment newInstance() {
        return new SearchIdCardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_idcard, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick(R.id.search_idcard_time)
    public void onTimeClicked() {
        TimeUtil.showDialogPick(getContext(), Calendar.getInstance(), mSearchIdcardTime);
    }


    private void queryIdcard(String name, String number, Date time) {
        BmobQuery<IdCardData> query1 = new BmobQuery<>();
        query1.addWhereEqualTo("number", number);
        BmobQuery<IdCardData> query2 = new BmobQuery<>();
        query2.addWhereEqualTo("name", name);
        List<BmobQuery<IdCardData>> queries = new ArrayList<>();
        queries.add(query1);
        queries.add(query2);
        BmobQuery<IdCardData> mainQuery = new BmobQuery<>();
        mainQuery.or(queries);
        mainQuery.findObjects(new FindListener<IdCardData>() {
            @Override
            public void done(List<IdCardData> list, BmobException e) {
                if (e == null) {
                    Logger.d(Arrays.toString(list.toArray()) + "\n" + list.size());
                } else {
                    Logger.e(e.getMessage());
                }
            }
        });
    }

    @OnClick(R.id.search_idcard_search)
    public void onSearchClicked() {
        String name = mSearchIdcardName.getText().toString();
        String number = mSearchIdcardNumber.getText().toString();
        Date time = Calendar.getInstance().getTime();
        String location = mSearchIdcardLocation.getText().toString();
        queryIdcard(name, number, time);
    }
}
