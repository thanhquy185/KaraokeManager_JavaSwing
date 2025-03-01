package PL;

import javax.swing.JPanel;

public class AdminCardPL extends JPanel {
	private static AdminCardPL instance;

	public static AdminCardPL getInstance() {
		if (AdminCardPL.instance == null) {
			AdminCardPL.instance = new AdminCardPL();
		}
		return AdminCardPL.instance;
	}

	public AdminCardPL() {
		AdminCardPL.instance = this;

		// Menu của Quản lý
		AdminMenuPL adminMenuPL = new AdminMenuPL();
		// Main của Quản lý (mặc định là Tóm tắt)
		Admin_DashboardManagerPL dashboardManagerPL = new Admin_DashboardManagerPL();

		// Đĩnh nghĩa các tính chất của Admin Card
		this.setSize(CommonPL.getScreenWidthByOwner(), CommonPL.getScreenHeightByOwner());
		this.setLayout(null);
		this.add(adminMenuPL);
		this.add(dashboardManagerPL);
	}

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
