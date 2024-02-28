package cinema.entities;

import org.springframework.stereotype.Component;


public class Cinema {
    private final int rows, columns;

    public Cinema(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

}
