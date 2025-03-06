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
import DTO.DiscountDTO;
import PL.CommonPL.CustomCornerDatePicker.CustomRoundedBorder;
import PL.CommonPL.CustomTextField;

public class Admin_DiscountManagerPL extends JPanel {
	// Các đối tượng từ tầng Bussiness Logical Layer
	private DiscountBLL discountBLL;
	// Các Component
	private JLabel titleLabel;
	// - Các Component của Filter Panel
	private JLabel findLabel;
	private JTextField findInputTextField;
	private JButton findInformButton;
	private JLabel sortsLabel;
	private Map<String, Boolean> sortsCheckboxs;
	private JButton sortsButton;
	private JLabel statusLabel;
	private Map<String, Boolean> statusRadios;
	private JButton statusButton;
	private JLabel dateStartLabel;
	private JButton dateStartInformButton;
	private JXDatePicker dateStartBeforeDatePicker;
	private JLabel dateStartRectangleLabel;
	private JXDatePicker dateStartAfterDatePicker;
	private JLabel dateEndLabel;
	private JButton dateEndInformButton;
	private JXDatePicker dateEndBeforeDatePicker;
	private JLabel dateEndRectangleLabel;
	private JXDatePicker dateEndAfterDatePicker;
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
	private JLabel addOrUpdatePercentLabel;
	private JTextField addOrUpdatePercentTextField;
	private JLabel addOrUpdateRoomCostLabel;
	private JTextField addOrUpdateRoomCostTextField;
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
	private final String[] columns = { "Mã khuyến mãi", "Tên khuyến mãi", "Phần trăm", "Mức áp dụng (VNĐ)",
			"Ngày bắt đầu", "Ngày kết thúc", "Trạng thái" };
	// + Chiều rộng các cột
	private final int[] widthColumns = { 150, 400, 150, 200, 150, 150, 150 };
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
	private final String[] statusStringForFilter;
	private final String[] statusStringForAddOrUpdate;
	private final String[] statusSQL;

