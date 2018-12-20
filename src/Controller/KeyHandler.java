package Controller;

import java.awt.event.*;

import Model.Enemy;
import Model.Screen;
import View.Frame;

import java.awt.*;
/**
 * @author Felix
 * @since 2015/11/10
 * 
 * Will give the mouse location precision to interact with the store and objects
 */
public class KeyHandler implements MouseMotionListener, MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//when a click is registered get the button
		Screen.store.click(e.getButton());
		for(Enemy foe : Screen.enemys){
			foe.click(e.getButton());
		}
		//Screen.click(e.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//want to get the perfect mouse precision
		//e.getX/Y count the borders so it is not correct. We add/subtract the error
		Screen.mse = new Point((e.getX()) - ((Frame.size.width - Screen.sWidth)/2), 
				(e.getY()) - ((Frame.size.height - (Screen.sHeight)) - (Frame.size.width - Screen.sWidth)/2));

		//System.out.println("Dragged: "+Screen.mse);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//want to get the perfect mouse precision
		//e.getX/Y count the borders so it is not correct. We add/subtract the error
		Screen.mse = new Point((e.getX()) - ((Frame.size.width - Screen.sWidth)/2), 
				(e.getY()) - ((Frame.size.height - (Screen.sHeight)) - (Frame.size.width - Screen.sWidth)/2));
		
		//System.out.println("Moved :"+Screen.mse);
	}

}
