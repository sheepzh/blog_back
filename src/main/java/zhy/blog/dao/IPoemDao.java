package zhy.blog.dao;

import zhy.blog.entity.Poem;
import zhy.blog.util.Page;

import java.util.List;

public interface IPoemDao extends IUcrdDao<Poem>{
    Poem get(int id);

    List<Poem> find(Poem poem, Page page);
}
