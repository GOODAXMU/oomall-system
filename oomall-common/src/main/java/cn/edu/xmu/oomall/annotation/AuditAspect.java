package cn.edu.xmu.oomall.annotation;

import cn.edu.xmu.oomall.util.JwtHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @auther mingqiu
 * @date 2020/6/26 下午2:16
 * modifiedBy Ming Qiu 2020/11/3 22:59
 */
@Aspect
@Component
public class AuditAspect {

	private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);

	@Pointcut("@annotation(cn.edu.xmu.oomall.annotation.Audit)")
	public void auditAspect() {
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 *
	 * @param joinPoint 切点
	 */
	@Before("auditAspect()")
	public void doBefore(JoinPoint joinPoint) {
	}


	@Around("auditAspect()")
	public Object around(JoinPoint joinPoint) {
		logger.debug("around: begin joinPoint = " + joinPoint);

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String token = request.getHeader(JwtHelper.LOGIN_TOKEN_KEY);

		JwtHelper.UserAndDepart userAndDepart = new JwtHelper().verifyTokenAndGetClaims(token);
		Long userId = userAndDepart.getUserId();
		Long departId = userAndDepart.getDepartId();

		Object[] args = joinPoint.getArgs();
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
		Annotation[][] annotations = method.getParameterAnnotations();
		for (int i = 0; i < annotations.length; i++) {
			Annotation[] paramAnn = annotations[i];
			for (Annotation annotation : paramAnn) {
				if (annotation.annotationType().equals(LoginUser.class)) {
					args[i] = userId;
					break;
				}
				if (annotation.annotationType().equals(Depart.class)) {
					args[i] = departId;
					break;
				}
			}
		}

		Object obj = null;
		try {
			obj = ((ProceedingJoinPoint) joinPoint).proceed(args);
		} catch (Throwable ignored) {

		}
		return obj;
	}
}