	public Admin_DiscountManagerPL() {
		// <===== Các đối tượng từ tầng Bussiness Logical Layer =====>
		discountBLL = new DiscountBLL();
		// <==================== ====================>

		// <===== Cập nhật các dữ liệu cần thiết =====>
		sortsString = new String[] { "Mã khuyến mãi tăng dần", "Mã khuyến mãi giảm dần", "Tên khuyến mãi tăng dần",
				"Tên khuyến mãi giảm dần", "Phần trăm tăng dần", "Phần trăm giảm dần", "Mức áp dụng tăng dần",
				"Mức áp dụng giảm dần", "Ngày bắt đầu tăng dần", "Ngày bắt đầu giảm dần" };
		sortsSQL = new String[] { "maKhuyenMai ASC", "maKhuyenMai DESC", "tenKhuyenMai ASC", "tenKhuyenMai DESC",
				"phanTram ASC", "phanTram DESC", "mucApDung ASC", "mucApDung DESC", "ngayBatDau ASC",
				"ngayKetThuc DESC" };
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

		// - Tuỳ chỉnh Date Start Label
		dateStartLabel = CommonPL.getParagraphLabel("Ngày bắt đầu", Color.BLACK, CommonPL.getFontParagraphPlain());
		dateStartLabel.setBounds(765, 15, 120, 24);

		// - Tuỳ chỉnh Date Start Inform Button
		dateStartInformButton = CommonPL.getQuestionIconForm("?", "Chú thích sử dụng lọc Ngày bắt đầu",
				"Lọc những ngày có trong đoạn từ Ngày trước đến Ngày sau và chỉ cho phép nhập các định dạng như: ddmmyyyy, dd-mm-yyyy.",
				Color.BLACK, CommonPL.getFontQuestionIcon());
		dateStartInformButton.setBounds(898, 15, 24, 24);

		// - Tuỳ chỉnh Date Start Before Date Picker
		dateStartBeforeDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(10, CommonPL.getLocale(),
				CommonPL.getDateFormat(), "Ngày trước", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(),
				20, 40);
		dateStartBeforeDatePicker.setBounds(765, 45, 170, 40);

		// - Tuỳ chỉnh Date Start Rectangle Label
		dateStartRectangleLabel = CommonPL.getTitleLabel("-", Color.BLACK, CommonPL.getFontParagraphPlain(),
				SwingConstants.CENTER, SwingConstants.CENTER);
		dateStartRectangleLabel.setBounds(935, 45, 20, 40);

		// - Tuỳ chỉnh Date Start After Date Picker
		dateStartAfterDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(10, CommonPL.getLocale(),
				CommonPL.getDateFormat(), "Ngày sau", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(),
				20, 40);
		dateStartAfterDatePicker.setBounds(955, 45, 170, 40);

		// - Tuỳ chỉnh Date End Label
		dateEndLabel = CommonPL.getParagraphLabel("Ngày kết thúc", Color.BLACK, CommonPL.getFontParagraphPlain());
		dateEndLabel.setBounds(15, 100, 125, 24);

		// - Tuỳ chỉnh Date End Inform Button
		dateEndInformButton = CommonPL.getQuestionIconForm("?", "Chú thích sử dụng lọc Ngày kết thúc",
				"Lọc những ngày có trong đoạn từ Ngày trước đến Ngày sau và chỉ cho phép nhập các định dạng như: ddmmyyyy, dd-mm-yyyy.",
				Color.BLACK, CommonPL.getFontQuestionIcon());
		dateEndInformButton.setBounds(153, 100, 24, 24);

		// - Tuỳ chỉnh Date End Before Date Picker
		dateEndBeforeDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(10, CommonPL.getLocale(),
				CommonPL.getDateFormat(), "Ngày trước", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(),
				20, 40);
		dateEndBeforeDatePicker.setBounds(15, 130, 170, 40);

		// - Tuỳ chỉnh Date End Rectangle Label
		dateEndRectangleLabel = CommonPL.getTitleLabel("-", Color.BLACK, CommonPL.getFontParagraphPlain(),
				SwingConstants.CENTER, SwingConstants.CENTER);
		dateEndRectangleLabel.setBounds(185, 130, 20, 40);

		// - Tuỳ chỉnh Date End After Date Picker
		dateEndAfterDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(10, CommonPL.getLocale(),
				CommonPL.getDateFormat(), "Ngày sau", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(),
				20, 40);
		dateEndAfterDatePicker.setBounds(205, 130, 170, 40);

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
		filterPanel.add(dateStartLabel);
		filterPanel.add(dateStartInformButton);
		filterPanel.add(dateStartBeforeDatePicker);
		filterPanel.add(dateStartRectangleLabel);
		filterPanel.add(dateStartAfterDatePicker);
		filterPanel.add(dateEndLabel);
		filterPanel.add(dateEndInformButton);
		filterPanel.add(dateEndBeforeDatePicker);
		filterPanel.add(dateEndRectangleLabel);
		filterPanel.add(dateEndAfterDatePicker);
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

	// Hàm sự kiện lọc dữ liệu
	private void filterDatasInTable() {
		// Nếu nhấn vào nút "Áp dụng"
		filterApplyButton.addActionListener(e -> {
			// Giá trị ô tìm kiếm
			String findValue = !findInputTextField.getText().equals("Nhập thông tin") ? findInputTextField.getText()
					: null;
			// Giá trị ô sắp xếp
			String sortsValue = CommonPL.getSQLFromCheckboxs(sortsCheckboxs, sortsSQL);
			// Giá trị ô trạng thái
			String statusValue = CommonPL.getSQLFromRadios(statusRadios, statusSQL);
			// Giá trị ngày bắt đầu
			String dateStartBefore = !dateStartBeforeDatePicker.getEditor().getText().equals("Ngày trước")
					? dateStartBeforeDatePicker.getEditor().getText()
					: null;
			String dateStartAfter = !dateStartAfterDatePicker.getEditor().getText().equals("Ngày sau")
					? dateStartAfterDatePicker.getEditor().getText()
					: null;
			if (dateStartBefore != null && dateStartAfter != null && CommonBLL.compareBetweenTwoDate(dateStartBefore, dateStartAfter) == 1) {
				CommonPL.createErrorDialog("Thông báo lỗi",
						"Ngày trước phải là trước hoặc bằng Ngày sau (Ngày bắt đầu)");
				return;
			}
			// Giá trị ngày kết thúc
			String dateEndBefore = !dateEndBeforeDatePicker.getEditor().getText().equals("Ngày trước")
					? dateEndBeforeDatePicker.getEditor().getText()
					: null;
			String dateEndAfter = !dateEndAfterDatePicker.getEditor().getText().equals("Ngày sau")
					? dateEndAfterDatePicker.getEditor().getText()
					: null;
			if (dateEndBefore != null && dateEndAfter != null && CommonBLL.compareBetweenTwoDate(dateEndBefore, dateEndAfter) == 1) {
				CommonPL.createErrorDialog("Thông báo lỗi",
						"Ngày trước phải là trước hoặc bằng Ngày sau (Ngày kết thúc)");
				return;
			}

			// Gán lệnh SQL tương ứng để truy vấn rồi cập nhật bảng
			String[] join = null;
			String condition = (findValue != null
					? String.format("(maKhuyenMai = '%s' OR tenKhuyenMai LIKE '%%%s%%')", findValue, findValue)
					: "")
					+ (statusValue != null ? (findValue != null ? " AND " + statusValue : statusValue) : "")
					+ (dateStartBefore != null && dateStartAfter != null ? (findValue != null || statusValue != null
							? " AND "
									+ String.format("ngayBatDau BETWEEN '%s' AND '%s'", dateStartBefore, dateStartAfter)
							: String.format("ngayBatDau BETWEEN '%s' AND '%s'", dateStartBefore, dateStartAfter)) : "")
					+ (dateEndBefore != null && dateEndAfter != null
							? (findValue != null || statusValue != null
									|| (dateStartBefore != null && dateStartAfter != null)
											? " AND " + String.format("ngayKetThuc BETWEEN '%s' AND '%s'",
													dateEndBefore, dateEndAfter)
											: String.format("ngayKetThuc BETWEEN '%s' AND '%s'", dateEndBefore,
													dateEndAfter))
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

			// Cập nhật lại ô trạng thái
			CommonPL.resetMapForFilter(statusRadios, statusStringForFilter, statusButton);

			// Cập nhật lại ô ngày bắt đầu
			CommonPL.resetDatePickerForFilter(dateStartBeforeDatePicker, "Ngày trước", Color.LIGHT_GRAY, 10);
			CommonPL.resetDatePickerForFilter(dateStartAfterDatePicker, "Ngày sau", Color.LIGHT_GRAY, 10);

			// Cập nhật lại ô ngày kết thúc
			CommonPL.resetDatePickerForFilter(dateEndBeforeDatePicker, "Ngày trước", Color.LIGHT_GRAY, 10);
			CommonPL.resetDatePickerForFilter(dateEndAfterDatePicker, "Ngày sau", Color.LIGHT_GRAY, 10);

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
			datasQuery[i][2] = (Object) discountList.get(i).getPercent();
			datasQuery[i][3] = (Object) CommonPL
					.moneyLongToMoneyFormat(new BigInteger(String.valueOf(discountList.get(i).getRoomCost())));
			datasQuery[i][4] = (Object) discountList.get(i).getDateStart();
			datasQuery[i][5] = (Object) discountList.get(i).getDateEnd();
			datasQuery[i][6] = (Object) (discountList.get(i).getStatus() ? "Hoạt động" : "Tạm dừng");
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

		// - Tuỳ chỉnh Add Or Update Percent Label
		addOrUpdatePercentLabel = CommonPL.getParagraphLabel("Phần trăm (%)", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdatePercentLabel.setBounds(20, 190, 220, 40);

		// - Tuỳ chỉnh Add Or Update Percent Text Field
		addOrUpdatePercentTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Phần trăm (%)", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdatePercentTextField.setBounds(20, 230, 220, 40);

		// - Tuỳ chỉnh Add Or Update Room Cost Label
		addOrUpdateRoomCostLabel = CommonPL.getParagraphLabel("Mức áp dụng (VNĐ)", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateRoomCostLabel.setBounds(260, 190, 220, 40);

		// - Tuỳ chỉnh Add Or Update Room Cost Text Field
		addOrUpdateRoomCostTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mức áp dụng (VNĐ)", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateRoomCostTextField.setBounds(260, 230, 220, 40);

		// - Tuỳ chỉnh Add Or Update Date Start Label
		addOrUpdateDateStartLabel = CommonPL.getParagraphLabel("Ngày bắt đầu", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateDateStartLabel.setBounds(20, 280, 220, 40);

		// - Tuỳ chỉnh Add Or Update Date Start Date Picker
		addOrUpdateDateStartDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(0, CommonPL.getLocale(),
				CommonPL.getDateFormat(), "Chọn Ngày", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(),
				40, 40);
		addOrUpdateDateStartDatePicker.setBounds(20, 320, 220, 40);

		// - Tuỳ chỉnh Add Or Update Date End Label
		addOrUpdateDateEndLabel = CommonPL.getParagraphLabel("Ngày kết thúc", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateDateEndLabel.setBounds(260, 280, 220, 40);

		// - Tuỳ chỉnh Add Or Update Date End Date Picker
		addOrUpdateDateEndDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(0, CommonPL.getLocale(),
				CommonPL.getDateFormat(), "Chọn Ngày", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(),
				40, 40);
		addOrUpdateDateEndDatePicker.setBounds(260, 320, 220, 40);

		// - Tuỳ chỉnh Add Or Update Status Label
		addOrUpdateStatusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateStatusLabel.setBounds(20, 370, 460, 40);

		// - Tuỳ chỉnh Add Or Update Status ComboBox
		Vector<String> statusVector = CommonPL.getVectorHasValues(statusStringForAddOrUpdate);
		addOrUpdateStatusComboBox = CommonPL.CustomComboBox(statusVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateStatusComboBox.setBounds(20, 410, 460, 40);

		// - Tuỳ chỉnh Add Or Update Time Label
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
			addOrUpdateIdTextField.setText(String.valueOf(object.get(0)));
			addOrUpdateIdTextField.setEnabled(false);
			addOrUpdateIdTextField.setCaretPosition(0);
			((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
			addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));

			// - Gán dữ liệu là "Tên khuyến mãi"
			addOrUpdateNameTextField.setText(String.valueOf(object.get(1)));
			addOrUpdateNameTextField.setCaretPosition(0);
			addOrUpdateNameTextField.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Phần trăm"
			addOrUpdatePercentTextField.setText(String.valueOf(object.get(2)));
			addOrUpdatePercentTextField.setCaretPosition(0);
			addOrUpdatePercentTextField.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Mức áp dụng"
			addOrUpdateRoomCostTextField.setText(String.valueOf(object.get(3)).replace(".", ""));
			addOrUpdateRoomCostTextField.setCaretPosition(0);
			addOrUpdateRoomCostTextField.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Ngày bắt đầu"
			try {
				Date date = CommonPL.getDateFormat().parse(String.valueOf(object.get(4)));
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
				Date date = CommonPL.getDateFormat().parse(String.valueOf(object.get(5)));
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
			addOrUpdateStatusComboBox.setSelectedItem(String.valueOf(object.get(6)));
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
		addOrUpdateButton.setBounds(20, 500, 460, 40);
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
			// + Phần trăm (%)
			String percent = !addOrUpdatePercentTextField.getText().equals("Nhập Phần trăm (%)")
					? addOrUpdatePercentTextField.getText()
					: null;
			// + Mức áp dụng (VNĐ)
			String roomCost = !addOrUpdateRoomCostTextField.getText().equals("Nhập Mức áp dụng (VNĐ)")
					? addOrUpdateRoomCostTextField.getText()
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
				String inform = discountBLL.insertDiscount(id, name, percent, roomCost, dateStart, dateEnd, status,
						dateUpdate);
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
				String inform = discountBLL.updateDiscount(id, name, percent, roomCost, dateStart, dateEnd, status,
						dateUpdate);
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
		addOrUpdateBlockPanel.setBounds(0, 0, 500, 590);
		addOrUpdateBlockPanel.setBackground(Color.WHITE);
		addOrUpdateBlockPanel.add(addOrUpdateIdLabel);
		addOrUpdateBlockPanel.add(addOrUpdateIdTextField);
		addOrUpdateBlockPanel.add(addOrUpdateNameLabel);
		addOrUpdateBlockPanel.add(addOrUpdateNameTextField);
		addOrUpdateBlockPanel.add(addOrUpdatePercentLabel);
		addOrUpdateBlockPanel.add(addOrUpdatePercentTextField);
		addOrUpdateBlockPanel.add(addOrUpdateRoomCostLabel);
		addOrUpdateBlockPanel.add(addOrUpdateRoomCostTextField);
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
		addOrUpdateDialog.setSize(500, 590);
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
