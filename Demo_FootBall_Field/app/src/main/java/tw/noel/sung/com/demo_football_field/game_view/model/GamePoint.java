package tw.noel.sung.com.demo_football_field.game_view.model;

/**
 * Created by noel on 2018/4/28.
 */

public class GamePoint {
    private int pointX;
    private int pointY;

    public GamePoint(int pointX, int pointY) {
        this.pointX = pointX;
        this.pointY = pointY;
    }

    public int getPointX() {
        return pointX;
    }

    public void setPointX(int pointX) {
        this.pointX = pointX;
    }

    public int getPointY() {
        return pointY;
    }

    public void setPointY(int pointY) {
        this.pointY = pointY;
    }
}
