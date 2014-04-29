package net.neevek.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import net.neevek.android.R;

/**
 * Created with IntelliJ IDEA.
 * User: neevek
 * Date: 11/17/13
 * Time: 8:25 PM
 */
public class PullToRefreshHeaderView extends LinearLayout implements OverScrollListView.PullToRefreshCallback {
    private final static int ROTATE_ANIMATION_DURATION = 300;

    private View mArrowView;
    private TextView mTvRefresh;
    private ProgressBar mProgressBar;

    private Animation mAnimRotateUp;
    private Animation mAnimRotateDown;

    private String mPullText = "Pull to refresh";
    private String mReleaseText = "Release to refresh";
    private String mRefreshText = "Refreshing...";

    public PullToRefreshHeaderView(Context context) {
        super(context);
        init();
    }

    public PullToRefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        mAnimRotateUp = new RotateAnimation(0, -180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimRotateUp.setDuration(ROTATE_ANIMATION_DURATION);
        mAnimRotateUp.setFillAfter(true);
        mAnimRotateDown = new RotateAnimation(-180f, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimRotateDown.setDuration(ROTATE_ANIMATION_DURATION);
        mAnimRotateDown.setFillAfter(true);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (mArrowView == null) {
            mArrowView = findViewById(R.id.iv_down_arrow);
            mTvRefresh = (TextView)findViewById(R.id.tv_refresh);
            mProgressBar = (ProgressBar)findViewById(R.id.pb_refreshing);
        }
    }

    @Override
    public void onStartPulling() {
        mProgressBar.setVisibility(GONE);
        mArrowView.setVisibility(VISIBLE);
        mTvRefresh.setVisibility(VISIBLE);
        mTvRefresh.setText(mPullText);
    }

    /**
     * @param scrollY [screenHeight, 0]
     */
    @Override
    public void onPull(int scrollY) {
    }

    @Override
    public void onReachAboveHeaderViewHeight() {
        mProgressBar.setVisibility(GONE);
        mTvRefresh.setText(mReleaseText);
        mArrowView.startAnimation(mAnimRotateUp);
    }

    @Override
    public void onReachBelowHeaderViewHeight() {
        mProgressBar.setVisibility(GONE);
        mTvRefresh.setText(mPullText);
        mArrowView.startAnimation(mAnimRotateDown);
    }

    @Override
    public void onStartRefreshing() {
        mArrowView.clearAnimation();
        mArrowView.setVisibility(GONE);
        mProgressBar.setVisibility(VISIBLE);
        mTvRefresh.setText(mRefreshText);
    }

    @Override
    public void onEndRefreshing() {
        mProgressBar.setVisibility(GONE);
        mTvRefresh.setVisibility(GONE);
    }

    public void setPullText(String pullText) {
        mPullText = pullText;
    }

    public void setReleaseText(String releaseText) {
        mReleaseText = releaseText;
    }

    public void setRefreshText(String refreshText) {
        mRefreshText = refreshText;
    }
}
