package ma.wem.springsecurityapp.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ApplicationUser implements UserDetails {
    private final Set<? extends GrantedAuthority> grantedAuthorities;
    private final String username;
    private final String password;
    private final boolean isAccountNonLocked;
    private final boolean isAccountNonExpired;
    private final boolean isEnabled;
    private final boolean isCredentialsNonExpired;

    public ApplicationUser(Set<? extends GrantedAuthority>
                                   grantedAuthorities,
                           String username, String password, boolean isAccountNonLocked, boolean isAccountNonExpired, boolean isEnabled, boolean isCredentialsNonExpired) {
        this.grantedAuthorities = grantedAuthorities;
        this.username = username;
        this.password = password;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isEnabled = isEnabled;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }
}
