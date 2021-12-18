package ru.geekbrains;

import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {

    private static final String TR = "</tr>";
    private static final String TR1 = "<tr>";
    private static final String TD = "</td>";
    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, NumberFormatException {
        PrintWriter wr = resp.getWriter();
        if (req.getParameter("id") != null) {
            long id = Long.parseLong(req.getParameter("id"));
            Product product = productRepository.findById(id);
            if (product == null) {
                wr.println("Product id=" + id + " not found");
            } else {
                wr.println("<table>");
                wr.println(TR1);
                wr.println("<th>Name</th>");
                wr.println("<th>Value</th>");
                wr.println(TR);
                wr.println(TR1);
                wr.println("<td>  id </td>");
                wr.println("<td> " + product.getId() + TD);
                wr.println(TR);
                wr.println(TR1);
                wr.println("<td>  name </td>");
                wr.println("<td>" + product.getName() + TD);
                wr.println(TR);
                wr.println("</table>");
            }
        } else if (req.getPathInfo() == null || req.getPathInfo().equals("/")) {
            wr.println("<table>");
            wr.println(TR1);
            wr.println("<th>Id</th>");
            wr.println("<th>Name</th>");
            wr.println(TR);
            for (Product product : productRepository.findAll()) {
                wr.println(TR1);
                wr.println("<td>" + product.getId() + TD);
                wr.println("<td><a href='product?id=" + product.getId() + "'>" + product.getName() + "</a></td>");
                wr.println(TR);
            }
            wr.println("</table>");
        }
    }

}
