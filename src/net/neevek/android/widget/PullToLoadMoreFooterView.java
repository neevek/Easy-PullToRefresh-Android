package net.neevek.android.widget;

import android.content.Context;
import android.util.AttributeSet;
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
    private final static int ROTATE_ANIMATION_DURATION = 300;

    private TextView mTvLoadMore;
    private ProgressBar mProgressBar;

    public PullToLoadMoreFooterView(Context context) {
        super(context);
    }

    public PullToLoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToLoadMoreFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void ensuresLoadMoreViewsAvailability() {
        if (mTvLoadMore == null) {
            mTvLoadMore = (TextView)findViewById(R.id.tv_load_more);
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
        mTvLoadMore.setText("Pull to load more");
    }

    @Override
    public void onCancelPulling() {
        ensuresLoadMoreViewsAvailability();
        mTvLoadMore.setText("Load more");
    }

    @Override
    public void onReachAboveRefreshThreshold() {
        mTvLoadMore.setText("Release to load more");
    }

    @Override
    public void onReachBelowRefreshThreshold() {
        onStartPulling();
    }

    @Override
    public void onStartLoadingMore() {
        ensuresLoadMoreViewsAvailability();
        mProgressBar.setVisibility(VISIBLE);
        mTvLoadMore.setText("Loading...");
    }

    @Override
    public void onEndLoadingMore(boolean noMoreToLoad) {
        mProgressBar.setVisibility(GONE);
        mTvLoadMore.setText("Load more");

        if (noMoreToLoad) {
            getChildAt(0).setVisibility(GONE);
        }
    }
}
