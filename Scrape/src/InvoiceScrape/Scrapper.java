package InvoiceScrape;

import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

import BNQ.EDA;
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
	int page =1;
	int placeOnPage = 0;
	int webPos = 1;
	int lastPage;
	int lastPlace;
	
	WebEngine webEngine;
	ArrayList<Invoices> listOfReports = new ArrayList<Invoices>();


	@Override
	public void start(final Stage stage) {

		//set start variables
		Parameters p = getParameters();
		List<String> s = p.getRaw();
		lastPage = Integer.parseInt(s.get(0));
		lastPlace = Integer.parseInt(s.get(1)) - 1;

		//hacky method dont really know how this works 1:login 2:make it wait 3:click inbox 4:click a order 5:go deeper 6:make sure you have actually gone deeper 7:read order
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

								if(page ==1)
								{
									webEngine.executeScript("window.open('https://gxstradeweb.gxsolc.com/edi-bin/EdiMailboxFrameset.pl?lang=en&box_type=sent', '_self' )");
								}
								else
								{
									webEngine.load("https://gxstradeweb.gxsolc.com/edi-bin/EdiMailbox.pl?NextDocListed=Next&LastStartNum=" + (((page-2)*10)+1) + "&box_type=sent&lang=en&sort_var=");
								}
								webPos =4;


							}
							//click top link
							if(webPos ==4)
							{								
								try {
									if(page == 1)
									{
										webEngine.executeScript("window.open('https://gxstradeweb.gxsolc.com' + Content.form.ReadUrl" + placeOnPage + ".value,'_self')");
									}
									else
									{
										webEngine.executeScript("window.open('https://gxstradeweb.gxsolc.com' + form.ReadUrl" + placeOnPage + ".value,'_self')");
									}

									placeOnPage += 1;
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
							//read it
							if(webPos ==7)
							{
								String html = (String) webEngine.executeScript("document.documentElement.outerHTML");
								if(isBNQ(html))
								{
									try {
										Invoices myEDA = Scrapper.this.readInvoices(html);
										boolean seenBefore =false;
										for(Invoices e : listOfReports)
										{
											if(e.purchOrNo.equals(myEDA.purchOrNo))
											{
												seenBefore = true;
											}
										}
										if(seenBefore)
										{
											if(lastPlace == 9)
											{
												lastPage += 1;
												lastPlace = 0;
											}
											else
											{
												lastPlace += 1;
											}
										}
										else
										{
											listOfReports.add(myEDA);
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
									

								}

								System.out.println("At Page " + page);
								System.out.println("At Place " + placeOnPage);

								//if at the last place and page stop
								if (placeOnPage > lastPlace && page >= lastPage)
								{
									System.out.println("adding to invoices");
									//Invoices.createInvoices(listOfReports);
									Invoices.addToInvoiceList(listOfReports);
									stage.close();
									webPos = 8;
								}
								else
								{

									//go to next page
									if(placeOnPage == 10)
									{

										placeOnPage = 0;
										page += 1;

										webEngine.load("https://gxstradeweb.gxsolc.com/edi-bin/EdiMailbox.pl?NextDocListed=Next&LastStartNum=" + (((page-2)*10)+1) + "&box_type=sent&lang=en&sort_var=");
										webPos = 4;

									}
									//go back
									else
									{
										if(page ==1)
										{
											webEngine.load("https://gxstradeweb.gxsolc.com/edi-bin/EdiMailboxFrameset.pl?lang=en&box_type=sent");
										}
										else
										{
											webEngine.load("https://gxstradeweb.gxsolc.com/edi-bin/EdiMailbox.pl?NextDocListed=Next&LastStartNum=" + (((page-2)*10)+1) + "&box_type=sent&lang=en&sort_var=");

										}
										webPos = 4;
									}
								}

							}


						}
					}
				});



		scene.setRoot(scrollPane);

		stage.setScene(scene);
		stage.show();

	}

	public boolean isBNQ(String html)
	{
		return html.contains("BnQ");
	}
	//takes certain webpage as a html and ouputs bnq stuff
	public Invoices readInvoices(String html) throws Exception
	{
		String invoiceNo,purchOrNo,invDate,delDate,amountOut;
		//CUSTOMER ORDER seems wrong
		html = html.replace("\n", "").replace("\r", "");
		invoiceNo = html.substring(html.indexOf("Invoice No") + 89, html.indexOf("&", html.indexOf("Invoice No")+89));
		System.out.println(invoiceNo);
		purchOrNo = html.substring(html.indexOf("Purchase Order No") + 96, html.indexOf("&", html.indexOf("Purchase Order No")+96));
		System.out.println(purchOrNo);
		invDate = html.substring(html.indexOf("Invoice Date") + 91, html.indexOf("&", html.indexOf("Invoice Date")+91));
		invDate = invDate.replace(".", "/");
		System.out.println(invDate);
		delDate = html.substring(html.indexOf("Delivery Date") + 91, html.indexOf("&", html.indexOf("Delivery Date")+91));
		delDate = delDate.replace(".", "/");
		System.out.println(delDate);
		amountOut = html.substring(html.indexOf("Sub-Total") + 112, html.indexOf("&", html.indexOf("Sub-Total")+112));
		System.out.println(amountOut);
		
		
		return new Invoices(invoiceNo,purchOrNo,invDate,delDate,amountOut);


	}

	//test with console
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter page up to where you need");
		int page = scanner.nextInt();
		System.out.println("Enter place on the page up to where you need (1- 10)");
		int placeOnPage = scanner.nextInt();

		String[] myArg = new String[3];
		myArg[0] = String.valueOf(page);
		myArg[1] = String.valueOf(placeOnPage);
		Scrapper.launch(myArg);


	}


}