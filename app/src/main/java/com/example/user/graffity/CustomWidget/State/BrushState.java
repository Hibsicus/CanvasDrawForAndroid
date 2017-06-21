package com.example.user.graffity.CustomWidget.State;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;

import com.example.user.graffity.R;

/**
 * Created by PIPOLE_VR19 on 2017/6/21.
 */

public class BrushState extends CanvasState {
    @Override
    public CanvasState ChangeState(FloatingActionButton changed) {
        changed.setImageResource(R.drawable.eraser_256);
        return new EraserState();
    }

    @Override
    public void HandleState() {

    }

    @Override
    public float GetStateValue() {
        return 0.0f;
    }
}
