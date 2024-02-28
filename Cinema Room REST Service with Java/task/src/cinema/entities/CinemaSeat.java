package cinema.entities;

import cinema.decorators.SeatDecorator;
import cinema.interfaces.Seat;

public class CinemaSeat extends SeatDecorator {
    public CinemaSeat(int row, int column, int price) {
        this(new BasicSeat(row,column), price);
    }

    public CinemaSeat(Seat seat, int price) {
        super(seat, price);
    }


}
