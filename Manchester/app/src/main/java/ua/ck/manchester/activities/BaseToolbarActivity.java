package ua.ck.manchester.activities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import ua.ck.manchester.R;

/**
 * Created by cheb on 3/9/16.
 */
public abstract class BaseToolbarActivity extends AppCompatActivity {
    private TextView mTitle;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.setContentView(layoutResID);
        addToolBarView();
    }

    protected void addToolBarView() {
        ViewGroup rootView = (ViewGroup) getWindow().getDecorView().getRootView();
        View inflatedView = LayoutInflater.from(this).inflate(getToolBarLayoutResource(), rootView, false);
        ((ViewGroup) rootView.getChildAt(0)).addView(inflatedView, 0);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setUpViews();
        setupToolbar();
    }

    protected void setupToolbar() {
        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);
        if (coordinatorLayout != null) {
            coordinatorLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTitle = (TextView) findViewById(R.id.txt_title);
        if (mTitle != null)
            mTitle.setText(getTitleResource());

        final ActionBar ab = getSupportActionBar();
        assert ab != null;
        int homeButtonDrawableResource = getHomeButtonDrawableResource();
        if (homeButtonDrawableResource != 0) {
            ab.setHomeAsUpIndicator(addColorFilter(getResources().getDrawable(homeButtonDrawableResource)));
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private Drawable addColorFilter(Drawable drawable) {
        drawable.mutate().setColorFilter(getResources().getColor(R.color.action_bar_title_txt_color), PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    protected abstract void setUpViews();

    /**
     * @return drawable resource which will be used as home button
     */
    protected int getHomeButtonDrawableResource() {
        return R.drawable.back_btn;
    }

    /**
     * @return the string resource which will be used as title
     */
    protected int getTitleResource() {
        return R.string.app_name;
    }

    protected int getToolBarLayoutResource() {
        return R.layout.toolbar_default;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//		TODO remove when will get correct drawables
        if (menu != null && menu.size() > 0) {
            for (int index = 0; index < menu.size(); index++) {
                Drawable newIcon = menu.getItem(index).getIcon();
                addColorFilter(newIcon);
                menu.getItem(index).setIcon(newIcon);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
