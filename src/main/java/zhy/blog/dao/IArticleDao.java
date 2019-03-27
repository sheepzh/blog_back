package zhy.blog.dao;

import zhy.blog.entity.Article;
import zhy.blog.util.Page;

import java.util.List;

/**
 * DAO of article
 *
 * @author zhyyy
 * @since 20190307
 */
public interface IArticleDao extends IUcrdDao<Article> {
    Article get(int id);

    List<Article> find(Article article, Page page);
}
