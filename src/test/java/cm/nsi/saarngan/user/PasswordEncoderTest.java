package cm.nsi.saarngan.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by : Ravanely
 * create at : 06/04/2022, 14:15, mer.
 * saar-ngan
 **/
public class PasswordEncoderTest {
    @Test
    public void testEncodePassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String rawPassword = "nam2020";

        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println(encodedPassword);

        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);

        assertThat(matches).isTrue();
    }
}