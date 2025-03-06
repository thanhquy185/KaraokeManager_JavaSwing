package BLL;

import java.util.regex.Pattern;

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
				} else if(day01 == day02) {
					return 0;
				}
			}
		}

		// Ngày đầu lớn hơn ngày sau
		return 1;
	}
}
