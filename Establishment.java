import java.io.*;
import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.String.format;

public class Establishment {
    private String name;
    private String firstLineAddress;
    private String postCode;
    private String address = this.firstLineAddress + this.postCode;
    private int maxOccupancy;


//Creators -- finished
    public Establishment() {
    }

    public Establishment(String name, String firstLineAddress, String postCode, int maxOccupancy) {
        this.name = name;
        this.firstLineAddress = firstLineAddress;
        this.postCode = postCode;
        this.maxOccupancy = maxOccupancy;
    }

    public Establishment(String name, String address, int maxOccupancy ){
        this.name = name;
        String[] s = address.split(",");
        firstLineAddress = s[0];
        postCode = s[1];
        this.maxOccupancy = maxOccupancy;
    }

    // getters
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getFirstLineAddress() {
        return firstLineAddress;
    }
    public String getPostCode() {
        return postCode;
    }
    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    // setters - dont need

    // eventual printing information


    @Override
    public String toString() {
        return "Name: " + this.name +
                ",\n" + "Address: " +
                firstLineAddress + ", " + postCode;
    }


    public void append(String string) {
        try {
            File file = new File(Controller.class.getResource("establishments.csv").getFile());
            FileWriter fw = new FileWriter(file, true);
            fw.write("\n" + string);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}





