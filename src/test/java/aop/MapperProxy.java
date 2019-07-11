package aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MapperProxy implements InvocationHandler {
    private Class clazz;

    public <T> T bulid(Class<T> clazz) {
        this.clazz = clazz;
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("调用了：" + method.getName());
        return "00000";
    }

}
