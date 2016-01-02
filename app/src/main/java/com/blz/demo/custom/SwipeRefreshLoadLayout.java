package com.blz.demo.custom;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

import com.blz.demo.R;

/**
 * Created by 卜令壮
 * on 2016/1/2
 * E-mail q137617549@qq.com
 */
public class SwipeRefreshLoadLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener {
    private static final String TAG = SwipeRefreshLoadLayout.class.getSimpleName();
    private ListView mListView;
    private float rawYDown;
    private float rawYMove;
    private boolean isLoading = false;
    private int mTouchSlop;
    private View mListViewFooter;
    private OnLoadListener mOnLoadListener;

    public SwipeRefreshLoadLayout(Context context) {
        this(context,null);
    }

    public SwipeRefreshLoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mListViewFooter = LayoutInflater.from(context).inflate(R.layout.listview_footer, null, false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //初始化ListView
        if (mListView == null) {
            getListView();
        }
    }

    public void getListView() {
        int childCount = getChildCount();
        if (childCount>0){
            View chlidView = getChildAt(0);
            if (chlidView instanceof ListView){
                mListView = (ListView) chlidView;
                mListView.setOnScrollListener(this);
                Log.e(TAG, "获取到ListView");
            }
        }
    }
    //获取手指按下的状态
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                rawYDown = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                rawYMove = ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                //判断可不可以加载，可以的话加载数据
                if (canLoad()){
                    loadData();
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    //加载数据
    private void loadData() {
        if (mOnLoadListener != null) {
            setLoading(true);
            mOnLoadListener.onLoad();
        }
    }

    private boolean canLoad() {
        //判断是否可以加载更多
        return isBottom() && !isLoading && isPullUp();
    }

    //ListView的滚动事件监听
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //判断滚动到最底部加载更多
        if (canLoad()){
            loadData();
        }
    }

    //判断是不是当前adapter中的最后面一个
    public boolean isBottom() {
        boolean ret = false;
        if (mListView != null && mListView.getAdapter() != null){
            int lastVisiblePosition = mListView.getLastVisiblePosition();
            int adapterLast = mListView.getAdapter().getCount() - 1;
            ret = (lastVisiblePosition == adapterLast);
        }
        return ret;
    }

    //根据滚动事件获取的手指操作，判断是不是上拉操作
    public boolean isPullUp() {
        Log.e(TAG, "判断是否溢出：按下"+rawYDown+"，移动"+rawYMove+"，溢出"+mTouchSlop);
        return (rawYDown-rawYMove)>= mTouchSlop;
    }

    public void setmOnLoadListener(OnLoadListener mOnLoadListener) {
        this.mOnLoadListener = mOnLoadListener;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading){
            mListView.addFooterView(mListViewFooter);
        }else{
            if (mListView != null) {
                mListView.removeFooterView(mListViewFooter);
            }

            rawYDown = 0;
            rawYMove = 0;
        }
    }

    public interface OnLoadListener{
         void onLoad();
    }
}
