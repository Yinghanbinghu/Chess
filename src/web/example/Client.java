package web.example;

import web.WebListener;
import web.WebProxy;

import javax.swing.*;

public class Client implements WebListener {

    public static void main(String[] args) throws InterruptedException {
        Client c1 = new Client(1);
        Thread.sleep(20);
    }

    WebProxy webProxy;
    int testID;
    public Client(int testID) {
        this.testID = testID;
        webProxy = new WebProxy(this, "103.46.128.49", 37856);
    }

    @Override
    public void gettingAction(String message) {
        System.out.println(testID + "接收到： "+message);
    }

    @Override
    public void showError() {
        JOptionPane.showMessageDialog(null,"没有链接到客户机");
    }
}
