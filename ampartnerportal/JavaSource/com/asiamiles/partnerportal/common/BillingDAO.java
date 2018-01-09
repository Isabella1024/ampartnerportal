package com.asiamiles.partnerportal.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.asiamiles.partnerportal.domain.BillingForm;
import com.asiamiles.partnerportal.util.AppLogger;
import com.cathaypacific.sqlframework.DatabaseQueryException;
import com.cathaypacific.sqlframework.DatabaseTransactionException;
import com.cathaypacific.sqlframework.ResultProcessor;
import com.cathaypacific.sqlframework.SQLTransaction;
import com.cathaypacific.utility.Logger;

/*
 * added by Steven 20140730 for billing log
 */
public class BillingDAO extends BaseDAO {

	private static BillingDAO instance = new BillingDAO();

	private Logger logger = AppLogger.getAppLogger();

	public static BillingDAO getInstance() {
		return instance;
	}
	
	
	 /**
	 * Get establishment code and end time by agent ID
	 * @param agentID
	 * @return
	 */
	public List getEstablishmentCode(String agentID) {
		Object[] obj = null;
		String sql = "select est_code, end_timestamp from tb_billing_action_log where status <> 'UNLOCK' and agent_id = ?";
		logger.info("getEstablishmentCode sql: " + sql);
		List paralist = new ArrayList();
		addParamValue(paralist, agentID);
		try {
			obj = SQLPROCESSOR.executeQuery(sql, paralist.toArray(),
					new ResultProcessor() {
						public Object[] process(ResultSet rs)
								throws SQLException {
							List result = new ArrayList();
							SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
							while (rs.next()) {
								StringBuffer estResult = new  StringBuffer();
								estResult.append(rs.getString("EST_CODE")).append("-_-");
								estResult.append(format.format(rs.getDate("END_TIMESTAMP")));
								result.add(estResult.toString());
							}
							return result.toArray();
						}
					});
		} catch (DatabaseQueryException e) {
			logger.error("Get establishment code take exception: " + e);
		}
		logger.info("Establishment Code:" + obj.length);
		if (null != obj && obj.length > 0) {
			return Arrays.asList(obj);
		}
		return null;
	}

	/**
	 * insert data of tb_billing_action_log and TB_BILLING_EXCLUDED_CLAIM
	 * @param billingForm
	 * @param billedCount
	 */
	public void insertLog(BillingForm billingForm,String billedCount) {
		SQLTransaction transaction = null;
		String tranNo = getTranNo();
		if(null == tranNo){
			logger.info("Get tran No failed, return....");
			return;
		}
		String insertActionLogSQL = "insert into tb_billing_action_log(TRAN_NO,AGENT_ID,EST_CODE,STATUS,RERUN_NO,EXPECTED_COUNT,ACTUAL_COUNT_1,ACTUAL_COUNT_2,CREATE_DATE,UPDATE_DATE,"
				+ "TIME_ZONE,INVOICE_NUMBER,EMAIL_ADDR,START_TIMESTAMP,END_TIMESTAMP) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String insertExcludedClaimSQL = "INSERT INTO TB_BILLING_EXCLUDED_CLAIM(TRAN_NO, CLAIM_NUMBER) values(?, ?)";
		try {
			transaction = SQLPROCESSOR.startTransaction();
			List paralist = new ArrayList();
			addParamValue(paralist, tranNo);
			addParamValue(paralist, billingForm.getAgent().getAgentID());
			addParamValue(paralist, billingForm.getEstablishmentCode());
			addParamValue(paralist, "NEW");
			addParamValue(paralist, 0);
			addParamValue(paralist, billedCount);
			addParamValue(paralist, 0);
			addParamValue(paralist, 0);
			addParamValue(paralist, new Date());
			addParamValue(paralist, new Date());
			addParamValue(paralist, billingForm.getTimeZone());
			addParamValue(paralist, billingForm.getInvoiceNo());
			addParamValue(paralist, billingForm.getAgent().getEmailAddress());
			addParamValue(paralist, billingForm
					.getSelectedUnbilledEstablishment()
					.getOldestCollectionTimestamp());
			addParamValue(paralist, billingForm.getCutOffDate());

			executeUpdate(transaction, insertActionLogSQL, paralist.toArray());
			logger.info("After execute insert billing action log...");
			
			List unbillList = billingForm.getUnbillList();
			logger.info("Excluded list size: " + unbillList.size());
			for(int i =0; i < unbillList.size(); i++){
				List paraExcludedlist = new ArrayList();
				addParamValue(paraExcludedlist, tranNo);
				addParamValue(paraExcludedlist, (Integer)unbillList.get(i));
				executeUpdate(transaction, insertExcludedClaimSQL, paraExcludedlist.toArray());
				logger.info("After execute excluded claim: i=" + i + " tranNo=" + tranNo + " excludedClaim=" + (Integer)unbillList.get(i));
			}
			
			transaction.commitTransaction();
		} catch (Exception e) {
			logger.error("insert billing data LOG take error: " + e);
			try {
				if (null != transaction) {
					transaction.rollbackTransaction();
				}
			} catch (DatabaseTransactionException e1) {
				logger.error("insert billing data rollback: " + e1);
			}
		}
	}
	
	/**
	 * get next transaction No
	 * @return
	 */
	public String getTranNo() {
		String getTranNoSQL = "select LPAD(inetdba.SQ_BILLING_TRAN_NO.Nextval, 10, '0') TRAN_NO from dual";
		logger.info("Get Tran No sql: " + getTranNoSQL);
		try {
			Object[] obj = SQLPROCESSOR.executeQuery(getTranNoSQL, null,
					new ResultProcessor() {
						public Object[] process(ResultSet rs)
								throws SQLException {
							List result = new ArrayList();
							if (rs.next()) {
								logger.info("TRAN_NO = " + rs.getString("TRAN_NO"));
								result.add(rs.getString("TRAN_NO"));
							}
							return result.toArray();
						}
					});
			if (null != obj && obj.length > 0) {
				return (String)obj[0];
			}
		} catch (DatabaseQueryException e) {
			logger.error("Get Tran No take exception: " + e);
		}
		return null;
	}
	
}
