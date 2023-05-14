package com.mt.bot.easyBot.bot;

import com.mt.bot.easyBot.utils.Proxys;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rometools.utils.Strings;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import static  com.mt.bot.easyBot.common.Constants.*;
/**
 * @author tao.mei
 * @description
 * @date 2023/5/12 10:16:55
 */
public class RssSub {

   private static ConcurrentHashMap<String,String>  concurrentHashMap = new ConcurrentHashMap<>();
    static {
        concurrentHashMap.put("twitter_user",TWITTER_USER);
        concurrentHashMap.put("weibo_hot",WEIBO_HOT);
    }

    public static List<String> getRss(String type,String param){
        String url ;
        if(Strings.isNotEmpty(param)){
            url =  concurrentHashMap.get(type.toLowerCase())+"/"+param ;
        }else{
            url =  concurrentHashMap.get(type.toLowerCase())  ;
        }

        SyndFeed feed = null;
        Proxy proxy = Proxys.getProxy();
        URL url1 = null;
        try {
            url1 = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        try {
            feed = new SyndFeedInput().build(new XmlReader(url1.openConnection(proxy)));
        } catch (FeedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<SyndEntry> list = feed.getEntries();
        List<String > rsList = new ArrayList<>();
        for (SyndEntry syndEntry:list){

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(syndEntry.getTitle()).append("\n")
                    .append(syndEntry.getLink());
            rsList.add(stringBuilder.toString());
        }
        return rsList;
    }
}
