package cinema.entities;

import java.util.UUID;

public class TokenizedTicket<T> {
    private final UUID token;
    private final T ticket;

    public TokenizedTicket(UUID token, T ticket) {
        this.token = token;
        this.ticket = ticket;
    }

    public UUID getToken() {
        return token;
    }

    public T getTicket() {
        return ticket;
    }
}
