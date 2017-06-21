package com.example.user.graffity.CustomWidget.State;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;

/**
 * Created by PIPOLE_VR19 on 2017/6/21.
 */

public abstract class CanvasState {
    abstract public CanvasState ChangeState(FloatingActionButton changed);
    abstract public void HandleState();
    abstract public float GetStateValue();
}
