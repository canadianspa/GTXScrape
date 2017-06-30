

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

		//set start variables
		Parameters p = getParameters();
		List<String> s = p.getRaw();

		//hacky method dont really know how this works 1:login 2:make it wait 3:click inbox 4:click a order 5:go deeper 6:make sure you have actually gone deeper 7:read order
		stage.setWidth(700);
		stage.setHeight(700);
		Scene scene = new Scene(new Group());
		WebView browser = new WebView();
		webEngine = browser.getEngine();
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(browser);

		webEngine.load("https://kingfisher.edt.fr/BCP-Web/login.do?");

		//cant work out how to tell the program that a state has finished loading without this 
		webEngine.getLoadWorker().stateProperty().addListener(
				new ChangeListener<State>() {
					public void changed(ObservableValue ov, State oldState, State newState) {
						if (newState == State.SUCCEEDED) {
							//log in
							if(webPos == 1)
							{
								try {
									webEngine.executeScript("UserForm.login.value='Xavier Labelle';UserForm.password.value='***REMOVED***';validate();");
									webPos = 2;
								} catch (Exception e) {

									e.printStackTrace();
								}
							}
							else if(webPos ==2)
							{
								try {
									webEngine.executeScript("checkedAllline(checked, \"chkbox\", \"webEdiDisplayDispatch\")");
									webPos = 3;
								} catch (Exception e) {

									e.printStackTrace();
								}
							}
							else if(webPos ==2)
							{
								try {
									webEngine.executeScript("checkedAllline(checked, \"chkbox\", \"webEdiDisplayDispatch\")");
									webPos = 2;
								} catch (Exception e) {

									e.printStackTrace();
								}
							}
							

						}
					}
				});



		scene.setRoot(scrollPane);

		stage.setScene(scene);
		stage.show();

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
		Scrapper.launch(myArg);


	}


}