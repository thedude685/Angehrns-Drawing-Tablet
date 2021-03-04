package ch.zli.angehrns_drawing_tablet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SimpleDrawingView extends View {
    public static Paint drawPaint;
    private Path path = new Path();
    List<Pair<Path, Integer>> path_color_list = new ArrayList<>();

    public SimpleDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
    }

    private void setupPaint() {
        drawPaint = new Paint();
        int paintColor = Color.BLACK;
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (Pair<Path, Integer> path_color_entry : path_color_list) {
            drawPaint.setColor(path_color_entry.second);
            canvas.drawPath(path_color_entry.first, drawPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float pointX = event.getX();
        float pointY = event.getY();

        // Checks for the event that occurs
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(pointX, pointY);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(pointX, pointY);
                break;
            default:
                path = new Path();
                return false;
        }
        // Force a view to draw again
        postInvalidate();

        path_color_list.add(new Pair<>(path, drawPaint.getColor()));

        return true;
    }

    public void clear(){
        path_color_list.clear();
    }

    public Bitmap viewToBitmap(View view) {
        return Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Canvas canvas = new Canvas(bitmap);
        //view.draw(canvas);
    }
}

