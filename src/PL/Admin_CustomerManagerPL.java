package PL;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.LinkedHashMap;
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
import javax.swing.UIManager;

import org.jdesktop.swingx.JXDatePicker;

import BLL.CustomerBLL;
import BLL.CustomerTypeBLL;
import DTO.CustomerDTO;
import PL.CommonPL.CustomCornerDatePicker.CustomRoundedBorder;
import PL.CommonPL.CustomTextField;

public class Admin_CustomerManagerPL extends JPanel {
	// Các đối tượng từ tầng Bussiness Logical Layer
	private CustomerBLL customerBLL;
	private CustomerTypeBLL customerTypeBLL;
	// Các Component
	private JLabel titleLabel;
	// - Các Component của Filter Panel
	private JLabel findLabel;
	private JButton findInformButton;
	private JTextField findInputTextField;
	private JLabel sortLabel;
	private Map<String, Boolean> sortCheckboxs;
	private JButton sortButton;
	private JLabel genderLabel;
	private Map<String, Boolean> genderRadios;
	private JButton genderButton;
	private JLabel typeLabel;
	private Map<String, Boolean> typeRadios;
	private JButton typeButton;
	private JLabel statusLabel;
	private JButton statusButton;
	private Map<String, Boolean> statusRadios;
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
	private JLabel addOrUpdateIdCardLabel;
	private JTextField addOrUpdateIdCardTextField;
	private JLabel addOrUpdateTypeLabel;
	private JComboBox<String> addOrUpdateTypeComboBox;
	private JLabel addOrUpdateFullnameLabel;
	private JTextField addOrUpdateFullnameTextField;
	private JLabel addOrUpdateGenderLabel;
	private JComboBox<String> addOrUpdateGenderComboBox;
	private JLabel addOrUpdateBirthdayLabel;
	private JXDatePicker addOrUpdateBirthdayDatePicker;
	private JLabel addOrUpdatePhoneLabel;
	private JTextField addOrUpdatePhoneTextField;
	private JLabel addOrUpdateEmailLabel;
	private JTextField addOrUpdateEmailTextField;
	private JLabel addOrUpdateAddressLabel;
	private JButton addOrUpdateAddressButton;
	private JTextField addOrUpdateAddressTextField;
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
	private final String[] columns = { "CCCD", "Loại khách hàng", "Họ và tên", "Ngày sinh", "Giới tính",
			"Số điện thoại", "Email", "Địa chỉ", "Trạng thái" };
	// + Chiều rộng các cột
	private final int[] widthColumns = { 200, 200, 500, 150, 150, 150, 400, 600, 150 };
	// + Dữ liệu
	private Object[][] datas = {};
	// + Dòng hiện tại đang được chọn
	private int rowSelected = -1;
	// + Giá trị (true / false) khi "Xoá" dòng dữ liệu
	private Boolean[] valueSelected = { null };

	// - Các giá trị mặc định cho text field, text area hoặc combobox để tìm kiếm,
	// thêm, sửa và xoá
	private final Map<String, String> defaultValuesForCrud = new LinkedHashMap<String, String>() {
		{
			put("idCard", "Nhập CCCD");
			put("type", "Chọn Loại khách hàng");
			put("fullname", "Nhập họ và tên");
			put("birthday", "Chọn Ngày sinh");
			put("gender", "Chọn Giới tính");
			put("phone", "Nhập số điện thoại");
			put("email", "Nhập email");
			put("address", "Nhập địa chỉ");
			put("status", "Chọn Trạng thái");
		}
	};

