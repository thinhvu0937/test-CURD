package java4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestLogin {
	WebDriver driver;
	String url = "http://localhost:8080/Poly.AsgJv4/Login";
	String USER_NAME_true = "admin";
	String PASS_WORD_true = "admin";
	String USER_NAME_false = "abc";
	String PASS_WORD_false = "123";
	String USER_NAME_null = "";
	String PASS_WORD_null = "";
	String title_true = "Home Page";
	String title_web = "";
	
	LocalDateTime localDateTime = LocalDateTime.now();
	LocalDate localDate = localDateTime.toLocalDate();
	String date = " " + localDate;
	Map<String, Object[]> testNGResult;
	HSSFWorkbook workbook;
	HSSFSheet sheet;

	@Test(priority = 1)
	public void TestLogin_true() {
		try {
			driver.get(url);
			driver.manage().window().maximize();
			WebElement username = driver.findElement(By.id("username"));
			username.sendKeys(USER_NAME_true);
			WebElement password = driver.findElement(By.id("password"));
			password.sendKeys(PASS_WORD_true);
			WebElement login = driver.findElement(By.className("btn-login"));
			login.click();
			Thread.sleep(1000);
			title_web = driver.getTitle();
			if (title_web.contentEquals(title_true)) {
				testNGResult.put("1",
						new Object[] { "Login1", "Kiểm Tra Login",
								"username: " + USER_NAME_true +"\n"+ " password: " + PASS_WORD_true, "Đăng Nhập Thành Công",
								"Pass", date });
			} else {
				testNGResult.put("1", new Object[] { "Login1", "Kiểm Tra Login",
						"username: " + USER_NAME_true +"\n"+ " password: " + PASS_WORD_true, "Đăng Nhập Thất Bại", "Fail", date  });
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			testNGResult.put("2", new Object[] { "Login1", "Test Login", "Login", "Fail", date  });
			Assert.assertTrue(false);
		}
	}

	@Test(priority = 2)
	public void TestLogin_false() {
		try {
			driver.get(url);
			driver.manage().window().maximize();
			WebElement username = driver.findElement(By.id("username"));
			username.sendKeys(USER_NAME_false);
			WebElement password = driver.findElement(By.id("password"));
			password.sendKeys(PASS_WORD_false);
			WebElement login = driver.findElement(By.className("btn-login"));
			login.click();
			Thread.sleep(1000);
			title_web = driver.getTitle();
			if (title_web.contentEquals(title_true)) {
				testNGResult.put("2",
						new Object[] { "Login2", "Kiểm Tra Login",
								"username: " + USER_NAME_false +"\n"+ " password: " + PASS_WORD_false,
								"Đăng Nhập Thành Công", "Pass", date  });
			} else {
				testNGResult.put("2",
						new Object[] { "Login2", "Kiểm Tra Login",
								"username: " + USER_NAME_false +"\n"+ " password: " + PASS_WORD_false, "Đăng Nhập Thất Bại",
								"Fail", date  });
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			testNGResult.put("3", new Object[] { "Login2", "Test Loin", "Login", "Fail" });
			Assert.assertTrue(false);
		}
	}

	@Test(priority = 3)
	public void TestLogin_null() {
		try {
			driver.get(url);
			driver.manage().window().maximize();
			WebElement username = driver.findElement(By.id("username"));
			username.sendKeys(USER_NAME_null);
			WebElement password = driver.findElement(By.id("password"));
			password.sendKeys(PASS_WORD_null);
			WebElement login = driver.findElement(By.className("btn-login"));
			login.click();
			Thread.sleep(1000);
			title_web = driver.getTitle();
			if (title_web.contentEquals(title_true)) {
				testNGResult.put("3",
						new Object[] { "Login3", "Kiểm Tra Login",
								"username: " + USER_NAME_null + "\n"+" password: " + PASS_WORD_null, "Đăng Nhập Thành Công",
								"Pass", date  });
			} else {
				testNGResult.put("3", new Object[] { "Login3", "Kiểm Tra Login",
						"username: " + USER_NAME_null + "\n"+" password: " + PASS_WORD_null, "Đăng Nhập Thất Bại", "Fail", date  });
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			testNGResult.put("4", new Object[] { "Login3", "Test Loin", "Login", "Fail" });
			Assert.assertTrue(false);
		}
	}

	@BeforeClass
	public void suiteSetUp() {
		WebDriverManager.chromedriver().setup();
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet("TestNG Result summary");
		testNGResult = new LinkedHashMap<String, Object[]>();
		testNGResult.put("0", new Object[] { "Test ID", "Mô Tả", "Test Data", "Kết quả Thực Tế", "Trạng Thái", "Ngày Test" });
		try {
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			throw new IllegalStateException("can't start web driver");
		}
	}

	@AfterClass
	public void afterClass() {
		Set<String> keyset = testNGResult.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = testNGResult.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof Date)
					cell.setCellValue((Date) obj);
				else if (obj instanceof Boolean)
					cell.setCellValue((Boolean) obj);
				else if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);
			}
		}
		try {
			FileOutputStream out = new FileOutputStream(new File("TestLoginResultExcel.xls"));
			workbook.write(out);
			out.close();
			System.out.println("Success");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		driver.close();
		driver.quit();
	}
}
