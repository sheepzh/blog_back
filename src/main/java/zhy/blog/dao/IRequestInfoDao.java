package zhy.blog.dao;

import zhy.blog.entity.RequestInfo;

import java.util.concurrent.TimeUnit;

public interface IRequestInfoDao extends IUcrdDao<RequestInfo> {
	/**
	 * Count the request time recently from the remote host.
	 *
	 * @param requestInfo request
	 * @param timeLength  the length of the time interval
	 * @param unit        the unit of the timeLength
	 * @return total num
	 */
	int countByRemoteHost(RequestInfo requestInfo, int timeLength, TimeUnit unit);
}
