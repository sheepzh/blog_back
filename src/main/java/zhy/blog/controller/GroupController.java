package zhy.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zhy.blog.dao.IGroupDao;
import zhy.blog.entity.GroupNode;
import zhy.blog.util.Response;

import java.util.List;

@RestController
public class GroupController {
    private final IGroupDao groupDao;

    @Autowired
    public GroupController(IGroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @RequestMapping(value = "group")
    public Object all(@RequestParam(value = "l", defaultValue = "0") int level) {
        Response response = new Response();
        GroupNode node = new GroupNode().setLevel(level);
        if (level != 0) node.setLevel(level);
        try {
            List<GroupNode> nodeList = groupDao.find(node);
            response.setData(nodeList);
            response.success();
        } catch (Exception e) {
            response.internalError();
        }
        return response;
    }
}
