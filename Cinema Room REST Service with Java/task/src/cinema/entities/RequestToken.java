package cinema.entities;

public class RequestToken {
    private  String token;

    public RequestToken() {
    }

    public RequestToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
