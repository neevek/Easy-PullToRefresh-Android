package net.neevek.android;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import net.neevek.android.widget.OverScrollListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OverScrollListView.OnRefreshListener, OverScrollListView.OnLoadMoreListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    private OverScrollListView mListView;

    private List<String> mDataList;
    private ArrayAdapter<String> mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mListView = (OverScrollListView)findViewById(R.id.listview);

        View header = getLayoutInflater().inflate(R.layout.header, null);
        View footer = getLayoutInflater().inflate(R.layout.footer, null);

        mListView.setPullToRefreshHeaderView(header);
        mListView.addHeaderView(getLayoutInflater().inflate(R.layout.footer, null));
        mListView.addHeaderView(getLayoutInflater().inflate(R.layout.footer, null));
        mListView.addHeaderView(getLayoutInflater().inflate(R.layout.footer, null));

        mListView.addFooterView(getLayoutInflater().inflate(R.layout.header, null));
        mListView.addFooterView(getLayoutInflater().inflate(R.layout.header, null));
        mListView.addFooterView(getLayoutInflater().inflate(R.layout.header, null));
        mListView.setPullToLoadMoreFooterView(footer);

        mListView.setOnRefreshListener(this);
        mListView.setOnLoadMoreListener(this);

        mDataList = new ArrayList<String>();
        mAdapter = new ArrayAdapter<String>(this, R.layout.item, R.id.tv_item, mDataList);

        mListView.setAdapter(mAdapter);

        initData();
    }

    private void initData() {
        if (mDataList == null) {
            mDataList = new ArrayList<String>();
        } else {
            mDataList.clear();
        }

        for (int i = 0; i < 25; ++i) {
            mDataList.add("Item " + i);
        }

        mAdapter.notifyDataSetChanged();

        if (mDataList.size() > 0 && !mListView.isLoadingMoreEnabled()) {
            mListView.enableLoadMore(true);
        }
    }

    @Override
    public void onLoadMore() {
        new Thread(){
            @Override
            public void run() {
                SystemClock.sleep(1000);

                mListView.post(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0, j = mDataList.size(); i < 10; ++i, ++j) {
                            mDataList.add("Item " + j);
                        }
                        mAdapter.notifyDataSetChanged();

                        boolean reachTheEnd = mDataList.size() >= 55;
                        mListView.finishLoadingMore();
                        if (reachTheEnd) {
                            mListView.enableLoadMore(false);
                            Toast.makeText(MainActivity.this, "Reach the end of the list, no more data to load.", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        }.start();
    }

    @Override
    public void onRefresh() {
        new Thread(){
            @Override
            public void run() {
                SystemClock.sleep(2000);

                mListView.post(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        mListView.finishRefreshing();
                        mListView.resetLoadMoreFooterView();
                    }
                });
            }
        }.start();
    }

    @Override
    public void onRefreshAnimationEnd() {
    }
}
