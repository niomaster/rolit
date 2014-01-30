package rolit.model.networking.client;

public class ClientGame {
    private String creator;
    private int players;
    private int status;

    public ClientGame(String creator, int players, int status) {
        this.creator = creator;
        this.players = players;
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
