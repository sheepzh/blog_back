package zhy.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zhy.blog.dao.IGroupDao;
import zhy.blog.entity.GroupNode;

@RestController
public class GroupController extends BaseController {
    private final IGroupDao groupDao;

    @Autowired
    public GroupController(IGroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @RequestMapping(value = "group")
    public Object all(@RequestParam(value = "l", defaultValue = "0") int level) {
        GroupNode node = new GroupNode().setLevel(level);
        if (level != 0) node.setLevel(level);
        return exceptionWrap(() -> groupDao.find(node));
    }
}
