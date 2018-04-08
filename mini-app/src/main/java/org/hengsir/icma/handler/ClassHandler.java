package org.hengsir.icma.handler;

import org.hengsir.icma.dao.SXCDao;
import org.hengsir.icma.entity.Class;
import org.hengsir.icma.entity.ClassVo;
import org.hengsir.icma.handler.constant.TypeEnum;
import org.hengsir.icma.handler.exception.HandleException;
import org.hengsir.icma.handler.request.ClassRequest;
import org.hengsir.icma.handler.request.ParentRequest;
import org.hengsir.icma.handler.request.body.ClassRequestBody;
import org.hengsir.icma.handler.response.ClassResponse;
import org.hengsir.icma.handler.response.ParentResponse;
import org.hengsir.icma.handler.response.ParentResponseHeader;
import org.hengsir.icma.handler.response.body.ClassResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/4/6 上午12:33
 */
@Service
public class ClassHandler extends AbstractInCommingHandler<ClassRequest,ClassResponse> {

    @Autowired
    SXCDao sxcDao;

    @Override
    protected void limitHandle(ClassRequest request) throws HandleException {

    }

    @Override
    protected void preHandle(ClassRequest request) throws HandleException {

    }

    @Override
    protected ClassResponse doHandle(ClassRequest request) throws HandleException {
        ClassRequestBody requestBody = request.getBody();
        ClassResponse response = new ClassResponse();
        ClassResponseBody responseBody = new ClassResponseBody();
        ParentResponseHeader header = new ParentResponseHeader();
        int xiBieId = requestBody.getXiBieId();
        ClassVo c = new ClassVo();
        c.setXiBieId(xiBieId);
        List<Class> classList = sxcDao.findClassByC(c);
        String code;
        String msg;
        if (classList != null) {
            code = "00";
            msg = "班级信息";
        } else {
            //获取失败
            code = "1";
            msg = "获取失败";
        }
        header.setCode(code);
        header.setDesc(msg);
        responseBody.setClassList(classList);
        response.setBody(responseBody);

        return response;
    }

    @Override
    protected void postHandle(ClassRequest request, ParentResponse response) throws HandleException {

    }

    @Override
    public boolean isSupported(ParentRequest request) {
        return request.getHeader().getType().equals(TypeEnum.CLASS.getCode());
    }
}
