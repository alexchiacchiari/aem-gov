
package com.adobe.aem.gov.core.servlets;


import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.eclipse.jetty.io.EofException;
import org.jetbrains.annotations.NotNull;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.servlet.Servlet;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Objects;

/**
 * This Servlet class handles the request of saveondb component of ui.apps
 *
 *
 * It posts records to a MySQL instance from a JCR Node
 *
 *
 * @author Alex Chiacchiari
 */

@Component(
        service = {Servlet.class},
        property={"sling.servlet.methods=POST",
                  "sling.servlet.paths=/bin/messages"
        }
)
public class SaveOnDbServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Reference(target = "(&(objectclass=javax.sql.DataSource)(datasource.name=localhost))")
    private DataSource dataSource;

    @Reference
    private Repository repository;

    @SlingObject
    private Resource records;

    @SlingObject
    private ResourceResolver resourceResolver;

    @SlingObject
    private Node currentNode;

    @SlingObject
    private Page currentPage;


    @Override
    protected void doPost(final @NotNull SlingHttpServletRequest req,
                          final @NotNull SlingHttpServletResponse resp) throws IOException {
        //setRespConfiguration(resp);
        String name = "";
        String message = "";
        String query = "INSERT INTO yuridb.users (username, textfield, date) VALUES (?, ?, current_date)";

        try(Connection con = getConnection()) {

            String saveOnDbNodePath = req.getParameter("path")
                    .replace("_jcr_content", "jcr:content");

            resourceResolver = req.getResourceResolver();
            records = resourceResolver
                    .getResource(saveOnDbNodePath)
                    .getChild("records");
            Objects.requireNonNull(records);

            Iterator<Resource> items = records.listChildren();

            Session session = repository.login( new SimpleCredentials("admin","admin".toCharArray()));

            while (items.hasNext()) {
                Resource res = items.next();
                name = res.getValueMap().get("name").toString();
                message = res.getValueMap().get("message").toString();

                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setString(1, name);
                preparedStmt.setString(2, message);
                preparedStmt.execute();
                resp.getWriter().
                        write("\nSuccessfully: \n"
                                +
                                "name: \n"
                                + name +
                                "\n" +
                                "message: \n"
                                + message);

                Node node = session.getNode(res.getPath());
                node.remove();
                node.getSession().save();
                

                resp.getWriter().
                        write(res.getName() + " DELETED \n");
            }
            if(session.isLive()) {
                session.logout();
            }

        } catch (SQLException | RepositoryException | NullPointerException | EofException e) {
            log.error(e.getMessage());
            resp.getStatus();
            resp.getWriter().write
                    ("Data already sent on MySQL Server. Please refill dialog with new data to send");
        }
    }


    void setRespConfiguration(@NotNull SlingHttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF8");
    }



    public Connection getConnection() {
        Connection con;
        try {
            con = dataSource.getConnection();
            log.info("### got connection");
            return con;
        } catch(Exception e) {
            log.error("### not able to get connection " + e.getMessage());

        }
        return null;
    }



}
