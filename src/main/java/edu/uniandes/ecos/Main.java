package edu.uniandes.ecos;

import edu.uniandes.ecos.modelo.ContadorClases;
import java.io.IOException;
import java.net.URL;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Hello world!
 *
 */
public class Main extends HttpServlet {
    public static String texto= "";
    public static void main(String[] args) throws Exception {
        Server server = new Server(Integer.valueOf(System.getenv("PORT")));
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new Main()), "/*");
        server.start();
        server.join();
        String ruta = context.getContextPath();
        System.out.println("La RUTa Es " + ruta);
        texto = ContadorClases.buscarArchivo(ruta);
    }
    
   @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
      showHome(req,resp);
  }

    private void showHome(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        resp.getWriter().print("Hello from Java!");
        //---
        resp.getWriter().print(texto);
    }
}
