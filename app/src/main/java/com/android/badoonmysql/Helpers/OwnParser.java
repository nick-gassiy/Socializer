package com.android.badoonmysql.Helpers;

import android.content.Context;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class OwnParser {

    public static boolean getCitiesFromXML(Context context, ArrayList<String> cities) {
        boolean check;
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = context.getAssets().open("rocid.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            processParsing(parser, cities);
            check = true;
        } catch (XmlPullParserException e) {
            check = false;
        } catch (IOException e) {
            check = false;
        }
        return check;
    }

    private static void processParsing(XmlPullParser parser, ArrayList<String> cities) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        String  city = null;
        //ArrayList<String > cities = new ArrayList<>();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    if ("city".equals(eltName)) {
                        city = new String();
                    } else if (city != null) {
                        if ("name".equals(eltName)) {
                            city = parser.nextText();
                            cities.add(city);
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
    }
}
