package com.practice;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FastScanner {

	BufferedInputStream bis;
	BufferedOutputStream bos;

	final static byte ZERO = '0';// 48 or 0x30
	final static byte NINE = '9';
	final static byte SPACEBAR = ' '; // 32 or 0x20
	final static byte MINUS = '-'; // 45 or 0x2d

	final static char FLOAT_POINT = '.';

	final int INPUT_BUFFER_SIZE = 1024 * 1024 * 8;
	final int BUF_SIZE_INPUT = INPUT_BUFFER_SIZE;
	final int BUF_SIZE = 1024;
	byte[] byteBufInput = new byte[BUF_SIZE_INPUT];
	byte by = -1;
	int byteRead = -1;
	int bytePos = -1;

	byte[] byteBuf = new byte[BUF_SIZE];
	int totalBytes;

	public FastScanner(InputStream in, OutputStream os) {
		bis = new BufferedInputStream(in);
		bos = new BufferedOutputStream(os);
	}

	private byte nextByte() throws IOException {
		if (bytePos < 0 || bytePos >= byteRead) {
			byteRead = bis.read(byteBufInput);
			bytePos = 0;
		}
		return byteBufInput[bytePos++];
	}

	public void writeUnsignedInt(int num) throws IOException {
		int byteWriteOffset = byteBuf.length;
		if (num == 0) {
			byteBuf[--byteWriteOffset] = ZERO;
		} else {
			while (num > 0) {
				byteBuf[--byteWriteOffset] = (byte) ((num % 10) + ZERO);
				num /= 10;
			}
		}
		bos.write(byteBuf, byteWriteOffset, byteBuf.length - byteWriteOffset);
	}

	public void writeSpaceBar() throws IOException {
		byteBuf[0] = SPACEBAR;
		bos.write(byteBuf, 0, 1);
	}

	/*
	 * //TODO test Unix/Windows formats public void toNextLine() throws
	 * IOException{ while ((ch=nextChar())!='\n'); }
	 * 
	 * //[0-9.-] eE? public double nextDouble() throws IOException{ while
	 * (((ch=nextChar())<'0' || ch>'9') && ch!=FLOAT_POINT && ch!='-'); char[]
	 * tmp = new char[255]; int itmp = 0; tmp[itmp++]=(char)ch; while
	 * (((ch=nextChar())>='0' && ch<='9') || ch==FLOAT_POINT || ch=='-'){
	 * tmp[itmp++]=(char)ch; } return Double.parseDouble(new
	 * String(tmp,0,itmp)); }
	 */

	/**
	 * Returns next meaningful character as a byte.<br>
	 * 
	 * @return
	 * @throws IOException
	 */
	public byte nextChar() throws IOException {
		while ((by = nextByte()) <= 0x20)
			;
		return by;
	}

	/**
	 * Returns next meaningful character OR space as a byte.<br>
	 * 
	 * @return
	 * @throws IOException
	 */
	public byte nextCharOrSpacebar() throws IOException {
		while ((by = nextByte()) < 0x20)
			;
		return by;
	}

	/**
	 * Spacebar is included as separator char
	 * 
	 * @throws IOException
	 */
	private void readToken() throws IOException {
		readToken((byte) 0x21);
	}

	/**
	 * Reads line.
	 * 
	 * @return
	 * @throws IOException
	 */
	public String nextLine() throws IOException {
		readToken((byte) 0x20);
		return new String(byteBuf, 0, totalBytes);
	}

	/**
	 * Reads token. Spacebar is separator char.
	 * 
	 * @return
	 * @throws IOException
	 */
	public String nextToken() throws IOException {
		readToken((byte) 0x21);
		return new String(byteBuf, 0, totalBytes);
	}

	private void readToken(byte acceptFrom) throws IOException {
		totalBytes = 0;
		while ((by = nextByte()) < acceptFrom)
			;
		byteBuf[totalBytes++] = by;
		while ((by = nextByte()) >= acceptFrom) {
			byteBuf[totalBytes++] = by;
		}
	}

	public int nextInt() throws IOException {
		readToken();
		int num = 0, i = 0;
		boolean sign = false;
		if (byteBuf[i] == MINUS) {
			sign = true;
			i++;
		}
		for (; i < totalBytes; i++) {
			num *= 10;
			num += byteBuf[i] - ZERO;
		}
		return sign ? -num : num;
	}

	public long nextLong() throws IOException {
		readToken();
		long num = 0;
		int i = 0;
		boolean sign = false;
		if (byteBuf[i] == MINUS) {
			sign = true;
			i++;
		}
		for (; i < totalBytes; i++) {
			num *= 10;
			num += byteBuf[i] - ZERO;
		}
		return sign ? -num : num;
	}

}
