package PL;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
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
import BLL.CommonBLL;
import BLL.FunctionBLL;
import BLL.PrivilegeBLL;
import BLL.PrivilegeDetailBLL;
import DTO.AccountDTO;
import DTO.FunctionDTO;
import DTO.PrivilegeDTO;
import DTO.PrivilegeDetailDTO;
import PL.CommonPL.CustomPasswordField;
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
	private Map<String, Boolean> sortsRadios;
	private JButton sortsButton;
	private JLabel privilegesLabel;
	private Map<String, Boolean> privilegesRadios;
	private JButton privilegesButton;
	private JLabel statusLabel;
	private Map<String, Boolean> statusRadios;
	private JButton statusButton;
	private JButton filterApplyButton;
	private JButton filterResetButton;
	private JPanel filterPanel;
	// - Các Component của Data Panel
	private JButton addButton;
	private JButton updateButton;
	private JButton lockButton;
	private JButton changePasswordButton;
	private JTable tableData;
	private JScrollPane tableScrollPane;
	private JPanel dataPanel;
	// - Các Component của Change Password
	private JLabel changePasswordInput01Label;
	private JTextField changePasswordInput01TextField;
	private JLabel changePasswordInput02Label;
	private JTextField changePasswordInput02TextField;
	private JButton changePasswordConfirm;
	private JPanel changePasswordBlockPanel;
	private JDialog changePasswordDialog;
	// - Các Component của Add Or Update Accont Dialog (dialog thêm một người dùng)
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

	// Các giá trị mặc định cho text field, text area hoặc combobox để tìm kiếm,
	// thêm, sửa và xoá
	private final Map<String, String> defaultValuesForCrud = new LinkedHashMap<String, String>() {
		{
			put("id", "Nhập Mã người dùng");
			put("fullname", "Nhập Họ và tên");
			put("phone", "Nhập Số điện thoại");
			put("email", "Nhập Email");
			put("address", "Nhập Địa chỉ");
			put("username", "Nhập Tên tài khoản");
			put("password", "Nhập Mật khẩu");
			put("privilege", "Chọn Quyền");
			put("status", "Chọn Trạng thái");
		}
	};

	// - Các thông tin cần thiết cho người dùng
	// + Sắp xếp
	private final String[] sortsString = new String[] { "Mã người dùng tăng dần", "Mã người dùng giảm dần",
			"Họ và tên tăng dần",
			"Họ và tên giảm dần", "Tên tài khoản tăng dần", "Tên tài khoản giảm dần" };
	private final String[] sortsSQL = new String[] { "maNguoiDung ASC", "maNguoiDung DESC", "hoVaTen ASC",
			"hoVaTen DESC",
			"tenTaiKhoan ASC", "tenTaiKhoan DESC" };
	// + Trạng thái
	private final String[] statusStringForFilter = new String[] { "Tất cả", "Hoạt động", "Tạm dừng" };
	private final String[] statusStringForAddOrUpdate = new String[] { defaultValuesForCrud.get("status"), "Hoạt động",
			"Tạm dừng" };
	private final String[] statusSQL = new String[] { "", "trangThai = 1", "trangThai = 0" };
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
		privilegesStringForFilter = renderPrivilegesString("Tìm kiếm");
		privilegesStringForAddOrUpdate = renderPrivilegesString("Thêm hoặc sửa");
		privilegesSQL = renderPrivilegesString("Truy vấn SQL");
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

		// - Tuỳ chỉnh Sorts Radios
		sortsRadios = CommonPL.getMapHasValues(sortsString);

		// - Tuỳ chỉnh Sorts Button
		sortsButton = CommonPL.ButtonHasRadios.createButtonHasRadios(sortsRadios, sortsString[0],
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

		// - Tuỳ chỉnh Lock Button
		lockButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
				CommonPL.getMiddlePathToShowIcon() + "lock-icon.png", "Khoá", Color.BLACK, Color.decode("#9f4d4d"),
				Color.BLACK, Color.decode("#9f4d4d"), CommonPL.getFontParagraphBold());
		lockButton.setBounds(465, 15, 210, 40);

		// - Tuỳ chỉnh Change Password Button
		changePasswordButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
				CommonPL.getMiddlePathToShowIcon() + "change-password-icon.png", "Đổi mật khẩu", Color.BLACK,
				Color.decode("#4a9e7d"),
				Color.BLACK, Color.decode("#4a9e7d"), CommonPL.getFontParagraphBold());
		changePasswordButton.setBounds(690, 15, 210, 40);

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
		dataPanel.add(lockButton);
		dataPanel.add(changePasswordButton);
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
							CommonPL.getCurrentDatetime());
					if (inform.equals("Có thể khoá một người dùng")) {
						CommonPL.createSuccessDialog("Thông báo thành công", String.format("%s thành công",
								currentObject.get(currentObject.size() - 1).equals("Hoạt động") ? "Khoá" : "Mở khoá"));
						resetPage();
					} else {
						CommonPL.createErrorDialog("Thông báo lỗi", inform);
					}
				}
			} else {
				CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần khoá");
			}
			rowSelected = -1;
			valueSelected[0] = false;
			tableData.clearSelection();
		});
		changePasswordButton.addActionListener(e -> {
			if (rowSelected != -1) {
				showChangePasswordDialog(String.valueOf(tableData.getValueAt(rowSelected, 0)));
			} else {
				CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần thay đổi mật khẩu");
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
		if (action.equals("Tìm kiếm")) {
			result[0] = "Tất cả";
		} else if (action.equals("Thêm hoặc sửa")) {
			result[0] = defaultValuesForCrud.get("privilege");
		} else if (action.equals("Truy vấn SQL")) {
			result[0] = "";
		}
		for (int i = 0; i < privilegeList.size(); i++) {
			if (action.equals("Tìm kiếm")) {
				result[i + 1] = String.format("%s", privilegeList.get(i).getName());
			} else if (action.equals("Thêm hoặc sửa")) {
				result[i + 1] = String.format("%s - %s", privilegeList.get(i).getId(), privilegeList.get(i).getName());
			} else if (action.equals("Truy vấn SQL")) {
				result[i + 1] = String.format("maQuyen = '%s'", privilegeList.get(i).getId());
			}
		}

		return result;
	}

	// Hàm reset trang
	private void resetPage() {
		// Cập nhật lại ô tìm kiếm
		findInputTextField.setText("Nhập thông tin");
		findInputTextField.setForeground(Color.LIGHT_GRAY);

		// Cập nhật lại ô sắp xếp
		CommonPL.resetMapForFilter(sortsRadios, sortsString, sortsButton);

		// Cập nhật lại ô quyền
		CommonPL.resetMapForFilter(privilegesRadios, privilegesStringForFilter, privilegesButton);

		// Cập nhật lại ô trạng thái
		CommonPL.resetMapForFilter(statusRadios, statusStringForFilter, statusButton);

		// Cập nhật lại bảng
		renderTableData(null, null, null);
	}

	// Hàm sự kiện lọc dữ liệu
	private void filterDatasInTable() {
		// Nếu nhấn vào nút "Áp dụng"
		filterApplyButton.addActionListener(e -> {
			// Giá trị ô tìm kiếm
			String findValue = !findInputTextField.getText().equals("Nhập thông tin") ? findInputTextField.getText()
					: null;
			// Giá trị ô sắp xếp
			String sortsValue = CommonPL.getSQLFromCheckboxs(sortsRadios, sortsSQL);
			// Giá trị ô quyền
			String privilegesValue = CommonPL.getSQLFromRadios(privilegesRadios, privilegesSQL);
			// Giá trị ô trạng thái
			String statusValue = CommonPL.getSQLFromRadios(statusRadios, statusSQL);

			// Gán lệnh SQL tương ứng để truy vấn rồi cập nhật bảng
			String[] join = null;
			String condition = (findValue != null ? String.format(
					"(maNguoiDung = %s OR hoVaTen LIKE '%%%s%%' OR soDienThoai LIKE '%%%s%%' OR email LIKE '%%%s%%' OR tenTaiKhoan LIKE '%%%s%%')",
					CommonBLL.isValidStringType04(findValue) ? Integer.parseInt(findValue) : 0, findValue,
					CommonBLL.isValidStringType04(findValue) ? Integer.parseInt(findValue) : 0, findValue, findValue)
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
			// Gọi hàm reset trang
			resetPage();
		});
	}

	// Hàm cập nhật dữ liệu cho bảng từ cơ sở dữ liệu
	private void renderTableData(String[] join, String condition, String order) {
		ArrayList<AccountDTO> accountList = accountBLL.getAllAccountByCondition(join, condition, order);
		Object[][] datasQuery = new Object[accountList.size()][columns.length];
		for (int i = 0; i < accountList.size(); i++) {
			String privilegeName = (privilegeBLL.getOnePrivilegeById(accountList.get(i).getPrivilegeId())).getName();

			ArrayList<PrivilegeDetailDTO> privilegeDetailList = privilegeDetailBLL
					.getAllPrivilegeDetailByAccountId(String.valueOf(accountList.get(i).getId()));
			String functionsStr = "";
			for (PrivilegeDetailDTO privilegeDetailDTO : privilegeDetailList) {
				functionsStr += ", " + functionBLL.getOneFunctionById(privilegeDetailDTO.getFunctionId()).getName();
			}
			if (functionsStr.length() != 0)
				functionsStr = functionsStr.substring(2);

			datasQuery[i][0] = (Object) accountList.get(i).getId();
			datasQuery[i][1] = (Object) accountList.get(i).getFullname();
			datasQuery[i][2] = (Object) accountList.get(i).getPhone();
			datasQuery[i][3] = (Object) accountList.get(i).getEmail();
			datasQuery[i][4] = (Object) accountList.get(i).getAddress();
			datasQuery[i][5] = (Object) accountList.get(i).getUsername();
			datasQuery[i][6] = "Mật khẩu đã được mã hoá";
			datasQuery[i][7] = (Object) privilegeName;
			datasQuery[i][8] = (Object) functionsStr;
			datasQuery[i][9] = (Object) (accountList.get(i).getStatus() ? "Hoạt động" : "Tạm dừng");
		}
		datas = datasQuery;

		CommonPL.updateRowsInTableData(tableData, datas);
	}

	// Hàm tạo một Dialog để thay đổi mật khẩu người dùng
	private void showChangePasswordDialog(String accountId) {
		// - Tuỳ chỉnh Change Password Input 01 Label
		changePasswordInput01Label = CommonPL.getParagraphLabel("Mật khẩu mới lần 1", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		changePasswordInput01Label.setBounds(20, 10, 460, 40);

		// - Tuỳ chỉnh Change Password Input 01 Text Field
		changePasswordInput01TextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mật khẩu mới lần 1",
				Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		changePasswordInput01TextField.setBounds(20, 50, 460, 40);

		// - Tuỳ chỉnh Change Password Input 02 Label
		changePasswordInput02Label = CommonPL.getParagraphLabel("Mật khẩu mới lần 2", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		changePasswordInput02Label.setBounds(20, 100, 460, 40);

		// - Tuỳ chỉnh Change Password Input 02 Text Field
		changePasswordInput02TextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mật khẩu mới lần 2",
				Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		changePasswordInput02TextField.setBounds(20, 140, 460, 40);

		// - Tuỳ chỉnh Change Password Button
		changePasswordConfirm = CommonPL.getRoundedBorderButton(20, "Thay đổi", Color.decode("#4a9e7d"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		changePasswordConfirm.setBounds(20, 230, 460, 40);
		SwingUtilities.invokeLater(() -> changePasswordConfirm.requestFocusInWindow());
		changePasswordConfirm.addActionListener(e -> {
			CommonPL.createSelectionsDialog("Thông báo lựa chọn",
					"Có chắc chắn thay đổi mật khẩu người dùng này?",
					valueSelected);
			if (valueSelected[0]) {
				// - Lấy ra các giá trị hiện tại từ các thẻ JTextField
				// + Mã người dùng
				String id = accountId != null ? accountId : null;
				// + Mật khẩu mới lần 1
				String input01 = !changePasswordInput01TextField.getText()
						.equals("Nhập Mật khẩu mới lần 1")
								? changePasswordInput01TextField.getText()
								: null;
				// + Mật khẩu mới lần 2
				// String phone =
				String input02 = !changePasswordInput02TextField.getText()
						.equals("Nhập Mật khẩu mới lần 2")
								? changePasswordInput02TextField.getText()
								: null;
				// + Ngày cập nhật
				String timeUpdate = CommonPL.getCurrentDatetime();

				// - Biến chứa thông báo trả về
				String inform = accountBLL.changePasswordAccount(accountId, input01, input02, timeUpdate);
				if (inform.equals("Có thể thay đổi mật khẩu một người dùng")) {
					CommonPL.createSuccessDialog("Thông báo thành công", inform);
					changePasswordDialog.dispose();
					resetPage();
				} else {
					CommonPL.createErrorDialog("Thông báo lỗi", inform);
				}
			}
			valueSelected[0] = false;
		});

		// - Tuỳ chỉnh Change Password Block Panel
		changePasswordBlockPanel = new JPanel();
		changePasswordBlockPanel.setLayout(null);
		changePasswordBlockPanel.setBounds(0, 0, 500, 320);
		changePasswordBlockPanel.setBackground(Color.WHITE);
		changePasswordBlockPanel.add(changePasswordInput01Label);
		changePasswordBlockPanel.add(changePasswordInput01TextField);
		changePasswordBlockPanel.add(changePasswordInput02Label);
		changePasswordBlockPanel.add(changePasswordInput02TextField);
		changePasswordBlockPanel.add(changePasswordConfirm);
		// <==================== ====================>

		// Định nghĩa tính chất cho Change Password Dialog
		changePasswordDialog = new JDialog();
		changePasswordDialog.setTitle("Thay đổi Mật khẩu người dùng");
		changePasswordDialog.setLayout(null);
		changePasswordDialog.setSize(500, 320);
		changePasswordDialog.setResizable(false);
		changePasswordDialog.setLocationRelativeTo(null);
		changePasswordDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		changePasswordDialog.addWindowListener(new WindowAdapter() {
			// @Override
			// public void windowDeactivated(WindowEvent e) {
			// // Đóng Dialog khi mất focus (nhấn ngoài)
			// changePasswordDialog.dispose();
			// }
		});
		changePasswordDialog.add(changePasswordBlockPanel);
		changePasswordDialog.setModal(true);
		changePasswordDialog.setVisible(true);
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
		addressDetailApplyButton = CommonPL.getRoundedBorderButton(20, "Tạo địa chỉ",
				Color.decode("#42A5F5"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		addressDetailApplyButton.setBounds(20, 390, 460, 40);
		addressDetailApplyButton.addActionListener(e -> {
			// - Lấy ra các thông tin để kiểm tra
			String houseNumberAndStreetName = addressDetailHouseNumberAndStreetNameTextField.getText();
			String cityName = String.valueOf(addressDetailCityNameComboBox.getSelectedItem());
			String districtName = String.valueOf(addressDetailDistrictNameComboBox.getSelectedItem());
			String wardName = String.valueOf(addressDetailWardNameComboBox.getSelectedItem());

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
		// addressDetailDialog.addWindowListener(new WindowAdapter() {
		// @Override
		// public void windowDeactivated(WindowEvent e) {
		// SwingUtilities.invokeLater(() ->
		// addOrUpdateAddressTextField.requestFocusInWindow());
		// addressDetailDialog.dispose(); // Đóng Dialog khi mất focus (nhấn ngoài)
		// }
		// });
		addressDetailDialog.add(addressDetailBlockPanel);
		addressDetailDialog.setModal(true);
		addressDetailDialog.setVisible(true);
	}

	// Hàm đặt lại "Chi tiết Quyền" với "Quyền" được chọn tương ứng
	private void renderAddPrivilegeDetailPanel() {
		ArrayList<FunctionDTO> functionList = functionBLL.getAllFunction();
		for (FunctionDTO function : functionList) {
			addOrUpdatePrivilegeDetailCheckboxs.put(function.getName(), false);
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
			String privilegeValueSelected = String.valueOf(addOrUpdatePrivilegeComboBox.getSelectedItem());
			if (privilegeValueSelected.equals("QUANLY - Quản lý")) {
				addOrUpdatePrivilegeDetailPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				addOrUpdatePrivilegeDetailLabel.setText("Chi tiết Quyền");
				for (FunctionDTO function : functionList) {
					addOrUpdatePrivilegeDetailCheckboxs.put(function.getName(), true);
				}
			} else if (privilegeValueSelected.equals("NHANVIEN - Nhân viên")) {
				addOrUpdatePrivilegeDetailPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				addOrUpdatePrivilegeDetailLabel.setText("Chi tiết Quyền");
				for (FunctionDTO function : functionList) {
					if (function.getName().equals("Thống kê")) {
						addOrUpdatePrivilegeDetailCheckboxs.put(function.getName(), true);
					} else {
						addOrUpdatePrivilegeDetailCheckboxs.put(function.getName(), false);
					}
				}
			} else if (privilegeValueSelected.equals("Chọn Quyền")) {
				addOrUpdatePrivilegeDetailPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
				addOrUpdatePrivilegeDetailLabel.setText("<html><strike>Chi tiết Quyền</strike></html>");
				for (FunctionDTO function : functionList) {
					addOrUpdatePrivilegeDetailCheckboxs.put(function.getName(), false);
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

				// Nếu là "Chọn Quyền" hay "QUANLY - Quản lý" thì không cho nhấn và ngược lại
				if (privilegeValueSelected.equals("Chọn Quyền") || privilegeValueSelected.equals("QUANLY - Quản lý")) {
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
		addOrUpdateIdTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("id"), Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateIdTextField.setHorizontalAlignment(JTextField.CENTER);
		addOrUpdateIdTextField.setEnabled(false);
		((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
		addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));
		addOrUpdateIdTextField.setBounds(20, 50, 460, 40);

		// - Tuỳ chỉnh Add Or Update Fullname Label
		addOrUpdateFullnameLabel = CommonPL.getParagraphLabel("Họ và tên", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateFullnameLabel.setBounds(500, 10, 460, 40);

		// - Tuỳ chỉnh Add Or Update Fullname Text Field
		addOrUpdateFullnameTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("fullname"),
				Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateFullnameTextField.setBounds(500, 50, 460, 40);

		// - Tuỳ chỉnh Add Or Update Phone Label
		addOrUpdatePhoneLabel = CommonPL.getParagraphLabel("Số điện thoại", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdatePhoneLabel.setBounds(20, 100, 220, 40);

		// - Tuỳ chỉnh Add Or Update Phone Text Field
		addOrUpdatePhoneTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("phone"),
				Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdatePhoneTextField.setBounds(20, 140, 220, 40);

		// - Tuỳ chỉnh Add Or Update Email Label
		addOrUpdateEmailLabel = CommonPL.getParagraphLabel("Email", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateEmailLabel.setBounds(260, 100, 220, 40);

		// - Tuỳ chỉnh Add Or Update Email Text Field
		addOrUpdateEmailTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("email"),
				Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateEmailTextField.setBounds(260, 140, 220, 40);

		// - Tuỳ chỉnh Add Or Update Address Label
		addOrUpdateAddressLabel = CommonPL.getParagraphLabel("Địa chỉ", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateAddressLabel.setBounds(500, 100, 460, 40);

		// - Tuỳ chỉnh Add Or Update Address Button
		addOrUpdateAddressButton = CommonPL.getRoundedBorderButton(20, "Tạo địa chỉ",
				Color.decode("#42A5F5"), Color.WHITE,
				new Font("Arial", Font.BOLD, 14));
		addOrUpdateAddressButton.setBounds(780, 106, 180, 28);
		addOrUpdateAddressButton.addActionListener(e -> {
			// addOrUpdateDialog.setVisible(true);
			addOrUpdateAddressButton.setBackground(Color.BLACK);
			showAddressDetailDialog();

			// addOrUpdateDialog.setVisible(false);
			// SwingUtilities.invokeLater(() -> {
			// addOrUpdateAddressButton.setBackground(Color.BLACK);
			// showAddressDetailDialog();
			// addOrUpdateDialog.setVisible(true);
			// });
		});

		// - Tuỳ chỉnh Add Or Update Address Text Field
		addOrUpdateAddressTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("address"),
				Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateAddressTextField.setBounds(500, 140, 460, 40);

		// - Tuỳ chỉnh Add Or Update Username Label
		addOrUpdateUsernameLabel = CommonPL.getParagraphLabel(
				"<html>Tên tài khoản&nbsp;<span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateUsernameLabel.setBounds(20, 190, 460, 40);

		// - Tuỳ chỉnh Add Or Update Username Text Field
		addOrUpdateUsernameTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("username"),
				Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateUsernameTextField.setBounds(20, 230, 460, 40);

		// - Tuỳ chỉnh Add Or Update Password Label
		addOrUpdatePasswordLabel = CommonPL.getParagraphLabel(
				"<html>Mật khẩu&nbsp;<span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdatePasswordLabel.setBounds(20, 280, 460, 40);

		// - Tuỳ chỉnh Add Or Update Password Text Field
		if (title.equals("Thêm Người dùng") && button.equals("Thêm") && object.isEmpty()) {
			addOrUpdatePasswordTextField = new CommonPL.CustomTextField(0, 0, 0,
					defaultValuesForCrud.get("password"),
					Color.LIGHT_GRAY,
					Color.BLACK, CommonPL.getFontParagraphPlain());
		} else {
			addOrUpdatePasswordTextField = new CommonPL.CustomPasswordField(0, 0, 0,
					defaultValuesForCrud.get("password"),
					Color.LIGHT_GRAY,
					Color.BLACK, CommonPL.getFontParagraphPlain());
			addOrUpdatePasswordTextField.setEnabled(false);
			((CustomPasswordField) addOrUpdatePasswordTextField).setBorderColor(Color.decode("#dedede"));
			addOrUpdatePasswordTextField.setBackground(Color.decode("#dedede"));
		}
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
				((CustomPasswordField) addOrUpdatePasswordTextField).setBorderColor(Color.decode("#dedede"));
				addOrUpdatePasswordTextField.setBackground(Color.decode("#dedede"));
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
			String[] privilegeDetails = (String.valueOf(object.get(8))).split("\\, ");
			// + Mặc dinh là mục "Chọn Quyền" hiển thị đầu tiên
			ArrayList<FunctionDTO> functionList = functionBLL.getAllFunction();
			for (FunctionDTO function : functionList) {
				addOrUpdatePrivilegeDetailCheckboxs.put(function.getName(), false);
			}
			// + Tuỳ theo chi tiết quyền của người dùng đã chọn mà cập nhật
			for (String key : addOrUpdatePrivilegeDetailCheckboxs.keySet()) {
				for (String privilege : privilegeDetails) {
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
				if (addOrUpdatePrivilegeComboBox.getSelectedItem().equals("QUANLY - Quản lý")) {
					itemCheckBox.setEnabled(false);
					itemCheckBox.setForeground(Color.LIGHT_GRAY);
				}
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
			addOrUpdateStatusComboBox.setSelectedItem(String.valueOf(object.get(9)));
			addOrUpdateStatusComboBox.setEnabled(false);
			((JTextField) addOrUpdateStatusComboBox.getEditor().getEditorComponent()).setCaretPosition(0);
			UIManager.put("ComboBox.disabledBackground", Color.decode("#dedede"));
			addOrUpdateStatusComboBox.setBorder(BorderFactory.createLineBorder(Color.decode("#dedede"), 1));
		}

		// - Tuỳ chỉnh Add Or Update Button
		addOrUpdateButton = CommonPL.getRoundedBorderButton(20, button,
				button == "Thêm" ? Color.decode("#699f4c") : Color.decode("#bf873e"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		addOrUpdateButton.setBounds(260, 590, 460, 40);
		SwingUtilities.invokeLater(() -> addOrUpdateButton.requestFocusInWindow());
		addOrUpdateButton.addActionListener(e -> {
			CommonPL.createSelectionsDialog("Thông báo lựa chọn",
					String.format("Có chắc chắn %s người dùng này?", button.toLowerCase()),
					valueSelected);
			if (valueSelected[0]) {
				// - Lấy ra các giá trị hiện tại từ các thẻ JTextField
				// + Mã người dùng
				String id = !addOrUpdateIdTextField.getText().equals(defaultValuesForCrud.get("id"))
						? addOrUpdateIdTextField.getText()
						: null;
				// + Tên người dùng
				String fullname = !addOrUpdateFullnameTextField.getText().equals(defaultValuesForCrud.get("fullname"))
						? addOrUpdateFullnameTextField.getText()
						: null;
				// + Số điện thoại
				String phone = !addOrUpdatePhoneTextField.getText().equals(defaultValuesForCrud.get("phone"))
						? addOrUpdatePhoneTextField.getText()
						: null;
				// + Email
				String email = !addOrUpdateEmailTextField.getText().equals(defaultValuesForCrud.get("email"))
						? addOrUpdateEmailTextField.getText()
						: null;
				// + Địa chỉ
				String address = !addOrUpdateAddressTextField.getText().equals(defaultValuesForCrud.get("address"))
						? addOrUpdateAddressTextField.getText()
						: null;
				// + Tên tài khoản
				String username = !addOrUpdateUsernameTextField.getText().equals(defaultValuesForCrud.get("username"))
						? addOrUpdateUsernameTextField.getText()
						: null;
				// + Mật khẩu
				String password = !addOrUpdatePasswordTextField.getText().equals(defaultValuesForCrud.get("password"))
						? addOrUpdatePasswordTextField.getText()
						: null;
				// + Quyền
				String privilegeId = !String.valueOf(addOrUpdatePrivilegeComboBox.getSelectedItem())
						.equals(defaultValuesForCrud.get("privilege"))
								? String.valueOf(addOrUpdatePrivilegeComboBox.getSelectedItem()).split(" - ")[0]
								: null;
				// + Trạng thái
				String status = !String.valueOf(addOrUpdateStatusComboBox.getSelectedItem())
						.equals(defaultValuesForCrud.get("status"))
								? String.valueOf(addOrUpdateStatusComboBox.getSelectedItem())
								: null;
				// + Ngày cập nhật
				String timeUpdate = CommonPL.getCurrentDatetime();

				// - Biến chứa thông báo trả về
				String inform = null;
				// - Tuỳ vào tác vụ thêm hoặc thay đổi mà gọi đến hàm ở tầng BLL tương ứng
				if (title.equals("Thêm Người dùng") && button.equals("Thêm")) {
					inform = accountBLL.insertAccount(id, fullname, phone, email, address,
							username, password,
							privilegeId, status, timeUpdate);
				} else if (title.equals("Thay đổi Người dùng") && button.equals("Thay đổi")) {
					inform = accountBLL.updateAccount(id, fullname, phone, email, address,
							privilegeId, timeUpdate, String.valueOf(object.get(2)), String.valueOf(object.get(3)));
				}
				// - Tuỳ vào kết quả của thông báo trả về mà thông báo và cập nhật bảng dữ liệu
				if (inform.equals("Có thể thêm một người dùng") || inform.equals("Có thể thay đổi một người dùng")) {
					privilegeDetailBLL.insertPrivilegeDetail(id, privilegeId, addOrUpdatePrivilegeDetailCheckboxs);
					CommonPL.createSuccessDialog("Thông báo thành công", inform);
					addOrUpdateDialog.dispose();
					resetPage();
				} else {
					CommonPL.createErrorDialog("Thông báo lỗi", inform);
				}
			}
			valueSelected[0] = false;
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
			// @Override
			// public void windowDeactivated(WindowEvent e) {
			// // Đóng Dialog khi mất focus (nhấn ngoài)
			// addOrUpdateDialog.dispose();
			// }
		});
		addOrUpdateDialog.add(addOrUpdateBlockPanel);
		addOrUpdateDialog.setModal(true);
		addOrUpdateDialog.setVisible(true);
	}
}
