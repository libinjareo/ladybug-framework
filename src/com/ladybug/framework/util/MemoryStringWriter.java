package com.ladybug.framework.util;

import java.io.IOException;
import java.io.Writer;

/**
 * 此String Writer能够写入大数据量数据。原始JDK中的StringWriter在每次缓存溢出的时候
 * 会双倍增加它的的缓存大小，这在小数据量的情况下，性能会保持的很好，但是在写入大数据量的时候 会造成很多问题。
 * 
 * @author james.li
 * 
 */
public class MemoryStringWriter extends Writer {

	private int cursor;
	private char[] buffer;
	private int maximumBufferIncrement;

	/**
	 * 创建一个新的character-stream writer,其临界区能够在写入的时候能够保持同步。
	 */
	public MemoryStringWriter() {
		this(4096);
	}

	/**
	 * 创建一个新的character-stream writer,其临界区能够在写入的时候能够保持同步。
	 */
	public MemoryStringWriter(final int bufferSize) {
		this(bufferSize, bufferSize * 4);
	}

	/**
	 * 创建一个新的character-stream writer,其临界区能够在写入的时候能够保持同步。
	 */
	public MemoryStringWriter(final int bufferSize,
			final int maximumBufferIncrement) {
		this.maximumBufferIncrement = maximumBufferIncrement;
		this.buffer = new char[bufferSize];
	}

	/**
	 * 写入字符数组
	 */
	@Override
	public synchronized void write(final char[] cbuf, final int off,
			final int len) throws IOException {
		if (len < 0) {
			throw new IllegalArgumentException();
		}
		if (off < 0) {
			throw new IndexOutOfBoundsException();
		}
		if (cbuf == null) {
			throw new NullPointerException();
		}

		if ((len + off) > cbuf.length) {
			throw new IndexOutOfBoundsException();
		}

		ensureSize(cursor + len);

		System.arraycopy(cbuf, off, this.buffer, cursor, len);

		cursor += len;
	}

	private void ensureSize(final int size) {
		if (this.buffer.length >= size) {
			return;
		}
		final int computedSize = (int) Math.min((this.buffer.length + 1) * 1.5,
				this.buffer.length + maximumBufferIncrement);
		final int newSize = Math.max(size, computedSize);
		final char[] newBuffer = new char[newSize];
		System.arraycopy(this.buffer, 0, newBuffer, 0, cursor);
		this.buffer = newBuffer;
	}

	@Override
	public void flush() throws IOException {

	}

	@Override
	public void close() throws IOException {

	}

	public int getCursor() {
		return cursor;
	}

	public String toString() {
		return new String(buffer, 0, cursor);
	}
}
