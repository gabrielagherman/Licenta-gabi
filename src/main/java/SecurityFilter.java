import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/calc")
public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var resp = (HttpServletResponse) response;

        var session = req.getSession(false);
        var token = req.getHeader("X-CSRF-TOKEN");

        if (session == null || token == null || !CsrfToken.matches(session.getId(), token))
            resp.sendError(401);
        else
            chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
