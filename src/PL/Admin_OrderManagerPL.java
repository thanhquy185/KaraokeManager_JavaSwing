package PL;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
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
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import BLL.AccountBLL;
import BLL.CustomerBLL;
import BLL.CustomerTypeBLL;
import BLL.OrderBLL;
import BLL.OrderDetailBLL;
import BLL.PrivilegeDetailBLL;
import BLL.RoomBLL;
import BLL.RoomTypeBLL;
import DTO.AccountDTO;
import DTO.CustomerDTO;
import DTO.CustomerTypeDTO;
import DTO.OrderDTO;
import DTO.OrderDetailDTO;
import DTO.FoodDTO;
import DTO.RoomDTO;
import DTO.RoomTypeDTO;
import PL.CommonPL.CustomTextField;
import BLL.FoodBLL;

public class Admin_OrderManagerPL extends JPanel {
        private OrderBLL orderBLL;
        private OrderDetailBLL orderDetailBLL;
        private FoodBLL foodBLL;
        private RoomBLL roomBLL;
        private RoomTypeBLL roomTypeBLL;
        private AccountBLL accountBLL;
        private CustomerBLL customerBLL;
        private CustomerTypeBLL customerTypeBLL;

        private JLabel titleLabel;
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
        private JLabel statusLabel;
        private Map<String, Boolean> statusRadios;
        private JButton statusButton;
        private JButton filterApplyButton;
        private JButton filterResetButton;
        private JPanel filterPanel;
        private JButton addButton;
        private JButton updateButton;
        private JButton printButton;
        private JTable tableData;
        private JScrollPane tableScrollPane;
        private JPanel dataPanel;

        private final String[] columns = { "Mã đơn", "Thời gian tạo đơn", "Thời gian bắt đầu", "Thời gian kết thúc",
                        "Phòng hát", "Nhân viên tạo đơn",
                        "Khách hàng", "Tổng tiền hát (VNĐ)", "Trạng thái" };
        private final int[] widthColumns = { 150, 200, 200, 200, 300, 300, 300, 400, 200 };
        private Object[][] datas = {};
        private int rowSelected = -1;
        private Boolean[] valueSelected = { null };

        private JLabel addOrUpdateIdLabel;
        private JTextField addOrUpdateIdTextField;
        private JLabel addOrUpdateTimeCreateLabel;
        private JTextField addOrUpdateTimeCreateTextField;
        private JLabel addOrUpdateTimeStartLabel;
        private JTextField addOrUpdateTimeStartTextField;
        private JLabel addOrUpdateTimeEndLabel;
        private JTextField addOrUpdateTimeEndTextField;
        private JLabel addOrUpdateRoomLabel;
        private JComboBox<String> addOrUpdateRoomComboBox;
        private JLabel addOrUpdateEmployeeLabel;
        private JTextField addOrUpdateEmployeeTextField;
        private JLabel addOrUpdateCustomerLabel;
        private JComboBox<String> addOrUpdateCustomerComboBox;
        private JLabel addOrUpdateTotalPriceLabel;
        private JTextField addOrUpdateTotalPriceTextField;
        private JLabel addOrUpdateStatusLabel;
        private JTextField addOrUpdateStatusTextField;
        private JButton addOrUpdateCompleteButton;
        private JButton addOrUpdateDeleteButton;
        private JButton addOrUpdateButton;
        private JLabel addOrUpdateDataLabel;

        private JButton addOrUpdateAddUnitButton;
        private JButton addOrUpdateDeleteUnitButton;
        private JTable addOrUpdateTableData;
        private JScrollPane addOrUpdateTableScrollPane;
        private JPanel addOrUpdateBlockPanel;
        private JDialog addOrUpdateDialog;

        private final String[] addOrUpdateColumns = { "Mã món ăn", "Tên món ăn", "Giá bán (VNĐ)", "Số lượng",
                        "Thành tiền (VNĐ)" };
        private final int[] addOrUpdateWidthColumns = { 150, 400, 200, 200, 300 };
        private Object[][] addOrUpdateDatas = {};
        private int addOrUpdateRowSelected = -1;
        private Boolean[] addOrUpdateValueSelected = { null };

        private Vector<String> foodsVector;
        private Vector<String> roomsVector;
        private Vector<String> customersVector;

        // - Các giá trị mặc định cho text field, text area hoặc combobox để tìm kiếm,
        // thêm, sửa và xoá
        private final Map<String, String> defaultValuesForCrud = new LinkedHashMap<String, String>() {
                {
                        put("id", "Nhập Mã đơn");
                        put("timeCreate", "Thời gian tạo đơn");
                        put("timeStart", "Thời gian bắt đầu");
                        put("timeEnd", "Thời gian kết thúc");
                        put("room", "Chọn Phòng hát");
                        put("employee", "Chọn Nhân viên tạo đơn");
                        put("customer", "Chọn Khách hàng");
                        put("totalPrice", "Nhập Tổng tiền hát");
                        put("status", "Chọn Trạng thái");
                        put("food", "Chọn Món ăn");
                }
        };

        private final String[] sortsString = { "Mã đơn tăng dần", "Mã đơn giảm dần", "Thời gian tạo đơn tăng dần",
                        "Thời gian tạo đơn giảm dần", "Tổng tiền tăng dần", "Tổng tiền giảm dần" };
        private final String[] sortsSQL = { "maHoaDon ASC", "maHoaDon DESC", "thoiGianTaoDon ASC",
                        "thoiGianTaoDon DESC", "tongTien ASC", "tongTien DESC" };
        private final String[] statusString = { "Tất cả", "Đã thanh toán", "Đã huỷ đơn", "Đang chờ xác nhận" };
        private final String[] statusSQL = { "", "trangThai = 2", "trangThai = 1", "trangThai = 0" };

