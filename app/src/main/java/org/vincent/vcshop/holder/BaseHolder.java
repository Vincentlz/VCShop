package org.vincent.vcshop.holder;

import android.view.View;

/**
 * Created by Vincent on 2016/10/15.
 */
public abstract class BaseHolder<T> {

    private View mRootView;

    public T mData;

    public BaseHolder() {
        mRootView = initView();
        mRootView.setTag(this);
    }

    public abstract View initView() ;

    public void setData(T data){
        this.mData = data;
        refreView();
    }

    public View getRootView(){
        return mRootView;
    }

    public abstract void refreView();

    public T getData(){
        return mData;
    }
}
