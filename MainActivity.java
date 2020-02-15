package com.example.greens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Bundle;
import android.text.Layout;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //  If click
        if (MotionEvent.ACTION_DOWN == event.getAction()) {

            DrawView dds = findViewById(R.id.drawView);
            //  Get view absolute position
            int point[] = new int[2];
            dds.getLocationOnScreen(point);

            //  Get view click position (click position - view position)
            float x = event.getX() - point[0];
            float y = event.getY() - point[1];

            //  Show position
            DrawView drawView = findViewById(R.id.drawView);
            drawView.setClickXY((int)x,(int)y);
        }

        return super.onTouchEvent(event);
    }

    public static void Sleep(int ms){
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
