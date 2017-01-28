package com.calgen.udacity.api.cronjob;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gurupad Mamadapur on 28-Jan-17.
 */

public class UpdateJokes extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UpdateJokes.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        try {
            logger.info("Cron job is executing.");
            // TODO: 29-Jan-17 Doesn't work, need to find some solution
            response.sendRedirect("https://sunshine-149409.appspot.com/_ah/api/jokeApi/v1/updateJokes");
            logger.info("Job completed.");
        } catch (Exception e) {
            logger.severe("Cron job failed : " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        doGet(req, resp);
    }
}
