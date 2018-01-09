/**
 * 
 */
package com.asiamiles.partnerportal.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


import com.asiamiles.partnerportal.dao.InstantRedeemGroupDao;
import com.asiamiles.partnerportal.domain.InstantRedeemPartnerGroup;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author fuzhen.he
 *
 */
public class InstantRedeemGroupDaoImpl extends JdbcTemplate implements InstantRedeemGroupDao {

	public int storeGroup(final InstantRedeemPartnerGroup instantRedeempartnerGroupModel, final String user) {
		String sql = "insert into TB_INSTANTREDEEM_GROUP(PARTNER_CODE, GROUP_ID, CREATED_TIME, CREATED_BY) values(?,?,sysdate,?)";
		return this.update(sql, new Object[]{instantRedeempartnerGroupModel.getPartnerCode(),
				instantRedeempartnerGroupModel.getGroupId(), user}, new int[]{java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});		
	}

	public int[] storeAgentGroup(final InstantRedeemPartnerGroup instantRedeempartnerGroupModel, final String user) {
		String sql = "insert into TB_INSTANTREDEEM_AGENT_GROUP(PARTNER_CODE, GROUP_ID, AGENT_ID, CREATED_TIME, CREATED_BY) values(?, ?, ?, sysdate, ?)";
		int num[] = new int[]{};

		if (null != instantRedeempartnerGroupModel.getAgentId())	{
			
			this.batchUpdate(sql, new BatchPreparedStatementSetter(){

				public void setValues(PreparedStatement paramPreparedStatement,
						int paramInt) throws SQLException {
					paramPreparedStatement.setString(1, instantRedeempartnerGroupModel.getPartnerCode());
					paramPreparedStatement.setString(2, instantRedeempartnerGroupModel.getGroupId());
					paramPreparedStatement.setString(3, instantRedeempartnerGroupModel.getAgentId()[paramInt]);
					paramPreparedStatement.setString(4, user);
				}

				public int getBatchSize() {
					return instantRedeempartnerGroupModel.getAgentId().length;
				}
				
			});

		}
		return num;
	}

	public int[] storePackageCodeGroup(final InstantRedeemPartnerGroup instantRedeempartnerGroupModel, final String user) {
		String sql = "insert into TB_INSTANTREDEEM_PACKAGE_GROUP(PARTNER_CODE, GROUP_ID, PACKAGE_CODE, CREATED_TIME, CREATED_BY) values(?, ?, ?, sysdate, ?)";
		int[] num = new int[]{};	

		if (null != instantRedeempartnerGroupModel.getPackageCode())	{
			num = this.batchUpdate(sql, new BatchPreparedStatementSetter(){
		
				public int getBatchSize() {
					return instantRedeempartnerGroupModel.getPackageCode().length;
				}

				public void setValues(PreparedStatement paramPreparedStatement,
						int paramInt) throws SQLException {
					paramPreparedStatement.setString(1, instantRedeempartnerGroupModel.getPartnerCode());
					paramPreparedStatement.setString(2, instantRedeempartnerGroupModel.getGroupId());
					paramPreparedStatement.setString(3, instantRedeempartnerGroupModel.getPackageCode()[paramInt]);
					paramPreparedStatement.setString(4, user);
				}
				
			});
		}
		return num;
	}

	public List listInstantPackageCode(final String partnerCode) {
		String sql = "select PACKAGE_CODE from TB_REDEMPTION_ECOUPON_TYPE where PARTNER_CODE = ? and (CODE_STANDARD = 'IRQRCode' or CODE_STANDARD = 'Others')";
		return this.queryForList(sql, new Object[]{partnerCode}, new int[]{java.sql.Types.VARCHAR});
	}

	public List listGroup(String partnerCode) {
		String sql = "select GROUP_ID from TB_INSTANTREDEEM_GROUP where PARTNER_CODE = ?";
		return this.queryForList(sql, new Object[]{partnerCode}, new int[]{java.sql.Types.VARCHAR});
	}
	
