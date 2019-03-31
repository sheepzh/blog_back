package zhy.blog.dao.leveldb;

import zhy.blog.dao.IArticleDao;
import zhy.blog.entity.Article;

import javax.validation.constraints.NotNull;

public class ArticleDao extends BaseDao<Article> implements IArticleDao {

    @Override
    @NotNull String dbName() {
        return "article";
    }
}
