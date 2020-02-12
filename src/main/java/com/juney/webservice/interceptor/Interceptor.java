package com.juney.webservice.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.juney.webservice.config.auth.CustomUserDetailService;
import com.juney.webservice.domain.user.User;
import com.juney.webservice.domain.user.UserRepository;
import com.juney.webservice.util.Jws;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Interceptor extends HandlerInterceptorAdapter{

	private UserRepository userRepository;
	private CustomUserDetailService customUserDetailService;
	private HttpSession httpSession;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestJws = request.getHeader("X-JWS");
		if(requestJws != null) {
			String id = Jws.parseJws(requestJws);
			User user = userRepository.findById(Long.parseLong(id)).get();
			if(user != null) {
				UserDetails userDetail = 
						customUserDetailService.loadUserByUsername(user.getEmail());
				Authentication authentication = 
			    		new UsernamePasswordAuthenticationToken(userDetail, userDetail.getPassword(), userDetail.getAuthorities());
				SecurityContext securityContext = SecurityContextHolder.getContext();
				securityContext.setAuthentication(authentication);
				httpSession.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
				response.sendRedirect("/");
			}
		}
		return super.preHandle(request, response, handler);
	}
  
	
	
}
