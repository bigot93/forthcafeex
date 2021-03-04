
package forthcafe.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

// @FeignClient(name = "Message", url = "${api.url.message}", fallback = MessageServiceImpl.class) // 
@FeignClient(name="Message", url="${api.url.message}")
public interface MessageService {

    @RequestMapping(method= RequestMethod.GET, path="/messages", consumes = "application/json")
    public void message(@RequestBody Message message);

}