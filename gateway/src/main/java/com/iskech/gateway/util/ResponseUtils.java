package com.iskech.gateway.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class ResponseUtils {
    private static final Logger log = LoggerFactory.getLogger(Oauth2Utils.class);

    /**
     * Response Data
     * 
     * @param response
     * @param responseData
     * @throws JsonProcessingException
     */
    public static void responseData(HttpServletResponse response, Object responseData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(responseData);
        PrintWriter out = null;

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        try {
            out = response.getWriter();
            out.append(jsonString);
        } catch (IOException e) {
            log.error(null, e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
