package cinema.controllers;

import cinema.entities.BasicSeat;
import cinema.entities.Cinema;
import cinema.entities.CinemaSeat;
import cinema.entities.SeatingPlan;
import cinema.exceptions.SeatAlreadyPurchasedException;
import cinema.exceptions.SeatRowColumnOutOfBound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MovieController {

    private final List<CinemaSeat> seats;
    private final Cinema cinema;

    public MovieController(List<CinemaSeat> seats, Cinema cinema) {
        this.seats = seats;
        this.cinema = cinema;
    }

    @GetMapping("/seats")
    public ResponseEntity<SeatingPlan> getCinemaSeats() {
        List<CinemaSeat> list = seats.stream().filter(CinemaSeat::isAvailable).toList();
        return ResponseEntity.ok(new SeatingPlan(cinema.getRows(), cinema.getColumns(), list));
    }

    @PostMapping("/purchase")
    public ResponseEntity<CinemaSeat> purchase(@RequestBody @NonNull BasicSeat selection) throws SeatRowColumnOutOfBound, SeatAlreadyPurchasedException {
        int row = selection.getRow();
        int column = selection.getColumn();
        
        if (row > 0 && row <= cinema.getRows() && column > 0 && column <= cinema.getColumns()) {
            for (CinemaSeat seat : seats) {
                if (seat.getRow() == row && seat.getColumn() == column) {
                    if (!seat.isAvailable()) {
                        throw new SeatAlreadyPurchasedException();
                    }
                    seat.setAvailable(false);
                    return ResponseEntity.ok(seat);
                }
            }
        }

        throw new SeatRowColumnOutOfBound();
    }

    @ExceptionHandler(SeatAlreadyPurchasedException.class)
    public ResponseEntity<?> handleSeatAlreadyPurchasedException(SeatAlreadyPurchasedException e) {
        return ResponseEntity.badRequest().body(
                Map.of("error", "The ticket has been already purchased!")
        );
    }

    @ExceptionHandler(SeatRowColumnOutOfBound.class)
    public ResponseEntity<?> handleSeatRowColumnOutOfBound(SeatRowColumnOutOfBound e) {
        return ResponseEntity.badRequest().body(
                Map.of("error", "The number of a row or a column is out of bounds!")
        );
    }
}

