package zhy.blog.dao.leveldb;

import com.alibaba.fastjson.JSON;
import zhy.blog.dao.IPoemDao;
import zhy.blog.entity.Poem;
import zhy.blog.util.Page;
import zhy.util.leveldb.query.Condition;
import zhy.util.leveldb.query.ConditionFilter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PoemDao extends BaseDao<Poem> implements IPoemDao {

    @Override
    @NotNull String dbName() {
        return "poem";
    }

    @Override
    public Poem get(int id) {
        return JSON.parseObject(helper.get(String.valueOf(id)), Poem.class);
    }

    @Override
    public List<Poem> find(Poem poem, Page page) {
        int start = page == null ? -1 : page.getStartNum();
        int end = page == null ? -1 : page.getEndNum();
        List<Condition> conditions = ConditionFilter.generateCondition(poem);
        Map<String, String> map = helper.find(conditions, start, end);
        return map.values()
                .parallelStream()
                .map(a -> JSON.parseObject(a, Poem.class))
                .collect(Collectors.toList());
    }
}
