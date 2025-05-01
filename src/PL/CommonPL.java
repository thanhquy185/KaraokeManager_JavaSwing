package PL;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXMonthView;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import DTO.AccountDTO;
import PL.CommonPL.CustomCornerDatePicker.CustomRoundedBorder;

public class CommonPL {
	// Kích thước màn hình tiêu chuẩn (dựa trên máy của Quy)
	final private static int screenWidthByDevelopment = 1440;
	final private static int screenHeightByDevelopment = 900;
	// Kích thước màn hình máy khác
	final private static int screenWidthByOwner = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	final private static int screenHeightByOwner = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	// Tỷ lệ giữa hai màn hình
	final private static double percentWidthBetweenTwoScreen = 1.0 * screenWidthByOwner / screenWidthByDevelopment;
	final private static double percentHeightBetweenTwoScreen = 1.0 * screenHeightByOwner / screenHeightByDevelopment;
	// Chiều rộng của Left Menu
	final private static int leftMenuWidth = 240;
	// Chiều rộng của Main
	final private static int mainWidth = screenWidthByDevelopment - leftMenuWidth;
	// Khoảng cách giữa các JPanel lớn trong Main
	final private static int marginBetweenBigPanelsInMain = 30;
	// Thông tin tài khoản hiện đang sử dụng phần mềm
	private static AccountDTO accountUsingApp;
	// Các Font
	final private static Font fontTitle = new Font("Arial", Font.PLAIN, 34);
	final private static Font fontParagraphBold = new Font("Arial", Font.BOLD, 18);
	final private static Font fontParagraphPlain = new Font("Arial", Font.PLAIN, 20);
	final private static Font fontQuestionIcon = new Font("Arial", Font.PLAIN, 14);
	// Thông tin về ngày giờ hiện tại khi sử dụng phần mềm
	final private static DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	final private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	final private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	// Định dạng ngôn ngữ và nội dung cho JXDatePicker
	final private static Locale locale = new Locale("vi", "VN");
	final private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
	final private static SimpleDateFormat dateDetailFormat = new SimpleDateFormat("EEEE, yyyy-MM-dd", locale);
	// Map chứa các địa chỉ hợp lệ ở Việt Nam
	final private static Multimap<String, Multimap<String, String>> addressInfo = LinkedHashMultimap.create();
	// Đường dẫn đến hình ảnh chứa icon (Đoạn giữa)
	final private static String middlePathToShowIcon = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "Images" + File.separator + "Icons" + File.separator;
	// Đường dẫn đến hình ảnh của Sản phẩm (Đoạn giữa)
	final private static String middlePathToShowFoodImage = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "Images" + File.separator + "Foods" + File.separator;

	// Hàm lấy kích thước màn hình máy khác
	public static int getScreenWidthByOwner() {
		return CommonPL.screenWidthByOwner;
	}

	public static int getScreenHeightByOwner() {
		return CommonPL.screenHeightByOwner;
	}

	// Hàm lấy ra tỷ lệ giữa hai màn hình
	public static double getPercentWidthBetweenTwoScreen() {
		return CommonPL.percentWidthBetweenTwoScreen;
	}

	public static double getPercentHeightBetweenTwoScreen() {
		return CommonPL.percentHeightBetweenTwoScreen;
	}

	// Hàm lấy ra chiều rộng của Left Menu
	public static int getLeftMenuWidth() {
		return CommonPL.leftMenuWidth;
	}

	// Hàm lấy ra chiều rộng của Main Menu
	public static int getMainWidth() {
		return CommonPL.mainWidth;
	}

	// Hàm lấy ra khoảng cách giữa các JPanel lớn bên trong Main
	public static int getMarginBetweenBigPanelsInMain() {
		return CommonPL.marginBetweenBigPanelsInMain;
	}

	// Hàm gán giá trị thông tin tài khoản đang sử dụng phần mềm
	public static void setAccountUsingApp(AccountDTO accountDTO) {
		CommonPL.accountUsingApp = accountDTO;
	}

	// Hàm lấy ra thông tin tài khoản đang sử dụng phần mềm
	public static AccountDTO getAccountUsingApp() {
		return CommonPL.accountUsingApp;
	}

	// Hàm lấy ra ngày giờ hiện tại
	public static String getCurrentDatetime() {
		return LocalDateTime.now().format(datetimeFormatter);
	}

	// Hàm lấy ra ngày hiện tại
	public static String getCurrentDate() {
		return LocalDateTime.now().format(dateFormatter);
	}

	// Hàm lấy ra giờ hiện tại
	public static String getCurrentTime() {
		return LocalDateTime.now().format(timeFormatter);
	}

	// Hàm lấy ra Locale
	public static Locale getLocale() {
		return CommonPL.locale;
	}

	// Hàm lấy ra Date Format
	public static SimpleDateFormat getDateFormat() {
		return CommonPL.dateFormat;
	}

	// Hàm lấy ra Date Detail Format
	public static SimpleDateFormat getDateDetailFormat() {
		return CommonPL.dateDetailFormat;
	}

	// Hàm lấy ra kích cỡ chữ tiêu đề ở Main Menu
	public static Font getFontTitle() {
		return CommonPL.fontTitle;
	}

	// Hàm lấy ra kích cỡ chữ nội dung (in đậm) ở Main Menu
	public static Font getFontParagraphBold() {
		return CommonPL.fontParagraphBold;
	}

	// Hàm lấy ra kích cỡ chữ nội dung (in thường) ở Main Menu
	public static Font getFontParagraphPlain() {
		return CommonPL.fontParagraphPlain;
	}

	// Hàm lấy ra kích cỡ chữ biểu tượng chấm hỏi (?) ở Main Menu
	public static Font getFontQuestionIcon() {
		return CommonPL.fontQuestionIcon;
	}

	// Hàm lấy ra Address Info Map
	public static Multimap<String, Multimap<String, String>> getAddressInfo() {
		return CommonPL.addressInfo;
	}

	// Hàm lấy ra Middle Path To Show Icon
	public static String getMiddlePathToShowIcon() {
		return CommonPL.middlePathToShowIcon;
	}

	// Hàm lấy ra Middle Path To Show Food Image
	public static String getMiddlePathToShowFoodImage() {
		return CommonPL.middlePathToShowFoodImage;
	}

	// Hàm chuyển từ tiền dạng số nguyên sang dạng chuỗi (VNĐ)
	public static String moneyLongToMoneyFormat(BigInteger money) {
		// - Locale.GERMANY dùng dấu "." để phân cách
		NumberFormat formatter = NumberFormat.getInstance(Locale.GERMANY);
		return formatter.format(money);

	}

	// Hàm chuyển từ tiền dạng chuỗi (VNĐ) sang dạng số nguyên
	public static long moneyFormatToMoneyLong(String moneyFormat) {
		return Long.parseLong(moneyFormat.replace(".", ""));
	}

	// Hàm chuyển định dạng ngày
	public static String convertDateFormat(String date) {
		boolean isSqlDate = false;
		if (date.charAt(2) == '-') {
			isSqlDate = true;
		}

		String day = date.substring(0, 2);
		String month = date.substring(3, 5);
		String year = date.substring(6);

		return isSqlDate ? day + "/" + month + "/" + year
				: year + "-" + month + "-" + day;
	}

	// Hàm trả về danh sách các tuần trong tháng
	public static String[][] getWeeksOfMonth(int year, int month) {
		List<String[]> weeksList = new ArrayList<>();
		LocalDate firstDay = LocalDate.of(year, month, 1);
		LocalDate lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDate current = firstDay;
		int weekNumber = 1;

		while (!current.isAfter(lastDay)) {
			LocalDate startOfWeek = current;
			LocalDate endOfWeek = current.plusDays(6);

			if (endOfWeek.isAfter(lastDay)) {
				endOfWeek = lastDay;
			}

			weeksList.add(new String[] {
					String.valueOf(weekNumber),
					startOfWeek.format(formatter),
					endOfWeek.format(formatter)
			});

			current = endOfWeek.plusDays(1);
			weekNumber++;
		}

		return weeksList.toArray(new String[0][3]);
	}

	// Hàm trả về danh sách các tháng trong năm
	public static String[][] getMonthsOfYear(int year) {
		String[][] monthsArray = new String[12][3];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		for (int month = 1; month <= 12; month++) {
			LocalDate firstDay = LocalDate.of(year, month, 1);
			LocalDate lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());

			monthsArray[month - 1][0] = String.valueOf(month);
			monthsArray[month - 1][1] = firstDay.format(formatter);
			monthsArray[month - 1][2] = lastDay.format(formatter);
		}

