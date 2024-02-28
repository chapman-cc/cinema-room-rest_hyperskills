package cinema.decorators;

import cinema.interfaces.Seat;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SeatDecorator implements Seat {
    private Seat seat;
    private boolean isAvailable;
    private final int price;

    public SeatDecorator(Seat seat, int price) {
        this.seat = seat;
        this.price = price;
        this.isAvailable = true;
    }

    @Override
    public int getRow() {
        return seat.getRow();
    }

    @Override
    public int getColumn() {
        return seat.getColumn();
    }

    @JsonIgnore
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getPrice() {
        return price;
    }
}
