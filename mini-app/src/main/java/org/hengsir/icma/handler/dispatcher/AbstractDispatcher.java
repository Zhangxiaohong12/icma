package org.hengsir.icma.handler.dispatcher;

import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单实现分发器。
 * Created by leaves chen[leaves615@gmail.com] on 2017/12/18.
 *
 * @author leaves chen[leaves615@gmail.com]
 */
public abstract class AbstractDispatcher<T> implements Dispatcher<T> {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(
        AbstractDispatcher.class);

    protected List<T> handlers = new ArrayList<>();

    @Override
    public void init() {

    }

    @Override
    public void setHandlers(List<T> handlers) {
        this.handlers = handlers;
    }
}
