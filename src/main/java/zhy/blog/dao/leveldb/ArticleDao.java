package zhy.blog.dao.leveldb;

import com.alibaba.fastjson.JSON;
import zhy.blog.dao.IArticleDao;
import zhy.blog.entity.Article;
import zhy.blog.util.Page;
import zhy.blog.util.Status;
import zhy.util.leveldb.client.LevelDbHelper;
import zhy.util.leveldb.query.Condition;
import zhy.util.leveldb.query.ConditionFilter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArticleDao extends BaseDao<Article> implements IArticleDao {

    public ArticleDao() {
        super.helper = new LevelDbHelper("article");
    }

    @Override
    public Article get(int id) {
        return JSON.parseObject(helper.get(String.valueOf(id)), Article.class);
    }

    @Override
    public List<Article> find(Article article, Page page) {
        int start = page == null ? -1 : page.getStartNum();
        int end = page == null ? -1 : page.getEndNum();
        List<Condition> conditions = ConditionFilter.generateCondition(article);
        conditions.add(new Condition("status", String.valueOf(Status.INVALID), Condition.NOT_EQUAL));
        conditions.add(new Condition("status", String.valueOf(Status.OLD), Condition.NOT_EQUAL));
        Map<String, String> map = helper.find(conditions, start, end);
        return map.values()
                .parallelStream()
                .map(a -> JSON.parseObject(a, Article.class))
                .collect(Collectors.toList());
    }

}
