package ma.wem.springsecurityapp.jwt;

public class UsernameAndPasswordAuthenticationRequest {
    private String username;
    private String password;
    public UsernameAndPasswordAuthenticationRequest() {

    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
