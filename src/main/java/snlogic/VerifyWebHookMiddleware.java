package snlogic;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class VerifyWebHookMiddleware implements Filter {

    @Autowired
    private ISDK sn;

    @Autowired
    private Utils utils;

    private final String HEADER_KEY = "X-SECURENATIVE";


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String payload = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        if (Strings.isNullOrEmpty(payload)) {
            res.sendError(500, "empty request");
        }

        try {
            String digest = "sha1=" + utils.calculateRFC2104HMAC(payload, this.sn.getSnOptions().getApiKey());
            String checksum = req.getHeader(HEADER_KEY);
            if (checksum == null || digest != checksum) {
                res.sendError(500,"Request body digest did not match ");
            }
        }
        catch (Exception e){
            System.out.println("Error");
        }

    }

    @Override
    public void destroy() {

    }
}
