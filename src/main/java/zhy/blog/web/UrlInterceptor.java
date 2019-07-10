package zhy.blog.web;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import zhy.blog.config.AppConfig;
import zhy.blog.dao.IRequestInfoDao;
import zhy.blog.entity.RequestInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static zhy.blog.util.StringUtil.isBlank;

public class UrlInterceptor implements HandlerInterceptor {
	
	private IRequestInfoDao requestInfoDao = new AppConfig().requestInfoDao();
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		visitCount(request);
	}
	
	private void visitCount(HttpServletRequest request) {
		
		String ip = request.getHeader("x-forwarded-for");
		if (isBlank(ip) || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("Proxy-Client-IP");
		if (isBlank(ip) || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (isBlank(ip) || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("HTTP_CLIENT_IP");
		if (isBlank(ip) || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (isBlank(ip) || ip.equalsIgnoreCase("unknown"))
			ip = request.getRemoteAddr();
		String param = request.getQueryString();
		param = isBlank(param) ? param : ("?" + param);
		String url = request.getServletPath() + param;
		RequestInfo requestInfo = new RequestInfo()
									  .setUrl(url)
									  .setIp(ip);
		try {
			requestInfoDao.insertNormal(requestInfo);
		} catch (Exception ignored) {
		}
	}
}


