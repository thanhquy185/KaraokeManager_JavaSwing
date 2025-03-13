package PL;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import BLL.CustomerBLL;
import BLL.CustomerTypeBLL;
import BLL.DiscountBLL;
import BLL.DiscountTypeBLL;
import BLL.InputTicketBLL;
import BLL.InputTicketDetailBLL;
import BLL.OrderBLL;
import BLL.OrderDetailBLL;
import BLL.ProductBLL;
import BLL.RoomBLL;
import BLL.RoomTypeBLL;
import DTO.DiscountDTO;
import DTO.InputTicketDTO;
import DTO.InputTicketDetailDTO;
import DTO.OrderDTO;
import DTO.OrderDetailDTO;
import DTO.ProductDTO;
import DTO.RoomTypeDTO;

public class Admin_DashboardManagerPL extends JPanel {
	// Các đối tượng ở tầng Bussiness Logical Layer
	private OrderBLL orderBLL;
	private OrderDetailBLL orderDetailBLL;
	private InputTicketBLL inputTicketBLL;
	private InputTicketDetailBLL inputTicketDetailBLL;
	private RoomBLL roomBLL;
	private RoomTypeBLL roomTypeBLL;
	private DiscountBLL discountBLL;
	private DiscountTypeBLL discountTypeBLL;
	private ProductBLL productBLL;
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
		// <===== Các đối tượng từ tầng Bussiness Logical Layer =====>
		orderBLL = new OrderBLL();
		orderDetailBLL = new OrderDetailBLL();
		inputTicketBLL = new InputTicketBLL();
		inputTicketDetailBLL = new InputTicketDetailBLL();
		roomBLL = new RoomBLL();
		roomTypeBLL = new RoomTypeBLL();
		discountBLL = new DiscountBLL();
		productBLL = new ProductBLL();
		// <==================== ====================>

		// <===== Cấu trúc của Title Label =====>
		// - Tuỳ chỉnh Title Label
		titleLabel = CommonPL.getTitleLabel("Thống kê", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER,
				SwingConstants.CENTER);
		titleLabel.setBounds(30, 0, 1140, 115);
		// <==================== ====================>

