package PL;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import BLL.CommonBLL;
import BLL.CustomerBLL;
import BLL.CustomerTypeBLL;
import BLL.FoodBLL;
import BLL.InputTicketBLL;
import BLL.InputTicketDetailBLL;
import BLL.OrderBLL;
import BLL.OrderDetailBLL;
import BLL.RoomBLL;
import BLL.RoomTypeBLL;
import DTO.InputTicketDTO;
import DTO.InputTicketDetailDTO;
import DTO.OrderDTO;
import DTO.OrderDetailDTO;
import DTO.RoomTypeDTO;
import DTO.FoodDTO;

public class Admin_DashboardManagerPL extends JPanel {
    // Các đối tượng ở tầng Bussiness Logical Layer
    private OrderBLL orderBLL;
    private OrderDetailBLL orderDetailBLL;
    private InputTicketBLL inputTicketBLL;
    private InputTicketDetailBLL inputTicketDetailBLL;
    private RoomBLL roomBLL;
    private RoomTypeBLL roomTypeBLL;
    private CustomerBLL customerBLL;
    private CustomerTypeBLL customerTypeBLL;
    private FoodBLL foodBLL;
    // Các Font
    private Font fontComboBox = new Font("Arial", Font.PLAIN, 14);
    private Font fontButton = new Font("Arial", Font.BOLD, 14);
    // Các Component
    private JLabel titleLabel;
    // - Các Component của Data Panel
    private JComboBox<String> dashboardTypeComboBox;
    private JComboBox<String> timelineComboBox;
    private JComboBox<String> timeDetailComboBox;
    private JButton filterButton;
    private JButton printButton;
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
    // + Biến đảm bảo sẽ hiện defaultTypes.get("profit") khi nhấn vào mục "Thống kê"
    private boolean isFirstClickToReadDashboard = false;
    // + Nếu lọc defaultTimelines.get("year") sẽ có chiều cao bảng khác
    private boolean isYearFiltered = false;

    // - Các thông tin cần thiết cho thống kê
    // + Các mục để lọc
    private final Map<String, String> defaultSelects = new LinkedHashMap<String, String>() {
        {
            put("type", "Chọn Loại thống kê");
            put("timeline", "Chọn Mốc thời gian");
            put("timeDetail", "Chọn Thời gian cụ thể");
        }
    };
    // + Các loại thống kê
    private final Map<String, String> defaultTypes = new LinkedHashMap<String, String>() {
        {
            put("profit", "Thống kê Lợi nhuận");
            put("revenue", "Thống kê Doanh thu");
            put("invest", "Thống kê Chi tiêu");
        }
    };
    // + Các mốc thời gian
    private final Map<String, String> defaultTimelines = new LinkedHashMap<String, String>() {
        {
            put("year", "Theo năm");
            put("month", "Theo tháng");
        }
    };

