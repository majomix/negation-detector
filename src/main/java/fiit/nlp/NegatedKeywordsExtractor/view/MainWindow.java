package fiit.nlp.NegatedKeywordsExtractor.view;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class MainWindow extends JFrame {

	private JFrame frmNegatedKeywordsExtractor;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmLoadCorpus;
	private JMenuItem mntmCloseCorpus;
	private JMenuItem mntmExit;
	private JSeparator fileMenuSeparator;
	private JMenu mnHelp;
	private JMenuItem mntmAbout;
	private JTabbedPane tabbedPane;
	private JSplitPane corpusTab;
	private JPanel documentListPanel;
	private JScrollPane scrollPane;
	private JList list;
	private JPanel documentDetailsPanel;
	private JPanel plainTextPanel;
	private JPanel sentencesPanel;
	private JScrollPane scrollPane_1;
	private JButton btnToggleAdvancedView;
	private JTextArea textArea;
	private JPanel panel;

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/fiit/nlp/NegatedKeywordsExtractor/view/Icon.png")));
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		setTitle("Negated Keywords Extractor (November 11, 2016)");
		setBounds(100, 100, 925, 526);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmLoadCorpus = new JMenuItem("Load corpus");
		mnFile.add(mntmLoadCorpus);
		
		mntmCloseCorpus = new JMenuItem("Close corpus");
		mnFile.add(mntmCloseCorpus);
		
		fileMenuSeparator = new JSeparator();
		mnFile.add(fileMenuSeparator);
		
		mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		corpusTab = new JSplitPane();
		corpusTab.setBorder(null);
		corpusTab.setResizeWeight(0.15);
		corpusTab.setDividerSize(2);
		tabbedPane.addTab("Corpus", null, corpusTab, null);
		
		documentListPanel = new JPanel();
		documentListPanel.setBorder(new TitledBorder(null, "List of documents", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		corpusTab.setLeftComponent(documentListPanel);
		documentListPanel.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		documentListPanel.add(scrollPane, BorderLayout.CENTER);
		
		list = new JList();
		scrollPane.setViewportView(list);
		
		documentDetailsPanel = new JPanel();
		documentDetailsPanel.setBorder(null);
		corpusTab.setRightComponent(documentDetailsPanel);
		documentDetailsPanel.setLayout(new CardLayout(0, 0));
		
		plainTextPanel = new JPanel();
		plainTextPanel.setBorder(new TitledBorder(null, "Plain text", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		documentDetailsPanel.add(plainTextPanel, "name_28419895800522");
		plainTextPanel.setLayout(new BorderLayout(0, 0));
		
		scrollPane_1 = new JScrollPane();
		plainTextPanel.add(scrollPane_1, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		
		btnToggleAdvancedView = new JButton("Toggle advanced view");
		plainTextPanel.add(btnToggleAdvancedView, BorderLayout.SOUTH);
		
		sentencesPanel = new JPanel();
		documentDetailsPanel.add(sentencesPanel, "name_28422019300242");
		sentencesPanel.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		sentencesPanel.add(panel, BorderLayout.SOUTH);
	}

}
