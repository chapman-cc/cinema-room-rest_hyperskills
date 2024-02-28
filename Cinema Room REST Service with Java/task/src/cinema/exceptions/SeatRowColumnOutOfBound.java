package cinema.exceptions;

public class SeatRowColumnOutOfBound extends RuntimeException {
    public SeatRowColumnOutOfBound(String message) {
        super(message);
    }
}
