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
import java.util.Date;

public class XmlParserHelper {
    private static RSSXMLTags currentTag;

    public static Subscription parse(InputStream is) {
        Subscription subscription = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(is, null);

            int eventType = xpp.getEventType();
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE,DD MMM yyyy HH:mm:ss");
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    String p = xpp.getName();
                } else if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("item")) {
                        subscription = new Subscription();
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
                        Date postDate = dateFormat.parse(subscription.getUpdatedAt());
                        subscription.setUpdatedAt(dateFormat.format(postDate));
                    } else {
                        currentTag = RSSXMLTags.IGNORETAG;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    String content = xpp.getText();
                    content = content.trim();
                    Log.d("debug", content);
                    if (subscription != null) {
                        switch (currentTag) {
                            case TITLE:
                                if (content.length() != 0) {
                                    if (subscription.getName() != null) {
                                        subscription.setName(subscription.getName() + content);
                                    } else {
                                        subscription.setName(content);
                                    }
                                }
                                break;
                            case LINK:
                                if (content.length() != 0) {
                                    if (subscription.getUrl() != null) {
                                        subscription.setUrl(subscription.getUrl() + content);
                                    } else {
                                        subscription.setUrl(content);
                                    }
                                }
                                break;
                            case DATE:
                                if (content.length() != 0) {
                                    if (subscription.getUpdatedAt() != null) {
                                        subscription.setUpdatedAt(subscription.getUpdatedAt() + content);
                                    } else {
                                        subscription.setUpdatedAt(content);
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return subscription;
    }
}