		// <===== Cấu trúc của Data Panel =====>
		// - Tuỳ chỉnh Dashboard Type ComboBox
		String[] dashboardTypes = { "Chọn Loại thống kê", "Thống kê Lợi nhuận", "Thống kê Doanh thu",
				"Thống kê Phiếu nhập", };
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
					int yearStart = 2020, yearEnd = 2030;
					if (!subValueSelected.equals("Chọn Mốc thời gian")) {
						// Nếu chọn "Theo năm"
						if (subValueSelected.equals("Theo năm")) {
							// Tính năm 1900 - năm 2100
							for (int year = yearStart; year <= yearEnd; year++) {
								timeDetailsVector.add(String.format("Năm %04d", year));
							}
						}

						// Nếu chọn "Theo tháng"
						if (subValueSelected.equals("Theo tháng")) {
							// Tính năm 1900 - năm 2100
							for (int year = yearStart; year <= yearEnd; year++) {
								// Tính tháng 1 - tháng 12
								for (int month = 1; month <= 12; month++) {
									timeDetailsVector.add(String.format("Tháng %02d/%04d", month, year));
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
		// Tên, kịch thước các cột và mảng chứa dữ liệu tương ứng cho ng loại thống kê
		// - Thống kê Thống kê Lợi nhuận
		// + Tên các cột
		final String[] profitColumns01 = { "Tháng", "Doanh thu (VNĐ)", "Chi (VNĐ)", "Lợi nhuận (VNĐ)" };
		final String[] profitColumns02 = { "Tuần", "Doanh thu (VNĐ)", "Chi (VNĐ)", "Lợi nhuận (VNĐ)" };
		// + Chiều rộng các cột
		final int[] profitWidthColumns = { 280, 240, 240, 350 };
		// - Thống kê Thống kê Doanh thu
		// + Tên các cột
		final String[] revenueColumns01 = { "Tháng", "Số hoá đơn", "Tiền phòng (VNĐ)", "Tiền sản phẩm (VNĐ)",
				"Tiền giảm (VNĐ)", "Doanh thu (VNĐ)" };
		final String[] revenueColumns02 = { "Tuần", "Số hoá đơn", "Tiền phòng (VNĐ)", "Tiền sản phẩm (VNĐ)",
				"Tiền giảm (VNĐ)", "Doanh thu (VNĐ)" };
		// + Chiều rộng các cột
		final int[] revenueWidthColumns = { 280, 110, 170, 170, 170, 210 };
		// - Thống kê Thống kê Phiếu nhập
		// + Tên các cột
		final String[] inputTicketColumns01 = { "Tháng", "Số phiếu nhập", "Tổng số nguyên liệu nhập", "Chi (VNĐ)" };
		final String[] inputTicketColumns02 = { "Tuần", "Số phiếu nhập", "Tổng số nguyên liệu nhập", "Chi (VNĐ)" };
		// + Chiều rộng các cột
		final int[] inputTicketWidthColumns = { 280, 240, 240, 350 };

		// Nếu là lần đầu nhấn vào mục "Thống kê"
		// PHẢI XỬ LÝ LÀ THỐNG KÊ THEO NĂM HIỆN TẠI
//		if (!isFirstClickToReadDashboard) {
//			// Cập nhật lại định dạng và các mục cho Dashboard Type ComboBox
//			dashboardTypeComboBox.setSelectedItem("Thống kê Lợi nhuận");
//			dashboardTypeComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//			dashboardTypeComboBox.setForeground(Color.BLACK);
//
//			// Cập nhật lại định dạng và các mục cho Time Type ComboBox
//			String[] timeTypes = { "Chọn Mốc thời gian", "Theo năm", "Theo tháng" };
//			Vector<String> timeTypesVector = CommonPL.getVectorHasValues(timeTypes);
//			CommonPL.updateComboBox(timeTypeComboBox, timeTypesVector, "Theo năm");
//			timeTypeComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//			timeTypeComboBox.setForeground(Color.BLACK);
//
//			// Cập nhật lại định dạng và các mục cho Time Detail ComboBox
//			Vector<String> timeDetailsVector = new Vector<>();
//			for (int year = 1900; year <= 2100; year++) {
//				timeDetailsVector.add("Năm " + String.valueOf(year));
//			}
//			CommonPL.updateComboBox(timeDetailComboBox, timeDetailsVector,
//					String.valueOf("Năm " + LocalDateTime.now().getYear()));
//			timeDetailComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//			timeDetailComboBox.setForeground(Color.BLACK);
//
//			// Cập nhật lại bảng dữ liệu và thêm vào Data Panel
//			CommonPL.updateTableData(tableData, profitColumns01, profitWidthColumns, profitDatas01);
//			isYearFiltered = true;
//			tableScrollPane.setBounds(15, 60, 1110, 630);
//
//			isFirstClickToReadDashboard = true;
//		}

		// Thay đổi khi nhấn nút "Lọc"
		filterButton.addActionListener(e -> {
			// Lấy ra giá trị được hiện tại của các ComboBox
			String currentDashboardType = String.valueOf(dashboardTypeComboBox.getSelectedItem());
			String currentTimeType = String.valueOf(timeTypeComboBox.getSelectedItem());
			String currentTimeDetail = String.valueOf(timeDetailComboBox.getSelectedItem());

			// Gán tháng và năm tuỳ theo Mốc theo gian
			int month = -1, year = -1;
			if (currentTimeType.equals("Theo năm")) {
				year = Integer.parseInt(currentTimeDetail.split(" ")[1]);
			} else if (currentTimeType.equals("Theo tháng")) {
				month = Integer.parseInt(currentTimeDetail.substring(6, 8));
				year = Integer.parseInt(currentTimeDetail.substring(9));
			}

			// Tuỳ theo mục đã chọn mà in ra bảng thống kê tương ứng
			if (!currentDashboardType.equals("Chọn Loại thống kê") && !currentTimeType.equals("Chọn Mốc thời gian")
					&& !currentTimeDetail.equals("Chọn Thời gian cụ thể")) {
				String[][] times = month != -1 ? CommonPL.getWeeksOfMonth(year, month) : CommonPL.getMonthsOfYear(year);

				if (currentDashboardType.equals("Thống kê Lợi nhuận")) {
					Object[][] profitDatas = new Object[times.length + 1][profitWidthColumns.length];

					BigInteger totalInvest = new BigInteger("0");
					BigInteger totalRevenue = new BigInteger("0");
					BigInteger totalProfit = new BigInteger("0");

					for (int i = 0; i < profitDatas.length - 1; i++) {
						// Truy vấn dữ liệu về Hoá đơn
						ArrayList<OrderDTO> listOrder = orderBLL.getAllOrderByCondition(null,
								String.format("ngayLapHD BETWEEN '%s' AND '%s' AND trangThai = 1",
										CommonPL.convertDateFormat(times[i][1]),
										CommonPL.convertDateFormat(times[i][2])),
								null);
						// Truy vấn dữ liệu về Phiếu nhập
						ArrayList<InputTicketDTO> listInputTicket = inputTicketBLL.getAllInputTicketByCondition(null,
								String.format("ngayLapPN BETWEEN '%s' AND '%s' AND trangThai = 1",
										CommonPL.convertDateFormat(times[i][1]),
										CommonPL.convertDateFormat(times[i][2])),
								null);
						
						// Doanh thu
						BigInteger revenue = new BigInteger("0");
						for (OrderDTO orderDTO : listOrder) {
							revenue = revenue.add(new BigInteger(String.valueOf(orderDTO.getCost())));
						}
						// Chi
						BigInteger invest = new BigInteger("0");
						for (InputTicketDTO inputTicketDTO : listInputTicket) {
							invest = invest.subtract(new BigInteger(String.valueOf(inputTicketDTO.getCost())));
						}
						// Lợi nhuận
						BigInteger profit = revenue.add(invest);
						
						// Gán dữ liệu cho từng ô theo dòng
						profitDatas[i][0] = String.format("%s %02d (%s - %s)", month != -1 ? "Tuần" : "Tháng",
								Integer.parseInt(times[i][0]), times[i][1], times[i][2]);
						profitDatas[i][1] = CommonPL.moneyLongToMoneyFormat(revenue);
						profitDatas[i][2] = CommonPL.moneyLongToMoneyFormat(invest);
						profitDatas[i][3] = CommonPL.moneyLongToMoneyFormat(profit);

						// Tính lại các ô ở dòng "TỔNG"
						totalInvest = totalInvest.add(revenue);
						totalRevenue = totalRevenue.add(invest);
						totalProfit = totalProfit.add(profit);
					}
					profitDatas[profitDatas.length - 1][0] = "TỔNG";
					profitDatas[profitDatas.length - 1][1] = CommonPL.moneyLongToMoneyFormat(totalInvest);
					profitDatas[profitDatas.length - 1][2] = CommonPL.moneyLongToMoneyFormat(totalRevenue);
					profitDatas[profitDatas.length - 1][3] = CommonPL.moneyLongToMoneyFormat(totalProfit);

					if (currentTimeType.equals("Theo năm")) {
						CommonPL.updateTableData(tableData, profitColumns01, profitWidthColumns, profitDatas);
						isYearFiltered = true;
					} else if (currentTimeType.equals("Theo tháng")) {
						CommonPL.updateTableData(tableData, profitColumns02, profitWidthColumns, profitDatas);
						isYearFiltered = false;
					}
				} else if (currentDashboardType.equals("Thống kê Doanh thu")) {
					Object[][] revenueDatas = new Object[times.length + 1][revenueWidthColumns.length];

					BigInteger totalBill = new BigInteger("0");
					BigInteger totalRoomCost = new BigInteger("0");
					BigInteger totalProductCost = new BigInteger("0");
					BigInteger totalDiscountCost = new BigInteger("0");
					BigInteger totalRevenue = new BigInteger("0");

					for (int i = 0; i < revenueDatas.length - 1; i++) {
						// Truy vấn dữ liệu về Hoá đơn
						ArrayList<OrderDTO> listOrder = orderBLL.getAllOrderByCondition(null,
								String.format("ngayLapHD BETWEEN '%s' AND '%s' AND trangThai = 1",
										CommonPL.convertDateFormat(times[i][1]),
										CommonPL.convertDateFormat(times[i][2])),
								null);

						// Số hoá đơn
						BigInteger bill = new BigInteger(String.valueOf(listOrder.size()));
						// Tổng tiền phòng
						BigInteger roomCost = new BigInteger("0");
						for (OrderDTO orderDTO : listOrder) {
							ArrayList<RoomTypeDTO> listRoomType = roomTypeBLL.getAllRoomTypeByCondition(
									new String[] { "JOIN Karaoke.Phong ON LoaiPhong.maLoaiPhong = Phong.maLoaiPhong" },
									String.format("maPhong = '%s'", orderDTO.getRoomId()), null);
							roomCost = roomCost.add(
									new BigInteger(String.valueOf(listRoomType.get(0).getCost() * orderDTO.getTime())));
						}
						// Tổng tiền sản phẩm
						BigInteger productCost = new BigInteger("0");
						for (OrderDTO orderDTO : listOrder) {
							ArrayList<OrderDetailDTO> listOrderDetail = orderDetailBLL.getAllOrderDetailByCondition(
									null, String.format("maHoaDon = '%s'", orderDTO.getId()), null);
							for (OrderDetailDTO orderDetailDTO : listOrderDetail) {
								ProductDTO productDTO = productBLL.getOneProductById(orderDetailDTO.getProductId());
								productCost = productCost.add(new BigInteger(
										String.valueOf(productDTO.getPrice() * orderDetailDTO.getQuantity())));
							}
						}
						// Tổng tiền giảm giá
						BigInteger discountCost = new BigInteger("0");
						for (OrderDTO orderDTO : listOrder) {
							DiscountDTO discountDTO = discountBLL.getOneDiscountById(orderDTO.getDiscountId());
							if (discountDTO.getDiscountType().equals("PHANTRAM")) {
								BigDecimal currentOrderBill = new BigDecimal(roomCost.add(productCost));
								BigDecimal percent = new BigDecimal(
										String.valueOf(-1.0 * discountDTO.getValue() / 100));
								discountCost = currentOrderBill.multiply(percent).toBigInteger();

							} else {
								discountCost = discountCost
										.subtract(new BigInteger(String.valueOf(discountDTO.getValue())));
							}
						}
						// Doanh thu
						BigInteger revenue = new BigInteger("0");
//						revenue = revenue.add(roomCost);
//						revenue = revenue.add(productCost);
//						revenue = revenue.add(discountCost);
						for (OrderDTO orderDTO : listOrder) {
							revenue = revenue.add(new BigInteger(String.valueOf(orderDTO.getCost())));
						}
						
						// Gán dữ liệu cho từng ô theo dòng
						revenueDatas[i][0] = String.format("%s %02d (%s - %s)", month != -1 ? "Tuần" : "Tháng",
								Integer.parseInt(times[i][0]), times[i][1], times[i][2]);
						revenueDatas[i][1] = bill;
						revenueDatas[i][2] = CommonPL.moneyLongToMoneyFormat(roomCost);
						revenueDatas[i][3] = CommonPL.moneyLongToMoneyFormat(productCost);
						revenueDatas[i][4] = CommonPL.moneyLongToMoneyFormat(discountCost);
						revenueDatas[i][5] = CommonPL.moneyLongToMoneyFormat(revenue);

						// Tính lại các ô ở dòng "TỔNG"
						totalBill = totalBill.add(bill);
						totalRoomCost = totalRoomCost.add(roomCost);
						totalProductCost = totalProductCost.add(productCost);
						totalDiscountCost = totalDiscountCost.add(discountCost);
						totalRevenue = totalRevenue.add(revenue);
					}
					revenueDatas[revenueDatas.length - 1][0] = "TỔNG";
					revenueDatas[revenueDatas.length - 1][1] = totalBill;
					revenueDatas[revenueDatas.length - 1][2] = CommonPL.moneyLongToMoneyFormat(totalRoomCost);
					revenueDatas[revenueDatas.length - 1][3] = CommonPL.moneyLongToMoneyFormat(totalProductCost);
					revenueDatas[revenueDatas.length - 1][4] = CommonPL.moneyLongToMoneyFormat(totalDiscountCost);
					revenueDatas[revenueDatas.length - 1][5] = CommonPL.moneyLongToMoneyFormat(totalRevenue);

					if (currentTimeType.equals("Theo năm")) {
						CommonPL.updateTableData(tableData, revenueColumns01, revenueWidthColumns, revenueDatas);
						isYearFiltered = true;
					} else if (currentTimeType.equals("Theo tháng")) {
						CommonPL.updateTableData(tableData, revenueColumns02, revenueWidthColumns, revenueDatas);
						isYearFiltered = false;
					}
				} else if (currentDashboardType.equals("Thống kê Phiếu nhập")) {
					Object[][] inputTicketDatas = new Object[times.length + 1][inputTicketWidthColumns.length];

					BigInteger totalTicket = new BigInteger("0");
					BigInteger totalIngredient = new BigInteger("0");
					BigInteger totalInvest = new BigInteger("0");

					for (int i = 0; i < inputTicketDatas.length - 1; i++) {
						// Truy vấn dữ liệu về Phiếu nhập
						ArrayList<InputTicketDTO> listInputTicket = inputTicketBLL.getAllInputTicketByCondition(null,
								String.format("ngayLapPN BETWEEN '%s' AND '%s' AND trangThai = 1",
										CommonPL.convertDateFormat(times[i][1]),
										CommonPL.convertDateFormat(times[i][2])),
								null);
						
						// Số phiếu nhập
						BigInteger ticket = new BigInteger(String.valueOf(listInputTicket.size()));
						// Tổng số nguyên liệu
						BigInteger ingredients = new BigInteger("0");
						for (InputTicketDTO inputTicketDTO : listInputTicket) {
							ArrayList<InputTicketDetailDTO> listInputTicketDetail = inputTicketDetailBLL.getAllInputTicketDetailByCondition(
									null, String.format("maPhieuNhap = '%s'", inputTicketDTO.getId()), null);
							for(InputTicketDetailDTO inputTicketDetailDTO : listInputTicketDetail) {								
								ingredients = ingredients.add(new BigInteger(String.valueOf(inputTicketDetailDTO.getInputQuantity())));
							}
						}
						// Chi
						BigInteger invest = new BigInteger("0");
						for (InputTicketDTO inputTicketDTO : listInputTicket) {
							invest = invest.subtract(new BigInteger(String.valueOf(inputTicketDTO.getCost())));
						}
						
						// Gán dữ liệu cho từng ô theo dòng
						inputTicketDatas[i][0] = String.format("%s %02d (%s - %s)", month != -1 ? "Tuần" : "Tháng",
								Integer.parseInt(times[i][0]), times[i][1], times[i][2]);
						inputTicketDatas[i][1] = ticket;
						inputTicketDatas[i][2] = ingredients;
						inputTicketDatas[i][3] = CommonPL.moneyLongToMoneyFormat(invest);

						// Tính lại các ô ở dòng "TỔNG"
						totalTicket = totalTicket.add(ticket);
						totalIngredient = totalIngredient.add(ingredients);
						totalInvest = totalInvest.add(invest);
					}
					
					// Gán dự liệu cho từng ô ở dòng cuối cùng
					inputTicketDatas[inputTicketDatas.length - 1][0] = "TỔNG";
					inputTicketDatas[inputTicketDatas.length - 1][1] = totalTicket;
					inputTicketDatas[inputTicketDatas.length - 1][2] = totalIngredient;
					inputTicketDatas[inputTicketDatas.length - 1][3] = CommonPL.moneyLongToMoneyFormat(totalInvest);

					if (currentTimeType.equals("Theo năm")) {
						CommonPL.updateTableData(tableData, inputTicketColumns01, inputTicketWidthColumns,
								inputTicketDatas);
						isYearFiltered = true;
					} else if (currentTimeType.equals("Theo tháng")) {
						CommonPL.updateTableData(tableData, inputTicketColumns02, inputTicketWidthColumns,
								inputTicketDatas);
						isYearFiltered = false;
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
