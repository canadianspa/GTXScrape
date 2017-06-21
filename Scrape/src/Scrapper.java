
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Scrapper extends Application {
	int webPos = 1;
	WebEngine webEngine;
	@Override
	public void start(final Stage stage) {
		//hacky method dont really know how this works 1:login 2:make it wait 3:click inbox 4:click a order 5:read order
		
		stage.setWidth(700);
		stage.setHeight(700);
		Scene scene = new Scene(new Group());

		WebView browser = new WebView();
		webEngine = browser.getEngine();

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(browser);

		webEngine.load("https://gxstradeweb.gxsolc.com/pub-log/login.html?lang=en");

		//cant work out how to tell the program that a state has finished loading without this 
		webEngine.getLoadWorker().stateProperty().addListener(
				new ChangeListener<State>() {
					public void changed(ObservableValue ov, State oldState, State newState) {
						if (newState == State.SUCCEEDED) {
							//log in
							if(webPos == 1)
							{
								try {
									webEngine.executeScript("form.User.value='***REMOVED***'; form.Password.value='***REMOVED***';form.submit.click();");
									webPos = 2;
								} catch (Exception e) {
									
									e.printStackTrace();
								}
							}
							if(webPos ==2)
							{
								//even more hackey this is to stop it from succeding too quickly (fuck knows why it changes)
								try {
									webEngine.executeScript("form.User.value='***REMOVED***'; form.Password.value='***REMOVED***';form.submit.click();");
									webPos = 2;
								} catch (Exception e) {
									webPos =3;
								}
								
							}
							//click inbox
							if(webPos ==3)
							{
								
								webEngine.executeScript("window.open('https://gxstradeweb.gxsolc.com/edi-bin/EdiMailboxFrameset.pl?lang=en&box_type=in', '_self' )");
								webPos =4;
									
								
							}
							//click top link
							if(webPos ==4)
							{								
								try {
									webEngine.executeScript("window.open('https://gxstradeweb.gxsolc.com' + Content.form.ReadUrl1.value,'_self')");
									webPos = 5;
								} catch (Exception e) {
								}
							}
							//go to inner html doc
							if(webPos ==5)
							{
						
								try
								{
								webEngine.executeScript("window.open(MAINAREA.location,'_self')");
								webPos = 6;
								
								} catch (Exception e) {
								}
								
							}
							//make sure that its actually there 
							if(webPos ==6)
							{
						
								try
								{
								webEngine.executeScript("window.open(MAINAREA.location,'_self')");
								} catch (Exception e) {
								webPos = 7;
								}
								
								
							}
							//
							
							if(webPos ==7)
							{
								String html = (String) webEngine.executeScript("document.documentElement.outerHTML");
								Scrapper.this.readBNQHTML(html);

							}
						}
					}
				});



		scene.setRoot(scrollPane);

		stage.setScene(scene);
		stage.show();
	}
	
	//takes certain webpage as a html and ouputs bnq stuff
	public EDA readBNQHTML(String html)
	{
		//System.out.println(html);
		String seqNo, storeCode,purchOrderNo,custTellNo1,bQSuppNo,custName;
		ArrayList<String> eanCode1 = new ArrayList<String>();
		ArrayList<String> desc1 = new ArrayList<String>();
		ArrayList<String> qty1 = new ArrayList<String>();
		
		Date dateOrderPlaced;
		String dateOrderPlacedStr;
		
		storeCode = html.substring(html.indexOf("hfStoreLocCode") + 25, html.indexOf(">", html.indexOf("hfStoreLocCode")+25)-1);
		purchOrderNo = html.substring(html.indexOf("PURCHASE ORDER NO") + 162, html.indexOf("&", html.indexOf("PURCHASE ORDER NO")+162));
		custTellNo1 = html.substring(html.indexOf("PHONE-DAY") + 166, html.indexOf("&", html.indexOf("PHONE-DAY")+166));
		bQSuppNo = html.substring(html.indexOf("VENDOR") + 155, html.indexOf("&", html.indexOf("VENDOR")+155));
		custName = html.substring(html.indexOf("ADDRESS - HOME DELIVERY") + 183, html.indexOf("&", html.indexOf("ADDRESS - HOME DELIVERY")+183));
		dateOrderPlacedStr = html.substring(html.indexOf("PURCHASE ORDER DATE") + 164, html.indexOf("&", html.indexOf("PURCHASE ORDER DATE")+164));
		
		System.out.println(storeCode);
		System.out.println(purchOrderNo);
		System.out.println(custTellNo1);
		System.out.println(bQSuppNo);
		System.out.println(custName);
		System.out.println(dateOrderPlacedStr);
		
		//need to split by <!-- Begin Detail Line -->
		
		String[] lines = html.split("<!-- Begin Detail Line -->");
		for(int i = 1; i < lines.length; i ++)
		{
			eanCode1.add(html.substring(html.indexOf("EA&n") + 155, html.indexOf("&", html.indexOf("EA&n")+155))); 
			desc1.add(html.substring(html.indexOf("EA&n") + 155, html.indexOf("&", html.indexOf("EA&n")+155)));
			qty1.add(html.substring(html.indexOf("EA&n") + 155, html.indexOf("&", html.indexOf("EA&n")+155)));
		}
		//System.out.println(custTellNo1);
		//System.out.println(custTellNo1);
		//System.out.println(custTellNo1);
		

		return null;
		//return new EDA( storeCode,  purchOrderNo,  custTellNo1, bQSuppNo,custName,  eanCode1,  desc1,  qty1,dateOrderPlaced);
	
		
	}

	public static void main(String[] args) {
		Scrapper s = new Scrapper();
		Scrapper.launch(args);

	}
}