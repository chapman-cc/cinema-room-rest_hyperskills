package cinema.controllers;

import cinema.entities.BasicSeat;
import cinema.entities.Cinema;
import cinema.entities.CinemaSeat;
import cinema.entities.SeatingPlan;
import cinema.interfaces.Seat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> purchase(@RequestBody @NonNull BasicSeat selection) {
        try {

            int row = selection.getRow();
            int column = selection.getColumn();


            if (row < 0 && row > cinema.getRows() || column < 0 && column > cinema.getColumns()) {
                throw new CinemaSeat.SeatRowColumnOutOfBound();
            }
            for (CinemaSeat seat : seats) {
                if (seat.getRow() == row && seat.getColumn() == column) {
                    if (seat.isAvailable()) {
                        seat.setAvailable(false);
                        return ResponseEntity.ok(seat);
                    }
                    throw new CinemaSeat.SeatAlreadyPurchasedException();
                }
            }

            throw new CinemaSeat.SeatRowColumnOutOfBound();
        } catch (CinemaSeat.SeatAlreadyPurchasedException e) {
            return ResponseEntity.badRequest().body(
                    getError("The ticket has been already purchased!")
            );
        } catch (CinemaSeat.SeatRowColumnOutOfBound e) {
            return ResponseEntity.badRequest().body(
                    getError("The number of a row or a column is out of bounds!")
            );
        }
    }

    private static Map<String, String> getError(String message) {
        return Map.of("error", message);
    }
}
