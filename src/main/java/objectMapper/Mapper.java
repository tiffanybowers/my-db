package objectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Mapper {
    private static Mapper instance;
    private static ObjectMapper mapper;

    public Mapper() {
        mapper = new ObjectMapper();
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public static Mapper getInstance() {
        if(instance == null) {
            instance = new Mapper();
        }
        return instance;
    }
}
