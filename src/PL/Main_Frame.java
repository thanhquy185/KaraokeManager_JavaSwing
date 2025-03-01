package PL;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main_Frame extends JFrame {
	private static Main_Frame instance;

	public static Main_Frame getInstance() {
		if (Main_Frame.instance == null) {
			Main_Frame.instance = new Main_Frame();
		}
		return Main_Frame.instance;
	}

	public Main_Frame() {
		// Khởi tạo dữ liệu ban đầu
		// - Dữ liệu về các địa chỉ có ở Việt Nam
		CommonPL.renderAddressInfo();
		// - Dữ liệu về bằng cấp và trường cấp bằng tương ứng ở Việt Nam
		CommonPL.renderDiplomaInfo();

		// Mặc định sẽ luôn hiện Trang Đăng nhập - Đăng ký
		AdminCardPL adminCardPL = new AdminCardPL();
//		Main_LoginRegisterCardPL loginRegisterCardPL = new Main_LoginRegisterCardPL();
		this.add(adminCardPL);

		// Đĩnh nghĩa tính chất của Frame
		this.setTitle("Hospital Manager");
		this.setSize(CommonPL.getScreenWidthByOwner(), CommonPL.getScreenHeightByOwner());
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

	public void changeFrameContent(JPanel currentCardFormPanel) {
		this.getContentPane().removeAll();
		this.add(currentCardFormPanel);
		this.revalidate();
		this.repaint();
	}
}
