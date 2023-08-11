package org.comfort42.busking.common.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.comfort42.busking.application.domain.model.Station;

import java.io.IOException;

public class StationIdJsonSerializer extends JsonSerializer<Station.StationId> {
    @Override
    public void serialize(Station.StationId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(value==null){
            gen.writeNull();
        }
        else{
            gen.writeString(value.toString());
        }
    }

    @Override
    public Class<Station.StationId> handledType() { return Station.StationId.class;}
}
