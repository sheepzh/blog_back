package zhy.blog.util;

import zhy.blog.dao.leveldb.ArticleDao;
import zhy.blog.dao.leveldb.CommentDao;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DbOperator {
    private static GroupDao groupDao = new GroupDao();
    private static ArticleDao articleDao = new ArticleDao();
    private static CommentDao commentDao = new CommentDao();
    private static PoemDao poemDao = new PoemDao();
    private static StateDao stateDao = new StateDao();

    public static void main(String[] a) {
        poemDao.init();
        Poem poem = new Poem();
        for (int i = 0; i < 100; i++) {
            int tn = (int) (Math.random() * 10);
            poem.setTitle(StringUtil.sameChars('X', tn+3) );
            tn = (int) (Math.random() * 30) + 4;
            poem.setContent(IntStream.range(0, tn).mapToObj((int c) -> "\n" + StringUtil.sameChars('x', (int) (Math.random() * 10 + 8))).collect(Collectors.joining()).substring(1));
            poemDao.insertInitialized(poem);
        }
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

