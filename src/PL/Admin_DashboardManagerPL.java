package PL;

import java.awt.Color;
import java.awt.Font;
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

public class Admin_DashboardManagerPL extends JPanel {
	// Các Font
	private Font fontComboBox = new Font("Arial", Font.PLAIN, 14);
	private Font fontButton = new Font("Arial", Font.BOLD, 14);
	// Các Component
	private JLabel titleLabel;
	// - Các Component của Data Panel
	private JComboBox<String> dashboardTypeComboBox;
	private JComboBox<String> timeTypeComboBox;
	private JComboBox<String> timeDetailComboBox;
	private JButton filterButton;
	private JButton createBillButton;
	private JTable tableData;
	private JScrollPane tableScrollPane;
	private JPanel dataPanel;

	// - Các thông tin cần có của Table Data và Table Scroll Pane
	// + Tiêu đề các cột
	private final String[] columns = { "Thống kê" };
	// + Chiều rộng các cột
	private final int[] widthColumns = { 1110 };
	// + Dữ liệu
	private Object[][] datas = { { "" } };
	// + Biến đảm bảo sẽ hiện "Thống kê Lợi nhuận" khi nhấn vào mục "Thống kê"
	private boolean isFirstClickToReadDashboard = false;
	// + Nếu lọc "Theo năm" sẽ có chiều cao bảng khác
	private boolean isYearFiltered = false;

