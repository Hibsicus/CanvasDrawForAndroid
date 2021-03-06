package com.example.user.graffity.CustomWidget.State;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;

import com.example.user.graffity.CustomWidget.CustomWidget.CanvasDraw;
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
    public void HandleState(CanvasDraw mCanvasDraw) {
        mCanvasDraw.ChangePaint(CanvasDraw.PAINT_ERASER_PAINT);
    }

    @Override
    public float GetStateValue() {
        return 1.0f;
    }
}
