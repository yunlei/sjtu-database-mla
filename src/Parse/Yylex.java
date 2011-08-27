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
	return new java_cup.runtime.Symbol(kind, yychar, yychar+yylength(),value);
}
private java_cup.runtime.Symbol nextToken(int kind){
	return nextToken(kind,yytext());
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

	Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
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
	private final int yy_state_dtrans[] = {
		0,
		159
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
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
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
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
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
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
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NOT_ACCEPT,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NOT_ACCEPT,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NOT_ACCEPT,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NO_ANCHOR,
		/* 169 */ YY_NO_ANCHOR,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NO_ANCHOR,
		/* 172 */ YY_NO_ANCHOR,
		/* 173 */ YY_NO_ANCHOR,
		/* 174 */ YY_NO_ANCHOR,
		/* 175 */ YY_NO_ANCHOR,
		/* 176 */ YY_NO_ANCHOR,
		/* 177 */ YY_NO_ANCHOR,
		/* 178 */ YY_NO_ANCHOR,
		/* 179 */ YY_NO_ANCHOR,
		/* 180 */ YY_NO_ANCHOR,
		/* 181 */ YY_NO_ANCHOR,
		/* 182 */ YY_NO_ANCHOR,
		/* 183 */ YY_NO_ANCHOR,
		/* 184 */ YY_NO_ANCHOR,
		/* 185 */ YY_NO_ANCHOR,
		/* 186 */ YY_NO_ANCHOR,
		/* 187 */ YY_NO_ANCHOR,
		/* 188 */ YY_NO_ANCHOR,
		/* 189 */ YY_NO_ANCHOR,
		/* 190 */ YY_NO_ANCHOR,
		/* 191 */ YY_NO_ANCHOR,
		/* 192 */ YY_NO_ANCHOR,
		/* 193 */ YY_NO_ANCHOR,
		/* 194 */ YY_NO_ANCHOR,
		/* 195 */ YY_NO_ANCHOR,
		/* 196 */ YY_NO_ANCHOR,
		/* 197 */ YY_NO_ANCHOR,
		/* 198 */ YY_NO_ANCHOR,
		/* 199 */ YY_NO_ANCHOR,
		/* 200 */ YY_NO_ANCHOR,
		/* 201 */ YY_NO_ANCHOR,
		/* 202 */ YY_NO_ANCHOR,
		/* 203 */ YY_NO_ANCHOR,
		/* 204 */ YY_NO_ANCHOR,
		/* 205 */ YY_NO_ANCHOR,
		/* 206 */ YY_NO_ANCHOR,
		/* 207 */ YY_NO_ANCHOR,
		/* 208 */ YY_NO_ANCHOR,
		/* 209 */ YY_NO_ANCHOR,
		/* 210 */ YY_NO_ANCHOR,
		/* 211 */ YY_NO_ANCHOR,
		/* 212 */ YY_NO_ANCHOR,
		/* 213 */ YY_NO_ANCHOR,
		/* 214 */ YY_NO_ANCHOR,
		/* 215 */ YY_NO_ANCHOR,
		/* 216 */ YY_NO_ANCHOR,
		/* 217 */ YY_NO_ANCHOR,
		/* 218 */ YY_NO_ANCHOR,
		/* 219 */ YY_NO_ANCHOR,
		/* 220 */ YY_NO_ANCHOR,
		/* 221 */ YY_NO_ANCHOR,
		/* 222 */ YY_NO_ANCHOR,
		/* 223 */ YY_NO_ANCHOR,
		/* 224 */ YY_NO_ANCHOR,
		/* 225 */ YY_NO_ANCHOR,
		/* 226 */ YY_NO_ANCHOR,
		/* 227 */ YY_NO_ANCHOR,
		/* 228 */ YY_NO_ANCHOR,
		/* 229 */ YY_NO_ANCHOR,
		/* 230 */ YY_NO_ANCHOR,
		/* 231 */ YY_NO_ANCHOR,
		/* 232 */ YY_NO_ANCHOR,
		/* 233 */ YY_NO_ANCHOR,
		/* 234 */ YY_NO_ANCHOR,
		/* 235 */ YY_NO_ANCHOR,
		/* 236 */ YY_NO_ANCHOR,
		/* 237 */ YY_NO_ANCHOR,
		/* 238 */ YY_NO_ANCHOR,
		/* 239 */ YY_NO_ANCHOR,
		/* 240 */ YY_NO_ANCHOR,
		/* 241 */ YY_NO_ANCHOR,
		/* 242 */ YY_NO_ANCHOR,
		/* 243 */ YY_NO_ANCHOR,
		/* 244 */ YY_NO_ANCHOR,
		/* 245 */ YY_NO_ANCHOR,
		/* 246 */ YY_NO_ANCHOR,
		/* 247 */ YY_NO_ANCHOR,
		/* 248 */ YY_NO_ANCHOR,
		/* 249 */ YY_NO_ANCHOR,
		/* 250 */ YY_NO_ANCHOR,
		/* 251 */ YY_NO_ANCHOR,
		/* 252 */ YY_NO_ANCHOR,
		/* 253 */ YY_NO_ANCHOR,
		/* 254 */ YY_NO_ANCHOR,
		/* 255 */ YY_NO_ANCHOR,
		/* 256 */ YY_NO_ANCHOR,
		/* 257 */ YY_NO_ANCHOR,
		/* 258 */ YY_NO_ANCHOR,
		/* 259 */ YY_NO_ANCHOR,
		/* 260 */ YY_NO_ANCHOR,
		/* 261 */ YY_NO_ANCHOR,
		/* 262 */ YY_NO_ANCHOR,
		/* 263 */ YY_NO_ANCHOR,
		/* 264 */ YY_NO_ANCHOR,
		/* 265 */ YY_NO_ANCHOR,
		/* 266 */ YY_NO_ANCHOR,
		/* 267 */ YY_NO_ANCHOR,
		/* 268 */ YY_NO_ANCHOR,
		/* 269 */ YY_NO_ANCHOR,
		/* 270 */ YY_NO_ANCHOR,
		/* 271 */ YY_NO_ANCHOR,
		/* 272 */ YY_NO_ANCHOR,
		/* 273 */ YY_NO_ANCHOR,
		/* 274 */ YY_NO_ANCHOR,
		/* 275 */ YY_NO_ANCHOR,
		/* 276 */ YY_NO_ANCHOR,
		/* 277 */ YY_NO_ANCHOR,
		/* 278 */ YY_NO_ANCHOR,
		/* 279 */ YY_NO_ANCHOR,
		/* 280 */ YY_NO_ANCHOR,
		/* 281 */ YY_NO_ANCHOR,
		/* 282 */ YY_NO_ANCHOR,
		/* 283 */ YY_NO_ANCHOR,
		/* 284 */ YY_NO_ANCHOR,
		/* 285 */ YY_NO_ANCHOR,
		/* 286 */ YY_NO_ANCHOR,
		/* 287 */ YY_NO_ANCHOR,
		/* 288 */ YY_NO_ANCHOR,
		/* 289 */ YY_NO_ANCHOR,
		/* 290 */ YY_NO_ANCHOR,
		/* 291 */ YY_NO_ANCHOR,
		/* 292 */ YY_NO_ANCHOR,
		/* 293 */ YY_NO_ANCHOR,
		/* 294 */ YY_NO_ANCHOR,
		/* 295 */ YY_NO_ANCHOR,
		/* 296 */ YY_NO_ANCHOR,
		/* 297 */ YY_NO_ANCHOR,
		/* 298 */ YY_NO_ANCHOR,
		/* 299 */ YY_NO_ANCHOR,
		/* 300 */ YY_NO_ANCHOR,
		/* 301 */ YY_NO_ANCHOR,
		/* 302 */ YY_NO_ANCHOR,
		/* 303 */ YY_NO_ANCHOR,
		/* 304 */ YY_NO_ANCHOR,
		/* 305 */ YY_NO_ANCHOR,
		/* 306 */ YY_NO_ANCHOR,
		/* 307 */ YY_NO_ANCHOR,
		/* 308 */ YY_NO_ANCHOR,
		/* 309 */ YY_NO_ANCHOR,
		/* 310 */ YY_NO_ANCHOR,
		/* 311 */ YY_NO_ANCHOR,
		/* 312 */ YY_NO_ANCHOR,
		/* 313 */ YY_NO_ANCHOR,
		/* 314 */ YY_NO_ANCHOR,
		/* 315 */ YY_NO_ANCHOR,
		/* 316 */ YY_NO_ANCHOR,
		/* 317 */ YY_NO_ANCHOR,
		/* 318 */ YY_NO_ANCHOR,
		/* 319 */ YY_NO_ANCHOR,
		/* 320 */ YY_NO_ANCHOR,
		/* 321 */ YY_NO_ANCHOR,
		/* 322 */ YY_NO_ANCHOR,
		/* 323 */ YY_NO_ANCHOR,
		/* 324 */ YY_NO_ANCHOR,
		/* 325 */ YY_NO_ANCHOR,
		/* 326 */ YY_NO_ANCHOR,
		/* 327 */ YY_NO_ANCHOR,
		/* 328 */ YY_NO_ANCHOR,
		/* 329 */ YY_NO_ANCHOR,
		/* 330 */ YY_NO_ANCHOR,
		/* 331 */ YY_NO_ANCHOR,
		/* 332 */ YY_NO_ANCHOR,
		/* 333 */ YY_NO_ANCHOR,
		/* 334 */ YY_NO_ANCHOR,
		/* 335 */ YY_NO_ANCHOR,
		/* 336 */ YY_NO_ANCHOR,
		/* 337 */ YY_NO_ANCHOR,
		/* 338 */ YY_NO_ANCHOR,
		/* 339 */ YY_NO_ANCHOR,
		/* 340 */ YY_NO_ANCHOR,
		/* 341 */ YY_NO_ANCHOR,
		/* 342 */ YY_NO_ANCHOR,
		/* 343 */ YY_NO_ANCHOR,
		/* 344 */ YY_NO_ANCHOR,
		/* 345 */ YY_NO_ANCHOR,
		/* 346 */ YY_NO_ANCHOR,
		/* 347 */ YY_NO_ANCHOR,
		/* 348 */ YY_NO_ANCHOR,
		/* 349 */ YY_NO_ANCHOR,
		/* 350 */ YY_NO_ANCHOR,
		/* 351 */ YY_NO_ANCHOR,
		/* 352 */ YY_NO_ANCHOR,
		/* 353 */ YY_NO_ANCHOR,
		/* 354 */ YY_NO_ANCHOR,
		/* 355 */ YY_NO_ANCHOR,
		/* 356 */ YY_NO_ANCHOR,
		/* 357 */ YY_NO_ANCHOR,
		/* 358 */ YY_NO_ANCHOR,
		/* 359 */ YY_NO_ANCHOR,
		/* 360 */ YY_NO_ANCHOR,
		/* 361 */ YY_NO_ANCHOR,
		/* 362 */ YY_NO_ANCHOR,
		/* 363 */ YY_NO_ANCHOR,
		/* 364 */ YY_NO_ANCHOR,
		/* 365 */ YY_NO_ANCHOR,
		/* 366 */ YY_NO_ANCHOR,
		/* 367 */ YY_NO_ANCHOR,
		/* 368 */ YY_NO_ANCHOR,
		/* 369 */ YY_NO_ANCHOR,
		/* 370 */ YY_NO_ANCHOR,
		/* 371 */ YY_NO_ANCHOR,
		/* 372 */ YY_NO_ANCHOR,
		/* 373 */ YY_NO_ANCHOR,
		/* 374 */ YY_NO_ANCHOR,
		/* 375 */ YY_NO_ANCHOR,
		/* 376 */ YY_NO_ANCHOR,
		/* 377 */ YY_NO_ANCHOR,
		/* 378 */ YY_NO_ANCHOR,
		/* 379 */ YY_NO_ANCHOR,
		/* 380 */ YY_NO_ANCHOR,
		/* 381 */ YY_NO_ANCHOR,
		/* 382 */ YY_NO_ANCHOR,
		/* 383 */ YY_NO_ANCHOR,
		/* 384 */ YY_NO_ANCHOR,
		/* 385 */ YY_NO_ANCHOR,
		/* 386 */ YY_NO_ANCHOR,
		/* 387 */ YY_NO_ANCHOR,
		/* 388 */ YY_NO_ANCHOR,
		/* 389 */ YY_NO_ANCHOR,
		/* 390 */ YY_NO_ANCHOR,
		/* 391 */ YY_NO_ANCHOR,
		/* 392 */ YY_NO_ANCHOR,
		/* 393 */ YY_NO_ANCHOR,
		/* 394 */ YY_NO_ANCHOR,
		/* 395 */ YY_NO_ANCHOR,
		/* 396 */ YY_NO_ANCHOR,
		/* 397 */ YY_NO_ANCHOR,
		/* 398 */ YY_NO_ANCHOR,
		/* 399 */ YY_NO_ANCHOR,
		/* 400 */ YY_NO_ANCHOR,
		/* 401 */ YY_NO_ANCHOR,
		/* 402 */ YY_NO_ANCHOR,
		/* 403 */ YY_NO_ANCHOR,
		/* 404 */ YY_NO_ANCHOR,
		/* 405 */ YY_NO_ANCHOR,
		/* 406 */ YY_NO_ANCHOR,
		/* 407 */ YY_NO_ANCHOR,
		/* 408 */ YY_NO_ANCHOR,
		/* 409 */ YY_NO_ANCHOR,
		/* 410 */ YY_NO_ANCHOR,
		/* 411 */ YY_NO_ANCHOR,
		/* 412 */ YY_NO_ANCHOR,
		/* 413 */ YY_NO_ANCHOR,
		/* 414 */ YY_NO_ANCHOR,
		/* 415 */ YY_NO_ANCHOR,
		/* 416 */ YY_NO_ANCHOR,
		/* 417 */ YY_NO_ANCHOR,
		/* 418 */ YY_NO_ANCHOR,
		/* 419 */ YY_NO_ANCHOR,
		/* 420 */ YY_NO_ANCHOR,
		/* 421 */ YY_NO_ANCHOR,
		/* 422 */ YY_NO_ANCHOR,
		/* 423 */ YY_NO_ANCHOR,
		/* 424 */ YY_NO_ANCHOR,
		/* 425 */ YY_NO_ANCHOR,
		/* 426 */ YY_NO_ANCHOR,
		/* 427 */ YY_NO_ANCHOR,
		/* 428 */ YY_NO_ANCHOR,
		/* 429 */ YY_NO_ANCHOR,
		/* 430 */ YY_NO_ANCHOR,
		/* 431 */ YY_NO_ANCHOR,
		/* 432 */ YY_NO_ANCHOR,
		/* 433 */ YY_NO_ANCHOR,
		/* 434 */ YY_NO_ANCHOR,
		/* 435 */ YY_NO_ANCHOR,
		/* 436 */ YY_NO_ANCHOR,
		/* 437 */ YY_NO_ANCHOR,
		/* 438 */ YY_NO_ANCHOR,
		/* 439 */ YY_NO_ANCHOR,
		/* 440 */ YY_NO_ANCHOR,
		/* 441 */ YY_NO_ANCHOR,
		/* 442 */ YY_NO_ANCHOR,
		/* 443 */ YY_NO_ANCHOR,
		/* 444 */ YY_NO_ANCHOR,
		/* 445 */ YY_NO_ANCHOR,
		/* 446 */ YY_NO_ANCHOR,
		/* 447 */ YY_NO_ANCHOR,
		/* 448 */ YY_NO_ANCHOR,
		/* 449 */ YY_NO_ANCHOR,
		/* 450 */ YY_NO_ANCHOR,
		/* 451 */ YY_NO_ANCHOR,
		/* 452 */ YY_NO_ANCHOR,
		/* 453 */ YY_NO_ANCHOR,
		/* 454 */ YY_NO_ANCHOR,
		/* 455 */ YY_NO_ANCHOR,
		/* 456 */ YY_NO_ANCHOR,
		/* 457 */ YY_NO_ANCHOR,
		/* 458 */ YY_NO_ANCHOR,
		/* 459 */ YY_NO_ANCHOR,
		/* 460 */ YY_NO_ANCHOR,
		/* 461 */ YY_NO_ANCHOR,
		/* 462 */ YY_NO_ANCHOR,
		/* 463 */ YY_NO_ANCHOR,
		/* 464 */ YY_NO_ANCHOR,
		/* 465 */ YY_NO_ANCHOR,
		/* 466 */ YY_NO_ANCHOR,
		/* 467 */ YY_NO_ANCHOR,
		/* 468 */ YY_NO_ANCHOR,
		/* 469 */ YY_NO_ANCHOR,
		/* 470 */ YY_NO_ANCHOR,
		/* 471 */ YY_NO_ANCHOR,
		/* 472 */ YY_NO_ANCHOR,
		/* 473 */ YY_NO_ANCHOR,
		/* 474 */ YY_NO_ANCHOR,
		/* 475 */ YY_NO_ANCHOR,
		/* 476 */ YY_NO_ANCHOR,
		/* 477 */ YY_NO_ANCHOR,
		/* 478 */ YY_NO_ANCHOR,
		/* 479 */ YY_NO_ANCHOR,
		/* 480 */ YY_NO_ANCHOR,
		/* 481 */ YY_NO_ANCHOR,
		/* 482 */ YY_NO_ANCHOR,
		/* 483 */ YY_NO_ANCHOR,
		/* 484 */ YY_NO_ANCHOR,
		/* 485 */ YY_NO_ANCHOR,
		/* 486 */ YY_NO_ANCHOR,
		/* 487 */ YY_NO_ANCHOR,
		/* 488 */ YY_NO_ANCHOR,
		/* 489 */ YY_NO_ANCHOR,
		/* 490 */ YY_NO_ANCHOR,
		/* 491 */ YY_NO_ANCHOR,
		/* 492 */ YY_NO_ANCHOR,
		/* 493 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"67:8,51:2,53,67:2,52,67:18,51,67,69,67:2,62,67,54,65,66,60,58,63,59,50,61,4" +
"8:10,67,64,56,55,57,67:2,1,11,10,6,4,16,23,22,15,47,18,2,17,7,12,13,47,5,9," +
"3,19,20,21,14,8,47,67,68,67:2,49,67,24,34,33,29,27,39,46,45,38,47,41,25,40," +
"30,35,36,47,28,32,26,42,43,44,37,31,47,67:5,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,494,
"0,1,2,3,1,4,1:2,5,6,1:9,7,8:2,9,8,10,11,8:2,12,8,13,1:4,8:9,14,8:13,15,8:4," +
"16,8:3,17,8:10,18,8:69,19,1,20,1:4,21,22,16,23,24,25,26,27,28,29,30,31,32,3" +
"3,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,5" +
"8,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,8" +
"3,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,1" +
"06,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124," +
"125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143" +
",144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,16" +
"2,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,1" +
"81,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199," +
"200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218" +
",219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,23" +
"7,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,2" +
"56,257,258,259,260,261,262,263,264,265,266,267,268,269,270,271,272,273,274," +
"275,276,277,278,279,280,281,282,283,284,285,286,287,288,289,290,291,292,293" +
",294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,31" +
"2,313,314,315,316,317,318,319,320,321,322,323,324,325,326,327,328,329,330,3" +
"31,332,333,334,335,8,336,337,338,339,340,341,342,343,344,345,346,347,348,34" +
"9,350,351,352,353,354,355,356,357")[0];

	private int yy_nxt[][] = unpackFromString(358,70,
"1,2,156,158,422,453,466,272,471,273,476,160,161,477,471,162,478,274,275,276" +
",479,480,481,482,163,483,164,484,485,486,277,471,278,487,165,166,488,471,16" +
"7,489,279,280,281,490,491,492,493,471,3,-1,4,5,155,5,6,7,8,9,10,11,12,13,14" +
",15,16,17,18,-1:74,471,168,471:3,169,170,471,19,471:9,282,171,471:29,-1:68," +
"3,-1,157,-1:70,5,-1,5,-1:71,32,-1,33,-1:67,34,-1:15,471:9,39,471:39,-1:21,4" +
"71:49,-1:21,471:5,336,471:43,-1:21,471:2,44,471:2,339,471:2,340,429,471:39," +
"-1:21,471:32,53,471:16,-1:21,471:28,354,471:20,-1:21,471:25,58,471:2,357,47" +
"1:2,358,436,471:16,-1:21,471:11,71,471:37,-1:21,471:34,82,471:14,-1:68,63,-" +
"1:22,471:4,447,471:44,-1:21,471:27,450,471:21,-1:21,148:51,-1:3,148:13,-1,1" +
"48,-1:26,151,-1,152,-1,153,-1:38,154,-1:53,31,-1:17,471:14,283,471:34,-1:21" +
",284,471:3,285,471:6,20,471:37,-1:20,1,148:51,-1:2,149,148:13,150,148,-1,47" +
"1:7,21,471:3,297,471:37,-1:21,471:4,22,471,23,471:5,424,471:36,-1:21,471:6," +
"24,471:42,-1:21,471:24,179,471:3,180,181,471,25,471:9,306,182,471:6,-1:21,4" +
"71:23,308,471:3,309,471:6,26,471:14,-1:21,471:30,27,471:3,321,471:14,-1:21," +
"471:27,28,471,29,471:5,431,471:13,-1:21,471:29,30,471:19,-1:21,471,35,330,4" +
"71:46,-1:21,471:5,36,471:43,-1:21,471:5,37,471,38,471:41,-1:21,471:22,40,47" +
"1:26,-1:21,471:2,41,471:46,-1:21,471,472,42,471:46,-1:21,471:16,43,471:32,-" +
"1:21,471:13,45,471:35,-1:21,471:6,46,471:42,-1:21,471:7,47,471:41,-1:21,471" +
":3,48,471:45,-1:21,471:24,49,348,471:23,-1:21,471:28,50,471:20,-1:21,471:28" +
",51,471,52,471:18,-1:21,471:45,54,471:3,-1:21,471:25,55,471:23,-1:21,471:24" +
",473,56,471:23,-1:21,471:39,57,471:9,-1:21,471:36,59,471:12,-1:21,471:29,60" +
",471:19,-1:21,471:30,61,471:18,-1:21,471:26,62,471:22,-1:21,471:11,64,471:3" +
"7,-1:21,471:3,65,471:45,-1:21,471:3,66,471:45,-1:21,471:9,67,471:39,-1:21,4" +
"71:12,68,471:36,-1:21,471,69,471:47,-1:21,471:4,70,471:44,-1:21,471:16,72,4" +
"71:32,-1:21,471:20,73,471:28,-1:21,471:21,74,471:27,-1:21,471:34,75,471:14," +
"-1:21,471:26,76,471:22,-1:21,471:26,77,471:22,-1:21,471:32,78,471:16,-1:21," +
"471:35,79,471:13,-1:21,471:24,80,471:24,-1:21,471:27,81,471:21,-1:21,471:39" +
",83,471:9,-1:21,471:43,84,471:5,-1:21,471:44,85,471:4,-1:21,471:4,86,471:44" +
",-1:21,471:3,87,471:45,-1:21,471:2,88,471:46,-1:21,471:17,89,471:31,-1:21,4" +
"71:4,90,471:44,-1:21,471:13,91,471:35,-1:21,471:3,92,471:45,-1:21,471:2,93," +
"471:46,-1:21,471:6,94,471:42,-1:21,471:3,95,471:45,-1:21,471:2,96,471:46,-1" +
":21,471:12,97,471:36,-1:21,471:27,98,471:21,-1:21,471:26,99,471:22,-1:21,47" +
"1:25,100,471:23,-1:21,471:40,101,471:8,-1:21,471:27,102,471:21,-1:21,471:36" +
",103,471:12,-1:21,471:26,104,471:22,-1:21,471:25,105,471:23,-1:21,471:29,10" +
"6,471:19,-1:21,471:26,107,471:22,-1:21,471:25,108,471:23,-1:21,471:35,109,4" +
"71:13,-1:21,471:3,110,471:45,-1:21,471:8,111,471:40,-1:21,471:3,112,471:45," +
"-1:21,471:2,113,471:46,-1:21,471:3,114,471:45,-1:21,471:6,115,471:42,-1:21," +
"471:2,116,471:46,-1:21,471:3,117,471:45,-1:21,471:8,118,471:40,-1:21,471:22" +
",119,471:26,-1:21,471:26,120,471:22,-1:21,471:31,121,471:17,-1:21,471:26,12" +
"2,471:22,-1:21,471:25,123,471:23,-1:21,471:26,124,471:22,-1:21,471:29,125,4" +
"71:19,-1:21,471:25,126,471:23,-1:21,471:26,127,471:22,-1:21,471:31,128,471:" +
"17,-1:21,471:45,129,471:3,-1:21,471:2,130,471:46,-1:21,471:6,131,471:42,-1:" +
"21,471:7,132,471:41,-1:21,471:6,133,471:42,-1:21,471:25,134,471:23,-1:21,47" +
"1:29,135,471:19,-1:21,471:30,136,471:18,-1:21,471:29,137,471:19,-1:21,471:3" +
",138,471:45,-1:21,471:3,139,471:45,-1:21,471:2,140,471:46,-1:21,471:26,141," +
"471:22,-1:21,471:26,142,471:22,-1:21,471:25,143,471:23,-1:21,471:2,144,471:" +
"46,-1:21,471:25,145,471:23,-1:21,471:8,146,471:40,-1:21,471:31,147,471:17,-" +
"1:21,471:11,172,471:6,293,471:30,-1:21,471:3,173,471:14,174,471:30,-1:21,17" +
"5,471:13,176,471:34,-1:21,471:3,177,471:45,-1:21,471:6,454,471,178,471:3,30" +
"1,471:36,-1:21,471:34,183,471:6,317,471:7,-1:21,471:26,184,471:14,185,471:7" +
",-1:21,471:23,186,471:13,187,471:11,-1:21,471:26,188,471:22,-1:21,471:29,45" +
"7,471,189,471:3,325,471:13,-1:21,471:2,190,471:46,-1:21,471:17,191,471:31,-" +
"1:21,471:10,331,471:38,-1:21,471:18,192,471:30,-1:21,471:9,332,471:39,-1:21" +
",471:14,425,471:34,-1:21,471:15,455,471:33,-1:21,471:2,437,471:46,-1:21,471" +
",467,471:6,193,471:6,460,471:33,-1:21,471:11,194,471:37,-1:21,471:8,333,471" +
":40,-1:21,471,195,471:47,-1:21,471:3,469,471:45,-1:21,471:18,334,471:30,-1:" +
"21,196,471:2,335,471:45,-1:21,471:11,456,471:37,-1:21,471,341,471:47,-1:21," +
"471:11,197,471:37,-1:21,471:4,439,471:44,-1:21,471:5,474,471:43,-1:21,471:3" +
",198,471:45,-1:21,471:2,199,471:46,-1:21,471:19,438,471:29,-1:21,346,471:10" +
",347,471:37,-1:21,471:25,200,471:23,-1:21,471:40,201,471:8,-1:21,471:33,349" +
",471:15,-1:21,471:41,202,471:7,-1:21,471:32,350,471:16,-1:21,471:37,432,471" +
":11,-1:21,471:38,458,471:10,-1:21,471:25,441,471:23,-1:21,471:24,468,471:6," +
"203,471:6,463,471:10,-1:21,471:34,204,471:14,-1:21,471:31,351,471:17,-1:21," +
"471:24,205,471:24,-1:21,471:26,470,471:22,-1:21,471:41,352,471:7,-1:21,471:" +
"23,206,471:2,353,471:22,-1:21,471:34,459,471:14,-1:21,471:24,359,471:24,-1:" +
"21,471:34,207,471:14,-1:21,471:27,443,471:21,-1:21,471:28,475,471:20,-1:21," +
"471:26,208,471:22,-1:21,471:25,209,471:23,-1:21,471:42,442,471:6,-1:21,471:" +
"23,364,471:10,365,471:14,-1:21,471:3,210,471:45,-1:21,471,211,471:47,-1:21," +
"366,471:48,-1:21,471:2,461,471:46,-1:21,471:6,212,471:42,-1:21,471:9,213,47" +
"1:39,-1:21,471:3,214,471:45,-1:21,471:14,373,471:34,-1:21,471:16,374,471:32" +
",-1:21,471:3,215,471:45,-1:21,471:3,375,471:45,-1:21,471:8,216,471:40,-1:21" +
",217,471:48,-1:21,471:11,218,471:37,-1:21,471:18,379,471:30,-1:21,471:4,219" +
",471:44,-1:21,471:6,220,471:42,-1:21,471:18,221,471:30,-1:21,471:26,222,471" +
":22,-1:21,471:24,223,471:24,-1:21,471:23,381,471:25,-1:21,471:25,464,471:23" +
",-1:21,471:29,224,471:19,-1:21,471:32,225,471:16,-1:21,471:26,226,471:22,-1" +
":21,471:37,388,471:11,-1:21,471:39,389,471:9,-1:21,471:26,227,471:22,-1:21," +
"471:26,390,471:22,-1:21,471:31,228,471:17,-1:21,471:23,229,471:25,-1:21,471" +
":34,230,471:14,-1:21,471:41,394,471:7,-1:21,471:27,231,471:21,-1:21,471:29," +
"232,471:19,-1:21,471:41,233,471:7,-1:21,471:12,234,471:36,-1:21,471:2,235,4" +
"71:46,-1:21,471:4,446,471:44,-1:21,471:10,445,471:38,-1:21,471:2,236,471:46" +
",-1:21,471:9,237,471:39,-1:21,471:2,238,471:46,-1:21,471:11,239,471:37,-1:2" +
"1,399,471:48,-1:21,471:4,240,471:44,-1:21,471:3,400,471:45,-1:21,471:14,401" +
",471:34,-1:21,471:2,241,471:46,-1:21,471:3,242,471:45,-1:21,471:6,243,471:4" +
"2,-1:21,471:35,244,471:13,-1:21,471:25,245,471:23,-1:21,471:27,449,471:21,-" +
"1:21,471:33,448,471:15,-1:21,471:25,246,471:23,-1:21,471:32,247,471:16,-1:2" +
"1,471:25,248,471:23,-1:21,471:34,249,471:14,-1:21,471:23,405,471:25,-1:21,4" +
"71:27,250,471:21,-1:21,471:26,406,471:22,-1:21,471:37,407,471:11,-1:21,471:" +
"25,251,471:23,-1:21,471:26,252,471:22,-1:21,471:29,253,471:19,-1:21,471,254" +
",471:47,-1:21,471:6,410,471:42,-1:21,255,471:48,-1:21,471:4,256,471:44,-1:2" +
"1,471:16,411,471:32,-1:21,471:22,257,471:26,-1:21,471:24,258,471:24,-1:21,4" +
"71:29,414,471:19,-1:21,471:23,259,471:25,-1:21,471:27,260,471:21,-1:21,471:" +
"39,415,471:9,-1:21,471:45,261,471:3,-1:21,471:8,262,471:40,-1:21,471:10,263" +
",471:38,-1:21,471:9,264,471:39,-1:21,471:3,417,471:45,-1:21,471:31,265,471:" +
"17,-1:21,471:33,266,471:15,-1:21,471:32,267,471:16,-1:21,471:26,419,471:22," +
"-1:21,471:9,420,471:39,-1:21,471:6,268,471:42,-1:21,471:32,421,471:16,-1:21" +
",471:29,269,471:19,-1:21,471:3,270,471:45,-1:21,471:26,271,471:22,-1:21,471" +
":8,286,471:4,287,471:35,-1:21,471:14,338,471:34,-1:21,471:2,337,471:46,-1:2" +
"1,471:8,367,471:40,-1:21,471:3,345,471:45,-1:21,471:11,342,471:37,-1:21,471" +
",344,471:47,-1:21,471:4,376,471:44,-1:21,471:37,356,471:11,-1:21,471:25,355" +
",471:23,-1:21,471:31,382,471:17,-1:21,471:26,363,471:22,-1:21,471:34,360,47" +
"1:14,-1:21,471:24,362,471:24,-1:21,471:27,391,471:21,-1:21,369,471:48,-1:21" +
",471:14,380,471:34,-1:21,471:3,377,471:45,-1:21,471:18,396,471:30,-1:21,471" +
":23,384,471:25,-1:21,471:37,395,471:11,-1:21,471:26,392,471:22,-1:21,471:41" +
",402,471:7,-1:21,408,471:48,-1:21,471:3,451,471:45,-1:21,471:14,409,471:34," +
"-1:21,471:23,412,471:25,-1:21,471:26,452,471:22,-1:21,471:37,413,471:11,-1:" +
"21,471:6,416,471:42,-1:21,471:29,418,471:19,-1:21,471:3,288,471:45,-1:21,47" +
"1:14,343,471:34,-1:21,471:3,368,471:45,-1:21,471,462,471:47,-1:21,471:37,36" +
"1,471:11,-1:21,471:26,383,471:22,-1:21,471:24,465,471:24,-1:21,440,471:48,-" +
"1:21,471:14,397,471:34,-1:21,471:3,398,471:45,-1:21,471:23,444,471:25,-1:21" +
",471:37,403,471:11,-1:21,471:26,404,471:22,-1:21,289,471:2,290,291,471:9,29" +
"2,471:34,-1:21,471:3,370,471:45,-1:21,471:26,385,471:22,-1:21,372,471:48,-1" +
":21,471:23,387,471:25,-1:21,471:3,371,471:45,-1:21,471:26,386,471:22,-1:21," +
"378,471:48,-1:21,471:23,393,471:25,-1:21,471:4,294,471:6,295,471:9,296,471:" +
"27,-1:21,471:4,423,471:44,-1:21,298,427,471:2,299,471:6,300,471:37,-1:21,42" +
"8,471:13,302,471:34,-1:21,471:14,303,471:6,426,471:27,-1:21,304,471:48,-1:2" +
"1,471:4,305,471:44,-1:21,471:37,307,471:11,-1:21,471:31,310,471:4,311,471:1" +
"2,-1:21,471:26,312,471:22,-1:21,471:23,313,471:2,314,315,471:9,316,471:11,-" +
"1:21,471:27,318,471:6,319,471:9,320,471:4,-1:21,471:27,430,471:21,-1:21,471" +
":23,322,434,471:2,323,471:6,324,471:14,-1:21,471:23,435,471:13,326,471:11,-" +
"1:21,471:37,327,471:6,433,471:4,-1:21,471:23,328,471:25,-1:21,471:27,329,47" +
"1:21,-1:20");

	public java_cup.runtime.Symbol nextToken ()
		throws java.io.IOException {
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
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

	{
      if(yy_state ==STRING) return nextToken(sym.ERROR);
	 else return nextToken(sym.EOF);
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
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ return nextToken(sym.NAME,yytext()); }
					case -3:
						break;
					case 3:
						{ return nextToken(sym.INTVALUE,new Integer(yytext())); }
					case -4:
						break;
					case 4:
						{ return nextToken(sym.DOT); }
					case -5:
						break;
					case 5:
						{ }
					case -6:
						break;
					case 6:
						{ string.setLength(0);yybegin(STRING); }
					case -7:
						break;
					case 7:
						{ return nextToken(sym.EQ); }
					case -8:
						break;
					case 8:
						{ return nextToken(sym.LT); }
					case -9:
						break;
					case 9:
						{ return nextToken(sym.GT); }
					case -10:
						break;
					case 10:
						{ return nextToken(sym.PLUS); }
					case -11:
						break;
					case 11:
						{ return nextToken(sym.MINUS); }
					case -12:
						break;
					case 12:
						{ return nextToken(sym.TIMES); }
					case -13:
						break;
					case 13:
						{ return nextToken(sym.DIVIDE); }
					case -14:
						break;
					case 14:
						{ return nextToken(sym.MOD); }
					case -15:
						break;
					case 15:
						{ return nextToken(sym.COMMA); }
					case -16:
						break;
					case 16:
						{ return nextToken(sym.SEMICOLON); }
					case -17:
						break;
					case 17:
						{ return nextToken(sym.LPAREN); }
					case -18:
						break;
					case 18:
						{ return nextToken(sym.RPAREN); }
					case -19:
						break;
					case 19:
						{ return nextToken(sym.AS); }
					case -20:
						break;
					case 20:
						{return nextToken(sym.TO);}
					case -21:
						break;
					case 21:
						{ return nextToken(sym.BY); }
					case -22:
						break;
					case 22:
						{ return nextToken(sym.OR); }
					case -23:
						break;
					case 23:
						{ return nextToken(sym.ON); }
					case -24:
						break;
					case 24:
						{ return nextToken(sym.IN); }
					case -25:
						break;
					case 25:
						{ return nextToken(sym.AS); }
					case -26:
						break;
					case 26:
						{return nextToken(sym.TO);}
					case -27:
						break;
					case 27:
						{ return nextToken(sym.BY); }
					case -28:
						break;
					case 28:
						{ return nextToken(sym.OR); }
					case -29:
						break;
					case 29:
						{ return nextToken(sym.ON); }
					case -30:
						break;
					case 30:
						{ return nextToken(sym.IN); }
					case -31:
						break;
					case 31:
						{ }
					case -32:
						break;
					case 32:
						{ return nextToken(sym.LE); }
					case -33:
						break;
					case 33:
						{ return nextToken(sym.NEQ); }
					case -34:
						break;
					case 34:
						{ return nextToken(sym.GE); }
					case -35:
						break;
					case 35:
						{ return nextToken(sym.ALL); }
					case -36:
						break;
					case 36:
						{ return nextToken(sym.ADD); }
					case -37:
						break;
					case 37:
						{ return nextToken(sym.AND); }
					case -38:
						break;
					case 38:
						{ return nextToken(sym.ANY); }
					case -39:
						break;
					case 39:
						{ return nextToken(sym.ASC); }
					case -40:
						break;
					case 40:
						{ return nextToken(sym.AVG); }
					case -41:
						break;
					case 41:
						{ return nextToken(sym.NOT); }
					case -42:
						break;
					case 42:
						{ return nextToken(sym.SET); }
					case -43:
						break;
					case 43:
						{ return nextToken(sym.SUM); }
					case -44:
						break;
					case 44:
						{ return nextToken(sym.INT); }
					case -45:
						break;
					case 45:
						{ return nextToken(sym.MAX); }
					case -46:
						break;
					case 46:
						{ return nextToken(sym.MIN); }
					case -47:
						break;
					case 47:
						{ return nextToken(sym.KEY); }
					case -48:
						break;
					case 48:
						{ return nextToken(sym.USE); }
					case -49:
						break;
					case 49:
						{ return nextToken(sym.ALL); }
					case -50:
						break;
					case 50:
						{ return nextToken(sym.ADD); }
					case -51:
						break;
					case 51:
						{ return nextToken(sym.AND); }
					case -52:
						break;
					case 52:
						{ return nextToken(sym.ANY); }
					case -53:
						break;
					case 53:
						{ return nextToken(sym.ASC); }
					case -54:
						break;
					case 54:
						{ return nextToken(sym.AVG); }
					case -55:
						break;
					case 55:
						{ return nextToken(sym.NOT); }
					case -56:
						break;
					case 56:
						{ return nextToken(sym.SET); }
					case -57:
						break;
					case 57:
						{ return nextToken(sym.SUM); }
					case -58:
						break;
					case 58:
						{ return nextToken(sym.INT); }
					case -59:
						break;
					case 59:
						{ return nextToken(sym.MAX); }
					case -60:
						break;
					case 60:
						{ return nextToken(sym.MIN); }
					case -61:
						break;
					case 61:
						{ return nextToken(sym.KEY); }
					case -62:
						break;
					case 62:
						{ return nextToken(sym.USE); }
					case -63:
						break;
					case 63:
						{ return nextToken(sym.FLOATVALUE,Double.valueOf((yytext()))); }
					case -64:
						break;
					case 64:
						{ return nextToken(sym.AUTO); }
					case -65:
						break;
					case 65:
						{ return nextToken(sym.LIKE); }
					case -66:
						break;
					case 66:
						{ return nextToken(sym.TRUE); }
					case -67:
						break;
					case 67:
						{ return nextToken(sym.DESC); }
					case -68:
						break;
					case 68:
						{ return nextToken(sym.DROP); }
					case -69:
						break;
					case 69:
						{ return nextToken(sym.NULL); }
					case -70:
						break;
					case 70:
						{ return nextToken(sym.CHAR); }
					case -71:
						break;
					case 71:
						{ return nextToken(sym.INTO); }
					case -72:
						break;
					case 72:
						{ return nextToken(sym.FROM); }
					case -73:
						break;
					case 73:
						{ return nextToken(sym.VIEW); }
					case -74:
						break;
					case 74:
						{return nextToken(sym.WITH);}
					case -75:
						break;
					case 75:
						{ return nextToken(sym.AUTO); }
					case -76:
						break;
					case 76:
						{ return nextToken(sym.LIKE); }
					case -77:
						break;
					case 77:
						{ return nextToken(sym.TRUE); }
					case -78:
						break;
					case 78:
						{ return nextToken(sym.DESC); }
					case -79:
						break;
					case 79:
						{ return nextToken(sym.DROP); }
					case -80:
						break;
					case 80:
						{ return nextToken(sym.NULL); }
					case -81:
						break;
					case 81:
						{ return nextToken(sym.CHAR); }
					case -82:
						break;
					case 82:
						{ return nextToken(sym.INTO); }
					case -83:
						break;
					case 83:
						{ return nextToken(sym.FROM); }
					case -84:
						break;
					case 84:
						{ return nextToken(sym.VIEW); }
					case -85:
						break;
					case 85:
						{return nextToken(sym.WITH);}
					case -86:
						break;
					case 86:
						{ return nextToken(sym.ALTER); }
					case -87:
						break;
					case 87:
						{ return nextToken(sym.TABLE); }
					case -88:
						break;
					case 88:
						{ return nextToken(sym.COUNT); }
					case -89:
						break;
					case 89:
						{ return nextToken(sym.CHECK); }
					case -90:
						break;
					case 90:
						{ return nextToken(sym.ORDER); }
					case -91:
						break;
					case 91:
						{ return nextToken(sym.INDEX); }
					case -92:
						break;
					case 92:
						{ return nextToken(sym.FALSE); }
					case -93:
						break;
					case 93:
						{ return nextToken(sym.FLOAT); }
					case -94:
						break;
					case 94:
						{ return nextToken(sym.UNION); }
					case -95:
						break;
					case 95:
						{ return nextToken(sym.WHERE); }
					case -96:
						break;
					case 96:
						{return nextToken(sym.GRANT);}
					case -97:
						break;
					case 97:
						{ return nextToken(sym.GROUP); }
					case -98:
						break;
					case 98:
						{ return nextToken(sym.ALTER); }
					case -99:
						break;
					case 99:
						{ return nextToken(sym.TABLE); }
					case -100:
						break;
					case 100:
						{ return nextToken(sym.COUNT); }
					case -101:
						break;
					case 101:
						{ return nextToken(sym.CHECK); }
					case -102:
						break;
					case 102:
						{ return nextToken(sym.ORDER); }
					case -103:
						break;
					case 103:
						{ return nextToken(sym.INDEX); }
					case -104:
						break;
					case 104:
						{ return nextToken(sym.FALSE); }
					case -105:
						break;
					case 105:
						{ return nextToken(sym.FLOAT); }
					case -106:
						break;
					case 106:
						{ return nextToken(sym.UNION); }
					case -107:
						break;
					case 107:
						{ return nextToken(sym.WHERE); }
					case -108:
						break;
					case 108:
						{return nextToken(sym.GRANT);}
					case -109:
						break;
					case 109:
						{ return nextToken(sym.GROUP); }
					case -110:
						break;
					case 110:
						{ return nextToken(sym.ESCAPE); }
					case -111:
						break;
					case 111:
						{ return nextToken(sym.EXISTS); }
					case -112:
						break;
					case 112:
						{ return nextToken(sym.DELETE); }
					case -113:
						break;
					case 113:
						{ return nextToken(sym.SELECT); }
					case -114:
						break;
					case 114:
						{ return nextToken(sym.CREATE); }
					case -115:
						break;
					case 115:
						{return nextToken(sym.OPTION);}
					case -116:
						break;
					case 116:
						{ return nextToken(sym.INSERT); }
					case -117:
						break;
					case 117:
						{ return nextToken(sym.UPDATE); }
					case -118:
						break;
					case 118:
						{ return nextToken(sym.VALUES); }
					case -119:
						break;
					case 119:
						{ return nextToken(sym.HAVING); }
					case -120:
						break;
					case 120:
						{ return nextToken(sym.ESCAPE); }
					case -121:
						break;
					case 121:
						{ return nextToken(sym.EXISTS); }
					case -122:
						break;
					case 122:
						{ return nextToken(sym.DELETE); }
					case -123:
						break;
					case 123:
						{ return nextToken(sym.SELECT); }
					case -124:
						break;
					case 124:
						{ return nextToken(sym.CREATE); }
					case -125:
						break;
					case 125:
						{return nextToken(sym.OPTION);}
					case -126:
						break;
					case 126:
						{ return nextToken(sym.INSERT); }
					case -127:
						break;
					case 127:
						{ return nextToken(sym.UPDATE); }
					case -128:
						break;
					case 128:
						{ return nextToken(sym.VALUES); }
					case -129:
						break;
					case 129:
						{ return nextToken(sym.HAVING); }
					case -130:
						break;
					case 130:
						{ return nextToken(sym.DEFAULT); }
					case -131:
						break;
					case 131:
						{ return nextToken(sym.BOOLEAN); }
					case -132:
						break;
					case 132:
						{ return nextToken(sym.PRIMARY); }
					case -133:
						break;
					case 133:
						{return nextToken(sym.FOREIGN);}
					case -134:
						break;
					case 134:
						{ return nextToken(sym.DEFAULT); }
					case -135:
						break;
					case 135:
						{ return nextToken(sym.BOOLEAN); }
					case -136:
						break;
					case 136:
						{ return nextToken(sym.PRIMARY); }
					case -137:
						break;
					case 137:
						{return nextToken(sym.FOREIGN);}
					case -138:
						break;
					case 138:
						{ return nextToken(sym.DATABASE); }
					case -139:
						break;
					case 139:
						{return nextToken(sym.DESCRIBE);}
					case -140:
						break;
					case 140:
						{ return nextToken(sym.DISTINCT); }
					case -141:
						break;
					case 141:
						{ return nextToken(sym.DATABASE); }
					case -142:
						break;
					case 142:
						{return nextToken(sym.DESCRIBE);}
					case -143:
						break;
					case 143:
						{ return nextToken(sym.DISTINCT); }
					case -144:
						break;
					case 144:
						{ return nextToken(sym.INCREMENT); }
					case -145:
						break;
					case 145:
						{ return nextToken(sym.INCREMENT); }
					case -146:
						break;
					case 146:
						{return nextToken(sym.REFERENCES);}
					case -147:
						break;
					case 147:
						{return nextToken(sym.REFERENCES);}
					case -148:
						break;
					case 148:
						{ string.append( yytext() ); }
					case -149:
						break;
					case 149:
						{ yybegin(YYINITIAL); 
                                 return nextToken(sym.STRING,string.toString());}
					case -150:
						break;
					case 150:
						{ string.append('\\'); }
					case -151:
						break;
					case 151:
						{ string.append('\t'); }
					case -152:
						break;
					case 152:
						{ string.append('\r'); }
					case -153:
						break;
					case 153:
						{ string.append('\n'); }
					case -154:
						break;
					case 154:
						{ string.append('\"'); }
					case -155:
						break;
					case 156:
						{ return nextToken(sym.NAME,yytext()); }
					case -156:
						break;
					case 158:
						{ return nextToken(sym.NAME,yytext()); }
					case -157:
						break;
					case 160:
						{ return nextToken(sym.NAME,yytext()); }
					case -158:
						break;
					case 161:
						{ return nextToken(sym.NAME,yytext()); }
					case -159:
						break;
					case 162:
						{ return nextToken(sym.NAME,yytext()); }
					case -160:
						break;
					case 163:
						{ return nextToken(sym.NAME,yytext()); }
					case -161:
						break;
					case 164:
						{ return nextToken(sym.NAME,yytext()); }
					case -162:
						break;
					case 165:
						{ return nextToken(sym.NAME,yytext()); }
					case -163:
						break;
					case 166:
						{ return nextToken(sym.NAME,yytext()); }
					case -164:
						break;
					case 167:
						{ return nextToken(sym.NAME,yytext()); }
					case -165:
						break;
					case 168:
						{ return nextToken(sym.NAME,yytext()); }
					case -166:
						break;
					case 169:
						{ return nextToken(sym.NAME,yytext()); }
					case -167:
						break;
					case 170:
						{ return nextToken(sym.NAME,yytext()); }
					case -168:
						break;
					case 171:
						{ return nextToken(sym.NAME,yytext()); }
					case -169:
						break;
					case 172:
						{ return nextToken(sym.NAME,yytext()); }
					case -170:
						break;
					case 173:
						{ return nextToken(sym.NAME,yytext()); }
					case -171:
						break;
					case 174:
						{ return nextToken(sym.NAME,yytext()); }
					case -172:
						break;
					case 175:
						{ return nextToken(sym.NAME,yytext()); }
					case -173:
						break;
					case 176:
						{ return nextToken(sym.NAME,yytext()); }
					case -174:
						break;
					case 177:
						{ return nextToken(sym.NAME,yytext()); }
					case -175:
						break;
					case 178:
						{ return nextToken(sym.NAME,yytext()); }
					case -176:
						break;
					case 179:
						{ return nextToken(sym.NAME,yytext()); }
					case -177:
						break;
					case 180:
						{ return nextToken(sym.NAME,yytext()); }
					case -178:
						break;
					case 181:
						{ return nextToken(sym.NAME,yytext()); }
					case -179:
						break;
					case 182:
						{ return nextToken(sym.NAME,yytext()); }
					case -180:
						break;
					case 183:
						{ return nextToken(sym.NAME,yytext()); }
					case -181:
						break;
					case 184:
						{ return nextToken(sym.NAME,yytext()); }
					case -182:
						break;
					case 185:
						{ return nextToken(sym.NAME,yytext()); }
					case -183:
						break;
					case 186:
						{ return nextToken(sym.NAME,yytext()); }
					case -184:
						break;
					case 187:
						{ return nextToken(sym.NAME,yytext()); }
					case -185:
						break;
					case 188:
						{ return nextToken(sym.NAME,yytext()); }
					case -186:
						break;
					case 189:
						{ return nextToken(sym.NAME,yytext()); }
					case -187:
						break;
					case 190:
						{ return nextToken(sym.NAME,yytext()); }
					case -188:
						break;
					case 191:
						{ return nextToken(sym.NAME,yytext()); }
					case -189:
						break;
					case 192:
						{ return nextToken(sym.NAME,yytext()); }
					case -190:
						break;
					case 193:
						{ return nextToken(sym.NAME,yytext()); }
					case -191:
						break;
					case 194:
						{ return nextToken(sym.NAME,yytext()); }
					case -192:
						break;
					case 195:
						{ return nextToken(sym.NAME,yytext()); }
					case -193:
						break;
					case 196:
						{ return nextToken(sym.NAME,yytext()); }
					case -194:
						break;
					case 197:
						{ return nextToken(sym.NAME,yytext()); }
					case -195:
						break;
					case 198:
						{ return nextToken(sym.NAME,yytext()); }
					case -196:
						break;
					case 199:
						{ return nextToken(sym.NAME,yytext()); }
					case -197:
						break;
					case 200:
						{ return nextToken(sym.NAME,yytext()); }
					case -198:
						break;
					case 201:
						{ return nextToken(sym.NAME,yytext()); }
					case -199:
						break;
					case 202:
						{ return nextToken(sym.NAME,yytext()); }
					case -200:
						break;
					case 203:
						{ return nextToken(sym.NAME,yytext()); }
					case -201:
						break;
					case 204:
						{ return nextToken(sym.NAME,yytext()); }
					case -202:
						break;
					case 205:
						{ return nextToken(sym.NAME,yytext()); }
					case -203:
						break;
					case 206:
						{ return nextToken(sym.NAME,yytext()); }
					case -204:
						break;
					case 207:
						{ return nextToken(sym.NAME,yytext()); }
					case -205:
						break;
					case 208:
						{ return nextToken(sym.NAME,yytext()); }
					case -206:
						break;
					case 209:
						{ return nextToken(sym.NAME,yytext()); }
					case -207:
						break;
					case 210:
						{ return nextToken(sym.NAME,yytext()); }
					case -208:
						break;
					case 211:
						{ return nextToken(sym.NAME,yytext()); }
					case -209:
						break;
					case 212:
						{ return nextToken(sym.NAME,yytext()); }
					case -210:
						break;
					case 213:
						{ return nextToken(sym.NAME,yytext()); }
					case -211:
						break;
					case 214:
						{ return nextToken(sym.NAME,yytext()); }
					case -212:
						break;
					case 215:
						{ return nextToken(sym.NAME,yytext()); }
					case -213:
						break;
					case 216:
						{ return nextToken(sym.NAME,yytext()); }
					case -214:
						break;
					case 217:
						{ return nextToken(sym.NAME,yytext()); }
					case -215:
						break;
					case 218:
						{ return nextToken(sym.NAME,yytext()); }
					case -216:
						break;
					case 219:
						{ return nextToken(sym.NAME,yytext()); }
					case -217:
						break;
					case 220:
						{ return nextToken(sym.NAME,yytext()); }
					case -218:
						break;
					case 221:
						{ return nextToken(sym.NAME,yytext()); }
					case -219:
						break;
					case 222:
						{ return nextToken(sym.NAME,yytext()); }
					case -220:
						break;
					case 223:
						{ return nextToken(sym.NAME,yytext()); }
					case -221:
						break;
					case 224:
						{ return nextToken(sym.NAME,yytext()); }
					case -222:
						break;
					case 225:
						{ return nextToken(sym.NAME,yytext()); }
					case -223:
						break;
					case 226:
						{ return nextToken(sym.NAME,yytext()); }
					case -224:
						break;
					case 227:
						{ return nextToken(sym.NAME,yytext()); }
					case -225:
						break;
					case 228:
						{ return nextToken(sym.NAME,yytext()); }
					case -226:
						break;
					case 229:
						{ return nextToken(sym.NAME,yytext()); }
					case -227:
						break;
					case 230:
						{ return nextToken(sym.NAME,yytext()); }
					case -228:
						break;
					case 231:
						{ return nextToken(sym.NAME,yytext()); }
					case -229:
						break;
					case 232:
						{ return nextToken(sym.NAME,yytext()); }
					case -230:
						break;
					case 233:
						{ return nextToken(sym.NAME,yytext()); }
					case -231:
						break;
					case 234:
						{ return nextToken(sym.NAME,yytext()); }
					case -232:
						break;
					case 235:
						{ return nextToken(sym.NAME,yytext()); }
					case -233:
						break;
					case 236:
						{ return nextToken(sym.NAME,yytext()); }
					case -234:
						break;
					case 237:
						{ return nextToken(sym.NAME,yytext()); }
					case -235:
						break;
					case 238:
						{ return nextToken(sym.NAME,yytext()); }
					case -236:
						break;
					case 239:
						{ return nextToken(sym.NAME,yytext()); }
					case -237:
						break;
					case 240:
						{ return nextToken(sym.NAME,yytext()); }
					case -238:
						break;
					case 241:
						{ return nextToken(sym.NAME,yytext()); }
					case -239:
						break;
					case 242:
						{ return nextToken(sym.NAME,yytext()); }
					case -240:
						break;
					case 243:
						{ return nextToken(sym.NAME,yytext()); }
					case -241:
						break;
					case 244:
						{ return nextToken(sym.NAME,yytext()); }
					case -242:
						break;
					case 245:
						{ return nextToken(sym.NAME,yytext()); }
					case -243:
						break;
					case 246:
						{ return nextToken(sym.NAME,yytext()); }
					case -244:
						break;
					case 247:
						{ return nextToken(sym.NAME,yytext()); }
					case -245:
						break;
					case 248:
						{ return nextToken(sym.NAME,yytext()); }
					case -246:
						break;
					case 249:
						{ return nextToken(sym.NAME,yytext()); }
					case -247:
						break;
					case 250:
						{ return nextToken(sym.NAME,yytext()); }
					case -248:
						break;
					case 251:
						{ return nextToken(sym.NAME,yytext()); }
					case -249:
						break;
					case 252:
						{ return nextToken(sym.NAME,yytext()); }
					case -250:
						break;
					case 253:
						{ return nextToken(sym.NAME,yytext()); }
					case -251:
						break;
					case 254:
						{ return nextToken(sym.NAME,yytext()); }
					case -252:
						break;
					case 255:
						{ return nextToken(sym.NAME,yytext()); }
					case -253:
						break;
					case 256:
						{ return nextToken(sym.NAME,yytext()); }
					case -254:
						break;
					case 257:
						{ return nextToken(sym.NAME,yytext()); }
					case -255:
						break;
					case 258:
						{ return nextToken(sym.NAME,yytext()); }
					case -256:
						break;
					case 259:
						{ return nextToken(sym.NAME,yytext()); }
					case -257:
						break;
					case 260:
						{ return nextToken(sym.NAME,yytext()); }
					case -258:
						break;
					case 261:
						{ return nextToken(sym.NAME,yytext()); }
					case -259:
						break;
					case 262:
						{ return nextToken(sym.NAME,yytext()); }
					case -260:
						break;
					case 263:
						{ return nextToken(sym.NAME,yytext()); }
					case -261:
						break;
					case 264:
						{ return nextToken(sym.NAME,yytext()); }
					case -262:
						break;
					case 265:
						{ return nextToken(sym.NAME,yytext()); }
					case -263:
						break;
					case 266:
						{ return nextToken(sym.NAME,yytext()); }
					case -264:
						break;
					case 267:
						{ return nextToken(sym.NAME,yytext()); }
					case -265:
						break;
					case 268:
						{ return nextToken(sym.NAME,yytext()); }
					case -266:
						break;
					case 269:
						{ return nextToken(sym.NAME,yytext()); }
					case -267:
						break;
					case 270:
						{ return nextToken(sym.NAME,yytext()); }
					case -268:
						break;
					case 271:
						{ return nextToken(sym.NAME,yytext()); }
					case -269:
						break;
					case 272:
						{ return nextToken(sym.NAME,yytext()); }
					case -270:
						break;
					case 273:
						{ return nextToken(sym.NAME,yytext()); }
					case -271:
						break;
					case 274:
						{ return nextToken(sym.NAME,yytext()); }
					case -272:
						break;
					case 275:
						{ return nextToken(sym.NAME,yytext()); }
					case -273:
						break;
					case 276:
						{ return nextToken(sym.NAME,yytext()); }
					case -274:
						break;
					case 277:
						{ return nextToken(sym.NAME,yytext()); }
					case -275:
						break;
					case 278:
						{ return nextToken(sym.NAME,yytext()); }
					case -276:
						break;
					case 279:
						{ return nextToken(sym.NAME,yytext()); }
					case -277:
						break;
					case 280:
						{ return nextToken(sym.NAME,yytext()); }
					case -278:
						break;
					case 281:
						{ return nextToken(sym.NAME,yytext()); }
					case -279:
						break;
					case 282:
						{ return nextToken(sym.NAME,yytext()); }
					case -280:
						break;
					case 283:
						{ return nextToken(sym.NAME,yytext()); }
					case -281:
						break;
					case 284:
						{ return nextToken(sym.NAME,yytext()); }
					case -282:
						break;
					case 285:
						{ return nextToken(sym.NAME,yytext()); }
					case -283:
						break;
					case 286:
						{ return nextToken(sym.NAME,yytext()); }
					case -284:
						break;
					case 287:
						{ return nextToken(sym.NAME,yytext()); }
					case -285:
						break;
					case 288:
						{ return nextToken(sym.NAME,yytext()); }
					case -286:
						break;
					case 289:
						{ return nextToken(sym.NAME,yytext()); }
					case -287:
						break;
					case 290:
						{ return nextToken(sym.NAME,yytext()); }
					case -288:
						break;
					case 291:
						{ return nextToken(sym.NAME,yytext()); }
					case -289:
						break;
					case 292:
						{ return nextToken(sym.NAME,yytext()); }
					case -290:
						break;
					case 293:
						{ return nextToken(sym.NAME,yytext()); }
					case -291:
						break;
					case 294:
						{ return nextToken(sym.NAME,yytext()); }
					case -292:
						break;
					case 295:
						{ return nextToken(sym.NAME,yytext()); }
					case -293:
						break;
					case 296:
						{ return nextToken(sym.NAME,yytext()); }
					case -294:
						break;
					case 297:
						{ return nextToken(sym.NAME,yytext()); }
					case -295:
						break;
					case 298:
						{ return nextToken(sym.NAME,yytext()); }
					case -296:
						break;
					case 299:
						{ return nextToken(sym.NAME,yytext()); }
					case -297:
						break;
					case 300:
						{ return nextToken(sym.NAME,yytext()); }
					case -298:
						break;
					case 301:
						{ return nextToken(sym.NAME,yytext()); }
					case -299:
						break;
					case 302:
						{ return nextToken(sym.NAME,yytext()); }
					case -300:
						break;
					case 303:
						{ return nextToken(sym.NAME,yytext()); }
					case -301:
						break;
					case 304:
						{ return nextToken(sym.NAME,yytext()); }
					case -302:
						break;
					case 305:
						{ return nextToken(sym.NAME,yytext()); }
					case -303:
						break;
					case 306:
						{ return nextToken(sym.NAME,yytext()); }
					case -304:
						break;
					case 307:
						{ return nextToken(sym.NAME,yytext()); }
					case -305:
						break;
					case 308:
						{ return nextToken(sym.NAME,yytext()); }
					case -306:
						break;
					case 309:
						{ return nextToken(sym.NAME,yytext()); }
					case -307:
						break;
					case 310:
						{ return nextToken(sym.NAME,yytext()); }
					case -308:
						break;
					case 311:
						{ return nextToken(sym.NAME,yytext()); }
					case -309:
						break;
					case 312:
						{ return nextToken(sym.NAME,yytext()); }
					case -310:
						break;
					case 313:
						{ return nextToken(sym.NAME,yytext()); }
					case -311:
						break;
					case 314:
						{ return nextToken(sym.NAME,yytext()); }
					case -312:
						break;
					case 315:
						{ return nextToken(sym.NAME,yytext()); }
					case -313:
						break;
					case 316:
						{ return nextToken(sym.NAME,yytext()); }
					case -314:
						break;
					case 317:
						{ return nextToken(sym.NAME,yytext()); }
					case -315:
						break;
					case 318:
						{ return nextToken(sym.NAME,yytext()); }
					case -316:
						break;
					case 319:
						{ return nextToken(sym.NAME,yytext()); }
					case -317:
						break;
					case 320:
						{ return nextToken(sym.NAME,yytext()); }
					case -318:
						break;
					case 321:
						{ return nextToken(sym.NAME,yytext()); }
					case -319:
						break;
					case 322:
						{ return nextToken(sym.NAME,yytext()); }
					case -320:
						break;
					case 323:
						{ return nextToken(sym.NAME,yytext()); }
					case -321:
						break;
					case 324:
						{ return nextToken(sym.NAME,yytext()); }
					case -322:
						break;
					case 325:
						{ return nextToken(sym.NAME,yytext()); }
					case -323:
						break;
					case 326:
						{ return nextToken(sym.NAME,yytext()); }
					case -324:
						break;
					case 327:
						{ return nextToken(sym.NAME,yytext()); }
					case -325:
						break;
					case 328:
						{ return nextToken(sym.NAME,yytext()); }
					case -326:
						break;
					case 329:
						{ return nextToken(sym.NAME,yytext()); }
					case -327:
						break;
					case 330:
						{ return nextToken(sym.NAME,yytext()); }
					case -328:
						break;
					case 331:
						{ return nextToken(sym.NAME,yytext()); }
					case -329:
						break;
					case 332:
						{ return nextToken(sym.NAME,yytext()); }
					case -330:
						break;
					case 333:
						{ return nextToken(sym.NAME,yytext()); }
					case -331:
						break;
					case 334:
						{ return nextToken(sym.NAME,yytext()); }
					case -332:
						break;
					case 335:
						{ return nextToken(sym.NAME,yytext()); }
					case -333:
						break;
					case 336:
						{ return nextToken(sym.NAME,yytext()); }
					case -334:
						break;
					case 337:
						{ return nextToken(sym.NAME,yytext()); }
					case -335:
						break;
					case 338:
						{ return nextToken(sym.NAME,yytext()); }
					case -336:
						break;
					case 339:
						{ return nextToken(sym.NAME,yytext()); }
					case -337:
						break;
					case 340:
						{ return nextToken(sym.NAME,yytext()); }
					case -338:
						break;
					case 341:
						{ return nextToken(sym.NAME,yytext()); }
					case -339:
						break;
					case 342:
						{ return nextToken(sym.NAME,yytext()); }
					case -340:
						break;
					case 343:
						{ return nextToken(sym.NAME,yytext()); }
					case -341:
						break;
					case 344:
						{ return nextToken(sym.NAME,yytext()); }
					case -342:
						break;
					case 345:
						{ return nextToken(sym.NAME,yytext()); }
					case -343:
						break;
					case 346:
						{ return nextToken(sym.NAME,yytext()); }
					case -344:
						break;
					case 347:
						{ return nextToken(sym.NAME,yytext()); }
					case -345:
						break;
					case 348:
						{ return nextToken(sym.NAME,yytext()); }
					case -346:
						break;
					case 349:
						{ return nextToken(sym.NAME,yytext()); }
					case -347:
						break;
					case 350:
						{ return nextToken(sym.NAME,yytext()); }
					case -348:
						break;
					case 351:
						{ return nextToken(sym.NAME,yytext()); }
					case -349:
						break;
					case 352:
						{ return nextToken(sym.NAME,yytext()); }
					case -350:
						break;
					case 353:
						{ return nextToken(sym.NAME,yytext()); }
					case -351:
						break;
					case 354:
						{ return nextToken(sym.NAME,yytext()); }
					case -352:
						break;
					case 355:
						{ return nextToken(sym.NAME,yytext()); }
					case -353:
						break;
					case 356:
						{ return nextToken(sym.NAME,yytext()); }
					case -354:
						break;
					case 357:
						{ return nextToken(sym.NAME,yytext()); }
					case -355:
						break;
					case 358:
						{ return nextToken(sym.NAME,yytext()); }
					case -356:
						break;
					case 359:
						{ return nextToken(sym.NAME,yytext()); }
					case -357:
						break;
					case 360:
						{ return nextToken(sym.NAME,yytext()); }
					case -358:
						break;
					case 361:
						{ return nextToken(sym.NAME,yytext()); }
					case -359:
						break;
					case 362:
						{ return nextToken(sym.NAME,yytext()); }
					case -360:
						break;
					case 363:
						{ return nextToken(sym.NAME,yytext()); }
					case -361:
						break;
					case 364:
						{ return nextToken(sym.NAME,yytext()); }
					case -362:
						break;
					case 365:
						{ return nextToken(sym.NAME,yytext()); }
					case -363:
						break;
					case 366:
						{ return nextToken(sym.NAME,yytext()); }
					case -364:
						break;
					case 367:
						{ return nextToken(sym.NAME,yytext()); }
					case -365:
						break;
					case 368:
						{ return nextToken(sym.NAME,yytext()); }
					case -366:
						break;
					case 369:
						{ return nextToken(sym.NAME,yytext()); }
					case -367:
						break;
					case 370:
						{ return nextToken(sym.NAME,yytext()); }
					case -368:
						break;
					case 371:
						{ return nextToken(sym.NAME,yytext()); }
					case -369:
						break;
					case 372:
						{ return nextToken(sym.NAME,yytext()); }
					case -370:
						break;
					case 373:
						{ return nextToken(sym.NAME,yytext()); }
					case -371:
						break;
					case 374:
						{ return nextToken(sym.NAME,yytext()); }
					case -372:
						break;
					case 375:
						{ return nextToken(sym.NAME,yytext()); }
					case -373:
						break;
					case 376:
						{ return nextToken(sym.NAME,yytext()); }
					case -374:
						break;
					case 377:
						{ return nextToken(sym.NAME,yytext()); }
					case -375:
						break;
					case 378:
						{ return nextToken(sym.NAME,yytext()); }
					case -376:
						break;
					case 379:
						{ return nextToken(sym.NAME,yytext()); }
					case -377:
						break;
					case 380:
						{ return nextToken(sym.NAME,yytext()); }
					case -378:
						break;
					case 381:
						{ return nextToken(sym.NAME,yytext()); }
					case -379:
						break;
					case 382:
						{ return nextToken(sym.NAME,yytext()); }
					case -380:
						break;
					case 383:
						{ return nextToken(sym.NAME,yytext()); }
					case -381:
						break;
					case 384:
						{ return nextToken(sym.NAME,yytext()); }
					case -382:
						break;
					case 385:
						{ return nextToken(sym.NAME,yytext()); }
					case -383:
						break;
					case 386:
						{ return nextToken(sym.NAME,yytext()); }
					case -384:
						break;
					case 387:
						{ return nextToken(sym.NAME,yytext()); }
					case -385:
						break;
					case 388:
						{ return nextToken(sym.NAME,yytext()); }
					case -386:
						break;
					case 389:
						{ return nextToken(sym.NAME,yytext()); }
					case -387:
						break;
					case 390:
						{ return nextToken(sym.NAME,yytext()); }
					case -388:
						break;
					case 391:
						{ return nextToken(sym.NAME,yytext()); }
					case -389:
						break;
					case 392:
						{ return nextToken(sym.NAME,yytext()); }
					case -390:
						break;
					case 393:
						{ return nextToken(sym.NAME,yytext()); }
					case -391:
						break;
					case 394:
						{ return nextToken(sym.NAME,yytext()); }
					case -392:
						break;
					case 395:
						{ return nextToken(sym.NAME,yytext()); }
					case -393:
						break;
					case 396:
						{ return nextToken(sym.NAME,yytext()); }
					case -394:
						break;
					case 397:
						{ return nextToken(sym.NAME,yytext()); }
					case -395:
						break;
					case 398:
						{ return nextToken(sym.NAME,yytext()); }
					case -396:
						break;
					case 399:
						{ return nextToken(sym.NAME,yytext()); }
					case -397:
						break;
					case 400:
						{ return nextToken(sym.NAME,yytext()); }
					case -398:
						break;
					case 401:
						{ return nextToken(sym.NAME,yytext()); }
					case -399:
						break;
					case 402:
						{ return nextToken(sym.NAME,yytext()); }
					case -400:
						break;
					case 403:
						{ return nextToken(sym.NAME,yytext()); }
					case -401:
						break;
					case 404:
						{ return nextToken(sym.NAME,yytext()); }
					case -402:
						break;
					case 405:
						{ return nextToken(sym.NAME,yytext()); }
					case -403:
						break;
					case 406:
						{ return nextToken(sym.NAME,yytext()); }
					case -404:
						break;
					case 407:
						{ return nextToken(sym.NAME,yytext()); }
					case -405:
						break;
					case 408:
						{ return nextToken(sym.NAME,yytext()); }
					case -406:
						break;
					case 409:
						{ return nextToken(sym.NAME,yytext()); }
					case -407:
						break;
					case 410:
						{ return nextToken(sym.NAME,yytext()); }
					case -408:
						break;
					case 411:
						{ return nextToken(sym.NAME,yytext()); }
					case -409:
						break;
					case 412:
						{ return nextToken(sym.NAME,yytext()); }
					case -410:
						break;
					case 413:
						{ return nextToken(sym.NAME,yytext()); }
					case -411:
						break;
					case 414:
						{ return nextToken(sym.NAME,yytext()); }
					case -412:
						break;
					case 415:
						{ return nextToken(sym.NAME,yytext()); }
					case -413:
						break;
					case 416:
						{ return nextToken(sym.NAME,yytext()); }
					case -414:
						break;
					case 417:
						{ return nextToken(sym.NAME,yytext()); }
					case -415:
						break;
					case 418:
						{ return nextToken(sym.NAME,yytext()); }
					case -416:
						break;
					case 419:
						{ return nextToken(sym.NAME,yytext()); }
					case -417:
						break;
					case 420:
						{ return nextToken(sym.NAME,yytext()); }
					case -418:
						break;
					case 421:
						{ return nextToken(sym.NAME,yytext()); }
					case -419:
						break;
					case 422:
						{ return nextToken(sym.NAME,yytext()); }
					case -420:
						break;
					case 423:
						{ return nextToken(sym.NAME,yytext()); }
					case -421:
						break;
					case 424:
						{ return nextToken(sym.NAME,yytext()); }
					case -422:
						break;
					case 425:
						{ return nextToken(sym.NAME,yytext()); }
					case -423:
						break;
					case 426:
						{ return nextToken(sym.NAME,yytext()); }
					case -424:
						break;
					case 427:
						{ return nextToken(sym.NAME,yytext()); }
					case -425:
						break;
					case 428:
						{ return nextToken(sym.NAME,yytext()); }
					case -426:
						break;
					case 429:
						{ return nextToken(sym.NAME,yytext()); }
					case -427:
						break;
					case 430:
						{ return nextToken(sym.NAME,yytext()); }
					case -428:
						break;
					case 431:
						{ return nextToken(sym.NAME,yytext()); }
					case -429:
						break;
					case 432:
						{ return nextToken(sym.NAME,yytext()); }
					case -430:
						break;
					case 433:
						{ return nextToken(sym.NAME,yytext()); }
					case -431:
						break;
					case 434:
						{ return nextToken(sym.NAME,yytext()); }
					case -432:
						break;
					case 435:
						{ return nextToken(sym.NAME,yytext()); }
					case -433:
						break;
					case 436:
						{ return nextToken(sym.NAME,yytext()); }
					case -434:
						break;
					case 437:
						{ return nextToken(sym.NAME,yytext()); }
					case -435:
						break;
					case 438:
						{ return nextToken(sym.NAME,yytext()); }
					case -436:
						break;
					case 439:
						{ return nextToken(sym.NAME,yytext()); }
					case -437:
						break;
					case 440:
						{ return nextToken(sym.NAME,yytext()); }
					case -438:
						break;
					case 441:
						{ return nextToken(sym.NAME,yytext()); }
					case -439:
						break;
					case 442:
						{ return nextToken(sym.NAME,yytext()); }
					case -440:
						break;
					case 443:
						{ return nextToken(sym.NAME,yytext()); }
					case -441:
						break;
					case 444:
						{ return nextToken(sym.NAME,yytext()); }
					case -442:
						break;
					case 445:
						{ return nextToken(sym.NAME,yytext()); }
					case -443:
						break;
					case 446:
						{ return nextToken(sym.NAME,yytext()); }
					case -444:
						break;
					case 447:
						{ return nextToken(sym.NAME,yytext()); }
					case -445:
						break;
					case 448:
						{ return nextToken(sym.NAME,yytext()); }
					case -446:
						break;
					case 449:
						{ return nextToken(sym.NAME,yytext()); }
					case -447:
						break;
					case 450:
						{ return nextToken(sym.NAME,yytext()); }
					case -448:
						break;
					case 451:
						{ return nextToken(sym.NAME,yytext()); }
					case -449:
						break;
					case 452:
						{ return nextToken(sym.NAME,yytext()); }
					case -450:
						break;
					case 453:
						{ return nextToken(sym.NAME,yytext()); }
					case -451:
						break;
					case 454:
						{ return nextToken(sym.NAME,yytext()); }
					case -452:
						break;
					case 455:
						{ return nextToken(sym.NAME,yytext()); }
					case -453:
						break;
					case 456:
						{ return nextToken(sym.NAME,yytext()); }
					case -454:
						break;
					case 457:
						{ return nextToken(sym.NAME,yytext()); }
					case -455:
						break;
					case 458:
						{ return nextToken(sym.NAME,yytext()); }
					case -456:
						break;
					case 459:
						{ return nextToken(sym.NAME,yytext()); }
					case -457:
						break;
					case 460:
						{ return nextToken(sym.NAME,yytext()); }
					case -458:
						break;
					case 461:
						{ return nextToken(sym.NAME,yytext()); }
					case -459:
						break;
					case 462:
						{ return nextToken(sym.NAME,yytext()); }
					case -460:
						break;
					case 463:
						{ return nextToken(sym.NAME,yytext()); }
					case -461:
						break;
					case 464:
						{ return nextToken(sym.NAME,yytext()); }
					case -462:
						break;
					case 465:
						{ return nextToken(sym.NAME,yytext()); }
					case -463:
						break;
					case 466:
						{ return nextToken(sym.NAME,yytext()); }
					case -464:
						break;
					case 467:
						{ return nextToken(sym.NAME,yytext()); }
					case -465:
						break;
					case 468:
						{ return nextToken(sym.NAME,yytext()); }
					case -466:
						break;
					case 469:
						{ return nextToken(sym.NAME,yytext()); }
					case -467:
						break;
					case 470:
						{ return nextToken(sym.NAME,yytext()); }
					case -468:
						break;
					case 471:
						{ return nextToken(sym.NAME,yytext()); }
					case -469:
						break;
					case 472:
						{ return nextToken(sym.NAME,yytext()); }
					case -470:
						break;
					case 473:
						{ return nextToken(sym.NAME,yytext()); }
					case -471:
						break;
					case 474:
						{ return nextToken(sym.NAME,yytext()); }
					case -472:
						break;
					case 475:
						{ return nextToken(sym.NAME,yytext()); }
					case -473:
						break;
					case 476:
						{ return nextToken(sym.NAME,yytext()); }
					case -474:
						break;
					case 477:
						{ return nextToken(sym.NAME,yytext()); }
					case -475:
						break;
					case 478:
						{ return nextToken(sym.NAME,yytext()); }
					case -476:
						break;
					case 479:
						{ return nextToken(sym.NAME,yytext()); }
					case -477:
						break;
					case 480:
						{ return nextToken(sym.NAME,yytext()); }
					case -478:
						break;
					case 481:
						{ return nextToken(sym.NAME,yytext()); }
					case -479:
						break;
					case 482:
						{ return nextToken(sym.NAME,yytext()); }
					case -480:
						break;
					case 483:
						{ return nextToken(sym.NAME,yytext()); }
					case -481:
						break;
					case 484:
						{ return nextToken(sym.NAME,yytext()); }
					case -482:
						break;
					case 485:
						{ return nextToken(sym.NAME,yytext()); }
					case -483:
						break;
					case 486:
						{ return nextToken(sym.NAME,yytext()); }
					case -484:
						break;
					case 487:
						{ return nextToken(sym.NAME,yytext()); }
					case -485:
						break;
					case 488:
						{ return nextToken(sym.NAME,yytext()); }
					case -486:
						break;
					case 489:
						{ return nextToken(sym.NAME,yytext()); }
					case -487:
						break;
					case 490:
						{ return nextToken(sym.NAME,yytext()); }
					case -488:
						break;
					case 491:
						{ return nextToken(sym.NAME,yytext()); }
					case -489:
						break;
					case 492:
						{ return nextToken(sym.NAME,yytext()); }
					case -490:
						break;
					case 493:
						{ return nextToken(sym.NAME,yytext()); }
					case -491:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
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
