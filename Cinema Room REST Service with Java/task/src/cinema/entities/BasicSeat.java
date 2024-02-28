package cinema.entities;

import cinema.interfaces.Seat;

public class BasicSeat implements Seat {
    private int row, column ;

    public BasicSeat() {

    }
    public BasicSeat(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
