package com.hbb.network.Function;

import com.hbb.network.exception.NetWorkException;
import com.hbb.network.netmodel.CommonResponse;

import io.reactivex.functions.Function;

/**
 * Created by Administrator on 2018/3/5.
 */

    public class HttpResultFunc<T> implements Function<CommonResponse<T>, T> {

        @Override
        public T apply(CommonResponse<T> tCommonResponse) throws Exception {
            if (tCommonResponse.getCode() != 0) {
                throw new NetWorkException(tCommonResponse.getCode());
            }
            return tCommonResponse.getData();
        }
    }
