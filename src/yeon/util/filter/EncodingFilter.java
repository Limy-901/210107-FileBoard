package yeon.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class EncodingFilter implements Filter {
	
	private String encoding;
	protected FilterConfig filterConfig;
	//필터를 설정하는 객체 선언.
		
	
	public void init(FilterConfig filterConfig) 
	// 파라미터에 입력한 값으로 설정하는 메소드
	
		throws ServletException {
		this.filterConfig=filterConfig;
		//입력한걸로 대입됨.
		this.encoding=filterConfig.getInitParameter("encoding");
		// 파라미터
	}
	
	/*
	 * HttpServletRequest 객체에 web.xml에서 전달된 문자  인코딩을 설정하는 메소드.
	 */	 
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
		throws IOException,ServletException {
		if(request.getCharacterEncoding()==null) {
			if(encoding!=null) {
				// 둘 다 입력값이 있다면,
				request.setCharacterEncoding(encoding);
				// 해당 타입으로 인코딩.
			}
		}
		chain.doFilter(request,response);		
	}
    
	/*
	 *  web.xml에서 전달된 인코딩 값을 초기화하는 메소드
	 */
	
	public void destroy() {
		this.encoding=null;
		this.filterConfig=null;
		// 값을 null로 만들어줌.
	}	
	
	public FilterConfig getFilterConfig() {
		return filterConfig;
	}
	
	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig=filterConfig;
	}
}//end of class