	public Admin_DashboardManagerPL() {
		// <===== Cấu trúc của Title Label =====>
		// - Tuỳ chỉnh Title Label
		titleLabel = CommonPL.getTitleLabel("Thống kê", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER,
				SwingConstants.CENTER);
		titleLabel.setBounds(30, 0, 1140, 115);
		// <==================== ====================>

		// <===== Cấu trúc của Data Panel =====>
		// - Tuỳ chỉnh Dashboard Type ComboBox
		String[] dashboardTypes = { "Chọn Loại thống kê", "Thống kê Lợi nhuận", "Thống kê Doanh thu", "Tài sản",
				"Thống kê Hồ sơ" };
		Vector<String> dashboardTypesVector = CommonPL.getVectorHasValues(dashboardTypes);
		dashboardTypeComboBox = CommonPL.CustomComboBox(dashboardTypesVector, Color.WHITE, Color.LIGHT_GRAY,
				Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontComboBox);
		dashboardTypeComboBox.setBounds(15, 15, 200, 30);

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
		filterButton = CommonPL.getRoundedBorderButton(14, "Lọc", Color.decode("#007bff"), Color.WHITE,
				new Font("Arial", Font.BOLD, 14));
		filterButton.setBounds(660, 15, 100, 30);

		// - Tuỳ chỉnh Create Bill Button Button
		createBillButton = CommonPL.getRoundedBorderButton(14, "Xuất biên lai", Color.decode("#699f4c"), Color.WHITE,
				new Font("Arial", Font.BOLD, 14));
		createBillButton.setBounds(925, 15, 200, 30);

		// - Tuỳ chỉnh Table Data và Table Scroll Pane
		tableData = CommonPL.createTableData(columns, widthColumns, datas, "dashboard manager");
		tableScrollPane = CommonPL.createScrollPane(false, false, tableData);
		tableScrollPane.setBounds(15, 60, 1110, 630);

		// - Tuỳ chỉnh Data Pane;
		dataPanel = new CommonPL.RoundedPanel(12);
		dataPanel.setLayout(null);
		dataPanel.setBackground(Color.WHITE);
		dataPanel.setBounds(30, 115, 1140, 705);
		dataPanel.add(dashboardTypeComboBox);
		dataPanel.add(timeTypeComboBox);
		dataPanel.add(timeDetailComboBox);
		dataPanel.add(filterButton);
		dataPanel.add(createBillButton);
		dataPanel.add(tableScrollPane);
		// <==================== ====================>

		// Đĩnh nghĩa tính chất cho Dashboard Manager PL
		this.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
		this.setLayout(null);
		this.setSize(CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
		this.setBackground(Color.decode("#E3F2FD"));
		this.setLayout(null);
		this.add(titleLabel);
		this.add(dataPanel);

		// Thiết lập sự kiện thay đổi các mục của các ComboBox để thống kê
		renderAllComboBoxToUpdateDashboard();

		// Thiết lập sự kiện thay đổi lại Table Data và Table Scroll Pane khi
		// "lọc thống kê"
		renderTableAfterFiltered();
	}

	// Hàm thay đổi các mục của các ComboBox để thống kê
	private void renderAllComboBoxToUpdateDashboard() {
		// Vector chứa các mốc thời gian
		Vector<String> timeTypesVector = new Vector<>();
		// Vector chứa các năm (lọc theo năm)
		Vector<String> timeDetailsVector = new Vector<>();
		// Vector chứa các tháng (lọc theo tháng)
		Vector<String> monthsVector = new Vector<>();

		// Sự kiện khi chọn "Loại thống kê"
		dashboardTypeComboBox.addActionListener(e -> {
			// Lấy ra giá trị được chọn hiện tại của Dashboard Type ComboBox
			String valueSelected = (String) dashboardTypeComboBox.getSelectedItem();

			// Xoá các giá trị hiện tại của các Vector
			timeTypesVector.clear();
			timeDetailsVector.clear();

			// Thêm giá trị mặc định cho các Vector
			timeTypesVector.add("Chọn Mốc thời gian");
			timeDetailsVector.add("Chọn Thời gian cụ thể");

			// Nếu giá trị hiện tại không là "Chọn Loại thống kê"
			if (!valueSelected.equals("Chọn Loại thống kê")) {
				// Xử lý Time Types Vector
				timeTypesVector.add("Theo năm");
				timeTypesVector.add("Theo tháng");
				CommonPL.updateComboBox(timeTypeComboBox, timeTypesVector, null);

				// Sự kiện khi chọn "Mốc thời gian"
				timeTypeComboBox.addActionListener(subE -> {
					// Lấy ra giá trị được chọn hiện tại của Time Type ComboBox
					String subValueSelected = (String) timeTypeComboBox.getSelectedItem();

					// Xoá các giá trị hiện tại của Time Details Vector
					timeDetailsVector.clear();

					// Thêm giá trị mặc định cho Time Details Vector
					timeDetailsVector.add("Chọn Thời gian cụ thể");

					// Nếu giá trị hiện tại không là "Chọn Mốc thời gian"
					if (!subValueSelected.equals("Chọn Mốc thời gian")) {
						// Nếu chọn "Theo năm"
						if (subValueSelected.equals("Theo năm")) {
							// Tính từ năm 1900 đến năm 2100
							for (int year = 1900; year <= 2100; year++) {
								timeDetailsVector.add("Năm " + String.valueOf(year));
							}
						}

						// Nếu chọn "Theo tháng"
						if (subValueSelected.equals("Theo tháng")) {
							// Tính từ năm 1900 đến năm 2100
							for (int year = 1900; year <= 2100; year++) {
								// Tính từ tháng 1 đến tháng 12
								for (int month = 1; month <= 12; month++) {
									timeDetailsVector
											.add("Tháng " + String.valueOf(month) + "-" + String.valueOf(year));
								}
							}
						}
					}

					// Cập nhật lại định dạng và các mục cho Time Detail ComboBox
					CommonPL.updateComboBox(timeDetailComboBox, timeDetailsVector, null);
					timeDetailComboBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
					timeDetailComboBox.setForeground(Color.LIGHT_GRAY);
				});
			}

			// Cập nhật lại định dạng và các mục cho ComboBox
			CommonPL.updateComboBox(timeTypeComboBox, timeTypesVector, null);
			timeTypeComboBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			timeTypeComboBox.setForeground(Color.LIGHT_GRAY);
			CommonPL.updateComboBox(timeDetailComboBox, timeDetailsVector, null);
			timeDetailComboBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			timeDetailComboBox.setForeground(Color.LIGHT_GRAY);
		});
	}

	// Hàm thay đổi lại Table Data và Table Scroll Pane khi "lọc thống kê"
	private void renderTableAfterFiltered() {
		// Tên, kịch thước các cột và mảng chứa dữ liệu tương ứng cho từng loại thống kê
		// - Thống kê Thống kê Lợi nhuận
		// + Tên các cột
		final String[] profitColumns01 = { "Tháng", "Đầu tư (VNĐ)", "Doanh thu (VNĐ)", "Lợi nhuận (VNĐ)" };
		final String[] profitColumns02 = { "Tháng", "Đầu tư (VNĐ)", "Doanh thu (VNĐ)", "Lợi nhuận (VNĐ)" };
		// + Chiều rộng các cột
		final int[] profitWidthColumns = { 444, 222, 222, 222 };
		// + Dữ liệu
		Object[][] profitDatas01 = { { "Tháng 01 (từ 01-01-yyyy đến 31-01-yyyy)", "Thống kê Lợi nhuận", "", "" },
				{ "Tháng 02 (từ 01-02-yyyy đến 31-02-yyyy)", "", "", "" }, { "Tháng 3", "", "", "" },
				{ "Tháng 04", "", "", "" }, { "Tháng 05", "", "", "" }, { "Tháng 06", "", "", "" },
				{ "Tháng 07", "", "", "" }, { "Tháng 08", "", "", "" }, { "Tháng 09", "", "", "" },
				{ "Tháng 10", "", "", "" }, { "Tháng 11", "", "", "" }, { "Tháng 12", "", "", "" },
				{ "Tổng cộng", "", "", "" }, };
		Object[][] profitDatas02 = { { "Tuần 01 (từ 01-mm-yyyy đến 07-mm-yyyy)", "Thống kê Lợi nhuận", "", "" },
				{ "Tuần 02 (từ 08-mm-yyyy đến 14-mm-yyyy)", "", "", "" },
				{ "Tuần 03 (từ 15-mm-yyyy đến 21-mm-yyyy)", "", "", "" },
				{ "Tuần 04 (từ 22-mm-yyyy đến 27-mm-yyyy)", "", "", "" },
				{ "Tuần 05 (từ 28-mm-yyyy đến 31-mm-yyyy)", "", "", "" }, { "Tổng cộng", "", "", "" }, };
		// - Thống kê Thống kê Doanh thu
		// + Tên các cột
		final String[] revenueColumns01 = { "Tháng", "Đơn hàng", "Thiết bị", "Vật tư", "Thuốc", "Thành tiền (VNĐ)" };
		final String[] revenueColumns02 = { "Tuần", "Đơn hàng", "Thiết bị", "Vật tư", "Thuốc", "Thành tiền (VNĐ)" };
		// + Chiều rộng các cột
		final int[] revenueWidthColumns = { 350, 140, 140, 140, 140, 200 };
		// + Dữ liệu
		Object[][] revenueDatas01 = { { "Tháng 01 (từ 01-01-yyyy đến 31-01-yyyy)", "Đơn hàng", "", "", "", "" },
				{ "Tháng 02 (từ 01-02-yyyy đến 31-02-yyyy)", "", "", "", "", "" }, { "Tháng 03", "", "", "", "", "" },
				{ "Tháng 04", "", "", "", "", "" }, { "Tháng 05", "", "", "", "", "" },
				{ "Tháng 06", "", "", "", "", "" }, { "Tháng 07", "", "", "", "", "" },
				{ "Tháng 08", "", "", "", "", "" }, { "Tháng 09", "", "", "", "", "" },
				{ "Tháng 10", "", "", "", "", "" }, { "Tháng 11", "", "", "", "", "" },
				{ "Tháng 12", "", "", "", "", "" }, { "Tổng cộng", "", "", "", "", "" }, };
		Object[][] revenueDatas02 = { { "Tuần 01 (từ 01-mm-yyyy đến 07-mm-yyyy)", "Đơn hàng", "", "", "", "" },
				{ "Tuần 02 (từ 08-mm-yyyy đến 14-mm-yyyy)", "", "", "", "", "" },
				{ "Tuần 03 (từ 15-mm-yyyy đến 21-mm-yyyy)", "", "", "", "", "" },
				{ "Tuần 04 (từ 22-mm-yyyy đến 27-mm-yyyy)", "", "", "", "", "" },
				{ "Tuần 05 (từ 28-mm-yyyy đến 31-mm-yyyy)", "", "", "", "", "" },
				{ "Tổng cộng", "", "", "", "", "" }, };
//		// - Thống kê Tài sản
//		// + Tên các cột
//		final String[] richesColumns01 = { "Tháng", "Thiết bị", "Thành tiền (VNĐ)", "Vật tư", "Thành tiền (VNĐ)",
//				"Thuốc", "Thành tiền (VNĐ)" };
//		final String[] richesColumns02 = { "Tuần", "Thiết bị", "Thành tiền (VNĐ)", "Vật tư", "Thành tiền (VNĐ)",
//				"Thuốc", "Thành tiền (VNĐ)" };
//		// + Chiều rộng các cột
//		final int[] richesWidthColumns = { 300, 100, 170, 100, 170, 100, 170 };
//		// + Dữ liệu
//		Object[][] richesDatas01 = { { "Tháng 1", "", "", "", "", "", "" }, { "Tháng 2", "", "", "", "", "", "" },
//				{ "Tháng 3", "", "", "", "", "", "" }, { "Tháng 4", "", "", "", "", "", "" },
//				{ "Tháng 5", "", "", "", "", "", "" }, { "Tháng 6", "", "", "", "", "", "" },
//				{ "Tháng 7", "", "", "", "", "", "" }, { "Tháng 8", "", "", "", "", "", "" },
//				{ "Tháng 9", "", "", "", "", "", "" }, { "Tháng 10", "", "", "", "", "", "" },
//				{ "Tháng 11", "", "", "", "", "", "" }, { "Tháng 12", "", "", "", "", "", "" },
//				{ "Tổng cộng", "", "", "", "", "", "" }, };
//		Object[][] richesDatas02 = { { "Tuần 1 (từ 01-01-yyyy đến 07-01-yyyy)", "", "", "", "", "", "" },
//				{ "Tuần 2 (từ 08-01-yyyy đến 14-01-yyyy)", "", "", "", "", "", "" },
//				{ "Tuần 3 (từ 15-01-yyyy đến 21-01-yyyy)", "", "", "", "", "", "" },
//				{ "Tuần 4 (từ 22-01-yyyy đến 27-01-yyyy)", "", "", "", "", "", "" },
//				{ "Tuần 5 (từ 28-01-yyyy đến 31-01-yyyy)", "", "", "", "", "", "" }, { "Tổng cộng", "", "", "" }, };
		// - Thống kê Thống kê Hồ sơ
		// + Tên các cột
		final String[] briefColumns01 = { "Tháng", "Đơn khám", "Tiền thu (VNĐ)", "Bệnh án", "Tiền thu (VNĐ)",
				"Thành tiền (VNĐ)" };
		final String[] briefColumns02 = { "Tuần", "Đơn khám", "Tiền thu (VNĐ)", "Bệnh án", "Tiền thu (VNĐ)",
				"Thành tiền (VNĐ)" };
		// + Chiều rộng các cột
		final int[] briefWidthColumns = { 350, 120, 180, 120, 180, 180 };
		// + Dữ liệu
		Object[][] briefDatas01 = {
				{ "Tháng 01 (từ 01-01-yyyy đến 31-01-yyyy)", "Đơn đăng ký - Bệnh án", "", "", "", "" },
				{ "Tháng 02 (từ 01-02-yyyy đến 31-02-yyyy)", "", "", "", "", "" }, { "Tháng 03", "", "", "", "", "" },
				{ "Tháng 04", "", "", "", "", "" }, { "Tháng 05", "", "", "", "", "" },
				{ "Tháng 06", "", "", "", "", "" }, { "Tháng 07", "", "", "", "", "" },
				{ "Tháng 08", "", "", "", "", "" }, { "Tháng 09", "", "", "", "", "" },
				{ "Tháng 10", "", "", "", "", "" }, { "Tháng 11", "", "", "", "", "" },
				{ "Tháng 12", "", "", "", "", "" }, { "Tổng cộng", "", "", "", "", "" }, };
		Object[][] briefDatas02 = {
				{ "Tuần 01 (từ 01-mm-yyyy đến 07-mm-yyyy)", "Đơn đăng ký - Bệnh án", "", "", "", "" },
				{ "Tuần 02 (từ 08-mm-yyyy đến 14-mm-yyyy)", "", "", "", "" },
				{ "Tuần 03 (từ 15-mm-yyyy đến 21-mm-yyyy)", "", "", "", "" },
				{ "Tuần 04 (từ 22-mm-yyyy đến 27-mm-yyyy)", "", "", "", "" },
				{ "Tuần 05 (từ 28-mm-yyyy đến 31-mm-yyyy)", "", "", "", "" }, { "Tổng cộng", "", "", "", "" }, };

		// Nếu là lần đầu nhấn vào mục "Thống kê"
		// PHẢI XỬ LÝ LÀ THỐNG KÊ THEO NĂM HIỆN TẠI
		if (!isFirstClickToReadDashboard) {
			// Cập nhật lại định dạng và các mục cho Dashboard Type ComboBox
			dashboardTypeComboBox.setSelectedItem("Thống kê Lợi nhuận");
			dashboardTypeComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			dashboardTypeComboBox.setForeground(Color.BLACK);

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
			CommonPL.updateTableData(tableData, profitColumns01, profitWidthColumns, profitDatas01);
			isYearFiltered = true;
			tableScrollPane.setBounds(15, 60, 1110, 630);

			isFirstClickToReadDashboard = true;
		}

		// Thay đổi khi nhấn nút "Lọc"
		filterButton.addActionListener(e -> {
			// Lấy ra giá trị được hiện tại của các ComboBox
			String currentDashboardType = (String) dashboardTypeComboBox.getSelectedItem();
			String currentTimeType = (String) timeTypeComboBox.getSelectedItem();
			String currentTimeDetail = (String) timeDetailComboBox.getSelectedItem();

			if (!currentDashboardType.equals("Chọn Loại thống kê") && !currentTimeType.equals("Chọn Mốc thời gian")
					&& !currentTimeDetail.equals("Chọn Thời gian cụ thể")) {
				if (currentDashboardType.equals("Thống kê Lợi nhuận")) {
					if (currentTimeType.equals("Theo năm")) {
						CommonPL.updateTableData(tableData, profitColumns01, profitWidthColumns, profitDatas01);
						isYearFiltered = true;
					} else if (currentTimeType.equals("Theo tháng")) {
						CommonPL.updateTableData(tableData, profitColumns02, profitWidthColumns, profitDatas02);
						isYearFiltered = false;
					}
				} else if (currentDashboardType.equals("Thống kê Doanh thu")) {
					if (currentTimeType.equals("Theo năm")) {
						CommonPL.updateTableData(tableData, revenueColumns01, revenueWidthColumns, revenueDatas01);
						isYearFiltered = true;
					} else if (currentTimeType.equals("Theo tháng")) {
						CommonPL.updateTableData(tableData, revenueColumns02, revenueWidthColumns, revenueDatas02);
						isYearFiltered = false;
					}
				} else if (currentDashboardType.equals("Thống kê Hồ sơ")) {
					if (currentTimeType.equals("Theo năm")) {
						CommonPL.updateTableData(tableData, briefColumns01, briefWidthColumns, briefDatas01);
						isYearFiltered = true;
					} else if (currentTimeType.equals("Theo tháng")) {
						CommonPL.updateTableData(tableData, briefColumns02, briefWidthColumns, briefDatas02);
						isYearFiltered = false;
					}
				} else if (currentDashboardType.equals("")) {
					if (currentTimeType.equals("Theo năm")) {

					} else if (currentTimeType.equals("Theo tháng")) {

					}
				}

				// Cập nhật lại Table Scroll Pane
				tableScrollPane.setBounds(15, 60, 1110, isYearFiltered ? 630 : 315);
			} else {
				CommonPL.createErrorDialog("Thông báo lỗi", "Cần chọn thông tin đầy đủ để thống kế");
			}
		});
	}
}