		return monthsArray;
	}

	// Hàm tách địa chỉ từ 1 địa chỉ hợp lệ

	// Thiết lập các địa chỉ cho Address Info khi chương trình chạy
	public static void renderAddressInfo() {
		File f = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "Others"
				+ File.separator + "AddressInfo.txt");
		if (f.exists()) {
			try {
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				while (true) {
					try {
						// Đọc để lấy dữ liệu của các địa chỉ từ file
						String line = br.readLine();
						if (line == null) {
							break;
						}
						if (line.indexOf("-") == 0 || line.indexOf("--") == 0) {
							continue;
						}
						String[] info = line.split("\\|");
						String city = info[0].trim();
						String district = info[1].trim();
						String ward = info[2].trim();

						// Thêm từng địa chỉ đã đọc
						boolean isExisted = false;
						// - Nếu địa chỉ đó đã tồn tại
						for (var entry : addressInfo.entries()) {
							if (entry.getKey().equals(city)) {
								Multimap<String, String> subMultimap = entry.getValue();
								for (var subEntry : subMultimap.entries()) {
									if (subEntry.getKey().equals(district)) {
										subMultimap.put(district, ward);
										isExisted = true;
									}
								}
							}
						}
						// - Nếu địa chỉ đó chưa tồn tại
						if (!isExisted) {
							Multimap<String, String> subMultimap = LinkedHashMultimap.create();
							subMultimap.put(district, ward);
							addressInfo.put(city, subMultimap);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Đường dẫn tệp 'AddressInfo.txt' không tồn tại");
		}
	}

	/**
	 * Hàm cập nhật JComboBox với danh sách các giá trị mới.
	 * 
	 * @param comboBox     JComboBox cần cập nhật
	 * @param items        Danh sách các mục cần thêm
	 * @param selectedItem Mục được chọn mặc định (nếu có)
	 */
	public static <T> void updateComboBox(JComboBox<T> comboBox, Collection<T> items, T selectedItem) {
		ActionListener[] listeners = comboBox.getActionListeners();
		for (ActionListener listener : listeners) {
			comboBox.removeActionListener(listener); // Tạm thời gỡ bỏ
		}

		comboBox.removeAllItems(); // Xóa tất cả các mục
		if (items != null) {
			for (T item : items) {
				comboBox.addItem(item); // Thêm các mục mới
			}
		}
		if (selectedItem != null) {
			comboBox.setSelectedItem(selectedItem); // Đặt mục được chọn
		}

		for (ActionListener listener : listeners) {
			comboBox.addActionListener(listener); // Thêm lại các ActionListener
		}
	}

	// Hàm cập nhật các giá trị tương ứng cho từng ComboBox để chọn Địa chỉ
	public static void renderAllComponentToSelectAddress(String houseNumberAndStreetNameInput,
			JTextField houseNumberAndStreetNameTextField, String cityFirstSelected, JComboBox<String> citysComboBox,
			String districtFirstSelected, JComboBox<String> districtsComboBox, String wardFirstSelected,
			JComboBox<String> wardsComboBox) {
		// Nếu đã chọn ít nhất 1 địa chỉ hợp lệ
		if (houseNumberAndStreetNameInput != null && cityFirstSelected != null && districtFirstSelected != null
				&& wardFirstSelected != null) {
			// - Cập nhật lại ô "Nhập Số nhà - địa chỉ"
			houseNumberAndStreetNameTextField.setText(houseNumberAndStreetNameInput);
			houseNumberAndStreetNameTextField.setForeground(Color.BLACK);

			// - Cập nhật lại ô "Chọn Tỉnh / Thành phố"
			citysComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			citysComboBox.setForeground(Color.BLACK);

			// - Cập nhật lại ô "Chọn Quận / Huyện"
			Set<String> districts = new LinkedHashSet<>();
			for (var entry : CommonPL.addressInfo.entries()) {
				if (entry.getKey().equals(cityFirstSelected)) {
					for (var subEntry : entry.getValue().entries()) {
						districts.add(subEntry.getKey());
					}
				}
			}
			districtsComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			districtsComboBox.setForeground(Color.BLACK);
			updateComboBox(districtsComboBox, districts, districtFirstSelected);

			// - Cập nhật lại ô "Chọn Phường / Xã"
			Set<String> wards = new LinkedHashSet<>();
			for (var entry : CommonPL.addressInfo.entries()) {
				if (entry.getKey().equals(cityFirstSelected)) {
					for (var subEntry : entry.getValue().entries()) {
						if (subEntry.getKey().equals(districtFirstSelected)) {
							wards.add(subEntry.getValue());
						}
					}
				}
			}
			wardsComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			wardsComboBox.setForeground(Color.BLACK);
			updateComboBox(wardsComboBox, wards, wardFirstSelected);

		}
		// Cập nhật Tỉnh/Thành phố
		updateComboBox(citysComboBox, CommonPL.addressInfo.keySet(), cityFirstSelected);

		// Lắng nghe sự kiện thay đổi Tỉnh/Thành phố
		citysComboBox.addActionListener(e -> {
			String selectedCity = (String) citysComboBox.getSelectedItem();
			if (selectedCity != null) {
				Set<String> districts = new LinkedHashSet<>();
				for (var entry : CommonPL.addressInfo.entries()) {
					if (entry.getKey().equals(selectedCity)) {
						for (var subEntry : entry.getValue().entries()) {
							districts.add(subEntry.getKey());
						}
					}
				}

				// Cập nhật lại ô "Chọn Quận / Huyện"
				districtsComboBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
				districtsComboBox.setForeground(Color.LIGHT_GRAY);
				updateComboBox(districtsComboBox, districts, null);

				// Cập nhật lại ô "Chọn Phường / Xã"
				wardsComboBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
				wardsComboBox.setForeground(Color.LIGHT_GRAY);
				Vector<String> wards = new Vector<>();
				wards.add("Chọn Phường / Xã");
				updateComboBox(wardsComboBox, wards, null);
			}
		});

		// Lắng nghe sự kiện thay đổi Quận/Huyện
		districtsComboBox.addActionListener(e -> {
			String selectedCity = (String) citysComboBox.getSelectedItem();
			String selectedDistrict = (String) districtsComboBox.getSelectedItem();
			if (selectedCity != null && selectedDistrict != null) {
				Set<String> wards = new LinkedHashSet<>();
				for (var entry : CommonPL.addressInfo.entries()) {
					if (entry.getKey().equals(selectedCity)) {
						for (var subEntry : entry.getValue().entries()) {
							if (subEntry.getKey().equals(selectedDistrict)) {
								wards.add(subEntry.getValue());
							}
						}
					}
				}

				// Cập nhật lại ô "Chọn Phường / Xã"
				wardsComboBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
				wardsComboBox.setForeground(Color.LIGHT_GRAY);
				updateComboBox(wardsComboBox, wards, null);
			}

		});
	}

	// Gán giá trị vào Map
	public static Map<String, Boolean> getMapHasValues(String[] values) {
		Map<String, Boolean> map = new LinkedHashMap<>();
		for (int i = 0; i < values.length; i++) {
			if (i == 0) {
				map.put(values[i], true);
			} else {
				map.put(values[i], false);
			}
		}
		return map;
	}

	// Trả về chuỗi SQL (Checkboxs)
	public static String getSQLFromCheckboxs(Map<String, Boolean> checkboxs, String[] sqlsInfor) {
		String sql = "";
		int i = 0;
		for (Map.Entry<String, Boolean> entry : checkboxs.entrySet()) {
			if (entry.getValue()) {
				sql += "," + sqlsInfor[i];
			}
			i++;
		}

		return sql.length() != 0 ? sql.substring(1) : null;
	}

	// Trả về chuỗi SQL (Radios)
	public static String getSQLFromRadios(Map<String, Boolean> radios, String[] sqlsInfor) {
		String sql = "";
		int i = 0;
		for (Map.Entry<String, Boolean> entry : radios.entrySet()) {
			if (entry.getValue()) {
				sql += sqlsInfor[i];
			}
			i++;
		}

		return sql.length() != 0 ? sql : null;
	}

	// Cập nhật lại giá trị cho Checkboxs, Radios (tìm kiếm / lọc)
	public static void resetMapForFilter(Map<String, Boolean> map, String[] filtersInfo, JButton button) {
		for (Map.Entry<String, Boolean> entry : map.entrySet()) {
			if (entry.getKey().equals(filtersInfo[0])) {
				entry.setValue(true);
				button.setText(filtersInfo[0]);
			} else {
				entry.setValue(false);
			}
		}
	}

	// Cập nhật lại giá trị cho JXDatePicker (tìm kiếm / lọc)
	public static void resetDatePickerForFilter(JXDatePicker datePicker, String text, Color color, int roundLength) {
		datePicker.getEditor().setText(text);
		datePicker.getEditor().setForeground(color);
		datePicker.getEditor().setBorder(new CustomRoundedBorder(color, roundLength, 0, 0, roundLength));
		((JButton) datePicker.getComponent(1))
				.setBorder(new CustomRoundedBorder(color, 0, roundLength, roundLength, 0));
	}

	// Gán giá trị vào Vector
	public static Vector<String> getVectorHasValues(String[] values) {
		Vector<String> vector = new Vector<>();
		for (int i = 0; i < values.length; i++) {
			vector.add(values[i]);
		}
		return vector;
	}

	// Định dạng nhãn dán chứa văn bản
	public static JLabel getParagraphLabel(String content, Color color, Font font) {
		JLabel label = new JLabel();
		label.setText(content);
		label.setFont(font);
		label.setForeground(color);
		return label;
	}

	// Định dạng nhãn dán chứa hình ảnh
	public static JLabel getImageLabel(int width, int height, String src) {
		ImageIcon image = new ImageIcon(src);
		Image scaledImage = image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		JLabel imageLabel = new JLabel(scaledIcon);
		return imageLabel;
	}

	// Định dạng nhãn dán chứa tiêu đề
	public static JLabel getTitleLabel(String content, Color color, Font font, int horizontalValue, int verticalValue) {
		JLabel label = new JLabel();
		label.setText(content);
		label.setFont(font);
		label.setForeground(color);
		label.setHorizontalAlignment(horizontalValue);
		label.setVerticalAlignment(verticalValue);
		return label;
	}

	// Lấy ra tất cả các JButton
	public static ArrayList<JButton> getAllButtons(Container container) {
		ArrayList<JButton> buttonList = new ArrayList<>();
		for (Component component : container.getComponents()) {
			if (component instanceof JButton) {
				buttonList.add((JButton) component);
			}
		}
		return buttonList;
	}

	// Bo góc cho JButton
	public static class RoundedBorder extends AbstractBorder {
		private final int radius;

		public RoundedBorder(int radius) {
			this.radius = radius;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(c.getForeground());
			g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
		}

		@Override
		public Insets getBorderInsets(Component c) {
			return new Insets(5, 5, 5, 5);
		}

		@Override
		public Insets getBorderInsets(Component c, Insets insets) {
			insets.left = insets.right = insets.top = insets.bottom = 5;
			return insets;
		}
	}

	public static class CustomRoundedButton extends JButton {
		private int arcWidth;
		private int arcHeight;

		public CustomRoundedButton(int arcWidth, int arcHeight, String text) {
			super(text);
			this.arcWidth = arcWidth;
			this.arcHeight = arcHeight;
			setContentAreaFilled(false);
			setFocusPainted(false);
			setBorderPainted(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();

			// Khử răng cưa
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Vẽ nền với bo góc tùy chỉnh
			g2.setColor(getBackground());
			g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arcWidth, arcHeight));

			// Vẽ viền
			g2.setColor(getForeground());
			g2.setStroke(new BasicStroke(2));
			g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight));

			// Vẽ text
			super.paintComponent(g);
			g2.dispose();
		}
	}

	// Định dạng mẫu mặc định cho JButton
	public static JButton getButtonDefaultForm(String content, Font font) {
		JButton button = new JButton();
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setContentAreaFilled(true);
		button.setOpaque(true);
		button.setBorderPainted(true);
		button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		button.setBackground(Color.BLACK);
		button.setText(content);
		button.setFont(font);
		button.setForeground(Color.WHITE);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent evt) {
				try {
					Thread.sleep(100);
					button.setBackground(Color.decode("#42A5F5"));
					button.setForeground(Color.WHITE);
					button.setBorder(BorderFactory.createLineBorder(Color.decode("#42A5F5"), 1));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				button.setBackground(Color.BLACK);
				button.setForeground(Color.WHITE);
				button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			}
		});
		return button;
	}

	// Định dạng nút JButton có bo góc
	public static JButton getRoundedBorderButton(int border, String text, Color bgColor, Color fgColor, Font font) {
		JButton button = new CommonPL.CustomRoundedButton(border, border, text);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBackground(bgColor);
		button.setForeground(fgColor);
		button.setFont(font);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent evt) {
				try {
					Thread.sleep(100);
					button.setBackground(fgColor);
					button.setForeground(bgColor);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				button.setBackground(bgColor);
				button.setForeground(fgColor);
			}
		});

		return button;
	}

	// Định dạng nút JButton có icon
	public static JButton getButtonHasIcon(int buttonWidth, int buttonHeight, int iconWidth, int iconHeight,
			int marginLeft, int marginTop, String src, String content, Color backgroundColor,
			Color backgroundColorHover, Color foregroundColor, Color foregroundColorHover, Font font) {
		// Khai báo đối tượng JButton
		JButton button = new JButton();

		// Định dạng icon
		ImageIcon image = new ImageIcon(src);
		Image scaledImage = image.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		JLabel imageLabel = new JLabel(scaledIcon);
		imageLabel.setBounds(marginLeft, marginTop, iconWidth, iconHeight);

		// Định dạng nhãn dán
		JLabel label = new JLabel();
		label.setText(content);
		label.setBounds(marginLeft * 2 + iconWidth, 0, buttonWidth - marginLeft * 2 + iconWidth, buttonHeight);
		label.setFont(font);
		label.setForeground(foregroundColor);

		// Định dạng các tính chất khác cho nút
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setContentAreaFilled(true);
		button.setOpaque(true);
		button.setBorderPainted(true);
		button.setBorder(BorderFactory.createLineBorder(backgroundColor, 2));
		button.setBackground(Color.WHITE);
		button.setLayout(null);
		button.add(imageLabel);
		button.add(label);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent evt) {
				try {
					Thread.sleep(100);
					// imageLabel.setOpaque(false);
					button.setBorder(BorderFactory.createLineBorder(backgroundColorHover, 2));
					// button.setBackground(backgroundColorHover);
					label.setForeground(foregroundColorHover);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				// imageLabel.setOpaque(true);
				button.setBorder(BorderFactory.createLineBorder(backgroundColor, 2));
				// button.setBackground(backgroundColor);
				label.setForeground(foregroundColor);
			}
		});
		return button;
	}

	// Định dạng mẫu mặc định cho JButton ở Left Menu
	public static JButton getButtonInLeftMenuForm(String content, Color fontColorHover, Color fontColor, Font font) {
		JButton button = new JButton();
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setContentAreaFilled(true);
		button.setOpaque(true);
		button.setBorderPainted(true);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setBackground(Color.WHITE);
		button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		button.setText(content);
		button.setFont(font);
		button.setForeground(fontColor);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent evt) {
				try {
					Thread.sleep(100);
					button.setForeground(fontColorHover);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				button.setForeground(fontColor);
			}
		});
		return button;
	}

	// Định dạng mẫu mặc định cho JButton có ý nghĩa là nút thao tác cuối cùng
	// public static JButton getLastButtonForm(String content, Color
	// backgroundColor, Color fontColor, Font font) {
	// JButton button = new JButton();
	// button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	// button.setContentAreaFilled(true);
	// button.setOpaque(true);
	// button.setBorderPainted(true);
	// button.setBorder(BorderFactory.createLineBorder(backgroundColor, 2));
	// button.setBackground(backgroundColor);
	// button.setText(content);
	// button.setFont(font);
	// button.setForeground(fontColor);
	// button.addMouseListener(new MouseAdapter() {
	// @Override
	// public void mouseEntered(MouseEvent e) {
	// try {
	// Thread.sleep(100);
	// button.setForeground(backgroundColor);
	// button.setBackground(fontColor);
	// } catch (InterruptedException e1) {
	// e1.printStackTrace();
	// }
	// }
	//
	// @Override
	// public void mouseExited(MouseEvent e) {
	// button.setBackground(backgroundColor);
	// button.setForeground(fontColor);
	// }
	// });
	// return button;
	// }

	// Định dạng mẫu mặc định cho icon (?)
	public static JButton getQuestionIconForm(String icon, String inform, String informDetail, Color color, Font font) {
		JButton button = new JButton();
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setOpaque(false);
		button.setBorder(new CommonPL.RoundedBorder(999));
		button.setText(icon);
		button.setFont(font);
		button.setForeground(color);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CommonPL.createInformDialog(inform, informDetail);
			}
		});
		return button;
	}

	// Bo góc JPanel
	public static class RoundedPanel extends JPanel {
		private int cornerRadius;

		public RoundedPanel(int cornerRadius) {
			this.cornerRadius = cornerRadius;
			setOpaque(false); // Đảm bảo nền trong suốt để không che lớp bo góc
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();

			// Bật chống răng cưa
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Vẽ nền bo góc
			g2.setColor(getBackground());
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

			g2.dispose();
		}
	}

	// Tạo viền nét đứt cho Panel
	// public static class DashedBorder extends AbstractBorder {
	// @Override
	// public void paintBorder(Component c, Graphics g, int x, int y, int width, int
	// height) {
	// Graphics2D g2d = (Graphics2D) g.create();
	// g2d.setColor(Color.BLACK); // Màu của viền
	// float[] dashPattern = { 10, 2 }; // Độ dài nét đứt và khoảng cách giữa các
	// nét
	// g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,
	// BasicStroke.JOIN_BEVEL, 0, dashPattern, 0));
	//
	// // Vẽ viền
	// g2d.drawRect(x, y, width - 1, height - 1);
	// g2d.dispose();
	// }
	//
	// @Override
	// public Insets getBorderInsets(Component c) {
	// return new Insets(5, 5, 5, 5); // Khoảng cách từ viền tới nội dung
	// }
	// }

	// Bo góc cho JTextField
	public static class CustomTextField extends JTextField {
		private Color borderColor = Color.LIGHT_GRAY; // Màu viền mặc định
		private int arcWidth = 0; // Bo góc mặc định (0 = vuông)
		private int arcHeight = 0;

		public CustomTextField(int columns, int arcWidth, int arcHeight, String content, Color color, Color colorHover,
				Font font) {
			super(columns);
			this.borderColor = color;
			this.arcWidth = arcWidth;
			this.arcHeight = arcHeight;
			setText(content);
			setFont(font);
			setForeground(color);
			setOpaque(false); // Đảm bảo nền trong suốt để custom border
			setBorder(new EmptyBorder(5, 10, 5, 10)); // Đệm phần nội dung

			getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent e) {
					checkContent();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					checkContent();
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					checkContent();
				}

				private void checkContent() {
					if (!getText().equals(content)) {
						setBorderColor(colorHover);
					} else {
						setBorderColor(color);
					}
				}
			});

			addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					if (getText().equals(content)) {
						setText("");
						setForeground(colorHover);
					}
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (getText().isEmpty()) {
						setText(content);
						setForeground(color);
					}
				}
			});
		}

		public void setBorderColor(Color color) {
			this.borderColor = color;
			repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();

			// Bật khử răng cưa để viền mượt hơn
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Vẽ nền JTextField
			g2.setColor(getBackground());
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

			// Vẽ viền ngoài với màu tùy chỉnh
			g2.setColor(borderColor);
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);

			g2.dispose();
			super.paintComponent(g);
		}

		@Override
		protected void paintBorder(Graphics g) {
			// Không vẽ viền mặc định
		}
	}

	// Bo góc cho JPasswordField
	public static class CustomPasswordField extends JPasswordField {
		private Color borderColor = Color.LIGHT_GRAY; // Màu viền mặc định
		private int arcWidth = 0; // Bo góc mặc định (0 = vuông)
		private int arcHeight = 0;

		public CustomPasswordField(int columns, int arcWidth, int arcHeight, String content, Color color,
				Color colorHover, Font font) {
			super(columns);
			this.borderColor = color;
			this.arcWidth = arcWidth;
			this.arcHeight = arcHeight;
			setText(content);
			setFont(font);
			setForeground(color);
			setOpaque(false); // Đảm bảo nền trong suốt để custom border
			setBorder(new EmptyBorder(5, 10, 5, 10)); // Đệm phần nội dung

			getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent e) {
					checkContent();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					checkContent();
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					checkContent();
				}

				private void checkContent() {
					if (!getText().equals(content)) {
						setBorderColor(colorHover);
					} else {
						setBorderColor(color);
					}
				}
			});

			addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					if (getText().equals(content)) {
						setText("");
						setForeground(colorHover);
					}
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (getText().isEmpty()) {
						setText(content);
						setForeground(color);
					}
				}
			});
		}

		public void setBorderColor(Color color) {
			this.borderColor = color;
			repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();

			// Bật khử răng cưa để viền mượt hơn
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Vẽ nền JTextField
			g2.setColor(getBackground());
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

			// Vẽ viền ngoài với màu tùy chỉnh
			g2.setColor(borderColor);
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);

			g2.dispose();
			super.paintComponent(g);
		}

		@Override
		protected void paintBorder(Graphics g) {
			// Không vẽ viền mặc định
		}
	}

	// Bo góc cho JTextArea
	public static class CustomTextArea extends JTextArea {
		private Color borderColor = Color.LIGHT_GRAY; // Màu viền mặc định
		private int arcWidth = 0; // Bo góc mặc định (0 = vuông)
		private int arcHeight = 0;

		public CustomTextArea(int rows, int columns, int arcWidth, int arcHeight, String content, Color borderColor,
				Color borderColorActive, Color fontColor, Color fontColorActive, Font font) {
			super(rows, columns);
			this.borderColor = borderColor;
			this.arcWidth = arcWidth;
			this.arcHeight = arcHeight;
			setText(content);
			setFont(font);
			setForeground(fontColor);
			setLineWrap(true); // Tự động xuống dòng
			setWrapStyleWord(true); // Ngắt dòng theo từ
			setOpaque(false); // Đảm bảo nền trong suốt để custom border
			setBorder(new EmptyBorder(5, 10, 5, 10)); // Đệm phần nội dung

			getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent e) {
					checkContent();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					checkContent();
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					checkContent();
				}

				private void checkContent() {
					if (!getText().equals(content)) {
						setBorderColor(borderColorActive);
					} else {
						setBorderColor(borderColor);
					}
				}
			});

			addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					if (getText().equals(content)) {
						setText("");
						setForeground(fontColorActive);
					}
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (getText().isEmpty()) {
						setText(content);
						setForeground(fontColor);
					}
				}
			});
		}

		public void setBorderColor(Color color) {
			this.borderColor = color;
			repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();

			// Bật khử răng cưa để viền mượt hơn
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Vẽ nền JTextArea
			g2.setColor(getBackground());
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

			// Vẽ viền ngoài với màu tùy chỉnh
			g2.setColor(borderColor);
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);

			g2.dispose();
			super.paintComponent(g);
		}

		@Override
		protected void paintBorder(Graphics g) {
			// Không vẽ viền mặc định
		}
	}

	// Định dạng JComboBox
	public static JComboBox<String> CustomComboBox(Vector<String> items, Color backgroundColor, Color foregroundColor,
			Color foregroundColorSelected, Color labelBackgroundColor, Color labelForegroundColor,
			Color labelBackgroundColorSelected, Color labelForegroundColorSelected, Font font) {
		// Tắt giao diện mặc định (UI) của hệ điều hành
		UIManager.put("ComboBox.background", backgroundColor);
		UIManager.put("ComboBox.selectionBackground", backgroundColor);
		UIManager.put("ComboBox.selectionForeground", foregroundColor);

		// Khởi tạo đối tượng ComboBox
		JComboBox<String> comboBox = new JComboBox<>(items);

		// Tuỳ chỉnh ComboBox
		comboBox.setBackground(backgroundColor);
		comboBox.setBorder(BorderFactory.createLineBorder(foregroundColor, 1));
		comboBox.setFont(font);
		comboBox.setForeground(foregroundColor);
		comboBox.setUI(new BasicComboBoxUI() {
			@Override
			protected JButton createArrowButton() {
				JButton arrowButton = super.createArrowButton();

				arrowButton.setPreferredSize(new Dimension(20, 40)); // Chỉ thay đổi chiều cao nút mũi tên
				arrowButton.setBorderPainted(false); // Loại bỏ viền
				arrowButton.setContentAreaFilled(false); // Loại bỏ bóng đổ (nền trong suốt)
				arrowButton.setFocusPainted(false); // Loại bỏ bóng khi có focus

				return arrowButton;
			}
		});
		// Tùy chỉnh danh sách hiển thị
		comboBox.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
						cellHasFocus);
				label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
				if (isSelected) {
					label.setForeground(labelForegroundColorSelected);
					label.setBackground(labelBackgroundColorSelected);
				} else {
					label.setBackground(labelBackgroundColor);
					label.setForeground(labelForegroundColor);
				}
				return label;
			}
		});
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (String.valueOf(comboBox.getSelectedItem()).equals(items.get(0))
						&& !comboBox.getSelectedItem().equals("Tất cả")) {
					UIManager.put("ComboBox.background", backgroundColor);
					UIManager.put("ComboBox.selectionBackground", backgroundColor);
					UIManager.put("ComboBox.selectionForeground", foregroundColor);
					comboBox.setUI(new BasicComboBoxUI() {
						@Override
						protected JButton createArrowButton() {
							JButton arrowButton = super.createArrowButton();

							arrowButton.setPreferredSize(new Dimension(20, 40)); // Chỉ thay đổi chiều cao nút mũi tên
							arrowButton.setBorderPainted(false); // Loại bỏ viền
							arrowButton.setContentAreaFilled(false); // Loại bỏ bóng đổ (nền trong suốt)
							arrowButton.setFocusPainted(false); // Loại bỏ bóng khi có focus

							return arrowButton;
						}
					});
					comboBox.setBorder(BorderFactory.createLineBorder(foregroundColor, 1));
					comboBox.setForeground(foregroundColor);
				} else {
					UIManager.put("ComboBox.background", backgroundColor);
					UIManager.put("ComboBox.selectionBackground", backgroundColor);
					UIManager.put("ComboBox.selectionForeground", foregroundColorSelected);
					comboBox.setUI(new BasicComboBoxUI() {
						@Override
						protected JButton createArrowButton() {
							JButton arrowButton = super.createArrowButton();

							arrowButton.setPreferredSize(new Dimension(20, 40)); // Chỉ thay đổi chiều cao nút mũi tên
							arrowButton.setBorderPainted(false); // Loại bỏ viền
							arrowButton.setContentAreaFilled(false); // Loại bỏ bóng đổ (nền trong suốt)
							arrowButton.setFocusPainted(false); // Loại bỏ bóng khi có focus

							return arrowButton;
						}
					});
					comboBox.setBorder(BorderFactory.createLineBorder(foregroundColorSelected, 1));
					comboBox.setForeground(foregroundColorSelected);
				}
				comboBox.revalidate();
				comboBox.repaint();

			}
		});

		return comboBox;
	}

	// Tạo modal thông báo thông thường
	public static void createInformDialog(String title, String content) {
		// Khai báo đối tượng Dialog
		JDialog dialog = new JDialog();

		// Tạo nội dung cần thông báo
		String inforCanFind = "<html>" + content + "</html>";
		JLabel infoLabel = new JLabel(inforCanFind, JLabel.CENTER);
		infoLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		infoLabel.setBounds(20, 20, 360, 190);

		// Tạo nút "Đóng"
		JButton closeButton = CommonPL.getButtonDefaultForm("Đóng", new Font("Arial", Font.BOLD, 20));
		closeButton.setBounds(20, 210, 360, 40);
		closeButton.addActionListener(event -> {
			dialog.dispose();
		});

		// Tạo block để hiển thị nền trắng
		JPanel blockPanel = new JPanel();
		blockPanel.setLayout(null);
		blockPanel.setBackground(Color.WHITE);
		blockPanel.setBounds(0, 0, 400, 300);
		blockPanel.add(infoLabel);
		blockPanel.add(closeButton);

		// Định nghĩa tính cho Dialog
		dialog.setTitle(title);
		dialog.setSize(400, 300);
		dialog.setLayout(null);
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(null);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.add(blockPanel);
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent e) {
				dialog.dispose(); // Đóng Dialog khi mất focus (nhấn ngoài)
			}
		});
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	// Tạo modal thông báo lỗi
	public static void createErrorDialog(String title, String content) {
		// Khai báo đối tượng Dialog
		JDialog dialog = new JDialog();

		// Tạo hình ảnh "x"
		ImageIcon image = new ImageIcon(CommonPL.getMiddlePathToShowIcon() + "no-icon.png");
		Image scaledImage = image.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		JLabel imageLabel = new JLabel(scaledIcon);
		JPanel imagePanel = new JPanel(new BorderLayout());
		imagePanel.setBounds(20, 20, 80, 80);
		imagePanel.setBackground(Color.WHITE);
		imagePanel.add(imageLabel, BorderLayout.CENTER);

		// Tạo nội dung cần thông báo
		String inforNeedInform = "<html>" + content + "</html>";
		JLabel infoLabel = new JLabel(inforNeedInform, JLabel.CENTER);
		infoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		infoLabel.setBounds(120, 20, 260, 80);

		// Tạo nút "Đóng"
		JButton closeButton = CommonPL.getButtonDefaultForm("Đóng", new Font("Arial", Font.BOLD, 18));
		closeButton.setBounds(280, 120, 100, 30);
		closeButton.addActionListener(event -> {
			dialog.dispose();
		});
		SwingUtilities.invokeLater(() -> closeButton.requestFocusInWindow());

		// Tạo block để hiển thị nền trắng
		JPanel blockPanel = new JPanel();
		blockPanel.setLayout(null);
		blockPanel.setBackground(Color.WHITE);
		blockPanel.setBounds(0, 0, 400, 200);
		blockPanel.add(imagePanel);
		blockPanel.add(infoLabel);
		blockPanel.add(closeButton);

		// Định nghĩa tính cho Dialog
		dialog.setTitle(title);
		dialog.setSize(400, 200);
		dialog.setLayout(null);
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(null);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.add(blockPanel);
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent e) {
				dialog.dispose(); // Đóng Dialog khi mất focus (nhấn ngoài)
			}
		});
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	// Tạo modal thông báo thành công
	public static void createSuccessDialog(String title, String content) {
		// Khai báo đối tượng Dialog
		JDialog dialog = new JDialog();

		// Tạo hình ảnh "✓"
		ImageIcon image = new ImageIcon(CommonPL.getMiddlePathToShowIcon() + "yes-icon.png");
		Image scaledImage = image.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		JLabel imageLabel = new JLabel(scaledIcon);
		JPanel imagePanel = new JPanel(new BorderLayout());
		imagePanel.setBounds(20, 20, 80, 80);
		imagePanel.setBackground(Color.WHITE);
		imagePanel.add(imageLabel, BorderLayout.CENTER);

		// Tạo nội dung cần thông báo
		String inforNeedInform = "<html>" + content + "</html>";
		JLabel infoLabel = new JLabel(inforNeedInform, JLabel.CENTER);
		infoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		infoLabel.setBounds(120, 20, 260, 80);

		// Tạo nút "Đóng"
		JButton closeButton = CommonPL.getButtonDefaultForm("Đóng", new Font("Arial", Font.BOLD, 18));
		closeButton.setBounds(280, 120, 100, 30);
		closeButton.addActionListener(event -> {
			dialog.dispose();
		});
		SwingUtilities.invokeLater(() -> closeButton.requestFocusInWindow());

		// Tạo block để hiển thị nền trắng
		JPanel blockPanel = new JPanel();
		blockPanel.setLayout(null);
		blockPanel.setBackground(Color.WHITE);
		blockPanel.setBounds(0, 0, 400, 200);
		blockPanel.add(imagePanel);
		blockPanel.add(infoLabel);
		blockPanel.add(closeButton);

		// Định nghĩa tính cho Dialog
		dialog.setTitle(title);
		dialog.setSize(400, 200);
		dialog.setLayout(null);
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(null);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent e) {
				dialog.dispose(); // Đóng Dialog khi mất focus (nhấn ngoài)
			}
		});
		dialog.add(blockPanel);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	// Tạo modal để thực viện lựa chọn Có / Không
	public static void createSelectionsDialog(String title, String content, final Boolean[] valueSelected) {
		// Khai báo các đối tượng Dialog
		JDialog dialog = new JDialog();

		// Tạo hình ảnh "!"
		ImageIcon image = new ImageIcon(CommonPL.getMiddlePathToShowIcon() + "error-icon.png");
		Image scaledImage = image.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		JLabel imageLabel = new JLabel(scaledIcon);
		JPanel imagePanel = new JPanel(new BorderLayout());
		imagePanel.setBounds(20, 20, 80, 80);
		imagePanel.setBackground(Color.WHITE);
		imagePanel.add(imageLabel, BorderLayout.CENTER);

		// Tạo nội dung cần thông báo
		String inforNeedInform = "<html>" + content + "</html>";
		JLabel infoLabel = new JLabel(inforNeedInform, JLabel.CENTER);
		infoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		infoLabel.setBounds(120, 20, 260, 80);

		// Tạo nút "Có"
		JButton yesButton = CommonPL.getButtonHasIcon(100, 30, 20, 20, 8, 5,
				CommonPL.getMiddlePathToShowIcon() + "yes-icon.png", "Có", Color.BLACK, Color.decode("#699f4c"),
				Color.BLACK, Color.decode("#699f4c"), new Font("Arial", Font.BOLD, 18));
		yesButton.setBounds(160, 120, 100, 30);
		yesButton.addActionListener(e -> {
			valueSelected[0] = true;
			dialog.dispose();
		});
		SwingUtilities.invokeLater(() -> yesButton.requestFocusInWindow());

		// Tạo nút "Không"
		JButton noButton = CommonPL.getButtonHasIcon(100, 30, 20, 20, 8, 5,
				CommonPL.getMiddlePathToShowIcon() + "no-icon.png", "Không", Color.BLACK, Color.decode("#9f4d4d"),
				Color.BLACK, Color.decode("#9f4d4d"), new Font("Arial", Font.BOLD, 18));
		noButton.setBounds(280, 120, 100, 30);
		noButton.addActionListener(e -> {
			valueSelected[0] = false;
			dialog.dispose();
		});

		// Tạo block để hiển thị nền trắng
		JPanel blockPanel = new JPanel();
		blockPanel.setLayout(null);
		blockPanel.setBackground(Color.WHITE);
		blockPanel.setBounds(0, 0, 400, 200);
		blockPanel.add(imagePanel);
		blockPanel.add(infoLabel);
		blockPanel.add(yesButton);
		blockPanel.add(noButton);

		// Định nghĩa tính cho Dialog
		dialog.setTitle(title);
		dialog.setSize(400, 200);
		dialog.setLayout(null);
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(null);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.add(blockPanel);
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent e) {
				valueSelected[0] = null; // Nếu không chọn thì sẽ null
				dialog.dispose(); // Đóng Dialog khi mất focus (nhấn ngoài)
			}
		});
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	// Lớp Button Has Checkboxs
	public class ButtonHasCheckboxs {
		// Constructors
		public ButtonHasCheckboxs() {

		}

		// Methods
		// - Phương thức tạo modal thông báo cho việc lựa chọn các checkbox
		public static void createCheckBoxsInDialog(JButton button, Map<String, Boolean> checkboxs, Font font) {
			// Khai báo đối tượng Dialog
			JDialog dialog = new JDialog();
			// Tính chiều cao của Dialog
			int dialogHeight = 20 + 36 * checkboxs.size() + 48 + 40 + 20;

			// Tạo các checkbox item
			int i = 0;
			for (String key : checkboxs.keySet()) {
				JCheckBox itemCheckBox = new JCheckBox();
				itemCheckBox.setBounds(20, 20 + i, 360, 24);
				if (checkboxs.get(key) == true) {
					itemCheckBox.setSelected(true);
				}
				itemCheckBox.setFont(font);
				itemCheckBox.setText(key);
				itemCheckBox.addActionListener(e -> {
					if (checkboxs.get(key) == true) {
						checkboxs.replace(key, false);
					} else {
						checkboxs.replace(key, true);
					}
				});
				dialog.add(itemCheckBox);
				i += 36;
			}

			// Tạo nút "Đóng"
			JButton closeButton = CommonPL.getButtonDefaultForm("Đóng", new Font("Arial", Font.BOLD, 20));
			closeButton.setBounds(20, dialogHeight - 88, 360, 40);
			closeButton.addActionListener(event -> {
				// Thay đổi nội dung ở nút với radio item đã được chọn
				String checkboxItemValue = null;
				int count = 0;
				for (String s : checkboxs.keySet()) {
					if (checkboxs.get(s) == true) {
						checkboxItemValue = s;
						count++;
					}
				}

				if (count == 0) {
					CommonPL.createErrorDialog("Thông báo lỗi", "Cần chọn ít nhất một tiêu chí");
					// dialog.setVisible(false);
					// SwingUtilities.invokeLater(() -> {
					// JOptionPane.showMessageDialog(null, "Cần chọn ít nhất một tiêu chí");
					// dialog.setVisible(true);
					// });
				} else if (count == 1) {
					button.setText(checkboxItemValue);
					dialog.dispose();
				} else {
					button.setText("Đã chọn nhiều tiêu chí");
					dialog.dispose();
				}
			});

			// Tạo Block để có hiển thị màu nền trắng cho Dialog
			JPanel blockPanel = new JPanel();
			blockPanel.setBackground(Color.WHITE);
			blockPanel.setBounds(0, 0, 400, dialogHeight);

			// Định nghĩa tính chất cho Dialog
			dialog.setTitle("Các tiêu chí bạn có thể lựa chọn");
			dialog.setSize(400, dialogHeight);
			dialog.setLayout(null);
			dialog.setResizable(false);
			dialog.setLocationRelativeTo(null);
			dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			dialog.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					// Thay đổi nội dung ở nút với radio item đã được chọn
					String checkboxItemValue = null;
					int count = 0;
					for (String s : checkboxs.keySet()) {
						if (checkboxs.get(s) == true) {
							checkboxItemValue = s;
							count++;
						}
					}

					if (count == 0) {
						CommonPL.createErrorDialog("Thông báo lỗi", "Cần chọn ít nhất một tiêu chí");
						// dialog.setVisible(false);
						// SwingUtilities.invokeLater(() -> {
						// JOptionPane.showMessageDialog(null, "Cần chọn ít nhất một tiêu chí");
						// dialog.setVisible(true);
						// });
					} else if (count == 1) {
						button.setText(checkboxItemValue);
						dialog.dispose();
					} else {
						button.setText("Đã chọn nhiều tiêu chí");
						dialog.dispose();
					}
				}
			});
			dialog.add(closeButton);
			dialog.add(blockPanel);
			dialog.setModal(true);
			dialog.setVisible(true);
		}

		// - Phương thức tạo nút nhấn chứa các checkboxs
		public static JButton createButtonHasCheckboxs(Map<String, Boolean> checkboxs, String firstCheckboxItem,
				Color color, Color colorHover, Font font) {
			JButton button = new JButton();
			button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			button.setBorder(new CommonPL.RoundedBorder(20));
			button.setText(firstCheckboxItem);
			button.setFont(font);
			button.setForeground(color);
			button.addActionListener(e -> {
				ButtonHasCheckboxs.createCheckBoxsInDialog(button, checkboxs, font);
			});
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					try {
						Thread.sleep(100);
						button.setForeground(colorHover);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					button.setForeground(color);
				}
			});
			return button;
		}
	}

	// Lớp Button Has Radios
	public class ButtonHasRadios {
		// Constructors
		public ButtonHasRadios() {

		}

		// Methods
		// - Phương thức tạo modal thông báo cho việc lựa chọn các radios
		public static void createRadiosInDialog(JButton button, Map<String, Boolean> radios, Font font) {
			// Khai báo đối tượng Dialog
			JDialog dialog = new JDialog();
			// Tính chiều cao của Dialog
			int dialogHeight = 20 + 36 * radios.size() + 48 + 40 + 20;

			ButtonGroup radiosGroup = new ButtonGroup();
			int i = 0;
			for (String key : radios.keySet()) {
				JRadioButton itemRadio = new JRadioButton();
				itemRadio.setBounds(20, 20 + i, 360, 24);
				if (radios.get(key) == true) {
					itemRadio.setSelected(true);
				}
				itemRadio.setFont(font);
				itemRadio.setText(key);
				itemRadio.addActionListener(e -> {
					if (radios.get(key) != true) {
						radios.replace(key, true);
					}
					for (String subKey : radios.keySet()) {
						if (key != subKey) {
							radios.replace(subKey, false);
						}
					}
				});
				radiosGroup.add(itemRadio);
				dialog.add(itemRadio);
				i += 36;
			}

			JButton closeButton = CommonPL.getButtonDefaultForm("Đóng", new Font("Arial", Font.BOLD, 20));
			closeButton.setBounds(20, dialogHeight - 88, 360, 40);
			closeButton.addActionListener(event -> {
				dialog.dispose();
			});

			JPanel blockPanel = new JPanel();
			blockPanel.setBackground(Color.WHITE);
			blockPanel.setBounds(0, 0, 400, dialogHeight);

			// Định nghĩa tính chất cho Dialog
			dialog.setTitle("Các tiêu chí bạn có thể chọn");
			dialog.setSize(400, dialogHeight);
			dialog.setLayout(null);
			dialog.setResizable(false);
			dialog.setLocationRelativeTo(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.addWindowListener(new WindowAdapter() {
				@Override
				public void windowDeactivated(WindowEvent e) {
					// Thay đổi nội dung ở nút với radio item đã được chọn
					String radioItemValue = null;
					for (String s : radios.keySet()) {
						if (radios.get(s) == true) {
							radioItemValue = s;
						}
					}
					button.setText(radioItemValue);

					// Đóng Dialog khi mất focus (nhấn ngoài)
					dialog.dispose();
				}
			});
			dialog.add(closeButton);
			dialog.add(blockPanel);
			dialog.setModal(true);
			dialog.setVisible(true);
		}

		// - Phương thức tạo nút nhấn chứa các radios
		public static JButton createButtonHasRadios(Map<String, Boolean> radios, String firsRadioItem, Color color,
				Color colorHover, Font font) {
			JButton button = new JButton();
			button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			button.setBorder(new CommonPL.RoundedBorder(20));
			button.setText(firsRadioItem);
			button.setFont(font);
			button.setForeground(color);
			button.addActionListener(e -> {
				ButtonHasRadios.createRadiosInDialog(button, radios, font);
			});
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					try {
						Thread.sleep(100);
						button.setForeground(colorHover);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					button.setForeground(color);
				}
			});
			return button;
		}
	}

	// Vô hiệu hoá một số ngày cụ thể trong Date Picker
	public static void disableDaysInDatePicker(JXDatePicker datePicker, Vector<Integer> dayOfWeek, String content) {
		// Lấy ra đối tượng giao diện lịch
		JXMonthView monthView = datePicker.getMonthView();

		// Lấy đối tượng Calendar hiện tại
		Calendar calendar = Calendar.getInstance();
		// Lấy ngày, tháng, năm hiện tại
		// - Ngày tính từ 1 đến 31
		int currentYear = calendar.get(Calendar.YEAR);
		// - Tháng tính từ 0 đến 11 tương đương từ tháng 1 đến tháng 12
		int currentMonth = calendar.get(Calendar.MONTH);
		// - Năm là các số nguyên không âm
		int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

		// Mảng chứa các ngày sẽ bị vô hiệu hoá
		List<Date> days = new ArrayList<>();

		// Tạo đối tượng lịch được chọn để vô hiệu hoá các ngày
		Calendar calendarSelected = Calendar.getInstance();

		// Biến giữ số năm trước / sau khi vô hiệu hoá (không chỉ tính năm hiện tại)
		int yearLength = 10;

		// Duyệt từ số năm trước đến năm hiện tại
		for (int year = currentYear - yearLength; year <= currentYear; year++) {
			// Đặt năm được chọn hiện tại
			calendarSelected.set(Calendar.YEAR, year);

			// Duyệt các ngày trước ngày hiện tại (vô hiệu hoá hết)
			disableAll: for (int month = Calendar.JANUARY; month <= Calendar.DECEMBER; month++) {
				// Đặt tháng được chọn hiện tại
				calendarSelected.set(Calendar.MONTH, month);
				// Nếu không phải là tháng hiện tại thì đặt ngày là 1
				calendarSelected.set(Calendar.DAY_OF_MONTH, 1);

				// Lặp qua tất cả các ngày trong tháng
				while (calendarSelected.get(Calendar.MONTH) == month) {
					// Nếu là ngày tháng năm hiện tại thì dừng
					if (calendarSelected.get(Calendar.YEAR) == currentYear
							&& calendarSelected.get(Calendar.MONTH) == currentMonth
							&& calendarSelected.get(Calendar.DAY_OF_MONTH) == currentDay) {
						break disableAll;
					}

					for (int i = 0; i < dayOfWeek.size(); i++) {
						// Vô hiệu hoá tất cả các ngày
						days.add(calendarSelected.getTime());
					}
					// Tăng lên ngày tiếp theo
					calendarSelected.add(Calendar.DAY_OF_MONTH, 1);
				}
			}
		}

		// Biến tạm đảm bảo duyệt từ ngày tháng năm hiện tại
		boolean isCurrentDateValid = false;
		// Duyệt từ năm hiện tại đến số năm sau
		for (int year = currentYear; year <= currentYear + yearLength; year++) {
			// Đặt năm được chọn hiện tại
			calendarSelected.set(Calendar.YEAR, year);

			// Duyệt các ngày sau ngày hiện tại (vô hiệu hoá một số ngày)
			disableSome: for (int month = Calendar.JANUARY; month <= Calendar.DECEMBER; month++) {
				// Đặt tháng được chọn hiện tại
				calendarSelected.set(Calendar.MONTH, month);
				// Đặt ngày được chọn hiện tại là 1
				calendarSelected.set(Calendar.DAY_OF_MONTH, 1);

				// Nếu chưa được duyệt từ ngày tháng năm hiện tại
				if (!isCurrentDateValid) {
					calendarSelected.set(Calendar.MONTH, currentMonth);
					calendarSelected.set(Calendar.DAY_OF_MONTH, currentDay);
					isCurrentDateValid = true;
				}

				// Lặp qua tất cả các ngày trong tháng
				while (calendarSelected.get(Calendar.MONTH) == month) {
					for (int i = 0; i < dayOfWeek.size(); i++) {
						// Kiểm tra nếu là thứ cần vô hiệu hoá (tạm là thứ 2)
						if (calendarSelected.get(Calendar.DAY_OF_WEEK) == (int) dayOfWeek.get(i)) {
							days.add(calendarSelected.getTime());
						}
					}
					// Tăng lên ngày tiếp theo
					calendarSelected.add(Calendar.DAY_OF_MONTH, 1);
				}
			}
		}

		// Thiết lập tất cả các ngày thứ Hai không thể chọn
		monthView.setUnselectableDates(days.toArray(new Date[0]));

		// Gán lại nội dung của ô nhập văn bản
		datePicker.getEditor().setText(content);
	}

	// Lớp Custom Corner Date Picker
	public class CustomCornerDatePicker {
		public static JXDatePicker CustomDatePicker(int roundLength, Locale locale, SimpleDateFormat dateFormat,
				String content, Color color, Color colorActive, Font font, int buttonWidth, int buttonHeight) {
			JXDatePicker datePicker = new JXDatePicker();
			datePicker.setLocale(locale);
			datePicker.setFormats(dateFormat);
			datePicker.setFont(font);

			// Định dạng khung chứa nội dung (editor)
			datePicker.getEditor().setFocusable(false);
			datePicker.getEditor().setText(content);
			datePicker.getEditor().setForeground(color);
			// - Bo góc chỉ góc trái (trên và dưới) cho editor
			datePicker.getEditor().setBorder(new CustomRoundedBorder(color, roundLength, 0, 0, roundLength));
			// - Gán sự kiện cho ô nhập liệu
			// datePicker.getEditor().addFocusListener(new FocusAdapter() {
			// @Override
			// public void focusGained(FocusEvent e) {
			// if (datePicker.getEditor().getText().equals(content)) {
			// datePicker.getEditor()
			// .setBorder(new CustomRoundedBorder(colorActive, roundLength, 0, 0,
			// roundLength));
			// datePicker.getEditor().setText("");
			// datePicker.getEditor().setForeground(colorActive);
			// ((JButton) datePicker.getComponent(1))
			// .setBorder(new CustomRoundedBorder(colorActive, 0, roundLength, roundLength,
			// 0));
			// }
			// }
			//
			// @Override
			// public void focusLost(FocusEvent e) {
			// if (datePicker.getEditor().getText().isEmpty()) {
			// datePicker.getEditor()
			// .setBorder(new CustomRoundedBorder(color, roundLength, 0, 0, roundLength));
			// datePicker.getEditor().setText(content);
			// datePicker.getEditor().setForeground(color);
			// ((JButton) datePicker.getComponent(1))
			// .setBorder(new CustomRoundedBorder(color, 0, roundLength, roundLength, 0));
			// }
			// }
			// });

			// Định dạng lại nút nhấn (button)
			JButton button = (JButton) datePicker.getComponent(1); // Lấy nút nhấn
			button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
			// - Bo góc chỉ góc phải (trên và dưới) cho button
			button.setBorder(new CustomRoundedBorder(color, 0, roundLength, roundLength, 0));

			// Bắt sự kiện khi thay đổi ngày
			datePicker.addActionListener(e -> {
				if (datePicker.getDate() != null) {
					datePicker.getEditor()
							.setBorder(new CustomRoundedBorder(colorActive, roundLength, 0, 0, roundLength));
					datePicker.getEditor().setForeground(colorActive);
					button.setBorder(new CustomRoundedBorder(colorActive, 0, roundLength, roundLength, 0));
				} else {
					datePicker.getEditor().setBorder(new CustomRoundedBorder(color, roundLength, 0, 0, roundLength));
					datePicker.getEditor().setForeground(color);
					button.setBorder(new CustomRoundedBorder(color, 0, roundLength, roundLength, 0));
				}
			});

			return datePicker;
		}

		// Lớp Border tùy chỉnh để bo góc theo từng góc
		public static class CustomRoundedBorder implements Border {
			private final Color color;
			private final int topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius;

			public CustomRoundedBorder(Color color, int topLeftRadius, int topRightRadius, int bottomRightRadius,
					int bottomLeftRadius) {
				this.color = color;
				this.topLeftRadius = topLeftRadius;
				this.topRightRadius = topRightRadius;
				this.bottomRightRadius = bottomRightRadius;
				this.bottomLeftRadius = bottomLeftRadius;
			}

			@Override
			public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setColor(color);

				// Xử lý bo góc và vẽ viền với các đoạn thẳng và đường cong nối tiếp nhau

				// Bo góc trên trái
				if (topLeftRadius > 0) {
					g2d.drawArc(x, y, topLeftRadius * 2, topLeftRadius * 2, 90, 90);
				}

				// Bo góc dưới trái
				if (bottomLeftRadius > 0) {
					g2d.drawArc(x, y + height - bottomLeftRadius * 2 - 1, bottomLeftRadius * 2, bottomLeftRadius * 2,
							180, 90);
				}

				// Bo góc trên phải
				if (topRightRadius > 0) {
					g2d.drawArc(x + width - topRightRadius * 2 - 1, y, topRightRadius * 2, topRightRadius * 2, 0, 90);
				}

				// Bo góc dưới phải
				if (bottomRightRadius > 0) {
					g2d.drawArc(x + width - bottomRightRadius * 2 - 1, y + height - bottomRightRadius * 2 - 1,
							bottomRightRadius * 2, bottomRightRadius * 2, 270, 90);
				}

				// Vẽ các cạnh thẳng nối liền (trên, dưới, trái, phải) sao cho không có khoảng
				// trống
				g2d.fillRect(x + topLeftRadius, y, width - topLeftRadius - topRightRadius, 1); // Viền trên
				g2d.fillRect(x + topLeftRadius, y + height - 1, width - topLeftRadius - topRightRadius, 1); // Viền dưới
				g2d.fillRect(x, y + topLeftRadius, 1, height - topLeftRadius - bottomLeftRadius); // Viền trái
				g2d.fillRect(x + width - 1, y + topRightRadius, 1, height - topRightRadius - bottomRightRadius); // Viền
																													// phải

				g2d.dispose();
			}

			@Override
			public Insets getBorderInsets(Component c) {
				return new Insets(5, 10, 5, 10);
			}

			@Override
			public boolean isBorderOpaque() {
				return false;
			}
		}
	}

	// Hàm cập nhật lại hàng tiêu đề của bảng (cập nhật tên và kích thước của cột)
	public static void updateTableData(JTable table, String[] nameColumns, int[] widthColumns, Object[][] datas) {
		// Lấy ra model (cấu trúc) đã xây dựng cho Table
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		// Xoá các cột hiện tại
		table.setAutoCreateColumnsFromModel(false);
		while (table.getColumnModel().getColumnCount() > 0) {
			table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
		}

		// Thêm cột mới
		for (int i = 0; i < nameColumns.length; i++) {
			TableColumn column = new TableColumn(i); // Tạo cột với chỉ số
			column.setHeaderValue(nameColumns[i]); // Gán tiêu đề
			column.setPreferredWidth(widthColumns[i]); // Gán chiều rộng
			table.getColumnModel().addColumn(column); // Thêm vào JTable
		}

		// Cập nhật model để với số cột mới
		model.setDataVector(datas, nameColumns);

		// Căn giữa các ô dữ liệu
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < nameColumns.length; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}

		// Thay đổi màu ô "Tổng cộng"
		table.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				String status = String.valueOf(value).trim();
				JLabel statusLabel = new JLabel(status, JLabel.CENTER);

				if (status.equals("TỔNG")) {
					statusLabel.setBackground(Color.decode("#DEDEDE"));
					statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
				} else {
					statusLabel.setBackground(Color.WHITE);
				}

				statusLabel.setOpaque(true);
				return statusLabel;
			}
		});
	}

	// Hàm cập nhật lại dữ liệu của bảng (cập nhật các hàng)
	public static void updateRowsInTableData(JTable table, Object[][] datas) {
		// Lấy ra model (cấu trúc) đã xây dựng cho Table
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		// Xóa tất cả các hàng
		model.setRowCount(0);

		// Thêm từng hàng vào model
		for (Object[] row : datas) {
			model.addRow(row);
		}
	}

	// Hàm tạo cấu trúc Table chứa dữ liệu
	public static JTable createTableData(String[] columns, int[] widthColumns, Object[][] datas, String type) {
		// Tạo DefaultTableModel
		DefaultTableModel model = new DefaultTableModel();

		// Thêm tiêu đề cột
		for (int i = 0; i < columns.length; i++) {
			model.addColumn(columns[i]);
		}

		// Thêm dữ liệu vào model
		for (int i = 0; i < datas.length; i++) {
			model.addRow(datas[i]);
		}

		// Tạo JTable với model
		JTable table = new JTable(model);

		// Tuỳ chỉnh lại ô dữ liệu nào có thể thay đổi (mặc định là không ô nào được
		// phép thay đổi)
		DefaultTableModel customModel = new DefaultTableModel(datas, columns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(customModel); // Gán model mới sau khi tạo bảng

		// Định dạng lại tiêu đề các cột
		// - Kiểu, kích thước font
		// table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
		// if (type.equals("dashboard manager")) {
		// table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
		// }
		// - Chiều cao của dòng tiêu đề
		table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getPreferredSize().width, 50));
		if (type.equals("dashboard manager")) {
			table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getPreferredSize().width, 45));
		}
		if (type.equals("add or update unit table")) {
			table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getPreferredSize().width, 40));
		}
		// - Chiều rộng giữa các cột ở dòng tiêu đề
		for (int i = 0; i < widthColumns.length; i++) {
			if (widthColumns[i] != -1) {
				table.getColumnModel().getColumn(i).setPreferredWidth(widthColumns[i]);
			}
		}
		// - Làm dịu các ô tiêu đề
		table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel headerLabel = new JLabel(value.toString(), SwingConstants.CENTER);
				headerLabel.setOpaque(true);
				headerLabel.setBackground(Color.decode("#000000")); // Màu nền tiêu đề
				headerLabel.setForeground(Color.WHITE); // Màu chữ
				headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
				if (type.equals("dashboard manager")) {
					headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
				}
				if (type.equals("add or update unit table")) {
					headerLabel.setFont(new Font("Arial", Font.BOLD, 15));
				}
				headerLabel.setBorder(BorderFactory.createEmptyBorder()); // Xóa viền cột

				return headerLabel;
			}
		});

		// Định dạng lại các dòng dữ liệu (không phải dòng tiêu đề)
		// - Kiểu, kích thước font
		table.setFont(new Font("Arial", Font.PLAIN, 16));
		if (type.equals("dashboard manager")) {
			table.setFont(new Font("Arial", Font.PLAIN, 14));
		}
		if (type.equals("add or update unit table")) {
			table.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		// - Đổi màu các dòng dữ liệu và Căn giữa
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				if (cell instanceof JLabel) {
					JLabel label = (JLabel) cell;
					label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding: top, left, bottom, right
				}

				if (!isSelected) { // Chỉ đổi màu khi không được chọn
					if (type.equals("add or update unit table")) {
						if (row % 2 == 0) {
							cell.setBackground(Color.decode("#fafafa"));
						} else {
							cell.setBackground(Color.decode("#efefef"));
						}
					} else {
						if (row % 2 == 0) {
							cell.setBackground(Color.decode("#fffbf8")); // Màu cho dòng chẵn
						} else {
							cell.setBackground(Color.decode("#f2f9ff")); // Màu cho dòng lẻ
						}
					}
				}

				return cell;
			}
		};
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < columns.length; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		// - Chiều cao của các dòng dữ liệu
		table.setRowHeight(50);
		if (type.equals("dashboard manager")) {
			table.setRowHeight(45);
		}
		if (type.equals("add or update unit table")) {
			table.setRowHeight(40);
		}
		// - Định dạng lại một dòng dữ liệu khi được chọn
		table.setSelectionBackground(Color.decode("#42A5F5"));
		table.setSelectionForeground(Color.WHITE);

		// - Nếu là Thống kê thì định dạng ô dữ liệu "Tổng cộng"
		if (type.equals("dashboard manager")) {
			// + Vẽ viền
			table.setShowGrid(true);
			table.setGridColor(Color.BLACK);
			table.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

			// + Thay đổi màu ô "Tổng cộng"
			table.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					String status = String.valueOf(value).trim();
					JLabel statusLabel = new JLabel(status, JLabel.CENTER);

					if (status.equals("Tổng cộng")) {
						statusLabel.setBackground(Color.decode("#777777"));
						statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
					} else {
						statusLabel.setBackground(Color.WHITE);
					}

					statusLabel.setOpaque(true);
					return statusLabel;
				}
			});

			// + Định dạng lại một dòng dữ liệu khi được chọn
			table.setSelectionForeground(Color.WHITE);
			table.setSelectionBackground(Color.WHITE);
		}

		// -
		if (!type.equals("dashboard manager") && !type.equals("work schedule manager")
				&& !type.equals("add or update unit table")) {
			// Biến tạm chứa vị trí chứa cột "Trạng thái"
			int columnsValue = -1;

			// Duyệt qua các cột
			for (int i = 0; i < widthColumns.length; i++) {
				String currentColumnName = table.getColumnModel().getColumn(i).getHeaderValue().toString();
				if (currentColumnName.equals("Trạng thái")) {
					columnsValue = i;
					break;
				}
			}

			// Thay đổi màu các ô của cột "Trạng thái"
			table.getColumnModel().getColumn(columnsValue).setCellRenderer(new TableCellRenderer() {
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					// - Lấy ra đối tượng JLabel
					String status = String.valueOf(value).trim();
					JLabel statusLabel = new JLabel(status, JLabel.CENTER);

					// - Cập nhật lại font chữ
					statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));

					// - Cập nhật lại màu nền ô (mặc định JLabel có màu nền là xám)
					if (row % 2 == 0) {
						statusLabel.setBackground(Color.decode("#fffbf8")); // Màu cho dòng chẵn
					} else {
						statusLabel.setBackground(Color.decode("#f2f9ff")); // Màu cho dòng lẻ
					}

					// - Cập nhật lại màu chữ theo ý nghĩa của chuỗi
					if (status.equals("Đã nhập hàng") || status.equals("Hoạt động")) {
						statusLabel.setForeground(Color.decode("#33CC00"));
					} else if (status.equals("Chưa thanh toán")) {
						statusLabel.setForeground(Color.decode("#FFCC33"));
					} else if (status.equals("Đã huỷ đơn") || status.equals("Đã huỷ phiếu")
							|| status.equals("Tạm dừng")) {
						statusLabel.setForeground(Color.decode("#EE0000"));
					} else if (status.equals("Đang chờ xác nhận")) {
						statusLabel.setForeground(Color.decode("#AAAAAA"));
					}

					// - Cập nhật lại ô khi được chọn
					if (isSelected) {
						statusLabel.setBackground(Color.decode("#42A5F5"));
						statusLabel.setForeground(Color.WHITE);
					}

					statusLabel.setOpaque(true);
					return statusLabel;
				}
			});
		}

		return table;
	}

	// Hàm tạo cấu trúc ScrollPane chứa Table
	public static JScrollPane createScrollPane(boolean hasVerticalScrollBar, boolean hasHorizontalScrollBar,
			JTable table) {
		// JScrollPane để chứa JTable
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		if (hasVerticalScrollBar) {
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		} else {
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		}
		if (hasHorizontalScrollBar) {
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			// Tắt tự động điều chỉnh kích thước các cột
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		} else {
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

			// Bật tự động điều chỉnh kích thước các cột (mặc định)
		}

		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Nếu nhấn vào JScrollPane mà không phải trên JTable
				if (table.rowAtPoint(e.getPoint()) == -1 && table.columnAtPoint(e.getPoint()) == -1) {
					table.clearSelection();
					// Chuyển focus khỏi JTable
					scrollPane.requestFocusInWindow();
				}
			}
		});

		return scrollPane;
	}
}
