package cinema;

import cinema.entities.Cinema;
import cinema.entities.CinemaSeat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CinemaUtility {
    @Bean
    public Cinema cinema() {
        return new Cinema(9, 9);
    }

    @Bean
    public List<CinemaSeat> seats() {
        List<CinemaSeat> seats = new ArrayList<>();

        for (int i = 0; i < cinema().getRows(); i++) {
            for (int j = 0; j < cinema().getColumns(); j++) {
                int row = i + 1;
                int column = j + 1;
                int price = row <= 4 ? 10 : 8;
                CinemaSeat s = new CinemaSeat(row, column, price);
                seats.add(s);
            }
        }

        return seats;
    }

}
