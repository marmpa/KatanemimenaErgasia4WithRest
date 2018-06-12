package com.mariostom.ergasia4katanemimena;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class JsonDateSerializer extends JsonSerializer<Date> 
{//Κλάση για να κάνει την μετατροπή απο αντικείμενο τύπου date σε Json

    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");//το πως θα φαίνεται η ημερομηνία
    
    @Override
    public void serialize(Date t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException 
    {
        String formatedDate = dateFormat.format(t);//μετατρέπει την ημερομηνία σε string με βάση το παραπάνω format
        
        jg.writeString(formatedDate);//γράφει το string στο jsongenerator
    }
    
}
