package com.google.android.gms.location.sample.locationupdates;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MarksList {


    // Constructor using the context as a parameter
    // Need the 'context' to access the application/package Assets XML data file (I Believe)
    public MarksList(Context context) throws IllegalAccessException {
        super();
        this.context = context;
//        Log.e("xxxxxxx", "in Marks Constructor");
    }

    // Define variables used within this class, and shared between methods
    private Context context;
    ArrayList<String> marksList = null;

    // Returns the 'nextMarkLat', for the 'nextMark passed in
//    public double[] getNextMarkLat(String nextMark) {
//
//        // String 'nextMark' is passed in, and used to get the correct array entry
//        double[] nextMarkCoords = new double[2];
//
//        // For each line in the marks ArrayList, in the format of 'Mark'
//        for (Mark mark : marksList2) {
//            String spot = mark.name;
//            if (spot.equals(nextMark)) {
//                nextMarkCoords[0] = Double.parseDouble(mark.lat);
//                nextMarkCoords[1] = Double.parseDouble(mark.lon);
//            }
//        }
////        Log.e("xxxxxxx", String.valueOf(nextMarkCoords[0]));
//        return nextMarkCoords;
//    }

    // The next 2 methods create the ArrayList, only done when this object is created
    public void parseXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream inputStream = context.getAssets().open("marks.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);

            // Load the 'public' ArrayList from the XML file
            marksList = processParsing(parser);


        } catch (XmlPullParserException | IOException e) {

        }

    }

    private ArrayList<String> processParsing(XmlPullParser parser) throws
            IOException, XmlPullParserException {

        // create the local (to this method) ArrayList. It will be returned to parseXML method
        ArrayList<String> marksList = new ArrayList<>();
        int eventType = parser.getEventType();
        MarkList currentMark = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;


            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();
//                    Log.d("eltName: ", eltName );

                    if ("mark".equals(eltName)) {
                        currentMark = new MarkList();
//                        marksList2.add(currentMark);
                    } else if (currentMark != null) {
                        if ("name".equals(eltName)) {
                            currentMark.name = parser.nextText();
                            marksList.add(currentMark.name);
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
//        Log.e("***** - marksList", String.valueOf(marksList));
        return marksList;

    }



}
