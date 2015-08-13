package br.ifpr.sisplan.util;


import org.junit.Assert;
import org.junit.Test;

public class TestDateUtil {

	@Test
	public void TestDate() {
		Assert.assertTrue(DateUtil.validateDateFormat("21/10/1985"));
		Assert.assertFalse(DateUtil.validateDateFormat("21-10-1985"));
		Assert.assertFalse(DateUtil.validateDateFormat("AAAA"));
		Assert.assertFalse(DateUtil.validateDateFormat("2015-02-09"));
	}
}
