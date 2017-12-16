package com.jingdong.utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
/**
 * 时间:2017/12/3 21:19
 * 作者:韩帅帅
 * 详情:
 */
public final class UserAgentIntercept implements Interceptor {
        private static final String USER_AGENT_HEADER_NAME = "User-Agent";
        private final String userAgentHeaderValue;

        public UserAgentIntercept(String userAgentHeaderValue) {
            this.userAgentHeaderValue = userAgentHeaderValue;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request originalRequest = chain.request();
            return chain.proceed(originalRequest.newBuilder()
                    .removeHeader(USER_AGENT_HEADER_NAME)
                    .addHeader(USER_AGENT_HEADER_NAME, userAgentHeaderValue)
                    .build());
        }
    }