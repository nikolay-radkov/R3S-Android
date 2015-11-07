package com.nikolay.r3s.utils;

import android.util.Log;

import com.nikolay.r3s.constants.RSSXMLTags;
import com.nikolay.r3s.models.Entry;
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
        Subscription subscription = new Subscription();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(is, null);
            Entry entry = null;
            boolean isInEntry = false;

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {

                } else if (eventType == XmlPullParser.START_TAG) {
                    String tagName = xpp.getName();
                    if (tagName.equals("item")) {
                        entry = new Entry();
                        isInEntry = true;
                        currentTag = RSSXMLTags.IGNORETAG;
                    } else if (tagName.equals("title")) {
                        currentTag = RSSXMLTags.TITLE;
                    } else if (tagName.equals("link")) {
                        currentTag = RSSXMLTags.LINK;
                    } else if (tagName.equals("pubDate")) {
                        currentTag = RSSXMLTags.DATE;
                    } else if (tagName.equals("description")) {
                        if(isInEntry) {
                            String contentValue = getInnerXml(xpp);
                            entry.setContent(contentValue);
                        } else {
                            currentTag = RSSXMLTags.DESCRIPTION;
                        }
                    } else if (xpp.getName().equals("url")) {
                        currentTag = RSSXMLTags.URL;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    String tagName = xpp.getName();
                    if (tagName.equals("item")) {
                        subscription.addEntry(entry);
                        isInEntry = false;
                    } else {
                        currentTag = RSSXMLTags.IGNORETAG;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    String content = xpp.getText();
                    content = content.trim();
                    Log.d("debug", content);
                    if (currentTag != RSSXMLTags.IGNORETAG && currentTag != null) {
                        if (isInEntry == true) {
                            switch (currentTag) {
                                case TITLE:
                                    if (content.length() != 0) {
//                                    if (entry.getTitle() != null) {
//                                        entry.setTitle(entry.getTitle() + content);
//                                    } else {
                                        entry.setTitle(content);
//                                    }
                                    }
                                    break;
                                case LINK:
                                    if (content.length() != 0) {
//                                    if (entry.getLink() != null) {
//                                        entry.setLink(entry.getLink() + content);
//                                    } else {
                                        entry.setLink(content);
//                                    }
                                    }
                                    break;
                                case DATE:
                                    if (content.length() != 0) {
//                                    if (entry.getCreatedAt() != null) {
//                                        entry.setCreatedAt(entry.getCreatedAt() + content);
//                                    } else {
                                        entry.setCreatedAt(content);
//                                    }
                                    }
                                    break;
//                                case DESCRIPTION:
//                                    if (content.length() != 0) {
//                                        if (entry.getContent() != null) {
//                                            entry.setContent(entry.getContent() + content);
//                                        } else {
//                                            entry.setContent(content);
//                                        }
//                                    }
//                                    break;
                                default:
                                    break;
                            }
                        } else {
                            if (isInEntry == false) {
                                switch (currentTag) {
                                    case TITLE:
                                        if (content.length() != 0) {
//                                        if (subscription.getName() != null) {
//                                            subscription.setName(subscription.getName() + content);
//                                        } else {
                                            subscription.setName(content);
//                                        }
                                        }
                                        break;
                                    case LINK:
                                        if (content.length() != 0) {
//                                        if (subscription.getUrl() != null) {
//                                            subscription.setUrl(subscription.getUrl() + content);
//                                        } else {
                                            subscription.setUrl(content);
//                                        }
                                        }
                                        break;
                                    case DESCRIPTION:
                                        if (content.length() != 0) {
//                                        if (subscription.getDescription() != null) {
//                                            subscription.setDescription(subscription.getDescription() + content);
//                                        } else {
                                            subscription.setDescription(content);
//                                        }
                                        }
                                        break;
                                    case URL:
                                        if (content.length() != 0) {
//                                        if (subscription.getFavicon() != null) {
//                                            subscription.setFavicon(subscription.getFavicon() + content);
//                                        } else {
                                            subscription.setFavicon(content);
//                                        }
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
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
        }

        return subscription;
    }


    private static String getInnerXml(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        StringBuilder sb = new StringBuilder();
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    if (depth > 0) {
                        sb.append("</" + parser.getName() + ">");
                    }
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    StringBuilder attrs = new StringBuilder();
                    for (int i = 0; i < parser.getAttributeCount(); i++) {
                        attrs.append(parser.getAttributeName(i) + "=\""
                                + parser.getAttributeValue(i) + "\" ");
                    }
                    sb.append("<" + parser.getName() + " " + attrs.toString() + ">");
                    break;
                default:
                    sb.append(parser.getText());
                    break;
            }
        }
        String content = sb.toString();
        return content;
    }
}
