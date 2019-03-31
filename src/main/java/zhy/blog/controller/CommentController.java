package zhy.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zhy.blog.dao.ICommentDao;
import zhy.blog.entity.Comment;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class CommentController extends BaseController {
    private final ICommentDao commentDao;

    @Autowired
    public CommentController(ICommentDao commentDao) {
        this.commentDao = commentDao;
    }


    @RequestMapping(value = "/comment", method = POST)
    public Object add(@RequestParam String user,
                      @RequestParam String content,
                      @RequestParam String email,
                      @RequestParam Integer targetId,
                      @RequestParam(defaultValue = "") String url) {
        return exceptionWrap(() -> {
            Comment comment = new Comment()
                    .setContent(content)
                    .setUser(user)
                    .setTargetId(targetId)
                    .setTargetType(Comment.COMMENT)
                    .setEmail(email)
                    .setTargetUrl(url);
            comment.assertValid();
            return commentDao.insertNormal(comment);
        });
    }
}
