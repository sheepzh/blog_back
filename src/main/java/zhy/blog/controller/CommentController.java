package zhy.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zhy.blog.dao.ICommentDao;
import zhy.blog.entity.Comment;
import zhy.blog.util.Response;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static zhy.blog.util.StringUtil.isBlank;

@RestController
public class CommentController {
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
        Response response = new Response();
        Comment comment = new Comment()
                .setContent(content)
                .setUser(user)
                .setTargetId(targetId)
                .setTargetType(Comment.COMMENT)
                .setEmail(email)
                .setTargetUrl(url);
        try {
            if (!isValid(comment)) return response.fail("Invalid comment");
            Integer id = commentDao.insertNormal(comment);
            response.success().setData(id);
        } catch (Exception e) {
            e.printStackTrace();
            response.internalError();
        }
        return response;
    }

    private boolean isValid(Comment comment) {
        return !isBlank(comment.getContent())
                && !isBlank(comment.getUser())
                && comment.getTargetId() != null
                && comment.getTargetType() != null;
    }
}
