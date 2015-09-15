package com.xian.myapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xian.myapp.R;
import com.xian.myapp.base.BaseActivity;

public class MyViewActivity extends BaseActivity {

    Paint paint=new Paint();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView view=new MyView(mContext);
        setContentView(view);

        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setTextSize(30);
        paint.setStrokeWidth(20);
    }




    @Override
    protected void onResume() {
        super.onResume();
    }


    class MyView extends View{

        public MyView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawText("xxxxxxxxxxxxxx", 30, 30, paint);
            canvas.drawLine(0, 0, 1000, 1000, paint);
            canvas.drawColor(Color.RED);
            //保存的画布大小为全屏幕大小
            canvas.save();

            canvas.clipRect(new Rect(100, 100, 800, 800));
            canvas.drawColor(Color.GREEN);
            //保存画布大小为Rect(100, 100, 800, 800)
            canvas.save();

            canvas.clipRect(new Rect(200, 200, 700, 700));
            canvas.drawColor(Color.BLUE);
            //保存画布大小为Rect(200, 200, 700, 700)
            canvas.save();

            canvas.clipRect(new Rect(300, 300, 600, 600));
            canvas.drawColor(Color.BLACK);
            //保存画布大小为Rect(300, 300, 600, 600)
            canvas.save();

            canvas.clipRect(new Rect(400, 400, 500, 500));
            canvas.drawColor(Color.WHITE);
            canvas.drawText("xxxxxxxxxxxxxx", 30, 30, paint);
            //连续出栈三次，将最后一次出栈的Canvas状态作为当前画布，并画成黄色背景
            canvas.restore();
            paint.setColor(Color.CYAN);
            canvas.drawLine(10, 10, 500, 800, paint);
            canvas.drawText("11111111", 0, 30, paint);
            canvas.restore();
            canvas.drawText("222222222", 0, 30, paint);
            canvas.drawLine(0,0,1000,1000,paint);
            canvas.restore();
            canvas.drawText("33333333", 0, 30, paint);

        }
    }
}