	// - Các thông tin cần thiết cho lọc khách hàng
	// + Sắp xếp
	private final String[] sortsString = { "Mã CCCD tăng dần", "Mã CCCD giảm dần", "Họ và tên tăng dần",
			"Họ và tên giảm dần", "Ngày sinh tăng dần", "Ngày sinh giảm dần" };
	private final String[] sortsSQL = { "cccd ASC", "cccd DESC", "hoVaTen ASC", "hoVaTen DESC", "ngaySinh ASC",
			"ngaySinh DESC" };
	// + Trạng thái
	private final String[] statusStringForFilter = { "Tất cả", "Hoạt động", "Tạm dừng" };
	private final String[] statusSQL = { "", "trangThai = 1", "trangThai = 0" };
	private final String[] statusStringForAddOrUpdate = { defaultValuesForCrud.get("status"), "Hoạt động", "Tạm dừng" };
	// + Loại khách hàng
	private final String[] typeSQL = { "", "maLoaiKhachHang = 'THUONG'", "maLoaiKhachHang = 'BAC'",
			"maLoaiKhachHang = 'VANG'", "maLoaiKhachHang = 'KIMCUONG'" };
	private final String[] typeStringForFilter = { "Tất cả", "Thường", "Bạc", "Vàng", "Kim cương" };
	private final String[] typeStringForAddOrUpdate = { defaultValuesForCrud.get("type"), "THUONG - Thường",
			"BAC - Bạc",
			"VANG - Vàng", "KIMCUONG - Kim cương" };
	// + Giới tính
	private final String[] genderSQL = { "", "gioiTinh = 'Nam'", "gioiTinh = 'Nữ'" };
	private final String[] genderStringForFilter = { "Tất cả", "Nam", "Nữ" };
	private final String[] genderStringForAddOrUpdate = { defaultValuesForCrud.get("gender"), "Nam", "Nữ" };

