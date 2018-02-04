package sample;

public class CarPosition {
    public int latitudinalPosition;
    public int longitudinalPosition;
    
    public Point getCarposition() {
        return new Point(this.longitudinalPosition, this.latitudinalPosition);
}
}
