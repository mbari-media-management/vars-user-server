package converters.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import scala.None;
import scala.Option;

import java.lang.reflect.Type;

/**
 * @author Brian Schlining
 * @since 2016-07-12T12:09:00
 */
public class OptionConverter implements JsonSerializer<scala.Option<?>> {

    @Override
    public JsonElement serialize(Option<?> src, Type typeOfSrc, JsonSerializationContext context) {
        if (src.isDefined()) {
            return context.serialize(src.get());
        }
        else {
            return JsonNull.INSTANCE;
        }
    }

}
