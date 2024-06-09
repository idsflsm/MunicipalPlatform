package it.unicam.cs.idsflsm.municipalplatform.application.configurations;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;
/**
 * Component class for encoding passwords. The encoder uses BCrypt hashing algorithm
 */
@Component
public class PasswordEncoder {
    /**
     * Encodes the given password using BCrypt
     * @param password the password to be encoded
     * @return the hashed password
     */
    public String encodePassword(String password) {
        // Generate a salt for encryption
        String salt = BCrypt.gensalt(12);
        // Hash the password with the generated salt
        return BCrypt.hashpw(password, salt);
    }
    /**
     * Verifies if the candidate password matches the hashed password
     * @param candidatePassword the candidate password
     * @param hashedPassword the hashed password
     * @return true there is matching of passwords, false otherwise
     */
    public boolean verifyPassword(String candidatePassword, String hashedPassword) {
        // Check if the candidate password matches the hashed password
        return BCrypt.checkpw(candidatePassword, hashedPassword);
    }
}
