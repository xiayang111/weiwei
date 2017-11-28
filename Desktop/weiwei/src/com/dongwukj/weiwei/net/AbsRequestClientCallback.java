package com.dongwukj.weiwei.net;

import com.dongwukj.weiwei.idea.result.BaseResult;

/**
 * Created by sunjaly on 15/4/18.
 */
public abstract class AbsRequestClientCallback {

    protected abstract  <T extends  BaseResult> void   success(T result);

    protected abstract <T extends  BaseResult> void fail(T result);

    protected abstract <T extends  BaseResult> void complete(T result);
    protected abstract <T extends  BaseResult> void logOut(T result);

}