    public Admin_DashboardManagerPL() {
        // <===== Các đối tượng từ tầng Bussiness Logical Layer =====>
        orderBLL = new OrderBLL();
        orderDetailBLL = new OrderDetailBLL();
        inputTicketBLL = new InputTicketBLL();
        inputTicketDetailBLL = new InputTicketDetailBLL();
        roomBLL = new RoomBLL();
        roomTypeBLL = new RoomTypeBLL();
        customerBLL = new CustomerBLL();
        customerTypeBLL = new CustomerTypeBLL();
        foodBLL = new FoodBLL();
        // <==================== ====================>

        // <===== Cấu trúc của Title Label =====>
        // - Tuỳ chỉnh Title Label
        titleLabel = CommonPL.getTitleLabel("Thống kê", Color.BLACK,
                CommonPL.getFontTitle(), SwingConstants.CENTER,
                SwingConstants.CENTER);
        titleLabel.setBounds(30, 0, 1140, 115);
        // <==================== ====================>

        // <===== Cấu trúc của Data Panel =====>
        // - Tuỳ chỉnh Dashboard Type ComboBox
        String[] dashboardTypes = { defaultSelects.get("type"), defaultTypes.get("profit"),
                defaultTypes.get("revenue"),
                defaultTypes.get("invest"), };
        Vector<String> dashboardTypesVector = CommonPL.getVectorHasValues(dashboardTypes);
        dashboardTypeComboBox = CommonPL.CustomComboBox(dashboardTypesVector,
                Color.WHITE, Color.LIGHT_GRAY,
                Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK,
                fontComboBox);
        dashboardTypeComboBox.setBounds(15, 15, 200, 30);

        // - Tuỳ chỉnh Time Type ComboBox
        String[] timeTypes = { defaultSelects.get("timeline") };
        Vector<String> timeTypesVector = CommonPL.getVectorHasValues(timeTypes);
        timelineComboBox = CommonPL.CustomComboBox(timeTypesVector, Color.WHITE,
                Color.LIGHT_GRAY, Color.BLACK,
                Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontComboBox);
        timelineComboBox.setBounds(230, 15, 200, 30);

        // - Tuỳ chỉnh Time Detail Type ComboBox
        String[] timeDetails = { defaultSelects.get("timeDetail") };
        Vector<String> timeDetailsVector = CommonPL.getVectorHasValues(timeDetails);
        timeDetailComboBox = CommonPL.CustomComboBox(timeDetailsVector, Color.WHITE,
                Color.LIGHT_GRAY, Color.BLACK,
                Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontComboBox);
        timeDetailComboBox.setBounds(445, 15, 200, 30);

        // - Tuỳ chỉnh Filter Button
        filterButton = CommonPL.getRoundedBorderButton(14, "Lọc",
                Color.decode("#007bff"), Color.WHITE,
                new Font("Arial", Font.BOLD, 14));
        filterButton.setBounds(660, 15, 100, 30);

        // - Tuỳ chỉnh Print Button
        printButton = CommonPL.getRoundedBorderButton(14, "In phiếu thống kê",
                Color.decode("#699f4c"), Color.WHITE,
                new Font("Arial", Font.BOLD, 14));
        printButton.setBounds(925, 15, 200, 30);

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
        dataPanel.add(timelineComboBox);
        dataPanel.add(timeDetailComboBox);
        dataPanel.add(filterButton);
        dataPanel.add(printButton);
        dataPanel.add(tableScrollPane);
        // <==================== ====================>

        // Đĩnh nghĩa tính chất cho Dashboard Manager PL
        this.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(),
                CommonPL.getScreenHeightByOwner());
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

