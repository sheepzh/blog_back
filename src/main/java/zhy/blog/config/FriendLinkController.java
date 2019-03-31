package zhy.blog.config;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zhy.blog.util.Response;

@RestController
public class FriendLinkController {

    @RequestMapping("/friend")
    public Object all() {
        try {

        } catch (Exception e) {

        }
        return new Response();
    }

}
