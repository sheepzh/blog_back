package zhy.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zhy.blog.dao.IArticleDao;
import zhy.blog.dao.ICommentDao;
import zhy.blog.dao.IGroupDao;
import zhy.blog.entity.Article;
import zhy.blog.entity.Comment;
import zhy.blog.util.Response;
import zhy.blog.util.Status;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ArticleController extends BaseController {

    private final IArticleDao articleDao;
    private final IGroupDao groupDao;
    private final ICommentDao commentDao;

    @Autowired
    public ArticleController(IArticleDao articleDao, IGroupDao groupDao, ICommentDao commentDao) {
        this.commentDao = commentDao;
        this.articleDao = articleDao;
        this.groupDao = groupDao;
    }


    /**
     * Get the article list by page
     *
     * @param groupId group id
     * @param pageNum number of page
     * @param pagePer number of article per page
     * @return response
     */
    @RequestMapping(value = "article", method = GET)
    public Response find(@RequestParam("g") int groupId,
                         @RequestParam(value = "pn", defaultValue = "1") int pageNum,
                         @RequestParam(value = "pp", defaultValue = "10") int pagePer) {
        return exceptionWrap(() -> {
//            List<GroupNode> nodes = groupDao.find(null);
//            nodes = GroupTreeNode.allChildren(nodes, groupId);
//            List<Article> result = new ArrayList<>();
//            for (GroupNode node : nodes) {
//                Article cond = new Article().setGroupId(node.getId());
//                int total = articleDao.count(cond);
//                Page page = new Page(total, pagePer, pageNum);
//                result.addAll(articleDao.find(cond, page));
//            }
//            int start = (pageNum - 1) * pagePer;
//            int end = Math.min(result.size(), pageNum * pagePer);
//            return end > start ? result.subList(start, end) : Collections.emptyList();
            return articleDao.find(null);
        });
    }

    /**
     * To add an article
     *
     * @return response
     */
    @RequestMapping(value = "article", method = POST)
    public Response add(@RequestParam String title,
                        @RequestParam String content,
                        @RequestParam Integer group) {
        return exceptionWrap(u -> {
            Article article = new Article().setContent(content).setTitle(title).setGroupId(group);
            article.assertValid();
            Date current = new Date();
            article.setCreateDate(current);
            article.setUpdateDate(current);
            article.setStatus(Status.INITIALIZED);
            articleDao.insert(article);
        });
    }

    /**
     * Update the article
     *
     * @return response
     */
    @RequestMapping(value = "article/{id}", method = PATCH)
    public Response update(@PathVariable int id,
                           @RequestParam String title,
                           @RequestParam String content,
                           @RequestParam Integer group) {
        return exceptionWrap(u -> {
            Article article = articleDao.get(id);
            Objects.requireNonNull(article);
            article.setTitle(title)
                    .setContent(content)
                    .setGroupId(group)
                    .setUpdateDate(new Date());
            article.assertValid();
            articleDao.update(article);
        });
    }

    /**
     * Get article by id
     *
     * @param id id
     * @return response
     */
    @RequestMapping(value = "article/{id}", method = GET)
    public Response get(@PathVariable("id") int id) {
        return exceptionWrap(() -> articleDao.get(id));
    }

    /**
     * Delete
     *
     * @param id article id
     * @return response
     */
    @RequestMapping(value = "article/{id}", method = DELETE)
    public Response delete(@PathVariable("id") int id) {
        return exceptionWrap(u -> {
            Article article = articleDao.get(id);
            if (article == null) return;
            article.setStatus(Status.OLD);
            articleDao.update(article);
        });
    }

    @RequestMapping(value = "/article/{id}/comment", method = GET)
    public Response commentList(@PathVariable("id") int targetId) {
        return exceptionWrap(() -> {
            Comment cond = new Comment().setTargetId(targetId).setTargetType(Comment.ARTICLE);
            List<Comment> result = commentDao.findAll(cond);
            result.sort(Comparator.comparing(Comment::getId).reversed());
            return result;
        });
    }

    @RequestMapping(value = "/article/{id}/comment", method = POST)
    public Response submitComment(@RequestParam String user,
                                  @RequestParam String content,
                                  @RequestParam String email,
                                  @PathVariable("id") Integer targetId,
                                  @RequestParam(defaultValue = "") String url) {
        return exceptionWrap(() -> {
            Comment comment = new Comment()
                    .setContent(content)
                    .setUser(user)
                    .setTargetId(targetId)
                    .setTargetType(Comment.ARTICLE)
                    .setEmail(email)
                    .setTargetUrl(url);
            comment.assertValid();
            return commentDao.insertNormal(comment);
        });
    }
}
