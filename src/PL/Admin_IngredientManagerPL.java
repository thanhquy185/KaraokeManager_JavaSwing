package PL;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private final String[] sortsString = { "Mã nguyên liệu tăng dần", "Mã nguyên liệu giảm dần", "Tên nguyên liệu tăng dần", "Tên nguyên liệu giảm dần" };
    private final String[] sortsSQL = { "maNguyenLieu ASC", "maNguyenLieu DESC", "tenNguyenLieu ASC", "tenNguyenLieu DESC" };
    private final String[] unitsString = { "Tất cả", "Gói", "Quả", "Hộp", "Thẻ", "Chai", "Lon", "Bó" };
    private final String[] unitsSQL = { "", "donVi = 'Gói'", "donVi = 'Quả'", "donVi = 'Hộp'", "donVi = 'Thẻ'", "donVi = 'Chai'", "donVi = 'Lon'", "donVi = 'Bó'" };
    private final String[] inventoryString = { "Tất cả", "Còn hàng", "Hết hàng" };
    private final String[] inventorySQL = { "", "tonKho > 0", "tonKho = 0" }; 
    private final String[] statusStringForFilter = { "Tất cả", "Hoạt động", "Tạm dừng" };
    private final String[] statusSQL = { "", "trangThai = 1", "trangThai = 0" };
    
    public Admin_IngredientManagerPL() {
        // Khởi tạo đối tượng BLL
        ingredientBLL = new IngredientBLL();

        // <===== Cấu trúc Title Label =====>
        titleLabel = CommonPL.getTitleLabel("Nguyên liệu", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER, SwingConstants.CENTER);
        titleLabel.setBounds(30, 0, 1140, 115);

        // <===== Cấu trúc Filter Panel =====>
        findLabel = CommonPL.getParagraphLabel("Tìm kiếm", Color.BLACK, CommonPL.getFontParagraphPlain());
        findLabel.setBounds(15, 15, 90, 24);

        findInformButton = CommonPL.getQuestionIconForm("?", "Thông tin bạn có thể tìm kiếm",
                "Bạn có thể tìm kiếm bằng các thông tin như: mã Nguyên liệu, tên Nguyên liệu", Color.BLACK, CommonPL.getFontQuestionIcon());
        findInformButton.setBounds(110, 15, 24, 24);

        findInputTextField = new CommonPL.CustomTextField(0, 20, 20, "Nhập thông tin", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        findInputTextField.setBounds(15, 45, 360, 40);

        sortLabel = CommonPL.getParagraphLabel("Sắp xếp", Color.BLACK, CommonPL.getFontParagraphPlain());
        sortLabel.setBounds(390, 15, 360, 24);

        sortCheckboxs = CommonPL.getMapHasValues(sortsString);
        sortButton = CommonPL.ButtonHasCheckboxs.createButtonHasCheckboxs(sortCheckboxs, sortsString[0], Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        sortButton.setBounds(390, 45, 360, 40);

        unitLabel = CommonPL.getParagraphLabel("Đơn vị", Color.BLACK, CommonPL.getFontParagraphPlain());
        unitLabel.setBounds(765, 15, 360, 24);

        unitCheckboxs = CommonPL.getMapHasValues(unitsString);
        unitButton = CommonPL.ButtonHasRadios.createButtonHasRadios(unitCheckboxs, unitsString[0], Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        unitButton.setBounds(765, 45, 360, 40);

        inventoryLabel = CommonPL.getParagraphLabel("Tồn kho", Color.BLACK, CommonPL.getFontParagraphPlain());
        inventoryLabel.setBounds(15, 100, 360, 24);

        inventoryCheckboxs = CommonPL.getMapHasValues(inventoryString);
        inventoryButton = CommonPL.ButtonHasRadios.createButtonHasRadios(inventoryCheckboxs, inventoryString[0], Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        inventoryButton.setBounds(15, 130, 360, 40);

        statusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK, CommonPL.getFontParagraphPlain());
        statusLabel.setBounds(390, 100, 360, 24);

        statusCheckboxs = CommonPL.getMapHasValues(statusStringForFilter);
        statusButton = CommonPL.ButtonHasRadios.createButtonHasRadios(statusCheckboxs, statusStringForFilter[0], Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        statusButton.setBounds(390, 130, 360, 40);

        filterApplyButton = CommonPL.getRoundedBorderButton(20, "Lọc", Color.decode("#007bff"), Color.WHITE, CommonPL.getFontParagraphBold());
        filterApplyButton.setBounds(765, 130, 170, 40);

        filterResetButton = CommonPL.getRoundedBorderButton(20, "Đặt lại", Color.decode("#f44336"), Color.WHITE, CommonPL.getFontParagraphBold());
        filterResetButton.setBounds(955, 130, 170, 40);

        filterPanel = new CommonPL.RoundedPanel(12);
        filterPanel.setLayout(null);
        filterPanel.setBounds(30, 115, 1140, 185);
        filterPanel.setBackground(Color.WHITE);
        filterPanel.add(findLabel);
        filterPanel.add(findInformButton);
        filterPanel.add(findInputTextField);
        filterPanel.add(sortLabel);
        filterPanel.add(sortButton);
        filterPanel.add(unitLabel);
        filterPanel.add(unitButton);
        filterPanel.add(inventoryLabel);
        filterPanel.add(inventoryButton);
        filterPanel.add(statusLabel);
        filterPanel.add(statusButton);
        filterPanel.add(filterApplyButton);
        filterPanel.add(filterResetButton);

        // <===== Cấu trúc Data Panel =====>
        addButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
                CommonPL.getMiddlePathToShowIcon() + "add-icon.png", "Thêm", Color.BLACK, Color.decode("#699f4c"), Color.BLACK, Color.decode("#699f4c"), CommonPL.getFontParagraphBold());
        addButton.setBounds(15, 15, 210, 40);

        updateButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
                CommonPL.getMiddlePathToShowIcon() + "update-icon.png", "Thay đổi", Color.BLACK, Color.decode("#bf873e"), Color.BLACK, Color.decode("#bf873e"), CommonPL.getFontParagraphBold());
        updateButton.setBounds(240, 15, 210, 40);

        lockButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
                CommonPL.getMiddlePathToShowIcon() + "lock-icon.png", "Khoá", Color.BLACK, Color.decode("#9f4d4d"), Color.BLACK, Color.decode("#9f4d4d"), CommonPL.getFontParagraphBold());
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
            showAddOrUpdateDialog("Thêm Nguyên liệu", "Thêm", new Vector<Object>());
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
                        String.format("Có chắc chắn muốn %s nguyên liệu này?", currentObject.get(4).equals("Hoạt động") ? "khóa" : "mở khóa"),
                        valueSelected);

                if (valueSelected[0]) {
                	String result = ingredientBLL.lockIngredient((String) currentObject.get(0), CommonPL.getCurrentDate());
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
            datasQuery[i][0] = ingredientList.get(i).getId(); // getId() phải trả về maNguyenLieu
            datasQuery[i][1] = ingredientList.get(i).getName(); // getName() phải trả về tenNguyenLieu
            datasQuery[i][2] = ingredientList.get(i).getUnit(); // getUnit() phải trả về donVi
            datasQuery[i][3] = ingredientList.get(i).getInventory(); // getInventory() phải trả về tonKho
            datasQuery[i][4] = ingredientList.get(i).getStatus() ? "Hoạt động" : "Tạm dừng"; // getStatus() phải trả về trangThai
        }
        datas = datasQuery;
        CommonPL.updateRowsInTableData(tableData, datas);
    }

    // Hàm xử lý sự kiện lọc dữ liệu
    private void filterDatasInTable() {
    	filterApplyButton.addActionListener(e -> {
    	    String findValue = !findInputTextField.getText().equals("Nhập thông tin") ? findInputTextField.getText() : null;
    	    String sortValue = CommonPL.getSQLFromCheckboxs(sortCheckboxs, sortsSQL);
    	    String unitValue = CommonPL.getSQLFromRadios(unitCheckboxs, unitsSQL);
    	    String inventoryValue = CommonPL.getSQLFromRadios(inventoryCheckboxs, inventorySQL);
    	    String statusValue = CommonPL.getSQLFromRadios(statusCheckboxs, statusSQL);

    	    String condition = (findValue != null ? String.format("(maNguyenLieu LIKE '%%%s%%' OR tenNguyenLieu LIKE '%%%s%%')", findValue, findValue) : "")
    	            + (unitValue != null ? (findValue != null ? " AND " + unitValue : unitValue) : "")
    	            + (inventoryValue != null ? (findValue != null || unitValue != null ? " AND " + inventoryValue : inventoryValue) : "")
    	            + (statusValue != null ? (findValue != null || unitValue != null || inventoryValue != null ? " AND " + statusValue : statusValue) : "");
    	    if (condition.length() == 0) condition = null;
    	    String order = sortValue;

    	    renderTableData(null, condition, order);
    	});

    	filterResetButton.addActionListener(e -> {
            findInputTextField.setText("Nhập thông tin");
            findInputTextField.setForeground(Color.LIGHT_GRAY);
            CommonPL.resetMapForFilter(sortCheckboxs, sortsString, sortButton);
            CommonPL.resetMapForFilter(unitCheckboxs, unitsString, unitButton);
            CommonPL.resetMapForFilter(inventoryCheckboxs, inventoryString, inventoryButton);
            CommonPL.resetMapForFilter(statusCheckboxs, statusStringForFilter, statusButton);
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
        addOrUpdateIdTextField.setBounds(20, 50, 460, 40);

        addOrUpdateNameLabel = CommonPL.getParagraphLabel(
                "<html>Tên nguyên liệu <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateNameLabel.setBounds(20, 100, 460, 40);

        addOrUpdateNameTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Tên nguyên liệu", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateNameTextField.setBounds(20, 140, 460, 40);

        addOrUpdateUnitLabel = CommonPL.getParagraphLabel(
                "<html>Đơn vị <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateUnitLabel.setBounds(20, 190, 460, 40);

        Vector<String> unitsVector = CommonPL.getVectorHasValues(new String[] { "Chọn Đơn vị", "Gói", "Quả", "Hộp", "Thẻ", "Chai", "Lon", "Bó" });
        addOrUpdateUnitComboBox = CommonPL.CustomComboBox(unitsVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateUnitComboBox.setBounds(20, 230, 460, 40);

        addOrUpdateInventoryLabel = CommonPL.getParagraphLabel("Tồn kho", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateInventoryLabel.setBounds(20, 280, 460, 40);

        // Không vô hiệu hóa trường inventory vì giờ nó là String và có thể nhập
        addOrUpdateInventoryTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập tồn kho", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateInventoryTextField.setBounds(20, 320, 460, 40);

        addOrUpdateStatusLabel = CommonPL.getParagraphLabel(
                "<html>Trạng thái <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateStatusLabel.setBounds(20, 370, 460, 40);

        Vector<String> statusVector = CommonPL.getVectorHasValues(statusStringForFilter);
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
        if (title.equals("Thêm Nguyên liệu") && button.equals("Thêm") && object.size() == 0) {
            String id = "NL" + String.format("%04d", Integer.parseInt(ingredientBLL.getLastIngredient().getId().substring(2)) + 1);
            addOrUpdateIdTextField.setText(id);
            addOrUpdateIdTextField.setEnabled(false);
            ((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
            addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));
            addOrUpdateInventoryTextField.setText("0"); // Giá trị mặc định khi thêm mới
            addOrUpdateInventoryTextField.setForeground(Color.BLACK);
        }

        // Khi "Thay đổi" nguyên liệu
        if (title.equals("Thay đổi Nguyên liệu") && button.equals("Thay đổi") && object.size() != 0) {
            addOrUpdateIdTextField.setText((String) object.get(0));
            addOrUpdateIdTextField.setEnabled(false);
            ((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
            addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));

            addOrUpdateNameTextField.setText((String) object.get(1));
            addOrUpdateNameTextField.setForeground(Color.BLACK);

            addOrUpdateUnitComboBox.setSelectedItem((String) object.get(2));
            addOrUpdateUnitComboBox.setForeground(Color.BLACK);

            addOrUpdateInventoryTextField.setText((String) object.get(3)); // Giờ là String
            addOrUpdateInventoryTextField.setForeground(Color.BLACK);

            addOrUpdateStatusComboBox.setSelectedItem((String) object.get(4));
            addOrUpdateStatusComboBox.setEnabled(false);
            UIManager.put("ComboBox.disabledBackground", Color.decode("#dedede"));
            addOrUpdateStatusComboBox.setBorder(BorderFactory.createLineBorder(Color.decode("#dedede"), 1));
        }

        // Sự kiện nút "Thêm" hoặc "Thay đổi"
        addOrUpdateButton.addActionListener(e -> {
            String id = addOrUpdateIdTextField.getText();
            String name = !addOrUpdateNameTextField.getText().equals("Nhập Tên nguyên liệu") ? addOrUpdateNameTextField.getText() : null;
            String unit = !addOrUpdateUnitComboBox.getSelectedItem().equals("Chọn Đơn vị") ? (String) addOrUpdateUnitComboBox.getSelectedItem() : null;
            String inventory = !addOrUpdateInventoryTextField.getText().equals("Nhập tồn kho") ? addOrUpdateInventoryTextField.getText() : "0"; // Xử lý String
            String statusStr = (String) addOrUpdateStatusComboBox.getSelectedItem();
            boolean status = statusStr.equals("Hoạt động");

            // Kiểm tra dữ liệu đầu vào
            if (name == null || unit == null || statusStr.equals("Chọn Trạng thái")) {
                CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng điền đầy đủ thông tin bắt buộc");
                return;
            }
            try {
                Integer.parseInt(inventory); // Kiểm tra xem inventory có phải là số không
            } catch (NumberFormatException ex) {
                CommonPL.createErrorDialog("Thông báo lỗi", "Tồn kho phải là một số nguyên");
                return;
            }

            if (title.equals("Thêm Nguyên liệu")) {
            	String result = ingredientBLL.insertIngredient(id, name, unit, inventory, status, CommonPL.getCurrentDate());
                if (result.equals("Có thể thêm một nguyên liệu")) {
                    CommonPL.createSuccessDialog("Thông báo thành công", "Thêm nguyên liệu thành công");
                    addOrUpdateDialog.dispose();
                    renderTableData(null, null, null);
                } else {
                    CommonPL.createErrorDialog("Thông báo lỗi", result);
                }
            } else if (title.equals("Thay đổi Nguyên liệu")) {
            	String result = ingredientBLL.updateIngredient(id, name, unit, inventory, status, CommonPL.getCurrentDate());
                if (result.equals("Có thể thay đổi một nguyên liệu")) {
                    CommonPL.createSuccessDialog("Thông báo thành công", "Cập nhật nguyên liệu thành công");
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