package PL;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

import BLL.IngredientBLL;
import DTO.IngredientDTO;
import PL.CommonPL.CustomTextField;

public class Admin_IngredientManagerPL extends JPanel {
	// Đối tượng từ tầng Business Logic Layer
	private IngredientBLL ingredientBLL;

	// Các Component
	private JLabel titleLabel;
	// - Các Component của Filter Panel
	private JLabel findLabel;
	private JTextField findInputTextField;
	private JButton findInformButton;
	private JLabel sortsLabel;
	private Map<String, Boolean> sortsCheckboxs;
	private JButton sortsButton;
	private JLabel unitsLabel;
	private Map<String, Boolean> unitsRadios;
	private JButton unitsButton;
	private JLabel inventoryLabel;
	private Map<String, Boolean> inventoryRadios;
	private JButton inventoryButton;
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
	// - Các Component của Add Or Update Dialog
	private JLabel addOrUpdateIdLabel;
	private JTextField addOrUpdateIdTextField;
	private JLabel addOrUpdateNameLabel;
	private JTextField addOrUpdateNameTextField;
	private JLabel addOrUpdateUnitLabel;
	private JComboBox<String> addOrUpdateUnitComboBox;
	private JLabel addOrUpdateInventoryLabel;
	private JTextField addOrUpdateInventoryTextField;
	private JLabel addOrUpdateStatusLabel;
	private JComboBox<String> addOrUpdateStatusComboBox;
	private JLabel addOrUpdateTimeLabel;
	private JLabel addOrUpdateTimeDetailLabel;
	private JButton addOrUpdateButton;
	private JPanel addOrUpdateBlockPanel;
	private JDialog addOrUpdateDialog;
	// - Các thông tin cần có của Table Data và Table Scroll Pane
	private final String[] columns = { "Mã nguyên liệu", "Tên nguyên liệu", "Đơn vị", "Tồn kho", "Trạng thái" };
	private final int[] widthColumns = { 150, 510, 150, 150, 150 };
	private Object[][] datas = {};
	private int rowSelected = -1;
	private Boolean[] valueSelected = { null };
	// - Các thông tin cần thiết cho lọc và sắp xếp
	private final String[] sortsString = { "Mã nguyên liệu tăng dần", "Mã nguyên liệu giảm dần",
			"Tên nguyên liệu tăng dần", "Tên nguyên liệu giảm dần" };
	private final String[] sortsSQL = { "maNguyenLieu ASC", "maNguyenLieu DESC", "tenNguyenLieu ASC",
			"tenNguyenLieu DESC" };
	private final String[] unitsStringForFilter = { "Tất cả", "Gói", "Quả", "Hộp", "Thẻ", "Chai", "Lon", "Bó" };
	private final String[] unitsStringForAddOrUpdate = { "Chọn Đơn vị", "Gói", "Quả", "Hộp", "Thẻ", "Chai", "Lon",
			"Bó" };
	private final String[] unitsSQL = { "", "donVi = 'Gói'", "donVi = 'Quả'", "donVi = 'Hộp'", "donVi = 'Thẻ'",
			"donVi = 'Chai'", "donVi = 'Lon'", "donVi = 'Bó'" };
	private final String[] inventoryString = { "Tất cả", "Còn hàng", "Hết hàng" };
	private final String[] inventorySQL = { "", "tonKho > 0", "tonKho = 0" };
	private final String[] statusStringForFilter = { "Tất cả", "Hoạt động", "Tạm dừng" };
	private final String[] statusStringForAddOrUpdate = { "Chọn Trạng thái", "Hoạt động", "Tạm dừng" };
	private final String[] statusSQL = { "", "trangThai = 1", "trangThai = 0" };

