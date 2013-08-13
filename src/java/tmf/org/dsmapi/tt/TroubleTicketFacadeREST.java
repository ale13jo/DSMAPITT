/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.tt;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author pierregauthier
 */
@Stateless
@Path("tmf.org.dsmapi.tt.troubleticket")
public class TroubleTicketFacadeREST extends AbstractFacade<TroubleTicket> {
    @PersistenceContext(unitName = "DSTroubleTicketPU")
    private EntityManager em;

    public TroubleTicketFacadeREST() {
        super(TroubleTicket.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(TroubleTicket entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/json"})
    public void edit(TroubleTicket entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public TroubleTicket find(@PathParam("id") String id) {
        
        TroubleTicket tt = new TroubleTicket();
        tt.setId("42");
        tt.setNotes(null);
        tt.setCreationDate(null);
        
       
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<TroubleTicket> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<TroubleTicket> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("proto")
    @Produces({"application/json"})
    public TroubleTicket proto() {
       TroubleTicket tt = new TroubleTicket();
       Date dt = new Date();
       String dts = toString(dt);
       
       
       tt.setCreationDate(dts);
       tt.setStatus(Status.Acknowledged);
       tt.setSeverity(Severity.Medium);
       tt.setType("Bills, charges or payment");
       
       RelatedObject ro = new RelatedObject();
       ro.setInvolvement("involvment");
       ro.setReference("referenceobject");
       
       RelatedObject relatedObjects[] = new RelatedObject[2];
       relatedObjects[0] = ro;
       relatedObjects[1] = ro;
       tt.setRelatedObjects(relatedObjects);
       
       RelatedParty rp = new RelatedParty();
       rp.setRole("role");
       rp.setReference("reference party");
       
       RelatedParty relatedParties[] = new RelatedParty[2];
       relatedParties[0] = rp;
       relatedParties[1] = rp;
       tt.setRelatedParties(relatedParties);
               
       Note note = new Note();
       note.setAuthor("author");
       note.setDate("date");
       note.setText("text");
       Note notes[] = new Note[2];
       notes[0] = note;
       notes[1] = note;
       tt.setNotes(notes);
       return tt;
       
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public static String toString( Date date ) {
        
        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssz" );
        
        TimeZone tz = TimeZone.getTimeZone( "UTC" );
        
        df.setTimeZone( tz );

        String output = df.format( date );
        
        return output;
/*
        int inset0 = 9;
        int inset1 = 6;
        
        String s0 = output.substring( 0, output.length() - inset0 );
        String s1 = output.substring( output.length() - inset1, output.length() );

        String result = s0 + s1;

        result = result.replaceAll( "UTC", "+00:00" ); 
        
        return result; */
        
    }
    
     public static Date parse( String input ) throws java.text.ParseException {

        //NOTE: SimpleDateFormat uses GMT[-+]hh:mm for the TZ which breaks
        //things a bit.  Before we go on we have to repair this.
        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssz" );
        
        //this is zero time so we need to add that TZ indicator for 
        if ( input.endsWith( "Z" ) ) {
            input = input.substring( 0, input.length() - 1) + "GMT-00:00";
        } else {
            int inset = 6;
        
            String s0 = input.substring( 0, input.length() - inset );
            String s1 = input.substring( input.length() - inset, input.length() );

            input = s0 + "GMT" + s1;
        }
        
        return df.parse( input );
        
    }

    
}