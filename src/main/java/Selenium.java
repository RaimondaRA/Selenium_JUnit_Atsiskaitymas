import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Selenium {
    public static final String SEARCH_BUTTON_BY_XPATH = "//*[@id=\"sb_form_go\"]"; //susikuriame globalu mygtuko kintamaji, kad galetume ji naudoti keliose vietose = mygtuko search kelias
    private static WebDriver browser; //susikuriame, apsirasome globalu kintamaji. private - nes uz klases ribu sio kintamojo nenaudosime
    public static void main(String [] args){ //main - visada bus viena f-ja, paleidziamoji. String'o (eiluciu) masyvas, args - issitraukti is vartotojo parametrus, duomenis. Static- nereikia kurits klases objekto, kad galetume panaudoti sita objekta. main - visada public ir static. Jei norime is main kreiptis i kitas f-jas, jos irgi privalo buti static
        System.out.println("Selenium + maven + jUnit");
//        setup(); //kreipiames i funkcija. F-jos uzkomentuotoas, nes nera butina paleisti jas or per Selenium.java ir per SeleniumTest.java. Testuotojas paleidzia SeleniumTest.java, o ne Selenium.java
//        search("Baranauskas"); //kreipiames i f-ja
//        getResults(); //kreipiames i f-ja
//        close(); //kreipiames i f-ja
        //System.out.println("Baranausko paieskos rezultatu skaicius lygus " + resultInt); //f-ja grazina, kiek pavyko rasti Baranausko rezultatu (skaiciu)
    }

    public static void setup(){ //main - static, todel ir f-ja, i kuria kreipiames is main, turi buti static. Globalus kintamieji, siuo atveju browser, taip pat turi buti static
        //Sujungiamas kodas su narsykle - driver'iai sujungia
        System.setProperty("webdriver.chrome.driver", "webdrivers/chromedriver91.exe"); //nesvarbu, koks vartotojas yra prisijunges prie sistemos, musu driver'is yra projekte. REikia surasti webdriverius - veiksmai pries

        browser = new ChromeDriver(); //sukuriama nauja Chrome driver instancija. Sukuriamas objektas, zemiau eiluteje - kreipiames i objekta
        browser.get("http://bing.com"); //uzkrauna puslapi, atidarys narsykle, nieko negrazina. Veiksmai pries
    }

    public static void search(String keyword){
        //Kiekviena elementa is internetinio puslapio issitraukiame pagal jo unikalu identifikatoriu - id, name, className
        //zemiau - surandamas elementas is psl pagal unikalu identifikatoriu. Siuo atveju tai paieskos laukas, i kuri reikes ivesti zodi "Baranauskas"
        WebElement searchField = browser.findElement(By.id("sb_form_q"));
        searchField.sendKeys(keyword); //suradus elementa puslapyje, i ji irasomas paieskos raktinis zodis (Baranauskas)
        //searchField.sendKeys(Keys.ENTER); //irasius paieskos raktini zodi, paieska vykdoma paspaudus Enter mygtuka

        //Mygtuko paspaudimas (paieska vykdoma paspaudus mygtuka)
        // 1. VEIKIANTIS VARIANTAS naudojant JavascriptExecutor, tuose psl., kur naudojamas JavaScript
        WebElement ele = browser.findElement(By.xpath(SEARCH_BUTTON_BY_XPATH)); //susirandame mygtuka
        JavascriptExecutor executor = (JavascriptExecutor)browser; //naudojama klase JavascripExecutor. Is browser issitraukiame ta elementa Javascript. Is puslapio issitraukiame Javascript. Kadangi yra naudojamas JS, todel turime tai aprasyti sioje eiluteje
        executor.executeScript("arguments[0].click();", ele); //vykdyk skripta. Kreipiames i kintamaji arguments. .click - paspausime [0] pirma arguments masyvo elementa, kuri nurodome - ele

        // 2. NEVEIKIA, kur naudojamas Javascript. Gaunamas org.openqa.selenium.ElementNotInteractableException: element not interactable
        /*WebDriverWait wait2 = new WebDriverWait(browser, 2); //sukuriamas WebDriverWait objektas, laukia iki 2 sec, kol atidarys psl (standartinis - 2sec.), laikia to elemento, kuris bus uzkrautas
        wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(SEARCH_BUTTON_BY_XPATH)));
        browser.findElement(By.xpath(SEARCH_BUTTON_BY_XPATH)).click();*/

        // 3. NEVEIKIA, kur naudojamas Javascript. Gaunamas org.openqa.selenium.ElementNotInteractableException: element not interactable
        /*WebDriverWait wait1 = new WebDriverWait(browser, 10);
        WebElement element1 = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(SEARCH_BUTTON_BY_XPATH)));
        element1.click();*/
    }

    public static int getResults(){ //atskira f-ja paieskos rezultatu grazinimui
        // Atlikus paieksa, perziurimas paieskos rezultatu web elementas
        WebElement result2 = browser.findElement(By.xpath("//*[@id=\"b_tween\"]/span")); //xpath - kelias iki elemento. Gali buti reliatyvus ir absoliutus. Cia - reliatyvus, kur yra tiesiog tas elementas

        //Konvertuojame "118 000 Rezultatai" -> "118 000", nes mums reikes skaiciaus rezultatu palyginimui
        String resultStr = result2.getText() //atlike "Baranausko" paieska bing'e gauname: 118 000 Rezultatai. getText - istraukiamas tekstas
                .replaceAll("[a-zA-Z]", "")
                .replaceAll("[ ,]", "");

        //Konvertuojame is String i int, nes mums reikes palyginti rezultata
        int resultInt = Integer.parseInt(resultStr);
        return resultInt;

//         if(resultInt > 50000) { //Naudosime assertEquals() metoda, kuris palygins, todel if'u nereikia
//            System.out.println("Baranauskas yra populiarus");
//        } else {
//            System.out.println("Baranauskas nera populiarus");
//        }
    }

    public static void close(){
        browser.close(); //uzdaro narsykles langa

    }
}




