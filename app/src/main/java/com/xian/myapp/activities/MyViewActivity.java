package com.xian.myapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
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
        private Paint mPaint;
        private Path mPath;
        public MyView(Context context) {
            super(context);

            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStrokeWidth(6);
            mPaint.setTextSize(16);
            mPaint.setTextAlign(Paint.Align.RIGHT);

            mPath = new Path();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawColor(Color.GRAY);
            canvas.save();
            canvas.translate(10, 10);
            drawScene(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(160, 10);
            canvas.clipRect(10, 10, 90, 90);
            canvas.clipRect(30, 30, 70, 70, Region.Op.DIFFERENCE);
            drawScene(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(10, 160);
            mPath.reset();
            canvas.clipPath(mPath); // makes the clip empty
            mPath.addCircle(50, 50, 50, Path.Direction.CCW);
            canvas.clipPath(mPath, Region.Op.REPLACE);
            drawScene(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(160, 160);
            canvas.clipRect(0, 0, 60, 60);
            canvas.clipRect(40, 40, 100, 100, Region.Op.UNION);
            drawScene(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(10, 310);
            canvas.clipRect(0, 0, 60, 60);
            canvas.clipRect(40, 40, 100, 100, Region.Op.XOR);
            drawScene(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(160, 310);
            canvas.clipRect(0, 0, 60, 60);
            canvas.clipRect(40, 40, 100, 100, Region.Op.REVERSE_DIFFERENCE);
            drawScene(canvas);
            canvas.restore();
        }

        private void drawScene(Canvas canvas) {
            canvas.clipRect(0, 0, 100, 100);

            canvas.drawColor(Color.WHITE);

            mPaint.setColor(Color.RED);
            canvas.drawLine(0, 0, 100, 100, mPaint);

            mPaint.setColor(Color.GREEN);
            canvas.drawCircle(30, 70, 30, mPaint);

            mPaint.setColor(Color.BLUE);
            canvas.drawText("Clipping", 100, 30, mPaint);
        }

    }
}
