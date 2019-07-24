package models;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScriptPrompts {
	Map<String, Text>prompt = new HashMap<String, Text>();

	public Map<String,Text> generate() {
		
		//Calibrate
		Text calibrateText = new Text("Before unplugging the DynaPort, make sure the device "
				+ "is placed directly on the laptop’s speaker; make sure the belt is facing "
				+ "up and is NOT between the speaker and the DynaPort.  After unplugging the "
				+ "device, make sure it is completely still and run the calibration test.  "
				+ "Once the calibration test is complete, the device can be removed from the laptop.");
		calibrateText.setWrappingWidth(600);
		calibrateText.setFont(Font.font ("Verdana", 24));
		
		prompt.put("calibrate", calibrateText);
		
		// 8 Foot 1
		Text walk8Text1 = new Text("Now I am going to observe how you walk at "
				+ "your normal pace. This is our walking course. I want you "
				+ "to walk to the other end of the course at your usual speed, "
				+ "just as if you were walking to the store. I want you to walk"
				+ " all the way past this line and then stop. I am going to "
				+ "signal you to begin with a sound. This is what it sounds like. "
				+ "[Press sound button] Did you hear the sound? [Repeat once if "
				+ "necessary] Let me show you what my normal pace would be if I"
				+ "were to walk the course. [Press sound button] After you cross "
				+ "this line, I want you to stop and do not turn around until I "
				+ "tell you to do so. Do you feel it would be safe for you to walk "
				+ "this course with me? Remember to look straight ahead and stay as "
				+ "still as possible until you hear the sound. Ready? [Press begin "
				+ "button, record seconds and steps]…Stop");
		walk8Text1.setWrappingWidth(600);
		walk8Text1.setFont(Font.font ("Verdana", 24));
		
		prompt.put("walk_8ft_1", walk8Text1);
		
		// 8 Foot 2
		Text walk8Text2 = new Text("Now I am going to observe how you walk at "
				+ "your normal pace. This is our walking course. I want you "
				+ "to walk to the other end of the course at your usual speed, "
				+ "just as if you were walking to the store. I want you to walk"
				+ " all the way past this line and then stop. I am going to "
				+ "signal you to begin with a sound. This is what it sounds like. "
				+ "[Press sound button] Did you hear the sound? [Repeat once if "
				+ "necessary] Let me show you what my normal pace would be if I"
				+ "were to walk the course. [Press sound button] After you cross "
				+ "this line, I want you to stop and do not turn around until I "
				+ "tell you to do so. Do you feel it would be safe for you to walk "
				+ "this course with me? Remember to look straight ahead and stay as "
				+ "still as possible until you hear the sound. Ready? [Press begin "
				+ "button, record seconds and steps]…Stop");
		walk8Text2.setWrappingWidth(600);
		walk8Text2.setFont(Font.font ("Verdana", 24));
		
		prompt.put("walk_8ft_2", walk8Text2);
		
		// eo
		Text eoText = new Text("For the next exercise, I would like you "
				+ "to place one foot beside the other in a comfortable "
				+ "position on this line. I just want you to stand when "
				+ "I say begin. [Demonstrate the position] You may use your "
				+ "arms, bend your knees, or move your body to maintain "
				+ "your balance, but try not to move your feet. Do you want "
				+ "me to support your arms while you get into the position? "
				+ "[Extend palms side up for the participant to use for balance "
				+ "if prompted] Try to hold this position until I say Stop. "
				+ "Remember to look straight ahead and stay as still as possible"
				+ " until I say Begin. Ready? Begin…Stop  ");
		eoText.setWrappingWidth(600);
		eoText.setFont(Font.font ("Verdana", 24));
		
		prompt.put("eo", eoText);


		// turn_360_1
		Text turn_360_1Text = new Text("Now I would like you to make a complete "
				+ "turn at your normal walking pace and turn until you reach "
				+ "your starting point, just like this. I am going to signal "
				+ "you to begin with a sound.  Now you try it. Line your feet "
				+ "on top of the tape and begin turning when you hear the sound "
				+ "and turn until you reach your starting point. Remember to look"
				+ " straight ahead and stay as still as possible until you hear"
				+ " the sound. Ready? [Press begin button, record seconds and steps]…Stop");
		turn_360_1Text.setWrappingWidth(600);
		turn_360_1Text.setFont(Font.font ("Verdana", 24));
		
		prompt.put("turn_360_1", turn_360_1Text);
		
		// ll
		Text llText = new Text("For the next exercise, I would like you "
				+ "to raise your right leg off the floor and balance on "
				+ "your left leg, like this. I want you to balance like "
				+ "this when I say begin. [Demonstrate the position] "
				+ "You may use your arms, bend your knees, or move your "
				+ "body to maintain your balance, but try not to move your "
				+ "feet. Do you want me to support your arms while you get "
				+ "into the position? Try to hold the position until I say "
				+ "Stop. Remember to look straight ahead and stay as still "
				+ "as possible until I say begin. Ready? Get into position. Begin…Stop ");
		llText.setWrappingWidth(600);
		llText.setFont(Font.font ("Verdana", 24));
		
		prompt.put("ll", llText);		

		
		// turn_360_2
		Text turn_360_2Text = new Text("Now I would like you to make a complete "
				+ "turn at your normal walking pace and turn until you reach your "
				+ "starting point, just like you did before. Line your feet on top "
				+ "of the tape and begin turning when you hear the sound and turn "
				+ "until you reach your starting point. I want you to turn in the "
				+ "same direction as you did before. Remember to look straight ahead "
				+ "and stay as still as possible until you hear the sound. Ready? "
				+ "[Press begin button, record seconds and steps]…Stop");
		turn_360_2Text.setWrappingWidth(600);
		turn_360_2Text.setFont(Font.font ("Verdana", 24));
		
		prompt.put("turn_360_2", turn_360_2Text);		

		// turn_360_2
		Text ecText = new Text("For the next exercise, I would like you to place one "
				+ "foot beside the other in a comfortable position on this line, but "
				+ "this time I would like you to close your eyes. I want you to just "
				+ "stand when I say begin. You may use your arms, bend your knees, or "
				+ "move your body to maintain your balance, but try not to move your "
				+ "feet. Do you want me to support your arms while you get into the "
				+ "position? Try to hold this position until I say Stop. Remember to "
				+ "look straight ahead and stay as still as possible until I say Begin. "
				+ "Ready? Close your eyes. Begin…Stop");
		ecText.setWrappingWidth(600);
		ecText.setFont(Font.font ("Verdana", 24));
		
		prompt.put("ec", ecText);
		
		// tug_1
		Text tug_1Text = new Text("This next exercise is called the Get Up and Go. "
				+ "For this performance, I would like you to walk this course again,"
				+ "but this time you are going to be seated in this chair with your "
				+ "back rested against the back of the chair. Then when you hear the "
				+ "sound you are going to stand up from this chair, walk to the other "
				+ "end of the course at your normal pace past the line, turn around, "
				+ "walk back to the chair and sit down. Remember to look straight ahead"
				+ " and stay as still as possible until you hear the sound. Ready? "
				+ "[Press begin button]...Stop");
		tug_1Text.setWrappingWidth(600);
		tug_1Text.setFont(Font.font ("Verdana", 24));
		
		prompt.put("tug_1", tug_1Text);
		
		// rl
		Text rlText = new Text("For this next exercise, "
				+ "I would like you to raise your left leg "
				+ "off the floor and balance on your right leg, "
				+ "like this. I want you to balance like this "
				+ "when I say begin. [Demonstrate the position] "
				+ "You may use your arms, bend your knees, or "
				+ "move your body to maintain your balance, but "
				+ "try not to move your feet. Do you want me to "
				+ "support your arms while you get into the position?"
				+ " Try to hold the position until I say Stop. "
				+ "Remember to look straight ahead and stay as still "
				+ "as possible until I say begin. Ready? Get into position. Begin…Stop");
		rlText.setWrappingWidth(600);
		rlText.setFont(Font.font ("Verdana", 24));

		prompt.put("rl", rlText);
		
		// tug_2
		Text tug_2Text = new Text("Now I am going to have you repeat the Get Up and Go "
				+ "Task which you performed before. This is the performance where you "
				+ "begin seated in this chair with your back rested against the back "
				+ "of the chair. Then when you hear the sound you are going to stand "
				+ "up from this chair, walk to the other end of the course at your "
				+ "normal pace past the line, turn around, walk back to the chair "
				+ "and sit down. Remember to look straight ahead and stay as still "
				+ "as possible until you hear the sound. Ready? [Press begin button]...Stop");
		tug_2Text.setWrappingWidth(600);
		tug_2Text.setFont(Font.font ("Verdana", 24));

		prompt.put("tug_2", tug_2Text);		
		
		// tan
		Text tanText = new Text("Now I want you to walk this course again but this time, "
				+ "when you hear the sound, I want you to place one foot in front of the "
				+ "other with the heel touching the toe of the other foot like this, "
				+ "alternating feet. If you feel you are losing your balance, step out "
				+ "like this and then resume walking again. I want you to continue this "
				+ "walk all the way past this line before you stop. Remember after you"
				+ " cross the line, I want you to stop and do not turn around until I "
				+ "tell you to do so. Do you feel it would be safe for you to walk this "
				+ "course with me? Remember to stay as still as possible until you hear "
				+ "the sound. Ready? [Press begin button, record side steps]…Stop  ");
		tanText.setWrappingWidth(600);
		tanText.setFont(Font.font ("Verdana", 24));

		prompt.put("tan", tanText);		
		
		
		// walk_32ft
		Text walk_32ftText = new Text("I would like you to walk this course again, normally. "
				+ "I want you to walk to the other end of the course at your usual speed,"
				+ " just as if you were walking to the store. This time, however, when "
				+ "you hear the sound, I want you to walk all the way past this line, "
				+ "turn around and walk back to the starting line. When you pass the "
				+ "starting line I want you to turn around and walk to the other end "
				+ "of the course, turn around and walk back. Remember after you cross "
				+ "the line, I want you to stop and do not turn around until I tell "
				+ "you to do so. Do you feel it would be safe for you to walk this"
				+ " course with me? Remember to look straight ahead and stay as still "
				+ "as possible until you hear the sound. Ready? [Press begin button]…Stop");
		walk_32ftText.setWrappingWidth(600);
		walk_32ftText.setFont(Font.font ("Verdana", 24));

		prompt.put("walk_32ft", walk_32ftText);		
		
		
		// tug_2
		Text toeText = new Text("For the next exercise, I want you to raise your "
				+ "heels off the floor and balance on the balls of your feet, "
				+ "like this. You may use your arms, bend your knees, or move "
				+ "your body to maintain your balance, but try not to move your"
				+ " feet. Do you want me to support your arms while you get into"
				+ " the position? Try to hold the position until I say Stop. "
				+ "Remember to look straight ahead and stay as still as possible"
				+ " until I say Begin. Ready? Get into position. Begin…Stop");
		toeText.setWrappingWidth(600);
		toeText.setFont(Font.font ("Verdana", 24));

		prompt.put("toe", toeText);		
		
		
		// cog_1
		Text cog_1Text = new Text("Next, we are going to examine some subtraction. For example, if we started with the number 50 and you took away 3 you would have 47. Then if you continue and take away 3 from 47 you would have 44. Then taking away 3 again would leave 41. Some people find this very difficult and some do not. If you have difficulty, just keep going and don’t be concerned if you think you have made a mistake.  \r\n" + 
				"\r\n" + 
				"Practice Trial: Please start with the number 100 and take away 3. Continue to take away 3 from what is left until I tell you to stop. \r\n" + 
				" \r\n" + 
				"Test Trial: Good. Please start again with the number 100 and take away 3. Continue to take away 3 from what is left until I tell you to stop. Ready? Begin…Stop\r\n" + 
				"\\r\\n"
				+ "Now I am going to have you repeat the Get Up "
				+ "and Go Task which you performed before. However, now you are "
				+ "going to count backwards by 3 while you are performing this "
				+ "task. Please start the count backwards, out loud so I can "
				+ "hear you, starting from 100 by 3 and continue counting while "
				+ "you perform this task. When you hear the sound, you are going "
				+ "to stand up from this chair, walk to the other end of the course"
				+ " at your normal pace past the line, turn around, walk back to "
				+ "the chair and sit down. Continue to count while you are performing"
				+ " this task. So, this is what it would look like if I performed the "
				+ "task, but I am going to count backwards by 3 starting from 50. "
				+ "Remember after you sit back down in the chair, do not get up until "
				+ "I tell you to do so. Now you try it. Remember to start the count backwards "
				+ "out loud so I can hear you starting from 100 by 3 and continue counting "
				+ "while you perform this task. Remember to look straight ahead and stay as "
				+ "still as possible until you hear the sound. Ready? [Press begin button]…Stop");
		cog_1Text.setWrappingWidth(600);
		cog_1Text.setFont(Font.font ("Verdana", 24));

		prompt.put("cog_1", cog_1Text);		
		
		
		// cog_2
		Text cog_2Text = new Text("Now I want you to do that again. Please begin the count"
				+ " backwards, out loud so I can hear you, starting from 100 by 3 and "
				+ "continue counting while you perform this task. Remember after you "
				+ "sit back down in the chair, do not get up until I tell you to do so."
				+ " Remember to look straight ahead and stay as still as possible until"
				+ " you hear the sound. Ready? [Press begin button]…Stop");
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

