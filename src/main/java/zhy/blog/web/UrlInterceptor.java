package zhy.blog.web;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import zhy.blog.config.AppConfig;
import zhy.blog.dao.IRequestInfoDao;
import zhy.blog.entity.RequestInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UrlInterceptor implements HandlerInterceptor {
	
	private IRequestInfoDao requestInfoDao = new AppConfig().requestInfoDao();
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		visitCount(request);
	}
	
	private void visitCount(HttpServletRequest request) {
		String ip = request.getRemoteHost();
		String url = request.getRequestURI();
		RequestInfo requestInfo = new RequestInfo()
									.setUrl(url)
									.setIp(ip);
		try {
			requestInfoDao.insertNormal(requestInfo);
		} catch (Exception ignored) {
		}
	}
}


