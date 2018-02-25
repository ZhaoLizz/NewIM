package com.yyydjk.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


/**
 * Created by dongjunkun on 2015/6/17.
 */
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
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 230);
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
    public void setDropDownMenu(@NonNull List<String> tabTexts, @NonNull List<View> popupViews, @NonNull View contentView, Drawable[] tabDrawable
            , Drawable containerBackground) {
        if (tabTexts.size() != popupViews.size()) {
            throw new IllegalArgumentException("params not match, tabTexts.size() should be equal popupViews.size()");
        }

        for (int i = 0; i < tabTexts.size(); i++) {
            addTab(tabTexts, i, tabDrawable[i]);
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
        if (containerView.getChildAt(2) != null) {
            containerView.removeViewAt(2);
        }

        popupMenuViews = new FrameLayout(getContext());
        popupMenuViews.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 700));
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
        tab.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        tab.setTextColor(textUnselectedColor);
        tab.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnselectedIcon), null);
        tab.setText(tabTexts.get(i));
        tab.setPadding(dpTpPx(5), dpTpPx(12), dpTpPx(5), dpTpPx(12));
        //添加点击事件
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMenu(tab);
            }
        });
        tabMenuView.addView(tab);
        //添加分割线
        if (i < tabTexts.size() - 1) {
            View view = new View(getContext());
            view.setLayoutParams(new LayoutParams(dpTpPx(0.5f), ViewGroup.LayoutParams.MATCH_PARENT));
            view.setBackgroundColor(dividerColor);
            tabMenuView.addView(view);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void addTab(@NonNull List<String> tabTexts, int i, Drawable d) {
        final LinearLayout tab = new LinearLayout(getContext());
        final TextView textViewTop = new TextView(getContext());
        final TextView textViewBottom = new TextView(getContext());
        LayoutParams textParamsB = new LayoutParams(150, 50);
        LayoutParams textParamsT = new LayoutParams(150, 150);
        LayoutParams layoutParams = new LayoutParams(200, 200);
        if (0 == i) {
            layoutParams.setMargins(20, 10, 80, 10);
        }
        if (1 == i) {
            layoutParams.setMargins(80, 10, 80, 10);
        }
        if (2 == i) {
            layoutParams.setMargins(80, 10, 20, 10);
        }
        tab.setLayoutParams(layoutParams);
        tab.setOrientation(VERTICAL);
        tab.setGravity(Gravity.CENTER);
        textViewTop.setLayoutParams(textParamsT);
        textViewBottom.setLayoutParams(textParamsB);
        textViewTop.setBackground(d);
        textViewBottom.setSingleLine();
        textViewBottom.setEllipsize(TextUtils.TruncateAt.END);
        textViewBottom.setGravity(Gravity.CENTER);
        textViewBottom.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
        textViewBottom.setTextColor(textUnselectedColor);
        textViewBottom.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnselectedIcon), null);
        textViewBottom.setText(tabTexts.get(i));
        //添加点击事件
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMenu(textViewBottom);
            }
        });
        tab.addView(textViewTop);
        tab.addView(textViewBottom);
        tabMenuView.addView(tab);

        //添加分割线
        if (i < tabTexts.size() - 1) {
            View view = new View(getContext());
            view.setLayoutParams(new LayoutParams(dpTpPx(0.5f), ViewGroup.LayoutParams.MATCH_PARENT));
            view.setBackgroundColor(dividerColor);
            tabMenuView.addView(view);
        }
    }

    /**
     * 改变tab文字
     *
     * @param text
     */
    public void setTabText(String text) {
        if (current_tab_position != -1) {
            ((TextView) ((LinearLayout) tabMenuView.getChildAt(current_tab_position)).getChildAt(1)).setText(text);
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
            ((TextView)((LinearLayout)tabMenuView.getChildAt(current_tab_position)).getChildAt(1)).setTextColor(textUnselectedColor);
            ((TextView)((LinearLayout)tabMenuView.getChildAt(current_tab_position)).getChildAt(1)).setCompoundDrawablesWithIntrinsicBounds(null, null,
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
                Log.d("Test", "setTabText: " + tabMenuView.getChildCount());
                for (int i = 0; i < tabMenuView.getChildCount(); i = i + 2) {
                    if (target == ((LinearLayout)tabMenuView.getChildAt(i)).getChildAt(1)) {
                        if (current_tab_position == i) {
                            closeMenu();
                        } else {
                            if (current_tab_position == -1) {
                                popupMenuViews.setVisibility(View.VISIBLE);
                                popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_in));
                                maskView.setVisibility(VISIBLE);
                                maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_in));
                                popupMenuViews.getChildAt(i / 2).setVisibility(View.VISIBLE);
                            } else {
                                popupMenuViews.getChildAt(i / 2).setVisibility(View.VISIBLE);
                            }
                            current_tab_position = i;
                            ((TextView)((LinearLayout) tabMenuView.getChildAt(i)).getChildAt(1)).setTextColor(textSelectedColor);
                            ((TextView)((LinearLayout) tabMenuView.getChildAt(i)).getChildAt(1)).setCompoundDrawablesWithIntrinsicBounds(null, null,
                                    getResources().getDrawable(menuSelectedIcon), null);
                        }
                    } else {
                        ((TextView)((LinearLayout) tabMenuView.getChildAt(i)).getChildAt(1)).setTextColor(textUnselectedColor);
                        ((TextView)((LinearLayout) tabMenuView.getChildAt(i)).getChildAt(1)).setCompoundDrawablesWithIntrinsicBounds(null, null,
                                getResources().getDrawable(menuUnselectedIcon), null);
                        popupMenuViews.getChildAt(i / 2).setVisibility(View.GONE);
                    }
                }
            }
        }, 50);

    }

    public int dpTpPx(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm) + 0.5);
    }
}
