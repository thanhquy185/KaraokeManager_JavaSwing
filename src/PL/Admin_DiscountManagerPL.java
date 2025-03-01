package PL;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import org.jdesktop.swingx.JXDatePicker;

import PL.CommonPL.CustomCornerDatePicker.CustomRoundedBorder;

public class Admin_DiscountManagerPL extends JPanel {
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
	private JTable tableData;
	private JScrollPane tableScrollPane;
	private JPanel dataPanel;
	// - Các Component của Add Dialog (dialog thêm một Khuyến mãi)
	private JLabel addOrUpdateIdLabel;
	private JButton addOrUpdateIdButton;
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
	private final String[] columns = { "Mã khuyến mãi", "Tên khuyến mãi", "Phần trăm", "Mức áp dụng", "Ngày bắt đầu",
			"Ngày kết thúc", "Trạng thái" };
	// + Chiều rộng các cột
	private final int[] widthColumns = { 150, 400, 150, 150, 150, 150, 150 };
	// + Dữ liệu
	private Object[][] datas = {
			{ "1", "Mừng buổi khai trương", "40", "200000", "2025-01-01", "2025-12-31", "Hoạt động" } };
	// + Dòng hiện tại đang được chọn
	private int rowSelected = -1;
	// + Giá trị (true / false) khi "Xoá" dòng dữ liệu
	private Boolean[] valueSelected = { null };

