package view.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Court extends AnchorPane {
	
	@FXML Canvas canvas;
	
	public Court() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/court.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            initialize();
            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
	
	private void initialize(){
			canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
			
				int column = (int) e.getX();
	        	int row = (int) e.getY();
				
			if (((MouseEvent) e).getButton().equals(MouseButton.SECONDARY)){
				System.out.println(column + " " + row + " promasio");
			}else {
	        	System.out.println(column + " " + row + " dao");
			}
        });
			
			canvas.widthProperty().bind(this.widthProperty());
	    	canvas.heightProperty().bind(this.heightProperty());
	
	    	
	}
}