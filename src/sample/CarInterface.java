package sample;

public interface CarInterface {
    void moveForward(int distance);

    boolean leftLaneDetect(int radarOne, int radarTwo, int radarThree, int lidar);

    int changeLane();

    CarPosition whereIs();
}
