package zhy.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zhy.blog.dao.ICommentDao;
import zhy.blog.entity.Comment;
import zhy.util.leveldb.query.Condition;
import zhy.util.leveldb.query.Operation;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Collections;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
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
					  @RequestParam(defaultValue = "") String url, HttpServletRequest request) {
		return exceptionWrap(() -> {
			Comment comment = new Comment()
								  .setContent(content)
								  .setUser(user)
								  .setTargetId(targetId)
								  .setTargetType(Comment.COMMENT)
								  .setEmail(email)
								  .setTargetUrl(url)
								  .setIpAddress(request.getRemoteHost());
			comment.assertValid();
			return commentDao.insertNormal(comment);
		});
	}
	
	@RequestMapping(value = "/comment/latest", method = GET)
	public Object latest() {
		return exceptionWrap(() -> {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -10);
			Condition condition = new Condition("createDate", calendar.getTimeInMillis() + "", Operation.NUMBER_GREATER);
			commentDao.setAdditionalCondition(Collections.singletonList(condition));
			return commentDao.findAll(null);
		});
	}
	
	@RequestMapping(value = "/comment/{id}", method = DELETE)
	public Object delete(@PathVariable int id) {
		return exceptionWrap(() -> commentDao.delete(id));
	}
}
