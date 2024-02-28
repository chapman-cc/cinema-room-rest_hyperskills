package cinema.entities;

import cinema.interfaces.Seat;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class CinemaSeat implements Seat {
    private final int row, column;
    private final int price;
    @JsonIgnore
    private boolean isAvailable;

    public CinemaSeat(Seat seat, int price) {
        this(seat.getRow(), seat.getColumn(), price);
    }

    public CinemaSeat(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
        this.isAvailable = true;
    }

    @Override
    public int getRow() {
        return row;

    }

    @Override
    public int getColumn() {
        return column;

    }

    public int getPrice() {
        return price;
    }

    @JsonIgnore
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
