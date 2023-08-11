package org.comfort42.busking.common.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.comfort42.busking.application.domain.model.Company;

import java.io.IOException;

public class CompanyIdJsonSerializer extends JsonSerializer<Company.CompanyId> {
    @Override
    public Class<Company.CompanyId> handledType() {return Company.CompanyId.class;}

    @Override
    public void serialize(Company.CompanyId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(value==null){
            gen.writeNull();
        }
        else{
            gen.writeString(value.toString());
        }
    }
}
