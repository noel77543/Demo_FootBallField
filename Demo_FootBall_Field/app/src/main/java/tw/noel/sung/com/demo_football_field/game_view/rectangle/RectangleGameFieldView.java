package tw.noel.sung.com.demo_football_field.game_view.rectangle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import tw.noel.sung.com.demo_football_field.R;
import tw.noel.sung.com.demo_football_field.game_view.BasicGameFieldView;
import tw.noel.sung.com.demo_football_field.game_view.model.GamePoint;

/**
 * Created by noel on 2018/4/28.
 */

public class RectangleGameFieldView extends BasicGameFieldView implements Runnable {


    //球場與外圍邊界
    private final int GAME_FIELD_SPACE = 50;
    private final int GATE_WIDTH = 150;
    private final int GATE_LENGTH = 300;
    private final int BALL_UPDATE_TIME = 100;

    //左上點
    private GamePoint gamePointLeftTop;
    //左下點
    private GamePoint gamePointLeftBottom;
    //右下點
    private GamePoint gamePointRightBottom;
    //右上點
    private GamePoint gamePointRightTop;

    //最右邊
    private int maxRight;
    //最左邊
    private int maxLeft;
    //最上面
    private int maxTop;
    //最下面
    private int maxBottom;


    //位移量Ｘ
    int stepX = 60;
    //位移量Ｙ
    int stepY = 60;

    //------------
    public RectangleGameFieldView(@NonNull Context context) {
        super(context);
        this.context = context;
        post(this);
    }

    public RectangleGameFieldView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        post(this);
    }

    public RectangleGameFieldView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        post(this);
    }

    //---------------------

    @Override
    public void run() {
        viewHeight = getHeight();
        viewWidth = getWidth();
        init();
    }


    //---------------------
    private void init() {
        path = new Path();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        //畫筆颜色
        paint.setColor(context.getResources().getColor(R.color.game_field_text));
        //線條寬度
        paint.setStrokeWidth(GAME_FIELD_STROKE_WIDTH);
        bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        setBackground(new BitmapDrawable(getResources(), bitmap));

        drawGameField();
        drawBall();
    }

    //----------------------

    /***
     *  畫製比賽場地最外圍
     */
    private void drawGameField() {
        init4Point();
        path.moveTo((float) gamePointLeftTop.getPointX(), (float) gamePointLeftTop.getPointY());
        path.lineTo((float) gamePointLeftBottom.getPointX(), (float) gamePointLeftBottom.getPointY());
        path.lineTo((float) gamePointRightBottom.getPointX(), (float) gamePointRightBottom.getPointY());
        path.lineTo((float) gamePointRightTop.getPointX(), (float) gamePointRightTop.getPointY());
        path.lineTo((float) gamePointLeftTop.getPointX() - (GAME_FIELD_STROKE_WIDTH / 2), (float) gamePointLeftTop.getPointY());

        drawLeftGate();
        drawRightGate();
        canvas.drawPath(path, paint);
    }
    //----------------------

    /***
     *  初始4點位置
     */
    private void init4Point() {
        //左上
        gamePointLeftTop = new GamePoint(GAME_FIELD_SPACE + GATE_WIDTH, GAME_FIELD_SPACE);
        //左下
        gamePointLeftBottom = new GamePoint(GAME_FIELD_SPACE + GATE_WIDTH, viewHeight - GAME_FIELD_SPACE);
        //右下
        gamePointRightBottom = new GamePoint(viewWidth - GAME_FIELD_SPACE - GATE_WIDTH, viewHeight - GAME_FIELD_SPACE);
        //右上
        gamePointRightTop = new GamePoint(viewWidth - GAME_FIELD_SPACE - GATE_WIDTH, GAME_FIELD_SPACE);

        maxTop = gamePointRightTop.getPointY();
        maxRight = gamePointRightTop.getPointX();
        maxLeft = gamePointLeftBottom.getPointX();
        maxBottom = gamePointLeftBottom.getPointY();
    }

    //---------------------

    /***
     *  繪製左邊球門
     */
    private void drawLeftGate() {
        path.moveTo((float) (gamePointLeftBottom.getPointX()), (float) (gamePointLeftBottom.getPointY() / 2) - (GATE_LENGTH / 2));
        path.lineTo((float) (gamePointLeftBottom.getPointX() - GATE_WIDTH), (float) (gamePointLeftBottom.getPointY() / 2) - (GATE_LENGTH / 2));
        path.lineTo((float) (gamePointLeftBottom.getPointX() - GATE_WIDTH), (float) (gamePointLeftBottom.getPointY() / 2) + (GATE_LENGTH / 2));
        path.lineTo((float) (gamePointLeftBottom.getPointX()), (float) (gamePointLeftBottom.getPointY() / 2) + (GATE_LENGTH / 2));
    }

    //-------------------------

    /***
     *  繪製右邊球門
     */
    private void drawRightGate() {
        path.moveTo((float) (gamePointRightBottom.getPointX()), (float) (gamePointRightBottom.getPointY() / 2) - (GATE_LENGTH / 2));
        path.lineTo((float) (gamePointRightBottom.getPointX() + GATE_WIDTH), (float) (gamePointRightBottom.getPointY() / 2) - (GATE_LENGTH / 2));
        path.lineTo((float) (gamePointRightBottom.getPointX() + GATE_WIDTH), (float) (gamePointRightBottom.getPointY() / 2) + (GATE_LENGTH / 2));
        path.lineTo((float) (gamePointRightBottom.getPointX()), (float) (gamePointRightBottom.getPointY() / 2) + (GATE_LENGTH / 2));
    }

    //---------------------

    /***
     *   繪製球的圖層置於球場之上
     */
    private void drawBall() {

        bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint.setStyle(Paint.Style.FILL);
        ballX = (viewWidth / 2) + GAME_FIELD_STROKE_WIDTH;
        ballY = (gamePointLeftBottom.getPointY() / 2) + GAME_FIELD_STROKE_WIDTH;
        canvas.drawCircle(ballX, ballY, BALL_SIZE, paint);
        setImageBitmap(bitmap);
        startScrollBall();

    }

    //---------------------

    /***
     *  每0.3秒繪製球的新位置
     */
    private void startScrollBall() {


        postDelayed(new Runnable() {
            @Override
            public void run() {
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                if ((ballX += stepX) > maxRight) {
                    stepX = -stepX;
                } else if ((ballX += stepX) < maxLeft) {
                    stepX = Math.abs(stepX);
                }

                if ((ballY += stepY) > maxBottom) {
                    stepY = -stepY;
                } else if ((ballY += stepY) < maxTop) {
                    stepY = Math.abs(stepY);
                }


                canvas.drawCircle
                        (ballX = ballX > maxRight ? maxRight : ballX < maxLeft ? maxLeft : ballX,
                                ballY = ballY > maxBottom ? maxBottom - 20 : ballY < maxTop ? maxTop + 20 : ballY,
                                BALL_SIZE, paint);
                invalidate();

                postDelayed(this, BALL_UPDATE_TIME);
            }
        }, BALL_UPDATE_TIME);
    }
}
