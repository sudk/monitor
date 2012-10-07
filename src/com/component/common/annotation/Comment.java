package com.component.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 实体字段中文描述标记
 * 
 * @author LiuKun
 * @date 2012-7-17
 * @time 下午5:01:36
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Comment {
	String value() default "实体字段中文描述";
}
