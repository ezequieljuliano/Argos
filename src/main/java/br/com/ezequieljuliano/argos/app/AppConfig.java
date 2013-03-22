package br.com.ezequieljuliano.argos.app;

import br.com.ezequieljuliano.argos.manager.LuceneManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

@Configuration
public class AppConfig {

	public @Bean MongoOperations mongoTemplate(Mongo mongo) {
		MongoTemplate mongoTemplate = new MongoTemplate(mongo, "Argos");
		return mongoTemplate;
	}

	/*
	 * Factory bean that creates the Mongo instance
	 */
	public @Bean MongoFactoryBean mongo() {
		MongoFactoryBean mongo = new MongoFactoryBean();
		mongo.setHost("localhost");
		return mongo;
	}

	/*
	 * Use this post processor to translate any MongoExceptions thrown in @Repository annotated classes
	 */
	public @Bean PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
        
        
        
    public @Bean Directory getDirectory() {
        Directory directory = new RAMDirectory();
        return directory;
    }

}
