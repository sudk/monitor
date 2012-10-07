package com.component.util;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingException;



public class JNDIUtil {
    public static Object lookup(Context ctx, String location)
            throws NamingException {

            return lookup(ctx, location, false);
        }

        public static Object lookup(Context ctx, String location, boolean cache)
            throws NamingException {

            Object obj = null;

            if (cache) {
                obj = _cache.get(location);

                if (obj == null) {
                    obj = _lookup(ctx, location);

                    _cache.put(location, obj);
                }
            }
            else {
                obj = _lookup(ctx, location);
            }

            return obj;
        }

        private static Object _lookup(Context ctx, String location)
            throws NamingException {

            Object obj = null;

            try {
                obj = ctx.lookup(location);
            }
            catch (NamingException n1) {

                // java:comp/env/ObjectName to ObjectName

                if (location.indexOf("java:comp/env/") != -1) {
                    try {
                        obj = ctx.lookup(
                            StringUtil.replace(location, "java:comp/env/", ""));
                    }
                    catch (NamingException n2) {

                        // java:comp/env/ObjectName to java:ObjectName

                        obj = ctx.lookup(
                            StringUtil.replace(location, "comp/env/", ""));
                    }
                }

                // java:ObjectName to ObjectName

                else if (location.indexOf("java:") != -1) {
                    try {
                        obj = ctx.lookup(StringUtil.replace(location, "java:", ""));
                    }
                    catch (NamingException n2) {

                        // java:ObjectName to java:comp/env/ObjectName

                        obj = ctx.lookup(StringUtil.replace(
                            location, "java:", "java:comp/env/"));
                    }
                }

                // ObjectName to java:ObjectName

                else if (location.indexOf("java:") == -1) {
                    try {
                        obj = ctx.lookup("java:" + location);
                    }
                    catch (NamingException n2) {

                        // ObjectName to java:comp/env/ObjectName

                        obj = ctx.lookup("java:comp/env/" + location);
                    }
                }
                else {
                    throw new NamingException();
                }
            }

            return obj;
        }

        private static Map _cache = new HashMap();
    
}

