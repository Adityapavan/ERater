package cs421;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


/**
 * This class is the entry point for the UI and the entire application
 * @author aditya
 *
 */
public class UserInterface {
	//Maintains the declarations to all the UI elements
	public static int MODE = 0;
	public static int running = 0;
	public static String folder_path = null;
	public static JPanel mainpanel,selectionpanel,resultpanel,radiopanel,infopanel, filepanel, runpanel; 
	public static JFrame frame;
	public static JTabbedPane tabbedpane;
	public static ButtonGroup radiogroup;
	public static JRadioButton trainradio, testradio;
	public static JTextArea infolabel, resultarea;
	public static JLabel filelabel;
	public static JTextField filetext;
	public static JButton filebutton, proceedbutton, cancelbutton ;
	public static JProgressBar progressbar;
	public static JScrollPane scrollpane;
	ProgramDriver pd;
	public static String stopwordpath;
	
	public static void main(String[] args) {
		//Uses Swing thread to load the UI after loading individual components. This will allow the UI frame to wait 
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new UserInterface();
			}
		});
	}
	
	//Constructor of the class to initialize all the UI elements
	public UserInterface(){
		frame = new JFrame();
		frame.setSize(800, 500);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainpanel = new JPanel();
		selectionpanel = new JPanel();
		selectionpanel.setLayout(new BoxLayout(selectionpanel,BoxLayout.PAGE_AXIS));
		resultpanel = new JPanel();
		resultpanel.setLayout(new BoxLayout(resultpanel,BoxLayout.PAGE_AXIS));
		
		tabbedpane = new JTabbedPane();
		tabbedpane.addTab("Selection", selectionpanel);
		tabbedpane.addTab("Result", resultpanel);
		
		radiopanel = new JPanel();
		radiogroup = new ButtonGroup();
		trainradio = new JRadioButton("Train Mode");
		trainradio.setSelected(true);
		trainradio.addItemListener(new RadioListener());
		testradio = new JRadioButton("Test Mode");
		testradio.addItemListener(new RadioListener());
		radiogroup.add(trainradio);
		radiogroup.add(testradio);
		
		radiopanel.add(trainradio);
		radiopanel.add(testradio);
		selectionpanel.add(radiopanel);
		
		infopanel = new JPanel();
		infopanel.setLayout(new FlowLayout());
		infolabel = new JTextArea(15,60);
		infolabel.setForeground(Color.BLUE);
		infolabel.setBackground(Color.WHITE);
		infolabel.setBorder(new CompoundBorder(
                new LineBorder(Color.GRAY),
                new EmptyBorder(1, 3, 1, 1)));
        infolabel.setLineWrap(true);
        infolabel.setWrapStyleWord(true);
        infolabel.setMargin(new Insets(15, 15, 15, 15));
        infolabel.setEditable(false);
        
        //Initializes the instruction set for the users. By default it is set to training. Users can change later on according to the need
        
        infolabel.setText("You are in TRAIN MODE. \n\n"
        		+ "Selecting train mode lets you train a set of essays. \n\n"
        		+ "To train essays: \n\n 1.Choose the directory containing training documents. "
        		+ "The documents should be in txt format."
        		+ "\n\n 2. The program will extract the essays in text files for training."
        		+ "\n\n 3. Click on proceed button."
        		+ "\n\n 4. The result will be dislayed in the adjacent tab ");
        infopanel.add(infolabel);
		selectionpanel.add(infopanel);
		
		filepanel = new JPanel();
		filepanel.setLayout(new FlowLayout());
		filelabel = new JLabel("Choose the folder containing essays: ");
		filetext = new JTextField(30);
		filebutton = new JButton("Browse");
		filebutton.setActionCommand("Browse");
		filebutton.addActionListener(new BrowseListener());
		filepanel.add(filelabel);
		filepanel.add(filetext);
		filepanel.add(filebutton);
		selectionpanel.add(filepanel);
		
		runpanel = new JPanel();
		runpanel.setLayout(new FlowLayout());
		proceedbutton = new JButton("Proceed");
		proceedbutton.setActionCommand("Proceed");
		proceedbutton.addActionListener(new ProceedListener());
		cancelbutton = new JButton("Cancel");
		cancelbutton.setActionCommand("Cancel");
		cancelbutton.addActionListener(new ProceedListener());
		runpanel.add(proceedbutton);
		runpanel.add(cancelbutton);
		selectionpanel.add(runpanel);
		
		resultarea = new JTextArea(20, 70);
		resultarea.setForeground(Color.BLUE);
		resultarea.setBackground(Color.WHITE);
		resultarea.setBorder(new CompoundBorder(
                new LineBorder(Color.GRAY),
                new EmptyBorder(1, 3, 1, 1)));
		resultarea.setLineWrap(true);
		resultarea.setWrapStyleWord(true);
		resultarea.setMargin(new Insets(15, 15, 15, 15));
		resultarea.setEditable(false);
		
		progressbar = new JProgressBar();
		progressbar.setIndeterminate(false);
		progressbar.setVisible(false);
		scrollpane = new JScrollPane();
		scrollpane.setViewportView(resultarea);
		resultpanel.add(scrollpane);
		resultpanel.add(progressbar);
		mainpanel.setLayout(new BorderLayout());
		mainpanel.add(tabbedpane);
		mainpanel.setBackground(Color.LIGHT_GRAY);
		frame.add(mainpanel);
	}
	
	/**
	 * Action listener for radio button: Train/Test
	 * Based on the item selection, corresponding text is displayed for the benefit of the users and the flag at back end is set
	 * This flag is used by the ProgramDriver class to run train or test operation
	 * @author aditya
	 *
	 */
	class RadioListener implements ItemListener{
		public void itemStateChanged(ItemEvent e) {
			if(e.getItem().equals(trainradio)){
				MODE = 0;
				infolabel.setText("");
				infolabel.setText("You are in TRAIN MODE. \n\n"
		        		+ "Selecting train mode lets you train a set of essays. \n\n"
		        		+ "To train essays: \n\n 1.Choose the directory containing training documents. "
		        		+ "The documents should be in txt format."
		        		+ "\n\n 2. The program will extract the essays in text files for training."
		        		+ "\n\n 3. Click on proceed button."
		        		+ "\n\n 4. The result will be dislayed in the adjacent tab ");
			}
			if(e.getItem().equals(testradio)){
				MODE = 1;
				infolabel.setText("");
				infolabel.setText("You are in TEST MODE. \n\n"
						+ "Testing uses trained models to proceed forward.  "
						+ "A default trained model is available for the program. So, no need to always run Train mode before Test mode. \n\n"
						+ "To test essays: \n\n 1.Choose the directory containing essays."
						+ "\n\n 2. The program will extract the essays in text files."
		        		+ "\n\n 3. Click on proceed button."
						+ "\n\n 4. The result will be displayed in the adjacent tab. ");
			}
			
		}
		
	}
	
	/**
	 * Action listener for the File choosing. Sets the textbox with the path to selected file, updates JFileChooser with file choice
	 * @author aditya
	 *
	 */
	class BrowseListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Browse")){
				JFileChooser filechooser = new JFileChooser("Choose files");
				filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				File workingDirectory = new File(System.getProperty("user.dir"));
				filechooser.setCurrentDirectory(workingDirectory);
				filechooser.setApproveButtonText("Select");
				
				int returnval = filechooser.showSaveDialog(null);
				if(returnval == JFileChooser.APPROVE_OPTION){
					folder_path = filechooser.getSelectedFile().getAbsolutePath();
					filetext.setText(folder_path);
				}
			}
		}
	}
	
	/**
	 * Action listener for the Proceed button. When clicked, an instance of the ProgramDriver is called, which uses the flag that was set 
	 * by the user (radio button - train/test) to run the train or test functions
	 * @author aditya
	 *
	 */
	class ProceedListener implements ActionListener{
		public void actionPerformed(ActionEvent proceedevt) {
					pd = new ProgramDriver(MODE, folder_path);
			if(proceedevt.getActionCommand().equals("Proceed")){
				if(UserInterface.running == 0 && folder_path != null && !folder_path.isEmpty()){
					UserInterface.running = 1;
					pd.run();
				}
			}
			if(proceedevt.getActionCommand().equals("Cancel")){
				if(UserInterface.running == 1){
					UserInterface.running = 0;
					pd.stop();
				}
			}
		}
	}
}
