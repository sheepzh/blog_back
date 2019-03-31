package zhy.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zhy.blog.dao.IArticleDao;
import zhy.blog.dao.ICommentDao;
import zhy.blog.dao.IFriendLinkDao;
import zhy.blog.dao.IGroupDao;
import zhy.blog.dao.IPoemDao;
import zhy.blog.dao.IStateDao;
import zhy.blog.dao.leveldb.ArticleDao;
import zhy.blog.dao.leveldb.CommentDao;
import zhy.blog.dao.leveldb.FriendLinkDao;
import zhy.blog.dao.leveldb.GroupDao;
import zhy.blog.dao.leveldb.PoemDao;
import zhy.blog.dao.leveldb.StateDao;

@Configuration
public class DaoBeanConfig {
    @Bean("articleDao")
    public IArticleDao initArticleDao() {
        return new ArticleDao();
    }

    @Bean("groupDao")
    public IGroupDao initGroupDao() {
        return new GroupDao();
    }

    @Bean("commentDao")
    public ICommentDao initContentDao() {
        return new CommentDao();
    }

    @Bean("poemDao")
    public IPoemDao initPoemDao() {
        return new PoemDao();
    }

    @Bean("stateDao")
    public IStateDao initStateDao() {
        return new StateDao();
    }

    @Bean("friendLinkDao")
    public IFriendLinkDao initFriendLinkDao() {
        return new FriendLinkDao();
    }
}
