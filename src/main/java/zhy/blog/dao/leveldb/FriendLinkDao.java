package zhy.blog.dao.leveldb;

import org.springframework.lang.NonNull;
import zhy.blog.dao.IFriendLinkDao;
import zhy.blog.entity.FriendLink;

import javax.validation.constraints.NotNull;
import java.util.List;

public class FriendLinkDao extends BaseDao<FriendLink> implements IFriendLinkDao {

    @NotNull
    @Override
    String dbName() {
        return "friend_link";
    }

    @Override
    public List<FriendLink> find(FriendLink cond) {
        return null;
    }

    @Override
    public FriendLink get(int id) {
        return null;
    }
}
