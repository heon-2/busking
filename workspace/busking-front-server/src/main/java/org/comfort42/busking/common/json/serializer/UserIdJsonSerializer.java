package org.comfort42.busking.common.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.comfort42.busking.application.domain.model.User;

import java.io.IOException;

public class UserIdJsonSerializer extends JsonSerializer<User.UserId> {

    @Override
    public Class<User.UserId> handledType() {
        return User.UserId.class;
    }

    @Override
    public void serialize(User.UserId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(value.toString());
        }
    }

}
