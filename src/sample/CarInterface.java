package sample;

public interface CarInterface {
    void moveForward(int distance);

    boolean leftLaneDetect(int[] query_1, int[] query_2);

    int changeLane();

    CarPosition whereIs();
   
}
