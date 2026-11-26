public class Room {

    private int number;
    private boolean state;

    public Room(int number) {
        this.number = number;
        this.state = false;
    }

    public int getNumber() {
        return number;
    }

    public boolean isOccupied() {
        return state;
    }

    public void setOccupied(boolean state) {
        this.state = state;
    }

}
