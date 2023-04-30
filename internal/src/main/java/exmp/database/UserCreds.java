package exmp.database;

public class UserCreds {
    private String token;
    private Long userId;

    public UserCreds(String token, Long userId){
        this.token = token;
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getToken() {
        return this.token;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
