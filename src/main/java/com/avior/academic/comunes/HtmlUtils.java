package com.avior.academic.comunes;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by jairo on 18/08/16.
 */
public class HtmlUtils {

    private static final Logger logger = Logger.getLogger(HtmlUtils.class);

    public static String limpiaTags(String html){
        return Jsoup.parse(html).text();
    }

    public static String getURLRelativa(String html){
    	try{
	        Document doc = Jsoup.parse(html);
	        Element link = doc.select("a").first();
	        //String absURL = link.attr("abs:href");
	        String relURL = link.attr("href");
	        logger.info("URL relativa: " + relURL);
	        return relURL.replace("~", "").substring(0, relURL.length());
    	}catch(Exception e){
    		logger.error("Error al parsear el html, se retorna la misma cadena: ",e);
    		return html;
    	}
    }
}