        // Thiết lập sự kiện in phiếu thống kê
        printDashboardTicket();
    }

    // Hàm thay đổi các mục của các ComboBox để thống kê
    private void renderAllComboBoxToUpdateDashboard() {
        // Vector chứa các mốc thời gian
        Vector<String> timeTypesVector = new Vector<>();
        // Vector chứa các thời gian cụ thể
        Vector<String> timeDetailsVector = new Vector<>();

        // Sự kiện khi chọn "Loại thống kê"
        dashboardTypeComboBox.addActionListener(e -> {
            // Lấy ra giá trị được chọn hiện tại của Dashboard Type ComboBox
            String valueSelected = (String) dashboardTypeComboBox.getSelectedItem();

            // Xoá các giá trị hiện tại của các Vector
            timeTypesVector.clear();
            timeDetailsVector.clear();

            // Thêm giá trị mặc định cho các Vector
            timeTypesVector.add(defaultSelects.get("timeline"));
            timeDetailsVector.add(defaultSelects.get("timeDetail"));

            // Nếu giá trị hiện tại không là defaultSelects.get("type")
            if (!valueSelected.equals(defaultSelects.get("type"))) {
                // Xử lý Time Types Vector
                timeTypesVector.add(defaultTimelines.get("year"));
                timeTypesVector.add(defaultTimelines.get("month"));
                CommonPL.updateComboBox(timelineComboBox, timeTypesVector, null);

                // Sự kiện khi chọn "Mốc thời gian"
                timelineComboBox.addActionListener(subE -> {
                    // Lấy ra giá trị được chọn hiện tại của Time Type ComboBox
                    String subValueSelected = (String) timelineComboBox.getSelectedItem();

                    // Xoá các giá trị hiện tại của Time Details Vector
                    timeDetailsVector.clear();

                    // Thêm giá trị mặc định cho Time Details Vector
                    timeDetailsVector.add(defaultSelects.get("timeDetail"));

                    // Nếu giá trị hiện tại không là defaultSelects.get("timeline")
                    int yearStart = 2020, yearEnd = 2030;
                    if (!subValueSelected.equals(defaultSelects.get("timeline"))) {
                        // Nếu chọn defaultTimelines.get("year")
                        if (subValueSelected.equals(defaultTimelines.get("year"))) {
                            // Tính năm 1900 - năm 2100
                            for (int year = yearStart; year <= yearEnd; year++) {
                                timeDetailsVector.add(String.format("Năm %04d", year));
                            }
                        }

                        // Nếu chọn defaultTimelines.get("month")
                        if (subValueSelected.equals(defaultTimelines.get("month"))) {
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
                    timeDetailComboBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,
                            1));
                    timeDetailComboBox.setForeground(Color.LIGHT_GRAY);
                });
            }

            // Cập nhật lại định dạng và các mục cho ComboBox
            CommonPL.updateComboBox(timelineComboBox, timeTypesVector, null);
            timelineComboBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,
                    1));
            timelineComboBox.setForeground(Color.LIGHT_GRAY);
            CommonPL.updateComboBox(timeDetailComboBox, timeDetailsVector, null);
            timeDetailComboBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,
                    1));
            timeDetailComboBox.setForeground(Color.LIGHT_GRAY);
        });
    }

    // Hàm thay đổi lại Table Data và Table Scroll Pane khi "lọc thống kê"
    private void renderTableAfterFiltered() {
        // Tên, kịch thước các cột và mảng chứa dữ liệu tương ứng cho ng loại thống kê
        // - Thống kê Thống kê Lợi nhuận
        // + Tên các cột
        final String[] profitColumns01 = { "Tháng", "Doanh thu (VNĐ)", "Chi tiêu (VNĐ)",
                "Lợi nhuận (VNĐ)" };
        final String[] profitColumns02 = { "Tuần", "Doanh thu (VNĐ)", "Chi tiêu (VNĐ)",
                "Lợi nhuận (VNĐ)" };
        // + Chiều rộng các cột
        final int[] profitWidthColumns = { 280, 240, 240, 350 };
        // - Thống kê Thống kê Doanh thu
        // + Tên các cột
        final String[] revenueColumns01 = { "Tháng", "Số hoá đơn", "Tổng giờ hát", "Doanh thu (VNĐ)" };
        final String[] revenueColumns02 = { "Tuần", "Số hoá đơn", "Tổng giờ hát", "Doanh thu (VNĐ)" };
        // + Chiều rộng các cột
        final int[] revenueWidthColumns = { 280, 240, 240, 350 };
        // - Thống kê Thống kê Phiếu nhập
        // + Tên các cột
        final String[] inputTicketColumns01 = { "Tháng", "Số phiếu nhập", "Tổng món ăn", "Chi tiêu (VNĐ)" };
        final String[] inputTicketColumns02 = { "Tuần", "Số phiếu nhập", "Tổng món ăn", "Chi tiêu (VNĐ)" };
        // + Chiều rộng các cột
        final int[] inputTicketWidthColumns = { 280, 240, 240, 350 };

        // Nếu là lần đầu nhấn vào mục "Thống kê"
        // PHẢI XỬ LÝ LÀ THỐNG KÊ THEO NĂM HIỆN TẠI
        // if (!isFirstClickToReadDashboard) {
        // // Cập nhật lại định dạng và các mục cho Dashboard Type ComboBox
        // dashboardTypeComboBox.setSelectedItem(defaultTypes.get("profit"));
        //
        // dashboardTypeComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK,
        // 1));
        // dashboardTypeComboBox.setForeground(Color.BLACK);
        //
        // // Cập nhật lại định dạng và các mục cho Time Type ComboBox
        // String[] timeTypes = { defaultSelects.get("timeline"),
        // defaultTimelines.get("year"),
        // defaultTimelines.get("month") };
        // Vector<String> timeTypesVector = CommonPL.getVectorHasValues(timeTypes);
        // CommonPL.updateComboBox(timelineComboBox, timeTypesVector,
        // defaultTimelines.get("year"));
        // timelineComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        // timelineComboBox.setForeground(Color.BLACK);
        //
        // // Cập nhật lại định dạng và các mục cho Time Detail ComboBox
        // Vector<String> timeDetailsVector = new Vector<>();
        // for (int year = 1900; year <= 2100; year++) {
        // timeDetailsVector.add("Năm " + String.valueOf(year));
        // }
        // CommonPL.updateComboBox(timeDetailComboBox, timeDetailsVector,
        // String.valueOf("Năm " + LocalDateTime.now().getYear()));
        // timeDetailComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        // timeDetailComboBox.setForeground(Color.BLACK);
        //
        // // Cập nhật lại bảng dữ liệu và thêm vào Data Panel
        // CommonPL.updateTableData(tableData, profitColumns01, profitWidthColumns,
        // profitDatas01);
        // isYearFiltered = true;
        // tableScrollPane.setBounds(15, 60, 1110, 630);
        //
        // isFirstClickToReadDashboard = true;
        // }

        // Thay đổi khi nhấn nút "Lọc"
        filterButton.addActionListener(e -> {
            // Lấy ra giá trị được hiện tại của các ComboBox
            String currentDashboardType = String.valueOf(dashboardTypeComboBox.getSelectedItem());
            String currentTimeType = String.valueOf(timelineComboBox.getSelectedItem());
            String currentTimeDetail = String.valueOf(timeDetailComboBox.getSelectedItem());

            // Gán tháng và năm tuỳ theo Mốc theo gian
            int month = -1, year = -1;
            if (currentTimeType.equals(defaultTimelines.get("year"))) {
                year = Integer.parseInt(currentTimeDetail.split(" ")[1]);
            } else if (currentTimeType.equals(defaultTimelines.get("month"))) {
                month = Integer.parseInt(currentTimeDetail.substring(6, 8));
                year = Integer.parseInt(currentTimeDetail.substring(9));
            }

            // Tuỳ theo mục đã chọn mà in ra bảng thống kê tương ứng
            if (!currentDashboardType.equals(defaultSelects.get("type")) &&
                    !currentTimeType.equals(defaultSelects.get("timeline"))
                    && !currentTimeDetail.equals(defaultSelects.get("timeDetail"))) {
                String[][] times = month != -1 ? CommonPL.getWeeksOfMonth(year, month) : CommonPL.getMonthsOfYear(year);

                if (currentDashboardType.equals(defaultTypes.get("profit"))) {
                    datas = new Object[times.length + 1][profitWidthColumns.length];

                    BigInteger totalInvest = new BigInteger("0");
                    BigInteger totalRevenue = new BigInteger("0");
                    BigInteger totalProfit = new BigInteger("0");

                    for (int i = 0; i < datas.length - 1; i++) {
                        // Truy vấn dữ liệu về Hoá đơn
                        ArrayList<OrderDTO> listOrder = orderBLL.getAllOrderByCondition(null,
                                String.format(
                                        "thoiGianTaoDon BETWEEN '%s 00:00:00' AND '%s 23:59:59' AND trangThai = 2",
                                        CommonPL.convertDateFormat(times[i][1]),
                                        CommonPL.convertDateFormat(times[i][2])),
                                null);
                        // Truy vấn dữ liệu về Phiếu nhập
                        ArrayList<InputTicketDTO> listInputTicket = inputTicketBLL.getAllInputTicketByCondition(null,
                                String.format(
                                        "thoiGianTaoPhieu BETWEEN '%s 00:00:00' AND '%s 23:59:59' AND trangThai = 2",
                                        CommonPL.convertDateFormat(times[i][1]),
                                        CommonPL.convertDateFormat(times[i][2])),
                                null);

                        // Doanh thu
                        BigInteger revenue = new BigInteger("0");
                        for (OrderDTO orderDTO : listOrder) {
                            revenue = revenue.add(new BigInteger(String.valueOf(orderDTO.getTotalPrice())));
                        }
                        // Chi tiêu
                        BigInteger invest = new BigInteger("0");
                        for (InputTicketDTO inputTicketDTO : listInputTicket) {
                            invest = invest.subtract(new BigInteger(String.valueOf(inputTicketDTO.getTotalPrice())));
                        }
                        // Lợi nhuận
                        BigInteger profit = revenue.add(invest);

                        // Gán dữ liệu cho từng ô theo dòng
                        datas[i][0] = String.format("%s %02d (%s - %s)", month != -1 ? "Tuần" : "Tháng",
                                Integer.parseInt(times[i][0]), times[i][1], times[i][2]);
                        datas[i][1] = CommonPL.moneyLongToMoneyFormat(revenue);
                        datas[i][2] = CommonPL.moneyLongToMoneyFormat(invest);
                        datas[i][3] = CommonPL.moneyLongToMoneyFormat(profit);

                        // Tính lại các ô ở dòng "TỔNG"
                        totalInvest = totalInvest.add(revenue);
                        totalRevenue = totalRevenue.add(invest);
                        totalProfit = totalProfit.add(profit);
                    }
                    datas[datas.length - 1][0] = "TỔNG";
                    datas[datas.length - 1][1] = CommonPL.moneyLongToMoneyFormat(totalInvest);
                    datas[datas.length - 1][2] = CommonPL.moneyLongToMoneyFormat(totalRevenue);
                    datas[datas.length - 1][3] = CommonPL.moneyLongToMoneyFormat(totalProfit);

                    if (currentTimeType.equals(defaultTimelines.get("year"))) {
                        CommonPL.updateTableData(tableData, profitColumns01, profitWidthColumns,
                                datas);
                        isYearFiltered = true;
                    } else if (currentTimeType.equals(defaultTimelines.get("month"))) {
                        CommonPL.updateTableData(tableData, profitColumns02, profitWidthColumns,
                                datas);
                        isYearFiltered = false;
                    }
                } else if (currentDashboardType.equals(defaultTypes.get("revenue"))) {
                    datas = new Object[times.length + 1][revenueWidthColumns.length];

                    BigInteger totalBill = new BigInteger("0");
                    BigInteger totalTime = new BigInteger("0");
                    BigInteger totalRevenue = new BigInteger("0");

                    for (int i = 0; i < datas.length - 1; i++) {
                        // Truy vấn dữ liệu về Hoá đơn
                        ArrayList<OrderDTO> listOrder = orderBLL.getAllOrderByCondition(null,
                                String.format(
                                        "thoiGianTaoDon BETWEEN '%s 00:00:00' AND '%s 23:59:59' AND trangThai = 2",
                                        CommonPL.convertDateFormat(times[i][1]),
                                        CommonPL.convertDateFormat(times[i][2])),
                                null);

                        // Số hoá đơn
                        BigInteger bill = new BigInteger(String.valueOf(listOrder.size()));
                        // Tổng giờ hát
                        BigInteger time = new BigInteger("0");
                        for (OrderDTO orderDTO : listOrder) {
                            time = time.add(BigInteger.valueOf(
                                    CommonBLL.calHoursBetweenTwoTimes(orderDTO.getTimeStart(), orderDTO.getTimeEnd())));
                        }
                        // Doanh thu
                        BigInteger revenue = new BigInteger("0");
                        for (OrderDTO orderDTO : listOrder) {
                            revenue = revenue.add(new BigInteger(String.valueOf(orderDTO.getTotalPrice())));
                        }

                        // Gán dữ liệu cho từng ô theo dòng
                        datas[i][0] = String.format("%s %02d (%s - %s)", month != -1 ? "Tuần"
                                : "Tháng",
                                Integer.parseInt(times[i][0]), times[i][1], times[i][2]);
                        datas[i][1] = bill;
                        datas[i][2] = time;
                        datas[i][3] = CommonPL.moneyLongToMoneyFormat(revenue);

                        // Tính lại các ô ở dòng "TỔNG"
                        totalBill = totalBill.add(bill);
                        totalTime = totalTime.add(time);
                        totalRevenue = totalRevenue.add(revenue);
                    }
                    datas[datas.length - 1][0] = "TỔNG";
                    datas[datas.length - 1][1] = totalBill;
                    datas[datas.length - 1][2] = totalTime;
                    datas[datas.length - 1][3] = CommonPL.moneyLongToMoneyFormat(totalRevenue);

                    if (currentTimeType.equals(defaultTimelines.get("year"))) {
                        CommonPL.updateTableData(tableData, revenueColumns01, revenueWidthColumns,
                                datas);
                        isYearFiltered = true;
                    } else if (currentTimeType.equals(defaultTimelines.get("month"))) {
                        CommonPL.updateTableData(tableData, revenueColumns02, revenueWidthColumns,
                                datas);
                        isYearFiltered = false;
                    }
                } else if (currentDashboardType.equals(defaultTypes.get("invest"))) {
                    datas = new Object[times.length + 1][inputTicketWidthColumns.length];

                    BigInteger totalTicket = new BigInteger("0");
                    BigInteger totalFood = new BigInteger("0");
                    BigInteger totalInvest = new BigInteger("0");

                    for (int i = 0; i < datas.length - 1; i++) {
                        // Truy vấn dữ liệu về Phiếu nhập
                        ArrayList<InputTicketDTO> listInputTicket = inputTicketBLL.getAllInputTicketByCondition(null,
                                String.format(
                                        "thoiGianTaoPhieu BETWEEN '%s 00:00:00' AND '%s 23:59:59' AND trangThai = 2",
                                        CommonPL.convertDateFormat(times[i][1]),
                                        CommonPL.convertDateFormat(times[i][2])),
                                null);

                        // Số phiếu nhập
                        BigInteger ticket = new BigInteger(String.valueOf(listInputTicket.size()));
                        // Tổng số món ăn
                        BigInteger food = new BigInteger("0");
                        for (InputTicketDTO inputTicketDTO : listInputTicket) {
                            ArrayList<InputTicketDetailDTO> listInputTicketDetail = inputTicketDetailBLL
                                    .getAllInputTicketDetailByCondition(
                                            null, String.format("maPhieuNhap = '%s'", inputTicketDTO.getId()), null);
                            for (InputTicketDetailDTO inputTicketDetailDTO : listInputTicketDetail) {
                                food = food.add(new BigInteger(String.valueOf(inputTicketDetailDTO.getQuantity())));
                            }
                        }
                        // Chi tiêu
                        BigInteger invest = new BigInteger("0");
                        for (InputTicketDTO inputTicketDTO : listInputTicket) {
                            invest = invest.subtract(new BigInteger(String.valueOf(inputTicketDTO.getTotalPrice())));
                        }

                        // Gán dữ liệu cho từng ô theo dòng
                        datas[i][0] = String.format("%s %02d (%s - %s)", month != -1 ? "Tuần" : "Tháng",
                                Integer.parseInt(times[i][0]), times[i][1], times[i][2]);
                        datas[i][1] = ticket;
                        datas[i][2] = food;
                        datas[i][3] = CommonPL.moneyLongToMoneyFormat(invest);

                        // Tính lại các ô ở dòng "TỔNG"
                        totalTicket = totalTicket.add(ticket);
                        totalFood = totalFood.add(food);
                        totalInvest = totalInvest.add(invest);
                    }

                    // Gán dự liệu cho từng ô ở dòng cuối cùng
                    datas[datas.length - 1][0] = "TỔNG";
                    datas[datas.length - 1][1] = totalTicket;
                    datas[datas.length - 1][2] = totalFood;
                    datas[datas.length - 1][3] = CommonPL.moneyLongToMoneyFormat(totalInvest);

                    if (currentTimeType.equals(defaultTimelines.get("year"))) {
                        CommonPL.updateTableData(tableData, inputTicketColumns01,
                                inputTicketWidthColumns,
                                datas);
                        isYearFiltered = true;
                    } else if (currentTimeType.equals(defaultTimelines.get("month"))) {
                        CommonPL.updateTableData(tableData, inputTicketColumns02,
                                inputTicketWidthColumns,
                                datas);
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

    // Hàm in phiếu
    private void printDashboardTicket() {
        printButton.addActionListener(e -> {
            String dashboardType = String.valueOf(dashboardTypeComboBox.getSelectedItem());
            String timeline = String.valueOf(timelineComboBox.getSelectedItem());
            String timeDetail = String.valueOf(timeDetailComboBox.getSelectedItem());

            if (!dashboardType.equals(defaultSelects.get("type")) && !timeline.equals(defaultSelects.get("timeline"))
                    && !timeDetail.equals(defaultSelects.get("timeDetail"))) {
                String title = dashboardType;
                Map<String, String> infos = new LinkedHashMap<>() {
                    {

                    }
                };
                String descTable = "Dữ liệu thống kê";
                Map<String, String> headTables = new LinkedHashMap<>() {
                    {
                        put(timeline.equals(defaultTimelines.get("year")) ? "Tháng" : "Tuần", "36%");
                        put(dashboardType.equals(defaultTypes.get("profit")) ? "Doanh thu (VNĐ)"
                                : dashboardType.equals(defaultTypes.get("revenue")) ? "Số hoá đơn" : "Số phiếu nhập",
                                "18%");
                        put(dashboardType.equals(defaultTypes.get("profit")) ? "Chi tiêu (VNĐ)"
                                : dashboardType.equals(defaultTypes.get("revenue")) ? "Tổng giờ hát"
                                        : "Tổng món ăn nhập",
                                "18%");
                        put(dashboardType.equals(defaultTypes.get("profit")) ? "Lợi nhuận (VNĐ)"
                                : dashboardType.equals(defaultTypes.get("revenue")) ? "Doanh thu (VNĐ)"
                                        : "Chi tiêu (VNĐ)",
                                "28%");
                    }
                };

                CommonPL.printTicket(title, infos, descTable, headTables, datas);
            } else {
                CommonPL.createErrorDialog("Thông báo lỗi", "Cần chọn thông tin đầy đủ để in phiếu");
            }
        });

    }
}
