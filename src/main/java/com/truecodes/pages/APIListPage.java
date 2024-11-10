package com.truecodes.pages;

import com.truecodes.shared.Activities;
import com.truecodes.shared.FindElements;
import com.truecodes.shared.PageWaits;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIListPage {
    private static APIListPage apiListPageInstance;
    private final FindElements findElements;
    private final Activities activities;
    private final PageWaits wait;
    private final WebDriver driver;
    Map<String, String> api;
    WebElement listGroup;
    private APIListPage(WebDriver webDriver){
        this.activities = Activities.getActionsObject(webDriver);
        this.wait = PageWaits.getPageWaitsObject(webDriver);
        this.findElements = FindElements.getInstance(webDriver);
        this.driver=webDriver;
    }
    public static synchronized APIListPage getInstance(WebDriver webDriver){
        if(apiListPageInstance == null){
            apiListPageInstance = new APIListPage(webDriver);
        }
        return apiListPageInstance;
    }
    public void openApiListPage(){
        // Navigate to "APIs list for practice"
        WebElement apiListBtn = findElements.ByXPath("//div[@class='item active']//button[@type='button'][normalize-space()='APIs list for practice']");
        activities.clickElement(apiListBtn);
    }
    public void baseApiWork(WebElement webElement){
        activities.scrollWindow(webElement);
        activities.clickElement(wait.waitUntilElementToBeClickable(webElement));
        listGroup = findElements.ByClass("list-group");// Locate the <ul> element with the class "list-group"
    }
    public Map<String, String> baseTwoWork(List<String> responseMessage,Map<String, String> api){
        api.put("url",responseMessage.get(0));
        api.put("method",responseMessage.get(1));
        api.put("statusCode",responseMessage.get(2));
        api.put("response",responseMessage.get(3));
        return api;
    }
    public List<String> getApiElements(WebElement webElement, String urlXpath, String methodXpath, String codeXpath, String responseXpath, String replaceResponse){
        List<String> responseMessage = new ArrayList<>();
        responseMessage.add(wait.waitUntilVisibility(urlXpath).getAttribute("href"));
        responseMessage.add(findElements.byWebElementNXpath(webElement,methodXpath).getText().replace("Request Method: ", ""));
        responseMessage.add(findElements.byWebElementNXpath(webElement,codeXpath).getText().replace("Response Code: ", ""));
        responseMessage.add(findElements.byWebElementNXpath(webElement,responseXpath).getText().replace(replaceResponse, ""));
        return responseMessage;
    }
    public Map<String, String> getApi1Elements(){
        WebElement APIElement = findElements.ByXPath("//u[normalize-space()='API 1: Get All Products List']");// expanding API1 GET method
        baseApiWork(APIElement);
        List<String> responseMessage = getApiElements(listGroup, "//li[b/text()='API URL:']/a", "//li[b/text()='Request Method:']","//li[b/text()='Response Code:']","//li[b/text()='Response JSON:']","Response JSON: ");
        return baseTwoWork(responseMessage,new HashMap<>());
    }
    public Map<String, String> getApi2Elements(){
        WebElement APIElement = findElements.ByXPath("//u[normalize-space()='API 2: POST To All Products List']");
        baseApiWork(APIElement);
        List<String> responseMessage = getApiElements(listGroup, "//li[b/text()='API URL:']/a", "/html[1]/body[1]/section[1]/div[1]/div[3]/div[1]/div[2]/ul[1]/li[2]","/html[1]/body[1]/section[1]/div[1]/div[3]/div[1]/div[2]/ul[1]/li[3]","/html[1]/body[1]/section[1]/div[1]/div[3]/div[1]/div[2]/ul[1]/li[4]","Response Message: ");
        return baseTwoWork(responseMessage,new HashMap<>());
    }
    public Map<String, String> getApi4Elements(){
        WebElement APIElement = findElements.ByXPath("//u[normalize-space()='API 4: PUT To All Brands List']");
        baseApiWork(APIElement);
        List<String> responseMessage = getApiElements(listGroup, "//div[@id='collapse4']//a[@target='_blank']", "/html[1]/body[1]/section[1]/div[1]/div[5]/div[1]/div[2]/ul[1]/li[2]","/html[1]/body[1]/section[1]/div[1]/div[5]/div[1]/div[2]/ul[1]/li[3]","/html[1]/body[1]/section[1]/div[1]/div[5]/div[1]/div[2]/ul[1]/li[4]","Response Message: ");
        return baseTwoWork(responseMessage,new HashMap<>());
    }
    public Map<String, String> getApi9Elements(){
        WebElement APIElement = findElements.ByXPath("//u[normalize-space()='API 9: DELETE To Verify Login']");
        baseApiWork(APIElement);
        List<String> responseMessage = getApiElements(listGroup, "//div[@id='collapse9']//a[@target='_blank']", "//div[@id='collapse9']//li[2]","//div[@id='collapse9']//li[3]","//div[@id='collapse9']//li[4]","Response Message: ");
        return baseTwoWork(responseMessage,new HashMap<>());
    }
    public Map<String, String> getApi14Elements(){
        WebElement APIElement = findElements.ByXPath("//u[normalize-space()='API 14: GET user account detail by email']");
        baseApiWork(APIElement);
        List<String> responseMessage = getApiElements(listGroup, "//div[@id='collapse14']//a[@target='_blank']", "//div[@id='collapse14']//li[2]","//div[@id='collapse14']//li[4]","//div[@id='collapse14']//li[5]","Response JSON: ");
        responseMessage.add(findElements.byWebElementNXpath(listGroup,"//div[@id='collapse14']//li[3]").getText().replace("Request Parameters: ", ""));
        api = baseTwoWork(responseMessage,new HashMap<>());
        api.put("params",responseMessage.get(4));
        return api;
    }
}
