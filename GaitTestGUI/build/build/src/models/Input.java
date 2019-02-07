/**
 * Input for remote use
 * 
 * @author Tim Truty
 *
 */

package models;

import java.util.BitSet;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Input{

    /**
     * Bitset which registers if any {@link KeyCode} keeps being pressed or if it is released.
     */
    private BitSet keyboardBitSet = new BitSet();

    // -------------------------------------------------
    // default key codes
    // will vary when you let the user customize the key codes or when you add support for a 2nd player
    // -------------------------------------------------

    private KeyCode pageUpKey = KeyCode.PAGE_UP;
    private KeyCode pageDownKey = KeyCode.PAGE_DOWN;
    private KeyCode periodKey = KeyCode.PERIOD;

    
    
    private boolean pageUpPressed;
    private boolean pageDownPressed;
    private boolean periodPressed;
    private boolean slideShowPressed;

	Scene scene;
    
    boolean pressed;
    int count = 0;

    public Input( Scene scene) {
        this.scene = scene;
    }

    public void addListeners() {

        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);

    }

    public void removeListeners() {

        scene.removeEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
        scene.removeEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);

    }

    /**
     * "Key Pressed" handler for all input events: register pressed key in the bitset
     */
    private EventHandler<KeyEvent> keyPressedEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {

        	
            // register key down
        	keyboardBitSet.set(event.getCode().ordinal(), true);
        	pressed = true;
            
            count++;
            //System.out.println("Pressed: " + (event.getCode().ordinal()));
        }
    };

    /**
     * "Key Released" handler for all input events: unregister released key in the bitset
     */
    private EventHandler<KeyEvent> keyReleasedEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {

            // register key up
            keyboardBitSet.set (event.getCode().ordinal(), false);
            pressed = false;
            //System.out.println("Relseased:  " + event.getCode().ordinal());
            switch (event.getCode().ordinal())
            {
            	case 12:
            		//System.out.println("Page UP!!!!!");
            		setPageUpPressed(true);
            		break;
            		
            	case 13:
            		//System.out.println("Page Down!!!!!");
            		setPageDownPressed(true);
            		break;
            	case 22:
            		//System.out.println("PERIOD!!!!!");
            		setPeriodPressed(true);
            		break;
            	case 10:
            		setSlideShowPressed(true);
            		break;
            	case 88:
            		setSlideShowPressed(true);
            		break;
            } 
            //setPageUpPressed(false);
            //setPageDownPressed(false);
            //setPeriodPressed(false);
            count = 0;
           

        }
    };


    // -------------------------------------------------
    // Evaluate bitset of pressed keys and return the player input.
    // If direction and its opposite direction are pressed simultaneously, then the direction isn't handled.
    // -------------------------------------------------

    public boolean isPageUp() {
    	return keyboardBitSet.get( pageUpKey.ordinal());
    	
    }

    public boolean isPageDown() {
    	return keyboardBitSet.get( pageDownKey.ordinal());
    }

    public boolean isPeriod() {
    	return keyboardBitSet.get( periodKey.ordinal());  
    }
    
    public boolean isPressed() {
    	return pressed;
    }

	public boolean isPageUpPressed() {
		return pageUpPressed;
	}

	public void setPageUpPressed(boolean pageUpPressed) {
		this.pageUpPressed = pageUpPressed;
	}

	public boolean isPageDownPressed() {
		return pageDownPressed;
	}

	public void setPageDownPressed(boolean pageDownPressed) {
		this.pageDownPressed = pageDownPressed;
	}

	public boolean isPeriodPressed() {
		return periodPressed;
	}

	public void setPeriodPressed(boolean periodPressed) {
		this.periodPressed = periodPressed;
	}
    
	public boolean isSlideShowPressed() {
		return slideShowPressed;
	}

	public void setSlideShowPressed(boolean slideShowPressed) {
		this.slideShowPressed = slideShowPressed;
	}
    
}