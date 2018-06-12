
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


public class JsonDateDeserializer extends JsonDeserializer<Date>//Κλάση κάνει extend JsonDeserializer για να αλλάξω το κάνει json σε αντικείμενο τύπου date
{

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException 
    {
        String strDate = jp.getText();//παίρνει το text απο που αντιστοιχή στο date
        System.out.println(strDate);
        try
        {
            long millisecond = Long.parseLong(strDate);//δοκιμάζει να κάνει το παραπάνω κείμενο σε milisecond
            return new Date(millisecond);//γυρίζει καινούργεια ημερομηνία με βάση τα ms που πήρε
        }//αλλιώς πέτα κάποιο exception
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
