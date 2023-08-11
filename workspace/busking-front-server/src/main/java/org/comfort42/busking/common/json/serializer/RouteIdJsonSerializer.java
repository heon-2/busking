package org.comfort42.busking.common.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.comfort42.busking.application.domain.model.Route;

import java.io.IOException;

public class RouteIdJsonSerializer extends JsonSerializer<Route.RouteId> {

    @Override
    public void serialize(Route.RouteId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(value==null){
            gen.writeNull();
        }
        else{
            gen.writeString(value.toString());
        }
    }

    @Override
    public Class<Route.RouteId> handledType() {return Route.RouteId.class;}

}
