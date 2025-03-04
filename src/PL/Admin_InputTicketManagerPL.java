package PL;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
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

import org.jdesktop.swingx.JXDatePicker;

import PL.CommonPL.CustomCornerDatePicker.CustomRoundedBorder;
import PL.CommonPL.CustomTextField;

public class Admin_InputTicketManagerPL extends JPanel {
	// Các Component
	private JLabel titleLabel;
	// - Các Component của Filter Panel
	private JLabel findLabel;
	private JTextField findInputTextField;
	private JButton findInformButton;
	private JLabel sortLabel;
	private Map<String, Boolean> sortCheckboxs;
	private JButton sortButton;
	private JLabel dateCreateLabel;
	private JButton dateCreateInformButton;
	private JXDatePicker dateCreateBeforeDatePicker;
	private JLabel dateCreateRectangleLabel;
	private JXDatePicker dateCreateAfterDatePicker;

	private JLabel dateContractLabel;
	private JButton dateContractInformButton;
	private JXDatePicker dateContractBeforeDatePicker;
	private JLabel dateContractRectangleLabel;
	private JXDatePicker dateContractAfterDatePicker;

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
	private JTable tableData;
	private JScrollPane tableScrollPane;
	private JPanel dataPanel;

	// - Các thông tin cần có của Table Data và Table Scroll Pane
	// + Tiêu đề các cột
	private final String[] columns = { "Mã phiếu", "Ngày lập phiếu", "Ngày hợp đồng", "Nhân viên lập phiếu",
			"Nhân viên nhận hàng", "Tổng tiền thanh toán", "Trạng thái" };
	// + Chiều rộng các cột
	private final int[] widthColumns = { 150, 150, 150, 200, 200, 450, 200 };
	// + Dữ liệu
	private Object[][] datas = { { "1", "2024-01-25", "2024-01-25", "NV0001", "NV0001", "1000000000", "Đã hoàn thành" },
			{ "1", "2024-01-25", "2024-01-25", "NV0001", "NV0001", "1000000000", "Chưa thanh toán" },
			{ "1", "2024-01-25", "2024-01-25", "NV0002", "NV0001", "1000000000", "Đang chờ xác nhận" },
			{ "1", "2024-01-25", "2024-01-25", "NV0001", "NV0001", "1000000000", "Đã huỷ phiếu" } };
	// + Dòng hiện tại đang được chọn
	private int rowSelected = -1;
	// + Giá trị (true / false) khi "Xoá" dòng dữ liệu
	private Boolean[] valueSelected = { null };

