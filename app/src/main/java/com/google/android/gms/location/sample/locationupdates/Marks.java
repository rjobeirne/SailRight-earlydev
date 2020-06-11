package com.google.android.gms.location.sample.locationupdates;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


// Method from https://medium.com/@ssaurel/parsing-xml-data-in-android-apps-71ef607fbb16

public class Marks{

    ArrayList<Mark> marks = new ArrayList<>();

    // Parse the marks.gpx file to an ArrayList of name and
  public void parseXML() throws IOException {
        String appDirectory = "SailRight";

        File dir = new File(Environment.getExternalStorageDirectory(), appDirectory);
        File yourFile = new File(dir, "marks.gpx");
//      InputStream yourFile = getAssets().open("marks.xml");


        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(yourFile);
            doc.getDocumentElement().normalize();

            Log.e("Root_element" , doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("mark");

            marks = new ArrayList<Mark>();

            for (int i = 0; i < nodeList.getLength(); i++) {

                Element element = (Element) nodeList.item(i);

                // Create an ArrayList of mark names and coords from the parsed marks.gpx file
                Mark model = new Mark();
                model.setmarkName(element.getAttribute("name"));
                model.setmarkLat(element.getAttribute("lat"));
                model.setmarkLon(element.getAttribute("lon" ));
                marks.add(model);
            }

        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
    }

    // Find the coordinates of the next mark
    public Location getNextMark(String nextMark) {

        Location nextMarkLoc = new Location("");
        String tryMark;
        Double tryLat, tryLon;

        for (int i = 0; i < marks.size(); i++) {
            tryMark = marks.get(i).getmarkName();
            tryLat = Double.parseDouble(marks.get(i).getmarkLat());
            tryLon = Double.parseDouble(marks.get(i).getmarkLon());

            if (tryMark.equals(nextMark)) {
                nextMarkLoc.setLatitude(tryLat);
                nextMarkLoc.setLongitude(tryLon);
                nextMarkLoc.setTime(Calendar.getInstance().getTimeInMillis());
            break;
            }
//                Log.e("***** try this mark", marks.get(i).getmarkName());
        }
//        Log.e("*** nextMark =", nextMark);
        Log.e("****nextMarkLoc =", nextMark + " " + String.valueOf(nextMarkLoc));

        return nextMarkLoc;
    }
}

