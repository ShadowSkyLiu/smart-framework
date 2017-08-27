package org.smart4j.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 提供一个创建代理对象的方法，输入一个目标类和一组Proxy接口实现，输出一个对象
 * 使用Enhance#create方法来创建代理对象，将intercept的参数传入ProxyChain的构造函数中即可
 */
public class ProxyManager {

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            public Object intercept(Object targetObject,
                                    Method method,
                                    Object[] objects,
                                    MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(
                        targetClass,
                        targetObject,
                        method,
                        methodProxy,
                        objects,
                        proxyList).doProxyChain();
            }
        });
    }

}
