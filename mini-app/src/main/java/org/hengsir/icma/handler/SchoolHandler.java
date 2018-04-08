package org.hengsir.icma.handler;

import org.hengsir.icma.dao.SXCDao;
import org.hengsir.icma.entity.School;
import org.hengsir.icma.handler.constant.TypeEnum;
import org.hengsir.icma.handler.exception.HandleException;
import org.hengsir.icma.handler.request.ParentRequest;
import org.hengsir.icma.handler.request.SchoolRequest;
import org.hengsir.icma.handler.request.body.SchoolRequestBody;
import org.hengsir.icma.handler.response.ParentResponse;
import org.hengsir.icma.handler.response.ParentResponseHeader;
import org.hengsir.icma.handler.response.SchoolResponse;
import org.hengsir.icma.handler.response.body.SchoolResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/4/4 下午2:02
 */
@Service
public class SchoolHandler extends AbstractInCommingHandler<SchoolRequest, SchoolResponse> {

    private Logger logger = LoggerFactory.getLogger(SchoolHandler.class);

    @Autowired
    SXCDao sxcDao;


    @Override
    protected void limitHandle(SchoolRequest request) throws HandleException {

    }

    @Override
    protected void preHandle(SchoolRequest request) throws HandleException {

    }

    @Override
    protected SchoolResponse doHandle(SchoolRequest request) throws HandleException {
        SchoolResponse resp = new SchoolResponse();
        SchoolResponseBody respBody = new SchoolResponseBody();
        ParentResponseHeader parentResponseHeader = new ParentResponseHeader();

        List<School> schools = sxcDao.findAllSchool();
        String code;
        String msg;
        if (schools != null) {
            code = "00";
            msg = "学校信息";
        } else {
            //获取失败
            code = "1";
            msg = "获取失败";
        }
        parentResponseHeader.setCode(code);
        parentResponseHeader.setDesc(msg);
        respBody.setSchoolList(schools);
        resp.setBody(respBody);
        return resp;
    }

    @Override
    protected void postHandle(SchoolRequest request, ParentResponse response) throws HandleException {

    }

    @Override
    public boolean isSupported(ParentRequest request) {
        return request.getHeader().getType().equals(TypeEnum.SCHOOL.getCode());
    }
}
