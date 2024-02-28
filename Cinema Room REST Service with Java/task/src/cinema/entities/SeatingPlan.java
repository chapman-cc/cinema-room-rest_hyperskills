package cinema.entities;

import java.util.List;

public class SeatingPlan {
    private final int rows;
    private final int columns;
    private final List<CinemaSeat> seats;

    public SeatingPlan(int rows, int columns, List<CinemaSeat> seats) {
        this.rows = rows;
        this.columns = columns;
        this.seats = seats;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<CinemaSeat> getSeats() {
        return seats;
    }
}
