package PL;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
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
import javax.swing.UIManager;

import BLL.AccountBLL;
import BLL.FunctionBLL;
import BLL.PrivilegeBLL;
import BLL.PrivilegeDetailBLL;
import DTO.AccountDTO;
import DTO.FunctionDTO;
import DTO.PrivilegeDTO;
import DTO.PrivilegeDetailDTO;
import PL.CommonPL.CustomTextField;

public class Admin_AccountManagerPL extends JPanel {
	// Các đối tượng từ tầng Bussiness Logical Layer
	private AccountBLL accountBLL;
	private FunctionBLL functionBLL;
	private PrivilegeBLL privilegeBLL;
	private PrivilegeDetailBLL privilegeDetailBLL;
	// Các Component
	private JLabel titleLabel;
	// - Các Component của Filter Panel
	private JLabel findLabel;
	private JTextField findInputTextField;
	private JButton findInformButton;
	private JLabel sortsLabel;
	private Map<String, Boolean> sortsCheckboxs;
	private JButton sortsButton;
	private JLabel privilegesLabel;
	private Map<String, Boolean> privilegesRadios;
	private JButton privilegesButton;
	private JLabel statusLabel;
	private Map<String, Boolean> statusRadios;
	private JButton statusButton;
	private JLabel numbersOfRowLabel;
	private JTextField numbersOfRowTextField;
	private JButton filterApplyButton;
	private JButton filterResetButton;
	private JPanel filterPanel;
	// - Các Component của Data Panel
	private JButton excelButton;
	private JButton addButton;
	private JButton updateButton;
	private JButton lockButton;
	private JTable tableData;
	private JScrollPane tableScrollPane;
	private JPanel dataPanel;
	// - Các Component của Add Dialog (dialog thêm một người dùng)
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
	private JLabel addOrUpdateUsernameLabel;
	private JTextField addOrUpdateUsernameTextField;
	private JLabel addOrUpdatePasswordLabel;
	private JTextField addOrUpdatePasswordTextField;
	private JLabel addOrUpdatePrivilegeLabel;
	private JComboBox<String> addOrUpdatePrivilegeComboBox;
	private JLabel addOrUpdatePrivilegeDetailLabel;
	private Map<String, Boolean> addOrUpdatePrivilegeDetailCheckboxs;
	private JPanel addOrUpdatePrivilegeDetailPanel;
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
	private final String[] columns = { "Mã người dùng", "Họ và tên", "Số điện thoại", "Email", "Địa chỉ",
			"Tên tài khoản", "Mật khẩu", "Quyền", "Chi tiết quyền", "Trạng thái" };
	// + Chiều rộng các cột
	private final int[] widthColumns = { 150, 500, 200, 300, 500, 300, 300, 150, 700, 150 };
	// + Dữ liệu
	private Object[][] datas = {};
	// + Dòng hiện tại đang được chọn
	private int rowSelected = -1;
	// + Giá trị (true / false) khi "Xoá" dòng dữ liệu
	private Boolean[] valueSelected = { null };
	// - Các thông tin cần thiết cho người dùng
	// + Sắp xếp
	private final String[] sortsString;
	private final String[] sortsSQL;
	// + Trạng thái
	private final String[] statusStringForFilter;
	private final String[] statusStringForAddOrUpdate;
	private final String[] statusSQL;
	// + Quyền
	private final String[] privilegesStringForFilter;
	private final String[] privilegesStringForAddOrUpdate;
	private final String[] privilegesSQL;

	public Admin_AccountManagerPL() {
		// <===== Các đối tượng từ tầng Bussiness Logical Layer =====>
		accountBLL = new AccountBLL();
		privilegeBLL = new PrivilegeBLL();
		functionBLL = new FunctionBLL();
		privilegeDetailBLL = new PrivilegeDetailBLL();
		// <==================== ====================>

		// <===== Cập nhật các dữ liệu cần thiết =====>
		sortsString = new String[] { "Mã người dùng tăng dần", "Mã người dùng giảm dần", "Họ và tên tăng dần",
				"Họ và tên giảm dần", "Tên tài khoản tăng dần", "Tên tài khoản giảm dần" };
		sortsSQL = new String[] { "maNguoiDung ASC", "maNguoiDung DESC", "hoVaTen ASC", "hoVaTen DESC",
				"tenTaiKhoan ASC", "tenTaiKhoan DESC" };
		privilegesStringForFilter = renderPrivilegesString("Tìm kiếm");
		privilegesStringForAddOrUpdate = renderPrivilegesString("Thêm hoặc sửa");
		privilegesSQL = new String[] { "", "maQuyen = 'NHANVIEN'", "maQuyen = 'QUANLY'" };
		statusStringForFilter = new String[] { "Tất cả", "Hoạt động", "Tạm dừng" };
		statusStringForAddOrUpdate = new String[] { "Chọn Trạng thái", "Hoạt động", "Tạm dừng" };
		statusSQL = new String[] { "", "trangThai = 1", "trangThai = 0" };
		// <==================== ====================>

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
				"Bạn có thể tìm kiếm bằng các thông tin như: Mã người dùng, Họ và tên, Số điện thoại, Email và Tên tài khoản",
				Color.BLACK, CommonPL.getFontQuestionIcon());
		findInformButton.setBounds(110, 15, 24, 24);