	public Admin_DiscountManagerPL() {
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

		// - Tuỳ chỉnh Sort Label
		sortLabel = CommonPL.getParagraphLabel("Sắp xếp", Color.BLACK, CommonPL.getFontParagraphPlain());
		sortLabel.setBounds(390, 15, 360, 24);

		// - Tuỳ chỉnh Sort Checkboxs
		String[] sorts = new String[] { "Mã khuyến mãi tăng dần", "Mã khuyến mãi giảm dần", "Tên khuyến mãi tăng dần",
				"Tên khuyến mãi giảm dần", "Phần trăm tăng dần", "Phần trăm giảm dần", "Mức áp dụng tăng dần",
				"Mức áp dụng giảm dần", "Ngày bắt đầu tăng dần", "Ngày bắt đầu giảm dần", "Ngày kết thúc tăng dần",
				"Ngày kết thúc giảm dần" };
		sortCheckboxs = CommonPL.getMapHasValues(sorts);

		// - Tuỳ chỉnh Sort Button
		sortButton = CommonPL.ButtonHasCheckboxs.createButtonHasCheckboxs(sortCheckboxs, sorts[0], Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		sortButton.setBounds(390, 45, 360, 40);

		// - Tuỳ chỉnh Status Label
		statusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK, CommonPL.getFontParagraphPlain());
		statusLabel.setBounds(765, 15, 360, 24);

		// - Tuỳ chỉnh Status Radios
		String[] status = new String[] { "Tất cả", "Hoạt động", "Tạm dừng" };
		statusRadios = CommonPL.getMapHasValues(status);

		// - Tuỳ chỉnh Status Button
		statusButton = CommonPL.ButtonHasRadios.createButtonHasRadios(statusRadios, status[0], Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		statusButton.setBounds(765, 45, 360, 40);

		// - Tuỳ chỉnh Numbers Of Row Label
		numbersOfRowLabel = CommonPL.getParagraphLabel("Số dòng:", Color.BLACK, new Font("Airal", Font.PLAIN, 14));
		numbersOfRowLabel.setBounds(765, 100, 62, 24);

		// - Tuỳ chỉnh Numbers Of Row Text Field
		numbersOfRowTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập số dòng", Color.LIGHT_GRAY, Color.BLACK,
				new Font("Airal", Font.PLAIN, 14));
		numbersOfRowTextField.setBounds(832, 100, 293, 24);

		// - Tuỳ chỉnh Filter Apply Button
		filterApplyButton = CommonPL.getLastButtonForm("Lọc", Color.decode("#007bff"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		filterApplyButton.setBounds(765, 130, 170, 40);

		// - Tuỳ chỉnh Filter Reset Button
		filterResetButton = CommonPL.getLastButtonForm("Đặt lại", Color.decode("#f44336"), Color.WHITE,
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
	}

	// Hàm hiển thị Dialog thêm một Khuyến mãi
	private void showAddOrUpdateDialog(String title, String button, Vector<Object> object) {
		// <===== Cấu trúc của Add Block Panel =====>
		// - Tuỳ chỉnh Add Or Update Id Label
		addOrUpdateIdLabel = CommonPL.getParagraphLabel("Mã khuyến mãi", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateIdLabel.setBounds(20, 10, 460, 40);

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
		addOrUpdateIdButton.setBounds(440, 16, 40, 28);
		if (object.size() != 0)
			addOrUpdateIdButton.setVisible(false);

		// - Tuỳ chỉnh Add Or Update Id Text Field
		addOrUpdateIdTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mã khuyến mãi", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
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
		addOrUpdatePercentLabel = CommonPL.getParagraphLabel("Phần trăm", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdatePercentLabel.setBounds(20, 190, 220, 40);

		// - Tuỳ chỉnh Add Or Update Percent Text Field
		addOrUpdatePercentTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Phần trăm", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdatePercentTextField.setBounds(20, 230, 220, 40);

		// - Tuỳ chỉnh Add Or Update Room Cost Label
		addOrUpdateRoomCostLabel = CommonPL.getParagraphLabel("Mức áp dụng", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateRoomCostLabel.setBounds(260, 190, 220, 40);

		// - Tuỳ chỉnh Add Or Update Room Cost Text Field
		addOrUpdateRoomCostTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mức áp dụng", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateRoomCostTextField.setBounds(260, 230, 220, 40);

		// - Tuỳ chỉnh Add Or Update Date Start Label
		addOrUpdateDateStartLabel = CommonPL.getParagraphLabel("Ngày bắt đầu", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateDateStartLabel.setBounds(20, 280, 220, 40);

		// - Tuỳ chỉnh Add Or Update Date Start Date Picker
		addOrUpdateDateStartDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(0, CommonPL.getLocale(),
				CommonPL.getDateFormat(), "Chọn Ngày.", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(),
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
		Vector<String> status = CommonPL
				.getVectorHasValues(new String[] { "Chọn Trạng thái", "Hoạt động", "Tạm dừng" });
		addOrUpdateStatusComboBox = CommonPL.CustomComboBox(status, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
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

		// - Tuỳ chỉnh Add Or Update Button
		addOrUpdateButton = CommonPL.getButtonDefaultForm(button, CommonPL.getFontParagraphBold());
		addOrUpdateButton.setBounds(20, 500, 460, 40);
		SwingUtilities.invokeLater(() -> addOrUpdateButton.requestFocusInWindow());

		// Khi "Thay đổi" một Khuyến mãi
		if (title.equals("Thay đổi Khuyến mãi") && button.equals("Thay đổi") && object.size() != 0) {
			// - Gán dữ liệu là "Mã khuyến mãi"
			addOrUpdateIdTextField.setText((String) object.get(0));
			addOrUpdateIdTextField.setEnabled(false);
			addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));

			// - Gán dữ liệu là "Tên khuyến mãi"
			addOrUpdateNameTextField.setText((String) object.get(1));
			addOrUpdateNameTextField.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Phần trăm"
			addOrUpdatePercentTextField.setText((String) object.get(2));
			addOrUpdatePercentTextField.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Mức áp dụng"
			addOrUpdateRoomCostTextField.setText((String) object.get(3));
			addOrUpdateRoomCostTextField.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Ngày bắt đầu"
			try {
				Date date = CommonPL.getDateFormat().parse((String) object.get(4));
				addOrUpdateDateStartDatePicker.setDate(date);
				((JButton) addOrUpdateDateStartDatePicker.getComponent(1))
						.setBorder(new CustomRoundedBorder(Color.BLACK, 0, 0, 0, 0));
				addOrUpdateDateStartDatePicker.getEditor().setBorder(new CustomRoundedBorder(Color.BLACK, 0, 0, 0, 0));
				addOrUpdateDateStartDatePicker.getEditor().setForeground(Color.BLACK);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// - Gán dữ liệu là "Ngày kết thúc"
			try {
				Date date = CommonPL.getDateFormat().parse((String) object.get(5));
				addOrUpdateDateEndDatePicker.setDate(date);
				((JButton) addOrUpdateDateEndDatePicker.getComponent(1))
						.setBorder(new CustomRoundedBorder(Color.BLACK, 0, 0, 0, 0));
				addOrUpdateDateEndDatePicker.getEditor().setBorder(new CustomRoundedBorder(Color.BLACK, 0, 0, 0, 0));
				addOrUpdateDateEndDatePicker.getEditor().setForeground(Color.BLACK);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// - Gán dữ liệu là "Trạng thái"
			addOrUpdateStatusComboBox.setSelectedItem((String) object.get(6));
			addOrUpdateStatusComboBox.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Thời gian cập nhật gần đây"
//							addOrUpdateTimeDetailLabel.setText((String) object.get(11));
		}

		// - Tuỳ chỉnh Add Or Update Block Panel
		addOrUpdateBlockPanel = new JPanel();
		addOrUpdateBlockPanel.setLayout(null);
		addOrUpdateBlockPanel.setBounds(0, 0, 500, 590);
		addOrUpdateBlockPanel.setBackground(Color.WHITE);
		addOrUpdateBlockPanel.add(addOrUpdateIdLabel);
		addOrUpdateBlockPanel.add(addOrUpdateIdButton);
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
		addOrUpdateBlockPanel.add(addOrUpdateTimeLabel);
		addOrUpdateBlockPanel.add(addOrUpdateTimeDetailLabel);
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

}
