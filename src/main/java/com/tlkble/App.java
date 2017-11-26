package com.tlkble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.tlkble.repository.UserRepository;
import com.tlkble.App;
import com.tlkble.domain.User;

/** Main run file
 * @author Ben
 *
 */

@SpringBootApplication
@EnableMongoRepositories(basePackages="com.tlkble.repository")
public class App {
	
	@Autowired
	UserRepository userRepository;
	
    public static void main( String[] args ) throws InterruptedException
    {
        SpringApplication.run(App.class, args);
    }
    
    //My bean to add user accounts
    @Bean
    public CommandLineRunner administrators(ApplicationContext ctx) {
        return args -> {       	
        	       	
        	@SuppressWarnings("unused")
			final User administratorA = new User("John", "Doe", "adminA@email.co.uk", "john54", "12345");
        	administratorA.setRole("ROLE_ADMIN");
			@SuppressWarnings("unused")
			final User user = new User("Ben", "Meysner", "bm234@student.le.ac.uk", "bm234", "testpass");
        	administratorA.setRole("ROLE_USER");
        	//userRepository.save(administratorA); 
        	//userRepository.save(user);

        };
    }
}
