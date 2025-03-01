package PL;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class AdminMenuPL extends JPanel {
	// Các Font
	private Font fontTitle = new Font("Arial", Font.BOLD, 28);
	private Font fontSubTitle = new Font("Arial", Font.BOLD, 22);
	// Các Color
	private Color colorBackgroundInLeftMenu = Color.WHITE;
	private Color colorBackgroundHoverInLeftMenu = Color.WHITE;
	private Color colorForegroundInLeftMenu = Color.BLACK;
	private Color colorForegroundHoverInLeftMenu = Color.decode("#1976D2");
	private Color colorBackgroundInMain = Color.decode("#E3F2FD");
	// Các Component
	private JLabel adminLabel;
	private JPanel linePanel;
	// - Menu chính
	private JButton dashboardManagerButton;
	private JButton roomStatusManagerButton;
	private JButton orderManagerButton;
	private JButton menuManagerButton;
	private JButton customerManagerButton;
	private JButton accountManagerButton;
	private JButton supplierManagerButton;
	private JButton inputTicketManagerButton;
	private JButton discountManagerButton;
	private JButton ingredientManagerButton;
	private JButton employeeManagerButton;
	private JButton calenderManagerButton;
	private JButton historyManagerButton;
	private JButton logoutManagerButton;
	private JPanel mainMenuPanel;

	public AdminMenuPL() {
		// <===== Cấu trúc của Admin Label =====>
		// - Tuỳ chỉnh Admin Label
		adminLabel = CommonPL.getTitleLabel("QUẢN LÝ", colorForegroundInLeftMenu, fontTitle, SwingConstants.CENTER,
				SwingConstants.CENTER);
		adminLabel.setBounds(10, 0, 220, 64);

		// - Vẽ đường thẳng
		linePanel = new CommonPL.RoundedPanel(8);
		linePanel.setBounds(10, 64, CommonPL.getLeftMenuWidth() - 20, 6);
		linePanel.setBackground(colorForegroundInLeftMenu);
		// <==================== ====================>

		// <===== Cấu trúc của Main Menu Panel =====>
		// - Tuỳ chỉnh Dashboard Manager Button
		dashboardManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "dashboard-icon.png", "Thống kê", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		dashboardManagerButton.setBounds(0, 0, CommonPL.getLeftMenuWidth(), 60);

		// - Tuỳ chỉnh Room Status Button
		roomStatusManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "reload-icon.png", "Tình trạng phòng", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		roomStatusManagerButton.setBounds(0, 60, CommonPL.getLeftMenuWidth(), 60);

		// - Tuỳ chỉnh Order Button
		orderManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "ring-icon.png", "Đặt món", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		orderManagerButton.setBounds(0, 120, CommonPL.getLeftMenuWidth(), 60);

		// - Tuỳ chỉnh Menu Manager Button
		menuManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "menu-icon.png", "Thực đơn", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		menuManagerButton.setBounds(0, 180, CommonPL.getLeftMenuWidth(), 60);

		// - Tuỳ chỉnh Customer Manager Button
		discountManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "discount-icon.png", "Khuyến mãi", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		discountManagerButton.setBounds(0, 240, CommonPL.getLeftMenuWidth(), 60);

		// - Tuỳ chỉnh Customer Manager Button
		customerManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "customer-icon.png", "Khách hàng", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		customerManagerButton.setBounds(0, 300, CommonPL.getLeftMenuWidth(), 60);

		// - Tuỳ chỉnh Account Manager Button
		accountManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "account-icon.png", "Người dùng", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		accountManagerButton.setBounds(0, 360, CommonPL.getLeftMenuWidth(), 60);

		// - Tuỳ chỉnh Customer Manager Button
		supplierManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "supplier-icon.png", "Nhà cung cấp", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		supplierManagerButton.setBounds(0, 420, CommonPL.getLeftMenuWidth(), 60);

		// - Tuỳ chỉnh Medical Record Manager Button
		inputTicketManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "input-ticket-icon.png", "Phiếu nhập", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		inputTicketManagerButton.setBounds(0, 480, CommonPL.getLeftMenuWidth(), 60);

		// - Tuỳ chỉnh Sick Manager Button
		ingredientManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "ingredient-icon.png", "Nguyên liệu", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		ingredientManagerButton.setBounds(0, 540, CommonPL.getLeftMenuWidth(), 60);

//		// - Tuỳ chỉnh Employee Manager Button
//		employeeManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
//				CommonPL.getMiddlePathToShowIcon() + "employee-icon.png", "Nhân viên", colorBackgroundInLeftMenu,
//				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
//				fontSubTitle);
//		employeeManagerButton.setBounds(0, 600, CommonPL.getLeftMenuWidth(), 60);
//
//		// - Tuỳ chỉnh Account Manager Button
//		calenderManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
//				CommonPL.getMiddlePathToShowIcon() + "calender-icon.png", "Lịch làm việc", colorBackgroundInLeftMenu,
//				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
//				fontSubTitle);
//		calenderManagerButton.setBounds(0, 660, CommonPL.getLeftMenuWidth(), 60);
//
//		// - Tuỳ chỉnh Account Manager Button
//		historyManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
//				CommonPL.getMiddlePathToShowIcon() + "clock-icon.png", "Lịch sử ca", colorBackgroundInLeftMenu,
//				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
//				fontSubTitle);
//		historyManagerButton.setBounds(0, 600, CommonPL.getLeftMenuWidth(), 60);
//
//		// - Tuỳ chỉnh Account Manager Button
		logoutManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "logout-icon.png", "Đăng xuất", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		logoutManagerButton.setBounds(0, 600, CommonPL.getLeftMenuWidth(), 60);

		// - Tuỳ chỉnh Main Menu Panel
		mainMenuPanel = new JPanel();
		mainMenuPanel.setLayout(null);
		mainMenuPanel.setBounds(0, 120, CommonPL.getLeftMenuWidth(), 660);
		mainMenuPanel.setBackground(Color.WHITE);
		mainMenuPanel.add(dashboardManagerButton);
		mainMenuPanel.add(roomStatusManagerButton);
		mainMenuPanel.add(orderManagerButton);
		mainMenuPanel.add(discountManagerButton);
		mainMenuPanel.add(menuManagerButton);
		mainMenuPanel.add(customerManagerButton);
		mainMenuPanel.add(accountManagerButton);
		mainMenuPanel.add(supplierManagerButton);
		mainMenuPanel.add(inputTicketManagerButton);
		mainMenuPanel.add(ingredientManagerButton);
//		mainMenuPanel.add(employeeManagerButton);
//		mainMenuPanel.add(calenderManagerButton);
//		mainMenuPanel.add(historyManagerButton);
		mainMenuPanel.add(logoutManagerButton);
		// <==================== ====================>

		// Định nghĩa các tính chất cho Admin Menu PL
		this.setLayout(null);
		this.setSize(CommonPL.getLeftMenuWidth(), CommonPL.getScreenHeightByOwner());
		this.setBounds(0, 0, CommonPL.getLeftMenuWidth(), CommonPL.getScreenHeightByOwner());
		this.setBackground(Color.WHITE);
		this.add(adminLabel);
		this.add(linePanel);
		this.add(mainMenuPanel);

		// Mặc định mục "Thống kê" sẽ được nhấn đầu tiên
		dashboardManagerButton.getComponent(1).setForeground(colorForegroundHoverInLeftMenu);
		MouseListener[] listeners = dashboardManagerButton.getMouseListeners();
		for (MouseListener listener : listeners) {
			dashboardManagerButton.removeMouseListener(listener);
		}

		// Thiết lập sự kiện cập nhật lại các mục theo chi tiết quyền
		renderButtonsInMenu();

		// Thiết lập sự kiện khi nhấn vào các mục
		menuButtonEvents();
	}

	// Hàm trả về mainMenuPanel
	public JPanel getMainMenuPanel() {
		return mainMenuPanel;
	}

	// Hàm cập nhật lại các mục theo chi tiết quyền của Người dùng hiện tại
	private void renderButtonsInMenu() {
		// - Truy vấn chi tiết quyền của Người dùng hiện tại đang sử dụng
		String privilegeDetail = "Thống kê,Tình trạng phòng,Đặt món,Thực đơn,Khuyến mãi,Khách hàng,Người dùng,Nhà cung cấp,Phiếu nhập,Nguyên liệu,Nhân viên,Lịch làm việc,Lịch sử ca";
		String[] privilegeDetailSplit = privilegeDetail.split(",");

		// - Lấy ra tất cả các JButton có ở Menu Panel và ẩn đi hết
		ArrayList<JButton> buttonsInMainMenu = CommonPL.getAllButtons(mainMenuPanel);
		ArrayList<JButton> buttons = new ArrayList<>();
		buttons.addAll(buttonsInMainMenu);
		for (JButton button : buttons) {
			button.setVisible(false);
		}

		// - Duyệt qua từng giá trị trong mảng rồi thêm mục tương ứng
		int i = 0;
		for (String privilege : privilegeDetailSplit) {
			for (JButton button : buttons) {
				String textInButton = ((JLabel) button.getComponent(1)).getText();
				if (privilege.equals(textInButton)) {
					button.setVisible(true);
					button.setBounds(0, i, CommonPL.getLeftMenuWidth(), 60);
					i += 60;
				}
			}
		}
		// - Mặc định là 'Đăng xuất' luôn có và ở vị trí cuối cùng
		logoutManagerButton.setVisible(true);
		logoutManagerButton.setBounds(0, i, CommonPL.getLeftMenuWidth(), 54);
		i += 54;

		// - Cập nhật lại kích thước và vị trí của Menu Panel
		mainMenuPanel.setBounds(0, (900 - i) / 2 + 8, CommonPL.getLeftMenuWidth(), i);
	}

	// Hàm thiết lập các sự kiện cho các nút ở menu trái
	private void menuButtonEvents() {
		// Lấy ra JButton có ở Menu Panel
		ArrayList<JButton> buttonsInMainMenu = CommonPL.getAllButtons(mainMenuPanel);
		ArrayList<JButton> buttons = new ArrayList<>();
		buttons.addAll(buttonsInMainMenu);

		// Thực hiện việc thêm sự kiện hoạt ảnh cho nút tương ứng
		for (int i = 0; i < buttons.size(); i++) {
			JButton buttonClicked = buttons.get(i);
			buttonClicked.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent evt) {
					JLabel labelInButtonClicked = (JLabel) buttonClicked.getComponent(1);
					if (labelInButtonClicked.getText().equals("Thống kê")) {
						Admin_DashboardManagerPL dashboardManagerPL = new Admin_DashboardManagerPL();
						dashboardManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						dashboardManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(dashboardManagerPL);
					} else if (labelInButtonClicked.getText().equals("Tình trạng phòng")) {
						Admin_RoomStatusManagerPL roomStatusManagerPL = new Admin_RoomStatusManagerPL();
						roomStatusManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						roomStatusManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(roomStatusManagerPL);
					} else if (labelInButtonClicked.getText().equals("Đặt món")) {
						Admin_OrderManagerPL orderManagerPL = new Admin_OrderManagerPL();
						orderManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						orderManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(orderManagerPL);
					} else if (labelInButtonClicked.getText().equals("Thực đơn")) {
						Admin_MenuManagerPL menuManagerPL = new Admin_MenuManagerPL();
						menuManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						menuManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(menuManagerPL);
					} else if (labelInButtonClicked.getText().equals("Khuyến mãi")) {
						Admin_DiscountManagerPL discountManagerPL = new Admin_DiscountManagerPL();
						discountManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						discountManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(discountManagerPL);
					} else if (labelInButtonClicked.getText().equals("Khách hàng")) {
						Admin_CustomerManagerPL customerManagerPL = new Admin_CustomerManagerPL();
						customerManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						customerManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(customerManagerPL);
					} else if (labelInButtonClicked.getText().equals("Người dùng")) {
						Admin_AccountManagerPL accountManagerPL = new Admin_AccountManagerPL();
						accountManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						accountManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(accountManagerPL);
					} else if (labelInButtonClicked.getText().equals("Nhà cung cấp")) {
						Admin_SupplierManagerPL supplierManagerPL = new Admin_SupplierManagerPL();
						supplierManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						supplierManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(supplierManagerPL);
					} else if (labelInButtonClicked.getText().equals("Phiếu nhập")) {
						Admin_InputTicketManagerPL inputTicketManagerPL = new Admin_InputTicketManagerPL();
						inputTicketManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						inputTicketManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(inputTicketManagerPL);
					} else if (labelInButtonClicked.getText().equals("Nguyên liệu")) {
						Admin_IngredientManagerPL ingredientManagerPL = new Admin_IngredientManagerPL();
						ingredientManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						ingredientManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(ingredientManagerPL);
					}
//					else if (labelInButtonClicked.getText().equals("Nhân viên")) {
//						Admin_EmployeeManagerPL employeeManagerPL = new Admin_EmployeeManagerPL();
//						employeeManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(),
//								CommonPL.getScreenHeightByOwner());
//						employeeManagerPL.setBackground(colorBackgroundInMain);
//						AdminCardPL.getInstance().changeAdminMain(employeeManagerPL);
//					} else if (labelInButtonClicked.getText().equals("Lịch làm việc")) {
//						Admin_WorkScheduleManagerPL workScheduleManagerPL = new Admin_WorkScheduleManagerPL();
//						workScheduleManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(),
//								CommonPL.getScreenHeightByOwner());
//						workScheduleManagerPL.setBackground(colorBackgroundInMain);
//						AdminCardPL.getInstance().changeAdminMain(workScheduleManagerPL);
//					}

					for (int j = 0; j < buttons.size(); j++) {
						JButton button = buttons.get(j);

						// Xoá tất cả các sự kiện của các nút
						MouseListener[] listeners = button.getMouseListeners();
						for (MouseListener listener : listeners) {
							button.removeMouseListener(listener);
						}

						// Lấy ra Label bên trong các nút
						JLabel labelInButtonApproved = (JLabel) button.getComponent(1);

						// Nếu không phải nút đã được nhấn
						if (labelInButtonApproved.getText() != labelInButtonClicked.getText()) {
							// Các nút chuyển về mặc định
							labelInButtonApproved.setForeground(colorForegroundInLeftMenu);

							// Thiết lập sự kiện di chuyển chuột vào các nút
							button.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseEntered(MouseEvent evt) {
									labelInButtonApproved.setForeground(colorForegroundHoverInLeftMenu);
								}

								@Override
								public void mouseExited(MouseEvent evt) {
									labelInButtonApproved.setForeground(colorForegroundInLeftMenu);
								}
							});
						}
					}

					// Nút được nhấn được đổi màu
					labelInButtonClicked.setForeground(colorForegroundHoverInLeftMenu);

					menuButtonEvents();
				}
			});
		}
	}
}
