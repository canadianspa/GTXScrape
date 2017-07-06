package BNQ;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.application.Application;
import javafx.application.Application.Parameters;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;

public class BNQMultiScraper extends Application {

	ConcurrentHashMap<Integer,Integer> tabWebPos = new ConcurrentHashMap<Integer,Integer>();
	ConcurrentHashMap<Integer,Integer> page = new ConcurrentHashMap<Integer,Integer>();
	ConcurrentHashMap<Integer,Integer> poLookedAt = new ConcurrentHashMap<Integer,Integer>();
	ConcurrentHashMap<Integer,Integer> amountToLookAt = new ConcurrentHashMap<Integer,Integer>();
	ConcurrentHashMap<Integer,Boolean> finished = new ConcurrentHashMap<Integer,Boolean>();



	int lastPage =1;
	int lastPlace = 1;
	int seqNo = 1;
	CopyOnWriteArrayList<EDA> listOfEda = new CopyOnWriteArrayList<EDA>();

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parameters p = getParameters();
		List<String> s = p.getRaw();
		lastPage = Integer.parseInt(s.get(0));
		lastPlace = Integer.parseInt(s.get(1)) - 1;
		seqNo = Integer.parseInt(s.get(2));
		TabPane tabPane = new TabPane();

		for(int i = 0;i <10;i++)
		{
			Tab tab1 = new Tab();
			tab1.setText("browser" + i);
			tab1.setContent(makeBrowserTab("browser" + i,i));
			tabPane.getTabs().add(tab1);
			page.put(i, 1);
			poLookedAt.put(i, 0);
			if(lastPlace < i)
			{
				amountToLookAt.put(i,(lastPage-1));
			}
			else 
			{
				amountToLookAt.put(i,lastPage);
			}
			if(amountToLookAt.get(i) == 0)
			{
				tabWebPos.put(i, 8);
				finished.put(i, true);

			}
			else
			{
				tabWebPos.put(i, 1);
				finished.put(i, false);

			}



		}



		Scene scene = new Scene(tabPane);
		primaryStage.setTitle("Two browsers");
		primaryStage.setScene(scene);
		primaryStage.show();

		Task task = new Task(){

			@Override
			protected Object call() throws Exception {
				
				while(true)
				{
					boolean ready = true;
					for(Integer i : poLookedAt.keySet()) 
					{
						if(finished.get(i) == false )
						{
							ready = false;
						}
					}
					if(ready)
					{
						Collections.sort(listOfEda, new Comparator<EDA>() {

							@Override
							public int compare(EDA o1, EDA o2) {
								return Integer.parseInt(o2.purchOrderNo)-Integer.parseInt(o1.purchOrderNo);
							}
					       
					    });
						EDANewFunctions.createAllXLS(listOfEda);
						EDANewFunctions.addToEDAList(listOfEda);
						EDANewFunctions.fillOut();
						System.out.println(listOfEda.size() + " BNQ orders");
						return null;
					}
				}

			}

		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		System.out.println("Starting background task...");
		th.start();


	}

