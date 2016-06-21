package com.pactera.chengguan.municipal.util;

import java.util.List;

/**
 * Created by huang hua
 * 2016/4/28.
 */
public interface FileUploadListener {

    public void onCompleted(List<String> fileUrlList, String Url);

    public void onError();

}
