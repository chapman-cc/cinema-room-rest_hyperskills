package cinema.controllers;

import cinema.entities.*;
import cinema.exceptions.IncorrectPasswordException;
import cinema.exceptions.SeatAlreadyPurchasedException;
import cinema.exceptions.SeatRowColumnOutOfBound;
import cinema.exceptions.WrongTokenException;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class MovieController {

    private final List<CinemaSeat> seats;
    private final Map<UUID, CinemaSeat> soldSeats;
    private final Cinema cinema;

    public MovieController(List<CinemaSeat> seats, Cinema cinema) {
        this.seats = new CopyOnWriteArrayList<>(seats);
        this.cinema = cinema;
        this.soldSeats = new ConcurrentHashMap<>();
    }

    @GetMapping("/seats")
    public ResponseEntity<SeatingPlan> getCinemaSeats() {
        List<CinemaSeat> list = seats.stream().filter(CinemaSeat::isAvailable).toList();
        return ResponseEntity.ok(new SeatingPlan(cinema.getRows(), cinema.getColumns(), list));
    }

    @PostMapping("/purchase")
    public ResponseEntity<TokenizedTicket<CinemaSeat>> purchase(@RequestBody @NonNull BasicSeat selection) throws SeatRowColumnOutOfBound, SeatAlreadyPurchasedException {
        int row = selection.getRow();
        int column = selection.getColumn();

        boolean b = row > 0 && row <= cinema.getRows();
        boolean b1 = column > 0 && column <= cinema.getColumns();
        if (b && b1) {
            for (CinemaSeat seat : seats) {
                if (seat.getRow() == row && seat.getColumn() == column) {
                    if (!seat.isAvailable()) {
                        throw new SeatAlreadyPurchasedException("The ticket has been already purchased!");
                    }
                    seat.setAvailable(false);
                    UUID uuid = UUID.randomUUID();
                    soldSeats.put(uuid, seat);
                    TokenizedTicket<CinemaSeat> tokenizedTicket = new TokenizedTicket<>(uuid, seat);
                    return ResponseEntity.ok(tokenizedTicket);
                }
            }
        }

        throw new SeatRowColumnOutOfBound("The number of a row or a column is out of bounds!");
    }

    @PostMapping("/return")
    public ResponseEntity<?> refundTicket(@RequestBody @NonNull RequestToken token) throws WrongTokenException {
        UUID uuid = UUID.fromString(token.getToken());
        if (!soldSeats.containsKey(uuid)) {
            throw new WrongTokenException("Wrong token!");
        }
        CinemaSeat seat = soldSeats.remove(uuid);
        seat.setAvailable(true);
        return ResponseEntity.ok(Map.of("ticket", seat));
    }

    @GetMapping("/stats")
    public ResponseEntity<Statistic> getStats(@RequestParam String password) {
        if (!password.equals("super_secret")) {
            throw new IncorrectPasswordException("The password is wrong!");
        }
        int sum = soldSeats.values().stream().mapToInt(CinemaSeat::getPrice).sum();
        int total = cinema.getRows() * cinema.getColumns();
        return ResponseEntity.ok(new Statistic(sum, total - soldSeats.size(), soldSeats.size()));
    }
}

