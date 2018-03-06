package cn.bmob.imdemo.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.BaseActivity;
import cn.bmob.imdemo.ui.fragment.SchoolCardFragment;
import cn.bmob.imdemo.ui.fragment.SearchIdCardFragment;
import cn.bmob.imdemo.ui.fragment.SearchLifeFragment;
import cn.bmob.imdemo.ui.fragment.SearchSchoolCardFragment;

public class SearchItemActivity extends BaseActivity {
    private SearchLifeFragment mSearchLifeFragment = SearchLifeFragment.newInstance();
    private SearchIdCardFragment mSearchIdCardFragment = SearchIdCardFragment.newInstance();
    private SearchSchoolCardFragment mSearchSchoolCardFragment = SearchSchoolCardFragment.newInstance();

    @Bind(R.id.search_item_fragment_coninter)
    FrameLayout mSearchItemFragmentConinter;
    @Bind(R.id.search_item_bottom)
    BottomNavigationView mSearchItemBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.search_item_fragment_coninter, mSearchLifeFragment)
                .add(R.id.search_item_fragment_coninter, mSearchIdCardFragment)
                .add(R.id.search_item_fragment_coninter, mSearchSchoolCardFragment)
                .replace(R.id.search_item_fragment_coninter, mSearchLifeFragment)
                .commit();
    }

    @Override
    protected void initView() {
        super.initView();
        mSearchItemBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_life:
                        switchFragment(mSearchLifeFragment);
                        break;
                    case R.id.menu_item_id:
                        switchFragment(mSearchIdCardFragment);
                        break;
                    case R.id.menu_item_school:
                        switchFragment(mSearchSchoolCardFragment);
                        break;
                }
                return true;
            }
        });
    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.search_item_fragment_coninter, fragment)
                .commit();
    }


}
