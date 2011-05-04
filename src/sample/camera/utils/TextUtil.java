package sample.camera.utils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �����֌W�̃��[�e�B���e�B
 * 
 * @version 1.00
 */
public class TextUtil {

	/**
	 * �S�p�J�^�J�i�̕����R�[�h�J�n�R�[�h
	 */
	private static final int UNICODE_ZEN_KATAKANA_START = 0x30A0;

	/**
	 * �S�p�J�^�J�i�̕����R�[�h�I���R�[�h
	 */
	private static final int UNICODE_ZEN_KATAKANA_END = 0x30FA;

	/**
	 * �S�p�������̕����R�[�h�J�n�R�[�h
	 */
	private static final int UNICODE_ZEN_HIRAGANA_START = 0x3040;

	/**
	 * �S�p�������̕����R�[�h�I���R�[�h
	 */
	private static final int UNICODE_ZEN_HIRAGANA_END = 0x309A;

	/**
	 * �S�p�J�^�J�i�ƕ������̃I�t�Z�b�g
	 */
	private static final int UNICODE_KANA_HIRA_OFFSET = 0x60;

	/**
	 * �f�t�H���g�R���X�g���N�^�͉B��
	 */
	protected TextUtil() {
	}

	/**
	 * �L���������̕�����ɕϊ�����<br>
	 * ������̐擪�͏�����<br>
	 * �Z�p���[�^�́u_�i�A���_�[�X�R�A�j�v
	 * 
	 * @param val
	 * @return �ϊ���̕�����
	 */
	public static String camelize(String val) {
		if (val == null) {
			return "";
		}

		val = val.toLowerCase();
		String[] array = val.split("_");
		if (array.length == 1) {
			return capitalize(val);
		} else if (array.length == 0) {
			return val;
		}

		StringBuffer buf = new StringBuffer();
		buf.append(array[0]);
		for (int ii = 1; ii < array.length; ii++) {
			buf.append(capitalize(array[ii]));
		}

		return buf.toString();
	}

	/**
	 * ������̐擪��啶���ɂ���
	 * 
	 * @param val
	 *            �F�ϊ����s��������
	 * @return�@�ϊ���̕�����
	 */
	public static String capitalize(final String val) {
		if (isBlank(val)) {
			return "";
		}

		char[] chars = val.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);

		return new String(chars);
	}

	/**
	 * �w��̕�����null�A�܂��̓u�����N�����肷��<br>
	 * trim()���s���ău�����N���肷��
	 * 
	 * @param val
	 * @return boolean :�@true=null�A�܂��̓u�����N�ł���@false=�u�����N�łȂ�
	 */
	public static boolean isBlank(String val) {
		if (val == null) {
			return true;
		}

		if (val == "\n") {
			return false;
		}

		if ("".equals(val.trim()) || "".equals(val.replace("�@", " ").trim())) {
			return true;
		}

		return false;
	}

	/**
	 * �[������
	 * 
	 * @param val
	 *            �F�Ώە�����
	 * @param size
	 *            �F�[�����ߌ�̕�����̌���
	 * @return �[�����߂���������
	 */
	public static String zeroUme(int val, int size) {
		if (size <= 0) {
			return "";
		}

		if (val < 0) {
			return String.valueOf(val);
		}

		return String.format("%0" + size + "d", val);
	}

	/**
	 * NVL�ϊ� �E�w���obj��null�A�܂��͋�̏ꍇ�Aval��Ԃ�<br>
	 * �E����ȊO��target��Ԃ�
	 * 
	 * @param target
	 *            �F�`�F�b�N�Ώۂ̃I�u�W�F�N�g
	 * @param val
	 *            �Ftarget��null�A��̏ꍇ�ɖ߂�l�Ƃ���l
	 * @return String
	 */
	public static String nvl(String target, String val) {
		if (target == null) {
			return val;
		}
		if ("".equals(target)) {
			return val;
		}

		return target;
	}

	/**
	 * ��ʂŕ������������������镶���̃R���o�[�g���s��<br>
	 * �ȉ��̕����̕ϊ����s��<br>
	 * �E�g�_�b�V��<br>
	 * ��������UnsupportedEncodingException�����������ꍇ�́ARuntimeException�𔭐�����
	 * 
	 * @param val
	 *            : SJIS�n�̕����R�[�h�iShift_JIS�AMS932�Ȃǁj�̕�����
	 * @return �ϊ���̕�����
	 */
	public static String sjis2ms932(String val) {
		if (val == null || "".equals(val)) {
			return "";
		}

		String ms932Val = "";
		for (int pos = 0; pos < val.length(); pos++) {
			String posVal = val.substring(pos, pos + 1);
			try {
				ms932Val = new String(posVal.getBytes("Shift_JIS"), "MS932");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}

			if (ms932Val.indexOf("�`") >= 0) {
				int convPos = ms932Val.indexOf("�`");
				val = val.replace(val.charAt(pos), ms932Val.charAt(convPos));
			}
		}
		return val;
	}

	/**
	 * �w��̒l��null�̏ꍇ�A�󕶎���Ԃ�
	 * 
	 * @param val
	 * @return �w��̒l��null�̏ꍇ�A�󕶎���Ԃ��B����ȊO�͓��͂̕���������̂܂ܕԂ�
	 */
	public static String n2b(String val) {
		if (val == null) {
			return "";
		}
		return val;
	}

	/**
	 * TSV��List�ɕϊ�����i�ȈՔŁj<br>
	 * �E�P�s���̃J���}��؂�̕������List�Ɋi�[����
	 * 
	 * @param tsvVal
	 *            �@�FTSV�i�^�u��؂�j�`���̕�����
	 * @return ��������i�[����List<String>
	 */
	public static List<String> tsv2List(String tsvVal) {
		if (tsvVal == null || tsvVal.length() == 0) {
			return new ArrayList<String>(0);
		}
		return Arrays.asList(tsvVal.split("\t"));
	}

	/**
	 * CSV��List�ɕϊ�����i�ȈՔŁj<br>
	 * �E�P�s���̃J���}��؂�̕������List�Ɋi�[����
	 * 
	 * @param csvStr
	 *            �@�FCSV�`���̕�����
	 * @return ��������i�[����List
	 */
	public static List<String> csv2List(String csvStr) {
		List<String> list = new ArrayList<String>();

		if (csvStr == null || csvStr.length() == 0) {
			return new ArrayList<String>(0);
		}

		String[] strs = csvStr.split(",");
		for (int ii = 0; ii < strs.length; ii++) {
			String val = strs[ii];
			if ("".equals(val)) {
				continue;
			}

			list.add(val);
		}
		return list;
	}

	/**
	 * List��CSV�ɕϊ�����
	 * 
	 * @param list
	 *            �@�F�����񂪊i�[���ꂽList
	 * @return CSV�`���̕�����
	 */
	public static String list2csv(List<String> list) {
		StringBuffer sbRe = new StringBuffer();

		if (list == null || list.size() == 0) {
			return "";
		}

		for (int ii = 0; ii < list.size(); ii++) {
			sbRe.append(list.get(ii));
			sbRe.append(",");
		}

		if (sbRe.length() > 0) {
			sbRe.setLength(sbRe.length() - 1);
		}
		return sbRe.toString();
	}

	private static final int CHECK_JOYO_KANJI_ERROR_CD = -99;

	/**
	 * �O���̗L�����`�F�b�N����B<br>
	 * �E�O��������ꍇ�A�ŏ��̕����ʒu��߂��܂��B<br>
	 * �E�O�����Ȃ��ꍇ�́A<b>-1</b>���߂�܂��B<br>
	 * �E�`�F�b�N�s�\�i�ϊ��G���[�j�̏ꍇ�́A<b>-99</b>��߂��܂��B
	 * 
	 * @param str
	 *            : �`�F�b�N���s��������
	 * @return �O��������ꍇ�͂��̕����ʒu�A�����ꍇ�� -1
	 */
	public static int checkJoyoKanji(String str) {
		if (str == null || str.length() == 0) {
			return -1;
		}
		if (str.indexOf("\uFFFD") >= 0) {
			return str.indexOf("\uFFFD");
		}
		String chk = null;
		try {
			chk = new String(str.getBytes("MS932"), "Shift_JIS");
		} catch (Exception e) {
			return CHECK_JOYO_KANJI_ERROR_CD;
		}
		return chk.indexOf("\uFFFD");
	}

	/**
	 * ����l�����݂��邩�̃`�F�b�N���s��<br>
	 * ","�ŘA�����ꂽ�����񓯎m�ŁA����̒l�����݂��邩�`�F�b�N����B
	 * 
	 * @param st1
	 *            �F","�ŘA��������r���镶����
	 * @param st2
	 *            �F","�ŘA��������r���镶����
	 * @return ����l������ꍇtrue�A����ȊO��false��Ԃ�
	 */
	public static boolean checkDouble(String st1, String st2) {
		if (st1 == null || st2 == null || st1.equals("") || st2.equals("")) {
			return false;
		}
		if (st1.equals(st2)) {
			return true;
		}

		String[] dts = st1.split(",");
		List<String> chkl = new ArrayList<String>();
		for (int i = 0; i < dts.length; i++) {
			chkl.add(dts[i]);
		}
		dts = st2.split(",");
		for (int i = 0; i < dts.length; i++) {
			if (chkl.contains(dts[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �S�p�J�^�J�i�𕽉����ɕϊ�����<br>
	 * �S�p�J�^�J�i�ȊO�͂��̂܂ܐݒ肷��
	 * 
	 * @param org
	 *            : �ϊ��Ώۂ̕�����
	 * @return �ϊ���̕�����
	 */
	public static String cnvKata2Hira(String org) {
		if (org == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < org.length(); i++) {
			char chr = org.charAt(i);
			if (chr >= UNICODE_ZEN_KATAKANA_START && chr <= UNICODE_ZEN_KATAKANA_END) {
				chr -= UNICODE_KANA_HIRA_OFFSET;
			}
			sb.append(chr);
		}
		return sb.toString();
	}

	/**
	 * ��������S�p�J�^�J�i�ɕϊ�����B<br>
	 * �������ȊO�͂��̂܂ܐݒ肷��B
	 * 
	 * @param org
	 *            : �ϊ��Ώۂ̕�����
	 * @return �ϊ���̕�����
	 */
	public static String cnvHira2Kata(String org) {
		if (org == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < org.length(); i++) {
			char chr = org.charAt(i);
			if (chr >= UNICODE_ZEN_HIRAGANA_START && chr <= UNICODE_ZEN_HIRAGANA_END) {
				chr += UNICODE_KANA_HIRA_OFFSET;
			}
			sb.append(chr);
		}
		return sb.toString();
	}

	private static final String ZENKAKU = "�@�{�|���^���b�I�H�h�����������f�M�i�j�m�n�C�D�G�F�Q�����O";
	private static final String HANKAKU = " +-*/=|!?\"#@$%&'`()[],.;:_<>^";

	/**
	 * �S�p�̉p����(�`-�y��-���O-�X�L��)�𔼊p����(A-Za-z0-9�L��)�ɕϊ�����<br>
	 * �ϊ��ł��Ȃ������͂��̂܂ܐݒ肳��܂��B
	 * 
	 * @param org
	 *            : �ϊ��O�̕�����
	 * @return �ϊ���̕�����
	 */
	public static String cnv2Han(String org) {
		if (org == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < org.length(); i++) {
			char chr = org.charAt(i);
			int ix = ZENKAKU.indexOf(chr);
			if (chr >= '�O' && chr <= '�X') {
				chr += '0' - '�O';
			} else if (chr >= '�`' && chr <= '�y') {
				chr += 'A' - '�`';
			} else if (chr >= '��' && chr <= '��') {
				chr += 'a' - '��';
			} else if (ix > -1) {
				chr = HANKAKU.charAt(ix);
			}
			sb.append(chr);
		}
		return sb.toString();
	}

	/**
	 * ���p����(A-Za-z0-9�L��)��S�p�̉p����(�`-�y��-���O-�X�L��)�ɕϊ�����<br>
	 * �ϊ��ł��Ȃ������͂��̂܂ܐݒ肳��܂��B
	 * 
	 * @param org
	 *            : �ϊ��O�̕�����
	 * @return �ϊ���̕�����
	 */
	public static String cnv2Zen(String org) {
		if (org == null) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < org.length(); i++) {
			char chr = org.charAt(i);
			int ix = HANKAKU.indexOf(chr);

			if (chr >= '0' && chr <= '9') {
				chr += '�O' - '0';
			} else if (chr >= 'A' && chr <= 'Z') {
				chr += '�`' - 'A';
			} else if (chr >= 'a' && chr <= 'z') {
				chr += '��' - 'a';
			} else if (ix > -1) {
				chr = ZENKAKU.charAt(ix);
			}
			sb.append(chr);
		}
		return sb.toString();
	}

	/**
	 * �S�p�̉p����(�`-�y��-���O-�X�L��)�𔼊p�p������(a-z0-9�L��)�ɕϊ�����B<br>
	 * A-Z,a-z,0-9�ȊO�̕����͖�������܂��B
	 * 
	 * @param org
	 *            : �ϊ��O�̕�����
	 * @return �ϊ���̕�����
	 */
	public static String cnv2Asc(String org) {
		if (org == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < org.length(); i++) {
			int nv = Character.getNumericValue(org.charAt(i));
			if (nv > -1) {
				sb.append(Character.forDigit(nv, Character.MAX_RADIX));
			} else {
				sb.append(org.charAt(i));
			}
		}
		return sb.toString();
	}

	private static final String HANKANA = "����������������������¯�������������������֬�������ܦݰ��";
	private static final String ZENKANA = "�A�C�E�G�I�@�B�D�F�H�J�L�N�P�R�T�V�X�Z�\�^�`�c�b�e�g�i�j�k�l�m�n�q�t�w�z�}�~�����������������������������������[�A�B";
	private static final String DAKUTEN = "��";

	/**
	 * ���p�J�i��S�p�J�i�ɕϊ�����B<br>
	 * ���_(��)�͈ꕶ���ɕϊ����܂��B
	 * 
	 * @param org
	 *            : �ϊ��Ώۂ̕�����
	 * @return �ϊ���̕�����
	 */
	public static String kanaHan2Zen(String org) {
		if (org == null) {
			return "";
		}

		int idx;
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < org.length(); i++) {
			char cr = org.charAt(i);
			idx = HANKANA.indexOf(cr);
			if (idx >= 0) {
				cr = ZENKANA.charAt(idx);
			}
			idx = DAKUTEN.indexOf(cr);

			if (idx >= 0 && sb.length() - 1 != -1) {
				if (org.charAt(i) == org.charAt(i - 1)) {
					sb.append(cr);
					continue;
				}
				cr = sb.charAt(sb.length() - 1);
				cr++;
				cr += idx;
				sb.setLength(sb.length() - 1);
			}

			sb.append(cr);
		}
		return sb.toString();
	}

	/**
	 * �S�p�J�^�J�i�𔼊p�J�^�J�i�ɕϊ�����B
	 * 
	 * @param targetVal
	 *            : �ϊ��Ώۂ̕�����
	 * @return �ϊ���̕�����
	 */
	public static String kanaZen2Han(String targetVal) {
		if (targetVal == null) {
			return "";
		}
		int idx;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < targetVal.length(); i++) {
			char cr = targetVal.charAt(i);
			char cr2 = cr;

			idx = ZENKANA.indexOf(cr);
			if (idx >= 0) {
				cr = HANKANA.charAt(idx);
			} else {
				cr2--;
				idx = ZENKANA.indexOf(cr2);
				if (idx >= 0) {
					sb.append(HANKANA.charAt(idx));
					cr = '�';
				} else {
					cr2--;
					idx = ZENKANA.indexOf(cr2);
					if (idx >= 0) {
						sb.append(HANKANA.charAt(idx));
						cr = '�';
					}
				}
			}
			sb.append(cr);
		}
		return sb.toString();
	}

	/**
	 * �S�p�p����������S�p�啶���ɕϊ�����
	 * 
	 * @param targetVal
	 *            : �ϊ��Ώۂ̕�����
	 * @return �ϊ���̕�����
	 */
	public static String toUpper4zen(String targetVal) {
		if (targetVal == null) {
			return "";
		}

		return targetVal.toUpperCase();
	}

	/**
	 * �����A�������ƐÉ��̃}�b�`���OMap
	 */
	private static Map<String, String> dakuonMap = new HashMap<String, String>();
	static {
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("��", "��");
		dakuonMap.put("�K", "�J");
		dakuonMap.put("�M", "�L");
		dakuonMap.put("�O", "�N");
		dakuonMap.put("�Q", "�P");
		dakuonMap.put("�S", "�R");
		dakuonMap.put("�U", "�T");
		dakuonMap.put("�W", "�V");
		dakuonMap.put("�Y", "�X");
		dakuonMap.put("�[", "�Z");
		dakuonMap.put("�]", "�\");
		dakuonMap.put("�_", "�^");
		dakuonMap.put("�a", "�`");
		dakuonMap.put("�d", "�c");
		dakuonMap.put("�f", "�e");
		dakuonMap.put("�h", "�g");
		dakuonMap.put("�o", "�n");
		dakuonMap.put("�r", "�q");
		dakuonMap.put("�u", "�t");
		dakuonMap.put("�x", "�w");
		dakuonMap.put("�{", "�z");
		dakuonMap.put("�p", "�n");
		dakuonMap.put("�s", "�q");
		dakuonMap.put("�v", "�t");
		dakuonMap.put("�y", "�w");
		dakuonMap.put("�|", "�z");
	}

	/**
	 * �����A�������𕶎��񂩂珜��
	 * 
	 * @param targetVal
	 *            : �ϊ��Ώۂ̕�����
	 * @return �ϊ���̕�����
	 */
	public static String dakuon2seion(String targetVal) {
		if (targetVal == null) {
			return "";
		}

		for (int ii = 0; ii < targetVal.length(); ii++) {
			String strKey = String.valueOf(targetVal.charAt(ii));
			if (dakuonMap.containsKey(strKey)) {
				targetVal = targetVal.replaceAll(strKey, dakuonMap.get(strKey));
			}
		}

		return targetVal;
	}

	/**
	 * �X���Ƒ啶���̃}�b�`���OMap
	 */
	private static Map<String, String> yonMap = new HashMap<String, String>();
	static {
		yonMap.put("��", "��");
		yonMap.put("��", "��");
		yonMap.put("��", "��");
		yonMap.put("��", "��");
		yonMap.put("��", "��");
		yonMap.put("�@", "�A");
		yonMap.put("�B", "�C");
		yonMap.put("�D", "�E");
		yonMap.put("�F", "�G");
		yonMap.put("�H", "�I");
		yonMap.put("��", "�J");
		yonMap.put("��", "�P");
		yonMap.put("��", "��");
		yonMap.put("�b", "�c");
		yonMap.put("��", "��");
		yonMap.put("��", "��");
		yonMap.put("��", "��");
		yonMap.put("��", "��");
		yonMap.put("��", "��");
		yonMap.put("��", "��");
		yonMap.put("��", "��");
		yonMap.put("��", "��");
	}

	/**
	 * �X����啶���ɕϊ�����
	 * 
	 * @param targetVal
	 *            : �ϊ��Ώۂ̕�����
	 * @return �ϊ���̕�����
	 */
	public static String toUpperYoon(String targetVal) {
		if (targetVal == null) {
			return "";
		}

		for (int ii = 0; ii < targetVal.length(); ii++) {
			String strKey = String.valueOf(targetVal.charAt(ii));
			if (yonMap.containsKey(strKey)) {
				targetVal = targetVal.replaceAll(strKey, yonMap.get(strKey));
			}
		}

		return targetVal;
	}

	/**
	 * �S�p�J�i�A�����ȊO�̕�������菜��
	 * 
	 * @param targetVal
	 *            �@�F�@�ϊ��Ώۂ̕�����
	 * @return �ϊ���̕�����
	 */
	public static String toZenKanaAndSuji(String targetVal) {
		if (targetVal == null || "".equals(targetVal)) {
			return "";
		}

		return targetVal.replaceAll("[^�@-���O-�X]", "");
	}

	/**
	 * �S�p�L������菜��<br>
	 * ���j�r���͎�菜���Ȃ�
	 * 
	 * @param targetVal
	 *            �ϊ��Ώە�����
	 * @return�@�ϊ���̕�����
	 */
	public static String delZenKigo(String targetVal) {
		if (targetVal == null || "".equals(targetVal)) {
			return "";
		}

		String val = targetVal.replaceAll("[�I�h���������f�i�j���`�b�e�o�{���p�����H�Q�|�O�����u�G�F�v�A�B�E�[�C�D �@�g�^]", "");

		return val;
	}

	/**
	 * �w��̕�����̐擪����pos�������̕������Ԃ�<br>
	 * pos�ɕ��̐���^�����ꍇ�́A������̏I�[����̕����𔲂��o��
	 * 
	 * @param val
	 *            �@�Ώۂ̕�����
	 * @param pos
	 *            �@�����o��������
	 * @return�@�����o����������
	 */
	public static String left(String val, int pos) {
		if (val == null || val.equals("")) {
			return "";
		}

		if (pos < 0) {
			return TextUtil.right(val, pos * -1);
		}

		if (val.length() < pos) {
			return val;
		}

		return val.substring(0, pos);
	}

	/**
	 * �w��̕�����̏I�[����pos�������̕������Ԃ�
	 * 
	 * @param val
	 *            �@�Ώۂ̕�����
	 * @param pos
	 *            �@�����o��������
	 * @return�@�����o����������
	 */
	public static String right(String val, int pos) {
		if (val == null || val.equals("")) {
			return "";
		}

		if (pos < 0) {
			return "";
		}

		if (val.length() < pos) {
			return val;
		}

		return val.substring(val.length() - pos, val.length());
	}

	/**
	 * ������̑O��Ɏw��̕������t������<br>
	 * val��null�܂��̓u�����N�̏ꍇ�́A�t�����Ȃ��B
	 * 
	 * @param val
	 *            �@�Ώە�����
	 * @param prefix
	 *            �@�O���ɕt�����镶����
	 * @param sufix
	 *            �@����ɕt�����镶����
	 * @return�@�ҏW��̕�����
	 */
	public static String addStr(String val, String prefix, String sufix) {
		if (val == null || val.trim().length() == 0) {
			return "";
		}
		if (prefix == null) {
			prefix = "";
		}
		if (sufix == null) {
			sufix = "";
		}
		return prefix + val + sufix;
	}

	/**
	 * ���l���J���}�t���̕�����ŃG�X
	 * 
	 * @param num
	 *            ���`���鐔�l
	 * @return�@���`����������
	 */
	public static String numberFormat(long num) {
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(num);
	}

	/**
	 * �X�y�[�X��؂�̃L�[���[�h��List������<br>
	 * limit�i�ő�L�[���[�h���j�܂ł�List�Ɋi�[����<br>
	 * ���p�����͑S�p�ɕϊ�����
	 * 
	 * @param keyword
	 *            �L�[���[�h
	 * @param limit
	 *            �ő�L�[���[�h��
	 * @param isRepDel
	 *            true : �d���L�[���[�h������<br>
	 *            false : �d���L�[���[�h���������Ȃ�
	 * @return �L�[���[�h��List
	 */
	public static List<String> createKeywordList(String keyword, int limit, boolean isRepDel) {

		// �S�p�X�y�[�X�𔼊p�X�y�[�X�ɕϊ�
		String changedVal = TextUtil.cnv2Zen(keyword).replaceAll("�@", " ").trim();

		// �󔒂ŋ�؂��ă��X�g�ɕϊ�
		String[] words = changedVal.split(" ");

		List<String> keywordList = new ArrayList<String>(0);
		for (String val : words) {
			if (isBlank(val.trim())) {
				continue;
			}

			if (!isRepDel) {
				keywordList.add(val);

			} else {

				// ����L�[���[�h����
				if (!keywordList.contains(val)) {
					keywordList.add(val);
				}
			}

			// limit�܂ł��g�p
			if (keywordList.size() >= limit) {
				break;
			}
		}

		return keywordList;
	}

	/**
	 * ���󔒏���<br>
	 * ������̍����̑S�p�A���p�X�y�[�X����������<br>
	 * 
	 * @param str
	 *            �C���Ώە�����
	 * @return String �C���㕶����
	 */
	public static final String leftTrim(String str) {
		String temp = "";
		int start = 0;

		if (str == null || str.length() == 0) {
			return "";
		}

		for (int i = 0; i < str.length(); i++) {
			temp = str.substring(i, i + 1);
			if (!temp.equals("�@") && !temp.equals(" ")) {
				start = i;
				break;
			}
		}

		if (temp.equals("�@") || temp.equals(" ")) {
			return "";
		}

		return str.substring(start, str.length());

	}

	/**
	 * �E�󔒏���<br>
	 * ������̉E���̑S�p�A���p�X�y�[�X����������<br>
	 * 
	 * @param str
	 *            �C���Ώە�����
	 * @return String �C���㕶����
	 */
	public static final String rightTrim(String str) {

		String temp = "";
		int end = 0;
		if (str == null || str.length() == 0) {
			return "";
		}

		for (int i = str.length() - 1; i >= 0; i--) {
			temp = str.substring(i, i + 1);
			if (!temp.equals("�@") && !temp.equals(" ")) {
				end = i;
				break;
			}
		}

		temp = str.substring(0, end + 1);
		if (temp.equals("�@") || temp.equals(" ")) {
			temp = "";
		}
		return temp;
	}

	/**
	 * �O��̋󔒏���<br>
	 * ������̑O��̔��p�A�S�p�X�y�[�X���폜����.<br>
	 * 
	 * @param str
	 *            �C���Ώە�����
	 * @return String �C���㕶����
	 */
	public static String allTrim(String str) {
		if (str == null) {
			str = "";
			return str;
		}
		String tmp = "";
		tmp = rightTrim(str);
		tmp = leftTrim(tmp);

		// �S�p�󔒂P�����ɂȂ�����󕶎���ݒ�
		if (str.equals("�@")) {
			str = "";
		}
		return tmp;
	}

	/**
	 * �t�@�C����t�H���_�̃p�X�����M���O����ۂɎg�p����B<br>
	 * �u\�v���u\\�v�ɕϊ�����<br>
	 * 
	 * @param str
	 *            �C���Ώە�����
	 * @return String �C���㕶����
	 */
	public static String replaceEscapeStr(String str) {
		if (str == null) {
			str = "";
			return str;
		}
		String tmp = str.replace("\\", "\\\\");
		return tmp;
	}
}
