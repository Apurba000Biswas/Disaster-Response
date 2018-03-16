package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 3/14/2018.
 */

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FloodQueryUtils {

    private static final String LOG_TAG = FloodQueryUtils.class.getSimpleName();

    private FloodQueryUtils(){
    }

    private static final String floodJASON = "{ \n" +
            "  \"@context\" : \"http://environment.data.gov.uk/flood-monitoring/meta/context.jsonld\" ,\n" +
            "  \"meta\" : { \n" +
            "    \"publisher\" : \"Environment Agency\" ,\n" +
            "    \"licence\" : \"http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/\" ,\n" +
            "    \"documentation\" : \"http://environment.data.gov.uk/flood-monitoring/doc/reference\" ,\n" +
            "    \"version\" : \"0.9\" ,\n" +
            "    \"comment\" : \"Status: Beta service\" ,\n" +
            "    \"hasFormat\" : [ \"http://environment.data.gov.uk/flood-monitoring/id/floods.csv?min-severity=3&_limit=10\", \"http://environment.data.gov.uk/flood-monitoring/id/floods.rdf?min-severity=3&_limit=10\", \"http://environment.data.gov.uk/flood-monitoring/id/floods.ttl?min-severity=3&_limit=10\", \"http://environment.data.gov.uk/flood-monitoring/id/floods.html?min-severity=3&_limit=10\" ] ,\n" +
            "    \"limit\" : 10\n" +
            "  }\n" +
            "   ,\n" +
            "  \"items\" : [ { \n" +
            "    \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floods/102549\" ,\n" +
            "    \"description\" : \"River Lugg south of Leominster\" ,\n" +
            "    \"eaAreaName\" : \"West\" ,\n" +
            "    \"eaRegionName\" : \"Midlands\" ,\n" +
            "    \"floodArea\" : { \n" +
            "      \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/031WAF116\" ,\n" +
            "      \"county\" : \"Herefordshire\" ,\n" +
            "      \"notation\" : \"031WAF116\" ,\n" +
            "      \"polygon\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/031WAF116/polygon\" ,\n" +
            "      \"riverOrSea\" : \"River Lugg\"\n" +
            "    }\n" +
            "     ,\n" +
            "    \"floodAreaID\" : \"031WAF116\" ,\n" +
            "    \"isTidal\" : false ,\n" +
            "    \"message\" : \"This message contains gauge information for Butts Bridge, Ford Bridge and Lugwardine.\\n\\nThis message will be updated by 9 am on Friday the 16th March, or if the situation changes.\\n\\nAt 16:15 today, Thursday the 15th of March;\\n\\nButts Bridge gauge was 2.18 metres and rising.\\n\\nFord Bridge gauge was 1.66 metres and steady.\\n\\nLugwardine gauge was 2.73 metres and steady. Levels are expected to remain elevated between 2.7 and 2.8 metres for the next few days\" ,\n" +
            "    \"severity\" : \"Flood Alert\" ,\n" +
            "    \"severityLevel\" : 3 ,\n" +
            "    \"timeMessageChanged\" : \"2018-03-15T16:24:00\" ,\n" +
            "    \"timeRaised\" : \"2018-03-15T16:24:00\" ,\n" +
            "    \"timeSeverityChanged\" : \"2018-03-05T18:26:00\"\n" +
            "  }\n" +
            "  , { \n" +
            "    \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floods/102575\" ,\n" +
            "    \"description\" : \"Lower River Soar in Leicestershire\" ,\n" +
            "    \"eaAreaName\" : \"East\" ,\n" +
            "    \"eaRegionName\" : \"Midlands\" ,\n" +
            "    \"floodArea\" : { \n" +
            "      \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/034WAF428\" ,\n" +
            "      \"county\" : \"Derbyshire, Leicestershire, Nottinghamshire\" ,\n" +
            "      \"notation\" : \"034WAF428\" ,\n" +
            "      \"polygon\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/034WAF428/polygon\" ,\n" +
            "      \"riverOrSea\" : \"River Soar\"\n" +
            "    }\n" +
            "     ,\n" +
            "    \"floodAreaID\" : \"034WAF428\" ,\n" +
            "    \"isTidal\" : false ,\n" +
            "    \"message\" : \"Levels remain high but steady in the Lower River Soar in response to rainfall. Flooding will continue throughout the remainder of Thursday 15th March and into Friday 16th March and will affect Slash Lane at Sileby and the Mountsorrel to Sileby Road. We urge residents to prepare, remain vigilant and avoid walking and driving through flood water. We are closely monitoring the situation. We will endeavour to update this message as the situation changes.\" ,\n" +
            "    \"severity\" : \"Flood Alert\" ,\n" +
            "    \"severityLevel\" : 3 ,\n" +
            "    \"timeMessageChanged\" : \"2018-03-15T15:55:00\" ,\n" +
            "    \"timeRaised\" : \"2018-03-15T15:55:00\" ,\n" +
            "    \"timeSeverityChanged\" : \"2018-03-08T15:26:00\"\n" +
            "  }\n" +
            "  , { \n" +
            "    \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floods/102601\" ,\n" +
            "    \"description\" : \"Lower River Derwent\" ,\n" +
            "    \"eaAreaName\" : \"Yorkshire\" ,\n" +
            "    \"eaRegionName\" : \"North East\" ,\n" +
            "    \"floodArea\" : { \n" +
            "      \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/122WAF950\" ,\n" +
            "      \"county\" : \"East Riding of Yorkshire, North Yorkshire, York\" ,\n" +
            "      \"notation\" : \"122WAF950\" ,\n" +
            "      \"polygon\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/122WAF950/polygon\" ,\n" +
            "      \"riverOrSea\" : \"River Derwent\"\n" +
            "    }\n" +
            "     ,\n" +
            "    \"floodAreaID\" : \"122WAF950\" ,\n" +
            "    \"isTidal\" : false ,\n" +
            "    \"message\" : \"This flood alert remains in force due to high river levels and bank full conditions on the lower River Derwent. Levels are elevated around Buttercrambe, Elvington, Stamford Bridge and all low lying land along the riverside.  Bands of rainfall are forecast on Thursday into Friday. As the catchment is currently saturated this is likely to keep levels high for several days and may cause further flooding of low lying land, roads and fields. We will continue to monitor the situation. Please continue to check the GOV.UK website for current river levels and more detailed weather forecasts from the Met Office.\" ,\n" +
            "    \"severity\" : \"Flood Alert\" ,\n" +
            "    \"severityLevel\" : 3 ,\n" +
            "    \"timeMessageChanged\" : \"2018-03-15T11:10:00\" ,\n" +
            "    \"timeRaised\" : \"2018-03-15T11:10:00\" ,\n" +
            "    \"timeSeverityChanged\" : \"2018-03-07T15:48:00\"\n" +
            "  }\n" +
            "  , { \n" +
            "    \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floods/102612\" ,\n" +
            "    \"description\" : \"West Somerset Streams\" ,\n" +
            "    \"eaAreaName\" : \"North Wessex\" ,\n" +
            "    \"eaRegionName\" : \"South West\" ,\n" +
            "    \"floodArea\" : { \n" +
            "      \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/112WAFTWSS\" ,\n" +
            "      \"county\" : \"Somerset\" ,\n" +
            "      \"notation\" : \"112WAFTWSS\" ,\n" +
            "      \"polygon\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/112WAFTWSS/polygon\" ,\n" +
            "      \"riverOrSea\" : \"Horner Water, River Aller, Washford River, Hawkcombe Stream, Monksilver Stream, Doniford Stream\"\n" +
            "    }\n" +
            "     ,\n" +
            "    \"floodAreaID\" : \"112WAFTWSS\" ,\n" +
            "    \"isTidal\" : false ,\n" +
            "    \"message\" : \"Showery rainfall, very heavy at times will affect the local area throughout this evening, Wednesday 14/03/2018 and into the early hours of tomorrow morning.  With all the Exmoor catchments already saturated this next spell of rainfall will cause all rivers to react.  This flood alert is being issued from the Beggearn Huish gauge on the Washford River where peak levels are forecast around midnight tonight.\\nHigh river levels and bankfull conditions may be experienced across all north facing Exmoor rivers where flooding may affect low lying land and roads.\" ,\n" +
            "    \"severity\" : \"Flood Alert\" ,\n" +
            "    \"severityLevel\" : 3 ,\n" +
            "    \"timeMessageChanged\" : \"2018-03-14T14:50:00\" ,\n" +
            "    \"timeRaised\" : \"2018-03-14T14:50:00\" ,\n" +
            "    \"timeSeverityChanged\" : \"2018-03-11T16:57:00\"\n" +
            "  }\n" +
            "  , { \n" +
            "    \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floods/102614\" ,\n" +
            "    \"description\" : \"Upper Avon River Swift and Clay Coton Brook\" ,\n" +
            "    \"eaAreaName\" : \"Central\" ,\n" +
            "    \"eaRegionName\" : \"Midlands\" ,\n" +
            "    \"floodArea\" : { \n" +
            "      \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/033WAF201\" ,\n" +
            "      \"county\" : \"Leicestershire, Northamptonshire, Warwickshire\" ,\n" +
            "      \"notation\" : \"033WAF201\" ,\n" +
            "      \"polygon\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/033WAF201/polygon\" ,\n" +
            "      \"riverOrSea\" : \"Avon, Swift, Claycoton\"\n" +
            "    }\n" +
            "     ,\n" +
            "    \"floodAreaID\" : \"033WAF201\" ,\n" +
            "    \"isTidal\" : false ,\n" +
            "    \"message\" : \"River levels in the Upper Avon, River Swift and Clay Coton catchments have fallen below the Flood Alert Level. However, we are expecting them to rise again in the next few hours. Meaning that flooding of low lying land and roads may continue throughout today in the Clay Coton, Stanford and Yelvertoft areas. Take care near areas of concern and continue to monitor the GOV.UK website for further updates. We will continue to monitor the situation.\" ,\n" +
            "    \"severity\" : \"Flood Alert\" ,\n" +
            "    \"severityLevel\" : 3 ,\n" +
            "    \"timeMessageChanged\" : \"2018-03-15T10:04:00\" ,\n" +
            "    \"timeRaised\" : \"2018-03-15T10:04:00\" ,\n" +
            "    \"timeSeverityChanged\" : \"2018-03-09T16:20:00\"\n" +
            "  }\n" +
            "  , { \n" +
            "    \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floods/102618\" ,\n" +
            "    \"description\" : \"Mid Bristol Avon Area\" ,\n" +
            "    \"eaAreaName\" : \"North Wessex\" ,\n" +
            "    \"eaRegionName\" : \"South West\" ,\n" +
            "    \"floodArea\" : { \n" +
            "      \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/112WAFTMBA\" ,\n" +
            "      \"county\" : \"Bath and North East Somerset, Wiltshire\" ,\n" +
            "      \"notation\" : \"112WAFTMBA\" ,\n" +
            "      \"polygon\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/112WAFTMBA/polygon\" ,\n" +
            "      \"riverOrSea\" : \"Bristol River Avon\"\n" +
            "    }\n" +
            "     ,\n" +
            "    \"floodAreaID\" : \"112WAFTMBA\" ,\n" +
            "    \"isTidal\" : false ,\n" +
            "    \"message\" : \"The rainfall has generally passed out of the area. However, further blustery showers are expected overnight and into tomorrow, giving rise to low rainfall totals.\\n\\nRiver levels on the River Avon at Great Somerford, River Avon at Bathford and Semington Brook at Semington peaked earlier today and are falling. River levels on the River avon at Bradford on Avon are expected to peak in the late evening and then fall. All rivers in the catchment are expected to fall overnight and into tomorrow. \\n\\nWe will continue to monitor the situation and will provide a further update on Friday morning.\" ,\n" +
            "    \"severity\" : \"Flood Alert\" ,\n" +
            "    \"severityLevel\" : 3 ,\n" +
            "    \"timeMessageChanged\" : \"2018-03-15T20:31:00\" ,\n" +
            "    \"timeRaised\" : \"2018-03-15T20:31:00\" ,\n" +
            "    \"timeSeverityChanged\" : \"2018-03-09T17:28:00\"\n" +
            "  }\n" +
            "  , { \n" +
            "    \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floods/102650\" ,\n" +
            "    \"description\" : \"River Avon in Worcestershire\" ,\n" +
            "    \"eaAreaName\" : \"West\" ,\n" +
            "    \"eaRegionName\" : \"Midlands\" ,\n" +
            "    \"floodArea\" : { \n" +
            "      \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/031WAF209\" ,\n" +
            "      \"county\" : \"Gloucestershire, Warwickshire, Worcestershire\" ,\n" +
            "      \"notation\" : \"031WAF209\" ,\n" +
            "      \"polygon\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/031WAF209/polygon\" ,\n" +
            "      \"riverOrSea\" : \"Avon\"\n" +
            "    }\n" +
            "     ,\n" +
            "    \"floodAreaID\" : \"031WAF209\" ,\n" +
            "    \"isTidal\" : false ,\n" +
            "    \"message\" : \"This message contains gauge information for Evesham and Bredon.\\n\\n\\nThis message will be updated by 10 am tomorrow or if the situation changes.\\n\\n\\nAt 3:30 pm today;\\n\\nthe level at the Evesham Gauge was 1.39 metres and falling. A peak level of 2.64 metres occurred Tuesday evening.\\n\\nthe level at the Bredon Gauge was 2.11 metres and falling slowly. A peak level of 2.63 occurred at midday yesterday.\\n\\n\\nThe following areas could be affected by flooding \\n\\nLow lying areas of farmland and caravan parks between Evesham and Tewkesbury.\\n\\nThe B4 0 8 0 at Eckington\" ,\n" +
            "    \"severity\" : \"Flood Alert\" ,\n" +
            "    \"severityLevel\" : 3 ,\n" +
            "    \"timeMessageChanged\" : \"2018-03-15T15:31:00\" ,\n" +
            "    \"timeRaised\" : \"2018-03-15T15:31:00\" ,\n" +
            "    \"timeSeverityChanged\" : \"2018-03-10T12:33:00\"\n" +
            "  }\n" +
            "  , { \n" +
            "    \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floods/102654\" ,\n" +
            "    \"description\" : \"River Severn in Gloucestershire\" ,\n" +
            "    \"eaAreaName\" : \"West\" ,\n" +
            "    \"eaRegionName\" : \"Midlands\" ,\n" +
            "    \"floodArea\" : { \n" +
            "      \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/031WAF214\" ,\n" +
            "      \"county\" : \"Gloucestershire, Worcestershire\" ,\n" +
            "      \"notation\" : \"031WAF214\" ,\n" +
            "      \"polygon\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/031WAF214/polygon\" ,\n" +
            "      \"riverOrSea\" : \"Severn Estuary\"\n" +
            "    }\n" +
            "     ,\n" +
            "    \"floodAreaID\" : \"031WAF214\" ,\n" +
            "    \"isTidal\" : false ,\n" +
            "    \"message\" : \"This message contains gauge information for Mythe, Sandhurst, Haw Bridge, Tewkesbury Upper Pound, Leigh Court gauges and Gloucester Docks \\n\\nThis message will be updated by 10 am tomorrow or if the situation changes.\\n\\nAt 3:30 pm today\\n\\nThe level at the Mythe gauge was 3.29 metres and falling.. \\n\\nThe level at the Upper Pound gauge was 3.49  metres and falling. \\n\\n\\nThe level at the Haw Bridge gauge was 4.27 metres and falling. \\n\\nThe level at the Sandhurst gauge was 3.01 metres and falling \\n\\nLeigh Court was 1.28  metres and rising slowly. \\n\\nThe level at the Gloucester Docks gauge was 2.68 metres and falling slowly\" ,\n" +
            "    \"severity\" : \"Flood Alert\" ,\n" +
            "    \"severityLevel\" : 3 ,\n" +
            "    \"timeMessageChanged\" : \"2018-03-15T15:37:00\" ,\n" +
            "    \"timeRaised\" : \"2018-03-15T15:37:00\" ,\n" +
            "    \"timeSeverityChanged\" : \"2018-03-10T14:33:00\"\n" +
            "  }\n" +
            "  , { \n" +
            "    \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floods/102655\" ,\n" +
            "    \"description\" : \"River Trent in Derbyshire\" ,\n" +
            "    \"eaAreaName\" : \"East\" ,\n" +
            "    \"eaRegionName\" : \"Midlands\" ,\n" +
            "    \"floodArea\" : { \n" +
            "      \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/034WAF414\" ,\n" +
            "      \"county\" : \"Derby, Derbyshire, Leicestershire\" ,\n" +
            "      \"notation\" : \"034WAF414\" ,\n" +
            "      \"polygon\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/034WAF414/polygon\" ,\n" +
            "      \"riverOrSea\" : \"River Trent\"\n" +
            "    }\n" +
            "     ,\n" +
            "    \"floodAreaID\" : \"034WAF414\" ,\n" +
            "    \"isTidal\" : false ,\n" +
            "    \"message\" : \"Levels are high and steady along the Derbyshire Trent due to recent heavy rainfall. Flooding is possible at Willington, Barrow upon Trent, Swarkestone, Bargate Lane and the access roads to Willington Meadows, the Twyford Village access road, Church Lane at Barrow, and Ingleby Lane. River levels are likely to remain high for several days with further rain forecast. We urge residents to prepare, remain vigilant and avoid walking and driving through flood water. We are closely monitoring the situation. We will endeavour to update this message as the situation changes\" ,\n" +
            "    \"severity\" : \"Flood Alert\" ,\n" +
            "    \"severityLevel\" : 3 ,\n" +
            "    \"timeMessageChanged\" : \"2018-03-15T21:03:00\" ,\n" +
            "    \"timeRaised\" : \"2018-03-15T21:03:00\" ,\n" +
            "    \"timeSeverityChanged\" : \"2018-03-10T14:55:00\"\n" +
            "  }\n" +
            "  , { \n" +
            "    \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floods/102659\" ,\n" +
            "    \"description\" : \"Middle Nene\" ,\n" +
            "    \"eaAreaName\" : \"Northern\" ,\n" +
            "    \"eaRegionName\" : \"Anglian\" ,\n" +
            "    \"floodArea\" : { \n" +
            "      \"@id\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/055WAF129TMN\" ,\n" +
            "      \"county\" : \"Cambridgeshire, Northamptonshire, Peterborough\" ,\n" +
            "      \"notation\" : \"055WAF129TMN\" ,\n" +
            "      \"polygon\" : \"http://environment.data.gov.uk/flood-monitoring/id/floodAreas/055WAF129TMN/polygon\" ,\n" +
            "      \"riverOrSea\" : \"River Nene\"\n" +
            "    }\n" +
            "     ,\n" +
            "    \"floodAreaID\" : \"055WAF129TMN\" ,\n" +
            "    \"isTidal\" : false ,\n" +
            "    \"message\" : \"Due to on-going rainfall in the Northamptonshire area, the middle Nene has been experiencing high levels for several days now. River levels are now starting to fall, however further rain is forecast over the next 24 hours which could lead to levels rising again. We expect the river to remain high throughout the next few days. We are constantly monitoring river levels and have staff in the field checking for and clearing blockages in this location. This message will be updated  as the situation changes.\" ,\n" +
            "    \"severity\" : \"Flood Alert\" ,\n" +
            "    \"severityLevel\" : 3 ,\n" +
            "    \"timeMessageChanged\" : \"2018-03-15T13:36:00\" ,\n" +
            "    \"timeRaised\" : \"2018-03-15T13:36:00\" ,\n" +
            "    \"timeSeverityChanged\" : \"2018-03-10T15:42:00\"\n" +
            "  }\n" +
            "   ]\n" +
            "}\n";


    public static List<FloodItem> fetchFloodData(){

        //floods.add(new FloodItem(3, "Flood Alert", "Maxico"));


        if (TextUtils.isEmpty(floodJASON)) {
            return null;
        }
        List<FloodItem> floods = new ArrayList<FloodItem>();
        try {
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject jsonObj = new JSONObject(floodJASON);
            JSONArray items = jsonObj.getJSONArray("items");
            // looping through All features
            for (int i = 0; i < items.length(); i++){
                JSONObject flood = items.getJSONObject(i);
                String eaAreaName = flood.getString("eaAreaName");

                JSONObject floodArea = flood.getJSONObject("floodArea");
                String county = floodArea.getString("county");
                String riverOrSea = floodArea.getString("riverOrSea");

                String message = flood.getString("message");
                String severity = flood.getString("severity");
                int severityLevel = flood.getInt("severityLevel");
                String timeMessageChanged = flood.getString("timeMessageChanged");
                String timeRaised = flood.getString("timeRaised");

                floods.add(new FloodItem(severityLevel, severity, eaAreaName, county, riverOrSea, message, timeMessageChanged, timeRaised));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the Flood JSON results", e);
        }
        return floods;
    }
}
