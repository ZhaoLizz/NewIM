package cn.bmob.imdemo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.BaseActivity;

public class SearchItemActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {


    @Bind(R.id.search_spinner)
    Spinner mSearchSpinner;
    @Bind(R.id.search_item_name)
    EditText mSearchItemName;
    @Bind(R.id.search_item_time)
    EditText mSearchItemTime;
    @Bind(R.id.search_item_location)
    TextView mSearchItemLocation;
    @Bind(R.id.publish_good_publish_btn)
    Button mPublishGoodPublishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        super.initView();
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.item_array, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSearchSpinner.setAdapter(arrayAdapter);
        mSearchSpinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            //生活用品
            case 0:

                break;
            //校园卡
            case 1:
                break;
            //身份证
            case 2:
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
