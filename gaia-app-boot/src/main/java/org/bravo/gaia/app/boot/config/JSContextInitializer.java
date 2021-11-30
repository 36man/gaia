package org.bravo.gaia.app.boot.config;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * js上下文资源初始化
 * @author lijian
 *
 */
@Component
public class JSContextInitializer implements ServletContextAware {

	/**
	 * 设置项目上下文路径，第一是在html的动态脚本中进行设置(freemarker)
	 * 第二是在全文的JS文件中进行设置，这样方便所有界面使用项目上下文路径
	 */
	@Override
	public void setServletContext(ServletContext servletContext) {
		String contextPath = servletContext.getContextPath();
		servletContext.setAttribute("contextPath", contextPath);
		String crlf = System.getProperty("line.separator");
		StringBuilder context = new StringBuilder();
		context.append("<script type=\"text/javascript\">").append(crlf)
				.append("var contextPath = '" + contextPath + "';")
				.append(crlf).append("</script>").append(crlf);
		servletContext.setAttribute("jsContext", context);
	}

}
