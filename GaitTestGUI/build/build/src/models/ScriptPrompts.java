package models;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScriptPrompts {
	Map<String, Text>prompt = new HashMap<String, Text>();

	public Map<String,Text> generate() {
		
		//Calibrate
		Text calibrateText = new Text("CALIBRATE\r\n"
				+ "Before unplugging the DynaPort, make sure the device "
				+ "is placed directly on the laptop’s speaker; make sure the belt is facing "
				+ "up and is NOT between the speaker and the DynaPort.  After unplugging the "
				+ "device, make sure it is completely still and run the calibration test.  "
				+ "Once the calibration test is complete, the device can be removed from the laptop.");
		calibrateText.setWrappingWidth(600);
		calibrateText.setFont(Font.font ("Verdana", 24));
		
		prompt.put("calibrate", calibrateText);
		
		// 8 Foot 1
		Text walk8Text1 = new Text("8 FOOT WALK 1\r\n"
				+ "“Now I am going to observe how you walk at your normal pace. "
				+ "This is our walking course. I want you to walk to the other end of the course "
				+ "at your usual speed, just as if you were walking to the store.  I want you to "
				+ "begin when you hear the sound [or “when I say ‘begin’” in the case that the "
				+ "sound is not being used] and walk all the way past this line and then stop. "
				+ "Let me show you what my normal pace would be if I were to walk the course.” "
				+ "[Press sound button and demonstrate] \r\n" + 
				"“After you cross this line, I want you to stop and do not turn around until I "
				+ "tell you to do so. Do you feel it would be safe for you to walk this course "
				+ "with me? Remember to look straight ahead and stay as still as possible until"
				+ " you hear the sound. Ready?” [Press begin button or say “Begin”], \r\n" + 
				"“Stop” Record seconds  [Record Seconds and Steps]\r\n" + 
				"");
		walk8Text1.setWrappingWidth(600);
		walk8Text1.setFont(Font.font ("Verdana", 24));
		
		prompt.put("walk_8ft_1", walk8Text1);
		
		// 8 Foot 2
		Text walk8Text2 = new Text("8 FOOT WALK 2\r\n"
				+ "“Now I want you to walk this course again at your normal pace."
				+ " I want you to begin when you hear the sound. Remember I want you to walk all "
				+ "the way past this line and then stop. Remember after you cross the line, I want"
				+ " you to stop and do not turn around until I tell you to do so. Remember to look"
				+ " straight ahead and stay as still as possible until you hear the sound. Ready?”"
				+ " [Press begin button], \r\n" + 
				" “Stop” [Record Seconds and Steps]\r\n" + 
				"");
		walk8Text2.setWrappingWidth(600);
		walk8Text2.setFont(Font.font ("Verdana", 24));
		
		prompt.put("walk_8ft_2", walk8Text2);
		
		// eo
		Text eoText = new Text("EYE OPEN STAND\r\n"
				+ "“For the next exercise, I would like you to place one foot beside"
				+ " the other in a comfortable position on this line. When you hear the sound, I "
				+ "just want you to continue standing.” [ Demonstrate the position] \r\n" + 
				"“You may use your arms, bend your knees, or move your body to maintain your "
				+ "balance, but try not to move your feet. Do you want me to support your arms"
				+ " while you get into the position?”\r\n" + 
				" [Extend palms side up for the participant to use for balance if prompted] \r\n" + 
				"“Remember to look straight ahead and stay as still as possible. When you hear"
				+ " the sound, try to hold this position until I say Stop. Ready?”"
				+ " [Press begin button] “Stop”  [20-Second Maximum]\r\n" + 
				"*Note: If less than 10 seconds , mark as unable.  No aid is allowed.\r\n" + 
				"");
		eoText.setWrappingWidth(600);
		eoText.setFont(Font.font ("Verdana", 24));
		
		prompt.put("eo", eoText);


		// turn_360_1
		Text turn_360_1Text = new Text("360 TURN 1\r\n"
				+ "“Now I would like you to make a complete turn at your "
				+ "normal walking pace and turn until you reach your starting point, "
				+ "just like this.” [Demonstrate]  \r\n" + 
				"“I want you to begin when you hear the sound.  Now you try it. Line "
				+ "your feet on top of the tape and begin turning when you hear the sound "
				+ "and turn until you reach your starting point. Remember to look straight "
				+ "ahead and stay as still as possible until you hear the sound. Ready?” [Press begin button]\r\n" + 
				" “Stop” [Record Seconds and Steps]\r\n" + 
				"");
		turn_360_1Text.setWrappingWidth(600);
		turn_360_1Text.setFont(Font.font ("Verdana", 24));
		
		prompt.put("turn_360_1", turn_360_1Text);
		
		// ll
		Text llText = new Text("LEFT LEG STAND\r\n"
				+ "“For the next exercise, I would like you to raise your "
				+ "right leg off the floor and balance on your left leg, like this. I "
				+ "want you to get into position and when you hear the sound, just continue "
				+ "standing on your left leg.” [Demonstrate the position] \r\n" + 
				"“You may use your arms, bend your knees, or move your body to maintain "
				+ "your balance, but try not to move your feet. Do you want me to support"
				+ " your arms while you get into the position?” [Extend hands palm side up"
				+ " for the participant to use for balance if prompted]   \r\n" + 
				"“Remember to look straight ahead and stay as still as possible. When you "
				+ "hear the sound, try to hold this position until I say Stop.  Ready?” "
				+ "[Allow P to get into position and then press begin button] \r\n" + 
				"“Stop” [Record for 10 seconds]\r\n" + 
				"*Note: If time <1second, record as unable.  No aid is allowed.\r\n" + 
				"");
		llText.setWrappingWidth(600);
		llText.setFont(Font.font ("Verdana", 24));
		
		prompt.put("ll", llText);		

		
		// turn_360_2
		Text turn_360_2Text = new Text("360 TURN 2\r\n"
				+ "“Now I would like you to make a complete turn "
				+ "at your normal walking pace and turn until you reach your starting "
				+ "point, just like you did before. Line your feet on top of the tape "
				+ "and begin turning when you hear the sound and turn until you reach "
				+ "your starting point. I want you to turn in the same direction as you "
				+ "did before.” [Remind P of direction they should turn]\r\n" + 
				"“Remember to look straight ahead and stay as still as possible until you "
				+ "hear the sound. Ready?” [Press begin button]\r\n" + 
				"“Stop” [Record Seconds and Steps]\r\n" + 
				"*Note: P should turn in the same direction for both trials\r\n" + 
				"");
		turn_360_2Text.setWrappingWidth(600);
		turn_360_2Text.setFont(Font.font ("Verdana", 24));
		
		prompt.put("turn_360_2", turn_360_2Text);		

		// turn_360_2
		Text ecText = new Text("EYES CLOSED STAND\r\n"
				+ "“For the next exercise, I would like you to place one foot "
				+ "beside the other in a comfortable position on this line, but this time I"
				+ " would like you to close your eyes. I just want you to continue standing"
				+ " with your eyes closed when you hear the sound. You may use your arms, bend"
				+ " your knees, or move your body to maintain your balance, but try not to move"
				+ " your feet. Do you want me to support your arms while you get into the position?” "
				+ "[Extend palms side up for the participant to use for balance if prompted] \r\n" + 
				"“Remember to look straight ahead and stay as still as possible. When you hear the "
				+ "sound, try to hold this position until I say Stop.  Ready? Close your eyes” "
				+ "[Check to make sure P’s eyes are closed, then press begin button]\r\n" + 
				"“Stop” [Record for 20 seconds]\r\n" + 
				"*Note: If time <20 sec, record as unable.  No aid is allowed.\r\n" + 
				"");
		ecText.setWrappingWidth(600);
		ecText.setFont(Font.font ("Verdana", 24));
		
		prompt.put("ec", ecText);
		
		// tug_1
		Text tug_1Text = new Text("GET UP AND GO 1\r\n"
				+ "“This next exercise is called the Get Up and Go. "
				+ "For this performance, I would like you to walk this course again, "
				+ "but this time you are going to be seated in this chair with your "
				+ "back rested against the back of the chair.  When you hear the sound "
				+ "you are going to stand up from this chair , walk to the other end of "
				+ "the course at your normal pace past the line, turn around, walk back "
				+ "to the chair and sit down.” [Demonstrate] \r\n" + 
				"“Remember to look straight ahead and stay as still as possible until "
				+ "you hear the sound. Ready?” [Press begin button]\r\n" + 
				"“Stop” [Press button on remote to end time once P’s back touches the back of the chair] \r\n" + 
				"*Note: Record protocol deviation if the only available chair is atypical. \r\n" + 
				"");
		tug_1Text.setWrappingWidth(600);
		tug_1Text.setFont(Font.font ("Verdana", 24));
		
		prompt.put("tug_1", tug_1Text);
		
		// rl
		Text rlText = new Text("RIGHT LEG STAND\r\n"
				+ "“For this next exercise, I would like you to raise your "
				+ "left leg off the floor and balance on your right leg, like this. I "
				+ "want you to get into position and when you hear the sound, just continue "
				+ "standing on your right leg.”  [Demonstrate the position] \r\n" + 
				"“You may use your arms, bend your knees, or move your body to maintain your "
				+ "balance, but try not to move your feet. Do you want me to support your arms "
				+ "while you get into the position?” [Extend palms side up for the participant "
				+ "to use for balance if prompted] \r\n" + 
				"“When you hear the sound, try to hold this position until I say Stop. Ready?” "
				+ "[Wait for P to get into position, then press begin button] \r\n" + 
				"“Stop” [Record for 10 seconds]\r\n" + 
				"*Note: If time <1second, record as unable.  No aid is allowed.\r\n" + 
				"");
		rlText.setWrappingWidth(600);
		rlText.setFont(Font.font ("Verdana", 24));

		prompt.put("rl", rlText);
		
		// tug_2
		Text tug_2Text = new Text("GET UP AND GO 2\r\n"
				+ "“Now I am going to have you repeat the Get Up and "
				+ "Go Task which you performed before. This is the performance where you "
				+ "begin seated in this chair with your back rested against the back of the "
				+ "chair.  When you hear the sound you are going to stand up from this chair, "
				+ "walk to the other end of the course at your normal pace past the line, turn "
				+ "around, walk back to the chair and sit down. Remember to look straight ahead "
				+ "and stay as still as possible until you hear the sound. Ready?” [Press begin button]\r\n" + 
				"“Stop” [Press button on remote to end time once P’s back touches the back of the chair]\r\n" + 
				"*Note: Record protocol deviation if the only available chair is atypical. \r\n" + 
				"");
		tug_2Text.setWrappingWidth(600);
		tug_2Text.setFont(Font.font ("Verdana", 24));

		prompt.put("tug_2", tug_2Text);		
		
		// tan
		Text tanText = new Text("TANDEM WALK\r\n"
				+ "“Now I want you to walk this course again but this time, "
				+ "when you hear the sound, I want you to place one foot in front of the "
				+ "other with the heel touching the toe of the other foot like this, alternating feet. [Demonstrate]\r\n" + 
				"“If you feel you are losing your balance, step out like this and then resume "
				+ "walking again. I want you to continue this walk all the way past this line "
				+ "before you stop. Remember after you cross the line, I want you to stop and "
				+ "do not turn around until I tell you to do so. Do you feel it would be safe "
				+ "for you to walk this course with me? Remember to stay as still as possible "
				+ "until you hear the sound. Ready?” [Press begin button]\r\n" + 
				"“Stop” [Record side steps] \r\n" + 
				"*Note: No aid is allowed. If participant is unable to accurately complete "
				+ "the tandem walk, code as UNABLE. For example, code as unable if the participants "
				+ "cannot touch their heel to their toe or if they keep touching the wall.\r\n" + 
				"");
		tanText.setWrappingWidth(600);
		tanText.setFont(Font.font ("Verdana", 24));

		prompt.put("tan", tanText);		
		
		
		// walk_32ft
		Text walk_32ftText = new Text("32 FOOT WALK\r\n"
				+ "“I would like you to walk this course again, normally. "
				+ "I want you to walk to the other end of the course at your usual speed, just "
				+ "as if you were walking to the store. This time, however, when you hear the sound, "
				+ "I want you to walk all the way past this line, turn around and walk back to the "
				+ "starting line. When you pass the starting line I want you to turn around and walk "
				+ "to the other end of the course, turn around and walk back. Remember after you cross "
				+ "the line, I want you to stop and do not turn around until I tell you to do so.” "
				+ "[Demonstrate] \r\n" + 
				"“Do you feel it would be safe for you to walk this course with me? Remember to look "
				+ "straight ahead and stay as still as possible until you hear the sound. Ready?” "
				+ "[Press begin button]… “Stop”\r\n" + 
				"");
		walk_32ftText.setWrappingWidth(600);
		walk_32ftText.setFont(Font.font ("Verdana", 24));

		prompt.put("walk_32ft", walk_32ftText);		
		
		
		// tug_2
		Text toeText = new Text("TOE STAND\r\n"
				+ "“For the next exercise, I want you to raise your heels "
				+ "off the floor and balance on the balls of your feet, like this.” "
				+ "[Demonstrate]\r\n" + 
				"You may use your arms, bend your knees, or move your body to maintain "
				+ "your balance, but try not to move your feet. Do you want me to support "
				+ "your arms while you get into the position?” [Extend palms side up for "
				+ "the participant to use for balance if prompted]\r\n" + 
				"“When you hear the sound, try to hold this position until I say Stop. "
				+ "Ready?” [Wait for P to get into position, then press begin button] \r\n" + 
				"“Stop” [Record for 10 seconds]\r\n" + 
				"*Note: If time <1second, record as unable.  No aid is allowed.\r\n" + 
				"");
		toeText.setWrappingWidth(600);
		toeText.setFont(Font.font ("Verdana", 24));

		prompt.put("toe", toeText);		
		
		
		// cog_1
		Text cog_1Text = new Text("COGNITIVE TUG 1.\r\n"
				+ "“Now I am going to have you repeat the Get Up and Go Task "
				+ "which you performed before. However, now you are going to count backwards "
				+ "by 3 while you are performing this task. Please start the count backwards, "
				+ "out loud so I can hear you, starting from 100 by 3 and continue counting "
				+ "while you perform this task. When you hear the sound, you are going to "
				+ "begin counting and stand up from this chair, walk to the other end of the "
				+ "course at your normal pace past the line, turn around, walk back to the "
				+ "chair and sit down. Continue to count while you are performing this task. "
				+ "So, this is what it would look like if I performed the task, but I am going "
				+ "to count backwards by 3 starting from 50” [Demonstrate starting with 50 and "
				+ "be sure to CLEARLY DEMONSTRATE STARTING COUNTING AS YOU BEGIN MOVING TO STAND UP]. \r\n" + 
				"“Remember after you sit back down in the chair, do not get up until I tell you to "
				+ "do so. Now you try it. Remember to start the count backwards out loud so I can "
				+ "hear you starting from 100 by 3 and continue counting while you perform this "
				+ "task. Remember to look straight ahead and stay as still as possible until you "
				+ "hear the sound. When you hear the sound, begin counting and moving at the same "
				+ "time, and continue counting throughout the task. Ready? [Press begin button]\r\n" + 
				"“Stop” [Record ALL numbers heard during TUG]\r\n" + 
				"*Note: Record protocol deviation if the only available chair is atypical. "
				+ "Record errors as well as spontaneous corrections, and document as a comment if P trails off counting during walk.\r\n" + 
				"");
		cog_1Text.setWrappingWidth(600);
		cog_1Text.setFont(Font.font ("Verdana", 24));

		prompt.put("cog_1", cog_1Text);		
		
		
		// cog_2
		Text cog_2Text = new Text("COGNITIVE TUG 2\r\n"
				+ "“Now I want you to do that again. Please begin the count backwards, "
				+ "out loud so I can hear you, starting from 100 by 3 and continue counting while you "
				+ "perform this task. Remember after you sit back down in the chair, do not get up until "
				+ "I tell you to do so. Remember to look straight ahead and stay as still as possible "
				+ "until you hear the sound. When you hear the sound, begin counting and moving at the "
				+ "same time, and continue counting throughout the task. Ready? [Press begin button]\r\n" + 
				"“Stop” [Record ALL numbers heard during TUG]\r\n" + 
				"*Note: Record errors as well as spontaneous corrections.  "
				+ "Document as a comment if P trails off/stops counting during TUG \r\n" + 
				"");
		cog_2Text.setWrappingWidth(600);
		cog_2Text.setFont(Font.font ("Verdana", 24));

		prompt.put("cog_2", cog_2Text);	
		
		// Finished Test
		Text finishedText = new Text("If testing is complete Press the STOP Button and "
				+ "Remeber to plug the device in and follow promts on screen");
		cog_2Text.setWrappingWidth(600);
		cog_2Text.setFont(Font.font ("Verdana", 24));

		prompt.put("finish", finishedText);		
		
		
		return prompt;
	}
}

