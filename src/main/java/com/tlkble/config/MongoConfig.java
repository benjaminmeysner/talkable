package com.tlkble.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;


/**
 * MongoDB Configuration for Cloud Database
 * @author Ben
 *
 */

@Configuration
@EnableMongoRepositories
public class MongoConfig extends AbstractMongoConfiguration {

	@Override
	protected String getDatabaseName() {
		return "tlkble";
	}

	@Override
	public Mongo mongo() throws Exception {		
		MongoCredential credential = MongoCredential.createCredential("ben", "tlkble", "change_me".toCharArray());
		return new MongoClient((new ServerAddress("ds145275.mlab.com:45275")), Arrays.asList(credential));
	}
	  
	@Override
	protected String getMappingBasePackage() {
		return "com.tlkble.domain";
	}
	
    @Override public @Bean
    MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
}
