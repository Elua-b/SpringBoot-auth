package live.learnlynx.api;

import live.learnlynx.api.v1.enums.ERole;
import live.learnlynx.api.v1.models.Role;
import live.learnlynx.api.v1.repositories.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class ELearningApplication {

    private final IRoleRepository roleRepository;

    @Autowired
    public ELearningApplication(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ELearningApplication.class, args);
    }

    @Bean
    public void registerRoles() {
        Set<ERole> roles = new HashSet<>();
        roles.add(ERole.ADMIN);
        roles.add(ERole.LECTURER);
        roles.add(ERole.STUDENT);
        roles.add(ERole.SCHOOL_ADMIN);

        for (ERole role : roles) {
            Optional<Role> roleByName = roleRepository.findByName(role);
            if (!roleByName.isPresent()) {
                Role newRole = new Role(role, role.toString());
                roleRepository.save(newRole);
                System.out.println("Created: " + role.toString());
            }
        }
    }
}
