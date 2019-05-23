package cn.whzwl.xbs.network.net.download;


public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
