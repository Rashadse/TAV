public interface CarInterface {
    void moveForward(int distance);

    boolean leftLaneDetect(int radarOne, int radarTwo, int radarThree, int lidar);

    int cHangeLane();

    CarPosition whereIs();
}
