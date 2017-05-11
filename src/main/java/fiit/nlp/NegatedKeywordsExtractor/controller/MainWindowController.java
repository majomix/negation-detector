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
	private NegationDetectorFacade model;
	
	public MainWindowController(IMainWindow view, NegationDetectorFacade model) {
		this.view = view;
		this.model = model;
		
		view.setLoadCorpusAction(new LoadCorpusAction());
	}
	
	public class LoadCorpusAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			LoadCorpusWorker loadCorpusWorker = new LoadCorpusWorker(new File(""));
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
