package tw.noel.sung.com.demo_football_field.game_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by noel on 2018/4/30.
 */

public class BasicGameFieldView extends ImageView {

    protected Context context;
    //view高度
    protected int viewHeight;
    //view寬度
    protected int viewWidth;

    //畫筆設定
    protected Paint paint;
    //繪製路徑
    protected Path path;

    //用於繪圖 與canvas相關
    protected Bitmap bitmap;
    //畫布 主要分兩個圖層用於 球的bitmap 與 球場的Background
    protected Canvas canvas;
    //球場周圍的線條寬度
    protected final int GAME_FIELD_STROKE_WIDTH = 5;

    //球的X座標
    protected float ballX;
    //球的Y座標
    protected float ballY;
    //球的半徑
    protected final float BALL_SIZE = 10;


    public BasicGameFieldView(Context context) {
        super(context);
    }

    public BasicGameFieldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BasicGameFieldView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
