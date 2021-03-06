/* $This file is distributed under the terms of the license in /doc/license.txt$ */
package edu.cornell.mannlib.vitro.webapp.utils.dataGetter;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;

import com.hp.hpl.jena.rdf.model.Model;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.controller.jena.JenaIngestController;
import edu.cornell.mannlib.vitro.webapp.dao.DisplayVocabulary;
import edu.cornell.mannlib.vitro.webapp.dao.jena.VitroJenaModelMaker;
import edu.cornell.mannlib.vitro.webapp.dao.jena.VitroJenaSDBModelMaker;

public abstract class DataGetterBase implements DataGetter {
   
    /** 
     * Get the model to use based on a model URI.
     */
    protected Model getModel(ServletContext context, VitroRequest vreq , String modelName) {
        //if not set use jenaOntModel from the request
        if( StringUtils.isEmpty(modelName) ){
            return vreq.getJenaOntModel();
        }else if(REQUEST_DISPLAY_MODEL.equals(modelName)){
            return vreq.getDisplayModel();
        }else if( REQUEST_JENA_ONT_MODEL.equals(modelName)){
            return vreq.getJenaOntModel();            
        }else if( CONTEXT_DISPLAY_MODEL.equals(modelName)){
            return (Model)context.getAttribute( DisplayVocabulary.DISPLAY_ONT_MODEL);            
        }else if( ! StringUtils.isEmpty( modelName)){           
            Model model = JenaIngestController.getModel( modelName, vreq, context);
            if( model == null )
                throw new IllegalAccessError("Cannot get model <" + modelName +"> for DataGetter.");
            else
                return model;
        }else{                    
            //default is just the JeanOntModel from the vreq.
            return vreq.getJenaOntModel();
        }
    }

   public final static String REQUEST_DISPLAY_MODEL = "vitro:requestDisplayModel";   
   public final static String REQUEST_JENA_ONT_MODEL = "vitro:requestJenaOntModel";
   public final static String CONTEXT_DISPLAY_MODEL =  "vitro:contextDisplayModel";
   
}
