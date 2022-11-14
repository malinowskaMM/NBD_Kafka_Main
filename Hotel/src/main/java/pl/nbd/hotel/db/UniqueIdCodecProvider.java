package pl.nbd.hotel.db;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.UUID;

public class UniqueIdCodecProvider implements CodecProvider {

    public UniqueIdCodecProvider() {

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == UniqueId.class) {
            return (Codec<T>) new UniqueIdCodec(registry);
        }
        return null;
    }
}
