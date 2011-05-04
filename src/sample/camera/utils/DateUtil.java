package sample.camera.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * �������[�e�B���e�B
 * 
 * @version 1.00
 */
public class DateUtil {

	/**
	 * yyyy/MM/dd���u/�v�ŕ��������ۂ̔z��T�C�Y
	 */
	private static final int SPLITED_YMD_LENGTH = 3;

	/**
	 * �N���Q���Ŏw�肳�ꂽ�ꍇ��1900�N��Ƃ��邩2000�N��Ƃ��邩���肷��~���l
	 */
	private static final int AIMAI_YEAR_HANTEI_YEAR = 80;

	/**
	 * yyyy/MM/dd���e�v�f�ŕ��������ۂ̗v�f��
	 */
	private static final int YMD_ITEM_COUNT = 3;

	/**
	 * �R���X�g���N�^�͉B��
	 */
	protected DateUtil() {
	}

	/**
	 * �V�X�e�����t���uYYYY/MM/DD�v�`���ŕԂ�
	 * 
	 * @return �V�X�e�����t�iYYYY/MM/DD�j
	 */
	public static String getNowDate() {
		return getFormatDate("yyyy/MM/dd");
	}

	/**
	 * �V�X�e�����t���uYYYY�v�`���ŕԂ�
	 * 
	 * @return �V�X�e�����t�iYYYY�j
	 */
	public static String getNowYear() {
		return getFormatDate("yyyy");
	}

	/**
	 * �V�X�e�����t���uMM�v�`���ŕԂ�
	 * 
	 * @return �V�X�e�����t�iMM�j
	 */
	public static String getNowMonth() {
		return getFormatDate("MM");
	}

	/**
	 * �}n���̔N�v�Z
	 * 
	 * @param targetDate
	 *            "yyyy/MM/dd"�̔N�����w�肷��
	 * @param addYear
	 *            �擾�������N�̑��ΔN���w�肷��
	 * @return �}n���̔N�v�Z���ʁiyyyy�j
	 */
	public static String addYear(String targetDate, int addYear) {
		return addYear2format(targetDate, addYear, "yyyy");
	}

	/**
	 * �}n���̔N�v�Z
	 * 
	 * @param targetDate
	 *            "yyyy/MM/dd"�̔N�����w�肷��
	 * @param addYear
	 *            �擾�������N�̑��ΔN���w�肷��
	 * @param format
	 *            ���t�̏����iyyyy/MM/dd�Ȃǁj
	 * @return �}n���̔N�v�Z���ʂ�format�̏����ŕԂ�
	 */
	public static String addYear2format(String targetDate, int addYear, String format) {
		if (targetDate == null || targetDate.trim().length() == 0 || targetDate.replace("�@", " ").trim().length() == 0) {
			return "";
		}

		String[] targetDates = targetDate.split("/");

		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile("^\\D+");
		matcher = pattern.matcher(targetDates[0]);
		boolean isMatch = matcher.matches();

		if (isMatch || targetDates.length != YMD_ITEM_COUNT) {
			return "";
		}

		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(targetDates[0]), Integer.parseInt(targetDates[1]) - 1, Integer.parseInt(targetDates[2]));
		cal.add(Calendar.YEAR, addYear);

		return (new SimpleDateFormat(format)).format(cal.getTime());
	}

	/**
	 * �}n���̔N���v�Z
	 * 
	 * @param monthVal
	 *            "yyyy/MM"�̔N�����w�肷��
	 * @param addMonth
	 *            �擾�������N���̑��Ό����w�肷��
	 * @return �}n���̔N���v�Z�iyyyy/MM�`���j<br>
	 *         �uyyyy/MM�v�`���Ƀ}�b�`���Ȃ��l���w�肵���ꍇ�́A�󕶎���Ԃ�
	 */
	public static String addMonth(String monthVal, int addMonth) {
		if (monthVal == null || monthVal.trim().length() == 0 || monthVal.replace("�@", " ").trim().length() == 0) {
			return "";
		}

		String[] targetDates = monthVal.split("/");

		Matcher matcher = Pattern.compile("^\\D+").matcher(targetDates[0]);
		boolean isMatch = matcher.matches();

		if (isMatch || targetDates.length != 2) {
			return "";
		}

		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(targetDates[0]), Integer.parseInt(targetDates[1]) - 1, 1);
		cal.add(Calendar.MONTH, addMonth);
		String date = new SimpleDateFormat("yyyy/MM").format(cal.getTime());
		return date;
	}

	/**
	 * �}n���̓��t�v�Z
	 * 
	 * @param date
	 *            ���ƂȂ�"yyyy/MM/dd"���w�肷��
	 * @param addDate
	 *            �擾���������t�̑��Γ����w�肷��
	 * @return �}n���̔N���v�Z�iyyyy/MM/dd�`���j
	 */
	public static String addDate(String date, int addDate) {
		if (date == null || date.trim().length() == 0 || date.replace("�@", " ").trim().length() == 0) {
			return "";
		}

		String[] targetDates = date.split("/");

		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile("^\\D+");
		matcher = pattern.matcher(targetDates[0]);
		boolean isMatch = matcher.matches();

		if (isMatch || targetDates.length != YMD_ITEM_COUNT) {
			return "";
		}

		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(targetDates[0]), Integer.parseInt(targetDates[1]) - 1, Integer.parseInt(targetDates[2]));
		cal.add(Calendar.DATE, addDate);
		return new SimpleDateFormat("yyyy/MM/dd").format(cal.getTime());
	}

	/**
	 * �V�X�e�����t���udd�v�`���ŕԂ�
	 * 
	 * @return �V�X�e�����t�idd�j
	 */
	public static String getNowDay() {
		return getFormatDate("dd");
	}

	/**
	 * �V�X�e�����Ԃ��uHH:mm:ss�v�`���ŕԂ�
	 * 
	 * @return �V�X�e�����ԁiHH:mm:ss�j
	 */
	public static String getNowTime() {
		return getFormatDate("HH:mm:ss");
	}

	/**
	 * �w��̃t�H�[�}�b�g�ŃV�X�e�����t��Ԃ�
	 * 
	 * @param format
	 * @return �w��̃t�H�[�}�b�g�Ő��`���ꂽ���t������
	 */
	public static String getFormatDate(String format) {
		if (format == null) {
			return "";
		}

		Date dt = new Date();
		SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
		df.applyPattern(format);
		String date = df.format(dt);
		if (date != null) {
			return date;
		}
		return "";
	}

	/**
	 * �V�X�e�����t������N�x���擾���A������ŕԂ�
	 * 
	 * @return �V�X�e���N�x�iYYYY�j
	 */
	public static String getNendo() {
		int nowYear = Integer.parseInt(getNowYear());
		int nowMonth = Integer.parseInt(getNowMonth());
		if (Calendar.JANUARY + 1 <= nowMonth && nowMonth <= Calendar.MARCH + 1) {
			nowYear--;
		}
		return Integer.toString(nowYear);
	}

	/**
	 * �w��̓��t�A���������񂩂�A���Y�N�x��Ԃ�<br>
	 * date�͐擪���uyyyy/MM�v�Ŏn�܂��Ă���΁A����ȍ~�̃t�H�[�}�b�g�͕s��
	 * 
	 * @param date
	 *            ���t������
	 * @return �N�x������
	 */
	public static String getNendo(String date) {
		String[] tm = date.split("/");
		if (tm.length < 2) {
			throw new IllegalArgumentException("�w��̓��t���uyyyy/MM�v�Ŏn�܂��Ă��܂���");
		}

		int year = Integer.parseInt(tm[0]);
		int month = Integer.parseInt(tm[1]);
		if (Calendar.JANUARY + 1 <= month && month <= Calendar.MARCH + 1) {
			year--;
		}
		return Integer.toString(year);
	}

	/**
	 * �w��̓��t���w��̃t�H�[�}�b�g�ɕϊ����ĕԂ�
	 * 
	 * @param date
	 *            ���t������
	 * @param format
	 *            �ϊ�������iyyyy/MM/dd�Ȃǁj
	 * @return �w��̓��t�`���ɕϊ�����������
	 */
	public static String convertDate(String date, String format) {
		if (date == null || date.trim().length() == 0 || date.replace("�@", " ").trim().length() == 0) {
			return "";
		}

		String[] dates = date.split("/");
		if (dates.length < SPLITED_YMD_LENGTH) {
			return "";
		}

		String year = dates[0];
		String month = dates[1];
		String day = dates[2];

		// 2���̔N��4���N�ɕϊ�
		if (year.length() == 2) {
			if (Integer.parseInt(year) < AIMAI_YEAR_HANTEI_YEAR) {
				year = "20" + year;
			} else {
				year = "19" + year;
			}
		}

		Calendar cal = Calendar.getInstance();
		int iy = Integer.parseInt(year);
		int im = Integer.parseInt(month) - 1;
		int id = Integer.parseInt(day);
		cal.set(iy, im, id);
		java.util.Date convDt = cal.getTime();

		// �f�t�H���g�̃��P�[���̓��t�t�H�[�}�b�^�����
		SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();

		// ���t�t�H�[�}�b�g��ݒ�
		df.applyPattern(format);

		// dt �� �w��t�H�[�}�b�g�֕ϊ�
		String convDtS = df.format(convDt);
		if (convDtS == null) {
			return "";
		}

		return convDtS;
	}

	/**
	 * �uyy/M/d�v�Ȃǂ̓��t��������uyyyy/MM/dd�v�`���ɕϊ�����
	 * 
	 * @param date
	 * @return yyyy/MM/dd�`���̕�����
	 */
	public static String convertDate(String date) {
		StringTokenizer st = new StringTokenizer(date, "/");
		List<String> dt = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			dt.add(st.nextToken());
		}
		if (dt.size() < SPLITED_YMD_LENGTH) {
			return "";
		}

		String year = (String) dt.get(0);
		String month = (String) dt.get(1);
		String day = (String) dt.get(2);

		// 2���̔N��4���N�ɕϊ�
		if (year.length() == 2) {
			if (Integer.parseInt(year) < AIMAI_YEAR_HANTEI_YEAR) {
				year = "20" + year;
			} else {
				year = "19" + year;
			}
		}

		Calendar cal = Calendar.getInstance();
		int iy = Integer.parseInt(year);
		int im = Integer.parseInt(month) - 1;
		int id = Integer.parseInt(day);
		cal.set(iy, im, id);
		java.util.Date convDt = cal.getTime();

		// �f�t�H���g�̃��P�[���̓��t�t�H�[�}�b�^�����
		SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();

		// ���t�t�H�[�}�b�g��ݒ�
		df.applyPattern("yyyy/MM/dd");

		// dt �� �w��t�H�[�}�b�g�֕ϊ�
		String convDtS = df.format(convDt);
		if (convDtS == null) {
			return "";
		}

		return convDtS;
	}

	/**
	 * �uYYYY/MM/DD�v�`���̓��t��<code>Calendar</code>�ŕԂ�
	 * 
	 * @param date
	 *            a <code>String</code>�F�uYYYY/MM/DD�v�`���̕�����
	 * @return �V�X�e�����t
	 */
	public static Calendar getCalendar(String date) {
		StringTokenizer st = new StringTokenizer(date, "/");
		List<String> dt = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			dt.add(st.nextToken());
		}

		String year = "";
		String month = "";
		String day = "";
		try {
			year = (String) dt.get(0);
			month = (String) dt.get(1);
			day = (String) dt.get(2);

		} catch (IndexOutOfBoundsException ex) {
			throw new IllegalArgumentException();
		}

		Calendar cal = Calendar.getInstance();
		int iy = Integer.parseInt(year);
		int im = Integer.parseInt(month) - 1;
		int id = Integer.parseInt(day);
		cal.set(iy, im, id);

		return cal;
	}

	/**
	 * �uYYYY/MM/DD�v�`���Ŏw�肳�ꂽ���t�̖�����Ԃ�
	 * 
	 * @param date
	 * @return �uYYYY/MM/DD�v�`���̖���
	 */
	public static String getDayOfMonth(String date) {
		int dayOfMonth = getDayByDayOfMonth(date);
		String day = "0" + String.valueOf(dayOfMonth);
		return convertDate(date, "yyyy/MM") + day.substring(day.length() - 2, 2);
	}

	/**
	 * �uYYYY/MM/DD�v�`���Ŏw�肳�ꂽ���t�̖�����Ԃ�
	 * 
	 * @param date
	 * @return ����
	 */
	public static int getDayByDayOfMonth(String date) {
		Calendar calendar = getCalendar(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * �uYYYY/MM/DD�v�`���Ŏw�肳�ꂽ���t�̗j����Ԃ�
	 * 
	 * @param date
	 * @return "��", "��", "��", "��", "��", "��", "�y"�̂����ꂩ�𕶎���ŕԂ�
	 */
	public static String getYoubi(String date) {
		if (date == null) {
			return "";
		}

		String[] youbi = { "��", "��", "��", "��", "��", "��", "�y" };
		Calendar calendar = getCalendar(date);
		return youbi[calendar.get(Calendar.DAY_OF_WEEK) - 1];
	}

	/**
	 * �w��̓��t�̗j����Calendar�̗j���ŕԂ�
	 * 
	 * @param date
	 *            �@�uyyyy/MM/dd�v�`���̓��t������
	 * @return �j���iCalendar�̗j���萔�l�j
	 */
	public static int getEngYoubi(String date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * �w��̓��t�iyyyy/MM/dd�j�ɗj����t������
	 * 
	 * @param date
	 *            �@�uyyyy/MM/dd�v�`���̓��t������
	 * @return�@�uyyyy/MM/dd(�j��)�v
	 */
	public static String getYoubiWithKakko(String date) {
		String yobi = DateUtil.getYoubi(date);
		if ("".equals(yobi)) {
			return "";
		}

		return date + "(" + yobi + ")";
	}

	/**
	 * �L���ȓ��t���`�F�b�N����<br>
	 * ���t��null�A�u�����N�̏ꍇ��true��Ԃ�
	 * 
	 * @param checkDate
	 *            �`�F�b�N������t
	 * @param format
	 *            �����iyyyy/MM/dd�Ȃǁj
	 * @return true : �L���ł���<br>
	 *         false : �����ȓ��t�ł���
	 */
	public static boolean isValidDate(String checkDate, String format) {
		if (TextUtil.isBlank(checkDate)) {
			return true;
		}

		if (checkDate.length() != format.length()) {
			return false;
		}

		SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateInstance();
		sdf.applyPattern(format);
		sdf.setLenient(false);
		try {
			sdf.parse(checkDate);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * �L���ȓ��t���`�F�b�N����<br>
	 * ���t��null�A�u�����N�̏ꍇ��true��Ԃ�
	 * 
	 * @param checkDate
	 *            �`�F�b�N������t�iyyyy/MM/dd�j
	 * @return true : �L���ł���<br>
	 *         false : �����ȓ��t�ł���
	 */
	public static boolean isValidDate(String checkDate) {
		return isValidDate(checkDate, "yyyy/MM/dd");
	}

	/**
	 * Timestamp�^�̓��t���w��̃t�H�[�}�b�g��String�^�ɕϊ����Ԃ�
	 * 
	 * @param date
	 * @param format
	 * @return �w��̃t�H�[�}�b�g�Ő��`���ꂽ���t������
	 */
	public static String getFormatDateByTimestamp(Timestamp date, String format) {
		if (format == null || date == null) {
			return "";
		}
		String dateStr = new SimpleDateFormat(format).format(date);

		if (dateStr != null) {
			return dateStr;
		}
		return "";
	}
}
