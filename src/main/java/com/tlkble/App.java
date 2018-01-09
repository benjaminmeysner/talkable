package com.tlkble;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/** Main run file
 * @author Ben
 *
 */

@SpringBootApplication
@EnableMongoRepositories(basePackages="com.tlkble.repository")
public class App {

    public static void main( String[] args ) throws InterruptedException
    {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner administrators(ApplicationContext ctx) {
        return args -> {  
        	
        	/**================= 
        	 * Run boot commands
        	 * ==================
        	 * 	mvn package -Dmaven.test.skip=true
        	 *  SKIP MAVEN TESTS
        	 */

        };
    }
}
