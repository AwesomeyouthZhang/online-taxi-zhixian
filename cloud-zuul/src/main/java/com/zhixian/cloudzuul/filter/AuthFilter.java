package com.zhixian.cloudzuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zhixian.internalcommon.constant.RedisKeyPrefixConstant;
import com.zhixian.internalcommon.utils.JwtInfo;
import com.zhixian.internalcommon.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
@Slf4j
public class AuthFilter extends ZuulFilter {


    @Autowired
    RedisTemplate redisTemplate;

    /**
     * zullFilter拦截类型
     * pre：可以在请求被路由之前调用
     * route：在路由请求时候被调用
     * post：在route和error过滤器之后被调用
     * error：处理请求时发生错误时被调用
     *
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;

    }

    /**
     * 拦截优先级
     * 值越小，优先级越大
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return -1;
    }

    /**
     * 拦截器是否生效
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 具体内容
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {

        log.info("auth拦截");

        //获取上下文（参数）
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        String token = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(token)) {
            JwtInfo parseToken = JwtUtil.parseToken(token);
            if (!Objects.isNull(parseToken)) {
                String tokenUserId = parseToken.getSubject();
                Long tokenIssueDate = parseToken.getIssueDate();
                String redisToken = (String) redisTemplate.opsForValue().get(RedisKeyPrefixConstant.PASSENGER_LOGIN_CODE_KEY_PRE.concat(tokenUserId));

                log.info("redisToken:{}\ntoken:{}", redisToken, token);


            }
        }


        // 不往下走, 还走剩下的过滤器,但是不想后面的服务转发.
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
        requestContext.setResponseBody("auth fail");

        return null;

    }
}
