package com.example.user.graffity.CustomWidget.State;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;

import com.example.user.graffity.CustomWidget.CustomWidget.CanvasDraw;

/**
 * Created by PIPOLE_VR19 on 2017/6/21.
 */

public abstract class CanvasState {
    int OriginColor;
    abstract public CanvasState ChangeState(FloatingActionButton changed);
    abstract public void HandleState(CanvasDraw mCanvasDraw);
    abstract public float GetStateValue();
}
