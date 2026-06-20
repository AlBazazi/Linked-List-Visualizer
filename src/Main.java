import javax.swing.SwingUtilities;

import components.Visualizer;
public class Main {
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(Visualizer::new);
	 }
}