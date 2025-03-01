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

public class Admin_IngredientManagerPL extends JPanel {
	// Các Component
	private JLabel titleLabel;
	// - Các Component của Filter Panel
	private JLabel findLabel;
	private JTextField findInputTextField;
	private JButton findInformButton;
	private JLabel sortLabel;
	private Map<String, Boolean> sortCheckboxs;
	private JButton sortButton;
	private JLabel unitLabel;
	private Map<String, Boolean> unitCheckboxs;
	private JButton unitButton;
	private JLabel inventoryLabel;
	private Map<String, Boolean> inventoryCheckboxs;
	private JButton inventoryButton;
	private JLabel statusLabel;
	private Map<String, Boolean> statusCheckboxs;
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
	// - Các Component của Add Or Update Dialog
	private JLabel addOrUpdateIdLabel;
	private JButton addOrUpdateIdButton;
	private JTextField addOrUpdateIdTextField;
	private JLabel addOrUpdateNameLabel;
	private JTextField addOrUpdateNameTextField;
	private JLabel addOrUpdateUnitLabel;
	private JComboBox<String> addOrUpdateUnitComboBox;
	private JLabel addOrUpdateStatusLabel;
	private JComboBox<String> addOrUpdateStatusComboBox;
	private JLabel addOrUpdateTimeLabel;
	private JLabel addOrUpdateTimeDetailLabel;
	private JButton addOrUpdateButton;
	private JPanel addOrUpdateBlockPanel;
	private JDialog addOrUpdateDialog;
	// - Các thông tin cần có của Table Data và Table Scroll Pane
	// + Tiêu đề các cột
	private final String[] columns = { "Mã nguyên liệu", "Tên nguyên liệu", "Đơn vị", "Tồn kho", "Trạng thái" };
	// + Chiều rộng các cột
	private final int[] widthColumns = { 150, 510, 150, 150, 150 };
	// + Dữ liệu
	private Object[][] datas = { { "NL0001", "Trứng gà", "Quả", "0", "Hoạt động" },
			{ "NL0002", "Mì gói Hảo Hảo", "Gói", "10", "Hoạt động" } };

	// + Dòng hiện tại đang được chọn
	private int rowSelected = -1;
	// + Giá trị (true / false) khi "Xoá" dòng dữ liệu
	private Boolean[] valueSelected = { null };

