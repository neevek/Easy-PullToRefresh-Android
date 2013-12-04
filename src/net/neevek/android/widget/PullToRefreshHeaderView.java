package net.neevek.android.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import net.neevek.android.R;

/**
 * @author neevek <i at neevek.net>
 *
 * The default implementation of a pull-to-refresh header view for OverScrollListView.
 * this can be taken as a reference.
 *
 */
public class PullToRefreshHeaderView extends LinearLayout implements OverScrollListView.PullToRefreshCallback {
    private final static int ROTATE_ANIMATION_DURATION = 300;

    private View mArrowView;
    private TextView mTvRefresh;
    private ProgressBar mProgressBar;

    private Animation mAnimRotateUp;
    private Animation mAnimRotateDown;

    public PullToRefreshHeaderView(Context context) {
        super(context);
        init();
    }

    public PullToRefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToRefreshHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mArrowView = findViewById(R.id.iv_down_arrow);
                mTvRefresh = (TextView)findViewById(R.id.tv_refresh);
                mProgressBar = (ProgressBar)findViewById(R.id.pb_refreshing);

                if (Build.VERSION.SDK_INT >= 16) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

        mAnimRotateUp = new RotateAnimation(0, -180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimRotateUp.setDuration(ROTATE_ANIMATION_DURATION);
        mAnimRotateUp.setFillAfter(true);

        mAnimRotateDown = new RotateAnimation(-180f, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimRotateDown.setDuration(ROTATE_ANIMATION_DURATION);
        mAnimRotateDown.setFillAfter(true);
    }

    /**
     * @param scrollY [screenHeight, 0]
     */
    @Override
    public void onPull(int scrollY) {
    }

    @Override
    public void onReachAboveHeaderViewHeight() {
        mTvRefresh.setText("Release To Refresh");
        mArrowView.startAnimation(mAnimRotateUp);
    }

    @Override
    public void onReachBelowHeaderViewHeight() {
        mTvRefresh.setText("Pull To Refresh");
        mArrowView.startAnimation(mAnimRotateDown);
    }

    @Override
    public void onStartRefreshing() {
        mArrowView.clearAnimation();
        mArrowView.setVisibility(GONE);
        mProgressBar.setVisibility(VISIBLE);
        mTvRefresh.setText("Loading...");
    }

    @Override
    public void onEndRefreshing() {
        mArrowView.setVisibility(VISIBLE);
        mProgressBar.setVisibility(GONE);
        mTvRefresh.setText("Pull To Refresh");
    }
}
