package fiit.nlp.NegatedKeywordsExtractor.controller;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractAction;
import javax.swing.SwingWorker;

import fiit.nlp.NegatedKeywordsExtractor.model.core.*;
import fiit.nlp.NegatedKeywordsExtractor.view.IMainWindow;

public class MainWindowController {
	private IMainWindow view;
	private ExtractorCore model;
	
	public MainWindowController(IMainWindow view, ExtractorCore model) {
		this.view = view;
		this.model = model;
		
		view.setLoadCorpusAction(new LoadCorpusAction());
	}
	
	// http://stackoverflow.com/questions/15728619/using-a-jfilechooser-with-swing-gui-classes-and-listeners/15729267#15729267
	// http://stackoverflow.com/questions/13575456/jtabbedpane-using-multiple-classes
	// http://stackoverflow.com/questions/30607591/java-swingpropertychangesupport
	
	public class LoadCorpusAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			LoadCorpusWorker loadCorpusWorker = new LoadCorpusWorker(new File("C://abc"));
			loadCorpusWorker.addPropertyChangeListener(new LoadCorpusWorkerListener());
			loadCorpusWorker.execute();
		}
		
		public class LoadCorpusWorker extends SwingWorker<Void, Void> {
			private File path;
						
			public LoadCorpusWorker(File path) {
				this.path = path;
			}
			
			@Override
			protected Void doInBackground() throws Exception {
				CorpusReaderFileSystem fs = new CorpusReaderFileSystem(path);
//				fs.createDocument(filename);
				model.loadCorpus(path.getCanonicalPath());
				return null;
			}
		}
		
		public class LoadCorpusWorkerListener implements PropertyChangeListener {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
		      if (SwingWorker.StateValue.DONE == evt.getNewValue()) {
		    	  LoadCorpusWorker loadCorpusWorker = (LoadCorpusWorker) evt.getSource();
		          try {
		             loadCorpusWorker.get();
		             //model.setCorpus(loadCorpusWorker.get());
		          } catch (InterruptedException e) {
		             e.printStackTrace();
		          } catch (ExecutionException e) {
		             e.printStackTrace();
		          }
		       }			
			}
		}
	}
}