		// - Tuỳ chỉnh Find Input Text Field
		findInputTextField = new CommonPL.CustomTextField(0, 20, 20, "Nhập thông tin", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		findInputTextField.setBounds(15, 45, 360, 40);

		// - Tuỳ chỉnh Sorts Label
		sortsLabel = CommonPL.getParagraphLabel("Sắp xếp", Color.BLACK, CommonPL.getFontParagraphPlain());
		sortsLabel.setBounds(390, 15, 360, 24);

		// - Tuỳ chỉnh Sorts Checkboxs
		sortsCheckboxs = CommonPL.getMapHasValues(sortsString);

		// - Tuỳ chỉnh Sorts Button
		sortsButton = CommonPL.ButtonHasCheckboxs.createButtonHasCheckboxs(sortsCheckboxs, sortsString[0],
				Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		sortsButton.setBounds(390, 45, 360, 40);

		// - Tuỳ chỉnh Privileges Label
		privilegesLabel = CommonPL.getParagraphLabel("Quyền", Color.BLACK, CommonPL.getFontParagraphPlain());
		privilegesLabel.setBounds(765, 15, 360, 24);

		// - Tuỳ chỉnh Privileges Radios
		privilegesRadios = CommonPL.getMapHasValues(privilegesStringForFilter);

		// - Tuỳ chỉnh Privileges Button
		privilegesButton = CommonPL.ButtonHasRadios.createButtonHasRadios(privilegesRadios,
				privilegesStringForFilter[0], Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		privilegesButton.setBounds(765, 45, 360, 40);

		// - Tuỳ chỉnh Status Label
		statusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK, CommonPL.getFontParagraphPlain());
		statusLabel.setBounds(15, 100, 360, 24);

		// - Tuỳ chỉnh Status Radios
		statusRadios = CommonPL.getMapHasValues(statusStringForFilter);

		// - Tuỳ chỉnh Status Button
		statusButton = CommonPL.ButtonHasRadios.createButtonHasRadios(statusRadios, statusStringForFilter[0],
				Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
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
		filterPanel.add(sortsLabel);
		filterPanel.add(sortsButton);
		filterPanel.add(privilegesLabel);
		filterPanel.add(privilegesButton);
		filterPanel.add(statusLabel);
		filterPanel.add(statusButton);
//		filterPanel.add(numbersOfRowLabel);
//		filterPanel.add(numbersOfRowTextField);
		filterPanel.add(filterApplyButton);
		filterPanel.add(filterResetButton);
		// <==================== ====================>

		// <===== Cấu trúc của Data Pane =====>
		// - Tuỳ chỉnh Excel Button
		excelButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
				CommonPL.getMiddlePathToShowIcon() + "excel-icon.png", "Excel", Color.BLACK, Color.decode("#4C8CFA"),
				Color.BLACK, Color.decode("#4C8CFA"), CommonPL.getFontParagraphBold());
		excelButton.setBounds(15, 15, 210, 40);

		// - Tuỳ chỉnh Add Button
		addButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
				CommonPL.getMiddlePathToShowIcon() + "add-icon.png", "Thêm", Color.BLACK, Color.decode("#699f4c"),
				Color.BLACK, Color.decode("#699f4c"), CommonPL.getFontParagraphBold());
		addButton.setBounds(240, 15, 210, 40);

		// - Tuỳ chỉnh Update Button
		updateButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
				CommonPL.getMiddlePathToShowIcon() + "update-icon.png", "Thay đổi", Color.BLACK,
				Color.decode("#bf873e"), Color.BLACK, Color.decode("#bf873e"), CommonPL.getFontParagraphBold());
		updateButton.setBounds(465, 15, 210, 40);

