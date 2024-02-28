package cinema.entities;

public class CustomErrorMessage {
    public static CustomErrorMessage of(String error) {
        return new CustomErrorMessage(error);
    }
    private String error;

    public CustomErrorMessage(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
