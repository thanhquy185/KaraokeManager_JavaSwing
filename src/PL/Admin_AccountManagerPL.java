package PL;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Admin_AccountManagerPL extends JPanel {
	// Các Component
	private JLabel titleLabel;
	// - Các Component của Filter Panel
	private JLabel findLabel;
	private JTextField findInputTextField;
	private JButton findInformButton;
	private JLabel sortLabel;
	private Map<String, Boolean> sortCheckboxs;
	private JButton sortButton;
	private JLabel typeLabel;
	private Map<String, Boolean> typeRadios;
	private JButton typeButton;
	private JLabel statusLabel;
	private Map<String, Boolean> statusRadios;
	private JButton statusButton;
	private JLabel numbersOfRowLabel;
	private JTextField numbersOfRowTextField;
	private JButton filterApplyButton;
	private JButton filterResetButton;
	private JPanel filterPanel;
	// - Các Component của Data Panel
	private JButton addButton;
	private JButton updateButton;
	private JButton deleteButton;
	private JTable tableData;
	private JScrollPane tableScrollPane;
	private JPanel dataPanel;
	// - Các Component của Add Dialog (dialog thêm một người dùng)
	private JLabel addOrUpdateUsernameLabel;
	private JTextField addOrUpdateUsernameTextField;
	private JLabel addOrUpdatePasswordLabel;
	private JTextField addOrUpdatePasswordTextField;
	private JLabel addOrUpdateTypeLabel;
	private JComboBox<String> addOrUpdateTypeComboBox;
	private JLabel addOrUpdateTypeDetailLabel;
	private JPanel addOrUpdateTypeDetailPanel;
	private JLabel addOrUpdateStatusLabel;
	private JComboBox<String> addOrUpdateStatusComboBox;
	private JLabel addOrUpdateTimeLabel;
	private JLabel addOrUpdateTimeDetailLabel;
	private JButton addOrUpdateButton;
	private JPanel addOrUpdateBlockPanel;
	private JDialog addOrUpdateDialog;

	// - Các thông tin cần có của Table Data và Table Scroll Pane
	// + Tiêu đề các cột
	private final String[] columns = { "Tên tài khoản", "Mật khẩu", "Quyền", "Chi tiết quyền", "Trạng thái" };
	// + Chiều rộng các cột
	private final int[] widthColumns = { 300, 300, 150, 500, 150 };
	// + Dữ liệu
	private Object[][] datas = { { "admin", "1", "Quản lý",
			"Thống kê,Tình trạng phòng,Đặt món,Thực đơn,Khuyến mãi,Khách hàng,Tài khoản,Nhà cung cấp,Phiếu nhập,Nguyên liệu,Nhân viên,Lịch làm việc,Lịch sử ca",
			"Hoạt động" }, { "NV0001", "20050801", "Nhân viên", "Thống kê,Tình trạng phòng,Đặt món", "Hoạt động" },
			{ "NV0002", "20020225", "Nhân viên", "Thống kê", "Tạm dừng" } };
	// + Dòng hiện tại đang được chọn
	private int rowSelected = -1;
	// + Giá trị (true / false) khi "Xoá" dòng dữ liệu
	private Boolean[] valueSelected = { null };

	public Admin_AccountManagerPL() {
		// <===== Cấu trúc của Title Label =====>
		// - Tuỳ chỉnh Title Label
		titleLabel = CommonPL.getTitleLabel("Người dùng", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER,
				SwingConstants.CENTER);
		titleLabel.setBounds(30, 0, 1140, 115);
		// <==================== ====================>

		// <===== Cấu trúc của Filter Panel =====>
		// - Tuỳ chỉnh Find Input Label
		findLabel = CommonPL.getParagraphLabel("Tìm kiếm", Color.BLACK, CommonPL.getFontParagraphPlain());
		findLabel.setBounds(15, 15, 90, 24);

		// - Tuỳ chỉnh Find Input Inform Button
		findInformButton = CommonPL.getQuestionIconForm("?", "Thông tin bạn có thể tìm kiếm",
				"Bạn có thể tìm kiếm bằng các thông tin như: tên Tài khoản", Color.BLACK,
				CommonPL.getFontQuestionIcon());
		findInformButton.setBounds(110, 15, 24, 24);

		// - Tuỳ chỉnh Find Input Text Field
		findInputTextField = new CommonPL.CustomTextField(0, 20, 20, "Nhập thông tin", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		findInputTextField.setBounds(15, 45, 360, 40);

		// - Tuỳ chỉnh Sort Label
		sortLabel = CommonPL.getParagraphLabel("Sắp xếp", Color.BLACK, CommonPL.getFontParagraphPlain());
		sortLabel.setBounds(390, 15, 360, 24);

		// - Tuỳ chỉnh Sort Checkboxs
		String[] sorts = new String[] { "Tên tài khoản tăng dần", "Tên tài khoản giảm dần" };
		sortCheckboxs = CommonPL.getMapHasValues(sorts);

		// - Tuỳ chỉnh Sort Button
		sortButton = CommonPL.ButtonHasCheckboxs.createButtonHasCheckboxs(sortCheckboxs, sorts[0], Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		sortButton.setBounds(390, 45, 360, 40);

		// - Tuỳ chỉnh Type Label
		typeLabel = CommonPL.getParagraphLabel("Quyền", Color.BLACK, CommonPL.getFontParagraphPlain());
		typeLabel.setBounds(765, 15, 360, 24);

		// - Tuỳ chỉnh Type Radios
		String[] types = new String[] { "Tất cả", "Quản lý", "Nhân viên" };
		typeRadios = CommonPL.getMapHasValues(types);

		// - Tuỳ chỉnh Type Button
		typeButton = CommonPL.ButtonHasRadios.createButtonHasRadios(typeRadios, types[0], Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		typeButton.setBounds(765, 45, 360, 40);

		// - Tuỳ chỉnh Status Label
		statusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK, CommonPL.getFontParagraphPlain());
		statusLabel.setBounds(15, 100, 360, 24);

		// - Tuỳ chỉnh Status Radios
		String[] status = new String[] { "Tất cả", "Hoạt động", "Tạm dừng" };
		statusRadios = CommonPL.getMapHasValues(status);

		// - Tuỳ chỉnh Status Button
		statusButton = CommonPL.ButtonHasRadios.createButtonHasRadios(statusRadios, status[0], Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		statusButton.setBounds(15, 130, 360, 40);

		// - Tuỳ chỉnh Numbers Of Row Label
		numbersOfRowLabel = CommonPL.getParagraphLabel("Số dòng:", Color.BLACK, new Font("Airal", Font.PLAIN, 14));
		numbersOfRowLabel.setBounds(765, 100, 62, 24);

		// - Tuỳ chỉnh Numbers Of Row Text Field
		numbersOfRowTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập số dòng", Color.LIGHT_GRAY, Color.BLACK,
				new Font("Airal", Font.PLAIN, 14));
		numbersOfRowTextField.setBounds(832, 100, 293, 24);

		// - Tuỳ chỉnh Filter Apply Button
		filterApplyButton = CommonPL.getRoundedBorderButton(20, "Lọc", Color.decode("#007bff"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		filterApplyButton.setBounds(765, 130, 170, 40);

		// - Tuỳ chỉnh Filter Reset Button
		filterResetButton = CommonPL.getRoundedBorderButton(20, "Đặt lại", Color.decode("#f44336"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		filterResetButton.setBounds(955, 130, 170, 40);

		// - Tuỳ chỉnh Filter Panel
		filterPanel = new CommonPL.RoundedPanel(12);
		filterPanel.setLayout(null);
		filterPanel.setBounds(30, 115, 1140, 185);
		filterPanel.setBackground(Color.WHITE);
		filterPanel.add(findLabel);
		filterPanel.add(findInformButton);
		filterPanel.add(findInputTextField);
		filterPanel.add(sortLabel);
		filterPanel.add(sortButton);
		filterPanel.add(typeLabel);
		filterPanel.add(typeButton);
		filterPanel.add(statusLabel);
		filterPanel.add(statusButton);
//		filterPanel.add(numbersOfRowLabel);
//		filterPanel.add(numbersOfRowTextField);
		filterPanel.add(filterApplyButton);
		filterPanel.add(filterResetButton);
		// <==================== ====================>

		// <===== Cấu trúc của Data Pane =====>
		// - Tuỳ chỉnh Add Button
		addButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
				CommonPL.getMiddlePathToShowIcon() + "add-icon.png", "Thêm", Color.BLACK, Color.decode("#699f4c"),
				Color.BLACK, Color.decode("#699f4c"), CommonPL.getFontParagraphBold());
		addButton.setBounds(15, 15, 210, 40);

		// - Tuỳ chỉnh Update Button
		updateButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
				CommonPL.getMiddlePathToShowIcon() + "update-icon.png", "Thay đổi", Color.BLACK,
				Color.decode("#bf873e"), Color.BLACK, Color.decode("#bf873e"), CommonPL.getFontParagraphBold());
		updateButton.setBounds(240, 15, 210, 40);

		// - Tuỳ chỉnh Delete Button
		deleteButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
				CommonPL.getMiddlePathToShowIcon() + "delete-icon.png", "Xoá", Color.BLACK, Color.decode("#9f4d4d"),
				Color.BLACK, Color.decode("#9f4d4d"), CommonPL.getFontParagraphBold());
		deleteButton.setBounds(465, 15, 210, 40);

		// - Tuỳ chỉnh Table Data và Table Scroll Pane
		tableData = CommonPL.createTableData(columns, widthColumns, datas, "account manager");
		tableScrollPane = CommonPL.createScrollPane(true, true, tableData);
		tableScrollPane.setBounds(15, 70, 1110, 400);

		// - Tuỳ chỉnh Data Pane;
		dataPanel = new CommonPL.RoundedPanel(12);
		dataPanel.setLayout(null);
		dataPanel.setBackground(Color.WHITE);
		dataPanel.setBounds(30, 330, 1140, 485);
		dataPanel.add(addButton);
		dataPanel.add(updateButton);
		dataPanel.add(tableScrollPane);
		// <==================== ====================>

		// Đĩnh nghĩa tính chất cho PL
		this.setSize(CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
		this.setLayout(null);
		this.add(titleLabel);
		this.add(filterPanel);
		this.add(dataPanel);

		tableData.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				rowSelected = tableData.getSelectedRow();
			}
		});
		addButton.addActionListener(e -> {
			showAddOrUpdateDialog("Thêm Người dùng", "Thêm", new Vector<Object>());
			rowSelected = -1;
			valueSelected[0] = false;
			tableData.clearSelection();
		});
		updateButton.addActionListener(e -> {
			if (rowSelected != -1) {
				Vector<Object> currentObject = new Vector<>();
				for (int i = 0; i < widthColumns.length; i++) {
					currentObject.add(tableData.getValueAt(rowSelected, i));
				}

				showAddOrUpdateDialog("Thay đổi Người dùng", "Thay đổi", currentObject);
			} else {
				CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần thay đổi");
			}
			rowSelected = -1;
			valueSelected[0] = false;
			tableData.clearSelection();
		});
		deleteButton.addActionListener(e -> {
			if (rowSelected != -1) {
				CommonPL.createSelectionsDialog("Thông báo lựa chọn", "Có chắc chắn muốn xoá dòng dữ liệu này ?",
						valueSelected);
				System.out.println(valueSelected[0]);
			} else {
				CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần xoá");
			}
			rowSelected = -1;
			valueSelected[0] = false;
			tableData.clearSelection();
		});
	}

	private JLabel addOrUpdateIdLabel;
	private JTextField addOrUpdateIdTextField;
	private JLabel addOrUpdateFullnameLabel;
	private JTextField addOrUpdateFullnameTextField;
	private JLabel addOrUpdatePhoneLabel;
	private JTextField addOrUpdatePhoneTextField;
	private JLabel addOrUpdateEmailLabel;
	private JTextField addOrUpdateEmailTextField;
	private JLabel addOrUpdateAddressLabel;
	private JButton addOrUpdateAddressButton;
	private JTextField addOrUpdateAddressTextField;

	// Hàm hiển thị Dialog thêm một tài khoản
	private void showAddOrUpdateDialog(String title, String button, Vector<Object> object) {
		// <===== Cấu trúc của Add Block Panel =====>
		// - Tuỳ chỉnh Add Or Update Id Label
		addOrUpdateIdLabel = CommonPL.getParagraphLabel("Mã người dùng", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateIdLabel.setBounds(20, 10, 460, 40);

		// - Tuỳ chỉnh Add Or Update Id Text Field
		addOrUpdateIdTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mã người dùng", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateIdTextField.setBounds(20, 50, 460, 40);

		// - Tuỳ chỉnh Add Or Update Fullname Label
		addOrUpdateFullnameLabel = CommonPL.getParagraphLabel("Họ và tên", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateFullnameLabel.setBounds(500, 10, 460, 40);

		// - Tuỳ chỉnh Add Or Update Fullname Text Field
		addOrUpdateFullnameTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Họ và tên", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateFullnameTextField.setBounds(500, 50, 460, 40);

		// - Tuỳ chỉnh Add Or Update Phone Label
		addOrUpdatePhoneLabel = CommonPL.getParagraphLabel("Số điện thoại", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdatePhoneLabel.setBounds(20, 100, 220, 40);

		// - Tuỳ chỉnh Add Or Update Phone Text Field
		addOrUpdatePhoneTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Số điện thoại", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdatePhoneTextField.setBounds(20, 140, 220, 40);

		// - Tuỳ chỉnh Add Or Update Email Label
		addOrUpdateEmailLabel = CommonPL.getParagraphLabel("Email", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateEmailLabel.setBounds(260, 100, 220, 40);

		// - Tuỳ chỉnh Add Or Update Email Text Field
		addOrUpdateEmailTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Email", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateEmailTextField.setBounds(260, 140, 220, 40);

		// - Tuỳ chỉnh Add Or Update Address Label
		addOrUpdateAddressLabel = CommonPL.getParagraphLabel("Địa chỉ", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateAddressLabel.setBounds(500, 100, 460, 40);

		// - Tuỳ chỉnh Add Or Update Address Text Field
		addOrUpdateAddressTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Địa chỉ", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateAddressTextField.setBounds(500, 140, 460, 40);

		// - Tuỳ chỉnh Add Or Update Username Label
		addOrUpdateUsernameLabel = CommonPL.getParagraphLabel("Tên tài khoản", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateUsernameLabel.setBounds(20, 190, 460, 40);

		// - Tuỳ chỉnh Add Or Update Username Text Field
		addOrUpdateUsernameTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Tên tài khoản", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateUsernameTextField.setBounds(20, 230, 460, 40);

		// - Tuỳ chỉnh Add Or Update Password Label
		addOrUpdatePasswordLabel = CommonPL.getParagraphLabel("Mật khẩu", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdatePasswordLabel.setBounds(20, 280, 460, 40);

		// - Tuỳ chỉnh Add Or Update Password Text Field
		addOrUpdatePasswordTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mật khẩu", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdatePasswordTextField.setBounds(20, 320, 460, 40);

		// - Tuỳ chỉnh Add Or Update Status Label
		addOrUpdateStatusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateStatusLabel.setBounds(20, 370, 460, 40);

		// - Tuỳ chỉnh Add Or Update Status ComboBox
		Vector<String> status = CommonPL
				.getVectorHasValues(new String[] { "Chọn Trạng thái", "Hoạt động", "Tạm dừng" });
		addOrUpdateStatusComboBox = CommonPL.CustomComboBox(status, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateStatusComboBox.setBounds(20, 410, 460, 40);

		// - Tuỳ chỉnh Add Or Update Type Label
		addOrUpdateTypeLabel = CommonPL.getParagraphLabel("Quyền", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateTypeLabel.setBounds(500, 190, 460, 40);

		// - Tuỳ chỉnh Add Or Update Type ComboBox
		Vector<String> types = CommonPL.getVectorHasValues(new String[] { "Chọn Quyền", "Quản lý", "Nhân viên" });
		addOrUpdateTypeComboBox = CommonPL.CustomComboBox(types, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateTypeComboBox.setBounds(500, 230, 460, 40);

		// - Tuỳ chỉnh Add Or Update Type Detail Label
		addOrUpdateTypeDetailLabel = CommonPL.getParagraphLabel("<html><strike>Chi tiết Quyền</strike></html>",
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateTypeDetailLabel.setBounds(500, 280, 460, 40);

		// - Tuỳ chỉnh Add Or Update Type Detail Panel
		addOrUpdateTypeDetailPanel = new JPanel();
		addOrUpdateTypeDetailPanel.setLayout(null);
		addOrUpdateTypeDetailPanel.setBounds(500, 320, 460, 220);
		addOrUpdateTypeDetailPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		addOrUpdateTypeDetailPanel.setBackground(Color.WHITE);
		renderAddTypeDetailPanel();

		// - Tuỳ chỉnh Add Or Update Time Label
		addOrUpdateTimeLabel = CommonPL.getParagraphLabel("Cập nhật gần đây:", Color.GRAY,
				new Font("Arial", Font.ITALIC, 18));
		addOrUpdateTimeLabel.setBounds(260, 550, 148, 40);

		// - Tuỳ chỉnh Add Or Update Time Detail Label
		addOrUpdateTimeDetailLabel = CommonPL.getTitleLabel("yyyy-MM-dd HH:mm:ss", Color.GRAY,
				new Font("Arial", Font.ITALIC, 18), SwingConstants.RIGHT, SwingConstants.CENTER);
		addOrUpdateTimeDetailLabel.setBounds(413, 550, 307, 40);
		// -- Tạo Timer cập nhật thời gian
		Timer timer = new Timer(1000, e -> {
			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDateTime = currentDateTime.format(formatter);
			addOrUpdateTimeDetailLabel.setText(formattedDateTime);
		});
		timer.start();

		// - Tuỳ chỉnh Add Or Update Button
		addOrUpdateButton = CommonPL.getRoundedBorderButton(20, button,
				button == "Thêm" ? Color.decode("#699f4c") : Color.decode("#bf873e"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		addOrUpdateButton.setBounds(260, 590, 460, 40);
		SwingUtilities.invokeLater(() -> addOrUpdateButton.requestFocusInWindow());

		// Khi "Thay đổi" một Tài khoản
		if (title.equals("Thay đổi Người dùng") && button.equals("Thay đổi") && object.size() != 0) {
			// - Gán dữ liệu là "Tên tài khoản"
			addOrUpdateUsernameTextField.setText((String) object.get(0));
			addOrUpdateUsernameTextField.setEnabled(false);
			addOrUpdateUsernameTextField.setBackground(Color.decode("#dedede"));

			// - Gán dữ liệu là "Mật khẩu"
			addOrUpdatePasswordTextField.setText((String) object.get(1));
			addOrUpdatePasswordTextField.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Quyền"
			addOrUpdateTypeComboBox.setSelectedItem((String) object.get(2));
			addOrUpdateTypeComboBox.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Chi tiết quyền"
			String[] typesDetail = ((String) object.get(3)).split("\\,");
			// + Lấy ra tất cả các JButton ở Main Menu
			ArrayList<JButton> buttonsInMainMenu = CommonPL.getAllButtons((new AdminMenuPL()).getMainMenuPanel());
			ArrayList<JButton> buttons = new ArrayList<>();
			buttons.addAll(buttonsInMainMenu);
			// + Mặc dinh là mục "Chọn Quyền" hiển thị đầu tiên
			Map<String, Boolean> checkboxs = new LinkedHashMap<>();
			for (JButton buttonn : buttons) {
				String itemValue = ((JLabel) buttonn.getComponent(1)).getText();
				if (!itemValue.equals("Đăng xuất")) {
					checkboxs.put(itemValue, false);
				}
			}
			for (String key : checkboxs.keySet()) {
				for (String type : typesDetail) {
					if (key.equals(type)) {
						checkboxs.put(key, true);
					}
				}
			}
			// - Cập nhật các checkbox mới
			addOrUpdateTypeDetailPanel.removeAll();
			int x = 10, y = 10;
			for (String key : checkboxs.keySet()) {
				JCheckBox itemCheckBox = new JCheckBox();
				if (checkboxs.get(key) == true) {
					itemCheckBox.setSelected(true);
				}
				itemCheckBox.setText(key);
				itemCheckBox.setFont(new Font("Arial", Font.PLAIN, 18));
				itemCheckBox.setForeground(Color.BLACK);
				itemCheckBox.setBounds(x, y, 215, 24);
				itemCheckBox.addActionListener(e2 -> {
					if (checkboxs.get(key) == true) {
						checkboxs.replace(key, false);
					} else {
						checkboxs.replace(key, true);
					}
				});

				addOrUpdateTypeDetailPanel.add(itemCheckBox);

				if (x == 10) {
					x = 235;
				} else {
					x = 10;
					y += 34;
				}

			}
			addOrUpdateTypeDetailPanel.revalidate();
			addOrUpdateTypeDetailPanel.repaint();

			// - Gán dữ liệu là "Trạng thái"
			addOrUpdateStatusComboBox.setSelectedItem((String) object.get(4));
			addOrUpdateStatusComboBox.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Thời gian cập nhật gần đây"
//							addOrUpdateTimeDetailLabel.setText((String) object.get(11));
		}

		// - Tuỳ chỉnh Add Or Update Block Panel
		addOrUpdateBlockPanel = new JPanel();
		addOrUpdateBlockPanel.setLayout(null);
		addOrUpdateBlockPanel.setBounds(0, 0, 980, 680);
		addOrUpdateBlockPanel.setBackground(Color.WHITE);
		addOrUpdateBlockPanel.add(addOrUpdateIdLabel);
		addOrUpdateBlockPanel.add(addOrUpdateIdTextField);
		addOrUpdateBlockPanel.add(addOrUpdateFullnameLabel);
		addOrUpdateBlockPanel.add(addOrUpdateFullnameTextField);
		addOrUpdateBlockPanel.add(addOrUpdatePhoneLabel);
		addOrUpdateBlockPanel.add(addOrUpdatePhoneTextField);
		addOrUpdateBlockPanel.add(addOrUpdateEmailLabel);
		addOrUpdateBlockPanel.add(addOrUpdateEmailTextField);
		addOrUpdateBlockPanel.add(addOrUpdateAddressLabel);
		addOrUpdateBlockPanel.add(addOrUpdateAddressTextField);
		addOrUpdateBlockPanel.add(addOrUpdateUsernameLabel);
		addOrUpdateBlockPanel.add(addOrUpdateUsernameTextField);
		addOrUpdateBlockPanel.add(addOrUpdatePasswordLabel);
		addOrUpdateBlockPanel.add(addOrUpdatePasswordTextField);
		addOrUpdateBlockPanel.add(addOrUpdateTypeLabel);
		addOrUpdateBlockPanel.add(addOrUpdateTypeComboBox);
		addOrUpdateBlockPanel.add(addOrUpdateTypeDetailLabel);
		addOrUpdateBlockPanel.add(addOrUpdateTypeDetailPanel);
		addOrUpdateBlockPanel.add(addOrUpdateStatusLabel);
		addOrUpdateBlockPanel.add(addOrUpdateStatusComboBox);
		addOrUpdateBlockPanel.add(addOrUpdateTimeLabel);
		addOrUpdateBlockPanel.add(addOrUpdateTimeDetailLabel);
		addOrUpdateBlockPanel.add(addOrUpdateButton);
		// <==================== ====================>

		// Định nghĩa tính chất cho Add Or Update Dialog
		addOrUpdateDialog = new JDialog();
		addOrUpdateDialog.setTitle(title);
		addOrUpdateDialog.setLayout(null);
		addOrUpdateDialog.setSize(980, 680);
		addOrUpdateDialog.setResizable(false);
		addOrUpdateDialog.setLocationRelativeTo(null);
		addOrUpdateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addOrUpdateDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent e) {
				// Đóng Dialog khi mất focus (nhấn ngoài)
				addOrUpdateDialog.dispose();
			}
		});
		addOrUpdateDialog.add(addOrUpdateBlockPanel);
		addOrUpdateDialog.setModal(true);
		addOrUpdateDialog.setVisible(true);
	}

	// Hàm đặt lại "Chi tiết Quyền" với "Quyền" được chọn tương ứng
	private void renderAddTypeDetailPanel() {
		// - Lấy ra tất cả các JButton ở Main Menu
		ArrayList<JButton> buttonsInMainMenu = CommonPL.getAllButtons((new AdminMenuPL()).getMainMenuPanel());
		ArrayList<JButton> buttons = new ArrayList<>();
		buttons.addAll(buttonsInMainMenu);

		// - Mặc dinh là mục "Chọn Quyền" hiển thị đầu tiên
		Map<String, Boolean> checkboxs = new LinkedHashMap<>();
		for (JButton button : buttons) {
			String itemValue = ((JLabel) button.getComponent(1)).getText();
			if (!itemValue.equals("Đăng xuất")) {
				checkboxs.put(itemValue, false);
			}
		}
		int x = 10, y = 10;
		for (String key : checkboxs.keySet()) {
			JCheckBox itemCheckBox = new JCheckBox();
			if (checkboxs.get(key) == true) {
				itemCheckBox.setSelected(true);
			}
			itemCheckBox.setText(key);
			itemCheckBox.setFont(new Font("Arial", Font.PLAIN, 18));
			itemCheckBox.setBounds(x, y, 215, 24);
			itemCheckBox.setEnabled(false);
			addOrUpdateTypeDetailPanel.add(itemCheckBox);
			if (x == 10) {
				x = 235;
			} else {
				x = 10;
				y += 34;
			}
		}

		// - Mỗi lần thay đổi "Quyền" thì phải có "Chi tiết Quyền" tương ứng
		addOrUpdateTypeComboBox.addActionListener(e1 -> {
			String typeValueSelected = (String) addOrUpdateTypeComboBox.getSelectedItem();
			if (typeValueSelected.equals("Quản lý")) {
				addOrUpdateTypeDetailPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				addOrUpdateTypeDetailLabel.setText("Chi tiết Quyền");
				for (JButton button : buttons) {
					String itemValue = ((JLabel) button.getComponent(1)).getText();
					if (!itemValue.equals("Đăng xuất")) {
						checkboxs.put(itemValue, true);
					}
				}
			} else if (typeValueSelected.equals("Nhân viên")) {
				addOrUpdateTypeDetailPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				addOrUpdateTypeDetailLabel.setText("Chi tiết Quyền");
				for (JButton button : buttons) {
					String itemValue = ((JLabel) button.getComponent(1)).getText();
					if (!itemValue.equals("Đăng xuất")) {
						if (itemValue.equals("Thống kê")) {
							checkboxs.put(itemValue, true);
						} else {
							checkboxs.put(itemValue, false);
						}
					}
				}
			} else if (typeValueSelected.equals("Chọn Quyền")) {
				addOrUpdateTypeDetailPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
				addOrUpdateTypeDetailLabel.setText("<html><strike>Chi tiết Quyền</strike></html>");
				for (JButton button : buttons) {
					String itemValue = ((JLabel) button.getComponent(1)).getText();
					if (!itemValue.equals("Đăng xuất")) {
						checkboxs.put(itemValue, false);
					}
				}
			}

			// - Cập nhật các checkbox mới
			addOrUpdateTypeDetailPanel.removeAll();
			int subX = 10, subY = 10;
			for (String key : checkboxs.keySet()) {
				JCheckBox itemCheckBox = new JCheckBox();
				if (checkboxs.get(key) == true) {
					itemCheckBox.setSelected(true);
				}
				itemCheckBox.setText(key);
				itemCheckBox.setFont(new Font("Arial", Font.PLAIN, 18));
				itemCheckBox.setForeground(Color.BLACK);
				itemCheckBox.setBounds(subX, subY, 215, 24);

				if (subX == 10) {
					subX = 235;
				} else {
					subX = 10;
					subY += 34;
				}

				// Nếu "Quyền" đang được chọn là: "Chọn Quyền"
				if (typeValueSelected.equals("Chọn Quyền")) {
					itemCheckBox.setEnabled(false);
					itemCheckBox.setForeground(Color.LIGHT_GRAY);
				} else {
					itemCheckBox.addActionListener(e2 -> {
						if (checkboxs.get(key) == true) {
							checkboxs.replace(key, false);
						} else {
							checkboxs.replace(key, true);
						}
					});
				}

				addOrUpdateTypeDetailPanel.add(itemCheckBox);
			}
			addOrUpdateTypeDetailPanel.revalidate();
			addOrUpdateTypeDetailPanel.repaint();
		});
	}
}
