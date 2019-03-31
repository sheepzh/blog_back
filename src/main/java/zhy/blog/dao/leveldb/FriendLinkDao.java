package zhy.blog.dao.leveldb;

import zhy.blog.dao.IFriendLinkDao;
import zhy.blog.entity.FriendLink;

import javax.validation.constraints.NotNull;

public class FriendLinkDao extends BaseDao<FriendLink> implements IFriendLinkDao {

    @NotNull
    @Override
    String dbName() {
        return "friend_link";
    }
}
