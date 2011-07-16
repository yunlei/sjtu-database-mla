package Parse;

import ErrorMsg.ErrorMsg;

class Yylex {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

	StringBuffer string = new StringBuffer();

	private java_cup.runtime.Symbol nextToken(int kind, Object value) {
		return new java_cup.runtime.Symbol(kind, yychar, yychar + yylength(),
				value);
	}

	private java_cup.runtime.Symbol nextToken(int kind) {
		return nextToken(kind, yytext());
	}

	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Yylex(java.io.Reader reader) {
		this();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex(java.io.InputStream instream) {
		this();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(
				instream));
	}

	private Yylex() {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int STRING = 1;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = { 0, 79 };

	private void yybegin(int state) {
		yy_lexical_state = state;
	}

	private int yy_advance() throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer, yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer, yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}

	private void yy_move_end() {
		if (yy_buffer_end > yy_buffer_start
				&& '\n' == yy_buffer[yy_buffer_end - 1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start
				&& '\r' == yy_buffer[yy_buffer_end - 1])
			yy_buffer_end--;
	}

	private boolean yy_last_was_cr = false;

	private void yy_mark_start() {
		yychar = yychar + yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}

	private void yy_mark_end() {
		yy_buffer_end = yy_buffer_index;
	}

	private void yy_to_mark() {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start)
				&& ('\r' == yy_buffer[yy_buffer_end - 1]
						|| '\n' == yy_buffer[yy_buffer_end - 1]
						|| 2028/* LS */== yy_buffer[yy_buffer_end - 1] || 2029/* PS */== yy_buffer[yy_buffer_end - 1]);
	}

	private java.lang.String yytext() {
		return (new java.lang.String(yy_buffer, yy_buffer_start, yy_buffer_end
				- yy_buffer_start));
	}

	private int yylength() {
		return yy_buffer_end - yy_buffer_start;
	}

	private char[] yy_double(char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2 * buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}

	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = { "Error: Internal error.\n",
			"Error: Unmatched input.\n" };

