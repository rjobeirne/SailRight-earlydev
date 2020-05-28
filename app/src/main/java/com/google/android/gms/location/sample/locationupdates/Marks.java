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
////    private Context context;
//    public Marks() throws IllegalAccessException {
//        super();
////        this.context = context;
//    }
//
//
//    public static String nextMark = "A Mark";
//    public static Double nextMarkLat = 99.99999;
//    private XmlPullParser parser;
//
//    public ArrayList<Mark> marks = parseXML();
//
////    public Marks() throws IllegalAccessException {
////    }
//
//
//
//
//
//    public ArrayList parseXML() {
//        XmlPullParserFactory parserFactory;
//        try {
//            parserFactory = XmlPullParserFactory.newInstance();
//            parser = parserFactory.newPullParser();
//            InputStream inputStream = getAssets().open("marks.xml");
//            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//            parser.setInput(inputStream, null);
//
//            ArrayList marks =  processParsing(parser);
////            Double[] nextMarkLoc;
////            nextMarkLoc = processParsing(parser);
////            Log.e("YXYXYXYXYXYXYX", String.valueOf(nextMarkLoc));
//
//
//        } catch (XmlPullParserException e) {
//
//
//        } catch (IOException e) {
//        }
//        return marks;
//
//    }
//
//
//
//
//
//        private ArrayList<Mark> processParsing(XmlPullParser parser) throws
//        IOException, XmlPullParserException {
//            ArrayList<Mark> marks = new ArrayList<>();
//            int eventType = parser.getEventType();
//            Mark currentMark = null;
//
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                String eltName = null;
//
//                switch (eventType) {
//                    case XmlPullParser.START_TAG:
//                        eltName = parser.getName();
//
//                        if ("mark".equals(eltName)) {
//                            currentMark = new Mark();
//                            marks.add(currentMark);
//                        } else if (currentMark != null) {
//                            if ("name".equals(eltName)) {
//                                currentMark.name = parser.nextText();
//                            } else if ("lat".equals(eltName)) {
//                                currentMark.lat = parser.nextText();
//                            } else if ("lon".equals(eltName)) {
//                                currentMark.lon = parser.nextText();
//                            }
//                        }
//                        break;
//                }
//                eventType = parser.next();
//            }
//
//            return marks;
////            destinationCoords(marks);
////                Double[] nextMarkCoords;
////                nextMarkCoords = destinationCoords(marks);
//
////                return nextMarkCoords;
//        }
//
//    /**
//     *  This takes the ArrayList marks and the selected next mark. then looks through the
//     *  arraylist to find the coordinates of the selected mark.
//     * @param marks
//     * @param nextMark
//     * @return next MarkCoords
//     * @throws IllegalAccessException
//     */
//    public static double getNextMark(ArrayList<Mark> marks, String nextMark) throws IllegalAccessException {
//
//
//        double nextMarkLat = 99;
////         Double nextMarkCoords[] = new Double[2];
//
//       for (Mark mark : marks) {
//                String spot = mark.name;
//                if (spot.equals(nextMark)) {
//                    nextMarkLat = Double.parseDouble(mark.lat);
////                    nextMarkCoords[0] = Double.parseDouble(mark.lat);
////                    nextMarkCoords[1] = Double.parseDouble(mark.lon);
//                }
//            }
//            Log.e("xxxxxxx", String.valueOf(nextMarkLat));
//            return nextMarkLat;
//
//
//    }



    public Marks(Context context) throws IllegalAccessException {
        super();
        this.context = context;
    }

     // Define variables used within this class, and shared between methods
    private Context context;
    ArrayList<Mark> marks = null;

    // The next 2 methods create the ArrayList, only done when this object is created
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
