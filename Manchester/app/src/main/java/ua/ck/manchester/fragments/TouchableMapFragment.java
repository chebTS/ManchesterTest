package ua.ck.manchester.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

import ua.ck.manchester.utils.MapWrapperLayout;

/**
 * Created by Anton Maniskevich on 3/6/15.
 */
public class TouchableMapFragment extends SupportMapFragment {

	private View mOriginalView;
	private MapWrapperLayout mMapWrapperLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mOriginalView = super.onCreateView(inflater, container, savedInstanceState);

		mMapWrapperLayout = new MapWrapperLayout(getActivity());
		mMapWrapperLayout.addView(mOriginalView);

		return mMapWrapperLayout;
	}

	@Override
	public View getView() {
		return mOriginalView;
	}

	public void setOnDragListener(MapWrapperLayout.OnDragListener onDragListener) {
		mMapWrapperLayout.setOnDragListener(onDragListener);
	}

}
