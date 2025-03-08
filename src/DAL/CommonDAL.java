package DAL;

public class CommonDAL {
	// Hàm chuyển mảng các kết join thành chuỗi kết join
	public static String getJoinValues(String[] join) {
		String result = "";
		for (int i = 0; i < join.length; i++) {
			result += "\n" + join[i];
		}

		return result;
	}
}
