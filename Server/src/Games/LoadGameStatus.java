package Games;

public class LoadGameStatus {
    boolean isLoaded;
    String errorMessage;
    String redirect;

    public LoadGameStatus(boolean isLoaded, String message, String redirect) {
        this.isLoaded = isLoaded;
        this.errorMessage = message;
        this.redirect = redirect;
    }

    public boolean isLoaded() {
        return this.isLoaded;
    }

    public void setLoaded(boolean loaded) {
        this.isLoaded = loaded;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
}