	public Admin_IngredientManagerPL() {
		// Khởi tạo đối tượng BLL
		ingredientBLL = new IngredientBLL();

		// <===== Cấu trúc Title Label =====>
		titleLabel = CommonPL.getTitleLabel("Nguyên liệu", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER,
				SwingConstants.CENTER);
		titleLabel.setBounds(30, 0, 1140, 115);

		// <===== Cấu trúc Filter Panel =====>
		findLabel = CommonPL.getParagraphLabel("Tìm kiếm", Color.BLACK, CommonPL.getFontParagraphPlain());
		findLabel.setBounds(15, 15, 90, 24);

		findInformButton = CommonPL.getQuestionIconForm("?", "Thông tin bạn có thể tìm kiếm",
				"Bạn có thể tìm kiếm bằng các thông tin như: mã Nguyên liệu, tên Nguyên liệu", Color.BLACK,
				CommonPL.getFontQuestionIcon());
		findInformButton.setBounds(110, 15, 24, 24);

		findInputTextField = new CommonPL.CustomTextField(0, 20, 20, "Nhập thông tin", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		findInputTextField.setBounds(15, 45, 360, 40);

		sortsLabel = CommonPL.getParagraphLabel("Sắp xếp", Color.BLACK, CommonPL.getFontParagraphPlain());
		sortsLabel.setBounds(390, 15, 360, 24);

		sortsCheckboxs = CommonPL.getMapHasValues(sortsString);
		sortsButton = CommonPL.ButtonHasCheckboxs.createButtonHasCheckboxs(sortsCheckboxs, sortsString[0],
				Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		sortsButton.setBounds(390, 45, 360, 40);

		unitsLabel = CommonPL.getParagraphLabel("Đơn vị", Color.BLACK, CommonPL.getFontParagraphPlain());
		unitsLabel.setBounds(765, 15, 360, 24);

		unitsRadios = CommonPL.getMapHasValues(unitsStringForFilter);
		unitsButton = CommonPL.ButtonHasRadios.createButtonHasRadios(unitsRadios, unitsStringForFilter[0],
				Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		unitsButton.setBounds(765, 45, 360, 40);

		inventoryLabel = CommonPL.getParagraphLabel("Tồn kho", Color.BLACK, CommonPL.getFontParagraphPlain());
		inventoryLabel.setBounds(15, 100, 360, 24);

		inventoryRadios = CommonPL.getMapHasValues(inventoryString);
		inventoryButton = CommonPL.ButtonHasRadios.createButtonHasRadios(inventoryRadios, inventoryString[0],
				Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		inventoryButton.setBounds(15, 130, 360, 40);

		statusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK, CommonPL.getFontParagraphPlain());
		statusLabel.setBounds(390, 100, 360, 24);

		statusRadios = CommonPL.getMapHasValues(statusStringForFilter);
		statusButton = CommonPL.ButtonHasRadios.createButtonHasRadios(statusRadios, statusStringForFilter[0],
				Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		statusButton.setBounds(390, 130, 360, 40);

		filterApplyButton = CommonPL.getRoundedBorderButton(20, "Lọc", Color.decode("#007bff"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		filterApplyButton.setBounds(765, 130, 170, 40);

		filterResetButton = CommonPL.getRoundedBorderButton(20, "Đặt lại", Color.decode("#f44336"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		filterResetButton.setBounds(955, 130, 170, 40);

		filterPanel = new CommonPL.RoundedPanel(12);
		filterPanel.setLayout(null);
		filterPanel.setBounds(30, 115, 1140, 185);
		filterPanel.setBackground(Color.WHITE);
		filterPanel.add(findLabel);
		filterPanel.add(findInformButton);
		filterPanel.add(findInputTextField);
		filterPanel.add(sortsLabel);
		filterPanel.add(sortsButton);
		filterPanel.add(unitsLabel);
		filterPanel.add(unitsButton);
		filterPanel.add(inventoryLabel);
		filterPanel.add(inventoryButton);
		filterPanel.add(statusLabel);
		filterPanel.add(statusButton);
		filterPanel.add(filterApplyButton);
		filterPanel.add(filterResetButton);

		// <===== Cấu trúc Data Panel =====>
		addButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
				CommonPL.getMiddlePathToShowIcon() + "add-icon.png", "Thêm", Color.BLACK, Color.decode("#699f4c"),
				Color.BLACK, Color.decode("#699f4c"), CommonPL.getFontParagraphBold());
		addButton.setBounds(15, 15, 210, 40);

		updateButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
				CommonPL.getMiddlePathToShowIcon() + "update-icon.png", "Thay đổi", Color.BLACK,
				Color.decode("#bf873e"), Color.BLACK, Color.decode("#bf873e"), CommonPL.getFontParagraphBold());
		updateButton.setBounds(240, 15, 210, 40);

		lockButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
				CommonPL.getMiddlePathToShowIcon() + "lock-icon.png", "Khoá", Color.BLACK, Color.decode("#9f4d4d"),
				Color.BLACK, Color.decode("#9f4d4d"), CommonPL.getFontParagraphBold());
		lockButton.setBounds(465, 15, 210, 40);

		tableData = CommonPL.createTableData(columns, widthColumns, datas, "ingredient manager");
		tableScrollPane = CommonPL.createScrollPane(true, true, tableData);
		tableScrollPane.setBounds(15, 70, 1110, 400);

		dataPanel = new CommonPL.RoundedPanel(12);
		dataPanel.setLayout(null);
		dataPanel.setBackground(Color.WHITE);
		dataPanel.setBounds(30, 330, 1140, 485);
		dataPanel.add(addButton);
		dataPanel.add(updateButton);
		dataPanel.add(lockButton);
		dataPanel.add(tableScrollPane);

		// Định nghĩa tính chất cho PL
		this.setSize(CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
		this.setLayout(null);
		this.add(titleLabel);
		this.add(filterPanel);
		this.add(dataPanel);

		// Sự kiện chọn dòng trong bảng
		tableData.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				rowSelected = tableData.getSelectedRow();
			}
		});

		// Sự kiện nút "Thêm"
		addButton.addActionListener(e -> {
			showAddOrUpdateDialog("Thêm Nguyên liệu", "Thêm", new Vector<>());
			rowSelected = -1;
			valueSelected[0] = false;
			tableData.clearSelection();
		});

		// Sự kiện nút "Thay đổi"
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
			rowSelected = -1;
			valueSelected[0] = false;
			tableData.clearSelection();
		});

		// Sự kiện nút "Khóa"
		lockButton.addActionListener(e -> {
			if (rowSelected != -1) {
				Vector<Object> currentObject = new Vector<>();
				for (int i = 0; i < widthColumns.length; i++) {
					currentObject.add(tableData.getValueAt(rowSelected, i));
				}
				CommonPL.createSelectionsDialog("Thông báo lựa chọn",
						String.format("Có chắc chắn muốn %s nguyên liệu này?",
								currentObject.get(4).equals("Hoạt động") ? "khóa" : "mở khóa"),
						valueSelected);

				if (valueSelected[0]) {
					String result = ingredientBLL.lockIngredient((String) currentObject.get(0),
							CommonPL.getCurrentDate());
					if (result.equals("Có thể thay đổi trạng thái nguyên liệu")) {
						CommonPL.createSuccessDialog("Thông báo thành công",
								currentObject.get(4).equals("Hoạt động") ? "Khóa thành công" : "Mở khóa thành công");
						renderTableData(null, null, null);
					} else {
						CommonPL.createErrorDialog("Thông báo lỗi", result);
					}
				}
			} else {
				CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần khóa");
			}
			rowSelected = -1;
			valueSelected[0] = false;
			tableData.clearSelection();
		});

		// Thiết lập sự kiện lọc dữ liệu
		filterDatasInTable();

		// Hiển thị dữ liệu ban đầu
		renderTableData(null, null, null);
	}

	// Hàm cập nhật dữ liệu cho bảng từ BLL
	private void renderTableData(String[] join, String condition, String order) {
		ArrayList<IngredientDTO> ingredientList = ingredientBLL.getAllIngredientsByCondition(join, condition, order);
		Object[][] datasQuery = new Object[ingredientList.size()][columns.length];
		for (int i = 0; i < ingredientList.size(); i++) {
			datasQuery[i][0] = ingredientList.get(i).getId();
			datasQuery[i][1] = ingredientList.get(i).getName();
			datasQuery[i][2] = ingredientList.get(i).getUnit();
			datasQuery[i][3] = ingredientList.get(i).getInventory();
			datasQuery[i][4] = ingredientList.get(i).getStatus() ? "Hoạt động" : "Tạm dừng";
		}
		datas = datasQuery;
		CommonPL.updateRowsInTableData(tableData, datas);
	}

	// Hàm xử lý sự kiện lọc dữ liệu
	private void filterDatasInTable() {
		filterApplyButton.addActionListener(e -> {
			String findValue = !findInputTextField.getText().equals("Nhập thông tin") ? findInputTextField.getText()
					: null;
			String sortsValue = CommonPL.getSQLFromCheckboxs(sortsCheckboxs, sortsSQL);
			String unitsValue = CommonPL.getSQLFromRadios(unitsRadios, unitsSQL);
			String inventoryValue = CommonPL.getSQLFromRadios(inventoryRadios, inventorySQL);
			String statusValue = CommonPL.getSQLFromRadios(statusRadios, statusSQL);

			String condition = (findValue != null
					? String.format("(maNguyenLieu LIKE '%%%s%%' OR tenNguyenLieu LIKE '%%%s%%')", findValue, findValue)
					: "")
					+ (unitsValue != null ? (findValue != null ? " AND " + unitsValue : unitsValue) : "")
					+ (inventoryValue != null
							? (findValue != null || unitsValue != null ? " AND " + inventoryValue : inventoryValue)
							: "")
					+ (statusValue != null
							? (findValue != null || unitsValue != null || inventoryValue != null ? " AND " + statusValue
									: statusValue)
							: "");
			if (condition.length() == 0)
				condition = null;
			String orderStr = sortsValue;

			renderTableData(null, condition, orderStr);
		});

		filterResetButton.addActionListener(e -> {
			findInputTextField.setText("Nhập thông tin");
			findInputTextField.setForeground(Color.LIGHT_GRAY);
			CommonPL.resetMapForFilter(sortsCheckboxs, sortsString, sortsButton);
			CommonPL.resetMapForFilter(unitsRadios, unitsStringForFilter, unitsButton);
			CommonPL.resetMapForFilter(inventoryRadios, inventoryString, inventoryButton);
			CommonPL.resetMapForFilter(statusRadios, statusStringForFilter, statusButton);
			renderTableData(null, null, null);
		});
	}

	// Hàm hiển thị Dialog thêm hoặc cập nhật nguyên liệu
	private void showAddOrUpdateDialog(String title, String button, Vector<Object> object) {
        // <===== Cấu trúc Add Or Update Block Panel =====>
        addOrUpdateIdLabel = CommonPL.getParagraphLabel(
                "<html>Mã nguyên liệu <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateIdLabel.setBounds(20, 10, 460, 40);

        addOrUpdateIdTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mã nguyên liệu", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateIdTextField.setEnabled(false);
        ((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
        addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));
        addOrUpdateIdTextField.setBounds(20, 50, 460, 40);

        addOrUpdateNameLabel = CommonPL.getParagraphLabel(
                "<html>Tên nguyên liệu <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateNameLabel.setBounds(20, 100, 460, 40);

        addOrUpdateNameTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Tên nguyên liệu", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateNameTextField.setBounds(20, 140, 460, 40);

        addOrUpdateUnitLabel = CommonPL.getParagraphLabel(
                "<html>Đơn vị <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateUnitLabel.setBounds(20, 190, 460, 40);

        Vector<String> unitsVector = CommonPL.getVectorHasValues(unitsStringForAddOrUpdate);
        addOrUpdateUnitComboBox = CommonPL.CustomComboBox(unitsVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateUnitComboBox.setBounds(20, 230, 460, 40);

        addOrUpdateInventoryLabel = CommonPL.getParagraphLabel("Tồn kho", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateInventoryLabel.setBounds(20, 280, 460, 40);

        addOrUpdateInventoryTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập tồn kho", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateInventoryTextField.setEnabled(false);
        ((CustomTextField) addOrUpdateInventoryTextField).setBorderColor(Color.decode("#dedede"));
        addOrUpdateInventoryTextField.setBackground(Color.decode("#dedede"));
        addOrUpdateInventoryTextField.setBounds(20, 320, 460, 40);

        addOrUpdateStatusLabel = CommonPL.getParagraphLabel(
                "<html>Trạng thái <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateStatusLabel.setBounds(20, 370, 460, 40);

        Vector<String> statusVector = CommonPL.getVectorHasValues(statusStringForAddOrUpdate);
        addOrUpdateStatusComboBox = CommonPL.CustomComboBox(statusVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateStatusComboBox.setBounds(20, 410, 460, 40);

        addOrUpdateTimeLabel = CommonPL.getParagraphLabel("Cập nhật gần đây:", Color.GRAY, new Font("Arial", Font.ITALIC, 18));
        addOrUpdateTimeLabel.setBounds(20, 460, 148, 40);

        addOrUpdateTimeDetailLabel = CommonPL.getTitleLabel("yyyy-MM-dd HH:mm:ss", Color.GRAY, new Font("Arial", Font.ITALIC, 18), SwingConstants.RIGHT, SwingConstants.CENTER);
        addOrUpdateTimeDetailLabel.setBounds(173, 460, 307, 40);
        Timer timer = new Timer(1000, e -> {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            addOrUpdateTimeDetailLabel.setText(formattedDateTime);
        });
        timer.start();

        addOrUpdateButton = CommonPL.getRoundedBorderButton(20, button, button.equals("Thêm") ? Color.decode("#699f4c") : Color.decode("#bf873e"), Color.WHITE, CommonPL.getFontParagraphBold());
        addOrUpdateButton.setBounds(20, 500, 460, 40);
        SwingUtilities.invokeLater(() -> addOrUpdateButton.requestFocusInWindow());

        // Khi "Thêm" nguyên liệu
        if (title.equals("Thêm Nguyên liệu") && button.equals("Thêm") && object.isEmpty()) {
            String id = "NL" + String.format("%05d", Integer.parseInt(ingredientBLL.getLastIngredient().getId().substring(2)) + 1);
            addOrUpdateIdTextField.setText(id);
            ((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
            
            addOrUpdateInventoryTextField.setText("0");
            ((CustomTextField) addOrUpdateInventoryTextField).setBorderColor(Color.decode("#dedede"));
        }

        // Khi "Thay đổi" nguyên liệu
        if (title.equals("Thay đổi Nguyên liệu") && button.equals("Thay đổi") && !object.isEmpty()) {
        	if(object.get(0) != null) {
        		addOrUpdateIdTextField.setText((String) object.get(0));
        		((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
        		addOrUpdateIdTextField.setCaretPosition(0);        		
        	}
            
        	if(object.get(1) != null) {
	            addOrUpdateNameTextField.setText((String) object.get(1));
	            addOrUpdateNameTextField.setForeground(Color.BLACK);
	            addOrUpdateNameTextField.setCaretPosition(0);
        	}
            
            if(object.get(2) != null) {
	            addOrUpdateUnitComboBox.setSelectedItem((String) object.get(2));
	            addOrUpdateUnitComboBox.setForeground(Color.BLACK);
				((JTextField) addOrUpdateUnitComboBox.getEditor().getEditorComponent()).setCaretPosition(0);
            }

			if(object.get(3) != null) { 			
				addOrUpdateInventoryTextField.setText(String.valueOf(object.get(3)));
				addOrUpdateInventoryTextField.setForeground(Color.BLACK);
				((CustomTextField) addOrUpdateInventoryTextField).setBorderColor(Color.decode("#dedede"));
				addOrUpdateInventoryTextField.setCaretPosition(0);
			}
            
            addOrUpdateStatusComboBox.setSelectedItem((String) object.get(4));
            addOrUpdateStatusComboBox.setEnabled(false);
            UIManager.put("ComboBox.disabledBackground", Color.decode("#dedede"));
            addOrUpdateStatusComboBox.setBorder(BorderFactory.createLineBorder(Color.decode("#dedede"), 1));
			((JTextField) addOrUpdateStatusComboBox.getEditor().getEditorComponent()).setCaretPosition(0);

        }

        // Sự kiện nút "Thêm" hoặc "Thay đổi"
        addOrUpdateButton.addActionListener(e -> {
            String id = addOrUpdateIdTextField.getText();
            String name = !addOrUpdateNameTextField.getText().equals("Nhập Tên nguyên liệu") ? addOrUpdateNameTextField.getText() : null;
            String unit = !addOrUpdateUnitComboBox.getSelectedItem().equals("Chọn Đơn vị") ? (String) addOrUpdateUnitComboBox.getSelectedItem() : null;
            String inventoryStr = !addOrUpdateInventoryTextField.getText().equals("Nhập tồn kho") ? addOrUpdateInventoryTextField.getText() : "0";
            String status = !addOrUpdateStatusComboBox.getSelectedItem().equals("Chọn Trạng thái") ? (String) addOrUpdateStatusComboBox.getSelectedItem() : null;
            String dateUpdate = CommonPL.getCurrentDate();

            if (title.equals("Thêm Nguyên liệu")) {
                String result = ingredientBLL.insertIngredient(id, name, unit, inventoryStr, status, dateUpdate);
                if (result.equals("Có thể thêm một nguyên liệu")) {
                    CommonPL.createSuccessDialog("Thông báo thành công", "Thêm thành công");
                    addOrUpdateDialog.dispose();
                    renderTableData(null, null, null);
                } else {
                    CommonPL.createErrorDialog("Thông báo lỗi", result);
                }
            } else if (title.equals("Thay đổi Nguyên liệu")) {
                String result = ingredientBLL.updateIngredient(id, name, unit, inventoryStr, status, dateUpdate);
                if (result.equals("Có thể thay đổi một nguyên liệu")) {
                    CommonPL.createSuccessDialog("Thông báo thành công", "Thay đổi thành công");
                    addOrUpdateDialog.dispose();
                    renderTableData(null, null, null);
                } else {
                    CommonPL.createErrorDialog("Thông báo lỗi", result);
                }
            }
        });

        // Cấu hình Add Or Update Block Panel
        addOrUpdateBlockPanel = new JPanel();
        addOrUpdateBlockPanel.setLayout(null);
        addOrUpdateBlockPanel.setBounds(0, 0, 500, 590);
        addOrUpdateBlockPanel.setBackground(Color.WHITE);
        addOrUpdateBlockPanel.add(addOrUpdateIdLabel);
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

        // Định nghĩa tính chất cho Dialog
        addOrUpdateDialog = new JDialog();
        addOrUpdateDialog.setTitle(title);
        addOrUpdateDialog.setLayout(null);
        addOrUpdateDialog.setSize(500, 590);
        addOrUpdateDialog.setResizable(false);
        addOrUpdateDialog.setLocationRelativeTo(null);
        addOrUpdateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        addOrUpdateDialog.add(addOrUpdateBlockPanel);
        addOrUpdateDialog.setModal(true);
        addOrUpdateDialog.setVisible(true);
    }
}