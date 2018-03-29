package org.hengsir.icma.controller;

import org.hengsir.icma.handler.dispatcher.InCommingDispatcher;
import org.hengsir.icma.handler.exception.HandleException;
import org.hengsir.icma.handler.request.ParentRequest;
import org.hengsir.icma.handler.response.ParentResponse;
import org.hengsir.icma.handler.response.ParentResponseHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * http 接口入口controller。
 */
@Controller
@RequestMapping("/api")
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    InCommingDispatcher dispatcher;//请求处理适配器

    /**
     * 标准入口(request:解密,验签;response:签名,加密)。
     *
     * @param body JSON字符串
     * @return 响应结果
     */
    @RequestMapping(value = "/handle", produces = {"application/json;charset=UTF-8"},
        method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String handle(@RequestBody String body, HttpServletRequest request) {
        ParentRequest parentRequest = null;
        try {
            parentRequest = new ParentRequest(body);
        } catch (HandleException ex) {
            ParentResponse parentResponse = new ParentResponse();
            ParentResponseHeader parentResponseHeader = new ParentResponseHeader(ex.getCode(),
                                                                                 ex.getMessage());
            parentResponse.setHeader(parentResponseHeader);
            return parentResponse.toJsonString();
        }
        ParentResponse handle = dispatcher.handle(parentRequest);
        return handle.toJsonString();
    }
}
