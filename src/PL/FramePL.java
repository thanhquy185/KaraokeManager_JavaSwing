package PL;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FramePL extends JFrame {
	// Properties
	// - Mục đích là chỉ có một frame được sử dụng
	private static FramePL instance;

	// Constructors
	public FramePL() {
		//
		FramePL.instance = this;

		// Khởi tạo dữ liệu ban đầu
		// - Dữ liệu về các địa chỉ có ở Việt Nam
		CommonPL.renderAddressInfo();

		// Mặc định sẽ luôn hiện Trang Đăng nhập - Đăng ký
		// AdminCardPL adminCardPL = new AdminCardPL();
		LoginRegisterPL loginRegisterPL = new LoginRegisterPL();
		this.add(loginRegisterPL);

		// Đĩnh nghĩa tính chất của Frame
		this.setTitle("Karaoke Manager");
		this.setSize(CommonPL.getScreenWidthByOwner(), CommonPL.getScreenHeightByOwner());
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

	// Methods
	public static FramePL getInstance() {
		if (FramePL.instance == null) {
			FramePL.instance = new FramePL();
		}
		return FramePL.instance;
	}

	public void changeFrameContent(JPanel currentCardFormPanel) {
		this.getContentPane().removeAll();
		this.add(currentCardFormPanel);
		this.revalidate();
		this.repaint();
	}
}
