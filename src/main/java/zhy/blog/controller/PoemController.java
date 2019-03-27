package zhy.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zhy.blog.dao.IArticleDao;
import zhy.blog.dao.ICommentDao;
import zhy.blog.dao.IPoemDao;
import zhy.blog.entity.Poem;
import zhy.blog.util.Page;
import zhy.blog.util.Response;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class PoemController {
    private IPoemDao poemDao;

    @Autowired
    public PoemController(IPoemDao poemDao) {
        this.poemDao = poemDao;
    }

    @RequestMapping(value = "/poem", method = GET)
    public Object list(@RequestParam(value = "pn", defaultValue = "1") int pageNum,
                       @RequestParam(value = "pp", defaultValue = "10") int pagePer) {
        Response response = new Response();
        try {
            int totalPage = poemDao.count(null);
            Page page = new Page(totalPage, pagePer, pageNum);
            List<Poem> poems = poemDao.find(null, page);
            response.success().setData(poems);
        } catch (Exception e) {
            e.printStackTrace();
            response.internalError();
        }
        return response;
    }

    @RequestMapping(value = "/poem/{id}", method = GET)
    public Object get(@PathVariable int id) {
        Response response = new Response();
        try {
            Poem poem = poemDao.get(id);
            response.success().setData(poem);
        } catch (Exception e) {
            e.printStackTrace();
            response.internalError();
        }
        return response;
    }
}
