package org.hengsir.icma.handler;

import org.hengsir.icma.handler.constant.ErrCode;
import org.hengsir.icma.handler.exception.HandleException;
import org.hengsir.icma.handler.request.ParentRequest;
import org.hengsir.icma.handler.response.ParentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * 处理器抽象类。定义标准处理流程。和标准抽象方法。
 * Created by leaves chen[leaves615@gmail.com] on 2017/12/18.
 *
 * @author leaves chen[leaves615@gmail.com]
 */
public abstract class AbstractInCommingHandler<R extends ParentRequest, S extends ParentResponse>
    extends InCommingHandler<R, S> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractInCommingHandler.class);


    protected Validator getValidator() {
        return validator;
    }

    //请求数据格式验证
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public S handle(ParentRequest request) throws HandleException {
        R wrappeiedRequest;
        try {
            wrappeiedRequest = wrapperRequest(request);//参数转换
        } catch (Exception ex) {
            throw new HandleException("wrapper specific request instance failed.", ex);
        }
        validateDataRelation(wrappeiedRequest);//验证终端：终端的配置是否与服务器一致
        validateRequest(wrappeiedRequest);//验证数据：必须字段
        limitHandle(wrappeiedRequest);//交易验证：是否允许此交易
        preHandle(wrappeiedRequest);//执行前
        S response = doHandle(wrappeiedRequest);//执行交易
        postHandle(wrappeiedRequest, response);//执行交易完成后操作
        return response;
    }

    /**
     * 请求参数验证。
     *
     * @param request 请求参数。
     */
    protected void validateRequest(R request) throws HandleException {
        Set<ConstraintViolation<R>> violations = validator.validate(request);
        if (violations != null && violations.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (ConstraintViolation<R> violation : violations) {
                if (stringBuilder.length() != 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(violation.getPropertyPath());
                stringBuilder.append(violation.getMessage());
            }
            throw new HandleException(
                ErrCode.ERRC_MISSING_ERROR.getCode(),
                ErrCode.ERRC_MISSING_ERROR.getDescription() + "(" + stringBuilder.toString() + ")");
        }
    }


    /**
     * 转换具体的业务处理请求类。
     *
     * @param request 请求参数。
     * @return 返回转换后的请求参数。
     * @throws IllegalAccessException    非法访问异常
     * @throws InstantiationException    实例化异常
     * @throws NoSuchMethodException     方法缺失异常
     * @throws InvocationTargetException 反射异常
     */
    protected R wrapperRequest(ParentRequest request)
        throws IllegalAccessException, InstantiationException, NoSuchMethodException,
               InvocationTargetException {
        Type genericInterfaces = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genericInterfaces).getActualTypeArguments();
        Class<R> responseClass = (Class) params[0];
        Constructor<R> constructor = responseClass.getDeclaredConstructor(String.class);
        return constructor.newInstance(request.getOriginData());
    }

    /**
     * 数据关系验证。
     * 验证终端、商户、子商户、机器指纹配置是否与服务器端配置一致。
     *
     * @param request 请求数据。
     * @throws HandleException 验证异常。
     */
    protected void validateDataRelation(R request) throws HandleException {
    }

    /**
     * 交易是否被允许。
     */
    protected abstract void limitHandle(R request) throws HandleException;

    /**
     * 处理前置。
     *
     * @param request 请求参数
     * @throws HandleException 处理异常
     */
    protected abstract void preHandle(R request) throws HandleException;

    /**
     * 处理。
     *
     * @param request 请求参数
     * @return 处理异常
     */
    protected abstract S doHandle(R request) throws HandleException;

    /**
     * 处理完成后执行。
     *
     * @param request  请求参数
     * @param response 处理结果
     */
    protected abstract void postHandle(R request, ParentResponse response) throws HandleException;
}
