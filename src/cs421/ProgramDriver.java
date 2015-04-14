package cs421;
import java.io.IOException;

import Train.Trainer;

/**
 * This is the entry point to back end processing. The program Driver class implements runnable thread,
 * which will run in the background to run operations like Train/ Test
 * @author aditya
 *
 */
public class ProgramDriver implements Runnable{
	int mode;
	String folder_path;
	public ProgramDriver(int mode, String folder_path){
		this.mode = mode;
		this.folder_path = folder_path;
	}
	
	/**
	 * When the users select Train mode, the training function is called by the ProgramDriver class 
	 * @param folder_path : path to the folder containing training documents in text format
	 */
	public void Train(){
		UserInterface.resultarea.append("You chose to train the documents..\n");
		UserInterface.resultarea.append("Training the documents in the folder: " + this.folder_path + "\n");
		Thread trainthread = new Thread(new Runnable(){
			public void run() {
				UserInterface.tabbedpane.setSelectedIndex(1);
				UserInterface.progressbar.setIndeterminate(true);
				UserInterface.progressbar.setVisible(true);
				UserInterface.resultarea.append("Loading data..\n");
				UserInterface.resultarea.append("Parsing Raw Data..\n");
				UserInterface.resultarea.append("Running Training modules..\n");
				
				try {
					Trainer tr = new Trainer(folder_path);
					tr.getScores();
				} catch (IOException e) {
					e.printStackTrace();
				}
				catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				UserInterface.progressbar.setIndeterminate(false);
				UserInterface.resultarea.append("Finished training..\n");
			}
		});
		trainthread.start();	
	}
	
	/**
	 * When the users select test mode on the user interface, the testing function is called by the ProgramDriver.
	 * @param folder_path : folder path containing testing documents in the text format
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void Test() throws IOException, InterruptedException{
		UserInterface.resultarea.append("You chose to test the documents..\n");
		UserInterface.resultarea.append("Testing the documents in the folder: " + this.folder_path + "\n");
		
		Thread testthread = new Thread(new Runnable(){
			public void run(){
				UserInterface.tabbedpane.setSelectedIndex(1);
				UserInterface.progressbar.setIndeterminate(true);
				UserInterface.progressbar.setVisible(true);
				
				//changes done here
				
				UserInterface.resultarea.append("Finished Testing..\n");
				UserInterface.progressbar.setIndeterminate(false);
			}
		});
		testthread.start();
	}
	
	/**
	 * When users wish to stop the operations, checks for executing java processes and kills them,
	 * Exits with return 0
	 */
	public void stop(){
		UserInterface.tabbedpane.setSelectedIndex(1);
		UserInterface.progressbar.setIndeterminate(false);
		System.exit(0);
	}
	
	/**
	 *  The entry point for the ProgramDriver. This thread runs the training or testing thread in parallel to UI
	 */
	public void run() {
		if(mode == 0){
			Train();
		}
		if(mode == 1){
			try {
				Test();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
