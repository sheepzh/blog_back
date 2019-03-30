package zhy.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zhy.blog.dao.ICommentDao;
import zhy.blog.dao.IPoemDao;
import zhy.blog.entity.Comment;
import zhy.blog.entity.Poem;
import zhy.blog.util.Response;

import java.util.Comparator;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class PoemController {
    private IPoemDao poemDao;
    private final ICommentDao commentDao;

    @Autowired
    public PoemController(IPoemDao poemDao, ICommentDao commentDao) {
        this.poemDao = poemDao;
        this.commentDao = commentDao;
    }

    @RequestMapping(value = "/poem", method = GET)
    public Object list() {
        Response response = new Response();
        try {
            int totalPage = poemDao.count(null);
            List<Poem> poems = poemDao.find(null, null);
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

    @RequestMapping(value = "/poem/{id}/comment", method = GET)
    public Object getComments(@PathVariable int id) {
        Response response = new Response();
        try {
            Comment cond = new Comment().setTargetId(id).setTargetType(Comment.POEM);
            List<Comment> result = commentDao.findAll(cond);
            result.sort(Comparator.comparing(Comment::getId).reversed());
            response.success().setData(result);
        } catch (Exception e) {
            e.printStackTrace();
            response.internalError();
        }
        return response;
    }

    @RequestMapping(value = "/poem/{id}/comment", method = POST)
    public Object postComment(@RequestParam String user,
                              @RequestParam String content,
                              @RequestParam(value = "email", defaultValue = "") String email,
                              @PathVariable("id") Integer targetId,
                              @RequestParam(defaultValue = "") String url) {
        Response response = new Response();
        Comment comment = new Comment()
                .setContent(content)
                .setUser(user)
                .setTargetId(targetId)
                .setTargetType(Comment.POEM)
                .setEmail(email)
                .setTargetUrl(url);
        try {
            if (!comment.isValid()) return response.fail("Invalid comment");
            Integer id = commentDao.insertNormal(comment);
            response.success().setData(id);
        } catch (Exception e) {
            e.printStackTrace();
            response.internalError();
        }
        return response;

    }
}
