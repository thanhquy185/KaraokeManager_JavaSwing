package PL;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
import javax.swing.UIManager;

import PL.CommonPL.CustomTextField;

public class Admin_SupplierManagerPL extends JPanel {
	// Các Component
	private JLabel titleLabel;
	// - Các Component của Filter Panel
	private JLabel findLabel;
	private JTextField findInputTextField;
	private JButton findInformButton;
	private JLabel sortLabel;
	private Map<String, Boolean> sortCheckboxs;
	private JButton sortButton;
	private JLabel statusLabel;
	private Map<String, Boolean> statusCheckboxs;
	private JButton statusButton;
	private JButton filterApplyButton;
	private JButton filterResetButton;
	private JPanel filterPanel;
	// - Các Component của Data Panel
	private JButton addButton;
	private JButton updateButton;
	private JButton lockButton;
	private JTable tableData;
	private JScrollPane tableScrollPane;
	private JPanel dataPanel;
	// - Các Component của Add Or Update Dialog
	private JLabel addOrUpdateIdLabel;
	private JTextField addOrUpdateIdTextField;
	private JLabel addOrUpdateNameLabel;
	private JTextField addOrUpdateNameTextField;
	private JLabel addOrUpdatePhoneLabel;
	private JTextField addOrUpdatePhoneTextField;
	private JLabel addOrUpdateEmailLabel;
	private JTextField addOrUpdateEmailTextField;
	private JLabel addOrUpdateAddressLabel;
	private JButton addOrUpdateAddressButton;
	private JTextField addOrUpdateAddressTextField;
	private JLabel addOrUpdateStatusLabel;
	private JComboBox<String> addOrUpdateStatusComboBox;
	private JLabel addOrUpdateTimeLabel;
	private JLabel addOrUpdateTimeDetailLabel;
	private JButton addOrUpdateButton;
	private JPanel addOrUpdateBlockPanel;
	private JDialog addOrUpdateDialog;
	// - Các Component của Add Or Update Address Detail Dialog
	private JLabel addressDetailHouseNumberAndStreetNameLabel;
	private JTextField addressDetailHouseNumberAndStreetNameTextField;
	private JLabel addressDetailCityNameLabel;
	private JComboBox<String> addressDetailCityNameComboBox;
	private JLabel addressDetailDistrictNameLabel;
	private JComboBox<String> addressDetailDistrictNameComboBox;
	private JLabel addressDetailWardNameLabel;
	private JComboBox<String> addressDetailWardNameComboBox;
	private JButton addressDetailApplyButton;
	private JPanel addressDetailBlockPanel;
	private JDialog addressDetailDialog;
	private String addressDetailHouseNumberAndStreetNameInput = null;
	private String addressDetailWardNameSelected = null;
	private String addressDetailDistrictNameSelected = null;
	private String addressDetailCityNameSelected = null;
	// - Các thông tin cần có của Table Data và Table Scroll Pane
	// + Tiêu đề các cột
	private final String[] columns = { "Mã NCC", "Tên NCC", "Số điện thoại", "Email", "Địa chỉ", "Trạng thái" };
	// + Chiều rộng các cột
	private final int[] widthColumns = { 150, 500, 150, 400, 600, 150 };
	// + Dữ liệu
	private Object[][] datas = {
			{ "NCC0001", "Tiệm tạp hoá An Khang", "0909123123", "ankhangtaphoa@gmail.com", "dia chi", "Hoạt động" } };

	// + Dòng hiện tại đang được chọn
	private int rowSelected = -1;
	// + Giá trị (true / false) khi "Xoá" dòng dữ liệu
	private Boolean[] valueSelected = { null };

