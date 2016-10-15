package org.vincent.vcshop.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.vincent.vcshop.holder.BaseHolder;
import org.vincent.vcshop.holder.MoreHolder;
import org.vincent.vcshop.manager.ThreadManager;
import org.vincent.vcshop.utils.UIUtils;

import java.util.List;

/**
 * Created by Vincent on 2016/10/15.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

    public ListView mListView;

    public List<T> mDatas;

    private BaseHolder holder;

    public MyBaseAdapter(List<T> data, ListView listView) {
        this.mListView = listView;
        setData(data);
    }

    public void setData(List<T> data) {
        this.mDatas = data;

    }

    public List<T> getData() {
        return mDatas;
    }

    /**
     * 获取到item的数据类型 1 普通数据类型 2特殊条目(加载更多的数据类型)
     *
     */
    // 加载更多的数据类型
    private int MOVE_ITEM_VIEW_TYPE = 0;
    // 加载默认的数据类型
    private int ITEM_VIEW_TPYE = 1;

    private MoreHolder moreHolder;

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return MOVE_ITEM_VIEW_TYPE;
        } else {
            return getInnerItemViewType(position);
        }

    }

    private int getInnerItemViewType(int position) {
        // TODO Auto-generated method stub
        return ITEM_VIEW_TPYE;
    }

    /**
     * 获取到itemview类型的count
     */
    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return super.getViewTypeCount() + 1;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mDatas.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView != null) {
            holder = (BaseHolder) convertView.getTag();
        } else {
            /**
             * 判断当前返回的数据类型是否是加载更多的数据类型
             */
            if (getItemViewType(position) == MOVE_ITEM_VIEW_TYPE) {
                holder = getMoreHolder();
            } else {
                holder = getHolder();
            }

        }
        // 判断当前是否是普通的数据类型。如果是普通的数据类型。那么就设置值
        if (getItemViewType(position) == ITEM_VIEW_TPYE) {
            holder.setData(mDatas.get(position));
        }

        return holder.getRootView();
    }

    /**
     * 加载更多数据
     *
     * @return
     */
    private BaseHolder getMoreHolder() {
        if (moreHolder == null) {
            moreHolder = new MoreHolder(this, hasMore());
        }

        return moreHolder;
    }

    /**
     * 表示有更多的数据
     *
     * @return
     */
    private boolean hasMore() {
        // TODO Auto-generated method stub
        return true;
    }

    public abstract BaseHolder getHolder();

    public boolean isLoading = false;

    /**
     * 从服务器加载更多的数据
     */
    public void onLoadMore() {

        // 防止重复加载
        if (!isLoading) {
            isLoading = true;
            ThreadManager.getLongPool().execute(new Runnable() {
                @Override
                public void run() {
                    final List<T> datas = loadMore();
                    UIUtils.post(new Runnable() {
                        @Override
                        public void run() {
                            if (datas == null) {
                                getMoreHolder().setData(MoreHolder.ERROR);
                            } else if (datas.size() < 20) {
                                getMoreHolder().setData(MoreHolder.NO_MORE);
                            } else {
                                getMoreHolder().setData(MoreHolder.HAS_MORE);
                            }
                            if (datas != null) {
                                if (getData() != null) {
                                    getData().addAll(datas);
                                } else {
                                    setData(datas);
                                }
                            }
                            notifyDataSetChanged();
                            isLoading = false;
                        }
                    });
                }
            });
        }

    }

    protected List loadMore() {
        // TODO Auto-generated method stub
        return null;
    }
}
