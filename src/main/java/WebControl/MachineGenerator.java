package WebControl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MachineGenerator {

    WebDriver targetDriver;
    private String driverName;
    private String driverUrl;


    public MachineGenerator(String driverUrl,String driverName){
    this.driverName = driverName;
    this.driverUrl = driverUrl;
    }

    public WebDriver getMachine(){
        System.setProperty(this.driverName,this.driverUrl);
        this.targetDriver =new ChromeDriver();
        return  this.targetDriver;
    }

}