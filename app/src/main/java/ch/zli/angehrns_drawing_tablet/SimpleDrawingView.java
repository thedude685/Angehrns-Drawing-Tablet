package ch.zli.angehrns_drawing_tablet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SimpleDrawingView extends View {
    public static Paint drawPaint;
    private Path path = new Path();
    public boolean isCleared = false;
    Bitmap savedDrawing;
    List<Pair<Path, Integer>> pathColorList = new ArrayList<>();
    Matrix matrix = new Matrix();

    public SimpleDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
        getSavedImage("/data/user/0/ch.zli.angehrns_drawing_tablet/files/image.png");
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

        if (!isCleared) {
            canvas.drawBitmap(savedDrawing, matrix, drawPaint);
        }

        for (Pair<Path, Integer> path_color_entry : pathColorList) {
            drawPaint.setColor(path_color_entry.second);
            canvas.drawPath(path_color_entry.first, drawPaint);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
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

        pathColorList.add(new Pair<>(path, drawPaint.getColor()));

        return true;
    }

    public void clear() {
        pathColorList.clear();
        isCleared = true;
    }

    public Bitmap viewToBitmap(View view) {

        int specWidth = View.MeasureSpec.makeMeasureSpec(1700 /* any */, View.MeasureSpec.EXACTLY);
        view.measure(specWidth, specWidth);
        int questionWidth = view.getMeasuredWidth();

        Bitmap b = Bitmap.createBitmap(questionWidth, questionWidth, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.drawColor(Color.WHITE);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(c);
        return b;
    }

    public void getSavedImage(String filepath) {
        savedDrawing = BitmapFactory.decodeFile(filepath);
    }

    public void removeLastPair() {
        if(pathColorList.size() > 0)
            pathColorList.remove(pathColorList.size() - 1);
    }
}