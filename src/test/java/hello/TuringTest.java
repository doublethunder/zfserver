package hello;

import com.dt.miniapp.util.JsonConvert;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenlei
 * @date 2019-04-24
 */
@Slf4j
public class TuringTest extends BaseTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void test() {
        String url = "http://openapi.tuling123.com/openapi/api/v2";

        Map<String, Object> params = new HashMap<>();
        params.put("reqType", 0);
        params.put("perception", buildPerception());
        params.put("userInfo", buildUserInfo());

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);

        String json = JsonConvert.serializeObject(params);

        log.info("requ={}", json);

        HttpEntity<String> formEntity = new HttpEntity<>(json, headers);

        Object responseEntity = restTemplate.postForObject(url, formEntity, Map.class);

        log.info("res={}", JsonConvert.serializeObject(responseEntity));
    }

    private Object buildUserInfo() {
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("apiKey", "14c8538a64064e3c82394ad136017692");
        userInfo.put("userId", "8fd6512af903bdbb");
        return userInfo;
    }

    Object buildPerception() {
        Map<String, Object> perception = new HashMap<>();
        Map<String, String> text = new HashMap<>();
        text.put("text", "你好");
        perception.put("inputText", text);
        return perception;
    }
}
