package com.nikolay.r3s.utils;

import android.util.Log;

import com.nikolay.r3s.constants.RSSXMLTags;
import com.nikolay.r3s.models.Subscription;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nikolay on 05-Nov-15.
 */
public class XmlParserHelper {
    private static RSSXMLTags currentTag;

    public static ArrayList<Subscription> parse(InputStream is) {
        ArrayList<Subscription> postDataList = new ArrayList<Subscription>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(is, null);

            int eventType = xpp.getEventType();
            Subscription pdData = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE,DD MMM yyyy HH:mm:ss");
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {

                } else if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("item")) {
                        pdData = new Subscription();
                        currentTag = RSSXMLTags.IGNORETAG;
                    } else if (xpp.getName().equals("title")) {
                        currentTag = RSSXMLTags.TITLE;
                    } else if (xpp.getName().equals("link")) {
                        currentTag = RSSXMLTags.LINK;
                    } else if (xpp.getName().equals("pubDate")) {
                        currentTag = RSSXMLTags.DATE;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName().equals("item")) {
// format the data here, otherwise format data in
// Adapter
                        Date postDate = dateFormat.parse(pdData.getUpdatedAt());
                        pdData.setUpdatedAt(dateFormat.format(postDate));
                        postDataList.add(pdData);
                    } else {
                        currentTag = RSSXMLTags.IGNORETAG;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    String content = xpp.getText();
                    content = content.trim();
                    Log.d("debug", content);
                    if (pdData != null) {
                        switch (currentTag) {
                            case TITLE:
                                if (content.length() != 0) {
                                    if (pdData.getName() != null) {
                                        pdData.setName(pdData.getName() + content);
                                    } else {
                                        pdData.setName(content);
                                    }
                                }
                                break;
                            case LINK:
                                if (content.length() != 0) {
                                    if (pdData.getUrl() != null) {
                                        pdData.setUrl(pdData.getUrl() + content);
                                    } else {
                                        pdData.setUrl(content);
                                    }
                                }
                                break;
                            case DATE:
                                if (content.length() != 0) {
                                    if (pdData.getUpdatedAt() != null) {
                                        pdData.setUpdatedAt(pdData.getUpdatedAt() + content);
                                    } else {
                                        pdData.setUpdatedAt(content);
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }

                eventType = xpp.next();
            }
            Log.v("tst", String.valueOf((postDataList.size())));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return postDataList;
    }

}
