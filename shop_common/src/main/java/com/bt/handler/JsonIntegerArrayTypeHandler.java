package com.bt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 86155
 */ /*
   <columnOverride column="ids" javaType="java.lang.Integer[]" typeHandler="JsonIntegerArrayTypeHandler"/>
 */
//指定java中的数据类型
@MappedTypes({Integer[].class})
//指定数据库中的类型
@MappedJdbcTypes({JdbcType.VARCHAR})
public class JsonIntegerArrayTypeHandler extends BaseTypeHandler<Integer[]> {
	private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Integer[] parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, toJson(parameter));
	}

	@Override
	public Integer[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return this.toObject(rs.getString(columnName));
	}

	@Override
	public Integer[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return this.toObject(rs.getString(columnIndex));
	}

	@Override
	public Integer[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return this.toObject(cs.getString(columnIndex));
	}

	private String toJson(Integer[] params) {
		try {
			return mapper.writeValueAsString(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "[]";
	}

	private Integer[] toObject(String content) {
		if (content != null && !content.isEmpty()) {
			try {
				return (Integer[]) mapper.readValue(content, Integer[].class);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			return null;
		}
	}
}