package com.minigee.app.base;

/**
 * Created by Zhou on 2015-12-7.
 */
public class C {
    public static final class api {
        public static final String base				= "http://192.168.1.139";
        public static final String get_session_id		= base+"/App/getSessionId";
        public static final String login_mix		= base+"/App/login/type/mix";
        public static final String logout			= base+"/App/logout";
        public static final String get_kr_news			= base+"/App/getKrNews";
        public static final String get_region			= base+"/App/getRegion";

        public static final String get_mcode			= base+"/App/mobileSend";

    }

    public static final class err {
        public static final String netnone			= "没有网络";

        public static final String neterror			= "网络异常错误";

        public static final String netdown			= "服务器抽风";

        public static final String nettime			= "连接超时";

    }

    public static final class netconn {
        public static final int conntime   =  3000;
    }
}
