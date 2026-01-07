package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	
	@DataProvider(name="LoginData")
	public Object[][] getData() throws IOException
	{
		String path=".\\testData\\Opencart_LoginData.xlsx";
		
		ExcelUtility xlutil=new ExcelUtility(path);
		
		int totalRows=xlutil.getRowCount("sheet1");
		int totalCols=xlutil.getCellCount("sheet1", 1);
		
		Object[][] loginData=new Object[totalRows][totalCols];
		//created for two dimension array which can store the data user and password

		for(int i=1; i<=totalRows; i++)
		{
			for(int j=0; j<totalCols; j++)
			{
				loginData[i-1][j]=xlutil.getCellData("sheet1", i, j);
			}
		}
		return loginData; //returning two dimension array
	}
}
