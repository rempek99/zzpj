package p.lodz.pl.zzpj.sharethebill.utils.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.Map;

public class CurrencyDeserializer extends StdDeserializer<Map> {

    private static final long serialVersionUID = 1L;

    protected CurrencyDeserializer() {
        super(Map.class);
    }


    @Override
    public Map<String, Float> deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        JsonDeserializer<Object> deserializer = ctxt.findRootValueDeserializer(ctxt.constructType(Map.class));
        Map<String, Float> map = (Map<String, Float>) deserializer.deserialize(jp, ctxt);

        return map;
    }

}