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

import BLL.FunctionBLL;
import BLL.PrivilegeDetailBLL;
import DTO.FunctionDTO;
import DTO.PrivilegeDetailDTO;

public class AdminMenuPL extends JPanel {
	// Các đối tượng từ tầng BLL
	private PrivilegeDetailBLL privilegeDetailBLL;
	private FunctionBLL functionBLL;
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
	private JButton accountManagerButton;
	private JButton orderManagerButton;
	private JButton roomManagerButton;
	private JButton customerManagerButton;
	private JButton supplierManagerButton;
	private JButton inputTicketManagerButton;
	private JButton categoryManagerButton;
	private JButton foodManagerButton;
	private JButton logoutButton;
	private JPanel mainMenuPanel;

	public AdminMenuPL() {
		// <===== Các đối tượng từ tầng BLL =====>
		privilegeDetailBLL = new PrivilegeDetailBLL();
		functionBLL = new FunctionBLL();
		// <==================== ====================>

		// <===== Cấu trúc của Admin Label =====>
		// - Tuỳ chỉnh Admin Label
		adminLabel = CommonPL.getTitleLabel("KARAOKE", colorForegroundInLeftMenu, fontTitle, SwingConstants.CENTER,
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

		// - Tuỳ chỉnh Account Manager Button
		accountManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "account-icon.png", "Người dùng", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		accountManagerButton.setBounds(0, 60, CommonPL.getLeftMenuWidth(), 60);

		// - Tuỳ chỉnh Order Button
		orderManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "ring-icon.png", "Hoá đơn", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		orderManagerButton.setBounds(0, 120, CommonPL.getLeftMenuWidth(), 60);

		// - Tuỳ chỉnh Room Button
		roomManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "karaoke-icon.png", "Phòng hát", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		roomManagerButton.setBounds(0, 180, CommonPL.getLeftMenuWidth(), 60);

		// - Tuỳ chỉnh Customer Manager Button
		customerManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "customer-icon.png", "Khách hàng", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		customerManagerButton.setBounds(0, 240, CommonPL.getLeftMenuWidth(), 60);

		// - Tuỳ chỉnh Customer Manager Button
		supplierManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "supplier-icon.png", "Nhà cung cấp", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		supplierManagerButton.setBounds(0, 300, CommonPL.getLeftMenuWidth(), 60);

		// - Tuỳ chỉnh Input Ticket Manager Button
		inputTicketManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "input-ticket-icon.png", "Phiếu nhập", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		inputTicketManagerButton.setBounds(0, 360, CommonPL.getLeftMenuWidth(), 60);

		// - Tuỳ chỉnh Food Manager Button
		categoryManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "category-icon.png", "Loại món ăn", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		categoryManagerButton.setBounds(0, 420, CommonPL.getLeftMenuWidth(), 60);

		// - Tuỳ chỉnh Food Manager Button
		foodManagerButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "food-icon.png", "Món ăn", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		foodManagerButton.setBounds(0, 480, CommonPL.getLeftMenuWidth(), 60);

		// - Nút thoát
		logoutButton = CommonPL.getButtonHasIcon(CommonPL.getLeftMenuWidth(), 60, 30, 30, 15, 15,
				CommonPL.getMiddlePathToShowIcon() + "logout-icon.png", "Đăng xuất", colorBackgroundInLeftMenu,
				colorBackgroundHoverInLeftMenu, colorForegroundInLeftMenu, colorForegroundHoverInLeftMenu,
				fontSubTitle);
		logoutButton.setBounds(0, CommonPL.getScreenHeightByOwner() - 150 - 60, CommonPL.getLeftMenuWidth(), 60);

		// - Tuỳ chỉnh Main Menu Panel
		mainMenuPanel = new JPanel();
		mainMenuPanel.setLayout(null);
		mainMenuPanel.setBounds(0, 100, CommonPL.getLeftMenuWidth(), CommonPL.getScreenHeightByOwner() - 150);
		mainMenuPanel.setBackground(Color.WHITE);
		mainMenuPanel.add(dashboardManagerButton);
		mainMenuPanel.add(accountManagerButton);
		mainMenuPanel.add(orderManagerButton);
		mainMenuPanel.add(roomManagerButton);
		mainMenuPanel.add(customerManagerButton);
		mainMenuPanel.add(supplierManagerButton);
		mainMenuPanel.add(inputTicketManagerButton);
		mainMenuPanel.add(categoryManagerButton);
		mainMenuPanel.add(foodManagerButton);
		mainMenuPanel.add(logoutButton);
		// <==================== ====================>

		// Định nghĩa các tính chất cho Admin Menu PL
		this.setLayout(null);
		this.setSize(CommonPL.getLeftMenuWidth(), CommonPL.getScreenHeightByOwner());
		this.setBounds(0, 0, CommonPL.getLeftMenuWidth(), CommonPL.getScreenHeightByOwner());
		this.setBackground(Color.WHITE);
		this.add(adminLabel);
		this.add(linePanel);
		this.add(mainMenuPanel);

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
		ArrayList<PrivilegeDetailDTO> privilegeDetailList = privilegeDetailBLL
				.getAllPrivilegeDetailByAccountId(String.valueOf(CommonPL.getAccountUsingApp().getId()));

		String functionsStr = "";
		for (PrivilegeDetailDTO privilegeDetailDTO : privilegeDetailList) {
			FunctionDTO functionDTO = functionBLL.getOneFunctionById(privilegeDetailDTO.getFunctionId());
			functionsStr += "," + functionDTO.getName();
		}
		if (functionsStr.length() != 0)
			functionsStr = functionsStr.substring(1);

		// - Các nút đã có ở menu
		String functionsPattern = "Thống kê,Người dùng,Hoá đơn,Phòng hát,Khách hàng,Nhà cung cấp,Phiếu nhập,Loại món ăn,Món ăn";
		String[] functionsSplit = functionsPattern.split(",");

		// - Lấy ra tất cả các JButton có ở Menu Panel và ẩn đi hết
		ArrayList<JButton> buttonsInMainMenu = CommonPL.getAllButtons(mainMenuPanel);
		ArrayList<JButton> buttons = new ArrayList<>();
		buttons.addAll(buttonsInMainMenu);
		for (JButton button : buttons) {
			button.setVisible(false);
		}

		// - Duyệt qua từng giá trị trong mảng rồi thêm mục tương ứng
		int i = 0;
		for (String function : functionsSplit) {
			if (functionsStr.contains(function)) {
				for (JButton button : buttons) {
					JLabel labelInButton = (JLabel) button.getComponent(1);
					if (function.equals(labelInButton.getText())) {
						button.setVisible(true);
						button.setBounds(0, i, CommonPL.getLeftMenuWidth(), 60);
						i += 60;
					}
				}
			}
		}
		// - Mặc định là 'Đăng xuất' luôn có và ở vị trí cuối cùng
		logoutButton.setVisible(true);
		logoutButton.setBounds(0, i, CommonPL.getLeftMenuWidth(), 60);
		i += 60;

		// - Cập nhật lại kích thước và vị trí của Menu Panel
		mainMenuPanel.setBounds(0, 115, CommonPL.getLeftMenuWidth(), i);
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
					// Đối tượng chứa nút đã được nhấn
					JLabel labelInButtonClicked = (JLabel) buttonClicked.getComponent(1);
					if (labelInButtonClicked.getText().equals("Thống kê")) {
						Admin_DashboardManagerPL dashboardManagerPL = new Admin_DashboardManagerPL();
						dashboardManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0,
								CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						dashboardManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(dashboardManagerPL);
					} else if (labelInButtonClicked.getText().equals("Người dùng")) {
						Admin_AccountManagerPL accountManagerPL = new Admin_AccountManagerPL();
						accountManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0,
								CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						accountManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(accountManagerPL);
					} else if (labelInButtonClicked.getText().equals("Hoá đơn")) {
						Admin_OrderManagerPL orderManagerPL = new Admin_OrderManagerPL();
						orderManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0,
								CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						orderManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(orderManagerPL);
					} else if (labelInButtonClicked.getText().equals("Phòng hát")) {
						Admin_RoomManagerPL roomManagerPL = new Admin_RoomManagerPL();
						roomManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0,
								CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						roomManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(roomManagerPL);
					} else if (labelInButtonClicked.getText().equals("Khách hàng")) {
						Admin_CustomerManagerPL customerManagerPL = new Admin_CustomerManagerPL();
						customerManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						customerManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(customerManagerPL);
					} else if (labelInButtonClicked.getText().equals("Nhà cung cấp")) {
						Admin_SupplierManagerPL supplierManagerPL = new Admin_SupplierManagerPL();
						supplierManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						supplierManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(supplierManagerPL);
					} else if (labelInButtonClicked.getText().equals("Phiếu nhập")) {
						Admin_InputTicketManagerPL inputTicketManagerPL = new Admin_InputTicketManagerPL();
						inputTicketManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0,
								CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						inputTicketManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(inputTicketManagerPL);
					} else if (labelInButtonClicked.getText().equals("Loại món ăn")) {
						Admin_CategoryManagerPL categoryManagerPL = new Admin_CategoryManagerPL();
						categoryManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						categoryManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(categoryManagerPL);
					} else if (labelInButtonClicked.getText().equals("Món ăn")) {
						Admin_FoodManagerPL foodManagerPL = new Admin_FoodManagerPL();
						foodManagerPL.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(),
								CommonPL.getScreenHeightByOwner());
						foodManagerPL.setBackground(colorBackgroundInMain);
						AdminCardPL.getInstance().changeAdminMain(foodManagerPL);
					} else if (labelInButtonClicked.getText().equals("Đăng xuất")) {
						CommonPL.setAccountUsingApp(null);
						LoginRegisterPL loginRegisterPL = new LoginRegisterPL();
						FramePL.getInstance().changeFrameContent(loginRegisterPL);
					}

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
