package com.example.user.graffity.CustomWidget.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.graffity.CustomWidget.CustomWidget.CanvasDraw;
import com.example.user.graffity.R;

/**
 * Created by User on 2017/6/9.
 */

public class CanvasFragment extends Fragment {
    CanvasDraw mCanvasDraw;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_main,container, false);
        mCanvasDraw = (CanvasDraw) view.findViewById(R.id.main_canvas);

        new Thread(mCanvasDraw).start();

        return view;
    }

    public void ChangePaintColor(int color)
    {
        mCanvasDraw.SetPaintColor(color);
    }

    public CanvasDraw GetChildCanvas()
    {
        return mCanvasDraw;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
