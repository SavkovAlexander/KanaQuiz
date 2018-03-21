package com.noprestige.kanaquiz.questions;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import com.noprestige.kanaquiz.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import static com.noprestige.kanaquiz.questions.RomanizationSystem.UNKNOWN;

abstract class XmlParser
{
    static void parseXmlKanaSet(XmlResourceParser parser, Resources resources,
            ArrayList<KanaQuestion[][][]> kanaSetList, ArrayList<String> prefIdList, ArrayList<String> setTitleList,
            ArrayList<String> setNoDiacriticsTitleList) throws XmlPullParserException, IOException, ParseException
    {
        String prefId = null;
        String setTitle = null;
        String setNoDiacriticsTitle = null;

        for (int i = 0; i < parser.getAttributeCount(); i++)
        {
            switch (parser.getAttributeName(i))
            {
                case "prefId":
                    prefId = parseXmlValue(parser, i, resources);
                    break;
                case "setTitle":
                    setTitle = parseXmlValue(parser, i, resources, R.string.set);
                    break;
                case "setNoDiacriticsTitle":
                    setNoDiacriticsTitle = parseXmlValue(parser, i, resources, R.string.set);
            }
        }

        if (prefId == null || setTitle == null)
            throw new ParseException("Missing attribute in KanaSet", parser.getLineNumber());

        if (setNoDiacriticsTitle == null)
            setNoDiacriticsTitle = setTitle;

        prefIdList.add(prefId);
        setTitleList.add(setTitle);
        setNoDiacriticsTitleList.add(setNoDiacriticsTitle);

        int indexPoint = kanaSetList.size();

        kanaSetList.add(new KanaQuestion[Diacritic.values().length][2][]);

        ArrayList<KanaQuestion> currentSet = new ArrayList<>();

        int lineNumber = parser.getLineNumber();

        for (int eventType = parser.getEventType();
                !(eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("KanaSet"));
                eventType = parser.next())
        {
            if (eventType == XmlPullParser.START_TAG)
            {
                if (parser.getName().equalsIgnoreCase("Section"))
                    parseXmlKanaSubsection(parser, resources, kanaSetList, indexPoint);

                else if (parser.getName().equalsIgnoreCase("KanaQuestion"))
                    currentSet.add(parseXmlKanaQuestion(parser, resources));

                else if (parser.getName().equalsIgnoreCase("WordQuestion"))
                    currentSet.add(parseXmlWordQuestion(parser, resources));
            }

            else if (eventType == XmlPullParser.END_DOCUMENT)
                throw new ParseException("Missing KanaSet closing tag", lineNumber);
        }
        parseXmlStoreSet(currentSet, kanaSetList, indexPoint, Diacritic.NO_DIACRITIC, false);
    }

    static private void parseXmlKanaSubsection(XmlResourceParser parser, Resources resources,
            ArrayList<KanaQuestion[][][]> kanaSetList, int indexPoint)
            throws XmlPullParserException, IOException, ParseException
    {
        Diacritic diacritics = null;
        boolean isDigraphs = false;
        boolean isDigraphsSet = false;

        ArrayList<KanaQuestion> currentSet = new ArrayList<>();

        for (int i = 0; i < parser.getAttributeCount(); i++)
        {
            switch (parser.getAttributeName(i))
            {
                case "diacritics":
                    diacritics = Diacritic.valueOf(parser.getAttributeValue(i));
                    break;
                case "digraphs":
                    isDigraphs = parser.getAttributeBooleanValue(i, false);
                    isDigraphsSet = true;
            }
        }

        if (diacritics == null || !isDigraphsSet)
            throw new ParseException("Missing attribute in Section tag", parser.getLineNumber());

        int lineNumber = parser.getLineNumber();

        for (int eventType = parser.getEventType();
                !(eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("Section"));
                eventType = parser.next())
        {
            if (eventType == XmlPullParser.START_TAG && parser.getName().equalsIgnoreCase("KanaQuestion"))
                currentSet.add(parseXmlKanaQuestion(parser, resources));

            else if (eventType == XmlPullParser.END_DOCUMENT)
                throw new ParseException("Missing Section closing tag", lineNumber);
        }
        parseXmlStoreSet(currentSet, kanaSetList, indexPoint, diacritics, isDigraphs);
    }

    static private void parseXmlStoreSet(ArrayList<KanaQuestion> currentSet, ArrayList<KanaQuestion[][][]> kanaSetList,
            int indexPoint, Diacritic diacritics, boolean isDigraphs)
    {
        KanaQuestion[] currentSetArray = new KanaQuestion[currentSet.size()];
        currentSet.toArray(currentSetArray);

        KanaQuestion[][][] pulledArray = kanaSetList.get(indexPoint);
        pulledArray[diacritics.ordinal()][isDigraphs ? 1 : 0] = currentSetArray;
        kanaSetList.set(indexPoint, pulledArray);
    }

