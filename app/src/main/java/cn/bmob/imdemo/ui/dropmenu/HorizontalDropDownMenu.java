package cn.bmob.imdemo.ui.dropmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.imdemo.R;


public class HorizontalDropDownMenu extends LinearLayout {

    //顶部菜单布局
    private LinearLayout tabMenuView;
    //底部容器，包含popupMenuViews，maskView
    private FrameLayout containerView;
    //弹出菜单父布局
    private FrameLayout popupMenuViews;
    //遮罩半透明View，点击可关闭DropDownMenu
    private View maskView;
    //tabMenuView里面选中的tab位置，-1表示未选中
    private int current_tab_position = -1;

    //分割线颜色
    private int dividerColor = 0xffffffff;
    //tab选中颜色
    private int textSelectedColor = 0xff890c85;
    //tab未选中颜色
    private int textUnselectedColor = 0xff111111;
    //遮罩颜色
    private int maskColor = 0x88888888;
    //tab字体大小
    private int menuTextSize = 14;

    //tab选中图标
    private int menuSelectedIcon;
    //tab未选中图标
    private int menuUnselectedIcon;

    private float menuHeighPercent = 0.5f;


    public HorizontalDropDownMenu(Context context) {
        super(context, null);
    }

    public HorizontalDropDownMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalDropDownMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(VERTICAL);


