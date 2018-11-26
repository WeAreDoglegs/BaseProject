package com.doglegs.core.download;

import lombok.Data;

@Data
public class DownloadBean {

    private long total;
    private long bytesReaded;

    public DownloadBean(long total, long bytesReaded) {
        this.total = total;
        this.bytesReaded = bytesReaded;
    }
    
}
