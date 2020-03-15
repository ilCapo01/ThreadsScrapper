package com.fxpscrapper.bar771.fxpscrapper;

import android.os.AsyncTask;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scrapper extends AsyncTask<Void, Void, List<ThreadItem>> {

    private int pageCount = 0;

    public Scrapper(int pageCount) {
        this.pageCount = (pageCount > 0 ? pageCount : 1);
    }

    private List<ThreadItem> getData(int pageCount) {
        // https://jsoup.org/cookbook/extracting-data/selector-syntax
        Document doc = null;
        try {
            Connection conn = Jsoup.connect("https://www.fxp.co.il/forumdisplay.php?f=1960&page="+pageCount);

            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");
            conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            conn.header("Connection", "keep-alive");
            conn.header("Accept-Language", "he");
            conn.header("DNT", "1");

            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element threadList = doc.getElementById("threadlist");
        Element form = threadList.getElementById("thread_inlinemod_form");
        Element threadListFXP = form.getElementsByClass("threads_list_fxp").first();
        Element container = threadListFXP.select("ol#threads").first();
        Elements items = container.select("li.threadbit");

        List<ThreadItem> threads = new ArrayList<ThreadItem>();
        for (int i=0; i<items.size(); i++) {
            Element details = items.get(i).getElementsByTag("div").first().getElementsByClass("threadinfo").first().getElementsByClass("inner").first().getElementsByClass("threadtitle").first();

            String threadPrefix = "";
            if (details.getElementsByTag("span").first() != null)
                threadPrefix = details.getElementsByTag("span").first().text();
            String threadTitle = details.getElementsByTag("a").first().text();
            String threadLink = details.getElementsByTag("a").first().attr("href");

            //System.out.println(threadPrefix + " | " + threadTitle + " | " + threadLink);
            threads.add(new ThreadItem("https://www.fxp.co.il/" + threadLink, (!threadPrefix.isEmpty() ? threadPrefix : "") + threadTitle));
        }
        return threads;
    }

    @Override
    protected void onPostExecute(List<ThreadItem> threadItems) {
        super.onPostExecute(threadItems);
        MainActivity.threads.clear();
        for (int i=0; i<threadItems.size(); i++)
            MainActivity.threads.add(threadItems.get(i));
    }

    @Override
    protected List<ThreadItem> doInBackground(Void... voids) {
        return getData(pageCount);
    }
}