	public Admin_InputTicketManagerPL() {
		// <===== Cấu trúc Title Label =====>
		// - Tuỳ chỉnh Title Label
		titleLabel = CommonPL.getTitleLabel("Phiếu nhập", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER,
				SwingConstants.CENTER);
		titleLabel.setBounds(30, 0, 1110, 115);
		// <==================== ====================>

		// <===== Cấu trúc Filter Panel =====>
		// - Tuỳ chỉnh Find Input Label
		findLabel = CommonPL.getParagraphLabel("Tìm kiếm", Color.BLACK, CommonPL.getFontParagraphPlain());
		findLabel.setBounds(15, 15, 90, 24);

		// - Tuỳ chỉnh Find Input Inform Button
		findInformButton = CommonPL.getQuestionIconForm("?", "Thông tin bạn có thể tìm kiếm",
				"Bạn có thể tìm kiếm bằng các thông tin như: Mã phiếu nhập", Color.BLACK,
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
		String[] sorts = new String[] { "Mã phiếu tăng dần", "Mã phiếu giảm dần", "Ngày lập phiếu tăng dần",
				"Ngày lập phiếu giảm dần", "Tổng tiền thanh toán tăng dần", "Tổng tiền thanh toán giảm dần" };
		sortCheckboxs = CommonPL.getMapHasValues(sorts);

		// - Tuỳ chỉnh Sort Button
		sortButton = CommonPL.ButtonHasCheckboxs.createButtonHasCheckboxs(sortCheckboxs, sorts[0], Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		sortButton.setBounds(390, 45, 360, 40);

		// - Tuỳ chỉnh Begin To Create Date Label
		dateCreateLabel = CommonPL.getParagraphLabel("Ngày lập phiếu", Color.BLACK, CommonPL.getFontParagraphPlain());
		dateCreateLabel.setBounds(765, 15, 135, 24);

		// - Tuỳ chỉnh Begin To Create Date Inform Button
		dateCreateInformButton = CommonPL.getQuestionIconForm("?", "Chú thích sử dụng lọc Ngày lập phiếu",
				"Lọc những ngày có trong đoạn từ Ngày trước đến Ngày sau và chỉ cho phép nhập các định dạng như: ddmmyyyy, dd-mm-yyyy.",
				Color.BLACK, CommonPL.getFontQuestionIcon());
		dateCreateInformButton.setBounds(913, 15, 24, 24);

		// - Tuỳ chỉnh Date Create Before Date Picker
		dateCreateBeforeDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(10, CommonPL.getLocale(),
				CommonPL.getDateFormat(), "Ngày trước", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(),
				20, 40);
		dateCreateBeforeDatePicker.setBounds(765, 45, 170, 40);

		// - Tuỳ chỉnh Date Create Rectangle Label
		dateCreateRectangleLabel = CommonPL.getTitleLabel("-", Color.BLACK, CommonPL.getFontParagraphPlain(),
				SwingConstants.CENTER, SwingConstants.CENTER);
		dateCreateRectangleLabel.setBounds(935, 45, 20, 40);

		// - Tuỳ chỉnh Date Create After Date Picker
		dateCreateAfterDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(10, CommonPL.getLocale(),
				CommonPL.getDateFormat(), "Ngày sau", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(),
				20, 40);
		dateCreateAfterDatePicker.setBounds(955, 45, 170, 40);

		// - Tuỳ chỉnh Begin To Contract Date Label
		dateContractLabel = CommonPL.getParagraphLabel("Ngày hợp đồng", Color.BLACK, CommonPL.getFontParagraphPlain());
		dateContractLabel.setBounds(15, 100, 138, 24);

		// - Tuỳ chỉnh Begin To Contract Date Inform Button
		dateContractInformButton = CommonPL.getQuestionIconForm("?", "Chú thích sử dụng lọc Ngày hợp đồng",
				"Lọc những ngày có trong đoạn từ Ngày trước đến Ngày sau và chỉ cho phép nhập các định dạng như: ddmmyyyy, dd-mm-yyyy.",
				Color.BLACK, CommonPL.getFontQuestionIcon());
		dateContractInformButton.setBounds(165, 100, 24, 24);

		// - Tuỳ chỉnh Date Contract Before Date Picker
		dateContractBeforeDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(10, CommonPL.getLocale(),
				CommonPL.getDateFormat(), "Ngày trước", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(),
				20, 40);
		dateContractBeforeDatePicker.setBounds(15, 130, 170, 40);

		// - Tuỳ chỉnh Date Contract Rectangle Label
		dateContractRectangleLabel = CommonPL.getTitleLabel("-", Color.BLACK, CommonPL.getFontParagraphPlain(),
				SwingConstants.CENTER, SwingConstants.CENTER);
		dateContractRectangleLabel.setBounds(185, 130, 20, 40);

		// - Tuỳ chỉnh Date Contract After Date Picker
		dateContractAfterDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(10, CommonPL.getLocale(),
				CommonPL.getDateFormat(), "Ngày sau", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(),
				20, 40);
		dateContractAfterDatePicker.setBounds(205, 130, 170, 40);

		// - Tuỳ chỉnh Status Label
		statusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK, CommonPL.getFontParagraphPlain());
		statusLabel.setBounds(390, 100, 360, 24);

		// - Tuỳ chỉnh Status Radios
		String[] status = new String[] { "Tất cả", "Đã hoàn thành", "Chưa thanh toán", "Đang chờ xác nhận",
				"Đã huỷ phiếu" };
		statusRadios = CommonPL.getMapHasValues(status);

		// - Tuỳ chỉnh Status Button
		statusButton = CommonPL.ButtonHasRadios.createButtonHasRadios(statusRadios, status[0], Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		statusButton.setBounds(390, 130, 360, 40);

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
		filterPanel.add(dateCreateLabel);
		filterPanel.add(dateCreateInformButton);
		filterPanel.add(dateCreateBeforeDatePicker);
		filterPanel.add(dateCreateRectangleLabel);
		filterPanel.add(dateCreateAfterDatePicker);
		filterPanel.add(dateContractLabel);
		filterPanel.add(dateContractInformButton);
		filterPanel.add(dateContractBeforeDatePicker);
		filterPanel.add(dateContractRectangleLabel);
		filterPanel.add(dateContractAfterDatePicker);
		filterPanel.add(statusLabel);
		filterPanel.add(statusButton);
//		filterPanel.add(numbersOfRowLabel);
//		filterPanel.add(numbersOfRowTextField);
		filterPanel.add(filterApplyButton);
		filterPanel.add(filterResetButton);
		// <==================== ====================>

		// <===== Cấu trúc Data Panel =====>
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

		// - Tuỳ chỉnh Table Data và Table Scroll Pane
		tableData = CommonPL.createTableData(columns, widthColumns, datas, "input ticket manager");
		tableScrollPane = CommonPL.createScrollPane(true, true, tableData);
		tableScrollPane.setBounds(15, 70, 1110, 400);

		// - Tuỳ chỉnh Add Or Update Data Pane;
		dataPanel = new CommonPL.RoundedPanel(12);
		dataPanel.setLayout(null);
		dataPanel.setBackground(Color.WHITE);
		dataPanel.setBounds(30, 330, 1140, 485);
		dataPanel.add(excelButton);
		dataPanel.add(addButton);
		dataPanel.add(updateButton);
		dataPanel.add(tableScrollPane);
		// <==================== ====================>

		// Đĩnh nghĩa tính chất cho Medicine supplierr Manager PL
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
			showAddOrUpdateDialog("Thêm Phiếu nhập", "Thêm", new Vector<Object>());
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

				showAddOrUpdateDialog("Thay đổi Phiếu nhập", "Thay đổi", currentObject);
			} else {
				CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần thay đổi");
			}
			rowSelected = -1;
			valueSelected[0] = false;
			tableData.clearSelection();
		});
	}

	// - Các Component của Add Or Update Dialog
	private JLabel addOrUpdateIdLabel;
	private JButton addOrUpdateIdButton;
	private JTextField addOrUpdateIdTextField;
	private JLabel addOrUpdateDateCreateLabel;
	private JXDatePicker addOrUpdateDateCreateDatePicker;
	private JLabel addOrUpdateDateContractLabel;
	private JXDatePicker addOrUpdateDateContractDatePicker;
	private JLabel addOrUpdateIdEmployeeCreatedLabel;
	private JComboBox<String> addOrUpdateIdEmployeeCreatedComboBox;
	private JLabel addOrUpdateIdEmployeeGottenLabel;
	private JComboBox<String> addOrUpdateIdEmployeeGottenComboBox;
	private JPanel addOrUpdateLinePanel;
	private JLabel addOrUpdateDataLabel;
	private JButton addOrUpdateAddUnitButton;
	private JButton addOrUpdateDeleteUnitButton;
	private JTable addOrUpdateTableData;
	private JScrollPane addOrUpdateTableScrollPane;
	private JLabel addOrUpdateCostLabel;
	private JTextField addOrUpdateCostTextField;
	private JLabel addOrUpdateStatusLabel;
	private JTextField addOrUpdateStatusTextField;
	private JButton addOrUpdateCompleteButton;
	private JButton addOrUpdateNotPayButton;
	private JButton addOrUpdateDeleteButton;
	private JButton addOrUpdateButton;
	private JPanel addOrUpdateBlockPanel;
	private JDialog addOrUpdateDialog;
	// - Các thông tin cần có của Table Data và Table Scroll Pane
	// + Tiêu đề các cột
	private final String[] addOrUpdateColumns = { "Mã NCC", "Mã nguyên liệu", "Giá nhập", "Số lượng" };
	// + Chiều rộng các cột
	private final int[] addOrUpdateWidthColumns = { 150, 150, 400, 200 };
	// + Dữ liệu
	private Object[][] addOrUpdateDatas = { { "NCC0001", "NL00001", "200000", "12" } };
	// + Dòng hiện tại đang được chọn
	private int addOrUpdateRowSelected = -1;
	// + Giá trị (true / false) khi "Xoá" dòng dữ liệu
	private Boolean[] addOrUpdateValueSelected = { null };
	// -
	// +
	private String[] employeesArray = { "Chọn Nhân viên", "NV0001", "NV0001" };
	private Vector<String> employeesVector = CommonPL.getVectorHasValues(employeesArray);

	private JLabel addUSupllierInforLabel;
	private JComboBox<String> addUSupllierInforComboBox;
	private JLabel addUUnitInforLabel;
	private JComboBox<String> addUUnitInforComboBox;
	private JLabel addUPriceInputLabel;
	private JTextField addUPriceInputTextField;
	private JLabel addUQuantityInputLabel;
	private JTextField addUQuantityInputTextField;
	private JButton addUButton;
	private JPanel addUBlockPanel;
	private JDialog addUDialog;
	// -
	// + Dòng hiện tại đang được chọn
	private int addURowSelected = -1;
	// + Giá trị (true / false) khi "Xoá" dòng dữ liệu
	private Boolean[] addUValueSelected = { null };

	// Hàm hiện một Dialog cho phép thêm 1 nguyên liệu để nhập
	private void showAddU() {
		// <===== Các Component của Add U Block Panel =====>
		// - Tuỳ chỉnh Add U Supplier Infor Label
		addUSupllierInforLabel = CommonPL.getParagraphLabel("Nhà cung cấp", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addUSupllierInforLabel.setBounds(20, 10, 460, 40);

		// - Tuỳ chỉnh Add U Supplier Infor ComboBox
		String[] suppliersArray = { "Chọn Nhà cung cấp", "NCC0001 - Tiệm tạp hoá", "NCC0002 - Acecook" };
		Vector<String> suppliersVector = CommonPL.getVectorHasValues(suppliersArray);
		addUSupllierInforComboBox = CommonPL.CustomComboBox(suppliersVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addUSupllierInforComboBox.setBounds(20, 50, 460, 40);

		// - Tuỳ chỉnh Add U Unit Infor Label
		addUUnitInforLabel = CommonPL.getParagraphLabel("Nguyên liệu", Color.BLACK, CommonPL.getFontParagraphPlain());
		addUUnitInforLabel.setBounds(20, 100, 460, 40);

		// - Tuỳ chỉnh Add U Unit Infor ComboBox
		String[] unitsArray = { "Chọn Nguyên liệu", "NL0001 - Mì gói hảo hảo", "NL0002 - Trứng gà" };
		Vector<String> unitsVector = CommonPL.getVectorHasValues(unitsArray);
		addUUnitInforComboBox = CommonPL.CustomComboBox(unitsVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addUUnitInforComboBox.setBounds(20, 140, 460, 40);

		// - Tuỳ chỉnh Add U Price Label
		addUPriceInputLabel = CommonPL.getParagraphLabel("Giá nhập", Color.BLACK, CommonPL.getFontParagraphPlain());
		addUPriceInputLabel.setBounds(20, 190, 220, 40);

		// - Tuỳ chỉnh Add U Price Text Field
		addUPriceInputTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Giá nhập", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addUPriceInputTextField.setBounds(20, 230, 220, 40);

		// - Tuỳ chỉnh Add U Quantity Label
		addUQuantityInputLabel = CommonPL.getParagraphLabel("Số lượng", Color.BLACK, CommonPL.getFontParagraphPlain());
		addUQuantityInputLabel.setBounds(260, 190, 220, 40);

		// - Tuỳ chỉnh Add U Quantity Text Field
		addUQuantityInputTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Số lượng", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addUQuantityInputTextField.setBounds(260, 230, 220, 40);

		// - Tuỳ chỉnh Add Or Update Button
		addUButton = CommonPL.getButtonDefaultForm("Thêm", CommonPL.getFontParagraphBold());
		addUButton.setBounds(20, 300, 460, 40);
		SwingUtilities.invokeLater(() -> addUButton.requestFocusInWindow());

		// - Tuỳ chỉnh Add U Block Panel
		addUBlockPanel = new JPanel();
		addUBlockPanel.setLayout(null);
		addUBlockPanel.setBackground(Color.WHITE);
		addUBlockPanel.setBounds(0, 0, 500, 390);
		addUBlockPanel.add(addUSupllierInforLabel);
		addUBlockPanel.add(addUSupllierInforComboBox);
		addUBlockPanel.add(addUUnitInforLabel);
		addUBlockPanel.add(addUUnitInforComboBox);
		addUBlockPanel.add(addUPriceInputLabel);
		addUBlockPanel.add(addUPriceInputTextField);
		addUBlockPanel.add(addUQuantityInputLabel);
		addUBlockPanel.add(addUQuantityInputTextField);
		addUBlockPanel.add(addUButton);
		// <==================== ====================>

		// Định nghĩa tính chất cho Add U Dialog
		addUDialog = new JDialog();
		addUDialog.setTitle("Thêm Nguyên liệu");
		addUDialog.setLayout(null);
		addUDialog.setSize(500, 390);
		addUDialog.setResizable(false);
		addUDialog.setAutoRequestFocus(false);
		addUDialog.setLocationRelativeTo(null);
		addUDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			addUDialog.addWindowListener(new WindowAdapter() {
//				@Override
//				public void windowDeactivated(WindowEvent e) {
//					// Đóng Dialog khi mất focus (nhấn ngoài)
//					addUDialog.dispose();
//				}
//			});
		addUDialog.add(addUBlockPanel);
		addUDialog.setModal(true);
		addUDialog.setVisible(true);
	}

	// Hàm hiện một Dialog cho phép thêm hoặc sửa 1 phiếu nhập
	private void showAddOrUpdateDialog(String title, String button, Vector<Object> object) {
		// <===== Các Component của Add Or Update Block Panel =====>
		// - Tuỳ chỉnh Add Or Update Id Label
		addOrUpdateIdLabel = CommonPL.getParagraphLabel("Mã phiếu", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateIdLabel.setBounds(20, 10, 140, 40);

		// - Tuỳ chỉnh Add Or Update Id Button
		addOrUpdateIdButton = CommonPL.getButtonHasIcon(40, 28, 20, 20, 10, 4,
				CommonPL.getMiddlePathToShowIcon() + "rotate-icon.png", null, null, null, null, null,
				new Font("Arial", Font.BOLD, 14));
		addOrUpdateIdButton.setOpaque(true);
		addOrUpdateIdButton.setBackground(Color.BLACK);
		addOrUpdateIdButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent evt) {
				try {
					Thread.sleep(100);
					addOrUpdateIdButton.setBorder(BorderFactory.createLineBorder(Color.decode("#42A5F5"), 2));
					addOrUpdateIdButton.setBackground(Color.decode("#42A5F5"));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				addOrUpdateIdButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				addOrUpdateIdButton.setBackground(Color.BLACK);
			}
		});
		addOrUpdateIdButton.setBounds(120, 16, 40, 28);
		if (object.size() != 0)
			addOrUpdateIdButton.setVisible(false);

		// - Tuỳ chỉnh Add Or Update Id Text Field
		addOrUpdateIdTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mã", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateIdTextField.setBounds(20, 50, 140, 40);

		// - Tuỳ chỉnh Add Or Update Date Create Label
		addOrUpdateDateCreateLabel = CommonPL.getParagraphLabel("Ngày lập phiếu", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateDateCreateLabel.setBounds(180, 10, 220, 40);

		// - Tuỳ chỉnh Add Or Update Date Create Date Picker
		addOrUpdateDateCreateDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(0, CommonPL.getLocale(),
				CommonPL.getDateFormat(), "Chọn Ngày", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(),
				40, 40);
		addOrUpdateDateCreateDatePicker.setBounds(180, 50, 220, 40);

		// - Tuỳ chỉnh Add Or Update Date Contract Label
		addOrUpdateDateContractLabel = CommonPL.getParagraphLabel("Ngày hợp đồng", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateDateContractLabel.setBounds(420, 10, 220, 40);

		// - Tuỳ chỉnh Add Or Update Date Contract Date Picker
		addOrUpdateDateContractDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(0, CommonPL.getLocale(),
				CommonPL.getDateFormat(), "Chọn Ngày", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(),
				40, 40);
		addOrUpdateDateContractDatePicker.setBounds(420, 50, 220, 40);

		// - Tuỳ chỉnh Add Or Update Id Employee Created Label
		addOrUpdateIdEmployeeCreatedLabel = CommonPL.getParagraphLabel("Nhân viên lập phiếu", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateIdEmployeeCreatedLabel.setBounds(660, 10, 220, 40);

		// - Tuỳ chỉnh Add Or Update Id Employee Created ComboBox
		addOrUpdateIdEmployeeCreatedComboBox = CommonPL.CustomComboBox(employeesVector, Color.WHITE, Color.LIGHT_GRAY,
				Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateIdEmployeeCreatedComboBox.setBounds(660, 50, 220, 40);

		// - Tuỳ chỉnh Add Or Update Id Employee Gotten Label
		addOrUpdateIdEmployeeGottenLabel = CommonPL.getParagraphLabel("Nhân viên nhận hàng", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateIdEmployeeGottenLabel.setBounds(900, 10, 220, 40);

		// - Tuỳ chỉnh Add Or Update Id Employee Gotten ComboBox
		addOrUpdateIdEmployeeGottenComboBox = CommonPL.CustomComboBox(employeesVector, Color.WHITE, Color.LIGHT_GRAY,
				Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateIdEmployeeGottenComboBox.setBounds(900, 50, 220, 40);

		// // - Tuỳ chỉnh Add Or Update Line Panel
		addOrUpdateLinePanel = new CommonPL.RoundedPanel(8);
		addOrUpdateLinePanel.setBounds(20, 118, 1100, 4);
		addOrUpdateLinePanel.setBackground(Color.LIGHT_GRAY);

		// - Tuỳ chỉnh Add Or Update Data Label
		addOrUpdateDataLabel = CommonPL.getParagraphLabel("Danh sách nguyên liệu", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateDataLabel.setBounds(20, 140, 220, 40);

		// - Tuỳ chỉnh Add Or Update Add Unit Button
		addOrUpdateAddUnitButton = CommonPL.getButtonHasIcon(40, 28, 20, 20, 10, 4,
				CommonPL.getMiddlePathToShowIcon() + "plus-icon.png", null, null, null, null, null,
				new Font("Arial", Font.BOLD, 14));
		addOrUpdateAddUnitButton.setOpaque(true);
		addOrUpdateAddUnitButton.setBackground(Color.BLACK);
		addOrUpdateAddUnitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent evt) {
				try {
					Thread.sleep(100);
					addOrUpdateAddUnitButton.setBorder(BorderFactory.createLineBorder(Color.decode("#42A5F5"), 2));
					addOrUpdateAddUnitButton.setBackground(Color.decode("#42A5F5"));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				addOrUpdateAddUnitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				addOrUpdateAddUnitButton.setBackground(Color.BLACK);
			}
		});
		addOrUpdateAddUnitButton.setBounds(553, 146, 40, 28);
		addOrUpdateAddUnitButton.setVisible(true);

		// - Tuỳ chỉnh Add Or Update Delete Unit Button
		addOrUpdateDeleteUnitButton = CommonPL.getButtonHasIcon(40, 28, 20, 20, 10, 4,
				CommonPL.getMiddlePathToShowIcon() + "minus-icon.png", null, null, null, null, null,
				new Font("Arial", Font.BOLD, 14));
		addOrUpdateDeleteUnitButton.setOpaque(true);
		addOrUpdateDeleteUnitButton.setBackground(Color.BLACK);
		addOrUpdateDeleteUnitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent evt) {
				try {
					Thread.sleep(100);
					addOrUpdateDeleteUnitButton.setBorder(BorderFactory.createLineBorder(Color.decode("#42A5F5"), 2));
					addOrUpdateDeleteUnitButton.setBackground(Color.decode("#42A5F5"));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				addOrUpdateDeleteUnitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				addOrUpdateDeleteUnitButton.setBackground(Color.BLACK);
			}
		});
		addOrUpdateDeleteUnitButton.setBounds(600, 146, 40, 28);
		addOrUpdateDeleteUnitButton.setVisible(true);

		// - Tuỳ chỉnh Add Or Update Table Data và Add Or Update Table Scroll Pane
		addOrUpdateTableData = CommonPL.createTableData(addOrUpdateColumns, addOrUpdateWidthColumns, addOrUpdateDatas,
				"add or update unit table");
		addOrUpdateTableScrollPane = CommonPL.createScrollPane(true, true, addOrUpdateTableData);
		addOrUpdateTableScrollPane.setBounds(20, 180, 620, 400);

		addOrUpdateTableData.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				addURowSelected = addOrUpdateTableData.getSelectedRow();
			}
		});
		addOrUpdateAddUnitButton.addActionListener(e -> {
			showAddU();
			addURowSelected = -1;
//			valueSelected[0] = false;
			addOrUpdateTableData.clearSelection();
		});
		addOrUpdateDeleteUnitButton.addActionListener(e -> {
			if (addURowSelected != -1) {
//				Vector<Object> currentObject = new Vector<>();
//				for (int i = 0; i < widthColumns.length; i++) {
//					currentObject.add(tableData.getValueAt(rowSelected, i));
//				}
				System.out.println(123);
//				showAddU();
			} else {
				CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần xoá");
			}
			addURowSelected = -1;
//			valueSelected[0] = false;
			addOrUpdateTableData.clearSelection();
		});

		// - Tuỳ chỉnh Add Or Update Cost Label
		addOrUpdateCostLabel = CommonPL.getParagraphLabel("Tổng tiền nhập (VNĐ)", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateCostLabel.setBounds(660, 140, 460, 40);

		// - Tuỳ chỉnh Add Or Update Cost Text Field
		addOrUpdateCostTextField = new CommonPL.CustomTextField(0, 0, 0, "Đang cập nhật", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateCostTextField.setBounds(660, 180, 460, 40);
		addOrUpdateCostTextField.setEnabled(false);
		addOrUpdateCostTextField.setBackground(Color.decode("#dedede"));

		// - Tuỳ chỉnh Add Or Update Status Label
		addOrUpdateStatusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateStatusLabel.setBounds(660, 230, 460, 40);

		// - Tuỳ chỉnh Add Or Update Status Text Field
		addOrUpdateStatusTextField = new CommonPL.CustomTextField(0, 0, 0, "Đang cập nhật", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateStatusTextField.setBounds(660, 270, 460, 40);
		addOrUpdateStatusTextField.setEnabled(false);
		addOrUpdateStatusTextField.setBackground(Color.decode("#dedede"));

		// - Tuỳ chỉnh Add Or Update Complete Button
		addOrUpdateCompleteButton = CommonPL.getRoundedBorderButton(20, "Đã hoàn thành", Color.decode("#33CC00"),
				Color.WHITE, CommonPL.getFontParagraphBold());
		addOrUpdateCompleteButton.setBounds(900, 540, 220, 40);
		addOrUpdateCompleteButton.setVisible(false);

		// - Tuỳ chỉnh Add Or Update Not Pay Button
		addOrUpdateNotPayButton = CommonPL.getRoundedBorderButton(20, "Chưa thanh toán", Color.decode("#FFCC33"),
				Color.WHITE, CommonPL.getFontParagraphBold());
		addOrUpdateNotPayButton.setBounds(660, 540, 220, 40);
		addOrUpdateNotPayButton.setVisible(false);

		// - Tuỳ chỉnh Add Or Update Delete Button
		addOrUpdateDeleteButton = CommonPL.getRoundedBorderButton(20, "Đã huỷ phiếu", Color.decode("#EE0000"),
				Color.WHITE, CommonPL.getFontParagraphBold());
		addOrUpdateDeleteButton.setBounds(900, 540, 220, 40);
		addOrUpdateDeleteButton.setVisible(false);

		// - Tuỳ chỉnh Add Or Update Button
		addOrUpdateButton = CommonPL.getRoundedBorderButton(20, button,
				button == "Thêm" ? Color.decode("#699f4c") : Color.decode("#bf873e"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		addOrUpdateButton.setBounds(660, 540, 460, 40);
		SwingUtilities.invokeLater(() -> addOrUpdateButton.requestFocusInWindow());

		// Khi "Thay đổi" một Khách hàng
		if (title.equals("Thay đổi Phiếu nhập") && button.equals("Thay đổi") && object.size() != 0) {
			// - Ẩn nút thêm phiếu nhập
			addOrUpdateButton.setVisible(false);

			// - Gán dữ liệu là "Mã phiếu"
			addOrUpdateIdTextField.setText((String) object.get(0));
			addOrUpdateIdTextField.setEnabled(false);
			addOrUpdateIdTextField.setCaretPosition(0);
			((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
			addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));

			// - Gán dữ liệu là "Ngày lập phiếu"
			try {
				Date date = CommonPL.getDateFormat().parse((String) object.get(1));
				addOrUpdateDateCreateDatePicker.setDate(date);
				((JButton) addOrUpdateDateCreateDatePicker.getComponent(1))
						.setBorder(new CustomRoundedBorder(Color.BLACK, 0, 0, 0, 0));
				addOrUpdateDateCreateDatePicker.getEditor().setCaretPosition(0);
				addOrUpdateDateCreateDatePicker.getEditor().setBorder(new CustomRoundedBorder(Color.BLACK, 0, 0, 0, 0));
				addOrUpdateDateCreateDatePicker.getEditor().setForeground(Color.BLACK);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// - Gán dữ liệu là "Ngày hợp đồng"
			try {
				Date date = CommonPL.getDateFormat().parse((String) object.get(2));
				addOrUpdateDateContractDatePicker.setDate(date);
				((JButton) addOrUpdateDateContractDatePicker.getComponent(1))
						.setBorder(new CustomRoundedBorder(Color.BLACK, 0, 0, 0, 0));
				addOrUpdateDateContractDatePicker.getEditor().setCaretPosition(0);
				addOrUpdateDateContractDatePicker.getEditor()
						.setBorder(new CustomRoundedBorder(Color.BLACK, 0, 0, 0, 0));
				addOrUpdateDateContractDatePicker.getEditor().setForeground(Color.BLACK);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// - Gán dữ liệu là "Nhân viên lập phiếu"
			addOrUpdateIdEmployeeCreatedComboBox.setSelectedItem((String) object.get(3));
			((JTextField) addOrUpdateIdEmployeeCreatedComboBox.getEditor().getEditorComponent()).setCaretPosition(0);
			addOrUpdateIdEmployeeCreatedComboBox.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Nhân viên nhận hàng"
			addOrUpdateIdEmployeeGottenComboBox.setSelectedItem((String) object.get(4));
			((JTextField) addOrUpdateIdEmployeeGottenComboBox.getEditor().getEditorComponent()).setCaretPosition(0);
			addOrUpdateIdEmployeeGottenComboBox.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Tổng tiền thanh toán"
			addOrUpdateCostTextField.setText((String) object.get(5));
			addOrUpdateCostTextField.setEnabled(false);
			addOrUpdateCostTextField.setCaretPosition(0);
			((CustomTextField) addOrUpdateCostTextField).setBorderColor(Color.decode("#dedede"));
			addOrUpdateCostTextField.setBackground(Color.decode("#dedede"));

			// - Gán dữ liệu là "Trạng thái"
			addOrUpdateStatusTextField.setText((String) object.get(6));
			addOrUpdateStatusTextField.setEnabled(false);
			addOrUpdateStatusTextField.setCaretPosition(0);
			((CustomTextField) addOrUpdateStatusTextField).setBorderColor(Color.decode("#dedede"));
			addOrUpdateStatusTextField.setBackground(Color.decode("#dedede"));
			if (addOrUpdateStatusTextField.getText().equals("Đã hoàn thành")) {
				addOrUpdateAddUnitButton.setVisible(false);
				addOrUpdateDeleteUnitButton.setVisible(false);
				addOrUpdateCompleteButton.setVisible(false);
				addOrUpdateNotPayButton.setVisible(false);
				addOrUpdateDeleteButton.setVisible(false);
				addOrUpdateButton.setVisible(false);
			}
			if (addOrUpdateStatusTextField.getText().equals("Chưa thanh toán")) {
				addOrUpdateAddUnitButton.setVisible(false);
				addOrUpdateDeleteUnitButton.setVisible(false);
				addOrUpdateCompleteButton.setVisible(true);
				addOrUpdateNotPayButton.setVisible(false);
				addOrUpdateDeleteButton.setVisible(false);
				addOrUpdateButton.setVisible(false);
			}
			if (addOrUpdateStatusTextField.getText().equals("Đang chờ xác nhận")) {
				addOrUpdateAddUnitButton.setVisible(true);
				addOrUpdateDeleteUnitButton.setVisible(true);
				addOrUpdateCompleteButton.setVisible(false);
				addOrUpdateNotPayButton.setVisible(true);
				addOrUpdateDeleteButton.setVisible(true);
				addOrUpdateButton.setVisible(false);
			}
			if (addOrUpdateStatusTextField.getText().equals("Đã huỷ phiếu")) {
				addOrUpdateAddUnitButton.setVisible(false);
				addOrUpdateDeleteUnitButton.setVisible(false);
				addOrUpdateCompleteButton.setVisible(false);
				addOrUpdateNotPayButton.setVisible(false);
				addOrUpdateDeleteButton.setVisible(false);
				addOrUpdateButton.setVisible(false);
			}

			// - Gán dữ liệu là "Thời gian cập nhật gần đây"
//					addOrUpdateTimeDetailLabel.setText((String) object.get(11));
		}

		// - Tuỳ chỉnh Add Or Update Block Panel
		addOrUpdateBlockPanel = new JPanel();
		addOrUpdateBlockPanel.setLayout(null);
		addOrUpdateBlockPanel.setBounds(0, 0, 1140, 630);
		addOrUpdateBlockPanel.setBackground(Color.WHITE);
		addOrUpdateBlockPanel.add(addOrUpdateIdLabel);
		addOrUpdateBlockPanel.add(addOrUpdateIdButton);
		addOrUpdateBlockPanel.add(addOrUpdateIdTextField);
		addOrUpdateBlockPanel.add(addOrUpdateDateCreateLabel);
		addOrUpdateBlockPanel.add(addOrUpdateDateCreateDatePicker);
		addOrUpdateBlockPanel.add(addOrUpdateDateContractLabel);
		addOrUpdateBlockPanel.add(addOrUpdateDateContractDatePicker);
		addOrUpdateBlockPanel.add(addOrUpdateIdEmployeeCreatedLabel);
		addOrUpdateBlockPanel.add(addOrUpdateIdEmployeeCreatedComboBox);
		addOrUpdateBlockPanel.add(addOrUpdateIdEmployeeGottenLabel);
		addOrUpdateBlockPanel.add(addOrUpdateIdEmployeeGottenComboBox);
		addOrUpdateBlockPanel.add(addOrUpdateLinePanel);
		addOrUpdateBlockPanel.add(addOrUpdateDataLabel);
		addOrUpdateBlockPanel.add(addOrUpdateAddUnitButton);
		addOrUpdateBlockPanel.add(addOrUpdateDeleteUnitButton);
		addOrUpdateBlockPanel.add(addOrUpdateTableScrollPane);
		addOrUpdateBlockPanel.add(addOrUpdateCostLabel);
		addOrUpdateBlockPanel.add(addOrUpdateCostTextField);
		addOrUpdateBlockPanel.add(addOrUpdateStatusLabel);
		addOrUpdateBlockPanel.add(addOrUpdateStatusTextField);
		addOrUpdateBlockPanel.add(addOrUpdateCompleteButton);
		addOrUpdateBlockPanel.add(addOrUpdateNotPayButton);
		addOrUpdateBlockPanel.add(addOrUpdateDeleteButton);
		addOrUpdateBlockPanel.add(addOrUpdateButton);
		// <==================== ====================>

		// Định nghĩa tính chất cho Add Or Update Dialog
		addOrUpdateDialog = new JDialog();
		addOrUpdateDialog.setTitle(title);
		addOrUpdateDialog.setLayout(null);
		addOrUpdateDialog.setSize(1140, 630);
		addOrUpdateDialog.setResizable(false);
		addOrUpdateDialog.setLocationRelativeTo(null);
		addOrUpdateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//		addOrUpdateDialog.addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowDeactivated(WindowEvent e) {
//				// Đóng Dialog khi mất focus (nhấn ngoài)
//				addOrUpdateDialog.dispose();
//			}
//		});
		addOrUpdateDialog.add(addOrUpdateBlockPanel);
		addOrUpdateDialog.setModal(true);
		addOrUpdateDialog.setVisible(true);
	}

}