	public Admin_CustomerManagerPL() {
		// <===== Các đối tượng từ tầng Bussiness Logical Layer =====>
		customerBLL = new CustomerBLL();
		customerTypeBLL = new CustomerTypeBLL();
		// <==================== ====================>

		// <===== Cấu trúc của Title Label =====>
		// - Tuỳ chỉnh Title Label
		titleLabel = CommonPL.getTitleLabel("Khách hàng", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER,
				SwingConstants.CENTER);
		titleLabel.setBounds(30, 0, 1140, 115);
		// <==================== ====================>

		// <===== Cấu trúc của Filter Panel =====>
		// - Tuỳ chỉnh Find Input Label
		findLabel = CommonPL.getParagraphLabel("Tìm kiếm", Color.BLACK, CommonPL.getFontParagraphPlain());
		findLabel.setBounds(15, 15, 90, 24);

		// - Tuỳ chỉnh Find Input Inform Button
		findInformButton = CommonPL.getQuestionIconForm("?", "Thông tin bạn có thể tìm kiếm",
				"Bạn có thể tìm kiếm bằng các thông tin như: CCCD, Họ và tên, Số điện thoại, Email", Color.BLACK,
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
		sortCheckboxs = CommonPL.getMapHasValues(sortsString);

		// - Tuỳ chỉnh Sort Button
		sortButton = CommonPL.ButtonHasCheckboxs.createButtonHasCheckboxs(sortCheckboxs, sortsString[0],
				Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		sortButton.setBounds(390, 45, 360, 40);

		// - Tuỳ chỉnh Type Label
		typeLabel = CommonPL.getParagraphLabel("Loại khách hàng", Color.BLACK, CommonPL.getFontParagraphPlain());
		typeLabel.setBounds(765, 15, 360, 24);

		// - Tuỳ chỉnh Type Radios
		typeRadios = CommonPL.getMapHasValues(typeStringForFilter);

		// - Tuỳ chỉnh Type Button
		typeButton = CommonPL.ButtonHasRadios.createButtonHasRadios(typeRadios, typeStringForFilter[0],
				Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		typeButton.setBounds(765, 45, 360, 40);

		// - Tuỳ chỉnh Gender Label
		genderLabel = CommonPL.getParagraphLabel("Giới tính", Color.BLACK, CommonPL.getFontParagraphPlain());
		genderLabel.setBounds(15, 100, 360, 24);

		// - Tuỳ chỉnh Gender Radios
		genderRadios = CommonPL.getMapHasValues(genderStringForFilter);

		// - Tuỳ chỉnh Gender Button
		genderButton = CommonPL.ButtonHasRadios.createButtonHasRadios(genderRadios, genderStringForFilter[0],
				Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		genderButton.setBounds(15, 130, 360, 40);

		// - Tuỳ chỉnh status Label
		statusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK, CommonPL.getFontParagraphPlain());
		statusLabel.setBounds(390, 100, 360, 24);

		// - Tuỳ chỉnh status Radios
		statusRadios = CommonPL.getMapHasValues(statusStringForFilter);

		// - Tuỳ chỉnh status Button
		statusButton = CommonPL.ButtonHasRadios.createButtonHasRadios(statusRadios, statusStringForFilter[0],
				Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		statusButton.setBounds(390, 130, 360, 40);

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
		filterPanel.add(genderLabel);
		filterPanel.add(genderButton);
		filterPanel.add(statusLabel);
		filterPanel.add(statusButton);
		filterPanel.add(filterResetButton);
		filterPanel.add(filterApplyButton);
		// <==================== ====================>

		// <===== Cấu trúc của Data Panel =====>
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
		tableData = CommonPL.createTableData(columns, widthColumns, datas, "customer manager");
		tableScrollPane = CommonPL.createScrollPane(true, true, tableData);
		tableScrollPane.setBounds(15, 70, 1110, 400);

		// - Tuỳ chỉnh Data Panel;
		dataPanel = new CommonPL.RoundedPanel(12);
		dataPanel.setLayout(null);
		dataPanel.setBackground(Color.WHITE);
		dataPanel.setBounds(30, 330, 1140, 485);
		dataPanel.add(addButton);
		dataPanel.add(updateButton);
		dataPanel.add(lockButton);
		dataPanel.add(tableScrollPane);
		// <==================== ====================>

		// Đĩnh nghĩa tính chất cho Customer Manager PL
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

			showAddOrUpdateDialog("Thêm Khách hàng", "Thêm", new Vector<Object>());

			valueSelected[0] = false;
			rowSelected = -1;
			tableData.clearSelection();
		});

		updateButton.addActionListener(subE -> {
			if (rowSelected != -1) {
				addressDetailHouseNumberAndStreetNameInput = null;
				addressDetailCityNameSelected = null;
				addressDetailDistrictNameSelected = null;
				addressDetailWardNameSelected = null;

				String customerIdSelected = String.valueOf(tableData.getValueAt(rowSelected, 0));
				CustomerDTO customerSelected = customerBLL.getOneCustomerById(customerIdSelected);
				Vector<Object> currentObject = new Vector<>();
				currentObject.add(customerSelected.getIdCard());
				currentObject.add(customerSelected.getType());
				currentObject.add(customerSelected.getFullname());
				currentObject.add(customerSelected.getBirthday());
				currentObject.add(customerSelected.getGender());
				currentObject.add(customerSelected.getPhone());
				currentObject.add(customerSelected.getEmail());
				currentObject.add(customerSelected.getAddress());
				currentObject.add(customerSelected.getStatus() ? "Hoạt động" : "Tạm dừng");

				showAddOrUpdateDialog("Thay đổi Khách hàng", "Thay đổi", currentObject);
			} else {
				CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần thay đổi");
			}
			valueSelected[0] = false;
			rowSelected = -1;
			tableData.clearSelection();
		});

		lockButton.addActionListener(e -> {
			if (rowSelected != -1) {
				String customerIdSelected = String.valueOf(tableData.getValueAt(rowSelected, 0));
				CustomerDTO customerSelected = customerBLL.getOneCustomerById(customerIdSelected);

				CommonPL.createSelectionsDialog("Thông báo lựa chọn",
						String.format("Có chắc chắn muốn %s dòng dữ liệu này ?",
								customerSelected.getStatus() ? "khoá" : "mở khoá"),
						valueSelected);

				if (valueSelected[0]) {
					String inform = customerBLL.lockCustomer(customerSelected.getIdCard(),
							CommonPL.getCurrentDatetime());
					if (inform.equals("Có thể khoá một khách hàng")) {
						CommonPL.createSuccessDialog("Thông báo thành công",
								customerSelected.getStatus() ? "Khóa thành công"
										: "Mở khóa thành công");
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

		// Thiết lập sự kiện lọc dữ liệu
		filterDatasInTable();

		// Cập nhật dữ liệu ban đầu
		renderTableData(null, null, null);
	}

	// Hàm cập nhật dữ liệu cho bảng từ cơ sở dữ liệu
	private void renderTableData(String[] join, String condition, String order) {
		ArrayList<CustomerDTO> customerList = customerBLL.getAllCustomerByCondition(join, condition, order);
		Object[][] datasQuery = new Object[customerList.size()][columns.length];
		for (int i = 0; i < customerList.size(); i++) {
			datasQuery[i][0] = (Object) customerList.get(i).getIdCard();
			datasQuery[i][1] = (Object) customerTypeBLL.getOneCustomerTypeById(customerList.get(i).getType()).getName();
			datasQuery[i][2] = (Object) customerList.get(i).getFullname();
			datasQuery[i][3] = (Object) customerList.get(i).getBirthday();
			datasQuery[i][4] = (Object) customerList.get(i).getGender();
			datasQuery[i][5] = (Object) customerList.get(i).getPhone();
			datasQuery[i][6] = (Object) customerList.get(i).getEmail();
			datasQuery[i][7] = (Object) customerList.get(i).getAddress();
			datasQuery[i][8] = (Object) (customerList.get(i).getStatus() ? "Hoạt động" : "Tạm dừng");
		}
		datas = datasQuery;
		CommonPL.updateRowsInTableData(tableData, datas);
	}

	// Hàm reset lại trang
	private void resetPage() {
		// Cập nhật lại ô tìm kiếm
		findInputTextField.setText("Nhập thông tin");
		findInputTextField.setForeground(Color.LIGHT_GRAY);

		// Cập nhật lại ô sắp xếp
		CommonPL.resetMapForFilter(sortCheckboxs, sortsString, sortButton);

		// Cập nhật lại ô loại khách hàng
		CommonPL.resetMapForFilter(typeRadios, typeStringForFilter, typeButton);

		// Cập nhật lại ô giới tính
		CommonPL.resetMapForFilter(genderRadios, genderStringForFilter, genderButton);

		// Cập nhật lại ô trạng thái
		CommonPL.resetMapForFilter(statusRadios, statusStringForFilter, statusButton);

		// Cập nhật lại bảng
		renderTableData(null, null, null);
	}

	// Hàm sự kiện lọc dữ liệu
	private void filterDatasInTable() {
		// Nếu nhấn vào nút "Lọc"
		filterApplyButton.addActionListener(e -> {
			// Giá trị ô tìm kiếm
			String findValue = !findInputTextField.getText().equals("Nhập thông tin") ? findInputTextField.getText()
					: null;
			// Giá trị ô sắp xếp
			String sortsValue = CommonPL.getSQLFromCheckboxs(sortCheckboxs, sortsSQL);
			// Giá trị ô loại khách hàng
			String typeValue = CommonPL.getSQLFromRadios(typeRadios, typeSQL);
			// Giá trị ô giới tính
			String genderValue = CommonPL.getSQLFromRadios(genderRadios, genderSQL);
			// Giá trị ô trạng thái
			String statusValue = CommonPL.getSQLFromRadios(statusRadios, statusSQL);

			// Gán lệnh SQL tương ứng để truy vấn rồi cập nhật bảng
			String condition = (findValue != null ? String.format(
					"(cccd = '%s' OR hoVaTen LIKE '%%%s%%' OR soDienThoai LIKE '%%%s%%' OR email LIKE '%%%s%%')",
					findValue, findValue, findValue, findValue) : "")
					+ (typeValue != null ? (findValue != null ? " AND " + typeValue : typeValue) : "")
					+ (genderValue != null
							? (findValue != null || typeValue != null ? " AND " + genderValue : genderValue)
							: "")
					+ (statusValue != null
							? (findValue != null || typeValue != null || genderValue != null ? " AND " + statusValue
									: statusValue)
							: "");
			if (condition.length() == 0)
				condition = null;
			String order = sortsValue;
			renderTableData(null, condition, order);
		});

		// Nếu nhấn vào nút "Đặt lại"
		filterResetButton.addActionListener(e -> {
			// Gọi hàm reset trang
			resetPage();
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
		citys.add("Chọn Tỉnh / Thành phố");
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
		addressDetailApplyButton = CommonPL.getRoundedBorderButton(20, "Xác nhận",
				Color.decode("#42A5F5"), Color.WHITE,
				CommonPL.getFontParagraphBold());
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

	// Hàm hiển thị Dialog cho phép "Thêm" hoặc "Thay đổi" Khách hàng
	private void showAddOrUpdateDialog(String title, String button, Vector<Object> object) {
		// <===== Cấu trúc của Add Or Update Block Panel =====>
		// - Tuỳ chỉnh Add Or Update Id Card Label
		addOrUpdateIdCardLabel = CommonPL.getParagraphLabel(
				"<html>CCCD&nbsp;<span style='color: red; font-size: 20px;'>*</span></html>",
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateIdCardLabel.setBounds(20, 10, 220, 40);

		// - Tuỳ chỉnh Add Or Update Id Card Text Field
		addOrUpdateIdCardTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("idCard"),
				Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateIdCardTextField.setBounds(20, 50, 220, 40);

		// - Tuỳ chỉnh Add Or Update Type Label
		addOrUpdateTypeLabel = CommonPL.getParagraphLabel(
				"<html>Loại khách hàng&nbsp;<span style='color: red; font-size: 20px;'>*</span></html>",
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateTypeLabel.setBounds(260, 10, 220, 40);

		// - Tuỳ chỉnh Add Or Update Type Text Field
		Vector<String> typesVector = CommonPL.getVectorHasValues(typeStringForAddOrUpdate);
		addOrUpdateTypeComboBox = CommonPL.CustomComboBox(typesVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateTypeComboBox.setBounds(260, 50, 220, 40);

		// - Tuỳ chỉnh Add Or Update Fullname Label
		addOrUpdateFullnameLabel = CommonPL.getParagraphLabel(
				"<html>Họ và tên&nbsp;<span style='color: red; font-size: 20px;'>*</span></html>",
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateFullnameLabel.setBounds(20, 100, 460, 40);

		// - Tuỳ chỉnh Add Or Update Fullname Text Field
		addOrUpdateFullnameTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("fullname"),
				Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateFullnameTextField.setBounds(20, 140, 460, 40);

		// - Tuỳ chỉnh Add Or Update Birthday Label
		addOrUpdateBirthdayLabel = CommonPL.getParagraphLabel("Ngày sinh", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateBirthdayLabel.setBounds(20, 190, 220, 40);

		// - Tuỳ chỉnh Add Or Update Birthday Date Picker
		addOrUpdateBirthdayDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(0, CommonPL.getLocale(),
				CommonPL.getDateFormat(), defaultValuesForCrud.get("birthday"), Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain(), 40, 40);
		addOrUpdateBirthdayDatePicker.setBounds(20, 230, 220, 40);

		// - Tuỳ chỉnh Add Or Update Gender Label
		addOrUpdateGenderLabel = CommonPL.getParagraphLabel("Giới tính", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateGenderLabel.setBounds(260, 190, 220, 40);

		// - Tuỳ chỉnh Add Or Update Gender Text Field
		Vector<String> gendersVector = CommonPL.getVectorHasValues(genderStringForAddOrUpdate);
		addOrUpdateGenderComboBox = CommonPL.CustomComboBox(gendersVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateGenderComboBox.setBounds(260, 230, 220, 40);

		// - Tuỳ chỉnh Add Or Update Phone Label
		addOrUpdatePhoneLabel = CommonPL.getParagraphLabel(
				"<html>Số điện thoại&nbsp;<span style='color: red; font-size: 20px;'>*</span></html>",
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdatePhoneLabel.setBounds(20, 280, 220, 40);

		// - Tuỳ chỉnh Add Or Update Phone Text Field
		addOrUpdatePhoneTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("phone"),
				Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdatePhoneTextField.setBounds(20, 320, 220, 40);

		// - Tuỳ chỉnh Add Or Update Email Label
		addOrUpdateEmailLabel = CommonPL.getParagraphLabel("Email", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateEmailLabel.setBounds(260, 280, 220, 40);

		// - Tuỳ chỉnh Add Or Update Email Text Field
		addOrUpdateEmailTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("email"),
				Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateEmailTextField.setBounds(260, 320, 220, 40);

		// - Tuỳ chỉnh Add Or Update Address Label
		addOrUpdateAddressLabel = CommonPL.getParagraphLabel("Địa chỉ", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateAddressLabel.setBounds(20, 370, 60, 40);

		// - Tuỳ chỉnh Add Or Update Address Button
		addOrUpdateAddressButton = CommonPL.getRoundedBorderButton(20, "Tạo địa chỉ",
				Color.decode("#42A5F5"), Color.WHITE,
				new Font("Arial", Font.BOLD, 14));
		addOrUpdateAddressButton.setBounds(360, 376, 120, 28);
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
				Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateAddressTextField.setBounds(20, 410, 460, 40);

		// - Tuỳ chỉnh Add Or Update Status Label
		addOrUpdateStatusLabel = CommonPL.getParagraphLabel(
				"<html>Trạng thái&nbsp;<span style='color: red; font-size: 20px;'>*</span></html>",
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateStatusLabel.setBounds(20, 460, 460, 40);

		// - Tuỳ chỉnh Add Or Update Status ComboBox
		Vector<String> statusVector = CommonPL.getVectorHasValues(statusStringForAddOrUpdate);
		addOrUpdateStatusComboBox = CommonPL.CustomComboBox(statusVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateStatusComboBox.setBounds(20, 500, 460, 40);

		// Khi "Thay đổi" một Khách hàng
		if (title.equals("Thay đổi Khách hàng") && button.equals("Thay đổi") && object.size() != 0) {
			// - Gán dữ liệu là "Mã CCCD"
			if (object.get(0) != null) {
				addOrUpdateIdCardTextField.setText(String.valueOf(object.get(0)));
				addOrUpdateIdCardTextField.setCaretPosition(0);
				addOrUpdateIdCardTextField.setEnabled(false);
				addOrUpdateIdCardTextField.setBackground(Color.decode("#dedede"));
				((CustomTextField) addOrUpdateIdCardTextField).setBorderColor(Color.decode("#dedede"));
			}

			// - Gán dữ liệu là "Loại khách hàng"
			if (object.get(1) != null) {
				for (int i = 0; i < typeStringForAddOrUpdate.length; i++) {
					if (typeStringForAddOrUpdate[i].startsWith(String.valueOf(object.get(1)))) {
						addOrUpdateTypeComboBox.setSelectedIndex(i);
						addOrUpdateTypeComboBox.setForeground(Color.BLACK);
						((JTextField) addOrUpdateTypeComboBox.getEditor().getEditorComponent()).setCaretPosition(0);
						break;
					}
				}
			}

			// - Gán dữ liệu là "Họ và tên"
			if (object.get(2) != null) {
				addOrUpdateFullnameTextField.setText(String.valueOf(object.get(2)));
				addOrUpdateFullnameTextField.setCaretPosition(0);
				addOrUpdateFullnameTextField.setForeground(Color.BLACK);
			}

			// - Gán dữ liệu là "Ngày sinh"
			if (object.get(3) != null) {
				try {
					Date date = CommonPL.getDateFormat().parse(String.valueOf(object.get(3)));
					addOrUpdateBirthdayDatePicker.setDate(date);
					((JButton) addOrUpdateBirthdayDatePicker.getComponent(1))
							.setBorder(new CustomRoundedBorder(Color.BLACK, 0, 0, 0, 0));
					addOrUpdateBirthdayDatePicker.getEditor().setCaretPosition(0);
					addOrUpdateBirthdayDatePicker.getEditor()
							.setBorder(new CustomRoundedBorder(Color.BLACK, 0, 0, 0, 0));
					addOrUpdateBirthdayDatePicker.getEditor().setForeground(Color.BLACK);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			// - Gán dữ liệu là "Giới tính"
			if (object.get(4) != null) {
				addOrUpdateGenderComboBox.setSelectedItem(String.valueOf(object.get(4)));
				((JTextField) addOrUpdateGenderComboBox.getEditor().getEditorComponent()).setCaretPosition(0);
				addOrUpdateGenderComboBox.setForeground(Color.BLACK);
			}

			// - Gán dữ liệu là "Số điện thoại"
			if (object.get(5) != null) {
				addOrUpdatePhoneTextField.setText(String.valueOf(object.get(5)));
				addOrUpdatePhoneTextField.setCaretPosition(0);
				addOrUpdatePhoneTextField.setForeground(Color.BLACK);
			}

			// - Gán dữ liệu là "Email"
			if (object.get(6) != null) {
				addOrUpdateEmailTextField.setText(String.valueOf(object.get(6)));
				addOrUpdateEmailTextField.setCaretPosition(0);
				addOrUpdateEmailTextField.setForeground(Color.BLACK);
			}

			// - Gán dữ liệu là "Địa chỉ"
			if (object.get(7) != null) {
				addOrUpdateAddressTextField.setText(String.valueOf(object.get(7)));
				addOrUpdateAddressTextField.setCaretPosition(0);
				addOrUpdateAddressTextField.setForeground(Color.BLACK);
			}

			// - Gán dữ liệu là "Trạng thái"
			if (object.get(8) != null) {
				addOrUpdateStatusComboBox.setSelectedItem(String.valueOf(object.get(8)));
				addOrUpdateStatusComboBox.setEnabled(false);
				((JTextField) addOrUpdateStatusComboBox.getEditor().getEditorComponent()).setCaretPosition(0);
				UIManager.put("ComboBox.disabledBackground", Color.decode("#dedede"));
				addOrUpdateStatusComboBox.setBorder(BorderFactory.createLineBorder(Color.decode("#dedede"), 1));
			}
		}

		// - Tuỳ chỉnh Add Or Update Button
		addOrUpdateButton = CommonPL.getRoundedBorderButton(20, button,
				button == "Thêm" ? Color.decode("#699f4c") : Color.decode("#bf873e"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		addOrUpdateButton.setBounds(20, 590, 460, 40);
		SwingUtilities.invokeLater(() -> addOrUpdateButton.requestFocusInWindow());
		addOrUpdateButton.addActionListener(e -> {
			CommonPL.createSelectionsDialog("Thông báo lựa chọn",
					String.format("Có chắc chắn %s khách hàng này?", button.toLowerCase()),
					valueSelected);
			if (valueSelected[0]) {
				// - Lấy ra các giá trị hiện tại từ các thẻ JTextField
				// + CCCD (Căn cước công dân)
				String id = !addOrUpdateIdCardTextField.getText().equals(defaultValuesForCrud.get("idCard"))
						? addOrUpdateIdCardTextField.getText()
						: null;
				// + Loại khách hàng
				String type = !String.valueOf(addOrUpdateTypeComboBox.getSelectedItem())
						.equals(defaultValuesForCrud.get("type"))
								? String.valueOf(addOrUpdateTypeComboBox.getSelectedItem()).split(" - ")[0]
								: null;
				// + Tên khách hàng
				String fullname = !addOrUpdateFullnameTextField.getText().equals(defaultValuesForCrud.get("fullname"))
						? addOrUpdateFullnameTextField.getText()
						: null;
				// + Ngày sinh
				String birthday = !String.valueOf(addOrUpdateBirthdayDatePicker.getEditor().getText())
						.equals(defaultValuesForCrud.get("birthday"))
								? String.valueOf(addOrUpdateBirthdayDatePicker.getEditor().getText())
								: null;
				// + Giới tính
				String gender = !String.valueOf(addOrUpdateGenderComboBox.getSelectedItem())
						.equals(defaultValuesForCrud.get("gender"))
								? String.valueOf(addOrUpdateGenderComboBox.getSelectedItem())
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
				if (title.equals("Thêm Khách hàng") && button.equals("Thêm")) {
					inform = customerBLL.insertCustomer(id, type, fullname, birthday, gender, phone, email, address,
							status,
							timeUpdate);
				} else if (title.equals("Thay đổi Khách hàng") && button.equals("Thay đổi")) {
					inform = customerBLL.updateCustomer(id, type, fullname, birthday, gender, phone, email, address,
							timeUpdate, String.valueOf(object.get(5)), String.valueOf(object.get(6)));
				}
				// - Tuỳ vào kết quả của thông báo trả về mà thông báo và cập nhật bảng dữ liệu
				if (inform.equals("Có thể thêm một khách hàng") || inform.equals("Có thể thay đổi một khách hàng")) {
					CommonPL.createSuccessDialog("Thông báo thành công", String.format("%s thành công", button));
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
		addOrUpdateBlockPanel.setBounds(0, 0, 500, 680);
		addOrUpdateBlockPanel.setBackground(Color.WHITE);
		addOrUpdateBlockPanel.add(addOrUpdateIdCardLabel);
		addOrUpdateBlockPanel.add(addOrUpdateIdCardTextField);
		addOrUpdateBlockPanel.add(addOrUpdateTypeLabel);
		addOrUpdateBlockPanel.add(addOrUpdateTypeComboBox);
		addOrUpdateBlockPanel.add(addOrUpdateFullnameLabel);
		addOrUpdateBlockPanel.add(addOrUpdateFullnameTextField);
		addOrUpdateBlockPanel.add(addOrUpdateBirthdayLabel);
		addOrUpdateBlockPanel.add(addOrUpdateBirthdayDatePicker);
		addOrUpdateBlockPanel.add(addOrUpdateGenderLabel);
		addOrUpdateBlockPanel.add(addOrUpdateGenderComboBox);
		addOrUpdateBlockPanel.add(addOrUpdatePhoneLabel);
		addOrUpdateBlockPanel.add(addOrUpdatePhoneTextField);
		addOrUpdateBlockPanel.add(addOrUpdateEmailLabel);
		addOrUpdateBlockPanel.add(addOrUpdateEmailTextField);
		addOrUpdateBlockPanel.add(addOrUpdateAddressLabel);
		addOrUpdateBlockPanel.add(addOrUpdateAddressButton);
		addOrUpdateBlockPanel.add(addOrUpdateAddressTextField);
		addOrUpdateBlockPanel.add(addOrUpdateStatusLabel);
		addOrUpdateBlockPanel.add(addOrUpdateStatusComboBox);
		addOrUpdateBlockPanel.add(addOrUpdateButton);
		// <==================== ====================>

		// Định nghĩa tính chất cho Add Or Update Dialog
		addOrUpdateDialog = new JDialog();
		addOrUpdateDialog.setTitle(title);
		addOrUpdateDialog.setLayout(null);
		addOrUpdateDialog.setSize(500, 680);
		addOrUpdateDialog.setResizable(false);
		addOrUpdateDialog.setLocationRelativeTo(null);
		addOrUpdateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addOrUpdateDialog.addWindowListener(new WindowAdapter() {
			// @Override
			// public void windowDeactivated(WindowEvent e) {
			// if (!addressDetailDialog.isVisible()) {
			//// addOrUpdateDialog.setVisible(true);
			// addOrUpdateDialog.dispose(); // Đóng Dialog khi mất focus (nhấn ngoài)
			// }
			// }
		});
		addOrUpdateDialog.add(addOrUpdateBlockPanel);
		addOrUpdateDialog.setModal(true);
		addOrUpdateDialog.setVisible(true);
	}
}
