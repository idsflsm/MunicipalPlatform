package it.unicam.cs.idsflsm.municipalplatform.application.configurations;

import it.unicam.cs.idsflsm.municipalplatform.application.factories.user.authenticated.AuthenticatedUserBuilderFactory;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.user.authenticated.IAuthenticatedUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
/**
 * Configuration class for initializing the database, on application startup,
 * with default administrator instance
 */
@Configuration
@AllArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    /**
     * The repository related to AuthenticatedUser entity
     */
    private final IAuthenticatedUserRepository _authenticatedUserRepository;
    /**
     * The password encoder for encoding user passwords
     */
    private final PasswordEncoder _passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        /*
        This method automatically generates an Administrator account,
        with default username and password.
        FOR TESTING PURPOSES ONLY
         */
        AuthenticatedUserBuilderFactory factory = new AuthenticatedUserBuilderFactory();
        var builder = factory.createAuthenticatedUserBuilder(UserRole.ADMINISTRATOR);
        builder.setUsername("idsflsm2324@hotmail.com");
        builder.setPassword(_passwordEncoder.encodePassword("MunPlatform01!"));
        builder.setName("Idsflsm2324");
        builder.setSurname("Administrator");
        builder.setRole(UserRole.ADMINISTRATOR);
        var user = builder.build();
        user.setId(UUID.randomUUID());
        _authenticatedUserRepository.save(user);
        System.out.println("\n\nAdministrator ID: " + user.getId() + "\n\n");
    }
}
