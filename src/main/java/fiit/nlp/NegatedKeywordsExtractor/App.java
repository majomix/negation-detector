package fiit.nlp.NegatedKeywordsExtractor;

import java.awt.EventQueue;
import fiit.nlp.NegatedKeywordsExtractor.model.core.NegationDetectorFacade;

public class App 
{
    public static void main( String[] args )
    {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NegationDetectorFacade model = new NegationDetectorFacade();
					//MainWindow window = new MainWindow();
					//window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }

}
