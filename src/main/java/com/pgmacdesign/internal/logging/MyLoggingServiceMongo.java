package com.pgmacdesign.internal.logging;

import com.pgmacdesign.internal.datamodels.DBLogging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
public class MyLoggingServiceMongo {

    @Autowired
    private MongoOperations mongoOperations;

    public void logObject(DBLogging obj) {
        try {
            if(obj != null){
                mongoOperations.save(obj);
            }
        } catch (Exception e){}
    }

}
