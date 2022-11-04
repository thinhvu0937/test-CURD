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

public class TestCreateUser {
	WebDriver driver;
	String url = "http://localhost:8080/Poly.AsgJv4/Login";
	String YOUTUBE_ID = "admin";
	String PASS_WORD = "admin";
	String USER_TRUE = "admin";
	String PASS_TRUE = "admin";
	String FULLNAME_TRUE = "8";
	String DESCRIPTION = "vjp";
	
	LocalDateTime localDateTime = LocalDateTime.now();
	LocalDate localDate = localDateTime.toLocalDate();
	String date = " " + localDate;
	Map<String, Object[]> testNGResult;
	HSSFWorkbook workbook;
	HSSFSheet sheet;
  @Test
  public void TestCreate_True() {
		try {
			driver.get(url);
			driver.manage().window().maximize();
			WebElement usernamelogin = driver.findElement(By.id("username"));
			usernamelogin.sendKeys(YOUTUBE_ID);
			WebElement passwordlogin = driver.findElement(By.id("password"));
			passwordlogin.sendKeys(PASS_WORD);
			WebElement login = driver.findElement(By.className("btn-login"));
			login.click();
			Thread.sleep(1000);
			WebElement admin = driver.findElement(By.id("admin"));
			admin.click();
			Thread.sleep(1000);
			
			WebElement videos = driver.findElement(By.id("videos"));
			videos.click();
			Thread.sleep(1000);
			WebElement videoId = driver.findElement(By.id("youtubeId"));
			videoId.sendKeys(USER_TRUE);
			WebElement videoTile = driver.findElement(By.id("videoTitle"));
			videoTile.sendKeys(PASS_TRUE);			
			WebElement viewCount = driver.findElement(By.id("viewCount"));
			viewCount.sendKeys(FULLNAME_TRUE);
			WebElement active = driver.findElement(By.name("active"));
			active.click();
			WebElement description =driver.findElement(By.id("description"));
			description.sendKeys(DESCRIPTION);
			WebElement create = driver.findElement(By.id("Create"));
			create.click();
			Thread.sleep(1000);
			testNGResult.put("1",new Object[] { "Create2", "Kiểm Tra Create user"
					,"username: " + YOUTUBE_ID +"\n"+ " password: " + PASS_WORD + "\n"+" user_input: "+ USER_TRUE +"\n"+ " pass_input: " + PASS_TRUE + "\n"+
					" fullname: " + FULLNAME_TRUE +"\n"+ " Email: " ,
					"Them User thanh cong", "Pass", date  });
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			testNGResult.put("1",new Object[] { "Create2", "Kiểm Tra Create user"
					,"username: " + YOUTUBE_ID + " password: " + PASS_WORD + " user_input: "+ USER_TRUE +"\n"+ " pass_input: " + PASS_TRUE + 
					" fullname: " + FULLNAME_TRUE + " Email: "  ,
					"Them User thanh cong", "Fail", date  });
			Assert.assertTrue(false);
		}
	}
  
  
	@BeforeClass
	public void suiteSetUp() {
		WebDriverManager.chromedriver().setup();
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet("Test Create User Result");
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
			FileOutputStream out = new FileOutputStream(new File("TestCreateUserResultExcel.xls"));
			workbook.write(out);
			out.close();
			System.out.println("Success");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
