package cm.nsi.saarngan.security;

import cm.nsi.saarngan.entity.User;
import cm.nsi.saarngan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * created by : Ravanely
 * create at : 06/04/2022, 10:28, mer.
 * saar-ngan
 **/
public class SaarNganUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.getUserByEmail(email);

        if (user != null)
            return new SaarNganUserDetails(user);

        throw new UsernameNotFoundException("Could not find user with email: " + email);
    }
}
