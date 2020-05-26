package lacuna.sample.webappgrantid;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {
    
    @Autowired
    private ObjectMapper objectMapper;

    public Map<String, ?> parseToken(String base64token) throws IOException {
        String token = base64token.split("\\.")[1];
        return objectMapper.readValue(Base64.decodeBase64(token), new TypeReference<Map<String, ?>>() {});
    }
}