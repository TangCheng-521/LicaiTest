package com.tang.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tang.licaidemo.R;

public class LoadMoreListView extends ListView implements OnScrollListener {
    private View footer;

    private int totalItem;
    private int lastItem;

    private boolean isLoading;

    private OnLoadMore onLoadMore;

    private LayoutInflater inflater;
    private ProgressBar pb_load;
    private TextView tv_load;
    private boolean noMore;

    public LoadMoreListView(Context context) {
        super(context);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setNoMore(boolean noMore) {
        this.noMore = noMore;
    }

    @SuppressLint("InflateParams")
    private void init(Context context) {
        inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.load_more_footer, null);
        pb_load = (ProgressBar) footer.findViewById(R.id.pb_load);
        tv_load = (TextView) footer.findViewById(R.id.tv_load);
        footer.setVisibility(View.GONE);
        this.addFooterView(footer);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.i("onScroll", firstVisibleItem + "，" + visibleItemCount + "，" + totalItemCount);
        this.lastItem = firstVisibleItem + visibleItemCount;
        this.totalItem = totalItemCount;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Log.i("onScrollStateChanged", scrollState + "");
        if (this.totalItem == lastItem && scrollState == SCROLL_STATE_IDLE) {
            Log.i("onScrollStateChanged", "yes");
            if (!isLoading) {
                if (noMore) {
                    tv_load.setText(R.string.no_more_data);
                    pb_load.setVisibility(View.GONE);
                } else {
                    tv_load.setText(R.string.loading);
                    pb_load.setVisibility(View.VISIBLE);
                }
                isLoading = true;
                footer.setVisibility(View.VISIBLE);
                if (!noMore) {
                    onLoadMore.loadMore();
                }
            }
        }
    }

    public void setLoadMoreListen(OnLoadMore onLoadMore) {
        this.onLoadMore = onLoadMore;
    }

    /**
     * 加载完成调用此方法
     */
    public void onLoadComplete() {
        footer.setVisibility(View.GONE);
        isLoading = false;

    }

    public interface OnLoadMore {
        void loadMore();
    }


}
