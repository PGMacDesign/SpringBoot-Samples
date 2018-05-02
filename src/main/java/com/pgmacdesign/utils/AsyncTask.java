package com.pgmacdesign.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;
import javax.swing.SwingUtilities;

/**
 * Java Version of Android's Asynctask. Pulled from this link:
 * https://stackoverflow.com/questions/36272642/asynctask-equivalent-in-java
 * @param <Params>
 * @param <Progress>
 * @param <Result>
 *     TO Research:
 *     1) https://docs.oracle.com/javase/tutorial/essential/concurrency/executors.html
 *     2) http://www.vogella.com/tutorials/JavaConcurrency/article.html
 *     3) http://www.baeldung.com/spring-async
 */
public abstract class AsyncTask<Params, Progress, Result> {
    protected AsyncTask() {
    }
    
    protected abstract void onPreExecute();
    
    protected abstract Result doInBackground(Params... params) ;
    
    protected abstract void onProgressUpdate(Progress... progress) ;
    
    protected abstract void onPostExecute(Result result) ;
    
    final void  publishProgress(Progress... values) {
        SwingUtilities.invokeLater(() -> this.onProgressUpdate(values) );
    }
    
    final AsyncTask<Params, Progress, Result> execute(Params... params) {
        // Invoke pre execute
        try {
            SwingUtilities.invokeAndWait( this::onPreExecute );
        } catch (InvocationTargetException|InterruptedException e){
            e.printStackTrace();
        }
        
        // Invoke doInBackground
        CompletableFuture<Result> cf =  CompletableFuture.supplyAsync( () -> doInBackground(params) );
        
        // Invoke post execute
        cf.thenAccept(this::onPostExecute);
        return this;
    }
}
