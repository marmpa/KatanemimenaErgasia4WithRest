
package com.mariostom.ergasia4katanemimena;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class JsonDateDeserializer extends JsonDeserializer<Date>
{

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException 
    {
        String strDate = jp.getText();
        System.out.println(strDate);
        try
        {
            long millisecond = Long.parseLong(strDate);
            return new Date(millisecond);
        }
        catch(NumberFormatException ex)
        {
            throw new NumberFormatException();
        }
        catch(Exception ex)
        {
            System.out.println(ex + "Mariooooos");
            throw new RuntimeException(ex);
        }
        
    }
    
}
