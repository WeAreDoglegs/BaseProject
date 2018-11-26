package com.doglegs.core.helper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * desc: 拖拽、侧滑删除
 */
public class SimpleDragCallback extends ItemTouchHelper.Callback {

    private OnSimpleDragLisener onSimpleDragLisener;

    public SimpleDragCallback(OnSimpleDragLisener onSimpleDragLisener) {
        this.onSimpleDragLisener = onSimpleDragLisener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            //final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;//代表处理滑动删除,将执行onSwiped()方法   代表支持哪个方向的滑动删除
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = 0;//为0 代表不处理滑动删除
            //final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;//代表处理滑动删除,将执行onSwiped()方法
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return onSimpleDragLisener.onMove(recyclerView, viewHolder, target);
    }

    /**
     * 当item被左右滑动时调用
     *
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        onSimpleDragLisener.onSwip(viewHolder, direction);
    }

    /**
     * 当Item被长按的时候是否可以被拖拽
     * 相当于长按后执行了：mItemTouchHelper.startDrag(holder);//开始拖动
     *
     * @return 返回true：长按item时，就可以实现拖动效果
     * 返回false：长按item事件就失效了，也就不能直接实现拖动效果了
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }


    /**
     * 左右滑动时是否可以执行onSwiped()方法
     *
     * @return 返回true:代表左右滑动item时，可以删除
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * 开始拖拽时调用
     *
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
        }
        super.onSelectedChanged(viewHolder, actionState);
    }


    /**
     * 拖拽结束时调用
     *
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
    }

    public interface OnSimpleDragLisener {
        void onSwip(RecyclerView.ViewHolder viewHolder, int direction);

        boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target);
    }

}