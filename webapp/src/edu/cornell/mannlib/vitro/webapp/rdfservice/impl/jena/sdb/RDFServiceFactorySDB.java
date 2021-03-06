/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vitro.webapp.rdfservice.impl.jena.sdb;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hp.hpl.jena.sdb.StoreDesc;

import edu.cornell.mannlib.vitro.webapp.rdfservice.ChangeListener;
import edu.cornell.mannlib.vitro.webapp.rdfservice.RDFService;
import edu.cornell.mannlib.vitro.webapp.rdfservice.RDFServiceException;
import edu.cornell.mannlib.vitro.webapp.rdfservice.RDFServiceFactory;

public class RDFServiceFactorySDB implements RDFServiceFactory {

    private final static Log log = LogFactory.getLog(RDFServiceFactorySDB.class);
    
    private DataSource ds;
    private StoreDesc storeDesc;
    private RDFService longTermRDFService;
    
    public RDFServiceFactorySDB(DataSource dataSource, StoreDesc storeDesc) {
        this.ds = dataSource;
        this.storeDesc = storeDesc;
        this.longTermRDFService = new RDFServiceSDB(dataSource, storeDesc);
    }
    
    @Override
    public RDFService getRDFService() {
        return this.longTermRDFService;
    }

    @Override
    public RDFService getShortTermRDFService() {
        try {
            RDFService rdfService = new RDFServiceSDB(ds.getConnection(), storeDesc);
            for (ChangeListener cl : ((RDFServiceSDB) longTermRDFService)
                    .getRegisteredListeners() ) {
                rdfService.registerListener(cl);    
            }
            return rdfService;
        } catch (Exception e) {
            log.error(e,e);
            throw new RuntimeException(e);
        }
    } 

    @Override
    public void registerListener(ChangeListener changeListener)
            throws RDFServiceException {
        this.longTermRDFService.registerListener(changeListener);
    }

    @Override
    public void unregisterListener(ChangeListener changeListener)
            throws RDFServiceException {
        this.longTermRDFService.registerListener(changeListener);
    }

}
