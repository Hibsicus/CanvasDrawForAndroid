package com.example.user.graffity.CustomWidget.State;

import android.support.design.widget.FloatingActionButton;

import com.example.user.graffity.R;

/**
 * Created by PIPOLE_VR19 on 2017/6/21.
 */

public class EraserState extends CanvasState {
    @Override
    public CanvasState ChangeState(FloatingActionButton changed) {
        changed.setImageResource(R.drawable.brush_256);
        return new BrushState();
    }

    @Override
    public void HandleState() {

    }

    @Override
    public float GetStateValue() {
        return 1.0f;
    }
}
