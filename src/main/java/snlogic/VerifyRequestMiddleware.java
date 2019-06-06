package snlogic;

import com.google.common.base.Strings;
import models.ActionResult;
import models.ActionType;
import models.EventOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class VerifyRequestMiddleware implements Filter {
    @Autowired
    private SecureNative sn;

    @Autowired
    Utils utils;


    @Override
    public void init(FilterConfig filterConfig){

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String cookie = utils.getCookie(req, null);
        if (Strings.isNullOrEmpty(cookie)){
            ActionResult response = this.sn.verify(new EventOptions(utils.remoteIpFromRequest(req), req.getHeader("user-agent"), EventTypes.types.get(EventTypes.EventKey.VERIFY)), req);
            if (ActionType.type.BLOCK == response.getAction()){
                res.sendRedirect(String.valueOf(500));
            }
            if (ActionType.type.REDIRECT == response.getAction()){
                res.sendRedirect("/error");
            }
        }
        filterChain.doFilter(req,res);
    }

    @Override
    public void destroy() {}
}
