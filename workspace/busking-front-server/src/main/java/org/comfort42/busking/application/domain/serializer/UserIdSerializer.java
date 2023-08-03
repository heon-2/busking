package org.comfort42.busking.application.domain.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.comfort42.busking.application.domain.model.User;

import java.io.IOException;

public class UserIdSerializer extends JsonSerializer<User.UserId> {

    @Override
    public void serialize(User.UserId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}