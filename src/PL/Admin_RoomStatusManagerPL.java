package PL;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Admin_RoomStatusManagerPL extends JPanel {
	// Các Font
	private Font fontParagraph = new Font("Arial", Font.PLAIN, 14);
	private Font fontTitleInBill = new Font("Arial", Font.BOLD, 40);
	private Font fontParagraphPlainInBill = new Font("Arial", Font.PLAIN, 16);
	private Font fontParagraphBoldInBill = new Font("Arial", Font.BOLD, 16);
	private Font fontParagraphIngredientInBill = new Font("Arial", Font.PLAIN, 12);
	private Font fontButton = new Font("Arial", Font.BOLD, 14);
	private Font fontTitleInProductInfor = new Font("Arial", Font.BOLD, 20);
	private Font fontParagraphInProductInfor = new Font("Arial", Font.PLAIN, 14);
	// Các Component
	private JLabel titleLabel;
	// - Các Component của Product Panel
	private JPanel block1Panel;
	private JLabel block1Label;
	private JPanel block2Panel;
	private JLabel block2Label;
	private JPanel block3Panel;
	private JLabel block3Label;
	private JButton updateButton;
	private JButton orderButton;
	private JTextField findInputTextField;
	private JComboBox<String> typesComboBox;
	private JComboBox<String> statusComboBox;
	private JButton filterButton;
	private JPanel listPanel;
	private JScrollPane listScrollPane;
	private JPanel productPanel;
	// - Các Component của Bill Panel
	private JLabel roomNameLabel;
	private JLabel customerIdLabel;
	private JLabel customerIdDetailLabel;
	private JLabel employeeIdLabel;
	private JLabel employeeIdDetailLabel;
	private JLabel orderDateLabel;
	private JLabel orderDateDetailLabel;
	private JLabel roomTypeLabel;
	private JLabel roomTypeDetailLabel;
	private JLabel orderTimeLabel;
	private JLabel orderTimeDetailLabel;
	private JLabel roomCostLabel;
	private JLabel roomCostDetailLabel;
	private JPanel line1Panel;
	private JLabel ingredientNameLabel;
	private JLabel ingredientQuantityLabel;
	private JLabel ingredientFinalCostLabel;
	private JPanel ingredientPanel;
	private JScrollPane ingredientScrollPane;
	private JPanel line2Panel;
	private JLabel summaryCostLabel;
	private JLabel summaryCostDetailLabel;
	private JLabel promotionCostLabel;
	private JLabel promotionCostDetailLabel;
	private JLabel finalCostLabel;
	private JLabel finalCostDetailLabel;
	private JComboBox<String> payMethodsComboBox;
	private JButton payButton;
	private JButton printBillButton;
	private JPanel billPanel;

	int counter = 0;

	public Admin_RoomStatusManagerPL() {
		// <===== Cấu trúc của Title Label =====>
		// - Tuỳ chỉnh Title Label
		titleLabel = CommonPL.getTitleLabel("Tình trạng phòng", Color.BLACK, CommonPL.getFontTitle(),
				SwingConstants.CENTER, SwingConstants.CENTER);
		titleLabel.setBounds(30, 0, 1140, 115);
		// <==================== ====================>

		// <===== Cấu trúc của Product Panel =====>
		// - Tuỳ chỉnh Block 1 Panel
		block1Panel = new JPanel();
		block1Panel.setBounds(15, 15, 30, 30);
		block1Panel.setBackground(Color.decode("#fe6d73"));

		// - Tuỳ chỉnh Block 1 Label
		block1Label = CommonPL.getParagraphLabel("Sử dụng", Color.decode("#fe6d73"), fontParagraph);
		block1Label.setBounds(50, 15, 55, 30);

		// - Tuỳ chỉnh Block 2 Panel
		block2Panel = new JPanel();
		block2Panel.setBounds(120, 15, 30, 30);
		block2Panel.setBackground(Color.decode("#17c3b2"));

		// - Tuỳ chỉnh Block 2 Label
		block2Label = CommonPL.getParagraphLabel("Trống", Color.decode("#17c3b2"), fontParagraph);
		block2Label.setBounds(155, 15, 40, 30);

		// - Tuỳ chỉnh Block 3 Panel
		block3Panel = new JPanel();
		block3Panel.setBounds(210, 15, 30, 30);
		block3Panel.setBackground(Color.decode("#DEDEDE"));

		// - Tuỳ chỉnh Block 3 Label
		block3Label = CommonPL.getParagraphLabel("Bảo trì", Color.decode("#DEDEDE"), fontParagraph);
		block3Label.setBounds(245, 15, 55, 30);

		// - Tuỳ chỉnh Update Button
		updateButton = CommonPL.getRoundedBorderButton(14, "Sửa phòng", Color.decode("#1976D2"), Color.WHITE,
				fontButton);
		updateButton.setBounds(300, 15, 180, 30);

		// - Tuỳ chỉnh Order Button
		orderButton = CommonPL.getRoundedBorderButton(14, "Đặt phòng", Color.decode("#1976D2"), Color.WHITE,
				fontButton);
		orderButton.setBounds(495, 15, 180, 30);

		// - Tuỳ chỉnh Find Input Text Field
		findInputTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Tên phòng", Color.LIGHT_GRAY, Color.BLACK,
				fontParagraph);
		findInputTextField.setBounds(15, 60, 180, 30);

		// - Tuỳ chỉnh Type ComboBox
		Vector<String> types = CommonPL.getVectorHasValues(new String[] { "Chọn Loại phòng", "Thường", "Cao cấp" });
		typesComboBox = CommonPL.CustomComboBox(types, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE,
				Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontParagraph);
		typesComboBox.setBounds(210, 60, 180, 30);

		// - Tuỳ chỉnh Status ComboBox
		Vector<String> status = CommonPL.getVectorHasValues(new String[] { "Chọn Trạng thái", "Sử dụng", "Trống" });
		statusComboBox = CommonPL.CustomComboBox(status, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE,
				Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontParagraph);
		statusComboBox.setBounds(405, 60, 180, 30);

		// - Tuỳ chỉnh Filter Button
		filterButton = CommonPL.getRoundedBorderButton(14, "Lọc", Color.decode("#007bff"), Color.WHITE, fontButton);
		filterButton.setBounds(600, 60, 75, 30);

		// - Tuỳ chỉnh List Panel
		listPanel = new JPanel();
		listPanel.setLayout(null);
		listPanel.setBackground(Color.WHITE);

		// - Tuỳ chỉnh List Scroll Pane
		listScrollPane = new JScrollPane(listPanel);
		listScrollPane.setBounds(15, 105, 675, 585);
		listScrollPane.setBorder(null);
		listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// - Tuỳ chỉnh Product Panel;
		productPanel = new CommonPL.RoundedPanel(12);
		productPanel.setLayout(null);
		productPanel.setBackground(Color.WHITE);
		productPanel.setBounds(30, 115, 690, 705);
		productPanel.add(block1Panel);
		productPanel.add(block1Label);
		productPanel.add(block2Panel);
		productPanel.add(block2Label);
		productPanel.add(block3Panel);
		productPanel.add(block3Label);
		productPanel.add(updateButton);
		productPanel.add(orderButton);
		productPanel.add(findInputTextField);
		productPanel.add(typesComboBox);
		productPanel.add(statusComboBox);
		productPanel.add(filterButton);
		productPanel.add(listScrollPane);
		// <==================== ====================>

		// <===== Cấu trúc của Bill Panel =====>
		// - Tuỳ chỉnh Room Name Label
		roomNameLabel = CommonPL.getTitleLabel("1.1", Color.decode("#1976D2"), fontTitleInBill, SwingConstants.CENTER,
				SwingConstants.CENTER);
		roomNameLabel.setBounds(0, 0, 135, 210);

		// - Tuỳ chỉnh Customer Id Label
		customerIdLabel = CommonPL.getParagraphLabel("Khách hàng:", Color.GRAY, fontParagraphPlainInBill);
		customerIdLabel.setBounds(135, 15, 100, 30);

		// - Tuỳ chỉnh Customer Detail Id Label
		customerIdDetailLabel = CommonPL.getTitleLabel("079205019936", Color.decode("#1976D2"), fontParagraphBoldInBill,
				SwingConstants.RIGHT, SwingConstants.CENTER);
		customerIdDetailLabel.setBounds(240, 15, 180, 30);

		// - Tuỳ chỉnh Employee Id Label
		employeeIdLabel = CommonPL.getParagraphLabel("Nhân viên:", Color.GRAY, fontParagraphPlainInBill);
		employeeIdLabel.setBounds(135, 45, 100, 30);

		// - Tuỳ chỉnh Employee Id Detail Label
		employeeIdDetailLabel = CommonPL.getTitleLabel("NV0001", Color.decode("#1976D2"), fontParagraphBoldInBill,
				SwingConstants.RIGHT, SwingConstants.CENTER);
		employeeIdDetailLabel.setBounds(240, 45, 180, 30);

		// - Tuỳ chỉnh Room Type Label
		roomTypeLabel = CommonPL.getParagraphLabel("Loại phòng:", Color.GRAY, fontParagraphPlainInBill);
		roomTypeLabel.setBounds(135, 75, 100, 30);

		// - Tuỳ chỉnh Room Type Detail Label
		roomTypeDetailLabel = CommonPL.getTitleLabel("Thường", Color.decode("#1976D2"), fontParagraphBoldInBill,
				SwingConstants.RIGHT, SwingConstants.CENTER);
		roomTypeDetailLabel.setBounds(240, 75, 180, 30);

		// - Tuỳ chỉnh Order Date Label
		orderDateLabel = CommonPL.getParagraphLabel("Ngày đặt:", Color.GRAY, fontParagraphPlainInBill);
		orderDateLabel.setBounds(135, 105, 100, 30);

		// - Tuỳ chỉnh Order Date Detail Label
		orderDateDetailLabel = CommonPL.getTitleLabel(String.valueOf(LocalDate.now()), Color.decode("#1976D2"),
				fontParagraphBoldInBill, SwingConstants.RIGHT, SwingConstants.CENTER);
		orderDateDetailLabel.setBounds(240, 105, 180, 30);

		// - Tuỳ chỉnh Order Time Label
		orderTimeLabel = CommonPL.getParagraphLabel("Tổng giờ:", Color.GRAY, fontParagraphPlainInBill);
		orderTimeLabel.setBounds(135, 135, 100, 30);

		// - Tuỳ chỉnh Order Time Detail Label
		orderTimeDetailLabel = CommonPL.getTitleLabel("00:00:00", Color.decode("#1976D2"), fontParagraphBoldInBill,
				SwingConstants.RIGHT, SwingConstants.CENTER);
		orderTimeDetailLabel.setBounds(240, 135, 180, 30);
		// + Tạo Timer cập nhật thời gian
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				counter++; // Tăng số giây lên 1
				int hours = counter / 3600;
				int minutes = (counter % 3600) / 60;
				int seconds = counter % 60;

				String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
				orderTimeDetailLabel.setText(formattedTime);
			}
		});
		timer.start();

		// - Tuỳ chỉnh Room Cost Label
		roomCostLabel = CommonPL.getParagraphLabel("Tiền phòng:", Color.GRAY, fontParagraphPlainInBill);
		roomCostLabel.setBounds(135, 165, 100, 30);

		// - Tuỳ chỉnh Room Cost Detail Label
		roomCostDetailLabel = CommonPL.getTitleLabel(
				CommonPL.moneyLongToMoneyFormat(new BigInteger(String.valueOf("10000000"))) + " VNĐ",
				Color.decode("#1976D2"), fontParagraphBoldInBill, SwingConstants.RIGHT, SwingConstants.CENTER);
		roomCostDetailLabel.setBounds(240, 165, 180, 30);

		// - Tuỳ chỉnh Line 1 Panel
		line1Panel = new CommonPL.RoundedPanel(8);
		line1Panel.setBackground(Color.decode("#1976D2"));
		line1Panel.setBounds(15, 210, 405, 3);

		// - Tuỳ chỉnh Ingredient Name Label
		ingredientNameLabel = CommonPL.getTitleLabel("Tên sản phẩm", Color.GRAY, fontParagraphIngredientInBill,
				SwingConstants.CENTER, SwingConstants.CENTER);
		ingredientNameLabel.setBounds(15, 225, 200, 30);

		// - Tuỳ chỉnh Unit Ingredient Label
		ingredientQuantityLabel = CommonPL.getTitleLabel("Số lượng", Color.GRAY, fontParagraphIngredientInBill,
				SwingConstants.CENTER, SwingConstants.CENTER);
		ingredientQuantityLabel.setBounds(215, 225, 65, 30);

		// - Tuỳ chỉnh Ingredient Final Cost Label
		ingredientFinalCostLabel = CommonPL.getTitleLabel("Thành tiền", Color.GRAY, fontParagraphIngredientInBill,
				SwingConstants.CENTER, SwingConstants.CENTER);
		ingredientFinalCostLabel.setBounds(280, 225, 140, 30);

		// - Truy vấn bảng dữ liệu nguyên liệu và chi tiết sản phẩm
		Object[][] datas = { { "Mì trứng", "2", "40000" }, { "Chai Pepsi", "5", "50000" },
				{ "Chai Cocacola", "19", "190000" }, };
		// - Tuỳ chỉnh Add Or Update Ingredient Panel
		ingredientPanel = new JPanel();
		ingredientPanel.setLayout(null);
		ingredientPanel.setPreferredSize(new Dimension(405, datas.length * 34));
		for (int i = 0; i < datas.length; i++) {
			// + Tên sản phẩm
			JLabel name = CommonPL.getParagraphLabel(String.valueOf(datas[i][0]), Color.GRAY,
					fontParagraphIngredientInBill);
			name.setBounds(0, 0, 200, 34);
			// + Số lượng
			JLabel quantity = CommonPL.getTitleLabel(String.valueOf(datas[i][1]), Color.GRAY,
					fontParagraphIngredientInBill, SwingConstants.CENTER, SwingConstants.CENTER);
			quantity.setBounds(200, 0, 65, 34);
			// + Thành tiền
			JLabel finalCost = CommonPL.getTitleLabel(String.valueOf(datas[i][2]), Color.GRAY,
					fontParagraphIngredientInBill, SwingConstants.CENTER, SwingConstants.CENTER);
			finalCost.setBounds(265, 0, 140, 34);
			// + Đường kẻ ngang
			JPanel linePanel = new CommonPL.RoundedPanel(8);
			linePanel.setBounds(0, 0, 405, 2);
			// + Dòng chứa các dữ liệu
			JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
			rowPanel.setLayout(null);
			rowPanel.setBounds(0, i * 34, 405, 34);
			rowPanel.setBackground(Color.WHITE);
			if (i >= 1)
				rowPanel.add(linePanel);
			rowPanel.add(name);
			rowPanel.add(quantity);
			rowPanel.add(finalCost);

			// + Thêm vào ingredientPanel
			ingredientPanel.setBackground(Color.WHITE);
			ingredientPanel.add(rowPanel);
		}

		// - Tuỳ chỉnh Ingredient Scroll Pane
		ingredientScrollPane = new JScrollPane(ingredientPanel);
		ingredientScrollPane.setBounds(15, 255, 420, 204);
		ingredientScrollPane.setBorder(null);
		ingredientScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		ingredientScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// - Tuỳ chỉnh Line 2 Panel
		line2Panel = new CommonPL.RoundedPanel(8);
		line2Panel.setBackground(Color.decode("#1976D2"));
		line2Panel.setBounds(15, 482, 405, 3);

		// - Tuỳ chỉnh Summary Cost Label
		summaryCostLabel = CommonPL.getParagraphLabel("Tổng cộng:", Color.GRAY, fontParagraphPlainInBill);
		summaryCostLabel.setBounds(15, 500, 100, 30);

		// - Tuỳ chỉnh Summary Cost Detail Label
		summaryCostDetailLabel = CommonPL.getTitleLabel(
				CommonPL.moneyLongToMoneyFormat(new BigInteger(String.valueOf("10280000"))) + " VNĐ",
				Color.decode("#1976D2"), fontParagraphBoldInBill, SwingConstants.RIGHT, SwingConstants.CENTER);
		summaryCostDetailLabel.setBounds(120, 500, 300, 30);

		// - Tuỳ chỉnh Promotion Cost Label
		promotionCostLabel = CommonPL.getParagraphLabel("Khuyến mãi:", Color.GRAY, fontParagraphPlainInBill);
		promotionCostLabel.setBounds(15, 530, 100, 30);

		// - Tuỳ chỉnh Promotion Cost Detail Label
		promotionCostDetailLabel = CommonPL.getTitleLabel(
				"-" + CommonPL.moneyLongToMoneyFormat(new BigInteger(String.valueOf("10000000"))) + " VNĐ",
				Color.decode("#1976D2"), fontParagraphBoldInBill, SwingConstants.RIGHT, SwingConstants.CENTER);
		promotionCostDetailLabel.setBounds(120, 530, 300, 30);

		// - Tuỳ chỉnh Final Cost Label
		finalCostLabel = CommonPL.getParagraphLabel("Thành tiền:", Color.GRAY, fontParagraphPlainInBill);
		finalCostLabel.setBounds(15, 560, 100, 30);

		// - Tuỳ chỉnh Final Cost Detail Label
		finalCostDetailLabel = CommonPL.getTitleLabel(
				CommonPL.moneyLongToMoneyFormat(new BigInteger(String.valueOf("280000"))) + " VNĐ",
				Color.decode("#1976D2"), fontParagraphBoldInBill, SwingConstants.RIGHT, SwingConstants.CENTER);
		finalCostDetailLabel.setBounds(120, 560, 300, 30);

		// - Tuỳ chỉnh Pay Methods ComboBox
		Vector<String> payMethods = CommonPL.getVectorHasValues(new String[] { "Chọn Phương thức thanh toán",
				"Thanh toán bằng tiền mặt", "Thanh toán bằng chuyển khoản", "Thanh toán bằng thẻ ngân hàng" });
		payMethodsComboBox = CommonPL.CustomComboBox(payMethods, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontParagraph);
		payMethodsComboBox.setBounds(15, 620, 405, 30);

		// - Tuỳ chỉnh Pay Button
		payButton = CommonPL.getRoundedBorderButton(14, "Thanh toán", Color.decode("#1976D2"), Color.WHITE, fontButton);
		payButton.setBounds(15, 660, 195, 30);

		// - Tuỳ chỉnh Print Bill Button
		printBillButton = CommonPL.getRoundedBorderButton(14, "In hoá đơn", Color.decode("#1976D2"), Color.WHITE,
				fontButton);
		printBillButton.setBounds(225, 660, 195, 30);

		// - Tuỳ chỉnh Bill Panel;
		billPanel = new CommonPL.RoundedPanel(12);
		billPanel.setLayout(null);
		billPanel.setBackground(Color.WHITE);
		billPanel.setBounds(735, 115, 435, 705);
		billPanel.add(roomNameLabel);
		billPanel.add(customerIdLabel);
		billPanel.add(customerIdDetailLabel);
		billPanel.add(employeeIdLabel);
		billPanel.add(employeeIdDetailLabel);
		billPanel.add(orderDateLabel);
		billPanel.add(orderDateDetailLabel);
		billPanel.add(roomTypeLabel);
		billPanel.add(roomTypeDetailLabel);
		billPanel.add(orderTimeLabel);
		billPanel.add(orderTimeDetailLabel);
		billPanel.add(roomCostLabel);
		billPanel.add(roomCostDetailLabel);
		billPanel.add(line1Panel);
		billPanel.add(ingredientNameLabel);
		billPanel.add(ingredientQuantityLabel);
		billPanel.add(ingredientFinalCostLabel);
		billPanel.add(ingredientScrollPane);
		billPanel.add(line2Panel);
		billPanel.add(summaryCostLabel);
		billPanel.add(summaryCostDetailLabel);
		billPanel.add(promotionCostLabel);
		billPanel.add(promotionCostDetailLabel);
		billPanel.add(finalCostLabel);
		billPanel.add(finalCostDetailLabel);
		billPanel.add(payMethodsComboBox);
		billPanel.add(payButton);
		billPanel.add(printBillButton);
		// <==================== ====================>

		// Đĩnh nghĩa tính chất cho Dashboard Manager PL
		this.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
		this.setLayout(null);
		this.setSize(CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
		this.setBackground(Color.decode("#E3F2FD"));
		this.setLayout(null);
		this.add(titleLabel);
		this.add(productPanel);
		this.add(billPanel);

		// Thiết lập sự kiện cập nhật danh sách phòng
		renderListPanel();

		//
//		buttonEventsInListPanel();

	}

	//
	private void buttonEventsInListPanel() {
		//
		ArrayList<JButton> buttonsInListPanel = CommonPL.getAllButtons(listPanel);
		ArrayList<JButton> buttons = new ArrayList<>();
		buttons.addAll(buttonsInListPanel);

		for (JButton button : buttons) {
			System.out.println(((JLabel) button.getComponent(2)).getText());
		}

		// Thực hiện việc thêm sự kiện hoạt ảnh cho nút tương ứng
		for (int i = 0; i < buttons.size(); i++) {
			JButton buttonClicked = buttons.get(i);
			buttonClicked.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent evt) {
					// Tuỳ theo giá trị của nút được chọn mà khởi tạo Panel tương ứng
					String valueSelected = ((JLabel) buttonClicked.getComponent(1)).getText();

					// Duyệt qua các nút (lấy ra các nút không được nhấn)
					for (int j = 0; j < buttons.size(); j++) {
						JButton button = buttons.get(j);

						// Xoá tất cả các sự kiện của các nút
						MouseListener[] listeners = button.getMouseListeners();
						for (MouseListener listener : listeners) {
							button.removeMouseListener(listener);
						}

						// Lấy ra Label bên trong các nút
						String valueApproved = ((JLabel) button.getComponent(1)).getText();

						// Nếu không phải nút đã được nhấn
						if (!valueApproved.equals(valueSelected)) {
							// Các nút chuyển về mặc định
							// + Cập nhật lại icon
							ImageIcon image = new ImageIcon(
									CommonPL.getMiddlePathToShowIcon() + "micro-white-icon.png");
							Image scaledImage = image.getImage().getScaledInstance(210, 210, Image.SCALE_SMOOTH);
							ImageIcon scaledIcon = new ImageIcon(scaledImage);
							((JLabel) button.getComponent(0)).setIcon(scaledIcon);
							// + Cập nhật lại màu tên phòng
							((JLabel) button.getComponent(2)).setForeground(Color.WHITE);
							// + Cập nhật lại màu nền
							button.setBackground(Color.decode("#1976D2"));

							// Thiết lập sự kiện di chuyển chuột vào các nút
							button.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseEntered(MouseEvent evt) {
									// + Cập nhật lại icon
									ImageIcon image = new ImageIcon(
											CommonPL.getMiddlePathToShowIcon() + "micro-blue-icon.png");
									Image scaledImage = image.getImage().getScaledInstance(210, 210,
											Image.SCALE_SMOOTH);
									ImageIcon scaledIcon = new ImageIcon(scaledImage);
									((JLabel) button.getComponent(0)).setIcon(scaledIcon);
									// + Cập nhật lại màu tên phòng
									((JLabel) button.getComponent(2)).setForeground(Color.decode("#1976D2"));
									// + Cập nhật lại màu nền
									button.setBackground(Color.WHITE);
								}

								@Override
								public void mouseExited(MouseEvent evt) {
									// + Cập nhật lại icon
									ImageIcon image = new ImageIcon(
											CommonPL.getMiddlePathToShowIcon() + "micro-white-icon.png");
									Image scaledImage = image.getImage().getScaledInstance(210, 210,
											Image.SCALE_SMOOTH);
									ImageIcon scaledIcon = new ImageIcon(scaledImage);
									((JLabel) button.getComponent(0)).setIcon(scaledIcon);
									// + Cập nhật lại màu tên phòng
									((JLabel) button.getComponent(2)).setForeground(Color.WHITE);
									// + Cập nhật lại màu nền
									button.setBackground(Color.decode("#1976D2"));
								}
							});
						}
					}

					// Nút được nhấn được đổi màu
					// + Cập nhật lại icon
					ImageIcon image = new ImageIcon(CommonPL.getMiddlePathToShowIcon() + "micro-blue-icon.png");
					Image scaledImage = image.getImage().getScaledInstance(210, 210, Image.SCALE_SMOOTH);
					ImageIcon scaledIcon = new ImageIcon(scaledImage);
					((JLabel) buttonClicked.getComponent(0)).setIcon(scaledIcon);
					// + Cập nhật lại màu tên phòng
					((JLabel) buttonClicked.getComponent(2)).setForeground(Color.decode("#1976D2"));
					// + Cập nhật lại màu nền
					buttonClicked.setBackground(Color.WHITE);

					// Gọi lại sự kiện
					buttonEventsInListPanel();
				}
			});
		}
	}

	// Hàm cập nhật lại danh sách sản phẩm
	private void renderListPanel() {
		// - Truy vấn dữ liệu về tất cả sản phẩm hiện có
		Object[][] datas = { { "1", "1.1", "079205019936", "1" }, { "2", "1.2", null, "1" },
				{ "3", "1.3", null, "0" }, };

		// - Duyệt qua từng đối tượng
		int x = 0, y = 0;
		for (int i = 0; i < datas.length; i++) {
			// + Biến tạm giữ vị trí đối tượng được duyệt
			int j = i;

			// + Ảnh sản phẩm
			JLabel imageLabel = CommonPL.getImageLabel(210, 210,
					CommonPL.getMiddlePathToShowIcon() + "micro-white-icon.png");
			imageLabel.setBounds(2, 2, 206, 206);
			// + Mã sản phẩm
			JLabel idLabel = CommonPL.getParagraphLabel(String.valueOf(datas[i][0]), Color.BLACK,
					fontParagraphInProductInfor);
			idLabel.setBounds(0, 0, 210, 20);
			// + Tên sản phẩm
			JLabel nameLabel = CommonPL.getTitleLabel(String.valueOf(datas[i][1]), Color.WHITE, fontTitleInProductInfor,
					SwingConstants.LEFT, SwingConstants.CENTER);
			nameLabel.setBounds(5, 5, 210, 20);

			// + Khối chứa sản phẩm
			JButton blockPanel = new JButton();
			blockPanel.setLayout(null);
			blockPanel.setOpaque(true);
			blockPanel.setBounds(x, y, 210, 210);
			blockPanel.setBackground(Color.decode("#1976D2"));
			blockPanel.add(imageLabel);
			blockPanel.add(idLabel);
			blockPanel.add(nameLabel);

			if (datas[i][2] != null) {
				// + Cập nhật lại icon
				ImageIcon image = new ImageIcon(CommonPL.getMiddlePathToShowIcon() + "micro-white-icon.png");
				Image scaledImage = image.getImage().getScaledInstance(210, 210, Image.SCALE_SMOOTH);
				ImageIcon scaledIcon = new ImageIcon(scaledImage);
				imageLabel.setIcon(scaledIcon);
				// + Cập nhật lại màu tên phòng
				nameLabel.setForeground(Color.WHITE);
				// + Cập nhật lại màu nền
				blockPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#fe6d73"), 8));
				blockPanel.setBackground(Color.decode("#fe6d73"));
			} else {
				if (datas[i][3] == "1") {
					// + Cập nhật lại icon
					ImageIcon image = new ImageIcon(CommonPL.getMiddlePathToShowIcon() + "micro-white-icon.png");
					Image scaledImage = image.getImage().getScaledInstance(210, 210, Image.SCALE_SMOOTH);
					ImageIcon scaledIcon = new ImageIcon(scaledImage);
					imageLabel.setIcon(scaledIcon);
					// + Cập nhật lại màu tên phòng
					nameLabel.setForeground(Color.WHITE);
					// + Cập nhật lại màu nền
					blockPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#17c3b2"), 8));
					blockPanel.setBackground(Color.decode("#17c3b2"));
				} else {
					// + Cập nhật lại icon
					ImageIcon image = new ImageIcon(CommonPL.getMiddlePathToShowIcon() + "micro-white-icon.png");
					Image scaledImage = image.getImage().getScaledInstance(210, 210, Image.SCALE_SMOOTH);
					ImageIcon scaledIcon = new ImageIcon(scaledImage);
					imageLabel.setIcon(scaledIcon);
					// + Cập nhật lại màu tên phòng
					nameLabel.setForeground(Color.WHITE);
					// + Cập nhật lại màu nền
					blockPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#dedede"), 8));
					blockPanel.setBackground(Color.decode("#dedede"));
				}
			}
//			blockPanel.addMouseListener(new MouseAdapter() {
//				@Override
//				public void mouseEntered(MouseEvent e) {
//					// + Cập nhật lại icon
//					ImageIcon image = new ImageIcon(CommonPL.getMiddlePathToShowIcon() + "micro-blue-icon.png");
//					Image scaledImage = image.getImage().getScaledInstance(210, 210, Image.SCALE_SMOOTH);
//					ImageIcon scaledIcon = new ImageIcon(scaledImage);
//					imageLabel.setIcon(scaledIcon);
//					// + Cập nhật lại màu tên phòng
//					nameLabel.setForeground(Color.decode("#1976D2"));
//					// + Cập nhật lại màu nền
//					blockPanel.setBackground(Color.WHITE);
//				}
//
//				@Override
//				public void mouseExited(MouseEvent e) {
//					// + Cập nhật lại icon
//					ImageIcon image = new ImageIcon(CommonPL.getMiddlePathToShowIcon() + "micro-white-icon.png");
//					Image scaledImage = image.getImage().getScaledInstance(210, 210, Image.SCALE_SMOOTH);
//					ImageIcon scaledIcon = new ImageIcon(scaledImage);
//					imageLabel.setIcon(scaledIcon);
//					// + Cập nhật lại màu tên phòng
//					nameLabel.setForeground(Color.WHITE);
//					// + Cập nhật lại màu nền
//					blockPanel.setBackground(Color.decode("#1976D2"));
//				}
//			});

			// + Thêm vào danh sách sản phẩm
			listPanel.add(blockPanel);

			// + Cập nhật lại vị trí của từng sản phẩm
			if (x == 450) {
				x = 0;
				y += 225;
			} else {
				x += 225;
			}
		}

		listPanel.setPreferredSize(new Dimension(675, (int) ((Math.ceil(1.0 * datas.length / 3) * 225))));
	}
}
