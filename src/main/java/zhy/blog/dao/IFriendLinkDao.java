package zhy.blog.dao;

import zhy.blog.entity.FriendLink;

import java.util.List;

/**
 * DAO of friend link
 *
 * @author zhy
 */
public interface IFriendLinkDao extends IUcrdDao<FriendLink> {
    List<FriendLink> find(FriendLink cond);

    FriendLink get(int id);
}
