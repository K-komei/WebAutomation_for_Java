package WebControl;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ControlCSV {
  File csvFile;
  ArrayList csvData;

  ControlCSV(String csvFilePath) {
    // set CSV Path
    csvFile = new File(csvFilePath);
    csvData = new ArrayList();
  }

  public ArrayList<String[]> GetCsvData() throws IOException {
    try {

      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "SHIFT_JIS"));
      String line;
              //= new String("uft-8");
      while ((line = br.readLine()) != null) {
        this.csvData.add(line.split(","));
      }
      br.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return this.csvData;
  }

  public void OutputCsvData(ArrayList<String[]> writeSource) throws IOException{
    try {
      BufferedWriter bw;
      bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile),"SHIFT_JIS"));
      for (String[] lines : writeSource){
        String inputLine = String.join(",",lines);
        bw.write(inputLine);
        bw.newLine();
      }
      bw.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }
}