        public Admin_OrderManagerPL() {
                // Khởi tạo các đối tượng từ tầng BLL
                orderBLL = new OrderBLL();
                orderDetailBLL = new OrderDetailBLL();
                foodBLL = new FoodBLL();
                roomBLL = new RoomBLL();
                roomTypeBLL = new RoomTypeBLL();
                accountBLL = new AccountBLL();
                customerBLL = new CustomerBLL();
                customerTypeBLL = new CustomerTypeBLL();

                // Khởi tạo danh sách món ăn
                foodsVector = new Vector<>();
                foodsVector.add(defaultValuesForCrud.get("food"));
                ArrayList<FoodDTO> foods = foodBLL.getAllFood();
                for (FoodDTO food : foods) {
                        if (food.getStatus()) {
                                foodsVector.add(food.getId() + " - " + food.getName());
                        }
                }

                // Khởi tạo danh sách phòng hát
                roomsVector = renderRoomCanOrder();

                // Khởi tạo danh sách khách hàng
                customersVector = renderCustomerCanOrder();

                // Cấu hình giao diện
                titleLabel = CommonPL.getTitleLabel("Hoá đơn", Color.BLACK, CommonPL.getFontTitle(),
                                SwingConstants.CENTER,
                                SwingConstants.CENTER);
                titleLabel.setBounds(30, 0, 1110, 115);

                findLabel = CommonPL.getParagraphLabel("Tìm kiếm", Color.BLACK, CommonPL.getFontParagraphPlain());
                findLabel.setBounds(15, 15, 90, 24);

                findInformButton = CommonPL.getQuestionIconForm("?", "Thông tin bạn có thể tìm kiếm",
                                "Bạn có thể tìm kiếm bằng mã hoá đơn", Color.BLACK, CommonPL.getFontQuestionIcon());
                findInformButton.setBounds(110, 15, 24, 24);

                findInputTextField = new CommonPL.CustomTextField(0, 20, 20, "Nhập thông tin", Color.LIGHT_GRAY,
                                Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                findInputTextField.setBounds(15, 45, 360, 40);

                sortLabel = CommonPL.getParagraphLabel("Sắp xếp", Color.BLACK, CommonPL.getFontParagraphPlain());
                sortLabel.setBounds(390, 15, 360, 24);

                sortCheckboxs = CommonPL.getMapHasValues(sortsString);
                sortButton = CommonPL.ButtonHasCheckboxs.createButtonHasCheckboxs(sortCheckboxs, sortsString[0],
                                Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
                sortButton.setBounds(390, 45, 360, 40);

                dateCreateLabel = CommonPL.getParagraphLabel("Ngày lập đơn", Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                dateCreateLabel.setBounds(765, 15, 135, 24);

                dateCreateInformButton = CommonPL.getQuestionIconForm("?", "Chú thích sử dụng lọc Ngày lập đơn",
                                "Lọc những ngày trong khoảng từ Ngày trước đến Ngày sau", Color.BLACK,
                                CommonPL.getFontQuestionIcon());
                dateCreateInformButton.setBounds(913, 15, 24, 24);

                dateCreateBeforeDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(10, CommonPL.getLocale(),
                                CommonPL.getDateFormat(), "Ngày trước", Color.LIGHT_GRAY, Color.BLACK,
                                CommonPL.getFontParagraphPlain(),
                                20, 40);
                dateCreateBeforeDatePicker.setBounds(765, 45, 170, 40);

                dateCreateRectangleLabel = CommonPL.getTitleLabel("-", Color.BLACK, CommonPL.getFontParagraphPlain(),
                                SwingConstants.CENTER, SwingConstants.CENTER);
                dateCreateRectangleLabel.setBounds(935, 45, 20, 40);

                dateCreateAfterDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(10, CommonPL.getLocale(),
                                CommonPL.getDateFormat(), "Ngày sau", Color.LIGHT_GRAY, Color.BLACK,
                                CommonPL.getFontParagraphPlain(),
                                20, 40);
                dateCreateAfterDatePicker.setBounds(955, 45, 170, 40);

                statusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK, CommonPL.getFontParagraphPlain());
                statusLabel.setBounds(15, 100, 360, 24);

                statusRadios = CommonPL.getMapHasValues(statusString);
                statusButton = CommonPL.ButtonHasRadios.createButtonHasRadios(statusRadios, statusString[0],
                                Color.LIGHT_GRAY,
                                Color.BLACK, CommonPL.getFontParagraphPlain());
                statusButton.setBounds(15, 130, 360, 40);

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
                filterPanel.add(sortLabel);
                filterPanel.add(sortButton);
                filterPanel.add(dateCreateLabel);
                filterPanel.add(dateCreateInformButton);
                filterPanel.add(dateCreateBeforeDatePicker);
                filterPanel.add(dateCreateRectangleLabel);
                filterPanel.add(dateCreateAfterDatePicker);
                filterPanel.add(statusLabel);
                filterPanel.add(statusButton);
                filterPanel.add(filterApplyButton);
                filterPanel.add(filterResetButton);

                addButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
                                CommonPL.getMiddlePathToShowIcon() + "add-icon.png", "Thêm", Color.BLACK,
                                Color.decode("#699f4c"),
                                Color.BLACK, Color.decode("#699f4c"), CommonPL.getFontParagraphBold());
                addButton.setBounds(15, 15, 210, 40);

                updateButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
                                CommonPL.getMiddlePathToShowIcon() + "update-icon.png", "Thay đổi", Color.BLACK,
                                Color.decode("#bf873e"), Color.BLACK, Color.decode("#bf873e"),
                                CommonPL.getFontParagraphBold());
                updateButton.setBounds(240, 15, 210, 40);

                printButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
                                CommonPL.getMiddlePathToShowIcon() + "print-icon.png", "In đơn", Color.BLACK,
                                Color.decode("#b5997a"), Color.BLACK, Color.decode("#b5997a"),
                                CommonPL.getFontParagraphBold());
                printButton.setBounds(465, 15, 210, 40);

                tableData = CommonPL.createTableData(columns, widthColumns, datas, "input order manager");
                tableScrollPane = CommonPL.createScrollPane(true, true, tableData);
                tableScrollPane.setBounds(15, 70, 1110, 400);

