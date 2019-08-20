package com.self.wq.freemarker.controller;

import com.self.wq.freemarker.service.FreeMarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.StringWriter;

/**
 * @author: wq
 * @date: 2018/9/3 12:44
 */
@Controller
public class FreeMarkerController {

    @Autowired
    FreeMarkerService fs;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String findAllCity(Model model) {

        StringWriter sw = fs.buildOneFlow();

        return sw.toString();
    }
}
