import javax.swing.SwingUtilities;
public class Main {
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            Visualizer v = new Visualizer();
	            v.setVisible(true);
	        });
	 }
}