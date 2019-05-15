package zhy.blog.util;

import zhy.blog.dao.leveldb.ArticleDao;
import zhy.blog.dao.leveldb.CommentDao;
import zhy.blog.dao.leveldb.FriendLinkDao;
import zhy.blog.dao.leveldb.GroupDao;
import zhy.blog.dao.leveldb.PoemDao;
import zhy.blog.dao.leveldb.StateDao;
import zhy.blog.entity.Article;
import zhy.blog.entity.BaseEntity;
import zhy.blog.entity.Comment;
import zhy.blog.entity.GroupNode;
import zhy.blog.entity.Poem;

import java.util.Arrays;
import java.util.List;

/**
 * Operator of database
 *
 * @author zhy
 */
public class DbOperator {
    private static GroupDao groupDao = new GroupDao();
    private static ArticleDao articleDao = new ArticleDao();
    private static CommentDao commentDao = new CommentDao();
    private static PoemDao poemDao = new PoemDao();
    private static StateDao stateDao = new StateDao();
    private static FriendLinkDao friendLinkDao = new FriendLinkDao();

    public static void main(String[] a) {
//        Article article = new Article().setGroupId(1).setTitle("测试").setContent("测试内容\n\n测试内容");
//        articleDao.insertInitialized(article);
//
//        GroupNode node = new GroupNode().setLevel(1).setName("1").setTags(new String[]{});
//        groupDao.insertNormal(node);
        Poem poem = new Poem().setTitle("测试").setContent("1\n\n2\n3\n555");
        poemDao.insertInitialized(poem);
    }

    private static void printGroup() {
        List<GroupNode> result = groupDao.find(null);
        System.out.println(result.stream().map(BaseEntity::toJSON).reduce(FuncUtil.joinStr("\n")).orElse(""));
    }

    private static void printArticle() {
        List<Article> result = articleDao.find(null, null);
        System.out.println(result.stream().map(BaseEntity::toJSON).reduce(FuncUtil.joinStr("\n")).orElse(""));
    }

    private static void printComment() {
        List<Comment> result = commentDao.find(null);
        System.out.println(result.stream().map(BaseEntity::toJSON).reduce(FuncUtil.joinStr("\n")).orElse(""));
    }

    private static void printPoem() {
        List<Poem> result = poemDao.find(null, null);
        System.out.println(result.stream().map(BaseEntity::toJSON).reduce(FuncUtil.joinStr("\n")).orElse(""));
    }

    private static void putGroup(GroupNode... groupNodes) {
        Arrays.stream(groupNodes).forEach(groupDao::update);
    }

    private static void putArticle(Article... articles) {
        Arrays.stream(articles).forEach(articleDao::update);
    }

    private static void putComment(Comment... comments) {
        Arrays.stream(comments).forEach(commentDao::update);
    }


}

