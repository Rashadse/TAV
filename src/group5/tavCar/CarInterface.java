package group5.tavCar;

public interface CarInterface {

    void moveForward(int distance);

    boolean leftLaneDetect(int[] query_1, int[] query_2);

    int changeLane(int[] query_1, int[] query_2);

    int[] whereIs();
   
}
