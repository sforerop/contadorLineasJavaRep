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
        
    }
    
   @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
      showHome(req,resp);
  }

    private void showHome(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getWriter().print("Contador de lineas de codigo!");
//        //---
//        System.out.println("Ruta 1: " + req.getRealPath("../source"));
//        System.out.println("Ruta 2: "+ req.getContextPath());
//        System.out.println("Ruta 3: " + req.getServletPath());
//        System.out.println("Ruta 4: "+ this.getServletContext().getContextPath());
//        System.out.println("Ruta 5: "+ resp.getClass().getResource("../resource"));
//
        ContadorClases.buscarArchivo();
//        resp.getWriter().print(texto);
    }
}