	public List queryPackageCodeByAgentId(String partnerCode, String agentId, String packageCode) {
		String sql = "SELECT packages.PACKAGE_CODE FROM TB_INSTANTREDEEM_AGENT_GROUP agents, Tb_INSTANTREDEEM_PACKAGE_GROUP packages" +
				" WHERE agents.GROUP_ID = packages.GROUP_ID AND agents.PARTNER_CODE = packages.PARTNER_CODE AND agents.PARTNER_CODE = ? AND agents.AGENT_ID = ? AND packages.PACKAGE_CODE = ?";
		return this.queryForList(sql, new Object [] {partnerCode, agentId, packageCode}, new int [] {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
	}

	public List queryAgentByGroupName(String partnerCode, String groupId) {
		String sql = "SELECT AGENT_ID FROM TB_INSTANTREDEEM_AGENT_GROUP WHERE PARTNER_CODE = ? AND GROUP_ID = ?";
		return this.queryForList(sql, new Object [] {partnerCode, groupId},new int [] {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
	}

	public int clearAgentGroup(String partnerCode, String groupId) {
		String sql = "DELETE FROM TB_INSTANTREDEEM_AGENT_GROUP WHERE PARTNER_CODE = ? AND GROUP_ID = ?";
		return this.update(sql, new Object [] {partnerCode, groupId}, new int [] {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
	}

	public int clearPackageCodeGroup(String partnerCode, String groupId) {
		String sql = "DELETE FROM TB_INSTANTREDEEM_PACKAGE_GROUP WHERE PARTNER_CODE = ? AND GROUP_ID = ?";
		return this.update(sql, new Object [] {partnerCode, groupId}, new int [] {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
	}
	
	public List queryPackageByGroupName(String partnerCode, String groupId) {
		String sql = "SELECT PACKAGE_CODE FROM TB_INSTANTREDEEM_PACKAGE_GROUP WHERE PARTNER_CODE = ? AND GROUP_ID = ?";
		return this.queryForList(sql, new Object [] {partnerCode, groupId},new int [] {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
	}

	public int deleteGroup(String partnerCode, String groupId) {
		String sql = "DELETE FROM TB_INSTANTREDEEM_GROUP WHERE PARTNER_CODE = ? AND GROUP_ID = ?";
		return this.update(sql, new Object [] {partnerCode, groupId}, new int[] {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
	}

	public List queryGroupByPackageCode(final String partnerCode, final String packageCode) {
		String sql = "SELECT GROUP_ID FROM TB_INSTANTREDEEM_PACKAGE_GROUP WHERE PARTNER_CODE = ? AND PACKAGE_CODE = ?";
		return this.queryForList(sql, new Object [] {partnerCode, packageCode}, new int [] {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
	}

	public int clearGroupForPackageCode(String partnerCode, String packageCode) {
		String sql = "DELETE FROM TB_INSTANTREDEEM_PACKAGE_GROUP WHERE PARTNER_CODE = ? AND PACKAGE_CODE = ?";
		return this.update(sql, new Object [] {partnerCode, packageCode}, new int [] {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
	}

	public int[] storeGroupForPackageCode(final String [] groupId, final String packageCode,
			final String partnerCode, final String user) {
		
		String sql = "insert into TB_INSTANTREDEEM_PACKAGE_GROUP(PARTNER_CODE, GROUP_ID, PACKAGE_CODE, CREATED_TIME, CREATED_BY) values(?, ?, ?, sysdate, ?)";
		int[] num = new int[]{};

		if (null != groupId)	{
			num = this.batchUpdate(sql, new BatchPreparedStatementSetter(){
		
				public int getBatchSize() {
					return groupId.length;
				}

				public void setValues(PreparedStatement paramPreparedStatement,
						int paramInt) throws SQLException {
					paramPreparedStatement.setString(1, partnerCode);
					paramPreparedStatement.setString(2, groupId[paramInt]);
					paramPreparedStatement.setString(3, packageCode);
					paramPreparedStatement.setString(4, user);
				}
				
			});
		}
		return num;
	}
	
	public int deleteAgentGroupByAgentId(String agentId) {
		String sql = "DELETE FROM TB_INSTANTREDEEM_AGENT_GROUP WHERE AGENT_ID = ?";
		return this.update(sql, new Object [] {agentId}, new int [] {java.sql.Types.VARCHAR});
	}

	public List queryPackageByPartnerCode(String partnerCode, String packageCode) {

		String sql = "SELECT PACKAGE_CODE FROM TB_REDEMPTION_ECOUPON_TYPE WHERE PARTNER_CODE = ? AND PACKAGE_CODE = ?";
		return this.queryForList(sql, new Object [] {partnerCode, packageCode}, new int [] {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
	}

}