        //为DropDownMenu添加自定义属性
        int menuBackgroundColor = 0xffffffff;
        int underlineColor = 0xffcccccc;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HorizontalDropDownMenu);
        underlineColor = a.getColor(R.styleable.HorizontalDropDownMenu_ddunderlineColor, underlineColor);
        dividerColor = a.getColor(R.styleable.HorizontalDropDownMenu_dddividerColor, dividerColor);
        textSelectedColor = a.getColor(R.styleable.HorizontalDropDownMenu_ddtextSelectedColor, textSelectedColor);
        textUnselectedColor = a.getColor(R.styleable.HorizontalDropDownMenu_ddtextUnselectedColor, textUnselectedColor);
        menuBackgroundColor = a.getColor(R.styleable.HorizontalDropDownMenu_ddmenuBackgroundColor, menuBackgroundColor);
        maskColor = a.getColor(R.styleable.HorizontalDropDownMenu_ddmaskColor, maskColor);
        menuTextSize = a.getDimensionPixelSize(R.styleable.HorizontalDropDownMenu_ddmenuTextSize, menuTextSize);
        menuSelectedIcon = a.getResourceId(R.styleable.HorizontalDropDownMenu_ddmenuSelectedIcon, menuSelectedIcon);
        menuUnselectedIcon = a.getResourceId(R.styleable.HorizontalDropDownMenu_ddmenuUnselectedIcon, menuUnselectedIcon);
        menuHeighPercent = a.getFloat(R.styleable.HorizontalDropDownMenu_ddmenuMenuHeightPercent, menuHeighPercent);
        a.recycle();


        //初始化tabMenuView并添加到tabMenuView
        tabMenuView = new LinearLayout(context);
        //LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120);
        tabMenuView.setOrientation(HORIZONTAL);
        tabMenuView.setGravity(Gravity.CENTER);
        tabMenuView.setBackgroundColor(0xffffffff);
        tabMenuView.setLayoutParams(params);
        addView(tabMenuView, 0);


        //为tabMenuView添加下划线
        View underLine = new View(getContext());
        underLine.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpTpPx(1.0f)));
        underLine.setBackgroundColor(underlineColor);
        addView(underLine, 1);

        //初始化containerView并将其添加到DropDownMenu
        containerView = new FrameLayout(context);
        containerView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        addView(containerView, 2);

    }

    /**
     * 初始化DropDownMenu
     *
     * @param tabTexts
     * @param popupViews
     * @param contentView
     */

    /*
    public void setDropDownMenu(@NonNull List<String> tabTexts, @NonNull List<View> popupViews, @NonNull View contentView) {
        if (tabTexts.size() != popupViews.size()) {
            throw new IllegalArgumentException("params not match, tabTexts.size() should be equal popupViews.size()");
        }

        for (int i = 0; i < tabTexts.size(); i++) {
            addTab(tabTexts, i);
        }
        containerView.addView(contentView, 0);

        maskView = new View(getContext());
        maskView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        maskView.setBackgroundColor(maskColor);
        maskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        containerView.addView(maskView, 1);
        maskView.setVisibility(GONE);
        if (containerView.getChildAt(2) != null){
            containerView.removeViewAt(2);
        }

        popupMenuViews = new FrameLayout(getContext());
        popupMenuViews.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (DeviceUtils.getScreenSize(getContext()).y*menuHeighPercent)));
        popupMenuViews.setVisibility(GONE);
        containerView.addView(popupMenuViews, 2);

        for (int i = 0; i < popupViews.size(); i++) {
            popupViews.get(i).setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            popupMenuViews.addView(popupViews.get(i), i);
        }

    }*/
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setDropDownMenu(@NonNull List<String> tabTexts, @NonNull List<RecyclerView> popupViews, @NonNull View contentView, Drawable containerBackground) {
        if (tabTexts.size() != popupViews.size()) {
            throw new IllegalArgumentException("params not match, tabTexts.size() should be equal popupViews.size()");
        }

        for (int i = 0; i < tabTexts.size(); i++) {
            addTab(tabTexts, i);
        }
        CardView searchView = new CardView(getContext());
        final LinearLayout searchLinearLayout = new LinearLayout(getContext());
        ImageView searchIcon = new ImageView(getContext());
        final EditText searchEditText = new EditText(getContext());

        LayoutParams cardParams = new LayoutParams(0, CardView.LayoutParams.MATCH_PARENT, 1.5f);
        LayoutParams linearParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        LayoutParams searchIconParams = new LayoutParams(80, LayoutParams.MATCH_PARENT);
        LayoutParams searchEditTextParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);


        searchIcon.setImageDrawable(getResources().getDrawable(R.drawable.list_dorp_menu_search_icon));
        searchIcon.setLayoutParams(searchIconParams);
        searchEditText.setHint(R.string.list_drop_menu_search);
        searchEditText.setLayoutParams(searchEditTextParams);
        searchEditText.setTextSize(8);
        searchEditText.setBackground(null);

        searchLinearLayout.setOrientation(HORIZONTAL);
        linearParams.setMargins(5, 0, 0, 0);
        searchLinearLayout.setLayoutParams(linearParams);
        searchIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), searchEditText.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        searchLinearLayout.addView(searchIcon);
        searchLinearLayout.addView(searchEditText);

        cardParams.setMargins(5, 15, 5, 15);
        searchView.setLayoutParams(cardParams);
        searchView.setRadius(10);
        searchView.setLayoutParams(cardParams);
        searchView.addView(searchLinearLayout);

        tabMenuView.addView(searchView);
        /*---------------------*/
        containerView.addView(contentView, 0);

        maskView = new View(getContext());
        maskView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        maskView.setBackgroundColor(maskColor);
        maskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        containerView.addView(maskView, 1);
        maskView.setVisibility(GONE);
        if (containerView.getChildAt(2) != null) {
            containerView.removeViewAt(2);
        }

        popupMenuViews = new FrameLayout(getContext());
        popupMenuViews.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 550));
        /*
        -------------------------------------------------------------------
         */
        popupMenuViews.setVisibility(GONE);
        popupMenuViews.setBackground(containerBackground);
        containerView.addView(popupMenuViews, 2);

        for (int i = 0; i < popupViews.size(); i++) {
            ViewGroup.LayoutParams v = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            popupViews.get(i).setLayoutParams(v);
            popupMenuViews.addView(popupViews.get(i), i);
        }

    }

    private void addTab(@NonNull List<String> tabTexts, int i) {
        final TextView tab = new TextView(getContext());
        tab.setSingleLine();
        tab.setEllipsize(TextUtils.TruncateAt.END);
        tab.setGravity(Gravity.CENTER);
        tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
        tab.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        tab.setTextColor(textUnselectedColor);
        tab.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnselectedIcon), null);
        tab.setText(tabTexts.get(i));
        tab.setPadding(dpTpPx(5), dpTpPx(12), dpTpPx(5), dpTpPx(12));
        //添加点击事件
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMenu(tab);
                Log.d("Test tab txt", tab.getText() + "");
            }
        });
        tabMenuView.addView(tab);
        //添加分割线
        /*

        if (i < tabTexts.size() - 1) {
            View view = new View(getContext());
            view.setLayoutParams(new LayoutParams(dpTpPx(0.5f), ViewGroup.LayoutParams.MATCH_PARENT));
            view.setBackgroundColor(dividerColor);
            tabMenuView.addView(view);
        }

         */
    }

    /**
     * 改变tab文字
     *
     * @param text
     */
    public void setTabText(String text) {
        if (current_tab_position != -1) {
            ((TextView) (tabMenuView.getChildAt(current_tab_position))).setText(text);
            Log.d("Test", "setTabText: " + ((LinearLayout) tabMenuView.getChildAt(current_tab_position)).getChildCount());
        }
    }

    public void setTabClickable(boolean clickable) {
        for (int i = 0; i < tabMenuView.getChildCount(); i = i + 2) {
            tabMenuView.getChildAt(i).setClickable(clickable);
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (current_tab_position != -1) {
            ((TextView) (tabMenuView.getChildAt(current_tab_position))).setTextColor(textUnselectedColor);
            ((TextView) (tabMenuView.getChildAt(current_tab_position))).setCompoundDrawablesWithIntrinsicBounds(null, null,
                    getResources().getDrawable(menuUnselectedIcon), null);
            popupMenuViews.setVisibility(View.GONE);
            popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_out));
            maskView.setVisibility(GONE);
            maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_out));
            current_tab_position = -1;
        }

    }

    /**
     * DropDownMenu是否处于可见状态
     *
     * @return
     */
    public boolean isShowing() {
        return current_tab_position != -1;
    }

    /**
     * 切换菜单
     *
     * @param target
     */
    private Handler mHandler = new Handler();

    private void switchMenu(final View target) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println(current_tab_position);

                for (int i = 0; i < tabMenuView.getChildCount() - 1; i = i + 1) {
                    if (target == (TextView) tabMenuView.getChildAt(i)) {
                        if (current_tab_position == i) {
                            closeMenu();
                        } else {
                            Log.d("test i", "run: " + i);
                            if (current_tab_position == -1) {
                                Log.d("Test", "run: -1-1");
                                popupMenuViews.setVisibility(View.VISIBLE);
                                popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_in));
                                maskView.setVisibility(VISIBLE);
                                maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_in));
                                popupMenuViews.getChildAt(i).setVisibility(View.VISIBLE);
                            } else {
                                popupMenuViews.getChildAt(i).setVisibility(View.VISIBLE);
                            }
                            current_tab_position = i;
                            ((TextView) (tabMenuView.getChildAt(i))).setTextColor(textSelectedColor);
                            ((TextView) (tabMenuView.getChildAt(i))).setCompoundDrawablesWithIntrinsicBounds(null, null,
                                    getResources().getDrawable(menuSelectedIcon), null);
                        }
                    } else {
                        ((TextView) (tabMenuView.getChildAt(i))).setTextColor(textUnselectedColor);
                        ((TextView) (tabMenuView.getChildAt(i))).setCompoundDrawablesWithIntrinsicBounds(null, null,
                                getResources().getDrawable(menuUnselectedIcon), null);
                        popupMenuViews.getChildAt(i).setVisibility(View.GONE);
                    }
                }
                Log.d("Test out", current_tab_position + "");
            }
        }, 50);

    }

    public int dpTpPx(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm) + 0.5);
    }
}
