import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import java.io.*;

public class reminder
{
	public static String task;
	private static String h,m;

	public static void generateScript(String task, String hour, String minute)
	{	
		//generate vbs script
		try
		{
		FileWriter fileWriter = new FileWriter(task.replaceAll("\\s","")+"msg.vbs");
		// fileWriter.write("@echo off \n");
		// fileWriter.write("mshta vbscript:msgbox(\"" + task + "\",64,\"Reminder\")(window.close)\n");
		fileWriter.write("msgbox\"" + task + "\",vbSystemModal,\"Reminder\"");
		fileWriter.close();
		}
		catch(Exception e)
		{
			System.out.println("Error");
		}

		//get the absolute paht of .vbs file
		File vbs = new File(task.replaceAll("\\s","")+"msg.vbs");
		String path = vbs.getAbsolutePath();
		// System.out.println(path);

		//generate cmd script
		String cmdTask = task.replaceAll("\\s","");
		try
		{
		FileWriter fileWriter = new FileWriter(cmdTask+".bat");
		fileWriter.write("@echo off \n");
		fileWriter.write("schtasks /create /tn " + cmdTask + " /tr " + path 
			+ " /sc once " + "/st " + hour + ":" + minute + " /f");
		fileWriter.close();
		}
		catch(Exception e)
		{
			System.out.println("Error");
		}
		File cmd = new File(cmdTask+".bat");
		String path2 = cmd.getAbsolutePath();

		//execute cmd script
		String command = "cmd.exe /c start /b " + cmdTask + ".bat";
		try
		{
		Process process = Runtime.getRuntime().exec(command);
		// System.out.println("test");
		}
		catch(Exception e)
		{
			System.out.println("Error");
		}


	}

	public static void main(String[] args)
	{
		// String task;
		// String s = "t e s t";
		// System.out.println(s.replaceAll("\\s",""));
		//set the frame
		JFrame frame = new JFrame("Reminder");
		frame.setBounds(300, 200, 600, 200);
		frame.setLayout(new FlowLayout());

		//set elements
		JLabel lb = new JLabel("Task");
		JLabel time = new JLabel("Time");
		JLabel time2 = new JLabel(":");
		JLabel power = new JLabel("Please make sure your machine is connected to power");
		JTextField tf = new JTextField(20);
		JTextField hr = new JTextField(5);
		JTextField min = new JTextField(5);
		JButton b = new JButton("Confirm");
		JButton e = new JButton("Exit");


		frame.add(lb);
		frame.add(tf);
		frame.add(time);
		frame.add(hr);
		frame.add(time2);
		frame.add(min);
		frame.add(b);
		frame.add(power);
		frame.add(e);


		frame.setVisible(true);

		/*---------------------all listeners------------------*/

		//close window
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});


		//button action
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				task = tf.getText();
				h = hr.getText();
				m = min.getText();
				tf.setText("");
				hr.setText("");
				min.setText("");
				generateScript(task, h, m);
			}
		});

		e.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});




	}
}