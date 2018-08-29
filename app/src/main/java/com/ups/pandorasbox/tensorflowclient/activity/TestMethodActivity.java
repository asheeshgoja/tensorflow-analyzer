package com.ups.pandorasbox.tensorflowclient.activity;

// NOTE THAT THE org.json LIBRARY ONLY WORKS IN ANDROID APPS (AND NOT STANDALONE JAVA).
import org.json.JSONException;
import org.json.JSONObject;

public class TestMethodActivity
{
    public static void testJsonSimple()
    {
        String result = "{}";
        final String NL = System.lineSeparator();

        String jString =
            "{" + NL +
            "  \"foo\": \"bar\", " + NL +
            "  \"array\": [" + NL +
            "    \"aaa\", " + NL +
            "    \"bbb\", " + NL +
            "    \"ccc\"  " + NL +
            "  ] " + NL +
            "}";

        try
        {
            JSONObject jObject = new JSONObject(jString);
            result = jObject.toString();
        }
        catch (JSONException jsone)
        {
            jsone.printStackTrace();
        }

        if (!result.equals("{}"))
        {
            System.out.println("RESULT RETURNED BY THE testJsonSimple METHOD: ");
            System.out.println("-------------------------------------------- ");
            System.out.println(result);

        }
    }
}
