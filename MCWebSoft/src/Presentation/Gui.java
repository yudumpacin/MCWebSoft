
package Presentation;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;


import java.awt.Color;

import java.awt.Dimension;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;

import  Application.*;
import Data.*;

public class Gui {

	private JFrame frame;
	private JTextArea smvFile;
    private JButton smvButton;
	private JButton runSMVbutton;
	private JButton lpropertyButton;
	private JButton apropertyButton;
	private JButton createTestCaseButton;
	private JButton runTestCaseButton;
	private JButton browseButton;
	private JButton defineCondition;
	private JButton chooseFSMType;
	private JButton addnewLinksButton;
	private JList listPropertyFrom;
	private JList listofLinks;
	private JList listPropertyTo;
	private NuSMVExecuter nusmv;
	private CreateIntFormat intformat;
	private IntFormatToNuSMV f ;
	private CounterexampleToScript sc;
	private String filePath;
	JTextField textUsernameid;
    JTextField textUsername; 
    JTextField passwordid;
	JTextField passwordname;
	JTextArea consoleOutput = new JTextArea(9,30);
    Boolean isCrawljax=false;                                                                                                                                                                                          
    Boolean isWebMole=false;
    JTextArea brokenLinkPropertyTA;
    JPanel panel; 
    boolean saveisClicked=false;
    ArrayList<Integer> selectedLogOutState;
    ArrayList<Integer> selectedLogOutElement;
    JFrame defineLOWindow = new JFrame();
    JFrame TextBoxFrame;
    JScrollPane scrollPane;
    JTextArea textForProperties;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public static void main(String[] args) {
		
		

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	});
		
		
	}

	
	public Gui() throws IOException {
		initialize();
	
		
	}

	private void initialize() {
		frame = new JFrame("MCWebSoft");
		frame.setBounds(20, 20, 700, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		
		JPanel panelButton = new JPanel();
		panelButton.setLayout(new BoxLayout (panelButton, BoxLayout.Y_AXIS));
		JPanel panelButton2 = new JPanel();
	
	
		smvFile = new  JTextArea(10,10);
		scrollPane = new JScrollPane(smvFile);
		
		scrollPane.setPreferredSize(new Dimension(50, 50));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		smvFile.setWrapStyleWord(true);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(scrollPane);
		
		
		ImageIcon dosya = new ImageIcon("lib\\dosya.png");
	    

		chooseFSMType = new JButton(dosya);
		chooseFSMType.addActionListener(new chooseInputStyleListener());
		chooseFSMType.setPreferredSize(new Dimension(5, 5));
		chooseFSMType.setToolTipText( "Click here to choose an input type" );
		panelButton.add(chooseFSMType);
		
		
		
		
		panelButton.add(Box.createRigidArea(new Dimension(0, 20)));
		defineCondition = new JButton("Define Condition");
		defineCondition.setEnabled(false);
		defineCondition.addActionListener(new DefineConditionListener());
		defineCondition.setToolTipText( "Click here to define a condition for adding access control property" );
		panelButton.add(defineCondition);
		
		
		ImageIcon convert = new ImageIcon("lib\\convert.png");
		panelButton.add(Box.createRigidArea(new Dimension(0, 20)));
		smvButton = new JButton(convert);
		smvButton.setEnabled(false);
		smvButton.addActionListener(new SMVListener());
		smvButton.setToolTipText( "Click here to convert the FSM to the Model Checker input model" );
		panelButton.add(smvButton);
		
		
		ImageIcon play = new ImageIcon("lib\\play.png");
		//panelButton.add(Box.createRigidArea(new Dimension(0, 20)));
		panelButton.add(Box.createRigidArea(new Dimension(0, 20)));
		runSMVbutton = new JButton(play);
		runSMVbutton.setEnabled(false);
		runSMVbutton.addActionListener(new RunSMVListener());
		runSMVbutton.setToolTipText( "Click here to run the Model Checker" );
		panelButton.add(runSMVbutton);
		
		
		ImageIcon replay = new ImageIcon("lib\\replay.png");
		panelButton.add(Box.createRigidArea(new Dimension(0, 20)));
		runTestCaseButton = new JButton(replay);
		runTestCaseButton.setEnabled(false);
		runTestCaseButton.addActionListener(new RunTCListener());
		runTestCaseButton.setToolTipText( "Click here to replay the detected errors" );
		panelButton.add(runTestCaseButton);
		
		
		ImageIcon modify = new ImageIcon("lib\\modify.png");
		panelButton.add(Box.createRigidArea(new Dimension(0, 40)));
		addnewLinksButton = new JButton(modify);
		addnewLinksButton.setEnabled(false);
		addnewLinksButton.addActionListener(new AddNewLinkListener());
		addnewLinksButton.setToolTipText( "Click here to modify the model" );
		panelButton.add(addnewLinksButton);
		
		
		lpropertyButton = new JButton("Add Link Consistency Property");
		lpropertyButton.setEnabled(false);
		lpropertyButton.addActionListener(new PropertyListener());
		panelButton2.add(lpropertyButton);
		
		
		
		apropertyButton = new JButton("Add Access Control Property");
		apropertyButton.setEnabled(false);
		apropertyButton.addActionListener(new AccessPropertyListener());
		panelButton2.add(apropertyButton);
		
		
		JPanel messagePanel = new JPanel();
		Font font = new Font("Arial", Font.BOLD, 16);
		 
		consoleOutput.setFont(font);
		consoleOutput.setForeground(Color.WHITE);
		consoleOutput.setBackground(Color.black);
		consoleOutput.setText("");
		consoleOutput.setWrapStyleWord(true);
		consoleOutput.setEditable(false);
		consoleOutput.setAlignmentX(consoleOutput.getAlignmentX()/2);
		messagePanel.add(consoleOutput);
		
		messagePanel.add(Box.createRigidArea(new Dimension(0, 50)));
		
		JPanel panelForProperties = new  JPanel();
		
		
	
		
		
		JLabel labelProperties = new JLabel("You added the property:");
		textForProperties = new JTextArea(10,22);
		textForProperties.setText("No Property yet!");
		textForProperties.setLineWrap(true);
		textForProperties.setEditable(false);
		textForProperties.setVisible(true);
		JScrollPane scrollPaneForProperties = new JScrollPane(textForProperties);
		scrollPaneForProperties.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneForProperties.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panelForProperties.add(labelProperties);
		panelForProperties.add(scrollPaneForProperties);
		
	
		
		frame.getContentPane().add(BorderLayout.WEST, panelButton);
		frame.getContentPane().add(BorderLayout.NORTH, messagePanel);
		frame.getContentPane().add(BorderLayout.CENTER, panelForProperties);
		frame.getContentPane().add(BorderLayout.SOUTH, panelButton2);
	
	}
	
	public class chooseInputStyleListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			consoleOutput.setText("\n\n"+"Ready to select an input type!");
			final JFrame fsmTypeWindow = new JFrame("Input Types");
			fsmTypeWindow.setVisible(true);
			fsmTypeWindow.setSize(400,400);
			fsmTypeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			JPanel panelfsmType = new JPanel();	
			panelfsmType.setLayout(new BoxLayout(panelfsmType, BoxLayout.Y_AXIS));
			JPanel panelOKButton = new JPanel();	
			
			ButtonGroup fsmTypesBG = new ButtonGroup();
			final JRadioButton crawljax = new JRadioButton("My FSM (Finite State Machine) is taken from Crawljax");
			final JRadioButton webmole = new JRadioButton("My FSM (Finite State Machine) is taken from MicroCrawler");
			final JRadioButton developerFSM = new JRadioButton("I want to define my own FSM (Finite State Machine)");
			
			fsmTypesBG.add(crawljax);
			fsmTypesBG.add(webmole);
			fsmTypesBG.add(developerFSM);
			
			
			
			JButton OKButton = new JButton("NEXT");
			panelOKButton.add(OKButton);panelfsmType.add(webmole);
			
			panelfsmType.add(crawljax);
			panelfsmType.add(webmole);
			panelfsmType.add(developerFSM);
		
			fsmTypeWindow.getContentPane().add(BorderLayout.NORTH, panelfsmType);
		
			fsmTypeWindow.getContentPane().add(BorderLayout.SOUTH, panelOKButton);
			OKButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					
					if(crawljax.isSelected())consoleOutput.setText("\n\n"+"Please, select a FSM file generated by Crawljax.");
					else if (webmole.isSelected()) consoleOutput.setText("\n\n"+"Please, select a FSM file generated by MicroCrawler.");
					else consoleOutput.setText("\n\n"+"Please define your web model in Intermediate Format.");
					File file;
					if(crawljax.isSelected()){
						isCrawljax = true;
						
						JFileChooser Filechoose=new JFileChooser();
			             int retval=Filechoose.showOpenDialog(frame);
			             if (retval == JFileChooser.APPROVE_OPTION) {
			                
			                 file = Filechoose.getSelectedFile();
			                 setFilePath(file.getPath());
			                 
			                 try {
								intformat = new CreateIntFormat();
								IntFormat crawlType = new CrawljaxIntFormat(filePath);
								File crawljaxIntFormatFile = intformat.createIntFormat(crawlType);
								smvFile.setText(ReadFile(crawljaxIntFormatFile).toString());
								 
								f = new IntFormatToNuSMV();
								intformat.setIntFormat(crawlType);
								
							} catch (IOException e1) {
								e1.printStackTrace();
							}
			                 
			                
				
				}  
			             smvButton.setEnabled(true);      
			             addnewLinksButton.setEnabled(true);
						fsmTypeWindow.dispose();
						
						chooseFSMType.setEnabled(false);
						
					}
					else if(webmole.isSelected()){
						isWebMole = true;
						JFileChooser Filechoose=new JFileChooser();
			             int retval=Filechoose.showOpenDialog(frame);
			             if (retval == JFileChooser.APPROVE_OPTION) {
			                
			                 file = Filechoose.getSelectedFile();
			                
			                 setFilePath(file.getPath());
			                 IntFormat crawlType = new MicroCrawlerIntFormat(file.getPath().toString());
			                 try {
			                	 intformat = new CreateIntFormat();
									File microIntFormat = intformat.createIntFormat(crawlType);
									smvFile.setText(ReadFile(microIntFormat).toString());
									 
									f = new IntFormatToNuSMV();
									intformat.setIntFormat(crawlType);
									
									
			             } catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			                
				
				}
			             smvButton.setEnabled(true);      
			             addnewLinksButton.setEnabled(true);
						fsmTypeWindow.dispose();
						
					}
					else{
						final JFrame typerpropWindow = new JFrame("The Intermediate Web Model");
						typerpropWindow.setVisible(true);
						typerpropWindow.setSize(500,500);
						typerpropWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						
						
						
						JPanel panelsmvText = new JPanel();
						final JTextArea copysmv = new JTextArea(25,35);
						copysmv.setWrapStyleWord(true);
						JScrollPane copyscrollPane = new JScrollPane(copysmv);
						copysmv.setText("");
						panelsmvText.add(copyscrollPane);
						JPanel panelButton = new JPanel();
						final JButton saveButton = new JButton("SAVE");
						panelButton.add(saveButton);
						typerpropWindow.getContentPane().add(BorderLayout.NORTH, panelsmvText);
						typerpropWindow.getContentPane().add(BorderLayout.SOUTH, panelButton);
						
						 saveButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							
							smvFile.setText("");
							smvFile.setText(copysmv.getText());
							// TODO Auto-generated method stub
							
							File fold=new File("lib\\IntFormatv1.xml");
				            fold.delete();
				          
				            File fnew=new File("lib\\IntFormatv1.xml");
				            
				            String source = smvFile.getText();
				            
				            
				            try {
				             FileWriter f2 = new FileWriter(fnew, false);
				             f2.write(source);
				             f2.close();
				              
				              f = new IntFormatToNuSMV();
				            }
				            catch (IOException e) {
				                // TODO Auto-generated catch block
				                e.printStackTrace();
				            }
				           
				           
				            
				            typerpropWindow.dispose(); 
				            
						
						
						}}	
							
								 );
								 	
								 		 }fsmTypeWindow.dispose();
								 		consoleOutput.setText("\n\n"+"Now, you have the FSM of web application in a seperate window."+"\n\n"+"You can convert the model to NuSMV model  "+"\n\n"+"or modify it again");
								 		   TextBoxFrame = new JFrame("The model in Intermediate Format");
										   TextBoxFrame.setVisible(true);
										   TextBoxFrame.setSize(400,400);
										   TextBoxFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
										   JPanel hidePanel = new JPanel();
										   JButton buttonhiDE = new JButton("HIDE"); 
										   hidePanel.add(buttonhiDE);
										   TextBoxFrame.getContentPane().add(BorderLayout.CENTER, panel);
										   
										   TextBoxFrame.getContentPane().add(BorderLayout.SOUTH, hidePanel);
										   buttonhiDE.addActionListener(new ActionListener() {
											
											@Override
											public void actionPerformed(ActionEvent e) {
												// TODO Auto-generated method stub
												TextBoxFrame.setVisible(false);
												
											}
										}); 
										   
				
				
										  }
					

			
			}
		);
		
			}
	
	}	
	
	public class AddNewLinkListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
	    consoleOutput.setText("\n\n"+"You can modify the web model in Intermediate Format.");
		final JFrame typerpropWindow = new JFrame("Intermediate Web Model");
		typerpropWindow.setVisible(true);
		typerpropWindow.setSize(500,500);
		typerpropWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton addNewWindow = new JButton("I do not know XML");
		
		JButton cancelButton = new JButton("CANCEL");
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				typerpropWindow.dispose();
			}
		});
		
		JPanel panelsmvText = new JPanel();
		final JTextArea copysmv = new JTextArea(25,35);
		copysmv.setWrapStyleWord(true);
		
		
		
		try {
			IntFormatToNuSMV f = new IntFormatToNuSMV();
		
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
	          smvFile.setText(ReadFile(new File("lib\\IntFormatv1.xml")).toString());
		
	    copysmv.setText(smvFile.getText().toString());
		JScrollPane copyscrollPane = new JScrollPane(copysmv);
		
		panelsmvText.add(copyscrollPane);
		
		
		JPanel panelButton = new JPanel();
		final JButton saveButton = new JButton("SAVE");
		panelButton.add(saveButton);
		panelButton.add(addNewWindow);
		panelButton.add(cancelButton);
		
	
		
		typerpropWindow.getContentPane().add(BorderLayout.NORTH, panelsmvText);
		typerpropWindow.getContentPane().add(BorderLayout.SOUTH, panelButton);
		
		 saveButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			consoleOutput.setText("\n\n"+"Your model is saved!"+"\n\n"+"You can convert the FSM in Intermediate Format to NuSMV model.");
			
		    
	
			smvFile.setText("");
			smvFile.setText(copysmv.getText());
			// TODO Auto-generated method stub
			
			File fold=new File("lib\\IntFormatv1.xml");
            fold.delete();
            
            File fnew=new File("lib\\IntFormatv1.xml");
            
            String source = smvFile.getText();
            
            try {
             FileWriter f2 = new FileWriter(fnew, false);
             f2.write(source);
             f2.close();
                
              f = new IntFormatToNuSMV();
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           
        
            typerpropWindow.dispose(); 
            
            
		}}	
		
);
		 
		 addNewWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				typerpropWindow.dispose();
				
				
				final JFrame frameforModification = new JFrame("Modify FSM");
				ButtonGroup ModifyTypes = new ButtonGroup();
				final JRadioButton modifyState = new JRadioButton("Modify State");
	    		final JRadioButton modifyElement = new JRadioButton("Modify Element");
	    		final JRadioButton modifyEdge = new JRadioButton("Modify Edge");
	    		ModifyTypes.add(modifyState);
	    		ModifyTypes.add(modifyElement);
	    		ModifyTypes.add(modifyEdge);
	    		
	    		JButton nextButtonForMod = new JButton("NEXT");
	    		JPanel modifyPanel = new JPanel();
	    		JPanel modifyButtonP = new JPanel();
	    		modifyButtonP.add(nextButtonForMod);
	    		modifyPanel.setLayout(new BoxLayout (modifyPanel, BoxLayout.Y_AXIS));
	    	    modifyPanel.add(modifyState);
	    		modifyPanel.add(modifyElement);
	    		modifyPanel.add(modifyEdge);
	    		
	    		JButton cancelButton = new JButton("CANCEL");

	    		cancelButton.addActionListener(new ActionListener() {
	    					
	    					@Override
	    					public void actionPerformed(ActionEvent arg0) {
	    						// TODO Auto-generated method stub
	    						frameforModification.dispose();
	    					}
	    				});



	    		modifyButtonP.add(cancelButton);
	    		
	    		frameforModification.setVisible(true);
	    		frameforModification.setSize(300,200);
	    		frameforModification.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    		frameforModification.getContentPane().add(BorderLayout.NORTH, modifyPanel);
	    		frameforModification.getContentPane().add(BorderLayout.SOUTH, modifyButtonP);
	    		
	    		nextButtonForMod.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						
						
						if(modifyState.isSelected()){
							try {
								listPropertyFrom = new JList(fillStates());
							} catch (IOException e3) {
								// TODO Auto-generated catch block
								e3.printStackTrace();
							} //data has type Object[]
							listPropertyFrom.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
							listPropertyFrom.setLayoutOrientation(JList.VERTICAL);
							listPropertyFrom.setVisibleRowCount(-1);
							
							JScrollPane listScrollerFrom = new JScrollPane(listPropertyFrom);
							listScrollerFrom.setPreferredSize(new Dimension(150, 100));
							
							
							final JFrame stateFrame = new JFrame("State");
							stateFrame.setVisible(true);
							stateFrame.setSize(500,500);
							stateFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							JPanel panelButton = new JPanel();
							JPanel panelNext = new JPanel();
							
							ImageIcon add = new ImageIcon("lib\\add.png");
						    JButton addButton = new JButton(add);
						    addButton.setToolTipText("Click here to add a state");
						    ImageIcon delete = new ImageIcon("lib\\delete.png");
						    JButton deleteButton = new JButton(delete);
						    deleteButton.setToolTipText("Click here to delete a state");
						    ImageIcon modify = new ImageIcon("lib\\edit.png");
						    JButton modifyButton = new JButton(modify);
						    modifyButton.setToolTipText("Click here to edit a state");
						    JButton nextButton = new JButton("NEXT"); 
						    JLabel labelForStates = new JLabel("List of States");
						    panelNext.setLayout(new BoxLayout(panelNext, BoxLayout.Y_AXIS));
						    
						    
						    JButton cancelButton = new JButton("CANCEL");

						    cancelButton.addActionListener(new ActionListener() {
						    			
						    			@Override
						    			public void actionPerformed(ActionEvent arg0) {
						    				// TODO Auto-generated method stub
						    				stateFrame.dispose();
						    			}
						    		});



						    panelButton.add(cancelButton);
						    
						    
						    panelNext.add(labelForStates);
						    panelNext.add(listScrollerFrom);
						    panelButton.add(addButton);
						    panelButton.add(Box.createRigidArea(new Dimension(0, 20)));
						    panelButton.add(deleteButton);
						    panelButton.add(Box.createRigidArea(new Dimension(0, 20)));
						    panelButton.add(modifyButton);
						    panelButton.add(Box.createRigidArea(new Dimension(0, 20)));
						    JLabel helpLabelState= new JLabel ("<html> You can add a new state by cliking add button <br/>If you want to delete or edit a state, first select the state from list box above</html>");
						    JPanel helpLabelPanel= new JPanel();
						    helpLabelPanel.add(helpLabelState);
						    stateFrame.getContentPane().add(BorderLayout.NORTH, panelButton);
						    stateFrame.getContentPane().add(BorderLayout.CENTER, panelNext);
						    stateFrame.getContentPane().add(BorderLayout.SOUTH, helpLabelPanel);
						    
						    addButton.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent arg0) {
									
									final JFrame stateaddFrame = new JFrame("State");
									stateaddFrame.setVisible(true);
									stateaddFrame.setSize(300,300);
									stateaddFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
									
								    JPanel paneladd = new JPanel();
									paneladd.setLayout(new BoxLayout(paneladd, BoxLayout.Y_AXIS));
									JLabel nameLabel = new JLabel("Name of State:");
									final JLabel urlLabel = new JLabel("URL of State:");
									final JTextField nameofState = new JTextField(200);
									final JTextField urlofState = new JTextField(200);
									paneladd.add(nameLabel);
									paneladd.add(nameofState);
									paneladd.add(Box.createRigidArea(new Dimension(0, 20)));
									paneladd.add(urlLabel);
									paneladd.add(urlofState);
									
									JButton cancelButton = new JButton("CANCEL");

									cancelButton.addActionListener(new ActionListener() {
												
												@Override
												public void actionPerformed(ActionEvent arg0) {
													// TODO Auto-generated method stub
													stateaddFrame.dispose();
												}
											});



									
									
									
									
									JButton buttonNext = new JButton("ADD");
									JPanel nextButtonPanel = new JPanel();
									nextButtonPanel.add(buttonNext);
									nextButtonPanel.add(cancelButton);
									stateaddFrame.getContentPane().add(BorderLayout.NORTH, paneladd);
									stateaddFrame.getContentPane().add(BorderLayout.CENTER, nextButtonPanel);
									// TODO Auto-generated method stub
									
									
									
									
									
									
									buttonNext.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent arg0) {
											// TODO Auto-generated method stub
											WebState ws = new WebState();
											Vector<WebState> stateList = new Vector<WebState>();
											ws.setName(nameofState.getText());
											ws.setUrl(urlofState.getText());
											stateList=intformat.getIntFormat().getStateList();
											stateList.add(ws);
											intformat.getIntFormat().setStateList(stateList);
											JOptionPane.showMessageDialog(null, nameofState.getText().toString()+" is added successfully");
											CreateIntFormat intFormat = new CreateIntFormat();
											try {
												intFormat.createIntFormat(intformat.getIntFormat().getStateList(),intformat.getIntFormat().getElementList(),intformat.getIntFormat().getEdgeList());
											} catch (IOException e2) {
												// TODO Auto-generated catch block
												e2.printStackTrace();
											}
											try {
												IntFormatToNuSMV f = new IntFormatToNuSMV();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											stateaddFrame.dispose();
											stateFrame.dispose();
											frameforModification.dispose();
											consoleOutput.setText("\n\n"+"You can convert the FSM in Intermediate Format to NuSMV now.");
										}
									});
									
								}
							});
						    
							deleteButton.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent arg0) {
									// TODO Auto-generated method stub
									
									intformat.getIntFormat().getStateList().remove(listPropertyFrom.getSelectedIndex());
									
									JOptionPane.showMessageDialog(null, listPropertyFrom.getSelectedValue().toString()+" is deleted successfully");
									CreateIntFormat intFormat = new CreateIntFormat();
									try {
										intFormat.createIntFormat(intformat.getIntFormat().getStateList(),intformat.getIntFormat().getElementList(),intformat.getIntFormat().getEdgeList());
									} catch (IOException e2) {
										// TODO Auto-generated catch block
										e2.printStackTrace();
									}
									stateFrame.dispose();
									frameforModification.dispose();
									consoleOutput.setText("\n\n"+"You can convert the FSM in Intermediate Format to NuSMV now.");
									
								}
							});
							
							modifyButton.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent arg0) {
									final JFrame ModifystateaddFrame = new JFrame("State");
									ModifystateaddFrame.setVisible(true);
									ModifystateaddFrame.setSize(300,300);
									ModifystateaddFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
									
									JPanel paneladdE = new JPanel();
									paneladdE.setLayout(new BoxLayout(paneladdE, BoxLayout.Y_AXIS));
									JLabel nameLabelModify = new JLabel("Name of State:");
									final JLabel urlLabelModify = new JLabel("URL of State:");
									final JTextField nameofStateModify = new JTextField(intformat.getIntFormat().getStateList().get(listPropertyFrom.getSelectedIndex()).getName());
									final JTextField urlofState = new JTextField(intformat.getIntFormat().getStateList().get(listPropertyFrom.getSelectedIndex()).getUrl());
									paneladdE.add(nameLabelModify);
									paneladdE.add(nameofStateModify);
									paneladdE.add(Box.createRigidArea(new Dimension(0, 20)));
									paneladdE.add(urlLabelModify);
									paneladdE.add(urlofState);
									
									JButton cancelButton = new JButton("CANCEL");

									cancelButton.addActionListener(new ActionListener() {
												
												@Override
												public void actionPerformed(ActionEvent arg0) {
													// TODO Auto-generated method stub
													ModifystateaddFrame.dispose();
												}
											});



									
									
									JButton buttonSave1 = new JButton("SAVE");
									JPanel saveButtonPanel = new JPanel();
									saveButtonPanel.add(buttonSave1);
									saveButtonPanel.add(cancelButton);
									ModifystateaddFrame.getContentPane().add(BorderLayout.NORTH, paneladdE);
									ModifystateaddFrame.getContentPane().add(BorderLayout.CENTER, saveButtonPanel);
									
									
									
									buttonSave1.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent e) {
											WebState ws = new WebState();
											ws.setName(nameofStateModify.getText());
											ws.setUrl(urlofState.getText());
											//.add(ws);
											JOptionPane.showMessageDialog(null, nameofStateModify.getText().toString()+" is saved successfully");
											CreateIntFormat intFormat = new CreateIntFormat();
											try {
												intFormat.createIntFormat(intformat.getIntFormat().getStateList(),intformat.getIntFormat().getElementList(),intformat.getIntFormat().getEdgeList());
											} catch (IOException e2) {
												// TODO Auto-generated catch block
												e2.printStackTrace();
											}
											ModifystateaddFrame.dispose();
											frameforModification.dispose();
											// TODO Auto-generated method stub
											consoleOutput.setText("\n\n"+"You can convert the FSM in Intermediate Format to NuSMV now.");
										}
									});
									
								}
									});
							
							
						}
						else if(modifyElement.isSelected()){
							CreateIntFormat intFormat = new CreateIntFormat();
							try {
								intFormat.createIntFormat(intformat.getIntFormat().getStateList(),intformat.getIntFormat().getElementList(),intformat.getIntFormat().getEdgeList());
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							try {
								listofLinks = new JList(fillElement().toArray());
							} catch (IOException e3) {
								// TODO Auto-generated catch block
								e3.printStackTrace();
							} //data has type Object[]
							listofLinks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							listofLinks.setLayoutOrientation(JList.VERTICAL);
							listofLinks.setVisibleRowCount(-1);
							
							JScrollPane listofLinksScroller = new JScrollPane(listofLinks);
							listofLinksScroller.setPreferredSize(new Dimension(250, 180));
							
							frameforModification.dispose();
							final JFrame elementFrame = new JFrame("Element");
							elementFrame.setVisible(true);
							elementFrame.setSize(500,500);
							elementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							JPanel panelButton = new JPanel();
							JPanel panelNext = new JPanel();
							panelNext.setLayout(new BoxLayout(panelNext, BoxLayout.Y_AXIS));
							ImageIcon add = new ImageIcon("lib\\add.png");
						    JButton addButton = new JButton(add);
						    addButton.setToolTipText("Click here to add an element");
						    ImageIcon delete = new ImageIcon("lib\\delete.png");
						    JButton deleteButton = new JButton(delete);
						    deleteButton.setToolTipText("Click here to delete an element");
						    ImageIcon modify = new ImageIcon("lib\\edit.png");
						    JButton modifyButton = new JButton(modify);
						    modifyButton.setToolTipText("Click here to edit an element"); 
						    JLabel elementLabel = new JLabel("List of Elements");
						    panelNext.add(elementLabel);
						    panelNext.add(listofLinksScroller);
						    
						    JButton cancelButton = new JButton("CANCEL");

						    cancelButton.addActionListener(new ActionListener() {
						    			
						    			@Override
						    			public void actionPerformed(ActionEvent arg0) {
						    				// TODO Auto-generated method stub
						    				elementFrame.dispose();
						    			}
						    		});



						    panelButton.add(cancelButton);
						    
							JLabel helplElementLabel= new JLabel ("<html> You can add a new element by cliking add button <br/>If you want to delete or edit an element, first select the element from list box above</html>");
							JPanel helpElementPanel= new JPanel();
							helpElementPanel.add(helplElementLabel);
						  
						    panelButton.add(addButton);
						    panelButton.add(Box.createRigidArea(new Dimension(0, 20)));
						    panelButton.add(deleteButton);
						    panelButton.add(Box.createRigidArea(new Dimension(0, 20)));
						    panelButton.add(modifyButton);
						    panelButton.add(Box.createRigidArea(new Dimension(0, 20)));
						    elementFrame.getContentPane().add(BorderLayout.NORTH, panelButton);
						    elementFrame.getContentPane().add(BorderLayout.CENTER, panelNext);
						    elementFrame.getContentPane().add(BorderLayout.SOUTH, helpElementPanel);
						    
						    addButton.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent arg0) {
									
									final JFrame elementaddFrame = new JFrame("Element");
									elementaddFrame.setVisible(true);
									elementaddFrame.setSize(300,300);
									elementaddFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
									
									JPanel paneladd = new JPanel();
								
									paneladd.setLayout(new BoxLayout(paneladd, BoxLayout.Y_AXIS));
									JLabel nameLabel = new JLabel("XPath Expression of Element");
									final JTextField xpathOfElement = new JTextField(100);
									paneladd.add(nameLabel);
									paneladd.add(xpathOfElement);
									
									JButton cancelButton = new JButton("CANCEL");

									cancelButton.addActionListener(new ActionListener() {
												
												@Override
												public void actionPerformed(ActionEvent arg0) {
													// TODO Auto-generated method stub
													elementaddFrame.dispose();
												}
											});



									
									
							
									JButton buttonNext = new JButton("ADD");
									JPanel nextButtonPanel = new JPanel();
									nextButtonPanel.add(buttonNext);
									nextButtonPanel.add(cancelButton);
									elementaddFrame.getContentPane().add(BorderLayout.NORTH, paneladd);
									elementaddFrame.getContentPane().add(BorderLayout.CENTER, nextButtonPanel);
									
									// TODO Auto-generated method stub
									
									buttonNext.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent arg0) {
											// TODO Auto-generated method stub
											Vector <Element> elementList = new Vector <Element>();
											Element el = new Element();
											el.setXpath(xpathOfElement.getText());
											elementList= intformat.getIntFormat().getElementList();
											elementList.add(el);
											intformat.getIntFormat().setElementList(elementList);
											JOptionPane.showMessageDialog(null, xpathOfElement.getText().toString()+" is added successfully");
											CreateIntFormat intFormat = new CreateIntFormat();
											try {
												intFormat.createIntFormat(intformat.getIntFormat().getStateList(),intformat.getIntFormat().getElementList(),intformat.getIntFormat().getEdgeList());
											} catch (IOException e2) {
												// TODO Auto-generated catch block
												e2.printStackTrace();
											}
											elementaddFrame.dispose();
											
											frameforModification.dispose();
											consoleOutput.setText("\n\n"+"You can convert the FSM in Intermediate Format to NuSMV now.");
										}
									});
									
								}
							});
						    
							deleteButton.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent arg0) {
									// TODO Auto-generated method stub
									intformat.getIntFormat().getElementList().remove(listofLinks.getSelectedIndex());
									JOptionPane.showMessageDialog(null, listofLinks.getSelectedValue().toString()+" is deleted successfully");
									CreateIntFormat intFormat = new CreateIntFormat();
									try {
										intFormat.createIntFormat(intformat.getIntFormat().getStateList(),intformat.getIntFormat().getElementList(),intformat.getIntFormat().getEdgeList());
									} catch (IOException e2) {
										// TODO Auto-generated catch block
										e2.printStackTrace();
									}
									
									frameforModification.dispose();
									consoleOutput.setText("\n\n"+"You can convert the FSM in Intermediate Format to NuSMV now.");
									
									
								}
							});
							
							modifyButton.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent arg0) {
									
								    final JFrame ModifyElementFrame = new JFrame("Element");
									ModifyElementFrame.setVisible(true);
									ModifyElementFrame.setSize(300,300);
									ModifyElementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
									
									JPanel paneladdE = new JPanel();
									paneladdE.setLayout(new BoxLayout(paneladdE, BoxLayout.Y_AXIS));
									JLabel nameLabelModify = new JLabel("Name of Element:");
									final JTextField nameofElementTextField = new JTextField(f.initialize_element().get(listPropertyTo.getSelectedIndex()).getXpath());
									paneladdE.add(nameLabelModify);
									paneladdE.add(nameofElementTextField);
									
									
									JButton cancelButton = new JButton("CANCEL");

									cancelButton.addActionListener(new ActionListener() {
												
												@Override
												public void actionPerformed(ActionEvent arg0) {
													// TODO Auto-generated method stub
													ModifyElementFrame.dispose();
												}
											});



									
			
									
									JButton buttonSave1 = new JButton("SAVE");
									JPanel saveButtonPanel = new JPanel();
									saveButtonPanel.add(buttonSave1);
									saveButtonPanel.add(cancelButton);
									ModifyElementFrame.getContentPane().add(BorderLayout.NORTH, paneladdE);
									ModifyElementFrame.getContentPane().add(BorderLayout.CENTER, saveButtonPanel);
									
									
									buttonSave1.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent e) {
											intformat.getIntFormat().getElementList().remove(listofLinks.getSelectedIndex());
											CreateIntFormat intFormat = new CreateIntFormat();
											try {
												intFormat.createIntFormat(intformat.getIntFormat().getStateList(),intformat.getIntFormat().getElementList(),intformat.getIntFormat().getEdgeList());
											} catch (IOException e2) {
												// TODO Auto-generated catch block
												e2.printStackTrace();
											}
											Element el = new Element();
											el.setXpath(nameofElementTextField.getText());
											f.initialize_element().add(el);
											JOptionPane.showMessageDialog(null, nameofElementTextField.getText().toString()+" is saved successfully");
											
											try {
												intFormat.createIntFormat(intformat.getIntFormat().getStateList(),intformat.getIntFormat().getElementList(),intformat.getIntFormat().getEdgeList());
											} catch (IOException e2) {
												// TODO Auto-generated catch block
												e2.printStackTrace();
											}
											consoleOutput.setText("\n\n"+"You can convert the FSM in Intermediate Format to NuSMV now.");
											ModifyElementFrame.dispose();
											// TODO Auto-generated method stub
											
											ModifyElementFrame.dispose();
											frameforModification.dispose();
											
										}
									});
									
								}
							});
							
							
							
							
							
							
						}
						else if(modifyEdge.isSelected()){
							
							try {
								listPropertyFrom = new JList(fillStates());
							} catch (IOException e5) {
								// TODO Auto-generated catch block
								e5.printStackTrace();
							} //data has type Object[]
							listPropertyFrom.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
							listPropertyFrom.setLayoutOrientation(JList.VERTICAL);
							listPropertyFrom.setVisibleRowCount(-1);
							
							JScrollPane listScrollerFrom = new JScrollPane(listPropertyFrom);
							listScrollerFrom.setPreferredSize(new Dimension(250, 80));
							
							try {
								listPropertyTo = new JList(fillStates());
							} catch (IOException e4) {
								// TODO Auto-generated catch block
								e4.printStackTrace();
							} //data has type Object[]
							listPropertyTo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							listPropertyTo.setLayoutOrientation(JList.VERTICAL);
							listPropertyTo.setVisibleRowCount(-1);
							
							JScrollPane listScrollerTo = new JScrollPane(listPropertyTo);
							listScrollerTo.setPreferredSize(new Dimension(250, 80));
							
							try {
								listofLinks = new JList(fillElement().toArray());
							} catch (IOException e3) {
								// TODO Auto-generated catch block
								e3.printStackTrace();
							} //data has type Object[]
							listofLinks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							listofLinks.setLayoutOrientation(JList.VERTICAL);
							listofLinks.setVisibleRowCount(-1);
							
							JScrollPane listofLinksScroller = new JScrollPane(listofLinks);
							listofLinksScroller.setPreferredSize(new Dimension(250, 180));
							
						    final JFrame ModifyEdgeFrame = new JFrame("Edge");
						    ModifyEdgeFrame.setVisible(true);
						    ModifyEdgeFrame.setSize(500,700);
						    ModifyEdgeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						    
						    JPanel panelButton = new JPanel();
							JPanel panelNext = new JPanel();
				
							ImageIcon add = new ImageIcon("lib\\add.png");
						    final JButton addButton = new JButton(add);
						    addButton.setToolTipText("Choose from, element and to states and Click here to add an edge");
						    ImageIcon delete = new ImageIcon("lib\\delete.png");
						    final JButton deleteButton = new JButton(delete);
						    deleteButton.setToolTipText("Choose from, element and to states and Click here to delete an edge");
						    ImageIcon modify = new ImageIcon("lib\\edit.png");
						    
						    panelNext.setLayout(new BoxLayout(panelNext, BoxLayout.Y_AXIS));
						    
						  
						    JLabel labelEdgeFrom = new JLabel("From State");
						    panelNext.add(labelEdgeFrom);
						    panelNext.add(listScrollerFrom);
						    JLabel labelEdgeElement = new JLabel("Element");
						    panelNext.add(labelEdgeElement);
						    panelNext.add(listofLinksScroller);
						    JLabel labelEdgeTo = new JLabel("To State");
						    panelNext.add(labelEdgeTo);
						    panelNext.add(listScrollerTo);
						    
						    JButton cancelButton = new JButton("CANCEL");

						    cancelButton.addActionListener(new ActionListener() {
						    			
						    			@Override
						    			public void actionPerformed(ActionEvent arg0) {
						    				// TODO Auto-generated method stub
						    				ModifyEdgeFrame.dispose();
						    			}
						    		});



						    panelButton.add(cancelButton);
						    
						    JLabel helplEdgeLabel= new JLabel ("<html> You can add or delete an edge <br/> by selecting the from state, to state and the element which connects these states</html>");
							JPanel helpEdgePanel= new JPanel();
							helpEdgePanel.add(helplEdgeLabel);
						    
						    panelButton.add(addButton);
						    panelButton.add(Box.createRigidArea(new Dimension(0, 20)));
						    panelButton.add(deleteButton);
						    panelButton.add(Box.createRigidArea(new Dimension(0, 20)));
						
						    panelButton.add(Box.createRigidArea(new Dimension(0, 20)));
						    ModifyEdgeFrame.getContentPane().add(BorderLayout.NORTH, panelButton);
						    ModifyEdgeFrame.getContentPane().add(BorderLayout.CENTER, panelNext);
						    ModifyEdgeFrame.getContentPane().add(BorderLayout.SOUTH, helpEdgePanel);
						    
						    
						    
						   addButton.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent arg0) {
								
								if(!(!listPropertyFrom.isSelectionEmpty() && !listPropertyTo.isSelectionEmpty() && !listofLinks.isSelectionEmpty())){
									JOptionPane.showMessageDialog(null, "Please select all the parts of an Edge");
									ModifyEdgeFrame.dispose();
								
								}
								Edge edgeAdd =  new Edge();
								
								WebState FromWs = new WebState();
								WebState ToWs = new WebState();
								Element Elws = new Element();
								
								FromWs.setName(intformat.getIntFormat().getStateList().get(listPropertyFrom.getSelectedIndex()).getName());
								FromWs.setName(intformat.getIntFormat().getStateList().get(listPropertyFrom.getSelectedIndex()).getUrl());
								
								Elws.setXpath(intformat.getIntFormat().getElementList().get(listofLinks.getSelectedIndex()).getXpath());
								
								ToWs.setName(intformat.getIntFormat().getStateList().get(listPropertyTo.getSelectedIndex()).getName());
								ToWs.setName(intformat.getIntFormat().getStateList().get(listPropertyTo.getSelectedIndex()).getUrl());
								
								edgeAdd.setFrom(FromWs);
								edgeAdd.setXpathh(Elws);
								edgeAdd.setTo(ToWs);
								
								intformat.getIntFormat().getEdgeList().add(edgeAdd);
								
						
								CreateIntFormat intFormat = new CreateIntFormat();
								try {
									intFormat.createIntFormat(intformat.getIntFormat().getStateList(),intformat.getIntFormat().getElementList(),intformat.getIntFormat().getEdgeList());
								} catch (IOException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
								JOptionPane.showMessageDialog(null, "The edge is successfully added");
								
								
								
								// TODO Auto-generated method stub
								consoleOutput.setText("\n\n"+"You can convert the FSM in Intermediate Format to NuSMV now.");
								ModifyEdgeFrame.dispose();
								frameforModification.dispose();
								
							}
							
								
						});
						   
						   
						   deleteButton.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								
								
                                Edge edgeAdd =  new Edge();
								
								WebState FromWs = new WebState();
								WebState ToWs = new WebState();
								Element Elws = new Element();
								
								FromWs.setName(intformat.getIntFormat().getStateList().get(listPropertyFrom.getSelectedIndex()).getName());
								FromWs.setName(intformat.getIntFormat().getStateList().get(listPropertyFrom.getSelectedIndex()).getUrl());
								
								Elws.setXpath(intformat.getIntFormat().getElementList().get(listofLinks.getSelectedIndex()).getXpath());
								
								ToWs.setName(intformat.getIntFormat().getStateList().get(listPropertyTo.getSelectedIndex()).getName());
								ToWs.setName(intformat.getIntFormat().getStateList().get(listPropertyTo.getSelectedIndex()).getUrl());
								
								edgeAdd.setFrom(FromWs);
								edgeAdd.setXpathh(Elws);
								edgeAdd.setTo(ToWs);
								
								
								for(int i=0;i<intformat.getIntFormat().getEdgeList().size();i++){
									if(intformat.getIntFormat().getEdgeList().get(i).getFrom().getName().equals(FromWs.getName()) && intformat.getIntFormat().getEdgeList().get(i).getTo().getName().equals(ToWs.getName()) && intformat.getIntFormat().getEdgeList().get(i).getXpathh().equals(Elws.getXpath())){
										intformat.getIntFormat().getEdgeList().remove(i); JOptionPane.showMessageDialog(null, "The edge is deleted!");}
										else if(!(intformat.getIntFormat().getEdgeList().get(i).getFrom().getName().equals(FromWs.getName()) && intformat.getIntFormat().getEdgeList().get(i).getTo().getName().equals(ToWs.getName()) && intformat.getIntFormat().getEdgeList().get(i).getXpathh().equals(Elws.getXpath())) && i==intformat.getIntFormat().getEdgeList().size()-1){
							
									JOptionPane.showMessageDialog(null, "Sorry:( This Edge is not defined in the FSM yet");}
								}
								CreateIntFormat intFormat = new CreateIntFormat();
								try {
									intFormat.createIntFormat(intformat.getIntFormat().getStateList(),intformat.getIntFormat().getElementList(),intformat.getIntFormat().getEdgeList());
								} catch (IOException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
							
								consoleOutput.setText("\n\n"+"You can convert the FSM in Intermediate Format to NuSMV now.");
								ModifyEdgeFrame.dispose();
								frameforModification.dispose();
							}
						});
						   
		
							
						}
						
					}
				});
			}
		});
	
		 }}
	

	public String[] fillStates() throws IOException{
		 f = new IntFormatToNuSMV();
		
		ArrayList<WebState> propertListFrom = new ArrayList<WebState>();
		ArrayList<WebState> propertListTo = new ArrayList<WebState>();
		
		
		String ArrayOfStates [] ;
		ArrayList<String> propertyListStates = new ArrayList<String>();
		propertListFrom.clear();
		propertListTo.clear();
		
		
			
				
				for(int i= 0; i<f.initialize_state().size();i++){
					propertListFrom.add(f.initialize_state().get(i));
					propertListTo.add(f.initialize_state().get(i));
					
				}
				
				
				ArrayOfStates= new String[propertListFrom.size()];
				for(int i=0;i<propertListFrom.size();i++){
					ArrayOfStates[i]= propertListFrom.get(i).getName()+" / "+ propertListFrom.get(i).getUrl();	
				}
			
		 
		
		
		
		return ArrayOfStates;
	}
	
	public  ArrayList<String> fillElement() throws IOException{
		
               f = new IntFormatToNuSMV();
		
		ArrayList<String> propertyListXpath = new ArrayList<String>();
		
		propertyListXpath.clear();
		
		
		IntFormatToNuSMV f = new IntFormatToNuSMV();
				
				
				for(int i= 0; i<f.initialize_element().size();i++){
					propertyListXpath.add(f.initialize_element().get(i).getXpath());
				}
				
				
				
		 
		
		
		return propertyListXpath;
		
	}
	
	public void bringSMVModel(){
           smvFile.setText("");
         
		try {
			IntFormatToNuSMV f = new IntFormatToNuSMV();
		
	        smvFile.setText(ReadFile(f.getSmvInput(isCrawljax)).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
    public class SMVListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		consoleOutput.setText("\n\n"+"The web model is converted to NuSMV input language!");
		
		   TextBoxFrame = new JFrame("NuSMV Model");
		   TextBoxFrame.setVisible(true);
		   TextBoxFrame.setSize(400,400);
		   TextBoxFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   JPanel hidePanel = new JPanel();
		   JButton buttonhiDE = new JButton("HIDE"); 
		   hidePanel.add(buttonhiDE);
		   TextBoxFrame.getContentPane().add(BorderLayout.CENTER, panel);
		   TextBoxFrame.getContentPane().add(BorderLayout.SOUTH, hidePanel);
		   buttonhiDE.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TextBoxFrame.setVisible(false);
				
			}
		}); 
		

		NuSMVExecuter nusmv = new NuSMVExecuter();
		
		
		 StringBuffer smvText = ReadFile( f.createSMV(nusmv.getsmvInputFileName(), isCrawljax));
		 smvFile.setText(smvText.toString());
		 smvFile.append(System.getProperty("line.separator"));
		
		 smvFile.append("--NO BROKEN LINK OR DEAD END");
		 smvFile.append(System.getProperty("line.separator"));
		 smvFile.append(f.printBrokenLinkProperty().toString());
		 smvFile.append(System.getProperty("line.separator"));
	
		 textForProperties.setText(
			    "--INDEX REACHABILITY"+
			    System.getProperty("line.separator")+
				
				"--ABSENCE OF DEAD END"+
				
				System.getProperty("line.separator"));
		
		 consoleOutput.setText("\n\n"+"Now you have the NuSMV model in a seperate window!"+"\n\n"+"You can run the model with specified properties "+"\n\n"+" or add a new one!");
		 runSMVbutton.setEnabled(true);
		defineCondition.setEnabled(true);
		
		lpropertyButton.setEnabled(true);
		addnewLinksButton.setEnabled(false);
		
		smvButton.setEnabled(true);
		chooseFSMType.setEnabled(false);
		smvButton.setEnabled(false);
		
		
	}
	
	
}
  
    public class PropertyListener implements ActionListener{
    	JList loginList ;
	@Override
	public void actionPerformed(ActionEvent arg0) {
		consoleOutput.setText("\n\n"+"You can define the link consistency property now.");
		
		final JFrame propWindow = new JFrame();
		propWindow.setVisible(true);
		propWindow.setSize(400,700);
		propWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		final JPanel panelBox = new JPanel();
		final JPanel panelList = new JPanel();
		JPanel panelbutton = new JPanel();
		
		JLabel labelLogin;
		
		try {
			listPropertyFrom = new JList(fillStates());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //data has type Object[]
		listPropertyFrom.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listPropertyFrom.setLayoutOrientation(JList.VERTICAL);
		listPropertyFrom.setVisibleRowCount(-1);
		
		JScrollPane listScrollerFrom = new JScrollPane(listPropertyFrom);
		listScrollerFrom.setPreferredSize(new Dimension(250, 80));
		
		try {
			listPropertyTo = new JList(fillStates());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //data has type Object[]
		listPropertyTo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listPropertyTo.setLayoutOrientation(JList.VERTICAL);
		listPropertyTo.setVisibleRowCount(-1);
		
		JScrollPane listScrollerTo = new JScrollPane(listPropertyTo);
		listScrollerTo.setPreferredSize(new Dimension(250, 80));
		
		JButton addbutton = new JButton("Add");
		JLabel fromLabel = new JLabel("From");
		JLabel toLabel = new JLabel("To");
		
		JButton cancelbutton = new JButton("CANCEL");
		
		panelbutton.add(addbutton);
		panelbutton.add(cancelbutton);
		
		panelList.setLayout(new BoxLayout (panelList, BoxLayout.Y_AXIS));
		panelList.add(fromLabel);
		panelList.add(listScrollerFrom);
		panelList.add(Box.createRigidArea(new Dimension(10, 10)));
		panelList.add(toLabel);
		panelList.add(listScrollerTo);
		
		String[] data = {"yes","no"};
		loginList = new JList(data);
		labelLogin = new JLabel("Does it require login");
		
		JScrollPane listScrollerLogin = new JScrollPane(loginList);
		listScrollerLogin.setPreferredSize(new Dimension(250, 80));
		
		panelList.add(labelLogin);
		panelList.add(listScrollerLogin);
		
		cancelbutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				propWindow.dispose();
				
			}
		});
		addbutton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				consoleOutput.setText("\n\n"+"The property is added to NuSMV model!"+"\n"+"You can run the model checker.");
				if(count==0){
				if(loginList.getSelectedValue().equals("yes")){
					JOptionPane.showMessageDialog(null, "You should first define condition");
					
				}
				else{
					textForProperties.setText(System.getProperty("line.separator")+
							"--LINK CONSISTENCY PROPERTY"+
							System.getProperty("line.separator"));
					smvFile.append(System.getProperty("line.separator"));
					smvFile.append("--LINK CONSISTENCY PROPERTY");
					smvFile.append(System.getProperty("line.separator"));
			if(getFilePath().contains("result") || isCrawljax){
				
			smvFile.append("SPEC AG((webstate="+ f.initialize_state().get(listPropertyFrom.getSelectedIndex()).getName()+")"+"->EF(webstate="+f.initialize_state().get(listPropertyTo.getSelectedIndex()).getName()+"));");
			textForProperties.setText("SPEC AG((webstate="+ f.initialize_state().get(listPropertyFrom.getSelectedIndex()).getName()+")"+"->EF(webstate="+f.initialize_state().get(listPropertyTo.getSelectedIndex()).getName()+"));");
			}
			else if(!(getFilePath().contains("result")) || !isCrawljax){
				
				smvFile.append("SPEC AG((webstate="+ intformat.getIntFormat().getStateList().get(listPropertyFrom.getSelectedIndex()).getName()+")"+"->EF(webstate="+f.initialize_state().get(listPropertyTo.getSelectedIndex()).getName()+"));");	
				textForProperties.setText("SPEC AG((webstate="+ intformat.getIntFormat().getStateList().get(listPropertyFrom.getSelectedIndex()).getName()+")"+"->EF(webstate="+f.initialize_state().get(listPropertyTo.getSelectedIndex()).getName()+"));");
				}
			
			smvFile.append(System.getProperty("line.separator"));
			}
			}
			else if(count>0){
				smvFile.append(System.getProperty("line.separator"));
				smvFile.append("--LINK CONSISTENCY PROPERTY");
				smvFile.append(System.getProperty("line.separator"));
				
				textForProperties.setText(System.getProperty("line.separator")+
						"--LINK CONSISTENCY PROPERTY"+
						System.getProperty("line.separator"));
				
				if(loginList.getSelectedValue().equals("yes")){
					if(getFilePath().contains("result") || isCrawljax){
						
				     	smvFile.append("SPEC AG((webstate="+ intformat.getIntFormat().getStateList().get(listPropertyFrom.getSelectedIndex()).getName()+" & login = TRUE "+")"+"->EF(webstate="+intformat.getIntFormat().getStateList().get(listPropertyTo.getSelectedIndex()).getName()+"));");
				     	textForProperties.setText("SPEC AG((webstate="+ intformat.getIntFormat().getStateList().get(listPropertyFrom.getSelectedIndex()).getName()+" & login = TRUE "+")"+"->EF(webstate="+f.initialize_state().get(listPropertyTo.getSelectedIndex()).getName()+"));");
				     	}
						else if(!(getFilePath().contains("result")) || !isCrawljax){
							//MicroCrawlerIntFormat mc = guiToCrawl.getMicro();
							smvFile.append("SPEC AG((webstate="+ intformat.getIntFormat().getStateList().get(listPropertyFrom.getSelectedIndex()).getName()+" & login = TRUE "+")"+"->EF(webstate="+intformat.getIntFormat().getStateList().get(listPropertyTo.getSelectedIndex()).getName()+"));");	
							textForProperties.setText("SPEC AG((webstate="+ intformat.getIntFormat().getStateList().get(listPropertyFrom.getSelectedIndex()).getName()+" & login = TRUE "+")"+"->EF(webstate="+intformat.getIntFormat().getStateList().get(listPropertyTo.getSelectedIndex()).getName()+"));");
							}
					
					
				}
				else
					if(getFilePath().contains("result") || isCrawljax){
						
				     	smvFile.append("SPEC AG((webstate="+ intformat.getIntFormat().getStateList().get(listPropertyFrom.getSelectedIndex()).getName()+")"+"->EF(webstate="+f.initialize_state().get(listPropertyTo.getSelectedIndex()).getName()+"));");
				     	textForProperties.setText("SPEC AG((webstate="+ intformat.getIntFormat().getStateList().get(listPropertyFrom.getSelectedIndex()).getName()+")"+"->EF(webstate="+intformat.getIntFormat().getStateList().get(listPropertyTo.getSelectedIndex()).getName()+"));");
				     	}
						else if(!(getFilePath().contains("result")) || !isCrawljax){
							
							textForProperties.setText("SPEC AG((webstate="+intformat.getIntFormat().getStateList().get(listPropertyFrom.getSelectedIndex()).getName()+")"+"->EF(webstate="+intformat.getIntFormat().getStateList().get(listPropertyTo.getSelectedIndex()).getName()+"));");
							smvFile.append("SPEC AG((webstate="+ intformat.getIntFormat().getStateList().get(listPropertyFrom.getSelectedIndex()).getName()+")"+"->EF(webstate="+intformat.getIntFormat().getStateList().get(listPropertyTo.getSelectedIndex()).getName()+"));");	
							
							}
				
			}
			smvFile.append(System.getProperty("line.separator"));
			
			propWindow.dispose();

			}
	
		}
	);
		propWindow.add(panelBox);
		propWindow.add(panelList);
		propWindow.getContentPane().add(BorderLayout.NORTH, panelBox);
		propWindow.getContentPane().add(BorderLayout.SOUTH, panelbutton);
		
	}
	  
 
 }
    
    public class LogoutListener implements ActionListener{
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			selectedLogOutElement=new ArrayList<Integer>();
			selectedLogOutState =new ArrayList<Integer>();
			if(saveisClicked==false){
			
			
			defineLOWindow.setVisible(true);
			defineLOWindow.setSize(300,700);
			defineLOWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
			JPanel panelLink2 = new JPanel();
			
			try {
				listPropertyTo = new JList(fillStates());
			} catch (IOException e2) {
				
				e2.printStackTrace();
			} 
			listPropertyTo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listPropertyTo.setLayoutOrientation(JList.VERTICAL);
			listPropertyTo.setVisibleRowCount(-1);
			
			JScrollPane listPropertyToScroller = new JScrollPane(listPropertyTo);
			listPropertyToScroller.setPreferredSize(new Dimension(250, 180));
			
			try {
				listofLinks = new JList(fillElement().toArray());
			} catch (IOException e1) {
				
				e1.printStackTrace();
			} 
			listofLinks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listofLinks.setLayoutOrientation(JList.VERTICAL);
			listofLinks.setVisibleRowCount(-1);
			
			JScrollPane listofLinksScroller = new JScrollPane(listofLinks);
			listofLinksScroller.setPreferredSize(new Dimension(250, 180));
			
		
			
			panelLink2.setLayout(new BoxLayout (panelLink2, BoxLayout.Y_AXIS));
		    JLabel linkLabel1 = new JLabel("Choose the control type");
			
		    panelLink2.add(Box.createRigidArea(new Dimension(10, 10)));
			JLabel linkLabel2 = new JLabel("Choose the state the logout starts");
			panelLink2.add(linkLabel2);
			panelLink2.add(listPropertyToScroller);
			panelLink2.add(Box.createRigidArea(new Dimension(10, 10)));
			JLabel xpathLabel = new JLabel("Choose with which link you logout");
			panelLink2.add(xpathLabel);
			panelLink2.add(listofLinksScroller);
		    
			
		    
		    
			JPanel panelnextButton = new JPanel();
			JButton ADDButton = new JButton("ADD");
		    
		
			panelnextButton.add(ADDButton);
			
			defineLOWindow.getContentPane().add(BorderLayout.NORTH, panelLink2);
			defineLOWindow.getContentPane().add(BorderLayout.SOUTH, panelnextButton);
			
			
			ADDButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					saveisClicked=true;
					selectedLogOutElement.add(listofLinks.getSelectedIndex());
					selectedLogOutState.add(listPropertyTo.getSelectedIndex());
					 
				JOptionPane.showMessageDialog(null, "relation is added");
				} 
					
			});

			
			}		
			
}
    }
 
    
 int count=0;
    public class  DefineConditionListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		consoleOutput.setText("\n\n"+"You can define a condition.");
		
		

					++count;
					final JFrame firstpropWindow = new JFrame();
					firstpropWindow.setVisible(true);
					firstpropWindow.setSize(300,400);
					firstpropWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					
					JPanel panelText = new JPanel();
					
					JPanel panelList = new JPanel();
					
					
					panelText.setLayout(new BoxLayout (panelText, BoxLayout.Y_AXIS));
				    JLabel linkLabelusertag = new JLabel("Enter username information");
				    JLabel linkLabeluserid = new JLabel("Enter username field id");
				    JLabel linkLabelusername = new JLabel("Enter username");
				    textUsernameid = new JTextField();
				    textUsername = new JTextField();
				    panelText.add(linkLabelusertag);
				    panelText.add(Box.createRigidArea(new Dimension(10, 10)));
				   
				    panelText.add(linkLabeluserid);
				    panelText.add(textUsernameid);
				    panelText.add(linkLabelusername);
				    panelText.add(textUsername);
				  
				    panelText.add(Box.createRigidArea(new Dimension(20, 20))); 
				    
					JLabel linkLabelpwtag = new JLabel("Enter password information");
					JLabel linkLabelpwid = new JLabel("Enter password field id");
					JLabel linkLabelpw = new JLabel("Enter password");
					passwordid = new JTextField();
					passwordname = new JTextField();
					panelText.add(linkLabelpwtag);
				    panelText.add(Box.createRigidArea(new Dimension(10, 10)));
				    panelText.add(linkLabelpwid);
				    panelText.add(passwordid);
				    panelText.add(linkLabelpw);
				    panelText.add(passwordname);
					
				    
					JPanel firstpanelnextButton = new JPanel();
					JButton nextButton = new JButton("NEXT");
					JButton cancelButton = new JButton("CANCEL");
				    firstpanelnextButton.add(nextButton);
				    firstpanelnextButton.add(cancelButton);
			
				    panelList.setLayout(new BoxLayout (panelList, BoxLayout.Y_AXIS));
					panelList.add(Box.createRigidArea(new Dimension(10, 20)));
					
				
					
				    firstpropWindow.getContentPane().add(BorderLayout.NORTH, panelText);
				    firstpropWindow.getContentPane().add(BorderLayout.CENTER, panelList);
				    firstpropWindow.getContentPane().add(BorderLayout.SOUTH, firstpanelnextButton);
				    
				    cancelButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							
							firstpropWindow.dispose();
							
						}
					});
					
				    nextButton.addActionListener(new ActionListener() {
	@Override
		public void actionPerformed(ActionEvent arg0) {
			firstpropWindow.dispose();
			
			smvFile.replaceRange(null, smvFile.getText().toString().indexOf("--NO BROKEN LINK OR DEAD END")  , smvFile.getText().toString().length());
			
			
			final JFrame definecondWindow = new JFrame();
			definecondWindow.setVisible(true);
			definecondWindow.setSize(300,700);
			definecondWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
			JPanel panelLink = new JPanel();
			
			
			
			try {
				listPropertyFrom = new JList(fillStates());
			} catch (IOException e3) {
				
				e3.printStackTrace();
			} 
			listPropertyFrom.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listPropertyFrom.setLayoutOrientation(JList.VERTICAL);
			listPropertyFrom.setVisibleRowCount(-1);
			
			JScrollPane listPropertyFromScroller = new JScrollPane(listPropertyFrom);
			listPropertyFromScroller.setPreferredSize(new Dimension(250, 180));
			
			try {
				listofLinks = new JList(fillElement().toArray());
			} catch (IOException e1) {
				
				e1.printStackTrace();
			} 
			listofLinks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listofLinks.setLayoutOrientation(JList.VERTICAL);
			listofLinks.setVisibleRowCount(-1);
			
			JScrollPane listofLinksScroller = new JScrollPane(listofLinks);
			listofLinksScroller.setPreferredSize(new Dimension(250, 180));
			
			try {
				listPropertyTo = new JList(fillStates());
			} catch (IOException e2) {
				
				e2.printStackTrace();
			} 
			listPropertyTo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listPropertyTo.setLayoutOrientation(JList.VERTICAL);
			listPropertyTo.setVisibleRowCount(-1);
			
			JScrollPane listPropertyTocroller = new JScrollPane(listPropertyTo);
			listPropertyTocroller.setPreferredSize(new Dimension(250, 180));
			
			panelLink.setLayout(new BoxLayout (panelLink, BoxLayout.Y_AXIS));
		    JLabel linkLabel1 = new JLabel("Choose the control type");
			
			panelLink.add(Box.createRigidArea(new Dimension(10, 10)));
			JLabel linkLabel2 = new JLabel("Choose where you enter control");
			panelLink.add(linkLabel2);
		    panelLink.add(listPropertyFromScroller);
		    panelLink.add(Box.createRigidArea(new Dimension(10, 10)));
			JLabel xpathLabel = new JLabel("Choose with which link you enter control");
			panelLink.add(xpathLabel);
		    panelLink.add(listofLinksScroller);
		    JLabel endLinkLabel = new JLabel("Choose with which link you end control");
			panelLink.add(endLinkLabel);
		    panelLink.add(listPropertyTocroller);
		    
		    
			JPanel panelnextButton = new JPanel();
			JButton nextButton2 = new JButton("NEXT");
		    
			panelnextButton.add(nextButton2);
			definecondWindow.getContentPane().add(BorderLayout.NORTH, panelLink);
			definecondWindow.getContentPane().add(BorderLayout.SOUTH, panelnextButton);
			

			nextButton2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					definecondWindow.dispose();
					final JFrame definecond2Window = new JFrame();
					definecond2Window.setVisible(true);
					definecond2Window.setSize(300,500);
					definecond2Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					
					
					JPanel panelLink2 = new JPanel();
					
					try {
						listPropertyTo = new JList(fillStates());
					} catch (IOException e2) {
						
						e2.printStackTrace();
					} 
					listPropertyTo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					listPropertyTo.setLayoutOrientation(JList.VERTICAL);
					listPropertyTo.setVisibleRowCount(-1);
					
					JScrollPane listPropertyToScroller = new JScrollPane(listPropertyTo);
					listPropertyToScroller.setPreferredSize(new Dimension(250, 180));
					
					try {
						listofLinks = new JList(fillElement().toArray());
					} catch (IOException e1) {
						
						e1.printStackTrace();
					} 
					listofLinks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					listofLinks.setLayoutOrientation(JList.VERTICAL);
					listofLinks.setVisibleRowCount(-1);
					
					JScrollPane listofLinksScroller = new JScrollPane(listofLinks);
					listofLinksScroller.setPreferredSize(new Dimension(250, 180));
					
				
					
					panelLink2.setLayout(new BoxLayout (panelLink2, BoxLayout.Y_AXIS));
				    JLabel linkLabel1 = new JLabel("Choose the control type");
					
				    panelLink2.add(Box.createRigidArea(new Dimension(10, 10)));
					JLabel linkLabel2 = new JLabel("Choose the state the logout starts");
					panelLink2.add(linkLabel2);
					panelLink2.add(listPropertyToScroller);
					panelLink2.add(Box.createRigidArea(new Dimension(10, 10)));
					JLabel xpathLabel = new JLabel("Choose with which link you logout");
					panelLink2.add(xpathLabel);
					panelLink2.add(listofLinksScroller);
				    
					JButton conButton = new JButton("CONTINUE");
				    
				    
					JPanel panelnextButton = new JPanel();
					JButton saveButton = new JButton("SAVE");
				    
					panelnextButton.add(conButton);
					panelnextButton.add(saveButton);
					
					definecond2Window.getContentPane().add(BorderLayout.NORTH, panelLink2);
					definecond2Window.getContentPane().add(BorderLayout.SOUTH, panelnextButton);
			        conButton.addActionListener(new LogoutListener());
				
					saveButton.addActionListener(new ActionListener() {
					@Override
						public void actionPerformed(ActionEvent arg0) {
							consoleOutput.setText("\n\n"+"Your condition is added to NuSMV model!");
							saveisClicked=true;
							
							if(defineLOWindow.isVisible()){
								defineLOWindow.dispose();
							}
							
							definecond2Window.dispose();
							
							try {
								
								
								
						        int offSetAssign = smvFile.getText().toString().indexOf("ASSIGN");
						      
						        
						        int offSetnextElement = smvFile.getText().toString().indexOf("next(element):= case");
						        
						        int offSetnext2Element = smvFile.getText().toString().indexOf("webstate="+f.initialize_state().get(listPropertyFrom.getSelectedIndex()).getName()+":{");
						        
						        String webstateStart =f.initialize_state().get(listPropertyFrom.getSelectedIndex()).getName().toString();
						        String total= "webstate="+webstateStart+":{".toString();
						        int numOfChar = total.length();
						        
						        int n=0;
						        for(int i=0;i<f.initialize_edge().size();i++){
						        if(f.initialize_edge().get(i).getFrom().getName().equals(f.initialize_state().get(listPropertyFrom.getSelectedIndex()).getName()))
						        	++n;}
						        if(n>1){
						        smvFile.getDocument().insertString(offSetnext2Element+numOfChar,listofLinks.getSelectedValue().toString().replace('[', '_').replace(']', '_').replace('/', '_')+",", SimpleAttributeSet.EMPTY);
						        }
						        else if(n==1)
						        	 smvFile.getDocument().insertString(offSetnext2Element+numOfChar,listofLinks.getSelectedValue().toString().replace('[', '_').replace(']', '_').replace('/', '_')+",", SimpleAttributeSet.EMPTY);
						        else if(n==0)
						        	smvFile.getDocument().insertString(offSetnextElement+20,System.getProperty("line.separator")+"webstate="+webstateStart+":{"+listofLinks.getSelectedValue().toString().replace('[', '_').replace(']', '_').replace('/', '_')+"};", SimpleAttributeSet.EMPTY);
							    
						        smvFile.getDocument().insertString(offSetAssign+6,System.getProperty("line.separator")+"init(login):=FALSE;", SimpleAttributeSet.EMPTY);
								smvFile.getDocument().insertString(17, System.getProperty("line.separator")+"login: boolean;", SimpleAttributeSet.EMPTY);
							int offSet = smvFile.getText().toString().indexOf("TRUE:dead_end_webstate;");
							f = new IntFormatToNuSMV();
								if(getFilePath().contains("result") || isCrawljax){
								
								
								smvFile.getDocument().insertString(offSet-2,System.getProperty("line.separator")+"webstate="+f.initialize_state().get(listPropertyFrom.getSelectedIndex()).getName()+
										" & "+" next(login)= TRUE & next(element)="+listofLinks.getSelectedValue().toString().replace('[', '_').replace(']', '_').replace('/', '_')+":"+f.initialize_state().get(listPropertyTo.getSelectedIndex()).getName()+";",
										SimpleAttributeSet.EMPTY);
					        
								}
								else if(!(getFilePath().contains("result")) || isCrawljax){
									
									smvFile.getDocument().insertString(offSet-2,System.getProperty("line.separator")+"webstate="+f.initialize_state().get(listPropertyFrom.getSelectedIndex()).getName()+
											" & "+" next(login)=TRUE & next(element)="+listofLinks.getSelectedValue().toString().replace('[', '_').replace(']', '_').replace('/', '_')+":"+f.initialize_state().get(listPropertyTo.getSelectedIndex()).getName()+";"
											, SimpleAttributeSet.EMPTY);
				}
								
								int offSetAc2;
								StringBuffer sb = new StringBuffer("");
								
								if(selectedLogOutState!=null){
								for(int i=0;i<selectedLogOutState.size();i++){
									
									sb.append("webstate= "+f.initialize_state().get((int)selectedLogOutState.get(i)).getName()+" & next(element)= "+f.initialize_element().get((int)selectedLogOutElement.get(i)).getXpath().replace('[', '_').replace(']', '_').replace('/', '_')+":FALSE;");
									sb.append(System.getProperty("line.separator"));
								}
								int offSetAc = smvFile.getText().toString().indexOf("--INDEX IS REACHABLE FROM ALL STATES");
								smvFile.getDocument().insertString(offSetAc,
								"next(login):= case"+
								System.getProperty("line.separator")+"webstate="+f.initialize_state().get(listPropertyFrom.getSelectedIndex()).getName()+"& next(element)= "+listofLinks.getSelectedValue().toString().replace('[', '_').replace(']', '_').replace('/', '_')+":TRUE;"+
								System.getProperty("line.separator")+"webstate="+f.initialize_state().get(listPropertyTo.getSelectedIndex()).getName()+"& next(element)= "+listofLinks.getSelectedValue().toString().replace('[', '_').replace(']', '_').replace('/', '_')+":FALSE;"+
								System.getProperty("line.separator")+sb.toString()+"TRUE: login;"+
								System.getProperty("line.separator")+"esac;"+System.getProperty("line.separator")+
								System.getProperty("line.separator"),SimpleAttributeSet.EMPTY);
								
								}
								else if(selectedLogOutState==null){
									offSetAc2 = smvFile.getText().toString().indexOf("--INDEX IS REACHABLE FROM ALL STATES");
									smvFile.getDocument().insertString(offSetAc2,
									"next(login):= case"+
									System.getProperty("line.separator")+"webstate="+f.initialize_state().get(listPropertyFrom.getSelectedIndex()).getName()+"& next(element)= "+listofLinks.getSelectedValue().toString().replace('[', '_').replace(']', '_').replace('/', '_')+":TRUE;"+
									System.getProperty("line.separator")+"webstate="+f.initialize_state().get(listPropertyTo.getSelectedIndex()).getName()+"& next(element)= "+listofLinks.getSelectedValue().toString().replace('[', '_').replace(']', '_').replace('/', '_')+":FALSE;"+
									System.getProperty("line.separator")+"TRUE: login;"+
									System.getProperty("line.separator")+"esac;"+System.getProperty("line.separator")+
									System.getProperty("line.separator"),SimpleAttributeSet.EMPTY);
								
								}
						     		
									
								} catch ( BadLocationException e) {
									
									e.printStackTrace();
								} catch (IOException e) {
									
									e.printStackTrace();
								}
						
						}
			});
		
				}
	        });
				
				
				apropertyButton.setEnabled(true);}
				
			});
	
              }	}
 
    public class AccessPropertyListener implements ActionListener{
    	
        @Override
		public void actionPerformed(ActionEvent arg0) {
        	
        	
        	
        	consoleOutput.setText("\n\n"+"You can define an access control property.");
        
        	final JFrame selectWindow = new JFrame("Access Control");
    		selectWindow.setVisible(true);
    		selectWindow.setSize(300,300);
    		selectWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		
    		JPanel panelSelect = new JPanel();	
        	JPanel panelButton = new JPanel();
    		
    		final JRadioButton brokenLinkProperty = new JRadioButton("Check absence of dead end");
    		//final JRadioButton indexreachability = new JRadioButton("Check that all states are reachable from index");
    		final JRadioButton login = new JRadioButton("Check accessibility of one state");
    		
            JButton nextButton = new JButton("NEXT");
            panelButton.add(nextButton);
        	
		
			panelSelect.add(brokenLinkProperty);
		    //panelSelect.add(indexreachability);
			panelSelect.add(login);
		
			panelSelect.setLayout(new BoxLayout (panelSelect, BoxLayout.Y_AXIS));
			selectWindow.getContentPane().add(BorderLayout.NORTH, panelSelect);
		
			selectWindow.getContentPane().add(BorderLayout.SOUTH, panelButton);
            
            
  nextButton.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				selectWindow.dispose();
				
				if(login.isSelected()){
				
				final JFrame propWindow = new JFrame("Access Control");
				propWindow.setVisible(true);
				propWindow.setSize(300,300);
				propWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		JPanel panelBox = new JPanel();
		JPanel panelList = new JPanel();
		JPanel panelbutton = new JPanel();
		
		propWindow.add(panelBox);
		propWindow.add(panelList);
		propWindow.add(panelbutton);
		
		
		
		
		try {
			listofLinks = new JList(fillStates());
		} catch (IOException e1) {
			
			e1.printStackTrace();
		} 
		listofLinks.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listofLinks.setLayoutOrientation(JList.VERTICAL);
		listofLinks.setVisibleRowCount(-1);
		
		JScrollPane listofLinksScroller = new JScrollPane(listofLinks);
		listofLinksScroller.setPreferredSize(new Dimension(250, 200));
		
		panelList.setLayout(new BoxLayout (panelList, BoxLayout.Y_AXIS));
		JLabel linkLabel = new JLabel("Choose link which requires control");
		panelList.add(linkLabel);
		panelList.add(listofLinksScroller);
		
	    JButton addbutton = new JButton("Add");
		panelbutton.add(addbutton);
		
		propWindow.getContentPane().add(BorderLayout.NORTH, panelList);
		propWindow.getContentPane().add(BorderLayout.SOUTH, panelbutton);
		
		addbutton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				
				propWindow.dispose();
				ArrayList<Integer> setOfStates = new ArrayList<Integer>();
				for(int i=0;i<f.initialize_state().size();i++){
					if(listofLinks.isSelectedIndex(i))
						setOfStates.add(i);
					
				
				}
				
			try {
				 
				 
				
				 for(int i=0;i<setOfStates.size();i++){
				 smvFile.append(System.getProperty("line.separator"));
				 smvFile.append("--ACCESS CONTROL PROPERTY");
				 smvFile.append(System.getProperty("line.separator"));
	             smvFile.append("SPEC !EF((webstate="+ f.initialize_state().get(setOfStates.get(i)).getName()+")"+" & login =FALSE);");
	             }
				 
				  for(int i=0;i<setOfStates.size();i++){
					  textForProperties .setText(System.getProperty("line.separator")
					 +"--ACCESS CONTROL PROPERTY"
					 +System.getProperty("line.separator")
		             +"SPEC !EF((webstate="+ f.initialize_state().get(setOfStates.get(i)).getName()+")"+" & login =FALSE);");
				  }
				  consoleOutput.setText("\n\n"+"Your access control property is added!");
                 propWindow.dispose();	 
					
				} catch (Exception  e) {
					
					e.printStackTrace();
				}
			
			propWindow.dispose();
			}
			});
				}
				
				if(brokenLinkProperty.isSelected()){
					
					final JFrame brokenLinkWindow = new JFrame();
					brokenLinkWindow.setVisible(true);
					brokenLinkWindow.setSize(800,400);
					brokenLinkWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			
			JPanel panelBL = new JPanel();
			JPanel panelListBL = new JPanel();
			
			brokenLinkPropertyTA = new JTextArea();
			brokenLinkPropertyTA.setWrapStyleWord(true);
			JLabel Pretext = new JLabel("<html> Default Broken Link property (see right box) is wrtten as index state is public (does not require login) <br/> if there is more please select from below list and presss ADD </html>", SwingConstants.CENTER);
			
			
			
			JLabel label1 = new JLabel("List of States (name/url)", SwingConstants.LEFT);
			JLabel label2 = new JLabel("Property");
			
			
			String loginAdd = " & login = TRUE";
			 final StringBuffer brokenLinkProp2 = new StringBuffer();
			 for(int i=0;i<f.initialize_state().size();i++){
				 if(i==0){
			     brokenLinkProp2.append("SPEC AG(webstate="+f.initialize_state().elementAt(i).getName().toString()+loginAdd+" -> !EX(webstate=dead_end_webstate));");}
				 else brokenLinkProp2.append("SPEC AG(webstate="+f.initialize_state().elementAt(i).getName().toString()+" -> !EX(webstate=dead_end_webstate));");
				 brokenLinkProp2.append(System.getProperty("line.separator"));
					 }
			 
			brokenLinkPropertyTA.setText(brokenLinkProp2.toString()); 
			JScrollPane TextScroller = new JScrollPane(brokenLinkPropertyTA);
			TextScroller.setPreferredSize(new Dimension(250, 180));
			
			TextScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			TextScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

			
			try {
				listPropertyFrom = new JList(fillStates());
			} catch (IOException e) {
				
				e.printStackTrace();
			} 
			listPropertyFrom.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			listPropertyFrom.setLayoutOrientation(JList.VERTICAL);
			listPropertyFrom.setVisibleRowCount(-1);
			
			JScrollPane listofBGScroller = new JScrollPane(listPropertyFrom);
			listofBGScroller.setPreferredSize(new Dimension(250, 180));
			listofBGScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			listofBGScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			
			JButton buttonAdd = new JButton("ADD");
			final JButton Savebutton = new JButton("SAVE");
			
			panelBL.setLayout(new BoxLayout(panelBL, BoxLayout.Y_AXIS));
			
			
			
			JPanel panel2 = new JPanel();
			panelBL.add(Pretext);
			
			JPanel panelLable = new JPanel();
            panelBL.add(Box.createRigidArea(new Dimension(0, 20)));
		    panel2.add(listofBGScroller);
			panel2.add(buttonAdd);
			panel2.add(TextScroller);
			
			JPanel panelButton = new JPanel();
			panelButton.add(Savebutton);
			
					
		buttonAdd.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
		
					String loginAdd = " & login = TRUE";
					ArrayList<Integer> selectedValue = new ArrayList<Integer>();
					
					for(int i=0;i<f.initialize_state().size();i++){
						if(listPropertyFrom.isSelectedIndex(i))
							selectedValue.add(i);
						else selectedValue.add(-100);
					
					}
					
					
					 final StringBuffer brokenLinkProp2 = new StringBuffer();
					 
					
					 for(int i=0;i<f.initialize_state().size();i++){
						 
					 if(selectedValue.get(i)==i  ){
		            brokenLinkProp2.append("SPEC AG(webstate="+f.initialize_state().get((int)selectedValue.get(i)).getName().toString()+loginAdd+" -> !EX(webstate=dead_end_webstate));");
			
					brokenLinkProp2.append(System.getProperty("line.separator"));
					 }
					 else if(selectedValue.get(i)!=i || selectedValue.get(i)==-100) {
					brokenLinkProp2.append("SPEC AG(webstate="+f.initialize_state().get(i).getName().toString()+" -> !EX(webstate=dead_end_webstate));");
					
					brokenLinkProp2.append(System.getProperty("line.separator"));
						
					 } 
					 }
				
					 
					 brokenLinkPropertyTA.setText(""); 
					 brokenLinkPropertyTA.setText(brokenLinkProp2.toString()); 
			}});
					 Savebutton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							
							smvFile.append("--NO BROKEN LINK OR DEAD END");
							smvFile.append(System.getProperty("line.separator"));
							smvFile.append(brokenLinkProp2.toString());
							brokenLinkWindow.dispose();
							
							// TODO Auto-generated method stub
							
						}
					});
					 
					 
					 brokenLinkWindow.add(panelBL);
					 brokenLinkWindow.add(panelButton);
					 brokenLinkWindow.getContentPane().add(BorderLayout.NORTH, panelBL);
					 brokenLinkWindow.getContentPane().add(BorderLayout.CENTER, panel2);
					 brokenLinkWindow.getContentPane().add(BorderLayout.SOUTH, panelButton);	 
}
			

				
	
				
				
				}});
  
  
        
        }}
			

 
    public class RunSMVListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		consoleOutput.setText("\n\n"+"The model checker is running...");
		
		try {
			IntFormatToNuSMV f = new IntFormatToNuSMV();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
	
		nusmv = new NuSMVExecuter();
		BufferedWriter wr;
		try {
			wr = new BufferedWriter(new FileWriter(nusmv.getsmvInputFileName()));
		
		    wr.write(smvFile.getText());
		    wr.flush();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
       
       
	   smvFile.setText(ReadFile(nusmv.getSmvOutputFile()).toString());
		
	
			File smvoutput = new File("lib\\output.txt");
			BufferedWriter wE;
			try {
				wE = new BufferedWriter(new FileWriter(smvoutput));
			
			    wE.write(smvFile.getText());
			    wE.flush();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			try {
				
				
				sc = new CounterexampleToScript();
			
				
				if(count>0){
					String textusername = textUsername.getText();
					String textusernameid = textUsernameid.getText();
					String textpasswordid =  passwordid.getText();
					String textpasswordname = passwordname.getText();
					String xpathlogin = listofLinks.getSelectedValue().toString();	
					
					sc.initializeOutputTraceWithLogin();
					sc.initializeWithLogin(xpathlogin, textusername, textusernameid, textpasswordname, textpasswordid,isCrawljax);
				}
				
				else{
				sc.initializeOutputTrace();
				sc.initalizeJunit(isCrawljax);}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	
			runSMVbutton.setEnabled(false);
			apropertyButton.setEnabled(false);
			lpropertyButton.setEnabled(false);
			defineCondition.setEnabled(false);
			runTestCaseButton.setEnabled(true);
			
			
			
			consoleOutput.setText("\n\n"+"The NuSMV execution is complete! "+"\n\n"+"Now you have the output of the execution in a sperate window."+"\n\n"+"You can replay the detected errors in the output file");
			JButton buttonhiDE = new JButton("HIDE");    
			TextBoxFrame = new JFrame("Output of NuSMV");
			   TextBoxFrame.setVisible(true);
			   TextBoxFrame.setSize(400,400);
			   TextBoxFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			   JPanel hidePanel = new JPanel();
			   hidePanel.add(buttonhiDE);
			   TextBoxFrame.getContentPane().add(BorderLayout.CENTER, panel);
			   TextBoxFrame.getContentPane().add(BorderLayout.SOUTH, hidePanel);
			   buttonhiDE.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					TextBoxFrame.setVisible(false);
					
				}
			});
		   
	}

 }
 
    public class CreateTCistener implements ActionListener{
    
	@Override
	public void actionPerformed(ActionEvent arg0) {
	
		File smvoutput = new File("lib\\output.txt");
		BufferedWriter wr;
		try {
			wr = new BufferedWriter(new FileWriter(smvoutput));
		
		    wr.write(smvFile.getText());
		    wr.flush();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try {
			
			
			 sc = new CounterexampleToScript();
	
			if(count>0){
				String textusername = textUsername.getText();
				String textusernameid = textUsernameid.getText();
				String textpasswordid =  passwordid.getText();
				String textpasswordname = passwordname.getText();
				String xpathlogin = listofLinks.getSelectedValue().toString();	
			
				sc.initializeOutputTraceWithLogin();
				sc.initializeWithLogin(xpathlogin, textusername, textusernameid, textpasswordname, textpasswordid,isCrawljax);
			}
			
			else{
			sc.initializeOutputTrace();
			sc.initalizeJunit(isCrawljax);}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	
		runTestCaseButton.setEnabled(true);
		smvFile.setText("TEST CASES ARE CREATED");
		
		consoleOutput.setText("\n\n"+"Test Cases are created!");
	
	}
	
 }
 
    public class RunTCListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		

		//while (!(new File("C:\\Users\\yudum\\workspace\\XMLtoSMV\\src\\junit\\Template0.java")).exists()) {
		while (!(new File("src\\junit\\Template0.java")).exists()) {
		    try { 
		    	
		        Thread.sleep(100);
		    } catch (InterruptedException ie) {    }
		}
		
		consoleOutput.setText("\n\n"+"The errors we found are on replay now!");
		
		smvFile.setText("TEST CASES ARE RUNNING ON FIREFOX...");
		
		try {
			sc.runOnSelenium();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		consoleOutput.setText("\n\n"+"The replay is complete!");
		
	}
	
}
 
    public StringBuffer ReadFile(File file){
		 BufferedReader br = null;
		 StringBuffer linesOfXML = new StringBuffer();
			try {
	 
				String sCurrentLine;
	 
				if(file ==null){
					System.out.println("File could not be found");
				}else
				br = new BufferedReader(new FileReader(file));
				while ((sCurrentLine = br.readLine()) != null) {
					linesOfXML.append(sCurrentLine);
					linesOfXML.append(System.getProperty("line.separator"));
				}}catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (br != null)br.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}}
		
		
		return linesOfXML;
}
  
  

}