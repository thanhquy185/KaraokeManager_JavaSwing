package PL;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class AD_WarehouseManagerPL extends JPanel {
	// Các Font
	private Font fontComboBox = new Font("Arial", Font.PLAIN, 14);
	private Font fontButton = new Font("Arial", Font.BOLD, 14);
	// Các Component
	private JLabel titleLabel;
	// - Các Component của Data Panel
	private JComboBox<String> warehouseTypeComboBox;
	private JComboBox<String> timeTypeComboBox;
	private JComboBox<String> timeDetailComboBox;
	private JButton filterButton;
	private JButton createBillButton;
	private JTable tableData;
	private JScrollPane tableScrollPane;
	private JPanel dataPanel;

	// - Các thông tin cần có của Table Data và Table Scroll Pane
	// + Tiêu đề các cột
	private final String[] columns = { "Kho hàng" };
	// + Chiều rộng các cột
	private final int[] widthColumns = { 1110 };
	// + Dữ liệu
	private Object[][] datas = { { "" } };
	// + Biến đảm bảo sẽ hiện "Kho thiết bị" khi nhấn vào mục "Kho hàng"
	private boolean isFirstClickToReadDashboard = false;

	public AD_WarehouseManagerPL() {
		// <===== Cấu trúc của Title Label =====>
		// - Tuỳ chỉnh Title Label
		titleLabel = CommonPL.getTitleLabel("Thống kê", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER,
				SwingConstants.CENTER);
		titleLabel.setBounds(30, 0, 1140, 115);
		// <==================== ====================>

		// <===== Cấu trúc của Data Panel =====>
		// - Tuỳ chỉnh Dashboard Type ComboBox
		String[] warehouseTypes = { "Chọn Kho", "Kho thiết bị", "Kho vật tư", "Kho thuốc", "Thống kê Hồ sơ" };
		Vector<String> warehouseTypesVector = CommonPL.getVectorHasValues(warehouseTypes);
		warehouseTypeComboBox = CommonPL.CustomComboBox(warehouseTypesVector, Color.WHITE, Color.LIGHT_GRAY,
				Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontComboBox);
		warehouseTypeComboBox.setBounds(15, 15, 200, 30);

		// - Tuỳ chỉnh Time Type ComboBox
		String[] timeTypes = { "Chọn Mốc thời gian" };
		Vector<String> timeTypesVector = CommonPL.getVectorHasValues(timeTypes);
		timeTypeComboBox = CommonPL.CustomComboBox(timeTypesVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontComboBox);
		timeTypeComboBox.setBounds(230, 15, 200, 30);

		// - Tuỳ chỉnh Time Detail Type ComboBox
		String[] timeDetails = { "Chọn Thời gian cụ thể" };
		Vector<String> timeDetailsVector = CommonPL.getVectorHasValues(timeDetails);
		timeDetailComboBox = CommonPL.CustomComboBox(timeDetailsVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontComboBox);
		timeDetailComboBox.setBounds(445, 15, 200, 30);

		// - Tuỳ chỉnh Filter Button
		filterButton = new CommonPL.CustomRoundedButton(14, 14, "Lọc");
		filterButton.setBounds(660, 15, 100, 30);
		filterButton.setBackground(Color.decode("#007bff"));
		filterButton.setForeground(Color.WHITE);
		filterButton.setFont(new Font("Arial", Font.BOLD, 14));
		filterButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent evt) {
				try {
					Thread.sleep(100);
					filterButton.setBackground(Color.WHITE);
					filterButton.setForeground(Color.decode("#007bff"));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				filterButton.setBackground(Color.decode("#007bff"));
				filterButton.setForeground(Color.WHITE);
			}
		});

		// - Tuỳ chỉnh Create Bill Button Button
		createBillButton = new CommonPL.CustomRoundedButton(14, 14, "Xuất biên lai");
		createBillButton.setBounds(925, 15, 200, 30);
		createBillButton.setBackground(Color.decode("#699f4c"));
		createBillButton.setForeground(Color.WHITE);
		createBillButton.setFont(new Font("Arial", Font.BOLD, 14));
		createBillButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent evt) {
				try {
					Thread.sleep(100);
					createBillButton.setBackground(Color.WHITE);
					createBillButton.setForeground(Color.decode("#699f4c"));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				createBillButton.setBackground(Color.decode("#699f4c"));
				createBillButton.setForeground(Color.WHITE);
			}
		});

		// - Tuỳ chỉnh Table Data và Table Scroll Pane
		tableData = CommonPL.createTableData(columns, widthColumns, datas, "work schedule manager");
		tableScrollPane = CommonPL.createScrollPane(false, false, tableData);
		tableScrollPane.setBounds(15, 60, 1110, 630);

		// - Tuỳ chỉnh Data Pane;
		dataPanel = new CommonPL.RoundedPanel(12);
		dataPanel.setLayout(null);
		dataPanel.setBackground(Color.WHITE);
		dataPanel.setBounds(30, 115, 1140, 705);
		dataPanel.add(warehouseTypeComboBox);
		dataPanel.add(timeTypeComboBox);
		dataPanel.add(timeDetailComboBox);
		dataPanel.add(filterButton);
		dataPanel.add(createBillButton);
		dataPanel.add(tableScrollPane);
		// <==================== ====================>

		// Đĩnh nghĩa tính chất cho Dashboard Manager PL
		this.setSize(CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
		this.setLayout(null);
		this.add(titleLabel);
		this.add(dataPanel);

		// Thiết lập sự kiện thay đổi lại Table Data và Table Scroll Pane khi
		// "lọc thống kê"
		renderTableAfterFiltered();
	}

	// Hàm thay đổi lại Table Data và Table Scroll Pane khi "lọc thống kê"
	private void renderTableAfterFiltered() {
		// Tên, kịch thước các cột và mảng chứa dữ liệu tương ứng cho từng loại thống kê
		// - Thống kê Kho thiết bị
		// + Tên các cột
		final String[] deviceNameColumns = { "Mã phiếu", "Mã thiết bị", "Tên thiết bị", "Loại thiết bị",
				"Ngày sản xuất" };
		// + Chiều rộng các cột
		final int[] deviceWidthColumns = { 444, 222, 222, 222 };
		// + Dữ liệu
		Object[][] deviceDatas = { {} };
		// - Thống kê Thống kê Doanh thu
		// - Thống kê Kho thiết bị
		// + Tên các cột
//		final String[] profitColumns = { "Tháng", "Đầu tư (VNĐ)", "Doanh thu (VNĐ)", "Lợi nhuận (VNĐ)" };
		// + Chiều rộng các cột
//		final int[] profitWidthColumns = { 444, 222, 222, 222 };
		// + Dữ liệu
//		Object[][] profitDatas = { {} };
		// - Thống kê Kho thiết bị
		// + Tên các cột
//		final String[] profitColumns = { "Tháng", "Đầu tư (VNĐ)", "Doanh thu (VNĐ)", "Lợi nhuận (VNĐ)" };
		// + Chiều rộng các cột
//		final int[] profitWidthColumns = { 444, 222, 222, 222 };
		// + Dữ liệu
//		Object[][] profitDatas = { {} };

		// Nếu là lần đầu nhấn vào mục "Thống kê"
		// PHẢI XỬ LÝ LÀ THỐNG KÊ THEO NĂM HIỆN TẠI
		if (!isFirstClickToReadDashboard) {
			// Cập nhật lại định dạng và các mục cho Dashboard Type ComboBox
			warehouseTypeComboBox.setSelectedItem("Kho thiết bị");
			warehouseTypeComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			warehouseTypeComboBox.setForeground(Color.BLACK);

			// Cập nhật lại định dạng và các mục cho Time Type ComboBox
			String[] timeTypes = { "Chọn Mốc thời gian", "Theo năm", "Theo tháng" };
			Vector<String> timeTypesVector = CommonPL.getVectorHasValues(timeTypes);
			CommonPL.updateComboBox(timeTypeComboBox, timeTypesVector, "Theo năm");
			timeTypeComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			timeTypeComboBox.setForeground(Color.BLACK);

			// Cập nhật lại định dạng và các mục cho Time Detail ComboBox
			Vector<String> timeDetailsVector = new Vector<>();
			for (int year = 1900; year <= 2100; year++) {
				timeDetailsVector.add("Năm " + String.valueOf(year));
			}
			CommonPL.updateComboBox(timeDetailComboBox, timeDetailsVector,
					String.valueOf("Năm " + LocalDateTime.now().getYear()));
			timeDetailComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			timeDetailComboBox.setForeground(Color.BLACK);

			// Cập nhật lại bảng dữ liệu và thêm vào Data Panel
			CommonPL.updateTableData(tableData, deviceNameColumns, deviceWidthColumns, deviceDatas);
			tableScrollPane.setBounds(15, 60, 1110, 45 * (deviceDatas.length + 1));

			isFirstClickToReadDashboard = true;
		}

		// Thay đổi khi nhấn nút "Lọc"
		filterButton.addActionListener(e -> {
			// Lấy ra giá trị được hiện tại của các ComboBox
			String currentwarehouseType = (String) warehouseTypeComboBox.getSelectedItem();
			String currentTimeType = (String) timeTypeComboBox.getSelectedItem();
			String currentTimeDetail = (String) timeDetailComboBox.getSelectedItem();

			if (!currentwarehouseType.equals("Chọn Kho") && !currentTimeType.equals("Chọn Mốc thời gian")
					&& !currentTimeDetail.equals("Chọn Thời gian cụ thể")) {
				if (currentwarehouseType.equals("Kho thiết bị")) {
					CommonPL.updateTableData(tableData, deviceNameColumns, deviceWidthColumns, deviceDatas);
				} else if (currentwarehouseType.equals("Thống kê Doanh thu")) {
//					CommonPL.updateTableData(tableData, revenueColumns01, revenueWidthColumns, revenueDatas01);
				} else if (currentwarehouseType.equals("Thống kê Hồ sơ")) {
//					CommonPL.updateTableData(tableData, briefColumns01, briefWidthColumns, briefDatas01);
				} else if (currentwarehouseType.equals("")) {
				}

				// Cập nhật lại Table Scroll Pane
//				tableScrollPane.setBounds(15, 60, 1110, 45 * (profitDatas.length + 1));
			} else {
				CommonPL.createErrorDialog("Thông báo lỗi", "Cần chọn thông tin đầy đủ để thống kế");
			}
		});
	}
}
