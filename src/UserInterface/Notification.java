package UserInterface;

import java.awt.Color;
import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

// displays a pop-up notification with the payment/cancelation receipt
public class Notification extends JDialog {

	private final JPanel contentPanel = new JPanel();


	public Notification(String notif) {
		setBounds(100, 100, 450, 300);
        setTitle("Notification Window");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setBackground(Color.CYAN);
		contentPanel.setLayout(null);
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 11, 416, 208);
			contentPanel.add(scrollPane);
			{
				JTextArea textArea = new JTextArea();
				scrollPane.setViewportView(textArea);
				textArea.setText(notif);
			}
		}
	}

}
