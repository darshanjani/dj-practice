package com.dj.config;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.exceptions.InvalidTypeException;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class BigIntegerCodec extends TypeCodec<BigInteger> {

	public BigIntegerCodec() {
		super(DataType.bigint(), BigInteger.class);
	}

	@Override
	public ByteBuffer serialize(BigInteger bigInteger, ProtocolVersion protocolVersion) throws InvalidTypeException {
		byte[] bytes = bigInteger.toByteArray();
		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
		byteBuffer.put(bytes);
		return byteBuffer;
	}

	@Override
	public BigInteger deserialize(ByteBuffer byteBuffer, ProtocolVersion protocolVersion) throws InvalidTypeException {
		byte[] bytes = Arrays.copyOf(byteBuffer.array(), byteBuffer.limit());
		return new BigInteger(bytes);
	}

	@Override
	public BigInteger parse(String s) throws InvalidTypeException {
		return BigInteger.valueOf(Long.parseLong(s));
	}

	@Override
	public String format(BigInteger bigInteger) throws InvalidTypeException {
		return String.valueOf(bigInteger.longValue());
	}
}
