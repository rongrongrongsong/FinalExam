package pkgEmpty;

import static org.junit.Assert.*;

import org.junit.Test;

import pkgCore.Retirement;

public class TestRetire {

	@Test
	public void test() {
		int yearstoWork=40;
		double workAnnualReturn=0.07;
		int yearsRetired=20;
		double retiredAnnualReturn=0.02;
		double yearsRetiredIncome=10000.00;
		double monthlySSI=2642;
		Retirement r=new Retirement(yearstoWork,workAnnualReturn,yearsRetired,retiredAnnualReturn,yearsRetiredIncome,monthlySSI);
		double expectedSave=-1454485.55;
		double expectedamounttoSave=554.13;
		assertEquals(r.AmountToSave(r.TotalAmountSaved()),expectedamounttoSave,0.01);
		assertEquals(r.TotalAmountSaved(),expectedSave,0.01);
	}

}
