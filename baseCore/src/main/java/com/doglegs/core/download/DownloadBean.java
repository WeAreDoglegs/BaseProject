package com.doglegs.core.download;

import lombok.Data;

/**
 * @author : Mai_Xiao_Peng
 * @email : Mai_Xiao_Peng@163.com
 * @time : 2018/8/22 15:43
 * @describe :
 */

@Data
public class DownloadBean {

    private long total;
    private long bytesReaded;

    public DownloadBean(long total, long bytesReaded) {
        this.total = total;
        this.bytesReaded = bytesReaded;
    }
    
}
