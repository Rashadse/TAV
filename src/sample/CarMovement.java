package sample;

public class CarMovement implements CarInterface {
    @Override
    public void moveForward(int distance) {

    }

    @Override
    public boolean leftLaneDetect(int radarOne, int radarTwo, int radarThree, int lidar) {
        return false;
    }

    @Override
    public int changeLane() {
        return 0;
    }

    @Override
    public CarPosition whereIs() {
        return null;
    }
}
