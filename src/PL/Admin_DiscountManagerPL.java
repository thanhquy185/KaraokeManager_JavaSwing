package PL;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

import org.jdesktop.swingx.JXDatePicker;

import BLL.CommonBLL;
import BLL.DiscountBLL;
import BLL.DiscountTypeBLL;
import DTO.DiscountDTO;
import DTO.DiscountTypeDTO;
import DTO.PrivilegeDTO;
import PL.CommonPL.CustomCornerDatePicker.CustomRoundedBorder;
import PL.CommonPL.CustomTextField;

public class Admin_DiscountManagerPL extends JPanel {
	// Các đối tượng từ tầng Bussiness Logical Layer
	private DiscountBLL discountBLL;
	private DiscountTypeBLL discountTypeBLL;
	// Các Component
	private JLabel titleLabel;
	// - Các Component của Filter Panel
	private JLabel findLabel;
	private JTextField findInputTextField;
	private JButton findInformButton;
	private JLabel sortsLabel;
	private Map<String, Boolean> sortsCheckboxs;
	private JButton sortsButton;
	private JLabel typesLabel;
	private Map<String, Boolean> typesRadios;
	private JButton typesButton;
	private JLabel dateLabel;
	private JButton dateInformButton;
	private JXDatePicker dateStartDatePicker;
	private JLabel dateRectangleLabel;
	private JXDatePicker dateEndDatePicker;
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
	private JTable tableData;
	private JScrollPane tableScrollPane;
	private JPanel dataPanel;
	// - Các Component của Add Dialog (dialog thêm một Khuyến mãi)
	private JLabel addOrUpdateIdLabel;
	private JTextField addOrUpdateIdTextField;
	private JLabel addOrUpdateNameLabel;
	private JTextField addOrUpdateNameTextField;
	private JLabel addOrUpdateTypeLabel;
	private JComboBox<String> addOrUpdateTypeComboBox;
	private JLabel addOrUpdateValueLabel;
	private JTextField addOrUpdateValueTextField;
	private JLabel addOrUpdateCostMinLabel;
	private JTextField addOrUpdateCostMinTextField;
	private JLabel addOrUpdateCostMaxLabel;
	private JTextField addOrUpdateCostMaxTextField;
	private JLabel addOrUpdateDateStartLabel;
	private JXDatePicker addOrUpdateDateStartDatePicker;
	private JLabel addOrUpdateDateEndLabel;
	private JXDatePicker addOrUpdateDateEndDatePicker;
	private JLabel addOrUpdateStatusLabel;
	private JComboBox<String> addOrUpdateStatusComboBox;
	private JLabel addOrUpdateTimeLabel;
	private JLabel addOrUpdateTimeDetailLabel;
	private JButton addOrUpdateButton;
	private JPanel addOrUpdateBlockPanel;
	private JDialog addOrUpdateDialog;
	// - Các thông tin cần có của Table Data và Table Scroll Pane
	// + Tiêu đề các cột
	private final String[] columns = { "Mã khuyến mãi", "Tên khuyến mãi", "Loại khuyến mãi", "Giá trị",
			"Mức tối thiểu (VNĐ)", "Mức tối đa (VNĐ)", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái" };
	// + Chiều rộng các cột
	private final int[] widthColumns = { 150, 400, 150, 150, 250, 250, 150, 150, 150 };
	// + Dữ liệu
	private Object[][] datas = {};
	// + Dòng hiện tại đang được chọn
	private int rowSelected = -1;
	// + Giá trị (true / false) khi "Xoá" dòng dữ liệu
	private Boolean[] valueSelected = { null };
	// - Các thông tin cần thiết cho khuyến mãi
	// + Sắp xếp
	private final String[] sortsString;
	private final String[] sortsSQL;
	// + Trạng thái
	private final String[] typesStringForFilter;
	private final String[] typesStringForAddOrUpdate;
	private final String[] typesSQL;
	// + Trạng thái
	private final String[] statusStringForFilter;
	private final String[] statusStringForAddOrUpdate;
	private final String[] statusSQL;

	public Admin_DiscountManagerPL() {
		// <===== Các đối tượng từ tầng Bussiness Logical Layer =====>
		discountBLL = new DiscountBLL();
		discountTypeBLL = new DiscountTypeBLL();
		// <==================== ====================>

		// <===== Cập nhật các dữ liệu cần thiết =====>
		sortsString = new String[] { "Mã khuyến mãi tăng dần", "Mã khuyến mãi giảm dần", "Tên khuyến mãi tăng dần",
				"Tên khuyến mãi giảm dần", "Giá trị tăng dần", "Giá trị giảm dần", "Mức tối thiểu (VNĐ) tăng dần",
				"Mức tối thiểu (VNĐ) giảm dần", "Ngày bắt đầu tăng dần", "Ngày bắt đầu giảm dần" };
		sortsSQL = new String[] { "maKhuyenMai ASC", "maKhuyenMai DESC", "tenKhuyenMai ASC", "tenKhuyenMai DESC",
				"giaTri ASC", "giaTri DESC", "mucToiThieu ASC", "mucToiThieu DESC", "ngayBatDau ASC",
				"ngayKetThuc DESC" };
		typesStringForFilter = renderTypesString("Tìm kiếm");
		typesStringForAddOrUpdate = renderTypesString("Thêm hoặc sửa");
		typesSQL = renderTypesString("Truy vấn SQL");
		statusStringForFilter = new String[] { "Tất cả", "Hoạt động", "Tạm dừng" };
		statusStringForAddOrUpdate = new String[] { "Chọn Trạng thái", "Hoạt động", "Tạm dừng" };
		statusSQL = new String[] { "", "trangThai = 1", "trangThai = 0" };
		// <==================== ====================>

		// <===== Cấu trúc của Title Label =====>
		// - Tuỳ chỉnh Title Label
		titleLabel = CommonPL.getTitleLabel("Khuyến mãi", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER,
				SwingConstants.CENTER);
		titleLabel.setBounds(30, 0, 1140, 115);
		// <==================== ====================>

		// <===== Cấu trúc của Filter Panel =====>
		// - Tuỳ chỉnh Find Input Label
		findLabel = CommonPL.getParagraphLabel("Tìm kiếm", Color.BLACK, CommonPL.getFontParagraphPlain());
		findLabel.setBounds(15, 15, 90, 24);

		// - Tuỳ chỉnh Find Input Inform Button
		findInformButton = CommonPL.getQuestionIconForm("?", "Thông tin bạn có thể tìm kiếm",
				"Bạn có thể tìm kiếm bằng các thông tin như: Mã khuyến mãi, Tên khuyến mãi", Color.BLACK,
				CommonPL.getFontQuestionIcon());
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

		// - Tuỳ chỉnh Types Label
		typesLabel = CommonPL.getParagraphLabel("Loại khuyến mãi", Color.BLACK, CommonPL.getFontParagraphPlain());
		typesLabel.setBounds(765, 15, 360, 24);

		// - Tuỳ chỉnh Types Radios
		typesRadios = CommonPL.getMapHasValues(typesStringForFilter);

		// - Tuỳ chỉnh Types Button
		typesButton = CommonPL.ButtonHasRadios.createButtonHasRadios(typesRadios, typesStringForFilter[0],
				Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		typesButton.setBounds(765, 45, 360, 40);

		// - Tuỳ chỉnh Date Label
		dateLabel = CommonPL.getParagraphLabel("Ngày áp dụng", Color.BLACK, CommonPL.getFontParagraphPlain());
		dateLabel.setBounds(15, 100, 125, 24);

		// - Tuỳ chỉnh Date Inform Button
		dateInformButton = CommonPL.getQuestionIconForm("?", "Chú thích sử dụng lọc Ngày áp dụng",
				"Lọc những ngày có trong đoạn từ Ngày trước đến Ngày sau và chỉ cho phép nhập các định dạng như: ddmmyyyy, dd-mm-yyyy.",
				Color.BLACK, CommonPL.getFontQuestionIcon());
		dateInformButton.setBounds(153, 100, 24, 24);

		// - Tuỳ chỉnh Date Start Date Picker
		dateStartDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(10, CommonPL.getLocale(),
				CommonPL.getDateFormat(), "Ngày bắt đầu", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain(), 20, 40);
		dateStartDatePicker.setBounds(15, 130, 170, 40);

		// - Tuỳ chỉnh Date Rectangle Label
		dateRectangleLabel = CommonPL.getTitleLabel("-", Color.BLACK, CommonPL.getFontParagraphPlain(),
				SwingConstants.CENTER, SwingConstants.CENTER);
		dateRectangleLabel.setBounds(185, 130, 20, 40);

		// - Tuỳ chỉnh Date End Date Picker
		dateEndDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(10, CommonPL.getLocale(),
				CommonPL.getDateFormat(), "Ngày kết thúc", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain(), 20, 40);
		dateEndDatePicker.setBounds(205, 130, 170, 40);

		// - Tuỳ chỉnh Status Label
		statusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK, CommonPL.getFontParagraphPlain());
		statusLabel.setBounds(390, 100, 360, 24);

		// - Tuỳ chỉnh Status Radios
		statusRadios = CommonPL.getMapHasValues(statusStringForFilter);

		// - Tuỳ chỉnh Status Button
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
		filterPanel.add(sortsLabel);
		filterPanel.add(sortsButton);
		filterPanel.add(typesLabel);
		filterPanel.add(typesButton);
		filterPanel.add(dateLabel);
		filterPanel.add(dateInformButton);
		filterPanel.add(dateStartDatePicker);
		filterPanel.add(dateRectangleLabel);
		filterPanel.add(dateEndDatePicker);
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
			showAddOrUpdateDialog("Thêm Khuyến mãi", "Thêm", new Vector<Object>());
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

				showAddOrUpdateDialog("Thay đổi Khuyến mãi", "Thay đổi", currentObject);
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
					String inform = discountBLL.lockDiscount(String.valueOf(currentObject.get(0)),
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

		//
		filterDatasInTable();

		// Cập nhật dữ liệu ban đầu cho bản
		renderTableData(null, null, null);
	}

	// Hàm cập nhật dữ liệu loại mã khuyến mãi cho việc thêm, sửa hoặc tìm kiếm
	private String[] renderTypesString(String action) {
		ArrayList<DiscountTypeDTO> discountTypeList = discountTypeBLL.getAllDiscountType();
		String[] result = new String[discountTypeList.size() + 1];
		if (action.equals("Tìm kiếm")) {
			result[0] = "Tất cả";
		} else if (action.equals("Thêm hoặc sửa")) {
			result[0] = "Chọn Loại khuyến mãi";
		} else if (action.equals("Truy vấn SQL")) {
			result[0] = "";
		}
		for (int i = 0; i < discountTypeList.size(); i++) {
			if (action.equals("Tìm kiếm")) {
				result[i + 1] = String.format("%s", discountTypeList.get(i).getName());
			} else if (action.equals("Thêm hoặc sửa")) {
				result[i + 1] = String.format("%s - %s", discountTypeList.get(i).getId(),
						discountTypeList.get(i).getName());
			} else if (action.equals("Truy vấn SQL")) {
				result[i + 1] = String.format("maLoaiKhuyenMai = '%s'", discountTypeList.get(i).getId());
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
			// Giá trị ô loại khuyến mãi
			String typesValue = CommonPL.getSQLFromRadios(typesRadios, typesSQL);
			// Giá trị ngày áp dụng
			String dateStart = !dateStartDatePicker.getEditor().getText().equals("Ngày bắt đầu")
					? dateStartDatePicker.getEditor().getText()
					: null;
			String dateEnd = !dateEndDatePicker.getEditor().getText().equals("Ngày kết thúc")
					? dateEndDatePicker.getEditor().getText()
					: null;
			if (dateStart != null && dateEnd != null && CommonBLL.compareBetweenTwoDate(dateStart, dateEnd) == 1) {
				CommonPL.createErrorDialog("Thông báo lỗi", "Ngày bắt đầu phải là trước hoặc bằng Ngày kết thúc");
				return;
			}
			// Giá trị ô trạng thái
			String statusValue = CommonPL.getSQLFromRadios(statusRadios, statusSQL);

			// Gán lệnh SQL tương ứng để truy vấn rồi cập nhật bảng
			String[] join = null;
			String condition = (findValue != null
					? String.format("(maKhuyenMai = '%s' OR tenKhuyenMai LIKE '%%%s%%')", findValue, findValue)
					: "")
					+ (typesValue != null ? (findValue != null ? " AND " + typesValue : typesValue) : "")
					+ (dateStart != null ? (findValue != null || typesValue != null
							? " AND " + String.format("ngayBatDau = '%s'", dateStart)
							: String.format("ngayBatDau = '%s'", dateStart)) : "")
					+ (dateEnd != null ? (findValue != null || typesValue != null || dateStart != null
							? " AND " + String.format("ngayKetThuc = '%s'", dateEnd)
							: String.format("ngayKetThuc = '%s'", dateEnd)) : "")
					+ (statusValue != null
							? (findValue != null || typesValue != null || dateStart != null || dateEnd != null
									? " AND " + statusValue
									: statusValue)
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

			// Cập nhật lại ô loại khuyến mãi
			CommonPL.resetMapForFilter(typesRadios, typesStringForFilter, typesButton);

			// Cập nhật lại ô ngày áp dụng
			CommonPL.resetDatePickerForFilter(dateStartDatePicker, "Ngày bắt đầu", Color.LIGHT_GRAY, 10);
			CommonPL.resetDatePickerForFilter(dateEndDatePicker, "Ngày kết thúc", Color.LIGHT_GRAY, 10);

			// Cập nhật lại ô trạng thái
			CommonPL.resetMapForFilter(statusRadios, statusStringForFilter, statusButton);

			// Cập nhật lại bảng
			renderTableData(null, null, null);
		});
	}

	// Hàm cập nhật dữ liệu cho bảng từ cơ sở dữ liệu
	private void renderTableData(String[] join, String condition, String order) {
		ArrayList<DiscountDTO> discountList = discountBLL.getAllDiscountByCondition(join, condition, order);
		Object[][] datasQuery = new Object[discountList.size()][columns.length];
		for (int i = 0; i < discountList.size(); i++) {
			datasQuery[i][0] = (Object) discountList.get(i).getId();
			datasQuery[i][1] = (Object) discountList.get(i).getName();
			datasQuery[i][2] = (Object) discountTypeBLL.getOneDiscountTypeById(discountList.get(i).getDiscountType())
					.getName();
			datasQuery[i][3] = (Object) CommonPL
					.moneyLongToMoneyFormat(new BigInteger(String.valueOf(discountList.get(i).getValue())));
			datasQuery[i][4] = (Object) CommonPL
					.moneyLongToMoneyFormat(new BigInteger(String.valueOf(discountList.get(i).getCostMin())));
			datasQuery[i][5] = (Object) CommonPL
					.moneyLongToMoneyFormat(new BigInteger(String.valueOf(discountList.get(i).getCostMax())));
			datasQuery[i][6] = (Object) discountList.get(i).getDateStart();
			datasQuery[i][7] = (Object) discountList.get(i).getDateEnd();
			datasQuery[i][8] = (Object) (discountList.get(i).getStatus() ? "Hoạt động" : "Tạm dừng");
		}
		datas = datasQuery;

		CommonPL.updateRowsInTableData(tableData, datas);
	}

	// Hàm hiển thị Dialog thêm một Khuyến mãi
	private void showAddOrUpdateDialog(String title, String button, Vector<Object> object) {
		// <===== Cấu trúc của Add Block Panel =====>
		// - Tuỳ chỉnh Add Or Update Id Label
		addOrUpdateIdLabel = CommonPL.getParagraphLabel("Mã khuyến mãi", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateIdLabel.setBounds(20, 10, 460, 40);

		// - Tuỳ chỉnh Add Or Update Id Text Field
		addOrUpdateIdTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mã khuyến mãi", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateIdTextField.setEnabled(false);
		((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
		addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));
		addOrUpdateIdTextField.setBounds(20, 50, 460, 40);

		// - Tuỳ chỉnh Add Or Update Name Label
		addOrUpdateNameLabel = CommonPL.getParagraphLabel("Tên khuyến mãi", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateNameLabel.setBounds(20, 100, 460, 40);

		// - Tuỳ chỉnh Add Or Update Name Text Field
		addOrUpdateNameTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Tên khuyến mãi", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateNameTextField.setBounds(20, 140, 460, 40);

		// - Tuỳ chỉnh Add Or Update Type Label
		addOrUpdateTypeLabel = CommonPL.getParagraphLabel("Loại khuyến mãi", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateTypeLabel.setBounds(20, 190, 220, 40);

		// - Tuỳ chỉnh Add Or Update Type ComboBox
		Vector<String> typesVector = CommonPL.getVectorHasValues(typesStringForAddOrUpdate);
		addOrUpdateTypeComboBox = CommonPL.CustomComboBox(typesVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateTypeComboBox.setBounds(20, 230, 220, 40);

		// - Tuỳ chỉnh Add Or Update Value Label
		addOrUpdateValueLabel = CommonPL.getParagraphLabel("Giá trị", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateValueLabel.setBounds(260, 190, 220, 40);

		// - Tuỳ chỉnh Add Or Update Value Text Field
		addOrUpdateValueTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Giá trị", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateValueTextField.setBounds(260, 230, 220, 40);

		// - Tuỳ chỉnh Add Or Update Cost Min Label
		addOrUpdateCostMinLabel = CommonPL.getParagraphLabel("Mức tổi thiểu (VNĐ)", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateCostMinLabel.setBounds(20, 280, 220, 40);

		// - Tuỳ chỉnh Add Or Update Cost Min Text Field
		addOrUpdateCostMinTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mức tổi thiểu (VNĐ)",
				Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateCostMinTextField.setCaretPosition(0);
		addOrUpdateCostMinTextField.setBounds(20, 320, 220, 40);

		// - Tuỳ chỉnh Add Or Update Cost Max Label
		addOrUpdateCostMaxLabel = CommonPL.getParagraphLabel("Mức tối đa (VNĐ)", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateCostMaxLabel.setBounds(260, 280, 220, 40);

		// - Tuỳ chỉnh Add Or Update Cost Max Text Field
		addOrUpdateCostMaxTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mức tối đa (VNĐ)", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateCostMaxTextField.setCaretPosition(0);
		addOrUpdateCostMaxTextField.setBounds(260, 320, 220, 40);

		// - Tuỳ chỉnh Add Or Update Date Start Label
		addOrUpdateDateStartLabel = CommonPL.getParagraphLabel("Ngày bắt đầu", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateDateStartLabel.setBounds(20, 370, 220, 40);

		// - Tuỳ chỉnh Add Or Update Date Start Date Picker
		addOrUpdateDateStartDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(0, CommonPL.getLocale(),
				CommonPL.getDateFormat(), "Chọn Ngày", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(),
				40, 40);
		addOrUpdateDateStartDatePicker.setBounds(20, 410, 220, 40);

		// - Tuỳ chỉnh Add Or Update Date End Label
		addOrUpdateDateEndLabel = CommonPL.getParagraphLabel("Ngày kết thúc", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateDateEndLabel.setBounds(260, 370, 220, 40);

		// - Tuỳ chỉnh Add Or Update Date End Date Picker
		addOrUpdateDateEndDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(0, CommonPL.getLocale(),
				CommonPL.getDateFormat(), "Chọn Ngày", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(),
				40, 40);
		addOrUpdateDateEndDatePicker.setBounds(260, 410, 220, 40);

		// - Tuỳ chỉnh Add Or Update Status Label
		addOrUpdateStatusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateStatusLabel.setBounds(20, 460, 460, 40);

		// - Tuỳ chỉnh Add Or Update Status ComboBox
		Vector<String> statusVector = CommonPL.getVectorHasValues(statusStringForAddOrUpdate);
		addOrUpdateStatusComboBox = CommonPL.CustomComboBox(statusVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateStatusComboBox.setBounds(20, 500, 460, 40);

		// - Tuỳ chỉnh Add Or Update Time Label
		addOrUpdateTimeLabel = CommonPL.getParagraphLabel("Cập nhật gần đây:", Color.GRAY,
				new Font("Arial", Font.ITALIC, 18));
		addOrUpdateTimeLabel.setBounds(20, 550, 148, 40);

		// - Tuỳ chỉnh Add Or Update Time Detail Label
		addOrUpdateTimeDetailLabel = CommonPL.getTitleLabel("yyyy-MM-dd HH:mm:ss", Color.GRAY,
				new Font("Arial", Font.ITALIC, 18), SwingConstants.RIGHT, SwingConstants.CENTER);
		addOrUpdateTimeDetailLabel.setBounds(173, 550, 307, 40);
		// -- Tạo Timer cập nhật thời gian
		Timer timer = new Timer(1000, e -> {
			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDateTime = currentDateTime.format(formatter);
			addOrUpdateTimeDetailLabel.setText(formattedDateTime);
		});
		timer.start();

		// - Khi "Thêm" một Khuyến mãi
		if (title.equals("Thêm Khuyến mãi") && button.equals("Thêm") && object.size() == 0) {
			// - Mặc định mã người dùng sẽ tự động tăng dần khi "thêm"
			String id = (discountBLL.getAllDiscountByCondition(null, null, "maKhuyenMai DESC").get(0)).getId();
			String postfix = String.format("%05d", Integer.parseInt(id.substring(3)) + 1);
			;
			addOrUpdateIdTextField.setText("KM" + postfix);
			((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
		}

		// Khi "Thay đổi" một Khuyến mãi
		if (title.equals("Thay đổi Khuyến mãi") && button.equals("Thay đổi") && object.size() != 0) {
			// - Gán dữ liệu là "Mã khuyến mãi"
			if (object.get(0) != null) {
				addOrUpdateIdTextField.setText(String.valueOf(object.get(0)));
				addOrUpdateIdTextField.setEnabled(false);
				addOrUpdateIdTextField.setCaretPosition(0);
				((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
				addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));
			}

			// - Gán dữ liệu là "Tên khuyến mãi"
			if (object.get(1) != null) {
				addOrUpdateNameTextField.setText(String.valueOf(object.get(1)));
				addOrUpdateNameTextField.setCaretPosition(0);
				addOrUpdateNameTextField.setForeground(Color.BLACK);
			}

			// - Gán dữ liệu là "Loại khuyến mãi"
			for (int i = 0; i < addOrUpdateTypeComboBox.getItemCount(); i++) {
				String item = addOrUpdateTypeComboBox.getItemAt(i);
				if (item.contains(String.valueOf(object.get(2)))) {
					addOrUpdateTypeComboBox.setSelectedItem(item);
					break;
				}
			}
			((JTextField) addOrUpdateTypeComboBox.getEditor().getEditorComponent()).setCaretPosition(0);
			addOrUpdateTypeComboBox.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Giá trị"
			if (object.get(3) != null) {
				addOrUpdateValueTextField.setText(String.valueOf(object.get(3)).replace(".", ""));
				addOrUpdateValueTextField.setCaretPosition(0);
				addOrUpdateValueTextField.setForeground(Color.BLACK);
			}

			// - Gán dữ liệu là "Mức tối thiểu (VNĐ)"
			if (object.get(4) != null) {
				addOrUpdateCostMinTextField.setText(String.valueOf(object.get(4)).replace(".", ""));
				addOrUpdateCostMinTextField.setCaretPosition(0);
				addOrUpdateCostMinTextField.setForeground(Color.BLACK);
			}

			// - Gán dữ liệu là "Mức tối đa (VNĐ)"
			if (object.get(4) != null) {
				addOrUpdateCostMaxTextField.setText(String.valueOf(object.get(5)).replace(".", ""));
				addOrUpdateCostMaxTextField.setCaretPosition(0);
				addOrUpdateCostMaxTextField.setForeground(Color.BLACK);
			}

			// - Gán dữ liệu là "Ngày bắt đầu"
			try {
				Date date = CommonPL.getDateFormat().parse(String.valueOf(object.get(6)));
				addOrUpdateDateStartDatePicker.setDate(date);
				((JButton) addOrUpdateDateStartDatePicker.getComponent(1))
						.setBorder(new CustomRoundedBorder(Color.BLACK, 0, 0, 0, 0));
				addOrUpdateDateStartDatePicker.getEditor().setCaretPosition(0);
				addOrUpdateDateStartDatePicker.getEditor().setBorder(new CustomRoundedBorder(Color.BLACK, 0, 0, 0, 0));
				addOrUpdateDateStartDatePicker.getEditor().setForeground(Color.BLACK);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// - Gán dữ liệu là "Ngày kết thúc"
			try {
				Date date = CommonPL.getDateFormat().parse(String.valueOf(object.get(7)));
				addOrUpdateDateEndDatePicker.setDate(date);
				((JButton) addOrUpdateDateEndDatePicker.getComponent(1))
						.setBorder(new CustomRoundedBorder(Color.BLACK, 0, 0, 0, 0));
				addOrUpdateDateEndDatePicker.getEditor().setCaretPosition(0);
				addOrUpdateDateEndDatePicker.getEditor().setBorder(new CustomRoundedBorder(Color.BLACK, 0, 0, 0, 0));
				addOrUpdateDateEndDatePicker.getEditor().setForeground(Color.BLACK);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// - Gán dữ liệu là "Trạng thái"
			addOrUpdateStatusComboBox.setSelectedItem(String.valueOf(object.get(8)));
			addOrUpdateStatusComboBox.setEnabled(false);
			((JTextField) addOrUpdateStatusComboBox.getEditor().getEditorComponent()).setCaretPosition(0);
			UIManager.put("ComboBox.disabledBackground", Color.decode("#dedede"));
			addOrUpdateStatusComboBox.setBorder(BorderFactory.createLineBorder(Color.decode("#dedede"), 1));

			// - Gán dữ liệu là "Thời gian cập nhật gần đây"
//							addOrUpdateTimeDetailLabel.setText((String) object.get(11));
		}

		// - Tuỳ chỉnh Add Or Update Button
		addOrUpdateButton = CommonPL.getRoundedBorderButton(20, button,
				button == "Thêm" ? Color.decode("#699f4c") : Color.decode("#bf873e"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		addOrUpdateButton.setBounds(20, 590, 460, 40);
		SwingUtilities.invokeLater(() -> addOrUpdateButton.requestFocusInWindow());
		addOrUpdateButton.addActionListener(e -> {
			// - Lấy ra các giá trị hiện tại từ các thẻ JTextField
			// + Mã khuyến mãi
			String id = !addOrUpdateIdTextField.getText().equals("Nhập Mã khuyến mãi")
					? addOrUpdateIdTextField.getText()
					: null;
			// + Tên khuyến mãi
			String name = !addOrUpdateNameTextField.getText().equals("Nhập Tên khuyến mãi")
					? addOrUpdateNameTextField.getText()
					: null;
			// + Loại khuyến mãi
			String type = !String.valueOf(addOrUpdateTypeComboBox.getSelectedItem()).equals("Chọn Loại khuyến mãi")
					? String.valueOf(addOrUpdateTypeComboBox.getSelectedItem()).split(" - ")[0]
					: null;
			// + Giá trị
			String value = !addOrUpdateValueTextField.getText().equals("Nhập Giá trị")
					? addOrUpdateValueTextField.getText()
					: null;
			// + Mức tối thiểu (VNĐ)
			String costMin = !addOrUpdateCostMinTextField.getText().equals("Nhập Mức tối thiểu (VNĐ)")
					? addOrUpdateCostMinTextField.getText()
					: null;
			// + Mức tối đa (VNĐ)
			String costMax = !addOrUpdateCostMaxTextField.getText().equals("Nhập Mức tối đa (VNĐ)")
					? addOrUpdateCostMaxTextField.getText()
					: null;
			// + Ngày bắt đầu
			String dateStart = !addOrUpdateDateStartDatePicker.getEditor().getText().equals("Chọn Ngày")
					? addOrUpdateDateStartDatePicker.getEditor().getText()
					: null;
			// + Ngày kết thúc
			String dateEnd = !addOrUpdateDateEndDatePicker.getEditor().getText().equals("Chọn Ngày")
					? addOrUpdateDateEndDatePicker.getEditor().getText()
					: null;
			// + Trạng thái
			String status = !String.valueOf(addOrUpdateStatusComboBox.getSelectedItem()).equals("Chọn Trạng thái")
					? String.valueOf(addOrUpdateStatusComboBox.getSelectedItem())
					: null;
			// + Ngày cập nhật
			String dateUpdate = CommonPL.getCurrentDate();

			// - Nếu là "Thêm"
			if (title.equals("Thêm Khuyến mãi") && button.equals("Thêm")) {
				String inform = discountBLL.insertDiscount(id, name, type, value, costMin, costMax, dateStart, dateEnd,
						status, dateUpdate);
				if (inform.equals("Có thể thêm một khuyến mãi")) {
					// - Hoàn thành việc thêm thì thông báo và cập nhật lại trên giao diện
					CommonPL.createSuccessDialog("Thông báo thành công", "Thêm thành công");
					addOrUpdateDialog.dispose();
					renderTableData(null, null, null);
				} else {
					CommonPL.createErrorDialog("Thông báo lỗi", inform);
				}
			}
			// - Nếu là "Sửa"
			else if (title.equals("Thay đổi Khuyến mãi") && button.equals("Thay đổi")) {
				String inform = discountBLL.updateDiscount(id, name, type, value, costMin, costMax, dateStart, dateEnd,
						status, dateUpdate);
				if (inform.equals("Có thể thay đổi một khuyến mãi")) {
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
		addOrUpdateBlockPanel.setBounds(0, 0, 500, 680);
		addOrUpdateBlockPanel.setBackground(Color.WHITE);
		addOrUpdateBlockPanel.add(addOrUpdateIdLabel);
		addOrUpdateBlockPanel.add(addOrUpdateIdTextField);
		addOrUpdateBlockPanel.add(addOrUpdateNameLabel);
		addOrUpdateBlockPanel.add(addOrUpdateNameTextField);
		addOrUpdateBlockPanel.add(addOrUpdateTypeLabel);
		addOrUpdateBlockPanel.add(addOrUpdateTypeComboBox);
		addOrUpdateBlockPanel.add(addOrUpdateValueLabel);
		addOrUpdateBlockPanel.add(addOrUpdateValueTextField);
		addOrUpdateBlockPanel.add(addOrUpdateCostMinLabel);
		addOrUpdateBlockPanel.add(addOrUpdateCostMinTextField);
		addOrUpdateBlockPanel.add(addOrUpdateCostMaxLabel);
		addOrUpdateBlockPanel.add(addOrUpdateCostMaxTextField);
		addOrUpdateBlockPanel.add(addOrUpdateDateStartLabel);
		addOrUpdateBlockPanel.add(addOrUpdateDateStartDatePicker);
		addOrUpdateBlockPanel.add(addOrUpdateDateEndLabel);
		addOrUpdateBlockPanel.add(addOrUpdateDateEndDatePicker);
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
		addOrUpdateDialog.setSize(500, 680);
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
