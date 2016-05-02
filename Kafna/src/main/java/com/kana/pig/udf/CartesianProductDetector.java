package com.kana.pig.udf;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.logicalLayer.schema.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * class detects actually completed joins not attempts that failed. 
 * TODO: mode documentation will follow
 * 
 * @author BAFF64F
 *
 */

public class CartesianProductDetector extends EvalFunc<Tuple> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CartesianProductDetector.class);
	private String leftAlias;
	private String rightAlias;
	private String joinType;
	private String beforeJoinCount;
	private String afterJoinCount;
	/*
	 * In left join this is the row count of the left table. For right join this
	 * the row count of the right table For inner join the count of either table
	 * can be used.
	 */
	private long beforeJoinRowCount;
	private long afterJoinRowCount;

	public CartesianProductDetector(String leftAlias, String rigtAlias, String joinType, String beforeJoinCount,
			String afterJoinCount) {
		LOGGER.info("====================>> In Constructor....");
		this.leftAlias = leftAlias;
		this.rightAlias = rigtAlias;
		this.joinType = joinType;
		this.beforeJoinCount = beforeJoinCount;
		this.afterJoinCount = afterJoinCount;
		this.beforeJoinRowCount = Long.parseLong(beforeJoinCount);
		this.afterJoinRowCount = Long.parseLong(afterJoinCount);
	}

	@Override
	public Tuple exec(Tuple input) throws IOException {
		LOGGER.info("---------->> In exec() method");
		if (cartesianPoductDetected()) {
			logErrorMessageAndStop();
		}
		return input;
	}

	@Override
	public Schema outputSchema(Schema input) {
		LOGGER.info("~~~~~~~~~~~~~~~>> In outputSchema ....");
		if (cartesianPoductDetected()) {
			logErrorMessageAndStop();
		}
		return input;
	}

	private void logErrorMessageAndStop() {
		StringBuilder builder = new StringBuilder(100);
		builder.append("Cartesian product detected while joining ").append(leftAlias).append(" and ").append(rightAlias);
		builder.append("using ").append(joinType).append(".\n");
		builder.append("Row count before join: ").append(beforeJoinCount).append(".\n");
		builder.append("Row count after join: ").append(afterJoinCount).append(".\n");
		LOGGER.error(builder.toString());
		throw new RuntimeException(builder.toString());
	}

	// beforeJoinRowCount for left join is the row count of the left table in
	// the join.
	// similarly for right join it is the row count of the right table.
	private boolean cartesianPoductDetected() {
		boolean cartesianProductDetected = false;
		if (joinType.equalsIgnoreCase("LEFT JOIN") || joinType.equalsIgnoreCase("RIGHT JOIN")) {
			if (afterJoinRowCount != beforeJoinRowCount) {
				cartesianProductDetected = true;
			}
		} else if (joinType.equalsIgnoreCase("INNER JOIN") || joinType.equalsIgnoreCase("JOIN")) {
			if (afterJoinRowCount > beforeJoinRowCount) {
				cartesianProductDetected = true;
			}
		}
		return cartesianProductDetected;
	}

}
