package zhy.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zhy.blog.dao.IStateDao;

@RestController
public class CommandController extends BaseController {
    private final IStateDao stateDao;

    @Autowired
    public CommandController(IStateDao stateDao) {
        this.stateDao = stateDao;
    }

    @RequestMapping(value = "/command/admin", method = RequestMethod.GET)
    public Object loginAdmin(@RequestParam("k") String k) {
        return exceptionWrap(() -> k.equals(stateDao.get("admin")));
    }
}
