package com.fxpscrapper.bar771.fxpscrapper;

public class ThreadItem {

    private String threadURL;
    private String threadName;

    public ThreadItem(String url, String name) {
        this.threadURL = url;
        this.threadName = name;
    }

    public String getUrl() {
        return threadURL;
    }

    public String getName() {
        return threadName;
    }

    public String toString() {
        return threadName;
    }
}
