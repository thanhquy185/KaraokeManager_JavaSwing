package PL;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdminImagePL extends JPanel {
	public AdminImagePL() {
		// <===== Cấu trúc của Image Panel =====>
		// - Tuỳ chỉnh Image Label
		ImageIcon image = new ImageIcon(CommonPL.getMiddlePathToShowIcon() + "big-image.jpg");
		Image scaledImage = image.getImage().getScaledInstance(
				CommonPL.getScreenWidthByOwner() - CommonPL.getLeftMenuWidth(), CommonPL.getScreenHeightByOwner(),
				Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		JLabel imageLabel = new JLabel(scaledIcon);

		// - Tuỳ chỉnh Image Panel
		JPanel imagePanel = new JPanel(new BorderLayout());
		imagePanel.add(imageLabel, BorderLayout.CENTER);
		imagePanel.setBounds(0, 0, CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
		// <====================== ======================>

		// Đĩnh nghĩa tính chất cho Dashboard Manager PL
		this.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
		this.setLayout(null);
		this.setSize(CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
		this.setBackground(Color.decode("#E3F2FD"));
		this.setLayout(null);
		this.add(imagePanel);
	}
}
