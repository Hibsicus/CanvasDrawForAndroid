package com.example.user.graffity.CustomWidget.DisplayPackage;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.graffity.CustomWidget.CustomWidget.ColorPicker;
import com.example.user.graffity.CustomWidget.EmailData.SPHelper;
import com.example.user.graffity.CustomWidget.Fragment.CanvasFragment;
import com.example.user.graffity.CustomWidget.State.BrushState;
import com.example.user.graffity.CustomWidget.State.CanvasState;
import com.example.user.graffity.CustomWidget.State.EraserState;
import com.example.user.graffity.R;

import java.util.zip.Inflater;

/**
 * Created by User on 2017/6/8.
 */

public class DisplayActivity extends AppCompatActivity {

    CanvasState CurrentState;

    CanvasFragment canvasFragment;
    
    
    ColorPicker ColorPickerDialog;
    FloatingActionButton Brush_Eraser;
    FloatingActionButton ColorPicker;

    int ChooseColor = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetInit();
        SetEvent();
        ShowCanvas();

//        if(SPHelper.LoadEmailFromSharedPreference(this) != "")
//        {
//            Toast.makeText(this, SPHelper.LoadEmailFromSharedPreference(this), Toast.LENGTH_SHORT).show();
//        }

        Intent intent = getIntent();
        if(intent != null)
        {
            SPHelper.SaveEmailToSharedPreference(this, intent.getStringExtra("Email"));
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus && Build.VERSION.SDK_INT >= 19)
        {
            View DecorView = getWindow().getDecorView();
            DecorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void SetInit()
    {
        CurrentState = new BrushState();
//        mColorPicker = (ColorPicker) findViewById(R.id.color_picker);
//        mColorPicker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
//            @Override
//            public void onColorChanged(int color) {
//
//            }
//        });
        
        Brush_Eraser = (FloatingActionButton) findViewById(R.id.brush_eraser);
        ColorPicker = (FloatingActionButton) findViewById(R.id.color_picker_btn);
        ColorPicker.setVisibility(View.INVISIBLE);
    }
    
    private void SetEvent()
    {
        //筆刷和橡皮擦
        Brush_Eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentState = CurrentState.ChangeState(Brush_Eraser);
                CurrentState.HandleState(canvasFragment.GetChildCanvas());
            }
        });

//        //調色盤
//        ColorPicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //設置Layout和調色盤的點擊事件
//
//                View dialogView = getLayoutInflater().inflate(R.layout.colorpicker_dialog, null);
//                ColorPickerDialog = (ColorPicker) dialogView.findViewById(R.id.color_picker);
//                ColorPickerDialog.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
//                    @Override
//                    public void onColorChanged(int color) {
//                       ChooseColor = color;
//                    }
//                });
//                AlertDialog.Builder dialog = new AlertDialog.Builder(DisplayActivity.this);
//                dialog.setView(dialogView);
//                dialog.setTitle("調色盤");
//                dialog.setCancelable(false);
//                dialog.setPositiveButton("確認", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if(canvasFragment != null)
//                        {
//                            canvasFragment.ChangePaintColor(ChooseColor);
//                        }
//                    }
//                });
//                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                dialog.show();
//            }
//        });
    }

    private void ShowCanvas()
    {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        canvasFragment = new CanvasFragment();
        transaction.replace(R.id.main_layout, canvasFragment);
        transaction.commit();
    }
    

}
