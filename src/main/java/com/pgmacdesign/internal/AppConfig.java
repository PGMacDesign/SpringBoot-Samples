package com.pgmacdesign.internal;

import com.mongodb.*;
import com.pgmacdesign.internal.asyncoperations.UserTableAsyncOperations;
import com.pgmacdesign.utils.Constants;
import com.pgmacdesign.utils.L;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.UnknownHostException;

@Configuration
@EnableAsync
public class AppConfig {

    private static AnnotationConfigApplicationContext context;

    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException{
        MongoCredential credential = MongoCredential.createCredential(
                Constants.MONGODB_USERNAME, Constants.MONGODB_DB_NAME, Constants.MONGODB_PASSWORD.toCharArray());
        ServerAddress serverAddress = new ServerAddress(Constants.MONGODB_HOST, Constants.MONGODB_PORT);
        MongoClientOptions clientOptions = MongoClientOptions.builder()
                .description(Constants.MONGODB_DESCRIPTION)
                //TODO add more here if need be
                .build();
        return new SimpleMongoDbFactory(new MongoClient(serverAddress, credential, clientOptions), Constants.MONGODB_DB_NAME);
        //return new SimpleMongoDbFactory(new MongoClient("localhost", 27017), "pattest");
    }

    @Bean
    public MongoOperations mongoOperations() throws UnknownHostException {
        L.m("mongoOperations hit within appConfig, @37");
        return new MongoTemplate(mongoDbFactory());
    }

    /**
     * Allows for Asynchronous calls to be made to the the {@link UserTableAsyncOperations} class
     * @return
     */
    @Bean
    public UserTableAsyncOperations getUserOps(){
        return new UserTableAsyncOperations();
    }

    public static AnnotationConfigApplicationContext getContext(){
        if(context == null){
            context = new AnnotationConfigApplicationContext(AppConfig.class);
        }

        return context;
    }
}
