package users;

public class LoginStatus {
    private boolean isConnected;
    private String errorMessage;
    private String userName;
    private boolean isComputer;
    private boolean isLoaded;
    private int gameNumber;

    public LoginStatus(boolean isConnected) {
        this.isConnected = isConnected;
        this.gameNumber = -1;
    }

    public LoginStatus(boolean isConnected, String errorMessage) {
        this.isConnected = isConnected;
        this.errorMessage = errorMessage;
    }

    public LoginStatus(boolean isConnected, String errorMessage, String userName, boolean isComputer, int gameNumber) {
        this(isConnected, errorMessage);
        this.userName = userName;
        this.isComputer = isComputer;
        this.gameNumber = gameNumber;
    }

    public boolean isComputer() {
        return this.isComputer;
    }

    public void setComputer(boolean computer) {
        this.isComputer = computer;
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public void setConnected(boolean connected) {
        this.isConnected = connected;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

}
