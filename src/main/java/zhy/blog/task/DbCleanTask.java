package zhy.blog.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zhy.blog.dao.IRequestInfoDao;
import zhy.blog.entity.RequestInfo;
import zhy.util.leveldb.query.Condition;
import zhy.util.leveldb.query.Operation;

import java.util.Calendar;
import java.util.Collections;

@Component
public class DbCleanTask extends Thread {
	final IRequestInfoDao requestInfoDao;
	
	@Autowired
	public DbCleanTask(IRequestInfoDao requestInfoDao) {
		this.requestInfoDao = requestInfoDao;
	}
	
	@Override
	public void run() {
		//Records will be saved in 30 days.
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -30);
		Condition condition = new Condition("createDate", String.valueOf(calendar.getTimeInMillis()),
			Operation.NUMBER_LOWER);
		requestInfoDao.setAdditionalCondition(Collections.singletonList(condition));
		requestInfoDao.find(null).stream()
			.map(RequestInfo::getId)
			.forEach(requestInfoDao::delete);
	}
}
