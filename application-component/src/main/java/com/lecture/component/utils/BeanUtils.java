package com.lecture.component.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class BeanUtils implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    private static final ConcurrentHashMap<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();

    /**
     * 浅拷贝
     *
     * @param source
     * @param target
     */
    public static void copy(Object source, Object target) {
        String key = genKey(source.getClass(), target.getClass(), "copy");
        BeanCopier beanCopier = BEAN_COPIER_CACHE.computeIfAbsent(key,
                o -> BeanCopier.create(source.getClass(), target.getClass(), false));
        beanCopier.copy(source, target, null);
    }

    /**
     * 满足任一条件，则忽略【原对象】字段
     * 1.字段为空:org.springframework.util.ObjectUtils.isEmpty()
     * 2.字段类型不一致
     *
     * @param source
     * @param target
     */
    public static void copyNonEmpty(final Object source, final Object target) {
        if (Objects.isNull(source) || Objects.isNull(target)) {
            return;
        }
        String key = genKey(source.getClass(), target.getClass(), "copyNonEmpty");
        BeanCopier beanCopier = BEAN_COPIER_CACHE.computeIfAbsent(key,
                o -> BeanCopier.create(source.getClass(), target.getClass(), true));
        beanCopier.copy(source, target, new Converter() {
            /**
             * @param sourceValue 源对象值
             * @param targetClass Class:目标对象属性类
             * @param setMethod String:目标对象setter方法名
             * @return
             */
            @Override
            public Object convert(Object sourceValue, Class targetClass, Object setMethod) {
                if (ObjectUtils.isEmpty(sourceValue) || !targetClass.isAssignableFrom(sourceValue.getClass())) {
                    String setMethodString = (String) setMethod;
                    String getMethodString = "g" + setMethodString.substring(1);
                    try {
                        return ReflectUtils.findDeclaredMethod(
                                target.getClass(),
                                getMethodString,
                                null).invoke(target, null);
                    } catch (Exception e) {
                        // ignore exception
                    }
                }
                return sourceValue;
            }
        });
    }

    private static String genKey(Class<?> srcClazz, Class<?> tgtClazz, String suffix) {
        return srcClazz.getName() + "_" + tgtClazz.getName() + "_" + suffix;
    }

    public static <T> T jsonCopy(Object source, Class<T> clazz) {
        return JSON.parseObject(JSONObject.toJSONString(source), clazz);
    }

    /**
     *
     * @param source 源对象
     * @param target 目标对象
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T copy(S source, Supplier<T> target) {
        T t = target.get();
        copy(source, t);
        return t;
    }

    public static <S, T> List<T> copyBeans(List<S> sources, Supplier<T> target) {
        return copyBeans(sources, target, null);
    }

    public static <S, T> List<T> copyBeans(List<S> sources, Supplier<T> target, BiConsumer<S,T> mapper) {
        return sources.stream().map(sourceObj -> {
            T targetObj = target.get();
            copy(sourceObj, targetObj);
            if (null != mapper) {
                mapper.accept(sourceObj, targetObj);
            }
            return targetObj;
        }).collect(Collectors.toCollection(sources instanceof RandomAccess ?
                () -> new ArrayList<>(sources.size()) : LinkedList::new));
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static Object getBean(String name) {
        //name表示其他要注入的注解name名
        return applicationContext.getBean(name);
    }

    /**
     * 拿到ApplicationContext对象实例后就可以手动获取Bean的注入实例对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}