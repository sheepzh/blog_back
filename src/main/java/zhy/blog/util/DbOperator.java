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

public class DbOperator {
    private static GroupDao groupDao = new GroupDao();
    private static ArticleDao articleDao = new ArticleDao();
    private static CommentDao commentDao = new CommentDao();
    private static PoemDao poemDao = new PoemDao();
    private static StateDao stateDao = new StateDao();

    public static void main(String[] a) {
        Poem poem = new Poem()
                .setTitle("春天")
                .setContent("我已不再贪图黑夜\n" +
                        "妈妈\n" +
                        "沉醉不如沉睡\n" +
                        "\n" +
                        "我的慢性咽炎不见好转\n" +
                        "一咳嗽\n" +
                        "还是会伤了你的心\n" +
                        "\n" +
                        "今天云彩明媚，消失了动物的踪迹\n" +
                        "我打算遗弃自己：\n" +
                        "\n" +
                        "电梯从八楼开始坠落\n" +
                        "我喑哑的失眠尚存恐惧\n" +
                        "\n" +
                        "左侧站了一个带耳机的女孩\n" +
                        "与我对视\n" +
                        "“你喜欢魁北克民谣吗？”\n" +
                        "\n" +
                        "我们望向窗外。人群\n" +
                        "在对话\n" +
                        "每粒沙子容得下两个人\n" +
                        "\n" +
                        "绿灯开始收场\n" +
                        "匆忙和徒劳混迹于斑马线\n" +
                        "\n" +
                        "黄昏是最绝望的灵魂\n" +
                        "\n" +
                        "我爱上了迷路。不止一次地\n" +
                        "路过同一个酒吧\n" +
                        "服务生在歌唱\n" +
                        "\n" +
                        "孤独行走的人\n" +
                        "连狂喜都不敢接受\n" +
                        "\n" +
                        "世界悄然改换了模样\n" +
                        "我度年如日\n" +
                        "\n" +
                        "你一开口，我就认出你");
        poemDao.insertInitialized(poem);
        printPoem();
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

