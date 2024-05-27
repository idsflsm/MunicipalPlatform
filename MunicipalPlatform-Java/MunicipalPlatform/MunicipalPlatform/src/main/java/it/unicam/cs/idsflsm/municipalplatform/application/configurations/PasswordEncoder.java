package it.unicam.cs.idsflsm.municipalplatform.application.configurations;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;
@Component
public class PasswordEncoder {
    public String encodePassword(String password) {
        // Generate a salt for encryption
        String salt = BCrypt.gensalt(12);
        // Hash the password with the generated salt
        return BCrypt.hashpw(password, salt);
    }
    public boolean verifyPassword(String candidatePassword, String hashedPassword) {
        // Check if the candidate password matches the hashed password
        return BCrypt.checkpw(candidatePassword, hashedPassword);
    }
}