                dataPanel = new CommonPL.RoundedPanel(12);
                dataPanel.setLayout(null);
                dataPanel.setBackground(Color.WHITE);
                dataPanel.setBounds(30, 330, 1140, 485);
                dataPanel.add(addButton);
                dataPanel.add(updateButton);
                dataPanel.add(printButton);
                dataPanel.add(tableScrollPane);

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
                        showAddOrUpdateDialog("Thêm hoá đơn", "Thêm", new Vector<>());
                        rowSelected = -1;
                        valueSelected[0] = false;
                        tableData.clearSelection();
                });

                updateButton.addActionListener(e -> {
                        if (rowSelected != -1) {
                                String orderIdSelected = String.valueOf(tableData.getValueAt(rowSelected, 0));
                                OrderDTO orderSelected = orderBLL.getOneOrderById(orderIdSelected);
                                Vector<Object> currentObject = new Vector<>();
                                currentObject.add(orderSelected.getId());
                                currentObject.add(orderSelected.getTimeCreate());
                                currentObject.add(orderSelected.getTimeStart());
                                currentObject.add(orderSelected.getTimeEnd());
                                currentObject.add(orderSelected.getTotalPrice());
                                currentObject.add(orderSelected.getStatus() == 2 ? "Đã thanh toán"
                                                : orderSelected.getStatus() == 1 ? "Đã huỷ đơn" : "Đang chờ xác nhận");
                                currentObject.add(orderSelected.getEmployeeId());
                                currentObject.add(orderSelected.getRoomId());
                                currentObject.add(orderSelected.getCustomerId());

                                showAddOrUpdateDialog("Thay đổi hoá đơn", "Thay đổi", currentObject);
                        } else {
                                CommonPL.createErrorDialog("Thông báo lỗi",
                                                "Vui lòng chọn 1 dòng dữ liệu cần thay đổi");
                        }
                        rowSelected = -1;
                        valueSelected[0] = false;
                        tableData.clearSelection();
                });

                printButton.addActionListener(e -> {
                        if (rowSelected != -1) {
                                String orderIdSelected = String.valueOf(tableData.getValueAt(rowSelected, 0));
                                OrderDTO orderSelected = orderBLL.getOneOrderById(orderIdSelected);

                                String title = "In hoá đơn";
                                RoomDTO roomSelected = roomBLL.getOneRoomById(orderSelected.getRoomId());
                                RoomTypeDTO roomTypeSelected = roomTypeBLL.getOneRoomTypeById(roomSelected.getType());
                                CustomerDTO customerSelected = customerBLL
                                                .getOneCustomerById(orderSelected.getCustomerId());
                                CustomerTypeDTO customerTypeSelected = customerTypeBLL
                                                .getOneCustomerTypeById(customerSelected.getType());
                                Map<String, String> infos = new LinkedHashMap<>() {
                                        {
                                                put("Mã đơn", String.format("#%s", orderSelected.getId()));
                                                put("Thời gian tạo đơn", orderSelected.getTimeCreate());
                                                put("Thời gian bắt đầu hát", orderSelected.getTimeStart());
                                                put("Thời gian kết thúc hát", orderSelected.getTimeEnd());
                                                put("Nhân viên tạo đơn", accountBLL
                                                                .getOneAccountById(String.valueOf(
                                                                                orderSelected.getEmployeeId()))
                                                                .getFullname());
                                                put("Phòng hát", roomSelected.getName() + String.format(
                                                                " (%s - %sđ/1 giờ)", roomTypeSelected.getName(),
                                                                CommonPL.moneyLongToMoneyFormat(BigInteger
                                                                                .valueOf(roomTypeSelected.getCost()))));
                                                put("Khách hàng", customerSelected.getFullname() + String
                                                                .format(" (%s - %s%% ưu đãi)",
                                                                                customerTypeSelected.getName(),
                                                                                customerTypeSelected.getDiscount()));
                                                put("Tổng tiền hát (VNĐ)", CommonPL.moneyLongToMoneyFormat(
                                                                new BigInteger(String.valueOf(
                                                                                orderSelected.getTotalPrice())))
                                                                + "đ" + String.format(" (Đã giảm %s%%)",
                                                                                customerTypeSelected.getDiscount()));
                                                put("Trạng thái", orderSelected.getStatus() == 2 ? "Đã thanh toán"
                                                                : orderSelected.getStatus() == 1 ? "Đã huỷ đơn"
                                                                                : "Đang chờ xác nhận");
                                        }
                                };
                                String descTable = "Chi tiết hoá đơn";
                                Map<String, String> headTables = new LinkedHashMap<>() {
                                        {
                                                put("Mã món ăn", "10%");
                                                put("Tên món ăn", "38%");
                                                put("Giá nhập (VNĐ)", "14%");
                                                put("Số lượng", "14%");
                                                put("Thành tiền (VNĐ)", "24%");
                                        }
                                };
                                ArrayList<OrderDetailDTO> orderDetails = orderDetailBLL
                                                .getAllOrderDetailByOrderId(
                                                                String.valueOf(orderSelected.getId()));
                                String[][] bodyTables = new String[orderDetails.size()][headTables.size()];
                                for (int i = 0; i < orderDetails.size(); i++) {
                                        bodyTables[i][0] = orderDetails.get(i).getFoodId();
                                        bodyTables[i][1] = foodBLL.getOneFoodById(orderDetails.get(i).getFoodId())
                                                        .getName();
                                        bodyTables[i][2] = CommonPL.moneyLongToMoneyFormat(
                                                        new BigInteger(String.valueOf(
                                                                        orderDetails.get(i).getPrice())));
                                        bodyTables[i][3] = String.valueOf(orderDetails.get(i).getQuantity());
                                        bodyTables[i][4] = CommonPL.moneyLongToMoneyFormat(
                                                        new BigInteger(String.valueOf(
                                                                        orderDetails.get(i).getPrice()
                                                                                        * orderDetails.get(i)
                                                                                                        .getQuantity())));
                                }

                                CommonPL.printTicket(title, infos, descTable, headTables, bodyTables);
                        } else {
                                CommonPL.createErrorDialog("Thông báo lỗi",
                                                "Vui lòng chọn 1 dòng dữ liệu cần in phiếu");
                        }

                        rowSelected = -1;
                        valueSelected[0] = false;
                        tableData.clearSelection();
                });

                filterDatasInTable();
                renderTableData(null, null, null);
        }

        private Vector<String> renderRoomCanOrder() {
                Vector<String> roomsVector = new Vector<>();
                roomsVector.add(defaultValuesForCrud.get("room"));
                String[] roomJoin = null;
                String roomCondition = """
                                maPhong NOT IN (
                                        SELECT Phong.maPhong
                                        FROM Karaoke.Phong
                                        JOIN Karaoke.HoaDon ON HoaDon.maPhong = Phong.maPhong
                                        WHERE HoaDon.trangThai = 0
                                )
                                """;
                String roomSort = null;
                ArrayList<RoomDTO> rooms = roomBLL.getAllRoomByCondition(roomJoin, roomCondition, roomSort);
                for (RoomDTO room : rooms) {
                        if (room.getStatus()) {
                                roomsVector.add(room.getId() + " - " + room.getName());
                        }
                }

                return roomsVector;
        }

        private Vector<String> renderCustomerCanOrder() {
                Vector<String> customersVector = new Vector<>();
                customersVector.add(defaultValuesForCrud.get("customer"));
                String[] customerJoin = null;
                String customerCondition = """
                                cccd NOT IN (
                                        SELECT KhachHang.cccd
                                        FROM Karaoke.KhachHang
                                        JOIN Karaoke.HoaDon ON HoaDon.maKhachHang = KhachHang.cccd
                                        WHERE HoaDon.trangThai = 0
                                )
                                """;
                String customerSort = null;
                ArrayList<CustomerDTO> customers = customerBLL.getAllCustomerByCondition(customerJoin,
                                customerCondition, customerSort);
                for (CustomerDTO customer : customers) {
                        if (customer.getStatus()) {
                                customersVector.add(customer.getIdCard() + " - " + customer.getFullname());
                        }
                }

                return customersVector;
        }

        private void renderRoomAndCustomerAllComponent() {
                // Cập nhật phòng hát
                roomsVector = renderRoomCanOrder();
                addOrUpdateRoomComboBox.setModel(new DefaultComboBoxModel<>(roomsVector));

                // Cập nhật khách hàng
                customersVector = renderCustomerCanOrder();
                addOrUpdateCustomerComboBox.setModel(new DefaultComboBoxModel<>(customersVector));
        }

        private void renderTableData(String[] join, String condition, String order) {
                ArrayList<OrderDTO> orderList = orderBLL.getAllOrderByCondition(join, condition, order);
                Object[][] datasQuery = new Object[orderList.size()][columns.length];
                for (int i = 0; i < orderList.size(); i++) {
                        OrderDTO orderDTO = orderList.get(i);
                        AccountDTO employee = accountBLL.getOneAccountById(String.valueOf(orderDTO.getEmployeeId()));
                        RoomDTO room = roomBLL.getOneRoomById(orderDTO.getRoomId());
                        CustomerDTO customer = customerBLL.getOneCustomerById(orderDTO.getCustomerId());

                        datasQuery[i][0] = String.valueOf(orderDTO.getId());
                        datasQuery[i][1] = orderDTO.getTimeCreate();
                        datasQuery[i][2] = orderDTO.getTimeStart();
                        datasQuery[i][3] = orderDTO.getTimeEnd();
                        datasQuery[i][4] = room.getName();
                        datasQuery[i][5] = employee.getFullname();
                        datasQuery[i][6] = customer.getFullname();
                        datasQuery[i][7] = CommonPL
                                        .moneyLongToMoneyFormat(BigInteger.valueOf(orderDTO.getTotalPrice()));
                        datasQuery[i][8] = orderDTO.getStatus() == 2 ? "Đã thanh toán"
                                        : (orderDTO.getStatus() == 1 ? "Đã huỷ đơn" : "Đang chờ xác nhận");
                }
                datas = datasQuery;
                CommonPL.updateRowsInTableData(tableData, datas);
        }

        private void resetPage() {
                findInputTextField.setText("Nhập thông tin");
                findInputTextField.setForeground(Color.LIGHT_GRAY);
                CommonPL.resetMapForFilter(sortCheckboxs, sortsString, sortButton);
                CommonPL.resetMapForFilter(statusRadios, statusString, statusButton);
                dateCreateBeforeDatePicker.setDate(null);
                dateCreateAfterDatePicker.setDate(null);
                renderTableData(null, null, null);
        }

        private void filterDatasInTable() {
                filterApplyButton.addActionListener(e -> {
                        String findValue = !findInputTextField.getText().equals("Nhập thông tin")
                                        ? findInputTextField.getText()
                                        : null;
                        String sortValue = CommonPL.getSQLFromCheckboxs(sortCheckboxs, sortsSQL);
                        String statusValue = CommonPL.getSQLFromRadios(statusRadios, statusSQL);

                        String dateCreateCondition = "";
                        if (dateCreateBeforeDatePicker.getDate() != null
                                        && dateCreateAfterDatePicker.getDate() != null) {
                                dateCreateCondition = String.format("thoiGianTaoDon BETWEEN '%s' AND '%s'",
                                                CommonPL.getDateFormat().format(dateCreateBeforeDatePicker.getDate()),
                                                CommonPL.getDateFormat().format(dateCreateAfterDatePicker.getDate()));
                        }

                        String condition = (findValue != null ? String.format("maHoaDon = '%s'", findValue) : "")
                                        + (dateCreateCondition.length() > 0
                                                        ? (findValue != null ? " AND " : "") + dateCreateCondition
                                                        : "")
                                        + (statusValue != null
                                                        ? (findValue != null || dateCreateCondition.length() > 0
                                                                        ? " AND "
                                                                        : "") + statusValue
                                                        : "");
                        if (condition.length() == 0)
                                condition = null;

                        renderTableData(null, condition, sortValue != null && !sortValue.isEmpty() ? sortValue : null);
                });

                filterResetButton.addActionListener(e -> {
                        resetPage();
                });
        }

        private void showAddFoodDialog() {
                JDialog addfoodDialog = new JDialog();
                addfoodDialog.setTitle("Thêm món ăn");
                addfoodDialog.setSize(500, 320);
                addfoodDialog.setLayout(null);
                addfoodDialog.setModal(true);
                addfoodDialog.setLocationRelativeTo(addOrUpdateDialog);

                JLabel foodLabel = CommonPL.getParagraphLabel("Món ăn", Color.BLACK, CommonPL.getFontParagraphPlain());
                foodLabel.setBounds(20, 10, 460, 40);

                JComboBox<String> foodComboBox = CommonPL.CustomComboBox(foodsVector, Color.WHITE, Color.LIGHT_GRAY,
                                Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                foodComboBox.setBounds(20, 50, 460, 40);

                JLabel priceLabel = CommonPL.getParagraphLabel("Giá bán", Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                priceLabel.setBounds(20, 100, 220, 40);

                JTextField priceField = new CommonPL.CustomTextField(0, 0, 0, "Giá bán", Color.LIGHT_GRAY,
                                Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                priceField.setEnabled(false);
                priceField.setBackground(Color.decode("#dedede"));
                ((CustomTextField) priceField).setBorderColor(Color.decode("#dedede"));
                priceField.setBounds(20, 140, 220, 40);

                JLabel quantityLabel = CommonPL.getParagraphLabel("Số lượng", Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                quantityLabel.setBounds(260, 100, 220, 40);

                JTextField quantityField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Số lượng", Color.LIGHT_GRAY,
                                Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                quantityField.setBounds(260, 140, 220, 40);

                JButton confirmButton = CommonPL.getRoundedBorderButton(20, "Thêm",
                                Color.decode("#42A5F5"), Color.WHITE,
                                CommonPL.getFontParagraphBold());
                confirmButton.setBounds(20, 230, 460, 40);

                foodComboBox.addActionListener(e -> {
                        String selectedFood = String.valueOf(foodComboBox.getSelectedItem());

                        if (selectedFood.equals(defaultValuesForCrud.get("food"))) {
                                priceField.setText("Giá bán");
                                return;
                        }

                        FoodDTO foodDTO = foodBLL.getOneFoodById(selectedFood.split(" - ")[0]);
                        priceField.setText(String.valueOf(foodDTO.getPrice()));
                        ((CustomTextField) priceField).setBorderColor(Color.decode("#dedede"));
                });

                confirmButton.addActionListener(evt -> {
                        String selectedFood = String.valueOf(foodComboBox.getSelectedItem());
                        if (selectedFood.equals(defaultValuesForCrud.get("food"))) {
                                CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn món ăn");
                                return;
                        }
                        String[] parts = selectedFood.split(" - ", 2);
                        String foodId = parts[0];
                        String foodName = parts.length > 1 ? parts[1] : "Không xác định";
                        Long price;
                        Integer quantity;

                        for (int i = 0; i < addOrUpdateTableData.getRowCount(); i++) {
                                String foodIdExists = String.valueOf(addOrUpdateTableData.getValueAt(i, 0));
                                if (foodId.equals(foodIdExists)) {
                                        CommonPL.createErrorDialog("Thông báo lỗi", "Món ăn đã được có");
                                        return;
                                }
                        }
                        try {
                                price = Long.parseLong(priceField.getText());
                                quantity = Integer.parseInt(quantityField.getText());
                        } catch (NumberFormatException ex) {
                                CommonPL.createErrorDialog("Thông báo lỗi", "Số lượng phải là số hợp lệ");
                                return;
                        }
                        if (price < 0 || quantity < 0) {
                                CommonPL.createErrorDialog("Thông báo lỗi", "Số lượng không được âm");
                                return;
                        }

                        Long totaltotalPrice = price * quantity;
                        Vector<Object> newRow = new Vector<>();
                        newRow.add(foodId);
                        newRow.add(foodName);
                        newRow.add(CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(price)));
                        newRow.add(quantity);
                        newRow.add(CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(totaltotalPrice)));
                        ((DefaultTableModel) addOrUpdateTableData.getModel()).addRow(newRow);

                        long total = 0;
                        for (int i = 0; i < addOrUpdateTableData.getRowCount(); i++) {
                                total += CommonPL
                                                .moneyFormatToMoneyLong((String) addOrUpdateTableData.getValueAt(i, 4));
                        }
                        // addOrUpdateTotalPriceTextField.setText(String.valueOf(BigInteger.valueOf(total)));
                        // ((CustomTextField)
                        // addOrUpdateTotalPriceTextField).setBorderColor(Color.decode("#dedede"));

                        addfoodDialog.dispose();
                });

                JPanel addfoodPanel = new JPanel();
                addfoodPanel.setLayout(null);
                addfoodPanel.setBackground(Color.WHITE);
                addfoodPanel.setBounds(0, 0, 500, 320);
                addfoodPanel.add(foodLabel);
                addfoodPanel.add(foodComboBox);
                addfoodPanel.add(priceLabel);
                addfoodPanel.add(priceField);
                addfoodPanel.add(quantityLabel);
                addfoodPanel.add(quantityField);
                addfoodPanel.add(confirmButton);

                addfoodDialog.add(addfoodPanel);
                addfoodDialog.setVisible(true);
        }

        private void updateOrderEvent(String type, Integer status) {
                CommonPL.createSelectionsDialog("Thông báo lựa chọn", String.format("Có chắc chắn %s đơn này ?", type),
                                valueSelected);
                if (valueSelected[0]) {
                        Integer orderId = Integer.parseInt(addOrUpdateIdTextField.getText());
                        String timeStart = !addOrUpdateTimeStartTextField.getText()
                                        .equals(defaultValuesForCrud.get("timeStart"))
                                                        ? addOrUpdateTimeStartTextField.getText()
                                                        : null;
                        String timeEnd = !addOrUpdateTimeEndTextField.getText()
                                        .equals(defaultValuesForCrud.get("timeEnd"))
                                                        ? addOrUpdateTimeEndTextField.getText()
                                                        : null;
                        String room = !addOrUpdateRoomComboBox.getSelectedItem()
                                        .equals(defaultValuesForCrud.get("room"))
                                                        ? String.valueOf(addOrUpdateRoomComboBox
                                                                        .getSelectedItem()).split(" - ")[0]
                                                        : null;
                        String customer = !addOrUpdateCustomerComboBox.getSelectedItem()
                                        .equals(defaultValuesForCrud.get("customer"))
                                                        ? String.valueOf(addOrUpdateCustomerComboBox
                                                                        .getSelectedItem()).split(" - ")[0]
                                                        : null;

                        Vector<OrderDetailDTO> orderDetails = new Vector<>();
                        for (int i = 0; i < addOrUpdateTableData.getRowCount(); i++) {
                                String foodId = String.valueOf(addOrUpdateTableData.getValueAt(i, 0));
                                Long price = CommonPL.moneyFormatToMoneyLong(
                                                String.valueOf(addOrUpdateTableData.getValueAt(i, 2)));
                                Long quantity = Long.parseLong(
                                                String.valueOf(addOrUpdateTableData.getValueAt(i, 3)));
                                orderDetails.add(new OrderDetailDTO(orderId, foodId, price, quantity));
                        }

                        String result = orderBLL.updateOrder(orderId, timeStart, timeEnd, room, customer, status,
                                        orderDetails);
                        if (result.equals("Thay đổi trạng thái hoá đơn thành công")) {
                                CommonPL.createSuccessDialog("Thông báo thành công",
                                                String.format("Hoá đơn đã %s", type));
                                addOrUpdateDialog.dispose();
                                resetPage();
                                renderRoomAndCustomerAllComponent();
                        } else {
                                CommonPL.createErrorDialog("Thông báo lỗi", result);
                        }
                }
                valueSelected[0] = false;
        }

        private void showAddOrUpdateDialog(String title, String button, Vector<Object> object) {
                addOrUpdateIdLabel = CommonPL.getParagraphLabel("Mã đơn", Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                addOrUpdateIdLabel.setBounds(20, 10, 220, 40);

                addOrUpdateIdTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("id"),
                                Color.LIGHT_GRAY,
                                Color.BLACK, CommonPL.getFontParagraphPlain());
                addOrUpdateIdTextField.setHorizontalAlignment(JTextField.CENTER);
                addOrUpdateIdTextField.setEnabled(false);
                addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));
                addOrUpdateIdTextField.setBounds(20, 50, 220, 40);

                addOrUpdateTimeCreateLabel = CommonPL.getParagraphLabel("Thời gian tạo đơn", Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                addOrUpdateTimeCreateLabel.setBounds(260, 10, 220, 40);

                addOrUpdateTimeCreateTextField = new CommonPL.CustomTextField(0, 0, 0,
                                defaultValuesForCrud.get("timeCreate"),
                                Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
                addOrUpdateTimeCreateTextField.setHorizontalAlignment(JTextField.CENTER);
                addOrUpdateTimeCreateTextField.setEnabled(false);
                addOrUpdateTimeCreateTextField.setBackground(Color.decode("#dedede"));
                addOrUpdateTimeCreateTextField.setBounds(260, 50, 220, 40);

                addOrUpdateTimeStartLabel = CommonPL.getParagraphLabel("Thời gian bắt đầu", Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                addOrUpdateTimeStartLabel.setBounds(500, 10, 220, 40);

                addOrUpdateTimeStartTextField = new CommonPL.CustomTextField(0, 0, 0,
                                defaultValuesForCrud.get("timeStart"),
                                Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
                addOrUpdateTimeStartTextField.setHorizontalAlignment(JTextField.CENTER);
                addOrUpdateTimeStartTextField.setEnabled(false);
                addOrUpdateTimeStartTextField.setBackground(Color.decode("#dedede"));
                addOrUpdateTimeStartTextField.setBounds(500, 50, 220, 40);

                addOrUpdateTimeEndLabel = CommonPL.getParagraphLabel("Thời gian kết thúc", Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                addOrUpdateTimeEndLabel.setBounds(740, 10, 220, 40);

                addOrUpdateTimeEndTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("timeEnd"),
                                Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
                addOrUpdateTimeEndTextField.setHorizontalAlignment(JTextField.CENTER);
                addOrUpdateTimeEndTextField.setEnabled(false);
                addOrUpdateTimeEndTextField.setBackground(Color.decode("#dedede"));
                addOrUpdateTimeEndTextField.setBounds(740, 50, 220, 40);
                Timer timeEndClock = new Timer(1000, e -> {
                        LocalDateTime currentDateTime = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter
                                        .ofPattern("yyyy-MM-dd HH:mm:ss");
                        String formattedDateTime = currentDateTime.format(formatter);
                        addOrUpdateTimeEndTextField.setText(formattedDateTime);
                        ((CustomTextField) addOrUpdateTimeEndTextField)
                                        .setBorderColor(Color.decode("#dedede"));
                });

                addOrUpdateStatusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                addOrUpdateStatusLabel.setBounds(260, 100, 220, 40);

                addOrUpdateStatusTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("status"),
                                Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
                addOrUpdateStatusTextField.setEnabled(false);
                addOrUpdateStatusTextField.setBackground(Color.decode("#dedede"));
                addOrUpdateStatusTextField.setBounds(260, 140, 220, 40);

                addOrUpdateTotalPriceLabel = CommonPL.getParagraphLabel("Tổng tiền hát (VNĐ)", Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                addOrUpdateTotalPriceLabel.setBounds(20, 100, 220, 40);

                addOrUpdateTotalPriceTextField = new CommonPL.CustomTextField(0, 0, 0,
                                defaultValuesForCrud.get("totalPrice"),
                                Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
                addOrUpdateTotalPriceTextField.setEnabled(false);
                addOrUpdateTotalPriceTextField.setBackground(Color.decode("#dedede"));
                ((CustomTextField) addOrUpdateTotalPriceTextField).setBorderColor(Color.decode("#dedede"));
                addOrUpdateTotalPriceTextField.setBounds(20, 140, 220, 40);

                addOrUpdateEmployeeLabel = CommonPL.getParagraphLabel("Nhân viên lập đơn", Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                addOrUpdateEmployeeLabel.setBounds(500, 100, 460, 40);

                addOrUpdateEmployeeTextField = new CommonPL.CustomTextField(0, 0, 0,
                                defaultValuesForCrud.get("employee"),
                                Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
                addOrUpdateEmployeeTextField.setEnabled(false);
                addOrUpdateEmployeeTextField.setBackground(Color.decode("#dedede"));
                addOrUpdateEmployeeTextField.setBounds(500, 140, 460, 40);

                addOrUpdateRoomLabel = CommonPL.getParagraphLabel("Phòng hát", Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                addOrUpdateRoomLabel.setBounds(20, 190, 460, 40);

                addOrUpdateRoomComboBox = CommonPL.CustomComboBox(roomsVector, Color.WHITE, Color.LIGHT_GRAY,
                                Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                addOrUpdateRoomComboBox.setBounds(20, 230, 460, 40);

                addOrUpdateCustomerLabel = CommonPL.getParagraphLabel("Khách hàng", Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                addOrUpdateCustomerLabel.setBounds(500, 190, 460, 40);

                addOrUpdateCustomerComboBox = CommonPL.CustomComboBox(customersVector, Color.WHITE, Color.LIGHT_GRAY,
                                Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                addOrUpdateCustomerComboBox.setBounds(500, 230, 460, 40);

                addOrUpdateDataLabel = CommonPL.getParagraphLabel("Danh sách món ăn", Color.BLACK,
                                CommonPL.getFontParagraphPlain());
                addOrUpdateDataLabel.setBounds(20, 280, 940, 40);

                addOrUpdateAddUnitButton = CommonPL.getButtonHasIcon(40, 28, 20, 20, 10, 4,
                                CommonPL.getMiddlePathToShowIcon() + "plus-icon.png", null, null, null, null, null,
                                new Font("Arial", Font.BOLD, 14));
                addOrUpdateAddUnitButton.setOpaque(true);
                addOrUpdateAddUnitButton.setBackground(Color.BLACK);
                addOrUpdateAddUnitButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent evt) {
                                addOrUpdateAddUnitButton
                                                .setBorder(BorderFactory.createLineBorder(Color.decode("#42A5F5"), 2));
                                addOrUpdateAddUnitButton.setBackground(Color.decode("#42A5F5"));
                        }

                        @Override
                        public void mouseExited(MouseEvent evt) {
                                addOrUpdateAddUnitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                                addOrUpdateAddUnitButton.setBackground(Color.BLACK);
                        }
                });
                addOrUpdateAddUnitButton.setBounds(874, 286, 40, 28);

                addOrUpdateDeleteUnitButton = CommonPL.getButtonHasIcon(40, 28, 20, 20, 10, 4,
                                CommonPL.getMiddlePathToShowIcon() + "minus-icon.png", null, null, null, null, null,
                                new Font("Arial", Font.BOLD, 14));
                addOrUpdateDeleteUnitButton.setOpaque(true);
                addOrUpdateDeleteUnitButton.setBackground(Color.BLACK);
                addOrUpdateDeleteUnitButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent evt) {
                                addOrUpdateDeleteUnitButton
                                                .setBorder(BorderFactory.createLineBorder(Color.decode("#42A5F5"), 2));
                                addOrUpdateDeleteUnitButton.setBackground(Color.decode("#42A5F5"));
                        }

                        @Override
                        public void mouseExited(MouseEvent evt) {
                                addOrUpdateDeleteUnitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                                addOrUpdateDeleteUnitButton.setBackground(Color.BLACK);
                        }
                });
                addOrUpdateDeleteUnitButton.setBounds(920, 286, 40, 28);

                addOrUpdateTableData = CommonPL.createTableData(addOrUpdateColumns, addOrUpdateWidthColumns,
                                addOrUpdateDatas,
                                "add or update unit table");
                addOrUpdateTableScrollPane = CommonPL.createScrollPane(true, true, addOrUpdateTableData);
                addOrUpdateTableScrollPane.setBounds(20, 320, 940, 400);

                addOrUpdateCompleteButton = CommonPL.getRoundedBorderButton(20, "Hoàn thành", Color.decode("#33CC00"),
                                Color.WHITE, CommonPL.getFontParagraphBold());
                addOrUpdateCompleteButton.setBounds(260, 750, 220, 40);
                addOrUpdateCompleteButton.setVisible(false);

                addOrUpdateDeleteButton = CommonPL.getRoundedBorderButton(20, "Hủy đơn", Color.decode("#EE0000"),
                                Color.WHITE,
                                CommonPL.getFontParagraphBold());
                addOrUpdateDeleteButton.setBounds(500, 750, 220, 40);
                addOrUpdateDeleteButton.setVisible(false);

                addOrUpdateButton = CommonPL.getRoundedBorderButton(20, button,
                                button.equals("Thêm") ? Color.decode("#699f4c") : Color.decode("#bf873e"), Color.WHITE,
                                CommonPL.getFontParagraphBold());
                addOrUpdateButton.setBounds(260, 750, 460, 40);
                SwingUtilities.invokeLater(() -> addOrUpdateButton.requestFocusInWindow());

                addOrUpdateAddUnitButton.addActionListener(e -> showAddFoodDialog());

                addOrUpdateDeleteUnitButton.addActionListener(e -> {
                        int selectedRow = addOrUpdateTableData.getSelectedRow();
                        if (selectedRow != -1) {
                                ((DefaultTableModel) addOrUpdateTableData.getModel()).removeRow(selectedRow);

                                long total = 0;
                                for (int i = 0; i < addOrUpdateTableData.getRowCount(); i++) {
                                        total += CommonPL.moneyFormatToMoneyLong(
                                                        String.valueOf(addOrUpdateTableData.getValueAt(i, 4)));
                                }

                                addOrUpdateTotalPriceTextField.setText(String.valueOf(BigInteger.valueOf(total)));
                                ((CustomTextField) addOrUpdateTotalPriceTextField)
                                                .setBorderColor(Color.decode("#dedede"));
                        } else {
                                CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn một món ăn để xóa");
                        }
                });

                if (title.equals("Thêm hoá đơn") && button.equals("Thêm") &&
                                object.isEmpty()) {
                        addOrUpdateIdTextField.setText(String.valueOf(orderBLL.getLastOrder().getId()
                                        + 1));
                        ((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));

                        addOrUpdateTimeCreateTextField.setText(CommonPL.getCurrentDatetime());
                        ((CustomTextField) addOrUpdateTimeCreateTextField).setBorderColor(Color.decode("#dedede"));

                        addOrUpdateTimeStartTextField.setText(CommonPL.getCurrentDatetime());
                        ((CustomTextField) addOrUpdateTimeStartTextField).setBorderColor(Color.decode("#dedede"));

                        timeEndClock.start();

                        AccountDTO currentUser = CommonPL.getAccountUsingApp();
                        addOrUpdateEmployeeTextField.setText(currentUser.getId() + " - " +
                                        currentUser.getFullname());
                        ((CustomTextField) addOrUpdateEmployeeTextField).setBorderColor(Color.decode("#dedede"));

                        addOrUpdateTotalPriceTextField.setText("0");
                        ((CustomTextField) addOrUpdateTotalPriceTextField).setBorderColor(Color.decode("#dedede"));

                        addOrUpdateStatusTextField.setText("Đang chờ xác nhận");
                        ((CustomTextField) addOrUpdateStatusTextField).setBorderColor(Color.decode("#dedede"));
                        addOrUpdateAddUnitButton.setVisible(false);
                        addOrUpdateDeleteUnitButton.setVisible(false);
                }

                if (title.equals("Thay đổi hoá đơn") && button.equals("Thay đổi") &&
                                !object.isEmpty()) {
                        addOrUpdateButton.setVisible(false);

                        if (object.get(0) != null) {
                                addOrUpdateIdTextField.setText(String.valueOf(object.get(0)));
                                ((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
                        }

                        if (object.get(1) != null) {
                                addOrUpdateTimeCreateTextField.setText(String.valueOf(object.get(1)));
                                ((CustomTextField) addOrUpdateTimeCreateTextField)
                                                .setBorderColor(Color.decode("#dedede"));
                        }

                        if (object.get(2) != null) {
                                addOrUpdateTimeStartTextField.setText(String.valueOf(object.get(2)));
                                ((CustomTextField) addOrUpdateTimeStartTextField)
                                                .setBorderColor(Color.decode("#dedede"));
                        }

                        if (object.get(3) != null) {
                                addOrUpdateTimeEndTextField.setText(String.valueOf(object.get(3)));
                                ((CustomTextField) addOrUpdateTimeEndTextField)
                                                .setBorderColor(Color.decode("#dedede"));
                        }

                        if (object.get(4) != null) {
                                addOrUpdateTotalPriceTextField.setText(String.valueOf(object.get(4)));
                                ((CustomTextField) addOrUpdateTotalPriceTextField)
                                                .setBorderColor(Color.decode("#dedede"));
                        }

                        String status = String.valueOf(object.get(5));
                        if (status != null) {
                                addOrUpdateStatusTextField.setText(status);
                                ((CustomTextField) addOrUpdateStatusTextField)
                                                .setBorderColor(Color.decode("#dedede"));
                        }
                        if (status.equals("Đã thanh toán") || status.equals("Đã huỷ đơn")) {
                                addOrUpdateAddUnitButton.setVisible(false);
                                addOrUpdateDeleteUnitButton.setVisible(false);
                                addOrUpdateCompleteButton.setVisible(false);
                                addOrUpdateDeleteButton.setVisible(false);
                                timeEndClock.stop();
                        } else if (status.equals("Đang chờ xác nhận")) {
                                addOrUpdateCompleteButton.setVisible(true);
                                addOrUpdateDeleteButton.setVisible(true);
                                timeEndClock.start();
                        }

                        if (object.get(6) != null) {
                                AccountDTO employee = accountBLL.getOneAccountById(String.valueOf(object.get(6)));
                                addOrUpdateEmployeeTextField.setText(employee.getId() + " - " + employee.getFullname());
                                ((CustomTextField) addOrUpdateEmployeeTextField)
                                                .setBorderColor(Color.decode("#dedede"));
                        }

                        if (object.get(7) != null) {
                                RoomDTO roomDTO = roomBLL.getOneRoomById(String.valueOf(object.get(7)));
                                Vector<String> roomVector = new Vector<>();
                                roomVector.add(roomDTO.getId() + " - " + roomDTO.getName());
                                addOrUpdateRoomComboBox.setModel(new DefaultComboBoxModel<>(roomVector));
                                addOrUpdateRoomComboBox.setSelectedIndex(0);
                                addOrUpdateRoomComboBox.setEnabled(false);
                                UIManager.put("ComboBox.disabledBackground", Color.decode("#dedede"));
                                addOrUpdateRoomComboBox
                                                .setBorder(BorderFactory.createLineBorder(
                                                                Color.decode("#dedede"), 1));
                        }

                        if (object.get(8) != null) {
                                CustomerDTO customerDTO = customerBLL.getOneCustomerById(String.valueOf(object.get(8)));
                                Vector<String> customerVector = new Vector<>();
                                customerVector.add(customerDTO.getIdCard() + " - " + customerDTO.getFullname());
                                addOrUpdateCustomerComboBox.setModel(new DefaultComboBoxModel<>(customerVector));
                                addOrUpdateCustomerComboBox.setSelectedIndex(0);
                                addOrUpdateCustomerComboBox.setEnabled(false);
                                UIManager.put("ComboBox.disabledBackground", Color.decode("#dedede"));
                                addOrUpdateCustomerComboBox
                                                .setBorder(BorderFactory.createLineBorder(
                                                                Color.decode("#dedede"), 1));
                        }

                        ArrayList<OrderDetailDTO> details = orderDetailBLL.getAllOrderDetailByCondition(
                                        null, String.format("maHoaDon = %s", object.get(0) != null ? object.get(0)
                                                        : "0"),
                                        null);
                        for (OrderDetailDTO detail : details) {
                                FoodDTO food = foodBLL.getOneFoodById(detail.getFoodId());
                                String foodName = food != null ? food.getName() : "Không xác định";

                                Vector<Object> row = new Vector<>();
                                row.add(detail.getFoodId());
                                row.add(foodName);
                                row.add(CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(detail.getPrice())));
                                row.add(detail.getQuantity());
                                row.add(CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(detail.getPrice()
                                                * detail.getQuantity())));

                                ((DefaultTableModel) addOrUpdateTableData.getModel()).addRow(row);
                        }
                }

                addOrUpdateButton.addActionListener(e -> {
                        CommonPL.createSelectionsDialog("Thông báo lựa chọn", "Có chắc chắn thêm hoá đơn này?",
                                        valueSelected);
                        if (valueSelected[0]) {
                                Integer id = !addOrUpdateIdTextField.getText()
                                                .equals(defaultValuesForCrud.get("id"))
                                                                ? Integer.parseInt(addOrUpdateIdTextField.getText())
                                                                : null;
                                String timeCreate = !addOrUpdateTimeCreateTextField.getText()
                                                .equals(defaultValuesForCrud.get("timeCreate"))
                                                                ? addOrUpdateTimeCreateTextField.getText()
                                                                : null;
                                String timeStart = !addOrUpdateTimeStartTextField.getText()
                                                .equals(defaultValuesForCrud.get("timeStart"))
                                                                ? addOrUpdateTimeStartTextField.getText()
                                                                : null;
                                String timeEnd = null;
                                Integer employee = !addOrUpdateEmployeeTextField.getText()
                                                .equals(defaultValuesForCrud.get("employee"))
                                                                ? Integer.parseInt(addOrUpdateEmployeeTextField
                                                                                .getText().split(" - ")[0])
                                                                : null;
                                String room = !addOrUpdateRoomComboBox.getSelectedItem()
                                                .equals(defaultValuesForCrud.get("room"))
                                                                ? String.valueOf(addOrUpdateRoomComboBox
                                                                                .getSelectedItem()).split(" - ")[0]
                                                                : null;
                                String customer = !addOrUpdateCustomerComboBox.getSelectedItem()
                                                .equals(defaultValuesForCrud.get("customer"))
                                                                ? String.valueOf(addOrUpdateCustomerComboBox
                                                                                .getSelectedItem()).split(" - ")[0]
                                                                : null;
                                Long totalPrice = !addOrUpdateTotalPriceTextField.getText()
                                                .equals(defaultValuesForCrud.get("totalPrice"))
                                                                ? Long.parseLong(addOrUpdateTotalPriceTextField
                                                                                .getText())
                                                                : null;
                                Integer status = !addOrUpdateStatusTextField.getText()
                                                .equals(defaultValuesForCrud.get("status"))
                                                                ? (addOrUpdateStatusTextField.getText()
                                                                                .equals("Đang chờ xác nhận") ? 0 : null)
                                                                : null;

                                // - Biến chứa thông báo trả về
                                String inform = null;
                                // - Tuỳ vào tác vụ thêm hoặc thay đổi mà gọi đến hàm ở tầng BLL tương ứng
                                if (title.equals("Thêm hoá đơn") && button.equals("Thêm")) {
                                        inform = orderBLL.insertOrder(id, timeCreate, timeStart, timeEnd, room,
                                                        employee, customer, totalPrice,
                                                        status);
                                }
                                // - Tuỳ vào kết quả của thông báo trả về mà thông báo và cập nhật bảng dữ liệu
                                if (inform.equals("Thêm hoá đơn thành công")) {
                                        CommonPL.createSuccessDialog("Thông báo thành công", inform);
                                        addOrUpdateDialog.dispose();
                                        resetPage();
                                        renderRoomAndCustomerAllComponent();
                                } else {
                                        CommonPL.createErrorDialog("Thông báo lỗi", inform);
                                }
                        }
                        valueSelected[0] = false;
                });

                addOrUpdateCompleteButton.addActionListener(e -> {
                        updateOrderEvent("thanh toán", 2);
                });

                addOrUpdateDeleteButton.addActionListener(e -> {
                        updateOrderEvent("huỷ", 1);
                });

                addOrUpdateBlockPanel = new JPanel();
                addOrUpdateBlockPanel.setLayout(null);
                addOrUpdateBlockPanel.setBounds(0, 0, 980,
                                addOrUpdateStatusTextField.getText().equals("Đang chờ xác nhận") ? 840 : 770);
                addOrUpdateBlockPanel.setBackground(Color.WHITE);
                addOrUpdateBlockPanel.add(addOrUpdateIdLabel);
                addOrUpdateBlockPanel.add(addOrUpdateIdTextField);
                addOrUpdateBlockPanel.add(addOrUpdateTimeCreateLabel);
                addOrUpdateBlockPanel.add(addOrUpdateTimeCreateTextField);
                addOrUpdateBlockPanel.add(addOrUpdateTimeStartLabel);
                addOrUpdateBlockPanel.add(addOrUpdateTimeStartTextField);
                addOrUpdateBlockPanel.add(addOrUpdateTimeEndLabel);
                addOrUpdateBlockPanel.add(addOrUpdateTimeEndTextField);
                addOrUpdateBlockPanel.add(addOrUpdateEmployeeLabel);
                addOrUpdateBlockPanel.add(addOrUpdateEmployeeTextField);
                addOrUpdateBlockPanel.add(addOrUpdateRoomLabel);
                addOrUpdateBlockPanel.add(addOrUpdateRoomComboBox);
                addOrUpdateBlockPanel.add(addOrUpdateCustomerLabel);
                addOrUpdateBlockPanel.add(addOrUpdateCustomerComboBox);
                addOrUpdateBlockPanel.add(addOrUpdateDataLabel);
                addOrUpdateBlockPanel.add(addOrUpdateAddUnitButton);
                addOrUpdateBlockPanel.add(addOrUpdateDeleteUnitButton);
                addOrUpdateBlockPanel.add(addOrUpdateTableScrollPane);
                addOrUpdateBlockPanel.add(addOrUpdateRoomLabel);
                addOrUpdateBlockPanel.add(addOrUpdateRoomComboBox);
                addOrUpdateBlockPanel.add(addOrUpdateTotalPriceLabel);
                addOrUpdateBlockPanel.add(addOrUpdateTotalPriceTextField);
                addOrUpdateBlockPanel.add(addOrUpdateStatusLabel);
                addOrUpdateBlockPanel.add(addOrUpdateStatusTextField);
                addOrUpdateBlockPanel.add(addOrUpdateCompleteButton);
                addOrUpdateBlockPanel.add(addOrUpdateDeleteButton);
                addOrUpdateBlockPanel.add(addOrUpdateButton);

                addOrUpdateDialog = new JDialog();
                addOrUpdateDialog.setTitle(title);
                addOrUpdateDialog.setLayout(null);
                addOrUpdateDialog.setSize(980,
                                addOrUpdateStatusTextField.getText().equals("Đang chờ xác nhận") ? 840 : 770);
                addOrUpdateDialog.setResizable(false);
                addOrUpdateDialog.setLocationRelativeTo(null);
                addOrUpdateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                addOrUpdateDialog.add(addOrUpdateBlockPanel);
                addOrUpdateDialog.setModal(true);
                addOrUpdateDialog.setVisible(true);
        }
}