    static private KanaQuestion parseXmlKanaQuestion(XmlResourceParser parser, Resources resources)
            throws ParseException, XmlPullParserException, IOException
    {
        String thisQuestion = null;
        String thisAnswer = null;

        for (int i = 0; i < parser.getAttributeCount(); i++)
        {
            switch (parser.getAttributeName(i))
            {
                case "question":
                    thisQuestion = parseXmlValue(parser, i, resources);
                    break;
                case "answer":
                    thisAnswer = parseXmlValue(parser, i, resources);
            }
        }

        if (thisQuestion == null || thisAnswer == null)
            throw new ParseException("Missing attribute in KanaQuestion", parser.getLineNumber());

        KanaQuestion returnValue = new KanaQuestion(thisQuestion, thisAnswer);

        if (!(parser.next() == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("KanaQuestion")))
            parseXmlAltAnswers(returnValue, parser);

        return returnValue;
    }

    static private WordQuestion parseXmlWordQuestion(XmlResourceParser parser, Resources resources)
            throws ParseException, XmlPullParserException, IOException
    {
        String thisRomanji = null;
        String thisKana = null;
        String thisKanji = null;
        String thisAnswer = null;
        for (int i = 0; i < parser.getAttributeCount(); i++)
        {
            switch (parser.getAttributeName(i))
            {
                case "romanji":
                    thisRomanji = parseXmlValue(parser, i, resources);
                    break;
                case "kana":
                    thisKana = parseXmlValue(parser, i, resources);
                    break;
                case "kanji":
                    thisKanji = parseXmlValue(parser, i, resources);
                    break;
                case "answer":
                    thisAnswer = parseXmlValue(parser, i, resources);
            }
        }
        if (thisRomanji == null || thisAnswer == null)
            throw new ParseException("Missing attribute in WordQuestion", 0);

        WordQuestion question;

        question = new WordQuestion(thisRomanji, thisAnswer);

        if (thisKana != null)
            question.setKana(thisKana);
        if (thisKanji != null)
            question.setKanji(thisKanji);

        return question;
    }

    static private void parseXmlAltAnswers(KanaQuestion question, XmlResourceParser parser)
            throws XmlPullParserException, IOException, ParseException
    {
        int lineNumber = parser.getLineNumber();

        for (int eventType = parser.getEventType();
                !(eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("KanaQuestion"));
                eventType = parser.next())
        {
            if (eventType == XmlPullParser.START_TAG && parser.getName().equalsIgnoreCase("AltAnswer"))
            {
                String systemString = UNKNOWN.toString();
                for (int i = 0; i < parser.getAttributeCount(); i++)
                {
                    switch (parser.getAttributeName(i))
                    {
                        case "system":
                            systemString = parser.getAttributeValue(i);
                    }
                }

                RomanizationSystem[] systemsList = parseRomanizationSystemList(systemString);

                if (parser.next() == XmlPullParser.TEXT)
                    for (RomanizationSystem system : systemsList)
                        question.addAltAnswer(parser.getText(), system);
                else
                    throw new ParseException("Empty AltAnswer tag", parser.getLineNumber());
            }

            else if (eventType == XmlPullParser.END_DOCUMENT)
                throw new ParseException("Missing KanaQuestion closing tag", lineNumber);
        }
    }

    static private RomanizationSystem[] parseRomanizationSystemList(String attributeString)
    {
        //trims and collapses whitespace
        attributeString = attributeString.trim().replaceAll("\\s+", " ");

        ArrayList<RomanizationSystem> list = new ArrayList<>();
        StringBuilder thisItem = new StringBuilder();

        for (int i = 0; i < attributeString.length(); i++)
        {
            if (attributeString.charAt(i) == ' ')
            {
                list.add(RomanizationSystem.valueOf(thisItem.toString()));
                thisItem = new StringBuilder();
            }
            else
                thisItem.append(attributeString.charAt(i));
        }

        list.add(RomanizationSystem.valueOf(thisItem.toString()));

        RomanizationSystem[] returnValue = new RomanizationSystem[list.size()];
        list.toArray(returnValue);
        return returnValue;
    }

    static private String parseXmlValue(XmlResourceParser parser, int index, Resources resources)
    {
        return parseXmlValue(parser, index, resources, 0);
    }

    static private String parseXmlValue(XmlResourceParser parser, int index, Resources resources, int stringResource)
    {
        int refId = parser.getAttributeResourceValue(index, 0);
        return (refId == 0) ? parser.getAttributeValue(index) : ((stringResource == 0) ? resources.getString(refId) :
                resources.getString(refId, resources.getString(stringResource)));
    }
}
