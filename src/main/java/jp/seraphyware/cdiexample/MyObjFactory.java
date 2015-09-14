/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.seraphyware.cdiexample;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.slf4j.Logger;

@ApplicationScoped
public class MyObjFactory {
    
    @Inject
    private Logger logger;
    
    @Produces
    @Dependent
    public MyObj createMyObj() {
        MyObj inst = new MyObj(self -> {
            logger.info("@@disposed: " + self);
        });
        logger.info("@@createMyObj: " + inst);
        return inst;
    }

    public void destroy(@Disposes MyObj obj) {
        logger.info("@@disposes: " + obj);
        obj.close();
    }
}
