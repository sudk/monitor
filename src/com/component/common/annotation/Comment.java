package com.component.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ʵ���ֶ������������
 * 
 * @author LiuKun
 * @date 2012-7-17
 * @time ����5:01:36
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Comment {
	String value() default "ʵ���ֶ���������";
}
