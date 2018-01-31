package sample;

public class CarMovement implements CarInterface {

    // default final value of 5 to be used each time the car moves
    final int distance = 5;
    // this boolean will be changed to false when the car reaches the end of the street
    boolean streetEndNotReached = true;

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