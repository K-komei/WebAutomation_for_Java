package WebControl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Thread.sleep;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

//"出発駅","経由駅","到着駅"をCSVデータより取得し、【ジョルダン】にて検索をかける。
//検索の最安値を取得し、CSVに料金を追記する。

public class RunControlChrome {

        public static void main(String[] args){

        //Webドライバー設定
            String driverName = "webdriver.chrome.driver";
            String driverUrl = "C:/Users/theme/Documents/Dev_Factory/Automation_for_Java/chromedriver.exe";
            MachineGenerator mg = new MachineGenerator(driverName,driverUrl);

            //Webドライバーオブジェクトを生成
            WebDriver Machine;
            Machine = mg.getMachine();
            FluentWait<WebDriver> wait = new WebDriverWait(Machine, 10);


            //CSV操作オブジェクトを生成
            String path = "C:/Users/theme/Desktop/testCsv.csv";
            ControlCSV csvData = new ControlCSV(path);

        //各種オブジェクトの設定確認
            if(Objects.equals(Machine,null) || Objects.equals(csvData,null) ){
            System.out.println("Main Object is null");
            System.exit(1004);
        }

        //メイン処理開始
        try{
            //入力データ格納リスト[inputStationData]
            //出力データ格納リスト[outputCashData]
            ArrayList<String[]> inputStationData = csvData.GetCsvData();
            ArrayList<String[]> outputCashData = new ArrayList<String[]>();

      // 入力データ行数分繰り返す
      for (String[] Lines : inputStationData) {
        // 入力データから各種駅名を取得
        String startStation = Lines[0];
        String viaStation = Lines[1];
        String endStation = Lines[2];

        String goUrl = "https://www.navitime.co.jp/transfer/?fromlink=pcnavi.header";
        String getCash = "";
        String[] resultLine = new String[4];

        // Webフォーム操作
        Machine.get(goUrl);
        wait.until(visibilityOfElementLocated(By.id("search-area")));
        Machine.findElement(By.id("orv-station-name")).sendKeys(startStation);
        Machine.findElement(By.id("th1-station-name")).sendKeys(viaStation);
        Machine.findElement(By.id("dnv-station-name")).sendKeys(endStation);
        Machine.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[1]/div/form/div[3]/input"))
            .click();
        wait.until(visibilityOfElementLocated(By.id("left_pane")));
        sleep(3);
        Machine.findElement(By.linkText("運賃が安いルート")).click();
        wait.until(visibilityOfElementLocated(By.id("left_pane")));
        sleep(3);
        getCash =
            Machine.findElement(
                    By.xpath(
                        "/html/body/div[1]/div/div/div[1]/ol[2]/li[1]/div[1]/dl[1]/dd[1]/span[1]"))
                .getText();

        // Webページより取得した結果を配列に格納
        resultLine[0] = Lines[0];
        resultLine[1] = Lines[1];
        resultLine[2] = Lines[2];
        resultLine[3] = getCash;

        // 配列を出力用リストに追加
        outputCashData.add(resultLine);
      }
      //Web操作オブジェクトを停止
      Machine.close();
      //結果をCSVに出力
      csvData.OutputCsvData(outputCashData);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
     }
    }