	private void yy_error(int code, boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}

	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i = 0; i < size1; i++) {
			for (int j = 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex == -1) ? st : st.substring(0,
						commaIndex);
				st = st.substring(commaIndex + 1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j] = Integer.parseInt(workString);
					continue;
				}
				lengthString = workString.substring(colonIndex + 1);
				sequenceLength = Integer.parseInt(lengthString);
				workString = workString.substring(0, colonIndex);
				sequenceInteger = Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}

	private int yy_acpt[] = {
	/* 0 */YY_NOT_ACCEPT,
	/* 1 */YY_NO_ANCHOR,
	/* 2 */YY_NO_ANCHOR,
	/* 3 */YY_NO_ANCHOR,
	/* 4 */YY_NO_ANCHOR,
	/* 5 */YY_NO_ANCHOR,
	/* 6 */YY_NO_ANCHOR,
	/* 7 */YY_NO_ANCHOR,
	/* 8 */YY_NO_ANCHOR,
	/* 9 */YY_NO_ANCHOR,
	/* 10 */YY_NO_ANCHOR,
	/* 11 */YY_NO_ANCHOR,
	/* 12 */YY_NO_ANCHOR,
	/* 13 */YY_NO_ANCHOR,
	/* 14 */YY_NO_ANCHOR,
	/* 15 */YY_NO_ANCHOR,
	/* 16 */YY_NO_ANCHOR,
	/* 17 */YY_NO_ANCHOR,
	/* 18 */YY_NO_ANCHOR,
	/* 19 */YY_NO_ANCHOR,
	/* 20 */YY_NO_ANCHOR,
	/* 21 */YY_NO_ANCHOR,
	/* 22 */YY_NO_ANCHOR,
	/* 23 */YY_NO_ANCHOR,
	/* 24 */YY_NO_ANCHOR,
	/* 25 */YY_NO_ANCHOR,
	/* 26 */YY_NO_ANCHOR,
	/* 27 */YY_NO_ANCHOR,
	/* 28 */YY_NO_ANCHOR,
	/* 29 */YY_NO_ANCHOR,
	/* 30 */YY_NO_ANCHOR,
	/* 31 */YY_NO_ANCHOR,
	/* 32 */YY_NO_ANCHOR,
	/* 33 */YY_NO_ANCHOR,
	/* 34 */YY_NO_ANCHOR,
	/* 35 */YY_NO_ANCHOR,
	/* 36 */YY_NO_ANCHOR,
	/* 37 */YY_NO_ANCHOR,
	/* 38 */YY_NO_ANCHOR,
	/* 39 */YY_NO_ANCHOR,
	/* 40 */YY_NO_ANCHOR,
	/* 41 */YY_NO_ANCHOR,
	/* 42 */YY_NO_ANCHOR,
	/* 43 */YY_NO_ANCHOR,
	/* 44 */YY_NO_ANCHOR,
	/* 45 */YY_NO_ANCHOR,
	/* 46 */YY_NO_ANCHOR,
	/* 47 */YY_NO_ANCHOR,
	/* 48 */YY_NO_ANCHOR,
	/* 49 */YY_NO_ANCHOR,
	/* 50 */YY_NO_ANCHOR,
	/* 51 */YY_NO_ANCHOR,
	/* 52 */YY_NO_ANCHOR,
	/* 53 */YY_NO_ANCHOR,
	/* 54 */YY_NO_ANCHOR,
	/* 55 */YY_NO_ANCHOR,
	/* 56 */YY_NO_ANCHOR,
	/* 57 */YY_NO_ANCHOR,
	/* 58 */YY_NO_ANCHOR,
	/* 59 */YY_NO_ANCHOR,
	/* 60 */YY_NO_ANCHOR,
	/* 61 */YY_NO_ANCHOR,
	/* 62 */YY_NO_ANCHOR,
	/* 63 */YY_NO_ANCHOR,
	/* 64 */YY_NO_ANCHOR,
	/* 65 */YY_NO_ANCHOR,
	/* 66 */YY_NO_ANCHOR,
	/* 67 */YY_NO_ANCHOR,
	/* 68 */YY_NO_ANCHOR,
	/* 69 */YY_NO_ANCHOR,
	/* 70 */YY_NO_ANCHOR,
	/* 71 */YY_NO_ANCHOR,
	/* 72 */YY_NO_ANCHOR,
	/* 73 */YY_NO_ANCHOR,
	/* 74 */YY_NO_ANCHOR,
	/* 75 */YY_NO_ANCHOR,
	/* 76 */YY_NO_ANCHOR,
	/* 77 */YY_NO_ANCHOR,
	/* 78 */YY_NO_ANCHOR,
	/* 79 */YY_NO_ANCHOR,
	/* 80 */YY_NO_ANCHOR,
	/* 81 */YY_NOT_ACCEPT,
	/* 82 */YY_NO_ANCHOR,
	/* 83 */YY_NO_ANCHOR,
	/* 84 */YY_NOT_ACCEPT,
	/* 85 */YY_NO_ANCHOR,
	/* 86 */YY_NOT_ACCEPT,
	/* 87 */YY_NO_ANCHOR,
	/* 88 */YY_NO_ANCHOR,
	/* 89 */YY_NO_ANCHOR,
	/* 90 */YY_NO_ANCHOR,
	/* 91 */YY_NO_ANCHOR,
	/* 92 */YY_NO_ANCHOR,
	/* 93 */YY_NO_ANCHOR,
	/* 94 */YY_NO_ANCHOR,
	/* 95 */YY_NO_ANCHOR,
	/* 96 */YY_NO_ANCHOR,
	/* 97 */YY_NO_ANCHOR,
	/* 98 */YY_NO_ANCHOR,
	/* 99 */YY_NO_ANCHOR,
	/* 100 */YY_NO_ANCHOR,
	/* 101 */YY_NO_ANCHOR,
	/* 102 */YY_NO_ANCHOR,
	/* 103 */YY_NO_ANCHOR,
	/* 104 */YY_NO_ANCHOR,
	/* 105 */YY_NO_ANCHOR,
	/* 106 */YY_NO_ANCHOR,
	/* 107 */YY_NO_ANCHOR,
	/* 108 */YY_NO_ANCHOR,
	/* 109 */YY_NO_ANCHOR,
	/* 110 */YY_NO_ANCHOR,
	/* 111 */YY_NO_ANCHOR,
	/* 112 */YY_NO_ANCHOR,
	/* 113 */YY_NO_ANCHOR,
	/* 114 */YY_NO_ANCHOR,
	/* 115 */YY_NO_ANCHOR,
	/* 116 */YY_NO_ANCHOR,
	/* 117 */YY_NO_ANCHOR,
	/* 118 */YY_NO_ANCHOR,
	/* 119 */YY_NO_ANCHOR,
	/* 120 */YY_NO_ANCHOR,
	/* 121 */YY_NO_ANCHOR,
	/* 122 */YY_NO_ANCHOR,
	/* 123 */YY_NO_ANCHOR,
	/* 124 */YY_NO_ANCHOR,
	/* 125 */YY_NO_ANCHOR,
	/* 126 */YY_NO_ANCHOR,
	/* 127 */YY_NO_ANCHOR,
	/* 128 */YY_NO_ANCHOR,
	/* 129 */YY_NO_ANCHOR,
	/* 130 */YY_NO_ANCHOR,
	/* 131 */YY_NO_ANCHOR,
	/* 132 */YY_NO_ANCHOR,
	/* 133 */YY_NO_ANCHOR,
	/* 134 */YY_NO_ANCHOR,
	/* 135 */YY_NO_ANCHOR,
	/* 136 */YY_NO_ANCHOR,
	/* 137 */YY_NO_ANCHOR,
	/* 138 */YY_NO_ANCHOR,
	/* 139 */YY_NO_ANCHOR,
	/* 140 */YY_NO_ANCHOR,
	/* 141 */YY_NO_ANCHOR,
	/* 142 */YY_NO_ANCHOR,
	/* 143 */YY_NO_ANCHOR,
	/* 144 */YY_NO_ANCHOR,
	/* 145 */YY_NO_ANCHOR,
	/* 146 */YY_NO_ANCHOR,
	/* 147 */YY_NO_ANCHOR,
	/* 148 */YY_NO_ANCHOR,
	/* 149 */YY_NO_ANCHOR,
	/* 150 */YY_NO_ANCHOR,
	/* 151 */YY_NO_ANCHOR,
	/* 152 */YY_NO_ANCHOR,
	/* 153 */YY_NO_ANCHOR,
	/* 154 */YY_NO_ANCHOR,
	/* 155 */YY_NO_ANCHOR,
	/* 156 */YY_NO_ANCHOR,
	/* 157 */YY_NO_ANCHOR,
	/* 158 */YY_NO_ANCHOR,
	/* 159 */YY_NO_ANCHOR,
	/* 160 */YY_NO_ANCHOR,
	/* 161 */YY_NO_ANCHOR,
	/* 162 */YY_NO_ANCHOR,
	/* 163 */YY_NO_ANCHOR,
	/* 164 */YY_NO_ANCHOR,
	/* 165 */YY_NO_ANCHOR,
	/* 166 */YY_NO_ANCHOR,
	/* 167 */YY_NO_ANCHOR,
	/* 168 */YY_NO_ANCHOR,
	/* 169 */YY_NO_ANCHOR,
	/* 170 */YY_NO_ANCHOR,
	/* 171 */YY_NO_ANCHOR,
	/* 172 */YY_NO_ANCHOR,
	/* 173 */YY_NO_ANCHOR,
	/* 174 */YY_NO_ANCHOR,
	/* 175 */YY_NO_ANCHOR,
	/* 176 */YY_NO_ANCHOR,
	/* 177 */YY_NO_ANCHOR,
	/* 178 */YY_NO_ANCHOR,
	/* 179 */YY_NO_ANCHOR,
	/* 180 */YY_NO_ANCHOR,
	/* 181 */YY_NO_ANCHOR,
	/* 182 */YY_NO_ANCHOR,
	/* 183 */YY_NO_ANCHOR,
	/* 184 */YY_NO_ANCHOR,
	/* 185 */YY_NO_ANCHOR,
	/* 186 */YY_NO_ANCHOR,
	/* 187 */YY_NO_ANCHOR,
	/* 188 */YY_NO_ANCHOR,
	/* 189 */YY_NO_ANCHOR,
	/* 190 */YY_NO_ANCHOR,
	/* 191 */YY_NO_ANCHOR,
	/* 192 */YY_NO_ANCHOR,
	/* 193 */YY_NO_ANCHOR,
	/* 194 */YY_NO_ANCHOR,
	/* 195 */YY_NO_ANCHOR,
	/* 196 */YY_NO_ANCHOR,
	/* 197 */YY_NO_ANCHOR,
	/* 198 */YY_NO_ANCHOR,
	/* 199 */YY_NO_ANCHOR,
	/* 200 */YY_NO_ANCHOR,
	/* 201 */YY_NO_ANCHOR,
	/* 202 */YY_NO_ANCHOR,
	/* 203 */YY_NO_ANCHOR,
	/* 204 */YY_NO_ANCHOR,
	/* 205 */YY_NO_ANCHOR,
	/* 206 */YY_NO_ANCHOR,
	/* 207 */YY_NO_ANCHOR,
	/* 208 */YY_NO_ANCHOR,
	/* 209 */YY_NO_ANCHOR,
	/* 210 */YY_NO_ANCHOR,
	/* 211 */YY_NO_ANCHOR,
	/* 212 */YY_NO_ANCHOR,
	/* 213 */YY_NO_ANCHOR,
	/* 214 */YY_NO_ANCHOR,
	/* 215 */YY_NO_ANCHOR,
	/* 216 */YY_NO_ANCHOR,
	/* 217 */YY_NO_ANCHOR,
	/* 218 */YY_NO_ANCHOR,
	/* 219 */YY_NO_ANCHOR,
	/* 220 */YY_NO_ANCHOR,
	/* 221 */YY_NO_ANCHOR,
	/* 222 */YY_NO_ANCHOR,
	/* 223 */YY_NO_ANCHOR,
	/* 224 */YY_NO_ANCHOR,
	/* 225 */YY_NO_ANCHOR,
	/* 226 */YY_NO_ANCHOR,
	/* 227 */YY_NO_ANCHOR,
	/* 228 */YY_NO_ANCHOR,
	/* 229 */YY_NO_ANCHOR };
	private int yy_cmap[] = unpackFromString(
			1,
			130,
			"53:8,36:2,38,53:2,37,53:18,36,53:4,48,53,40,51,52,46,44,49,45,35,47,33:10,5"
					+ "3,50,42,41,43,53:2,1,11,10,6,4,16,23,22,15,32,18,2,17,7,12,13,32,5,9,3,19,2"
					+ "0,21,14,8,32,53,39,53:2,34,53,29,32:3,27,28,32:5,30,32:5,25,31,24,26,32:5,5"
					+ "3:5,0:2")[0];

	private int yy_rmap[] = unpackFromString(
			1,
			230,
			"0,1,2,3,1,4,1,5,6,1:9,7,8,9,8,10,1:5,8:9,11,8:4,12,8:36,13,1,14,15,16,17,18"
					+ ",12,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42"
					+ ",43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67"
					+ ",68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92"
					+ ",93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,1"
					+ "13,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,"
					+ "132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,8,148,149,1"
					+ "50,151,152,153,154,155,156,157,158,159,160")[0];

	private int yy_nxt[][] = unpackFromString(
			161,
			54,
			"1,2,82,200,211,216,219,135,216,136,221,85,87,222,216,88,223,137,138,139,224"
					+ ",225,226,227,228,216:3,229,216:4,3,-1,4,5,81,5,84,-1,6,7,8,9,10,11,12,13,14"
					+ ",15,16,17,-1:56,216,89,216:3,90,91,216,18,216:9,140,92,216:14,-1:52,3,-1,86"
					+ ",-1:54,5,-1,5,-1:56,25,-1,26,-1:51,27,-1:13,216:9,32,216:24,-1:20,216:34,-1"
					+ ":20,216:5,167,216:28,-1:20,216:2,37,216:2,169,216:2,170,171,216:24,-1:20,21"
					+ "6:11,49,216:22,-1:52,42,-1:20,1,83:39,80,83:13,-1:38,23,-1:16,216:14,141,21"
					+ "6:19,-1:20,83:39,-1,83:13,-1:40,24,-1:14,216:7,19,216:3,153,216:22,-1:20,21"
					+ "6:4,20,216,21,216:27,-1:20,216:6,22,216:27,-1:20,216,28,161,216:31,-1:20,21"
					+ "6:5,29,216:28,-1:20,216:5,30,216,31,216:26,-1:20,216:22,33,216:11,-1:20,216"
					+ ":2,34,216:31,-1:20,216,217,35,216:31,-1:20,216:16,36,216:17,-1:20,216:13,38"
					+ ",216:20,-1:20,216:6,39,216:27,-1:20,216:7,40,216:26,-1:20,216:3,41,216:30,-"
					+ "1:20,216:11,43,216:22,-1:20,216:3,44,216:30,-1:20,216:9,45,216:24,-1:20,216"
					+ ":12,46,216:21,-1:20,216,47,216:32,-1:20,216:4,48,216:29,-1:20,216:16,50,216"
					+ ":17,-1:20,216:20,51,216:13,-1:20,216:26,52,216:7,-1:20,216:4,53,216:29,-1:2"
					+ "0,216:3,54,216:30,-1:20,216:2,55,216:31,-1:20,216:17,56,216:16,-1:20,216:4,"
					+ "57,216:29,-1:20,216:13,58,216:20,-1:20,216:2,59,216:31,-1:20,216:6,60,216:2"
					+ "7,-1:20,216:3,61,216:30,-1:20,216:12,62,216:21,-1:20,216:26,63,216:7,-1:20,"
					+ "216:3,64,216:30,-1:20,216:8,65,216:25,-1:20,216:3,66,216:30,-1:20,216:2,67,"
					+ "216:31,-1:20,216:3,68,216:30,-1:20,216:2,69,216:31,-1:20,216:3,70,216:30,-1"
					+ ":20,216:8,71,216:25,-1:20,216:22,72,216:11,-1:20,216:2,73,216:31,-1:20,216:"
					+ "6,74,216:27,-1:20,216:7,75,216:26,-1:20,216:3,76,216:30,-1:20,216:2,77,216:"
					+ "31,-1:20,216:2,78,216:31,-1:20,216:11,93,216:6,149,216:15,-1:20,216:3,94,21"
					+ "6:14,95,216:15,-1:20,96,216:13,97,216:19,-1:20,216:3,98,216:30,-1:20,216:6,"
					+ "212,216,99,216:3,155,216:21,-1:20,216:2,100,216:31,-1:20,216:17,101,216:16,"
					+ "-1:20,216:10,162,216:23,-1:20,216:9,163,216:24,-1:20,216:14,202,216:19,-1:2"
					+ "0,216:2,206,216:31,-1:20,216,213,216:6,102,216:6,215,216:18,-1:20,216:11,10"
					+ "3,216:22,-1:20,216:8,164,216:25,-1:20,216,104,216:32,-1:20,216:3,218,216:30"
					+ ",-1:20,216:18,165,216:15,-1:20,105,216:2,166,216:30,-1:20,216:11,205,216:22"
					+ ",-1:20,216:11,106,216:22,-1:20,216:5,220,216:28,-1:20,216,174,216:32,-1:20,"
					+ "216:3,107,216:30,-1:20,216:19,176,216:14,-1:20,216:25,108,216:8,-1:20,216:2"
					+ "9,178,216:4,-1:20,216:3,109,216:30,-1:20,216,110,216:32,-1:20,179,216:33,-1"
					+ ":20,216:2,209,216:31,-1:20,216:6,111,216:27,-1:20,216:9,112,216:24,-1:20,21"
					+ "6:3,113,216:30,-1:20,216:16,185,216:17,-1:20,216:3,114,216:30,-1:20,216:3,1"
					+ "86,216:30,-1:20,216:4,187,216:29,-1:20,115,216:33,-1:20,216:11,116,216:22,-"
					+ "1:20,216:18,189,216:15,-1:20,216:4,117,216:29,-1:20,216:14,190,216:19,-1:20"
					+ ",216:18,118,216:15,-1:20,216:30,119,216:3,-1:20,216:12,120,216:21,-1:20,216"
					+ ":2,121,216:31,-1:20,216:10,210,216:23,-1:20,216:2,122,216:31,-1:20,216:9,12"
					+ "3,216:24,-1:20,216:2,124,216:31,-1:20,194,216:33,-1:20,216:4,125,216:29,-1:"
					+ "20,216:3,195,216:30,-1:20,216:2,126,216:31,-1:20,216:3,127,216:30,-1:20,216"
					+ ":6,128,216:27,-1:20,216,129,216:32,-1:20,216:6,197,216:27,-1:20,130,216:33,"
					+ "-1:20,216:4,131,216:29,-1:20,216:16,198,216:17,-1:20,216:8,132,216:25,-1:20"
					+ ",216:9,133,216:24,-1:20,216:3,199,216:30,-1:20,216:6,134,216:27,-1:20,142,2"
					+ "16:33,-1:20,216:14,168,216:19,-1:20,216:8,180,216:25,-1:20,216:3,175,216:30"
					+ ",-1:20,216:11,172,216:22,-1:20,216,207,216:32,-1:20,181,216:33,-1:20,216:3,"
					+ "193,216:30,-1:20,216:18,191,216:15,-1:20,216:14,192,216:19,-1:20,196,216:33"
					+ ",-1:20,216:8,143,216:4,144,216:20,-1:20,216:14,173,216:19,-1:20,216:3,182,2"
					+ "16:30,-1:20,216:11,177,216:22,-1:20,208,216:33,-1:20,216:3,183,216:30,-1:20"
					+ ",184,216:33,-1:20,145,216:2,146,147,216:9,148,216:19,-1:20,188,216:33,-1:20"
					+ ",216:4,150,216:6,151,216:9,152,216:12,-1:20,216:4,201,216:29,-1:20,216,204,"
					+ "216:2,154,216:29,-1:20,156,216:13,157,216:19,-1:20,216:21,203,216:12,-1:20,"
					+ "158,216:33,-1:20,216:4,214,216:29,-1:20,216:24,159,216:9,-1:20,216:28,160,2"
					+ "16:5,-1:19");

	public java_cup.runtime.Symbol nextToken() throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol)
				yy_lookahead = YY_BOL;
			else
				yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

				{
					if (yy_state == STRING)
						return nextToken(sym.ERROR);
					else
						return nextToken(sym.EOF, null);
				}
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			} else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				} else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:

					case -2:
						break;
					case 2: {
						return nextToken(sym.NAME, yytext());
					}
					case -3:
						break;
					case 3: {
						return nextToken(sym.INTVALUE, new Integer(yytext()));
					}
					case -4:
						break;
					case 4: {
						return nextToken(sym.DOT);
					}
					case -5:
						break;
					case 5: {
					}
					case -6:
						break;
					case 6: {
						return nextToken(sym.EQ);
					}
					case -7:
						break;
					case 7: {
						return nextToken(sym.LT);
					}
					case -8:
						break;
					case 8: {
						return nextToken(sym.GT);
					}
					case -9:
						break;
					case 9: {
						return nextToken(sym.PLUS);
					}
					case -10:
						break;
					case 10: {
						return nextToken(sym.MINUS);
					}
					case -11:
						break;
					case 11: {
						return nextToken(sym.TIMES);
					}
					case -12:
						break;
					case 12: {
						return nextToken(sym.DIVIDE);
					}
					case -13:
						break;
					case 13: {
						return nextToken(sym.MOD);
					}
					case -14:
						break;
					case 14: {
						return nextToken(sym.COMMA);
					}
					case -15:
						break;
					case 15: {
						return nextToken(sym.SEMICOLON);
					}
					case -16:
						break;
					case 16: {
						return nextToken(sym.LPAREN);
					}
					case -17:
						break;
					case 17: {
						return nextToken(sym.RPAREN);
					}
					case -18:
						break;
					case 18: {
						return nextToken(sym.AS);
					}
					case -19:
						break;
					case 19: {
						return nextToken(sym.BY);
					}
					case -20:
						break;
					case 20: {
						return nextToken(sym.OR);
					}
					case -21:
						break;
					case 21: {
						return nextToken(sym.ON);
					}
					case -22:
						break;
					case 22: {
						return nextToken(sym.IN);
					}
					case -23:
						break;
					case 23: {
					}
					case -24:
						break;
					case 24: {
						string.setLength(0);
						yybegin(STRING);
					}
					case -25:
						break;
					case 25: {
						return nextToken(sym.LE);
					}
					case -26:
						break;
					case 26: {
						return nextToken(sym.NEQ);
					}
					case -27:
						break;
					case 27: {
						return nextToken(sym.GE);
					}
					case -28:
						break;
					case 28: {
						return nextToken(sym.ALL);
					}
					case -29:
						break;
					case 29: {
						return nextToken(sym.ADD);
					}
					case -30:
						break;
					case 30: {
						return nextToken(sym.AND);
					}
					case -31:
						break;
					case 31: {
						return nextToken(sym.ANY);
					}
					case -32:
						break;
					case 32: {
						return nextToken(sym.ASC);
					}
					case -33:
						break;
					case 33: {
						return nextToken(sym.AVG);
					}
					case -34:
						break;
					case 34: {
						return nextToken(sym.NOT);
					}
					case -35:
						break;
					case 35: {
						return nextToken(sym.SET);
					}
					case -36:
						break;
					case 36: {
						return nextToken(sym.SUM);
					}
					case -37:
						break;
					case 37: {
						return nextToken(sym.INT);
					}
					case -38:
						break;
					case 38: {
						return nextToken(sym.MAX);
					}
					case -39:
						break;
					case 39: {
						return nextToken(sym.MIN);
					}
					case -40:
						break;
					case 40: {
						return nextToken(sym.KEY);
					}
					case -41:
						break;
					case 41: {
						return nextToken(sym.USE);
					}
					case -42:
						break;
					case 42: {
						return nextToken(sym.FLOATVALUE, new Float(yytext()));
					}
					case -43:
						break;
					case 43: {
						return nextToken(sym.AUTO);
					}
					case -44:
						break;
					case 44: {
						return nextToken(sym.LIKE);
					}
					case -45:
						break;
					case 45: {
						return nextToken(sym.DESC);
					}
					case -46:
						break;
					case 46: {
						return nextToken(sym.DROP);
					}
					case -47:
						break;
					case 47: {
						return nextToken(sym.NULL);
					}
					case -48:
						break;
					case 48: {
						return nextToken(sym.CHAR);
					}
					case -49:
						break;
					case 49: {
						return nextToken(sym.INTO);
					}
					case -50:
						break;
					case 50: {
						return nextToken(sym.FROM);
					}
					case -51:
						break;
					case 51: {
						return nextToken(sym.VIEW);
					}
					case -52:
						break;
					case 52: {
						return nextToken(sym.TRUE);
					}
					case -53:
						break;
					case 53: {
						return nextToken(sym.ALTER);
					}
					case -54:
						break;
					case 54: {
						return nextToken(sym.TABLE);
					}
					case -55:
						break;
					case 55: {
						return nextToken(sym.COUNT);
					}
					case -56:
						break;
					case 56: {
						return nextToken(sym.CHECK);
					}
					case -57:
						break;
					case 57: {
						return nextToken(sym.ORDER);
					}
					case -58:
						break;
					case 58: {
						return nextToken(sym.INDEX);
					}
					case -59:
						break;
					case 59: {
						return nextToken(sym.FLOAT);
					}
					case -60:
						break;
					case 60: {
						return nextToken(sym.UNION);
					}
					case -61:
						break;
					case 61: {
						return nextToken(sym.WHERE);
					}
					case -62:
						break;
					case 62: {
						return nextToken(sym.GROUP);
					}
					case -63:
						break;
					case 63: {
						return nextToken(sym.FALSE);
					}
					case -64:
						break;
					case 64: {
						return nextToken(sym.ESCAPE);
					}
					case -65:
						break;
					case 65: {
						return nextToken(sym.EXISTS);
					}
					case -66:
						break;
					case 66: {
						return nextToken(sym.DELETE);
					}
					case -67:
						break;
					case 67: {
						return nextToken(sym.SELECT);
					}
					case -68:
						break;
					case 68: {
						return nextToken(sym.CREATE);
					}
					case -69:
						break;
					case 69: {
						return nextToken(sym.INSERT);
					}
					case -70:
						break;
					case 70: {
						return nextToken(sym.UPDATE);
					}
					case -71:
						break;
					case 71: {
						return nextToken(sym.VALUES);
					}
					case -72:
						break;
					case 72: {
						return nextToken(sym.HAVING);
					}
					case -73:
						break;
					case 73: {
						return nextToken(sym.DEFAULT);
					}
					case -74:
						break;
					case 74: {
						return nextToken(sym.BOOLEAN);
					}
					case -75:
						break;
					case 75: {
						return nextToken(sym.PRIMARY);
					}
					case -76:
						break;
					case 76: {
						return nextToken(sym.DATABASE);
					}
					case -77:
						break;
					case 77: {
						return nextToken(sym.DISTINCT);
					}
					case -78:
						break;
					case 78: {
						return nextToken(sym.INCREMENT);
					}
					case -79:
						break;
					case 79: {
						string.append(yytext());
					}
					case -80:
						break;
					case 80: {
						string.append("'");
					}
					case -81:
						break;
					case 82: {
						return nextToken(sym.NAME, yytext());
					}
					case -82:
						break;
					case 83: {
						string.append(yytext());
					}
					case -83:
						break;
					case 85: {
						return nextToken(sym.NAME, yytext());
					}
					case -84:
						break;
					case 87: {
						return nextToken(sym.NAME, yytext());
					}
					case -85:
						break;
					case 88: {
						return nextToken(sym.NAME, yytext());
					}
					case -86:
						break;
					case 89: {
						return nextToken(sym.NAME, yytext());
					}
					case -87:
						break;
					case 90: {
						return nextToken(sym.NAME, yytext());
					}
					case -88:
						break;
					case 91: {
						return nextToken(sym.NAME, yytext());
					}
					case -89:
						break;
					case 92: {
						return nextToken(sym.NAME, yytext());
					}
					case -90:
						break;
					case 93: {
						return nextToken(sym.NAME, yytext());
					}
					case -91:
						break;
					case 94: {
						return nextToken(sym.NAME, yytext());
					}
					case -92:
						break;
					case 95: {
						return nextToken(sym.NAME, yytext());
					}
					case -93:
						break;
					case 96: {
						return nextToken(sym.NAME, yytext());
					}
					case -94:
						break;
					case 97: {
						return nextToken(sym.NAME, yytext());
					}
					case -95:
						break;
					case 98: {
						return nextToken(sym.NAME, yytext());
					}
					case -96:
						break;
					case 99: {
						return nextToken(sym.NAME, yytext());
					}
					case -97:
						break;
					case 100: {
						return nextToken(sym.NAME, yytext());
					}
					case -98:
						break;
					case 101: {
						return nextToken(sym.NAME, yytext());
					}
					case -99:
						break;
					case 102: {
						return nextToken(sym.NAME, yytext());
					}
					case -100:
						break;
					case 103: {
						return nextToken(sym.NAME, yytext());
					}
					case -101:
						break;
					case 104: {
						return nextToken(sym.NAME, yytext());
					}
					case -102:
						break;
					case 105: {
						return nextToken(sym.NAME, yytext());
					}
					case -103:
						break;
					case 106: {
						return nextToken(sym.NAME, yytext());
					}
					case -104:
						break;
					case 107: {
						return nextToken(sym.NAME, yytext());
					}
					case -105:
						break;
					case 108: {
						return nextToken(sym.NAME, yytext());
					}
					case -106:
						break;
					case 109: {
						return nextToken(sym.NAME, yytext());
					}
					case -107:
						break;
					case 110: {
						return nextToken(sym.NAME, yytext());
					}
					case -108:
						break;
					case 111: {
						return nextToken(sym.NAME, yytext());
					}
					case -109:
						break;
					case 112: {
						return nextToken(sym.NAME, yytext());
					}
					case -110:
						break;
					case 113: {
						return nextToken(sym.NAME, yytext());
					}
					case -111:
						break;
					case 114: {
						return nextToken(sym.NAME, yytext());
					}
					case -112:
						break;
					case 115: {
						return nextToken(sym.NAME, yytext());
					}
					case -113:
						break;
					case 116: {
						return nextToken(sym.NAME, yytext());
					}
					case -114:
						break;
					case 117: {
						return nextToken(sym.NAME, yytext());
					}
					case -115:
						break;
					case 118: {
						return nextToken(sym.NAME, yytext());
					}
					case -116:
						break;
					case 119: {
						return nextToken(sym.NAME, yytext());
					}
					case -117:
						break;
					case 120: {
						return nextToken(sym.NAME, yytext());
					}
					case -118:
						break;
					case 121: {
						return nextToken(sym.NAME, yytext());
					}
					case -119:
						break;
					case 122: {
						return nextToken(sym.NAME, yytext());
					}
					case -120:
						break;
					case 123: {
						return nextToken(sym.NAME, yytext());
					}
					case -121:
						break;
					case 124: {
						return nextToken(sym.NAME, yytext());
					}
					case -122:
						break;
					case 125: {
						return nextToken(sym.NAME, yytext());
					}
					case -123:
						break;
					case 126: {
						return nextToken(sym.NAME, yytext());
					}
					case -124:
						break;
					case 127: {
						return nextToken(sym.NAME, yytext());
					}
					case -125:
						break;
					case 128: {
						return nextToken(sym.NAME, yytext());
					}
					case -126:
						break;
					case 129: {
						return nextToken(sym.NAME, yytext());
					}
					case -127:
						break;
					case 130: {
						return nextToken(sym.NAME, yytext());
					}
					case -128:
						break;
					case 131: {
						return nextToken(sym.NAME, yytext());
					}
					case -129:
						break;
					case 132: {
						return nextToken(sym.NAME, yytext());
					}
					case -130:
						break;
					case 133: {
						return nextToken(sym.NAME, yytext());
					}
					case -131:
						break;
					case 134: {
						return nextToken(sym.NAME, yytext());
					}
					case -132:
						break;
					case 135: {
						return nextToken(sym.NAME, yytext());
					}
					case -133:
						break;
					case 136: {
						return nextToken(sym.NAME, yytext());
					}
					case -134:
						break;
					case 137: {
						return nextToken(sym.NAME, yytext());
					}
					case -135:
						break;
					case 138: {
						return nextToken(sym.NAME, yytext());
					}
					case -136:
						break;
					case 139: {
						return nextToken(sym.NAME, yytext());
					}
					case -137:
						break;
					case 140: {
						return nextToken(sym.NAME, yytext());
					}
					case -138:
						break;
					case 141: {
						return nextToken(sym.NAME, yytext());
					}
					case -139:
						break;
					case 142: {
						return nextToken(sym.NAME, yytext());
					}
					case -140:
						break;
					case 143: {
						return nextToken(sym.NAME, yytext());
					}
					case -141:
						break;
					case 144: {
						return nextToken(sym.NAME, yytext());
					}
					case -142:
						break;
					case 145: {
						return nextToken(sym.NAME, yytext());
					}
					case -143:
						break;
					case 146: {
						return nextToken(sym.NAME, yytext());
					}
					case -144:
						break;
					case 147: {
						return nextToken(sym.NAME, yytext());
					}
					case -145:
						break;
					case 148: {
						return nextToken(sym.NAME, yytext());
					}
					case -146:
						break;
					case 149: {
						return nextToken(sym.NAME, yytext());
					}
					case -147:
						break;
					case 150: {
						return nextToken(sym.NAME, yytext());
					}
					case -148:
						break;
					case 151: {
						return nextToken(sym.NAME, yytext());
					}
					case -149:
						break;
					case 152: {
						return nextToken(sym.NAME, yytext());
					}
					case -150:
						break;
					case 153: {
						return nextToken(sym.NAME, yytext());
					}
					case -151:
						break;
					case 154: {
						return nextToken(sym.NAME, yytext());
					}
					case -152:
						break;
					case 155: {
						return nextToken(sym.NAME, yytext());
					}
					case -153:
						break;
					case 156: {
						return nextToken(sym.NAME, yytext());
					}
					case -154:
						break;
					case 157: {
						return nextToken(sym.NAME, yytext());
					}
					case -155:
						break;
					case 158: {
						return nextToken(sym.NAME, yytext());
					}
					case -156:
						break;
					case 159: {
						return nextToken(sym.NAME, yytext());
					}
					case -157:
						break;
					case 160: {
						return nextToken(sym.NAME, yytext());
					}
					case -158:
						break;
					case 161: {
						return nextToken(sym.NAME, yytext());
					}
					case -159:
						break;
					case 162: {
						return nextToken(sym.NAME, yytext());
					}
					case -160:
						break;
					case 163: {
						return nextToken(sym.NAME, yytext());
					}
					case -161:
						break;
					case 164: {
						return nextToken(sym.NAME, yytext());
					}
					case -162:
						break;
					case 165: {
						return nextToken(sym.NAME, yytext());
					}
					case -163:
						break;
					case 166: {
						return nextToken(sym.NAME, yytext());
					}
					case -164:
						break;
					case 167: {
						return nextToken(sym.NAME, yytext());
					}
					case -165:
						break;
					case 168: {
						return nextToken(sym.NAME, yytext());
					}
					case -166:
						break;
					case 169: {
						return nextToken(sym.NAME, yytext());
					}
					case -167:
						break;
					case 170: {
						return nextToken(sym.NAME, yytext());
					}
					case -168:
						break;
					case 171: {
						return nextToken(sym.NAME, yytext());
					}
					case -169:
						break;
					case 172: {
						return nextToken(sym.NAME, yytext());
					}
					case -170:
						break;
					case 173: {
						return nextToken(sym.NAME, yytext());
					}
					case -171:
						break;
					case 174: {
						return nextToken(sym.NAME, yytext());
					}
					case -172:
						break;
					case 175: {
						return nextToken(sym.NAME, yytext());
					}
					case -173:
						break;
					case 176: {
						return nextToken(sym.NAME, yytext());
					}
					case -174:
						break;
					case 177: {
						return nextToken(sym.NAME, yytext());
					}
					case -175:
						break;
					case 178: {
						return nextToken(sym.NAME, yytext());
					}
					case -176:
						break;
					case 179: {
						return nextToken(sym.NAME, yytext());
					}
					case -177:
						break;
					case 180: {
						return nextToken(sym.NAME, yytext());
					}
					case -178:
						break;
					case 181: {
						return nextToken(sym.NAME, yytext());
					}
					case -179:
						break;
					case 182: {
						return nextToken(sym.NAME, yytext());
					}
					case -180:
						break;
					case 183: {
						return nextToken(sym.NAME, yytext());
					}
					case -181:
						break;
					case 184: {
						return nextToken(sym.NAME, yytext());
					}
					case -182:
						break;
					case 185: {
						return nextToken(sym.NAME, yytext());
					}
					case -183:
						break;
					case 186: {
						return nextToken(sym.NAME, yytext());
					}
					case -184:
						break;
					case 187: {
						return nextToken(sym.NAME, yytext());
					}
					case -185:
						break;
					case 188: {
						return nextToken(sym.NAME, yytext());
					}
					case -186:
						break;
					case 189: {
						return nextToken(sym.NAME, yytext());
					}
					case -187:
						break;
					case 190: {
						return nextToken(sym.NAME, yytext());
					}
					case -188:
						break;
					case 191: {
						return nextToken(sym.NAME, yytext());
					}
					case -189:
						break;
					case 192: {
						return nextToken(sym.NAME, yytext());
					}
					case -190:
						break;
					case 193: {
						return nextToken(sym.NAME, yytext());
					}
					case -191:
						break;
					case 194: {
						return nextToken(sym.NAME, yytext());
					}
					case -192:
						break;
					case 195: {
						return nextToken(sym.NAME, yytext());
					}
					case -193:
						break;
					case 196: {
						return nextToken(sym.NAME, yytext());
					}
					case -194:
						break;
					case 197: {
						return nextToken(sym.NAME, yytext());
					}
					case -195:
						break;
					case 198: {
						return nextToken(sym.NAME, yytext());
					}
					case -196:
						break;
					case 199: {
						return nextToken(sym.NAME, yytext());
					}
					case -197:
						break;
					case 200: {
						return nextToken(sym.NAME, yytext());
					}
					case -198:
						break;
					case 201: {
						return nextToken(sym.NAME, yytext());
					}
					case -199:
						break;
					case 202: {
						return nextToken(sym.NAME, yytext());
					}
					case -200:
						break;
					case 203: {
						return nextToken(sym.NAME, yytext());
					}
					case -201:
						break;
					case 204: {
						return nextToken(sym.NAME, yytext());
					}
					case -202:
						break;
					case 205: {
						return nextToken(sym.NAME, yytext());
					}
					case -203:
						break;
					case 206: {
						return nextToken(sym.NAME, yytext());
					}
					case -204:
						break;
					case 207: {
						return nextToken(sym.NAME, yytext());
					}
					case -205:
						break;
					case 208: {
						return nextToken(sym.NAME, yytext());
					}
					case -206:
						break;
					case 209: {
						return nextToken(sym.NAME, yytext());
					}
					case -207:
						break;
					case 210: {
						return nextToken(sym.NAME, yytext());
					}
					case -208:
						break;
					case 211: {
						return nextToken(sym.NAME, yytext());
					}
					case -209:
						break;
					case 212: {
						return nextToken(sym.NAME, yytext());
					}
					case -210:
						break;
					case 213: {
						return nextToken(sym.NAME, yytext());
					}
					case -211:
						break;
					case 214: {
						return nextToken(sym.NAME, yytext());
					}
					case -212:
						break;
					case 215: {
						return nextToken(sym.NAME, yytext());
					}
					case -213:
						break;
					case 216: {
						return nextToken(sym.NAME, yytext());
					}
					case -214:
						break;
					case 217: {
						return nextToken(sym.NAME, yytext());
					}
					case -215:
						break;
					case 218: {
						return nextToken(sym.NAME, yytext());
					}
					case -216:
						break;
					case 219: {
						return nextToken(sym.NAME, yytext());
					}
					case -217:
						break;
					case 220: {
						return nextToken(sym.NAME, yytext());
					}
					case -218:
						break;
					case 221: {
						return nextToken(sym.NAME, yytext());
					}
					case -219:
						break;
					case 222: {
						return nextToken(sym.NAME, yytext());
					}
					case -220:
						break;
					case 223: {
						return nextToken(sym.NAME, yytext());
					}
					case -221:
						break;
					case 224: {
						return nextToken(sym.NAME, yytext());
					}
					case -222:
						break;
					case 225: {
						return nextToken(sym.NAME, yytext());
					}
					case -223:
						break;
					case 226: {
						return nextToken(sym.NAME, yytext());
					}
					case -224:
						break;
					case 227: {
						return nextToken(sym.NAME, yytext());
					}
					case -225:
						break;
					case 228: {
						return nextToken(sym.NAME, yytext());
					}
					case -226:
						break;
					case 229: {
						return nextToken(sym.NAME, yytext());
					}
					case -227:
						break;
					default:
						yy_error(YY_E_INTERNAL, false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
