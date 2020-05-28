package com.google.android.gms.location.sample.locationupdates;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

    // Method from https://medium.com/@ssaurel/parsing-xml-data-in-android-apps-71ef607fbb16

public class Marks extends MainActivity{

    public Marks(Context context) throws IllegalAccessException {
        super();
        this.context = context;
    }

    public double[] getNextMark(String nextMark) {

        // String 'nextMark' is passed in, and used to get the correct array entry
        double nextMarkCoords[] = new double[2];

        // For each line in the marks ArrayList, in the format of 'Mark'
        for (Mark mark : marks) {
            String nextMarkName = mark.name;
            if (nextMarkName.equals(nextMark)) {
                nextMarkCoords[0] = Double.parseDouble(mark.lat);
                nextMarkCoords[1] = Double.parseDouble(mark.lon);
            }
        }

        return nextMarkCoords;

    }

    // Define variables used within this class, and shared between methods
    private Context context;
    ArrayList<Mark> marks = null;

    // The next 2 methods create the ArrayList, only done when this object is created
    public void parseXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream inputStream = context.getAssets().open("marks.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);

            marks = processParsing(parser);

        } catch (XmlPullParserException | IOException e) {
        }
    }

    private ArrayList<Mark> processParsing(XmlPullParser parser) throws
            IOException, XmlPullParserException {

        // create the local (to this method) ArrayList. It will be returned to parseXML method
        ArrayList<Mark> marks = new ArrayList<Mark>();
        int eventType = parser.getEventType();
        Mark currentMark = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    if ("mark".equals(eltName)) {
                        currentMark = new Mark();
                        marks.add(currentMark);
                    } else if (currentMark != null) {
                        if ("name".equals(eltName)) {
                            currentMark.name = parser.nextText();
                        } else if ("lat".equals(eltName)) {
                            currentMark.lat = parser.nextText();
                        } else if ("lon".equals(eltName)) {
                            currentMark.lon = parser.nextText();
                        }
                    }
                    break;
            }

            eventType = parser.next();
        }
        return marks;
    }
}
