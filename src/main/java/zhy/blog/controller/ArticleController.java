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
import zhy.blog.entity.GroupNode;
import zhy.blog.entity.GroupTreeNode;
import zhy.blog.util.Page;
import zhy.blog.util.Response;
import zhy.blog.util.Status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ArticleController {

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
     * @param groupId      group id
     * @param pageNum      number of page
     * @param pagePer      number of article per page
     * @param includeChild whether include articles in child groups.
     * @return response
     */
    @RequestMapping(value = "article", method = GET)
    public Response find(@RequestParam("g") int groupId,
                         @RequestParam(value = "s", defaultValue = "false") boolean includeChild,
                         @RequestParam(value = "pn", defaultValue = "1") int pageNum,
                         @RequestParam(value = "pp", defaultValue = "10") int pagePer) {
        Response response = new Response();
        List<GroupNode> nodes = groupDao.find(null);
        nodes = GroupTreeNode.allChildren(nodes, groupId);
        try {
            List<Article> result = new ArrayList<>();
            for (GroupNode node : nodes) {
                Article cond = new Article().setGroupId(node.getId());
                int total = articleDao.count(cond);
                Page page = new Page(total, pagePer, pageNum);
                result.addAll(articleDao.find(cond, page));
            }
            int start = (pageNum - 1) * pagePer;
            int end = Math.min(result.size(), pageNum * pagePer);
            result = end > start ? result.subList(start, end) : Collections.emptyList();
            response.setData(result);
        } catch (Exception e) {
            e.printStackTrace();
            response.internalError();
        }
        return response;
    }

    /**
     * To add an article
     *
     * @param article to add
     * @return response
     */
    @RequestMapping(value = "article", method = POST)
    public Response add(Article article) {
        Response response = new Response();
        try {
            if (!article.isValid()) return response.fail("Invalid article");
            Date current = new Date();
            article.setCreateDate(current);
            article.setUpdateDate(current);
            article.setStatus(Status.INITIALIZED);
            articleDao.insert(article);
        } catch (Exception e) {
            response.internalError();
        }
        return response;
    }

    /**
     * Get article by id
     *
     * @param id id
     * @return response
     */
    @RequestMapping(value = "article/{id}", method = GET)
    public Response get(@PathVariable("id") int id) {
        Response response = new Response();
        try {
            Article article = articleDao.get(id);
            response = article == null ? response.notFound() : response.setData(article);
        } catch (Exception e) {
            e.printStackTrace();
            response.internalError();
        }
        return response;
    }

    /**
     * Delete
     *
     * @param id article id
     * @return response
     */
    @RequestMapping(value = "article/{id}", method = DELETE)
    public Response delete(@PathVariable("id") int id) {
        Response response = new Response();
        try {
            Article article = articleDao.get(id);
            if (article == null) return response.notFound();
            article.setStatus(Status.OLD);
            articleDao.update(article);
        } catch (Exception e) {
            e.printStackTrace();
            response.internalError();
        }
        return response;
    }

    /**
     * Update
     *
     * @param article to update
     * @return response
     */
    @RequestMapping(value = "article", method = PATCH)
    public Response update(Article article) {
        Response response = new Response();
        try {
            if (article.getId() == null) return response.notFound();
            if (!article.isValid()) return response.fail("Invalid article");
            articleDao.update(article);
        } catch (Exception e) {
            e.printStackTrace();
            response.internalError();
        }
        return response;
    }

    @RequestMapping(value = "/article/{id}/comment", method = GET)
    public Response commentList(@PathVariable("id") int targetId) {
        Response response = new Response();
        try {
            Comment cond = new Comment().setTargetId(targetId).setTargetType(Comment.ARTICLE);
            List<Comment> result = commentDao.findAll(cond);
            result.sort(Comparator.comparing(Comment::getId).reversed());
            response.success().setData(result);
        } catch (Exception e) {
            e.printStackTrace();
            response.internalError();
        }
        return response;
    }

    @RequestMapping(value = "/article/{id}/comment", method = POST)
    public Response submitComment(@RequestParam String user,
                                  @RequestParam String content,
                                  @RequestParam String email,
                                  @PathVariable("id") Integer targetId,
                                  @RequestParam(defaultValue = "") String url) {
        Response response = new Response();
        Comment comment = new Comment()
                .setContent(content)
                .setUser(user)
                .setTargetId(targetId)
                .setTargetType(Comment.ARTICLE)
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
