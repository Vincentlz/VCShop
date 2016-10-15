package org.vincent.vcshop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.vincent.vcshop.utils.UIUtils;
import org.vincent.vcshop.widget.LoadingPage;
import org.vincent.vcshop.widget.LoadingPage.LoadResult;

import java.util.List;

public abstract class BaseFragment extends Fragment {

	private LoadingPage mContentPage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentPage = new LoadingPage(UIUtils.getContext()) {

			@Override
			public View createSuccessView() {
				// TODO Auto-generated method stub
				return BaseFragment.this.createSuccessView();
			}

			@Override
			public LoadResult load() {
				// TODO Auto-generated method stub
				return BaseFragment.this.load();
			}
		};
		return mContentPage;
	}

	protected abstract LoadResult load() ;

	protected abstract View createSuccessView() ;

	/**
	 * 检查服务器返回的数据
	 * @param object
	 * @return
	 */
	protected LoadResult check(Object object) {
		//判断服务器数据是否为null
		if(null == object){
			return LoadResult.ERROR;
		}
		
		if(object instanceof List){
			
			List list = (List) object;
			
			if(list.size() == 0){
				return LoadResult.EMPTY;
			}
			
		}
		
		return LoadResult.SUCCEED;
	}

	
	/**
	 * 展示当前界面
	 */
	public void show() {
		if (null != mContentPage) {
			mContentPage.show();
		}

	}

}