	private Node makeBrowserTab(String identifier,int place) {

		BorderPane borderPane = new BorderPane();
		HBox hbox = new HBox();
		WebView browser = new WebView();
		browser.setPrefSize(960, 640);
		borderPane.setTop(hbox);
		borderPane.setCenter(browser);

		WebEngine engine = browser.getEngine();


		engine.load("https://gxstradeweb.gxsolc.com/pub-log/login.html?lang=en");

		engine.getLoadWorker().stateProperty().addListener(
				new ChangeListener<State>() {
					public void changed(ObservableValue ov, State oldState, State newState) {

						if( newState != Worker.State.SUCCEEDED ) {
							return;
						}

						if(tabWebPos.get(place) == 1)
						{
							try {
								engine.executeScript("form.User.value='***REMOVED***'; form.Password.value='***REMOVED***';form.submit.click();");
								tabWebPos.put(place, 3);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}

						//click inbox
						else if(tabWebPos.get(place) ==3)
						{

							String html = (String) engine.executeScript("document.documentElement.outerHTML");
							if(isError(html))
							{
								tabWebPos.put(place, 1);
								engine.load("https://gxstradeweb.gxsolc.com/pub-log/login.html?lang=en");
							}

							try {
								engine.load("https://gxstradeweb.gxsolc.com/edi-bin/EdiMailbox.pl?NextDocListed=Next&LastStartNum=" + (((page.get(place) -2)*10)+1) + "&box_type=in&lang=en&sort_var=");

								tabWebPos.put(place,4);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						//go to your place
						else if(tabWebPos.get(place) ==4)
						{			

							String html = (String) engine.executeScript("document.documentElement.outerHTML");
							if(isError(html))
							{
								System.out.println("eror");
								tabWebPos.put(place, 1);
								engine.load("https://gxstradeweb.gxsolc.com/pub-log/login.html?lang=en");
							}
							try {


								engine.executeScript("window.open('https://gxstradeweb.gxsolc.com' + form.ReadUrl" + place + ".value,'_self')");



								tabWebPos.put(place,5);
							} catch (Exception e) {
							}
						}
						//go to inner html doc
						else if(tabWebPos.get(place) ==5)
						{

							try
							{
								engine.executeScript("window.open(MAINAREA.location,'_self')");
								tabWebPos.put(place,6);

							} catch (Exception e) {
							}

						}
						else if(tabWebPos.get(place) ==6)
						{
							String html = (String) engine.executeScript("document.documentElement.outerHTML");
							poLookedAt.put(place, poLookedAt.get(place)+1);
							page.put(place, page.get(place) + 1);
							System.out.println("looked at " + poLookedAt.get(place));
							System.out.println("need to look at " + amountToLookAt.get(place));
							if(isBNQ(html))
							{
								try {
									EDA myEDA = BNQMultiScraper.this.readBNQHTML(html);
									//make sure havent already seen it
									boolean seenBefore =false;
									for(EDA e : listOfEda)
									{
										if(e.purchOrderNo.equals(myEDA.purchOrderNo))
										{
											seenBefore = true;
										}
									}
									if(!seenBefore)
									{
										listOfEda.add(myEDA);
										System.out.println(listOfEda.size());
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							if(poLookedAt.get(place) == amountToLookAt.get(place))
							{

								System.out.println("STOP!");
								tabWebPos.put(place, 8);
								finished.put(place, true);



							}
							else
							{
								tabWebPos.put(place,4);

								engine.load("https://gxstradeweb.gxsolc.com/edi-bin/EdiMailbox.pl?NextDocListed=Next&LastStartNum=" + (((page.get(place) -2)*10)+1) + "&box_type=in&lang=en&sort_var=");

							}


						}

					}


				});



		return borderPane;
	}

	public boolean isError(String html)
	{
		return html.contains("GXS TradeWeb Error Message");
	}
	public boolean isBNQ(String html)
	{
		return html.contains("BnQ");
	}
	//takes certain webpage as a html and ouputs bnq stuff
	public EDA readBNQHTML(String html) throws Exception
	{
		//System.out.println(html);
		String storeCode,purchOrderNo,custTellNo1,bQSuppNo,custName;
		ArrayList<String> eanCode1 = new ArrayList<String>();
		ArrayList<String> desc1 = new ArrayList<String>();
		ArrayList<String> qty1 = new ArrayList<String>();
		String dateOrderPlaced, delDate;
		String salesNumber;
		String custAdd1,custAdd2,custAdd3,custAdd4,custPostCode;

		storeCode = html.substring(html.indexOf("hfStoreLocCode") + 25, html.indexOf(">", html.indexOf("hfStoreLocCode")+25)-1);
		purchOrderNo = html.substring(html.indexOf("PURCHASE ORDER NO") + 162, html.indexOf("&", html.indexOf("PURCHASE ORDER NO")+162));
		custTellNo1 = html.substring(html.indexOf("PHONE-DAY") + 166, html.indexOf("&", html.indexOf("PHONE-DAY")+166));
		bQSuppNo = html.substring(html.indexOf("VENDOR") + 155, html.indexOf("&", html.indexOf("VENDOR")+155));
		custName = html.substring(html.indexOf("ADDRESS - HOME DELIVERY") + 183, html.indexOf("&", html.indexOf("ADDRESS - HOME DELIVERY")+183));
		dateOrderPlaced = html.substring(html.indexOf("PURCHASE ORDER DATE") + 164, html.indexOf("&", html.indexOf("PURCHASE ORDER DATE")+164));
		dateOrderPlaced = dateOrderPlaced.replace(".", "/");
		delDate = html.substring(html.indexOf("DELIVERY DATE") + 158, html.indexOf("&", html.indexOf("DELIVERY DATE")+158));
		delDate = delDate.replace(".", "/");
		String custDetail = html.substring(html.indexOf("ADDRESS - HOME DELIVERY"), html.indexOf("CONTACT"));
		salesNumber = html.substring(html.indexOf("SALES ORDER NO") + 160, html.indexOf("&", html.indexOf("SALES ORDER NO")+160));
		String[] custDetailSplit = custDetail.split("<tr>");
		String[] custInfo = new String[5]; 


		//all the actual information 
		for(int i = 2; i < 7; i++)
		{
			custInfo[i -2] = custDetailSplit[i].substring(103, custDetailSplit[i].indexOf("&",103));
		}

		//if the information is empty shift postcode
		int numOfAddrLines = 4;
		for(int i = 4;i >= 0; i--)
		{
			if(custInfo[i].equals(""))
			{
				numOfAddrLines = i - 1;
			}
		}

		if(numOfAddrLines == 0)
		{
			custAdd1 = "";
			custAdd2 = "";
			custAdd3 = "";
			custAdd4 = "";
			custPostCode = custInfo[0];
		}
		else if(numOfAddrLines == 1)
		{
			custAdd1 = custInfo[0];
			custAdd2 = "";
			custAdd3 = "";
			custAdd4 = "";
			custPostCode = custInfo[1];
		}
		else if(numOfAddrLines == 2)
		{
			custAdd1 = custInfo[0];
			custAdd2 = custInfo[1];
			custAdd3 = "";
			custAdd4 = "";
			custPostCode = custInfo[2];
		}
		else if(numOfAddrLines == 3)
		{
			custAdd1 = custInfo[0];
			custAdd2 = custInfo[1];
			custAdd3 = custInfo[2];
			custAdd4 = "";
			custPostCode = custInfo[3];
		}
		else if(numOfAddrLines == 4)
		{
			custAdd1 = custInfo[0];
			custAdd2 = custInfo[1];
			custAdd3 = custInfo[2];
			custAdd4 = custInfo[3];
			custPostCode = custInfo[4];
		}
		else
		{
			System.out.println("No Address Lines?");
			custAdd1 = "";
			custAdd2 = "";
			custAdd3 = "";
			custAdd4 = "";
			custPostCode = "";
		}

		//need to split by <!-- Begin Detail Line -->

		String[] lines = html.split("<!-- Begin Detail Line -->");
		for(int i = 1; i < lines.length; i ++)
		{
			eanCode1.add(lines[i].substring(lines[i].indexOf("EA&n") + 90, lines[i].indexOf("&", lines[i].indexOf("EA&n")+90))); 
			desc1.add(lines[i].substring(lines[i].indexOf("EA&n") + 333, lines[i].indexOf("&", lines[i].indexOf("EA&n")+333)));
			qty1.add(lines[i].substring(191, lines[i].indexOf("&",191)));

		}

		return new EDA(String.valueOf(seqNo), storeCode,  purchOrderNo,  custTellNo1, bQSuppNo,custName,  eanCode1,  desc1,  qty1,dateOrderPlaced,delDate,custAdd1,custAdd2,custAdd3,custAdd4,custPostCode,salesNumber);


	}

	//test with console
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter page up to where you need");
		int page = scanner.nextInt();
		System.out.println("Enter place on the page up to where you need (1- 10)");
		int placeOnPage = scanner.nextInt();
		System.out.println("Enter the next sequence number");
		int seqNO = scanner.nextInt();

		String[] myArg = new String[3];
		myArg[0] = String.valueOf(page);
		myArg[1] = String.valueOf(placeOnPage);
		myArg[2] = String.valueOf(seqNO);
		BNQMultiScraper.launch(myArg);
	}
}