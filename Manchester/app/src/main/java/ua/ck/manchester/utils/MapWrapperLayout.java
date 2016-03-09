package ua.ck.manchester.utils;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by Anton Maniskevich on 3/6/15.
 */
public class MapWrapperLayout extends FrameLayout {

	public interface OnDragListener {
		public void onDrag(MotionEvent motionEvent);
	}

	private OnDragListener mOnDragListener;

	public MapWrapperLayout(Context context) {
		super(context);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (mOnDragListener != null) {
			mOnDragListener.onDrag(ev);
		}
		return super.dispatchTouchEvent(ev);
	}

	public void setOnDragListener(OnDragListener mOnDragListener) {
		this.mOnDragListener = mOnDragListener;
	}
}
