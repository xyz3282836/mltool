package com.minigee.app.http;

import com.minigee.app.base.C;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.app.DefaultParamsBuilder;

/**
 * Created by Zhou on 2015-12-7.
 */
@HttpRequest(
        host = C.api.get_kr_news,
        path = "",
        builder = DefaultParamsBuilder.class/*可选参数, 控制参数构建过程, 定义参数签名, SSL证书等*/
)
public class KrNewsParams extends RequestParams {
    public String lastId;
}