	public Admin_SupplierManagerPL() {
		// <===== Cấu trúc Title Label =====>
		// - Tuỳ chỉnh Title Label
		titleLabel = CommonPL.getTitleLabel("Nhà cung cấp", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER,
				SwingConstants.CENTER);
		titleLabel.setBounds(30, 0, 1110, 115);
		// <==================== ====================>

		// <===== Cấu trúc Filter Panel =====>
		// - Tuỳ chỉnh Find Input Label
		findLabel = CommonPL.getParagraphLabel("Tìm kiếm", Color.BLACK, CommonPL.getFontParagraphPlain());
		findLabel.setBounds(15, 15, 90, 24);

		// - Tuỳ chỉnh Find Input Inform Button
		findInformButton = CommonPL.getQuestionIconForm("?", "Thông tin bạn có thể tìm kiếm",
				"Bạn có thể tìm kiếm bằng các thông tin như: mã Nhà cung cấp, tên Nhà cung cấp, số điện thoại Nhà cung cấp",
				Color.BLACK, CommonPL.getFontQuestionIcon());
		findInformButton.setBounds(110, 15, 24, 24);

		// - Tuỳ chỉnh Find Input Text Field
		findInputTextField = new CommonPL.CustomTextField(0, 20, 20, "Nhập thông tin", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		findInputTextField.setBounds(15, 45, 360, 40);

		// - Tuỳ chỉnh Sort Label
		sortLabel = CommonPL.getParagraphLabel("Sắp xếp", Color.BLACK, CommonPL.getFontParagraphPlain());
		sortLabel.setBounds(390, 15, 360, 24);

		// - Tuỳ chỉnh Sort Checkboxs
		String[] sorts = new String[] { "Mã Nhà cung cấp tăng dần", "Mã Nhà cung cấp giảm dần",
				"Tên Nhà cung cấp tăng dần", "Tên Nhà cung cấp giảm dần", "Số lượng loại thuốc tăng dần",
				"Số lượng loại thuốc giảm dần" };
		sortCheckboxs = CommonPL.getMapHasValues(sorts);

		// - Tuỳ chỉnh Sort Button
		sortButton = CommonPL.ButtonHasCheckboxs.createButtonHasCheckboxs(sortCheckboxs, sorts[0], Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		sortButton.setBounds(390, 45, 360, 40);

		// - Tuỳ chỉnh Type Label
		statusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK, CommonPL.getFontParagraphPlain());
		statusLabel.setBounds(765, 15, 360, 24);

		// - Tuỳ chỉnh Type Checkboxs
		String[] status = new String[] { "Tất cả", "Hoạt động", "Tạm dừng" };
		statusCheckboxs = CommonPL.getMapHasValues(status);

		// - Tuỳ chỉnh Type Button
		statusButton = CommonPL.ButtonHasRadios.createButtonHasRadios(statusCheckboxs, status[0], Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		statusButton.setBounds(765, 45, 360, 40);

		// - Tuỳ chỉnh Filter Apply Button
		filterApplyButton = CommonPL.getRoundedBorderButton(20, "Lọc", Color.decode("#007bff"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		filterApplyButton.setBounds(765, 130, 170, 40);

		// - Tuỳ chỉnh Filter Reset Button
		filterResetButton = CommonPL.getRoundedBorderButton(20, "Đặt lại", Color.decode("#f44336"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		filterResetButton.setBounds(955, 130, 170, 40);

		// - Tuỳ chỉnh Add Or Update Filter Panel
		filterPanel = new CommonPL.RoundedPanel(12);
		filterPanel.setLayout(null);
		filterPanel.setBounds(30, 115, 1140, 185);
		filterPanel.setBackground(Color.WHITE);
		filterPanel.add(findLabel);
		filterPanel.add(findInformButton);
		filterPanel.add(findInputTextField);
		filterPanel.add(sortLabel);
		filterPanel.add(sortButton);
		filterPanel.add(statusLabel);
		filterPanel.add(statusButton);
		filterPanel.add(filterApplyButton);
		filterPanel.add(filterResetButton);

		// <===== Cấu trúc Data Panel =====>
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

		// - Tuỳ chỉnh Lock Button
		lockButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
				CommonPL.getMiddlePathToShowIcon() + "lock-icon.png", "Khoá", Color.BLACK, Color.decode("#9f4d4d"),
				Color.BLACK, Color.decode("#9f4d4d"), CommonPL.getFontParagraphBold());
		lockButton.setBounds(465, 15, 210, 40);

		// - Tuỳ chỉnh Table Data và Table Scroll Pane
		// + Tạo cấu trúc bảng dữ liệu
		tableData = CommonPL.createTableData(columns, widthColumns, datas, "supplies manager");
		tableScrollPane = CommonPL.createScrollPane(true, true, tableData);
		tableScrollPane.setBounds(15, 70, 1110, 400);

		// - Tuỳ chỉnh Data Pane;
		dataPanel = new CommonPL.RoundedPanel(12);
		dataPanel.setLayout(null);
		dataPanel.setBackground(Color.WHITE);
		dataPanel.setBounds(30, 330, 1140, 485);
		dataPanel.add(addButton);
		dataPanel.add(updateButton);
		dataPanel.add(lockButton);
		dataPanel.add(tableScrollPane);

		// Đĩnh nghĩa tính chất cho Supplier Manager PL
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
			addressDetailHouseNumberAndStreetNameInput = null;
			addressDetailCityNameSelected = null;
			addressDetailDistrictNameSelected = null;
			addressDetailWardNameSelected = null;

			showAddOrUpdateDialog("Thêm Nhà cung cấp", "Thêm", new Vector<Object>());

			valueSelected[0] = null;
			rowSelected = -1;
			tableData.clearSelection();
		});
		updateButton.addActionListener(e -> {
			if (rowSelected != -1) {
				addressDetailHouseNumberAndStreetNameInput = null;
				addressDetailCityNameSelected = null;
				addressDetailDistrictNameSelected = null;
				addressDetailWardNameSelected = null;

				Vector<Object> currentObject = new Vector<>();
				for (int i = 0; i < widthColumns.length; i++) {
					currentObject.add(tableData.getValueAt(rowSelected, i));
				}

				showAddOrUpdateDialog("Thay đổi Nhà cung cấp", "Thay đổi", currentObject);
			} else {
				CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần thay đổi");
			}

			valueSelected[0] = null;
			rowSelected = -1;
			tableData.clearSelection();
		});
		lockButton.addActionListener(e -> {
			if (rowSelected != -1) {
				CommonPL.createSelectionsDialog("Thông báo lựa chọn", "Có chắc chắn muốn khoá dòng dữ liệu này ?",
						valueSelected);
				System.out.println(valueSelected[0]);
			} else {
				CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần khoá");
			}
			rowSelected = -1;
			valueSelected[0] = false;
			tableData.clearSelection();
		});
	}

	// Hàm tạo một Dialog nhỏ khi nhấn để chọn địa chỉ
	private void showAddressDetailDialog() {
		// - Khai báo đối tượng Add Address Detail Dialog
		addressDetailDialog = new JDialog();

		// <==== Cấu trúc của Add Address
		// Detail Block Panel =====>
		// - Tuỳ chỉnh Add Address Detail House Number And
		// Street Name Label
		addressDetailHouseNumberAndStreetNameLabel = CommonPL.getParagraphLabel("Số nhà - tên đường", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addressDetailHouseNumberAndStreetNameLabel.setBounds(20, 10, 460, 40);

		// - Tuỳ chỉnh Add Address Detail House Number And
		// Street Name Text Field
		addressDetailHouseNumberAndStreetNameTextField = new CommonPL.CustomTextField(0, 0, 0,
				"Nhập Số nhà - tên đường", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addressDetailHouseNumberAndStreetNameTextField.setBounds(20, 50, 460, 40);

		// - Tuỳ chỉnh Add Address Detail City Name Label
		addressDetailCityNameLabel = CommonPL.getParagraphLabel("Tỉnh / Thành phố", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addressDetailCityNameLabel.setBounds(20, 100, 460, 40);

		// - Tuỳ chỉnh Add Address Detail City Name ComboBox
		Vector<String> citys = new Vector<>();
		addressDetailCityNameComboBox = CommonPL.CustomComboBox(citys, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addressDetailCityNameComboBox.setBounds(20, 140, 460, 40);

		// - Tuỳ chỉnh Add Address Detail District Name Label
		addressDetailDistrictNameLabel = CommonPL.getParagraphLabel("Quận / Huyện", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addressDetailDistrictNameLabel.setBounds(20, 190, 460, 40);

		// - Tuỳ chỉnh Add Address Detail District Name ComboBox
		Vector<String> districts = new Vector<>();
		districts.add("Chọn Quận / Huyện");
		addressDetailDistrictNameComboBox = CommonPL.CustomComboBox(districts, Color.WHITE, Color.LIGHT_GRAY,
				Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addressDetailDistrictNameComboBox.setBounds(20, 230, 460, 40);

		// - Tuỳ chỉnh Add Address Detail Ward Name Label
		addressDetailWardNameLabel = CommonPL.getParagraphLabel("Phường / Xã", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addressDetailWardNameLabel.setBounds(20, 280, 460, 40);

		// - Tuỳ chỉnh Add Address Detail Ward Name ComboBox
		Vector<String> wards = new Vector<>();
		wards.add("Chọn Phường / Xã");
		addressDetailWardNameComboBox = CommonPL.CustomComboBox(wards, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addressDetailWardNameComboBox.setBounds(20, 320, 460, 40);

		// - Gán sự kiện thay đổi khi thay đổi địa chỉ tương ứng
		CommonPL.renderAllComponentToSelectAddress(addressDetailHouseNumberAndStreetNameInput,
				addressDetailHouseNumberAndStreetNameTextField, addressDetailCityNameSelected,
				addressDetailCityNameComboBox, addressDetailDistrictNameSelected, addressDetailDistrictNameComboBox,
				addressDetailWardNameSelected, addressDetailWardNameComboBox);

		// - Tuỳ chỉnh Add Address Detail Apply Button
		addressDetailApplyButton = CommonPL.getButtonDefaultForm("Chọn địa chỉ", CommonPL.getFontParagraphBold());
		addressDetailApplyButton.setBounds(20, 390, 460, 40);
		addressDetailApplyButton.addActionListener(e -> {
			// - Lấy ra các thông tin để kiểm tra
			String houseNumberAndStreetName = addressDetailHouseNumberAndStreetNameTextField.getText();
			String cityName = (String) addressDetailCityNameComboBox.getSelectedItem();
			String districtName = (String) addressDetailDistrictNameComboBox.getSelectedItem();
			String wardName = (String) addressDetailWardNameComboBox.getSelectedItem();

			if (houseNumberAndStreetName.equals("Nhập Số nhà - tên đường") || wardName.equals("Chọn Phường / Xã")
					|| districtName.equals("Chọn Quận / Huyện") || cityName.equals("Chọn Tỉnh / Thành phố")) {
				addressDetailApplyButton.setBackground(Color.BLACK);
				CommonPL.createErrorDialog("Thông báo lỗi", "Cần cung cấp đầy đủ thông tin cần thiết");
			} else {
				// - Đã có ít nhất 1 lần chọn địa chỉ thành công
				addressDetailHouseNumberAndStreetNameInput = houseNumberAndStreetName;
				addressDetailCityNameSelected = cityName;
				addressDetailDistrictNameSelected = districtName;
				addressDetailWardNameSelected = wardName;

				// - Gán địa chỉ cho khung chứa thông tin địa chỉ
				addOrUpdateAddressTextField
						.setText(houseNumberAndStreetName + ", " + wardName + ", " + districtName + ", " + cityName);
				addOrUpdateAddressTextField.setForeground(Color.BLACK);

				// - Đóng Add Address Detail Dialog
				addressDetailDialog.dispose();
			}

		});
		SwingUtilities.invokeLater(() -> addressDetailApplyButton.requestFocusInWindow());

		// - Tuỳ chỉnh Add Address Detail Block Panel
		addressDetailBlockPanel = new JPanel();
		addressDetailBlockPanel.setLayout(null);
		addressDetailBlockPanel.setBounds(0, 0, 500, 480);
		addressDetailBlockPanel.setBackground(Color.WHITE);
		addressDetailBlockPanel.add(addressDetailHouseNumberAndStreetNameLabel);
		addressDetailBlockPanel.add(addressDetailHouseNumberAndStreetNameTextField);
		addressDetailBlockPanel.add(addressDetailCityNameLabel);
		addressDetailBlockPanel.add(addressDetailCityNameComboBox);
		addressDetailBlockPanel.add(addressDetailDistrictNameLabel);
		addressDetailBlockPanel.add(addressDetailDistrictNameComboBox);
		addressDetailBlockPanel.add(addressDetailWardNameLabel);
		addressDetailBlockPanel.add(addressDetailWardNameComboBox);
		addressDetailBlockPanel.add(addressDetailApplyButton);

		// <==================== ====================>

		// Định nghĩa tính chất cho Add Address Detail Dialog
		addressDetailDialog.setTitle("Chọn địa chỉ");
		addressDetailDialog.setLayout(null);
		addressDetailDialog.setSize(500, 480);
		addressDetailDialog.setResizable(false);
		addressDetailDialog.setAutoRequestFocus(false);
		addressDetailDialog.setLocationRelativeTo(null);
		addressDetailDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//				addressDetailDialog.addWindowListener(new WindowAdapter() {
//					@Override
//					public void windowDeactivated(WindowEvent e) {
//						SwingUtilities.invokeLater(() -> addOrUpdateAddressTextField.requestFocusInWindow());
//						addressDetailDialog.dispose(); // Đóng Dialog khi mất focus (nhấn ngoài)
//					}
//				});
		addressDetailDialog.add(addressDetailBlockPanel);
		addressDetailDialog.setModal(true);
		addressDetailDialog.setVisible(true);
	}

	// Hàm tạo Dialog cho phép thêm hoặc sửa một nhà cung cấp
	private void showAddOrUpdateDialog(String title, String button, Vector<Object> object) {
		// <===== Cấu trúc Add Or Update Block Panel =====>
		// - Tuỳ chỉnh Add Or Update Id Label
		addOrUpdateIdLabel = CommonPL.getParagraphLabel("Mã NCC", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateIdLabel.setBounds(20, 10, 460, 40);

		// - Tuỳ chỉnh Add Or Update Id Text Field
		addOrUpdateIdTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mã NCC", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateIdTextField.setBounds(20, 50, 460, 40);

		// - Tuỳ chỉnh Add Or Update Name Label
		addOrUpdateNameLabel = CommonPL.getParagraphLabel("Tên NCC", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateNameLabel.setBounds(20, 100, 460, 40);

		// - Tuỳ chỉnh Add Or Update Name Text Field
		addOrUpdateNameTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Tên NCC", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateNameTextField.setBounds(20, 140, 460, 40);

		// - Tuỳ chỉnh Add Or Update Phone Label
		addOrUpdatePhoneLabel = CommonPL.getParagraphLabel("Số điện thoại", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdatePhoneLabel.setBounds(20, 190, 220, 40);

		// - Tuỳ chỉnh Add Or Update Phone Text Field
		addOrUpdatePhoneTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập SĐT", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdatePhoneTextField.setBounds(20, 230, 220, 40);

		// - Tuỳ chỉnh Add Or Update Email Label
		addOrUpdateEmailLabel = CommonPL.getParagraphLabel("Email", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateEmailLabel.setBounds(260, 190, 220, 40);

		// - Tuỳ chỉnh Add Or Update Email Text Field
		addOrUpdateEmailTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Email", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateEmailTextField.setBounds(260, 230, 220, 40);

		// - Tuỳ chỉnh Add Or Update Address Label
		addOrUpdateAddressLabel = CommonPL.getParagraphLabel("Địa chỉ", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateAddressLabel.setBounds(20, 280, 460, 40);

		// - Tuỳ chỉnh Add Or Update Address Button
		addOrUpdateAddressButton = CommonPL.getButtonDefaultForm("Nhấn để chọn địa chỉ",
				new Font("Arial", Font.BOLD, 14));
		addOrUpdateAddressButton.setBounds(300, 286, 180, 28);
		addOrUpdateAddressButton.addActionListener(e -> {
//							addOrUpdateDialog.setVisible(true);
			addOrUpdateAddressButton.setBackground(Color.BLACK);
			showAddressDetailDialog();

//							addOrUpdateDialog.setVisible(false);
//							SwingUtilities.invokeLater(() -> {
//								addOrUpdateAddressButton.setBackground(Color.BLACK);
//								showAddressDetailDialog();
//								addOrUpdateDialog.setVisible(true);
//							});
		});

		// - Tuỳ chỉnh Add Or Update Address Text Field
		addOrUpdateAddressTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Địa chỉ", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateAddressTextField.setBounds(20, 320, 460, 40);

		// - Tuỳ chỉnh Add Or Update Status Label
		addOrUpdateStatusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateStatusLabel.setBounds(20, 370, 460, 40);

		// - Tuỳ chỉnh Add Or Update Status ComboBox
		String[] status = new String[] { "Chọn Trạng thái", "Hoạt động", "Tạm dừng" };
		Vector<String> statusVector = CommonPL.getVectorHasValues(status);
		addOrUpdateStatusComboBox = CommonPL.CustomComboBox(statusVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateStatusComboBox.setBounds(20, 410, 460, 40);

		// - Tuỳ chỉnh Add Or Update Status Label
		addOrUpdateTimeLabel = CommonPL.getParagraphLabel("Cập nhật gần đây:", Color.GRAY,
				new Font("Arial", Font.ITALIC, 18));
		addOrUpdateTimeLabel.setBounds(20, 460, 148, 40);

		// - Tuỳ chỉnh Add Or Update Time Detail Label
		addOrUpdateTimeDetailLabel = CommonPL.getTitleLabel("yyyy-MM-dd HH:mm:ss", Color.GRAY,
				new Font("Arial", Font.ITALIC, 18), SwingConstants.RIGHT, SwingConstants.CENTER);
		addOrUpdateTimeDetailLabel.setBounds(173, 460, 307, 40);
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
		addOrUpdateButton.setBounds(20, 500, 460, 40);
		SwingUtilities.invokeLater(() -> addOrUpdateButton.requestFocusInWindow());

		// Khi "Thay đổi" một Nhà cung cấp
		if (title.equals("Thay đổi Nhà cung cấp") && button.equals("Thay đổi") && object.size() != 0) {
			// - Gán dữ liệu là "Mã NCC"
			addOrUpdateIdTextField.setText((String) object.get(0));
			addOrUpdateIdTextField.setEnabled(false);
			addOrUpdateIdTextField.setCaretPosition(0);
			((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
			addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));

			// - Gán dữ liệu là "Tên NCC"
			addOrUpdateNameTextField.setText((String) object.get(1));
			addOrUpdateNameTextField.setCaretPosition(0);
			addOrUpdateNameTextField.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Số điện thoại"
			addOrUpdatePhoneTextField.setText((String) object.get(2));
			addOrUpdatePhoneTextField.setCaretPosition(0);
			addOrUpdatePhoneTextField.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Email"
			addOrUpdateEmailTextField.setText((String) object.get(3));
			addOrUpdateEmailTextField.setCaretPosition(0);
			addOrUpdateEmailTextField.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Địa chỉ"
			addOrUpdateAddressTextField.setText((String) object.get(4));
			addOrUpdateAddressTextField.setCaretPosition(0);
			addOrUpdateAddressTextField.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Trạng thái"
			addOrUpdateStatusComboBox.setSelectedItem((String) object.get(5));
			addOrUpdateStatusComboBox.setEnabled(false);
			((JTextField) addOrUpdateStatusComboBox.getEditor().getEditorComponent()).setCaretPosition(0);
			UIManager.put("ComboBox.disabledBackground", Color.decode("#dedede"));
			addOrUpdateStatusComboBox.setBorder(BorderFactory.createLineBorder(Color.decode("#dedede"), 1));

			// - Gán dữ liệu là "Thời gian cập nhật gần đây"
//					addOrUpdateTimeDetailLabel.setText((String) object.get(11));
		}

		// - Tuỳ chỉnh Add Or Update Block Panel
		addOrUpdateBlockPanel = new JPanel();
		addOrUpdateBlockPanel.setLayout(null);
		addOrUpdateBlockPanel.setBounds(0, 0, 500, 590);
		addOrUpdateBlockPanel.setBackground(Color.WHITE);
		addOrUpdateBlockPanel.add(addOrUpdateIdLabel);
//		addOrUpdateBlockPanel.add(addOrUpdateIdButton);
		addOrUpdateBlockPanel.add(addOrUpdateIdTextField);
		addOrUpdateBlockPanel.add(addOrUpdateNameLabel);
		addOrUpdateBlockPanel.add(addOrUpdateNameTextField);
		addOrUpdateBlockPanel.add(addOrUpdateStatusLabel);
		addOrUpdateBlockPanel.add(addOrUpdateStatusComboBox);
		addOrUpdateBlockPanel.add(addOrUpdatePhoneLabel);
		addOrUpdateBlockPanel.add(addOrUpdatePhoneTextField);
		addOrUpdateBlockPanel.add(addOrUpdateEmailLabel);
		addOrUpdateBlockPanel.add(addOrUpdateEmailTextField);
		addOrUpdateBlockPanel.add(addOrUpdateAddressLabel);
		addOrUpdateBlockPanel.add(addOrUpdateAddressButton);
		addOrUpdateBlockPanel.add(addOrUpdateAddressTextField);
		addOrUpdateBlockPanel.add(addOrUpdateTimeLabel);
		addOrUpdateBlockPanel.add(addOrUpdateTimeDetailLabel);
		addOrUpdateBlockPanel.add(addOrUpdateButton);

		// Định nghĩa tính chất cho Medicine Supplier Manager Add Dialog
		addOrUpdateDialog = new JDialog();
		addOrUpdateDialog.setTitle(title);
		addOrUpdateDialog.setLayout(null);
		addOrUpdateDialog.setSize(500, 590);
		addOrUpdateDialog.setResizable(false);
		addOrUpdateDialog.setLocationRelativeTo(null);
		addOrUpdateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addOrUpdateDialog.addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowDeactivated(WindowEvent e) {
//				addOrUpdateDialog.dispose(); // Đóng Dialog khi mất focus (nhấn ngoài)
//			}
		});
		addOrUpdateDialog.add(addOrUpdateBlockPanel);
		addOrUpdateDialog.setModal(true);
		addOrUpdateDialog.setVisible(true);
	}
}
