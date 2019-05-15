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
import zhy.blog.util.Status;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class PoemController extends BaseController {
    private IPoemDao poemDao;
    private final ICommentDao commentDao;

    @Autowired
    public PoemController(IPoemDao poemDao, ICommentDao commentDao) {
        this.poemDao = poemDao;
        this.commentDao = commentDao;
    }

    @RequestMapping(value = "/poem", method = GET)
    public Object list() {
        return exceptionWrap(() -> poemDao.find(null));
    }

    @RequestMapping(value = "/poem/{id}", method = GET)
    public Object get(@PathVariable int id) {
        return exceptionWrap(() -> poemDao.get(id));
    }

    @RequestMapping(value = "/poem/{id}/comment", method = GET)
    public Object getComments(@PathVariable int id) {
        return exceptionWrap(() -> {
            Comment cond = new Comment().setTargetId(id).setTargetType(Comment.POEM);
            List<Comment> result = commentDao.findAll(cond);
            result.sort(Comparator.comparing(Comment::getId).reversed());
            return result;
        });
    }

    @RequestMapping(value = "/poem/{id}/comment", method = POST)
    public Object postComment(@RequestParam String user,
                              @RequestParam String content,
                              @RequestParam(value = "email", defaultValue = "") String email,
                              @PathVariable("id") Integer targetId,
                              @RequestParam(defaultValue = "") String url) {
        return exceptionWrap(() -> {
            Comment comment = new Comment()
                    .setContent(content)
                    .setUser(user)
                    .setTargetId(targetId)
                    .setTargetType(Comment.POEM)
                    .setEmail(email)
                    .setTargetUrl(url);
            comment.assertValid();
            return commentDao.insertNormal(comment);
        });
    }

    @RequestMapping(value = "/poem", method = POST)
    public Object addPoem(@RequestParam String title,
                          @RequestParam String content) {
        return exceptionWrap(u -> {
            Poem poem = (Poem) new Poem()
                    .setTitle(title)
                    .setContent(content)
                    .assertValid()
                    .setCreateDate(new Date())
                    .setUpdateDate(new Date())
                    .setStatus(Status.INITIALIZED);
            poemDao.insertInitialized(poem);
        });
    }

    @RequestMapping(value = "/poem/{id}", method = PATCH)
    public Object updatePoem(@PathVariable int id,
                             @RequestParam String title,
                             @RequestParam String content) {
        return exceptionWrap(u -> {
            Poem poem = poemDao.get(id);
            Objects.requireNonNull(poem)
                    .setTitle(title)
                    .setContent(content)
                    .assertValid()
                    .setUpdateDate(new Date());
            poemDao.update(poem);
        });
    }
}
