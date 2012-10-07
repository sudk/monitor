package com.component.common.spring.jndi;


import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.component.util.JNDIUtil;


/**
 * Created by IntelliJ IDEA.
 * User: LiuKun
 * Date: 2007-11-27
 * Time: 17:37:33
 */
public class JndiObjectFactoryBean
        extends org.springframework.jndi.JndiObjectFactoryBean {
    //private static final Logger logger = Logger.getLogger(JndiObjectFactoryBean.class);
    public JndiObjectFactoryBean() {
        super();
        if (System.getProperty("java.naming.factory.initial") == null) {
            System.setProperty("java.naming.factory.initial",
                    "com.ufc.naming.MirrorInitialCtxFactory");
        }
    }

    protected Object lookup() throws NamingException {
        return JNDIUtil.lookup(new InitialContext(), getJndiName());
    }

}

