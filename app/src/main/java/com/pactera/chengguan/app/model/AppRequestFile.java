package com.pactera.chengguan.app.model;

import java.io.File;

/**上传文件
 * Created by lijun on 2015/12/16.
 */
public class AppRequestFile {
    //文件
    private File file;
    //文件名称
    private String name;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
