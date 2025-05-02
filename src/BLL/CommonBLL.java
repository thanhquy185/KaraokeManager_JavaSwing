package BLL;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import PL.CommonPL;

public class CommonBLL {
	// Hàm kiểm tra chuỗi hợp lệ kiểu 1 (cho phép: chữ không dấu, chữ có dấu và số)
	public static boolean isValidStringType01(String s) {
		if (s == null || s == "") {
			return false;
		}

		String pattern = " /-ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzÀÁÂÃẢẠÈÉÊẼẺẸÌÍĨỈỊÒÓÔÕỎỌÙÚŨỦỤỲÝỸỶỴĂẰẮẲẴẶÂẦẤẨẪẬÊỀẾỂỄỆÔỒỐỔỖỘƠỜỚỞỠỢƯỪỨỬỮỰĐàáâãảạèéêẽẻẹìíĩỉịòóôõỏọùúũủụỳýỹỷỵăằắẳẵặâầấẩẫậêềếểễệôồốổỗộơờớởỡợưừứửữựđ0123456789";
		for (int i = 0; i < s.length(); i++) {
			if (pattern.indexOf(s.charAt(i)) == -1) {
				return false;
			}
		}

		return true;
	}

	// Hàm kiểm tra chuỗi hợp lệ kiểu 2 (cho phép: chữ không dấu, số và đặc biệt)
	public static boolean isValidStringType02(String s) {
		if (s == null || s == "") {
			return false;
		}

		String pattern = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz@#$%^&*()_+={}[]|:;\"'<>,.?/0123456789";
		for (int i = 0; i < s.length(); i++) {
			if (pattern.indexOf(s.charAt(i)) == -1) {
				return false;
			}
		}

		return true;
	}

	// Hàm kiểm tra chuỗi hợp lệ kiểu 3 (cho phép: chữ không dấu, số)
	public static boolean isValidStringType03(String s) {
		if (s == null || s == "") {
			return false;
		}

		String pattern = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		for (int i = 0; i < s.length(); i++) {
			if (pattern.indexOf(s.charAt(i)) == -1) {
				return false;
			}
		}

		return true;
	}

	// Hàm kiểm tra chuỗi hợp lệ kiểu 4 (cho phép: số)
	public static boolean isValidStringType04(String s) {
		if (s == null || s == "") {
			return false;
		}

		String pattern = "0123456789";
		for (int i = 0; i < s.length(); i++) {
			if (pattern.indexOf(s.charAt(i)) == -1) {
				return false;
			}
		}

		return true;
	}

	// Hàm kiểm tra chuỗi hợp lệ kiểu 5 (địa chỉ hợp lệ)
	public static boolean isValidStringType05(String s) {
		if (s == null || s == "") {
			return false;
		}

		String pattern = " /,ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzÀÁÂÃẢẠÈÉÊẼẺẸÌÍĨỈỊÒÓÔÕỎỌÙÚŨỦỤỲÝỸỶỴĂẰẮẲẴẶÂẦẤẨẪẬÊỀẾỂỄỆÔỒỐỔỖỘƠỜỚỞỠỢƯỪỨỬỮỰĐàáâãảạèéêẽẻẹìíĩỉịòóôõỏọùúũủụỳýỹỷỵăằắẳẵặâầấẩẫậêềếểễệôồốổỗộơờớởỡợưừứửữựđ0123456789";
		for (int i = 0; i < s.length(); i++) {
			if (pattern.indexOf(s.charAt(i)) == -1) {
				return false;
			}
		}

		return true;
	}

	// Hàm kiểm tra chuỗi hợp lệ kiểu 6 (email hợp lệ)
	public static boolean isValidStringType06(String s) {
		if (s == null || s == "") {
			return false;
		}

		String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		return Pattern.matches(EMAIL_REGEX, s);
	}

	// Hàm kiểm tra giữa 2 ngày
	public static int compareBetweenTwoDate(String date01, String date02) {
		// Ngày 01
		int day01 = Integer.parseInt(date01.substring(8));
		int month01 = Integer.parseInt(date01.substring(5, 7));
		int year01 = Integer.parseInt(date01.substring(0, 4));

		// Ngày 02
		int day02 = Integer.parseInt(date02.substring(8));
		int month02 = Integer.parseInt(date02.substring(5, 7));
		int year02 = Integer.parseInt(date02.substring(0, 4));

		// Kiểm tra các trường hợp
		if (year01 < year02) {
			return -1;
		} else if (year01 == year02) {
			if (month01 < month02) {
				return -1;
			} else if (month01 == month02) {
				if (day01 < day02) {
					return -1;
				} else if (day01 == day02) {
					return 0;
				}
			}
		}

		// Ngày đầu lớn hơn ngày sau
		return 1;
	}

	// Hàm lưu ảnh 1 một món ăn
	public static String updateFoodImage(File fileImage, String foodId) {
		// Tạo thư mục nếu chưa có
		File dir = new File(CommonPL.getMiddlePathToShowFoodImage());
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// Tạo tên mới cho ảnh
		String newFileName = System.currentTimeMillis() + "-" + foodId
				+ fileImage.getName().substring(fileImage.getName().lastIndexOf("."));
		File savedFile = new File(dir, newFileName);

		try {
			// Copy file ảnh
			Files.copy(fileImage.toPath(), savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			return newFileName;
		} catch (IOException ex) {
			ex.printStackTrace();
			CommonPL.createErrorDialog("Thông báo lỗi", "Không thể lưu ảnh");
		}

		return null;
	}

	// Hàm tính số giờ giữa 2 thời gian
	public static Long calHoursBetweenTwoTimes(String startStr, String endStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		LocalDateTime start = LocalDateTime.parse(startStr, formatter);
		LocalDateTime end = LocalDateTime.parse(endStr, formatter);

		if (end.isBefore(start)) {
			System.out.println("Thời gian kết thúc phải sau thời gian bắt đầu.");
			return null;
		}

		Duration duration = Duration.between(start, end);
		double minutes = duration.toMinutes();
		double hours = minutes / 60.0;

		return (long) Math.ceil(hours);
	}
}
