package org.hengsir.icma.handler;

import org.hengsir.icma.dao.SXCDao;
import org.hengsir.icma.entity.XiBie;
import org.hengsir.icma.entity.XiBieVo;
import org.hengsir.icma.handler.constant.TypeEnum;
import org.hengsir.icma.handler.exception.HandleException;
import org.hengsir.icma.handler.request.ParentRequest;
import org.hengsir.icma.handler.request.XiBieRequest;
import org.hengsir.icma.handler.request.body.XiBieRequestBody;
import org.hengsir.icma.handler.response.ParentResponse;
import org.hengsir.icma.handler.response.ParentResponseHeader;
import org.hengsir.icma.handler.response.XiBieResponse;
import org.hengsir.icma.handler.response.body.XiBieResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/4/5 下午12:41
 */
@Service
public class XiBieHandler extends AbstractInCommingHandler<XiBieRequest,XiBieResponse> {

    @Autowired
    SXCDao sxcDao;

    @Override
    protected void limitHandle(XiBieRequest request) throws HandleException {

    }

    @Override
    protected void preHandle(XiBieRequest request) throws HandleException {

    }

    @Override
    protected XiBieResponse doHandle(XiBieRequest request) throws HandleException {
        XiBieResponse response = new XiBieResponse();
        XiBieRequestBody requestBody = request.getBody();
        XiBieResponseBody responseBody = new XiBieResponseBody();
        ParentResponseHeader header = new ParentResponseHeader();
        int schoolId = requestBody.getSchoolId();
        XiBieVo xi = new XiBieVo();
        xi.setSchoolId(schoolId);
        List<XiBie> xiBieList = sxcDao.findXiBieByXi(xi);
        String code;
        String msg;
        if (xiBieList != null) {
            code = "00";
            msg = "系别信息";
        } else {
            //获取失败
            code = "1";
            msg = "系别失败";
        }
        header.setCode(code);
        header.setDesc(msg);
        responseBody.setXiBieList(xiBieList);
        response.setHeader(header);
        response.setBody(responseBody);
        return response;
    }

    @Override
    protected void postHandle(XiBieRequest request, ParentResponse response) throws HandleException {

    }

    @Override
    public boolean isSupported(ParentRequest request) {
        return request.getHeader().getType().equals(TypeEnum.XIBIE.getCode());
    }
}
