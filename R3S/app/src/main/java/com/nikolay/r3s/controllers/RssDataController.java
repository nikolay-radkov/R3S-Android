//package com.nikolay.r3s.controllers;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.nikolay.r3s.models.Subscription;
//
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserException;
//import org.xmlpull.v1.XmlPullParserFactory;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//
//public class RssDataController extends AsyncTask<String, Integer, Subscription[]> {
//    private RSSXMLTag currentTag;
//
//    @Override
//    protected Subscription[] doInBackground(String... params) {
//        String urlStr = params[0];
//        InputStream is = null;
//        ArrayList<> postDataList = new ArrayList();
//        try {
//            URL url = new URL(urlStr);
//            HttpURLConnection connection = (HttpURLConnection) url
//                    .openConnection();
//            connection.setReadTimeout(10 * 1000);
//            connection.setConnectTimeout(10 * 1000);
//            connection.setRequestMethod("GET");
//            connection.setDoInput(true);
//            connection.connect();
//
//            int response = connection.getResponseCode();
//            Log.d("debug", "The response is: " + response);
//            is = connection.getInputStream();
//
//// parse xml after getting the data
//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            factory.setNamespaceAware(true);
//            XmlPullParser xpp = factory.newPullParser();
//            xpp.setInput(is, null);
//
//            int eventType = xpp.getEventType();
//            Subscription pdData = null;
//            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE,DD MMM yyyy HH:mm:ss");
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                if (eventType == XmlPullParser.START_DOCUMENT) {
//
//                } else if (eventType == XmlPullParser.START_TAG) {
//                    if (xpp.getName().equals("item")) {
//                        pdData = new PostData();
//                        currentTag = RSSXMLTag.IGNORETAG;
//                    } else if (xpp.getName().equals("title")) {
//                        currentTag = RSSXMLTag.TITLE;
//                    } else if (xpp.getName().equals("link")) {
//                        currentTag = RSSXMLTag.LINK;
//                    } else if (xpp.getName().equals("pubDate")) {
//                        currentTag = RSSXMLTag.DATE;
//                    }
//                } else if (eventType == XmlPullParser.END_TAG) {
//                    if (xpp.getName().equals("item")) {
//// format the data here, otherwise format data in
//// Adapter
//                        Date postDate = dateFormat.parse(pdData.getUpdatedAt());
//                        pdData.setUpdatedAt(dateFormat.format(postDate));
//                        postDataList.add(pdData);
//                    } else {
//                        currentTag = RSSXMLTag.IGNORETAG;
//                    }
//                } else if (eventType == XmlPullParser.TEXT) {
//                    String content = xpp.getText();
//                    content = content.trim();
//                    Log.d("debug", content);
//                    if (pdData != null) {
//                        switch (currentTag) {
//                            case TITLE:
//                                if (content.length() != 0) {
//                                    if (pdData.getName() != null) {
//                                        pdData.setName(pdData.getName() + content);
//                                    } else {
//                                        pdData.setName(content);
//                                    }
//                                }
//                                break;
//                            case LINK:
//                                if (content.length() != 0) {
//                                    if (pdData.getUrl() != null) {
//                                        pdData.setUrl(pdData.getUrl()+ content);
//                                    } else {
//                                        pdData.setUrl(content);
//                                    }
//                                }
//                                break;
//                            case DATE:
//                                if (content.length() != 0) {
//                                    if (pdData.getUpdatedAt() != null) {
//                                        pdData.setUpdatedAt(pdData.getUpdatedAt() + content);
//                                    } else {
//                                        pdData.setUpdatedAt(content);
//                                    }
//                                }
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                }
//
//                eventType = xpp.next();
//            }
//            Log.v("tst", String.valueOf((postDataList.size())));
//        } catch (MalformedURLException e) {
//// TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//// TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//// TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (ParseException e) {
//// TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return postDataList;
//    }
//
//    @Override
//    protected void onPostExecute(ArrayList result) {
//// TODO Auto-generated method stub
//        for (int i = 0; i < result.size(); i++) {
//            listData.add(result.get(i));
//        }
//        //postAdapter.notifyDataSetChanged();
//    }
//}