package zhy.blog.dao.leveldb;

import zhy.blog.dao.IRequestInfoDao;
import zhy.blog.entity.RequestInfo;
import zhy.util.leveldb.query.Condition;
import zhy.util.leveldb.query.Operation;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class RequestInfoDao extends BaseDao<RequestInfo> implements IRequestInfoDao {
	@Override
	@NotNull String dbName() {
		return "request_info";
	}
	
	@Override
	public int countByRemoteHost(RequestInfo requestInfo, int timeLength, TimeUnit unit) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, (int) -unit.toMinutes(timeLength));
		String startTimeMills = String.valueOf(calendar.getTimeInMillis());
		Condition condition = new Condition("requestTime", startTimeMills, Operation.NUMBER_GREATER);
		setAdditionalCondition(Collections.singletonList(condition));
		
		return count(requestInfo);
	}
}
