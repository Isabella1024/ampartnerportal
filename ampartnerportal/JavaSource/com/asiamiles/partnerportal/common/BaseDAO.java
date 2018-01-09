package com.asiamiles.partnerportal.common;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import com.cathaypacific.sqlframework.DatabaseTransactionException;
import com.cathaypacific.sqlframework.NullSQLType;
import com.cathaypacific.sqlframework.ResultProcessor;
import com.cathaypacific.sqlframework.SQLProcessor;
import com.cathaypacific.sqlframework.SQLTransaction;
/*
 * added by Steven 20140730 for billing log
 */
public class BaseDAO {
	protected static final SQLProcessor SQLPROCESSOR = SQLProcessor.getInstance("clsNARPortal",
			"ixClsNARPortal");

	/*
	 * Convert null value to NullSQLType;
	 */
	protected void addParamValue(List paramList, String s) {
		if (null != paramList) {
			if (s == null) {
				paramList.add(new NullSQLType(Types.VARCHAR));
			} else {
				paramList.add(s);
			}
		}
	}

	protected void addParamValue(List paramList, Date t) {
		if (null != paramList) {
			if (t == null) {
				paramList.add(new NullSQLType(Types.DATE));
			} else {
				paramList.add(new Timestamp(t.getTime()));
			}
		}
	}
	
	protected void addParamValue(List paramList, Integer num) {
		if (null != paramList) {
			if (null == num) {
				paramList.add(new NullSQLType(Types.INTEGER));
			} else {
				paramList.add(num);
			}
		}
	}

	protected void addParamValue(List paramList, int num) {
		if (null != paramList) {
			if (num == -1) {
				paramList.add(new NullSQLType(Types.INTEGER));
			} else {
				paramList.add(new Integer(num));
			}
		}
	}

	protected void addParamValue(List paramList, double num) {
		if (null != paramList) {
			if (num == -1) {
				paramList.add(new NullSQLType(Types.DOUBLE));
			} else {
				paramList.add(new Double(num));
			}
		}
	}

	protected int executeUpdate(SQLTransaction transaction, String sql, Object[] params)
			throws Exception {
		try {
			return transaction.executeUpdate(sql, params);
		} catch (Throwable e) {
			throw new Exception(e);
		}
	}
	
	protected Object[] executeQuery(SQLTransaction transaction,String sql, Object[] pStmntValues, ResultProcessor processor) throws DatabaseTransactionException{
		return transaction.executeQuery(sql, pStmntValues, processor);
	}
}
