import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain; charset=utf-8");

        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            response.sendError(401);
            return;
        }

        var offset = "Basic ".length();
        var credentials = new String(Base64.getDecoder().decode(
                authHeader.substring(offset)), UTF_8);

        var components = credentials.split(":", 2);
        if (components.length != 2) {
            response.sendError(500);
            return;
        }

        var username = components[0];
        var password = components[1];

        if (!username.equals(password)) { // Fake check
            response.sendError(401);
            return;
        }

        var session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        session = request.getSession(true);

        out.print(new JSONObject().put("token",
                CsrfToken.csrfToken(session.getId())));

        response.setStatus(200);
    }
}
