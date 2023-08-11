package org.comfort42.busking.common.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.comfort42.busking.application.domain.model.Report;

import java.io.IOException;

public class ReportIdJsonSerializer extends JsonSerializer<Report.ReportId> {
    @Override
    public Class<Report.ReportId> handledType(){return Report.ReportId.class;}

    @Override
    public void serialize(Report.ReportId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(value == null){
            gen.writeNull();
        }
        else{
            gen.writeString(value.toString());
        }
    }
}
