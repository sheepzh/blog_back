package zhy.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zhy.blog.dao.IStateDao;
import zhy.blog.util.Response;

@RestController
public class CommandController {
    private final IStateDao stateDao;

    @Autowired
    public CommandController(IStateDao stateDao) {
        this.stateDao = stateDao;
    }

    @RequestMapping(value = "/command/admin", method = RequestMethod.GET)
    public Object loginAdmin(@RequestParam("k") String k) {
        Response response = new Response();
        try {
            String value = stateDao.get("admin");
            response.success();
            if (k.equals(value)) response.setData(true);
            else response.setData(false);
        } catch (Exception e) {
            response.internalError();
        }
        return response;
    }
}
