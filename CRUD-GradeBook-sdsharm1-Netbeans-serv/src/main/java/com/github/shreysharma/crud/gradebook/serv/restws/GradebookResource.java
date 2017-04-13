/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.shreysharma.crud.gradebook.serv.restws;

import java.net.URI;
import java.util.Random;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.shreysharma.crud.gradebook.serv.model.Gradebook;
import com.github.shreysharma.crud.gradebook.serv.utils.Converter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * REST Web Service
 */
@Path("Gradebook")
public class GradebookResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(GradebookResource.class);
    
    private static Gradebook gradebook;
    private static int listcounter =0 ;
    private static ArrayList<Gradebook> gradebooklist = new ArrayList<Gradebook>(); 
    private static Set<Integer> randnumset = new HashSet<Integer>();

    @Context
    private UriInfo context;
    
    /**Creates a new instance of GradebookResource*/
    public GradebookResource() {
        LOG.info("Creating an Gradebook Resource");
    }

    /**
     * POST method for creating an instance of ScratchResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response createResource(String content) {
        LOG.info("Creating the instance Gradebook Resource {}", gradebook);
        LOG.debug("POST request in new server");
        LOG.debug("Request Content = {}", content);
        
        Response response;
        //if (gradebooklist == null){
            LOG.debug("Attempting to create an Gradebook Resource and setting it to {}", content);
         //    for (Integer s : randnumset) LOG.info(" {} ,  ",s);
        try {
                gradebook = (Gradebook) Converter.convertFromXmlToObject(content, Gradebook.class);
                LOG.debug("The XML {} was converted to the object {}", content, gradebook);
                
                //------------------------
                //check if ID already present in gradebooklist
                int IDpresent= 0 ;
                 for(int i=0 ; i< gradebooklist.size(); i ++ ){
                     if ( gradebooklist.get(i).getId() -1 == listcounter ){
                          IDpresent = 1;  
                          LOG.info("ID FOUND");
                     }
                 }
                if(IDpresent==1){ //create response code, of unable to create resource since already present
                     response = Response.status(Response.Status.CONFLICT).build(); 
                  //   return response;
                }
                 
                if(gradebook.getPriority().equals("") ){
                        LOG.info("NO NAME");
                        response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
                        return response;
                }

                    //--------------------
                
                LOG.info("Creating a {} {} Status Response", Response.Status.CREATED.getStatusCode(), Response.Status.CREATED.getReasonPhrase());
                
                //   Id for newly created resource , ENSURE unique iD
                if(randnumset.isEmpty())LOG.info("Empty Response") ;else{LOG.info("NON Empty Response") ; }
                Random randomGenerator = new Random(1234567890);
              int gradebookId ;
                //=  Math.abs(randomGenerator.nextInt(1000)); 
               // while( !gradebookIdisunique(gradebookId) ){
              //      gradebookId =  Math.abs(randomGenerator.nextInt(1000));
            //    }
            /*    if(!randnumset.contains(gradebookId)) randnumset.add(gradebookId);
                else{
                      gradebookId = genrandnum();
                      //}while(randnumset.contains(gradebookId) );
                      randnumset.add(gradebookId);      }
            for (Integer s : randnumset) LOG.info(" {} ,  ",s);*/      
            
                listcounter++;
                gradebookId=  listcounter; 
                gradebook.setId(gradebookId);
               LOG.debug("Set Gradebk obj ID to {} ", gradebookId);
               
                String xmlString = Converter.convertFromObjectToXml(gradebook, Gradebook.class);
                URI locationURI = URI.create(context.getAbsolutePath() + "/" + Integer.toString(gradebookId));
            
                response = Response.status(Response.Status.CREATED).location(locationURI).entity(xmlString).build();
                //add to Arraylist gradebooklist
                gradebooklist.add(gradebook);
            } catch (JAXBException e) {
                LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
                LOG.debug("XML is {} is incompatible with Gradebook Resource", content);
                
                response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
            } catch (RuntimeException e) {
                LOG.debug("Catch All exception");
                LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
                
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
            }
      //  } else {  
      //      LOG.info("Creating a {} {} Status Response", Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getReasonPhrase());
      //      LOG.debug("Cannot create Gradebook Resource as values is already set to {}", gradebook);
      //      response = Response.status(Response.Status.CONFLICT).entity(content).build();
     //   }
                   
        LOG.debug("Generated response {}", response);      
        return response;
    }
    

    /**
     * Retrieves representation of an instance of edu.asu.cse446.sample.crud.restws.GradebookResource
     * @param id
     * @return an instance of java.lang.String
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getResource(@PathParam("id") String id) {
        LOG.info("Retrieving the Gradebook Resource {}", gradebook);
        LOG.debug("GET request in new server");
        LOG.debug("PathParam id = {}", id);
        
        Response response = null;//set response to null if nothing is recorded in responese
        //retrieve gradebook from the gradebooklist
        if ( gradebooklist.isEmpty() ){
            LOG.info("Creating a {} {} Status Response", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
            LOG.debug("No Gradebook Resource present in list to return");
            
            response = Response.status(Response.Status.GONE).entity("No Gradebook Resource to return").build();
        } else {
            
            //traverse through gradebooklist
            int entryfound= -1;
            for(int i=0 ; i< gradebooklist.size(); i ++ ){
                  if ( gradebooklist.get(i).getId()  == Integer.parseInt(id) ){
                      entryfound=i;
//                    LOG.debug("gradebook.getId() = {}", gradebook.getId());
                    LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
                    LOG.debug("Retrieving the Gradebook Resource ID {}", gradebooklist.get(i).getId() );

                    String xmlString = Converter.convertFromObjectToXml(gradebooklist.get(i), Gradebook.class);

                    response = Response.status(Response.Status.OK).entity(xmlString).build();
                } 
            }
            //absent in grabklist
            if(entryfound == -1){
                    LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
                    LOG.debug("The ENtry was not found in the Gradebook Resource ");
                    response = Response.status(Response.Status.NOT_FOUND).entity("No Gradebook Resource to return").build();
                 }
         }        
        
        LOG.debug("Returning the value {}", response);
        
        return response;
    }

    /**
     * PUT method for updating an instance of ScratchResource
     * @param id
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response updateResource(@PathParam("id") String id, String content) {
        LOG.info("Updating the Gradebook Resource {} with {}", gradebook, content);
        LOG.debug("PUT request in new server. PathParam id = {}", id);
        LOG.debug("Request Content = {}", content);
        
        Response response = null;
        
         if ( gradebooklist.isEmpty() ){
            LOG.debug("GradebookList is empty ");
            LOG.info("Creating a {} {} Status Response", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
            response = Response.status(Response.Status.GONE).entity("No Gradebook Resource to return").build();
            return response;
        }
         else{
            LOG.debug("Attempting to update the Gradebook Resource {}", gradebook);
            
            try {   
                gradebook = (Gradebook) Converter.convertFromXmlToObject(content, Gradebook.class);
                LOG.debug("The XML {} was converted to the object , Updated Gradebook Resource to {}", content, gradebook);         
                
                    //traverse through gradebooklist
                    int entryfound= -1;
                    for(int i=0 ; i< gradebooklist.size(); i ++ )
                    {
                          if ( gradebooklist.get(i).getId()  == Integer.parseInt(id) ){ //whenever you have an update you can send changes by the link ID
                              entryfound=i;
                              
                            LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
                            LOG.debug("Retrieving the Gradebook Resource ID {}", gradebooklist.get(i).getId() );
                            //override all the values 
                            gradebooklist.get(i).setPriority(gradebook.getPriority());
                            gradebooklist.get(i).setFinalgrade(gradebook.getFinalgrade());
                          
                            gradebooklist.get(i).setAssignFeed(gradebook.getAssignFeed());
                            gradebooklist.get(i).setAssignMark(gradebook.getAssignMark());
                            gradebooklist.get(i).setFinalMark(gradebook.getFinalMark());
                            gradebooklist.get(i).setFinalFeed(gradebook.getFinalFeed());
                            gradebooklist.get(i).setInclasslabsMark(gradebook.getInclasslabsMark());
                            gradebooklist.get(i).setInclasslabsFeed(gradebook.getInclasslabsFeed());
                            gradebooklist.get(i).setMidtermFeed(gradebook.getMidtermFeed());
                            gradebooklist.get(i).setMidtermMark(gradebook.getMidtermMark());
                            gradebooklist.get(i).setQuizFeed(gradebook.getQuizFeed());
                            gradebooklist.get(i).setQuizMark(gradebook.getQuizMark());
                            
                            String xmlString = Converter.convertFromObjectToXml(gradebooklist.get(i), Gradebook.class);
                            response = Response.status(Response.Status.OK).entity(xmlString).build();
                        } 
                    }
                    //absent in GradebookList
                    if(entryfound == -1){
                        LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
                        LOG.debug("No Entry found to Update ");
                        response = Response.status(Response.Status.NOT_FOUND).entity("No Gradebook Resource to return").build();
                     }
            } catch (JAXBException e) 
            {
                LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
                LOG.debug("XML is {} is incompatible with Gradebook Resource", content);
                response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
            } catch (RuntimeException e)
            {
                LOG.debug("Catch All exception");
                LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
            }
           return response;    
        } 
         
         
         //PUT DEFAULT VALUE TO OK IF NOTHING WORKS    
     /* LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
        `String xmlString = Converter.convertFromObjectToXml(gradebook, Gradebook.class);
         response = Response.status(Response.Status.EXPECTATION_FAILED).entity(content).build();
        LOG.debug(" SHOULD NOT Generate THIS response {}", response);
        return response; */
    }

    /**
     * Retrieves representation of an instance of edu.asu.cse446.sample.crud.restws.GradebookResource
     * @param id
     * @return an instance of java.lang.String
     */
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteResource(@PathParam("id") String id) {
        LOG.info("Removing the Gradebook Resource {}", gradebook);
        LOG.debug("DELETE request in the new server");
        LOG.debug("PathParam id = {}", id);
            
        Response response = null;
        
        if (gradebooklist.isEmpty() ){
            LOG.info("Creating a {} {} Status Response", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
            LOG.debug("No Gradebook Resource to delete");
            
            response = Response.status(Response.Status.GONE).build();
        } else {
            //traverse through GradebookList
            int foundflag=0;
            for(int i=0 ; i< gradebooklist.size(); i ++ ){  
              if ( gradebooklist.get(i).getId() == Integer.parseInt(id)){ foundflag=1;
                LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
                LOG.debug("Deleting the Gradebook Resource {}", gradebook);
                 gradebook = null;
                 gradebooklist.remove(i);
                 response = Response.status(Response.Status.NO_CONTENT).build();
                }  
            }
             if(foundflag==0) {
                LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
                LOG.debug("Retrieving the Gradebook Resource {}", gradebook);
                response = Response.status(Response.Status.NOT_FOUND).build();
            }
        }        
        
        LOG.debug("Generated response {}", response);
        
        return response;
    }
    
    
    public int genrandnum(){
        Random randomGenerator = new Random(1234567890);
        return   Math.abs(randomGenerator.nextInt(1000)); 
    }
    
    public boolean gradebookIdisunique(int gradebookId){
        if(randnumset.contains(gradebookId))
        return false;
        return true;
    }
    
}

