package cn.bmob.imdemo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.BaseActivity;
import cn.bmob.imdemo.constant.Constants;
import cn.bmob.imdemo.ui.fragment.MessageLifeFragment;

public class ItemMessageActivity extends BaseActivity {
    private MessageLifeFragment mMessageLifeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_message);


        mMessageLifeFragment = new MessageLifeFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.item_message_fragment_container, mMessageLifeFragment)
                .commit();

        switch (getIntent().getStringExtra("category")) {
            case Constants.CATEGORY_PHOHO_DATA:
                switchFragment(mMessageLifeFragment);
                break;

        }

    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.search_item_fragment_coninter, fragment)
                .commit();
    }
}
