package it.unicam.cs.idsflsm.municipalplatform.application.configurations;

import it.unicam.cs.idsflsm.municipalplatform.application.factories.user.AuthenticatedUserBuilderFactory;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.user.authenticated.IAuthenticatedUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@AllArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final IAuthenticatedUserRepository _authenticatedUserRepository;
    private final PasswordEncoder _passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        AuthenticatedUserBuilderFactory factory = new AuthenticatedUserBuilderFactory();
        var builder = factory.createAuthenticatedUserBuilder(UserRole.ADMINISTRATOR);
        builder.setUsername("administrator");
        builder.setPassword(_passwordEncoder.encodePassword("MunPlatform01!"));
        builder.setName("admin");
        builder.setSurname("istrator");
        builder.setRole(UserRole.ADMINISTRATOR);
        var user = builder.build();
        user.setId(UUID.randomUUID());
        _authenticatedUserRepository.save(user);
        // TODO : (NOT TODO) : FOR DEBUGGING PURPOSES ONLY
        System.out.println("\n\nAdministrator ID: " + user.getId() + "\n\n");
    }
}
