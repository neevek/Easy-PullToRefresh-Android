package net.neevek.android.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import net.neevek.android.R;

/**
 * @author neevek <i at neevek.net>
 *
 * The default implementation of a pull-to-load-more footer view for OverScrollListView.
 * this can be taken as a reference.
 *
 */
public class PullToLoadMoreFooterView extends FrameLayout implements OverScrollListView.PullToLoadMoreCallback {
    private TextView mTvLoadMore;
    private ProgressBar mProgressBar;

    private String mPullText = "Pull to load more";
    private String mClickText = "Click to load more";
    private String mReleaseText = "Release to load more";
    private String mLoadingText = "Loading...";

    public PullToLoadMoreFooterView(Context context) {
        super(context);
        init();
    }

    public PullToLoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToLoadMoreFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ensuresLoadMoreViewsAvailability();

                if (Build.VERSION.SDK_INT >= 16) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    private void ensuresLoadMoreViewsAvailability() {
        if (mTvLoadMore == null) {
            mTvLoadMore = (TextView)findViewById(R.id.tv_load_more);
            mTvLoadMore.setText(mClickText);
        }

        if (mProgressBar == null) {
            mProgressBar = (ProgressBar)findViewById(R.id.pb_loading);
        }
    }

    @Override
    public void onReset() {
        ensuresLoadMoreViewsAvailability();
        getChildAt(0).setVisibility(VISIBLE);
    }

    @Override
    public void onStartPulling() {
        ensuresLoadMoreViewsAvailability();
        mTvLoadMore.setText(mPullText);
    }

    @Override
    public void onCancelPulling() {
        ensuresLoadMoreViewsAvailability();
        mTvLoadMore.setText(mClickText);
    }

    @Override
    public void onReachAboveRefreshThreshold() {
        mTvLoadMore.setText(mReleaseText);
    }

    @Override
    public void onReachBelowRefreshThreshold() {
        onStartPulling();
    }

    @Override
    public void onStartLoadingMore() {
        ensuresLoadMoreViewsAvailability();
        mProgressBar.setVisibility(VISIBLE);
        mTvLoadMore.setText(mLoadingText);
    }

    @Override
    public void onEndLoadingMore() {
        mProgressBar.setVisibility(GONE);
        mTvLoadMore.setText(mClickText);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        getChildAt(0).setVisibility(visibility);
    }

    public void setPullText(String pullText) {
        mPullText = pullText;
    }

    public void setClickText(String clickText) {
        mClickText = clickText;
    }

    public void setReleaseText(String releaseText) {
        mReleaseText = releaseText;
    }

    public void setLoadingText(String loadingText) {
        mLoadingText = loadingText;
    }
}
