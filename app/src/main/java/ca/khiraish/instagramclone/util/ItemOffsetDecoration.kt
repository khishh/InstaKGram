package ca.khiraish.instagramclone.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class ItemOffsetDecoration: RecyclerView.ItemDecoration{

    private var mItemOffset: Int

    constructor(mItemOffset: Int) : super() {
        this.mItemOffset = mItemOffset
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position: Int = parent.getChildLayoutPosition(view)

        val manager = parent.layoutManager

        if (manager != null && position < manager.childCount) outRect.top = mItemOffset

        if (position % 2 == 0) {
            outRect.right = mItemOffset / 2 // Right offset for left child
        } else {
            outRect.left = mItemOffset / 2 // Left offset for Right child
        }

        outRect.bottom = mItemOffset
    }
}