		// - Tuỳ chỉnh Lock Button
		lockButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
				CommonPL.getMiddlePathToShowIcon() + "lock-icon.png", "Khoá", Color.BLACK, Color.decode("#9f4d4d"),
				Color.BLACK, Color.decode("#9f4d4d"), CommonPL.getFontParagraphBold());
		lockButton.setBounds(690, 15, 210, 40);

		// - Tuỳ chỉnh Table Data và Table Scroll Pane
		tableData = CommonPL.createTableData(columns, widthColumns, datas, "account manager");
		tableScrollPane = CommonPL.createScrollPane(true, true, tableData);
		tableScrollPane.setBounds(15, 70, 1110, 400);

		// - Tuỳ chỉnh Data Pane;
		dataPanel = new CommonPL.RoundedPanel(12);
		dataPanel.setLayout(null);
		dataPanel.setBackground(Color.WHITE);
		dataPanel.setBounds(30, 330, 1140, 485);
		dataPanel.add(excelButton);
		dataPanel.add(addButton);
		dataPanel.add(updateButton);
		dataPanel.add(lockButton);
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
		lockButton.addActionListener(e -> {
			if (rowSelected != -1) {
				Vector<Object> currentObject = new Vector<>();
				for (int i = 0; i < widthColumns.length; i++) {
					currentObject.add(tableData.getValueAt(rowSelected, i));
				}

				CommonPL.createSelectionsDialog("Thông báo lựa chọn",
						String.format("Có chắc chắn muốn %s dòng dữ liệu này ?",
								currentObject.get(currentObject.size() - 1).equals("Hoạt động") ? "khoá" : "mở khoá"),
						valueSelected);

				if (valueSelected[0]) {
					String inform = accountBLL.lockAccount(String.valueOf(currentObject.get(0)),
							CommonPL.getCurrentDate());
					if (inform.equals("Có thể khoá một người dùng")) {
						CommonPL.createSuccessDialog("Thông báo thành công", String.format("%s thành công",
								currentObject.get(currentObject.size() - 1).equals("Hoạt động") ? "Khoá" : "Mở khoá"));
						renderTableData(null, null, null);
					} else {
						CommonPL.createErrorDialog("Thông báo lỗi", inform);
					}
					renderTableData(null, null, null);
				}
			} else {
				CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần khoá");
			}
			rowSelected = -1;
			valueSelected[0] = false;
			tableData.clearSelection();
		});

		// Thiết lập sự kiện lọc dữ liệu
		filterDatasInTable();

		// Cập nhật dữ liệu ban đầu
		renderTableData(null, null, null);
	}

	// Hàm cập nhật dữ liệu quyền cho việc thêm, sửa hoặc tìm kiếm
	private String[] renderPrivilegesString(String action) {
		ArrayList<PrivilegeDTO> privilegeList = privilegeBLL.getAllPrivilege();
		String[] result = new String[privilegeList.size() + 1];
		if (action.equals("Thêm hoặc sửa")) {
			result[0] = "Chọn Quyền";
		} else {
			result[0] = "Tất cả";
		}
		for (int i = 0; i < privilegeList.size(); i++) {
			if (action.equals("Thêm hoặc sửa")) {
				result[i + 1] = String.format("%s - %s", privilegeList.get(i).getId(), privilegeList.get(i).getName());
			} else {
				result[i + 1] = String.format("%s", privilegeList.get(i).getName());
			}
		}

		return result;
	}

	// Hàm sự kiện lọc dữ liệu
	private void filterDatasInTable() {
		// Nếu nhấn vào nút "Áp dụng"
		filterApplyButton.addActionListener(e -> {
			// Giá trị ô tìm kiếm
			String findValue = !findInputTextField.getText().equals("Nhập thông tin") ? findInputTextField.getText()
					: null;
			// Giá trị ô sắp xếp
			String sortsValue = CommonPL.getSQLFromCheckboxs(sortsCheckboxs, sortsSQL);
			// Giá trị ô quyền
			String privilegesValue = CommonPL.getSQLFromRadios(privilegesRadios, privilegesSQL);
			// Giá trị ô trạng thái
			String statusValue = CommonPL.getSQLFromRadios(statusRadios, statusSQL);

			// Gán lệnh SQL tương ứng để truy vấn rồi cập nhật bảng
			String[] join = null;
			String condition = (findValue != null ? String.format(
					"(maNguoiDung = %s OR hoVaTen LIKE '%%%s%%' OR soDienThoai LIKE '%%%s%%' OR email LIKE '%%%s%%' OR tenTaiKhoan LIKE '%%%s%%')",
					CommonPL.isValidStringType04(findValue) ? Integer.parseInt(findValue) : 0, findValue,
					CommonPL.isValidStringType04(findValue) ? Integer.parseInt(findValue) : 0, findValue, findValue)
					: "")
					+ (privilegesValue != null ? (findValue != null ? " AND " + privilegesValue : privilegesValue) : "")
					+ (statusValue != null
							? (findValue != null || privilegesValue != null ? " AND " + statusValue : statusValue)
							: "");
			if (condition.length() == 0)
				condition = null;
			String order = sortsValue;
			renderTableData(join, condition, order);
		});

		// Nếu nhấn vào nút "Đặt lại"
		filterResetButton.addActionListener(e -> {
			// Cập nhật lại ô tìm kiếm
			findInputTextField.setText("Nhập thông tin");
			findInputTextField.setForeground(Color.LIGHT_GRAY);

			// Cập nhật lại ô sắp xếp
			CommonPL.resetMapForFilter(sortsCheckboxs, sortsString, sortsButton);

			// Cập nhật lại ô quyền
			CommonPL.resetMapForFilter(privilegesRadios, privilegesStringForFilter, privilegesButton);

			// Cập nhật lại ô trạng thái
			CommonPL.resetMapForFilter(statusRadios, statusStringForFilter, statusButton);

			// Cập nhật lại bảng
			renderTableData(null, null, null);
		});
	}

	// Hàm cập nhật dữ liệu cho bảng từ cơ sở dữ liệu
	private void renderTableData(String[] join, String condition, String order) {
		ArrayList<AccountDTO> accountList = accountBLL.getAllAccountByCondition(join, condition, order);
		Object[][] datasQuery = new Object[accountList.size()][columns.length];
		for (int i = 0; i < accountList.size(); i++) {
			String privilegeName = (privilegeBLL.getOnePrivilegeById(accountList.get(i).getPrivilegeId())).getName();

			String[] joinPrivilegeDetail = null;
			String conditionPrivilegeDetail = String.format("ChiTietQuyen.maNguoiDung = '%s'",
					accountList.get(i).getId());
			String orderPrivilegeDetail = null;
			ArrayList<PrivilegeDetailDTO> privilegeDetailList = privilegeDetailBLL.getAllPrivilegeDetailByCondition(
					joinPrivilegeDetail, conditionPrivilegeDetail, orderPrivilegeDetail);

			String functionsStr = "";
			for (PrivilegeDetailDTO privilegeDetailDTO : privilegeDetailList) {
				if (privilegeDetailDTO.getStatus()) {
					String[] joinFunction = null;
					String conditionFunction = String.format("maChucNang = '%s'", privilegeDetailDTO.getFunctionId());
					String orderFunction = null;
					ArrayList<FunctionDTO> functionList = functionBLL.getAllFunctionByCondition(joinFunction,
							conditionFunction, orderFunction);
					for (FunctionDTO functionDTO : functionList) {
						functionsStr += "," + functionDTO.getName();
					}
				}
			}
			if (functionsStr.length() != 0)
				functionsStr = functionsStr.substring(1);

			datasQuery[i][0] = (Object) accountList.get(i).getId();
			datasQuery[i][1] = (Object) accountList.get(i).getFullname();
			datasQuery[i][2] = (Object) accountList.get(i).getPhone();
			datasQuery[i][3] = (Object) accountList.get(i).getEmail();
			datasQuery[i][4] = (Object) accountList.get(i).getAddress();
			datasQuery[i][5] = (Object) accountList.get(i).getUsername();
			datasQuery[i][6] = (Object) accountList.get(i).getPassword();
			datasQuery[i][7] = (Object) privilegeName;
			datasQuery[i][8] = (Object) functionsStr;
			datasQuery[i][9] = (Object) (accountList.get(i).getStatus() ? "Hoạt động" : "Tạm dừng");
		}
		datas = datasQuery;

		CommonPL.updateRowsInTableData(tableData, datas);
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

	// Hàm đặt lại "Chi tiết Quyền" với "Quyền" được chọn tương ứng
	private void renderAddPrivilegeDetailPanel() {
		// - Lấy ra tất cả các JButton ở Main Menu
		ArrayList<JButton> buttonsInMainMenu = CommonPL.getAllButtons((new AdminMenuPL()).getMainMenuPanel());
		ArrayList<JButton> buttons = new ArrayList<>();
		buttons.addAll(buttonsInMainMenu);

		// - Mặc dinh là mục "Chọn Quyền" hiển thị đầu tiên
		for (JButton button : buttons) {
			String itemValue = ((JLabel) button.getComponent(1)).getText();
			if (!itemValue.equals("Đăng xuất")) {
				addOrUpdatePrivilegeDetailCheckboxs.put(itemValue, false);
			}
		}
		int x = 10, y = 10;
		for (String key : addOrUpdatePrivilegeDetailCheckboxs.keySet()) {
			JCheckBox itemCheckBox = new JCheckBox();
			if (addOrUpdatePrivilegeDetailCheckboxs.get(key) == true) {
				itemCheckBox.setSelected(true);
			}
			itemCheckBox.setText(key);
			itemCheckBox.setFont(new Font("Arial", Font.PLAIN, 18));
			itemCheckBox.setBounds(x, y, 215, 24);
			itemCheckBox.setEnabled(false);
			addOrUpdatePrivilegeDetailPanel.add(itemCheckBox);
			if (x == 10) {
				x = 235;
			} else {
				x = 10;
				y += 34;
			}
		}

		// - Mỗi lần thay đổi "Quyền" thì phải có "Chi tiết Quyền" tương ứng
		addOrUpdatePrivilegeComboBox.addActionListener(e1 -> {
			String privilegeValueSelected = (String) addOrUpdatePrivilegeComboBox.getSelectedItem();
			if (privilegeValueSelected.equals("QUANLY - Quản lý")) {
				addOrUpdatePrivilegeDetailPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				addOrUpdatePrivilegeDetailLabel.setText("Chi tiết Quyền");
				for (JButton button : buttons) {
					String itemValue = ((JLabel) button.getComponent(1)).getText();
					if (!itemValue.equals("Đăng xuất")) {
						addOrUpdatePrivilegeDetailCheckboxs.put(itemValue, true);
					}
				}
			} else if (privilegeValueSelected.equals("NHANVIEN - Nhân viên")) {
				addOrUpdatePrivilegeDetailPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				addOrUpdatePrivilegeDetailLabel.setText("Chi tiết Quyền");
				for (JButton button : buttons) {
					String itemValue = ((JLabel) button.getComponent(1)).getText();
					if (!itemValue.equals("Đăng xuất")) {
						if (itemValue.equals("Thống kê")) {
							addOrUpdatePrivilegeDetailCheckboxs.put(itemValue, true);
						} else {
							addOrUpdatePrivilegeDetailCheckboxs.put(itemValue, false);
						}
					}
				}
			} else if (privilegeValueSelected.equals("Chọn Quyền")) {
				addOrUpdatePrivilegeDetailPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
				addOrUpdatePrivilegeDetailLabel.setText("<html><strike>Chi tiết Quyền</strike></html>");
				for (JButton button : buttons) {
					String itemValue = ((JLabel) button.getComponent(1)).getText();
					if (!itemValue.equals("Đăng xuất")) {
						addOrUpdatePrivilegeDetailCheckboxs.put(itemValue, false);
					}
				}
			}

			// - Cập nhật các checkbox mới
			addOrUpdatePrivilegeDetailPanel.removeAll();
			int subX = 10, subY = 10;
			for (String key : addOrUpdatePrivilegeDetailCheckboxs.keySet()) {
				JCheckBox itemCheckBox = new JCheckBox();
				if (addOrUpdatePrivilegeDetailCheckboxs.get(key) == true) {
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
				if (privilegeValueSelected.equals("Chọn Quyền")) {
					itemCheckBox.setEnabled(false);
					itemCheckBox.setForeground(Color.LIGHT_GRAY);
				} else {
					itemCheckBox.addActionListener(e2 -> {
						if (addOrUpdatePrivilegeDetailCheckboxs.get(key) == true) {
							addOrUpdatePrivilegeDetailCheckboxs.replace(key, false);
						} else {
							addOrUpdatePrivilegeDetailCheckboxs.replace(key, true);
						}
					});
				}

				addOrUpdatePrivilegeDetailPanel.add(itemCheckBox);
			}
			addOrUpdatePrivilegeDetailPanel.revalidate();
			addOrUpdatePrivilegeDetailPanel.repaint();
		});
	}

	// Hàm hiển thị Dialog thêm hoặc cập nhật một tài khoản
	private void showAddOrUpdateDialog(String title, String button, Vector<Object> object) {
		// <===== Cấu trúc của Add Block Panel =====>
		// - Tuỳ chỉnh Add Or Update Id Label
		addOrUpdateIdLabel = CommonPL.getParagraphLabel(
				"<html>Mã người dùng&nbsp;<span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateIdLabel.setBounds(20, 10, 460, 40);

		// - Tuỳ chỉnh Add Or Update Id Text Field
		addOrUpdateIdTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mã người dùng", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateIdTextField.setEnabled(false);
		((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
		addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));
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

		// - Tuỳ chỉnh Add Or Update Address Button
		addOrUpdateAddressButton = CommonPL.getButtonDefaultForm("Nhấn để chọn địa chỉ",
				new Font("Arial", Font.BOLD, 14));
		addOrUpdateAddressButton.setBounds(780, 106, 180, 28);
		addOrUpdateAddressButton.addActionListener(e -> {
//									addOrUpdateDialog.setVisible(true);
			addOrUpdateAddressButton.setBackground(Color.BLACK);
			showAddressDetailDialog();

//									addOrUpdateDialog.setVisible(false);
//									SwingUtilities.invokeLater(() -> {
//										addOrUpdateAddressButton.setBackground(Color.BLACK);
//										showAddressDetailDialog();
//										addOrUpdateDialog.setVisible(true);
//									});
		});

		// - Tuỳ chỉnh Add Or Update Address Text Field
		addOrUpdateAddressTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Địa chỉ", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateAddressTextField.setBounds(500, 140, 460, 40);

		// - Tuỳ chỉnh Add Or Update Username Label
		addOrUpdateUsernameLabel = CommonPL.getParagraphLabel(
				"<html>Tên tài khoản&nbsp;<span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateUsernameLabel.setBounds(20, 190, 460, 40);

		// - Tuỳ chỉnh Add Or Update Username Text Field
		addOrUpdateUsernameTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Tên tài khoản", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateUsernameTextField.setBounds(20, 230, 460, 40);

		// - Tuỳ chỉnh Add Or Update Password Label
		addOrUpdatePasswordLabel = CommonPL.getParagraphLabel(
				"<html>Mật khẩu&nbsp;<span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdatePasswordLabel.setBounds(20, 280, 460, 40);

		// - Tuỳ chỉnh Add Or Update Password Text Field
		addOrUpdatePasswordTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mật khẩu", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdatePasswordTextField.setBounds(20, 320, 460, 40);

		// - Tuỳ chỉnh Add Or Update Status Label
		addOrUpdateStatusLabel = CommonPL.getParagraphLabel(
				"<html>Trạng thái&nbsp;<span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateStatusLabel.setBounds(20, 370, 460, 40);

		// - Tuỳ chỉnh Add Or Update Status ComboBox
		Vector<String> statusVector = CommonPL.getVectorHasValues(statusStringForAddOrUpdate);
		addOrUpdateStatusComboBox = CommonPL.CustomComboBox(statusVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateStatusComboBox.setBounds(20, 410, 460, 40);

		// - Tuỳ chỉnh Add Or Update Privilege Label
		addOrUpdatePrivilegeLabel = CommonPL.getParagraphLabel(
				"<html>Quyền&nbsp;<span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdatePrivilegeLabel.setBounds(500, 190, 460, 40);

		// - Tuỳ chỉnh Add Or Update Privilege ComboBox
		Vector<String> privilegesVector = CommonPL.getVectorHasValues(privilegesStringForAddOrUpdate);
		addOrUpdatePrivilegeComboBox = CommonPL.CustomComboBox(privilegesVector, Color.WHITE, Color.LIGHT_GRAY,
				Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdatePrivilegeComboBox.setBounds(500, 230, 460, 40);

		// - Tuỳ chỉnh Add Or Update Privilege Detail Label
		addOrUpdatePrivilegeDetailLabel = CommonPL.getParagraphLabel("<html><strike>Chi tiết Quyền</strike></html>",
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdatePrivilegeDetailLabel.setBounds(500, 280, 460, 40);

		// - Tuỳ chỉnh Add Or Update Privilege Detail Checkbox;
		addOrUpdatePrivilegeDetailCheckboxs = new LinkedHashMap<>();

		// - Tuỳ chỉnh Add Or Update Privilege Detail Panel
		addOrUpdatePrivilegeDetailPanel = new JPanel();
		addOrUpdatePrivilegeDetailPanel.setLayout(null);
		addOrUpdatePrivilegeDetailPanel.setBounds(500, 320, 460, 220);
		addOrUpdatePrivilegeDetailPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		addOrUpdatePrivilegeDetailPanel.setBackground(Color.WHITE);
		renderAddPrivilegeDetailPanel();

		// - Khi "Thêm" một Người dùng
		if (title.equals("Thêm Người dùng") && button.equals("Thêm") && object.size() == 0) {
			// - Mặc định mã người dùng sẽ tự động tăng dần khi "thêm"
			String id = String
					.valueOf((accountBLL.getAllAccountByCondition(null, null, "maNguoiDung DESC").get(0)).getId() + 1);
			addOrUpdateIdTextField.setText(id);
			((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
		}

		// - Khi "Thay đổi" một Người dùng
		if (title.equals("Thay đổi Người dùng") && button.equals("Thay đổi") && object.size() != 0) {
			// - Gán dữ liệu là "Mã người dùng"
			addOrUpdateIdTextField.setText(String.valueOf(object.get(0)));
			((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
			addOrUpdateIdTextField.setCaretPosition(0);

			// - Gán dữ liệu là "Họ và tên"
			if (object.get(1) != null) {
				addOrUpdateFullnameTextField.setText(String.valueOf(object.get(1)));
				addOrUpdateFullnameTextField.setCaretPosition(0);
				addOrUpdateFullnameTextField.setForeground(Color.BLACK);
			}

			// - Gán dữ liệu là "Số điện thoại"
			if (object.get(2) != null) {
				addOrUpdatePhoneTextField.setText(String.valueOf(object.get(2)));
				addOrUpdatePhoneTextField.setCaretPosition(0);
				addOrUpdatePhoneTextField.setForeground(Color.BLACK);
			}

			// - Gán dữ liệu là "Email"
			if (object.get(3) != null) {
				addOrUpdateEmailTextField.setText(String.valueOf(object.get(3)));
				addOrUpdateEmailTextField.setCaretPosition(0);
				addOrUpdateEmailTextField.setForeground(Color.BLACK);
			}

			// - Gán dữ liệu là "Địa chỉ"
			if (object.get(4) != null) {
				addOrUpdateAddressTextField.setText(String.valueOf(object.get(4)));
				addOrUpdateAddressTextField.setCaretPosition(0);
				addOrUpdateAddressTextField.setForeground(Color.BLACK);
			}

			// - Gán dữ liệu là "Tên tài khoản"
			addOrUpdateUsernameTextField.setText(String.valueOf(object.get(5)));
			addOrUpdateUsernameTextField.setEnabled(false);
			addOrUpdateUsernameTextField.setCaretPosition(0);
			((CustomTextField) addOrUpdateUsernameTextField).setBorderColor(Color.decode("#dedede"));
			addOrUpdateUsernameTextField.setBackground(Color.decode("#dedede"));

			// - Gán dữ liệu là "Mật khẩu"
			if (object.get(6) != null) {
				addOrUpdatePasswordTextField.setText(String.valueOf(object.get(6)));
				addOrUpdatePasswordTextField.setCaretPosition(0);
				addOrUpdatePasswordTextField.setForeground(Color.BLACK);
			}

			// - Gán dữ liệu là "Quyền"
			for (int i = 0; i < addOrUpdatePrivilegeComboBox.getItemCount(); i++) {
				String item = addOrUpdatePrivilegeComboBox.getItemAt(i);
				if (item.contains(String.valueOf(object.get(7)))) {
					addOrUpdatePrivilegeComboBox.setSelectedItem(item);
					break;
				}
			}
			((JTextField) addOrUpdatePrivilegeComboBox.getEditor().getEditorComponent()).setCaretPosition(0);
			addOrUpdatePrivilegeComboBox.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Chi tiết quyền"
			String[] privilegesDetail = (String.valueOf(object.get(8))).split("\\,");
			// + Lấy ra tất cả các JButton ở Main Menu
			ArrayList<JButton> buttonsInMainMenu = CommonPL.getAllButtons((new AdminMenuPL()).getMainMenuPanel());
			ArrayList<JButton> buttons = new ArrayList<>();
			buttons.addAll(buttonsInMainMenu);
			// + Mặc dinh là mục "Chọn Quyền" hiển thị đầu tiên
			for (JButton buttonn : buttons) {
				String itemValue = ((JLabel) buttonn.getComponent(1)).getText();
				if (!itemValue.equals("Đăng xuất")) {
					addOrUpdatePrivilegeDetailCheckboxs.put(itemValue, false);
				}
			}
			for (String key : addOrUpdatePrivilegeDetailCheckboxs.keySet()) {
				for (String privilege : privilegesDetail) {
					if (key.equals(privilege)) {
						addOrUpdatePrivilegeDetailCheckboxs.put(key, true);
					}
				}
			}
			// - Cập nhật các checkbox mới
			addOrUpdatePrivilegeDetailPanel.removeAll();
			int x = 10, y = 10;
			for (String key : addOrUpdatePrivilegeDetailCheckboxs.keySet()) {
				JCheckBox itemCheckBox = new JCheckBox();
				if (addOrUpdatePrivilegeDetailCheckboxs.get(key) == true) {
					itemCheckBox.setSelected(true);
				}
				itemCheckBox.setText(key);
				itemCheckBox.setFont(new Font("Arial", Font.PLAIN, 18));
				itemCheckBox.setForeground(Color.BLACK);
				itemCheckBox.setBounds(x, y, 215, 24);
				itemCheckBox.addActionListener(e2 -> {
					if (addOrUpdatePrivilegeDetailCheckboxs.get(key) == true) {
						addOrUpdatePrivilegeDetailCheckboxs.replace(key, false);
					} else {
						addOrUpdatePrivilegeDetailCheckboxs.replace(key, true);
					}
				});

				addOrUpdatePrivilegeDetailPanel.add(itemCheckBox);

				if (x == 10) {
					x = 235;
				} else {
					x = 10;
					y += 34;
				}

			}
			addOrUpdatePrivilegeDetailPanel.revalidate();
			addOrUpdatePrivilegeDetailPanel.repaint();

			// - Gán dữ liệu là "Trạng thái"
			addOrUpdateStatusComboBox.setSelectedItem((String) object.get(9));
			addOrUpdateStatusComboBox.setEnabled(false);
			((JTextField) addOrUpdateStatusComboBox.getEditor().getEditorComponent()).setCaretPosition(0);
			UIManager.put("ComboBox.disabledBackground", Color.decode("#dedede"));
			addOrUpdateStatusComboBox.setBorder(BorderFactory.createLineBorder(Color.decode("#dedede"), 1));

			// - Gán dữ liệu là "Thời gian cập nhật gần đây"
//							addOrUpdateTimeDetailLabel.setText((String) object.get(11));
		}

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
		addOrUpdateButton.addActionListener(e -> {
			// - Lấy ra các giá trị hiện tại từ các thẻ JTextField
			// + Mã người dùng
			String id = !addOrUpdateIdTextField.getText().equals("Nhập Mã người dùng")
					? addOrUpdateIdTextField.getText()
					: null;
			// + Tên người dùng
			String fullname = !addOrUpdateFullnameTextField.getText().equals("Nhập Họ và tên")
					? addOrUpdateFullnameTextField.getText()
					: null;
			// + Số điện thoại
			String phone = !addOrUpdatePhoneTextField.getText().equals("Nhập Số điện thoại")
					? addOrUpdatePhoneTextField.getText()
					: null;
			// + Email
			String email = !addOrUpdateEmailTextField.getText().equals("Nhập Email")
					? addOrUpdateEmailTextField.getText()
					: null;
			// + Địa chỉ
			String address = !addOrUpdateAddressTextField.getText().equals("Nhập Địa chỉ")
					? addOrUpdateAddressTextField.getText()
					: null;
			// + Tên tài khoản
			String username = !addOrUpdateUsernameTextField.getText().equals("Nhập Tên tài khoản")
					? addOrUpdateUsernameTextField.getText()
					: null;
			// + Mật khẩu
			String password = !addOrUpdatePasswordTextField.getText().equals("Nhập Mật khẩu")
					? addOrUpdatePasswordTextField.getText()
					: null;
			// + Quyền
			String privilegeId = !String.valueOf(addOrUpdatePrivilegeComboBox.getSelectedItem()).equals("Chọn Quyền")
					? String.valueOf(addOrUpdatePrivilegeComboBox.getSelectedItem()).split(" - ")[0]
					: null;
			// + Trạng thái
			String status = !String.valueOf(addOrUpdateStatusComboBox.getSelectedItem()).equals("Chọn Trạng thái")
					? String.valueOf(addOrUpdateStatusComboBox.getSelectedItem())
					: null;
			// + Ngày cập nhật
			String dateUpdate = CommonPL.getCurrentDate();

//			System.out.println(id);
//			System.out.println(fullname);
//			System.out.println(phone);
//			System.out.println(email);
//			System.out.println(address);
//			System.out.println(username);
//			System.out.println(password);
//			System.out.println(privilegeId);
//			System.out.println(status);
//			System.out.println(dateUpdate);

			// - Nếu là "Thêm"
			if (title.equals("Thêm Người dùng") && button.equals("Thêm")) {
				String inform = accountBLL.insertAccount(id, fullname, phone, email, address, username, password,
						privilegeId, status, dateUpdate);
				if (inform.equals("Có thể thêm một người dùng")) {
					// - Phải thêm vào bảng Người dùng thì mới thêm dữ liệu vào bảng Chi tiết quyền
					privilegeDetailBLL.insertPrivilegeDetail(id, privilegeId, addOrUpdatePrivilegeDetailCheckboxs);

					// - Hoàn thành việc thêm thì thông báo và cập nhật lại trên giao diện
					CommonPL.createSuccessDialog("Thông báo thành công", "Thêm thành công");
					addOrUpdateDialog.dispose();
					renderTableData(null, null, null);
				} else {
					CommonPL.createErrorDialog("Thông báo lỗi", inform);
				}
			}
			// - Nếu là "Sửa"
			else if (title.equals("Thay đổi Người dùng") && button.equals("Thay đổi")) {
				String inform = accountBLL.updateAccount(id, fullname, phone, email, address, username, password,
						privilegeId, status, dateUpdate);
				if (inform.equals("Có thể thay đổi một người dùng")) {
					// - Phải thay đổi vào bảng Người dùng thì mới thay đổi dữ liệu vào bảng Chi
					// tiết quyền
					privilegeDetailBLL.updatePrivilegeDetail(id, privilegeId, addOrUpdatePrivilegeDetailCheckboxs);

					// - Hoàn thành việc thêm thì thông báo và cập nhật lại trên giao diện
					CommonPL.createSuccessDialog("Thông báo thành công", "Thay đổi thành công");
					addOrUpdateDialog.dispose();
					renderTableData(null, null, null);
				} else {
					CommonPL.createErrorDialog("Thông báo lỗi", inform);
				}
			}
		});

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
		addOrUpdateBlockPanel.add(addOrUpdateAddressButton);
		addOrUpdateBlockPanel.add(addOrUpdateAddressTextField);
		addOrUpdateBlockPanel.add(addOrUpdateUsernameLabel);
		addOrUpdateBlockPanel.add(addOrUpdateUsernameTextField);
		addOrUpdateBlockPanel.add(addOrUpdatePasswordLabel);
		addOrUpdateBlockPanel.add(addOrUpdatePasswordTextField);
		addOrUpdateBlockPanel.add(addOrUpdatePrivilegeLabel);
		addOrUpdateBlockPanel.add(addOrUpdatePrivilegeComboBox);
		addOrUpdateBlockPanel.add(addOrUpdatePrivilegeDetailLabel);
		addOrUpdateBlockPanel.add(addOrUpdatePrivilegeDetailPanel);
		addOrUpdateBlockPanel.add(addOrUpdateStatusLabel);
		addOrUpdateBlockPanel.add(addOrUpdateStatusComboBox);
//		addOrUpdateBlockPanel.add(addOrUpdateTimeLabel);
//		addOrUpdateBlockPanel.add(addOrUpdateTimeDetailLabel);
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
//			@Override
//			public void windowDeactivated(WindowEvent e) {
//				// Đóng Dialog khi mất focus (nhấn ngoài)
//				addOrUpdateDialog.dispose();
//			}
		});
		addOrUpdateDialog.add(addOrUpdateBlockPanel);
		addOrUpdateDialog.setModal(true);
		addOrUpdateDialog.setVisible(true);
	}
}
