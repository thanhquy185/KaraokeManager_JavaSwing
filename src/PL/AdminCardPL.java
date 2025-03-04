package PL;

import javax.swing.JPanel;

public class AdminCardPL extends JPanel {
	// Properties
	// - Mục đích là chỉ có một admin card được sử dụng
	private static AdminCardPL adminCardPL;

	// Constructors
	public AdminCardPL() {
		//
		AdminCardPL.adminCardPL = this;

		// Menu của Quản lý
		AdminMenuPL adminMenuPL = new AdminMenuPL();
		// Main của Quản lý (mặc định là Tóm tắt)
		AdminImagePL adminImagePL = new AdminImagePL();
//		Admin_DashboardManagerPL dashboardManagerPL = new Admin_DashboardManagerPL();

		// Đĩnh nghĩa các tính chất của Admin Card
		this.setSize(CommonPL.getScreenWidthByOwner(), CommonPL.getScreenHeightByOwner());
		this.setLayout(null);
		this.add(adminMenuPL);
		this.add(adminImagePL);
	}

	public static AdminCardPL getInstance() {
		if (AdminCardPL.adminCardPL == null) {
			AdminCardPL.adminCardPL = new AdminCardPL();
		}
		return AdminCardPL.adminCardPL;
	}

	// Methods
	public void changeAdminMenu(JPanel currentAdminMenuPanel) {
		if (this.getComponents().length >= 2) {
			this.remove(1);
		}
		this.add(currentAdminMenuPanel);
		this.revalidate();
		this.repaint();
	}

	public void changeAdminMain(JPanel currentAdminMainPanel) {
		if (this.getComponents().length >= 2) {
			this.remove(1);
		}
		this.add(currentAdminMainPanel);
		this.revalidate();
		this.repaint();
	}
}
