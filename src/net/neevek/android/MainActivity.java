package net.neevek.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import net.neevek.android.widget.OverScrollListView;

public class MainActivity extends Activity implements View.OnClickListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    private OverScrollListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.btn_done_refreshing).setOnClickListener(this);

        mListView = (OverScrollListView)findViewById(R.id.listview);

        View header = getLayoutInflater().inflate(R.layout.header, null);

        mListView.setPullToRefreshHeaderView(header);
//        mListView.addHeaderView(header);

        mListView.setOnRefreshListener(new OverScrollListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, ">>>> onRefresh called.");
            }

            @Override
            public void onRefreshAnimationEnd() {
                Log.d(TAG, ">>>> onRefreshAnimationEnd called.");
            }
        });

        String[] arr = new String[25];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = "Item " + i;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item, R.id.tv_item, arr);

        mListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_done_refreshing:
                mListView.finishRefreshing();
                break;
        }
    }
}
