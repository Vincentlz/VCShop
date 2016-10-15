package org.vincent.vcshop.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import org.vincent.vcshop.R;
import org.vincent.vcshop.manager.ThreadManager;
import org.vincent.vcshop.utils.UIUtils;

public abstract class LoadingPage extends FrameLayout {

	/**
	 * 根据状态进行编程： 1 默认值（第一种状态） 2 加载中的状态（第二种） 3 加载失败 4 加载空的状态（成功没有数据） 5
	 * 加载成功（成功有数据）
	 * 
	 * 
	 * 
	 */
	// 默认的状态
	private int STATE_UNLOADED = 1;
	// 2 加载中的状态（第二种）
	private int STATE_LOADING = 2;
	// 3 加载失败
	private int STATE_ERROR = 3;
	// 4 加载空的状态（成功没有数据）
	private int STATE_EMPTY = 4;
	// 5 加载成功（成功有数据）
	private int STATE_SUCCEED = 5;

	// 记录当前的状态
	private int mState;
	private View mLoadingView;
	private View mErrorView;
	private View mEmptyView;
	private View mSuccessView;

	public LoadingPage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	/**
	 * 初始化界面
	 */
	private void init() {
		// 初始化一个默认值
		mState = STATE_UNLOADED;

		mLoadingView = createLoadingView();

		if (null != mLoadingView) {
			addView(mLoadingView, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
		}

		mErrorView = createErroryView();

		if (null != mErrorView) {
			addView(mErrorView, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
		}

		mEmptyView = createEmptyView();

		if (null != mEmptyView) {

			addView(mEmptyView, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));

		}

		showSafePager();

	}

	/**
	 * 展示当前添加进去的界面
	 */
	private void showSafePager() {
		UIUtils.runInMainThread(new Runnable() {

			@Override
			public void run() {
				showPage();

			}
		});

	}

	/**
	 * 展示界面
	 */
	protected void showPage() {

		if (null != mLoadingView) {

			mLoadingView.setVisibility(mState == STATE_UNLOADED
					|| mState == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);

		}

		if (null != mErrorView) {
			mErrorView.setVisibility(mState == STATE_ERROR ? View.VISIBLE
					: View.INVISIBLE);
		}

		if (null != mEmptyView) {
			mEmptyView.setVisibility(mState == STATE_EMPTY ? View.VISIBLE
					: View.INVISIBLE);
		}
		/**
		 * 当前的状态必须是成功的状态
		 */
		if (null == mSuccessView && mState == STATE_SUCCEED) {
			mSuccessView = createSuccessView();
			addView(mSuccessView, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
		}

		if (null != mSuccessView) {
			mSuccessView.setVisibility(mState == STATE_SUCCEED ? View.VISIBLE
					: View.INVISIBLE);
		}

	}

	public abstract View createSuccessView();

	private View createEmptyView() {
		// TODO Auto-generated method stub
		return UIUtils.inflate(R.layout.loading_page_empty);
	}

	private View createErroryView() {
		// TODO Auto-generated method stub
		return UIUtils.inflate(R.layout.loading_page_error);
	}

	private View createLoadingView() {
		return UIUtils.inflate(R.layout.loading_page_loading);
	}

	public LoadingPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoadingPage(Context context) {
		super(context);
		init();
	}

	/**
	 * 对外暴露的第一个方法 一般是加载的状态
	 */
	public void show() {

		if (mState == STATE_ERROR || mState == STATE_EMPTY) {
			mState = STATE_UNLOADED;
		}
		// 确保用户第一次进来看到的就是加载的状态
		if (mState == STATE_UNLOADED) {
			mState = STATE_LOADING;
			LoadTask task = new LoadTask();
			ThreadManager.getLongPool().execute(task);
			// ExecutorService service = Executors.newFixedThreadPool(3);

		}
		showSafePager();
	}

	/**
	 * 从服务器加载数据
	 * 
	 * @author mwqi
	 * 
	 */
	private class LoadTask implements Runnable {

		@Override
		public void run() {
			final LoadResult result = load();
			UIUtils.runInMainThread(new Runnable() {

				@Override
				public void run() {
					
					mState = result.getValue();
					
					showPage();
				}
			});
		}

	}

	// // 3 加载失败
	// private int STATE_ERROR = 3;
	// // 4 加载空的状态（成功没有数据）
	// private int STATE_EMPTY = 4;
	// // 5 加载成功（成功有数据）
	// private int STATE_SUCCEED = 5;
	public enum LoadResult {

		ERROR(3), EMPTY(4), SUCCEED(5);

		int value;

		LoadResult(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

	public abstract LoadResult load();

}
