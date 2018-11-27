package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.ueditor.ActionEnter;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ldb on 2018/11/26.
 */
@RestController
@RequestMapping("/xry/ueditor")
public class UEditorController {

    @RequestMapping(value = "/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        String filePath = "";
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
            if (!"".equals(exec) && exec.indexOf("title") > -1) {
                JSONObject jsonConfig = new JSONObject(exec);
                if (jsonConfig.toMap().size() > 0) {
                    filePath = jsonConfig.getString("title");
                }
            }
            System.out.println(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