	public Admin_IngredientManagerPL() {
		// <===== Cấu trúc Title Label =====>
		// - Tuỳ chỉnh Title Label
		titleLabel = CommonPL.getTitleLabel("Nguyên liệu", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER,
				SwingConstants.CENTER);
		titleLabel.setBounds(30, 0, 1110, 115);
		// <==================== ====================>

		// <===== Cấu trúc Filter Panel =====>
		// - Tuỳ chỉnh Find Input Label
		findLabel = CommonPL.getParagraphLabel("Tìm kiếm", Color.BLACK, CommonPL.getFontParagraphPlain());
		findLabel.setBounds(15, 15, 90, 24);

		// - Tuỳ chỉnh Find Input Inform Button
		findInformButton = CommonPL.getQuestionIconForm("?", "Thông tin bạn có thể tìm kiếm",
				"Bạn có thể tìm kiếm bằng các thông tin như: mã Nguyên liệu, tên Nguyên liệu", Color.BLACK,
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
		String[] sorts = new String[] { "Mã Nguyên liệu tăng dần", "Mã Nguyên liệu giảm dần",
				"Tên Nguyên liệu tăng dần", "Tên Nguyên liệu giảm dần" };
		sortCheckboxs = CommonPL.getMapHasValues(sorts);

		// - Tuỳ chỉnh Sort Button
		sortButton = CommonPL.ButtonHasCheckboxs.createButtonHasCheckboxs(sortCheckboxs, sorts[0], Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		sortButton.setBounds(390, 45, 360, 40);

		// - Tuỳ chỉnh Unit Label
		unitLabel = CommonPL.getParagraphLabel("Đơn vị", Color.BLACK, CommonPL.getFontParagraphPlain());
		unitLabel.setBounds(765, 15, 360, 24);

		// - Tuỳ chỉnh Unit Checkboxs
		String[] units = new String[] { "Tất cả", "Gói", "Quả", "Hộp", "Thẻ", "Chai", "Lon", "Bó" };
		unitCheckboxs = CommonPL.getMapHasValues(units);

		// - Tuỳ chỉnh Unit Button
		unitButton = CommonPL.ButtonHasRadios.createButtonHasRadios(unitCheckboxs, units[0], Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		unitButton.setBounds(765, 45, 360, 40);

		// - Tuỳ chỉnh Inventory Label
		inventoryLabel = CommonPL.getParagraphLabel("Tồn kho", Color.BLACK, CommonPL.getFontParagraphPlain());
		inventoryLabel.setBounds(15, 100, 360, 24);

		// - Tuỳ chỉnh Inventory Checkboxs
		String[] inventorys = new String[] { "Tất cả", "Còn hàng", "Hết hàng" };
		inventoryCheckboxs = CommonPL.getMapHasValues(inventorys);

		// - Tuỳ chỉnh Inventory Button
		inventoryButton = CommonPL.ButtonHasRadios.createButtonHasRadios(inventoryCheckboxs, inventorys[0],
				Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		inventoryButton.setBounds(15, 130, 360, 40);

		// - Tuỳ chỉnh Status Label
		statusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK, CommonPL.getFontParagraphPlain());
		statusLabel.setBounds(390, 100, 360, 24);

		// - Tuỳ chỉnh Status Checkboxs
		String[] status = new String[] { "Tất cả", "Hoạt động", "Tạm dừng" };
		statusCheckboxs = CommonPL.getMapHasValues(status);

		// - Tuỳ chỉnh Status Button
		statusButton = CommonPL.ButtonHasRadios.createButtonHasRadios(statusCheckboxs, status[0], Color.LIGHT_GRAY,
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
		filterApplyButton = CommonPL.getLastButtonForm("Lọc", Color.decode("#007bff"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		filterApplyButton.setBounds(765, 130, 170, 40);

		// - Tuỳ chỉnh Filter Reset Button
		filterResetButton = CommonPL.getLastButtonForm("Đặt lại", Color.decode("#f44336"), Color.WHITE,
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
		filterPanel.add(inventoryLabel);
		filterPanel.add(inventoryButton);
		filterPanel.add(unitLabel);
		filterPanel.add(unitButton);
//		filterPanel.add(numbersOfRowLabel);
//		filterPanel.add(numbersOfRowTextField);
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

		// - Tuỳ chỉnh Table Data và Table Scroll Pane
		tableData = CommonPL.createTableData(columns, widthColumns, datas, "medicine supplier manager");
		tableScrollPane = CommonPL.createScrollPane(true, false, tableData);
		tableScrollPane.setBounds(15, 70, 1110, 400);

		// - Tuỳ chỉnh Data Pane;
		dataPanel = new CommonPL.RoundedPanel(12);
		dataPanel.setLayout(null);
		dataPanel.setBackground(Color.WHITE);
		dataPanel.setBounds(30, 330, 1140, 485);
		dataPanel.add(addButton);
		dataPanel.add(updateButton);
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
			showAddOrUpdateDialog("Thêm Nguyên liệu", "Thêm", new Vector<Object>());

			valueSelected[0] = null;
			rowSelected = -1;
			tableData.clearSelection();
		});
		updateButton.addActionListener(e -> {
			if (rowSelected != -1) {
				Vector<Object> currentObject = new Vector<>();
				for (int i = 0; i < widthColumns.length; i++) {
					currentObject.add(tableData.getValueAt(rowSelected, i));
				}

				showAddOrUpdateDialog("Thay đổi Nguyên liệu", "Thay đổi", currentObject);
			} else {
				CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần thay đổi");
			}

			valueSelected[0] = null;
			rowSelected = -1;
			tableData.clearSelection();
		});
	}

	private JLabel addOrUpdateInventoryLabel;
	private JTextField addOrUpdateInventoryTextField;

	// Hàm tạo Dialog cho phép thêm hoặc sửa một Nguyên liệu
	private void showAddOrUpdateDialog(String title, String button, Vector<Object> object) {
		// <===== Cấu trúc Add Or Update Block Panel =====>
		// - Tuỳ chỉnh Add Or Update Id Label
		addOrUpdateIdLabel = CommonPL.getParagraphLabel("Mã nguyên liệu", Color.BLACK,
				CommonPL.getFontParagraphPlain());
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
		addOrUpdateIdTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mã nguyên liệu", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateIdTextField.setBounds(20, 50, 460, 40);

		// - Tuỳ chỉnh Add Or Update Name Label
		addOrUpdateNameLabel = CommonPL.getParagraphLabel("Tên nguyên liệu", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateNameLabel.setBounds(20, 100, 460, 40);

		// - Tuỳ chỉnh Add Or Update Name Text Field
		addOrUpdateNameTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Tên nguyên liệu", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateNameTextField.setBounds(20, 140, 460, 40);

		// - Tuỳ chỉnh Add Or Update Unit Label
		addOrUpdateUnitLabel = CommonPL.getParagraphLabel("Đơn vị", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateUnitLabel.setBounds(20, 190, 460, 40);

		// - Tuỳ chỉnh Add Or Update Unit Text Field
		String[] units = new String[] { "Chọn Đơn vị", "Gói", "Quả", "Hộp", "Thẻ", "Chai", "Lon", "Bó" };
		Vector<String> unitsVector = CommonPL.getVectorHasValues(units);
		addOrUpdateUnitComboBox = CommonPL.CustomComboBox(unitsVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateUnitComboBox.setBounds(20, 230, 460, 40);

		// - Tuỳ chỉnh Add Or Update Inventory Label
		addOrUpdateInventoryLabel = CommonPL.getParagraphLabel("Tồn kho", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateInventoryLabel.setBounds(20, 280, 460, 40);

		// - Tuỳ chỉnh Add Or Update Inventory Text Field
		addOrUpdateInventoryTextField = new CommonPL.CustomTextField(0, 0, 0, "Đang cập nhật", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateInventoryTextField.setBounds(20, 320, 460, 40);
		addOrUpdateInventoryTextField.setEnabled(false);
		addOrUpdateInventoryTextField.setBackground(Color.decode("#dedede"));

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

		// Khi "Thay đổi" một Nguyên liệu
		if (title.equals("Thay đổi Nguyên liệu") && button.equals("Thay đổi") && object.size() != 0) {
			// - Gán dữ liệu là "Mã nguyên liệu"
			addOrUpdateIdTextField.setText((String) object.get(0));
			addOrUpdateIdTextField.setEnabled(false);
			addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));

			// - Gán dữ liệu là "Tên nguyên liệu"
			addOrUpdateNameTextField.setText((String) object.get(1));
			addOrUpdateNameTextField.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Đơn vị"
			addOrUpdateUnitComboBox.setSelectedItem((String) object.get(2));
			addOrUpdateUnitComboBox.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Tồn kho"
			addOrUpdateInventoryTextField.setText((String) object.get(3));
			addOrUpdateInventoryTextField.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Trạng thái"
			addOrUpdateStatusComboBox.setSelectedItem((String) object.get(4));
			addOrUpdateStatusComboBox.setForeground(Color.BLACK);

			// - Gán dữ liệu là "Thời gian cập nhật gần đây"
//					addOrUpdateTimeDetailLabel.setText((String) object.get(11));
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
		addOrUpdateBlockPanel.add(addOrUpdateUnitLabel);
		addOrUpdateBlockPanel.add(addOrUpdateUnitComboBox);
		addOrUpdateBlockPanel.add(addOrUpdateInventoryLabel);
		addOrUpdateBlockPanel.add(addOrUpdateInventoryTextField);
		addOrUpdateBlockPanel.add(addOrUpdateStatusLabel);
		addOrUpdateBlockPanel.add(addOrUpdateStatusComboBox);
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
