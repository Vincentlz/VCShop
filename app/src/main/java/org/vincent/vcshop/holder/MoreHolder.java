package org.vincent.vcshop.holder;

import android.view.View;
import android.widget.RelativeLayout;

import org.vincent.vcshop.R;
import org.vincent.vcshop.adapter.MyBaseAdapter;
import org.vincent.vcshop.utils.UIUtils;

import static android.view.View.*;

/**
 * Created by Vincent on 2016/10/15.
 */
public class MoreHolder extends BaseHolder<Integer> implements OnClickListener {

    /**
     * 1 有更多的数据 2 没有更多的数据 3返回失败
     *
     * @param hasMore
     */
    // * 1 有更多的数据
    public static final int HAS_MORE = 1;
    // * 2 没有更多的数据
    public static final int NO_MORE = 2;
    // * 3返回失败
    public static final int ERROR = 3;
    private RelativeLayout rl_more_loading;
    private RelativeLayout rl_more_error;

    public MyBaseAdapter adapter;


    public MoreHolder(MyBaseAdapter myBaseAdapter, boolean hasMore) {
        setData(hasMore?HAS_MORE:NO_MORE);
        this.adapter = myBaseAdapter;
    }



    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_more_loading);

        rl_more_loading = (RelativeLayout) view.findViewById(R.id.rl_more_loading);

        rl_more_error = (RelativeLayout) view.findViewById(R.id.rl_more_error);

        rl_more_error.setOnClickListener(this);

        return view;
    }

    @Override
    public void refreView() {
        Integer data = getData();

        rl_more_loading.setVisibility(data == HAS_MORE ? VISIBLE : GONE);

        rl_more_error.setVisibility(data == ERROR ? VISIBLE : GONE);

    }

    @Override
    public View getRootView() {
        if(getData() == HAS_MORE){
            loadMore();
        }
        return super.getRootView();
    }

    @Override
    public void onClick(View v) {
        loadMore();

    }

    public void loadMore() {
        adapter.onLoadMore();

    }
}
